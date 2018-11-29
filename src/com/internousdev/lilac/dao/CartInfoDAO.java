package com.internousdev.lilac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.lilac.dto.CartInfoDTO;
import com.internousdev.lilac.util.DBConnector;

public class CartInfoDAO {


	//カート情報を取得
	public List<CartInfoDTO> getCartInfoDtoList(String loginId){
		com.internousdev.lilac.util.DBConnector dbConnector = new DBConnector();
		Connection con = dbConnector.getConnection();
		List<CartInfoDTO>cartInfoDtoList = new ArrayList<CartInfoDTO>();

		String sql = "select"
				+ " ci.id as id,"
				+ " ci.user_id as user_id,"
				+ " ci.temp_user_id as temp_user_id,"
				+ " ci.product_id as product_id,"
				+ " sum(ci.product_count) as product_count,"
				+ " pi.price as price,"
				+ " pi.regist_date as regist_date,"
				+ " pi.update_date as update_date,"
				+ " pi.product_name as product_name,"
				+ " pi.product_name_kana as product_name_kana,"
				+ " pi.product_description as product_description,"
				+ " pi.category_id as category_id,"
				+ " pi.image_file_path as image_file_path,"
				+ " pi.image_file_name as image_file_name,"
				+ " pi.release_date as release_date,"
				+ " pi.release_company as release_company,"
				+ " pi.status as status,"
				+ " (sum(ci.product_count) * pi.price) as subtotal"
				+ " FROM cart_info as ci"
				+ " LEFT JOIN product_info as pi"
				+ " ON ci.product_id = pi.product_id"
				+ " WHERE ci.user_id = ?"
				+ " group by product_id";
				try{
					PreparedStatement ps = con.prepareStatement(sql);
					//ここはなぜ system.out.println？(どうでもいいらしい)
					System.out.println("cartinfodao-getcartinfodtolist:"+loginId);
					ps.setString(1, loginId);
					ResultSet rs = ps.executeQuery();
					while(rs.next()){
						CartInfoDTO cartInfoDTO = new CartInfoDTO();
						cartInfoDTO.setId(rs.getInt("id"));
						cartInfoDTO.setUserId(rs.getString("user_id"));
						cartInfoDTO.setTempUserId(rs.getString("temp_user_id"));
						cartInfoDTO.setProductId(rs.getInt("product_id"));
						cartInfoDTO.setProductCount(rs.getInt("product_count"));
						cartInfoDTO.setPrice(rs.getInt("price"));
						cartInfoDTO.setRegistDate(rs.getDate("regist_date"));
						cartInfoDTO.setUpdateDate(rs.getDate("update_date"));
						cartInfoDTO.setProductName(rs.getString("product_name"));
						cartInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
						cartInfoDTO.setProductDescription(rs.getString("product_description"));
						cartInfoDTO.setCategoryId(rs.getInt("category_id"));
						cartInfoDTO.setImageFilePath(rs.getString("image_file_path"));
						cartInfoDTO.setImageFileName(rs.getString("image_file_name"));
						cartInfoDTO.setReleaseDate(rs.getDate("release_date"));
						cartInfoDTO.setReleaseCompany(rs.getString("release_company"));
						cartInfoDTO.setStatus(rs.getString("status"));
						cartInfoDTO.setSubtotal(rs.getInt("subtotal"));
						cartInfoDtoList.add(cartInfoDTO);
					}
				}catch(SQLException e){
					e.printStackTrace();
				}finally{
					try{
						con.close();
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
				return cartInfoDtoList;

	}

	//合計金額を出すために各商品の値段×個数の合計金額を取得
	public int getTotalPrice(String userId){
		int totalPrice = 0;
		DBConnector dbConnector = new DBConnector();
		Connection con = dbConnector.getConnection();
		String sql = "select sum(product_count * price) as total_price from cart_info where user_id=? ";
		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

			if(rs.next()){
				totalPrice = rs.getInt("total_price");
			}

		}catch (SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return totalPrice;
	}

	//カート情報を登録
	public int regist(String userId, String tempUserId, int productId, String productCount, int price){
		DBConnector dbConnector = new DBConnector();
		Connection con = dbConnector.getConnection();
		int count = 0;
		String sql = "insert into cart_info(user_id, temp_user_id, product_id, product_count, price, regist_date, update_date)"
				+ " values(?, ?, ?, ?, ?, now(), now())";

		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, tempUserId);
			ps.setInt(3, productId);
			ps.setString(4, productCount);
			ps.setInt(5, price);

			count = ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}

		return count;
	}

	//購入IDに紐づいているカート情報を削除
	public int delete(String productId, String userId){
		DBConnector dbConnector = new DBConnector();
		Connection con = dbConnector.getConnection();
		int count = 0;
		String sql ="delete from cart_info where product_id=? and user_id=?";

		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, productId);
			ps.setString(2, userId);
			count = ps.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return count;
	}

	//userIdに紐づいているカート情報を削除
	public int deleteAll(String userId){
		DBConnector dbConnector = new DBConnector();
		Connection con = dbConnector.getConnection();
		int count = 0;
		String sql ="delete from cart_info where user_id=?";

		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userId);

			count = ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return count;
	}

	//cart_infoテーブルでtempUserIdとloginIdを紐づける
	public int linkToLoginId(String tempUserId, String loginId){
		DBConnector dbConnector = new DBConnector();
		Connection con = dbConnector.getConnection();
		int count = 0;
		String sql = "update cart_info set user_id=?, temp_user_id = null where temp_user_id=?";

		try{
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, tempUserId);
			count = ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return count;
	}

}
