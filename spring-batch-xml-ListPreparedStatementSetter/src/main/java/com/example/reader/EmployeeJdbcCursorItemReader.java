package com.example.reader;

import com.example.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EmployeeJdbcCursorItemReader extends JdbcCursorItemReader<Employee> {
    private String paramedSql;
    private ParsedSql parsedSql;
    List<SqlParameter> declaredParams;
    MapSqlParameterSource paramSource ;

    @Override
    public void setSql(String sql) {
        this.paramedSql = sql;
        Map<String, List<Integer>> namedParameters = new HashMap<>();
        namedParameters.put("ageList", Arrays.asList(22,44));

        parsedSql = NamedParameterUtils.parseSqlStatement(paramedSql);
        paramSource = new MapSqlParameterSource(namedParameters);

        String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
        super.setSql(sqlToUse);
    }

    @Override
    public void setPreparedStatementSetter(PreparedStatementSetter preparedStatementSetter) {

//        Map<String, List<Integer>> namedParameters = new HashMap<>();
//        namedParameters.put("ageList", Arrays.asList(22,44));
//
//        ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(paramedSql);
//        MapSqlParameterSource paramSource = new MapSqlParameterSource(namedParameters);
//
//        String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
        declaredParams = NamedParameterUtils.buildSqlParameterList(parsedSql, paramSource);

        Object[] parameters = NamedParameterUtils.buildValueArray(parsedSql, paramSource, declaredParams);
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory(paramedSql, declaredParams);
        PreparedStatementSetter pss = factory.newPreparedStatementSetter(parameters);

        //log.info("sql: {}", sqlToUse);
        log.info("parameters: {}", parameters);

        super.setPreparedStatementSetter(pss);
    }
}