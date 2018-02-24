package com.yysc.login.bean;

/**
 * 用户数据实体类
 * 
 * @author Peter
 * 
 */

public class UserInfoContent {

	private String phone;// 手机号码
	private String realNameVerify;// 是否实名认证 0否 1是
	private String userId;// 用户Id
	private String isLoaning;// 是否存在正在进行的贷款 0否 1是
	private String certNo;// 身份证号码
	private String custName;// 真实姓名
	private String sex;// 性别

	public UserInfoContent(String phone, String realNameVerify, String userId,
			String isLoaning, String certNo, String custName, String sex) {

		this.phone = phone;
		this.realNameVerify = realNameVerify;
		this.userId = userId;
		this.isLoaning = isLoaning;
		this.certNo = certNo;
		this.custName = custName;
		this.sex = sex;

	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealNameVerify() {
		return realNameVerify;
	}

	public void setRealNameVerify(String realNameVerify) {
		this.realNameVerify = realNameVerify;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsLoaning() {
		return isLoaning;
	}

	public void setIsLoaning(String isLoaning) {
		this.isLoaning = isLoaning;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
