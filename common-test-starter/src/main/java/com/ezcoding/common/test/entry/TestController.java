package com.ezcoding.common.test.entry;

import com.ezcoding.common.foundation.core.exception.ModuleExceptionBuilderFactory;
import com.ezcoding.common.web.resolver.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.COMMON_PARAM_VALIDATE_ERROR;
import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.COMMON_REQUEST_TYPE_ERROR;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-20 13:42
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ModuleExceptionBuilderFactory moduleExceptionBuilderFactory;

    @GetMapping("1")
    @JsonResult
    public String test1() {
        throw moduleExceptionBuilderFactory.messageSourceTemplateExceptionBuilder(COMMON_PARAM_VALIDATE_ERROR).build();
    }

    @GetMapping("2")
    @JsonResult
    public String test2() {
        throw moduleExceptionBuilderFactory.messageSourceTemplateExceptionBuilder(COMMON_REQUEST_TYPE_ERROR).build();
    }

}
