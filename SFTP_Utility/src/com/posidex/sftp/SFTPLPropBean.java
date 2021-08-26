package com.posidex.sftp;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.posidex.customanttasks.StringEncrypter;


public class SFTPLPropBean {
	public static final Logger logger = Logger.getLogger(SFTPLPropBean.class);
	
	private String sftpHostName;
	private String sftpUser;
	private String sftpPWD;
	private int port;
	private int timeOut;
	private String lastModifQuery;
	private String lastModifUpdtQuery;
	private String isBLKFTPEnabled;
	/**
	 * @return the timeOut
	 */
	public int getTimeOut() {
		return timeOut;
	}

	/**
	 * @param timeOut
	 *            the timeOut to set
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the sftpHostName
	 */
	public String getSftpHostName() {
		return sftpHostName;
	}

	/**
	 * @param sftpHostName
	 *            the sftpHostName to set
	 */
	public void setSftpHostName(String sftpHostName) {
		this.sftpHostName = sftpHostName;
	}

	/**
	 * @return the sftpUser
	 */
	public String getSftpUser() {
		return sftpUser;
	}

	/**
	 * @param sftpUser
	 *            the sftpUser to set
	 */
	public void setSftpUser(String sftpUser) {
		this.sftpUser = sftpUser;
	}

	/**
	 * @return the sftpPWD
	 */
	public String getSftpPWD() {
		return sftpPWD;
	}

	/**
	 * @param sftpPWD
	 *            the sftpPWD to set
	 */
	public void setSftpPWD(String sftpPWD) {
		this.sftpPWD = sftpPWD;
	}

	public String getLastModifQuery() {
		return lastModifQuery;
	}

	public void setLastModifQuery(String lastModifQuery) {
		this.lastModifQuery = lastModifQuery;
	}

	public String getLastModifUpdtQuery() {
		return lastModifUpdtQuery;
	}

	public void setLastModifUpdtQuery(String lastModifUpdtQuery) {
		this.lastModifUpdtQuery = lastModifUpdtQuery;
	}
	
	public String getIsBLKFTPEnabled() {
		return isBLKFTPEnabled;
	}

	public void setIsBLKFTPEnabled(String isBLKFTPEnabled) {
		this.isBLKFTPEnabled = isBLKFTPEnabled;
	}

	/*public SFTPLPropBean() {
		String sftphost;
		String username;
		String pwd;
		int port;
		int timeout;
		try {
			// configProps.load(new FileInputStream("ckycProp.properties"));
			configProps.load(SFTPLPropBean.class.getClassLoader().getResourceAsStream("configProp.properties"));
			sftphost = configProps.getProperty("BLK_SFTP_host");
			username = configProps.getProperty("BLK_SFTP_userName");
			pwd = StringEncrypter.decrypt(configProps.getProperty("BLK_SFTP_password"));
			port = Integer.parseInt(configProps.getProperty("BLK_SFTP_port"));
			timeout = Integer.parseInt(configProps.getProperty("SFTP_timoeout"));
			setSftpHostName(sftphost);
			setSftpUser(username);
			setSftpPWD(pwd);
			setPort(port);
			setTimeOut(timeout);
		} catch (Exception e) {
			logger.error("Exception while loading properties" + e.getMessage());
			logger.error(e, e);
			try {
				throw e;
			} catch (Exception e1) {

				e1.printStackTrace();
			}
		}
	}*/
	public SFTPLPropBean(String sftpHostName, String sftpUser, String sftpPWD, int port) {
		try {
			
			this.sftpHostName = sftpHostName;
			this.sftpUser = sftpUser;
			this.sftpPWD = sftpPWD;
			this.port = port;
			
		} catch (Exception e) {
			logger.error("Exception while loading properties" + e.getMessage());
			logger.error(e, e);
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private String sourceLoc;

	public String getSourceLoc() {
		return sourceLoc;
	}

	public void setSourceLoc(String sourceLoc) {
		this.sourceLoc = sourceLoc;
	}
	
	private String BlkDownldLocalFldr = "";

	public String getBlkDownldLocalFldr() {
		return BlkDownldLocalFldr;
	}

	public void setBlkDownldLocalFldr(String blkDownldLocalFldr) {
		BlkDownldLocalFldr = blkDownldLocalFldr;
	}

	
}
