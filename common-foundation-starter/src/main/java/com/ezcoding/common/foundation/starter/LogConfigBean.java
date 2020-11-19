package com.ezcoding.common.foundation.starter;

import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:26
 */
public class LogConfigBean {

    private Boolean enable = false;
    private List<String> printerClass;
    private List<String> parserClass;
    private List<String> formatterClass;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<String> getPrinterClass() {
        return printerClass;
    }

    public void setPrinterClass(List<String> printerClass) {
        this.printerClass = printerClass;
    }

    public List<String> getParserClass() {
        return parserClass;
    }

    public void setParserClass(List<String> parserClass) {
        this.parserClass = parserClass;
    }

    public List<String> getFormatterClass() {
        return formatterClass;
    }

    public void setFormatterClass(List<String> formatterClass) {
        this.formatterClass = formatterClass;
    }

}
