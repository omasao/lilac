package com.internousdev.lilac.action;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.CartInfoDAO;
import com.internousdev.lilac.dao.UserInfoDAO;
import com.opensymphony.xwork2.ActionSupport;

public class CreateUserCompleteAction extends ActionSupport implements SessionAware{

	private String familyName;
	private String firstName;
	private String familyNameKana;
	private String firstNameKana;
	private String sex;
	private String email;
	private String loginId;
	private String password;
	private Map<String, Object> session;

	public String execute(){
		String cartflag;
		String result = ERROR;
		int count =0;
		int cartCount = 0;
		UserInfoDAO UserInfoDao = new UserInfoDAO();

		if(!(session.containsKey("mCategoryDtoList"))){
			result = "timeout";
			return result;
		}

		count = UserInfoDao.createUser(familyName,firstName,familyNameKana,firstNameKana,sex,email,loginId,password);

		if(count > 0) {
			CartInfoDAO cartInfoDao = new CartInfoDAO();

			if(session.containsKey("cartflag")){
				cartflag = session.get("cartflag").toString();
			}else{
				cartflag = "0";
			}
			session.remove("cartflag");

			cartCount = cartInfoDao.linkToLoginId(String.valueOf(session.get("tempUserId")), loginId);

			if(cartflag.equals("1")&& cartCount > 0) {
				// ユーザーを登録したところなので宛先情報はない
				session.put("destinationInfoDtoList", null);
				result = "settlement";
			} else {
				result = SUCCESS;
			}

			session.put("loginId", loginId);
			session.put("logined", 1);

		}

	return result;
	}

	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFamilyNameKana() {
		return familyNameKana;
	}
	public void setFamilyNameKana(String familyNameKana) {
		this.familyNameKana = familyNameKana;
	}
	public String getFirstNameKana() {
		return firstNameKana;
	}
	public void setFirstNameKana(String firstNameKana) {
		this.firstNameKana = firstNameKana;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
