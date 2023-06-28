package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {
    @Autowired
    CourseTeacherMapper courseTeacherMapper;
    @Override
    public List<CourseTeacher> findTeacher(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(CourseTeacher::getCourseId,courseId);
        List<CourseTeacher> result = courseTeacherMapper.selectList(queryWrapper);
        return result;
    }

    @Override
    public CourseTeacher saveCourseTeacher(CourseTeacher courseTeacher) {
        Long courseTeacherId = courseTeacher.getId();
        if(courseTeacherId == null){
            courseTeacherMapper.insert(courseTeacher);
        }else{
//            CourseTeacher courseTeacher1 = courseTeacherMapper.selectById(courseTeacherId);
//            BeanUtils.copyProperties(courseTeacher,courseTeacher1);
            courseTeacherMapper.updateById(courseTeacher);
        }
        return courseTeacher;
    }
    /**
     * @description 添加修改教师
     * @author zcy
     * @date 20236/28
     */

    @Override
    public void deleteCourseTeacher(Long courseId, Long teacherId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(CourseTeacher::getCourseId,courseId).eq(CourseTeacher::getId,teacherId);
        int flag = courseTeacherMapper.delete(queryWrapper);
        if(flag==0){
            XueChengPlusException.cast("删除失败");
        }
    }


}
