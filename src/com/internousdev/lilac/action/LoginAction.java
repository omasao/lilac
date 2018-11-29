package com.internousdev.lilac.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.CartInfoDAO;
import com.internousdev.lilac.dao.DestinationInfoDAO;
import com.internousdev.lilac.dao.UserInfoDAO;
import com.internousdev.lilac.dto.DestinationInfoDTO;
import com.internousdev.lilac.dto.UserInfoDTO;
import com.internousdev.lilac.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport implements SessionAware{
	private String loginId;
	private String password;
	private boolean savedLoginId;

	private Map<String, Object> session;

	public String execute() {
		String cartflag;
//		List<MCategoryDTO> mCategoryDtoList = new ArrayList<MCategoryDTO>();
		List<String> loginIdErrorMessageList = new ArrayList<String>();
		List<String> passwordErrorMessageList = new ArrayList<String>();

		String result = ERROR;

		if(!(session.containsKey("mCategoryDtoList"))){
			result = "timeout";
			return result;
		}

		session.remove("loginIdErrorMessageList");
		session.remove("passwordErrorMessageList");
		session.remove("errorPasswordErrorMessageList");

		if(savedLoginId == true) {
			session.put("savedLoginId", true);
			session.put("loginId", loginId);
		}else {
			session.put("savedLoginId", false);
/*			session.remove("loginId");*/
		}

		InputChecker inputChecker = new InputChecker();
		loginIdErrorMessageList = inputChecker.doCheck("ログインID", loginId, 1, 8, true, false, false, true, false, false, false, false, false);
		passwordErrorMessageList = inputChecker.doCheck("パスワード", password, 1, 16, true, false, false, true, false, false, false, false, false);

		if(loginIdErrorMessageList.size()!=0 || passwordErrorMessageList.size()!=0) {
			session.put("loginIdErrorMessageList", loginIdErrorMessageList);
			session.put("passwordErrorMessageList", passwordErrorMessageList);
			session.remove("errorPasswordErrorMessageList");

			return ERROR;
		}

		UserInfoDAO userInfoDao = new UserInfoDAO();
		if(userInfoDao.isExistsUserInfo(loginId, password)) {
			if(userInfoDao.login(loginId, password) >0 ) {
				UserInfoDTO userInfoDTO = userInfoDao.getUserInfo(loginId, password);
				session.put("loginId", userInfoDTO.getUserId());
				int count=0;
				CartInfoDAO cartInfoDao = new CartInfoDAO();

				count = cartInfoDao.linkToLoginId(String.valueOf(session.get("tempUserId")), loginId);
				if(session.containsKey("cartflag")){
					cartflag = session.get("cartflag").toString();
				}else{
					cartflag = "0";
				}
				session.remove("cartflag");
				if(cartflag.equals("1")&& count > 0) {
					DestinationInfoDAO destinationInfoDao = new DestinationInfoDAO();

						List<DestinationInfoDTO> destinationInfoDtoList = new ArrayList<DestinationInfoDTO>();
						destinationInfoDtoList = destinationInfoDao.getDestinationInfo(loginId);
						Iterator<DestinationInfoDTO> iterator = destinationInfoDtoList.iterator();
						if(!(iterator.hasNext())) {
							destinationInfoDtoList = null;
						}
						session.put("destinationInfoDtoList", destinationInfoDtoList);
						result = "settlement";

				}else {
					result = SUCCESS;
				}
			}
			session.put("logined", 1);
		}else {
			session.put("errorPasswordErrorMessageList", "パスワードが異なります。");
		}
		return result;
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

	public boolean isSavedLoginId() {
		return savedLoginId;
	}

	public void setSavedLoginId(boolean savedLoginId) {
		this.savedLoginId = savedLoginId;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
