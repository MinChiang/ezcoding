package com.ezcoding.generator.bean;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-11-23 9:29
 */
public class GenerateResult implements IGenerateResult {

    private boolean result;

    @Override
    public boolean result() {
        return this.result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
