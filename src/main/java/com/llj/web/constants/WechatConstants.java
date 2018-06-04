package com.llj.web.constants;

import com.llj.framework.io.PropertiesSource;

/**
 * Created by lu on 2017/2/17.
 */
public interface WechatConstants {

	/**
	 * appid
	 */
	String WECHAT_APPID = PropertiesSource.getProperty("WECHAT_APPID");

	/**
	 * appsecret
	 */
	String WECHAT_APPSECRET = PropertiesSource.getProperty("WECHAT_APPSECRET");

	/**
	 * token
	 */
	String WECHAT_TOKEN = PropertiesSource.getProperty("WECHAT_TOKEN");

}
