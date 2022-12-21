package com.lyk.coursearrange.entity.po;


import com.baomidou.mybatisplus.annotation.TableName;
import com.lyk.coursearrange.enums.ProjectEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * 人员管理
 *
 * @TableName course_user
 */
@Data
@TableName("course_user")
@AllArgsConstructor
@NoArgsConstructor
public class CourseUserPo extends BasePo{
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
    private Integer location;

    private Integer sort;
}
