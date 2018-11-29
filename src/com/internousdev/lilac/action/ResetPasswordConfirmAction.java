package com.internousdev.lilac.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.lilac.dao.UserInfoDAO;
import com.internousdev.lilac.util.InputChecker;
import com.opensymphony.xwork2.ActionSupport;

public class ResetPasswordConfirmAction extends ActionSupport implements SessionAware{

	private String loginId;
	private String password;
	private String newPassword;
	private String reConfirmationPassword;


	private Map<String,Object>session;

	public String execute(){

		List<String> resetPassLoginIdErrorMessageList = new ArrayList<String>();
		List<String> passwordErrorMessageList = new ArrayList<String>();
		List<String> passwordIncorrectErrorMessageList = new ArrayList<String>();
		List<String> newPasswordErrorMessageList = new ArrayList<String>();
		List<String> reConfirmationNewPasswordErrorMessageList = new ArrayList<String>();
		List<String> newPasswordIncorrectErrorMessageList = new ArrayList<String>();

		String result=ERROR;

		if(!(session.containsKey("mCategoryDtoList"))){
			result = "timeout";
			return result;
		}

		/*↓エラーメッセージの上書き時にエラーが起きないようリムーブ*/

		session.remove("loginIdErrorMessageList");
		session.remove("passwordErrorMessageList");
		session.remove("passwordIncorrectErrorMessageList");
		session.remove("newPasswordErrorMessageList");
		session.remove("reConfirmationNewPasswordErrorMessageList");
		session.remove("newPasswordIncorrectErrorMessageList");
		session.remove("resetPassLoginId");

		session.put("resetPassLoginId", loginId);

		InputChecker inputChecker=new InputChecker();

		/*↓インプットチェッカーで出力されたエラーメッセージをリストにいれる*/
		resetPassLoginIdErrorMessageList = inputChecker.doCheck("ログインID", loginId, 1, 8, true, false, false, true, false, false, false, false, false);
		passwordErrorMessageList = inputChecker.doCheck("現在のパスワード", password, 1, 16, true, false, false, true, false, false, false, false, false);
		newPasswordErrorMessageList = inputChecker.doCheck("新しいパスワード", newPassword, 1, 16, true, false, false, true, false, false, false, false, false);
		reConfirmationNewPasswordErrorMessageList = inputChecker.doCheck("新しいパスワード（再確認）", reConfirmationPassword, 1, 16, true, false, false, true, false, false,false, false, false);
		newPasswordIncorrectErrorMessageList = inputChecker.doPasswordCheck(newPassword, reConfirmationPassword);

		/*↓エラーメッセージがでなくて、かつ*/
		if(resetPassLoginIdErrorMessageList.size()==0
				&& passwordErrorMessageList.size()==0
				&& newPasswordErrorMessageList.size()==0
				&& reConfirmationNewPasswordErrorMessageList.size()==0
				&& newPasswordIncorrectErrorMessageList.size()==0){

			/*↓ログインIDとパスワードがDBにあれば、loginId,newPassword,concealedPasswordをセッションに入れる*/
			UserInfoDAO userInfoDAO=new UserInfoDAO();
			if(userInfoDAO.isExistsUserInfo(loginId,password)){
				String concealedPassword = concealPassword(newPassword);
				session.put("newPassword", newPassword);
				session.put("concealedPassword", concealedPassword);
				result = SUCCESS;

				/*↓ログインIDとパスワードがDBがなければ、エラーメッセージをセッションに入れる*/
			}else{
				passwordIncorrectErrorMessageList.add("入力されたパスワードが異なります。");
				session.put("passwordIncorrectErrorMessageList", passwordIncorrectErrorMessageList);

			}
			/*↓セッションにエラーメッセージを追加*/
		}else{
			session.put("resetPassLoginIdErrorMessageList", resetPassLoginIdErrorMessageList);
			session.put("passwordErrorMessageList", passwordErrorMessageList);
			session.put("reConfirmationNewPasswordErrorMessageList", reConfirmationNewPasswordErrorMessageList);
			session.put("newPasswordIncorrectErrorMessageList", newPasswordIncorrectErrorMessageList);
			session.put("newPasswordErrorMessageList", newPasswordErrorMessageList);
		}

		return result;

	}

	public String concealPassword(String password) {

		int beginIndex = 0;
		int endIndex = 1;

		StringBuilder stringBuilder = new StringBuilder("***************");
		String concealPassword = stringBuilder.replace(beginIndex, endIndex, password.substring(beginIndex,endIndex)).toString();

		return concealPassword;
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

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getReConfirmationPassword() {
		return reConfirmationPassword;
	}

	public void setReConfirmationPassword(String reConfirmationPassword) {
		this.reConfirmationPassword = reConfirmationPassword;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
