package com.posidex.sftp;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.posidex.customanttasks.StringEncrypter;

public class SFTPDownloadManager {

	public static final Logger logger = Logger.getLogger(SFTPDownloadManager.class.getName());
	static Properties dbProp = new Properties();
	static Properties solProp = new Properties();

	public void init() throws Exception {
		logger.info("inside init()");
		try {
			solProp.load(SFTPDownloadManager.class.getClassLoader().getResourceAsStream("sftpsolution.properties"));
			DOMConfigurator.configure(solProp.getProperty("log4jLocation"));
		} catch (Exception cnf) {
			logger.error("Error while loading the properties");
			throw cnf;
		}
	}

	public static void main(String[] args) throws Exception {
		SFTPDownloadManager sftpDownloadManager = new SFTPDownloadManager();
		
		// logger.info("solProp :: "+solProp);

		HashMap<String, String> listOfFiles = null;
		String srcSysList = null;
		String privakeypath = null;

		String host = null;
		String username = null;
		String pwd = null;
		int port;
		int timeOut = 0;
		String isPvtKeyEnabled = null;

		boolean isFileAvaialable = false;
		String[] srcSysArr = null;
	

		try {
			sftpDownloadManager.init();
			srcSysList = solProp.getProperty("SOURCE_SYSTEMS");
			logger.info("Configured List for src system is::" + srcSysList);
			privakeypath = solProp.getProperty("privakeypath");
			srcSysArr = srcSysList.split("\\,");
			for (int i = 0; i < srcSysArr.length; i++) {
				logger.info("Source system:: " + srcSysArr[i]);
				host = solProp.getProperty(srcSysArr[i] + "_SFTPHOST");
				username = solProp.getProperty(srcSysArr[i] + "_SFTPUSERNAME");
				if (Boolean.parseBoolean(solProp.getProperty("isPwdEncrypted"))) {
					pwd = StringEncrypter.decrypt(solProp.getProperty(srcSysArr[i] + "_SFTP_ENC_PWD"));
				} else {
					pwd = solProp.getProperty(srcSysArr[i] + "_SFTP_PWD");
				}
				port = Integer.parseInt(solProp.getProperty("PORT"));
				timeOut = Integer.parseInt(solProp.getProperty("SFTP_timoeout"));
				isPvtKeyEnabled = solProp.getProperty("isPvtKeyEnabled");
				
				logger.info("host :: " + host + " username :: " + username + " isPvtKeyEnabled :: " + isPvtKeyEnabled
						+ " :: timeOut  ::" + timeOut);

				if (host != null && username != null && pwd != null && isPvtKeyEnabled != null) {
					SFTPLPropBean1 sftpBean = new SFTPLPropBean1(host, username, pwd, port, timeOut);

					logger.info("Calling download method for system:::" + srcSysArr[i]);
					/*
					 * Below method would return true/flase, if the files downloaded successfully
					 * then return true else return false
					 */
					isFileAvaialable = SftpFileDownloader.getDownload(solProp, srcSysArr[i], sftpBean,
							Boolean.parseBoolean(isPvtKeyEnabled), privakeypath);
					logger.info("isFileAvaialable :: " + isFileAvaialable);

					if (isFileAvaialable) {

						String locaFileLocation = solProp.getProperty(srcSysArr[i] + "_LOCAL_PATH");
						String rejectLocation = solProp.getProperty(srcSysArr[i] + "_REJECT_PATH");
						File files = new File(locaFileLocation);
						String[] listofFiles = files.list();
						logger.info("local file location is::" + locaFileLocation);
						// String filename = solProp.getProperty(srcSysArr[i] + "_FILENAME");
					}

				} else {
					throw new Exception("SFTP details are not available");
				}

			}
			generateFile(solProp, srcSysList, "Success", "Downloading the files from SFTP is successful");
			System.exit(0);
		} catch (Exception e) {
			generateFile(solProp, srcSysList, "Failure", e.getMessage());
			logger.error(e, e);
			throw e;
		} 

	}

	/**
	 * @param solProp
	 * @param srcStsm
	 * @param status
	 * @param message
	 * @throws Exception
	 */
	private static void generateFile(Properties solProp, String srcStsm, String status, String message)
			throws Exception {
		logger.info("inside generateFile with arguments srcStsm :: " + srcStsm + ", status:: " + status + ", message:: "
				+ message);
		File file = null;
		FileWriter myWriter = null;
		try {
			logger.info(status + ".txt");
			/*
			 * file = new File(solProp.getProperty("FILE_LOC") + "/" + srcStsm + "_" +
			 * status + ".txt"); file.createNewFile();
			 */
			myWriter = new FileWriter(solProp.getProperty("FILE_LOC") + "/" + status + "_"
					+ SftpFileDownloader.getFormattedDate(new Date(), "ddMMyyyyhhmmss") + ".txt");
			myWriter.write(message);
			myWriter.flush();

		} catch (Exception e) {
			logger.info("Erro while creating the file, error cause is:: " + e.getMessage());
			throw e;
		} finally {
			if (myWriter != null)
				myWriter.close();
		}

		logger.info("leaving generateFile");
	}

}