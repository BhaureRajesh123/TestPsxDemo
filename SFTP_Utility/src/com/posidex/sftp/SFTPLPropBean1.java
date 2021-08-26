package com.posidex.sftp;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.posidex.customanttasks.StringEncrypter;


public class SFTPLPropBean1 {
	public static final Logger logger = Logger.getLogger(SFTPLPropBean1.class);
	static Properties configProps = new Properties();
	private String sftpHostName;
	private String sftpUser;
	private String sftpPWD;
	private int port;
	private int timeOut;
	private String lastModifQuery;
	private String lastModifUpdtQuery;
	private String lastPrvModUpdtQry;
	private String prvLastModUpdtQry;
	private String isBLKFTPEnabled;
	private String sourceLoc;
	private String BlkDownldLocalFldr = "";

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

	/**
	 * @return the lastModifQuery
	 */
	public String getLastModifQuery() {
		return lastModifQuery;
	}

	/**
	 * @param lastModifQuery
	 *  			the lastModifQuery to set
	 */
	public void setLastModifQuery(String lastModifQuery) {
		this.lastModifQuery = lastModifQuery;
	}

	/**
	 * @return the lastModifUpdtQuery
	 */
	public String getLastModifUpdtQuery() {
		return lastModifUpdtQuery;
	}

	/**
	 * @param lastModifUpdtQuery
	 *  				the lastModifUpdtQuery to set
	 */
	public void setLastModifUpdtQuery(String lastModifUpdtQuery) {
		this.lastModifUpdtQuery = lastModifUpdtQuery;
	}
	
	/**
	 * @return the isBLKFTPEnabled
	 */
	public String getIsBLKFTPEnabled() {
		return isBLKFTPEnabled;
	}

	/**
	 * @param isBLKFTPEnabled 
	 * 					the isBLKFTPEnabled to set
	 */
	/**
	 * @param isBLKFTPEnabled
	 * 				 the isBLKFTPEnabled to set
	 */
	public void setIsBLKFTPEnabled(String isBLKFTPEnabled) {
		this.isBLKFTPEnabled = isBLKFTPEnabled;
	}

	/**
	 * @return the sourceLoc
	 */
	public String getSourceLoc() {
		return sourceLoc;
	}

	/**
	 * @param sourceLoc 
	 * 				the sourceLoc to set
	 */
	public void setSourceLoc(String sourceLoc) {
		this.sourceLoc = sourceLoc;
	}
	
	/**
	 * @return the BlkDownldLocalFldr
	 */
	public String getBlkDownldLocalFldr() {
		return BlkDownldLocalFldr;
	}

	/**
	 * @param blkDownldLocalFldr
	 * 					the BlkDownldLocalFldr to set
	 */
	public void setBlkDownldLocalFldr(String blkDownldLocalFldr) {
		BlkDownldLocalFldr = blkDownldLocalFldr;
	}

	/**
	 * @return the lastPrvModUpdtQry
	 */
	public String getLastPrvModUpdtQry() {
		return lastPrvModUpdtQry;
	}

	/**
	 * @param lastPrvModUpdtQry
	 * 					 the lastPrvModUpdtQry to set
	 */
	public void setLastPrvModUpdtQry(String lastPrvModUpdtQry) {
		this.lastPrvModUpdtQry = lastPrvModUpdtQry;
	}

	/**
	 * @return the prvLastModUpdtQry
	 */
	public String getPrvLastModUpdtQry() {
		return prvLastModUpdtQry;
	}

	/**
	 * @param prvLastModUpdtQry
	 * 						 the prvLastModUpdtQry to set
	 */
	public void setPrvLastModUpdtQry(String prvLastModUpdtQry) {
		this.prvLastModUpdtQry = prvLastModUpdtQry;
	}

	/** the Zero Param constructor
	 * 
	 */
	
	public SFTPLPropBean1(String sftpHostName, String sftpUser, String sftpPWD, int port,int timeOut) {
		try {
			
			this.sftpHostName = sftpHostName;
			this.sftpUser = sftpUser;
			this.sftpPWD = sftpPWD;
			this.port = port;
			this.timeOut = timeOut;
		} catch (Exception e) {
			logger.error("Exception while loading properties" + e.getMessage());
			logger.error(e, e);
			try {
				throw e;
			} catch (Exception e1) {
				logger.error("Exception while loading properties" + e1.getMessage());
			}
		}
	}
	
}
