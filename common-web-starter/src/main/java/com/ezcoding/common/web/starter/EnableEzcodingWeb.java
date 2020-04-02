package com.ezcoding.common.web.starter;

import com.ezcoding.common.foundation.starter.EnableEzcodingFoundation;
import com.ezcoding.common.foundation.starter.EzcodingFoundationAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-03-22 20:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableEzcodingFoundation
@Import({WebCommonConfig.class, WebMvcConfig.class})
public @interface EnableEzcodingWeb {

}
