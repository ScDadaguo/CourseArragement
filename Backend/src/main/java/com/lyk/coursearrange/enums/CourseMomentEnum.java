package com.lyk.coursearrange.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CourseMomentEnum {
    ONE(1, "8:00-8:30",LocalDateTime.of(2020, 1, 1, 8, 0), LocalDateTime.of(2020, 1, 1, 8, 30)),
    SECOND(2, "8:35-9:05",LocalDateTime.of(2020, 1, 1, 8, 35), LocalDateTime.of(2020, 1, 1, 9, 5)),
    THIRD(3, "9:10-9:40",LocalDateTime.of(2020, 1, 1, 9, 10), LocalDateTime.of(2020, 1, 1, 9, 40)),
    FOURTH(4, "9:45-10:15",LocalDateTime.of(2020, 1, 1, 9, 45), LocalDateTime.of(2020, 1, 1, 10, 15)),
    FIFTH(5, "10:20-10:50",LocalDateTime.of(2020, 1, 1, 10, 20), LocalDateTime.of(2020, 1, 1, 10, 50)),
    SIXTH(6, "10:55-11:25",LocalDateTime.of(2020, 1, 1, 10, 55), LocalDateTime.of(2020, 1, 1, 11, 25)),
    SEVENTH(7, "11.25-11.55",LocalDateTime.of(2020, 1, 1, 11, 25), LocalDateTime.of(2020, 1, 1, 11, 55)),

    EIGHTH(8, "1:30-2:00",LocalDateTime.of(2020, 1, 1, 13, 30), LocalDateTime.of(2020, 1, 1, 14, 0)),
    NINTH(9, "2:05-2:35",LocalDateTime.of(2020, 1, 1, 14, 5), LocalDateTime.of(2020, 1, 1, 14, 35)),
    TENTH(10, "2:40-3:10",LocalDateTime.of(2020, 1, 1, 14, 40), LocalDateTime.of(2020, 1, 1, 15, 10)),
    ELEVENTH(11, "3:15-3:45",LocalDateTime.of(2020, 1, 1, 15, 15), LocalDateTime.of(2020, 1, 1, 15, 45)),
    TWELFTH(12, "3:50-4:20",LocalDateTime.of(2020, 1, 1, 15, 50), LocalDateTime.of(2020, 1, 1, 16, 20)),
    THIRTEENTH(13, "4:25-4:55",LocalDateTime.of(2020, 1, 1, 16, 25), LocalDateTime.of(2020, 1, 1, 16, 55)),
    XIV(14, "4:55-5:15",LocalDateTime.of(2020, 1, 1, 16, 55), LocalDateTime.of(2020, 1, 1, 17, 15)),

    ;

    private Integer code;
    private String desc;

    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public static CourseMomentEnum getByCode(Integer code) {
        CourseMomentEnum typeEnum = Arrays.stream(CourseMomentEnum.values())
                .filter(v -> v.getCode().equals(code))
                .findFirst().get();
        return typeEnum;
    }
}
