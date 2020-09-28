package com.ezcoding.common.mybatis.type;

import com.ezcoding.common.foundation.core.enums.EnumMappableUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2020-09-28 9:39
 */
public class DelegateEnumHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private final Class<E> type;
    private final BaseTypeHandler<E> baseTypeHandler;

    public DelegateEnumHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
        this.baseTypeHandler = new EnumTypeHandler<>(this.type);
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        Object map = EnumMappableUtils.map(parameter);
        if (map == null) {
            baseTypeHandler.setNonNullParameter(ps, i, parameter, jdbcType);
        } else {
            if (jdbcType == null) {
                ps.setObject(i, map);
            } else {
                ps.setObject(i, map, jdbcType.TYPE_CODE);
            }
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String string = rs.getString(columnName);
        if (string == null) {
            return null;
        }
        E map = EnumMappableUtils.map(string, this.type);
        if (map == null) {
            return baseTypeHandler.getNullableResult(rs, columnName);
        } else {
            return map;
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String string = rs.getString(columnIndex);
        if (string == null) {
            return null;
        }
        E map = EnumMappableUtils.map(string, this.type);
        if (map == null) {
            return baseTypeHandler.getNullableResult(rs, columnIndex);
        } else {
            return map;
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String string = cs.getString(columnIndex);
        if (string == null) {
            return null;
        }
        E map = EnumMappableUtils.map(string, this.type);
        if (map == null) {
            return baseTypeHandler.getNullableResult(cs, columnIndex);
        } else {
            return map;
        }
    }

}
