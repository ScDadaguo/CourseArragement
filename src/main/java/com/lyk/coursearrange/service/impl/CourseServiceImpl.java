package com.lyk.coursearrange.service.impl;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyk.coursearrange.dao.CourseMapper;
import com.lyk.coursearrange.dao.CourseUserMapper;
import com.lyk.coursearrange.entity.dto.CourseExcelDto;
import com.lyk.coursearrange.entity.po.CoursePo;
import com.lyk.coursearrange.entity.po.CourseUserPo;
import com.lyk.coursearrange.entity.request.CourseUserAddReq;
import com.lyk.coursearrange.enums.CourseMomentEnum;
import com.lyk.coursearrange.enums.CourseUserTypeEnum;
import com.lyk.coursearrange.enums.ProjectEnum;
import com.lyk.coursearrange.service.CourseService;
import com.lyk.coursearrange.util.SearchUtil;
import com.lyk.coursearrange.util.TestFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseMapper courseMapper;
    @Resource
    private CourseUserMapper courseUserMapper;

    @Override
    public void addPatient(CourseUserAddReq courseUserAddReq) {
        courseUserMapper.insert(fromCourseUserAddReq(courseUserAddReq));
        QueryWrapper<CourseUserPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", courseUserAddReq.getName());
        CourseUserPo courseUserPo = courseUserMapper.selectOne(queryWrapper);
        processAddPatient(courseUserPo.getId());
    }

    @Override
    public void deletePatient(Integer userId) {

        QueryWrapper<CourseUserPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId).eq("user_type", CourseUserTypeEnum.PATIENT.getCode());

        final CourseUserPo patient = courseUserMapper.selectOne(queryWrapper);
        if (patient != null) {
            courseUserMapper.deleteById(patient.getId());
            QueryWrapper<CoursePo> coursePoQueryWrapper = new QueryWrapper<>();
            coursePoQueryWrapper.eq("patient_user_id", patient.getId());
            courseMapper.delete(coursePoQueryWrapper);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processAddPatient(Integer userId) {

        final CourseUserPo patient = courseUserMapper.selectById(userId);
        if (patient == null) {
            throw new RuntimeException("用户不存在");
        }
        //获取需要他需要做的项目
        List<ProjectEnum> projectEnumList = SearchUtil.strToList(patient.getResponsibilities())
                .stream().map(ProjectEnum::getByCode)
                .collect(Collectors.toList());

        for (ProjectEnum projectEnum : projectEnumList) {
            processSingleProject(projectEnum, patient);
        }
    }

    @NotNull
    private Map<CourseUserPo, List<CoursePo>> getEveryDoctorAndMachineCourses(List<CourseUserPo> courseUserPos) {
        Map<Integer, List<CourseUserPo>> map = courseUserPos.stream().collect(Collectors.groupingBy(CourseUserPo::getId));

        QueryWrapper<CoursePo> coursePoQueryWrapper = new QueryWrapper<>();
        coursePoQueryWrapper.in("doctor_user_id", map.keySet());
        List<CoursePo> coursePos = courseMapper.selectList(coursePoQueryWrapper);

        final Map<Integer, List<CoursePo>> integerListMap = coursePos.stream().collect(Collectors.groupingBy(CoursePo::getDoctorUserId));
        Map<CourseUserPo, List<CoursePo>> result = new HashMap<>();
        for (Map.Entry<Integer, List<CourseUserPo>> entry : map.entrySet()) {
            result.put(entry.getValue().get(0), integerListMap.get(entry.getKey()));
        }
        return result;
    }

    //处理单个项目
    private void processSingleProject(ProjectEnum projectEnum, CourseUserPo patient) {
        // 根据负责内容查询医生/机器
        List<CourseUserPo> courseUserPos = queryResponsibilitiesDoctorAndMachine(projectEnum);
        // 根据医生/机器查询已经排的课程 分组 key:userId value:courseList
        Map<CourseUserPo, List<CoursePo>> map = getEveryDoctorAndMachineCourses(courseUserPos);
        // 医生/机器 课程数从小到大排序的CourseUserPo
        List<CourseUserPo> doctors = map.entrySet().stream()
                .sorted(Comparator.comparingInt(o -> o.getValue().size()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        boolean isFind = false;
        for (CourseUserPo doctor : doctors) {
            // 找到该医生空闲时间段
            List<CoursePo> emptyMoment = findEmptyMoment(projectEnum, map, doctor);

            for (CoursePo coursePo : emptyMoment) {
                //判断这节课是否和高压氧时间冲突
                if (isIntersection(patient, coursePo)) {
                    continue;
                } else {
                    isFind = true;
                    coursePo.setCourseType(projectEnum.getCode());
                    coursePo.setDoctorUserId(doctor.getId());
                    coursePo.setDoctorUserName(doctor.getName());
                    coursePo.setPatientUserName(patient.getName());
                    coursePo.setPatientUserId(patient.getId());
                    courseMapper.insert(coursePo);
                    break;
                }
            }
            if (isFind) {
                break;
            }
        }
        if (!isFind) {
            throw new RuntimeException("没有医生/机器可以安排");
        }
    }

    //判断这节课是否和高压氧时间冲突
    private static boolean isIntersection(CourseUserPo patient, CoursePo coursePo) {
        return intersection(CourseMomentEnum.getByCode(coursePo.getMomentType()), patient.getHyperbaricOxygenStartTime(), patient.getHyperbaricOxygenEndTime());
    }

    @NotNull
    private List<CoursePo> findEmptyMoment(ProjectEnum projectEnum, Map<CourseUserPo, List<CoursePo>> map, CourseUserPo doctor) {
        List<CoursePo> coursePoList = buildNullMomentList(map.get(doctor), projectEnum);
        coursePoList = coursePoList.stream().filter(x -> x.getDoctorUserId() == null).collect(Collectors.toList());
        return coursePoList;
    }

    /**
     * 根据负责内容查询医生/机器
     *
     * @param projectEnum
     * @return
     */
    private List<CourseUserPo> queryResponsibilitiesDoctorAndMachine(ProjectEnum projectEnum) {
        QueryWrapper<CourseUserPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("user_type", CourseUserTypeEnum.DOCTOR.getCode(), CourseUserTypeEnum.MACHINE.getCode());

        queryWrapper.like("responsibilities", projectEnum.getCode());

        return courseUserMapper.selectList(queryWrapper).stream().filter(x -> SearchUtil.strToList(x.getResponsibilities())
                        .stream()
                        .anyMatch(y -> y.equals(projectEnum.getCode())))
                .collect(Collectors.toList());
    }


    @Override
    public void exportInfoToExcel() {

        final List<CoursePo> coursePos = courseMapper.selectList(new QueryWrapper<>());
        final Map<String, List<CoursePo>> groupByCourseType =
                coursePos.stream().collect(Collectors.groupingBy(CoursePo::getDoctorUserName));

        List<List<CoursePo>> matchList = new ArrayList<>();

        for (Map.Entry<String, List<CoursePo>> entry : groupByCourseType.entrySet()) {

            List<CoursePo> coursePoList = entry.getValue();

            coursePoList = buildNullMomentList(coursePoList, null);

            matchList.add(coursePoList);
        }

        final List<CourseExcelDto> excelDtoList =
                matchList.stream()
                        .sorted((Comparator.comparingInt(o -> o.stream().map(CoursePo::getSort).filter(Objects::nonNull).findAny().orElse(100))))
                        .map(x -> {
                            CourseExcelDto courseExcelDto = new CourseExcelDto();
                            final String doctorUserName = x.stream().filter(item -> {
                                        return StringUtils.isNotBlank(item.getDoctorUserName());
                                    })
                                    .findFirst()
                                    .get().getDoctorUserName();
                            courseExcelDto.setProject(doctorUserName);
                            for (int i = 0; i < 14; i++) {
                                CoursePo coursePo = x.get(i);
                                Method method = null;
                                try {
                                    method = CourseExcelDto.class.getMethod("setMoment" + (i + 1), String.class);
                                    method.setAccessible(true);
                                    method.invoke(courseExcelDto, coursePo.getPatientUserName());
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            return courseExcelDto;
                        }).collect(Collectors.toList());

        String fileName = TestFileUtil.getPath() + "儿康排课表" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写
        try (ExcelWriter excelWriter = EasyExcel.write(fileName, CourseExcelDto.class).build()) {
            WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
            excelWriter.write(excelDtoList, writeSheet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<CoursePo> buildNullMomentList(List<CoursePo> coursePoList, ProjectEnum projectEnum) {
        List<CourseMomentEnum> momentEnums = null;
        if (projectEnum == null || projectEnum.isLL()) {
            momentEnums =
                    Arrays.stream(CourseMomentEnum.values())
                            .sorted(Comparator.comparing(CourseMomentEnum::getCode))
                            .collect(Collectors.toList());
        } else {
            momentEnums = Arrays.stream(CourseMomentEnum.values())
                    .filter(CourseMomentEnum.SEVENTH::equals)
                    .sorted(Comparator.comparing(CourseMomentEnum::getCode))
                    .collect(Collectors.toList());
        }


        List<Integer> allMomentTemp = momentEnums.stream().map(CourseMomentEnum::getCode).collect(Collectors.toList());
        allMomentTemp.removeAll(
                coursePoList.stream().map(CoursePo::getMomentType).collect(Collectors.toList())
        );
        for (Integer momentType : allMomentTemp) {
            final CoursePo coursePo = new CoursePo();
            coursePo.setMomentType(momentType);
            coursePoList.add(coursePo);
        }
        coursePoList = coursePoList
                .stream()
                .sorted(Comparator.comparing(CoursePo::getMomentType))
                .collect(Collectors.toList());
        return coursePoList;
    }


    /**
     * 从excel中读取数据
     */
    @Override
    public void importDataFromExcel() {
        String fileName = "src/main/resources/template/现有课表.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, CourseExcelDto.class, new ExcelDataListener(this)).sheet().doRead();

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void initData(List<CourseExcelDto> list) {
        // 1.删除所有病人
        deleteAllPatients();

        List<CoursePo> coursePos = new ArrayList<>();
        for (CourseExcelDto item : list) {
            try {
                for (int i = 1; i <= 14; i++) {
                    Field field = CourseExcelDto.class.getDeclaredField("moment" + i);
                    field.setAccessible(true);
                    String value = (String) field.get(item);
                    if (StringUtils.isBlank(value)) {
                        continue;
                    }
                    CourseUserPo patient = courseUserMapper.selectOne(new QueryWrapper<CourseUserPo>().eq("name", value));

                    final CourseUserPo doctor = courseUserMapper.selectOne
                            (new QueryWrapper<CourseUserPo>()
                                    .eq("name", item.getProject())
                                    .in("user_type", CourseUserTypeEnum.DOCTOR.getCode(), CourseUserTypeEnum.MACHINE.getCode())
                            );
                    if (doctor == null) {
                        throw new RuntimeException("医生不存在，系统错误");
                    }
                    if (patient == null) {
                        patient = new CourseUserPo();
                        patient.setName(value);
                        patient.setUserType(CourseUserTypeEnum.PATIENT.getCode());
                        patient.setSort(doctor.getSort());
                        courseUserMapper.insert(patient);
                    }
                    CoursePo coursePo = new CoursePo();
                    coursePo.setPatientUserName(value);
                    coursePo.setPatientUserId(patient.getId());
                    coursePo.setDoctorUserName(item.getProject());
                    coursePo.setDoctorUserId(doctor.getId());
                    coursePo.setMomentType(i);
                    coursePo.setCourseType(null);
                    coursePo.setSort(doctor.getSort());
                    coursePos.add(coursePo);
                }
            } catch (Exception e) {
                log.error("初始化数据失败", e);
                throw new RuntimeException("excel数据格式错误");
            }
        }
        if (CollectionUtils.isEmpty(coursePos)) {
            return;
        }
        deleteAllCourses();

        coursePos.stream().parallel().forEach(courseMapper::insert);
    }

    private void deleteAllCourses() {
        final List<Integer> existIds =
                courseMapper.selectList(new QueryWrapper<CoursePo>()).stream().map(CoursePo::getId).collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(existIds)) {
            courseMapper.deleteBatchIds(existIds);
        }
    }

    private void deleteAllPatients() {
        final List<Integer> patientIds = courseUserMapper.selectList(new QueryWrapper<CourseUserPo>().eq("user_type", 2))
                .stream()
                .map(CourseUserPo::getId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(patientIds)) {
            return;
        }
        courseUserMapper.deleteBatchIds(patientIds);
    }


    public static LocalDateTime formDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static boolean intersection(CourseMomentEnum momentEnum, Date startTimeNew, Date endTimeNew) {
        if (startTimeNew == null || endTimeNew == null) {
            return false;
        }

        return intersection(momentEnum.getStartTime().toLocalTime(), momentEnum.getEndTime().toLocalTime(), formDate(startTimeNew).toLocalTime(), formDate(endTimeNew).toLocalTime());
    }

    public static boolean intersection(LocalTime startTime, LocalTime endTime, LocalTime startTimeNew, LocalTime endTimeNew) {


        if (endTimeNew.compareTo(startTime) >= 0 && startTimeNew.compareTo(startTime) <= 0) {
            return true;
        }
        if (endTime.compareTo(startTimeNew) >= 0 && startTime.compareTo(startTimeNew) <= 0) {
            return true;
        }

        boolean oldSpan = false;
        boolean newSpan = false;

        LocalDateTime startLocalDateTime = LocalDateTime.of(LocalDate.now(), startTime);
        LocalDateTime endLocalDateTime;
        if (startTime.compareTo(endTime) > 0) {
            oldSpan = true;
            endLocalDateTime = LocalDateTime.of(LocalDate.now().plusDays(1), endTime);
        } else {
            endLocalDateTime = LocalDateTime.of(LocalDate.now(), endTime);
        }
        LocalDateTime startLocalDateTimeNew = LocalDateTime.of(LocalDate.now(), startTimeNew);
        LocalDateTime endLocalDateTimeNew;
        if (startTimeNew.compareTo(endTimeNew) > 0) {
            newSpan = true;
            endLocalDateTimeNew = LocalDateTime.of(LocalDate.now().plusDays(1), endTimeNew);
        } else {
            endLocalDateTimeNew = LocalDateTime.of(LocalDate.now(), endTimeNew);
        }

        //并非两个都夸天
        if (!(oldSpan && newSpan)) {
            if (oldSpan && startLocalDateTimeNew.getHour() < 12) {
                endLocalDateTimeNew = endLocalDateTimeNew.plusDays(1);
                startLocalDateTimeNew = startLocalDateTimeNew.plusDays(1);
            }
            if (newSpan && startLocalDateTime.getHour() < 12) {
                endLocalDateTime = endLocalDateTime.plusDays(1);
                startLocalDateTime = startLocalDateTime.plusDays(1);
            }
        }

        if (startLocalDateTimeNew.compareTo(startLocalDateTime) >= 0 && startLocalDateTimeNew.compareTo(endLocalDateTime) <= 0) {
            return true;
        }
        if (endLocalDateTimeNew.compareTo(startLocalDateTime) >= 0 && endLocalDateTimeNew.compareTo(endLocalDateTime) <= 0) {
            return true;
        }
        if (startLocalDateTimeNew.compareTo(startLocalDateTime) <= 0 && endLocalDateTimeNew.compareTo(endLocalDateTime) >= 0) {
            return true;
        }

        return false;
    }

    private CourseUserPo fromCourseUserAddReq(CourseUserAddReq courseUserAddReq) {
        CourseUserPo courseUserPo = new CourseUserPo();
        courseUserPo.setUserType(CourseUserTypeEnum.PATIENT.getCode());
        courseUserPo.setName(courseUserAddReq.getName());
        courseUserPo.setPhone(courseUserAddReq.getPhone());
        courseUserPo.setResponsibilities(courseUserAddReq.getResponsibilities());
        courseUserPo.setBedNumber(courseUserAddReq.getBedNumber());
        courseUserPo.setHyperbaricOxygenStartTime(courseUserAddReq.getHyperbaricOxygenStartTime());
        courseUserPo.setHyperbaricOxygenEndTime(courseUserAddReq.getHyperbaricOxygenEndTime());
        courseUserPo.setLocation(courseUserAddReq.getLocation());
        courseUserPo.setSort(courseUserAddReq.getSort());
        return courseUserPo;
    }
}
