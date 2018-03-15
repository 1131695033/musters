/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.products.web;

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
import com.jeeplus.modules.products.entity.Products;
import com.jeeplus.modules.products.service.ProductsService;

/**
 * 服务产品Controller
 * @author zyh
 * @version 2018-03-13
 */
@Controller
@RequestMapping(value = "${adminPath}/products/products")
public class ProductsController extends BaseController {

	@Autowired
	private ProductsService productsService;
	
	@ModelAttribute
	public Products get(@RequestParam(required=false) String id) {
		Products entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = productsService.get(id);
		}
		if (entity == null){
			entity = new Products();
		}
		return entity;
	}
	
	/**
	 * 服务产品列表页面
	 */
	@RequiresPermissions("products:products:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/products/productsList";
	}
	
		/**
	 * 服务产品列表数据
	 */
	@ResponseBody
	@RequiresPermissions("products:products:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Products products, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Products> page = productsService.findPage(new Page<Products>(request, response), products); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑服务产品表单页面
	 */
	@RequiresPermissions(value={"products:products:view","products:products:add","products:products:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Products products, Model model) {
		model.addAttribute("products", products);
		return "modules/products/productsForm";
	}

	/**
	 * 保存服务产品
	 */
	@ResponseBody
	@RequiresPermissions(value={"products:products:add","products:products:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Products products, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, products)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		productsService.save(products);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存服务产品成功");
		return j;
	}
	
	/**
	 * 删除服务产品
	 */
	@ResponseBody
	@RequiresPermissions("products:products:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Products products, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		productsService.delete(products);
		j.setMsg("删除服务产品成功");
		return j;
	}
	
	/**
	 * 批量删除服务产品
	 */
	@ResponseBody
	@RequiresPermissions("products:products:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			productsService.delete(productsService.get(id));
		}
		j.setMsg("删除服务产品成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("products:products:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Products products, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "服务产品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Products> page = productsService.findPage(new Page<Products>(request, response, -1), products);
    		new ExportExcel("服务产品", Products.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出服务产品记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("products:products:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Products> list = ei.getDataList(Products.class);
			for (Products products : list){
				try{
					productsService.save(products);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条服务产品记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条服务产品记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入服务产品失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/products/products/?repage";
    }
	
	/**
	 * 下载导入服务产品数据模板
	 */
	@RequiresPermissions("products:products:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "服务产品数据导入模板.xlsx";
    		List<Products> list = Lists.newArrayList(); 
    		new ExportExcel("服务产品数据", Products.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/products/products/?repage";
    }

}