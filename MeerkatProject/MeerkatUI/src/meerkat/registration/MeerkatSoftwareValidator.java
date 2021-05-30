package meerkat.registration;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import config.LangConfig;
import config.StatusMsgsConfig;
import globalstate.MeerkatUI;
import java.awt.Desktop;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.application.HostServices;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import ui.dialogwindow.InfoDialog;

/**
 * This class serves as the client's access point to the registration server, as well
 * as the server's executable runnable jar for responding to the client's requests.
 * 
 * original author - everbeek
 * Put in Meerkat 2.0 
 *
 */
public class MeerkatSoftwareValidator{

	private static final String MESSAGE_SERVER_ERROR_TRY_AGAIN = "Server error when attempting to register. Please try again later, or contact Meerkat support at "+MeerkatValidationConstants.MEERKAT_EMAIL_ADDRESS;
	private static final String MESSAGE_NO_MATCHING_ACCOUNT_PREFIX = "There is no account corresponding to the email address and serial number:<br/>";
	private static final String MESSAGE_REVOKED_SERIAL_PREFIX = "We have revoked usage of the serial:<br/>";
	private static final String MESSAGE_ANOTHER_COMPUTER_PREFIX = "Another computer is already registered with the serial:<br/>";
	// For placing validation info, for use when offline.
	private static String MEERKAT_REG_DIR = "meerkat_files/reg/";
	
	private static String MEERKAT_REG_DATA_CACHE_FILE_NAME = "meerkat_valid.cache";
	static {
	// We want to have machine-customized locations for this to make it harder to copy, without having to store it in
	// OS-dependent temp directories or the like.
	try {
	String hostname = InetAddress.getLocalHost().getHostName();
	int characterSum = 0;
	for(int i = 0; i < hostname.length(); i++){
	characterSum += hostname.charAt(i);
	}
	String hostCode = hostname.length()+"-"+characterSum;
	MEERKAT_REG_DATA_CACHE_FILE_NAME = MEERKAT_REG_DIR+hostCode+"/"+MEERKAT_REG_DATA_CACHE_FILE_NAME;
	} catch (UnknownHostException e) {
	MEERKAT_REG_DATA_CACHE_FILE_NAME = MEERKAT_REG_DIR+MEERKAT_REG_DATA_CACHE_FILE_NAME;
	}
	}
	private static String MEERKAT_REG_SALT_FILE_NAME = MEERKAT_REG_DIR+"meerkat.regkey";
	private static String MEERKAT_SERIAL_FILE_NAME = MEERKAT_REG_DIR+"meerkat.serial";
	private static String MEERKAT_USAGE_LOG_FILE = MEERKAT_REG_DIR+"usage_statistics.log";
	static {
	// TabHandler must be created and initialized before MeerkatSoftwareValidator is constructed.
	// Seems that I really just want to fix up the path prefix then apply it to those paths. These were unnecessary.
//	MEERKAT_REG_DATA_CACHE_FILE_NAME = TabHandler.relMeerkatPath(MEERKAT_REG_DATA_CACHE_FILE_NAME);
//	MEERKAT_REG_SALT_FILE_NAME = TabHandler.relMeerkatPath(MEERKAT_REG_SALT_FILE_NAME);
//	MEERKAT_SERIAL_FILE_NAME = TabHandler.relMeerkatPath(MEERKAT_SERIAL_FILE_NAME);
//	MEERKAT_USAGE_LOG_FILE = TabHandler.relMeerkatPath(MEERKAT_USAGE_LOG_FILE);
	File prepDir = new File(MEERKAT_USAGE_LOG_FILE);
	prepDir.getParentFile().mkdirs();
	}
	public static final String MEERKAT_SRV_URL = "http://meerkat-srv.aicml.ca/meerkat2.0";
	private static final int SERVER_CONNECTION_TIMEOUT_MILLISECS = 5000;
	private static final String REGISTRATION_SERVER_URL = MEERKAT_SRV_URL + "/client_registration/registration_server.php";
        private static final String CHECK_LATEST_VERSION_NO_SERVER_URL = MEERKAT_SRV_URL + "/client_registration/check_latest_version_released.php";
	private static final String CHECK_LATEST_VERSION_NO_KEY =  "version_no";
        private static final String CHECK_LATEST_VERSION_NO_URL_KEY = "url";
        private static String MEERKAT_LATEST_RELEASE_VERSION = "";

	
	// Dialog strings
	private static final String TITLE_EMAIL_SERIAL_PROMPT = "Meerkat Registration Information";
	private static final String MESSAGE_EMAIL_SERIAL_PROMPT = "Please enter the email address you registered with on the Meerkat website \n and the Meerkat serial number emailed to you.";
	
	private static final String TITLE_MISSING_REGISTRATION_INFO = "Missing Information";
	private static final String MESSAGE_MISSING_REGISTRATION_INFO = "You didn't enter an email address and the Meerkat serial number associated with it. Please try again.";
	
	private static final String TITLE_INCOMPLETE_VALIDATION_SERVER_RESPONSE = "Server Validation Problem";
	private static final String MESSAGE_INCOMPLETE_VALIDATION_SERVER_RESPONSE = "The server sent back broken or invalid registration information. This is highly unusual!<br/>Please contact the Meerkat customer support, "+MeerkatValidationConstants.MEERKAT_EMAIL_ADDRESS+", with the email address and serial number you registered with.";
	
	private static final String TITLE_COMPUTER_MISMATCH = "Validation Problem";
	private static final String MESSAGE_COMPUTER_MISMATCH = "There appears to be a problem validating your Meerkat installation.<br/>Perhaps you are using a serial number that has been used to install Meerkat on a different computer?<br/>If you need help, please contact the Meerkat customer support, "+MeerkatValidationConstants.MEERKAT_EMAIL_ADDRESS+", with the email address you registered with when you downloaded Meerkat, or register for a copy on our website at:<br/>"+MeerkatValidationConstants.MEERKAT_HUMAN_WEBPAGE_URL;
	
	private static final String TITLE_CORRUPT_REGISTRATION_INFO = "Corrupt Registration Information";
	private static final String MESSAGE_CORRUPT_REGISTRATION_INFO = "Some of the registration information seems inconsistent.<br/>Please restart Meerkat, and try again.<br/>If this problem persists, please contact customer support at "+MeerkatValidationConstants.MEERKAT_EMAIL_ADDRESS+" for further assistance.";
	
	private static final String TITLE_MISSING_OR_CORRUPT_REGISTRATION_INFO = "Missing or Corrupt Registration Information";
	private static final String MESSAGE_MISSING_OR_CORRUPT_REGISTRATION_INFO = "Some of the registration information seems missing or corrupt.<br/>Please restart Meerkat, and try again.<br/>If this problem persists, please contact customer support at "+MeerkatValidationConstants.MEERKAT_EMAIL_ADDRESS+" for further assistance.";
	
	public static final String TITLE_INVALID_REGISTRATION = "Meerkat Registration Error";
	public static final String MESSAGE_INVALID_REGISTRATION = "There seems to be some problem with your registration details.<br/>Perhaps you entered the wrong email or serial, or this serial has been used on a different computer.<br/>Please contact the Meerkat customer support, "+MeerkatValidationConstants.MEERKAT_EMAIL_ADDRESS+", with the email address you registered with when you downloaded Meerkat, or register for a copy on our website at:<br/>"+MeerkatValidationConstants.MEERKAT_HUMAN_WEBPAGE_URL;
	
	public static final String TITLE_SERVER_UNAVAILBLE_FOR_FIRST_RUN = "Registration Server Unavailable";
	public static final String MESSAGE_SERVER_UNAVAILABLE_FOR_FIRST_RUN = "The server is not currently available for validating your registration details.<br/>Please try again later, or contact customer support at "+MeerkatValidationConstants.MEERKAT_EMAIL_ADDRESS+" for further assistance.";

	public static final String TITLE_SERVER_CANNOT_FIND_ACCOUNT = "Validation Error";
	public static final String MESSAGE_SERVER_CANNOT_FIND_ACCOUNT = "The server had a problem finding yoru account information.<br/>Please contact customer support at "+MeerkatValidationConstants.MEERKAT_EMAIL_ADDRESS+" for further assistance, with your registered email and serial number.";
	
	private static final String TITLE_ACCOUNT_REVOKED = "Meerkat Account Revoked";
	private static final String MESSAGE_ACCOUNT_REVOKED = "It appears that your Meerkat account with that serial number has been revoked.<br/>Please contact Meerkat customer support at "+MeerkatValidationConstants.MEERKAT_EMAIL_ADDRESS+" for further assistance, with yoru registered email and serial number.";

	private static final String GET_ASSISTANCE_SNIPPET = "contact Meerkat support at "+MeerkatValidationConstants.MEERKAT_EMAIL_ADDRESS+" with your registered email address and serial.";
	private static final String MESSAGE_PLEASE_GET_ASSISTANCE = "<br/>Please "+GET_ASSISTANCE_SNIPPET;
	private static final String MESSAGE_REGISTRATION_FAILED_SERVER_ERROR = "Registration has failed, possibly due to a server error. "+MESSAGE_PLEASE_GET_ASSISTANCE;
	private static final String MESSAGE_INCOMPATIBLE_VERSION_TYPE = "This version of Meerkat is incompatible with the version you have registered with this email and serial number combination."+MESSAGE_PLEASE_GET_ASSISTANCE;
	private static final String MESSAGE_PLEASE_REGISTER_AGAIN = "<br/>Please register for an additional serial at our website, "+MeerkatValidationConstants.MEERKAT_HUMAN_WEBPAGE_URL+" or "+GET_ASSISTANCE_SNIPPET;
	private static final String MESSAGE_PLEASE_DOWNLOAD_NEWER_VERSION = "<br/>Please download a newer version of Meerkat from "+MeerkatValidationConstants.MEERKAT_HUMAN_WEBPAGE_URL+" or "+GET_ASSISTANCE_SNIPPET;
	private static final String MESSAGE_INCOMPATIBLE_VERSION_NUMBER = "This version of Meerkat is incompatible with the version you have registered for."+MESSAGE_PLEASE_DOWNLOAD_NEWER_VERSION;

