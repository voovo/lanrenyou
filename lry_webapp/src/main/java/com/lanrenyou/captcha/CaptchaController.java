package com.lanrenyou.captcha;

import nl.captcha.Captcha;
import nl.captcha.text.renderer.WordRenderer;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lanrenyou.controller.base.BaseController;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/captcha")
public class CaptchaController extends BaseController {

    private static final int DEFAULT_CAPTCHA_IMAGE_WIDTH = 80;
    private static final int DEFAULT_CAPTCHA_IMAGE_HEIGHT = 34;
    private static final int DEFAULT_CAPTCHA_LENGTH = 4;
    private static final Font[] DEFAULT_FONT_LIST = {
        new Font("Franklin Gothic Medium", Font.HANGING_BASELINE, 22),
        new Font("Franklin Gothic Medium", Font.ITALIC, 22),
        new Font("Franklin Gothic Medium", Font.BOLD, 22),
        new Font("Franklin Gothic Medium", Font.ROMAN_BASELINE, 22),
        new Font("Franklin Gothic Medium", Font.LAYOUT_RIGHT_TO_LEFT, 22),
        new Font("Franklin Gothic Medium", Font.LAYOUT_LEFT_TO_RIGHT, 22),
        new Font("Franklin Gothic Medium", Font.TRUETYPE_FONT, 22)
    };
    private static final Color[] DEFAULT_COLOR_LIST = {
        new Color(0x04534e), new Color(0x8c004c), new Color(0x1a2788), new Color(0x2e2e2e),
        new Color(0x115000), new Color(0x006390), new Color(0x6d6d6d), new Color(0x884611)

    };
    private static final char[] DEFAULT_CAPTCHA_CHAR_SET = new char[]{
        'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'j', 'k', 'm', 'n', 'p', 'r',
        's', 't', 'u', 'v', 'w', 'x', 'y',
        'A', 'B', 'D', 'E', 'F', 'G', 'H',
        'J', 'K', 'L', 'M', 'N', 'P', 'R',
        'S', 'T', 'U', 'W', 'X', 'Y', '2',
        '3', '4', '5', '6', '7', '8'}; //i,1,l,o,0,q,9因为不易辨识，V,W连在一起时不易辨识;

    @RequestMapping("/getCaptcha")
    public ModelAndView getCaptcha(HttpServletResponse httpResponse) throws IOException {
        //自定义设置字体颜色和大小 最简单的效果 多种字体随机显示
        WordRenderer wordRenderer = new TextCaptchaRender(Arrays.asList(DEFAULT_COLOR_LIST), Arrays.asList(DEFAULT_FONT_LIST));
        //自定义验证码背景
        CaptchaBackgroundProducer captchaBackground = new CaptchaBackgroundProducer(request);

        Captcha.Builder builder = new Captcha.Builder(DEFAULT_CAPTCHA_IMAGE_WIDTH, DEFAULT_CAPTCHA_IMAGE_HEIGHT);

        Captcha captcha = builder.addBackground(captchaBackground).build(); //设置背景
        builder.addText(new CaptchaTextProducer(DEFAULT_CAPTCHA_LENGTH, DEFAULT_CAPTCHA_CHAR_SET), wordRenderer); //设置文本
        //当通过firebug之类的工具首次查看验证码图片的url时，请求会重新加载，导致cache中存的数据与实际呈现给用户的数据不一样
        //此类问题可以不予理会，因为真实情况下很少有人会去用firebug查看url
        String captchaValue = captcha.getAnswer().toUpperCase();
        request.getSession().setAttribute("captchaValue", captchaValue + "#"+System.currentTimeMillis());
        logger.debug("Create Captcha:{} | sessionId:{}", captchaValue, request.getSession().getId());
        try {
            httpResponse.setHeader("Cache-Control", "private,no-cache,no-store");
            httpResponse.setContentType("image/png");
            ImageIO.write(captcha.getImage(), "png", httpResponse.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/checkCaptcha", method=RequestMethod.POST)
	@ResponseBody
	public String checkCaptcha(@RequestParam(value = "reg_code") String captcha){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isBlank(captcha)){
			map.put("status", "n");
			map.put("info", "验证码不得为空");
			return gson.toJson(map);
		}
		String sessionCaptcha = (String) request.getSession().getAttribute("captchaValue");
		if(null == sessionCaptcha){
			map.put("status", "n");
			map.put("info", "验证码验证失败");
			return gson.toJson(map);
		}
		String[] arr = sessionCaptcha.split("#");
		if(arr.length != 2){
			map.put("status", "n");
			map.put("info", "验证码验证失败");
			return gson.toJson(map);
		}
		long startTime = Long.parseLong(arr[1]);
		if(System.currentTimeMillis() - startTime > 600000){
			map.put("status", "n");
			map.put("info", "验证码失效");
			return gson.toJson(map);
		}
		logger.debug("Check Captcha:{}| session captcha:{} | sessionId:{}", captcha, arr[0], request.getSession().getId());
		if(!captcha.toUpperCase().equals(arr[0])){
			map.put("status", "n");
			map.put("info", "验证码不正确");
			return gson.toJson(map);
		}
		map.put("status", "y");
		map.put("info", "验证码校验成功");
		return gson.toJson(map);
	}
    
}
