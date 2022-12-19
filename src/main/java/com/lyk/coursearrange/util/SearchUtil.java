package com.lyk.coursearrange.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anzongying
 * @date 5:18 下午 2021/1/19
 */

public class SearchUtil {

    public static List<Integer> strToList(String str) {
        if (StringUtils.isBlank(str)) {
            return new ArrayList<>();
        }
        return Arrays.stream(str.split(",")).map(Integer::valueOf).collect(Collectors.toList());
    }
}
