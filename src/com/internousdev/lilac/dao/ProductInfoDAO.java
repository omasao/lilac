package com.internousdev.lilac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.lilac.dto.ProductInfoDTO;
import com.internousdev.lilac.util.DBConnector;

public class ProductInfoDAO {


//商品一覧」ボタンを押下するとこのメソッドが呼び出される。
	public List<ProductInfoDTO> getProductInfoList() {

		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();

		List<ProductInfoDTO> productInfoDtoList = new ArrayList<ProductInfoDTO>();

		//DBに格納されている商品情報を全て選択する。
		String sql = "select * from product_info";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();

				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setUpdateDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
				productInfoDtoList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDtoList;
	}

//商品一覧画面から任意の画像をクリックした場合。（ProductDetailsAction → productDetails.jsp)
	public ProductInfoDTO getProductInfo(int productId) {

		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();

		ProductInfoDTO productInfoDTO = new ProductInfoDTO();

		//クリックされた商品の商品情報のみを選択します。
		String sql = "select * from product_info where product_id=?";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, productId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setUpdateDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTO;
	}

//検索ワードを入力した場合（カテゴリー：全てのカテゴリー）
	public List<ProductInfoDTO> getProductInfoListAll(String[] keywordsList) {

		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();

		List<ProductInfoDTO> productInfoDtoList = new ArrayList<ProductInfoDTO>();

		//部分検索：keywordの値が'商品名'または'商品名かな'の一部と一致するか
		String sql = "select * from product_info where";

		boolean initializeFlag = true;

		for (String keyword : keywordsList) {
			if (initializeFlag) {
				//第一検索ワード（lile '%xxx%'：部分一致）
				sql += " (product_name like '%" + keyword + "%' or product_name_kana like '%" + keyword + "%')";
				initializeFlag = false;
			} else {
				//第二検索ワード以降（OR検索）
				sql += " or (product_name like '%" + keyword + "%' or product_name_kana like '%" + keyword + "%')";
			}
		}

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();

				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setUpdateDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
				productInfoDtoList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDtoList;
	}

//カテゴリーを選択、且つ、検索ワードを入力した場合
	public List<ProductInfoDTO> getProductInfoListByKeywords(String[] keywordsList, String categoryId) {

		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();

		List<ProductInfoDTO> productInfoDtoList = new ArrayList<ProductInfoDTO>();

		String sql = "select * from product_info where";

		boolean initializeFlag = true;
		for (String keyword : keywordsList) {
			if (initializeFlag) {
				//第一検索ワード
				sql += " category_id =" + categoryId + " and ((product_name like '%" + keyword + "%' or product_name_kana like '%" + keyword + "%')";
				initializeFlag = false;
			} else {
				//第二検索ワード以降(OR検索）
				sql += " or (product_name like '%" + keyword + "%' or product_name_kana like '%" + keyword + "%')";
			}
		}
		sql += ")";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();

				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setUpdateDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
				productInfoDtoList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDtoList;
	}

//商品詳細の下部に関連した商品３つ表示させる
	public List<ProductInfoDTO> getProductInfoListByCategoryId(int categoryId, int productId, int limitOffset,int limitRowCount) {

		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();

		List<ProductInfoDTO> productInfoDtoList = new ArrayList<ProductInfoDTO>();

		//同じカテゴリー and 選択された商品は除く and ランダムで３件選択します。（not in()：〜以外　　order by rand()：ランダムでデータを取得します。　　limit 0,3：0件飛ばして3件取得します。）
		String sql = "select * from product_info where category_id=? and product_id not in(?) order by rand() limit ?,?";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, categoryId);
			preparedStatement.setInt(2, productId);
			preparedStatement.setInt(3, limitOffset);
			preparedStatement.setInt(4, limitRowCount);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();

				productInfoDTO.setId(resultSet.getInt("id"));
				productInfoDTO.setProductId(resultSet.getInt("product_id"));
				productInfoDTO.setProductName(resultSet.getString("product_name"));
				productInfoDTO.setProductNameKana(resultSet.getString("product_name_kana"));
				productInfoDTO.setProductDescription(resultSet.getString("product_description"));
				productInfoDTO.setCategoryId(resultSet.getInt("category_id"));
				productInfoDTO.setPrice(resultSet.getInt("price"));
				productInfoDTO.setImageFilePath(resultSet.getString("image_file_path"));
				productInfoDTO.setImageFileName(resultSet.getString("image_file_name"));
				productInfoDTO.setReleaseDate(resultSet.getDate("release_date"));
				productInfoDTO.setReleaseCompany(resultSet.getString("release_company"));
				productInfoDTO.setStatus(resultSet.getInt("status"));
				productInfoDTO.setUpdateDate(resultSet.getDate("regist_date"));
				productInfoDTO.setUpdateDate(resultSet.getDate("update_date"));
				productInfoDtoList.add(productInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				connection.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDtoList;
	}
}