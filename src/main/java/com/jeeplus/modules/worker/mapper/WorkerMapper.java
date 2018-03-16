/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.worker.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.worker.entity.Worker;

/**
 * 工人信息MAPPER接口
 * @author zyh
 * @version 2018-03-16
 */
@MyBatisMapper
public interface WorkerMapper extends BaseMapper<Worker> {
	
}