	/**
         * 
         * Variables added later to this file for MeerkatFx implementation
         */
        // PATHS
	public static final String MEERKAT_FILES_BASE_DIR_PATH = "meerkat_files";
	public static String MEERKAT_FILES_DIR_PATH = MEERKAT_FILES_BASE_DIR_PATH;
	public static String MEERKAT_DATA_DIR_PATH = MEERKAT_FILES_DIR_PATH + "/!_meerkat_data";
	public static String MEERKAT_APPLICATION_ICON = MEERKAT_FILES_DIR_PATH + "/icons/meerkat_icons/logo_48x48x32.png";
	public static String FIT_TO_SCREEN_ICON_FILE_PATH = MEERKAT_FILES_DIR_PATH
	+ "/icons/SilkCompanion1/icons/arrow_nw_ne_sw_se.png";
	public static String MEERKAT_RELEASE_VERSION_FILE_PATH = MEERKAT_FILES_DIR_PATH + "/" + "version_info/version.txt";

	public static String RECENT_DIRECTORY_SAVE_OPEN_PATH = MEERKAT_FILES_DIR_PATH + "/recentDirectoryPathStore.txt";
	public static String MEERKAT_RELEASE_VERSION = "Plus_Meerkat_2.0_v1.001";
        // version format = Meerkat_<MajorRelease_NewSoftware>_v<MajorRelease.00<MinorRelease>>
        
        private static Stage stageEmailSerialPopup = new Stage();

        
        
 /*****************************
 * Methods
 * 
 ****************************/
        
        
        
	/**
	 * Each time Meerkat is run, this method should be called. It determines if it needs to continue,
	 * or if the registration process has already been completed for the runnign version of Meerkat.
	 * 
	 * @return
	 */
	public boolean firstRun() {	
	// Check for evidence of prior registration.
	if(this.registrationFilesPresent()){
	// Already registered previously. Great!
	return true;
	} else {
	if(!this.registrationServerAvailable()){
	// Internet *must* be up for first run
	return false;
	}
                        
                        
	// Let's register this installation!
	Map<String, String> args = new HashMap<String, String>();
	// Send of the MAC, CPUid, email, serial, and receive a public key from the server.	
	
	while(true){ // Loop this until we return. Allows coming back to re-enter data.
	boolean firstThroughLoop = true; // Forces once through, as well as preventing missing data error message.
	while(firstThroughLoop || !args.containsKey(MeerkatValidationConstants.ATTR_EMAIL) || !args.containsKey(MeerkatValidationConstants.ATTR_SERIAL)){
	if(!firstThroughLoop){
	//Utils.displayWarningDialog(MeerkatSoftwareValidator.MESSAGE_MISSING_REGISTRATION_INFO, MeerkatSoftwareValidator.TITLE_MISSING_REGISTRATION_INFO, null, TabHandler.curObj);
	       displayWarningDialog(MeerkatSoftwareValidator.MESSAGE_MISSING_REGISTRATION_INFO, MeerkatSoftwareValidator.TITLE_MISSING_REGISTRATION_INFO);
                                        }
                                        // Oops! I came back and wrote the same method a second time. We'll see which one I like better now...
	// showEmailSerialRequestDialog(args);
	displayEmailSerialPopup(args); // Puts 'email' and 'serial' directly into the args container. Also populates fields from container.
	firstThroughLoop = false;
	}

	// Get MAC address as unique info. These are stored server side for later use in run validation.
	List<String> activeMacAddressSet;
	try {
	activeMacAddressSet = this.getCurrentlyUsedMACAddresses();
	} catch (SocketException e1) {
	// Unlikely, we fail here anyway.
	return false;
	}
	Collections.sort(activeMacAddressSet);
	Gson gson = new Gson();
	String activeIpMacSetJson = gson.toJson(activeMacAddressSet, new TypeToken<Collection<String>>() {}.getType());
	args.put(MeerkatValidationConstants.ATTR_ACTIVE_IP_MAC_SET_IN_JSON, activeIpMacSetJson);


	// TODO Should we allow people to register via the application and not necessitate that they go to the website?
	// We could always let this email entered result in a registration, and have the server email the user
	// their serial, as well as providing the serial immediately for this process.

	
	// The complete macset is also sent, so we can do a one-step exchange in the case that this is a fresh re-install and we need to re-register the client machine.
	// How do we obfuscate the macset? Do we need to?
	List<String> totalMacAddressSet;
	try {
	totalMacAddressSet = this.getAllMACAddresses();
	} catch (UnknownHostException e1) {
	// Unlikely, we fail here anyway.
	return false;
	} catch (SocketException e1) {
	// Unlikely, we fail here anyway.
	return false;
	}
	Collections.sort(totalMacAddressSet);
	String totalMacSetJson = gson.toJson(totalMacAddressSet, new TypeToken<Collection<String>>() {}.getType());
	args.put(MeerkatValidationConstants.ATTR_TOTAL_CLIENT_MAC_SET_IN_JSON, totalMacSetJson);

	args.put(MeerkatValidationConstants.ATTR_MEERKAT_VERSION_NUMBER, MEERKAT_RELEASE_VERSION);
	
                                
                                System.out.println(" ===========  Server input ======== ");
                                //for(String key : args.keySet()){
                                //    System.out.println(key + " :" + args.get(key) + "::");
                                //}
                                
	Gson gsonEncoder = new Gson();
                                //Gson gsonEncoder = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().crea‌​te();
                                String jsonFinal = gsonEncoder.toJson(args, new TypeToken<Map<String, String>>() {}.getType());
                                System.out.println(jsonFinal);
	Map<String, String> response = dispatchRequest(MeerkatValidationConstants.SERVER_COMMAND_REGISTER_CLIENT, jsonFinal);
                                System.out.println(" ===========  Server respose ======== ");
                                for(String key : response.keySet()){
                                    System.out.println(key + " :" + response.get(key));
                                }
                                
	// Bad response?
	if(null == response ||
	(null != response && !response.containsKey(MeerkatValidationConstants.ATTR_SERVER_RESPONSE))){
	// Why would this fail? Dunno, but we have to check before saving stuff.
	//Utils.showMessage(MESSAGE_SERVER_ERROR_TRY_AGAIN);
                                        showMessage(MESSAGE_SERVER_ERROR_TRY_AGAIN);
	return false;
	} else if(!MeerkatValidationConstants.SERVER_RESPONSE_REGISTRATION_SUCCESS_KEY.equals(response.get(MeerkatValidationConstants.ATTR_SERVER_RESPONSE))) {
	String serverResp = response.get(MeerkatValidationConstants.ATTR_SERVER_RESPONSE);

	if(MeerkatValidationConstants.SERVER_RESPONSE_NO_CORRESPONDING_ACCOUNT.equals(serverResp)){
	//Utils.showMessage(MESSAGE_NO_MATCHING_ACCOUNT_PREFIX+args.get(MeerkatValidationConstants.ATTR_EMAIL)+", "+args.get(MeerkatValidationConstants.ATTR_SERIAL)+MESSAGE_PLEASE_GET_ASSISTANCE);
	showMessage(MESSAGE_NO_MATCHING_ACCOUNT_PREFIX+args.get(MeerkatValidationConstants.ATTR_EMAIL)+", "+args.get(MeerkatValidationConstants.ATTR_SERIAL)+MESSAGE_PLEASE_GET_ASSISTANCE);
                                                continue; // re-loop to see dialog again
	} else if(MeerkatValidationConstants.SERVER_RESPONSE_ACCOUNT_SERIAL_REVOKED.equals(serverResp)) {
	//Utils.showMessage(MESSAGE_REVOKED_SERIAL_PREFIX+args.get(MeerkatValidationConstants.ATTR_SERIAL)+MESSAGE_PLEASE_GET_ASSISTANCE);
	showMessage(MESSAGE_REVOKED_SERIAL_PREFIX+args.get(MeerkatValidationConstants.ATTR_SERIAL)+MESSAGE_PLEASE_GET_ASSISTANCE);
	
                                                continue; // re-loop to see dialog again
                                                
	} else if(MeerkatValidationConstants.SERVER_RESPONSE_ACCOUNT_VERSION_TYPE_INCORRECT.equals(serverResp)) {
	//Utils.showMessage(MESSAGE_INCOMPATIBLE_VERSION_TYPE);
	showMessage(MESSAGE_INCOMPATIBLE_VERSION_TYPE);
	
                                                continue; // re-loop to see dialog again
	} else if(MeerkatValidationConstants.SERVER_RESPONSE_ACCOUNT_SERIAL_ALREADY_REGISTERED.equals(serverResp)){
	//Utils.showMessage(MESSAGE_ANOTHER_COMPUTER_PREFIX+args.get(MeerkatValidationConstants.ATTR_SERIAL)+MESSAGE_PLEASE_REGISTER_AGAIN);
	showMessage(MESSAGE_ANOTHER_COMPUTER_PREFIX+args.get(MeerkatValidationConstants.ATTR_SERIAL)+MESSAGE_PLEASE_REGISTER_AGAIN);
	
                                                continue; // re-loop to see dialog again
	} else if(MeerkatValidationConstants.SERVER_RESPONSE_ACCOUNT_VERSION_NUMBER_INCORRECT.equals(serverResp)) {
	//Utils.showMessage(MESSAGE_INCOMPATIBLE_VERSION_NUMBER);
	showMessage(MESSAGE_INCOMPATIBLE_VERSION_NUMBER);
	
                                                return false;
	} else {
	//Utils.showMessage(MESSAGE_REGISTRATION_FAILED_SERVER_ERROR);
	showMessage(MESSAGE_REGISTRATION_FAILED_SERVER_ERROR);
	
                                                return false;
	}
	} else{
	// if(MeerkatValidationConstants.SERVER_RESPONSE_REGISTRATION_SUCCESS_KEY.equals(response.get(MeerkatValidationConstants.ATTR_SERVER_RESPONSE))
	try{
	this.saveRegistrationInformation(response, args);
	// An actual success!
	return true;
	} catch (IOException e) {
	//Utils.showMessage("I/O exception while finishing registration. Please change the read and write permissions for the directory that Meerkat is in, and try loading Meerkat again.");
	showMessage("I/O exception while finishing registration. Please change the read and write permissions for the directory that Meerkat is in, and try loading Meerkat again.");
	
                                                // Fail-convenient? Nope! Get them to fix permissions. They will have to tolerate this inconvenience.
	this.clearSavedRegistrationData(); // Make sure there's no junk hanging out.
	return false;
	} catch(Exception e){
	//Utils.showMessage("Unknown error.Please contact customer support at "+MeerkatValidationConstants.MEERKAT_EMAIL_ADDRESS+" for further assistance.");
	showMessage("Unknown error.Please contact customer support at "+MeerkatValidationConstants.MEERKAT_EMAIL_ADDRESS+" for further assistance.");
	
                                                this.clearSavedRegistrationData(); // Make sure there's no junk hanging out.
	return false;
	}
	}
	}
	} // end of eternal loop
	}

        
        	/**
	 * Use this method for the actual sending and receiving of messages to and from the registration server.
	 * 
	 * @param command	The server recognized command that should be executed server side.
	 * @param args	A JSON stringified version of data that the server can deal with.
	 * @return
	 */
	private Map<String, String> dispatchRequest(String command, String jsonString){
	Map<String, String> structuredResponse = null;

	try {
	URL url = new URL(MeerkatSoftwareValidator.REGISTRATION_SERVER_URL);
	URLConnection conn = url.openConnection();
	conn.setConnectTimeout(MeerkatSoftwareValidator.SERVER_CONNECTION_TIMEOUT_MILLISECS); // Default is something like 20 seconds
	conn.setDoOutput(true); // Will lead to unknown host exception when host is unavailable.
	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	// TODO
	// Make the request here. What's our overall format?? The format for mining is a single JSON data clump attached to a single POST variable. Let's do that,
	// using the command as the POST variable and the already encoded JSON arguments as its value. 
	String postDataStr = "command="+URLEncoder.encode(command, "UTF-8")+"&";
	postDataStr += "data="+URLEncoder.encode(jsonString, "UTF-8");
	// Send data
	wr.write(postDataStr);
	wr.flush();

	// Get the response
	String jsonResponse = "";
	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	String line;
	if ((line = rd.readLine()) != null) {
	jsonResponse += line;
	}
	rd.close();
	
	// Parse the response. We always expect JSON from this server.
	Gson gson = new Gson();
	structuredResponse = gson.fromJson(jsonResponse, new TypeToken<Map<String, String>>() {}.getType());
                        
                } catch (UnknownHostException e) {
	structuredResponse = null; //redundant
	} catch (IOException e) {
	structuredResponse = null; //redundant
	} catch (JsonSyntaxException e){
	structuredResponse = null; //redundant
	}

	return structuredResponse;
	}
        

