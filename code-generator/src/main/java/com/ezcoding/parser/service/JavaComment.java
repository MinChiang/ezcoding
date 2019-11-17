package com.ezcoding.parser.service;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-06-17 9:33
 */
public class JavaComment implements IParsable {

    /**
     * 基本内容
     */
    public String content;

    @Override
    public boolean parse(String content) {
        boolean result = false;
        return result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
