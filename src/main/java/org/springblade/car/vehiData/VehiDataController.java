package org.springblade.car.vehiData;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springblade.car.entity.Ad;
import org.springblade.car.entity.Brand;
import org.springblade.car.vehiData.entity.Vbrand;
import org.springblade.car.vehiData.service.IVbrandService;
import org.springblade.core.boot.ctrl.BladeController;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springblade.util.FindVinUtil;
import org.springblade.util.HttpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("second-hand-car/ba")
public class VehiDataController extends BladeController {
    private IVbrandService vbrandService;



//        public static void main (String[]args){
//            String host = "https://jisucxdq.market.alicloudapi.com";
//            String path = "/car/brand";
//            String method = "GET";
//            String appcode = "";
//            Map<String, String> headers = new HashMap<String, String>();
//            headers.put("Authorization", "APPCODE " + appcode);
//            headers.put("Content-Type", "application/json; charset=UTF-8");
//            Map<String, String> querys = new HashMap<String, String>();
//
//
//            try {
//                /**
//                 * 重要提示如下:
//                 * HttpUtils请从
//                 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
//                 * 下载
//                 *
//                 * 相应的依赖请参照
//                 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
//                 */
//                // HttpResponse response =HttpUtils.doGet(host, path, method, headers, querys);
//                // System.out.println(response.toString());
//                //获取response的body
//
//                // System.out.println(EntityUtils.toString(response.getEntity(),"utf-8"));
//                //String str=EntityUtils.toString(response.getEntity());
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

}
