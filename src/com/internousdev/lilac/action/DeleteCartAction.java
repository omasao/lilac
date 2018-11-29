package com.internousdev.lilac.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.CartInfoDAO;
import com.internousdev.lilac.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteCartAction extends ActionSupport implements SessionAware{
	private Collection<String> checkList;

	private Map<String, Object> session;

	public String execute() {
		String result = ERROR;
		CartInfoDAO cartInfoDAO = new CartInfoDAO();
		int count = 0;
		List<String> checkListErrorMessageList = new ArrayList<String>();

		if(!(session.containsKey("mCategoryDtoList"))){
			result = "timeout";
			return result;
		}

		String userId = null;
		if(session.containsKey("loginId")) {
			userId = String.valueOf(session.get("loginId"));
		}else if (session.containsKey("tempUserId")) {
			userId = String.valueOf(session.get("tempUserId"));
		}

		//cart.jspでチェックされた購入IDでcart_infoテーブルから削除
		//その数が0ならエラーメッセージをセッションにput
		session.remove("checkListErrorMessageList");

		//何もチェックしないで削除ボタン押すとcheckListがnullになる
		if(checkList == null){
			checkListErrorMessageList.add("チェックされていません。");
			session.put("checkListErrorMessageList", checkListErrorMessageList);
			return ERROR;
		}else{
			for(String productId:checkList) {
				System.out.println(productId);
				System.out.println(userId);
				count += cartInfoDAO.delete(productId, userId);
			}
		}
		if(count <= 0) {
			checkListErrorMessageList.add("チェックされていません。");
			session.put("checkListErrorMessageList", checkListErrorMessageList);
			return ERROR;
		}else {

			List<CartInfoDTO> cartInfoDtoList = new ArrayList<CartInfoDTO>();

			//削除後のカート情報を取得、セッションにput
			cartInfoDtoList = cartInfoDAO.getCartInfoDtoList(userId);
			Iterator<CartInfoDTO> iterator = cartInfoDtoList.iterator();
			if(!(iterator.hasNext())) {
				cartInfoDtoList = null;
			}
			session.put("cartInfoDtoList", cartInfoDtoList);

			//cart_infoテーブルでは合計金額はないので各商品（値段×個数）
			//の合わせた合計をセッションにput
			int totalPrice = Integer.parseInt(String.valueOf(cartInfoDAO.getTotalPrice(userId)));
			session.put("totalPrice", totalPrice);

			result = SUCCESS;
		}
		return result;
	}
	public Collection<String> getCheckList() {
		return checkList;
	}
	public void setCheckList(Collection<String> checkList) {
		this.checkList = checkList;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
