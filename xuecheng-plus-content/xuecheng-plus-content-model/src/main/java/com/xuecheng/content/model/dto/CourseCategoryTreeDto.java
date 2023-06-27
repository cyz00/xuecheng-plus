package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * @author zcy
 * @description 课程分类树型结点dto
 * @date 2023/6/20
 */
@Data //@Data ： 注在类上，提供类的get、set、equals、hashCode、canEqual、toString方法
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {
    //子节点
    List<CourseCategoryTreeDto> childrenTreeNodes;
}
