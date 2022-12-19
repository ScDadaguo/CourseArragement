package com.lyk.coursearrange.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author dadaguo
 */
@AllArgsConstructor
@NoArgsConstructor
public class CourseExcelDto {
    @ExcelProperty("医生/理疗")
    private String project;
    @ExcelProperty("8:00-8:30")
    private String moment1;
    @ExcelProperty("8:35-9:05")
    private String moment2;
    @ExcelProperty("9:10-9:40")
    private String moment3;
    @ExcelProperty("9:45-10:15")
    private String moment4;
    @ExcelProperty("10:20-10:50")
    private String moment5;
    @ExcelProperty("10:55-11:25")
    private String moment6;
    @ExcelProperty("11.25-11.55")
    private String moment7;
    @ExcelProperty("1:30-2:00")
    private String moment8;
    @ExcelProperty("2:05-2:35")
    private String moment9;
    @ExcelProperty("2:40-3:10")
    private String moment10;
    @ExcelProperty("3:15-3:45")
    private String moment11;
    @ExcelProperty("3:50-4:20")
    private String moment12;
    @ExcelProperty("4:25-4:55")
    private String moment13;
    @ExcelProperty("4:55-5:15")
    private String moment14;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getMoment1() {
        return moment1;
    }

    public void setMoment1(String moment1) {
        this.moment1 = moment1;
    }

    public String getMoment2() {
        return moment2;
    }

    public void setMoment2(String moment2) {
        this.moment2 = moment2;
    }

    public String getMoment3() {
        return moment3;
    }

    public void setMoment3(String moment3) {
        this.moment3 = moment3;
    }

    public String getMoment4() {
        return moment4;
    }

    public void setMoment4(String moment4) {
        this.moment4 = moment4;
    }

    public String getMoment5() {
        return moment5;
    }

    public void setMoment5(String moment5) {
        this.moment5 = moment5;
    }

    public String getMoment6() {
        return moment6;
    }

    public void setMoment6(String moment6) {
        this.moment6 = moment6;
    }

    public String getMoment7() {
        return moment7;
    }

    public void setMoment7(String moment7) {
        this.moment7 = moment7;
    }

    public String getMoment8() {
        return moment8;
    }

    public void setMoment8(String moment8) {
        this.moment8 = moment8;
    }

    public String getMoment9() {
        return moment9;
    }

    public void setMoment9(String moment9) {
        this.moment9 = moment9;
    }

    public String getMoment10() {
        return moment10;
    }

    public void setMoment10(String moment10) {
        this.moment10 = moment10;
    }

    public String getMoment11() {
        return moment11;
    }

    public void setMoment11(String moment11) {
        this.moment11 = moment11;
    }

    public String getMoment12() {
        return moment12;
    }

    public void setMoment12(String moment12) {
        this.moment12 = moment12;
    }

    public String getMoment13() {
        return moment13;
    }

    public void setMoment13(String moment13) {
        this.moment13 = moment13;
    }

    public String getMoment14() {
        return moment14;
    }

    public void setMoment14(String moment14) {
        this.moment14 = moment14;
    }
}
