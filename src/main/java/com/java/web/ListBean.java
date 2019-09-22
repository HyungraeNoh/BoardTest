package com.java.web;

public class ListBean {

	private String no;
	private String title;
	private String txt;
	private String id;
	private String nickname;
	
	public ListBean(String no, String title, String txt, String id, String nickname) {
		this.no = no;
		this.title = title;
		this.txt = txt;
		this.id = id;
		this.nickname = nickname;
		
	}
	public String getNo() {
		return this.no;
	}
	
	public void setNo(String no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getTxt() {
		return this.txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	

}
