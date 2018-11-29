package com.internousdev.lilac.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.MCategoryDAO;
import com.internousdev.lilac.dto.MCategoryDTO;
import com.internousdev.lilac.util.CommonUtility;
import com.opensymphony.xwork2.ActionSupport;

//success→home.jsp
public class HomeAction extends ActionSupport implements SessionAware{

	private Map<String, Object> session;

	public String execute() {

		//仮のユーザーIDを発行する
		if (!(session.containsKey("tempUserId"))) {
			CommonUtility commonUtility = new CommonUtility();
			session.put("tempUserId", commonUtility.getRamdomValue());
		}

		//初期状態としてloginedを0にセットする
		if(!session.containsKey("logined")) {
			session.put("logined", 0);
		}

		List<MCategoryDTO> mCategoryDtoList = new ArrayList<MCategoryDTO>();
		//sessionにmCategoryDtolistを作って入れている
		if(!session.containsKey("mCategoryList")){
		MCategoryDAO mCategoryDao = new MCategoryDAO();
		mCategoryDtoList = mCategoryDao.getMCategoryList();
		session.put("mCategoryDtoList", mCategoryDtoList);
		}

		return SUCCESS;
	}

	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}