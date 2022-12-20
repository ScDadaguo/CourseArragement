package com.lyk.coursearrange.service;


import com.lyk.coursearrange.entity.dto.CourseExcelDto;
import com.lyk.coursearrange.entity.request.CourseUserAddReq;

import java.util.List;

public interface CourseService {
    //新增一个病人
    void addPatient(CourseUserAddReq courseUserAddReq);

    //删除一个病人
    void deletePatient(Integer userId);

    /**
     * 处理增加一个病人
     */
    void processAddPatient(Integer userId);

    /**
     * 导出课表到excel
     */
    void exportInfoToExcel();

    List<CourseExcelDto> queryAllCourse();

    /**
     * 从课表excel导入数据到系统
     */
    void importDataFromExcel();


    /**
     * 批量插入课程
     */
    void initData(List<CourseExcelDto> list);
}
