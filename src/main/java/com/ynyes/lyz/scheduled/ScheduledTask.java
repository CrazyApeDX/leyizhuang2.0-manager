package com.ynyes.lyz.scheduled;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ynyes.lyz.entity.TdOrder;
import com.ynyes.lyz.entity.user.TdUser;
import com.ynyes.lyz.service.TdOrderService;
import com.ynyes.lyz.service.TdUserService;


@Component
public class ScheduledTask {

		private final Logger LOG = LoggerFactory.getLogger(ScheduledTask.class);
		
		@Autowired
		private TdUserService tdUserService;
		
		@Autowired
		private TdOrderService tdOrderService;
		
		// 修改导购 每天晚上1点执行
	 	@Scheduled(cron="0 0 1 * * ?")
	    public void executeTask() {
	        LOG.info("修改导购定时任务:"+new Date());
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        Date now = new Date();
	        Date begainDate = new Date();
	        try {
				begainDate = sdf.parse("2017-10-01 00:00:00");
			} catch (ParseException e) {
				LOG.info("时间格式化出错");
			}
	        
	        // 10 月 01 号 后执行
	        if(now.after(begainDate)){
	        	Calendar calendar = Calendar.getInstance();
	        	calendar.setTime(now);
			    calendar.add(Calendar.DATE,-60); 
		        // 60天前日期 
			    Date date = calendar.getTime();
			    
			    List<TdUser> list = tdUserService.queryAllUser(date);
			    
		        int count = 0;
		        
		        for (TdUser user : list) {
		        	// 排除默认门店下的用户 以及分销用户 
		        	if(user.getDiyName() != null && !user.getDiyCode().equals("MR001") && !user.getDiyCode().equals("1000")
		        			&& user.getSellerId() != null && !user.getSellerId().equals(0L)
		        			&& !user.getDiyCode().contains("FX#")){
		        		if(user.getChangeSellerTime() == null){
			        		// 注册日期大于60以前
		        			if(user.getRegisterTime() != null && user.getRegisterTime().before(date)){
		        				//检查销量
		    		        	List<TdOrder> tdOrderList = tdOrderService.querySalesByusernameAndsellerId(user.getUsername(), user.getSellerId(), date);
		    		        	
		    		        	if(tdOrderList == null || tdOrderList.size() == 0 ){
		    		        			count++;
		    			        		// 无销量 清空导购
		    			        		LOG.info(">>>>>>>>>>>>>会员:"+user.getRealName()+"  在导购："+ user.getSellerName() +"下无销量！");
		    			        		
		    			    	        user = tdUserService.clearSeller(user);
		    			    	        tdUserService.save(user);
		    		        	}
			        		}
			        	}else{
			        		// 修改导购日期大于60天
			        		if(user.getChangeSellerTime().before(date)){
			        			//检查销量
		    		        	List<TdOrder> tdOrderList = tdOrderService.querySalesByusernameAndsellerIdAndOrderTime(
		    		        												user.getUsername(), user.getSellerId(),user.getChangeSellerTime());
		    		        	
		    		        	if(tdOrderList == null || tdOrderList.size() == 0 ){
		    		        			count++;
		    			        		// 无销量 清空导购
		    			        		LOG.info(">>>>>>>>>>>>>会员:"+user.getRealName()+"  在导购："+ user.getSellerName() +"下无销量!!!");
		    			        		
		    			        		user = tdUserService.clearSeller(user);
		    			    	        tdUserService.save(user);
		    		        	}
			        		}
			        	}
			        	
		        	}
		        	
		        	
		        	// 清理上限 2000个
		        	if(count >= 2000){
		        		break;
		        	}
				}
		        
		        LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>>>修改导购:"+count+"个！");
	        }
	        																																																					Calendar calendar = Calendar.getInstance();
		    												

	    }
}
