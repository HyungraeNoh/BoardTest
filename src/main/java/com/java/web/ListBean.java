package com.java.web;

public class ListBean {

	private int no;
	private String title;
	private String txt;
	
	public ListBean(int no, String txt, String title) {
		this.no = no;
		this.txt = txt;
		this.title = title;
	}
	public int getNo() {
		return this.no;
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
