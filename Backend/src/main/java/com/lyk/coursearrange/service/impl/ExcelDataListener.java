package com.lyk.coursearrange.service.impl;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.lyk.coursearrange.entity.dto.CourseExcelDto;
import com.lyk.coursearrange.service.CourseService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guohao
 * @date 2022/12/8 16:31
 */
@Data
@Slf4j
public class ExcelDataListener implements ReadListener<CourseExcelDto> {


    private static final int BATCH_COUNT = 5;

    List<CourseExcelDto> list = new ArrayList<>();

    private final CourseService courseService;

    public ExcelDataListener(CourseService courseService) {
        this.courseService = courseService;
    }

    @Override
    public void invoke(CourseExcelDto data, AnalysisContext context) {
        list.add(data);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    private void saveData() {
        courseService.initData(list);
    }
}

