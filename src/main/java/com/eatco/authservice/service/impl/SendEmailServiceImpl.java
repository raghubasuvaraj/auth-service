package com.eatco.authservice.service.impl;

import java.util.Date;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.eatco.authservice.exception.ApplicationException;
import com.eatco.authservice.model.User;
import com.eatco.authservice.model.UserPasswordResetToken;
import com.eatco.authservice.repository.UserPasswordResetRepository;
import com.eatco.authservice.service.SendEmailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SendEmailServiceImpl implements SendEmailService {

	@Value("${user.reset.password.token.expiry}")
	private long resetTokenExpiry;

	@Value("${spring.mail.username}")
	private String userName;

	@Value("${spring.mail.from}")
	private String fromEmail;

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private int port;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String auth;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private String ttls;

	@Autowired
	private UserPasswordResetRepository userPasswordResetRepository;

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		try {
			UserPasswordResetToken userPasswordResetToken = new UserPasswordResetToken();
			UserPasswordResetToken oldUserPasswordResetToken = userPasswordResetRepository.findByUserId(user.getId());
			if (null != oldUserPasswordResetToken) {
				userPasswordResetToken.setId(oldUserPasswordResetToken.getId());
			}
			userPasswordResetToken.setToken(token);
			userPasswordResetToken.setUserId(user.getId());
			userPasswordResetToken.setExpiryDate(new Date(new Date().getTime() + resetTokenExpiry));
			userPasswordResetRepository.save(userPasswordResetToken);
		} catch (Exception e) {
			log.error("Failed to create reset token", e.getMessage());
			throw new ApplicationException("Failed to create reset token");
		}

	}

	@Override
	public void createEmailVerificationTokenForUser(String userEmail, String token) {
		try {
			UserPasswordResetToken userPasswordResetToken = new UserPasswordResetToken();
			UserPasswordResetToken oldUserPasswordResetToken = userPasswordResetRepository.findByEmail(userEmail);
			if (null != oldUserPasswordResetToken) {
				userPasswordResetToken.setId(oldUserPasswordResetToken.getId());
			}
			userPasswordResetToken.setToken(token);
			userPasswordResetToken.setEmail(userEmail);
			userPasswordResetToken.setExpiryDate(new Date(new Date().getTime() + resetTokenExpiry));
			userPasswordResetRepository.save(userPasswordResetToken);
		} catch (Exception e) {
			log.error("Failed to create password verification token", e.getMessage());
			throw new ApplicationException("Failed to create reset token");
		}
	}

	@Override
//	@Async
	public int constructResetTokenEmail(String token, User user) {
		int i = 0;
		try {
			StringBuilder msg = new StringBuilder();
			msg.append(
					"<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">\n"
							+ "  <div style=\"margin:50px auto;width:70%;padding:20px 0\">\n"
							+ "    <div style=\"border-bottom:1px solid #eee\">\n"
							+ "      <a href=\"\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">EATCO2</a>\n"
							+ "    </div>\n" + "    <p style=\"font-size:1.1em\">Hi,</p>\n"
							+ "    <p>Thank you for choosing EATCO2. Use the following OTP to complete your Sign Up procedures. OTP is valid for 5 minutes</p>\n"
							+ "    <h2 style=\"background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">"
							+ token + "</h2>\n" + "    <p style=\"font-size:0.9em;\">Regards,<br />EATCO2</p>\n"
							+ "    <hr style=\"border:none;border-top:1px solid #eee\" />\n"
							+ "    <div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">\n"
							+ "      <p>EatCO2 APS</p>\n" + "      <p>Flæsketorvet 68</p>\n"
							+ "      <p>1711 København V</p>\n" + "      <p>Denmark</p>\n" + "    </div>\n"
							+ "  </div>\n" + "</div>");
			i = this.sendMail(msg, user.getEmail(), "EatCo2 - Reset password");
			//sendWelcomeEmail(user);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error in sending mail for {} , {}", user.getEmail(), e.getMessage());
			throw new ApplicationException("Error in sending mail");
		}
		return i;
	}

	@Override
