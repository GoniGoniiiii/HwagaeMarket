package com.spring.view.common;

import java.sql.*;

public class JDBCUtil {
	public static Connection getConnection() throws Exception{
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/addressBookdb?useSSL=false",
					"root",
					"mysql"
					);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeConnection(PreparedStatement pstmt, Connection conn) {
		if(pstmt!=null) {
			try {
				if(!pstmt.isClosed()) pstmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				pstmt = null;
			}
		}
		
		if(conn!=null) {
			try {
				if(!conn.isClosed()) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				conn = null;
			}
		}
	}
	
	public static void closeConnection(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		
		if(rs!=null) {
			try {
				if(!rs.isClosed()) rs.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				rs = null;
			}
		}
		
		if(pstmt!=null) {
			try {
				if(!pstmt.isClosed()) pstmt.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				pstmt = null;
			}
		}
		
		if(conn!=null) {
			try {
				if(!conn.isClosed()) conn.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally {
				conn = null;
			}
		}
	}
}
