package com.lyk.coursearrange.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyk.coursearrange.common.Result;
import com.lyk.coursearrange.dao.CourseUserMapper;
import com.lyk.coursearrange.entity.dto.CourseExcelDto;
import com.lyk.coursearrange.entity.po.CourseUserPo;
import com.lyk.coursearrange.entity.request.CourseUserAddReq;
import com.lyk.coursearrange.entity.response.CourseUserView;
import com.lyk.coursearrange.enums.CourseUserTypeEnum;
import com.lyk.coursearrange.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
public class CourseController {
    @Resource
    private CourseService courseService;
    @Resource
    private CourseUserMapper courseUserMapper;

    /**
     * 查询所有病人
     */
    @GetMapping("/course/queryAllPatients/{pageSize}")
    public Result<Page<CourseUserView>> queryAllPatients(@PathVariable("pageSize") Integer pageSize,
                                                         @RequestParam(defaultValue = "10") Integer limit) {
        Page<CourseUserPo> pages = new Page<>(pageSize, limit);
        QueryWrapper<CourseUserPo> wrapper = new QueryWrapper<CourseUserPo>()
                .eq("user_type", CourseUserTypeEnum.PATIENT.getCode())
                .orderByDesc("date_update");
        IPage<CourseUserPo> courseUserPoIPage = courseUserMapper.selectPage(pages, wrapper);
        return Result.ofSuccess(convertToPage(courseUserPoIPage));
    }

    /**
     * 查询所有医生
     */
    @GetMapping("/course/queryAllDoctors/{pageSize}")
    public Result<Page<CourseUserView>> queryAllDoctors(@PathVariable("pageSize") Integer pageSize,
                                                         @RequestParam(defaultValue = "10") Integer limit) {
        Page<CourseUserPo> pages = new Page<>(pageSize, limit);
        QueryWrapper<CourseUserPo> wrapper = new QueryWrapper<CourseUserPo>()
                .eq("user_type", CourseUserTypeEnum.DOCTOR.getCode())
                .orderByDesc("date_update");
        IPage<CourseUserPo> courseUserPoIPage = courseUserMapper.selectPage(pages, wrapper);
        return Result.ofSuccess(convertToPage(courseUserPoIPage));
    }

    /**
     * 查询所有理疗机器
     */
    @GetMapping("/course/queryAllMachines/{pageSize}")
    public Result<Page<CourseUserView>> queryAllMachines(@PathVariable("pageSize") Integer pageSize,
                                                        @RequestParam(defaultValue = "10") Integer limit) {
        Page<CourseUserPo> pages = new Page<>(pageSize, limit);
        QueryWrapper<CourseUserPo> wrapper = new QueryWrapper<CourseUserPo>()
                .eq("user_type", CourseUserTypeEnum.MACHINE.getCode())
                .orderByDesc("date_update");
        IPage<CourseUserPo> courseUserPoIPage = courseUserMapper.selectPage(pages, wrapper);
        return Result.ofSuccess(convertToPage(courseUserPoIPage));
    }

    @GetMapping("/course/queryAllCourse")
    public Result<List<CourseExcelDto>> queryAllCourse() {
        return Result.ofSuccess(courseService.queryAllCourse());
    }

    /**
     * 新增一个病人
     */
    @PostMapping("/course/addPatient")
    public Result<Void> addPatient(@RequestBody CourseUserAddReq req) {
        courseService.addPatient(req);
        return Result.ofSuccess();
    }


    /**
     * 删除一个病人
     */
    @PostMapping("/deletePatient")
    public void deletePatient(Integer userId) {
        courseService.deletePatient(userId);
    }


    /**
     * 增加一个病人
     *
     * @param userId
     */

    @GetMapping("/processAddPatient")
    public void processAddPatient(Integer userId) {
        courseService.processAddPatient(userId);
    }

    /**
     * 导出课表
     */
    @GetMapping("/exportInfoToExcel")
    public void scheduleLessons() {
        courseService.exportInfoToExcel();
    }


    /**
     * 从excel课表信息初始化数据
     */
    @GetMapping("/importDataFromExcel")
    public void importDataFromExcel() {
        courseService.importDataFromExcel();
    }


    private Page<CourseUserView> convertToPage(IPage<CourseUserPo> courseUserPoIPage) {
        IPage<CourseUserView> viewIPage = courseUserPoIPage.convert(CourseUserView::convert);
        Page<CourseUserView> page = new Page<>(viewIPage.getCurrent(), viewIPage.getSize(), viewIPage.getTotal());
        page.setRecords(viewIPage.getRecords());
        return page;
    }
}
