package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDetailsDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class DOMSalesInvoiceBO {

	public DOMSalesInvoiceDO createDO(String invData, long custId, int pt, 
			String amount, String camount, long agencyId, String discount) throws BusinessException {
		DOMSalesInvoiceDO doObj = new DOMSalesInvoiceDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setSi_date(sdf.parse(invData).getTime());
			doObj.setCustomer_id(custId);
			doObj.setPayment_terms(pt);
			if(pt==1) {
				doObj.setPayment_status(3);
			} else {
				doObj.setPayment_status(1);
			}
			doObj.setSi_amount(amount);
			doObj.setCash_amount(camount);
			doObj.setCbal_amount("0.00");
			doObj.setDbal_amount("0.00");
			doObj.setDiscount(discount);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY DOM SALES DATA");
		}
		return doObj;
	}
	
	public DOMSalesInvoiceDetailsDO createDOMSalesInvoiceDetailsDO(int pid, int quantity, String unitRate, String unitRateDiscount,
			int prec, int psvc, long staffId, long areaCodeId, String vatPercent, String igstAmount, String sgstAmount, String cgstAmount, String amount,
			long onlineBankId,String onlineAmount, String opbasicprice, String opTaxableAmt, String opTotalAmt, String opCgstAmt, String opSgstAmt, String opIgstAmt) {
		DOMSalesInvoiceDetailsDO doObj = new DOMSalesInvoiceDetailsDO();
		doObj.setProd_code(pid);
		doObj.setQuantity(quantity);
		doObj.setUnit_rate(unitRate);
		doObj.setDisc_unit_rate(unitRateDiscount);
		doObj.setPre_cyls(prec);
		doObj.setPsv_cyls(psvc);
		doObj.setCs_fulls(0);
		doObj.setCs_emptys(0);
		doObj.setDs_fulls(0);
		doObj.setDs_emptys(0);
		doObj.setStaff_id(staffId);
		doObj.setAc_id(areaCodeId);
		doObj.setGstp(vatPercent);
		doObj.setIgst_amount(igstAmount);
		doObj.setSgst_amount(sgstAmount);
		doObj.setCgst_amount(cgstAmount);
		doObj.setSale_amount(amount);
		doObj.setBank_id(onlineBankId);
		doObj.setAmount_rcvd_online(onlineAmount);
		
		doObj.setOp_basic_price(opbasicprice);
		doObj.setOp_taxable_amt(opTaxableAmt);
		doObj.setOp_total_amt(opTotalAmt);
		doObj.setOp_cgst_amt(opCgstAmt);
		doObj.setOp_sgst_amt(opSgstAmt);
		doObj.setOp_igst_amt(opIgstAmt);
		
		return doObj;
	}
}
