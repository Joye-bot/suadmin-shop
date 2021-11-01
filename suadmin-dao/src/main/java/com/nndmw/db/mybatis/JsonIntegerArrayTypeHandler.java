package com.nndmw.db.mybatis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * json整数数组类型的处理程序
 *
 * @author nndmw
 * @date 2021/10/31
 */
public class JsonIntegerArrayTypeHandler extends BaseTypeHandler<Integer[]> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i,
                                    Integer[] integers, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, toJson(integers));
    }

    @Override
    public Integer[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        try {
            return this.toObject(resultSet.getString(s));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new Integer[0];
    }

    @Override
    public Integer[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        try {
            return this.toObject(resultSet.getString(i));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new Integer[0];
    }

    @Override
    public Integer[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        try {
            return this.toObject(callableStatement.getString(i));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new Integer[0];
    }

    /**
     * 转换为json
     *
     * @param params 参数个数
     * @return {@link String}
     */
    private String toJson(Integer[] params) {
        try {
            return MAPPER.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "[]";
    }

    /**
     * 转换为对象
     *
     * @param content 内容
     * @return {@link Integer[]}
     * @throws JsonProcessingException json处理异常
     */
    private Integer[] toObject(String content) throws JsonProcessingException {
        if (content != null && !content.isEmpty()) {
            return MAPPER.readValue(content, Integer[].class);
        } else {
            return new Integer[0];
        }
    }
}
