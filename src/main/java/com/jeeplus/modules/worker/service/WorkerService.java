/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.worker.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.worker.entity.Worker;
import com.jeeplus.modules.worker.mapper.WorkerMapper;

/**
 * 工人信息Service
 * @author zyh
 * @version 2018-03-16
 */
@Service
@Transactional(readOnly = true)
public class WorkerService extends CrudService<WorkerMapper, Worker> {

	public Worker get(String id) {
		return super.get(id);
	}
	
	public List<Worker> findList(Worker worker) {
		return super.findList(worker);
	}
	
	public Page<Worker> findPage(Page<Worker> page, Worker worker) {
		return super.findPage(page, worker);
	}
	
	@Transactional(readOnly = false)
	public void save(Worker worker) {
		super.save(worker);
	}
	
	@Transactional(readOnly = false)
	public void delete(Worker worker) {
		super.delete(worker);
	}
	
}