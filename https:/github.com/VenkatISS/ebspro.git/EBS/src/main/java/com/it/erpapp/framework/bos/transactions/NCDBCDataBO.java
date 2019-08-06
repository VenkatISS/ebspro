package com.it.erpapp.framework.bos.transactions;

import java.text.SimpleDateFormat;

import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDetailsDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class NCDBCDataBO {

	public NCDBCInvoiceDO createDO(String invDate, String custNo, long staffId, 
			 String tdAmount, String invAmount, int ctype, long bomId,
			int nocs, String cashAmount, String onlineAmount, long agencyId, long bankId, String totdiscount) throws BusinessException {
		NCDBCInvoiceDO doObj = new NCDBCInvoiceDO();
		try {
			doObj.setBom_id(bomId);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			doObj.setInv_date(sdf.parse(invDate).getTime());
			doObj.setCustomer_no(custNo);
			doObj.setStaff_id(staffId);
			doObj.setDep_amount(tdAmount);
			doObj.setInv_amount(invAmount);
			doObj.setConn_type(ctype);
			doObj.setNo_of_conns(nocs);
			doObj.setCash_amount(cashAmount);
			doObj.setOnline_amount(onlineAmount);
			doObj.setDiscount(totdiscount);
			doObj.setCreated_by(agencyId);
			doObj.setCreated_date(System.currentTimeMillis());
			doObj.setVersion(1);
			doObj.setDeleted(0);
			doObj.setBank_id(bankId);
		} catch (Exception e){
			e.printStackTrace();
			throw new BusinessException("UNABLE TO SAVE AGENCY RECONNECTION DATA");
		}
		return doObj;
	}
	
	public NCDBCInvoiceDetailsDO createNCDBCInvoiceDetails(int nocs, long pid, 
			String gstp, int quantity, String unitRate,
			String unitRateDiscount, String basicPrice, String depositAmount,
			String sgstAmount, String cgstAmount, String prodAmount,
			String opbasicprice, String opTaxableAmt, String opTotalAmt, String opCgstAmt, String opSgstAmt, String opIgstAmt) {

		NCDBCInvoiceDetailsDO doObj = new NCDBCInvoiceDetailsDO();
		doObj.setProd_code(pid);
		doObj.setQuantity(nocs*quantity);
		doObj.setUnit_rate(unitRate);
		doObj.setDisc_unit_rate(unitRateDiscount);
		doObj.setGstp(gstp);
		doObj.setDeposit_amount(depositAmount);
		doObj.setBasic_price(basicPrice);
		doObj.setSgst_amount(sgstAmount);
		doObj.setCgst_amount(cgstAmount);
		doObj.setProduct_amount(prodAmount);
		
		doObj.setOp_basic_price(opbasicprice);
		doObj.setOp_taxable_amt(opTaxableAmt);
		doObj.setOp_total_amt(opTotalAmt);
		doObj.setOp_cgst_amt(opCgstAmt);
		doObj.setOp_sgst_amt(opSgstAmt);
		doObj.setOp_igst_amt(opIgstAmt);
		
		return doObj;
	}
}
