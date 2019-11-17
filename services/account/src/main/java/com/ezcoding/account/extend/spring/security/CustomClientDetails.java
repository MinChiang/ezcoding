package com.ezcoding.account.extend.spring.security;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-10-11 21:04
 */
@Data
@TableName("account_oauth")
public class CustomClientDetails {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端密码
     */
    private String clientSecret;

    /**
     * 访问范围
     */
    private String scopes;

    /**
     * 有权限的资源id
     */
    private String resourceIds;

    /**
     * 授权模式
     */
    private String authorizedGrantTypes;

    /**
     * 注册的自动跳转url
     */
    private String registeredRedirectUris;

    /**
     * 自动授权的访问范围
     */
    private String autoApproveScopes;

    /**
     * token有效时长
     */
    private Integer accessTokenValiditySeconds;

    /**
     * refresh_token有效时长
     */
    private Integer refreshTokenValiditySeconds;

    /**
     * 额外信息
     */
    private String additionalInformations;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date createTime;

}
