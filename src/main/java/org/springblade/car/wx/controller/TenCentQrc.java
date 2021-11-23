package org.springblade.car.wx.controller;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ocr.v20181119.OcrClient;
import com.tencentcloudapi.ocr.v20181119.models.*;

public class TenCentQrc
{
  /*  public static void main(String [] args) {
        try{

            //腾讯云：APPID:1307724314
            String SecretId="AKIDRgfPtTnpb6LJ0QaT5QUHn32lERN2QXO0";
            String SecretKey=" MRFm33mEPKxKRHrACwPbYGhrmrgEVVFi";
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(SecretId, SecretKey);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("ocr.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            OcrClient client = new OcrClient(cred, "ap-guangzhou", clientProfile);

            // 实例化一个请求对象,每个接口都会对应一个request对象
            GeneralEfficientOCRRequest req = new GeneralEfficientOCRRequest();
           // req.setImageBase64("统一吉林银行、ty");
            req.setImageUrl("http://120.25.247.50:9000/car/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20211123161341.jpg");
            // 返回的resp是一个GeneralEfficientOCRResponse的实例，与请求对象对应
            GeneralEfficientOCRResponse resp = client.GeneralEfficientOCR(req);
            // 输出json格式的字符串回包
            System.out.println(GeneralEfficientOCRResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
    }
*/
}
