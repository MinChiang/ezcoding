package com.ezcoding.common.mybatis.type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-02-19 15:30
 */
@MappedTypes(JsonNode.class)
public class JsonTypeHandler extends BaseTypeHandler<JsonNode> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setString(i, objectMapper.writeValueAsString(parameter));
        } catch (JsonProcessingException e) {
            throw new SQLException("can not parse parameter [" + i + "]", e);
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        try {
            String string = rs.getString(columnName);
            if (string == null) {
                return null;
            }
            return objectMapper.readTree(string);
        } catch (IOException e) {
            throw new SQLException("can not parse parameter [" + columnName + "]", e);
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        try {
            String string = rs.getString(columnIndex);
            if (string == null) {
                return null;
            }
            return objectMapper.readTree(string);
        } catch (IOException e) {
            throw new SQLException("can not parse paramter [" + columnIndex + "]", e);
        }
    }

    @Override
    public JsonNode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        try {
            String string = cs.getString(columnIndex);
            if (string == null) {
                return null;
            }
            return objectMapper.readTree(string);
        } catch (IOException e) {
            throw new SQLException("can not parse parameter [" + columnIndex + "]", e);
        }
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

}
