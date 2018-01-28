package com.jeeplus.modules.swcommon.pkg.mapper;

import java.util.Map;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.core.persistence.annotation.MyBatisMapper;


@MyBatisMapper
public interface PkgMapper extends BaseMapper<Map<String,Object>>{
	
	public void saveForNumber(Map<String,Object> map);
	
	public String getAppNo();

}
