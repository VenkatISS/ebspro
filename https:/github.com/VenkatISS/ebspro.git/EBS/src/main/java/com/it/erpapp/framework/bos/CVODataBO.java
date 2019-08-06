package com.it.erpapp.framework.bos;

import com.it.erpapp.framework.model.CVODataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class CVODataBO {

	public CVODataDO createDO(String name, String contact, String caddr, int gstyn,
			String tin, String email, String pan,String balances, int category, long agencyId)
			throws BusinessException {
		CVODataDO cvodo = new CVODataDO();
		try {
			cvodo.setCvo_name(name);
			cvodo.setCvo_address(caddr);
			
			  if(contact=="")
                  cvodo.setCvo_contact("NA");
                  else
                          cvodo.setCvo_contact(contact);
			cvodo.setIs_gst_reg(gstyn);
			cvodo.setCvo_tin(tin);
			cvodo.setCvo_email(email);
			cvodo.setCvo_pan(pan);
			cvodo.setObal(balances);
			cvodo.setCbal(balances);
			cvodo.setEbal("0.00");
			cvodo.setCvo_cat(category);
			cvodo.setCreated_by(agencyId);
			cvodo.setCreated_date(System.currentTimeMillis());
			cvodo.setVersion(1);
			cvodo.setDeleted(0);
			
		} catch (Exception e) {
			throw new BusinessException("UNABLE TO SAVE AGENCY CUSTOMER / VENDOR DATA");
		}
		return cvodo;
	}
	
	//update cvo


    public CVODataDO updateDO(String name, String contact, String caddr, String gstyn,
                    String tin, String email, String pan,String balances,String cbalances, String category, String cvoId,long agencyId)
                    throws BusinessException {
            CVODataDO cvodo = new CVODataDO();
            try {
                    cvodo.setCvo_name(name);
                    cvodo.setCvo_address(caddr);
                    if(contact=="")
                    cvodo.setCvo_contact("NA");
                    else
                            cvodo.setCvo_contact(contact);
                    if(gstyn.equals("1"))
                    cvodo.setIs_gst_reg(1);
                    else
                            cvodo.setIs_gst_reg(2);

                    cvodo.setCvo_tin(tin);
                    cvodo.setCvo_email(email);
                    cvodo.setCvo_pan(pan);
                    cvodo.setObal(balances);
                    cvodo.setCbal(cbalances);
                    cvodo.setEbal("0.00");
                    if(category.equals("VENDOR"))
                            cvodo.setCvo_cat(0);
                    else if(category.equals("CUSTOMER"))
                            cvodo.setCvo_cat(1);
                    else if(category.equals("OMC-IOCL"))
                            cvodo.setCvo_cat(2);
                    else if(category.equals("OTHERS"))
                            cvodo.setCvo_cat(3);

                    cvodo.setId(Long.parseLong(cvoId));
                    cvodo.setCreated_by(agencyId);
                    cvodo.setCreated_date(System.currentTimeMillis());
                    cvodo.setModified_date(System.currentTimeMillis());
                    cvodo.setVersion(1);
                    cvodo.setDeleted(0);

            } catch (Exception e) {
                    throw new BusinessException("UNABLE TO UPDATE AGENCY CUSTOMER / VENDOR DATA");
            }
            return cvodo;
    }
}

