package com.lyk.coursearrange.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CourseTypeEnum {
    PT(0, "PT"),
    OT(1, "OT"),
    ST(2, "ST"),
    LL(3,"理疗"),
    ;

    private Integer code;
    private String desc;

    public static CourseTypeEnum getByCode(Integer code) {
        CourseTypeEnum typeEnum = Arrays.stream(CourseTypeEnum.values())
                .filter(v -> v.getCode().equals(code))
                .findFirst().get();
        return typeEnum;
    }
}
