package com.ezcoding.common.mybatis.type;

import com.ezcoding.common.core.user.model.LoginRegisterTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-03-24 9:30
 */
@MappedTypes(LoginRegisterTypeEnum.class)
public class LoginRegisterTypeEnumHandler extends BaseTypeHandler<LoginRegisterTypeEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LoginRegisterTypeEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getId());
    }

    @Override
    public LoginRegisterTypeEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int id = rs.getInt(columnName);
        return LoginRegisterTypeEnum.from(id);
    }

    @Override
    public LoginRegisterTypeEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int id = rs.getInt(columnIndex);
        return LoginRegisterTypeEnum.from(id);
    }

    @Override
    public LoginRegisterTypeEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int id = cs.getInt(columnIndex);
        return LoginRegisterTypeEnum.from(id);
    }

}
