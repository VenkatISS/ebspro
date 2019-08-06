package com.it.erpapp.framework.bos;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class AgencyCVOBalanceDataBO {

	Logger logger = Logger.getLogger(AgencyCVOBalanceDataBO.class.getName());

	public AgencyCVOBalanceDataDO createDO(String balances, int cvo_cat, long agencyId, int ttype, 
			String invno, int cvo_flag,long cvo_id,String inv_date, String discount) throws BusinessException {

		AgencyCVOBalanceDataDO acvoDO = new AgencyCVOBalanceDataDO();
		try {
//			acvoDO.setRef_id(ref_id);
			acvoDO.setInv_ref_no(invno);
			acvoDO.setCvoflag(cvo_flag);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(("0").equals(inv_date)) {
				acvoDO.setInv_date(System.currentTimeMillis());
			}else {
				acvoDO.setInv_date((sdf.parse(inv_date)).getTime());
			}
			
			acvoDO.setTrans_type(ttype);
			acvoDO.setCvo_cat(cvo_cat);
			acvoDO.setCvo_refid(cvo_id);
			acvoDO.setDiscount(discount);

			acvoDO.setAmount(balances);
			acvoDO.setCbal_amount(balances);
			
			acvoDO.setCreated_by(agencyId);
			acvoDO.setCreated_date(System.currentTimeMillis());
			acvoDO.setVersion(1);
			acvoDO.setDeleted(0);
			
		}catch (Exception e){
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException("UNABLE TO SAVE AGENCY CVO DATA IN CVO_BALANCE_DATA");
		}
		return acvoDO;
	}
	
	//update cvo

    public AgencyCVOBalanceDataDO updateDO(String balances,String cbalances, String cvo_cat, long agencyId, int ttype,
                    String invno, int cvo_flag,long cvo_id,String inv_date, String discount) throws BusinessException {

            AgencyCVOBalanceDataDO acvoDO = new AgencyCVOBalanceDataDO();
            try {
//                    acvoDO.setRef_id(ref_id);
                    acvoDO.setInv_ref_no(invno);
                    acvoDO.setCvoflag(cvo_flag);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    if(("0").equals(inv_date)) {
                            acvoDO.setInv_date(System.currentTimeMillis());
                    }else {
                            acvoDO.setInv_date((sdf.parse(inv_date)).getTime());
                    }

                    acvoDO.setTrans_type(ttype);
                    if(cvo_cat.equals("VENDOR"))
                            acvoDO.setCvo_cat(0);
                    else if(cvo_cat.equals("CUSTOMER"))
                            acvoDO.setCvo_cat(1);
                    else if(cvo_cat.equals("OMC-IOCL"))
                            acvoDO.setCvo_cat(2);
                    else if(cvo_cat.equals("OTHERS"))
                            acvoDO.setCvo_cat(3);
                    acvoDO.setCvo_refid(cvo_id);
                    acvoDO.setDiscount(discount);

                    acvoDO.setAmount(balances);
                    acvoDO.setCbal_amount(cbalances);

                    acvoDO.setCreated_by(agencyId);
                    acvoDO.setCreated_date(System.currentTimeMillis());
                    acvoDO.setVersion(1);
                    acvoDO.setDeleted(0);

            }catch (Exception e){
                    e.printStackTrace();
                    logger.error(e);
                    throw new BusinessException("UNABLE TO UPADTE AGENCY CVO DATA IN CVO_BALANCE_DATA");
            }
            return acvoDO;
    }
}
