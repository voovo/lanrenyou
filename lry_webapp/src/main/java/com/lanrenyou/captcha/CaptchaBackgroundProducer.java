package com.lanrenyou.captcha;

import nl.captcha.backgrounds.BackgroundProducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lanrenyou.util.ServletUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.security.SecureRandom;
import java.util.Random;

public class CaptchaBackgroundProducer implements BackgroundProducer {
    private static final Random RANDOM = new SecureRandom();
    private static File[] backgroundImages;

    public CaptchaBackgroundProducer(HttpServletRequest request) {
    	File backgroundImageDir = new File(ServletUtil.getWebRoot(request) + "/WEB-INF/ftl/captcha/background");
        if (backgroundImageDir.exists() && backgroundImageDir.isDirectory()) {
            backgroundImages = backgroundImageDir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".jpg") || name.endsWith(".png");
                }
            });
        }
    }

    public BufferedImage getBackground(int width, int height) {
        if (backgroundImages.length <= 0 || width <= 0 || height <= 0) {
            return null;
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(backgroundImages[Math.abs(RANDOM.nextInt()) % backgroundImages.length]);
            Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage target = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
            Graphics g = target.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            return target;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage addBackground(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        return getBackground(width, height);
    }
}