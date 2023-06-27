package com.xuecheng.content.service;


import com.xuecheng.content.model.dto.*;
import java.util.List;

/**
 * @description 课程计划管理相关接口
 * @author zcy
 * @date 2023/6/26
 * @version 1.0
 */
public interface TeachplanService {

    /**
     * @description 查询课程计划树型结构
     * @param courseId  课程id
     * @return List<TeachplanDto>
     * @author zcy
     * @date 2023/6/26
     */
    public List<TeachplanDto> findTeachplanTree(Long courseId);

    /**
     * @description 保存课程信息
     * @param saveTeachplanDto
     * @author zcy
     * @date 20236/27
     */
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto);

    /**
     * @description 删除课程计划
     * @param
     * @author zcy
     * @date 20236/27
     */

    public void deleteTeachplan(Long teachplanId);

    /**
     * @description 课程计划排序
     * @param
     * @author zcy
     * @date 20236/27
     */

    public void orderTeachplan(String movetype,Long teachplanId);

}

