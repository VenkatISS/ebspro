package com.it.erpapp.framework.bos.pps;

import com.it.erpapp.framework.model.transactions.AgencyBOMDO;
import com.it.erpapp.framework.model.transactions.BOMItemsDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class AgencyBOMBO {

	public AgencyBOMDO createDO(String name, int bomType, long agencyId) throws BusinessException {
		AgencyBOMDO doObj = new AgencyBOMDO();
		try {
			doObj.setBom_name(name);
			doObj.setBom_type(bomType);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY BOM DATA");
		}
		return doObj;
	}
	
	public BOMItemsDO createBOMItemDO(String code, String qty, String depositeRorN){
		BOMItemsDO doObj = new BOMItemsDO();
		try {
			doObj.setProd_code(code);
			doObj.setQty(qty.equalsIgnoreCase("NA")? 0 : Integer.parseInt(qty));
			doObj.setDeposit_req(depositeRorN); //Yes / NO or NA
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY BOM DATA");
		}
		return doObj;
	}
}
