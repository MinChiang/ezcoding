package com.ezcoding.common.test.entry;

import com.ezcoding.common.foundation.core.exception.processor.WebExceptionBuilderFactory;
import com.ezcoding.common.web.resolver.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ezcoding.common.foundation.core.exception.ExceptionCodeGeneratorConstants.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-01-20 13:42
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @GetMapping("1")
    @JsonResult
    public String test1() {
        throw WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_PARAM_VALIDATE_ERROR).build();
    }

    @GetMapping("2")
    @JsonResult
    public String test2() {
        throw WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_REQUEST_TYPE_ERROR).build();
    }

    @GetMapping("3")
    @JsonResult
    public String test3() {
        throw new NullPointerException("fuck");
    }

    @GetMapping("4")
    @JsonResult
    public String test4() {
        throw WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_RESOURCE_NOT_FIND_ERROR).params("xixi", "haha").build();
    }

    @GetMapping("5")
    @JsonResult
    public String test5() {
        throw WebExceptionBuilderFactory.webExceptionBuilder(GEN_COMMON_USER_NOT_LOGIN_ERROR).params("xixi", "haha").build();
    }

}
