package com.lyk.coursearrange.entity.request;

import lombok.Data;

import java.util.Date;

@Data
public class CourseUserAddReq {
    /**
     * 1-医生/机器 2-患者
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
    private Date hyperbaricOxygenStartTime;
    /**
     * 高压氧结束时间
     */
    private Date hyperbaricOxygenEndTime;
    /**
     * 上课位置（1-楼下 2-楼上）
     */
    private Integer location;

    private Integer sort;
}
