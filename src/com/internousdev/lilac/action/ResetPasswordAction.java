package com.internousdev.lilac.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class ResetPasswordAction extends ActionSupport implements SessionAware{

	private Map<String,Object>session;

	public String execute(){

		if(!(session.containsKey("mCategoryDtoList"))){
			String result = "timeout";
			return result;
		}
		session.remove("resetPassLoginId");

		String result=ERROR;

		/*↓他のページから遷移してきたときにエラーメッセージを表示させないためにリムーブ*/

		session.remove("loginIdErrorMessageList");
		session.remove("passwordErrorMessageList");
		session.remove("passwordIncorrectErrorMessageList");
		session.remove("newPasswordErrorMessageList");
		session.remove("reConfirmationNewPasswordErrorMessageList");
		session.remove("newPasswordIncorrectErrorMessageList");
		session.remove("resetPassLoginIdErrorMessageList");
		session.remove("resetPassLoginId");

		result=SUCCESS;

		return result;

	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
