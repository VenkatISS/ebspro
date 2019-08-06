package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDetailsDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class PurchaseReturnBO {

	public PurchaseReturnDO createDO(String prid, String srdate, long invrefdate, long custid, 
			String pramount, String narratione, long agencyId) throws BusinessException {
		PurchaseReturnDO doObj = new PurchaseReturnDO();
		try {
			doObj.setInv_ref_no(prid);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setInv_date((sdf.parse(srdate)).getTime());
			doObj.setInv_ref_date(invrefdate);
			doObj.setCvo_id(custid);
			doObj.setDbal_amount("0.00");
			doObj.setInv_amt(pramount);
			doObj.setNarration(narratione);
			doObj.setClearance_status(1);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY PURCHASE RETURN DATA");
		}
		return doObj;
	}

	public PurchaseReturnDetailsDO createPurchaseReturnDetailsDO(int pid, String gstpv, int rquantity, String unitRate,
			String netWeight, String amt, String igstamt, String cgstamt, String sgstamt, String aamt, String vno) {
		PurchaseReturnDetailsDO doObj = new PurchaseReturnDetailsDO();
		doObj.setProd_code(pid);
		doObj.setGstp(gstpv);
		doObj.setRtn_qty(rquantity);
		doObj.setUnit_rate(unitRate);
		doObj.setNet_weight(netWeight);
		doObj.setAmount(amt);
		doObj.setIgst_amount(igstamt);
		doObj.setCgst_amount(cgstamt);
		doObj.setSgst_amount(sgstamt);
		doObj.setAamount(aamt);
		doObj.setVehicle_no(vno);
		return doObj;
	}
}
