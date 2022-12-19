package com.lyk.coursearrange.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum LocationEnum {
    CLOCK(0, "楼上"),
    COOPERATE(1, "楼下");

    private Integer code;

    private String desc;

    public static LocationEnum getByCode(Integer code) {
        LocationEnum typeEnum = Arrays.stream(LocationEnum.values())
                .filter(v -> v.getCode().equals(code))
                .findFirst().get();
        return typeEnum;
    }
}
