package com.ezcoding.common.foundation.core.tools.verification;

import java.util.Objects;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-14 17:08
 */
public class ImageVerificationCode extends VerificationCode {

    private static final long serialVersionUID = -6551416325744919047L;

    private final int height;
    private final int width;
    private final int codeLength;

    public ImageVerificationCode(char[] code, byte[] data, int height, int width, int codeLength) {
        super(code, data);
        this.height = height;
        this.width = width;
        this.codeLength = codeLength;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getCodeLength() {
        return codeLength;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ImageVerificationCode that = (ImageVerificationCode) o;
        return height == that.height &&
                width == that.width &&
                codeLength == that.codeLength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height, width, codeLength);
    }

}
