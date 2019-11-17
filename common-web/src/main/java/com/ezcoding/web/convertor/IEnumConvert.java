package com.ezcoding.web.convertor;

import java.io.Serializable;

/**
 * spring mvc converter插件，将前端传入的string类型格式的参数转换为enum类型的参数
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2018-08-17 13:57
 */
public interface IEnumConvert {

    /**
     * 从前端传入的枚举值
     *
     * @return 返回本enum值的标识
     */
    Serializable getConvertValue();

}
