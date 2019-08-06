package com.it.erpapp.framework.bos.pps;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.pps.AreaCodeDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class AreaCodeDataBO {

	public AreaCodeDataDO createDO(String code, String name, int owd, 
			String tcharges, String effectiveDate, long agencyId)
			throws BusinessException {
		AreaCodeDataDO doObj = new AreaCodeDataDO();
		try {
			doObj.setArea_code(code);
			doObj.setArea_name(name);
			doObj.setOne_way_dist(owd);
			doObj.setTransport_charges(tcharges);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d = sdf.parse(effectiveDate);
			doObj.setEffective_date(d.getTime());
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY AREA CODE DATA");
		}
		return doObj;
	}
}
