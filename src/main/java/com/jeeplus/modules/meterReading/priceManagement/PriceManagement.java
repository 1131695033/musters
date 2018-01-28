package com.jeeplus.modules.meterReading.priceManagement;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PriceManagement {
	
	@RequestMapping("/PriceManagement")
	public String priceManagement(){
		return "meterReading/priceManagement/PriceManagement";
	}
}
