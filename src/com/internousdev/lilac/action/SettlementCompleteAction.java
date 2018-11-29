package com.internousdev.lilac.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.CartInfoDAO;
import com.internousdev.lilac.dao.PurchaseHistoryInfoDAO;
import com.internousdev.lilac.dto.CartInfoDTO;
import com.internousdev.lilac.dto.PurchaseHistoryInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class SettlementCompleteAction extends ActionSupport implements SessionAware{
	private String id;
	private Map<String,Object> session;

	public String execute(){
		String result=ERROR;

		if(!(session.containsKey("mCategoryDtoList"))){
			result = "timeout";
			return result;
		}

		//success="SettlementComplete.jsp"
		//error="SettlementConfirm.jsp"
		//@SuppressWarnings("unchecked")を使いList<>のキャストをうまいことやっている
		//1.キャストしてsessionの値から購入情報と宛先情報のDTOListをセット
		@SuppressWarnings("unchecked")
		ArrayList<PurchaseHistoryInfoDTO> purchaseHistoryInfoDtoList=(ArrayList<PurchaseHistoryInfoDTO>)session.get("purchaseHistoryInfoDtoList");

		//@SuppressWarnings("unchecked")
		for(int i=0;i<purchaseHistoryInfoDtoList.size();i++){
			purchaseHistoryInfoDtoList.get(i).setDestinationId(Integer.parseInt(id));
		}
		//購入情報 DTOからDAOを使い登録
		PurchaseHistoryInfoDAO purchaseHistoryInfoDAO=new PurchaseHistoryInfoDAO();
		int count=0;
		for(int i=0;i<purchaseHistoryInfoDtoList.size();i++){
			count+=purchaseHistoryInfoDAO.regist(
				String.valueOf(session.get("loginId")),
				purchaseHistoryInfoDtoList.get(i).getProductId(),
				purchaseHistoryInfoDtoList.get(i).getProductCount(),
				purchaseHistoryInfoDtoList.get(i).getDestinationId(),
				purchaseHistoryInfoDtoList.get(i).getSubtotal()
				);
			}
		//0のときCartInfoDAOのカート情報を消してDTOにセット
		if(count>0){
			CartInfoDAO cartInfoDAO=new CartInfoDAO();
			count=cartInfoDAO.deleteAll(String.valueOf(session.get("loginId")));
			if(count>0){
				List<CartInfoDTO> cartInfoDtoList=new ArrayList<CartInfoDTO>();
				cartInfoDtoList=cartInfoDAO.getCartInfoDtoList(String.valueOf(session.get("loginId")));
				Iterator<CartInfoDTO> iterator=cartInfoDtoList.iterator();
				if(!(iterator.hasNext())){
					cartInfoDtoList=null;
				}

				String cartflag;

				cartflag = "0";
				session.put("cartflag",cartflag);
				session.put("cartInfoDtoList",cartInfoDtoList);

				int totalPrice=Integer.parseInt(String.valueOf(cartInfoDAO.getTotalPrice(String.valueOf(session.get("loginId")))));
				session.put("totalPrice",totalPrice);
				result=SUCCESS;
			}
		}
		return result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}