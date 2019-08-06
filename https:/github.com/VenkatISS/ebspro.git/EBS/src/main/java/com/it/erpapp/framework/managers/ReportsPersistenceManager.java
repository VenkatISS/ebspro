package com.it.erpapp.framework.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.it.erpapp.cache.CacheManager;
import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.framework.model.AgencyCashDataDO;
import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.CVODataDO;
import com.it.erpapp.framework.model.GSTPaymentDetailsDO;
import com.it.erpapp.framework.model.ProductCategoryDO;
import com.it.erpapp.framework.model.pps.ARBDataDO;
import com.it.erpapp.framework.model.pps.EquipmentDataDO;
import com.it.erpapp.framework.model.transactions.ARBPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.OtherPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.PayablesDO;
import com.it.erpapp.framework.model.transactions.PurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.ReceivablesDO;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.framework.model.transactions.others.CreditNotesDO;
import com.it.erpapp.framework.model.transactions.others.DebitNotesDO;
import com.it.erpapp.framework.model.transactions.others.PaymentsDataDO;
import com.it.erpapp.framework.model.transactions.others.ReceiptsDataDO;
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
import com.it.erpapp.framework.model.vos.NCDBCRCTVReportVO;
import com.it.erpapp.framework.model.vos.StockReportVO;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

public class ReportsPersistenceManager {
	
	Logger logger = Logger.getLogger(ReportsPersistenceManager.class.getName());
	
