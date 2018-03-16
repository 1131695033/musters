/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.worker.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.config.Global;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.worker.entity.Worker;
import com.jeeplus.modules.worker.service.WorkerService;

/**
 * 工人信息Controller
 * @author zyh
 * @version 2018-03-16
 */
@Controller
@RequestMapping(value = "${adminPath}/worker/worker")
public class WorkerController extends BaseController {

	@Autowired
	private WorkerService workerService;
	
	@ModelAttribute
	public Worker get(@RequestParam(required=false) String id) {
		Worker entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = workerService.get(id);
		}
		if (entity == null){
			entity = new Worker();
		}
		return entity;
	}
	
	/**
	 * 工人信息列表页面
	 */
	@RequiresPermissions("worker:worker:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/worker/workerList";
	}
	
		/**
	 * 工人信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("worker:worker:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Worker worker, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Worker> page = workerService.findPage(new Page<Worker>(request, response), worker); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑工人信息表单页面
	 */
	@RequiresPermissions(value={"worker:worker:view","worker:worker:add","worker:worker:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Worker worker, Model model) {
		model.addAttribute("worker", worker);
		return "modules/worker/workerForm";
	}

	/**
	 * 保存工人信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"worker:worker:add","worker:worker:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Worker worker, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, worker)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		workerService.save(worker);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存工人信息成功");
		return j;
	}
	
	/**
	 * 删除工人信息
	 */
	@ResponseBody
	@RequiresPermissions("worker:worker:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Worker worker, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		workerService.delete(worker);
		j.setMsg("删除工人信息成功");
		return j;
	}
	
	/**
	 * 批量删除工人信息
	 */
	@ResponseBody
	@RequiresPermissions("worker:worker:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			workerService.delete(workerService.get(id));
		}
		j.setMsg("删除工人信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("worker:worker:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Worker worker, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工人信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Worker> page = workerService.findPage(new Page<Worker>(request, response, -1), worker);
    		new ExportExcel("工人信息", Worker.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工人信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("worker:worker:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Worker> list = ei.getDataList(Worker.class);
			for (Worker worker : list){
				try{
					workerService.save(worker);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条工人信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条工人信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入工人信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/worker/worker/?repage";
    }
	
	/**
	 * 下载导入工人信息数据模板
	 */
	@RequiresPermissions("worker:worker:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "工人信息数据导入模板.xlsx";
    		List<Worker> list = Lists.newArrayList(); 
    		new ExportExcel("工人信息数据", Worker.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/worker/worker/?repage";
    }

}