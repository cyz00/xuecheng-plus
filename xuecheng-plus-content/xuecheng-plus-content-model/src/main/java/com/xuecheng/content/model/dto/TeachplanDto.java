package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @description 课程计划模型类
 * @author zcy
 * @date 2023/6/26
 * @version 1.0
 */
@Data
@ToString
public class TeachplanDto extends Teachplan {
    //课程关联的媒体
    private TeachplanMedia teachplanMedia;

    //小账节
    private List<TeachplanDto> teachPlanTreeNodes;
}
