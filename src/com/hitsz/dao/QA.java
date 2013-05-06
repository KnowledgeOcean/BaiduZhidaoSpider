package com.hitsz.dao;

public class QA {
	/**
	 * �ʾ����
	 */
	private String title;
	/**
	 * �ʾ�����
	 */
	private String question;
	
	
	@Override
	public String toString() {
		return "QA [title=" + title + ", \nquestion=" + question + ", \ncategory="
				+ category + ", \nqId=" + qId + ", \nqDate=" + qDate + ", \nanswer="
				+ answer + ", \naDate=" + aDate + ", \naId=" + aId + ", \naLevel="
				+ aLevel + ", \naExpert=" + aExpert + "]\n";
	}
	/**
	 * �ʾ����
	 */
	private String category;
	/**
	 * ������ID
	 */
	private String qId;
	/**
	 * ����ʱ��
	 */
	private String qDate;
	/**
	 * ���ɴ�
	 */
	private String answer;
	/**
	 * ���ύʱ��
	 */
	private String aDate;
	/**
	 * �ش���ID
	 */
	private String aId;
	/**
	 * �ش��ߵȼ�
	 */
	private String aLevel;
	/**
	 * �ش��ߵ��ó�
	 */
	private String aExpert;
	
	public QA(String title, String question, String category, String qId,
			String qDate) {
		super();
		this.title = title;
		this.question = question;
		this.category = category;
		this.qId = qId;
		this.qDate = qDate;
	}

	public QA(String title, String question, String category, String qId,
			String qDate, String answer, String aDate, String aId,
			String aLevel, String aExpert) {
		super();
		this.title = title;
		this.question = question;
		this.category = category;
		this.qId = qId;
		this.qDate = qDate;
		this.answer = answer;
		this.aDate = aDate;
		this.aId = aId;
		this.aLevel = aLevel;
		this.aExpert = aExpert;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getqId() {
		return qId;
	}
	public void setqId(String qId) {
		this.qId = qId;
	}
	public String getqDate() {
		return qDate;
	}
	public void setqDate(String qDate) {
		this.qDate = qDate;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getaDate() {
		return aDate;
	}
	public void setaDate(String aDate) {
		this.aDate = aDate;
	}
	public String getaId() {
		return aId;
	}
	public void setaId(String aId) {
		this.aId = aId;
	}
	public String getaLevel() {
		return aLevel;
	}
	public void setaLevel(String aLevel) {
		this.aLevel = aLevel;
	}
	public String getaExpert() {
		return aExpert;
	}
	public void setaExpert(String aExpert) {
		this.aExpert = aExpert;
	}
}
