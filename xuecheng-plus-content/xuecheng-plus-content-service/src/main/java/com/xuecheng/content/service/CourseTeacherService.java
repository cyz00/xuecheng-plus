package com.xuecheng.content.service;

import com.xuecheng.content.model.po.CourseTeacher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CourseTeacherService {
    /**
     * @description 查询老师
     * @param
     * @author zcy
     * @date 20236/27
     */

    public List<CourseTeacher> findTeacher(Long courseId);
    /**
     * @description 添加修改教师
     * @param
     * @author zcy
     * @date 20236/27
     */
    public CourseTeacher saveCourseTeacher(CourseTeacher courseTeacher);
    /**
     * @description 删除教师
     * @param
     * @author zcy
     * @date 20236/28
     */
    public void deleteCourseTeacher(Long courseId, Long teacherId);

}
