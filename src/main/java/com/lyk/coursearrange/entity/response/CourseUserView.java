package com.lyk.coursearrange.entity.response;


import com.lyk.coursearrange.entity.po.BasePo;
import com.lyk.coursearrange.entity.po.CourseUserPo;
import com.lyk.coursearrange.enums.ProjectEnum;
import com.lyk.coursearrange.util.SearchUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import com.lyk.coursearrange.enums.LocationEnum;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 人员管理
 *
 * @TableName course_user
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CourseUserView extends BasePo {
    /**
     * 0-医生 1-理疗机器 2-病人
     */
    private Integer userType;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 负责内容/需要做的项目 （pt，ot，st等等）
     * {@link ProjectEnum}
     *
     */
    private String responsibilities;
    /**
     * 病床号（如：21043）
     */
    private String bedNumber;
    /**
     * 高压氧开始时间
     */
    private String hyperbaricOxygenStartTime;
    /**
     * 高压氧结束时间
     */
    private String hyperbaricOxygenEndTime;
    /**
     * 上课位置（1-楼下 2-楼上）
     */
    private String location;

    private Integer sort;

    public static CourseUserView convert(CourseUserPo courseUserPo) {
        CourseUserView courseUserView = new CourseUserView();
        courseUserView.setUserType(courseUserPo.getUserType());
        courseUserView.setName(courseUserPo.getName());
        courseUserView.setPhone(courseUserPo.getPhone());
        if (StringUtils.isNotBlank(courseUserPo.getResponsibilities())) {
            courseUserView.setResponsibilities(SearchUtil.strToList(courseUserPo.getResponsibilities())
                    .stream()
                    .map(x -> ProjectEnum.getByCode(x).getDesc())
                    .collect(Collectors.joining(",")));
        }
        courseUserView.setBedNumber(courseUserPo.getBedNumber());
        courseUserView.setHyperbaricOxygenStartTime(courseUserPo.getHyperbaricOxygenStartTime());
        courseUserView.setHyperbaricOxygenEndTime(courseUserPo.getHyperbaricOxygenEndTime());
        courseUserView.setLocation(Optional.ofNullable(courseUserPo.getLocation()).map(LocationEnum::getByCode).map(LocationEnum::getDesc).orElse(""));
        courseUserView.setSort(courseUserPo.getSort());
        courseUserView.setId(courseUserPo.getId());
        courseUserView.setDateCreate(courseUserPo.getDateCreate());
        courseUserView.setDateUpdate(courseUserPo.getDateUpdate());
        courseUserView.setIsDeleted(courseUserPo.getIsDeleted());
        return courseUserView;

    }
}
