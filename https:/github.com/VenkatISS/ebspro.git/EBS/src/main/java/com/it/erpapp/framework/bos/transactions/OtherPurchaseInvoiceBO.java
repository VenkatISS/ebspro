package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.OtherPurchaseInvoiceDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class OtherPurchaseInvoiceBO {

	public OtherPurchaseInvoiceDO createDO(String inv_no, String inv_date, 
			int vendorId, int rc, String prodName, int tbl , String hscnCode, 
			String gstp, int minh,int ch, int ah, String unitRate, int qty,
			int uomv, String taxAmt, String igstAmt, String cgstAmt, 
			String sgstAmt, String prodAmt,
			long agencyId) throws BusinessException {
		OtherPurchaseInvoiceDO doObj = new OtherPurchaseInvoiceDO();
		try {
			doObj.setInv_ref_no(inv_no);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setInv_date((sdf.parse(inv_date)).getTime());
			doObj.setVendor_id(vendorId);
			doObj.setPayment_status(1);
			doObj.setReverse_charge(rc);
			doObj.setPname(prodName);
			doObj.setTaxable(tbl);
			doObj.setHsn_code(hscnCode);
			doObj.setGstp(gstp);
			doObj.setMh(minh);
			doObj.setCh(ch);
			doObj.setAh(ah);
			doObj.setUnit_rate(unitRate);
			doObj.setQuantity(qty);
			doObj.setUom(uomv);
			doObj.setDbal_amount("0.00");
			doObj.setBasic_amount(taxAmt);
			doObj.setIgst_amount(igstAmt);
			doObj.setSgst_amount(sgstAmt);
			doObj.setCgst_amount(cgstAmt);
			doObj.setP_amount(prodAmt);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY OTHER-PURCHASE INVOICE DATA");
		}
		return doObj;
	}

}
