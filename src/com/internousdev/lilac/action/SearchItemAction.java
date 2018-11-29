package com.internousdev.lilac.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.ProductInfoDAO;
import com.internousdev.lilac.dto.ProductInfoDTO;
import com.internousdev.lilac.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

//success→productList.jsp
//error→productList.jsp
public class SearchItemAction extends ActionSupport implements SessionAware{

	private String categoryId;///カテゴリーID
	private String keywords;//検索キーワード
	private List<ProductInfoDTO> productInfoDtoList = new ArrayList<ProductInfoDTO>();//商品のリスト
	private List<String> keywordsErrorMessageList = null;//キーワードの文字エラーリスト
	private Map<String, Object> session;

	public String execute() {

		if(!(session.containsKey("mCategoryDtoList"))){
			String result = "timeout";
			return result;
		}

		String result = ERROR;

		//対象の文字列.replace(置換される文字列, 置換する文字列)
		//→全角スペースを半角スペースに置き換える
		//→2つ以上の空白を1つの空白に置き換える
		//trim()で前後の空白を削除
		if (StringUtils.isBlank(keywords)){
			keywords = "";

		}else{
			keywords = keywords.replaceAll("　", " ").replaceAll("\\s{2,}", " ").trim();
		}

		//keywordsの文字チェック
		if(!(keywords.equals(""))){
			InputChecker inputChecker = new InputChecker();
			keywordsErrorMessageList = new ArrayList<String>();
			keywordsErrorMessageList = inputChecker.doCheck("検索ワード", keywords, 0, 16, true, true, true, true, false, true, false, true, true);
			Iterator<String>iterator = keywordsErrorMessageList.iterator();

			if(!(iterator.hasNext())){
				keywordsErrorMessageList = null;

			} else {
				return SUCCESS;
			}
		}

		ProductInfoDAO productInfoDAO = new ProductInfoDAO();

		//カテゴリーIDと検索ワードによる商品リストの生成
		//String型変数名.split("区切り文字", 分割後の要素数)
		//→半角スペースでキーワードを区切る
		switch (categoryId) {
			case "1":
				productInfoDtoList = productInfoDAO.getProductInfoListAll(keywords.split(" "));
				result = SUCCESS;
				break;

			default:
				productInfoDtoList = productInfoDAO.getProductInfoListByKeywords(keywords.split(" "), categoryId);
				result = SUCCESS;
				break;
		}

		//iterator→コレクション内の要素に順番にアクセスする
		//hasNext→繰り返し処理において、次の要素がある場合にtrueを返す
		Iterator<ProductInfoDTO> iterator = productInfoDtoList.iterator();

		if(!(iterator.hasNext())) {
			productInfoDtoList = null;
		}
			return result;
	}

	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
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
	public List<String> getKeywordsErrorMessageList() {
		return keywordsErrorMessageList;
	}
	public void setKeywordsErrorMessageList(List<String> keywordsErrorMessageList) {
		this.keywordsErrorMessageList = keywordsErrorMessageList;
	}
}