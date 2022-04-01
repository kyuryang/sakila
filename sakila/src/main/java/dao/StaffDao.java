package dao;

import java.util.*;
import java.sql.*;

public class StaffDao {
	//ArrayList는  List 인터페이스의 구현체중 하나이다.
	//HashMap은 인터페이스의 구현체 중 하나이다.
	public List<Map<String,Object>> selectStaffList(){				//다형성 ArrayList의 부모 -> List , Hashmap의 부모 -> map
		List<Map<String,Object>> list =new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt=null;
		ResultSet rs =null;
		try {
		Class.forName("org.mariadb.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila","root","java1234");

		String sql = "SELECT\r\n"
				+ "		s1.staff_id 											staffId,"
				+ "		CONCAT(s1.first_name,' ',s1.last_name) 					staffName,"
				+ "		s1.address_id 											addressId,"
				+ "		CONCAT(a.address, IFNULL(a.address2,' '), district) 	staffAddress,"
				+ "		s1.picture 												picture,"
				+ "		s1.email 												email,"
				+ "		s1.store_id 											storeId,"
				+ "		s1.active 												active,"
				+ "		s1.username 											userName,"
				+ "		s1.password 											password,"
				+ "		s1.last_update											lastUpdate"
				+ "		FROM staff s1 INNER JOIN store s2"
				+ "		INNER JOIN address a"
				+ "		ON s1.store_id = s2.store_id"
				+ "		AND s1.address_id = a.address_id;";
		stmt =conn.prepareStatement(sql);
		rs = stmt.executeQuery();
		while(rs.next()) {
			Map<String,Object> map =new HashMap<>();		//<--다형성 
			map.put("staffId", rs.getInt("staffId"));
			map.put("staffName", rs.getString("staffName"));
			map.put("addressId", rs.getInt("addressId"));
			map.put("staffAddress", rs.getString("staffAddress"));
			map.put("picture", rs.getString("picture"));
			map.put("email", rs.getString("email"));
			map.put("storeId", rs.getInt("storeId"));
			map.put("active", rs.getInt("active"));
			map.put("userName", rs.getString("userName"));
			map.put("password", rs.getString("password"));
			map.put("lastUpdate", rs.getString("lastUpdate"));
			list.add(map);
		}
	} catch (Exception e) {				//<--다형성--classNotFoundException , SQLException 두개의 예외를 부모타입 Exception으로 처리 
		e.printStackTrace();
		System.out.println("Exception 발생");
	} finally {
			//db자원 해제 -try절에서 예외가 발생하면 자원해지 못한상태에서 코드가 종료된다. finally절이 필요
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	//selectStoreList() 테스트
	public static void main(String[] args) {
		StaffDao sd = new StaffDao();
		List<Map<String,Object>> list = sd.selectStaffList();
		for(Map m : list) {
			System.out.print(m.get("staffId"));
			System.out.print(m.get("staffName"));
			System.out.print(m.get("addressId"));				//단위테스트
			System.out.print(m.get("staffAddress"));
			System.out.print(m.get("picture"));
			System.out.print(m.get("email"));
			System.out.print(m.get("storeId"));
			System.out.print(m.get("active"));
			System.out.print(m.get("userName"));
			System.out.print(m.get("password"));
			System.out.print(m.get("lastUpdate"));
			
		}
	}
}
