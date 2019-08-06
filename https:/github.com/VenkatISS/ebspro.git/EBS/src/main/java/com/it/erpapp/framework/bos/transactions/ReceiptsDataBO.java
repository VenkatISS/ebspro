package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.others.ReceiptsDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class ReceiptsDataBO {

	public ReceiptsDataDO createDO(String rcp_date, long customerId, String amount,
			int mop, String iDetails, long cbankId, long staffId, String narration, long agencyId) throws BusinessException {
		ReceiptsDataDO doObj = new ReceiptsDataDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setRcp_date((sdf.parse(rcp_date)).getTime());
			doObj.setRcvd_from(customerId);
			doObj.setRcp_amount(amount);
			doObj.setCbal_amount("0.00");
			doObj.setDbal_amount("0.00");
			doObj.setPayment_mode(mop);
			doObj.setInstr_details(iDetails);
			doObj.setCredited_bank(cbankId);
			doObj.setStaff_id(staffId);
			doObj.setNarration(narration);
			doObj.setClearance_status(1);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY RECEIPTS DATA");
		}
		return doObj;
	}
}
