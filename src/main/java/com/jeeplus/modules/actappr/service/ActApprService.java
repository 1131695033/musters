/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.actappr.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.actappr.entity.ActAppr;
import com.jeeplus.modules.actappr.mapper.ActApprMapper;

/**
 * 审批信息Service
 * @author jh
 * @version 2017-11-01
 */
@Service
@Transactional(readOnly = true)
public class ActApprService extends CrudService<ActApprMapper, ActAppr> {

	public ActAppr get(String id) {
		return super.get(id);
	}
	
	public List<ActAppr> findList(ActAppr actAppr) {
		return super.findList(actAppr);
	}
	
	public Page<ActAppr> findPage(Page<ActAppr> page, ActAppr actAppr) {
		return super.findPage(page, actAppr);
	}
	
	@Transactional(readOnly = false)
	public void save(ActAppr actAppr) {
		super.save(actAppr);
	}
	
	@Transactional(readOnly = false)
	public void delete(ActAppr actAppr) {
		super.delete(actAppr);
	}
	
}