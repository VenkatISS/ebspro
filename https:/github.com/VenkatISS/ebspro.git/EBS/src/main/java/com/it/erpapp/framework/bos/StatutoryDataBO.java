package com.it.erpapp.framework.bos;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.StatutoryDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class StatutoryDataBO {

	public StatutoryDataDO createDO(int itemType, String refNumber,
			String validUPTO, int remindBefore, String remarks, long agencyId)
			throws BusinessException {
		StatutoryDataDO statutoryData = new StatutoryDataDO();
		try {
			statutoryData.setItem_type(itemType);
			statutoryData.setItem_ref_no(refNumber);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d = sdf.parse(validUPTO);
			statutoryData.setValid_upto(new Date(d.getTime()));
			statutoryData.setRemind_before(remindBefore);
			statutoryData.setRemarks(remarks);
			statutoryData.setCreated_by(agencyId);
			statutoryData.setCreated_date(System.currentTimeMillis());
			statutoryData.setVersion(1);
			statutoryData.setDeleted(0);
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY STATUTORY DATA");
		}
		return statutoryData;
	}
}
