package com.spring.biz.addressBook.impl;

import java.util.List;

import com.spring.biz.addressBook.AddressBookDTO;


public interface AddressBookService {
    public void insert(AddressBookDTO addressbook);
    public void update(AddressBookDTO addressbook);
    public void delete(AddressBookDTO addressbook);
    public AddressBookDTO selectOne(AddressBookDTO addressbook);
    public AddressBookDTO exist(String email, String password);
    public List<AddressBookDTO> searchName(String name);
    public List<AddressBookDTO> searchTel(String tel);
    public List<AddressBookDTO> selectList();
}
