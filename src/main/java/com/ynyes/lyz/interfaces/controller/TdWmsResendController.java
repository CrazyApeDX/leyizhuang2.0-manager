package com.ynyes.lyz.interfaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.service.TdCommonService;
import com.ynyes.lyz.service.TdReturnNoteService;

@Controller
@RequestMapping(value = "/wms")
public class TdWmsResendController {
	
	@Autowired
	TdReturnNoteService returnNoteService;
	
	@Autowired
	TdCommonService tdCommonService;
	
	@RequestMapping(value="/returnNote",produces = "application/json;charset=utf8")
	@ResponseBody
	public String resendReturnNote(@RequestParam String returnNumber){
		TdReturnNote returnNote = returnNoteService.findByReturnNumber(returnNumber);
		if(null != returnNote && returnNote.getTurnType() == 2){
			if(!returnNote.getRemarkInfo().equals("用户取消订单，退货") && !returnNote.equals("拒签退货")){
				if(returnNote.getStatusId() != null && returnNote.getStatusId() == 1){
					tdCommonService.sendBackToWMS(returnNote);
					if (returnNote.getStatusId() == 1) {
						returnNote.setStatusId(2L);
					}
					returnNoteService.save(returnNote);
					return returnNote.getReturnNumber()+"重传成功!";
				}
				return "该订单已传输！";
			}
			return "该单不是正常销售退货，请选择正确的重传方法!";//该单是拒签退货或取消订单
		}
		return "未找到该退单或该退单不是物流取货！";
	}

}
