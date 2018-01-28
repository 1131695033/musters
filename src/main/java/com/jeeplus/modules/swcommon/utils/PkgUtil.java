package com.jeeplus.modules.swcommon.utils;

import java.util.HashMap;
import java.util.Map;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.swcommon.pkg.entity.Pkg;
import com.jeeplus.modules.swcommon.pkg.mapper.PkgMapper;

public class PkgUtil {

	
	private static PkgMapper pkgMapper = SpringContextHolder.getBean(PkgMapper.class);
	
	
	/**
	 * 公共接口
	 * @param typeid
	 * @return
	 */
	public static Pkg saveForNumber(String name, String params){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("inName", name);
		map.put("inParams", params);
		pkgMapper.saveForNumber(map);
		return new Pkg(map.get("outCode").toString(), map.get("outMsg").toString());
	}
	
	/**
	 * 获取申请编号
	 * @return
	 */
	public static String getAppNo(){
		return pkgMapper.getAppNo();
	}

}
