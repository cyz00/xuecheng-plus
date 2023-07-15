package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CoursePreviewDto;

public interface CoursePublishService {
    /**
     * @description 获取课程预览信息
     * @param courseId 课程id
     * @return com.xuecheng.content.model.dto.CoursePreviewDto
     * @author zcy
     * @date 2023/7/12
     */
    public CoursePreviewDto getCoursePreviewInfo(Long courseId);
    /**
     * @description 提交审核
     * @param courseId  课程id
     * @return void
     * @author zcy
     * @date 2023/7/13
     */
    public void commitAudit(Long companyId,Long courseId);
    /**
     * @description 课程发布接口
     * @param companyId 机构id
     * @param courseId 课程id
     * @return void
     * @author zcy
     * @date 2023/7/14
     */
    public void publish(Long companyId,Long courseId);



}
