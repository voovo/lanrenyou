package com.lanrenyou.captcha;

import nl.captcha.text.producer.TextProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Random;

public class CaptchaTextProducer implements TextProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaTextProducer.class);
    private static final Random RANDOM = new SecureRandom();
    private static final int DEFAULT_CAPTCHA_LENGTH = 4;
    private static final int MAX_BUILD_CAPTCHA_TEXT_COUNT = 3;
    private static final char[] DEFAULT_CAPTCHA_CHAR_SET = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'j', 'k', 'm', 'n', 'p', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y',
            'A', 'B', 'D', 'E', 'F', 'G', 'H',
            'J', 'K', 'L', 'M', 'N', 'P', 'R',
            'S', 'T', 'U', 'W', 'X', 'Y', '2',
            '3', '4', '5', '6', '7', '8'}; //i,1,l,o,0,q,9因为不易辨识，V,W连在一起时不易辨识;
    private static final String[] EXCLUDE_CAPTCHA_SET = {"NM", "NN", "MN", "nn", "MM"};
    private final int _length;
    private final char[] _srcChars;

    public CaptchaTextProducer() {
        this(DEFAULT_CAPTCHA_LENGTH, DEFAULT_CAPTCHA_CHAR_SET);
    }

    public CaptchaTextProducer(int length, char[] srcChars) {
        this._length = length;
        this._srcChars = copyOf(srcChars, srcChars.length);
    }

    @Override
    public String getText() {
        StringBuilder capTextBuilder;
        String textResult;
        boolean needRebuild = false;
        int buildCount = 0;
        while (true) {
            capTextBuilder = new StringBuilder();
            for (int i = 0; i < this._length; i++) {
                capTextBuilder.append(this._srcChars[RANDOM.nextInt(this._srcChars.length)]);
            }
            textResult = capTextBuilder.toString();
            for (String element : EXCLUDE_CAPTCHA_SET) {
                if (textResult.contains(element)) {
                    LOGGER.warn("===>>>Captcha text {} contains exclude item {}, build it again.", textResult, element);
                    needRebuild = true;
                    break;
                }
            }
            if (!needRebuild || ++buildCount >= MAX_BUILD_CAPTCHA_TEXT_COUNT) {//控制生成次数，避免无限循环
                LOGGER.debug("===>>>Total build captcha count is {}", buildCount);
                break;
            }
        }

        return textResult;
    }

    private static char[] copyOf(char[] original, int newLength) {
        char[] copy = new char[newLength];
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }
}
