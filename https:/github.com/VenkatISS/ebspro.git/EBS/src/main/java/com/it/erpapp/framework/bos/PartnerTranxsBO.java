package com.it.erpapp.framework.bos;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.PartnerTranxDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class PartnerTranxsBO {

	public PartnerTranxDO createDO(String tdate, long pid, int ttype, 
			String tamount, long bankid, String remarks, long agencyId)
			throws BusinessException {
		PartnerTranxDO dataDO = new PartnerTranxDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dataDO.setTranx_date((sdf.parse(tdate)).getTime());
			dataDO.setPartner_id(pid);
			dataDO.setTranx_type(ttype);
			dataDO.setTranx_amount(tamount);
			dataDO.setBank_id(bankid);
			dataDO.setRemarks(remarks);
			dataDO.setCreated_by(agencyId);
			dataDO.setCreated_date(System.currentTimeMillis());
			dataDO.setVersion(1);
			dataDO.setDeleted(0);
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY PARTNERS TRANSACTIONS DATA");
		}
		return dataDO;
	}
}
