package com.xuecheng.content.service;


import com.xuecheng.content.model.dto.*;
import com.xuecheng.content.model.po.TeachplanMedia;
import org.springframework.web.bind.annotation.PathVariable;

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

    /**
     * @description 教学计划绑定媒资
     * @param bindTeachplanMediaDto
     * @return com.xuecheng.content.model.po.TeachplanMedia
     * @author zcy
     * @date 2023/7/12
     */
    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);
    /**
     * @description 教学计划绑定媒资
     * @param teachPlanId
     * @param mediaId
     * @author zcy
     * @date 2023/7/12
     */
    public void removeassociateMedia(Long teachPlanId, String mediaId);


}

