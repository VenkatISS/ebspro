package com.it.erpapp.framework.bos.pps;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.pps.EquipmentDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class EquipmentDataBO {

	public EquipmentDataDO createDO(int pcode, int eu, String gstv, 
			String cstehv, String sdv, int efsv, int eesv,  String effectiveDate, long agencyId) throws BusinessException {
		EquipmentDataDO doObj = new EquipmentDataDO();
		try {
			doObj.setProd_code(pcode);
			doObj.setUnits(eu);
			doObj.setVatp("0");
			doObj.setGstp(gstv);
			doObj.setCsteh_no(cstehv);
			doObj.setSecurity_deposit(sdv);
			doObj.setOs_fulls(efsv);
			doObj.setOs_emptys(eesv);
			doObj.setCs_fulls(efsv);
			doObj.setCs_emptys(eesv);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d = sdf.parse(effectiveDate);
			doObj.setEffective_date(d.getTime());
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY EQUIPMENT DATA");
		}
		return doObj;
	}
}
