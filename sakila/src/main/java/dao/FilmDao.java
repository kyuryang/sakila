package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DBUtil;
import vo.ActorInfo;
import vo.FilmInfo;

public class FilmDao {			//필름 프로시저
	public Map<String,Object> filmStockCall(int filmId, int storeId){			//film_in_Stcok 프로시저 호출 메서드
		Map<String,Object> map = new HashMap<String,Object>();
		
		Connection conn = null;
		//쿼리를 실행하는 타입
		//PreparedStatement 
		//프로시저의 결과물을 저장하는 타입
		CallableStatement stmt = null;
		ResultSet rs =null;
		
		// select inventory_id ....
		List<Integer> list = new ArrayList<>();
		//select count (inventory_id) ....
		Integer count = 0; // 결과물을 받을 변수 (@x) //===> film_in_stock 의 결과물
		
		conn=DBUtil.getConnection();
		try {
			stmt=conn.prepareCall("{call film_in_stock(?,?,?)}");
			stmt.setInt(1, filmId);
			stmt.setInt(2, storeId);
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
	public Map<String,Object> filmNotStockCall(int filmId, int storeId){		//filmNotStockCall 프로시저 호출 메서드
		Map<String,Object> map = new HashMap<String,Object>();
		Connection conn = null;
		CallableStatement stmt = null;											//프로시저 실행 타입
		ResultSet rs = null;
		
		List<Integer> list = new ArrayList<>();
		Integer count = 0;
		conn=DBUtil.getConnection();
		try {
			stmt=conn.prepareCall("{call film_not_in_stock(?,?,?)}");			//film_not_in_stock 프로시저 호출
			stmt.setInt(1, filmId);
			stmt.setInt(2, storeId);
			stmt.registerOutParameter(3, Types.INTEGER);   // ==> 결과값을 받을 변수는 registerOutParameter()사용  3번째 변수형
			rs=stmt.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1));    //rs.getInt("inventory_id")
				
			}
			count=stmt.getInt(3);		//프로시저 3번째 out변수 값
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		map.put("list", list);
		map.put("count", count);
		
		
		return map;
		
	}
	
