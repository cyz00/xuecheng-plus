package com.xuecheng.content.api;

import com.xuecheng.content.mapper.CourseTeacherMapper;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseTeacher;
import com.xuecheng.content.service.CourseTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zcy
 * @description 师资管理接口
 * @date 2023/6/26
 */
@Api(value = "师资管理接口",tags = "师资管理接口")
@RestController
public class CourseTeacherController {
    @Autowired
    CourseTeacherService courseTeacherService;
    @ApiOperation("查询老师")
    @GetMapping("/courseTeacher/list/{courseId}")
    public List<CourseTeacher> getCourseTeacher(@PathVariable Long courseId){
        List<CourseTeacher> courseTeachersList = courseTeacherService.findTeacher(courseId);
        return courseTeachersList;
    }

    @ApiOperation("添加/修改教师")
    @PostMapping("/courseTeacher")
    public CourseTeacher addCourseTeacher(@RequestBody CourseTeacher courseTeacher){
        CourseTeacher courseteacher = courseTeacherService.saveCourseTeacher(courseTeacher);
        return courseteacher;
    }

    @ApiOperation("删除教师")
    @DeleteMapping("/courseTeacher/course/{courseId}/{teacherId}")
    public void deleteCourseTeacher(@PathVariable Long courseId,@PathVariable Long teacherId){
        courseTeacherService.deleteCourseTeacher(courseId,teacherId);
    }

}
