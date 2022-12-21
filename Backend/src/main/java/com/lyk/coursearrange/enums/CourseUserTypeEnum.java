package com.lyk.coursearrange.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CourseUserTypeEnum {
    DOCTOR(0, "医生"),
    MACHINE(1, "理疗机器"),
    PATIENT(2, "病人"),
    ;

    private Integer code;
    private String desc;

    public static CourseUserTypeEnum getByCode(Integer code) {
        CourseUserTypeEnum typeEnum = Arrays.stream(CourseUserTypeEnum.values())
                .filter(v -> v.getCode().equals(code))
                .findFirst().get();
        return typeEnum;
    }
}
