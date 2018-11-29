package com.internousdev.lilac.action;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.PurchaseHistoryInfoDAO;
import com.internousdev.lilac.dto.PurchaseHistoryInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class PurchaseHistoryAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session;

	public String execute(){

		if(!(session.containsKey("mCategoryDtoList"))){
			String result = "timeout";
			return result;
		}

		PurchaseHistoryInfoDAO purchaseHistoryInfoDao=new PurchaseHistoryInfoDAO();
		List<PurchaseHistoryInfoDTO> purchaseHistoryInfoDtoList=new ArrayList<PurchaseHistoryInfoDTO>();
		purchaseHistoryInfoDtoList=purchaseHistoryInfoDao.getPurchaseHistoryList(String.valueOf(session.get("loginId")));

		Iterator<PurchaseHistoryInfoDTO> iterator=purchaseHistoryInfoDtoList.iterator();
		if(!(iterator.hasNext())){
			purchaseHistoryInfoDtoList=null;
		}
		session.put("purchaseHistoryInfoDtoList",purchaseHistoryInfoDtoList);

		return SUCCESS;

	}

	public Map<String,Object> getSession(){
		return session;
	}

	public void setSession(Map<String,Object> session){
		this.session=session;
	}

}