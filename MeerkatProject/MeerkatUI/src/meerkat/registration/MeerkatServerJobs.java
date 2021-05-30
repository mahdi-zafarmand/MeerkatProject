/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meerkat.registration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static meerkat.registration.MeerkatSoftwareValidator.showMessage;

/*
 * Note : MeerkatServerJobs, MeerkatSoftwareValidator and MeerkatValidationConstants have been taken from Meerkat Lite and made to work 
 * for Meerkat 2.0. The registration and validation process is same as that of Meerkat Lite. 
 * 
 */
public class MeerkatServerJobs {
    
    // STRINGS
	public static final String MEERKAT_SRV_URL = "http://meerkat-srv.aicml.ca/meerkatPlus";
	public static final String MINING_SERVER_URL = MEERKAT_SRV_URL + "/mining_server/mining_java_dispatcher.php";
	public static final String CHANGE_LOG_URL = MEERKAT_SRV_URL + "/change_logs/changes.php";
	public static final String CHANGE_LOG_VERSION_PLUS_URL = MEERKAT_SRV_URL + "/change_logs/plus_most_recent_version.txt";
	public static final String CHANGE_LOG_VERSION_FULL_URL = MEERKAT_SRV_URL + "/change_logs/full_most_recent_version.txt";
	private static final String CHANGE_LOG_OUT_OF_DATE_TITLE = "Change File Update Failed";
	private static final String CHANGE_LOG_OUT_OF_DATE_MESSAGE = "The change log file could not be updated from the website.<br/>There might be a problem on the server, but there may be permission problems in your fiel system.<br/>Please check the file permissions in the Meerkat subdirectory '!_meerkat_data/help' directory to ensure Meerkat can write to it.";
    
    //TODO remove unused strings or meerkatFX    
    // PATHS
	public static final String MEERKAT_FILES_BASE_DIR_PATH = "meerkat_files";
	public static String MEERKAT_FILES_DIR_PATH = MEERKAT_FILES_BASE_DIR_PATH;
	public static String MEERKAT_DATA_DIR_PATH = MEERKAT_FILES_DIR_PATH + "/!_meerkat_data";
	public static String MEERKAT_APPLICATION_ICON = MEERKAT_FILES_DIR_PATH + "/icons/meerkat_icons/logo_48x48x32.png";
	
	public static String MEERKAT_RELEASE_VERSION_FILE_PATH = MEERKAT_FILES_DIR_PATH + "/" + "version_info/version.txt";
	private static String START_PAGE_FILE_PATH = MeerkatServerJobs.MEERKAT_FILES_DIR_PATH + "/help/intro.html";
	public static String RECENT_DIRECTORY_SAVE_OPEN_PATH = MEERKAT_FILES_DIR_PATH + "/recentDirectoryPathStore.txt";
	public static String topJarParentPath = null;

	// Not needed in MeerkatFx 
        //When running from a jar, we need to pop up out of this directory (where the jar will be located) in order to access
	// the meerkat_files directory, and others.
	//public final static String JAR_PATH_SEGMENT = "/Meerkat.app/Contents/Resources/Java";

	// RESTRICTIONS
        //Not being used in plus - remove them later
	public static final int MAX_PLUS_NODES = 500;
	public static final int MAX_PLUS_EDGES = 2000;
	public static final int MAX_LARGE_FOR_LAYOUT_NODES = 400;
	public static final int MAX_LARGE_FOR_LAYOUT_EDGES = 1000;
	private static final int SERVER_CONNECTION_TIMEOUT_MILLISECS = 5000;



	// NOTE: Only use these with their respective getters.
	private static String releaseString = null;
	private static String majorVersionNumberString = null;

	private static MeerkatSoftwareValidator meerkatValidator;
    
    
    
    public static void startupRegistrationValidationCheck() {
		meerkatValidator = new MeerkatSoftwareValidator();

		if (MeerkatServerJobs.isDeveloperVersion()) {
			return;
		}
                //check if os bit version and java bit version match
               //if they match then proceed - otherwise exit with a message
               // that "Java bit and OS bit do not match"
                System.out.println("======= Java bit ==== " + System.getProperty("sun.arch.data.model"));
                System.out.println( "======= OS bit ==== " +System.getProperty("os.arch"));
                if(!System.getProperty("sun.arch.data.model").equals(getOSBitNo())){
                   //os and java bit version do not match
                   showMessage("Java bit number and Operating System bit do not match.");
                   System.exit(0);
               }
                //meerkatValidator.checkIfNextVersionReleased();
		// Is this the first usage after installation? Perform first time registration process.
		boolean success = meerkatValidator.firstRun();

		if (!success) {
			// Server didn't like this first handshake. Give message to contact customer support, halt start of Meertkat for
			// full version.
			// For Plus, this isn't really a deal breaker, and we will probably log the client details server side for the
			// next step.
			if (!meerkatValidator.registrationServerAvailable()) {
				RegistrationUtil.showMessage(MeerkatSoftwareValidator.MESSAGE_SERVER_UNAVAILABLE_FOR_FIRST_RUN,
						MeerkatSoftwareValidator.TITLE_SERVER_UNAVAILBLE_FOR_FIRST_RUN);
			} else {
				RegistrationUtil.showMessage(MeerkatSoftwareValidator.MESSAGE_INVALID_REGISTRATION,
						MeerkatSoftwareValidator.TITLE_INVALID_REGISTRATION);
			}
			// TODO Exit or nag here? First registration might be a different story than later validation. Give thought.
			System.exit(0);
		}

		// Log usage for Meerkat, as well as perform validation for Full version.
		meerkatValidator.logUsage();

		// Validate running copy. This uses things prepared during the first run after installation.
		// This does contacts the server unless the server is unavailble, in which case it tries using
		// the registration cached details.
		if (!isDeveloperVersion()) {
			// Any messages should come from down below, since we want some diagnostic information if they have problems.
			boolean valid = meerkatValidator.validateClient();

			if (!valid) {
				// Client didn't validate against info stored from first run registration process. Abort Meerkat run!
				// Clear the keys and stuff, to force a re-registration
				// This relies heavily on fail-convenient, so that we only do this when the user actually has completely
				// invalid data.
				meerkatValidator.clearSavedRegistrationData();

				// Perhaps we really want a nag screen displayed here instead of halting the run? As one poster put it,
				// perhaps a friend of a user is trying it out for the first time...
				// TODO To exit or to nag? Exit for now...
				System.exit(0);
			}
		}
	}
    
