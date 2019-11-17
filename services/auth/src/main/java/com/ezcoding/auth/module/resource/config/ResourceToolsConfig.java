package com.ezcoding.auth.module.resource.config;

import com.ezcoding.auth.module.resource.core.expression.DefaultRoleExpressionMatcher;
import com.ezcoding.auth.module.resource.core.judge.AdminResourceJudge;
import com.ezcoding.auth.module.resource.core.judge.IResourceJudgeable;
import com.ezcoding.auth.module.resource.core.judge.PathResourceJudge;
import com.ezcoding.auth.module.resource.core.judge.ResourceJudgeChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-07 11:10
 */
@Configuration
public class ResourceToolsConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    public RedisTemplate<String, String> accessListRedisTemplate() {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(stringRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(stringRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    private PathResourceJudge pathResourceJudge() {
        PathResourceJudge pathResourceJudge = new PathResourceJudge();
        pathResourceJudge.setRedisTemplate(accessListRedisTemplate());
        pathResourceJudge.setMatcher(new DefaultRoleExpressionMatcher());
        return pathResourceJudge;
    }

    private AdminResourceJudge adminResourceJudge() {
        return new AdminResourceJudge();
    }

    @Bean
    public IResourceJudgeable resourceJudgeable() {
        PathResourceJudge pathResourceJudge = pathResourceJudge();
        AdminResourceJudge adminResourceJudge = adminResourceJudge();

        ResourceJudgeChain resourceJudgeChain = new ResourceJudgeChain();
        resourceJudgeChain.addJudge(adminResourceJudge);
        resourceJudgeChain.addJudge(pathResourceJudge);
        return resourceJudgeChain;
    }

}
