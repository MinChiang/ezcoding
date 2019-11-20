package com.test;

import com.ezcoding.common.foundation.core.message.head.*;
import com.ezcoding.web.resolver.JsonParam;
import com.ezcoding.web.resolver.JsonResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-11-20 11:15
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @JsonResult
    @PostMapping
    public Map<String, Object> test(@JsonParam String input) {
        Map<String, Object> map = new HashMap<>();
        map.put("haha", input);
        return map;
    }

    @JsonResult
    @GetMapping
    public ResponseSystemHead test1(@JsonParam RequestSystemHead systemHead) {
        System.out.println(systemHead);
        return new ResponseSystemHead("1", "123123");
    }

    @JsonResult
    @DeleteMapping
    public ResponseAppHead test2(@JsonParam RequestAppHead requestAppHead) {
        System.out.println(requestAppHead);
        return new ResponseAppHead(new PageInfo(1, 1), "123", "123123");
    }

}
