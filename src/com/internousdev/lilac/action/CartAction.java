package com.internousdev.lilac.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.CartInfoDAO;
import com.internousdev.lilac.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class CartAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session;

	public String execute(){
		String result = ERROR;
		String userId = null;
		CartInfoDAO cartInfoDao = new CartInfoDAO();
		List<CartInfoDTO> cartInfoDtoList = new ArrayList<CartInfoDTO>();

		if(!(session.containsKey("mCategoryDtoList"))){
			result = "timeout";
			return result;
		}

		//ログイン後、もしくはカート追加して再度CartAction実行した際cart.jspで
		//最初からエラーが表示されるので消しておく
		session.remove("checkListErrorMessageList");

		//ログインしているかどうかでuserIdにいれるIdを判断
		if(Integer.parseInt(String.valueOf(session.get("logined")))==0){
			userId = String.valueOf(session.get("tempUserId"));
		}else{
			userId = String.valueOf(session.get("loginId"));
		}

		//cart_infoテーブルからカート情報をゲット、そしてセッションにput
		cartInfoDtoList = cartInfoDao.getCartInfoDtoList(userId);
		Iterator<CartInfoDTO> iterator = cartInfoDtoList.iterator();
		if(!(iterator.hasNext())){
			cartInfoDtoList = null;
		}
		session.put("cartInfoDtoList", cartInfoDtoList);

		//cart_infoテーブルでは合計金額はないので各商品（値段×個数）
		//の合わせた合計をセッションにput
		int totalPrice = Integer.parseInt(String.valueOf(cartInfoDao.getTotalPrice(userId)));
		session.put("totalPrice", totalPrice);
		result = SUCCESS;

		return result;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
