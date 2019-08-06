package com.it.erpapp.framework.bos;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.it.erpapp.framework.model.AgencyCashDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class AgencyCashDataBO {
	
	Logger logger = Logger.getLogger(AgencyCashDataBO.class.getName());
	public AgencyCashDataDO createDO(String tdate,int ttype, String camount,long agencyId)
			throws BusinessException {
		AgencyCashDataDO bddo = new AgencyCashDataDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			bddo.setT_date((sdf.parse(tdate)).getTime());
			
			bddo.setTrans_type(ttype);
			bddo.setCash_amount(camount);
			/*bddo.setModified_by(modifiedby);
			bddo.setModified_date(modifieddate);
			*/bddo.setCreated_by(agencyId);
			bddo.setCreated_date(System.currentTimeMillis());
			bddo.setVersion(1);
			bddo.setDeleted(0);
			bddo.setDiscount("0.0");
			
		} catch (Exception e){
			logger.error(e);
			throw new BusinessException("UNABLE TO SAVE AGENCY BANK DATA");
		}
		return bddo;
	}
}
