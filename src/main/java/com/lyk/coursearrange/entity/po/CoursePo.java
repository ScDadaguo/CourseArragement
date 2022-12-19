package com.lyk.coursearrange.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("course")
@AllArgsConstructor
@NoArgsConstructor
public class CoursePo extends BasePo{
    /**
     * 病人id
     */
    private Integer patientUserId;
    /**
     * 病人姓名
     */
    private String patientUserName;
    /**
     * 医生/机器 id
     */
    private Integer doctorUserId;

    /**
     * 医生/机器姓名
     */
    private String doctorUserName;
    /**
     * 课程时间
     * {@link CourseMomentEnum}
     */
    private Integer momentType;
    /**
     * 课程类别
     * {@link ProjectEnum}
     */
    private Integer courseType;
    /**
     * 上课位置（1-楼下 2-楼上）
     */
    private Integer location;

    private Integer sort;
}