	public void checkIfNextVersionReleased(){
	String latestMeerkatReleaseVersionNo = "";
                String latestMeerkatReleaseVersionURL = "";
                Map<String, String> structuredResponse;
	try {
	URL url = new URL(MeerkatSoftwareValidator.CHECK_LATEST_VERSION_NO_SERVER_URL);
	URLConnection conn = url.openConnection();
	conn.setConnectTimeout(MeerkatSoftwareValidator.SERVER_CONNECTION_TIMEOUT_MILLISECS); // Default is something like 20 seconds
	conn.setDoOutput(true); // Will lead to unknown host exception when host is unavailable.
	OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	// TODO
	// Make the request here. What's our overall format?? The format for mining is a single JSON data clump attached to a single POST variable. Let's do that,
	// using the command as the POST variable and the already encoded JSON arguments as its value. 
	String postDataStr = MeerkatValidationConstants.ATTR_MEERKAT_VERSION_NUMBER+"="+URLEncoder.encode(MEERKAT_RELEASE_VERSION, "UTF-8");
	
	// Send data
	wr.write(postDataStr);
	wr.flush();

	// Get the version no
	String response = "";
	BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	String line;
	if ((line = rd.readLine()) != null) {
	response += line;
	}
	rd.close();
                        
                        // Parse the response. We always expect JSON from this server.
	Gson gson = new Gson();
	structuredResponse = gson.fromJson(response, new TypeToken<Map<String, String>>() {}.getType());
	
	// Parse the response. We always expect JSON from this server but this is just a string.
	latestMeerkatReleaseVersionNo = structuredResponse.get(MeerkatSoftwareValidator.CHECK_LATEST_VERSION_NO_KEY);
        MEERKAT_LATEST_RELEASE_VERSION = latestMeerkatReleaseVersionNo;
                        latestMeerkatReleaseVersionURL = structuredResponse.get(MeerkatSoftwareValidator.CHECK_LATEST_VERSION_NO_URL_KEY);
                        
                        
                          
                          
                          //Plus_Meerkat_2.0_v1.001
                          int intmajorReleaseNewSoftware = Integer.parseInt(latestMeerkatReleaseVersionNo.split("_")[2].split("\\.")[0]);
                          int intmajorRelaseVersion = Integer.parseInt(latestMeerkatReleaseVersionNo.split("_")[3].split("\\.")[0].substring(1));
                          int minorRelease = Integer.parseInt(latestMeerkatReleaseVersionNo.split("_")[3].split("\\.")[1]);
                          
                          int intmajorReleaseNewSoftware_local = Integer.parseInt( MEERKAT_RELEASE_VERSION.split("_")[2].split("\\.")[0]);
                          int intmajorRelaseVersion_local = Integer.parseInt(MEERKAT_RELEASE_VERSION.split("_")[3].split("\\.")[0].substring(1));
                          int minorRelease_local = Integer.parseInt(MEERKAT_RELEASE_VERSION.split("_")[3].split("\\.")[1]);
                          System.out.println(intmajorReleaseNewSoftware + " - " + intmajorRelaseVersion + " - " + minorRelease);
                          System.out.println(intmajorReleaseNewSoftware_local + " - " + intmajorRelaseVersion_local + " - " + minorRelease_local);
                          if(intmajorReleaseNewSoftware_local < intmajorReleaseNewSoftware || intmajorRelaseVersion_local < intmajorRelaseVersion ||  minorRelease_local < minorRelease){
                            //a new version has been released
                            //show dialogue that a new version has been released
                            showNextVersionReleaseDailog(latestMeerkatReleaseVersionNo, latestMeerkatReleaseVersionURL);
                          }
                          
                        
                        
	} catch (UnknownHostException e) {
	latestMeerkatReleaseVersionNo = null; //redundant
	} catch (IOException e) {
	latestMeerkatReleaseVersionNo = null; //redundant
	} catch (JsonSyntaxException e){
	latestMeerkatReleaseVersionNo = null; //redundant
	}catch(Exception e){
                
                }

	          
	}
    
