/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.sys.web;

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
import com.jeeplus.modules.sys.entity.SysSequence;
import com.jeeplus.modules.sys.service.SysSequenceService;

/**
 * 序列号管理Controller
 * 
 * @author maldinichen
 * @version 2017-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysseq/sysSequence")
public class SysSequenceController extends BaseController {

	@Autowired
	private SysSequenceService sysSequenceService;

	@ModelAttribute
	public SysSequence get(@RequestParam(required = false) String id) {
		SysSequence entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = sysSequenceService.get(id);
		}
		if (entity == null) {
			entity = new SysSequence();
		}
		return entity;
	}

	/**
	 * 序列号管理列表页面
	 */
	@RequiresPermissions("sys:sysseq:sysSequence:list")
	@RequestMapping(value = { "list", "" })
	public String list() {
		return "modules/sys/sysseq/sysSequenceList";
	}

	/**
	 * 序列号管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys:sysseq:sysSequence:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SysSequence sysSequence, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<SysSequence> page = sysSequenceService.findPage(new Page<SysSequence>(request, response), sysSequence);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑序列号管理表单页面
	 */
	@RequiresPermissions(value = { "sys:sysseq:sysSequence:view", "sys:sysseq:sysSequence:add",
			"sys:sysseq:sysSequence:edit" }, logical = Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysSequence sysSequence, Model model) {
		model.addAttribute("sysSequence", sysSequence);
		return "modules/sys/sysseq/sysSequenceForm";
	}

	/**
	 * 保存序列号管理
	 */
	@ResponseBody
	@RequiresPermissions(value = { "sys:sysseq:sysSequence:add", "sys:sysseq:sysSequence:edit" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(SysSequence sysSequence, Model model, RedirectAttributes redirectAttributes) throws Exception {
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, sysSequence)) {
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		sysSequenceService.save(sysSequence);// 新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存序列号管理成功");
		return j;
	}

	/**
	 * 删除序列号管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:sysseq:sysSequence:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SysSequence sysSequence, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		sysSequenceService.delete(sysSequence);
		j.setMsg("删除序列号管理成功");
		return j;
	}

	/**
	 * 批量删除序列号管理
	 */
	@ResponseBody
	@RequiresPermissions("sys:sysseq:sysSequence:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			sysSequenceService.delete(sysSequenceService.get(id));
		}
		j.setMsg("删除序列号管理成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys:sysseq:sysSequence:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public AjaxJson exportFile(SysSequence sysSequence, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
			String fileName = "序列号管理" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<SysSequence> page = sysSequenceService.findPage(new Page<SysSequence>(request, response, -1),
					sysSequence);
			new ExportExcel("序列号管理", SysSequence.class).setDataList(page.getList()).write(response, fileName).dispose();
			j.setSuccess(true);
			j.setMsg("导出成功！");
			return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出序列号管理记录失败！失败信息：" + e.getMessage());
		}
		return j;
	}

	/**
	 * 导入Excel数据
	 * 
	 */
	@RequiresPermissions("sys:sysseq:sysSequence:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysSequence> list = ei.getDataList(SysSequence.class);
			for (SysSequence sysSequence : list) {
				try {
					sysSequenceService.save(sysSequence);
					successNum++;
				} catch (ConstraintViolationException ex) {
					failureNum++;
				} catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条序列号管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条序列号管理记录" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入序列号管理失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/sys/sysseq/sysSequence/?repage";
	}

	/**
	 * 下载导入序列号管理数据模板
	 */
	@RequiresPermissions("sys:sysseq:sysSequence:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "序列号管理数据导入模板.xlsx";
			List<SysSequence> list = Lists.newArrayList();
			new ExportExcel("序列号管理数据", SysSequence.class, 1).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + Global.getAdminPath() + "/sys/sysseq/sysSequence/?repage";
	}

}