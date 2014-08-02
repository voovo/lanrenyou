package com.lanrenyou.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtil {

	protected static Logger logger = LoggerFactory.getLogger(MailUtil.class);

	static int port = 25;

	// static String server = "smtp.sina.com";// 邮件服务器mail.cpip.net.cn
	//
	// static String from = "懒人游";// 发送者,显示的发件人名字
	//
	// static String user = "jsan_xinlang@sina.com";// 发送者邮箱地址
	//
	// static String password = "jsanxl";// 密码
	//
	// public static int sendEmail(String email, String subject, String body)
	// throws UnsupportedEncodingException {
	// try {
	// Properties props = new Properties();
	// props.put("mail.smtp.host", server);
	// props.put("mail.smtp.port", String.valueOf(port));
	// props.put("mail.smtp.auth", "true");
	// logger.info("MailUtil ##############  1");
	// Transport transport = null;
	// Session session = Session.getDefaultInstance(props, null);
	// logger.info("MailUtil ##############  2");
	// transport = session.getTransport("smtp");
	// transport.connect(server, user, password);
	// logger.info("MailUtil ##############  3");
	// MimeMessage msg = new MimeMessage(session);
	// msg.setSentDate(new Date());
	// logger.info("MailUtil ##############  4");
	// InternetAddress fromAddress = new InternetAddress(user, from,
	// "UTF-8");
	// logger.info("MailUtil ##############  5");
	// msg.setFrom(fromAddress);
	// logger.info("MailUtil ##############  6");
	// InternetAddress[] toAddress = new InternetAddress[1];
	// toAddress[0] = new InternetAddress(email);
	// logger.info("MailUtil ##############  7");
	// msg.setRecipients(Message.RecipientType.TO, toAddress);
	// msg.setSubject(subject, "UTF-8");
	// msg.setText(body, "UTF-8");
	// msg.saveChanges();
	// logger.info("MailUtil ##############  8");
	// transport.sendMessage(msg, msg.getAllRecipients());
	// logger.info("MailUtil ##############  9");
	// return 1;
	// } catch (NoSuchProviderException e) {
	// e.printStackTrace();
	// return -1;
	// } catch (MessagingException e) {
	// e.printStackTrace();
	// return -2;
	// }
	// }

	public static void main(String args[]) throws UnsupportedEncodingException {
		MailUtil.sendEmail("495269917@qq.com", "测试六", "请在两小时内点击以下链接完成账号激活：<a href=\"http://www.lanrenyou.com/regist/verifyEmail?code=NNJ9s9XmLL9Xa8tLP3iyWFaaNsFw01F2NQ**\" target=\"_blank\">http://www.lanrenyou.com/regist/verifyEmail?code=NNJ9s9XmLL9Xa8tLP3iyWFaaNsFw01F2NQ**</a>");// 收件人
	}

	static String smtphost = "SMTP.163.com";// smtp服务器
	static String user = "jsan163@163.com";// 登录smtp服务器的账号
	static String password = "jsan126";// 登录服务器的密码
	static String from = "懒人游";// 发送人邮箱
//	String to = "495269917@qq.com";// 接受人邮箱
//	String title = "这是测试邮件不要回复！！";// 邮件标题
//	String content = "这是测试邮件不要回复！！";// 邮件内容

	public static int sendEmail(String to, String title, String content) throws UnsupportedEncodingException {
		try {
			Properties props = new Properties();// 创建一个对象属性
			props.put("mail.smtp.host", smtphost);// 指定smtp服务器
			props.put("mail.smtp.auth", "true");// 指定是否需要

			SmtpAuth auth = new SmtpAuth();// 创建一个授权验证对象
			auth.setAccount(user, password);

			Session mailsession = Session.getDefaultInstance(props, auth);// 创建一个session对象
			Message message = new MimeMessage(mailsession);// 创建一个session对象

			message.setFrom(new InternetAddress(user, from, "UTF-8"));// 指定发件人
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));// 指定收件人邮箱

			message.setSubject(title);// 文件的标题
//			message.setText(content);// 发送文本内容的邮件

			message.setSentDate(new Date());// 指定邮件的发送日期
			message.setHeader("X-Priority", "1");// 指定邮件的优先级1、紧急 3、普通 5、缓慢
			message.setContent(content, "text/html;charset=gb2312");
			message.saveChanges();

			Transport transport = mailsession.getTransport("smtp");// 创建一个Transport对象
			transport.connect(smtphost, user, password);// 连接SMTP服务器
			transport.sendMessage(message, message.getAllRecipients());// 发送邮件
			transport.close();
			return 1;

		} catch (AddressException e) {
			e.printStackTrace();
			return -1;
		} catch (MessagingException e) {
			e.printStackTrace();
			return -2;
		}
	}

	static class SmtpAuth extends Authenticator {
		String user, password;

		void setAccount(String user, String password) {
			this.user = user;
			this.password = password;
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, password);
		}
	}


}
