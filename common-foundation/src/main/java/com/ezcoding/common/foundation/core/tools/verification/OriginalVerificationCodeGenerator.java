package com.ezcoding.common.foundation.core.tools.verification;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
@Deprecated
public class OriginalVerificationCodeGenerator extends AbstractImageVerificationCodeGenerator {

    private final char[] charRange = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    @Override
    public VerificationCode generate() throws IOException {

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        //填充背景
        graphics.setColor(new Color(255, 255, 255));
        graphics.fillRect(0, 0, width, height);

        //设置字体
        int fontSize = (int) (height * 0.8);
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        graphics.setFont(font);

        //随机生成字符
        int yLoc = (int) (height * 0.85);
        int xLoc = (int) (width / length);
        char[] cs = new char[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            //生成对应字符的下标
            int index = random.nextInt(charRange.length);
            //获取对应字符
            char c = charRange[index];
            cs[i] = c;
            //随机生成字体颜色
            Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            graphics.setColor(color);
            graphics.drawString(String.valueOf(c), i * xLoc, yLoc);
        }

        //写出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", baos);

        //生成验证码
        return new ImageVerificationCode(cs, baos.toByteArray(), height, width, length);
    }

}
