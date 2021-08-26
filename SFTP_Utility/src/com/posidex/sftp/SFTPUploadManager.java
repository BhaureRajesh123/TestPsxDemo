package com.posidex.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.posidex.customanttasks.StringEncrypter;

public class SFTPUploadManager {

	public static final Logger logger = Logger.getLogger(SFTPUploadManager.class.getName());

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
		SFTPUploadManager sftpUploadManager = new SFTPUploadManager();

		sftpUploadManager.init();
		// logger.info("solProp :: "+solProp);

		boolean flag = false;
		String srcSysList = null;
		String privakeypath = null;

		String host = null;
		String username = null;
		String pwd = null;
		int port;
		int timeOut = 0;
		boolean isPvtKeyEnabled = false;

		try {
			srcSysList = solProp.getProperty("SOURCE_SYSTEMS");
			logger.info("Configured List for src system is::" + srcSysList);
			privakeypath = solProp.getProperty("privakeypath");
			String[] srcSysArr = srcSysList.split("\\,");
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
				if (solProp.getProperty("isPvtKeyEnabled") != null) {
					isPvtKeyEnabled = Boolean.parseBoolean(solProp.getProperty("isPvtKeyEnabled"));
				} else {
					logger.info("isPvtKeyEnabled value is not available");
				}
				if (host != null && username != null && pwd != null) {
					SFTPLPropBean1 sftpBean = new SFTPLPropBean1(host, username, pwd, port, timeOut);

					logger.info("Calling upload method for system:::" + srcSysArr[i]);

					SftpFileDownloader.upload(solProp, srcSysArr[i], sftpBean, isPvtKeyEnabled, privakeypath);
				} else {
					throw new Exception("SFTP details are not available");

				}

			}
			generateFile(solProp, srcSysList, "Success", "Upload Process success");

		} catch (Exception e) {
			generateFile(solProp, srcSysList, "Failure", e.getMessage());
			logger.error(e, e);
			throw e;
		}
	}

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
			myWriter = new FileWriter(solProp.getProperty("FILE_LOC") + "/" + status + ".txt");
			myWriter.write(message);
			myWriter.close();
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
