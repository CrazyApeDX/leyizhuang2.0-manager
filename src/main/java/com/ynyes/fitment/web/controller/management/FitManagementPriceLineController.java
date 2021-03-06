package com.ynyes.fitment.web.controller.management;

import com.ynyes.fitment.core.constant.Global;
import com.ynyes.fitment.foundation.entity.FitPriceLine;
import com.ynyes.fitment.foundation.service.FitPriceLineService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value = "/Verwalter/fitment/price/line")
public class FitManagementPriceLineController {

	@Autowired
	private FitPriceLineService fitPriceLineService;

    @RequestMapping(value = "/list/{headerId}", produces = "text/html;charset=utf-8")
    public String priceLineList(@PathVariable Long headerId,
                                Integer page,
                                Integer size,
                                ModelMap map,
                                String __EVENTTARGET,
                                String __EVENTARGUMENT,
                                String __VIEWSTATE,
                                String keywords) {

        if (null != __EVENTTARGET) {
            switch (__EVENTTARGET) {
                case "btnPage":
                    if (null != __EVENTARGUMENT) {
                        page = Integer.parseInt(__EVENTARGUMENT);
                    }
                    break;
                case "btnSearch":
                    //设置当前页为第一页
                    page = 0;
                    break;
            }
        }

        Page<FitPriceLine> linePage = null;
        page = null == page ? Global.DEFAULT_PAGE : page;
        size = null == size ? Global.DEFAULT_SIZE : size;

        map.addAttribute("keywords", keywords);
        map.addAttribute("page", page);
        map.addAttribute("size", size);
        map.addAttribute("__EVENTTARGET", __EVENTTARGET);
        map.addAttribute("__EVENTARGUMENT", __EVENTARGUMENT);
        map.addAttribute("__VIEWSTATE", __VIEWSTATE);
        map.addAttribute("headerId", headerId);

        if (StringUtils.isNotBlank(keywords)) {
            map.addAttribute("linePage", fitPriceLineService.findByHeaderIdAndGoodsSkuContaining(headerId,keywords, page, size));
        } else {
            try {
                linePage = this.fitPriceLineService.findByHeaderId(headerId, page, size);
                map.addAttribute("linePage", linePage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "/fitment/management/price_line_list";
    }
}
