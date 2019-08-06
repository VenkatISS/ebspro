package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDetailsDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class ARBSalesInvoiceBO {

	public ARBSalesInvoiceDO createDO(String invData, long custId, long staffId,   
			String amount, int pt, long agencyId, String discount) throws BusinessException {
		ARBSalesInvoiceDO doObj = new ARBSalesInvoiceDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setSi_date(sdf.parse(invData).getTime());
			doObj.setCustomer_id(custId);
			doObj.setStaff_id(staffId);
			doObj.setPayment_terms(pt);
			if(pt==1) {
				doObj.setPayment_status(3);
			} else {
				doObj.setPayment_status(1);
			}
			doObj.setSi_amount(amount);
			doObj.setCbal_amount("0.00");
			doObj.setDbal_amount("0.00");
			doObj.setDiscount(discount);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY COM SALES DATA");
		}
		return doObj;
	}
	
	public ARBSalesInvoiceDetailsDO createARBSalesInvoiceDetailsDO(long pid, int quantity, String unitRate, 
			String unitRateDiscount, String vatPercent, long bankId, String igstAmount, String sgstAmount, String cgstAmount, String amount,
			String opbasicprice, String opTaxableAmt, String opTotalAmt, String opCgstAmt, String opSgstAmt, String opIgstAmt) {

		ARBSalesInvoiceDetailsDO doObj = new ARBSalesInvoiceDetailsDO();
		doObj.setProd_code(pid);
		doObj.setQuantity(quantity);
		doObj.setUnit_rate(unitRate);
		doObj.setDisc_unit_rate(unitRateDiscount);
		doObj.setGstp(vatPercent);
		doObj.setBank_id(bankId);
		doObj.setIgst_amount(igstAmount);
		doObj.setSgst_amount(sgstAmount);
		doObj.setCgst_amount(cgstAmount);
		doObj.setSale_amount(amount);
		
		doObj.setOp_basic_price(opbasicprice);
		doObj.setOp_taxable_amt(opTaxableAmt);
		doObj.setOp_total_amt(opTotalAmt);
		doObj.setOp_cgst_amt(opCgstAmt);
		doObj.setOp_sgst_amt(opSgstAmt);
		doObj.setOp_igst_amt(opIgstAmt);
		
		return doObj;
	}
}
