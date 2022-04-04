package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import util.DBUtil;
import vo.ActorInfo;

public class ActorInfoDao {
		public ActorInfoDao() {}
	public List<ActorInfo> selectActorInfoListByPage(int beginRow, int rowPerPage){
		List<ActorInfo> list = new ArrayList<ActorInfo>();
		Connection conn=null;			
		conn =DBUtil.getConnection();
		String sql="select actor_id actorId, first_name firstName, last_name lastName,film_info filmInfo from actor_info order by actor_id limit ?,?";
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt=conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs=stmt.executeQuery();
			ActorInfo actI = null;
			while(rs.next()) {
				actI = new ActorInfo();
				actI.setActorId(rs.getInt("actorId"));
				actI.setFirstName(rs.getString("firstName"));
				actI.setLastName(rs.getString("lastName"));
				actI.setFilmInfo(rs.getString("filmInfo"));
				list.add(actI);
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
			for(ActorInfo a : list){
				System.out.println(a.getFirstName() + "페이징 디버깅");				
			}
		} 
		return list;
	}
	public int selectActorInfoTotalRow() {							//총 열 수
		int count=0;
		Connection conn;
		conn=DBUtil.getConnection();
		String sql="select count(*) cnt from actor_info";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();
			while(rs.next()) {
				count=rs.getInt("cnt");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		System.out.println(count + " count 디버깅");
		
		return count;
	}
}
