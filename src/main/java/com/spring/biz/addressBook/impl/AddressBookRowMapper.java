package com.spring.biz.addressBook.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.spring.biz.addressBook.AddressBookDTO;

public class AddressBookRowMapper implements RowMapper<AddressBookDTO> {

    @Override
    public AddressBookDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        AddressBookDTO addressbook = new AddressBookDTO();
        addressbook.setId(rs.getInt("id"));
        addressbook.setName(rs.getString("name"));
        addressbook.setEmail(rs.getString("email"));
        addressbook.setPass(rs.getString("pass"));
        addressbook.setComdept(rs.getString("comdept"));
        addressbook.setBirth(rs.getString("birth"));
        addressbook.setTel(rs.getString("tel"));
        addressbook.setMemo(rs.getString("memo"));

        return addressbook;
    }
}