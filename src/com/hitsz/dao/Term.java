package com.hitsz.dao;

import java.io.Serializable;

public class Term implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7868017331860962546L;

	/**
	 * ���
	 */
	private int id;
	
	/**
	 * ��ַ
	 */
	private String url;
	
	/**
	 * ����
	 */
	private String title;
	
	/**
	 * �ʾ��
	 */
	private String answer;
	
	/**
	 * ʱ��
	 */
	private String date;
	
	
	@Override
	public String toString() {
		return "Term [id=" + id + "\r\n"+
				"url=" + url +"\r\n"+
				"title=" + title+ "\r\n"
				+ "answer=" + answer +"\r\n" 
				+"date=" + date + "]";
	}

	public Term(int id, String url, String title, String answer, String date) {
		super();
		this.id = id;
		this.url = url;
		this.title = title;
		this.answer = answer;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
}
