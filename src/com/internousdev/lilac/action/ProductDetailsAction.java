package com.internousdev.lilac.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.ProductInfoDAO;
import com.internousdev.lilac.dto.MCategoryDTO;
import com.internousdev.lilac.dto.ProductInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDetailsAction extends ActionSupport implements SessionAware{

	private int productId;

	private List<MCategoryDTO> mCategoryDtoList = new ArrayList<MCategoryDTO>();
	private List<ProductInfoDTO> productInfoDtoList = new ArrayList<ProductInfoDTO>();
	private Map<String, Object> session;

	public String execute() {

		String result = ERROR;

		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		ProductInfoDTO productInfoDTO = new ProductInfoDTO();

		if(!(session.containsKey("mCategoryDtoList"))){
			result = "timeout";
			return result;
		}

		//商品一覧(productList.jsp)の商品画像をクリックするとproductIdが渡され、そのクリックされた商品情報をproductInfoDTOに格納
		productInfoDTO = productInfoDAO.getProductInfo(productId);

		//sessionにクリックされた商品の情報を格納
		session.put("id", productInfoDTO.getId());
		session.put("productId", productInfoDTO.getProductId());
		session.put("productName", productInfoDTO.getProductName());
		session.put("productNameKana", productInfoDTO.getProductNameKana());
		session.put("imageFilePath", productInfoDTO.getImageFilePath());
		session.put("imageFileName", productInfoDTO.getImageFileName());
		session.put("price", productInfoDTO.getPrice());
		session.put("releaseCompany", productInfoDTO.getReleaseCompany());
		session.put("releaseDate", productInfoDTO.getReleaseDate());
		session.put("productDescription", productInfoDTO.getProductDescription());

		//商品購入の数（１〜５）をproductCountListに格納
		//Arrays.asList()：初期化時に値を追加する。ここでは、productCountListに1~5の値が追加された。
		List<Integer> productCountList = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5));
		session.put("productCountList", productCountList);

		//下部に関連した（選択された商品と同じカテゴリー）商品をランダムで３つ並べる。
		productInfoDtoList = productInfoDAO.getProductInfoListByCategoryId(productInfoDTO.getCategoryId(), productInfoDTO.getProductId(), 0, 3);
		Iterator<ProductInfoDTO> iterator = productInfoDtoList.iterator();

		//エラーすると参照型変数のproductCountListがメモリ上に残るので、参照を切ります。
		if(!(iterator.hasNext())) {
			productCountList = null;
		}

		if(!productInfoDtoList.isEmpty() || productCountList==null) {
			session.put("productInfoDtoList", productInfoDtoList);
			result = SUCCESS;
		}
		return result;
	}

	public List<MCategoryDTO> getmCategoryDtoList() {
		return mCategoryDtoList;
	}
	public void setmCategoryDtoList(List<MCategoryDTO> mCategoryDtoList) {
		this.mCategoryDtoList = mCategoryDtoList;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public List<ProductInfoDTO> getProductInfoDtoList() {
		return productInfoDtoList;
	}
	public void setProductInfoDtoList(List<ProductInfoDTO> productInfoDtoList) {
		this.productInfoDtoList = productInfoDtoList;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}