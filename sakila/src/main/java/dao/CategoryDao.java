package dao;

import java.util.*;

import util.DBUtil;
import java.sql.*;
import vo.*;


public class CategoryDao {   // 자바 SET : rowId(중복데이터가 들어올 수 없다) , List , Map  3개 
	public List<Category> selectCategoryList(){
		List<Category> list = new ArrayList<Category>();
		Connection conn =null;
		PreparedStatement stmt=null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql="select category_id categoryId,name,last_update lastUpdate from category";
		try {
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();
			while(rs.next()) {
				Category c= new Category();
				c.setCategoryId(rs.getInt("categoryId"));
				c.setName(rs.getString("Name"));
				c.setLastUpdate(rs.getString("lastUpdate"));
				list.add(c);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
}
