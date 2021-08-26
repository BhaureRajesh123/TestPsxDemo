package com.posidex.sftp;

import javax.swing.ProgressMonitor;

import org.apache.log4j.Logger;

import com.jcraft.jsch.SftpProgressMonitor;

public class SFTPProgressMonitorLogger implements SftpProgressMonitor {
	
	public static final Logger logger = Logger.getLogger(SFTPProgressMonitorLogger.class);
	ProgressMonitor monitor;
	long count = 0;
	long max = 0;
	long per,prePer=0;

	public void init(int op, String src, String dest, long max) {
		logger.info("Initiating the file transfer operation:"+op+" from:"+src+" to:"+dest+" File size:"+count);
		this.max=max;
	}

	//private long percent = -1;

	public boolean count(long count) {
		this.count+=count;
		if(max!=0){
		per=(this.count*100)/max;
		if(prePer+1==per){
			logger.info("Percentage transfered:"+per);
			prePer=per;
		}
		}else{
			try {
				throw new Exception("Exception in SFTPProgressMonitorLogger due to max is 0");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
//		if(Math.(float)per1%1==0f){
//			logger.info("Percentage transfered:"+per1);
//		}
		
		return true;

	}

	public void end() {
		logger.info("File transfer completed");
		//monitor.close();
	}
}
