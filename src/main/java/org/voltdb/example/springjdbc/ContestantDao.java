/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2008-2015 VoltDB Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.voltdb.example.springjdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public class ContestantDao extends NamedParameterJdbcTemplate {
    private static final String selectSql = "SELECT firstname AS firstName, lastname AS lastName, code AS code FROM CONTESTANTS WHERE code = :code";
    private static final String getAllSql = "SELECT firstname AS firstName, lastname AS lastName, code AS code FROM CONTESTANTS";
    private static final String deleteByCode = "DELETE FROM CONTESTANTS WHERE code = :code";
    private static final String deleteAll = "DELETE FROM CONTESTANTS";
    private static final String insertContestant = "INSERT INTO CONTESTANTS ( FIRSTNAME,LASTNAME,CODE) VALUES ( :firstName, :lastName, :code )";
    private static final String updateContestantByCode = "UPDATE CONTESTANTS SET firstname = :firstName, lastname  = :lastName WHERE code = :code";

    @Autowired
    private DataSource dataSource;

    public ContestantDao(DataSource source) {
        super(source);
    }

    //Mapper to fetch row and get POJO
    class ContestantRowMapper implements RowMapper {

        @Override
        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            ContestantData row = new ContestantData();
            row.setFirstName(rs.getString("firstname"));
            row.setLastName(rs.getString("lastname"));
            row.setCode(rs.getString("code"));
            return row;
        }

    }

    public List<ContestantData> findContestant(String code) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", new SqlParameterValue(Types.VARCHAR, code));
        return query( selectSql, params, new ContestantRowMapper());
    }

    public List<ContestantData> getAllContestants() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return query( getAllSql, params, new ContestantRowMapper());
    }

    public int deleteAllContestants() {
        MapSqlParameterSource params = new MapSqlParameterSource();
        return update( deleteAll, params);
    }

    public int deleteContestant(String code) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("code", new SqlParameterValue(Types.VARCHAR, code));
        return update( deleteByCode, params);
    }

    public int insertContestant(String firstName, String lastName, String code) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", new SqlParameterValue(Types.VARCHAR, firstName));
        params.addValue("lastName", new SqlParameterValue(Types.VARCHAR, lastName));
        params.addValue("code", new SqlParameterValue(Types.VARCHAR, code));

        return update( insertContestant, params);
    }

    public int updateContestant(String firstName, String lastName, String code) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("firstName", new SqlParameterValue(Types.VARCHAR, firstName));
        params.addValue("lastName", new SqlParameterValue(Types.VARCHAR, lastName));
        params.addValue("code", new SqlParameterValue(Types.VARCHAR, code));

        return update( updateContestantByCode, params);
    }

}
