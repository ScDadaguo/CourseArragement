package com.lyk.coursearrange.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyk.coursearrange.entity.po.CourseUserPo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CourseUserMapper extends BaseMapper<CourseUserPo> {

    @Select("SELECT *  FROM course_user where user_type=2")
    List<CourseUserPo> queryAllPatients();
}
