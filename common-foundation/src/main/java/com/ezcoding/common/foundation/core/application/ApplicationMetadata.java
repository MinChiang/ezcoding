package com.ezcoding.common.foundation.core.application;

import com.ezcoding.common.foundation.core.constant.GlobalConstants;
import com.ezcoding.common.foundation.core.tools.uuid.SnowflakeIdProducer;

/**
 * 机器服务信息标志位置
 * 设置样例：
 * dataCenterNo    category    categoryNo
 * 0               auth        0
 * 0               auth        1
 * 0               account     0
 * 0               account     1
 *
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-27 11:37
 */
public class ApplicationMetadata {

    /**
     * 数据中心号（同一个数据中心此变量需要相同，目前无具体作用）
     */
    private Integer dataCenterNo;

    /**
     * 类型名称（设置为服务名称）
     */
    private String category;

    /**
     * 服务类别序号（相同类型服务的机器序号）
     */
    private Integer categoryNo;

    /**
     * 当前启动微服务编号
     */
    private Integer categoryCode;

    /**
     * 格式化后的微服务编号
     */
    private String categoryFormat;

    public ApplicationMetadata(Integer dataCenterNo, String category, Integer categoryNo) {
        Integer code = GlobalConstants.Application.SERVICE_NAME_APPLICATION_CODE_MAPPING.get(category);
        if (code == null) {
            throw new Error("未找到当前应用：" + category + "的系统标识码，请在GlobalConstants.Application注册对应的类型，可选类型：" + GlobalConstants.Application.SERVICE_NAME_APPLICATION_CODE_MAPPING.keySet());
        }

        int maxApplicationCode = ((1 << GlobalConstants.Application.APPLICATION_CODE_BIT_LENGTH) - 1);
        if (code > maxApplicationCode) {
            throw new Error("服务号码最大只能支持到：" + maxApplicationCode);
        }
        int maxApplicationNo = ((1 << (SnowflakeIdProducer.MACHINE_BIT - GlobalConstants.Application.APPLICATION_CODE_BIT_LENGTH)) - 1);
        if (categoryNo > maxApplicationNo) {
            throw new Error("同类型机器号最大只能支持到：" + maxApplicationNo);
        }

        this.dataCenterNo = dataCenterNo;
        this.category = category;
        this.categoryNo = categoryNo;

        this.categoryCode = code;
        this.categoryFormat = categoryFormat(code);
    }

    /**
     * 获取格式化后的应用序号
     *
     * @return 应用序号
     */
    private String categoryFormat(int applicationCode) {
        StringBuilder result = new StringBuilder(String.valueOf(applicationCode));
        if (result.length() < GlobalConstants.Application.APPLICATION_CODE_LENGTH) {
            result.insert(0, "0");
        }
        return result.toString();
    }

    public Integer getDataCenterNo() {
        return dataCenterNo;
    }

    public String getCategory() {
        return category;
    }

    public Integer getCategoryNo() {
        return categoryNo;
    }

    public Integer getCategoryCode() {
        return categoryCode;
    }

    public String getCategoryFormat() {
        return categoryFormat;
    }

}