        private static void showNextVersionReleaseDailog(String latestMeerkatReleaseVersionNo, String latestMeerkatReleaseVersionURL){
        
            Stage stgNextVersionReleaseDailog = new Stage();
            stgNextVersionReleaseDailog.initModality(Modality.APPLICATION_MODAL);
            
            Hyperlink link = new Hyperlink();
            link.setText(latestMeerkatReleaseVersionURL);
            link.setBorder(Border.EMPTY);
            link.setPadding(new Insets(0, 0, 0, 0));
            link.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    try{
                        if( Desktop.isDesktopSupported() )
                        {
                            new Thread(() -> {
                                   try {
                                       Desktop.getDesktop().browse( new URI( latestMeerkatReleaseVersionURL ) );
                                   } catch (Exception et) {
                                       System.out.println("Exception in hyperlink click in MeerkatSoftwareValdiator:showNextVersionReleaseDailog()");
                                   }
                               }).start();
                        }
                    }catch(Exception exp){
                        System.out.println("Exception in hyperlink click in MeerkatSoftwareValdiator:showNextVersionReleaseDailog()");
                    }
                }
            });
            
            HBox lblhbox = new HBox();
            Label lblAboutMeerkat = new Label("A new version of Meerkat (" + latestMeerkatReleaseVersionNo + ") is available at: ");   
            Label lblConfirmDwnld = new Label("Do you want to download it?");
            
            //creating a HBOX of labels and link
            lblhbox.getChildren().addAll(lblAboutMeerkat, link);

            // Build a VBox
            Button btnOK = new Button("OK");
            Button btnCancel = new Button("Cancel");
            
            //HBox for buttonsq
            HBox hbox = new HBox();
            hbox.getChildren().addAll(btnOK,btnCancel);
            hbox.setAlignment(Pos.CENTER);
            hbox.setPadding(new Insets(5,10,5,10));
            hbox.setSpacing(20);
            
            
            VBox vboxRows = new VBox(5);
            vboxRows.setPadding(new Insets(10,10,10,10));        
            vboxRows.getChildren().addAll(lblhbox, lblConfirmDwnld, hbox);

            
            Scene scnNextVersionReleaseDailog = new Scene(vboxRows);
            stgNextVersionReleaseDailog.initModality(Modality.APPLICATION_MODAL);
            stgNextVersionReleaseDailog.setTitle("New version released!");        
            stgNextVersionReleaseDailog.setResizable(false);


            // Events 
            btnOK.setOnAction((ActionEvent e) -> {
                // Close the dialog box
                stgNextVersionReleaseDailog.close();
                
                try{
                    if( Desktop.isDesktopSupported() )
                        {
                            new Thread(() -> {
                                   try {
                                       Desktop.getDesktop().browse( new URI( latestMeerkatReleaseVersionURL ) );
                                   } catch (Exception et) {
                                       System.out.println("Exception in MeerkatSoftwareValdiator:showNextVersionReleaseDailog()");
                                   }
                               }).start();
                        }
                }catch(Exception ex){
                    System.out.println("Exception in MeerkatSoftwareValdiator:showNextVersionReleaseDailog()");
                }
                
            });
            
            btnCancel.setOnAction((event) -> {
                stgNextVersionReleaseDailog.close();
            });

            stgNextVersionReleaseDailog.setScene(scnNextVersionReleaseDailog);
            stgNextVersionReleaseDailog.show();
 
        }
            
            
        
        
	/**
	 * Creates and shows a JDialog asking the user for their email address and serial, as registered on the Meerkat website.
	 * Will sanity check the results, and place them in the map container provided by the caller, indexed by 'email' and 'serial'.
	 */
        /*
	public void displayEmailSerialPopupOld(final Map<String, String> resultsContainer){
	
	// TODO Try this in all dialog constructors: GraphicsEnvironment.getLocalGraphicsEnvironment()
	
                
                //modified for meerkatfx
                //final JDialog parametersDialog = new JDialog(TabHandler.curObj.findTopmostMajorMeerkatWindow(), JDialog.ModalityType.TOOLKIT_MODAL);
	//put modality later
                final JDialog parametersDialog = new JDialog();
                parametersDialog.setTitle(MeerkatSoftwareValidator.TITLE_EMAIL_SERIAL_PROMPT);
	parametersDialog.setModal(true);
	int width = 50;
	int height = 30;
	parametersDialog.setMaximumSize(new Dimension(width,height));
	parametersDialog.setSize(parametersDialog.getMaximumSize());
	parametersDialog.addWindowListener(new WindowAdapter(){
	//public void windowClosed(WindowEvent e){
	//	System.exit(0);
	//}
	public void windowClosing(WindowEvent e){
	System.exit(0);
	}
	});
	
	JPanel paramPanel = new JPanel(new GridBagLayout());
	JPanel notePanel = new JPanel(new GridBagLayout());
	
	GridBagConstraints cons;
	
	notePanel.add(new JLabel(
	formatText(MeerkatSoftwareValidator.MESSAGE_EMAIL_SERIAL_PROMPT, 40)
	),
	gridBag(0, 0, 3, 1, 1, 1, new Insets(0,0,5,5), GridBagConstraints.WEST, true));

	// Email address
	paramPanel.add(new JLabel("Email"), gridBag(0, 1, 1, 1, 1, 1, new Insets(0,0,5,5), GridBagConstraints.EAST, false));
	
	final JTextField emailTxt = new JTextField(resultsContainer.get(MeerkatValidationConstants.ATTR_EMAIL));
	cons = gridBag(1, 1, 1, 1, 1, 1, new Insets(0,0,5,5), GridBagConstraints.WEST, true);
	cons.ipadx = 30;
	paramPanel.add(emailTxt, cons);
	
	// Serial number
	// Email address
	paramPanel.add(new JLabel("Serial"), gridBag(0, 2, 1, 1, 1, 1, new Insets(0,0,5,5), GridBagConstraints.EAST, false));
	
	final JTextField serialTxt = new JTextField(resultsContainer.get(MeerkatValidationConstants.ATTR_SERIAL));
	cons = gridBag(1, 2, 1, 1, 1, 1, new Insets(0,0,5,5), GridBagConstraints.WEST, true);
	cons.ipadx = 30;
	paramPanel.add(serialTxt, cons);
	
	
	// OK BUTTON
	JButton applyButton = new JButton("Register");	
	applyButton.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e) {	
	// Default them to two communities unless they specify a certain number
	String emailAddress = emailTxt.getText();
	String serialNum = serialTxt.getText();

	if(null == emailAddress || emailAddress.equals("") || null == serialNum || serialNum.equals("")){
	//Utils.displayWarningDialog("You forgot to enter either your email address or Meerkat serial number. Please try again.", "Missing Info", parametersDialog.getLocation(), null);
                                        displayWarningDialog("You forgot to enter either your email address or Meerkat serial number. Please try again.", "Missing Info");
	return;
	}
	
	// Let's go ahead and check the email/serial combination here? Hmm....could do lower down, right?
	// TODO Check these here or in caller>? Caller for now...

	// Save the values to the container provided for this dialog
	resultsContainer.put(MeerkatValidationConstants.ATTR_EMAIL, emailAddress);
	resultsContainer.put(MeerkatValidationConstants.ATTR_SERIAL, serialNum);
	
	// Close this dialog.
	parametersDialog.dispose();
	}	
	});
	
	// QUIT BUTTON
	JButton cancelButton = new JButton("Quit");
	cancelButton.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e) {	
	System.exit(0);	
	}	
	});
	cancelButton.setSize(applyButton.getSize());
	
	paramPanel.add(applyButton, gridBag(1, 3, 1, 1, 1, 1, new Insets(5,0,5,5), null, false));
	paramPanel.add(cancelButton, gridBag(1, 4, 1, 1, 1, 1, new Insets(5,0,0,0), null, false));
	parametersDialog.getRootPane().setDefaultButton(applyButton);
	
	JPanel outerPanel = new JPanel(new GridBagLayout());
	outerPanel.add(notePanel, gridBag(0, 0, 1, 1, 1, 1, new Insets(10,10,10,5), null, true));
	outerPanel.add(paramPanel, gridBag(0, 1, 1, 1, 1, 1, new Insets(10,10,10,5), null, true));
	parametersDialog.add(outerPanel);
	parametersDialog.pack();
	parametersDialog.setLocation(
	(int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth() - parametersDialog.getWidth())/2,
	(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() - parametersDialog.getHeight())/2
	);
	parametersDialog.setVisible(true);
                
	}
        */
        
        public void displayEmailSerialPopup(final Map<String, String> resultsContainer){
	
	
            stageEmailSerialPopup = new Stage();
            //dialog.initModality(Modality.APPLICATION_MODAL);
            stageEmailSerialPopup.setTitle(MeerkatSoftwareValidator.TITLE_EMAIL_SERIAL_PROMPT);

            VBox vBoxAll = new VBox();
            
            final Label messageLabel = new Label(MeerkatSoftwareValidator.MESSAGE_EMAIL_SERIAL_PROMPT);
            
            VBox vBoxMessages = new VBox();
            vBoxMessages.getChildren().add(messageLabel);
            
            HBox hoxEmail = new HBox();
            //generate text fields
            final TextField txtFieldEmail = new TextField();
            Label labelEmail = new Label("Email");
            hoxEmail.getChildren().addAll(labelEmail, txtFieldEmail);
            hoxEmail.setSpacing(5);
            hoxEmail.setAlignment(Pos.CENTER);
            
            
            HBox hoxSerial = new HBox();
            final TextField txtFieldSerial = new TextField();
            Label labelSerial = new Label("Serial");
            hoxSerial.getChildren().addAll(labelSerial, txtFieldSerial);
            hoxSerial.setSpacing(5);
            hoxSerial.setAlignment(Pos.CENTER);
            stageEmailSerialPopup.setOnCloseRequest((e) -> {
                e.consume();
                System.exit(0);
            
            });
            
            VBox vboxTxtFields = new VBox();
            vboxTxtFields.getChildren().addAll(hoxEmail, hoxSerial);
            vboxTxtFields.setSpacing(5);
            vboxTxtFields.setAlignment(Pos.CENTER);
            
            
            
            HBox hboxButtons = new HBox();

            Button btnRegister = new Button("Register");
            btnRegister.setOnAction((event) -> {

                event.consume();
                // Default them to two communities unless they specify a certain number
                String emailAddress = txtFieldEmail.getText();
                String serialNum = txtFieldSerial.getText();

                if(null == emailAddress || emailAddress.equals("") || null == serialNum || serialNum.equals("")){
                        //Utils.displayWarningDialog("You forgot to enter either your email address or Meerkat serial number. Please try again.", "Missing Info", parametersDialog.getLocation(), null);
                        displayWarningDialog("You forgot to enter either your email address or Meerkat serial number. Please try again.", "Missing Info");
                        //////errorLabel.setVisible(true);
                        //return;
                }

                // Let's go ahead and check the email/serial combination here? Hmm....could do lower down, right?
                // TODO Check these here or in caller>? Caller for now...

                // Save the values to the container provided for this dialog
                resultsContainer.put(MeerkatValidationConstants.ATTR_EMAIL, emailAddress);
                resultsContainer.put(MeerkatValidationConstants.ATTR_SERIAL, serialNum);

                // Close this dialog.
                stageEmailSerialPopup.close();

            });



            Button btnQuit = new Button("Quit");
            btnQuit.setOnAction((event) -> {
                event.consume();
                stageEmailSerialPopup.close();
                //TODO do system clean up etc as well
                System.exit(0);	
            });

            hboxButtons.getChildren().addAll(btnRegister, btnQuit);
            //hboxButtons.setPadding(new Insets(10));
            hboxButtons.setSpacing(20);
            hboxButtons.setAlignment(Pos.CENTER);
            
            VBox vBoxEnteries = new VBox();
            vBoxEnteries.getChildren().addAll(vboxTxtFields, hboxButtons);
            vBoxEnteries.setSpacing(15);
            vBoxAll.getChildren().addAll(vBoxMessages, vBoxEnteries);
            vBoxAll.setPadding(new Insets(20,20,20,20));
            vBoxAll.setSpacing(10);
            Scene scene = new Scene(vBoxAll);
            stageEmailSerialPopup.setScene(scene);
            stageEmailSerialPopup.setResizable(false);
            stageEmailSerialPopup.showAndWait();
        }
        
        

	/**
	 * Saves all registration information. Call with the response from the registration server.
	 * 
	 * @param response
	 * @throws IOException
	 */
	private void saveRegistrationInformation(Map<String, String> response, Map<String, String> args) throws IOException {
	this.saveRegisteredSerialNumberUserEmail(args);
	this.saveRegisteredSalt(response);
	}
	
	/**
	 * Save the user entered email address and serial number for future use.
	 * 
	 * @param args
	 * @throws IOException
	 */
	private void saveRegisteredSerialNumberUserEmail(Map<String, String> args) throws IOException {
	// Save the public key from the registration process, as well as the email and serial.
	File serialFile = new File(MeerkatSoftwareValidator.MEERKAT_SERIAL_FILE_NAME);
	if(!serialFile.exists()){
	try{
	serialFile.createNewFile();
	} catch(SecurityException e){
	throw e;
	}
	}
	serialFile.setWritable(true, false);
	serialFile.setReadable(true, false);

	FileWriter fstream = new FileWriter(serialFile);
	BufferedWriter out = new BufferedWriter(fstream);
	Map<String, String> serialEmailMap = new HashMap<String, String>();
	serialEmailMap.put(MeerkatValidationConstants.ATTR_EMAIL, args.get(MeerkatValidationConstants.ATTR_EMAIL));
	serialEmailMap.put(MeerkatValidationConstants.ATTR_SERIAL, args.get(MeerkatValidationConstants.ATTR_SERIAL));
	Gson gsonEncoder = new Gson();
	String jsonVersion = gsonEncoder.toJson(serialEmailMap, new TypeToken<Map<String, String>>() {}.getType());
	out.write(jsonVersion);
	out.close();
	}
	

	private void cacheRegData(String regData, String timestamp) throws IOException {
	File regCacheFile = new File(MEERKAT_REG_DATA_CACHE_FILE_NAME);
	
	if(!regCacheFile.exists()){
	try{
	regCacheFile.getParentFile().mkdirs();
	regCacheFile.createNewFile();
	regCacheFile.setWritable(true, false);
	regCacheFile.setReadable(true, false);
	} catch(SecurityException e){
	// Utils.showMessage("I/O exception. Please change the read and write permissions for the directory that Meerkat is in, and try loading Meerkat again.");
	throw e;
	}
	}

	FileWriter fstream = new FileWriter(regCacheFile); // Will replace contents
	BufferedWriter out = new BufferedWriter(fstream);

	Map<String, String> temp = new HashMap<String, String>();
	temp.put(MeerkatValidationConstants.ATTR_ENCRYPTED_MESSAGE_ARRAY_IN_JSON, regData);
	temp.put(MeerkatValidationConstants.ATTR_CLIENT_TIMESTAMP, timestamp); // Not server timestamp
	Gson gson = new Gson();
	String jsonData = gson.toJson(temp, new TypeToken<Map<String, String>>() {}.getType());
	out.write(jsonData);
	out.close();
	}

	/**
	 * Save registration key that we received from the server.
	 * 
	 * @param response
	 * @throws IOException
	 */
	private void saveRegisteredSalt(Map<String, String> response) throws IOException {
	// Save the key from the registration process, as well as the email and serial.
	File keyFile = new File(MeerkatSoftwareValidator.MEERKAT_REG_SALT_FILE_NAME);
	if(!keyFile.exists()){
	try{
	keyFile.createNewFile();
	} catch(SecurityException e){
	throw e;
	}
	}
	keyFile.setWritable(true, false);
	keyFile.setReadable(true, false);

	FileWriter fstream = new FileWriter(keyFile);
	BufferedWriter out = new BufferedWriter(fstream);
	Gson gson = new Gson();
	Map<String, String> justSalt = new HashMap<String, String>();
	justSalt.put(MeerkatValidationConstants.ATTR_SALT, response.get(MeerkatValidationConstants.ATTR_SALT));
	String jsonedSalt = gson.toJson(justSalt, new TypeToken<Map<String, String>>() {}.getType());
	out.write(jsonedSalt);
	out.close();
	}
	
	/**
	 * Call this method only when we need to clear all registration data, such as when running and the
	 * MAC from the server is not present client side.
	 * This is the method that could result in two users registering back and forth as they "take turns"
	 * using the email and serial. This is ok. One user can also do this. I would prefer to offer unlimited
	 * serial numbers for individual users.
	 * 
	 */
	public void clearSavedRegistrationData(){
	File publicKeyFile = new File(MeerkatSoftwareValidator.MEERKAT_REG_SALT_FILE_NAME);
	File serialFile = new File(MeerkatSoftwareValidator.MEERKAT_SERIAL_FILE_NAME);
	File cacheFile = new File(MeerkatSoftwareValidator.MEERKAT_REG_DATA_CACHE_FILE_NAME);
	
	publicKeyFile.delete();
	serialFile.delete();
	cacheFile.delete();
	}
	
	/**
	 * Checks to see if there are files stored for the email and serial combination, as well a file stored for
	 * the registration key used in the full version. We do require the public key to be present for Plus,
	 * even though we don't use it, simpyl to keep things simple and consistent.
	 * 
	 * @return
	 */
	private boolean registrationFilesPresent() {
	
                System.out.println("Meerkat - starting - Checking for registration files");
                File regKeyFile = new File(MeerkatSoftwareValidator.MEERKAT_REG_SALT_FILE_NAME);
	File serialFile = new File(MeerkatSoftwareValidator.MEERKAT_SERIAL_FILE_NAME);
	
	if(serialFile.exists() && regKeyFile.exists()){
	return true;
	} else {
	return false;
	}
	
	}

	/**
	 * Check to see if the registration server is available. If we are following a fail-convenient
	 * approach to registration, whenever this fails, the caller should elegantly pass further checks.
	 * 
	 * @return
	 */
	public boolean registrationServerAvailable() {
	boolean serverAvailable = false;
	try {
	URL url = new URL(MeerkatSoftwareValidator.REGISTRATION_SERVER_URL);
	URLConnection conn = url.openConnection();
	conn.setConnectTimeout(MeerkatSoftwareValidator.SERVER_CONNECTION_TIMEOUT_MILLISECS); // Default is something like 20 seconds
	    conn.setDoOutput(true); // Will lead to unknown host exception when host is unavailable.
	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
	    wr.write(" ");
	    wr.flush();
	    wr.close();
	    
	    // Get the response
	    String jsonResponse = "";
	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String line;
	    if ((line = rd.readLine()) != null) {
	    	jsonResponse += line;
	    }
	        rd.close();
	        Gson gson = new Gson();
	        Map<String, String> responseMap = gson.fromJson(jsonResponse, new TypeToken<Map<String, String>>() {}.getType());
	        String serverResponse = responseMap.get(MeerkatValidationConstants.ATTR_SERVER_RESPONSE);
	        if(serverResponse.equals(MeerkatValidationConstants.SERVER_RESPONSE_MISSING_COMMAND_DATA)){
	        	// We expect exactly this response to giving no info in the request.
	        	serverAvailable = true;
	        } else {
	        	serverAvailable = false;
	        }
	} catch (UnknownHostException e) {
	serverAvailable = false;
	} catch (IOException e) {
	serverAvailable = false;
	}
	
	    return serverAvailable;
	}

	/**
	 * Send usage data to the server, from the usage data file. By using a file for the complete
	 * message, we can send usage data stored there during times when the internet was down.
	 * It's not much harder, and we get slightly more accurate statistics later.
	 * 
	 */
	public void logUsage() {
	// Read email and serial as stored in file. If file is missing, fail-convenient and let
	// this pass on through. The program has run and failed to save these because of the server
	// being down or inaccessible to this client. We let these first runs slip through under these circumstances.
	try{

	Map<String, String> structuredResponse = getEmailAndSerial();
	if(null == structuredResponse){
	// If this is null, we need to re-register anyway. Let's do it.
	this.clearSavedRegistrationData();	
	//Utils.showMessage(MeerkatSoftwareValidator.MESSAGE_INVALID_REGISTRATION, MeerkatSoftwareValidator.TITLE_INVALID_REGISTRATION);
	showMessage(MeerkatSoftwareValidator.MESSAGE_INVALID_REGISTRATION, MeerkatSoftwareValidator.TITLE_INVALID_REGISTRATION);
	
                        System.exit(0);
	}
	
	structuredResponse.put(MeerkatValidationConstants.ATTR_CLIENT_TIMESTAMP, new Timestamp(new Date().getTime()).toString());
	structuredResponse.put(MeerkatValidationConstants.ATTR_MEERKAT_VERSION_NUMBER, MEERKAT_RELEASE_VERSION);
	
	structuredResponse.put(MeerkatValidationConstants.ATTR_CLIENT_IP, InetAddress.getLocalHost().getHostAddress());

	File usageLog = new File(MeerkatSoftwareValidator.MEERKAT_USAGE_LOG_FILE);
	Gson gson = new Gson();
	try{
	//First, append usage data to log file.
	boolean newFile = usageLog.createNewFile();
	FileWriter fstreamWrite = new FileWriter(usageLog, true);
	BufferedWriter out = new BufferedWriter(fstreamWrite);
	if(!newFile){
	out.write("\n");
	}
	out.write(gson.toJson(structuredResponse, new TypeToken<Map<String, String>>() {}.getType()));
	out.close();

	// Next, send log file contents to server, if it is available
	Map<String, String> logData = new HashMap<String, String>();
	BufferedReader usageLogIn = new BufferedReader(new FileReader(usageLog));
	String line = "";
	Integer i = 1; // as useless index to map. We just need a map for consistency elsewhere, or we'd use an array.
	while(null != (line = usageLogIn.readLine())){
	//Map<String, String> lineData = gson.fromJson(line, new TypeToken<Map<String, String>>() {}.getType());
	logData.put(i.toString(), line);
	i++;
	}
	usageLogIn.close();
	logData.put(MeerkatValidationConstants.ATTR_NUM_USAGE_ENTRIES, (i-1)+""); // One less, due to late increment in loop
	
	// We don't really need a response from the server per se...
	String logDataJson = gson.toJson(logData, new TypeToken<Map<String, String>>() {}.getType());
	Map<String, String> serverResponse = dispatchRequest(MeerkatValidationConstants.SERVER_COMMAND_LOG_CLIENT_USAGE, logDataJson);

	// Lastly, if the server received the log file contents, wipe that log file.
	if(null != serverResponse && serverResponse.containsKey(MeerkatValidationConstants.ATTR_SERVER_RESPONSE) && MeerkatValidationConstants.SERVER_RESPONSE_LOG_SUCCESS_KEY.equals(serverResponse.get(MeerkatValidationConstants.ATTR_SERVER_RESPONSE))){
	usageLog.delete();
	}

	} catch (FileNotFoundException e1) {
	// The email serial file might not be present. This is normally handled before this method call,
	// so we'll fail-convenient on this, and let this one usage slip through the cracks.
	// Fail-convenient!
	}
	} catch (SecurityException e) {
	// Maybe we cannot write to the log? Can't say why...
	// Fail-convenient!
	} catch (IOException e) {
	// This could be anything. Is the directory not writable? Is it missing?
	// Fail-convenient here too!
	}
	
	}

	
	/**
	 * This method retrieves the email and serial from the file they are stored in, if it exists.
	 * The values will be keyed as they are stored in the file (likely as 'email', and 'serial').
	 * 
	 * @return
	 * @throws IOException
	 */
	private Map<String, String> getEmailAndSerial() throws IOException {
	// Get email and serial
	File emailSerialFile = new File(MeerkatSoftwareValidator.MEERKAT_SERIAL_FILE_NAME);
	
	if(null == emailSerialFile || !emailSerialFile.exists() || !emailSerialFile.canRead()){
	return null;
	}
	
	BufferedReader emailSerialIn = new BufferedReader(new FileReader(emailSerialFile));
	String emailSerialFileContents = emailSerialIn.readLine();
	emailSerialIn.close();
	
	Gson gson = new Gson();
	Map<String, String> emailSerialContainer = gson.fromJson(emailSerialFileContents, new TypeToken<Map<String, String>>() {}.getType());
	if(!emailSerialContainer.containsKey(MeerkatValidationConstants.ATTR_EMAIL)
	|| !emailSerialContainer.containsKey(MeerkatValidationConstants.ATTR_SERIAL)){
	return null;
	} else {
	return emailSerialContainer;
	}
	}
	
	private Map<String, String> getRegistrationSalt() throws IOException {	
	// Get the registration key
	File regSaltFile = new File(MeerkatSoftwareValidator.MEERKAT_REG_SALT_FILE_NAME);
	
	if(null == regSaltFile || !regSaltFile.exists() || !regSaltFile.canRead()){
	return null;
	}
	
	BufferedReader regSaltIn = new BufferedReader(new FileReader(regSaltFile));
	String regSaltFileContents = regSaltIn.readLine();
	regSaltIn.close();
	
	Gson gson = new Gson();
	return gson.fromJson(regSaltFileContents, new TypeToken<Map<String, String>>() {}.getType());
	}
	
	private Map<String, String> getCachedRegistrationData() throws IOException {	
	// Get the registration key
	File regCacheFile = new File(MEERKAT_REG_DATA_CACHE_FILE_NAME);
	
	if(null == regCacheFile || !regCacheFile.exists() || !regCacheFile.canRead()){
	return null;
	}
	
	BufferedReader regCacheIn = new BufferedReader(new FileReader(regCacheFile));
	String regCacheFileContents = regCacheIn.readLine();
	regCacheIn.close();
	
	Gson gson = new Gson();
	return gson.fromJson(regCacheFileContents, new TypeToken<Map<String, String>>() {}.getType());
	}

	public boolean validateClient() {
	Gson gson = new Gson();
	Map<String, String> validationData;
	try {
	// Get salt, timestamp, email, serial. All but salt go to server.
	String timestamp = new Timestamp(new Date().getTime()).toString();
	
	Map<String, String> regSaltContainer = getRegistrationSalt();
	if(null == regSaltContainer){
	//Utils.showMessage(MeerkatSoftwareValidator.MESSAGE_CORRUPT_REGISTRATION_INFO, MeerkatSoftwareValidator.TITLE_CORRUPT_REGISTRATION_INFO);
	showMessage(MeerkatSoftwareValidator.MESSAGE_CORRUPT_REGISTRATION_INFO, MeerkatSoftwareValidator.TITLE_CORRUPT_REGISTRATION_INFO);
	
                                return false;
	}
	String salt = regSaltContainer.get(MeerkatValidationConstants.ATTR_SALT);
	
	validationData = this.getEmailAndSerial();

	if(null == validationData){
	//Utils.showMessage(MeerkatSoftwareValidator.MESSAGE_CORRUPT_REGISTRATION_INFO, MeerkatSoftwareValidator.TITLE_CORRUPT_REGISTRATION_INFO);
	showMessage(MeerkatSoftwareValidator.MESSAGE_CORRUPT_REGISTRATION_INFO, MeerkatSoftwareValidator.TITLE_CORRUPT_REGISTRATION_INFO);
	
                                return false;
	}
	
	validationData.put(MeerkatValidationConstants.ATTR_CLIENT_TIMESTAMP, timestamp);
	validationData.put(MeerkatValidationConstants.ATTR_MEERKAT_VERSION_NUMBER, MEERKAT_RELEASE_VERSION);
	
	// If this validates, store the data, with timestamp, for when the net is down, and check for that file.
	// Do not let it validate with either an old file or an internet connection, and prefer the internet connection.	
	Collection<String> registeredMacSet = null;
	if(this.registrationServerAvailable()){
	// get registration info, hashed, from server
	// We don't really need a response from the server per se...
	Map<String, String> serverResponse = dispatchRequest(MeerkatValidationConstants.SERVER_COMMAND_VALIDATE_CLIENT, gson.toJson(validationData, new TypeToken<Map<String, String>>() {}.getType()));
	
	// Good response?
	if(null != serverResponse && serverResponse.containsKey(MeerkatValidationConstants.ATTR_SERVER_RESPONSE) && MeerkatValidationConstants.SERVER_RESPONSE_VALIDATE_CLIENT.equals(serverResponse.get(MeerkatValidationConstants.ATTR_SERVER_RESPONSE))){
	// Iterate over mac addresses, hash them, see if any match any of those sent back (double loop is actually necessary!)
	registeredMacSet  = gson.fromJson(serverResponse.get(MeerkatValidationConstants.ATTR_ENCRYPTED_MESSAGE_ARRAY_IN_JSON), new TypeToken<Collection<String>>() {}.getType());
	// Cache for later, if server is temporarily unavailable.
	this.cacheRegData(serverResponse.get(MeerkatValidationConstants.ATTR_ENCRYPTED_MESSAGE_ARRAY_IN_JSON), timestamp);
	} else {
	// Skip rest of processing, just deal with errors.
	registeredMacSet = null;
	

	if(null == serverResponse){
	// This is a general error that could happen somewhere. An exception on the server for example.
	//Utils.showMessage(MeerkatSoftwareValidator.MESSAGE_INCOMPLETE_VALIDATION_SERVER_RESPONSE, MeerkatSoftwareValidator.TITLE_INCOMPLETE_VALIDATION_SERVER_RESPONSE);
	showMessage(MeerkatSoftwareValidator.MESSAGE_INCOMPLETE_VALIDATION_SERVER_RESPONSE, MeerkatSoftwareValidator.TITLE_INCOMPLETE_VALIDATION_SERVER_RESPONSE);
	
                                                return false;
	} else  if(!serverResponse.containsKey(MeerkatValidationConstants.ATTR_SERVER_RESPONSE)){
	// This is a general error that could happen somewhere. An exception on the server for example.
	//Utils.showMessage(MeerkatSoftwareValidator.MESSAGE_INCOMPLETE_VALIDATION_SERVER_RESPONSE, MeerkatSoftwareValidator.TITLE_INCOMPLETE_VALIDATION_SERVER_RESPONSE);
	showMessage(MeerkatSoftwareValidator.MESSAGE_INCOMPLETE_VALIDATION_SERVER_RESPONSE, MeerkatSoftwareValidator.TITLE_INCOMPLETE_VALIDATION_SERVER_RESPONSE);
	
                                                return false;
	} 
	
	String serverRespMessage = serverResponse.get(MeerkatValidationConstants.ATTR_SERVER_RESPONSE);

	if(MeerkatValidationConstants.SERVER_RESPONSE_NO_CORRESPONDING_ACCOUNT.equals(serverRespMessage)){
	// This is a weird possibility, since we draw this info from the file that was only saved if registration succeeded.
	//Utils.showMessage(MeerkatSoftwareValidator.MESSAGE_SERVER_CANNOT_FIND_ACCOUNT, MeerkatSoftwareValidator.TITLE_SERVER_CANNOT_FIND_ACCOUNT);
	showMessage(MeerkatSoftwareValidator.MESSAGE_SERVER_CANNOT_FIND_ACCOUNT, MeerkatSoftwareValidator.TITLE_SERVER_CANNOT_FIND_ACCOUNT);
	
                                                return false;
	} else if(MeerkatValidationConstants.SERVER_RESPONSE_ACCOUNT_SERIAL_REVOKED.equals(serverRespMessage)){
	// This is fine. Tell them and quit.
	//Utils.showMessage(MeerkatSoftwareValidator.MESSAGE_ACCOUNT_REVOKED, MeerkatSoftwareValidator.TITLE_ACCOUNT_REVOKED);
	showMessage(MeerkatSoftwareValidator.MESSAGE_ACCOUNT_REVOKED, MeerkatSoftwareValidator.TITLE_ACCOUNT_REVOKED);
	
                                                return false;
	} else if(MeerkatValidationConstants.SERVER_RESPONSE_CANNOT_VALIDATE_UNREGISTERED_ACCOUNT.equals(serverRespMessage)){
	// Need to register. For some reason the server didn't find the data from registration.
	//Utils.showMessage(MeerkatSoftwareValidator.MESSAGE_INVALID_REGISTRATION, MeerkatSoftwareValidator.TITLE_INVALID_REGISTRATION);
	showMessage(MeerkatSoftwareValidator.MESSAGE_INVALID_REGISTRATION, MeerkatSoftwareValidator.TITLE_INVALID_REGISTRATION);
	
                                                return false;
	} else if(MeerkatValidationConstants.SERVER_RESPONSE_ACCOUNT_VERSION_TYPE_INCORRECT.equals(serverRespMessage)){
	//Utils.showMessage(MESSAGE_INCOMPATIBLE_VERSION_TYPE);
	showMessage(MESSAGE_INCOMPATIBLE_VERSION_TYPE);
	
                                                return false;
	} else if(MeerkatValidationConstants.SERVER_RESPONSE_ACCOUNT_VERSION_NUMBER_INCORRECT.equals(serverRespMessage)) {
	//Utils.showMessage(MESSAGE_INCOMPATIBLE_VERSION_NUMBER);
	showMessage(MESSAGE_INCOMPATIBLE_VERSION_NUMBER);
	
                                                return false;
	} else if(!MeerkatValidationConstants.SERVER_RESPONSE_VALIDATE_CLIENT.equals(serverRespMessage)){
	// default fail. Unknown problem! What do I say about this?
	//Utils.showMessage(MeerkatSoftwareValidator.MESSAGE_INCOMPLETE_VALIDATION_SERVER_RESPONSE, MeerkatSoftwareValidator.MESSAGE_INCOMPLETE_VALIDATION_SERVER_RESPONSE);
	showMessage(MeerkatSoftwareValidator.MESSAGE_INCOMPLETE_VALIDATION_SERVER_RESPONSE, MeerkatSoftwareValidator.MESSAGE_INCOMPLETE_VALIDATION_SERVER_RESPONSE);
	
                                                return false;
	} else {
	// This is a general error that could happen somewhere. An exception on the server for example.
	//Utils.showMessage(MeerkatSoftwareValidator.MESSAGE_INCOMPLETE_VALIDATION_SERVER_RESPONSE, MeerkatSoftwareValidator.TITLE_INCOMPLETE_VALIDATION_SERVER_RESPONSE);
	showMessage(MeerkatSoftwareValidator.MESSAGE_INCOMPLETE_VALIDATION_SERVER_RESPONSE, MeerkatSoftwareValidator.TITLE_INCOMPLETE_VALIDATION_SERVER_RESPONSE);
	
                                                return false;
	}
	}
	
	} else {
	// get registration info, hashed, from cached previous attempt
	Map<String, String> cachedData = getCachedRegistrationData();
	//System.out.println(cachedData.get(MeerkatValidationConstants.ATTR_ENCRYPTED_MESSAGE_ARRAY_IN_JSON));
	registeredMacSet = gson.fromJson(cachedData.get(MeerkatValidationConstants.ATTR_ENCRYPTED_MESSAGE_ARRAY_IN_JSON), new TypeToken<Collection<String>>() {}.getType());
	// We need  to overwrite with the cached timestamp, in order to validate properly.
	timestamp = cachedData.get(MeerkatValidationConstants.ATTR_CLIENT_TIMESTAMP);
	}
	
	// Wherever we got the info from, see if it validates.
	if(null == registeredMacSet){
	// Not allowed to run if there isn't a valid response from the server
	// Any error messages should be more specific and handled above.
	return false;
	} else {
	ArrayList<NetworkInterface> machineNetIntSet = Collections.list(NetworkInterface.getNetworkInterfaces());

	boolean macIsPresent = false;
	// For each registered mac...
	for(String registeredHashMac: registeredMacSet){
	// ...is there a corresponding mac currently on the machine?
	for (NetworkInterface netint : machineNetIntSet){
	try {
	// TODO Are the macs from the server individually hashed? I hope so!
	String machineMac = Arrays.toString(netint.getHardwareAddress());
	String hashedMachineMac = MeerkatValidationConstants.hashMac(machineMac, salt, timestamp);
	if(hashedMachineMac.equals(registeredHashMac)){
	macIsPresent = true;
	break;
	}
	} catch (NoSuchAlgorithmException e) {
	// Not going to happen, but we have to deal with it anyway.
	macIsPresent = false;
	//Utils.showMessage("Java error.", "Client Error");
	showMessage("Java error.", "Client Error");
	
                                                        continue;
	}
	}
	}
	
	// Validate MAC address retrieved form server (registration time MAC) against MACs available on client system.
	//boolean macIsPresent = this.isThisMACAddressOnThisSystem(macAddress);
	if(!macIsPresent){
	//Utils.showMessage(MeerkatSoftwareValidator.MESSAGE_COMPUTER_MISMATCH, MeerkatSoftwareValidator.TITLE_COMPUTER_MISMATCH);
                                        showMessage(MeerkatSoftwareValidator.MESSAGE_COMPUTER_MISMATCH, MeerkatSoftwareValidator.TITLE_COMPUTER_MISMATCH);
	
                                }
	return macIsPresent;
	}
	} catch (IOException e) {
	// This could be anything. Is the email and serial file missing? It shouldn't be...let's clear their registration and get them to register yet again.
	// How would this happen in the normal sequence of registration and validation events? It wouldn't.
	//Utils.showMessage(MeerkatSoftwareValidator.MESSAGE_MISSING_OR_CORRUPT_REGISTRATION_INFO, MeerkatSoftwareValidator.TITLE_MISSING_OR_CORRUPT_REGISTRATION_INFO);
	showMessage(MeerkatSoftwareValidator.MESSAGE_MISSING_OR_CORRUPT_REGISTRATION_INFO, MeerkatSoftwareValidator.TITLE_MISSING_OR_CORRUPT_REGISTRATION_INFO);
	
                        return false;
	}

	}

	// Keep this for testing things out, at least for a while.
	@SuppressWarnings("unused")
	private void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
	System.out.printf("Display name: %s\n", netint.getDisplayName());
	System.out.printf("Name: %s\n", netint.getName());
	Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();

	for (InetAddress inetAddress : Collections.list(inetAddresses)) {
	System.out.printf("InetAddress: %s\n", inetAddress);
	}
	System.out.printf("Up? %s\n", netint.isUp());
	System.out.printf("Loopback? %s\n", netint.isLoopback());
	System.out.printf("PointToPoint? %s\n", netint.isPointToPoint());
	System.out.printf("Supports multicast? %s\n", netint.supportsMulticast());
	System.out.printf("Virtual? %s\n", netint.isVirtual());
	System.out.printf("Hardware address: %s\n",
	Arrays.toString(netint.getHardwareAddress()));
	System.out.printf("MTU: %s\n", netint.getMTU());
	System.out.printf("Parent: %s\n", null!=netint.getParent() ? netint.getParent().getName() : "null" );

