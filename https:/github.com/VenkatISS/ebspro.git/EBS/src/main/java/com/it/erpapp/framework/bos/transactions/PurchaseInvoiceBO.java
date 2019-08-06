package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.PurchaseInvoiceDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class PurchaseInvoiceBO {

	public PurchaseInvoiceDO createDO(long omc_id, String inv_no, String inv_date, String stk_recvd_date, 
			int pcode, int eord, String up, int qty, String vatp, String oc, String bamt, 
			String igstamt, String sgstamt, String cgstamt, 
			String totalAmount, long agencyId) throws BusinessException {
		PurchaseInvoiceDO doObj = new PurchaseInvoiceDO();
		try {
			doObj.setOmc_id(omc_id);
			doObj.setPayment_status(3);
			doObj.setInv_ref_no(inv_no);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setInv_date((sdf.parse(inv_date)).getTime());
			doObj.setStk_recvd_date((sdf.parse(stk_recvd_date)).getTime());
			doObj.setProd_code(pcode);
			doObj.setUnit_price(up);
			doObj.setEmr_or_delv(eord);
			doObj.setQuantity(qty);
			doObj.setCs_fulls(0);
			doObj.setCs_emptys(0);
			doObj.setDs_fulls(0);
			doObj.setDs_emptys(0);
			doObj.setGstp(vatp);
			doObj.setOther_charges(oc);
			doObj.setBasic_amount(bamt);
			doObj.setIgst_amount(igstamt);
			doObj.setSgst_amount(sgstamt);
			doObj.setCgst_amount(cgstamt);
			doObj.setC_amount(totalAmount);
			doObj.setVbal_amount("0.00");
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY PURCHASE INVOICE DATA");
		}
		return doObj;
	}
}
