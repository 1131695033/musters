/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.actappr.web;

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
import com.jeeplus.modules.actappr.entity.ActAppr;
import com.jeeplus.modules.actappr.service.ActApprService;

/**
 * 审批信息Controller
 * @author jh
 * @version 2017-11-01
 */
@Controller
@RequestMapping(value = "${adminPath}/actappr/actAppr")
public class ActApprController extends BaseController {

	@Autowired
	private ActApprService actApprService;
	
	@ModelAttribute
	public ActAppr get(@RequestParam(required=false) String id) {
		ActAppr entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = actApprService.get(id);
		}
		if (entity == null){
			entity = new ActAppr();
		}
		return entity;
	}
	
	/**
	 * 审批信息列表页面
	 */
	@RequiresPermissions("actappr:actAppr:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/actappr/actApprList";
	}
	
		/**
	 * 审批信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("actappr:actAppr:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(ActAppr actAppr, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ActAppr> page = actApprService.findPage(new Page<ActAppr>(request, response), actAppr); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑审批信息表单页面
	 */
	@RequiresPermissions(value={"actappr:actAppr:view","actappr:actAppr:add","actappr:actAppr:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ActAppr actAppr, Model model) {
		model.addAttribute("actAppr", actAppr);
		if(StringUtils.isBlank(actAppr.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "modules/actappr/actApprForm";
	}

	/**
	 * 保存审批信息
	 */
	@RequiresPermissions(value={"actappr:actAppr:add","actappr:actAppr:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ActAppr actAppr, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, actAppr)){
			return form(actAppr, model);
		}
		//新增或编辑表单保存
		actApprService.save(actAppr);//保存
		addMessage(redirectAttributes, "保存审批信息成功");
		return "redirect:"+Global.getAdminPath()+"/actappr/actAppr/?repage";
	}
	
	/**
	 * 删除审批信息
	 */
	@ResponseBody
	@RequiresPermissions("actappr:actAppr:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(ActAppr actAppr, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		actApprService.delete(actAppr);
		j.setMsg("删除审批信息成功");
		return j;
	}
	
	/**
	 * 批量删除审批信息
	 */
	@ResponseBody
	@RequiresPermissions("actappr:actAppr:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			actApprService.delete(actApprService.get(id));
		}
		j.setMsg("删除审批信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("actappr:actAppr:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(ActAppr actAppr, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "审批信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ActAppr> page = actApprService.findPage(new Page<ActAppr>(request, response, -1), actAppr);
    		new ExportExcel("审批信息", ActAppr.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出审批信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("actappr:actAppr:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ActAppr> list = ei.getDataList(ActAppr.class);
			for (ActAppr actAppr : list){
				try{
					actApprService.save(actAppr);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条审批信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条审批信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入审批信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/actappr/actAppr/?repage";
    }
	
	/**
	 * 下载导入审批信息数据模板
	 */
	@RequiresPermissions("actappr:actAppr:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "审批信息数据导入模板.xlsx";
    		List<ActAppr> list = Lists.newArrayList(); 
    		new ExportExcel("审批信息数据", ActAppr.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/actappr/actAppr/?repage";
    }

}