//	for (InterfaceAddress item  : netint.getInterfaceAddresses()) {
//	System.out.printf("Interface Address: %s\n", item.getAddress());
//	}

	System.out.printf("\n");
	}
	 

//	 /**
//	  * This method checks to see if the MAC address provided exists among all of
//	  * the network interfaces on this system.
//	  * 
//	  * @param macAddress
//	  * @return
//	  * @throws SocketException	If we cannot access the interfaces
//	  */
//	 private boolean isThisMACAddressOnThisSystem(String macAddress) throws SocketException{
//	 Enumeration<NetworkInterface> nets;
//	 nets = NetworkInterface.getNetworkInterfaces();
//	 for (NetworkInterface netint : Collections.list(nets)){
//	 if(netint.getHardwareAddress().toString().equals(macAddress)){
//	 return true;
//	 }
//	 }
//
//	 return false;
//	 }
	
	private String getClientIPAddress() {
	String strIPAddress = null;
	Socket local = null;
	InetAddress objLocalHost = null;
	try {
	local = new Socket("www.google.com", 80);
	objLocalHost = local.getLocalAddress();
	strIPAddress = objLocalHost.getHostAddress();
	} catch (Exception ex) {
	ex.printStackTrace();
	strIPAddress = "";
	} finally {
	try {
	local.close();
	} catch (IOException ioe) {
	// Nothing...
	}
	}
	if("".equals(strIPAddress)){
	try {
	strIPAddress = InetAddress.getLocalHost().getHostAddress();
	} catch (UnknownHostException e) {
	// Nothing...
	}
	}
	return strIPAddress;
	}
	 
	 /**
	  * This method determines the MAC address for the network interface that is currently being used to access the internet.
	  * The goal is to find the MAC address of a network interface that will likely be in the user's system
	  * for a long time. The only way I thought of to know this with any confidence is to see what the user's internal IP
	  * is, and look for the network interface that has an IP matching that, and treating this as their internet MAC.
	  * 
	  * We need to receive multiple MAC addresses because multiple devices can apparently be mapped to the same IP,
	  * and therefore multiple ones can be mapped to the IP associated with the local host. THere is no unambiguous way
	  * to determine which is "best".
	  * 
	  * What I do with this MAC address is important elsewhere. Treat this method as axiomatic...
	  * 
	  * @return
	  * @throws UnknownHostException	If the host does not have an IP
	  * @throws SocketException	If we cannot access the interfaces
	  */
	 private List<String> getCurrentlyUsedMACAddresses() throws SocketException{
	 List<String> macAddresses =  new ArrayList<String>();
	 
	 String internalIPAddress = getClientIPAddress();

	 Enumeration<NetworkInterface> nets;
	 nets = NetworkInterface.getNetworkInterfaces();
	 for (NetworkInterface netint : Collections.list(nets)){
	 if(!netint.isUp()){
	 continue;
	 }
	 //displayInterfaceInformation(netint);
	 for(InetAddress addy: Collections.list(netint.getInetAddresses())){
	 if(addy.getHostAddress().equals(internalIPAddress)){
	 // TODO The database gets some blank MACs (empty array really). Perhaps the adapter can match the IP and have a blank MAC?
	 // TODO I should probably try to gather all of them that match the IP and take the best one (that isn't empty).
	 // TODO It also shows up as 'null' in the database sometimes, but I can't think of a solution for that.
	 // TODO Actually, that null is super suspicious since it is not an empty array! What's up with that?
	 // Collect all possible MACs that have among their bound IP addresses the IP used for accessing the internet.
	 macAddresses.add(Arrays.toString(netint.getHardwareAddress())); // In array printout form, not standard hex and colon format.
	 }
	 }
	 }
	 return macAddresses;
	 }
	 
	 /**
	  * This vmethod gets every mac address in use. This is useful for allowing re-registration of a client machine,
	  * because we can send all of these addresses to the server for comparisons, reducing re-registration code complexity.
	  * 
	  * @return
	  * @throws UnknownHostException
	  * @throws SocketException
	  */
	 private List<String> getAllMACAddresses() throws UnknownHostException, SocketException{
	 List<String> macAddresses =  new ArrayList<String>();
	 
	 Enumeration<NetworkInterface> nets;
	 nets = NetworkInterface.getNetworkInterfaces();
	 for (NetworkInterface netint : Collections.list(nets)){
	 macAddresses.add(Arrays.toString(netint.getHardwareAddress())); // In array printout form, not standard hex and colon format.
                 }
	 return macAddresses;
	 }
         
         
         
         
         /**
	 * This method takes the input string and separates into wrapLength long lines by
	 * adding <br> tags after wrapLength number of characters.<br>
	 * 
	 * @param text the input string that will be split into multiple lines
	 * @param wrapLength the maximum number of characters per line
	 * @return the formatted string complete with <html> and </html> tags
	 */
	public static String formatText(String text, int wrapLength){
	String formattedString = "<html>";
	String lineBrokenText = text.replaceAll("<br/>", " <br/> "); // Make sure we break any html linebreaks into tokens.
	String[] tokens = lineBrokenText.split(" ");
	int lengthCounter = 0;
	for(String token : tokens){
	if(token.equals("<br/>")){
	// Line is broken here, so we start the count over.
	formattedString += token;
	lengthCounter = 0;
	} else if ((lengthCounter + token.length()) < wrapLength){
	formattedString += token + " ";
	lengthCounter += token.length();
	}
	else if(token.length() < wrapLength){
	lengthCounter = token.length();
	formattedString += "<br>" + token + " ";	
	}
	else{
	String subToken = token.substring(0, wrapLength - 1);
	formattedString += "<br>" + subToken + "<br>" + token.substring(wrapLength - 1) + " ";
	lengthCounter = token.substring(wrapLength - 1).length();
	}
	}
	
//	for (i = 0; i + wrapLength < text.length(); i = i + wrapLength){
//	formattedString = formattedString + text.substring(i,i+wrapLength) + "<br>";
//	}
//	if (i < text.length()){
//	formattedString = formattedString + text.substring(i,text.length());
//	}
	formattedString = formattedString + "</html>";
	
	return formattedString;
	}
        
        
        
        
        //Utils.displayWarningDialog(MeerkatSoftwareValidator.MESSAGE_MISSING_REGISTRATION_INFO, MeerkatSoftwareValidator.TITLE_MISSING_REGISTRATION_INFO, null, TabHandler.curObj);
        public static void displayWarningDialog(final String warningMessage, String title){
        
            //TODO 
            //Utils.displayWarningDialog()
            showMessage(warningMessage, -1);
        }
        
        
        public static void showMessage(String message, String title){
            InfoDialog.Display(message, -1);
        }
        
        public static void showMessage(String message){
            showMessage(message, -1);
        }
        
        public static void showMessage(String message, int pintTimeOutInSeconds){

            Stage stgInfoDialog = new Stage();

            WebView webView = new WebView();
            webView.getEngine().loadContent(formatText(message, 100));
            webView.setPrefHeight(200);

            Label lblInfo = new Label(message);
            lblInfo.setWrapText(true);
            lblInfo.setMaxWidth(400);
            
            Button btnOK = new Button("Try Again");
            btnOK.setOnAction(e -> {
                stgInfoDialog.close();
            });
            btnOK.setCancelButton(true);        
            btnOK.setAlignment(Pos.CENTER);


            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.setAlignment(Pos.CENTER);

            ColumnConstraints column1 = new ColumnConstraints();
            column1.setHalignment(HPos.CENTER);
            grid.getColumnConstraints().add(column1); 

            grid.add(webView, 0  , 0);
            grid.add(btnOK, 0  , 1);


            // Build a VBox
            /*
            VBox vboxRows = new VBox(5);        
            vboxRows.setPadding(new Insets(5,5,5,5));
            vboxRows.getChildren().addAll(lblInfo, btnOK);
                    */

            //Scene scnNewProjectWizard = new Scene(vboxRows);
            Scene scnInfoDialog = new Scene(grid);
            scnInfoDialog.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent evt) -> {
                if (evt.getCode().equals(KeyCode.ESCAPE)) {
                    stgInfoDialog.close();
                    MeerkatUI UIInstance = MeerkatUI.getUIInstance();
                    UIInstance.getController().updateStatusBar(false, StatusMsgsConfig.WINDOW_CLOSED);
                }
            });

            stgInfoDialog.initModality(Modality.APPLICATION_MODAL);
            stgInfoDialog.setTitle(LangConfig.GENERAL_INFORMATION); 
            stgInfoDialog.setResizable(false);
            stgInfoDialog.setMaxWidth(300);
            stgInfoDialog.setMinWidth(300);
            
            
            
            

            stgInfoDialog.setScene(scnInfoDialog);
            stgInfoDialog.showAndWait();

            if (pintTimeOutInSeconds > 0) {
                // Close the dialog box after few seconds
                PauseTransition delay = new PauseTransition(Duration.millis(pintTimeOutInSeconds*1000));
                delay.setOnFinished( event -> stgInfoDialog.close() );
                delay.play();
            }
        }
        
        public static String getMeerkatVersion(){
            return MEERKAT_LATEST_RELEASE_VERSION;
        }

        
}