        private static String getOSBitNo(){

            String osName = System.getProperty("os.name").toLowerCase();
            System.out.println(osName);
            if(osName.contains("windows")){
            
            String arch = System.getenv("PROCESSOR_ARCHITECTURE");
            String wow64Arch = System.getenv("PROCESSOR_ARCHITEW6432");

            String realArch = arch.endsWith("64")
                  || wow64Arch != null && wow64Arch.endsWith("64")
                      ? "64" : "32";
            
            return realArch;
        
            }
            else{
            
                String strbitNo = System.getProperty("os.arch");

                if(strbitNo.contains("64")){
                    return "64";
                }
                return "32";
            }
        }
    
    
    	/**
	 * Lazy initializing accessor for the meerkat version.
	 * 
	 * @return
	 */
	public static String getMeerkatReleaseVersionString() {
		// TODO Later we might want a strong method of version checking to prevent
		// people from using one full version for different people.
		// As it is now, one can just share a full version executable, or take a single
		// file from it and add it to a Plus version. These identical levels of security.
		if (null == releaseString) {
			releaseString = "unknown";

			File versionFile = new File(MEERKAT_RELEASE_VERSION_FILE_PATH);

			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(versionFile));
				// We currently expect the first line in the file to be the version string.
				// Obviously this could change later, but it should be super easy to access.
				String line = in.readLine();
				releaseString = line;
				in.close();
			} catch (IOException e) {
				RegistrationUtil.displayWarningDialog(
						"Meerkatversion files or folder is missing. Please re-extract it from the Meerkat zip file. Meerkat will not function properly without this resource available. ("
								+ MEERKAT_RELEASE_VERSION_FILE_PATH + ").", "Warning");
				System.exit(0);
			}
		}
		return releaseString;
	}

	public String getMeerkatMajorVersionString() {
		if (null == majorVersionNumberString) {
			String fullVersion = getMeerkatReleaseVersionString();
			Pattern versionPattern = Pattern.compile(".+_v([0-9]+[.][0-9]+)_.+");
			Matcher m1 = versionPattern.matcher(fullVersion);
			if (m1.matches()) {
				majorVersionNumberString = m1.group(1);
			} else {
				majorVersionNumberString = "0.0"; // Not sure what else to do, but it should never happen really.
			}
		}
		return majorVersionNumberString;
	}

    
    
    
    	private enum MeerkatVersion {
		PLUS,
		DEV,
		FULL;
	}

	/**
	 * Don't use this directly. Always use the accessor, getMeerkatVersion().
	 */
	private static MeerkatVersion version = null;

	/**
	 * Lazy initializing accessor for the meerkat version.
	 * 
	 * @return
	 */
	private static MeerkatVersion getMeerkatVersion() {
		// TODO Later we might want a strong method of version checking to prevent
		// people from using one full version for different people.
		// As it is now, one can just share a full version executable, or take a single
		// file from it and add it to a PLUS version. These identical levels of security.
		if (null == version) {
			// Default to PLUS to be safe and restrictive in case the file is deleted.
			version = MeerkatVersion.PLUS;

			File plusLicenseFile = new File(MeerkatServerJobs.MEERKAT_FILES_DIR_PATH + "/" + "licenses/MEERKAT_PLUS_LICENSE.txt");
			File devLicenseFile = new File(MeerkatServerJobs.MEERKAT_FILES_DIR_PATH + "/" + "licenses/MEERKAT_DEV_UNLICENSED.txt");
			File fullLicenseFile = new File(MeerkatServerJobs.MEERKAT_FILES_DIR_PATH + "/" + "licenses/MEERKAT_LICENSE.txt");
			// File meerkatFiles = new File(TabHandler.MEERKAT_FILES_DIR_PATH);
			// JOptionPane.showMessageDialog(TabHandler.curObj, TabHandler.MEERKAT_FILES_DIR_PATH+" "+(meerkatFiles.exists()
			// ? " exists " : "doesn't exist"));
			// JOptionPane.showMessageDialog(TabHandler.curObj,
			// "Looking for license "+devLicenseFile.toString()+" and "+fullLicenseFile);

			if (devLicenseFile.exists()) {
				version = MeerkatVersion.DEV;
			} else if (fullLicenseFile.exists()) {
				version = MeerkatVersion.FULL;
			} else if (plusLicenseFile.exists()) {
				// Default, but we'll be explicit anyhow.
				version = MeerkatVersion.PLUS;
			}

		}
		return version;
	}

	public static boolean isFullVersion() {
		return MeerkatServerJobs.getMeerkatVersion() == MeerkatVersion.FULL;
	}

	public static boolean isPlusVersion() {
		return MeerkatServerJobs.getMeerkatVersion() == MeerkatVersion.PLUS;
	}

	public static boolean isDeveloperVersion() {
		return MeerkatServerJobs.getMeerkatVersion() == MeerkatVersion.DEV;
	}
        
        public static void checkIfNextVersionReleased(){
            meerkatValidator.checkIfNextVersionReleased();
        }
    
}