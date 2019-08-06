package com.it.erpapp.framework.bos.pps;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.pps.ARBDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class ARBDataBO {

	public ARBDataDO createDO(int pcode, String brand, String pname,
			int punits, String gstpv, String cstehv, String pmrp, 
			int pos, String effectiveDate, long agencyId) throws BusinessException {
		ARBDataDO doObj = new ARBDataDO();
		try {
			doObj.setProd_code(pcode);
			doObj.setProd_brand(brand);
			doObj.setProd_name(pname);
			doObj.setUnits(punits);
			doObj.setVatp("0");
			doObj.setGstp(gstpv);
			doObj.setCsteh_no(cstehv);
			doObj.setProd_mrp(pmrp);
			doObj.setProd_offer_price("0");
			doObj.setOpening_stock(pos);
			doObj.setCurrent_stock(pos);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d = sdf.parse(effectiveDate);
			doObj.setEffective_date(d.getTime());
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY ARB DATA");
		}
		return doObj;
	}
}
