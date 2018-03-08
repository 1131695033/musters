/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.classify.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.classify.entity.Classify;
import com.jeeplus.modules.classify.mapper.ClassifyMapper;

/**
 * 服务分类Service
 * @author zyh
 * @version 2018-03-08
 */
@Service
@Transactional(readOnly = true)
public class ClassifyService extends TreeService<ClassifyMapper, Classify> {

	public Classify get(String id) {
		return super.get(id);
	}
	
	public List<Classify> findList(Classify classify) {
		if (StringUtils.isNotBlank(classify.getParentIds())){
			classify.setParentIds(","+classify.getParentIds()+",");
		}
		return super.findList(classify);
	}
	
	@Transactional(readOnly = false)
	public void save(Classify classify) {
		super.save(classify);
	}
	
	@Transactional(readOnly = false)
	public void delete(Classify classify) {
		super.delete(classify);
	}
	
}