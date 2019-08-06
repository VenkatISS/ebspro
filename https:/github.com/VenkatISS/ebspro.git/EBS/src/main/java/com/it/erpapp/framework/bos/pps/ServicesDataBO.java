package com.it.erpapp.framework.bos.pps;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.pps.ServicesDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class ServicesDataBO {

	public ServicesDataDO createDO(int scode, String scharges, String effectiveDate, 
			String gstAmt, String sacCode, long agencyId) throws BusinessException {
		ServicesDataDO doObj = new ServicesDataDO();
		try {
			doObj.setProd_code(scode);
			doObj.setProd_charges(scharges);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d = sdf.parse(effectiveDate);
			doObj.setEffective_date(d.getTime());
			doObj.setGst_amt(gstAmt);
			doObj.setSac_code(sacCode);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY SERVICES DATA");
		}
		return doObj;
	}
}
