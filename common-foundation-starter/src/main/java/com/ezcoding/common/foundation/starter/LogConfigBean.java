package com.ezcoding.common.foundation.starter;

import com.ezcoding.common.foundation.core.log.impl.EmptyLogFormatter;
import com.ezcoding.common.foundation.core.log.impl.EmptyLogParser;
import com.ezcoding.common.foundation.core.log.impl.Slf4jLogPrinter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-11-19 14:26
 */
public class LogConfigBean {

    private Boolean enable = true;

    private String defaultPrinterClass = Slf4jLogPrinter.class.getName();
    private String defaultParserClass = EmptyLogParser.class.getName();
    private String defaultFormatterClass = EmptyLogFormatter.class.getName();

    private List<String> printerClass = new ArrayList<>();
    private List<String> parserClass = new ArrayList<>();
    private List<String> formatterClass = new ArrayList<>();

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getDefaultPrinterClass() {
        return defaultPrinterClass;
    }

    public void setDefaultPrinterClass(String defaultPrinterClass) {
        this.defaultPrinterClass = defaultPrinterClass;
    }

    public String getDefaultParserClass() {
        return defaultParserClass;
    }

    public void setDefaultParserClass(String defaultParserClass) {
        this.defaultParserClass = defaultParserClass;
    }

    public String getDefaultFormatterClass() {
        return defaultFormatterClass;
    }

    public void setDefaultFormatterClass(String defaultFormatterClass) {
        this.defaultFormatterClass = defaultFormatterClass;
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
