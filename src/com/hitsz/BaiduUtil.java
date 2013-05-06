package com.hitsz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hitsz.dao.QA;
import com.hitsz.dao.Term;

/**
 * 
 * @author Jack_Tan
 *
 */
public class BaiduUtil {
	public final static  String URLHEAD = "http://zhidao.baidu.com";

	static final String ENCODING = "gbk";

	static final int LIMIT = 20;
	
	List<Term> termList = new ArrayList<Term>();
	List<QA>	qaList = new ArrayList<QA>();
	/**
	 * �ٶ�֪�����������ÿ��item��ʼ���
	 */
	final String itemBegin = "<dl class=\"result-item\">";
	final String itemEnd = "</dd></dl>";
	
	
	/**
	 * ʹ��Ĭ�ϱ���gbk
	 * @param htmlurl �����url
	 * @return
	 * @throws IOException 
	 */
	String getHtml(String htmlurl) throws IOException{
		return getHtml(htmlurl,ENCODING);
	}
	
	/**
	 * ��ȡ��ҳ������
	 * @param htmlurl	
	 * @param encoding	��ҳ����
	 * @return
	 * @throws IOException 
	 */
	String getHtml(String htmlurl,String encoding) throws IOException{
		URL url;
        String temp = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(htmlurl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url
                    .openStream(), encoding));// ��ȡ��ҳȫ������
            while ((temp = in.readLine()) != null) {
                sb.append(temp+"\r\n");
            }
            in.close();
        }catch(MalformedURLException me){
            System.out.println("�������URL��ʽ�����⣡����ϸ����");
            me.getMessage();
           throw me;
        }catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        return sb.toString();
	}
	
//	String getTitle(String content){
////		 String regex;
////	        String title = "";
////	        List<String> list = new ArrayList<String>();
////	        regex = "<title>.*?</title>";
////	        Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
////	        Matcher ma = pa.matcher(s);
////	        while (ma.find()) {
////	            list.add(ma.group());
////	        }
////	        for (int i = 0; i < list.size(); i++) {
////	            title = title + list.get(i);
////	        }
//		
////		int start = 0;
////		int end = 0;
////		start = content.indexOf(itemBegin);
////		end = content.indexOf(itemEnd, start);
////		
////		while(validate(start, end)){
////			
////		}
//		
//	    return null;//outTag(title);
//	}

	/**
	 * ��ȡ���е�item��������һ��list
	 * @param content
	 * @return
	 */
	public List<Term> getItem(String content){
		
		int start = 0;
		int end = 0;
		start = content.indexOf(itemBegin);
		end = content.indexOf(itemEnd, start);
		
		while(validate(start, end)){
			String tmp = content.substring(start,end);
			/**
			 * ����ȡLIMIT��item
			 */
			if(termList.size() <= LIMIT){
				Term term = getOneTerm(tmp);
				termList.add(term);
			}
			else
				break;
			start = content.indexOf(itemBegin, end) ;
			end = content.indexOf(itemEnd, start);
		}
		
        return termList;
	}

	/**
	 * ��ÿ��item���н���������һ��Item
	 * @param item
	 * @return
	 */
	private Term getOneTerm(String item) {
		String titleHead = "log=";
		String titleMid = "\"si:1\">";
		String titleEnd = "</a>";
		
		String ansHead = "��";
		String ansMid = "<b>:</b></span>";
		String ansEnd = "</p>";
				
		String url = null;
		int id = 0;
		String title = null;
		String answer = null;
		String date = null;
		
		int start, end;
		start = end = 0;
		
		url = getUrl(item);
		url = url.substring(0,url.length()-1);
		
//		System.out.println("url -->"+ url);
		
		id = getId(url);
		
//		System.out.println("id -->"+id);
		
		start = item.indexOf(titleHead);
		end = item.indexOf(titleEnd, start);
		
		/**
		 * ��Ϊtitle�ĸ�ʽ���£�
		 * 		target="_blank" log="si:1">���ڹ�Ʊ<em>����</em>����?</a>
		 * ��si��*�е�*�Ǳ仯�ģ�����ֻƥ��log��Ȼ���ټ���"si��*">�м�ĳ��ȣ��Դ���ƥ��
		 * title 
		 */
		title = item.substring(start + titleHead.length() + titleMid.length(),end);
		
		//�滻һЩ����ַ�����<em></em>
		title = replaceMark(title);
		
//		System.out.println("title -->"+ title);
		
		start = item.indexOf(ansHead);
		end = item.indexOf(ansEnd,start);
		/**
		 * answer�ĸ�ʽ���£�
		 * <span class="answer-flag">��<b>:</b></span>1 ������Ϲ�ϵ��
		 * 			�����������������ϣ��������������ĸ��ʽ�У </p>  </dd>  
		 * 				<dd class="result-cate">  
		 * ƥ��ͷΪ����
		 */
		answer = item.substring(start + ansHead.length() + ansMid.length(),end);
		answer = replaceMark(answer);
		
//		System.out.println("answer -->"+ answer);
		
		
		date = getDate(item);
		
//		System.out.println("date -->"+ date);
//						
//		System.out.println("\n");
		
		Term term = new Term(id, url, title, answer, date);
		
		return term;
	}
	

	/**
	 * ƥ��URl
	 * @param item
	 * @return
	 */
	private String getUrl(String item) {
		String regex ="[a-zA-Z]+://[^\\s]*";;
		Pattern pa = Pattern.compile(regex);
		Matcher ma = pa.matcher(item);
		
		String url = null;
		
		if(ma.find()){
			url = ma.group();
		}	
		return url;
	}

	/**
	 * ƥ������
	 * @param item
	 * @return
	 */
	private String getDate(String item) {
		String date = null;
		String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		Pattern pa = Pattern.compile(regex);
		Matcher ma = pa.matcher(item);
		
		if(ma.find()){
			date = ma.group();
		}	
		return date;
	}

	/**
	 * �滻���
	 * @param str
	 * @return
	 */
	private String replaceMark(String str) {
		String em = "</em>";
		String em1 = "<em>"; 
		
		str = str.replace(em, "");
		str = str.replace(em1, "");
		return str;
	}

	/**
	 * ��ȡurl���е�id
	 * @param url
	 * @return
	 */
	private int getId(String url) {
		String regex = "\\d+";
		Pattern pa = Pattern.compile(regex);
		Matcher ma = pa.matcher(url);
		
		String id = null;
		
		if(ma.find()){
			id = ma.group();
		}
		
		return Integer.parseInt(id);
	}
	
