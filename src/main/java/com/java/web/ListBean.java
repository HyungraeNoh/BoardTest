package com.java.web;

public class ListBean {

	private String no;
	private String title;
	private String txt;
	
	public ListBean(String no, String title, String txt) {
		this.no = no;
		this.title = title;
		this.txt = txt;
		
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
	
	

}
