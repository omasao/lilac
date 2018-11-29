package com.internousdev.lilac.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class CreateDestinationAction extends ActionSupport implements SessionAware{

	private static final String MALE = "男性";
	private static final String FEMALE = "女性";
	private String defaultSexValue = MALE;
	private String sex;
	private Map<String, Object> session;

	public String execute(){
		String result = ERROR;

		if(!(session.containsKey("mCategoryDtoList"))){
			result = "timeout";
			return result;
		}

		session.remove("familyNameErrorMessageList");
		session.remove("firstNameErrorMessageList");
		session.remove("familyNameKanaErrorMessageList");
		session.remove("firstNameKanaErrorMessageList");
		session.remove("emailErrorMessageList");
		session.remove("telNumberErrorMessageList");
		session.remove("userAddressErrorMessageList");

		if(sex==null){
			session.put("sex", MALE);
		}else{
			session.put("sex", String.valueOf(session.get("sex")));
		}

		List<String> sexList = new ArrayList<String>();

		sexList.add(MALE);
		sexList.add(FEMALE);
		session.put("sexList", sexList);

		result=SUCCESS;
		return result;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDefaultSexValue() {
		return defaultSexValue;
	}

	public void setDefaultSexValue(String defaultSexValue) {
		this.defaultSexValue = defaultSexValue;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
