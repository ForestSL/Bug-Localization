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
		    //设置post方法
            conn.setRequestMethod("POST");
            //不使用缓存
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //读取超时时间
            conn.setReadTimeout(8000);
            //连接超时时间
            conn.setConnectTimeout(8000);
            //这一句很重要，设置不要302自动跳转，后面会讲解到
            conn.setInstanceFollowRedirects(false);
		    return "success";
		}catch (Exception e) {
		    //Url出错
			return "failed";
		}
	}
	  
	/*测试用*/
	public static void main(String[] args) {  
		String openStatus=openHtml("https://issues.apache.org/jira/projects/CXF");
		System.out.println(openStatus);
	}  
	
}
