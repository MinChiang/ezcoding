package com.ezcoding.parser.service;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-06-17 9:26
 */
public interface IParsable {

    /**
     * 解析字符串内容
     *
     * @param content 字符串内容
     * @return 解析是否成功
     */
    boolean parse(String content);

}
