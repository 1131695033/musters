/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.dmsyspara.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.dmsyspara.entity.DmSysPara;
import com.jeeplus.modules.dmsyspara.mapper.DmSysParaMapper;

/**
 * 系统参数维护Service
 * @author qcz
 * @version 2017-11-06
 */
@Service
@Transactional(readOnly = true)
public class DmSysParaService extends CrudService<DmSysParaMapper, DmSysPara> {

	public DmSysPara get(String id) {
		return super.get(id);
	}
	
	public List<DmSysPara> findList(DmSysPara dmSysPara) {
		return super.findList(dmSysPara);
	}
	
	public Page<DmSysPara> findPage(Page<DmSysPara> page, DmSysPara dmSysPara) {
		return super.findPage(page, dmSysPara);
	}
	
	@Transactional(readOnly = false)
	public void save(DmSysPara dmSysPara) {
		super.save(dmSysPara);
	}
	
	@Transactional(readOnly = false)
	public void delete(DmSysPara dmSysPara) {
		super.delete(dmSysPara);
	}
	
}