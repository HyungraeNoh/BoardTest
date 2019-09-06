package com.java.web;

public class FilesBean {
	
	String fileName;
	String fileURL;
	String delYn;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileURL() {
		return fileURL;
	}
	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	@Override
	public String toString() {
		return "FilesBean [fileName=" + fileName + ", fileURL=" + fileURL + ", delYn=" + delYn + "]";
	}
}