	public List<FilmInfo> selectNBSFilmList(int beginRow,int rowPerPage){		//nicer_but_slower_film_list view 조회 메서드
		List<FilmInfo> list = new ArrayList<>();
		Connection conn= null;
		PreparedStatement stmt =null;
		ResultSet rs =null;
		
		conn=DBUtil.getConnection();											//DBUtil에서 static메서드 호출
		String sql = "select FID, title,description, category, price, length,rating,actors from nicer_but_slower_film_list order by FID Limit ?,?";
		try {																	//배우 목록을 소문자로 출력해주는 리스트뷰 조회하는 쿼리문
			stmt=conn.prepareStatement(sql);
			stmt.setInt(1, beginRow);
			stmt.setInt(2, rowPerPage);
			rs=stmt.executeQuery();
			FilmInfo f = null;
			while(rs.next()) {
				f=new FilmInfo();
				f.setFID(rs.getInt("FID"));
				f.setTitle(rs.getString("title"));
				f.setDescription(rs.getString("description"));					//쿼리결과물 -> FIlmInfo객체 저장 후 -> list에 저장
				f.setCategory(rs.getString("category"));
				f.setPrice(rs.getDouble("price"));
				f.setLength(rs.getInt("length"));
				f.setRating(rs.getString("rating"));
				f.setActors(rs.getString("actors"));
				list.add(f);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
		try {
			conn.close();
			stmt.close();														//db자원해제
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
		return list;
	}
	public List<FilmInfo> selectFilmListByPage(int beginRow, int rowPerPage){	//film_list view 페이징,db연동 후 조회값 list에 저장
		List<FilmInfo> list =new ArrayList<FilmInfo>();
		FilmInfo fi = null;
		
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;														//DB 연동하여 
		conn=DBUtil.getConnection();
		
		String sql="SELECT FID, title, description,category,price,length,rating,actors FROM film_list order by FID limit ?,?";

		
		try {
			stmt=conn.prepareStatement(sql);									//sql문 입력후 실행
			stmt.setInt(1,beginRow);											//쿼리문 ?값 입력
			stmt.setInt(2,rowPerPage);
			rs=stmt.executeQuery();												//결과값 rs에 저장
			while(rs.next()) {
				fi=new FilmInfo();

				fi.setFID(rs.getInt("FID"));
				fi.setTitle(rs.getString("title"));
				fi.setDescription(rs.getString("description"));
				fi.setCategory(rs.getString("category"));						//db결과값을 fi객체필드에 저장 후 list에 저장
				fi.setPrice(rs.getDouble("price"));
				fi.setLength(rs.getInt("length"));
				fi.setRating(rs.getString("rating"));
				fi.setActors(rs.getString("actors"));
				list.add(fi);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	public int filmTotalRow() {													//film_list뷰 총데이터 수 반환
		int count=0;
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;														//DB 연동하여 
		conn=DBUtil.getConnection();
		
		String sql="SELECT count(*) cnt from film_list";						//view의 총 데이터 개수를 결과 값으로 가져오는 쿼리

		
		try {
			stmt=conn.prepareStatement(sql);									//sql문 입력후 실행
			rs=stmt.executeQuery();												//결과값 rs에 저장
			while(rs.next()) {													//rs의 다음 결과값이 없을 때 까지 실행
			count=rs.getInt("cnt");						
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	public List<Double> selectfilmPriceList(){
		List<Double> list = new ArrayList<Double>();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;														//DB 연동하여 
		conn=DBUtil.getConnection();
		
		String sql="select distinct price from film_list order by price";
		try {
			stmt=conn.prepareStatement(sql);									//sql문 입력후 실행
			rs=stmt.executeQuery();												//결과값 rs에 저장
			while(rs.next()) {													//rs의 다음 결과값이 없을 때 까지 실행
				list.add(rs.getDouble("price"));
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}
	public List<FilmInfo> selectFilmListSearch(int beginRow, int rowPerPage, String category, String rating, double price, int length, String title, String actor) {		
		List<FilmInfo> list = new ArrayList<FilmInfo>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql="";
		
		try {
			
			
			// 동적쿼리
		if(category.equals("")==true) {											//카테고리 X 				
			if(rating.equals("")==true) {										//등급 	O
			sql = "SELECT fid,title,description,category,price,length,rating,actors FROM film_list WHERE title LIKE ? AND actors LIKE ?";
			if(  price==-1 && length==-1) {										//price length 입력 x
				sql += " ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setInt(3, beginRow);
				stmt.setInt(4, rowPerPage);
			} else if(  price==-1 && length!=-1) { 								// length O 
				if(length == 0) {
					sql += " AND length<60 ORDER BY fid LIMIT ?, ?";
				} else if(length == 1) {
					sql += " AND length>=60 ORDER BY fid LIMIT ?, ?";
				}
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setInt(3, beginRow);
				stmt.setInt(4, rowPerPage);
			} else if(  price!=-1 && length==-1) { 								//price O
				sql += " AND price=? ORDER BY fid LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setDouble(3, price);
				stmt.setInt(4, beginRow);
				stmt.setInt(5, rowPerPage);
			} else if(  price!=-1 && length!=-1) { 								//price,length O
				if(length == 0) {
					sql += " AND price=? AND length<60 ORDER BY fid LIMIT ?, ?";
				} else if(length == 1) {
					sql += " AND price=? AND length>=60 ORDER BY fid LIMIT ?, ?";
				}
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setDouble(3, price);
				stmt.setInt(4, beginRow);
				stmt.setInt(5, rowPerPage);
			}   
			} else {																//등급 선택 O 카테고리 X
				sql = "SELECT fid,title,description,category,price,length,rating,actors FROM film_list WHERE rating like ? and title LIKE ? AND actors LIKE ?";
				if(  price==-1 && length==-1) {		//price length 입력 x
					sql += " ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+rating+"%");
					stmt.setString(2, "%"+title+"%");
					stmt.setString(3, "%"+actor+"%");
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
				} else if(  price==-1 && length!=-1) {							 // length O
					if(length == 0) {
						sql += " AND length<60 ORDER BY fid LIMIT ?, ?";
					} else if(length == 1) {
						sql += " AND length>=60 ORDER BY fid LIMIT ?, ?";
					}
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, rating);
					stmt.setString(2, "%"+title+"%");
					stmt.setString(3, "%"+actor+"%");
					stmt.setInt(4, beginRow);
					stmt.setInt(5, rowPerPage);
				} else if(  price!=-1 && length==-1) { 								//price O
					sql += " AND price=? ORDER BY fid LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, rating);
					stmt.setString(2, "%"+title+"%");
					stmt.setString(3, "%"+actor+"%");
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				} else if(  price!=-1 && length!=-1) { 							//price,length O
					if(length == 0) {
						sql += " AND price=? AND length<60 ORDER BY fid LIMIT ?, ?";
					} else if(length == 1) {
						sql += " AND price=? AND length>=60 ORDER BY fid LIMIT ?, ?";
					}
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, rating);
					stmt.setString(2, "%"+title+"%");
					stmt.setString(3, "%"+actor+"%");
					stmt.setDouble(4, price);
					stmt.setInt(5, beginRow);
					stmt.setInt(6, rowPerPage);
				}   
			}
			
		} else {																//카테고리 O
			 sql = "SELECT fid,title,description,category,price,length,rating,actors FROM film_list WHERE category LIKE ? AND title LIKE ? AND actors LIKE ?";
				if(rating.equals("")==true) {									//등급 X
					sql = "SELECT fid,title,description,category,price,length,rating,actors FROM film_list WHERE title LIKE ? AND actors LIKE ?";
					if(  price==-1 && length==-1) {								//price length 입력 x
						sql += " ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setInt(3, beginRow);
						stmt.setInt(4, rowPerPage);
					} else if(  price==-1 && length!=-1) {						 // length O
						if(length == 0) {
							sql += " AND length<60 ORDER BY fid LIMIT ?, ?";
						} else if(length == 1) {
							sql += " AND length>=60 ORDER BY fid LIMIT ?, ?";
						}
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setInt(3, beginRow);
						stmt.setInt(4, rowPerPage);
					} else if(  price!=-1 && length==-1) {						 //priceO
						sql += " AND price=? ORDER BY fid LIMIT ?, ?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setDouble(3, price);
						stmt.setInt(4, beginRow);
						stmt.setInt(5, rowPerPage);
					} else if(  price!=-1 && length!=-1) { 						//price,length O
						if(length == 0) {
							sql += " AND price=? AND length<60 ORDER BY fid LIMIT ?, ?";
						} else if(length == 1) {
							sql += " AND price=? AND length>=60 ORDER BY fid LIMIT ?, ?";
						}
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setDouble(3, price);
						stmt.setInt(4, beginRow);
						stmt.setInt(5, rowPerPage);
					}   
					} else {													//등급 선택 O 카테고리 O
						sql = "SELECT fid,title,description,category,price,length,rating,actors FROM film_list WHERE category like ? and rating like ? and title LIKE ? AND actors LIKE ?";
						if(  price==-1 && length==-1) {							//price length 입력 x
							sql += " ORDER BY fid LIMIT ?, ?";
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, category);
							stmt.setString(2, rating);
							stmt.setString(3, "%"+title+"%");
							stmt.setString(4, "%"+actor+"%");
							stmt.setInt(5, beginRow);
							stmt.setInt(6, rowPerPage);
						} else if(  price==-1 && length!=-1) { 					// length O
							if(length == 0) {
								sql += " AND length<60 ORDER BY fid LIMIT ?, ?";
							} else if(length == 1) {
								sql += " AND length>=60 ORDER BY fid LIMIT ?, ?";
							}
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, category);
							stmt.setString(2, rating);
							stmt.setString(3, "%"+title+"%");
							stmt.setString(4, "%"+actor+"%");
							stmt.setInt(5, beginRow);
							stmt.setInt(6, rowPerPage);
						} else if(  price!=-1 && length==-1) {					 //price O
							sql += " AND price=? ORDER BY fid LIMIT ?, ?";
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, category);
							stmt.setString(2, rating);
							stmt.setString(3, "%"+title+"%");
							stmt.setString(4, "%"+actor+"%");
							stmt.setDouble(5, price);
							stmt.setInt(6, beginRow);
							stmt.setInt(7, rowPerPage);
						} else if(  price!=-1 && length!=-1) { 					//price,length O
							if(length == 0) {
								sql += " AND price=? AND length<60 ORDER BY fid LIMIT ?, ?";
							} else if(length == 1) {
								sql += " AND price=? AND length>=60 ORDER BY fid LIMIT ?, ?";
							}
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, category);
							stmt.setString(2, rating);
							stmt.setString(3, "%"+title+"%");
							stmt.setString(4, "%"+actor+"%");
							stmt.setDouble(5, price);
							stmt.setInt(6, beginRow);
							stmt.setInt(7, rowPerPage);
						}   
					
					}
		}

            rs = stmt.executeQuery();
			while(rs.next()) {
				FilmInfo f = new FilmInfo();
				f.setFID(rs.getInt("fid"));
				f.setTitle(rs.getString("title"));
				f.setDescription(rs.getString("description"));
				f.setCategory(rs.getString("category"));
				f.setPrice(rs.getDouble("price"));
				f.setLength(rs.getInt("length"));
				f.setRating(rs.getString("rating"));
				f.setActors(rs.getString("actors"));
				list.add(f);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public int searchTotalRow(String category, String rating, double price, int length, String title, String actor) {   	//검색 후 결과의 총 데이터 수 반환 
		int count =0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn = DBUtil.getConnection();
		String sql="";
		
		try {	
			// 동적쿼리
		if(category.equals("")==true) {											//카테고리 X 				
			if(rating.equals("")==true) {										//등급 	O
			sql = "SELECT count(*) cnt FROM film_list WHERE title LIKE ? AND actors LIKE ?";
			if(  price==-1 && length==-1) {										//price length 입력 x
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");

			} else if(  price==-1 && length!=-1) { 								// length O 
				if(length == 0) {
					sql += " AND length<60 ";
				} else if(length == 1) {
					sql += " AND length>=60 ";
				}
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");

			} else if(  price!=-1 && length==-1) { 								//price O
				sql += " AND price=? ";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setDouble(3, price);

			} else if(  price!=-1 && length!=-1) { 								//price,length O
				if(length == 0) {
					sql += " AND price=? AND length<60";
				} else if(length == 1) {
					sql += " AND price=? AND length>=60";
				}
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+title+"%");
				stmt.setString(2, "%"+actor+"%");
				stmt.setDouble(3, price);

			}   
			} else {																//등급 선택 O 카테고리 X
				sql = "SELECT count(*) cnt FROM film_list WHERE rating like ? and title LIKE ? AND actors LIKE ?";
				if(  price==-1 && length==-1) {		//price length 입력 x
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+rating+"%");
					stmt.setString(2, "%"+title+"%");
					stmt.setString(3, "%"+actor+"%");

				} else if(  price==-1 && length!=-1) {							 // length O
					if(length == 0) {
						sql += " AND length<60";
					} else if(length == 1) {
						sql += " AND length>=60";
					}
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, rating);
					stmt.setString(2, "%"+title+"%");
					stmt.setString(3, "%"+actor+"%");

				} else if(  price!=-1 && length==-1) { 								//price O
					sql += " AND price=?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, rating);
					stmt.setString(2, "%"+title+"%");
					stmt.setString(3, "%"+actor+"%");
					stmt.setDouble(4, price);

				} else if(  price!=-1 && length!=-1) { 							//price,length O
					if(length == 0) {
						sql += " AND price=? AND length<60";
					} else if(length == 1) {
						sql += " AND price=? AND length>=60";
					}
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, rating);
					stmt.setString(2, "%"+title+"%");
					stmt.setString(3, "%"+actor+"%");
					stmt.setDouble(4, price);

				}   
			}
			
		} else {																//카테고리 O
			 sql = "SELECT count(*) cnt FROM film_list WHERE category LIKE ? AND title LIKE ? AND actors LIKE ?";
				if(rating.equals("")==true) {									//등급 X
					sql = "SELECT count(*) cnt FROM film_list WHERE title LIKE ? AND actors LIKE ?";
					if(  price==-1 && length==-1) {								//price length 입력 x
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");

					} else if(  price==-1 && length!=-1) {						 // length O
						if(length == 0) {
							sql += " AND length<60 ";
						} else if(length == 1) {
							sql += " AND length>=60 ";
						}
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");

					} else if(  price!=-1 && length==-1) {						 //priceO
						sql += " AND price=?";
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setDouble(3, price);

					} else if(  price!=-1 && length!=-1) { 						//price,length O
						if(length == 0) {
							sql += " AND price=? AND length<60";
						} else if(length == 1) {
							sql += " AND price=? AND length>=60";
						}
						stmt = conn.prepareStatement(sql);
						stmt.setString(1, "%"+title+"%");
						stmt.setString(2, "%"+actor+"%");
						stmt.setDouble(3, price);

					}   
					} else {													//등급 선택 O 카테고리 O
						sql = "SELECT count(*) cnt FROM film_list WHERE category like ? and rating like ? and title LIKE ? AND actors LIKE ?";
						if(  price==-1 && length==-1) {							//price length 입력 x
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, category);
							stmt.setString(2, rating);
							stmt.setString(3, "%"+title+"%");
							stmt.setString(4, "%"+actor+"%");

						} else if(  price==-1 && length!=-1) { 					// length O
							if(length == 0) {
								sql += " AND length<60 ";
							} else if(length == 1) {
								sql += " AND length>=60";
							}
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, category);
							stmt.setString(2, rating);
							stmt.setString(3, "%"+title+"%");
							stmt.setString(4, "%"+actor+"%");

						} else if(  price!=-1 && length==-1) {					 //price O
							sql += " AND price=? ";
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, category);
							stmt.setString(2, rating);
							stmt.setString(3, "%"+title+"%");
							stmt.setString(4, "%"+actor+"%");
							stmt.setDouble(5, price);

						} else if(  price!=-1 && length!=-1) { 					//price,length O
							if(length == 0) {
								sql += " AND price=? AND length<60 ";
							} else if(length == 1) {
								sql += " AND price=? AND length>=60 ";
							}
							stmt = conn.prepareStatement(sql);
							stmt.setString(1, category);
							stmt.setString(2, rating);
							stmt.setString(3, "%"+title+"%");
							stmt.setString(4, "%"+actor+"%");
							stmt.setDouble(5, price);
	
						}   
					
					}
		}
		rs = stmt.executeQuery();					//if문을 통해 각 검색된 조건마다의 데이터 개수를 결과로 가져와서
		while(rs.next()) {
			count=rs.getInt("cnt");					//count에 저장
		}
	} catch(SQLException e) {
		e.printStackTrace();
	}
		
		
		return count;
	}
	
	public static void main(String[] args) {
		FilmDao fd = new FilmDao();
		int filmId =100;
		int storeId=1;
		Map<String,Object> map1 = fd.filmStockCall(filmId, storeId);
		List<Integer> list1 = (List<Integer>)map1.get("list");
		int count1 = (Integer)map1.get("count");
		
		System.out.println(count1);
		for(int i : list1) {
			System.out.println(i);												//단위 테스트
		}
		Map<String,Object> map2 = fd.filmNotStockCall(filmId, storeId);
		List<Integer> list2 =  (List<Integer>)map2.get("list");
		int count2 = (Integer)map2.get("count");
		System.out.println(count2);
		for(int i : list2) {
			System.out.println(i);
		}
		List<FilmInfo> pageList = new ArrayList<>();
		pageList=fd.selectFilmListByPage(0, 10);							//selectFilmInfo 단위테스트
		for(FilmInfo f : pageList) {
			System.out.println(f);
		}
		System.out.println(fd.filmTotalRow());								//filmTotalRow 단위테스트
		
		List<FilmInfo> searchList = new ArrayList<>();
		searchList = fd.selectFilmListSearch(0,10,"","a", 0.99, 1,"","");
		for(FilmInfo f : searchList) {
			System.out.println(f);
			
		}
		
	}
}
