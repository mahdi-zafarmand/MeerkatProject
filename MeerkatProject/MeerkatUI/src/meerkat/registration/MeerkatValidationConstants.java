package meerkat.registration;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is intended to hold things shared by the {@link MeerkatRegistrationServer} and the {@link MeerkatSoftwareValidator},
 * so that the server class does not need to have *many* client classes when it is jarred up for server deployment,
 * and so that the client application doesn't need the server class included when it is packaged.
 * 
 * @author everbeek
 *
 */
public class MeerkatValidationConstants {
	
	public static final String MEERKAT_HUMAN_WEBPAGE_URL = "http://meerkat.aicml.ca";
	public static final String MEERKAT_COMPUTER_WEBPAGE_URL = "http://meerkat-srv.aicml.ca";
	public static final String MEERKAT_EMAIL_ADDRESS = "meerkat@cs.ualberta.ca";
	public static final String MEERKAT_EMAIL_NAME = "Meerkat";
	public static final String MEERKAT_SERVER_EMAIL_NAME = "Meerkat";
	
	// Commands
	public static final String SERVER_COMMAND_REGISTER_CLIENT = "Register New Client";
	public static final String SERVER_COMMAND_LOG_CLIENT_USAGE = "Log Client Usage";
	public static final String SERVER_COMMAND_VALIDATE_CLIENT = "Validate Client Registration";
	public static final String SERVER_COMMAND_CREATE_NEW_PLUS_ACCOUNT = "Create New Plus Account";
	public static final String SERVER_COMMAND_VALIDATE_EMAIL_AND_SERIAL = "Validate Email And Serial";
	
	// Private, unused yet...
	// This one better be secured in the sense that is isn't possible to "walk into the store and take one"...
	private static final String SERVER_COMMAND_CREATE_NEW_FULL_ACCOUNT = "Create New Full Account";
	private static final String SERVER_COMMAND_CREATE_ADDITIONAL_FULL_SERIAL = "Create Additional Full Serial";
	
	/*
	 * TODO
	 * The responses and json attribute names are not merely enums because we do need to send them over the internet.
	 * Perhaps we could encode Java objects, send them, and decode them, which would serve to prevent versions
	 * from getting out of synch. Yes, that is likely a better idea than sending it in plain json.
	 * I like json though. Can I combine the two in a helpful way? Yes, see work notes for more details.
	 */

	// Responses
	// NB Whenever the strings contents of these are alterred, we have to update any referring PHP code.
	// This is a risky issue. We might convert to use JSP to avoid this possible problem.
	public static final String SERVER_RESPONSE_REGISTRATION_SUCCESS_KEY = "registration_success";
	public static final String SERVER_RESPONSE_REGISTRATION_REREGISTER_ATTEMPT = "attempt reregistration";
	public static final String SERVER_RESPONSE_LOG_SUCCESS_KEY = "log_success";
	public static final String SERVER_RESPONSE_VALIDATE_CLIENT = "validation_success";
	public static final String SERVER_RESPONSE_MISSING_COMMAND_DATA = "missing command and/or data for command.";
	public static final String SERVER_RESPONSE_DB_CONNECT_ERROR = "database connection error";
	public static final String SERVER_RESPONSE_NO_CORRESPONDING_ACCOUNT = "no account corresponding to provided email and serial";
	public static final String SERVER_RESPONSE_ACCOUNT_SERIAL_REVOKED = "account serial is revoked";
	public static final String SERVER_RESPONSE_ACCOUNT_VERSION_TYPE_INCORRECT = "incorrect Meerkat version type";
	public static final String SERVER_RESPONSE_ACCOUNT_VERSION_NUMBER_INCORRECT = "incorrect Meerkat version number";
	public static final String SERVER_RESPONSE_CANNOT_VALIDATE_UNREGISTERED_ACCOUNT = "cannot validate unregistered account";
	public static final String SERVER_RESPONSE_SERVER_ERROR = "server error";
	public static final String SERVER_RESPONSE_PLUS_ACCOUNT_CREATION_SUCCESS = "Plus account successufully created";
	public static final String SERVER_RESPONSE_PLUS_NEW_SERIAL_SUCCESS = "new serial created successfully";
	public static final String SERVER_RESPONSE_ACCOUNT_SERIAL_ALREADY_REGISTERED = "account already registered";
	public static final String SERVER_RESPONSE_PLUS_ACCOUNT_EXISTS_WITH_SERIAL = "Plus account exists already";
	public static final String SERVER_RESPONSE_MALFORMED_EMAIL_ADDRESS = "malformed email address";
	public static final String SERVER_RESPONSE_UNRECOGNIZED_COMMAND = "unrecognized command";
	public static final String SERVER_RESPONSE_PLUS_ACCOUNT_DOES_NOT_EXIST = "account does not exist";
	public static final String SERVER_RESPONSE_ACCOUNT_SERIAL_VALID = "account valid for email and serial provided";
	public static final String SERVER_RESPONSE_REMINDER_EMAIL_SENT = "serial reminder email sent";
	public static final String SERVER_RESPONSE_REMINDER_EMAIL_NO_SERIALS = "no serial numbers associated with account";
	
	
	// Data names (for use in maps and json)
	public static final String ATTR_SERVER_RESPONSE = "server response";
	public static final String ATTR_ACTIVE_IP_MAC_SET_IN_JSON = "active_MACs_in_json";
	public static final String ATTR_TOTAL_CLIENT_MAC_SET_IN_JSON = "all_MACs_in_json";
	public static final String ATTR_EMAIL = "email";
	public static final String ATTR_SERIAL = "serial";
	public static final String ATTR_PUBLIC_KEY = "public_key";
	public static final String ATTR_CLIENT_TIMESTAMP = "client_timestamp";
	public static final String ATTR_SERVER_TIMESTAMP = "server_timestamp";
	public static final String ATTR_MEERKAT_VERSION_NUMBER = "meerkat_version";
	public static final String ATTR_ENCRYPTED_MESSAGE_ARRAY_IN_JSON = "encrypted_message";
	public static final String ATTR_SALT = "salt";
	public static final String ATTR_CLIENT_IP = "ip";
	public static final String ATTR_WEB_FORM_USER_NAME = "name";
	public static final String ATTR_WEB_FORM_USER_COUNTRY = "country";
	public static final String ATTR_WEB_FORM_USER_INTENDED_MEERKAT_USAGE = "intendedUsage";
	public static final String ATTR_WEB_FORM_USER_INSTITUTION_TYPE = "institutionType";
	public static final String ATTR_SERVER_LOG_ERROR = "log_error";
	public static final String ATTR_SERVER_ERROR_OCCURRED = "error_occurred";
	public static final String ATTR_SERVER_EMAIL_ERROR = "email error";
	public static final String ATTR_EMAIL_TEXT = "email_text";
	public static final String ATTR_NUM_USAGE_ENTRIES = "number of usage entries";
	
	
	
	public static String hashMac(String macAddress, String salt, String timestamp) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String saltedMac = macAddress+timestamp+salt;
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");

		crypt.reset();
		crypt.update(saltedMac.getBytes("utf8"));
		String hashed = new BigInteger(1, crypt.digest()).toString(16);
		
		return hashed;
	}

}
