package com.ynyes.lyz.controller.front;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ynyes.lyz.service.TdReturnNoteService;

@RestController
@RequestMapping(value = "/return", produces = "application/json;charset=utf-8")
public class TdReturnNoteController {

	@Autowired
	private TdReturnNoteService tdReturnNoteService;

	@RequestMapping(value = "/cancel/{id}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> returnCancel(@PathVariable("id") Long id) {
		Map<String, Object> res = new HashMap<>();
		res.put("status", -1);
		try {
			if (tdReturnNoteService.doCancel(id)) {
				res.put("status", 0);
			} else {
				res.put("message", "操作失败， 此单不允许取消退货");
			}
		} catch (Exception e) {
			res.put("message", "无法查询到相关订单的信息，请联系管理员");
			e.printStackTrace();
		}
		return res;
	}
}
