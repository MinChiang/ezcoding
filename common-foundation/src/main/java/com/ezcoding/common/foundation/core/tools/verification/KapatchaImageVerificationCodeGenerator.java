package com.ezcoding.common.foundation.core.tools.verification;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-05-22 17:08
 */
public class KapatchaImageVerificationCodeGenerator extends AbstractImageVerificationCodeGenerator {

    private static final int DEFAULT_SIZE = 1 << 12;
    private static final String DEFAULT_FORMAT_NAME = "jpg";

    private Producer producer;

    public KapatchaImageVerificationCodeGenerator() {
        init();
    }

    private void init() {
        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", String.valueOf(width));
        properties.setProperty("kaptcha.image.height", String.valueOf(height));
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,宋体,宋体");
        Config config = new Config(properties);
        captchaProducer.setConfig(config);
        this.producer = captchaProducer;
    }

    @Override
    public VerificationCode generate() throws IOException {
        String text = producer.createText();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(DEFAULT_SIZE);
        ImageIO.write(producer.createImage(text), DEFAULT_FORMAT_NAME, byteArrayOutputStream);
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(text);
        verificationCode.setData(byteArrayOutputStream.toByteArray());
        return verificationCode;
    }

}
