package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.mapper.TeachplanMediaMapper;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import com.xuecheng.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @description 课程计划管理相关接口实现
 * @author zcy
 * @date 2023/6/26
 * @version 1.0
 */
@Service
public class TeachplanServiceImpl implements TeachplanService {
    @Autowired
    TeachplanMapper teachplanMapper;
    @Autowired
    TeachplanMediaMapper teachplanMediaMapper;
    @Override
    public List<TeachplanDto> findTeachplanTree(Long courseId) {
        List<TeachplanDto> teachplanDtos = teachplanMapper.selectTreeNodes(courseId);
        return teachplanDtos;
    }

    private int getTeachplanCount(Long courseId,Long parentId){
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper=queryWrapper.eq(Teachplan::getCourseId,courseId).eq(Teachplan::getParentid,parentId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        return count;
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto saveTeachplanDto) {
        //通过课程计划id判断是新增还是修改
        Long teachplanId = saveTeachplanDto.getId();
        if(teachplanId == null){
            //新增
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(saveTeachplanDto,teachplan);
            //确定排序字段，找到同级几点的个数，排序字段就是个数加1
            Long parentid = saveTeachplanDto.getParentid();
            Long courseId = saveTeachplanDto.getCourseId();
            int count = getTeachplanCount(courseId,parentid);
            teachplan.setOrderby(count+1);
            teachplanMapper.insert(teachplan);
        }else{
            //修改
            Teachplan teachplan = teachplanMapper.selectById(teachplanId);
            //将参数放置在teachplan
            BeanUtils.copyProperties(saveTeachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);

        }
    }

    @Override
    public void deleteTeachplan(Long teachplanId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid,teachplanId);
        Integer count = teachplanMapper.selectCount(queryWrapper);
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        if(count==0){
            teachplanMapper.deleteById(teachplan);

            LambdaQueryWrapper<TeachplanMedia> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(TeachplanMedia::getTeachplanId,teachplanId);
            TeachplanMedia teachplanMedia = teachplanMediaMapper.selectOne(queryWrapper1);
            if(teachplanMedia !=null){
                teachplanMediaMapper.deleteById(queryWrapper1);
            }
//            teachplanMediaMapper.deleteById(queryWrapper1);

        }else{
            XueChengPlusException.cast("课程计划信息还有子级信息，无法操作");
        }

    }
    private Teachplan getmoveTeachplan(Long courseId,Long parentId,Integer orderby){
        LambdaQueryWrapper<Teachplan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Teachplan::getCourseId,courseId).eq(Teachplan::getParentid,parentId).eq(Teachplan::getOrderby,orderby);
        Teachplan teachplan = teachplanMapper.selectOne(wrapper);
        return teachplan;
    }


    @Override
    public void orderTeachplan(String movetype, Long teachplanId) {
        Teachplan teachplan = teachplanMapper.selectById(teachplanId);
        Integer orderby = teachplan.getOrderby();
        Long courseId = teachplan.getCourseId();
        Long parentId = teachplan.getParentid();
        int count = getTeachplanCount(courseId,parentId);
        if(movetype.equals("movedown")){
            if(orderby==count){
                XueChengPlusException.cast("已经是最后一个");
            }else{
                Teachplan preteachplan =getmoveTeachplan(courseId,parentId,orderby+1);
                preteachplan.setOrderby(orderby);
                teachplan.setOrderby(orderby+1);
                teachplanMapper.updateById(preteachplan);
                teachplanMapper.updateById(teachplan);
            }
        }else{
            if(orderby==1){
                XueChengPlusException.cast("已经是第一个");
            }else{
                Teachplan preteachplan =getmoveTeachplan(courseId,parentId,orderby-1);
                preteachplan.setOrderby(orderby);
                teachplan.setOrderby(orderby-1);
                teachplanMapper.updateById(preteachplan);
                teachplanMapper.updateById(teachplan);
            }

        }

    }
}
