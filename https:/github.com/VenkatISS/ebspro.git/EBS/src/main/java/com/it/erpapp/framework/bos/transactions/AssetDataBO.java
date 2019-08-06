package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.others.AssetDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class AssetDataBO {

	public AssetDataDO createDO(String atDate, int ttype, String itemDesc, 
			int amah, int mop, long bankId, String avalue, long staffId, long agencyId) throws BusinessException {
		AssetDataDO doObj = new AssetDataDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setAsset_tdate((sdf.parse(atDate)).getTime());
			doObj.setAsset_ttype(ttype);
			doObj.setAsset_desc(itemDesc);
			doObj.setAsset_ah(amah);
			doObj.setAsset_mop(mop);
			doObj.setAsset_value(avalue);
			doObj.setBank_id(bankId);
			doObj.setStaff_id(staffId);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY ASSETS DATA");
		}
		return doObj;
	}
}
