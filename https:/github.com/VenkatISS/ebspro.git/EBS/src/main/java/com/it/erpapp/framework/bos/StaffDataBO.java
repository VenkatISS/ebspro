package com.it.erpapp.framework.bos;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.StaffDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class StaffDataBO {

	public StaffDataDO createDO(String empCode, String empName,
			String empDOB, String empDesignation, int role, long agencyId)
			throws BusinessException {
		StaffDataDO staffDataDO = new StaffDataDO();
		try {
			staffDataDO.setEmp_code(empCode);
			staffDataDO.setEmp_name(empName);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date d = sdf.parse(empDOB);
			staffDataDO.setDob(new Date(d.getTime()));
			staffDataDO.setDesignation(empDesignation);
			staffDataDO.setRole(role);
			staffDataDO.setCreated_by(agencyId);
			staffDataDO.setCreated_date(System.currentTimeMillis());
			staffDataDO.setVersion(1);
			staffDataDO.setDeleted(0);
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY STATUTORY DATA");
		}
		return staffDataDO;
	}
}
