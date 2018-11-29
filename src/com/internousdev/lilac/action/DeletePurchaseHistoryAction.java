package com.internousdev.lilac.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.PurchaseHistoryInfoDAO;
import com.internousdev.lilac.dto.PurchaseHistoryInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class DeletePurchaseHistoryAction extends ActionSupport implements SessionAware{
	private Map<String,Object>session;
	public String execute(){
		String result=ERROR;

		if(!(session.containsKey("mCategoryDtoList"))){
			result = "timeout";
			return result;
		}

		PurchaseHistoryInfoDAO purchaseHistoryInfoDAO=new PurchaseHistoryInfoDAO();
		int count =purchaseHistoryInfoDAO.deleteAll(String.valueOf(session.get("loginId")));
		if(count>0){
			List<PurchaseHistoryInfoDTO> purchaseHistoryInfoDtoList=
					purchaseHistoryInfoDAO.getPurchaseHistoryList(String.valueOf(session.get("loginId")));
			Iterator<PurchaseHistoryInfoDTO> iterator=purchaseHistoryInfoDtoList.iterator();
			if(!(iterator.hasNext())){
				purchaseHistoryInfoDtoList=null;
			}
		session.put("purchaseHistoryInfoDtoList",purchaseHistoryInfoDtoList);
		result=SUCCESS;
		}
		return result;

}

	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
