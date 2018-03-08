/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.classify.mapper;

import com.jeeplus.core.persistence.TreeMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.classify.entity.Classify;

/**
 * 服务分类MAPPER接口
 * @author zyh
 * @version 2018-03-08
 */
@MyBatisMapper
public interface ClassifyMapper extends TreeMapper<Classify> {
	
}