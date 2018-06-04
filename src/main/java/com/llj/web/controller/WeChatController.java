package com.llj.web.controller;

import static org.apache.log4j.Logger.getLogger;

import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.llj.web.constants.WechatConstants;
import com.llj.web.domain.message.TextMessage;
import com.llj.web.utils.MessageUtil;
import com.llj.web.utils.SignUtil;

/**
 * Created by lu on 2018/4/8.
 * <p>
 * Description:微信
 */
@RestController
@RequestMapping("/wechat")
public class WeChatController {

	/**
	 * 日志
	 */
	private static Logger logger = getLogger(WeChatController.class.getName());

	/**
	 * 微信请求处理接口
	 *
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/process", produces = "application/json;charset=UTF-8")
	public void process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		// 调用核心业务类接收消息、处理消息
		String respMessage = processRequest(request);
		if (null != respMessage)
			// 响应消息
			out.print(respMessage);
		else {
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戳
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			// 随机字符串
			String echostr = request.getParameter("echostr");

			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (SignUtil.checkSignature(signature, timestamp, nonce, WechatConstants.WECHAT_TOKEN)) {
				out.print(echostr);
			}
		}

		out.close();
		out = null;
	}

	/**
	 * 处理业务逻辑
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			if (requestMap == null) {
				return null;
			}
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");

			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);

			String respContent = "";
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) { // 消息类型
				String eventType = requestMap.get("Event");
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 关注
					respContent = "感谢您的关注！\n";
					// StringBuffer contentMsg = new StringBuffer();
					// contentMsg.append("您还可以回复下列数字，体验相应服务").append("\n\n");
					// contentMsg.append("1 我就是1个测试的").append("\n");
					// contentMsg.append("2 我就是2个测试的").append("\n");
					// contentMsg.append("3 我就是3个测试的").append("\n");
					// respContent = respContent + contentMsg.toString();

				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消关注
					// 取消关注,用户接受不到我们发送的消息了，可以在这里记录用户取消关注的日志信息

				} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// 点击事件
					String EventKey = requestMap.get("EventKey");
					if (EventKey.equals("V1001_TODAY_MUSIC")) {

					}
				}

			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {// 文本类型

			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

}