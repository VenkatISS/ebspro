package com.it.erpapp.framework.bos;

import com.it.erpapp.framework.model.CreditLimitsDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class CreditLimitsDataBO {

	public CreditLimitsDataDO createDO(long custId, String creditLimit, int creditDays,String dicsountComCyl1,
			String dicsountComCyl2,String dicsountComCyl3,String dicsountComCyl4,String dicsountComCyl5, int controlType, long agencyId)
		throws BusinessException {
		CreditLimitsDataDO dataDO = new CreditLimitsDataDO();
		try {
			dataDO.setCust_id(custId);
			dataDO.setCredit_limit(creditLimit);
			dataDO.setCredit_days(creditDays);
			
			dataDO.setDisc_19kg_ndne(dicsountComCyl1);
			dataDO.setDisc_19kg_cutting_gas(dicsountComCyl2);
			dataDO.setDisc_35kg_ndne(dicsountComCyl3);
			dataDO.setDisc_47_5kg_ndne(dicsountComCyl4);
			dataDO.setDisc_450kg_sumo(dicsountComCyl5);

			dataDO.setCc_type(controlType);
			dataDO.setCreated_by(agencyId);
			dataDO.setCreated_date(System.currentTimeMillis());
			dataDO.setVersion(1);
			dataDO.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY CUSTOMER CREDIT LIMITS DATA");
		}
		return dataDO;
	}
}
