package dao;
import vo.ActorInfo;
import vo.Customer;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

}

