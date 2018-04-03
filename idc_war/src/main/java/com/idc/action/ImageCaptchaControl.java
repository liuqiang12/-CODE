package com.idc.action;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import system.data.supper.action.BaseController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * 验证码的单独控制器
 * @author Administrator
 *
 */
@Controller
public class ImageCaptchaControl extends BaseController {
	@Resource
    private ImageCaptchaService imageCaptchaService;
    
    @RequestMapping(value = "/captcha.do", method = RequestMethod.GET)
    public void getCode(HttpServletRequest request, HttpServletResponse response) {

        try {
        	
            ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
            String captchaId = request.getSession().getId();
            
            BufferedImage challenge = (BufferedImage) imageCaptchaService.getChallengeForID(captchaId,request.getLocale());
            
            ImageIO.write(challenge, "jpeg", jpegOutputStream);
            byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
            
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpeg");
            ServletOutputStream responseOutputStream = response.getOutputStream();
            responseOutputStream.write(captchaChallengeAsJpeg);
            responseOutputStream.flush();
            responseOutputStream.close();
            
        } catch (Exception e) {
            logger.error("generate captcha image error: {}", e.getMessage());
        }
        
    }
}
