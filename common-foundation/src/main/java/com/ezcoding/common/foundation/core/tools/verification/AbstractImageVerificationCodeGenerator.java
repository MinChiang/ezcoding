package com.ezcoding.common.foundation.core.tools.verification;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-05-22 17:19
 */
public abstract class AbstractImageVerificationCodeGenerator extends AbstractLimitLengthVerificationCodeGenerator {

    /**
     * 高度（像素）
     */
    protected int heigth = 50;

    /**
     * 长度（像素）
     */
    protected int width = 100;

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
