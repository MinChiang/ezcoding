package com.ezcoding.auth.module.resource.core.judge;

import com.ezcoding.auth.module.resource.bean.model.ResourceBundle;
import com.ezcoding.auth.module.resource.core.expression.DefaultRoleExpressionMatcher;
import com.ezcoding.auth.module.resource.core.expression.IRoleExpressionMatchable;
import com.ezcoding.sdk.account.user.api.IUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Optional;
import java.util.Set;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-06 11:23
 */
public class PathResourceJudge implements IResourceJudgeable {

    private static final double SPLITTER_MIN = 2D;
    private static final String REDIS_PATTERN_PREFIX = "auth:resource:";
    private static final String REDIS_ROLES_LIST = "auth:resource:role_list";
    private static final String PATH_PATTERN = "/";
    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

    private IRoleExpressionMatchable matcher = new DefaultRoleExpressionMatcher();

    private RedisTemplate<String, String> redisTemplate;

    /**
     * 从请求路径中解析系统名称
     *
     * @return 解析出的系统名称
     */
    private String determineApplicationName(String uri) {
        String[] split = StringUtils.split(uri, PATH_PATTERN, 2);
        return split == null ? null : split[0];
    }

    @Override
    public boolean judge(ResourceBundle bundle, IUser user) {
        //获取系统应用名称
        String applicationName = this.determineApplicationName(bundle.getAccessPath());
        if (applicationName == null) {
            return true;
        }

        //获取对应的路径分割数量
        int splitterMax = StringUtils.countMatches(bundle.getAccessPath(), PATH_PATTERN);
        //进行路径匹配
        Set<String> patterns = redisTemplate.opsForZSet().reverseRangeByScore(REDIS_PATTERN_PREFIX + applicationName, SPLITTER_MIN, splitterMax);
        if (patterns != null) {
            String bundleAccessPath = bundle.getAccessPath();
            //去除路径末尾的路径标志
            if (bundleAccessPath.endsWith(PATH_PATTERN)) {
                bundleAccessPath = bundleAccessPath.substring(0, bundleAccessPath.length() - 1);
            }
            String finalBundleAccessPath = bundleAccessPath;
            Optional<String> first = patterns.stream().filter(p -> PATH_MATCHER.match(p, finalBundleAccessPath)).findFirst();
            //没有进行匹配的路径规则，则直接放行
            if (!first.isPresent()) {
                return true;
            }
            Object rolesExpression = redisTemplate.opsForHash().get(REDIS_ROLES_LIST, first.get());
            if (rolesExpression == null) {
                return true;
            }
            return matcher.match(rolesExpression.toString(), user.getRoles());
        }
        return true;
    }

    public IRoleExpressionMatchable getMatcher() {
        return matcher;
    }

    public void setMatcher(IRoleExpressionMatchable matcher) {
        this.matcher = matcher;
    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
