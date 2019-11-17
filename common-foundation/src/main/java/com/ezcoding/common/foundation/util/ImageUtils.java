package com.ezcoding.common.foundation.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-12-12 17:02
 */
public class ImageUtils {

    /**
     * 将图片格式转换为目标格式
     *
     * @param is         图片输入流
     * @param formatName 需要格式化的目标格式，如jpg、png等
     * @return 图片输出流
     * @throws IOException 图片转格式失败
     */
    public static byte[] formatImageStyle(InputStream is, String formatName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        formatImageStyle(is, baos, formatName);
        return baos.toByteArray();
    }

    /**
     * 将图片格式转换为目标格式
     *
     * @param is         图片输入流
     * @param os         输出流
     * @param formatName 需要格式化的目标格式，如jpg、png等
     * @throws IOException 图片转格式失败
     */
    public static void formatImageStyle(InputStream is, OutputStream os, String formatName) throws IOException {
        BufferedImage read = ImageIO.read(is);
        ImageIO.write(read, formatName, os);
    }

    /**
     * 将图片大小转换为目标大小以及目标格式
     *
     * @param is         图片输入流
     * @param formatName 目标格式
     * @param height     目标高度
     * @param width      目标宽度
     * @return 图片输出流
     * @throws IOException 图片转大小失败
     */
    public static byte[] formatImageSize(InputStream is, String formatName, int width, int height) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        formatImageSize(is, baos, formatName, width, height);
        return baos.toByteArray();
    }

    /**
     * 将图片大小转换为目标大小以及目标格式
     *
     * @param is         图片输入流
     * @param os         图片输出流
     * @param formatName 目标格式
     * @param height     目标高度
     * @param width      目标宽度
     * @throws IOException 图片转大小失败
     */
    public static void formatImageSize(InputStream is, OutputStream os, String formatName, int width, int height) throws IOException {
        BufferedImage read = ImageIO.read(is);
        BufferedImage bufferedImage = new BufferedImage(width, height, read.getType());
        bufferedImage.getGraphics().drawImage(read, 0, 0, width, height, null);
        ImageIO.write(bufferedImage, formatName, os);
    }

}
