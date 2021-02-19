package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
//다른함수에서 사용하기 쉽도록 getConnection 앞에 static(정적인, 상수의) 값을 넣어준다.
//다른함수에서 사용하기 쉽도록 getConnection 앞에 static(정적인, 상수의) 값을 넣어준다.
//database util은 테이블과 연동을 위한 util임 . -데이터베이스에 접근을 해서 접근된 상태의 객체를 반환하는 유틸 따라서 LectureEvaluation을 쓴다.
	public static Connection getConnection() {
		try {
			String dbURL = "jdbc:mysql://localhost:3306/LectureEvaluation"; //Lectureevaluation이라는 다이나믹 웹프로젝트와 연동시킴.
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
