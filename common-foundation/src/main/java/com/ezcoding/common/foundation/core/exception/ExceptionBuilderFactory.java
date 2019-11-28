package com.ezcoding.common.foundation.core.exception;

import com.ezcoding.common.foundation.core.application.ModuleLayerModule;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ezcoding.common.foundation.core.application.constants.ModuleConstants.DEFAULT_MODULE_LAYER_MODULE;
import static com.ezcoding.common.foundation.core.exception.CommonApplicationException.*;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-18 16:01
 */
public class ExceptionBuilderFactory<E extends AbstractApplicationException> {

    private static final Map<String, ExceptionBuilderFactory> CODE_BUILDER_MAPPING = Maps.newConcurrentMap();
    private static final Map<ErrorAliasBean, ExceptionBuilderFactory> ALIAS_BUILDER_MAPPING = Maps.newHashMap();
    private static final Map<ModuleLayerModule, List<ExceptionBuilderFactory>> MODULE_BUILDERS_MAPPING = Maps.newConcurrentMap();

    static {
        ExceptionBuilderFactory.register(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "1", "参数校验异常：{0}", COMMON_PARAM_VALIDATE_ERROR);
        ExceptionBuilderFactory.register(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "2", "请求类型异常", COMMON_REQUEST_TYPE_ERROR);
        ExceptionBuilderFactory.register(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "3", "未找到访问的资源", COMMON_RESOURCE_NOT_FIND_ERROR);
        ExceptionBuilderFactory.register(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "4", "用户未登录", COMMON_USER_NOT_LOGIN_ERROR);
        ExceptionBuilderFactory.register(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "5", "权限不足", COMMON_NO_PERMISSION_ERROR);
        ExceptionBuilderFactory.register(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "6", "参数解析异常：{0}", COMMON_PARAM_PARSE_ERROR);
        ExceptionBuilderFactory.register(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "7", "用户令牌异常", COMMON_TOKEN_PARSE_ERROR);
        ExceptionBuilderFactory.register(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "8", "服务暂不可用，请稍后重试", COMMON_SERVICE_NOT_AVALIABLE_ERROR);
        ExceptionBuilderFactory.register(CommonApplicationException.class, DEFAULT_MODULE_LAYER_MODULE, "9", "本用户已被踢出，请重新登陆", COMMON_USER_IS_KICKED_OUT_ERROR);
    }

    /**
     * 对应的错误类
     */
    private Class<E> e;

    /**
     * 错误详情码
     */
    private String detailCode;

    /**
     * 模板内容
     */
    private String template;

    /**
     * 错误所属模块
     */
    private ModuleLayerModule moduleLayerModule;

    private ExceptionBuilderFactory(Class<E> e, ModuleLayerModule moduleLayerModule, String detailCode, String template) {
        this.e = e;
        this.moduleLayerModule = moduleLayerModule;
        this.detailCode = detailCode;
        this.template = template;
    }

    /**
     * 注册错误构造器
     *
     * @param e                 错误类型
     * @param moduleLayerModule 所属模块号
     * @param detailCode        错误详情码
     * @param template          模板内容
     * @param <E>               错误类型
     * @param alias             异常别名
     * @return 构造器实例
     */
    public static <E extends AbstractApplicationException> ExceptionBuilderFactory<E> register(Class<E> e, ModuleLayerModule moduleLayerModule, String detailCode, String template, String... alias) {
        String key = moduleLayerModule.getCode() + StringUtils.leftPad(detailCode, DETAIL_CODE_LENGTH, FILL_CHAR);
        if (CODE_BUILDER_MAPPING.containsKey(key)) {
            throw new IllegalArgumentException("错误号码" + key + "已经被注册");
        }
        List<ErrorAliasBean<E>> collect = null;
        if (alias != null) {
            collect = Arrays.stream(alias).map(as -> new ErrorAliasBean<>(e, as)).collect(Collectors.toList());
            if (collect.stream().anyMatch(ALIAS_BUILDER_MAPPING::containsKey)) {
                throw new IllegalArgumentException("错误别称" + collect.get(0).alias + "已经被注册");
            }
        }

        ExceptionBuilderFactory<E> factory = new ExceptionBuilderFactory<>(e, moduleLayerModule, detailCode, template);
        CODE_BUILDER_MAPPING.put(key, factory);
        if (CollectionUtils.isNotEmpty(collect)) {
            collect.forEach(eab -> ALIAS_BUILDER_MAPPING.put(eab, factory));
        }
        MODULE_BUILDERS_MAPPING.computeIfAbsent(moduleLayerModule, module -> Lists.newLinkedList()).add(factory);
        return factory;
    }

    /**
     * 注册错误构造器
     *
     * @param e                 错误类型
     * @param moduleLayerModule 所属模块号
     * @param detailCode        错误详情码
     * @param template          模板内容
     * @param <E>               错误类型
     * @return 构造器实例
     */
    public static <E extends AbstractApplicationException> ExceptionBuilderFactory<E> register(Class<E> e, ModuleLayerModule moduleLayerModule, String detailCode, String template) {
        return register(e, moduleLayerModule, detailCode, template, (String[]) null);
    }

    /**
     * 查找异常构造器
     *
     * @param moduleLayerModule 所属模块号
     * @param detailCode        错误详情码
     * @return 构造器实例
     */
    public static ExceptionBuilderFactory<?> lookup(ModuleLayerModule moduleLayerModule, String detailCode) {
        String key = moduleLayerModule.getCode() + StringUtils.leftPad(detailCode, DETAIL_CODE_LENGTH, FILL_CHAR);
        return CODE_BUILDER_MAPPING.get(key);
    }

    /**
     * 查找异常构造器
     *
     * @param code 错误号码（错误详情号）
     * @return 构造器实例
     */
    public static ExceptionBuilderFactory<?> lookupByErrorCode(String code) {
        return CODE_BUILDER_MAPPING.get(code);
    }

    /**
     * 查找异常构造器
     *
     * @param e     错误类型
     * @param alias 待查找的别名
     * @param <E>   错误类型
     * @return 构造器实例
     */
    public static <E extends AbstractApplicationException> ExceptionBuilderFactory<E> lookupByAlias(Class<E> e, String alias) {
        return ALIAS_BUILDER_MAPPING.get(new ErrorAliasBean<>(e, alias));
    }

    /**
     * 查找模块所属的异常构造器
     *
     * @param moduleLayerModule 所属模块号
     * @return 构造器实例
     */
    public static List<ExceptionBuilderFactory> lookupBuilders(ModuleLayerModule moduleLayerModule) {
        return MODULE_BUILDERS_MAPPING.get(moduleLayerModule);
    }

    /**
     * 新建创建器
     *
     * @return 异常创建器
     */
    public ExceptionBuilder<E> instance() {
        return new ExceptionBuilder<>(this.e, this.moduleLayerModule, this.detailCode, this.template);
    }

    private static final class ErrorAliasBean<E extends AbstractApplicationException> {

        private Class<E> e;
        private String alias;

        ErrorAliasBean(Class<E> e, String alias) {
            this.e = e;
            this.alias = alias;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ErrorAliasBean<?> that = (ErrorAliasBean<?>) o;
            return Objects.equals(e, that.e) &&
                    Objects.equals(alias, that.alias);
        }

        @Override
        public int hashCode() {
            return Objects.hash(e, alias);
        }

    }

}
