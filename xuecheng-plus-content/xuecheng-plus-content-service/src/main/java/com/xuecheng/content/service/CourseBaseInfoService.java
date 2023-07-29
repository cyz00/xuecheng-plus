package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

/**
 * @description 课程基本信息管理业务接口
 * @author zcy
 * @date 2023/6/16
 * @version 1.0
*/
public interface CourseBaseInfoService  {

/**
 * @description 课程查询接口
 * @param pageParams 分页参数
 * @param queryCourseParamsDto 条件条件
 * @return com.xuecheng.base.model.PageResult<com.xuecheng.content.model.po.CourseBase>
 * @author zcy
 * @date 2023
 */
    //课程分页查询
    PageResult<CourseBase> queryCourseBaseList(Long companyId,PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);
    /**
     * @description 添加课程基本信息
     * @param companyId  教学机构id
     * @param addCourseDto  课程基本信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author zcy
     * @date 2023/6/21
     */

    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);
    /**
     * @description 根据课程id查询课程信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author zcy
     * @date 2023/6/25
     */
    CourseBaseInfoDto getCourseBaseInfo(Long courseId);
    /**
     * @description 修改课程信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author zcy
     * @date 2023/6/25
     */
    CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto);

    /**
     * @description 删除课程
     * @param
     * @author zcy
     * @date 20236/28
     */
    public void deleteCourse(Long companyId,Long courseId);


}
