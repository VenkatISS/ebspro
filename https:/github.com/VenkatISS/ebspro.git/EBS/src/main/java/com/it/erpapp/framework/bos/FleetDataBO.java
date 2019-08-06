package com.it.erpapp.framework.bos;

import com.it.erpapp.framework.model.FleetDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class FleetDataBO {

	public FleetDataDO createDO(String vno, String vmake,
			int vtype, int vusage, long agencyId)
			throws BusinessException {
		FleetDataDO fddo = new FleetDataDO();
		try {
			fddo.setVehicle_no(vno);
			fddo.setVehicle_make(vmake);
			fddo.setVehicle_type(vtype);
			fddo.setVehicle_usuage(vusage);
			fddo.setCreated_by(agencyId);
			fddo.setCreated_date(System.currentTimeMillis());
			fddo.setVersion(1);
			fddo.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY FLEET DATA");
		}
		return fddo;
	}
}
