package com.ririiy.redisanno.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("tb_news")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {

	@TableId(type = IdType.AUTO)
	private Long newId;
	
	private String title;
	
	private String content;
	
	private Date createTime;
}
