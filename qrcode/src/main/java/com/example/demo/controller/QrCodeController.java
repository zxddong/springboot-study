package com.example.demo.controller;

import com.example.demo.util.QRCodeUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;


@Controller
public class QrCodeController {


    @Value("${image.path}")
    private String imagePath;

    /**
     * 根据 url 生成 普通二维码
     */
    @RequestMapping(value = "/createCommonQRCode")
    public void createCommonQRCode(HttpServletResponse response, String url) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            //使用工具类生成二维码
            QRCodeUtil.encode(url, stream);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }

    /**
     * 根据 url 生成 带有logo二维码
     */
    @RequestMapping(value = "/createLogoQRCode")
    public void createLogoQRCode(HttpServletResponse response, String url) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            String logoPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                    + "templates" + File.separator + "logo.png";
            //使用工具类生成二维码
            QRCodeUtil.encode(url, logoPath, stream, true);
            //使用工具类生成二维码图片
            QRCodeUtil.encode(url,logoPath,imagePath+File.separator+System.currentTimeMillis()+".png",true);
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }


}

