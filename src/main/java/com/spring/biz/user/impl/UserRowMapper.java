package com.spring.biz.user.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.spring.biz.user.UserDTO;


public class UserRowMapper implements RowMapper<UserDTO> {

    @Override
    public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
    	UserDTO user = new UserDTO();
    	user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPass(rs.getString("pass"));
        user.setComdept(rs.getString("comdept"));
        user.setBirth(rs.getString("birth"));
        user.setTel(rs.getString("tel"));
        user.setMemo(rs.getString("memo"));

        return user;
    }
}