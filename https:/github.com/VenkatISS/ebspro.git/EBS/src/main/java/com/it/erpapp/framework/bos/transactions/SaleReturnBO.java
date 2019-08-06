package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.sales.SalesReturnDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDetailsDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class SaleReturnBO {

	public SalesReturnDO createDO(String srid, String srdate, long custid, 
			String sramount,int pt, String narration, long agencyId) throws BusinessException {
		SalesReturnDO doObj = new SalesReturnDO();
		try {
			doObj.setInv_ref_no(srid);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setInv_date((sdf.parse(srdate)).getTime());
			doObj.setCvo_id(custid);
			doObj.setDbal_amt("0.00");
			doObj.setInv_amt(sramount);
			doObj.setPayment_terms(pt);
			doObj.setNarration(narration);
			doObj.setClearance_status(1);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY SALES RETURN DATA");
		}
		return doObj;
	}

	public SalesReturnDetailsDO createSalesReturnDetailsDO(int pid, String gstpv, int rquantity, String unitRate,
			String netWeight, String taxableAmt, long bankId, String igstamt, String cgstamt, String sgstamt, String totAmt) {
		SalesReturnDetailsDO doObj = new SalesReturnDetailsDO();
		doObj.setProd_code(pid);
		doObj.setGstp(gstpv);
		doObj.setRtn_qty(rquantity);
		doObj.setUnit_rate(unitRate);
		doObj.setNet_weight(netWeight);
		doObj.setAmount(taxableAmt);
		doObj.setBank_id(bankId);
		doObj.setIgst_amount(igstamt);
		doObj.setCgst_amount(cgstamt);
		doObj.setSgst_amount(sgstamt);
		doObj.setAamount(totAmt);
		return doObj;
	}
}
