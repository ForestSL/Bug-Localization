package com.sl.v0.actions.panel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadReport {
	
	public static String openHtml(String url){
		HttpURLConnection conn = null;
		try {
		    URL realUrl = new URL(url);
		    conn = (HttpURLConnection) realUrl.openConnection();
		    //����post����
            conn.setRequestMethod("POST");
            //��ʹ�û���
            conn.setUseCaches(false);
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //��ȡ��ʱʱ��
            conn.setReadTimeout(8000);
            //���ӳ�ʱʱ��
            conn.setConnectTimeout(8000);
            //��һ�����Ҫ�����ò�Ҫ302�Զ���ת������ὲ�⵽
            conn.setInstanceFollowRedirects(false);
		    return "success";
		}catch (Exception e) {
		    //Url����
			return "failed";
		}
	}
	  
	/*������*/
	public static void main(String[] args) {  
		String openStatus=openHtml("https://issues.apache.org/jira/projects/CXF");
		System.out.println(openStatus);
	}  
	
}
