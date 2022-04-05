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
	public List<Customer> selectCustomerListByPage(int beginRow, int rowPerPage){			//Customer view 페이징 메서드
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
			cu.setCustomerZipcode(rs.getString("customerZipcode"));				//DB 결과값들을 cu에 저장
			cu.setCustomerPhone(rs.getString("customerPhone"));
			cu.setCustomerCountry(rs.getString("customerCountry"));
			cu.setCustomerNotes(rs.getString("customerNotes"));
			cu.setCustomerSID(rs.getInt("customerSID"));
			list.add(cu);														//cu를 list에 저장
			} 
		} catch (SQLException e1) {
		e1.printStackTrace();
	} finally {
		try {
			rs.close();
			stmt.close();														//db자원 해제
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
	
	public int selectTotalRow() {												//총 출력된 DB 값 출력 메서드
		int count=0;
		Connection conn;
		conn=DBUtil.getConnection();
		String sql="select count(*) cnt from customer_list";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt=conn.prepareStatement(sql);
			rs=stmt.executeQuery();												//결과값을 rs에 저장
			while(rs.next()) {													//rs의 다음값이 없을 때까지
				count=rs.getInt("cnt");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();													//db자원 해제
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		System.out.println(count + " count 디버깅");
		
		return count;
	}
	public List<Map<String,Object>> rewardsReport(int purchases, double amount){		//reward_Report  프로시저 호출 메서드
		Map<String,Object> map = null;
		Connection conn = null;
		CallableStatement stmt = null;											//프로시저 실행 타입
		ResultSet rs = null;
		
		List<Map<String,Object>> list = new ArrayList<>();						
		
		Integer count = 0;														//프로시저 결과 수를 저장할 변수
		conn=DBUtil.getConnection();
		try {
			stmt=conn.prepareCall("{call rewards_report(?,?,?)}");			//rewards_report
			stmt.setInt(1, purchases);
			
			stmt.setDouble(2, amount);
			stmt.registerOutParameter(3, Types.INTEGER);   // ==> 결과값을 받을 변수는 registerOutParameter()사용  3번째 변수형
			rs=stmt.executeQuery();

			while(rs.next()) {													//rs값들을 -> map에 저장 후 -> list에 저장
			map =new HashMap<String,Object>();
			map.put("customerId", rs.getInt("customer_id"));			
			map.put("storeId", rs.getInt("store_id"));							
			map.put("firstName", rs.getString("first_name"));
			map.put("lastName", rs.getString("last_name"));
			map.put("email", rs.getString("email"));							//프로시저 결과 값들을 map에 저장 
			map.put("addressId", rs.getInt("address_id"));						
			map.put("active", rs.getInt("active"));
			map.put("createDate", rs.getString("create_date"));
			map.put("lastUpdate", rs.getString("last_update"));
			
			list.add(map);														//저장한 map을 list에 저장
			
			}
			count=stmt.getInt(3);											//프로시저 out변수값 count에 저장
			map.put("count", count);
							//프로시저 3번째 out변수 값
			;		

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return list;
		
	}
	public static void main(String[] args) {
		CustomerDao cd = new CustomerDao();
		Map<String,Object> map = new HashMap<>();
		
		List<Map<String, Object>> list = cd.rewardsReport(1,1.0);
		for(Map<String, Object> i : list) {											//단위테스트
			System.out.println(i);
		
	}
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i).get("count"));
		}
}

}