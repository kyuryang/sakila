package dao;

import java.util.*;

import util.DBUtil;

import java.sql.*;

public class RentalDao {
	public List<Map<String, Object>> selectRentalSearchList(int beginRow,int rowPerPage, int storeId, String customerName, String beginDate, String endDate) {		//검색과 페이징 기능 메서드

		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map=null;
		// db
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		try {
		String sql = "SELECT r.rental_id rentalId,"
				+ " r.inventory_id inventoryId,"
				+ " r.customer_id customerId,"
				+ "r.staff_id staffId,"
				+ "r.rental_date rentalDate,"
				+ "r.return_date returnDate,"
				+ "		 concat(c.first_name,' ',c.last_name) cName,\r\n" + "		 s.store_id storeId,\r\n"
				+ "		 i.film_id filmId,\r\n" + "		 f.title\r\n" + "FROM rental r INNER JOIN customer c \r\n"
				+ "ON r.customer_id = c.customer_id	INNER JOIN staff s \r\n"
				+ "	ON r.staff_id = s.staff_id INNER JOIN inventory i\r\n"
				+ "		ON r.inventory_id = i.inventory_id \r\n" 
				+ "			INNER JOIN film f\r\n"
				+ "			ON i.film_id = f.film_id\r\n" 
				
				+ "WHERE concat(c.first_name,' ',c.last_name) LIKE ? "
				+ "AND  r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d')\r\n"
				+ "AND STR_TO_DATE(?,'%Y-%m-%d')";			//s.store_id=?
		if(storeId==-1  ) {				//모든 항목 선택 x 3개
			sql += " ORDER BY rental_id LIMIT ?, ?";
			stmt = conn.prepareStatement(sql);
			  stmt.setString(1, "%"+customerName+"%");
			  stmt.setString(2, beginDate);
			  stmt.setString(3,endDate ) ;
			  stmt.setInt(4, beginRow);
			  stmt.setInt(5, rowPerPage);
		} else if (storeId !=-1  ) {			//가게만 O
			  sql+="and s.store_id=?  ORDER BY rental_id LIMIT ?, ?";
			  stmt = conn.prepareStatement(sql);	
			  stmt.setString(1, "%"+customerName+"%");
			  stmt.setString(2, beginDate);
			  stmt.setString(3,endDate ) ;
			  stmt.setInt(4, storeId);
			  stmt.setInt(5, beginRow);
			  stmt.setInt(6, rowPerPage);
			  
		} 
			rs = stmt.executeQuery();
			while (rs.next()) {
				 map= new HashMap<String, Object>();
				map.put("rentalId", rs.getInt("rentalId"));
				map.put("storeId",rs.getString("storeId"));
				map.put("rentalDate", rs.getString("rentalDate"));
				map.put("inventoryId", rs.getInt("inventoryId"));
				map.put("customerId", rs.getInt("customerId"));
				map.put("returnDate", rs.getString("returnDate"));
				map.put("staffId", rs.getInt("staffId"));
				map.put("cName", rs.getString("cName"));
				map.put("storeId", rs.getInt("storeId"));
				map.put("filmId", rs.getInt("filmId"));
				map.put("title", rs.getString("title"));

				list.add(map);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
		public int selectRentalTotalList(int storeId, String customerName, String beginDate, String endDate) {	//검색결과의 총 개수 반환
			int count=0;
			// db
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			conn = DBUtil.getConnection();
			try {
			String sql = "SELECT count(*) cnt  \r\n"
					+ "FROM rental r INNER JOIN customer c \r\n"
					+ "ON r.customer_id = c.customer_id	INNER JOIN staff s \r\n"
					+ "	ON r.staff_id = s.staff_id INNER JOIN inventory i\r\n"
					+ "		ON r.inventory_id = i.inventory_id \r\n" 
					+ "			INNER JOIN film f\r\n"
					+ "			ON i.film_id = f.film_id\r\n" 
					
					+ "WHERE concat(c.first_name,' ',c.last_name) LIKE ? "
					+ "AND  r.rental_date BETWEEN STR_TO_DATE(?,'%Y-%m-%d')\r\n"
					+ "AND STR_TO_DATE(?,'%Y-%m-%d')";			//s.store_id=?
			if(storeId==-1 && beginDate.equals("0") && endDate.equals("9999-12-12") ) {				//모든 항목 선택 x 3개
		
				stmt = conn.prepareStatement(sql);
				  stmt.setString(1, "%"+customerName+"%");
				  stmt.setString(2, beginDate);
				  stmt.setString(3,endDate ) ;
	
			} else if (storeId !=-1 && beginDate.equals("0") && endDate.equals("9999-12-12") ) {			//가게만 O
				  sql+="and s.store_id=?";
				  stmt = conn.prepareStatement(sql);	
				  stmt.setString(1, "%"+customerName+"%");
				  stmt.setString(2, beginDate);
				  stmt.setString(3,endDate ) ;
				  stmt.setInt(4, storeId);

				  
			} else if(storeId==-1 && !beginDate.equals("0") && endDate.equals("9999-12-12") ) {	//빌린년도 O
				  stmt = conn.prepareStatement(sql);
				  stmt.setString(1, "%"+customerName+"%");
				  stmt.setString(2, beginDate);
				  stmt.setString(3,endDate );

			}else if(storeId==-1 && beginDate.equals("0") && !endDate.equals("9999-12-12") ) {	//반환년도 O
				  stmt = conn.prepareStatement(sql);
				  stmt.setString(1, "%"+customerName+"%");
				  stmt.setString(2, beginDate);
				  stmt.setString(3,endDate );

			} else if (storeId !=-1 && !beginDate.equals("0") && endDate.equals("9999-12-12") ) {		//가게번호, 빌린년도
				sql+="and s.store_id=?";
				stmt = conn.prepareStatement(sql);
				  stmt.setString(1, "%"+customerName+"%");
				  stmt.setString(2, beginDate);
				  stmt.setString(3,endDate ) ;
				  stmt.setInt(4, storeId);

			} else if (storeId !=-1 && beginDate.equals("0") && !endDate.equals("9999-12-12") ) {		//가게번호, 반환년도
				sql+="and s.store_id=?";
				stmt = conn.prepareStatement(sql);
				  stmt.setString(1, "%"+customerName+"%");
				  stmt.setString(2, beginDate);
				  stmt.setString(3,endDate ) ;
				  stmt.setInt(4, storeId);

			} else if (storeId ==-1 && !beginDate.equals("0") && !endDate.equals("9999-12-12") ) {		//빌린년도,반환년도
				stmt = conn.prepareStatement(sql);
				  stmt.setString(1, "%"+customerName+"%");
				  stmt.setString(2, beginDate);
				  stmt.setString(3,endDate ) ;

			} else if (storeId !=-1 && !beginDate.equals("0") && !endDate.equals("9999-12-12") ) {		//3개 다 O
				sql+="and s.store_id=?";
				stmt = conn.prepareStatement(sql);
				  stmt.setString(1, "%"+customerName+"%");
				  stmt.setString(2, beginDate);
				  stmt.setString(3,endDate ) ;
				  stmt.setInt(4, storeId);

			} 
				rs = stmt.executeQuery();
				while (rs.next()) {
					count=rs.getInt("cnt");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return count;
		}
}
