package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
//�ٸ��Լ����� ����ϱ� ������ getConnection �տ� static(������, �����) ���� �־��ش�.
//�ٸ��Լ����� ����ϱ� ������ getConnection �տ� static(������, �����) ���� �־��ش�.
//database util�� ���̺�� ������ ���� util�� . -�����ͺ��̽��� ������ �ؼ� ���ٵ� ������ ��ü�� ��ȯ�ϴ� ��ƿ ���� LectureEvaluation�� ����.
	public static Connection getConnection() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/LectureEvaluation"; //Lectureevaluation�̶�� ���̳��� ��������Ʈ�� ������Ŵ.
			String dbID = "root";
			String dbPassword = "root";
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
