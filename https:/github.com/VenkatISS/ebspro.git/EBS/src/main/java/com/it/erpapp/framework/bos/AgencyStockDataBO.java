package com.it.erpapp.framework.bos;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class AgencyStockDataBO {
	
	Logger logger = Logger.getLogger(AgencyStockDataBO.class.getName());
	
	public AgencyStockDataDO createDO(long ref_id, int stock_flag, String inv_no, String tdate, int ttype, int prod_code, long prod_id,
			int fulls, int empties, int csfulls, int csempties, long cvoid, String discount,long agencyId) throws BusinessException {

		AgencyStockDataDO asdo = new AgencyStockDataDO();
		try {
			// set ref_id , inv_no, fulls, empties, csfulls, csempties in persistence manager
			asdo.setRef_id(ref_id);
			asdo.setInv_no(inv_no);
			asdo.setStock_flag(stock_flag);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			asdo.setTrans_date((sdf.parse(tdate)).getTime());
			
			asdo.setTrans_type(ttype);
			asdo.setProd_code(prod_code);
			asdo.setProd_id(prod_id);
			asdo.setFulls(fulls);
			asdo.setEmpties(empties);
			asdo.setCs_fulls(csfulls);
			asdo.setCs_emptys(csempties);
			asdo.setCvo_id(cvoid);
			asdo.setDiscount(discount);
			
			asdo.setCreated_by(agencyId);
			asdo.setCreated_date(System.currentTimeMillis());
			asdo.setVersion(1);
			asdo.setDeleted(0);
			
		}catch (Exception e){
			logger.error(e);
			throw new BusinessException("UNABLE TO SAVE AGENCY STOCK DATA");
		}
		return asdo;
	}
}
