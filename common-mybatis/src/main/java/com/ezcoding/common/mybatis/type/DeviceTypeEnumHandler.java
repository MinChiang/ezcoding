package com.ezcoding.common.mybatis.type;

import com.ezcoding.common.core.user.model.DeviceTypeEnum;
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
@MappedTypes(DeviceTypeEnum.class)
public class DeviceTypeEnumHandler extends BaseTypeHandler<DeviceTypeEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DeviceTypeEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getId());
    }

    @Override
    public DeviceTypeEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int id = rs.getInt(columnName);
        return DeviceTypeEnum.from(id);
    }

    @Override
    public DeviceTypeEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int id = rs.getInt(columnIndex);
        return DeviceTypeEnum.from(id);
    }

    @Override
    public DeviceTypeEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int id = cs.getInt(columnIndex);
        return DeviceTypeEnum.from(id);
    }

}
