package com.internousdev.lilac.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class GoLoginAction extends ActionSupport implements SessionAware{

	private  Map<String, Object> session;

	public String execute() {

		if(!(session.containsKey("mCategoryDtoList"))){
			String result = "timeout";
			return result;
		}

		session.remove("loginIdErrorMessageList");
		session.remove("passwordErrorMessageList");
		session.remove("errorPasswordErrorMessageList");
		session.remove("cartflag");
		return SUCCESS;
	}

	public Map<String, Object> getSession(){
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}


}
