package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.sales.TVDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class TVDataBO {

	public TVDataDO createDO(String tvDate, String custNo, int tvcat, long staffId, 
			String tvAmount, int pid, int cyls, int regs, String cyld, String regd, String impAmount, String cgstAmt,
			String sgstAmt, int tvChargesTypSelected, String adminCharges, String paymentTerms, long agencyId) throws BusinessException {
		TVDataDO doObj = new TVDataDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setTv_date(sdf.parse(tvDate).getTime());
			doObj.setCustomer_no(custNo);
			doObj.setGst_no("NA");
			doObj.setTv_cat(tvcat);
			doObj.setStaff_id(staffId);
			doObj.setTv_amount(tvAmount);
			doObj.setProd_code(pid);
			doObj.setNo_of_cyl(cyls);
			doObj.setNo_of_reg(regs);
			doObj.setCyl_deposit(cyld);
			doObj.setReg_deposit(regd);
			doObj.setImp_amount(impAmount);
			doObj.setTv_charges_type(tvChargesTypSelected);
			doObj.setAdmin_charges(adminCharges);
			doObj.setIgst_amount("0");
			doObj.setCgst_amount(cgstAmt);
			doObj.setSgst_amount(sgstAmt);
			doObj.setPayment_terms(paymentTerms);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY TRANSFER VOUCHER DATA");
		}
		return doObj;
	}
	
/*	public QuotationDetailsDO createQuotationDetailsDO(long pid, String vatp, int quantity, String unitRate,
			String unitRateDiscount, String basicPrice, String sgstAmount, String cgstAmount, String prodAmount, String fn) {
		QuotationDetailsDO doObj = new QuotationDetailsDO();
		doObj.setProd_code(pid);
		doObj.setVatp(vatp);
		doObj.setQuantity(quantity);
		doObj.setUnit_rate(unitRate);
		doObj.setDisc_unit_rate(unitRateDiscount);
		doObj.setBasic_amount(basicPrice);
		doObj.setSgst_amount(sgstAmount);
		doObj.setCgst_amount(cgstAmount);
		doObj.setProd_amount(prodAmount);
		doObj.setFoot_notes(fn);
		return doObj;
	}*/
}
