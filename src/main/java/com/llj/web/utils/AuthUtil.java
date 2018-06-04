/**
 *
 */
package com.llj.web.utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户公共类
 *
 * @author lu
 */
public class AuthUtil {

    /**
     * 获取toke
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String getToken(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        if (null == session) {
            return null;
        }
        session.setMaxInactiveInterval(0);
        String token = session.getId();
        return token;
    }
}