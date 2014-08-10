package com.example.iptest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.provider.Settings;

public class NetworkUtils {
	/**
	 * 判断当前网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		boolean res = false;
		int reTry = 3;
		
		if(isAirplaneModeOn(context)){
			return res;
		}
		
		for (int i = 0; i < reTry; i++) {
			try {
				ConnectivityManager nm = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = nm.getActiveNetworkInfo();
				if (null != networkInfo) {
					res = networkInfo.isConnected();
				}
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return res;
	}
	
	
	/**
	 *  判断是否为飞行模式
	 * @param context
	 * @return
	 */
	private static boolean isAirplaneModeOn(Context context) {
		  return Settings.System.getInt(context.getContentResolver(),
		  Settings.System.AIRPLANE_MODE_ON, 0) != 0;
	}
	
	/**
	* 获取当前网络状态的类型
	* @param mContext
	* @return 返回网络类型
	*/

	public static final int NETWORK_TYPE_NONE = -0x1; // 断网情况
	public static final int NETWORK_TYPE_WIFI = 0x1; // WiFi模式
	public static final int NETWOKR_TYPE_MOBILE = 0x2; // gprs模式
	
	public static int getCurrentNetType(Context mContext) {
		ConnectivityManager connManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // wifi
		NetworkInfo gprs = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); // gprs
		if (wifi != null && wifi.getState() == State.CONNECTED) {
			return NETWORK_TYPE_WIFI;
		} else if (gprs != null && gprs.getState() == State.CONNECTED) {
			return NETWOKR_TYPE_MOBILE;
		}
		return NETWORK_TYPE_NONE;
	}
	  public static String getLocalIpAddress() {	try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
					//if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
		}
		return "";}
	  public static  String GetNetIp(){  
		     URL infoUrl = null;  
		     InputStream inStream = null;  
		     try {  
		         infoUrl = new URL("http://www.ip38.com/");  
		         URLConnection connection = infoUrl.openConnection();  
		         HttpURLConnection httpConnection = (HttpURLConnection)connection;  
		         int responseCode = httpConnection.getResponseCode();  
		         if(responseCode == HttpURLConnection.HTTP_OK)  
		           {      
		              inStream = httpConnection.getInputStream();     
		            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream,"gb2312"));  
		            StringBuilder strber = new StringBuilder();  
		            String line = null;  
		            while ((line = reader.readLine()) != null)   
		                strber.append(line + "\n");  
		            inStream.close(); 
		            String str=strber.toString();
		            String strs[]=str.split("LI")[1].split("\n")[1].split("    ");
		            return strs[1];               
		          }  
		     } catch (MalformedURLException e) {  
		         // TODO Auto-generated catch block  
		         e.printStackTrace();  
		     } catch (IOException e) {  
		         // TODO Auto-generated catch block  
		         e.printStackTrace();  
		     } 
		     catch (Exception e) {  
		         // TODO Auto-generated catch block  
		         e.printStackTrace();  
		     } 
		    return "";  
		 } 
}
