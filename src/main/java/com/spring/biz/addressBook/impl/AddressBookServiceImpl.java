package com.spring.biz.addressBook.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.biz.addressBook.AddressBookDTO;

@Service("bookService")
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookDAO addressbookDAO;

    @Override
    public void insert(AddressBookDTO addressbook) {
        addressbookDAO.insert(addressbook);
    }

    @Override
    public void update(AddressBookDTO addressbook) {
        addressbookDAO.update(addressbook);
    }

    @Override
    public void delete(AddressBookDTO addressbook) {
        addressbookDAO.delete(addressbook);
    }

    @Override
    public AddressBookDTO selectOne(AddressBookDTO addressbook) {
        return addressbookDAO.selectOne(addressbook);
    }

    @Override
    public AddressBookDTO exist(String email, String password) {
        return addressbookDAO.exist(email, password);
    }

    @Override
    public List<AddressBookDTO> searchName(String name) {
        return addressbookDAO.searchName(name);
    }

    @Override
    public List<AddressBookDTO> searchTel(String tel) {
        return addressbookDAO.searchTel(tel);
    }

    @Override
    public List<AddressBookDTO> selectList() {
        return addressbookDAO.selectList();
    }
}
