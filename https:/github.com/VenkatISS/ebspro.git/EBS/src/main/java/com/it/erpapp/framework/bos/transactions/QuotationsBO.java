package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.sales.QuotationDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.QuotationsDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class QuotationsBO {

	public QuotationsDO createDO(String qtnDate, long custId, String custName, long staffId, 
			String qtnAmount, String footNotes, long agencyId) throws BusinessException {
		QuotationsDO doObj = new QuotationsDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setQtn_date(sdf.parse(qtnDate).getTime());
			doObj.setCustomer_id(custId);
			doObj.setCustomer_name(custName);
			doObj.setStaff_id(staffId);
			doObj.setQtn_amount(qtnAmount);
			doObj.setFoot_notes(footNotes);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY QUOTATION DATA");
		}
		return doObj;
	}
	
	public QuotationDetailsDO createQuotationDetailsDO(long pid, String vatp, int quantity, String unitRate,
			String unitRateDiscount, String basicPrice, String igstAmount, 
			String sgstAmount, String cgstAmount, String prodAmount) {
		QuotationDetailsDO doObj = new QuotationDetailsDO();
		doObj.setProd_code(pid);
		doObj.setVatp(vatp);
		doObj.setQuantity(quantity);
		doObj.setUnit_rate(unitRate);
		doObj.setDisc_unit_rate(unitRateDiscount);
		doObj.setBasic_amount(basicPrice);
		doObj.setIgst_amount(igstAmount);
		doObj.setSgst_amount(sgstAmount);
		doObj.setCgst_amount(cgstAmount);
		doObj.setProd_amount(prodAmount);
		
		return doObj;
	}
}
