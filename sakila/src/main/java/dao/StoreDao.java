package dao;

import java.util.*;

import util.DBUtil;

import java.sql.*;

public class StoreDao {
	//ArrayList는  List 인터페이스의 구현체중 하나이다.
	//HashMap은 인터페이스의 구현체 중 하나이다.
	public List<Map<String,Object>> selectStoreList(){				//다형성 ArrayList의 부모 -> List , Hashmap의 부모 -> map
		List<Map<String,Object>> list =new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt=null;
		ResultSet rs =null;
		try {
//		Class.forName("org.mariadb.jdbc.Driver");
//		conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/sakila","root","java1234");
			conn=DBUtil.getConnection();

		String sql = "SELECT "
				+ "	s1.store_id													storeId,"
				+ "	s1.manager_staff_id											staffId,"
				+ "	CONCAT(s2.first_name,' ', s2.last_name) 					staffNAME,"
				+ "	s1.address_id 												addressId,"
				+ "	CONCAT(a.address, IFNULL(a.address2,' '), district) 		staffAddress,"
				+ "	s1.last_update												lastUpdate"
				+ " FROM store s1 INNER JOIN staff s2"
				+ " INNER JOIN address a"
				+ " ON s1.manager_staff_id = s2.staff_id"
				+ " AND s1.address_id = a.address_id;";
		stmt =conn.prepareStatement(sql);
		rs = stmt.executeQuery();
		while(rs.next()) {
			Map<String,Object> map =new HashMap<>();		//<--다형성 
			map.put("storeId", rs.getInt("storeId"));
			map.put("staffId", rs.getInt("staffId"));
			map.put("staffName", rs.getString("staffName"));
			map.put("addressId", rs.getString("addressId"));
			map.put("staffAddress", rs.getString("staffAddress"));
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
		StoreDao sd = new StoreDao();
		List<Map<String,Object>> list = sd.selectStoreList();
		for(Map m : list) {
			System.out.print(m.get("storeId"));
			System.out.print(m.get("staffId"));
			System.out.print(m.get("staffName"));
			System.out.print(m.get("addressName"));				//단위테스트
			System.out.print(m.get("addressId"));
			System.out.println(m.get("lastUpdate"
					+ ""));
			
		}
	}
}




