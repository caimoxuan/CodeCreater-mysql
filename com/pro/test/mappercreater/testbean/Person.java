package com.pro.test.mappercreater.testbean;

import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private String nickname;
	private Integer sex;
	private String birthday;
	private String phone;
	private String mail;
	private String qq;
	private String company;
	private String personalid;

	public void setId(Integer id){
		this.id = id;
	}
	public Integer getId(){
		return id;
	}

	public void setMail(String mail){
		this.mail = mail;
	}
	public String getMail(){
		return mail;
	}

	public void setBirthday(String birthday){
		this.birthday = birthday;
	}
	public String getBirthday(){
		return birthday;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}
	public String getPhone(){
		return phone;
	}

	public void setSex(Integer sex){
		this.sex = sex;
	}
	public Integer getSex(){
		return sex;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	public String getNickname(){
		return nickname;
	}

	public void setCompany(String company){
		this.company = company;
	}
	public String getCompany(){
		return company;
	}

	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}

	public void setPersonalid(String personalid){
		this.personalid = personalid;
	}
	public String getPersonalid(){
		return personalid;
	}

	public void setQq(String qq){
		this.qq = qq;
	}
	public String getQq(){
		return qq;
	}

	public String toString(){
		return "Person["
		+"id=" + id + ","
		+"mail=" + mail + ","
		+"birthday=" + birthday + ","
		+"phone=" + phone + ","
		+"sex=" + sex + ","
		+"nickname=" + nickname + ","
		+"company=" + company + ","
		+"name=" + name + ","
		+"personalid=" + personalid + ","
		+"qq=" + qq 
		+"]";
	}
}
