package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.others.CreditNotesDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class CreditNotesBO {

	

	public CreditNotesDO createDO(String ndate, int ntype, String invno, long cvid, long refDate, 
			long pid, String gstp, String tamount, String igstAmt, String cgstAmt, 
			String sgstAmt, String noteAmt, long bid, int nreasons, long agencyId) throws BusinessException {

		CreditNotesDO doObj = new CreditNotesDO();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setNote_date(sdf.parse(ndate).getTime());
			doObj.setCredit_note_type(ntype);
			doObj.setCvo_id(cvid);
			doObj.setRef_no(invno);
			doObj.setRef_date(refDate);
			doObj.setProd_code(pid);
			doObj.setDbal_amount("0.00");
			doObj.setGstp(gstp);
			doObj.setTaxable_amount(tamount);
			doObj.setIgst_amount(igstAmt);
			doObj.setCgst_amount(cgstAmt);
			doObj.setSgst_amount(sgstAmt);
			doObj.setCredit_amount(noteAmt);
			doObj.setBank_id(bid);
			doObj.setNreasons(nreasons);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			
		} catch (Exception e){
			throw new BusinessException("UNABLE TO SAVE AGENCY CREDIT NOTES DATA");
		}
		return doObj;
	}
	
}
