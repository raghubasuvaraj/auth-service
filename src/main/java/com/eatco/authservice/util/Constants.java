package com.eatco.authservice.util;

public class Constants {
	
	// will accept these type of name François, O'Brian etc
	public static final String UNICOD_NAME_REGEX = "^[\\p{L} .'-]+$";
	
	public static final String BASIC_EMAIL_REGEX = ".*@.*\\..*";
	
	public static final String EMAIL_REGEX = "^[\\p{L}\\d\\+-]+(?:\\.[\\w\\+-]+)*@[\\p{L}\\d]+\\.([\\p{L}\\d]{2,4}\\.)?[\\p{L}]{2,4}$";

	public static final String PHONE_REGEX = "^[6-9][\\d]{9}$";
	
	public static final String ALLOWED_SPL_CHARACTERS = "!@#$%^&*()_+";	
	
	public static final String MANDATORY_FIELD = "is a mandatory field.";
	
	public static final String COMING_DATE = ", please provide the upcoming date.";
	
	public static final String GIVE_VALID_VALUE = ", please enter a valid data.";

	public static final String IS_EMPTY = "must not be left blank";
	public static final String PHONE_NUMBER_LENGTH = "must be 10 Digits";

	// Sequence number generation
	public static final String SEQUENCE_NAME = "database_sequence";
	public static final String SEQUENCE_GENERATION_FAILED = "Sequence genration failed";

	public static final String USER_ID_GENERATION_FAILED = "User id genration failed";
	public static final String PASSWORD_ENCRYPTION_FAILED = "Password encryption failed";
	public static final String RANDOM_NUMBER_GENERATION_FAILED = "Random number generation failed";

	// File operations
	public static final String FILE_READING_FAILED = "File reading failed!";
	public static final String READING_FROM_CSV_FAILED = "Reading data from the CSV file failed!";
	public static final String COLUMN_MAPPING_FAILED = "Column mapping failed";

	public static final String TEM_PASSWORD_GENERATION_FAILED = "Failed to generate a temporary password.";
	public static final String ERROR_CODE = "ERRONEOUS_SPECIAL_CHARS";	
	
	
	/**
	 * http://www.sample.com => true
	 * https://www.sample.com/ => true
	 * https://www.sample.com# => true
	 * http://www.sample.com/xyz => true
	 * http://www.sample.com/#xyz => true
	 * www.sample.com => true
	 * www.sample.com/xyz/#/xyz => true
	 * sample.com => true
	 * sample.com?name=foo => true
	 * http://www.sample.com#xyz => true
	 * http://www.sample.c => false
	 */
	public static final String WEBSITE_REGEX = "^((https?|ftp|smtp):\\/\\/)?(www.)?[a-z0-9]+\\.[a-z]+(\\/[a-zA-Z0-9#]+\\/?)*$";
	
	public static final String OTP_FOUR_DIGIT = "^[0-9]{4}$";

	public static final String DUPLICATED_VALUE = ", duplicated value";
	
	

}

