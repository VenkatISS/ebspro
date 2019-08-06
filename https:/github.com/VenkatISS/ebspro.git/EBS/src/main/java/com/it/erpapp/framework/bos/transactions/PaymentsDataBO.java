package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.others.PaymentsDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class PaymentsDataBO {

	public PaymentsDataDO createDO(String rcp_date, long customerId, String amount, int cv, int taxtypes, int mop,
			String iDetails, long dbankId, String charges, long staffId, String narration, long agencyId) throws BusinessException {
		PaymentsDataDO doObj = new PaymentsDataDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setPymt_date((sdf.parse(rcp_date)).getTime());
			doObj.setPaid_to(customerId);
			doObj.setPymt_amount(amount);
			doObj.setCust_ven(cv);
			doObj.setTax_type(taxtypes);
			doObj.setVbal_amount("0.00");
			doObj.setDbal_amount("0.00");
			doObj.setPayment_mode(mop);
			doObj.setInstr_details(iDetails);
			doObj.setDebited_bank(dbankId);
			doObj.setBank_charges(charges);
			doObj.setStaff_id(staffId);
			doObj.setNarration(narration);
			doObj.setClearance_status(1);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);

		} catch (Exception e) {
			throw new BusinessException("UNABLE TO SAVE AGENCY PAYMENTS DATA");
		}
		return doObj;
	}
}