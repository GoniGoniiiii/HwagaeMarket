package com.spring.biz.addressBook.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.biz.addressBook.AddressBookDTO;

@Repository
public class AddressBookDAOSpring {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String INSERT_BOOK = "insert into book(name, email, pass, comdept, birth, tel, memo) values(?,?,?,?,?,?,?)";
	
	private final String UPDATE_BOOK = "update book set name=?, email=?, tel=?, comdept=?, meomo=? where id=?";
	
	private final String DELETE_BOOK = "delete from book where id=?";
	
	private final String SELECT_LIST = "select id, name, email, comdept, birth, tel, memo from book order by id";
	
	private final String SELECT_ONE = "select id, name, email, pass, comdept, birth, tel, memo from book where id=?";
	
	private final String EXIST = "select name,email from book where email=? and pass=?";
	
	private final String SEARCH_NAME = "select id, name, email, comdept, birth, tel, memo from book where name like ?";
	
	private final String SEARCH_TEL = "select id, name, email, comdept, birth, tel, memo from book where tel like ?";
	
	public void insert(AddressBookDTO addressBook) {
		jdbcTemplate.update(INSERT_BOOK, addressBook.getName(), addressBook.getEmail(), addressBook.getPass(),
				addressBook.getComdept(),addressBook.getBirth(),addressBook.getTel(),addressBook.getMemo());
	}
	
	public void update(AddressBookDTO addressBook) {
		jdbcTemplate.update(UPDATE_BOOK,addressBook.getName(),addressBook.getEmail(),addressBook.getTel(),
				addressBook.getComdept(),addressBook.getMemo(),addressBook.getId());
	}
	
	public void delete(AddressBookDTO addressBook) {
		jdbcTemplate.update(DELETE_BOOK,addressBook.getId());
	}
	
	public AddressBookDTO selectOne(AddressBookDTO addressBook) {
		Object[] args = {addressBook.getId()};
		return jdbcTemplate.queryForObject(SELECT_ONE, args, new AddressBookRowMapper());
	}
	
	public List<AddressBookDTO> selectList(){
		return jdbcTemplate.query(SELECT_LIST, new AddressBookRowMapper());
	}
	
	public List<AddressBookDTO> searchName(String name){
		Object[] args = {"%"+name+"%"};
		return jdbcTemplate.query(SEARCH_NAME,args,new AddressBookRowMapper());
	}
	
	public List<AddressBookDTO> searchTel(String tel){
		Object[] args = {"%"+tel+"%"};
		return jdbcTemplate.query(SEARCH_TEL,args,new AddressBookRowMapper());
	}
}
