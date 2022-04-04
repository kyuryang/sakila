package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {		//db중복되는 부분(db비밀번호 등이 수정될 때 한번에 바뀌게끔)  <--필드도없고 중복도 안됨 static으로 만들자
	public static Connection getConnection() {
		Connection conn=null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila","root","java1234");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
