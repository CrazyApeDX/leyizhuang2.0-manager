package com.ynyes.lyz.interfaces.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ynyes.lyz.entity.TdReturnNote;
import com.ynyes.lyz.interfaces.entity.TdTbwBackMCancel;
import com.ynyes.lyz.interfaces.repository.TdTbwBackMCancelRepo;
import com.ynyes.lyz.interfaces.utils.InterfaceConfigure;

@Service
@Transactional
public class TdTbwBackMCancelService
{
	@Autowired
    private TdTbwBackMCancelRepo repository;
	
	static private String wmsUrl = InterfaceConfigure.WMS_WS_URL;
	private JaxWsDynamicClientFactory WMSDcf = JaxWsDynamicClientFactory.newInstance();
	private org.apache.cxf.endpoint.Client WMSClient = WMSDcf.createClient(wmsUrl);
	private QName WMSName = new QName("http://tempuri.org/", "GetErpInfo");
    
    /**
     * 删除
     * 
     * @param id 菜单项ID
     */
    public void delete(Long id)
    {
        if (null != id)
        {
            repository.delete(id);
        }
    }
    
    /**
     * 删除
     * 
     * @param e 菜单项
     */
    public void delete(TdTbwBackMCancel e)
    {
        if (null != e)
        {
            repository.delete(e);
        }
    }
    
    public void delete(List<TdTbwBackMCancel> entities)
    {
        if (null != entities)
        {
            repository.delete(entities);
        }
    }
    
    /**
     * 查找
     * 
     * @param id ID
     * @return
     */
    public TdTbwBackMCancel findOne(Long id)
    {
        if (null == id)
        {
            return null;
        }
        
        return repository.findOne(id);
    }
    
    /**
     * 查找
     * 
     * @param ids
     * @return
     */
    public List<TdTbwBackMCancel> findAll(Iterable<Long> ids)
    {
        return (List<TdTbwBackMCancel>) repository.findAll(ids);
    }
    
    public List<TdTbwBackMCancel> findAll()
    {
        return (List<TdTbwBackMCancel>) repository.findAll();
    }
    
    
    /**
     * 保存
     * 
     * @param e
     * @return
     */
    public TdTbwBackMCancel save(TdTbwBackMCancel e)
    {
        if (null == e.getInitDate())
        {
            e.setInitDate(new Date());
        }
//        if (null == e.getSendFlag())
//        {
//			e.setSendFlag("N");
//		}
        e.setModifyDate(new Date());

        return repository.save(e);
    }
    
    public List<TdTbwBackMCancel> save(List<TdTbwBackMCancel> entities)
    {
        return (List<TdTbwBackMCancel>) repository.save(entities);
    }
    
    
    /**
     * @param note
     * @author YanLe
     * 
     * 退货单取消动作通知物流
     */
    public Boolean sendBackCancelToWMS(TdReturnNote note) {
    	
    	Object objects[] = null;
    	
    	if (null == note) {
			return false;
		}
		TdTbwBackMCancel cancel = new TdTbwBackMCancel();
		cancel.setWmsId(cancel.generateWmsId());
		cancel.setInitDate(new Date());
		cancel.setModifyDate(new Date());
		if(null != note.getReturnNumber()){
			cancel.setcPoNo(note.getReturnNumber());
		}else{
			return false;
		}
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 30);
		cancel.setcDueDt(cal.getTime());
		if(null != note.getDiyCode()){
			cancel.setcCustomerNo(note.getDiyCode());
		}else{
			return false;
		}
		
		if(null != note.getOrderTime()){
			cancel.setcRequestDt(note.getOrderTime());
		}else{
			return false;
		}
		
		System.out.println(cancel.toXml());
		try {
			objects = WMSClient.invoke(WMSName, "td_tbw_back_m_cancel", "1", cancel.toXml());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "";
		if (objects != null) {
			for (Object object : objects) {
				result += object;
			}
		}
		HashMap<String, Object> resultMap = checkResult(result);
		Integer sendFlag = (Integer) resultMap.get("code");
		String errMsg = (String) resultMap.get("errMsg");
		cancel.setErrorMsg(errMsg);
		cancel.setSendFlag(sendFlag);
    	this.save(cancel);
    	if(null != sendFlag && sendFlag==0){
    		return Boolean.TRUE;
    	}else{
    		return Boolean.FALSE;
    	}
    }
    
    public HashMap<String,Object> checkResult(String resultStr) {
    	HashMap<String, Object> map = new HashMap<>();
		if (!resultStr.contains("<CODE>") || !resultStr.contains("</CODE>") || !resultStr.contains("<MESSAGE>")
				|| !resultStr.contains("</MESSAGE>")) {
			map.put("code", 1);
			map.put("errMsg","返回XML格式错误错误!");
			return map;
		}
		String regEx = "<CODE>([\\s\\S]*?)</CODE>";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(resultStr);

		if (mat.find()) {
			System.out.println("CODE is :" + mat.group(0));
			String code = mat.group(0).replace("<CODE>", "");
			code = code.replace("</CODE>", "").trim();
			if (Integer.parseInt(code) == 0) {
				map.put("code", 0);
				map.put("errMsg","");
				return map;
			} else {
				map.put("code", 1);
				String errorMsg = "<MESSAGE>([\\s\\S]*?)</MESSAGE>";
				pat = Pattern.compile(errorMsg);
				mat = pat.matcher(resultStr);
				if (mat.find()) {
					System.out.println("ERRORMSG is :" + mat.group(0));
					String msg = mat.group(0).replace("<MESSAGE>", "");
					msg = msg.replace("</MESSAGE>", "").trim();
					map.put("errMsg", msg);
				}
				
			}
		}
		return map;
	}
}
