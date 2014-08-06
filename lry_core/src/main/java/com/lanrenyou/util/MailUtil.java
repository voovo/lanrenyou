package com.lanrenyou.util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtil {

	protected static Logger logger = LoggerFactory.getLogger(MailUtil.class);

	private static MailUtil instance = null;

    Properties emailProperties;
    Session mailSession;
    MimeMessage emailMessage;
    String emailHost = "smtp.gmail.com";
    String emailPort = "587";// gmail's smtp port
    String fromUser = "info@lanrenyou.com";// just the id alone without
    String from = "LanRenYou";
    String fromUserEmailPassword = "password";
    
    public static int sendEmail(String email, String subject, String body) throws UnsupportedEncodingException{
    	if(null == instance){
    		instance = new MailUtil();
    	}
    	return instance.send(email, subject, body);
    }

    public int send(String email, String subject, String body) throws UnsupportedEncodingException {

        emailProperties = System.getProperties();
        emailProperties.put("mail.smtp.port", emailPort);
        emailProperties.put("mail.smtp.auth", "true");
        emailProperties.put("mail.smtp.starttls.enable", "true");

        mailSession = Session.getDefaultInstance(emailProperties, null);
        emailMessage = new MimeMessage(mailSession);
        try {
			emailMessage.addRecipient(Message.RecipientType.TO,  new InternetAddress(email));
			emailMessage.setSubject(subject, "UTF-8");
			emailMessage.setContent(body, "text/html;charset=gb2312");// for a html email
			// emailMessage.setText(emailBody);// for a text email
			
			InternetAddress fromAddress = new InternetAddress(fromUser, from, "UTF-8");
			emailMessage.setFrom(fromAddress);
			Transport transport = mailSession.getTransport("smtp");
			
			transport.connect(emailHost, fromUser, fromUserEmailPassword);
			transport.sendMessage(emailMessage, emailMessage.getAllRecipients());
			transport.close();
			return 1;
		} catch (AddressException e) {
			e.printStackTrace();
			logger.error("{}", e);
			return -1;
		} catch (MessagingException e) {
			e.printStackTrace();
			logger.error("{}", e);
			return -1;
		}

    }

	public static void main(String args[]) throws UnsupportedEncodingException {
		MailUtil.sendEmail("495269917@qq.com", "测试七", "请在两小时内点击以下链接完成账号激活：<a href=\"http://www.lanrenyou.com/regist/verifyEmail?code=NNJ9s9XmLL9Xa8tLP3iyWFaaNsFw01F2NQ**\" target=\"_blank\">http://www.lanrenyou.com/regist/verifyEmail?code=NNJ9s9XmLL9Xa8tLP3iyWFaaNsFw01F2NQ**</a>");// 收件人
	}

}
