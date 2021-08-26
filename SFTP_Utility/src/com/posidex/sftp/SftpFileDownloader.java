package com.posidex.sftp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

/**
 * SftpTitanFileDownloader class is used to download the files from SFTP server
 * to our local file system
 * 
 * @author deepak
 *
 */
public class SftpFileDownloader {
	public static final Logger logger = Logger.getLogger(SftpFileDownloader.class.getName());
	public static Session jsession = null;
	static SFTPLPropBean1 sftpBean = null;
	static Vector<ChannelSftp.LsEntry> listOfFiles = null;
	static String lastUpdatedFileDate = "";

	/**
	 * getDownload() method is used to download the files from SFTP server to our
	 * local file system
	 * 
	 * @param configProp
	 *            represents the properties file
	 * @param sourceSystem
	 *            represents SourceSystem name
	 * @param sftplProBean
	 *            represents SFTP connection details
	 * @param privateKeyenabled
	 *            represents whether private key is enabled or disabled
	 * @param privakeypath
	 *            represents private key path
	 * @return the list of files which we downloded from the SFTP server
	 * @throws Exception
	 *             throw an Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean getDownload(Properties solprops, String sourceSystem, SFTPLPropBean1 sftplProBean,
			boolean privateKeyenabled, String privakeypath) throws Exception {
		logger.info("Inside getDownload method");

		sftpBean = sftplProBean;
		ChannelSftp.LsEntry lsEntry = null;
		JSch jsch = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		String host = "";
		String useName = "";
		String pwd = "";
		String dateFormat = null;
		String fileName = "";
		String uploadFileName = "";

		int noOfFilesToRead = 0;
		int port = 0;
		boolean isFileAvaialable = false;

		try {
			host = sftpBean.getSftpHostName();
			useName = sftpBean.getSftpUser();
			port = sftpBean.getPort();
			pwd = sftpBean.getSftpPWD();
			dateFormat = solprops.getProperty(sourceSystem + "_DATEFORMAT");

			// String controlFileName = solprops.getProperty("ControlFileName");
			String controlFileName = solprops.getProperty(sourceSystem + "_CONTROLFILE");
			controlFileName = controlFileName.replace("<<dateformat>>",
					new SimpleDateFormat(dateFormat).format(new Date()));
			logger.info("controlFileName:: " + controlFileName);
			String sftpSourcePath = solprops.getProperty(sourceSystem + "_SFTP_PATH");
			logger.info("source file location is::" + sftpSourcePath);
			String locaFileLocation = solprops.getProperty(sourceSystem + "_LOCAL_PATH");
			logger.info("local file location is::" + locaFileLocation);
			String filename = solprops.getProperty(sourceSystem + "_FILENAME");

			filename = filename.replace("<<dateformat>>", new SimpleDateFormat(dateFormat).format(new Date()));
			logger.info("file name for " + sourceSystem + " is::" + filename);

			logger.info("SFTP Connection Details ::" + "\n" + "User name :: " + useName + " Host Name :: " + host
					+ " Port :: " + port);

			jsch = new JSch();
			jsession = jsch.getSession(useName, host, port);
			if (privateKeyenabled) {
				jsch.addIdentity(privakeypath);
			}
			jsession.setPassword(pwd);
			jsession.setConfig("StrictHostKeyChecking", "no");
			jsession.setTimeout(sftpBean.getTimeOut());
			jsession.connect();

			if (!jsession.isConnected()) {
				jsession.connect();
			}
			logger.info("Session connection ::" + jsession.isConnected());
			channel = (ChannelSftp) jsession.openChannel("sftp");
			channel.connect();
			logger.info("Channel connection ::" + channel.isConnected());
			logger.info("dateFormat:: " + dateFormat);
			String currentDate = new SimpleDateFormat(dateFormat).format(new Date());

			if (channel.isConnected()) {
				if (Boolean.parseBoolean(solprops.getProperty("IS_DATE_ENABLED"))) {
					channelSftp = (ChannelSftp) channel;
					logger.info("SFTP path after opening::" + sftpSourcePath + "/" + currentDate);// upload/sftp/ddMMyyyy
					channelSftp.cd(sftpSourcePath + "/" + currentDate);

					listOfFiles = channelSftp.ls(sftpSourcePath + "/" + currentDate);

					Collections.sort(listOfFiles);// Gives files in sorting order
					if (listOfFiles.isEmpty()) {
						logger.warn(
								"There is no file availbale in " + sftpSourcePath + "/" + currentDate + " Location");
					} else {
						logger.info("listOfFiles.size():::is" + listOfFiles.size());

						for (int i = 0; i < listOfFiles.size(); i++) {
							lsEntry = (com.jcraft.jsch.ChannelSftp.LsEntry) listOfFiles.get(i);
							fileName = lsEntry.getFilename();
							logger.info("expected filename is ::: "+filename);
							logger.info("actual filename is ::"+fileName);
							if (fileName.startsWith(filename)) {
								isFileAvaialable = true;
								channelSftp.get(sftpSourcePath + "/" + currentDate + "/" + fileName,
										locaFileLocation + "/" + fileName);
							} else if (controlFileName.equals(fileName)) {
								channelSftp.get(sftpSourcePath + "/" + currentDate + "/" + fileName,
										locaFileLocation + "/" + fileName);
							}
						} // for

					}
				} else {
					channelSftp = (ChannelSftp) channel;
					logger.info("SFTP path after opening::" + sftpSourcePath);// upload/sftp
					channelSftp.cd(sftpSourcePath);

					listOfFiles = channelSftp.ls(sftpSourcePath);

					Collections.sort(listOfFiles);// Gives files in sorting order
					if (listOfFiles.isEmpty()) {
						logger.warn("There is no file availbale in " + sftpSourcePath + " Location");
					} else {
						logger.info("listOfFiles.size():::is" + listOfFiles.size());

						for (int i = 0; i < listOfFiles.size(); i++) {
							lsEntry = (com.jcraft.jsch.ChannelSftp.LsEntry) listOfFiles.get(i);
							fileName = lsEntry.getFilename();
							/*
							 * if(filename.equals(fileName)) { isFileAvaialable=true;
							 * channelSftp.get(sftpSourcePath + "/" + fileName, locaFileLocation + "/" +
							 * fileName, new SFTPProgressMonitor()); }else
							 * if(controlFileName.equals(fileName)) { channelSftp.get(sftpSourcePath + "/" +
							 * fileName, locaFileLocation + "/" + fileName, new SFTPProgressMonitor()); }
							 */
							if (fileName.startsWith(filename)) {
								isFileAvaialable = true;
								channelSftp.get(sftpSourcePath + "/" + fileName, locaFileLocation + "/" + fileName,
										new SFTPProgressMonitor());
							} else if (controlFileName.equals(fileName)) {
								channelSftp.get(sftpSourcePath + "/" + fileName, locaFileLocation + "/" + fileName,
										new SFTPProgressMonitor());
							}
						} // for
						logger.info("File download completed");
					}
				}

			}
		} catch (Exception e) {
			logger.error("Error while reading File from FTP Location::" + e.getMessage());
			logger.error(e, e);
			throw e;
		} finally {
			if (jsession != null)
				jsession.disconnect();
			if (channelSftp != null)
				channelSftp.disconnect();
			if (pwd != null)
				pwd = null;
		}
		return isFileAvaialable;
	}

	public static boolean upload(Properties solprops, String sourceSystem, SFTPLPropBean1 sftplProBean,
			boolean privateKeyenabled, String privakeypath) throws Exception {

		logger.info("Inside upload method");
		HashMap<String, String> resultMap = new HashMap<String, String>();
		JSch jsch = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		String host = "";
		String useName = "";
		int port = 0;
		String pwd = "";
		ChannelSftp.LsEntry lsEntry = null;

		String dateFormat = null;

		String fileName = "";
		String uploadFileName = "";
		String controlFileName = "";
		boolean flag = false;

		sftpBean = sftplProBean;
		try {
			host = sftpBean.getSftpHostName();
			useName = sftpBean.getSftpUser();
			port = sftpBean.getPort();
			pwd = sftpBean.getSftpPWD();
			dateFormat = solprops.getProperty(sourceSystem + "_DATEFORMAT");

			// String controlFileName = solprops.getProperty("ControlFileName");
			controlFileName = solprops.getProperty(sourceSystem + "_UPLOAD_CONTROLFILE");
			controlFileName = controlFileName.replace("<<dateformat>>",
					new SimpleDateFormat(dateFormat).format(new Date()));
			logger.info("controlFileName:: " + controlFileName);
			String sourcepath = solprops.getProperty(sourceSystem + "_UPLOAD_HANDOFF_PATH");
			logger.info("source file location is::" + sourcepath);
			String uploadSFTPpath = solprops.getProperty(sourceSystem + "_UPLOAD_SFTP_PATH");
			logger.info("sftp location is::" + uploadSFTPpath);
			uploadFileName = solprops.getProperty(sourceSystem + "_UPLOAD_FILENAME");
			uploadFileName = uploadFileName.replace("<<dateformat>>",
					new SimpleDateFormat(dateFormat).format(new Date()));
			logger.info("Hnaodoff file name :: " + uploadFileName);
			logger.info("SFTP Connection Details ::" + "\n" + "User name :: " + useName + " Host Name :: " + host
					+ " Port :: " + port);

			jsch = new JSch();
			jsession = jsch.getSession(useName, host, port);
			if (privateKeyenabled) {
				jsch.addIdentity(privakeypath);
			}
			jsession.setPassword(pwd);
			jsession.setConfig("StrictHostKeyChecking", "no");
			jsession.setTimeout(sftpBean.getTimeOut());
			jsession.connect();

			if (!jsession.isConnected()) {
				jsession.connect();
			}
			logger.info("Session connection ::" + jsession.isConnected());
			channel = (ChannelSftp) jsession.openChannel("sftp");
			channel.connect();
			logger.info("Channel connection ::" + channel.isConnected());
			logger.info("dateFormat:: " + dateFormat);
			String currentDate = new SimpleDateFormat(dateFormat).format(new Date());

			if (channel.isConnected()) {

				channelSftp = (ChannelSftp) channel;
				logger.info("SFTP Upload path after opening::" + uploadSFTPpath);// upload/sftp
				channelSftp.cd(uploadSFTPpath);
				/*
				 * if(new File(sourcepath + "/" + uploadFileName).exists()) {
				 * logger.info("Source filename :: "+sourcepath + "/" +
				 * uploadFileName+", Upload sftp filename,"+ uploadSFTPpath + "/" +
				 * uploadFileName); channelSftp.put(sourcepath + "/" + uploadFileName,
				 * uploadSFTPpath + "/" + uploadFileName, new SFTPProgressMonitor());
				 * 
				 * logger.info("Control filename :: "+sourcepath + "/" +
				 * controlFileName+", Upload sftp filename,"+ uploadSFTPpath + "/" +
				 * controlFileName); if(new File(sourcepath + "/" + controlFileName).exists()) {
				 * channelSftp.put(sourcepath + "/" + controlFileName, uploadSFTPpath + "/" +
				 * controlFileName, new SFTPProgressMonitor()); } }
				 * 
				 * 
				 * 
				 * else { logger.info(uploadFileName+":: File is not available"); }
				 */

				File files[] = new File(sourcepath).listFiles();
				//Collections.sort(files);

					for (int i = 0; i < files.length; i++) {
						String filename = files[i].getName();
						if (filename.startsWith(uploadFileName)) {
							channelSftp.put(sourcepath + "/" + filename, uploadSFTPpath + "/" + filename,
									new SFTPProgressMonitor());
						}else {
							if(filename.startsWith(controlFileName)) {
								channelSftp.put(sourcepath + "/" + filename, uploadSFTPpath + "/" + filename,
										new SFTPProgressMonitor());
							}
						}

					}
				

				logger.info("File uploaded completed");
			}
			flag = true;

		} catch (Exception e) {
			logger.error("Error while reading File from FTP Location::" + e.getMessage());
			logger.error(e, e);
			throw e;
		} finally {
			if (jsession != null)
				jsession.disconnect();
			if (channelSftp != null)
				channelSftp.disconnect();
			if (pwd != null)
				pwd = null;
		}
		return flag;
	}

	public static String getFormattedDate(java.util.Date dt, String format) throws Exception {
		String retVal = "";//$NON-NLS-1$
		if (dt != null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				retVal = sdf.format(dt);
			} catch (Exception ex) {
				logger.error("Exception in getFormattedDate: " + ex.getMessage());//$NON-NLS-1$
				throw ex;
			}
		}
		return retVal;
	}

}
