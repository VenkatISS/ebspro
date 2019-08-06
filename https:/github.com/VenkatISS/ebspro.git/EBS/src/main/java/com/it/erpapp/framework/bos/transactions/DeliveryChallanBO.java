package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.sales.DeliveryChallanDO;
import com.it.erpapp.framework.model.transactions.sales.DeliveryChallanDetailsDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class DeliveryChallanBO {

	public DeliveryChallanDO createDO(String dcDate,long dcrefdate, long custId, long staffId, String dmode, String invNo,
			long fleetId, String dcAmount, String dinstr, long agencyId) throws BusinessException {
		DeliveryChallanDO doObj = new DeliveryChallanDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setDc_date(sdf.parse(dcDate).getTime());
			doObj.setDc_ref_date(dcrefdate);
			doObj.setCustomer_id(custId);
			doObj.setStaff_id(staffId);
			doObj.setFleet_id(fleetId); 
			doObj.setDelivery_mode(dmode);
			doObj.setInv_no(invNo);
			doObj.setDelivery_instructions(dinstr);
			doObj.setDc_amount(dcAmount);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY DELIVERY CHALLAN DATA");
		}
		return doObj;
	}
	
	public DeliveryChallanDetailsDO createDeliveryChallanDetailsDO(String pid, String vatp, int quantity, String unitRate,
			String basicPrice, String igstAmount, String sgstAmount, String cgstAmount, String prodAmount) {
		DeliveryChallanDetailsDO doObj = new DeliveryChallanDetailsDO();
		doObj.setProd_code(pid);
		doObj.setGstp(vatp);
		doObj.setQuantity(quantity);
		doObj.setUnit_rate(unitRate);
		doObj.setBasic_amount(basicPrice);
		doObj.setIgst_amount(igstAmount);
		doObj.setSgst_amount(sgstAmount);
		doObj.setCgst_amount(cgstAmount);
		doObj.setProd_amount(prodAmount);
		return doObj;
	}
}