//	public List<String> getItem(String content){
//		
//		int start = 0;
//		int end = 0;
//		start = content.indexOf(itemBegin);
//		end = content.indexOf(itemEnd, start);
//		
//		while(validate(start, end)){
//			String tmp = content.substring(start,end);
//			/**
//			 * ����ȡLIMIT��item
//			 */
//			if(list.size() <= LIMIT)
//				list.add(tmp);
//			else
//				break;
//			start = content.indexOf(itemBegin, end) ;
//			end = content.indexOf(itemEnd, start);
//		}
//		
//  //    System.out.println("size: "+ list.size()+"\n"+list.toString());
//      
////		for (int i = 0; i < list.size(); i++) {
////      		System.out.println("The "+i+"th(s) item is : \n" + list.get(i)+"\n");
////      	}
//        return list;
//	}
	



	/**
	 * �жϵõ����±��ǲ��ǺϷ���
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private boolean validate(int start, int end) {
		if(start < 0 || end < 0)
			return false;
		if(start > end)
			return false;
		return true;
	}

	/**
	 * ��list�е�ÿ��term���н���
	 */
	public void parseTerm() {
		 String title = null;
		 String question = null;
		 String category = null;
		 String qId = null;
		 String qDate = null;
		 String answer = null;
		 String aDate = null;
		 String aId = null;
		 String aLevel = null;
		 String aExpert = null;
		 
		 for(int i=0; i < termList.size(); i++){
			 
			 String url = termList.get(i).getUrl();
			 if(null == url)
				 continue;
			 
			 String content = null;
			try {
				content = getHtml(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 if(null == content)
				 continue;
			 title = getTitle(content);
			 
			 question = getQuestion(content);
			 
			 category = getCategory(content);
			 
			 qId = getQId(content);
			 
			 qDate = getQDate(content);
			 
			 answer = getAnswer(content);
			 
			 aDate = getADate(content);
			 
			 aId = getAID(content);
			 
			 aLevel = getLevel(content);
			 
			 aExpert = getExpert(content);
			 
			 QA qa = new QA(title, question, category, qId,
					 qDate, answer, aDate, aId, aLevel, aExpert);
			 
			 qaList.add(qa);
		 }
		 
	}

	public void Test(String content){
		
		 String title = null;
		 String question = null;
		 String category = null;
		 String qId = null;
		 String qDate = null;
		 String answer = null;
		 String aDate = null;
		 String aId = null;
		 String aLevel = null;
		 String aExpert = null;
		 
		 title = getTitle(content);
		 
		 question = getQuestion(content);
		 
		 category = getCategory(content);
		 
		 qId = getQId(content);
		 
		 qDate = getQDate(content);
		 
		 answer = getAnswer(content);
		 
		 aDate = getADate(content);
		 
		 aId = getAID(content);
		 
		 aLevel = getLevel(content);
		 
		 aExpert = getExpert(content);
	}
	
	
	private String getExpert(String content) {
		//�ó�
		//</p>
		//>�ﾶ</a>
		
		String exHead = "�ó�";
		String exEnd = "</p>";
		
		String expert = "";
		String str = null;
		int start , end;	
		
		start = content.indexOf(exHead);
		end = content.indexOf(exEnd,start);
		
		if(validate(start, end)){
			str = content.substring(start, end);
			
			String headStr = "\">";
			String endStr = "</a>";
			
			start = str.indexOf(headStr);
			end  = str.indexOf(endStr,start);
			
			while(validate(start, end)){
				expert += " "+str.substring(start + headStr.length(),end);
				start = str.indexOf(headStr,end);
				end = str.indexOf(endStr, start);
			}
		}
		
		System.out.println("expert -->" + expert);
		
		return expert;
	}

	private String getLevel(String content) {
		
		return null;
	}

	private String getAID(String content) {
		//user-name
		String uname = null;
		String answerHead = "qb-username";
		String answerEnd = "?from";
		String answerMid = "/p/";
		
		uname = getSubStr(answerHead, answerEnd, answerMid, content);

		System.out.println("uname -->"+ uname);

		return uname;
	}

	private String getADate(String content) {
		
		String aDate = null;
		
		String dateHead = "realname-time";
		String dateEnd = "</span>";
		String dateMid = "ins>";
		
		aDate = getSubStr(dateHead, dateEnd, dateMid, content);
		
		System.out.println("aDate -->"+ aDate);

		
//		//2013-04-17 12:10
//		String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}.*?[0-9]{2}:[0-9]{2}";
//		Pattern pa = Pattern.compile(regex);
//		Matcher ma = pa.matcher(aDate);
//		
//		if(ma.find()){
//			aDate = ma.group();
//		}	
//		System.out.println("aDate -->"+ aDate);
		return aDate;
	}

	private String getSubStr(String headStr, String endStr, String midStr,String content){
		String str = null;
		int start , end;	
		
		start = content.indexOf(headStr);
		end = content.indexOf(endStr,start);
		
		if(validate(start, end)){
			str = content.substring(start, end);
			start = str.indexOf(midStr);
			if(validate(start + midStr.length(), str.length()))
				str = str.substring(start + midStr.length() ,str.length());
		}
		return str;
	}
	
	
	private String getAnswer(String content) {
		//aContent
		String answer = null;
		String answerHead = "aContent";
		String answerEnd = "</pre>";
		String answerMid = ">";
		
		answer = getSubStr(answerHead, answerEnd, answerMid, content);

		

		answer  = answer.replace("<br />", "\r\n");
		System.out.println("answer -->"+ answer);
		return answer;
	}

	private String getQDate(String content) {
		
		String date = null;
		String dateHead = "ask-time";
		String dateEnd = "</span>";
		String dateMid = "ins>";
		
		date = getSubStr(dateHead, dateEnd, dateMid, content);
		
		System.out.println("Qdate -->"+ date);

//		//2013-04-17 12:10
//		String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}.*?[0-9]{2}:[0-9]{2}";
//		Pattern pa = Pattern.compile(regex);
//		Matcher ma = pa.matcher(date);
//		
//		if(ma.find()){
//			date = ma.group();
//		}	
//		System.out.println("Qdate -->"+ date);
		return date;
	}

	private String getQId(String content) {
		
		String name = null;
		String nameHead = "user-name";
		String nameEnd = "?from";
		String nameMid = "/p/";
		
		name = getSubStr(nameHead, nameEnd, nameMid, content);
		
		System.out.println("name --> "+ name);
		
		return name;
	}

	private String getCategory(String content) {
		String category = null;
		String caHead = "����";
		String caEnd = "</a>";
		String caMid = ">";
		category = getSubStr(caHead, caEnd, caMid, content);
				
		System.out.println("category --> "+ category);
		
		return category;
	}

	private String getQuestion(String content) {

		String question = null;
		String questionHead = "accuse=\"qContent\">";
		String questionEnd = "</pre>";
		
		question = getSubStr(questionHead, questionEnd, content);
		
		System.out.println("question --> "+ question);
		
		return question;
	}
	

	private String getTitle(String content) {
		String title = null;
		String titleHead = "class=\"ask-title\">";
		String titleEnd = "</span>";
		
		title  = getSubStr(titleHead, titleEnd, content);
			
		System.out.println("title --> "+ title);
		
		return title;
	}
	
	private String getSubStr(String headStr, String endStr, String content){
		String str = null;
		int start , end;
		
		start = content.indexOf(headStr);
		end = content.indexOf(endStr,start);
		
		if(validate(start, end)){
			str = content.substring(start + headStr.length(), end);
		}
		return str;
	}
}
