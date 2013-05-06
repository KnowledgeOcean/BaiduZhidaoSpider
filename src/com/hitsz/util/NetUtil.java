package com.hitsz.util;

import java.util.Properties;

import com.hitsz.util.config.ConfigUtil;
import com.hitsz.util.config.PropNotConfigedException;


/**
 * ��������
 * @author JasonTan
 *
 */
public class NetUtil {

	/**
	 * set web proxy
	 */
	public static void setProxy(){
		
		String strProxy = "";
		String strPort = "";
		//�������ļ��л�ȡ�˿�
		
		try {
			strProxy =  ConfigUtil.getPropValue("Spider.ProxyIP");
			strPort =  ConfigUtil.getPropValue("Spider.ProxyPort");
		} catch (PropNotConfigedException e) {
			e.printStackTrace();
		}
		

		Properties systemProperties = System.getProperties(); 
		systemProperties.setProperty("http.proxyHost",strProxy); 
		systemProperties.setProperty("http.proxyPort",strPort); 
	}

}
