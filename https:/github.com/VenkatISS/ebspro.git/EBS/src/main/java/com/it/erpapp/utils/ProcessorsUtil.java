package com.it.erpapp.utils;

import com.it.erpapp.framework.managers.ProcessorsUtilManager;

public class ProcessorsUtil {

	public void processDepreciationReport(long recordId){
	
		getProcessorsUtilManager().processDepreciationReportByRecordId(recordId);
	}

	private ProcessorsUtilManager getProcessorsUtilManager(){
		return new ProcessorsUtilManager();
	}
}

