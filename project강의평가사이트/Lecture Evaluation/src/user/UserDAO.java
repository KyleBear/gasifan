package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DatabaseUtil;

public class UserDAO {

	public int login(String userID, String userPassword) {
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword))
					return 1; // 로그인 성공
				else
					return 0; // 비밀번호 틀림
			}
			return -1; // 아이디 없음
		} catch (Exception e) {
			e.printStackTrace();
		
		} finally {
		try	{if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
		try	{if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
		try	{if(rs != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
		}
		return -2; 
		}// 데이터베이스 오류
	
	public int join(UserDTO user) {//user의 객체를 받음.
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, false)";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserEmail());
			pstmt.setString(4, user.getUserEmailHash());
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try	{if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
			try	{if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
			try	{if(rs != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
			}
		return -1; // 회원가입 실패
	}
	
	public String getUserEmail(String userID) {
		String SQL = "SELECT userEmail FROM USER WHERE userID = ?"; //이메일 속성의 값을 가져옴. User ID 에서
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1); // 이메일 주소 반환
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		try	{if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
		try	{if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
		try	{if(rs != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
		}
		return null;
	}
		
	
	
	public boolean getUserEmailChecked(String userID) {
		String SQL = "SELECT userEmail FROM USER WHERE userID = ?"; //이메일 속성의 값을 가져옴. User ID 에서
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection();
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getBoolean(1); // 이메일 주소 반환
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		try	{if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
		try	{if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
		try	{if(rs != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
		}
		return false;
	}
	

	public boolean setUserEmailChecked(String userID) {
		String SQL = "UPDATE USER SET userEmailChecked = true WHERE userID = ?"; //이메일 속성의 값을 가져옴. User ID 에서
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtil.getConnection(); //db연결
			pstmt = conn.prepareStatement(SQL); //sql 문장연결
			pstmt.setString(1, userID);
			pstmt.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		try	{if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
		try	{if(conn != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
		try	{if(rs != null) conn.close(); } catch (Exception e) {e.printStackTrace();}
		}
		return false;
	}
}