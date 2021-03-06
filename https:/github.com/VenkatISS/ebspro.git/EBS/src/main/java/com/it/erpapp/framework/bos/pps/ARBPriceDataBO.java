package com.it.erpapp.framework.bos.pps;

import com.it.erpapp.framework.model.pps.ARBPriceDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class ARBPriceDataBO {

	public ARBPriceDataDO createDO(long pid, String bp, String sgstp, String cgstp,
			String rsp, String ofp, String ofpBp, int mon, int yr, long agencyId)
			throws BusinessException {
		ARBPriceDataDO doObj = new ARBPriceDataDO();
		try {
			doObj.setArb_code(pid);
			doObj.setBase_price(bp);
			doObj.setSgst_price(sgstp);
			doObj.setCgst_price(cgstp);
			doObj.setRsp(rsp);
//			doObj.setOffer_price("0.00");
			doObj.setOffer_price(ofp);
			doObj.setOp_base_price(ofpBp);
			doObj.setMontha(mon);
			doObj.setYeara(yr);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY ARB PRICE DATA");
		}
		return doObj;
	}
}
