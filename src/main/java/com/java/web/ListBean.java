package com.java.web;

public class ListBean {
	
	private int no;
	private String txt;
	
	public ListBean(int no, String txt) {
		this.no = no;
		this.txt = txt;
	}
	
	public int getNo() {
		return this.no;
	}
	
	public String getTxt() {
		return this.txt;
	}
	
	public void setTxt(String txt) {
		this.txt = txt;
	}

}
