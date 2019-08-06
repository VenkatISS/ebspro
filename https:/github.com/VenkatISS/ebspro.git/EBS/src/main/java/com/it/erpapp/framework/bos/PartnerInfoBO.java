package com.it.erpapp.framework.bos;

import com.it.erpapp.framework.model.PartnerInfoDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class PartnerInfoBO {

	public PartnerInfoDO createDO(String name, String pan, 
			String sharePercent, String openingBalance, long agencyId)
			throws BusinessException {
		PartnerInfoDO dataDO = new PartnerInfoDO();
		try {
			dataDO.setPartner_name(name);
			dataDO.setPan(pan);
			dataDO.setShare_percent(sharePercent);
			dataDO.setOpening_balance(openingBalance);
			dataDO.setStatus(1);
			dataDO.setCreated_by(agencyId);
			dataDO.setCreated_date(System.currentTimeMillis());
			dataDO.setVersion(1);
			dataDO.setDeleted(0);
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY PARTNERS DATA");
		}
		return dataDO;
	}
}
