package com.internousdev.lilac.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.CartInfoDAO;
import com.internousdev.lilac.dto.CartInfoDTO;
import com.internousdev.lilac.util.CommonUtility;
import com.opensymphony.xwork2.ActionSupport;

public class AddCartAction extends ActionSupport implements SessionAware{

	private int productId;
	private int price;
	private String productCount;

	private Map<String, Object> session;

	public String execute(){
		String result = ERROR;
		String userId = null;
		String tempUserId = null;

		if(!(session.containsKey("mCategoryDtoList"))){
			result = "timeout";
			return result;
		}

		//ログイン後、もしくはカート追加して再度CartAction実行した際cart.jspで
		//最初からエラーが表示されるので消しておく
		session.remove("checkListErrorMessageList");

		//loginIdとtempUserIdがあるかないか判断
		//なければtempUserIdを作成、セッションにput
		if(!(session.containsKey("tempUserId"))){
			CommonUtility commonUtility = new CommonUtility();
			session.put("tempUserId", commonUtility.getRamdomValue());
		}

		//未ログイン時は、セッションにあるloginId,tempUserIdをuserId,tempUserIdにセット
		//ログイン時は、セッションにあるloginIdをuserIdにセット
		if(Integer.parseInt(String.valueOf(session.get("logined")))==0){
			userId = String.valueOf(session.get("tempUserId"));
			tempUserId = String.valueOf(session.get("tempUserId"));
		}else{
			userId = String.valueOf(session.get("loginId"));
		}

		//splitの部分が謎!!  ([0]は配列の0番目を意味している)
		productCount = String.valueOf((productCount.split(" ,",0))[0]);

		//cart_infoテーブルにカート情報を入れる
		CartInfoDAO cartInfoDao = new CartInfoDAO();
		int count = cartInfoDao.regist(userId, tempUserId, productId, productCount, price);
		if(count > 0){

			result = SUCCESS;
		}

		//カート情報を入れた後、テーブルから格納されてた値をリストで取得して
		//セッションにput
		List<CartInfoDTO> cartInfoDtoList = new ArrayList<CartInfoDTO>();
		cartInfoDtoList = cartInfoDao.getCartInfoDtoList(userId);
		Iterator<CartInfoDTO>iterator = cartInfoDtoList.iterator();

		if(!(iterator.hasNext())){
			cartInfoDtoList = null;
		}
		session.put("cartInfoDtoList", cartInfoDtoList);

		//cart_infoテーブルでは合計金額はないので各商品（値段×個数）
		//の合わせた合計をセッションにput
		int totalPrice = Integer.parseInt(String.valueOf(cartInfoDao.getTotalPrice(userId)));
		session.put("totalPrice", totalPrice);
		return result;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
