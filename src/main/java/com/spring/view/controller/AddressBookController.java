package com.spring.view.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.spring.biz.addressBook.AddressBookDTO;
import com.spring.biz.addressBook.impl.AddressBookDAO;
import com.spring.biz.addressBook.impl.AddressBookService;

@Controller
@SessionAttributes("member")
public class AddressBookController {
   
   // login.do, logout.do, insert, delete, update, 
   //selectList, selectOne, searchName, searchTel
   
   @Autowired
   private AddressBookService bookService;
   
   @RequestMapping("/login.do")
   public String login(String email, String pass, HttpSession session) throws Exception {
      AddressBookDAO bookDAO = new AddressBookDAO();
      if(bookDAO.exist(email, pass)!=null) {
         session.setAttribute("memberI", bookDAO.exist(email, pass));
         System.out.println("세션 속성: " + session.getAttribute("memberI"));
         return "redirect:memberList.do";
      }else {
         throw new Exception("으에?");
      }
   }
   
   @RequestMapping("/logout.do")
   public String logout(HttpSession session) {
      session.invalidate();
      System.out.println("로그아웃 했다");
      return "views/LoginForm";
   }
   
   @RequestMapping("/delete.do")
   public String deleteMember(AddressBookDTO dto) {
      bookService.delete(dto);
      return "redirect:memberList.do";
   }
   
   @RequestMapping("/memberList.do")
   public String getMemberList(AddressBookDAO bookDAO, Model model) {
      List<AddressBookDTO> bookList = bookDAO.selectList();
      model.addAttribute("memberList",bookList);
      return "views/addressBook/MemberList";
   }
   
   
   @RequestMapping("/member.do")
   public String getMember(AddressBookDTO bookDTO, Model model) {
      model.addAttribute("user",bookService.selectOne(bookDTO));
      return "views/addressBook/MemberUpdateForm";
   }
   
   @RequestMapping("/insert.do")
   public String insertMember(AddressBookDTO bookDTO) {
      if(bookDTO.getName()==null||bookDTO.getEmail()==null||bookDTO.getPass()==null) {
         return "views/addressBook/MemberForm";
      }
      else {
         bookService.insert(bookDTO);
         return "redirect:login.do";
      }
   }
   
   @RequestMapping("/update.do")
   public String updateMember(AddressBookDTO bookDTO) {
      bookService.update(bookDTO);
      return "redirect:memberList.do";
   }
   
   @RequestMapping("/search.do")
   public String searchMember(@RequestParam String searchType, @RequestParam String searchKey, Model model) {
       List<AddressBookDTO> searchResult = null;

       if ("name".equals(searchType)) {
           searchResult = bookService.searchName(searchKey);
       } else if ("tel".equals(searchType)) {
           searchResult = bookService.searchTel(searchKey);
       }

       model.addAttribute("searchResult", searchResult);
       return "views/addressBook/Result";
   }
   
   @RequestMapping("/loginform.do")
   public String loginform(HttpSession session) {
      System.out.println("로그인부터 하세요");
      return "views/LoginForm";
   }
   
}