package com.it.erpapp.framework.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.it.erpapp.framework.model.GSTPaymentDetailsDO;
import com.it.erpapp.framework.model.GSTPaymentsDataDO;
import com.it.erpapp.framework.model.transactions.ARBPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.OtherPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.PurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.others.CreditNotesDO;
import com.it.erpapp.framework.model.transactions.others.DebitNotesDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.RCDataDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.TVDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

public class GSTReportsPersistenceManager {

	Logger logger = Logger.getLogger(GSTReportsPersistenceManager.class.getName());

	//DOM
	@SuppressWarnings("unchecked")
	public List<PurchaseInvoiceDO> getAgencyGSTDOMPurchaseInvoicesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<PurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<PurchaseInvoiceDO> query = session.createQuery("from PurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 and prod_code <= 3");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<PurchaseInvoiceDO> result = query.getResultList();
			
			if(result.size()>0) {
				for (PurchaseInvoiceDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// DOM Sales Invoices
	@SuppressWarnings("unchecked")
	public List<DOMSalesInvoiceDO> getAgencyGSTDSInvoicesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<DOMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<DOMSalesInvoiceDO> query = session.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and si_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<DOMSalesInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (DOMSalesInvoiceDO dataDO : result) {
					Query<DOMSalesInvoiceDetailsDO> squery = session.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1  and prod_code <= 3 ");
					squery.setParameter(1, dataDO.getId());
					List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
						doList.add(dataDO);
					}
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseInvoiceDO> getAgencyGSTCOMPurchaseInvoicesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<PurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<PurchaseInvoiceDO> query = session.createQuery("from PurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 and prod_code > 3 and (prod_code < 10 or prod_code =12)");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<PurchaseInvoiceDO> result = query.getResultList(); 
			
			if(result.size()>0) {
				for (PurchaseInvoiceDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<COMSalesInvoiceDO> getAgencyGSTCSInvoicesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<COMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<COMSalesInvoiceDO> query = session.createQuery("from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and si_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<COMSalesInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (COMSalesInvoiceDO dataDO : result) {
					Query<COMSalesInvoiceDetailsDO> squery = session.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1 and prod_code > 3 and prod_code < 10");
					squery.setParameter(1, dataDO.getId());
					List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
					}
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// ARB Purchase Invoices
	@SuppressWarnings("unchecked")
	public List<ARBPurchaseInvoiceDO> getAgencyGSTARBPurchaseInvoicesByDateRange(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<ARBPurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<ARBPurchaseInvoiceDO> query = session.createQuery("from ARBPurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<ARBPurchaseInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (ARBPurchaseInvoiceDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// ARB Purchase Invoices
	@SuppressWarnings("unchecked")
	public List<OtherPurchaseInvoiceDO> getAgencyGSTOtherPurchaseInvoicesByDateRange(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<OtherPurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<OtherPurchaseInvoiceDO> query = session.createQuery("from OtherPurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 and taxable = ?5");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(5, 1);
			List<OtherPurchaseInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (OtherPurchaseInvoiceDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<ARBSalesInvoiceDO> getAgencyGSTARBSInvoicesByDateRange(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<ARBSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<ARBSalesInvoiceDO> query = session.createQuery("from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and si_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<ARBSalesInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (ARBSalesInvoiceDO dataDO : result) {
					Query<ARBSalesInvoiceDetailsDO> squery = session.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1 ");
					squery.setParameter(1, dataDO.getId());
					List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
					}
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	
	@SuppressWarnings("unchecked")
	public List<NCDBCInvoiceDO> getAgencyGSTNCDBCInvoicesByDateRange(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<NCDBCInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<NCDBCInvoiceDO> query = session.createQuery("from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (NCDBCInvoiceDO dataDO : result) {
					Query<NCDBCInvoiceDetailsDO> squery = session.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 ");
					squery.setParameter(1, dataDO.getId());
					List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
					}
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<RCDataDO> getAgencyGSTRCInvoicesByDateRange(long agencyId,long fromDate,long toDate) throws BusinessException {
		List<RCDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<RCDataDO> query = session.createQuery("from RCDataDO where created_by = ?1 and deleted = ?2 "
			+ " and rc_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<RCDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (RCDataDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<TVDataDO> getAgencyGSTTVInvoicesByDateRange(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<TVDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<TVDataDO> query = session.createQuery("from TVDataDO where created_by = ?1 and deleted = ?2 "
			+ " and tv_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<TVDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (TVDataDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseReturnDO> getAgencyGSTPurchaseReturnsInvoicesByDateRange(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<PurchaseReturnDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<PurchaseReturnDO> query = session.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<PurchaseReturnDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (PurchaseReturnDO dataDO : result) {
					Query<PurchaseReturnDetailsDO> squery = session.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 ");
					squery.setParameter(1, dataDO.getId());
					List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
						doList.add(dataDO);
					}
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<SalesReturnDO> getAgencyGSTSalesReturnsInvoicesByDateRange(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<SalesReturnDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<SalesReturnDO> query = session.createQuery("from SalesReturnDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<SalesReturnDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (SalesReturnDO dataDO : result) {
					Query<SalesReturnDetailsDO> squery = session.createQuery("from SalesReturnDetailsDO where sr_ref_id = ?1 ");
					squery.setParameter(1, dataDO.getId());
					List<SalesReturnDetailsDO> itemsResult = squery.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
						doList.add(dataDO);
					}
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}
	@SuppressWarnings("unchecked")
	public List<CreditNotesDO> getAgencyCreditNotesByDateRange(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<CreditNotesDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<CreditNotesDO> query = session.createQuery("from CreditNotesDO where created_by = ?1 and deleted = ?2 "
			+ " and note_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<CreditNotesDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (CreditNotesDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<DebitNotesDO> getAgencyDebitNotesByDateRange(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<DebitNotesDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<DebitNotesDO> query = session.createQuery("from DebitNotesDO where created_by = ?1 and deleted = ?2 "
			+ " and note_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<DebitNotesDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (DebitNotesDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}
	
	@SuppressWarnings("unchecked")
	public List<GSTPaymentsDataDO> getAgencyGSTPaymentsData(
			long agencyId,String m,String y) throws BusinessException {
		List<GSTPaymentsDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<GSTPaymentsDataDO> query = session.createQuery("from GSTPaymentsDataDO where month = ?3 and year = ?4 and created_by = ?1 and deleted = ?2");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, Integer.parseInt(m));
			query.setParameter(4, Integer.parseInt(y));
			List<GSTPaymentsDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (GSTPaymentsDataDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}
	
	@SuppressWarnings("unchecked")
	public void saveAgencyPaymentsData(long agencyId,String m,String y,String sigst,String scgst,String ssgst, String pigst, String pcgst, String psgst) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query<GSTPaymentsDataDO> query = session.createQuery("from GSTPaymentsDataDO where month = ?3 and year = ?4 and created_by = ?1 and deleted = ?2");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, Integer.parseInt(m));
			query.setParameter(4, Integer.parseInt(y));
			List<GSTPaymentsDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (GSTPaymentsDataDO dataDO : result) {
					dataDO.setCreditors_igst(pigst);
					dataDO.setCreditors_cgst(pcgst);
					dataDO.setCreditors_sgst(psgst);
					dataDO.setLiability_igst(sigst);
					dataDO.setLiability_cgst(scgst);
					dataDO.setLiability_sgst(ssgst);
					session.update(dataDO);
				}
			}else{
				GSTPaymentsDataDO gstpDataDO = new GSTPaymentsDataDO();
				gstpDataDO.setMonth(Integer.parseInt(m));
				gstpDataDO.setYear(Integer.parseInt(y));
				gstpDataDO.setStatus(0);
				gstpDataDO.setSet_off_date(0);
				gstpDataDO.setPay_off_date(0);
				gstpDataDO.setCreditors_igst(pigst);
				gstpDataDO.setCreditors_cgst(pcgst);
				gstpDataDO.setCreditors_sgst(psgst);
				gstpDataDO.setLiability_igst(sigst);
				gstpDataDO.setLiability_cgst(scgst);
				gstpDataDO.setLiability_sgst(ssgst);
				gstpDataDO.setCash_cgst("0");
				gstpDataDO.setCash_sgst("0");
				gstpDataDO.setCreated_by(agencyId);
				gstpDataDO.setCreated_date(System.currentTimeMillis());
				gstpDataDO.setVersion(1);
				gstpDataDO.setDeleted(0);
				session.save(gstpDataDO);
			}
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> GSTReportsPersistenceManager --> saveAgencyPaymentsData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void setOffAgencyPaymentsData(long agencyId,String m,String y,String sigst,String scgst,String ssgst, String pigst, String pcgst, String psgst, String ccgst, String csgst) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query<GSTPaymentsDataDO> query = session.createQuery("from GSTPaymentsDataDO where month = ?3 and year = ?4 and created_by = ?1 and deleted = ?2");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, Integer.parseInt(m));
			query.setParameter(4, Integer.parseInt(y));
			List<GSTPaymentsDataDO> result = query.getResultList();
			if(result.size()>0) {
				for (GSTPaymentsDataDO dataDO : result) {
					dataDO.setCreditors_igst(pigst);
					dataDO.setCreditors_cgst(pcgst);
					dataDO.setCreditors_sgst(psgst);
					dataDO.setLiability_igst(sigst);
					dataDO.setLiability_cgst(scgst);
					dataDO.setLiability_sgst(ssgst);
					dataDO.setCash_cgst(ccgst);
					dataDO.setCash_sgst(csgst);
					dataDO.setSet_off_date(System.currentTimeMillis());
					dataDO.setStatus(1);
					session.update(dataDO);
				}
			}else{
				GSTPaymentsDataDO gstpDataDO = new GSTPaymentsDataDO();
				gstpDataDO.setMonth(Integer.parseInt(m));
				gstpDataDO.setYear(Integer.parseInt(y));
				gstpDataDO.setStatus(0);
				gstpDataDO.setSet_off_date(0);
				gstpDataDO.setPay_off_date(0);				
				gstpDataDO.setCreditors_igst(pigst);
				gstpDataDO.setCreditors_cgst(pcgst);
				gstpDataDO.setCreditors_sgst(psgst);
				gstpDataDO.setLiability_igst(sigst);
				gstpDataDO.setLiability_cgst(scgst);
				gstpDataDO.setLiability_sgst(ssgst);
				gstpDataDO.setCash_cgst("0");
				gstpDataDO.setCash_sgst("0");
				gstpDataDO.setCreated_by(agencyId);
				gstpDataDO.setCreated_date(System.currentTimeMillis());
				gstpDataDO.setVersion(1);
				gstpDataDO.setDeleted(0);
				session.save(gstpDataDO);
			}
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> GSTReportsPersistenceManager --> setOffAgencyPaymentsData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public void payoffAgencyPaymentsData(long agencyId, String m, String y, String tgstAmount,long cdl) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query<GSTPaymentDetailsDO> query = session.createQuery("from GSTPaymentDetailsDO where created_by = ?1 order by created_date desc ").setMaxResults(1);
			query.setParameter(1, agencyId);
			List<GSTPaymentDetailsDO> result = query.getResultList();
			GSTPaymentDetailsDO gstpDO = new GSTPaymentDetailsDO();
			if(!(result.isEmpty())) {
				GSTPaymentDetailsDO gpdo = result.get(0);
				gstpDO.setPayment_id(0);
				gstpDO.setMonth(Integer.parseInt(m));
				gstpDO.setYear(Integer.parseInt(y));
				gstpDO.setPstatus(2);
				gstpDO.setPdate(cdl);
				gstpDO.setTax_type(1);
				gstpDO.setGst_amount(tgstAmount);
				
				BigDecimal gstcb = new BigDecimal(gpdo.getFinal_gst_amount());
				BigDecimal paymentAmt = new BigDecimal(tgstAmount);
				gstpDO.setTotal_gst_amount(gstcb.subtract(paymentAmt).toString());
				gstpDO.setFinal_gst_amount(gstcb.subtract(paymentAmt).toString());

				gstpDO.setDt_gst_amount("0.00");
				gstpDO.setIncometax_amount("0.00");
				gstpDO.setCreated_by(agencyId);
				gstpDO.setCreated_date(System.currentTimeMillis());
				gstpDO.setModified_by(0);
				gstpDO.setModified_date(0);
				gstpDO.setVersion(1);
				gstpDO.setDeleted(0);
			}
			
			Query<GSTPaymentsDataDO> gpquery = session.createQuery("from GSTPaymentsDataDO where month = ?3 and year = ?4 and created_by = ?1 and deleted = ?2");
			gpquery.setParameter(1, agencyId);
			gpquery.setParameter(2, 0);
			gpquery.setParameter(3, Integer.parseInt(m));
			gpquery.setParameter(4, Integer.parseInt(y));
			List<GSTPaymentsDataDO> gpresult = gpquery.getResultList();
			if(!(gpresult.isEmpty())) {
				for (GSTPaymentsDataDO dataDO : gpresult) {
					dataDO.setStatus(2);
					dataDO.setPay_off_date(System.currentTimeMillis());
					session.update(dataDO);
				}
			}
			session.save(gstpDO);
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> GSTReportsPersistenceManager --> payoffAgencyPaymentsData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<GSTPaymentDetailsDO> getAgencyPaymentDetailsData(long agencyId) throws BusinessException {
		List<GSTPaymentDetailsDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<GSTPaymentDetailsDO> query = session.createQuery("from GSTPaymentDetailsDO where created_by = ?1 order by created_date desc").setMaxResults(1);
			query.setParameter(1, agencyId);
			List<GSTPaymentDetailsDO> result = query.getResultList(); 
			
			if(!result.isEmpty()) {
				for (GSTPaymentDetailsDO dataDO : result) {
					doList.add(dataDO);
				}
			}
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}
}