//	@Async
	public int sendEmailVerificationTemplate(String email, String token) {
		int i = 0;
		try {
			
			StringBuilder msg = new StringBuilder();
			msg.append(
					"<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">\n"
							+ "  <div style=\"margin:50px auto;width:70%;padding:20px 0\">\n"
							+ "    <div style=\"border-bottom:1px solid #eee\">\n"
							+ "      <a href=\"\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">EATCO2</a>\n"
							+ "    </div>\n" + "    <p style=\"font-size:1.1em\">Hi,</p>\n"
							+ "    <p>Thank you for choosing EATCO2. Use the following OTP to complete your Sign Up procedures. OTP is valid for 5 minutes</p>\n"
							+ "    <h2 style=\"background: #00466a;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\">"
							+ token + "</h2>\n" + "    <p style=\"font-size:0.9em;\">Regards,<br />EATCO2</p>\n"
							+ "    <hr style=\"border:none;border-top:1px solid #eee\" />\n"
							+ "    <div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">\n"
							+ "      <p>EatCO2 APS</p>\n" + "      <p>Flæsketorvet 68</p>\n"
							+ "      <p>1711 København V</p>\n" + "      <p>Denmark</p>\n" + "    </div>\n"
							+ "  </div>\n" + "</div>");
			i = this.sendMail(msg, email, "EatCo2 -  Email verification");

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error in sending mail for {}", email);
			throw new ApplicationException("Error in sending mail");
		}
		return i;
	}

	@Override
	public int sendWelcomeEmail(User user) {
		int i = 0;

		JavaMailSenderImpl mailSender = null;
		try {

			StringBuilder msg = new StringBuilder();
			msg.append(
					"<body style=\"padding: 0; margin: 0; -webkit-text-size-adjust:100%; -ms-text-size-adjust:100%; width:100% !important; background-color:#F2F2F2;\" bgcolor=\"#F2F2F2\" marginheight=\"0\" marginwidth=\"0\" topmargin=\"0\" rightmargin=\"0\" bottommargin=\"0\" leftmargin=\"0\">\n"
							+ "   <table id=\"BackgroundTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" height=\"100%\" bgcolor=\"#F2F2F2\" style=\"margin:0; padding:0; width:100% !important; line-height: 100% !important; border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; background-color:#F2F2F2;\">\n"
							+ "   <tbody>\n" + "      <tr>\n"
							+ "         <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse;\">\n"
							+ "            <table id=\"ColumnTable\" class=\"mobilefullwidth\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#F2F2F2\" width=\"600\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; background-color:#F2F2F2;\">\n"
							+ "   <tbody>\n" + "      <tr>\n" + "         <td style=\"border-collapse: collapse;\">\n"
							+ "            <table id=\"PreHeaderTable\" class=\"mobiledisplayno\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#F2F2F2\" width=\"100%\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; background-color:#F2F2F2; width:100%!important;\">\n"
							+ "               <tbody>\n" + "                  <tr>\n"
							+ "                     <td height=\"30\" style=\"border-collapse: collapse;\" align=\"left\" valign=\"middle\">&nbsp;</td>\n"
							+ "                  </tr>\n" + "               </tbody>\n" + "            </table>\n"
							+ "            <!--PreHeaderTable-->\n"
							+ "            <table id=\"BarTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#4288a7\" width=\"100%\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; background-color:#4288a7; width:100%!important;\">\n"
							+ "               <tbody>\n" + "                  <tr>\n"
							+ "                     <td class=\"mobilemargin\" bgcolor=\"#4288a7\" width=\"30\" style=\"border-collapse: collapse;\"></td>\n"
							+ "                     <td align=\"left\" valign=\"middle\" style=\"border-collapse: collapse; text-align: left; background-color:#4288a7;min-width:210px !important;\">\n"
							+ "                        <a target=\"_blank\" href=\"https://eatco2.com\" style=\"font-family: Helvetica, Arial, sans-serif; font-size:24px; line-height:1.4; font-weight: normal; color: #FFFFFF; text-align: left; text-decoration: none;\">\n"
							+ "                        <img alt=\"komoot\" width=\"70\" height=\"60\" border=\"0\" style=\"outline:none; text-decoration:none; -ms-interpolation-mode: bicubic; display:block;\" src=\"https://s3.amazonaws.com/static.komoot.de/email/welcome-mail/logo.jpg\">\n"
							+ "                        </a>\n" + "                     </td>\n"
							+ "                  </tr>\n" + "               </tbody>\n" + "            </table>\n"
							+ "            <!--BarTable-->\n"
							+ "            <table id=\"HeaderTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#91CAE3\" width=\"100%\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; width:100%!important; background-color:#91CAE3;\">\n"
							+ "               <tbody>\n" + "                  <tr>\n"
							+ "                     <td align=\"center\" valign=\"middle\" style=\"border-collapse: collapse; background-color:#91CAE3;\">\n"
							+ "                        <a style=\"color:#FFFFFF; text-decoration:none;\" target=\"_blank\" href=\"http://eatco2.com\">\n"
							+ "                        <img class=\"mobilefullwidth\" alt=\"Schön dass du dabei bist!\" width=\"600\" height=\"270\" border=\"0\" style=\"outline:none; -ms-interpolation-mode: bicubic; display:block;\" src=\"https://s3.amazonaws.com/static.komoot.de/email/welcome-mail/header.jpg\">\n"
							+ "                        </a>\n" + "                     </td>\n"
							+ "                  </tr>\n" + "               </tbody>\n" + "            </table>\n"
							+ "            <!--HeaderTable-->\n"
							+ "            <table id=\"SectionOneTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" bgcolor=\"#A3C556\" width=\"100%\" style=\"border-collapse:collapse; mso-table-lspace:0pt; mso-table-rspace:0pt; background-color:#A3C556; width:100%!important;\">\n"
							+ "               <tbody>\n" + "                  <tr>\n"
							+ "                     <td class=\"mobilemargin\" bgcolor=\"#A3C556\" width=\"30\" style=\"border-collapse: collapse;\"></td>\n"
							+ "                     <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; font-family: Helvetica, Arial, sans-serif; font-size:28px; line-height:1.4; font-weight: normal; color: #ffffff; text-align: center; background-color:#A3C556;\">\n"
							+ "                        <span style=\"font-weight: bold;\">" + user.getName()
							+ "</span>, We’re So Glad You’ve Joined Us!\n" + "                     </td>\n"
							+ "                     <td class=\"mobilemargin\" bgcolor=\"#A3C556\" width=\"30\" style=\"border-collapse: collapse;\"></td>\n"
							+ "                  </tr>\n" + "                  <tr>\n"
							+ "                     <td colspan=\"3\" bgcolor=\"#A3C556\" height=\"10\" style=\"border-collapse: collapse;\"></td>\n"
							+ "                  </tr>\n" + "                  <tr>\n"
							+ "                     <td class=\"mobilemargin\" bgcolor=\"#A3C556\" width=\"30\" style=\"border-collapse: collapse;\"></td>\n"
							+ "                     <td align=\"center\" valign=\"top\" style=\"border-collapse: collapse; font-family: Helvetica, Arial, sans-serif; font-size:18px; line-height:1.4; font-weight: normal; color: #e9f5cb; text-align: center; background-color:#A3C556;\">\n"
							+ "                        Europe’s #1 carbon compensation app welcomes you to the family. Keep reading to find out how to make EATCo2 your own.\n"
							+ "                     </td>\n"
							+ "                     <td class=\"mobilemargin\" bgcolor=\"#A3C556\" width=\"30\" style=\"border-collapse: collapse;\"></td>\n"
							+ "                  </tr>\n" + "                  <tr>\n"
							+ "                     <td colspan=\"3\" bgcolor=\"#A3C556\" height=\"40\" style=\"border-collapse: collapse;\"></td>\n"
							+ "                  </tr>\n" + "               </tbody>\n" + "            </table>\n"
							+ "            <!--SectionOneTable-->\n" + "</body>");
			i = this.sendMail(msg, user.getEmail(), "Welcome to EatCO2");
			i = 1;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error in sending mail for {} , {}", user.getEmail(), e.getMessage());
		}
		return i;
	}

	private int sendMail(StringBuilder msg, String toMailId, String subject) {
		int i = 0;
		JavaMailSenderImpl mailSender = null;
		try {
			mailSender = new JavaMailSenderImpl();
			mailSender.setHost(host);
			mailSender.setPort(port);
			mailSender.setUsername(userName);
			mailSender.setPassword(password);
			Properties properties = new Properties();
			properties.setProperty("mail.smtp.auth", auth);
			properties.setProperty("mail.smtp.starttls.enable", ttls);
			mailSender.setJavaMailProperties(properties);

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setTo(toMailId);
			helper.setSubject(subject);
			helper.setFrom(fromEmail);
			helper.setText(msg.toString(), true); // Use this or above line.
			mailSender.send(mimeMessage);
			log.info("Email send for {} for Subject {}",toMailId,subject);
			i = 1;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error in sending mail for {}", toMailId);
			throw new ApplicationException("Error in sending mail");
		}
		return i;
	}
}
