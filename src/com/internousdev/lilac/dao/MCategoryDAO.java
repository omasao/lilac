package com.internousdev.lilac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.internousdev.lilac.dto.MCategoryDTO;
import com.internousdev.lilac.util.DBConnector;

public class MCategoryDAO {

	//全てのカテゴリー情報を取得するメソッド
	public List<MCategoryDTO> getMCategoryList(){

		DBConnector dbConnector = new DBConnector();
		Connection connection = dbConnector.getConnection();
		List<MCategoryDTO> mCategoryDtoList = new ArrayList<MCategoryDTO>();

		String sql = "select * from m_category";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();

			//mCategoryDtoListにカテゴリー情報をセットしている
			while(resultSet.next()) {
				MCategoryDTO mCategoryDTO = new MCategoryDTO();
				mCategoryDTO.setId(resultSet.getInt("id")); //ID
				mCategoryDTO.setCategoryId(resultSet.getInt("category_id")); //カテゴリーID
				mCategoryDTO.setCategoryName(resultSet.getString("category_name")); //カテゴリーの名前
				mCategoryDTO.setCategoryDescription(resultSet.getString("category_description")); //カテゴリーの詳細
				mCategoryDTO.setInsertDate(resultSet.getDate("insert_date")); //作成日
				mCategoryDTO.setUpdateDate(resultSet.getDate("update_date")); //変更日
				mCategoryDtoList.add(mCategoryDTO);
			}

			//iterator→コレクション内の要素に順番にアクセスする
			//hasNext→繰り返し処理において、次の要素がある場合にtrueを返す
			Iterator<MCategoryDTO> iterator = mCategoryDtoList.iterator();

			if(!(iterator.hasNext())) {
				mCategoryDtoList = null;
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
		return mCategoryDtoList;
	}
}
