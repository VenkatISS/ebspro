package com.it.erpapp.framework.bos;

import com.it.erpapp.framework.model.ExpenditureDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class ExpenditureDataBO {

	public ExpenditureDataDO createDO(String itemName, int sh, int mh, long agencyId)
			throws BusinessException {
		ExpenditureDataDO edo = new ExpenditureDataDO();
		try {
			edo.setItem_name(itemName);
			edo.setSh(sh);
			edo.setMh(mh);
			edo.setCreated_by(agencyId);
			edo.setCreated_date(System.currentTimeMillis());
			edo.setVersion(1);
			edo.setDeleted(0);
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY EXPENDITURE DATA");
		}
		return edo;
	}
}
