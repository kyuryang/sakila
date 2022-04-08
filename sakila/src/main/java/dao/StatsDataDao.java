package dao;
import java.sql.PreparedStatement;
import java.util.*;
import java.sql.*;
import util.*;
public class StatsDataDao {

	public List<Map<String, Object>> amountByCoustomer() {						//#제일 많이(금액) 빌려간 사람
	      List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      conn = DBUtil.getConnection();
	      String sql = "SELECT p.customer_id customerId,"
	            + "      concat(c.first_name,' ', c.last_name) name,"
	            + "      sum(p.amount) total"
	            + "      FROM payment p INNER JOIN customer c"
	            + "      ON p.customer_id = c.customer_id"
	            + "      GROUP BY p.customer_id"
	            + "       having sum(p.amount)>=180"
	            + "      ORDER BY SUM(p.amount) DESC";
	      try {
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> m = new HashMap<>();
	            m.put("customerId",rs.getInt("customerId"));
	            m.put("name",rs.getString("name"));
	            m.put("total",rs.getInt("total"));
	            list.add(m);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
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
	public List<Map<String,Object>> filmCountByRentalRate(){					//#렌탈 rate_rate 별 영화 5개
		      List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		      Connection conn = null;
		      PreparedStatement stmt = null;
		      ResultSet rs = null;
		      conn = DBUtil.getConnection();
		      String sql = "SELECT rental_rate rentalRate, COUNT(*) cnt \r\n"
		      		+ "FROM film GROUP BY rental_rate\r\n"
		      		+ "ORDER BY COUNT(*) desc";
		      try {
		         stmt = conn.prepareStatement(sql);
		         rs = stmt.executeQuery();
		         while(rs.next()) {
		            Map<String, Object> m = new HashMap<>();
		            m.put("rentalRate",rs.getInt("rentalRate"));
		            m.put("count",rs.getString("cnt"));
		            list.add(m);
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      } finally {
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

	public List<Map<String,Object>> filmCountByRating(){						//등급별 영화 갯수
	    List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      conn = DBUtil.getConnection();
	      String sql = "SELECT rating,COUNT(*) cnt FROM film GROUP BY rating ORDER BY COUNT(*)";
	      try {
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> m = new HashMap<>();
	            m.put("rating",rs.getString("rating"));
	            m.put("count",rs.getString("cnt"));
	            list.add(m);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
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
	public List<Map<String,Object>> filmCountByLanguage(){						//#language별 영화 개수
	    List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      conn = DBUtil.getConnection();
	      String sql = "SELECT name,COUNT(*) cnt FROM film f INNER JOIN language l \r\n"
	      		+ "ON f.language_id = l.language_id\r\n"
	      		+ "GROUP BY l.name;";
	      try {
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> m = new HashMap<>();
	            m.put("name",rs.getString("name"));
	            m.put("count",rs.getString("cnt"));
	            list.add(m);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
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
	public List<Map<String,Object>> filmCountByLength(){						//#length별 영화 개수 (구간,기준)을 정해서 
	    List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      conn = DBUtil.getConnection();
	      String sql = "SELECT t.length2, COUNT(*) cnt \r\n"
	      		+ "from(SELECT title, LENGTH, \r\n"
	      		+ "	case when LENGTH<=60 then 'less 60'					\r\n"
	      		+ "			when length BETWEEN 61 AND 120 then 'between60and119'\r\n"
	      		+ "				when length BETWEEN 121 AND 180 then 'between121and180'\r\n"
	      		+ "			ELSE 'more 180'\r\n"
	      		+ "		END LENGTH2\r\n"
	      		+ "	FROM film) t\r\n"
	      		+ "GROUP BY t.length2;";
	      try {
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> m = new HashMap<>();
	            m.put("length",rs.getString("length2"));
	            m.put("count",rs.getString("cnt"));
	            list.add(m);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
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
	public List<Map<String,Object>> rentalCountingByCustomer(){						//5. customer별 빌린 횟수
	    List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      conn = DBUtil.getConnection();
	      String sql ="SELECT t.customer_id customerId, COUNT(*) cnt FROM (SELECT customer_id FROM rental)t GROUP BY t.customer_id order by cnt desc limit 0,10";
	      try {
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> m = new HashMap<>();
	            m.put("customerId",rs.getString("customerId"));
	            m.put("count",rs.getString("cnt"));
	            list.add(m);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
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
	public List<Map<String,Object>> filmCountByActor(){					//#6. actor별 영화 출연 횟수	
	      List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      conn = DBUtil.getConnection();
	      String sql = "SELECT actor_id, COUNT(*) cnt FROM (SELECT actor_id FROM film_actor) f GROUP BY actor_id ORDER BY COUNT(*) DESC LIMIT 0,10";
	      try {
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> m = new HashMap<>();
	            m.put("actorId",rs.getInt("actor_id"));
	            m.put("count",rs.getString("cnt"));
	            list.add(m);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
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
	public List<Map<String,Object>> rentalCountByStaff(){					///#staff별 고객에게 rental 횟수
	      List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      conn = DBUtil.getConnection();
	      String sql = "SELECT staff_id,COUNT(*) cnt FROM rental r INNER JOIN store s ON r.staff_id = s.manager_staff_id GROUP BY staff_id";
	      try {
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> m = new HashMap<>();
	            m.put("staffId",rs.getInt("staff_id"));
	            m.put("count",rs.getString("cnt"));
	            list.add(m);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
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
	public List<Map<String,Object>> filmCountByStore(){					//##7 store별 영화 소지 개수
	      List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	      Connection conn = null;
	      PreparedStatement stmt = null;
	      ResultSet rs = null;
	      conn = DBUtil.getConnection();
	      String sql = "SELECT store_id, COUNT(*) cnt FROM (SELECT store_id FROM inventory) i GROUP BY store_id ORDER BY COUNT(*) DESC LIMIT 0,10";
	      try {
	         stmt = conn.prepareStatement(sql);
	         rs = stmt.executeQuery();
	         while(rs.next()) {
	            Map<String, Object> m = new HashMap<>();
	            m.put("storeId",rs.getInt("store_id"));
	            m.put("count",rs.getString("cnt"));
	            list.add(m);
	         }
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
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
	}

