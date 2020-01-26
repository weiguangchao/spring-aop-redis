package com.ririiy.redisanno.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ririiy.redisanno.entity.News;

@Mapper
public interface NewsMapper extends BaseMapper<News> {

}
