package com.internousdev.lilac.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.ProductInfoDAO;
import com.internousdev.lilac.dto.ProductInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class ProductListAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session;

	private List<ProductInfoDTO> productInfoDtoList = new ArrayList<ProductInfoDTO>();

	//private Map<String, Object> session;

	public String execute() {
		String result = ERROR;

		if(!(session.containsKey("mCategoryDtoList"))){
			result = "timeout";
			return result;
		}

		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		//商品の商品情報を全て取得します。
		productInfoDtoList = productInfoDAO.getProductInfoList();

		result = SUCCESS;
		return result;
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
