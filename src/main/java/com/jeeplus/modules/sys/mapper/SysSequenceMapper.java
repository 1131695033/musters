/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;
import com.jeeplus.modules.sys.entity.SysSequence;

/**
 * 序列号管理MAPPER接口
 * @author maldinichen
 * @version 2017-08-16
 */
@MyBatisMapper
public interface SysSequenceMapper extends BaseMapper<SysSequence> {
	
}