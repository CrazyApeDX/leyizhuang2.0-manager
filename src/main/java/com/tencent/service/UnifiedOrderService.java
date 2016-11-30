package com.tencent.service;

import com.tencent.common.Configure;
import com.tencent.protocol.pay_protocol.UnifiedOrderReqData;

/**
 * User: Sharon
 * Date: 2015/09/09
 * Time: 16:03
 */
public class UnifiedOrderService extends BaseService{

    public UnifiedOrderService() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        super(Configure.UNIFIED_ORDER_API);
    }

    /**
     * 请求支付服务
     * @param scanPayReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @return API返回的数据
     * @throws Exception
     */
    public String request(UnifiedOrderReqData unifiedOrderReqData) throws Exception {

        //--------------------------------------------------------------------
        //发送HTTPS的Post请求到API地址
        //--------------------------------------------------------------------
        String responseString = sendPost(unifiedOrderReqData);

        return responseString;
    }
}
