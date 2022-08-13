package com.eatco.authservice.util;


import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.eatco.authservice.enums.ErrorCodes;
import com.eatco.authservice.exception.USException;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class CommonUtil {

    private static final SimpleDateFormat formatter =new SimpleDateFormat("dd-MM-yyyy");

    public static Date getFormattedDate(String date) throws ParseException {
        return formatter.parse(date);
    }
    
    
	/**
	 * Generate current date format in ddMMyyyy then convert into String
	 * 
	 * @return
	 */
	public static String currentDateInDDMMYYYYFormat() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date).replace("/", "");
	}

	/**
	 * Generate a randum number. Can be used for generate the OTP also. Pass the
	 * size of the number wanted will create the randum with the size
	 * 
	 * @param randumNumberSize
	 * @return
	 */
	public static String generateRandumNumber(int randumNumberSize) {
		try {
			String randumNumber = null;

			// don't apply the role S2119
			Random randomValues = new Random();

			String numbers = "0123456789";
			char[] valueinChar = new char[randumNumberSize];

			for (int i = 0; i < valueinChar.length; i++) {
				valueinChar[i] = numbers.charAt(randomValues.nextInt(numbers.length()));
				randumNumber = String.valueOf(valueinChar);
			}
			return randumNumber;
		} catch (Exception e) {
			log.info(ExceptionUtils.getStackTrace(e));
			throw new USException(ErrorCodes.INTERNAL_SERVER_ERROR, Constants.RANDOM_NUMBER_GENERATION_FAILED);
		}
	}

	/**
	 * {@code lastUserId} fetched from the {@link com.lms.userservice.entity.Users
	 * Users} collection filed userId
	 * 
	 * @param lastUserId
	 * @param id
	 * @param name
	 * @return
	 */
	public static String generateUserId(long sequence, String id, String name) {
		try {
			String prefix = String.valueOf(sequence);
			String objectId = id.substring(id.length() - 5);
			String tail = name.toLowerCase().replaceAll("\\s", "");
			return prefix + "-" + objectId + "-" + tail;
		} catch (Exception e) {
			log.info(ExceptionUtils.getStackTrace(e));
			throw new USException(ErrorCodes.INTERNAL_SERVER_ERROR, Constants.USER_ID_GENERATION_FAILED);
		}
	}

	public static String passwordEncryptor(String password) {
		try {
			String encryted = null;
			if (!StringUtils.isEmpty(password)) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				encryted = passwordEncoder.encode(password);
			}
			return encryted;
		} catch (Exception e) {
			log.info(ExceptionUtils.getStackTrace(e));
			throw new USException(ErrorCodes.INTERNAL_SERVER_ERROR, Constants.PASSWORD_ENCRYPTION_FAILED);
		}
	}

	/**
	 * Generate temporary password with passay library. Will create combination of
	 * upper case, lower case, digits, alphanumeric with 10 character length
	 * 
	 * @return
	 */
	public static String generatePassayPassword() {
		log.info("Temporary password generating");
		try {
			PasswordGenerator gen = new PasswordGenerator();

			CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
			CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
			lowerCaseRule.setNumberOfCharacters(2);

			CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
			CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
			upperCaseRule.setNumberOfCharacters(2);

			CharacterData digitChars = EnglishCharacterData.Digit;
			CharacterRule digitRule = new CharacterRule(digitChars);
			digitRule.setNumberOfCharacters(2);

			CharacterData specialChars = new CharacterData() {
				public String getErrorCode() {
					return Constants.ERROR_CODE;
				}

				public String getCharacters() {
					return Constants.ALLOWED_SPL_CHARACTERS;
				}
			};

			CharacterRule splCharRule = new CharacterRule(specialChars);
			splCharRule.setNumberOfCharacters(2);
			return gen.generatePassword(10, splCharRule, lowerCaseRule, upperCaseRule, digitRule);
		} catch (Exception e) {
			log.info(ExceptionUtils.getStackTrace(e));
			throw new USException(ErrorCodes.INTERNAL_SERVER_ERROR, Constants.TEM_PASSWORD_GENERATION_FAILED);
		}
	}



	public static double exponentialValue(double base, double exponent) {
		if (exponent < 0) {
			throw new USException(ErrorCodes.INTERNAL_SERVER_ERROR, "Please provide positive exponent");
		}
		try {
			return Math.pow(base, exponent);
		} catch (Exception e) {
			log.info(ExceptionUtils.getStackTrace(e));
			throw new USException(ErrorCodes.INTERNAL_SERVER_ERROR, "Cannot find the power of the given expression");
		}
	}
	
	/**
	 * Converting the string to jsonObject, then read into a string Array List.
	 * Functionality created based on the admin pagination.
	 * @param jsonString
	 * @return
	 */
	public static List<String> convertStringToJsonObject(String jsonString){
		try {
			List<String> list = new ArrayList<>();
			JSONArray array = new JSONArray(jsonString);  
			for(int i=0; i < array.length(); i++) {  
				JSONObject object = array.getJSONObject(i);  
				list.add(object.getString("branchid"));
			}
			return list;
		}catch (Exception e) {
			log.info(ExceptionUtils.getStackTrace(e));
			throw new USException(ErrorCodes.INTERNAL_SERVER_ERROR, "Cannot convert string to json object");
		}
	}  
}
