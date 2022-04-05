package dao;
import vo.ActorInfo;
import vo.Customer;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDao {
	public List<Customer> selectCustomerListByPage(int beginRow, int rowPerPage){
		List<Customer> list =new ArrayList<>();
	Connection conn=null;	
	conn=DBUtil.getConnection();
	
	String sql = "select ID customerId, name customerName, address customerAddress, 'zip code' customerZipcode, phone customerPhone, country customerCountry, notes customerNotes,SID customerSID from customer_list order by ID limit ?,? ";
	PreparedStatement stmt =null;
	ResultSet rs=null;
	
	try {
		stmt=conn.prepareStatement(sql);
		stmt.setInt(1, beginRow);
		stmt.setInt(2, rowPerPage);
		rs=stmt.executeQuery();
		Customer cu=null;
		
		while(rs.next()) {
			cu=new Customer();
			cu.setCustomerId(rs.getInt("customerId"));
			cu.setCustomerName(rs.getString("customerName"));
			cu.setCustomerAddress(rs.getString("customerAddress"));
			cu.setCustomerZipcode(rs.getString("customerZipcode"));
			cu.setCustomerPhone(rs.getString("customerPhone"));
			cu.setCustomerCountry(rs.getString("customerCountry"));
			cu.setCustomerNotes(rs.getString("customerNotes"));
			cu.setCustomerSID(rs.getInt("customerSID"));
			list.add(cu);
			} 
		} catch (SQLException e1) {
		e1.printStackTrace();
	} finally {
		try {
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for(Customer c : list){
			System.out.println(c.getCustomerCountry() + "페이징 디버깅");				
		}
	} 

	
	return list;
	}
	
	public int selectTotalRow() {
		int count=0;
		Connection conn;
		conn=DBUtil.getConnection();
		String sql="select count(*) cnt from customer_list";
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
	public Map<String,Object> rewardsReport(int purchases, int amount){
		Map<String,Object> map = new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement stmt = null;											//프로시저 실행 타입
		ResultSet rs = null;
		
		List<Integer> list = new ArrayList<>();
		Integer count = 0;
		conn=DBUtil.getConnection();
		try {
			stmt=conn.prepareCall("{call rewards_report(?,?,?)}");			//film_not_in_stock 프로시저 호출
			stmt.setInt(1, purchases);
			stmt.setInt(2, amount);
			stmt.registerOutParameter(3, Types.INTEGER);   // ==> 결과값을 받을 변수는 registerOutParameter()사용  3번째 변수형
			rs=stmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1));    //rs.getInt("inventory_id
				
			}
			count=stmt.getInt(3);		//프로시저 3번째 out변수 값
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		map.put("list", list);
		map.put("count", count);
		
		
		return map;
		
	}
	public static void main(String[] args) {
		CustomerDao cd = new CustomerDao();
		Map<String,Object> map = new HashMap<>();
		map=cd.rewardsReport(1, 10);
		List<Integer> list =  (List<Integer>)map.get("list");
		int count = (Integer)map.get("count");
		System.out.println(count);
		for(int i : list) {
			System.out.println(i);
		
	}
}

}