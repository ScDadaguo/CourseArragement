package com.lyk.coursearrange.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author dadaguo
 */

@AllArgsConstructor
@Getter
public enum ProjectEnum {
    JING_LU_CI(0, "经颅磁",true),
    QIANG_CI(1, "强磁",true),
    NAO_XUN_HUAN(2, "脑循环",true),
    SHENG_WU_FAN_KUI(3, "生物反馈",true),
    NMES(4, "NMES",true),
    GE_JI(5, "膈肌",true),
    UPRIGHT_BED(6, "直立床",true),
    STEP_ON_THE_BIKE(7, "踩车",true),
    PILOT(8, "导频",true),
    WAX_THERAPY(9, "蜡疗",true),
    UPRIGHT_BED_FLOOR(10, "直立床楼",true),
    PT(11, "PT",false),
    OT(12, "OT",false),
    ST(13, "ST",false),
    SI(14, "感统",false),
    ;

    private Integer code;

    private String desc;

    private boolean isLL;

    public static ProjectEnum getByCode(Integer code) {
        ProjectEnum typeEnum = Arrays.stream(ProjectEnum.values())
                .filter(v -> v.getCode().equals(code))
                .findFirst().get();
        return typeEnum;
    }
}
