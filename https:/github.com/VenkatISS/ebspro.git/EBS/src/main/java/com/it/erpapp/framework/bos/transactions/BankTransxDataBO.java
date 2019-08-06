package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class BankTransxDataBO {

	public BankTranxsDataDO createDO(String btDate, long bankId, String amount, String bcharges,
			int tt, int mot, String iDetails, int ahId, String narration, long agencyId) throws BusinessException {
		BankTranxsDataDO doObj = new BankTranxsDataDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setBt_date((sdf.parse(btDate)).getTime());
			doObj.setBank_id(bankId);
			doObj.setTrans_amount(amount);
			doObj.setTrans_charges(bcharges);
			doObj.setTrans_type(tt);
			doObj.setDbank_acb("0.00");
			doObj.setTrans_mode(mot);
			doObj.setInstr_details(iDetails);
			doObj.setAcc_head(0);
			doObj.setNarration(narration);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY BANK TRANSACTIONS DATA");
		}
		return doObj;
	}
}
