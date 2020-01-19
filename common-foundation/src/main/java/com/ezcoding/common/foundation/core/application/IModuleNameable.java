package com.ezcoding.common.foundation.core.application;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-10 13:46
 */
public interface IModuleNameable {

    String FILE_PATH_SPLITTER = "/";
    String NAMEABLE_MODULE_SPLITTER = ":";
    String UNDER_LINE_SPLITTER = "_";
    String MIDDLE_LINE_SPLITTER = "-";
    String DOT_SPLITTER = ".";

    /**
     * 系统号长度
     */
    int APPLICATION_CODE_LENGTH = 2;

    /**
     * 模块号长度
     */
    int MODULE_CODE_LENGTH = 4;

    /**
     * 业务功能号长度
     */
    int FUNCTION_CODE_LENGTH = 4;

    /**
     * 空缺内容填补字符
     */
    char FILL_CHAR = '0';

    /**
     * 功能性号码总长度
     */
    int DETAIL_CODE_LENGHT = APPLICATION_CODE_LENGTH + MODULE_CODE_LENGTH + FUNCTION_CODE_LENGTH;

    /**
     * 获取模块整体路径
     *
     * @param pathSplitter 路径分隔符
     * @return 模块整体路径
     */
    String getPath(String pathSplitter);

    /**
     * 获取模块唯一标志号码
     *
     * @return 唯一标志号码
     */
    String getCode();

    /**
     * 获取模块名称
     *
     * @return 模块名称
     */
    String getName();

}
