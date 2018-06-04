package com.llj.web.constants;

import com.llj.framework.io.PropertiesSource;

/**
 * redis 缓存常量类
 *
 * @author lu
 */
public interface RedisKeyConstants {

	String WECHAT_ACCESS_TOKEN = PropertiesSource.getProperty("WECHAT_ACCESS_TOKEN");

}
