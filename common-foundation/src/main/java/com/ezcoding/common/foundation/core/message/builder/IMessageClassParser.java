package com.ezcoding.common.foundation.core.message.builder;

import com.ezcoding.common.foundation.core.message.RequestMessage;

import java.io.IOException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-07-18 20:37
 */
public interface IMessageClassParser {

    /**
     * 根据请求报文解析成对应的包装类
     *
     * @param message 请求报文
     * @param clz     待解析的类，需存在一个无参构造函数
     * @param <T>     待解析的类
     * @return 解析的对象
     * @throws IOException 对象解析异常
     */
    <T> T parse(RequestMessage<?> message, Class<T> clz) throws IOException;

    /**
     * 根据请求报文解析成对应的包装类数组
     *
     * @param message 请求报文
     * @param clzs    待解析的类，需存在一个无参构造函数
     * @return 待解析的类
     * @throws IOException 对象解析异常
     */
    Object[] parse(RequestMessage<?> message, Class<?>[] clzs) throws IOException;

}
