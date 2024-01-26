/*
 * package com.spring.biz.user.impl;
 * 
 * import java.sql.*;
 * 
 * import org.springframework.stereotype.Repository;
 * 
 * import com.spring.biz.user.UserDTO; import com.spring.view.common.JDBCUtil;
 * 
 * @Repository("userDAO") public class UserDAO { private Connection conn = null;
 * private PreparedStatement pstmt = null; private ResultSet rs = null;
 * 
 * private final String GET_USER =
 * "select * from book where email=? and pass=?";
 * 
 * public UserDTO getUser(UserDTO userDTO) { UserDTO user = null; try { conn =
 * JDBCUtil.getConnection(); pstmt = conn.prepareStatement(GET_USER);
 * pstmt.setString(1, ); } } }
 */