	// Purchase Invoices
	@SuppressWarnings("unchecked")
	public List<PurchaseInvoiceDO> getAgencyPurInvoicesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate, int productCode) throws BusinessException {
		List<PurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String includeProductCode = productCode != 0 ? "and prod_code = ?5":"";
			Query<PurchaseInvoiceDO> query = session.createQuery("from PurchaseInvoiceDO where created_by = ?1 and deleted = ?2"
					+ " and inv_date between ?3 and ?4 "+includeProductCode);
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			if (productCode > 0) {
				query.setParameter(5, productCode);
			}
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
	public List<AgencyStockDataDO> getAgencyStockLedgerDataByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate, long productCode) throws BusinessException {
		List<AgencyStockDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {

			String includeProductCode = ((productCode != 0) && (productCode < 13) ) ? 
					( (productCode < 10) ? "and prod_code = ?6 and stock_flag != 10" : "and prod_code = ?6" ) : 
					( ((productCode != 0) && (productCode >= 13) ) ? "and prod_id = ?6" : "" ) ;
					
			Query<AgencyStockDataDO> query = session.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and inv_no != ?3"
					+ " and trans_date between ?4 and ?5 "+includeProductCode +" order by trans_date,created_date");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, "NA");
			query.setParameter(4, fromDate);
			query.setParameter(5, toDate);
			if (productCode > 0 && productCode < 13) {
				query.setParameter(6, (int) productCode);
			}else if(productCode > 0 && productCode >= 13){
				query.setParameter(6, productCode);
			}

			List<AgencyStockDataDO> result = query.getResultList();
			if(result.size()>0) {
				for (AgencyStockDataDO dataDO : result) {
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
	public String getAgencyStockLedgerOpsByProductType(
			long agencyId,long fromDate, long productCode) throws BusinessException {
		String opbal = "0";
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {			
			String pcode = (productCode < 13) ? "prod_code" : "prod_id";
			
//			Query<AgencyStockDataDO> dataQuery = session.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted != ?2 and "+ pcode +" = ?3 "
//						+ "  and trans_date < ?4 order by trans_date desc, created_date desc").setMaxResults(1);
//			dataQuery.setParameter(1, agencyId);
//			dataQuery.setParameter(2, 3);
//			if(productCode < 13) dataQuery.setParameter(3, (int) productCode);
//			else dataQuery.setParameter(3, productCode);
//			dataQuery.setParameter(4, fromDate);
			Query<AgencyStockDataDO> dataQuery = session.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and "+ pcode +" = ?3 "
					+ "  and trans_date < ?4 order by trans_date desc, created_date desc").setMaxResults(1);
			dataQuery.setParameter(1, agencyId);
			dataQuery.setParameter(2, 0);
			if(productCode < 13) dataQuery.setParameter(3, (int) productCode);
			else dataQuery.setParameter(3, productCode);
			dataQuery.setParameter(4, fromDate);

			List<AgencyStockDataDO> result = dataQuery.getResultList();
			
			if(result.size()>0) {
				for (AgencyStockDataDO dataDO : result) {
					opbal = Integer.toString(dataDO.getCs_fulls());
				}
			}else {
//				Query<AgencyStockDataDO> dQuery = session.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted != ?2 and "+ pcode +" = ?3 "
//						+ "  and trans_date = ?4 order by trans_date desc, created_date").setMaxResults(1);
//				dQuery.setParameter(1, agencyId);
//				dQuery.setParameter(2, 3);
//				if(productCode < 13) dQuery.setParameter(3, (int) productCode);
//				else dQuery.setParameter(3, productCode);
//				dQuery.setParameter(4, fromDate);
				
				Query<AgencyStockDataDO> dQuery = session.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and "+ pcode +" = ?3 "
						+ "  and trans_date = ?4 order by trans_date desc, created_date").setMaxResults(1);
				dQuery.setParameter(1, agencyId);
				dQuery.setParameter(2, 0);
				if(productCode < 13) dQuery.setParameter(3, (int) productCode);
				else dQuery.setParameter(3, productCode);
				dQuery.setParameter(4, fromDate);
				
				result = dQuery.getResultList();

				if(result.size()>0) {
					for (AgencyStockDataDO dataDO : result) {
						opbal = Integer.toString(dataDO.getCs_fulls());
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return opbal;
	}

	// Purchase Invoices
	@SuppressWarnings("unchecked")
	public List<PurchaseInvoiceDO> getAgencyPurchaseInvoicesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate, int productCode) throws BusinessException {
		List<PurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String includeProductCode = productCode != 0 ? "and prod_code = ?5":"";
			Query<PurchaseInvoiceDO> query = session.createQuery("from PurchaseInvoiceDO where created_by = ?1 and (deleted = ?2 OR deleted = ?6) "
					+ " and inv_date between ?3 and ?4 "+includeProductCode);
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(6, 2);
			if (productCode > 0) {
				query.setParameter(5, productCode);
			}
			List<PurchaseInvoiceDO> result = query.getResultList(); 
			
			if(result.size()>0) {
				for (PurchaseInvoiceDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// Purchase Invoices
	@SuppressWarnings("unchecked")
	public List<PurchaseInvoiceDO> getAgencyPurchaseInvoicesByDateRangeAndVendor(
			long agencyId,long fromDate,long toDate, long vendorId) throws BusinessException {
		List<PurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<PurchaseInvoiceDO> query = session.createQuery("from PurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 and omc_id = ?5");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(5, vendorId);
			List<PurchaseInvoiceDO> result = query.getResultList(); 
			
			if(result.size()>0) {
				for (PurchaseInvoiceDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// ARB Purchase Invoices
	@SuppressWarnings("unchecked")
	public List<ARBPurchaseInvoiceDO> getAgencyARBPurchaseInvoicesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate, long productCode) throws BusinessException {
		List<ARBPurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String includeProductCode = productCode != 0 ? "and arb_code = ?5":"";
			Query<ARBPurchaseInvoiceDO> query = session.createQuery("from ARBPurchaseInvoiceDO where created_by = ?1 and (deleted = ?2 OR deleted = ?6) "
					+ " and inv_date between ?3 and ?4 "+includeProductCode);
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(6, 2);
			if (productCode > 0) {
				query.setParameter(5, productCode);
			}
			List<ARBPurchaseInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (ARBPurchaseInvoiceDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// ARB Purchase Invoices
	@SuppressWarnings("unchecked")
	public List<ARBPurchaseInvoiceDO> getAgencyARBPurchaseInvoicesByDateRangeAndVendor(
			long agencyId,long fromDate,long toDate, long vendorId) throws BusinessException {
		List<ARBPurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<ARBPurchaseInvoiceDO> query = session.createQuery("from ARBPurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 and vendor_id = ?5");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(5, vendorId);
			List<ARBPurchaseInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (ARBPurchaseInvoiceDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// DOM Sales Invoices
		@SuppressWarnings("unchecked")
		public List<DOMSalesInvoiceDO> getAgencyDSInvoicesByDateRangeAndProductType(
				long agencyId,long fromDate,long toDate, int productCode) throws BusinessException {
			List<DOMSalesInvoiceDO> doList = new ArrayList<>();
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				Query<DOMSalesInvoiceDO> query = session.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5)"
						+ " and si_date between ?3 and ?4 ");
				query.setParameter(1, agencyId);
				query.setParameter(2, 0);
				query.setParameter(3, fromDate);
				query.setParameter(4, toDate);
				query.setParameter(5, 2);
				List<DOMSalesInvoiceDO> result = query.getResultList(); 
				if(result.size()>0) {
					for (DOMSalesInvoiceDO dataDO : result) {
						String includeProductCode = productCode != 0 ? "and prod_code = ?2":"";
						Query<DOMSalesInvoiceDetailsDO> squery = session.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1 "+includeProductCode);
						squery.setParameter(1, dataDO.getId());
						if (productCode > 0) {
							squery.setParameter(2, productCode);
						}
						List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
						if(itemsResult.size()>0){
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					}
				} 
			}catch(Exception e) {
				e.printStackTrace();
				logger.error(e);
				throw new BusinessException(e.getMessage());
			}finally {
				session.clear();
				session.close();
			}
			return doList;
		}
		
		// DOM Sales Invoices
		@SuppressWarnings("unchecked")
		public List<DOMSalesInvoiceDO> getAgencyDSRInvoicesByDateRangeAndProductType(
				long agencyId,long fromDate,long toDate, int productCode) throws BusinessException {
			List<DOMSalesInvoiceDO> doList = new ArrayList<>();
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				Query<DOMSalesInvoiceDO> query = session.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2"
						+ " and si_date between ?3 and ?4 ");
				query.setParameter(1, agencyId);
				query.setParameter(2, 0);
				query.setParameter(3, fromDate);
				query.setParameter(4, toDate);
				List<DOMSalesInvoiceDO> result = query.getResultList(); 
				if(result.size()>0) {
					for (DOMSalesInvoiceDO dataDO : result) {
						String includeProductCode = productCode != 0 ? "and prod_code = ?2":"";
						Query<DOMSalesInvoiceDetailsDO> squery = session.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1 "+includeProductCode);
						squery.setParameter(1, dataDO.getId());
						if (productCode > 0) {
							squery.setParameter(2, productCode);
						}
						List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
						if(itemsResult.size()>0){
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					}
				} 
			}catch(Exception e) {
				e.printStackTrace();
				logger.error(e);
				throw new BusinessException(e.getMessage());
			}finally {
				session.clear();
				session.close();
			}
			return doList;
		}

					// COM Sales Invoices
					@SuppressWarnings("unchecked")
					public List<COMSalesInvoiceDO> getAgencyCSRInvoicesByDateRangeAndProductType(
							long agencyId,long fromDate,long toDate, int productCode) throws BusinessException {
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
									String includeProductCode = productCode != 0 ? "and prod_code = ?2":"";
									Query<COMSalesInvoiceDetailsDO> squery = session.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1 "+includeProductCode);
									squery.setParameter(1, dataDO.getId());
									squery.setParameter(2, productCode);
									List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
									if(itemsResult.size()>0){
										dataDO.getDetailsList().addAll(itemsResult);
										doList.add(dataDO);
									}
								}
							} 
						}catch(Exception e) {
							e.printStackTrace();
							logger.error(e);
							throw new BusinessException(e.getMessage());
						}finally {
							session.clear();
							session.close();
						}
						return doList;
					}
				
					// ARB Sales Invoices
					@SuppressWarnings("unchecked")
					public List<ARBSalesInvoiceDO> getAgencyARBSRInvoicesByDateRangeAndProductType(
							long agencyId,long fromDate,long toDate, long productCode) throws BusinessException {
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
									String includeProductCode = productCode != 0 ? "and prod_code = ?2":"";
									Query<ARBSalesInvoiceDetailsDO> squery = session.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1 "+includeProductCode);
									squery.setParameter(1, dataDO.getId());
									squery.setParameter(2, productCode);
									List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
									if(itemsResult.size()>0){
										dataDO.getDetailsList().addAll(itemsResult);
									}
									doList.add(dataDO);
								}
							} 
						}catch(Exception e) {
							e.printStackTrace();
							logger.error(e);
							throw new BusinessException(e.getMessage());
						}finally {
							session.clear();
							session.close();
						}
						return doList;
					}
				
		// ARB Purchase Invoices
				@SuppressWarnings("unchecked")
				public List<ARBPurchaseInvoiceDO> getAgencyARBPurInvoicesByDateRangeAndProductType(
						long agencyId,long fromDate,long toDate, long productCode) throws BusinessException {
					List<ARBPurchaseInvoiceDO> doList = new ArrayList<>();
					Session session = HibernateUtil.getSessionFactory().openSession();
					try {
						String includeProductCode = productCode != 0 ? "and arb_code = ?5":"";
						Query<ARBPurchaseInvoiceDO> query = session.createQuery("from ARBPurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
								+ " and inv_date between ?3 and ?4 "+includeProductCode);
						query.setParameter(1, agencyId);
						query.setParameter(2, 0);
						query.setParameter(3, fromDate);
						query.setParameter(4, toDate);
						if (productCode > 0) {
							query.setParameter(5, productCode);
						}
						List<ARBPurchaseInvoiceDO> result = query.getResultList(); 
						if(result.size()>0) {
							for (ARBPurchaseInvoiceDO dataDO : result) {
								doList.add(dataDO);
							}
						} 
					}catch(Exception e) {
						e.printStackTrace();
						logger.error(e);
						throw new BusinessException(e.getMessage());
					}finally {
						session.clear();
						session.close();
					}
					return doList;
				}
			
	@SuppressWarnings("unchecked")
	public List<DOMSalesInvoiceDO> getAgencyDSInvoicesByDateRangeAndStaff(
			long agencyId,long fromDate,long toDate, long staffId) throws BusinessException {
		List<DOMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<DOMSalesInvoiceDO> query = session.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and si_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<DOMSalesInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (DOMSalesInvoiceDO dataDO : result) {
					Query<DOMSalesInvoiceDetailsDO> squery = session.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1 and staff_id = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, staffId);
					List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
						doList.add(dataDO);
					}
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	
	// Ledger Report Based OPENINGBALANCE Based On PartyWise
	
	@SuppressWarnings("unchecked")
	public String getOpeningBalanceLedgerByPartyType(long agencyId,long  fromDate, long cvoId) throws BusinessException {
		String opbal = "0";
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<AgencyCVOBalanceDataDO> query = session.createQuery("from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_refid = ?3 "
					+ "  and inv_date < ?4 order by inv_date desc, created_date desc").setMaxResults(1);
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, cvoId);
			query.setParameter(4, fromDate);

			List<AgencyCVOBalanceDataDO> result = query.getResultList();
			if(result.size()>0) {
				for (AgencyCVOBalanceDataDO dataDO : result) {
					opbal =dataDO.getCbal_amount();
				}
			}else {
				Query<AgencyCVOBalanceDataDO> dquery = session.createQuery("from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_refid = ?3 "
						+ "  and inv_date = ?4 order by inv_date desc, created_date").setMaxResults(1);
				dquery.setParameter(1, agencyId);
				dquery.setParameter(2, 0);
				dquery.setParameter(3, cvoId);
				dquery.setParameter(4, fromDate);

				result = dquery.getResultList();
				if(result.size()>0) {
					for (AgencyCVOBalanceDataDO dataDO : result) {
						opbal =dataDO.getCbal_amount();
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return opbal;
	}


	//NEW CODE FOR BALANCE ADJUSTEMNET	
	@SuppressWarnings("unchecked")
	public List<AgencyCVOBalanceDataDO> getAgencyTransactionReportByDateRangeAndCVOId(long agencyId, long fdl,
			long tdl, long cvoId) {
		List<AgencyCVOBalanceDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<AgencyCVOBalanceDataDO> query = session.createQuery("from AgencyCVOBalanceDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?6)"
				+ " and cvo_refid = ?5 and inv_date between ?3 and ?4 and inv_ref_no != ?7 order by inv_date ,id ");
		
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			query.setParameter(6, 2);
			query.setParameter(7, "NA");
	
			List<AgencyCVOBalanceDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (AgencyCVOBalanceDataDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}


	@SuppressWarnings("unchecked")
	public List<NCDBCInvoiceDO> getAgencyNCSInvoicesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate, int productCode) throws BusinessException {
		List<NCDBCInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<NCDBCInvoiceDO> query1 = session.createQuery("from NCDBCInvoiceDO where created_by = ?1 and (deleted = ?2 or deleted = ?5) "
					+ " and inv_date between ?3 and ?4 ");
			query1.setParameter(1, agencyId);
			query1.setParameter(2, 0);
			query1.setParameter(3, fromDate);
			query1.setParameter(4, toDate);
			query1.setParameter(5, 2);
			List<NCDBCInvoiceDO> result = query1.getResultList(); 
			if(result.size()>0) {
				for (NCDBCInvoiceDO dataDO : result) {
					String includeProductCode = productCode != 0 ? "and prod_code = ?2":"";
					Query<NCDBCInvoiceDetailsDO> squery11 = session.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 "+includeProductCode);
					squery11.setParameter(1, dataDO.getId());
					if (productCode > 0) {
						squery11.setParameter(2, new Long(productCode));
					}
					List<NCDBCInvoiceDetailsDO> itemsResult = squery11.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
						doList.add(dataDO);
					}
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}
	
	@SuppressWarnings("unchecked")
	public List<NCDBCInvoiceDO> getAgencyNCDBCSInvoicesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate, int productCode) throws BusinessException {
		List<NCDBCInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<NCDBCInvoiceDO> query1 = session.createQuery("from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 ");
			query1.setParameter(1, agencyId);
			query1.setParameter(2, 0);
			query1.setParameter(3, fromDate);
			query1.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = query1.getResultList(); 
			if(result.size()>0) {
				for (NCDBCInvoiceDO dataDO : result) {
					String includeProductCode = productCode != 0 ? "and prod_code = ?2":"";
					Query<NCDBCInvoiceDetailsDO> squery11 = session.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 "+includeProductCode);
					squery11.setParameter(1, dataDO.getId());
					if (productCode > 0) {
						squery11.setParameter(2, new Long(productCode));
					}
					List<NCDBCInvoiceDetailsDO> itemsResult = squery11.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
						doList.add(dataDO);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}
	@SuppressWarnings("unchecked")
    public List<NCDBCInvoiceDO> getAgencyNCDBCSInvoicesByDateRangeAndStaff(
                    long agencyId,long fromDate,long toDate, long staffId) throws BusinessException {
            List<NCDBCInvoiceDO> doList = new ArrayList<>();
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
            	Query<NCDBCInvoiceDO> query = session.createQuery("from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 "
            			+ " and inv_date between ?3 and ?4 and staff_id = ?5");
            	query.setParameter(1, agencyId);
            	query.setParameter(2, 0);
            	query.setParameter(3, fromDate);
            	query.setParameter(4, toDate);
            	query.setParameter(5, staffId);
            	List<NCDBCInvoiceDO> result = query.getResultList();
            	if(result.size()>0) {
            		for (NCDBCInvoiceDO dataDO : result) {
            			Query<NCDBCInvoiceDetailsDO> squery = session.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1");
            			squery.setParameter(1, dataDO.getId());
            			List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
            			if(itemsResult.size()>0){
            				for (NCDBCInvoiceDetailsDO dDO : itemsResult) {
            					if(dDO.getProd_code()>100){
            						dataDO.getDetailsList().add(dDO);
            						doList.add(dataDO);
            					}
            				}
            			}
            		}
            	}
            }catch(Exception e) {
    			e.printStackTrace();
    			logger.error(e);
            	throw new BusinessException(e.getMessage());
            }finally {
            	session.clear();
            	session.close();
            }
            return doList;
    }
	@SuppressWarnings("unchecked")
	public List<NCDBCInvoiceDO> getAgencyNCSInvoicesByDateRangeAndStaff(
			long agencyId,long fromDate,long toDate, long staffId) throws BusinessException {
		List<NCDBCInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<NCDBCInvoiceDO> query1 = session.createQuery("from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 ");
			query1.setParameter(1, agencyId);
			query1.setParameter(2, 0);
			query1.setParameter(3, fromDate);
			query1.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = query1.getResultList(); 
			if(result.size()>0) {
				for (NCDBCInvoiceDO dataDO : result) {
					Query<NCDBCInvoiceDetailsDO> squery11 = session.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 and staff_id = ?2");
					squery11.setParameter(1, dataDO.getId());
					squery11.setParameter(2, new Long(staffId));
					List<NCDBCInvoiceDetailsDO> itemsResult = squery11.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
						doList.add(dataDO);
					}
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<NCDBCInvoiceDO> getAgencyRegulatorSalesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate, int productCode) throws BusinessException {
		List<NCDBCInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			//First fetch from NC Invoices
			Query<NCDBCInvoiceDO> query1 = session.createQuery("from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 ");
			query1.setParameter(1, agencyId);
			query1.setParameter(2, 0);
			query1.setParameter(3, fromDate);
			query1.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = query1.getResultList(); 
			if(result.size()>0) {
				for (NCDBCInvoiceDO dataDO : result) {
					String includeProductCode = productCode != 0 ? "and prod_code = ?2":"";
					Query<NCDBCInvoiceDetailsDO> squery11 = session.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 "+includeProductCode);
					squery11.setParameter(1, dataDO.getId());
					if (productCode > 0) {
						squery11.setParameter(2, new Long(productCode));
					}
					List<NCDBCInvoiceDetailsDO> itemsResult = squery11.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
						doList.add(dataDO);
					}
				}
			} 
			
			//Secondly fetch from RC
			
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

	// COM Sales Invoices
	@SuppressWarnings("unchecked")
	public List<COMSalesInvoiceDO> getAgencyCSInvoicesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate, int productCode) throws BusinessException {
		List<COMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<COMSalesInvoiceDO> query = session.createQuery("from COMSalesInvoiceDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) "
					+ " and si_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(5, 2);
			List<COMSalesInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (COMSalesInvoiceDO dataDO : result) {
					String includeProductCode = productCode != 0 ? "and prod_code = ?2":"";
					Query<COMSalesInvoiceDetailsDO> squery = session.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1 "+includeProductCode);
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, productCode);
					List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
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

	// COM Sales Invoices
	@SuppressWarnings("unchecked")
	public List<COMSalesInvoiceDO> getAgencyCSInvoicesByDateRangeAndStaff(
			long agencyId,long fromDate,long toDate, long staffId) throws BusinessException {
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
					Query<COMSalesInvoiceDetailsDO> squery = session.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1 and staff_id = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, staffId);
					List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
						doList.add(dataDO);
					}
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// ARB Sales Invoices
	@SuppressWarnings("unchecked")
	public List<ARBSalesInvoiceDO> getAgencyARBSInvoicesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate, long productCode) throws BusinessException {
		List<ARBSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<ARBSalesInvoiceDO> query = session.createQuery("from ARBSalesInvoiceDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) "
					+ " and si_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(5, 2);
			List<ARBSalesInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (ARBSalesInvoiceDO dataDO : result) {
					String includeProductCode = productCode != 0 ? "and prod_code = ?2":"";
					Query<ARBSalesInvoiceDetailsDO> squery = session.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1 "+includeProductCode);
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, productCode);
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
	public List<ARBSalesInvoiceDO> getAgencyARBSInvoicesByDateRangeAndStaff(
			long agencyId,long fromDate,long toDate, long staffId) throws BusinessException {
		List<ARBSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<ARBSalesInvoiceDO> query = session.createQuery("from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and si_date between ?3 and ?4 and staff_id = ?5");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(5, staffId);
			List<ARBSalesInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (ARBSalesInvoiceDO dataDO : result) {
					Query<ARBSalesInvoiceDetailsDO> squery = session.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if(itemsResult.size()>0){
						dataDO.getDetailsList().addAll(itemsResult);
						doList.add(dataDO);
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// Bank Transactions
	@SuppressWarnings("unchecked")
	public List<BankTranxsDataDO> getAgencyBankTransactionsByDateRangeAndBankId(
			long agencyId,long fromDate,long toDate, long bankId) throws BusinessException {
		List<BankTranxsDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String includeBankId = bankId != 0 ? "and bank_id = ?5 ":"";
			
			Query<BankTranxsDataDO> query = session.createQuery("from BankTranxsDataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?6) "
					+ " and bt_date between ?3 and ?4 "+includeBankId+" order by bt_date asc,id asc");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(6, 2);
			if (includeBankId.length() > 0) {
				query.setParameter(5, bankId);
			}
			List<BankTranxsDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (BankTranxsDataDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	//DOM Cyclinders Stock Report
	@SuppressWarnings("unchecked")
	public StockReportVO getAgencyDOMCStockByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate, int productCode) throws BusinessException {

		StockReportVO srvo = new StockReportVO();
		srvo.setProductCode(""+productCode);
		List<ProductCategoryDO> dataDOList = CacheManager.getCylinderTypesDataList();
		for(ProductCategoryDO dataDO : dataDOList ) {
			if(dataDO.getId()==productCode) {
				srvo.setProductSelected(dataDO.getCat_name()+" - "+dataDO.getCat_desc());
				break;
			}
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			//Fetch Equipment Data
			Query<EquipmentDataDO> edquery = session.createQuery("from EquipmentDataDO where created_by = ?1 and prod_code = ?2 and deleted = ?3 ");
			edquery.setParameter(1, agencyId);
			edquery.setParameter(2, productCode);
			edquery.setParameter(3, 0);
			List<EquipmentDataDO> edresult = edquery.getResultList(); 
			if(edresult.size()>0) {
				for (EquipmentDataDO doObj : edresult) {
					srvo.setCurrentStock(doObj.getCs_fulls());
					srvo.setEmptyStock(doObj.getCs_emptys());
				}
			} 
			
			//Fetch DOM Purchases
			int rn = 0;
			String includeProductCode = productCode != 0 ? "and prod_code = ?5":"";
			Query<PurchaseInvoiceDO> query = session.createQuery("from PurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 "+includeProductCode);
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			if (productCode > 0) {
				query.setParameter(5, productCode);
			}
			List<PurchaseInvoiceDO> result = query.getResultList(); 
			
			if(result.size()>0) {
				int purchasesCount=0;
				for (PurchaseInvoiceDO dataDO : result) {
					if(rn==0)
						srvo.setOpeningStockF(dataDO.getCs_fulls()-dataDO.getQuantity());
					purchasesCount = purchasesCount + dataDO.getQuantity();
					++rn;
				}
				srvo.setTotalPurchases(purchasesCount);
			} 
			
			//Fetch DOM Purchases Returns
			Query<PurchaseReturnDO> querypr = session.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4");
			querypr.setParameter(1, agencyId);
			querypr.setParameter(2, 0);
			querypr.setParameter(3, fromDate);
			querypr.setParameter(4, toDate);
			List<PurchaseReturnDO> resultpr = querypr.getResultList(); 
			
			if(resultpr.size()>0) {
				int purchasesRCount=0;
				for (PurchaseReturnDO dataDO : resultpr) {
					Query<PurchaseReturnDetailsDO> squerypr = session.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 and prod_code = ?2");
					squerypr.setParameter(1, dataDO.getId());
					squerypr.setParameter(2, productCode);
					List<PurchaseReturnDetailsDO> itemsPRResult = squerypr.getResultList();
					if(itemsPRResult.size()>0){
						for(PurchaseReturnDetailsDO detailsDO : itemsPRResult)
							purchasesRCount = purchasesRCount + detailsDO.getRtn_qty();
					}
				}
				srvo.setTotalPurchaseReturns(purchasesRCount);
			} 
			
			//Fetch DOM Sales Count
			Query<DOMSalesInvoiceDO> queryDOMSI = session.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and si_date between ?3 and ?4 ");
			queryDOMSI.setParameter(1, agencyId);
			queryDOMSI.setParameter(2, 0);
			queryDOMSI.setParameter(3, fromDate);
			queryDOMSI.setParameter(4, toDate);
			List<DOMSalesInvoiceDO> resultDOMSI = queryDOMSI.getResultList(); 
			if(resultDOMSI.size()>0) {
				for (DOMSalesInvoiceDO dataDO : resultDOMSI) {
					String includeProductCodeDOMSI = productCode != 0 ? "and prod_code = ?2":"";
					Query<DOMSalesInvoiceDetailsDO> squery = session.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1 "+includeProductCodeDOMSI);
					squery.setParameter(1, dataDO.getId());
					if (productCode > 0) {
						squery.setParameter(2, productCode);
					}
					List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if(itemsResult.size()>0){
						int domsiCount=0;
						for (DOMSalesInvoiceDetailsDO dataDetailsDO : itemsResult) {
							domsiCount = domsiCount + dataDetailsDO.getQuantity();
							srvo.setClosingStockF(dataDetailsDO.getCs_fulls());
							srvo.setClosingStockE(dataDetailsDO.getCs_emptys());
						}
						srvo.setTotalSales(domsiCount);
					}
				}
			} 
			
			

		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return srvo;
	}

	//COM Cyclinders Stock Report
	@SuppressWarnings("unchecked")
	public StockReportVO getAgencyCOMCStockByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate, int productCode) throws BusinessException {

		StockReportVO srvo = new StockReportVO();
		srvo.setProductCode(""+productCode);
		List<ProductCategoryDO> dataDOList = CacheManager.getCylinderTypesDataList();
		for(ProductCategoryDO dataDO : dataDOList ) {
			if(dataDO.getId()==productCode) {
				srvo.setProductSelected(dataDO.getCat_name()+" - "+dataDO.getCat_desc());
				break;
			}
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			//Fetch Equipment Data
			Query<EquipmentDataDO> edquery = session.createQuery("from EquipmentDataDO where created_by = ?1 and prod_code = ?2 and deleted = ?3 ");
			edquery.setParameter(1, agencyId);
			edquery.setParameter(2, productCode);
			edquery.setParameter(3, 0);
			List<EquipmentDataDO> edresult = edquery.getResultList(); 
			if(edresult.size()>0) {
				for (EquipmentDataDO doObj : edresult) {
					srvo.setCurrentStock(doObj.getCs_fulls());
					srvo.setEmptyStock(doObj.getCs_emptys());
				}
			} 

			//Fetch COM Purchases
			int rn = 0;
			String includeProductCode = productCode != 0 ? "and prod_code = ?5":"";
			Query<PurchaseInvoiceDO> query = session.createQuery("from PurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 "+includeProductCode);
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(5, productCode);
			List<PurchaseInvoiceDO> result = query.getResultList(); 
			
			if(result.size()>0) {
				int purchasesCount=0;
				for (PurchaseInvoiceDO dataDO : result) {
					if(rn==0)
						srvo.setOpeningStockF(dataDO.getCs_fulls()-dataDO.getQuantity());
					purchasesCount = purchasesCount + dataDO.getQuantity();
					++rn;
				}
				srvo.setTotalPurchases(purchasesCount);
			} 
			
			//Fetch COM Purchases Returns
			Query<PurchaseReturnDO> querypr = session.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4");
			querypr.setParameter(1, agencyId);
			querypr.setParameter(2, 0);
			querypr.setParameter(3, fromDate);
			querypr.setParameter(4, toDate);
			List<PurchaseReturnDO> resultpr = querypr.getResultList(); 
			
			if(resultpr.size()>0) {
				int purchasesRCount=0;
				for (PurchaseReturnDO dataDO : resultpr) {
					Query<PurchaseReturnDetailsDO> squerypr = session.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 and prod_code = ?2");
					squerypr.setParameter(1, dataDO.getId());
					squerypr.setParameter(2, productCode);
					List<PurchaseReturnDetailsDO> itemsPRResult = squerypr.getResultList();
					if(!itemsPRResult.isEmpty()){
						for(PurchaseReturnDetailsDO detailsDO : itemsPRResult)
							purchasesRCount = purchasesRCount + detailsDO.getRtn_qty();
					}
				}
				srvo.setTotalPurchaseReturns(purchasesRCount);
			} 
			
			//Fetch COM Sales Count
			Query<COMSalesInvoiceDO> queryCOMSI = session.createQuery("from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and si_date between ?3 and ?4 ");
			queryCOMSI.setParameter(1, agencyId);
			queryCOMSI.setParameter(2, 0);
			queryCOMSI.setParameter(3, fromDate);
			queryCOMSI.setParameter(4, toDate);
			List<COMSalesInvoiceDO> resultCOMSI = queryCOMSI.getResultList(); 
			if(resultCOMSI.size()>0) {
				for (COMSalesInvoiceDO dataDO : resultCOMSI) {
					String includeProductCodeDOMSI = productCode != 0 ? "and prod_code = ?2":"";
					Query<COMSalesInvoiceDetailsDO> squery = session.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1 "+includeProductCodeDOMSI);
					squery.setParameter(1, dataDO.getId());
					if (productCode > 0) {
						squery.setParameter(2, productCode);
					}
					List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if(itemsResult.size()>0){
						int domsiCount=0;
						for (COMSalesInvoiceDetailsDO dataDetailsDO : itemsResult) {
							domsiCount = domsiCount + dataDetailsDO.getQuantity();
							srvo.setClosingStockF(dataDetailsDO.getCs_fulls());
							srvo.setClosingStockE(dataDetailsDO.getCs_emptys());
						}
						srvo.setTotalSales(domsiCount);
					}
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return srvo;
	}

	//ARB Stock Report
	@SuppressWarnings("unchecked")
	public StockReportVO getAgencyARBStockByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate, long productCode) throws BusinessException {

		StockReportVO srvo = new StockReportVO();
		srvo.setProductCode(""+productCode);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			//Fetch ARB Data
			Query<ARBDataDO> edquery = session.createQuery("from ARBDataDO where created_by = ?1 and id = ?2 and deleted = ?3 ");
			edquery.setParameter(1, agencyId);
			edquery.setParameter(2, productCode);
			edquery.setParameter(3, 0);
			List<ARBDataDO> edresult = edquery.getResultList(); 
			if(edresult.size()>0) {
				for (ARBDataDO doObj : edresult) {
					srvo.setCurrentStock(doObj.getCurrent_stock());
					List<ProductCategoryDO> dataDOList = CacheManager.getARBTypesDataList();
					for(ProductCategoryDO dataDO : dataDOList ) {
						if(dataDO.getId()==doObj.getProd_code()) {
							srvo.setProductSelected(dataDO.getCat_desc() + " - " + doObj.getProd_brand() + " - " + doObj.getProd_name());
							break;
						}
					}
				}
			} 
			
			//Fetch ARB Purchases
			int rn = 0;
			String includeProductCode = productCode != 0 ? "and arb_code = ?5":"";
			Query<ARBPurchaseInvoiceDO> query = session.createQuery("from ARBPurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 "+includeProductCode);
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			if (productCode > 0) {
				query.setParameter(5, productCode);
			}
			List<ARBPurchaseInvoiceDO> result = query.getResultList(); 
			
			if(result.size()>0) {
				int purchasesCount=0;
				for (ARBPurchaseInvoiceDO dataDO : result) {
					if(rn==0){
						srvo.setOpeningStockF(dataDO.getC_stock()-dataDO.getQuantity());
					}
					++rn;
					purchasesCount = purchasesCount + dataDO.getQuantity();
				}
				srvo.setTotalPurchases(purchasesCount);
			} 
			
			//Fetch ARB Sales Count
			Query<ARBSalesInvoiceDO> queryDOMSI = session.createQuery("from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and si_date between ?3 and ?4 ");
			queryDOMSI.setParameter(1, agencyId);
			queryDOMSI.setParameter(2, 0);
			queryDOMSI.setParameter(3, fromDate);
			queryDOMSI.setParameter(4, toDate);
			List<ARBSalesInvoiceDO> resultDOMSI = queryDOMSI.getResultList(); 
			if(resultDOMSI.size()>0) {
				for (ARBSalesInvoiceDO dataDO : resultDOMSI) {
					String includeProductCodeDOMSI = productCode != 0 ? "and prod_code = ?2":"";
					Query<ARBSalesInvoiceDetailsDO> squery = session.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1 "+includeProductCodeDOMSI);
					squery.setParameter(1, dataDO.getId());
					if (productCode > 0) {
						squery.setParameter(2, productCode);
					}
					List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if(itemsResult.size()>0){
						int domsiCount=0;
						for (ARBSalesInvoiceDetailsDO dataDetailsDO : itemsResult) {
							domsiCount = domsiCount + dataDetailsDO.getQuantity();
							srvo.setClosingStockF(dataDetailsDO.getC_stock());
						}
						srvo.setTotalSales(domsiCount+srvo.getTotalSales());
					}
				}
			} 
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return srvo;
	}

	//DAY END
	//DOM
	@SuppressWarnings("unchecked")
	public List<PurchaseInvoiceDO> getAgencyDAYENDDOMPurchaseInvoicesByDateRangeAndProductType(
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
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// DOM Sales Invoices
	@SuppressWarnings("unchecked")
	public List<DOMSalesInvoiceDO> getAgencyDAYENDDSInvoicesByDateRangeAndProductType(
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
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseInvoiceDO> getAgencyDAYENDCOMPurchaseInvoicesByDateRangeAndProductType(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<PurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<PurchaseInvoiceDO> query = session.createQuery("from PurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 and prod_code > 3 and prod_code < 10");
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
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<COMSalesInvoiceDO> getAgencyDAYENDCSInvoicesByDateRangeAndProductType(
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
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// ARB Purchase Invoices
	@SuppressWarnings("unchecked")
	public List<ARBPurchaseInvoiceDO> getAgencyDAYENDARBPurchaseInvoicesByDateRange(
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
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<ARBSalesInvoiceDO> getAgencyDAYENDARBSInvoicesByDateRange(
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
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// Bank Transactions
	@SuppressWarnings("unchecked")
	public List<BankTranxsDataDO> getAgencyDAYENDBankTransactionsByDateRangeAndBankId(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<BankTranxsDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<BankTranxsDataDO> query = session.createQuery("from BankTranxsDataDO where created_by = ?1 and deleted = ?2 "
					+ " and bt_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<BankTranxsDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (BankTranxsDataDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseInvoiceDO> getAgencyPurchaseInvoicesByDateRangeAndVendorId(long agencyId, long fdl, long tdl, long cvoId) {
		List<PurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<PurchaseInvoiceDO> query = session.createQuery("from PurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			List<PurchaseInvoiceDO> result = query.getResultList(); 
			
			if(result.size()>0) {
				for (PurchaseInvoiceDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<ARBPurchaseInvoiceDO> getAgencyARBPurchaseInvoicesByDateRangeAndVendorId(long agencyId, long fdl,
			long tdl, long cvoId) {
		List<ARBPurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<ARBPurchaseInvoiceDO> query = session.createQuery("from ARBPurchaseInvoiceDO where created_by = ?1 and (deleted = ?2 or deleted = ?6) "
					+ " and vendor_id = ?5 and inv_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			query.setParameter(6, 2);
			List<ARBPurchaseInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (ARBPurchaseInvoiceDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<OtherPurchaseInvoiceDO> getAgencyOtherPurchaseInvoicesByDateRangeAndVendorId(long agencyId, long fdl,
			long tdl, long cvoId) {
		List<OtherPurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<OtherPurchaseInvoiceDO> query = session.createQuery("from OtherPurchaseInvoiceDO where created_by = ?1 and (deleted = ?2 or deleted = ?6)"
					+ " and vendor_id = ?5 and inv_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			query.setParameter(6, 2);
			List<OtherPurchaseInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (OtherPurchaseInvoiceDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	public List<PaymentsDataDO> getAgencyPaymentsByDateRangeAndVendorId(long agencyId, long fdl, long tdl, long cvoId) {
		List<PaymentsDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<PaymentsDataDO> query = session.createQuery("from PaymentsDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?6) "
					+ " and paid_to = ?5 and pymt_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			query.setParameter(6, 2);
			List<PaymentsDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (PaymentsDataDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<DOMSalesInvoiceDO> getAgencyDOMSInvoicesByDateRangeAndCustomerId(long agencyId, long fdl, long tdl,
			long cvoId) {
		List<DOMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<DOMSalesInvoiceDO> query = session.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and (deleted = ?2 OR deleted = ?6) "
					+ " and customer_id = ?5 and si_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			query.setParameter(6, 2);
			List<DOMSalesInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for(DOMSalesInvoiceDO dataDO : result) {
					Query<DOMSalesInvoiceDetailsDO> squery = session.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					List<DOMSalesInvoiceDetailsDO> detailsList=new ArrayList<>();
					if(!itemsResult.isEmpty()){
						
						if(itemsResult.size()==1) {
							float discount=0;
							DOMSalesInvoiceDetailsDO details=new DOMSalesInvoiceDetailsDO();

						for(DOMSalesInvoiceDetailsDO detailsdataDO:itemsResult) {
							int quantityAftrPsvP=(detailsdataDO.getQuantity()-detailsdataDO.getPre_cyls()-detailsdataDO.getPsv_cyls());
							discount=discount+(quantityAftrPsvP)* (Float.parseFloat(detailsdataDO.getDisc_unit_rate()));
							details.setDisc_unit_rate(String.valueOf(discount));
							detailsList.add(details);
							dataDO.getDetailsList().addAll(detailsList);
							doList.add(dataDO);
						}
						}
						else if(itemsResult.size()>1)
						{
							DOMSalesInvoiceDetailsDO details=new DOMSalesInvoiceDetailsDO();
							float dis=0;
							for (DOMSalesInvoiceDetailsDO dataDetailsDO : itemsResult) {
								if(dataDetailsDO.getDsi_id()==dataDO.getId()) {
									//dis=dis+Float.parseFloat(dataDetailsDO.getDisc_unit_rate());
									int quantityAftrPsvP=(dataDetailsDO.getQuantity()-dataDetailsDO.getPre_cyls()-dataDetailsDO.getPsv_cyls());
									dis=dis+(quantityAftrPsvP)* (Float.parseFloat(dataDetailsDO.getDisc_unit_rate()));
									details.setDisc_unit_rate(String.valueOf(dis));
									detailsList.add(details);
									dataDO.getDetailsList().addAll(detailsList);
							}
							}
							doList.add(dataDO);
						}
					}
				}
			}
		}
					
		catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}


	@SuppressWarnings("unchecked")
	public List<COMSalesInvoiceDO> getAgencyCOMSInvoicesByDateRangeAndCustomerId(long agencyId, long fdl, long tdl,
			long cvoId) {
		List<COMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<COMSalesInvoiceDO> query = session.createQuery("from COMSalesInvoiceDO where created_by = ?1 and (deleted = ?2 OR deleted = ?6) "
					+ " and customer_id = ?5 and si_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			query.setParameter(6, 2);
			List<COMSalesInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (COMSalesInvoiceDO dataDO : result) {
					Query<COMSalesInvoiceDetailsDO> squery = session.createQuery("from  COMSalesInvoiceDetailsDO where csi_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<COMSalesInvoiceDetailsDO> itemsResult =squery.getResultList();
					List<COMSalesInvoiceDetailsDO> detailsList=new ArrayList<>();

					if(!itemsResult.isEmpty()){
						if(itemsResult.size()==1){
							float discount=0;
							COMSalesInvoiceDetailsDO details=new COMSalesInvoiceDetailsDO();
							for(COMSalesInvoiceDetailsDO detailsdataDO:itemsResult) {
								int quantityAftrPsvP=(detailsdataDO.getQuantity()-detailsdataDO.getPre_cyls());
								discount=discount+(quantityAftrPsvP)* (Float.parseFloat(detailsdataDO.getDisc_unit_rate()));
								details.setDisc_unit_rate(String.valueOf(discount));
								detailsList.add(details);
								dataDO.getDetailsList().addAll(detailsList);
								doList.add(dataDO);
							}
						}else if(itemsResult.size()>1){
							COMSalesInvoiceDetailsDO details=new COMSalesInvoiceDetailsDO();
							float dis=0;
							for (COMSalesInvoiceDetailsDO dataDetailsDO : itemsResult) {
								if(dataDetailsDO.getCsi_id()==dataDO.getId()) {
									int quantityAftrPsvP=(dataDetailsDO.getQuantity()-dataDetailsDO.getPre_cyls());
									dis=dis+(quantityAftrPsvP)* (Float.parseFloat(dataDetailsDO.getDisc_unit_rate()));
									details.setDisc_unit_rate(String.valueOf(dis));
									detailsList.add(details);
									dataDO.getDetailsList().addAll(detailsList);
								}
							}
							doList.add(dataDO);
						}
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<ARBSalesInvoiceDO> getAgencyARBSInvoicesByDateRangeAndCustomerId(long agencyId, long fdl, long tdl,
			long cvoId) {
		List<ARBSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<ARBSalesInvoiceDO> query = session.createQuery("from ARBSalesInvoiceDO where created_by = ?1 and (deleted = ?2 OR deleted = ?6)"
					+ " and customer_id = ?5 and si_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			query.setParameter(6, 2);
			List<ARBSalesInvoiceDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (ARBSalesInvoiceDO dataDO : result) {
					Query<ARBSalesInvoiceDetailsDO> squery = session.createQuery("from ARBSalesInvoiceDetailsDO where ARBSI_ID = ?1");
					squery.setParameter(1, dataDO.getId());
					List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					List<ARBSalesInvoiceDetailsDO> detailsList=new ArrayList<>();

					if(!itemsResult.isEmpty()){
						if(itemsResult.size()==1){
							float discount=0;
							ARBSalesInvoiceDetailsDO details=new ARBSalesInvoiceDetailsDO();

							for(ARBSalesInvoiceDetailsDO detailsdataDO:itemsResult) {
								int quantityAftrPsvP=(detailsdataDO.getQuantity());
								discount=discount+(quantityAftrPsvP)* (Float.parseFloat(detailsdataDO.getDisc_unit_rate()));
								details.setDisc_unit_rate(String.valueOf(discount));
								detailsList.add(details);
								dataDO.getDetailsList().addAll(detailsList);
								doList.add(dataDO);
							}
						}else if(itemsResult.size()>1){
							ARBSalesInvoiceDetailsDO details=new ARBSalesInvoiceDetailsDO();
							float dis=0;
							for (ARBSalesInvoiceDetailsDO dataDetailsDO : itemsResult) {
								if(dataDetailsDO.getArbsi_id()==dataDO.getId()) {
									//dis=dis+Float.parseFloat(dataDetailsDO.getDisc_unit_rate());
									int quantityAftrPsvP=(dataDetailsDO.getQuantity());
									dis=dis+(quantityAftrPsvP)* (Float.parseFloat(dataDetailsDO.getDisc_unit_rate()));
									details.setDisc_unit_rate(String.valueOf(dis));
									detailsList.add(details);
									dataDO.getDetailsList().addAll(detailsList);
								}
							}
							doList.add(dataDO);
						}
					}
					
				}
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	public List<ReceiptsDataDO> getAgencyReceiptsByDateRangeAndCustomerId(long agencyId, long fdl, long tdl,
			long cvoId) {
		List<ReceiptsDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<ReceiptsDataDO> query = session.createQuery("from ReceiptsDataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?6) "
					+ " and rcvd_from = ?5 and rcp_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			query.setParameter(6, 2);
			List<ReceiptsDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (ReceiptsDataDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	public List<PurchaseReturnDO> getAgencyPurchaseReturnsByDateRangeAndCustomerId(long agencyId, long fdl, long tdl,
			long cvoId) {
		List<PurchaseReturnDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<PurchaseReturnDO> query = session.createQuery("from PurchaseReturnDO where created_by = ?1 and (deleted = ?2 OR deleted = ?6) "
					+ " and cvo_id = ?5 and inv_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			query.setParameter(6, 2);
			List<PurchaseReturnDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (PurchaseReturnDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	public List<SalesReturnDO> getAgencySalesReturnsByDateRangeAndCustomerId(long agencyId, long fdl, long tdl,
			long cvoId) {
		List<SalesReturnDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<SalesReturnDO> query = session.createQuery("from SalesReturnDO where created_by = ?1 and (deleted = ?2 OR deleted = ?6) "
					+ " and cvo_id = ?5 and inv_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			query.setParameter(6, 2);
			List<SalesReturnDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (SalesReturnDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	public List<CreditNotesDO> getAgencyCreditNotesByDateRangeAndCustomerId(long agencyId, long fdl, long tdl,
			long cvoId) {
		List<CreditNotesDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<CreditNotesDO> query = session.createQuery("from CreditNotesDO where created_by = ?1 and (deleted = ?2 or deleted = ?6)"
					+ " and cvo_id = ?5 and note_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			query.setParameter(6, 2);

			List<CreditNotesDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (CreditNotesDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	public List<DebitNotesDO> getAgencyDebitNotesByDateRangeAndCustomerId(long agencyId, long fdl, long tdl,
			long cvoId) {
		List<DebitNotesDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<DebitNotesDO> query = session.createQuery("from DebitNotesDO where created_by = ?1 and (deleted = ?2 or deleted = ?6) "
					+ " and cvo_id = ?5 and note_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			query.setParameter(6, 2);
			List<DebitNotesDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (DebitNotesDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}
	// cash Transactions
	@SuppressWarnings("unchecked")
	public List<AgencyCashDataDO> getAgencyCashTransactionsByDateRangeAndCashId(
			long agencyId,long fromDate,long toDate, long cashId) throws BusinessException {
		List<AgencyCashDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {

			Query<AgencyCashDataDO> query = session.createQuery("from AgencyCashDataDO where created_by = ?1 and deleted = ?2 "
					+ " and t_date between ?3 and ?4 order by t_date, created_date");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			
			List<AgencyCashDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (AgencyCashDataDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}
	
	@SuppressWarnings("unchecked")
	public List<NCDBCRCTVReportVO> getAgencyNCDBCByTypeProductDeposit(long agencyId, long fromDate, long toDate, int pc,
			int ct, int dep) {
		List<NCDBCRCTVReportVO> voList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String addConnectionTypeCriteria = "";
			if(ct>0) {
				addConnectionTypeCriteria = " and conn_type = ?5";
			}
			Query<NCDBCInvoiceDO> query1 = session.createQuery("from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and inv_date between ?3 and ?4"+addConnectionTypeCriteria);
			query1.setParameter(1, agencyId);
			query1.setParameter(2, 0);
			query1.setParameter(3, fromDate);
			query1.setParameter(4, toDate);
			if(ct>0) {
				query1.setParameter(5, ct);
			}
			List<NCDBCInvoiceDO> result = query1.getResultList(); 
			if(result.size()>0) {
				for (NCDBCInvoiceDO dataDO : result) {
					long dpc = 0;
					int cyls = 0;
					int regs = 0;
					Query<NCDBCInvoiceDetailsDO> squery11 = session.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1");
					squery11.setParameter(1, dataDO.getId());
					List<NCDBCInvoiceDetailsDO> itemsResult = squery11.getResultList();
					if(!itemsResult.isEmpty()){
						for(NCDBCInvoiceDetailsDO detailsDO : itemsResult) {
							long ipc = detailsDO.getProd_code();
							if(ipc<10) {
								dpc=ipc;
								cyls = detailsDO.getQuantity();
							} else if ( ipc>9 && ipc<13) {
								regs = detailsDO.getQuantity();
							}
						}
					}
					if(pc>0) {
					
						if(pc==dpc) {
							NCDBCRCTVReportVO dataVO = new NCDBCRCTVReportVO();
							dataVO.setTranxDate(dataDO.getInv_date());
							dataVO.setConnectionType(dataDO.getConn_type());
							dataVO.setNocs(dataDO.getNo_of_conns());
							dataVO.setNcs(cyls);
							dataVO.setNrs(regs);
							dataVO.setTotalSecurityDeposit(dataDO.getDep_amount());
							dataVO.setProdCode(dpc);
							voList.add(dataVO);
						} else {
							continue;
						}
						
					} else {
						NCDBCRCTVReportVO dataVO = new NCDBCRCTVReportVO();
						dataVO.setTranxDate(dataDO.getInv_date());
						dataVO.setConnectionType(dataDO.getConn_type());
						dataVO.setNocs(dataDO.getNo_of_conns());
						dataVO.setNcs(cyls);
						dataVO.setNrs(regs);
						dataVO.setTotalSecurityDeposit(dataDO.getDep_amount());
						dataVO.setProdCode(dpc);
						voList.add(dataVO);
					}
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return voList;
	}

	public List<NCDBCRCTVReportVO> getAgencyRCByTypeProductDeposit(long agencyId, long fromDate, long toDate, int pc,
			int ct, int dep) {
		List<NCDBCRCTVReportVO> voList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<RCDataDO> query = session.createQuery("from RCDataDO where created_by = ?1 and deleted = ?2 "
					+ " and rc_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<RCDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (RCDataDO dataDO : result) {
					int ipc = dataDO.getProd_code();
					if(pc>0) {
						if(pc==ipc) {
							NCDBCRCTVReportVO dataVO = new NCDBCRCTVReportVO();
							dataVO.setTranxDate(dataDO.getRc_date());
							dataVO.setConnectionType(5);
							dataVO.setNocs(1);
							dataVO.setNcs(dataDO.getNo_of_cyl());
							dataVO.setNrs(dataDO.getNo_of_reg());
							BigDecimal cd = new BigDecimal(dataDO.getCyl_deposit());
							BigDecimal rd = new BigDecimal(dataDO.getReg_deposit());
							dataVO.setTotalSecurityDeposit((cd.add(rd)).toString());
							dataVO.setProdCode(pc);
							voList.add(dataVO);
						} else {
							continue;
						}
					} else {
						NCDBCRCTVReportVO dataVO = new NCDBCRCTVReportVO();
						dataVO.setTranxDate(dataDO.getRc_date());
						dataVO.setConnectionType(5);
						dataVO.setNocs(1);
						dataVO.setNcs(dataDO.getNo_of_cyl());
						dataVO.setNrs(dataDO.getNo_of_reg());
						BigDecimal cd = new BigDecimal(dataDO.getCyl_deposit());
						BigDecimal rd = new BigDecimal(dataDO.getReg_deposit());
						dataVO.setTotalSecurityDeposit((cd.add(rd)).toString());
						dataVO.setProdCode(dataDO.getProd_code());
						voList.add(dataVO);
					}
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return voList;
	}

	public List<NCDBCRCTVReportVO> getAgencyTVByTypeProductDeposit(long agencyId, long fromDate, long toDate, int pc,
			int ct, int dep) {
		List<NCDBCRCTVReportVO> voList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<TVDataDO> query = session.createQuery("from TVDataDO where created_by = ?1 and deleted = ?2 "
					+ " and tv_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<TVDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (TVDataDO dataDO : result) {
					int ipc = dataDO.getProd_code();
					if(pc>0) {
						if(pc==ipc) {
							NCDBCRCTVReportVO dataVO = new NCDBCRCTVReportVO();
							dataVO.setTranxDate(dataDO.getTv_date());
							dataVO.setConnectionType(6);
							dataVO.setNocs(1);
							dataVO.setNcs(dataDO.getNo_of_cyl());
							dataVO.setNrs(dataDO.getNo_of_reg());
							BigDecimal cd = new BigDecimal(dataDO.getCyl_deposit());
							BigDecimal rd = new BigDecimal(dataDO.getReg_deposit());
							dataVO.setTotalSecurityDeposit((cd.add(rd)).toString());
							dataVO.setProdCode(pc);
							voList.add(dataVO);
						} else {
							continue;
						}
					} else {
						NCDBCRCTVReportVO dataVO = new NCDBCRCTVReportVO();
						dataVO.setTranxDate(dataDO.getTv_date());
						dataVO.setConnectionType(6);
						dataVO.setNocs(1);
						dataVO.setNcs(dataDO.getNo_of_cyl());
						dataVO.setNrs(dataDO.getNo_of_reg());
						BigDecimal cd = new BigDecimal(dataDO.getCyl_deposit());
						BigDecimal rd = new BigDecimal(dataDO.getReg_deposit());
						dataVO.setTotalSecurityDeposit((cd.add(rd)).toString());
						dataVO.setProdCode(dataDO.getProd_code());
						voList.add(dataVO);
					}
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return voList;
	}

	@SuppressWarnings("unchecked")
	public List<SalesReturnDO> getAgencySalesReturnsByDateRangeAndProductType(long agencyId, long fdl, long tdl,
			int pc) {
		List<SalesReturnDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<SalesReturnDO> query = session.createQuery("from SalesReturnDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) "
					+ " and inv_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, 2);
			List<SalesReturnDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (SalesReturnDO dataDO : result) {
					String includeProductCode = pc != 0 ? " and prod_code = ?2":"";
					Query<SalesReturnDetailsDO> squery = session.createQuery("from SalesReturnDetailsDO where sr_ref_id = ?1"+includeProductCode);
					squery.setParameter(1, dataDO.getId());
					if(pc>0){
						squery.setParameter(2, pc);
					}
					List<SalesReturnDetailsDO> itemsResult = squery.getResultList();
					if(!itemsResult.isEmpty()){
						dataDO.getDetailsList().addAll(itemsResult);
					}
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseReturnDO> getAgencyPurchaseReturnsByDateRangeAndProductType(long agencyId, long fdl, long tdl,
			int pc) {
		List<PurchaseReturnDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<PurchaseReturnDO> query = session.createQuery("from PurchaseReturnDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) "
					+ " and inv_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, 2);
			List<PurchaseReturnDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (PurchaseReturnDO dataDO : result) {
					String includeProductCode = pc != 0 ? " and prod_code = ?2":"";
					Query<PurchaseReturnDetailsDO> squery = session.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1"+includeProductCode);
					squery.setParameter(1, dataDO.getId());
					if(pc>0){
						squery.setParameter(2, pc);
					}
					List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
					if(!itemsResult.isEmpty()){
						dataDO.getDetailsList().addAll(itemsResult);
					}
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}


	public List<ReceivablesDO> getAgencyReceivablesByCustomerId(long agencyId, long tdl, long cvoId) {
		long fdl = 1498847400000L;
		List<ReceivablesDO> receivablesList = new ArrayList<>();
		List<ReceiptsDataDO> receiptsDataDOList = new ArrayList<>();
		List<SalesReturnDO> salesReturnDOList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			//Fetch DOM
			@SuppressWarnings("unchecked")
			Query<DOMSalesInvoiceDO> dquery = session.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and customer_id = ?5 and si_date between ?3 and ?4 and payment_status < 3");
			dquery.setParameter(1, agencyId);
			dquery.setParameter(2, 0);
			dquery.setParameter(3, fdl);
			dquery.setParameter(4, tdl);
			dquery.setParameter(5, cvoId);
			List<DOMSalesInvoiceDO> dresult = dquery.getResultList(); 
			if(dresult.size()>0) {
				for(DOMSalesInvoiceDO dataDO : dresult) {
					ReceivablesDO rbDO = new ReceivablesDO();
					rbDO.setId(dataDO.getId());
					rbDO.setInvType(1);
					rbDO.setStatus(dataDO.getPayment_status());
					rbDO.setInvAmount(dataDO.getSi_amount());
					rbDO.setCreatedOn(dataDO.getCreated_date());
					rbDO.setInvDate(dataDO.getSi_date());
					receivablesList.add(rbDO);
				}
			}
			

			//Fetch COM
			@SuppressWarnings("unchecked")
			Query<COMSalesInvoiceDO> cquery = session.createQuery("from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and customer_id = ?5 and si_date between ?3 and ?4 and payment_status != 3");
			cquery.setParameter(1, agencyId);
			cquery.setParameter(2, 0);
			cquery.setParameter(3, fdl);
			cquery.setParameter(4, tdl);
			cquery.setParameter(5, cvoId);
			List<COMSalesInvoiceDO> cresult = cquery.getResultList(); 
			if(cresult.size()>0) {
				for(COMSalesInvoiceDO dataDO : cresult) {
					ReceivablesDO rbDO = new ReceivablesDO();
					rbDO.setId(dataDO.getId());
					rbDO.setInvType(2);
					rbDO.setStatus(dataDO.getPayment_status());
					rbDO.setInvAmount(dataDO.getSi_amount());
					rbDO.setCreatedOn(dataDO.getCreated_date());
					rbDO.setInvDate(dataDO.getSi_date());
					receivablesList.add(rbDO);
				}
			}
		
			//Fetch ARB
			@SuppressWarnings("unchecked")
			Query<ARBSalesInvoiceDO> aquery = session.createQuery("from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and customer_id = ?5 and si_date between ?3 and ?4 and payment_status != 3");
			aquery.setParameter(1, agencyId);
			aquery.setParameter(2, 0);
			aquery.setParameter(3, fdl);
			aquery.setParameter(4, tdl);
			aquery.setParameter(5, cvoId);
			List<ARBSalesInvoiceDO> aresult = aquery.getResultList(); 
			if(aresult.size()>0) {
				for(ARBSalesInvoiceDO dataDO : aresult) {
					ReceivablesDO rbDO = new ReceivablesDO();
					rbDO.setId(dataDO.getId());
					rbDO.setInvType(3);
					rbDO.setStatus(dataDO.getPayment_status());
					rbDO.setInvAmount(dataDO.getSi_amount());
					rbDO.setCreatedOn(dataDO.getCreated_date());
					rbDO.setInvDate(dataDO.getSi_date());
					receivablesList.add(rbDO);
				}
			}

			//Fetch Sales Returns
			@SuppressWarnings("unchecked")
			Query<SalesReturnDO> srquery = session.createQuery("from SalesReturnDO where created_by = ?1 and deleted = ?2 "
			+ " and cvo_id = ?5 and inv_date between ?3 and ?4 and clearance_status != 3");
			srquery.setParameter(1, agencyId);
			srquery.setParameter(2, 0);
			srquery.setParameter(3, fdl);
			srquery.setParameter(4, tdl);
			srquery.setParameter(5, cvoId);
			List<SalesReturnDO> srresult = srquery.getResultList(); 
			if(srresult.size()>0) {
				for (SalesReturnDO dataDO : srresult) {
					salesReturnDOList.add(dataDO);
				}
			} 
			
			@SuppressWarnings("unchecked")
			Query<ReceiptsDataDO> query = session.createQuery("from ReceiptsDataDO where created_by = ?1 and deleted = ?2 "
					+ " and rcvd_from = ?5 and rcp_date between ?3 and ?4 and clearance_status != 3");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			List<ReceiptsDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (ReceiptsDataDO dataDO : result) {
					receiptsDataDOList.add(dataDO);
				}
			} 

			Collections.sort(receivablesList,Comparator.comparing(ReceivablesDO::getCreatedOn));
			Collections.sort(receiptsDataDOList,Comparator.comparing(ReceiptsDataDO::getCreated_date));
			Collections.sort(salesReturnDOList,Comparator.comparing(SalesReturnDO::getCreated_date));
			//Process
			BigDecimal tra = new BigDecimal("0.00");
			BigDecimal pamt = new BigDecimal("0.00");
			if(receiptsDataDOList.size()>0) {
				for(ReceiptsDataDO rdataDO : receiptsDataDOList) {
					BigDecimal na = new BigDecimal(rdataDO.getRcp_amount());
					tra = tra.add(na);
				}
			}
			if(salesReturnDOList.size()>0){
				for(SalesReturnDO srDO : salesReturnDOList) {
					BigDecimal na = new BigDecimal(srDO.getInv_amt());
					tra = tra.add(na);
				}
			}
			pamt = tra;
			
			BigDecimal bsiamt = new BigDecimal("0.00");
			CVODataDO dataDO = session.get(CVODataDO.class, new Long(cvoId));
			BigDecimal eamt = new BigDecimal(dataDO.getEbal());
			if(eamt.compareTo(BigDecimal.ZERO) < 0){
				pamt = pamt.add(eamt.abs());
			}
			for(ReceivablesDO rcDO : receivablesList) {
				if(rcDO.getStatus()==2){
					rcDO.setInvAmount(dataDO.getEbal());
					break;
				}
			}
			
			if(pamt.compareTo(BigDecimal.ZERO) > 0) {
				int rp = 0; //Receivables processed
				for(ReceivablesDO rcvDataDO : receivablesList) {
					rp = rp + 1;
					BigDecimal samt = new BigDecimal(rcvDataDO.getInvAmount());
					int cv = pamt.compareTo(samt);
					if(cv==0){
						rcvDataDO.setStatus(3);
						bsiamt = new BigDecimal("0.00");
						break;
					} else if (cv <0){
						rcvDataDO.setStatus(2);
						bsiamt = samt.subtract(pamt);
						break;
					} else if (cv >0){
						rcvDataDO.setStatus(3);
						bsiamt = samt.subtract(pamt);
						pamt = pamt.subtract(samt);
					}
				}
				
				Iterator<ReceivablesDO> iter = receivablesList.iterator();
				while (iter.hasNext()) {
					ReceivablesDO rcvDataDO = iter.next();
					if(rcvDataDO.getStatus()==3 || rcvDataDO.getStatus()==2) {
						if(rcvDataDO.getInvType()==1) {
							DOMSalesInvoiceDO idataDO = session.get(DOMSalesInvoiceDO.class, new Long(rcvDataDO.getId()));
							idataDO.setPayment_status(rcvDataDO.getStatus());
							session.update(idataDO);
						} else if(rcvDataDO.getInvType()==2){
							COMSalesInvoiceDO idataDO = session.get(COMSalesInvoiceDO.class, new Long(rcvDataDO.getId()));
							idataDO.setPayment_status(rcvDataDO.getStatus());
							session.update(idataDO);
						} else if(rcvDataDO.getInvType()==3){
							ARBSalesInvoiceDO idataDO = session.get(ARBSalesInvoiceDO.class, new Long(rcvDataDO.getId()));
							idataDO.setPayment_status(rcvDataDO.getStatus());
							session.update(idataDO);
						}
						
						if(rcvDataDO.getStatus()==3)
							iter.remove();
					} else 
						break;
				}

				if( rp==0 && pamt.compareTo(BigDecimal.ZERO)>0 ) {
					bsiamt = bsiamt.subtract(pamt);
				}
				
				dataDO.setEbal(bsiamt.toString());
				session.update(dataDO);
				
				for(ReceiptsDataDO rpDO : receiptsDataDOList) {
					rpDO.setClearance_status(3);
					session.update(rpDO);
				}
				
				for(SalesReturnDO srDO : salesReturnDOList) {
					srDO.setClearance_status(3);
					session.update(srDO);
				}
			} 
			
			for(ReceivablesDO rcDO : receivablesList) {
				if(rcDO.getStatus()==2){
					rcDO.setInvAmount(dataDO.getEbal());
					break;
				}
			}
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> ReportsPersistenceManager --> getAgencyReceivablesByCustomerId  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return receivablesList;
	}

	public List<PayablesDO> getAgencyPayablesByVendorId(long agencyId, long tdl, long cvoId) {
		long fdl = 1498847400000L;
		List<PayablesDO> payablesList = new ArrayList<>();
		List<PaymentsDataDO> paymentsDataDOList = new ArrayList<>();
		List<PurchaseReturnDO> purchaseReturnDOList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			//Fetch ARB
			@SuppressWarnings("unchecked")
			Query<ARBPurchaseInvoiceDO> aquery = session.createQuery("from ARBPurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and vendor_id = ?5 and inv_date between ?3 and ?4 and payment_status != 3");
			aquery.setParameter(1, agencyId);
			aquery.setParameter(2, 0);
			aquery.setParameter(3, fdl);
			aquery.setParameter(4, tdl);
			aquery.setParameter(5, cvoId);
			List<ARBPurchaseInvoiceDO> aresult = aquery.getResultList(); 
			if(aresult.size()>0) {
				for(ARBPurchaseInvoiceDO dataDO : aresult) {
					PayablesDO pbDO = new PayablesDO();
					pbDO.setId(dataDO.getId());
					pbDO.setInvType(1);
					pbDO.setStatus(dataDO.getPayment_status());
					pbDO.setInvAmount(dataDO.getC_amount());
					pbDO.setCreatedOn(dataDO.getCreated_date());
					pbDO.setInvDate(dataDO.getInv_date());
					payablesList.add(pbDO);
				}
			}

			//Fetch Other Purchases
			@SuppressWarnings("unchecked")
			Query<OtherPurchaseInvoiceDO> oquery = session.createQuery("from OtherPurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
					+ " and vendor_id = ?5 and inv_date between ?3 and ?4 and payment_status != 3");
			oquery.setParameter(1, agencyId);
			oquery.setParameter(2, 0);
			oquery.setParameter(3, fdl);
			oquery.setParameter(4, tdl);
			oquery.setParameter(5, cvoId);
			List<OtherPurchaseInvoiceDO> oresult = oquery.getResultList(); 
			if(oresult.size()>0) {
				for(OtherPurchaseInvoiceDO dataDO : oresult) {
					PayablesDO pbDO = new PayablesDO();
					pbDO.setId(dataDO.getId());
					pbDO.setInvType(2);
					pbDO.setStatus(dataDO.getPayment_status());
					pbDO.setInvAmount(dataDO.getP_amount());
					pbDO.setCreatedOn(dataDO.getCreated_date());
					pbDO.setInvDate(dataDO.getInv_date());
					payablesList.add(pbDO);
				}
			}

			//Fetch Purchase Returns
			@SuppressWarnings("unchecked")
			Query<PurchaseReturnDO> prquery = session.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
			+ " and cvo_id = ?5 and inv_date between ?3 and ?4 and clearance_status != 3");
			prquery.setParameter(1, agencyId);
			prquery.setParameter(2, 0);
			prquery.setParameter(3, fdl);
			prquery.setParameter(4, tdl);
			prquery.setParameter(5, cvoId);
			List<PurchaseReturnDO> prresult = prquery.getResultList(); 
			if(prresult.size()>0) {
				for (PurchaseReturnDO dataDO : prresult) {
					purchaseReturnDOList.add(dataDO);
				}
			} 
			
			@SuppressWarnings("unchecked")
			Query<PaymentsDataDO> query = session.createQuery("from PaymentsDataDO where created_by = ?1 and deleted = ?2 "
					+ " and paid_to = ?5 and pymt_date between ?3 and ?4 and clearance_status != 3");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fdl);
			query.setParameter(4, tdl);
			query.setParameter(5, cvoId);
			List<PaymentsDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (PaymentsDataDO dataDO : result) {
					paymentsDataDOList.add(dataDO);
				}
			} 

			Collections.sort(payablesList,Comparator.comparing(PayablesDO::getCreatedOn));
			Collections.sort(paymentsDataDOList,Comparator.comparing(PaymentsDataDO::getCreated_date));
			Collections.sort(purchaseReturnDOList,Comparator.comparing(PurchaseReturnDO::getCreated_date));
			
			//Process
			BigDecimal tpa = new BigDecimal("0.00");
			BigDecimal pamt = new BigDecimal("0.00");
			if(paymentsDataDOList.size()>0) {
				for(PaymentsDataDO pdataDO : paymentsDataDOList) {
					BigDecimal na = new BigDecimal(pdataDO.getPymt_amount());
					tpa = tpa.add(na);
				}
			}
			if(purchaseReturnDOList.size()>0) {
				for(PurchaseReturnDO prdataDO : purchaseReturnDOList) {
					BigDecimal na = new BigDecimal(prdataDO.getInv_amt());
					tpa = tpa.add(na);
				}
			}
			pamt = tpa;

			BigDecimal bpiamt = new BigDecimal("0.00");
			CVODataDO dataDO = session.get(CVODataDO.class, new Long(cvoId));
			BigDecimal eamt = new BigDecimal(dataDO.getEbal());
			if(eamt.compareTo(BigDecimal.ZERO) < 0) {
				pamt = pamt.add(eamt.abs());
			}
			for(PayablesDO pdDO : payablesList) {
				if(pdDO.getStatus()==2){
					pdDO.setInvAmount(dataDO.getEbal());
					break;
				}
			}
			
			if(pamt.compareTo(BigDecimal.ZERO) > 0) {
				int pp = 0; //payables processed
				for(PayablesDO pbvDataDO : payablesList) {
					pp = pp+1;
					BigDecimal samt = new BigDecimal(pbvDataDO.getInvAmount());
					int cv = pamt.compareTo(samt);
					if(cv==0){
						pbvDataDO.setStatus(3);
						bpiamt = new BigDecimal("0.00");
						break;
					} else if (cv <0){
						pbvDataDO.setStatus(2);
						bpiamt = samt.subtract(pamt);
						break;
					} else if (cv >0){
						pbvDataDO.setStatus(3);
						bpiamt = samt.subtract(pamt);
						pamt = pamt.subtract(samt);
					}
				}
				Iterator<PayablesDO> iter = payablesList.iterator();
				while (iter.hasNext()) {
					PayablesDO pbvDataDO = iter.next();
					if(pbvDataDO.getStatus()==3 || pbvDataDO.getStatus()==2) {
						if(pbvDataDO.getInvType()==1){
							ARBPurchaseInvoiceDO idataDO = session.get(ARBPurchaseInvoiceDO.class, new Long(pbvDataDO.getId()));
							idataDO.setPayment_status(pbvDataDO.getStatus());
							session.update(idataDO);
						} else if(pbvDataDO.getInvType()==2){
							OtherPurchaseInvoiceDO idataDO = session.get(OtherPurchaseInvoiceDO.class, new Long(pbvDataDO.getId()));
							idataDO.setPayment_status(pbvDataDO.getStatus());
							session.update(idataDO);
						}
						
						if(pbvDataDO.getStatus()==3)
							iter.remove();
					} else 
						break;
				}
				
				if( pp==0 && pamt.compareTo(BigDecimal.ZERO)>0 ) {
					bpiamt = bpiamt.subtract(pamt);
				}
				
				dataDO.setEbal(bpiamt.toString());
				session.update(dataDO);
				
				for(PaymentsDataDO pmDO : paymentsDataDOList) {
					pmDO.setClearance_status(3);
					session.update(pmDO);
				}
				
				for(PurchaseReturnDO prDO : purchaseReturnDOList) {
					prDO.setClearance_status(3);
					session.update(prDO);
				}
			} 
			
			for(PayablesDO pbDO : payablesList) {
				if(pbDO.getStatus()==2){
					pbDO.setInvAmount(dataDO.getEbal());
					break;
				}
			}
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> ReportsPersistenceManager --> getAgencyPayablesByVendorId  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return payablesList;
	}
	

	//GST PAYMENTS Report Data
	@SuppressWarnings("unchecked")
	public List<GSTPaymentDetailsDO> getAgencyGSTPaymentDetailsReportByDateRange(
			long agencyId,long fromDate,long toDate) throws BusinessException {
		List<GSTPaymentDetailsDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<GSTPaymentDetailsDO> query = session.createQuery("from GSTPaymentDetailsDO where created_by = ?1 and (deleted = ?2 OR deleted = ?6) "
					+ " and pdate between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(6, 2);
			List<GSTPaymentDetailsDO> result = query.getResultList();
			
			if(result.size()>0) {
				for (GSTPaymentDetailsDO dataDO : result) {
					doList.add(dataDO);
				}
			} 
		}catch(Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
		return doList;
	}
}
