package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.sales.RCDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class RCDataBO {

	public RCDataDO createDO(String rcDate, String custNo, long staffId, String rcAmount, int pid, int cyls, 
			int regs,String cyld,String regd,String dgccAmount,String cgstAmt,String sgstAmt,String scgstAmt, 
			String ssgstAmt, int rcChargesType, String adminCharges, String paymentTerms, long agencyId,
			String opbasicprice, String opTaxableAmt, String opTotalAmt, String opCgstAmt, String opSgstAmt, String opIgstAmt
			) throws BusinessException {

		RCDataDO doObj = new RCDataDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setRc_date(sdf.parse(rcDate).getTime());
			doObj.setCustomer_no(custNo);
			doObj.setStaff_id(staffId);
			doObj.setRc_amount(rcAmount);
			doObj.setProd_code(pid);
			doObj.setNo_of_cyl(cyls);
			doObj.setNo_of_reg(regs);
			doObj.setCyl_deposit(cyld);
			doObj.setReg_deposit(regd);
			doObj.setDgcc_amount(dgccAmount);
			doObj.setRc_charges_type(rcChargesType);
			doObj.setAdmin_charges(adminCharges);
			doObj.setIgst_amount("0");
			doObj.setCgst_amount(cgstAmt);
			doObj.setSgst_amount(sgstAmt);
			doObj.setScgst_amount(scgstAmt);
			doObj.setSsgst_amount(ssgstAmt);
			doObj.setPayment_terms(paymentTerms);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
			doObj.setOp_basic_price(opbasicprice);
			doObj.setOp_taxable_amt(opTaxableAmt);
			doObj.setOp_total_amt(opTotalAmt);
			doObj.setOp_cgst_amt(opCgstAmt);
			doObj.setOp_sgst_amt(opSgstAmt);
			doObj.setOp_igst_amt(opIgstAmt);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY RECONNECTION DATA");
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
