package com.it.erpapp.framework.bos;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.GSTPaymentDetailsDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class GSTPaymentDetailsBO {

	public GSTPaymentDetailsDO createDO(int month, int year, String pdate,int pstatus, int tax_type, String gst_amount,
			String incometax_amount, long agencyId) throws BusinessException {
		GSTPaymentDetailsDO doObj = new GSTPaymentDetailsDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			doObj.setMonth(month);
			doObj.setYear(year);
			doObj.setPdate((sdf.parse(pdate)).getTime());
			doObj.setPstatus(pstatus);
			doObj.setTax_type(tax_type);
			doObj.setGst_amount(gst_amount);
			doObj.setIncometax_amount(incometax_amount);
			doObj.setDt_gst_amount("0.00");
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);

		} catch (Exception e) {
			throw new BusinessException("UNABLE TO SAVE GST PAYMENT DETAILS DATA");
		}
		return doObj;
	}
}