package com.xuecheng.base.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author zcy
 * @description 分页查询结果模型类
 * @date 2023/6/12
 */
//<T>范型,序列化是将java对象转化为字节对象，需要传输的时候就需要进行序列化
@Data
@ToString
public class PageResult<T> implements Serializable {
    private List<T> items;

    //总记录数
    private long counts;

    //当前页码
    private long page;

    //每页记录数
    private long pageSize;

    public PageResult(List<T> items, long counts, long page, long pageSize) {
        this.items = items;
        this.counts = counts;
        this.page = page;
        this.pageSize = pageSize;
    }
}
