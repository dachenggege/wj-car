package org.springblade.car.factory;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author bond
 * @date 2021/9/22 13:41
 * @desc
 */
public class ImageUtils {
    /**
     * 将网络图片进行Base64位编码
     * @param imgUrl 图片的url路径，如http://.....xx.jpg
     * @return
     */
    public static String encodeImgageToBase64(URL imageUrl) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(imageUrl);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(outputStream.toByteArray());
    }
    /**
     * 将本地图片进行Base64位编码
     * @param imgUrl 图片的url路径，如http://.....xx.jpg
     * @return
     */
    public static String encodeImgageToBase64(File imageFile) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(outputStream.toByteArray());
    }

    /**
     * @param base64编码的图片信息
     * @return
     */
    public static void decodeBase64ToImage(String base64, String path,
                                           String imgName) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            FileOutputStream write = new FileOutputStream(new File(path
                    + imgName));
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
