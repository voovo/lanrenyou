package com.lanrenyou.captcha;

import nl.captcha.text.renderer.WordRenderer;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * User: haluo
 * Date: 12-10-9
 * Time: 下午2:01
 */
public class TextCaptchaRender implements WordRenderer {
    private static final Random RANDOM = new SecureRandom();
    private static final List<Color> DEFAULT_COLORS = new LinkedList<Color>();
    private static final List<Font> DEFAULT_FONTS = new LinkedList<Font>();
    private static final double MIN_WIDTH_START_SPAN_RATIO = 0.2D;
    private static final double MAX_WIDTH_START_SPAN_RATIO = 0.8D;
    private static final double MIN_HEIGHT_BOUND_SPAN_RATIO = 0.3D;
    private static final double MAX_HEIGHT_BOUND_SPAN_RATIO = 0.7D;
    private final List<Color> colors = new LinkedList<Color>();
    private final List<Font> fonts = new LinkedList<Font>();

    static {
        DEFAULT_COLORS.add(Color.BLACK);
        DEFAULT_FONTS.add(new Font("Arial", 1, 40));
        DEFAULT_FONTS.add(new Font("Courier", 1, 40));
    }

    public TextCaptchaRender() {
        this(DEFAULT_COLORS, DEFAULT_FONTS);
    }

    public TextCaptchaRender(List<Color> colors, List<Font> fonts) {
        this.colors.addAll(colors);
        this.fonts.addAll(fonts);
    }

    public void render(String word, BufferedImage image) {
        Graphics2D g2D = image.createGraphics();
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2D.setRenderingHints(hints);

        FontRenderContext fontRenderContext = g2D.getFontRenderContext();

        char wordChars[] = word.toCharArray();
        Font chosenFonts[] = new Font[wordChars.length];
        int charWidths[] = new int[wordChars.length];
        int totalWidth = 0;
        int charSpace;
        for (int i = 0; i < wordChars.length; i++) {
            chosenFonts[i] = this.fonts.get(RANDOM.nextInt(this.fonts.size()));
            char charToDraw[] = {wordChars[i]};
            GlyphVector gv = chosenFonts[i].createGlyphVector(fontRenderContext, charToDraw);
            charSpace = RANDOM.nextInt(3) + 1;  //字符间的间隔,为1到3个像素
            charWidths[i] = (int) gv.getVisualBounds().getWidth() + charSpace;
            totalWidth = totalWidth + charWidths[i]; //所有字符需要的总的宽度，便于控制字符边界，防止超出底图的范围
        }

        double widthStartSpanRatio = Math.abs(Math.random());   //字符离背景图横向起始边界的比例
        if (widthStartSpanRatio < MIN_WIDTH_START_SPAN_RATIO) { //这里是为了防止字符离横向起始边界太近
            widthStartSpanRatio = MIN_WIDTH_START_SPAN_RATIO;
        }
        if (widthStartSpanRatio > MAX_WIDTH_START_SPAN_RATIO) { //这里是为防止字符离横向起始边界太远
            widthStartSpanRatio = MAX_WIDTH_START_SPAN_RATIO;
        }
        //相对于背景图,宽度的起始点,减去字符所占的中宽度是为了保证背景图能容下所有的字符
        int xBaseLine = (int) ((image.getWidth() - totalWidth) * widthStartSpanRatio);
        int yBaseLine;
        for (int i = 0; i < wordChars.length; i++) {
            //设置字体
            int fontSize = chosenFonts[i].getSize();
            g2D.setFont(chosenFonts[i]);
            //设置颜色
            g2D.setColor(this.colors.get(RANDOM.nextInt(this.colors.size())));
            char charToDraw[] = {wordChars[i]};
            double heightBoundSpanRatio = Math.abs(Math.random());  //字符离背景图上边界间距的比例
            if (heightBoundSpanRatio < MIN_HEIGHT_BOUND_SPAN_RATIO) {   //这里是为了防止字符离上边界太近
                heightBoundSpanRatio = MIN_HEIGHT_BOUND_SPAN_RATIO;
            }
            if (widthStartSpanRatio > MAX_HEIGHT_BOUND_SPAN_RATIO) {    //这里是为了防止字符离上边界太远，即离下边界太近
                heightBoundSpanRatio = MAX_HEIGHT_BOUND_SPAN_RATIO;
            }
            yBaseLine = (int) ((image.getHeight() - fontSize) * heightBoundSpanRatio) + fontSize; //加上fontSize是为了保证高度一定能容下一个字
            g2D.drawChars(charToDraw, 0, charToDraw.length, xBaseLine, yBaseLine);
            xBaseLine = xBaseLine + charWidths[i];
        }
    }
}