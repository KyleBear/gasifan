package bbs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
//import�κп��� ��������
public class BbsDAO {
	// dao : �����ͺ��̽� ���� ��ü�� ����
		private Connection conn; // connection:db�������ϰ� ���ִ� ��ü
		//private PreparedStatement pstmt;

		private ResultSet rs;
		// mysql ó���κ�
		public BbsDAO() {
			// �����ڸ� ������ش�.
			try {
				String dbURL = "jdbc:mysql://localhost:3306/BBS?&serverTimezone=UTC";
				String dbID = "root";
				String dbPassword = "root";
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//������ �ð��� �������� �Լ�
		public String getDate() { 
			String SQL = "SELECT NOW()";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					return rs.getString(1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ""; //데베 오류
		}

		//bbsID 게시물 번
			public int getNext() { 
				String SQL = "SELECT bbsID FROM BBS ORDER BY bbsID DESC";
				try {
					PreparedStatement pstmt = conn.prepareStatement(SQL);
					rs = pstmt.executeQuery();
					if(rs.next()) {
						return rs.getInt(1) + 1;
					}
					return 1;//ù ��° �Խù��� ���
				} catch (Exception e) {
					e.printStackTrace();
				}
				return -1; //�����ͺ��̽� ����
			}

			//������ ���� �ۼ��ϴ� �Լ� write
			public int write(String bbsTitle, String userID, String bbsContent) { 
				String SQL = "INSERT INTO BBS VALUES(?, ?, ?, ?, ?, ?)";
				try {
					PreparedStatement pstmt = conn.prepareStatement(SQL);
					pstmt.setInt(1, getNext());
					pstmt.setString(2, bbsTitle);
					pstmt.setString(3, userID);
					pstmt.setString(4, getDate());
					pstmt.setString(5, bbsContent);
					pstmt.setInt(6,1);
//available�� ������ 1 �� ����.
					return pstmt.executeUpdate();
//���� ����� 0 �̻��� ����� ��ȯ �׷��� ���� �� -1 �� ��ȯ
				} catch (Exception e) {
					e.printStackTrace();
				}
				return -1; //�����ͺ��̽� ����
			}
			public ArrayList<Bbs> getList(int pageNumber){ 
				// 특정한 숫자보다 작고 삭제가 되지않아서 available 이 1인 글만 가져오고 위에서 10개의 글만 가져오고 글 번호를 내림차순 하는 쿼리문
				
				String SQL = "SELECT * FROM BBS WHERE bbsID < ? and bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
				// bbs 클래스에서 나오는 인스턴스를 보관하는 리스트를 하나 만듬. 
				
				ArrayList<Bbs> list = new ArrayList<Bbs>();
				try {
					PreparedStatement pstmt = conn.prepareStatement(SQL);
					pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
					rs = pstmt.executeQuery();
					while (rs.next()) {

						Bbs bbs = new Bbs();
						bbs.setBbsID(rs.getInt(1));
						bbs.setBbsTitle(rs.getString(2));
						bbs.setUserID(rs.getString(3));
						bbs.setBbsDate(rs.getString(4));
						bbs.setBbsContent(rs.getString(5));
						bbs.setBbsAvailable(rs.getInt(6));
						list.add(bbs);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				return list; 
			}
			//10 개 밖에 없다면 다음페이지가 없다는 걸 알려줌. 페이지 처리를 위해 존재하는 함수.
		public boolean nextPage(int pageNumber) {
			String SQL = "SELECT * FROM BBS WHERE bbsID < ? and bbsAvailable = 1 ORDER BY bbsID DESC LIMIT 10";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, getNext() - (pageNumber -1) * 10);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				}
			return false; 		
		}
		public Bbs getBbs(int bbsID) {
			String SQL = "SELECT * FROM BBS WHERE bbsID = ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(SQL);
				pstmt.setInt(1, bbsID);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					Bbs bbs = new Bbs();
					bbs.setBbsID(rs.getInt(1));
					bbs.setBbsTitle(rs.getString(2));
					bbs.setUserID(rs.getString(3));
					bbs.setBbsDate(rs.getString(4));
					bbs.setBbsContent(rs.getString(5));
					bbs.setBbsAvailable(rs.getInt(6));
					return bbs;
				}// �۹�ȣ�� bbsID�� �ο��ϰ� ���� �������� �Լ�
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
			}
		//���� �Լ�

		public int update(int bbsID, String bbsTitle, String bbsContent) {

				String SQL = "UPDATE BBS SET bbsTitle = ?, bbsContent = ?, WHERE bbsID = ?";

				try {

					PreparedStatement pstmt = conn.prepareStatement(SQL);

					pstmt.setString(1, bbsTitle);

					pstmt.setString(2, bbsContent);

					pstmt.setInt(3, bbsID);

					return pstmt.executeUpdate();



				} catch (Exception e) {

					e.printStackTrace();

				}

				return -1; // �����ͺ��̽� ����

			}

		//���� �Լ�

		public int delete(int bbsID) {

			String SQL = "UPDATE BBS SET bbsAvailable = 0 WHERE bbsID = ?";

			try {

				PreparedStatement pstmt = conn.prepareStatement(SQL);   

				pstmt.setInt(1, bbsID);

				return pstmt.executeUpdate();



			} catch (Exception e) {

				e.printStackTrace();

			}

			return -1; // �����ͺ��̽� ����

		}

		}

