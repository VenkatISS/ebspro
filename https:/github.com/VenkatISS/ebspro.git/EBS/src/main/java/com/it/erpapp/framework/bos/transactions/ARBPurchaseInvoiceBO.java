package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.ARBPurchaseInvoiceDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class ARBPurchaseInvoiceBO {

	public ARBPurchaseInvoiceDO createDO(String inv_no, String inv_date, String stk_recvd_date, 
			int pcode, long vendorId, int rcyn, String up, int qty, String gstp, String oc, 
			String bamt, String igstamt, String sgstamt, String cgstamt, String totalAmount, 
			long agencyId) throws BusinessException {
		ARBPurchaseInvoiceDO doObj = new ARBPurchaseInvoiceDO();
		try {
			doObj.setInv_ref_no(inv_no);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setInv_date((sdf.parse(inv_date)).getTime());
			doObj.setStk_recvd_date((sdf.parse(stk_recvd_date)).getTime());
			doObj.setArb_code(pcode);
			doObj.setUnit_price(up);
			doObj.setVendor_id(vendorId);
			doObj.setPayment_status(1);
			doObj.setReverse_charge(rcyn);
			doObj.setQuantity(qty);
			doObj.setGstp(gstp);
			doObj.setOther_charges(oc);
			doObj.setBasic_amount(bamt);
			doObj.setIgst_amount(igstamt);
			doObj.setSgst_amount(sgstamt);
			doObj.setCgst_amount(cgstamt);
			doObj.setC_amount(totalAmount);
			doObj.setDbal_amount("0.00");
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY ARB-PURCHASE INVOICE DATA");
		}
		return doObj;
	}
}
