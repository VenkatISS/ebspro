package com.it.erpapp.framework.managers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.it.erpapp.framework.model.AgencyDetailsDO;
import com.it.erpapp.framework.model.transactions.ARBPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.PurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.DEProductsDetailsViewDO;
import com.it.erpapp.framework.model.transactions.sales.DEProductsViewDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.RCDataDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDetailsDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

public class DEReportsPersistenceManager {

	Logger logger = Logger.getLogger(DEReportsPersistenceManager.class.getName());

	@SuppressWarnings("unchecked")
	public List<DEProductsViewDO> getAgencyProductsTransactionsByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<DEProductsViewDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {

			Query<PurchaseInvoiceDO> epquery = session
					.createQuery("from PurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and inv_date between ?3 and ?4 ");
			epquery.setParameter(1, agencyId);
			epquery.setParameter(2, 0);
			epquery.setParameter(3, fromDate);
			epquery.setParameter(4, toDate);
			List<PurchaseInvoiceDO> epresult = epquery.getResultList();
			if (epresult.size() > 0) {
				for (PurchaseInvoiceDO dataDO : epresult) {
					DEProductsViewDO pvo = new DEProductsViewDO();
					pvo.setId(dataDO.getId());
					pvo.setTx_date(dataDO.getStk_recvd_date());
					pvo.setCreated_date(dataDO.getCreated_date());
					pvo.setTx_type(1);
					List<DEProductsDetailsViewDO> detailsList = new ArrayList<>();
					DEProductsDetailsViewDO pdetailsvo = new DEProductsDetailsViewDO();
					pdetailsvo.setTx_id(dataDO.getId());
					pdetailsvo.setTx_type(1);
					pdetailsvo.setProd_code(dataDO.getProd_code());
					pdetailsvo.setQuantity(dataDO.getQuantity());
					pdetailsvo.setC_stock(dataDO.getCs_fulls());
					pdetailsvo.setE_stock(dataDO.getCs_emptys());
					detailsList.add(pdetailsvo);
					pvo.setDetailsList(detailsList);
					doList.add(pvo);
				}
			}

			// Fetch ARB Purchases
			Query<ARBPurchaseInvoiceDO> arbpquery = session
					.createQuery("from ARBPurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and stk_recvd_date between ?3 and ?4 ");
			arbpquery.setParameter(1, agencyId);
			arbpquery.setParameter(2, 0);
			arbpquery.setParameter(3, fromDate);
			arbpquery.setParameter(4, toDate);
			List<ARBPurchaseInvoiceDO> arbpresult = arbpquery.getResultList();
			if (arbpresult.size() > 0) {
				for (ARBPurchaseInvoiceDO dataDO : arbpresult) {
					DEProductsViewDO pvo = new DEProductsViewDO();
					pvo.setId(dataDO.getId());
					pvo.setTx_date(dataDO.getStk_recvd_date());
					pvo.setCreated_date(dataDO.getCreated_date());
					pvo.setTx_type(2);
					List<DEProductsDetailsViewDO> detailsList = new ArrayList<>();
					DEProductsDetailsViewDO pdetailsvo = new DEProductsDetailsViewDO();
					pdetailsvo.setTx_id(dataDO.getId());
					pdetailsvo.setTx_type(2);
					pdetailsvo.setProd_code(dataDO.getArb_code());
					pdetailsvo.setQuantity(dataDO.getQuantity());
					pdetailsvo.setC_stock(dataDO.getC_stock());
					pdetailsvo.setE_stock(0);
					detailsList.add(pdetailsvo);
					pvo.setDetailsList(detailsList);
					doList.add(pvo);
				}
			}

			// Fetch DOM Sales
			Query<DOMSalesInvoiceDO> dsquery = session
					.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 ");
			dsquery.setParameter(1, agencyId);
			dsquery.setParameter(2, 0);
			dsquery.setParameter(3, fromDate);
			dsquery.setParameter(4, toDate);
			List<DOMSalesInvoiceDO> dsresult = dsquery.getResultList();
			if (dsresult.size() > 0) {
				for (DOMSalesInvoiceDO dataDO : dsresult) {
					DEProductsViewDO pvo = new DEProductsViewDO();
					pvo.setId(dataDO.getId());
					pvo.setTx_date(dataDO.getSi_date());
					pvo.setCreated_date(dataDO.getCreated_date());
					pvo.setTx_type(3);
					List<DEProductsDetailsViewDO> detailsList = new ArrayList<>();
					Query<DOMSalesInvoiceDetailsDO> dsdquery = session
							.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1");
					dsdquery.setParameter(1, dataDO.getId());
					List<DOMSalesInvoiceDetailsDO> dsdresults = dsdquery.getResultList();
					if (dsdresults.size() > 0) {
						for (DOMSalesInvoiceDetailsDO detailsDO : dsdresults) {
							DEProductsDetailsViewDO pdetailsvo = new DEProductsDetailsViewDO();
							pdetailsvo.setTx_id(dataDO.getId());
							pdetailsvo.setTx_type(3);
							pdetailsvo.setProd_code(detailsDO.getProd_code());
							pdetailsvo.setQuantity(detailsDO.getQuantity());
							pdetailsvo.setC_stock(detailsDO.getCs_fulls());
							pdetailsvo.setE_stock(detailsDO.getCs_emptys());
							pdetailsvo.setE_stock(0);
							detailsList.add(pdetailsvo);
						}
					}
					pvo.setDetailsList(detailsList);
					doList.add(pvo);
				}
			}

			// Fetch COM Sales
			Query<COMSalesInvoiceDO> csquery = session
					.createQuery("from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 ");
			csquery.setParameter(1, agencyId);
			csquery.setParameter(2, 0);
			csquery.setParameter(3, fromDate);
			csquery.setParameter(4, toDate);
			List<COMSalesInvoiceDO> csresult = csquery.getResultList();
			if (csresult.size() > 0) {
				for (COMSalesInvoiceDO dataDO : csresult) {
					DEProductsViewDO pvo = new DEProductsViewDO();
					pvo.setId(dataDO.getId());
					pvo.setTx_date(dataDO.getSi_date());
					pvo.setCreated_date(dataDO.getCreated_date());
					pvo.setTx_type(4);
					List<DEProductsDetailsViewDO> detailsList = new ArrayList<>();
					Query<COMSalesInvoiceDetailsDO> csdquery = session
							.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1");
					csdquery.setParameter(1, dataDO.getId());
					List<COMSalesInvoiceDetailsDO> csdresults = csdquery.getResultList();
					if (csdresults.size() > 0) {
						for (COMSalesInvoiceDetailsDO detailsDO : csdresults) {
							DEProductsDetailsViewDO pdetailsvo = new DEProductsDetailsViewDO();
							pdetailsvo.setTx_id(dataDO.getId());
							pdetailsvo.setTx_type(4);
							pdetailsvo.setProd_code(detailsDO.getProd_code());
							pdetailsvo.setQuantity(detailsDO.getQuantity());
							pdetailsvo.setC_stock(detailsDO.getCs_fulls());
							pdetailsvo.setE_stock(detailsDO.getCs_emptys());
							pdetailsvo.setE_stock(0);
							detailsList.add(pdetailsvo);
						}
					}
					pvo.setDetailsList(detailsList);
					doList.add(pvo);
				}
			}

			// Fetch ARB Sales
			Query<ARBSalesInvoiceDO> arbsquery = session
					.createQuery("from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 ");
			arbsquery.setParameter(1, agencyId);
			arbsquery.setParameter(2, 0);
			arbsquery.setParameter(3, fromDate);
			arbsquery.setParameter(4, toDate);
			List<ARBSalesInvoiceDO> arbsresult = arbsquery.getResultList();
			if (arbsresult.size() > 0) {
				for (ARBSalesInvoiceDO dataDO : arbsresult) {
					DEProductsViewDO pvo = new DEProductsViewDO();
					pvo.setId(dataDO.getId());
					pvo.setTx_date(dataDO.getSi_date());
					pvo.setCreated_date(dataDO.getCreated_date());
					pvo.setTx_type(5);
					List<DEProductsDetailsViewDO> detailsList = new ArrayList<>();
					Query<ARBSalesInvoiceDetailsDO> arbsdquery = session
							.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1");
					arbsdquery.setParameter(1, dataDO.getId());
					List<ARBSalesInvoiceDetailsDO> sdresults = arbsdquery.getResultList();
					if (sdresults.size() > 0) {
						for (ARBSalesInvoiceDetailsDO detailsDO : sdresults) {
							DEProductsDetailsViewDO pdetailsvo = new DEProductsDetailsViewDO();
							pdetailsvo.setTx_id(dataDO.getId());
							pdetailsvo.setTx_type(5);
							pdetailsvo.setProd_code(detailsDO.getProd_code());
							pdetailsvo.setQuantity(detailsDO.getQuantity());
							pdetailsvo.setC_stock(detailsDO.getC_stock());
							pdetailsvo.setE_stock(0);
							detailsList.add(pdetailsvo);
						}
					}
					pvo.setDetailsList(detailsList);
					doList.add(pvo);
				}
			}

			// Fetch NC Sales
			Query<NCDBCInvoiceDO> ncarbsquery = session.createQuery(
					"from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 " + " and inv_date between ?3 and ?4 ");
			ncarbsquery.setParameter(1, agencyId);
			ncarbsquery.setParameter(2, 0);
			ncarbsquery.setParameter(3, fromDate);
			ncarbsquery.setParameter(4, toDate);
			List<NCDBCInvoiceDO> ncarbsresult = ncarbsquery.getResultList();
			if (ncarbsresult.size() > 0) {
				for (NCDBCInvoiceDO dataDO : ncarbsresult) {
					DEProductsViewDO pvo = new DEProductsViewDO();
					pvo.setId(dataDO.getId());
					pvo.setTx_date(dataDO.getInv_date());
					pvo.setCreated_date(dataDO.getCreated_date());
					pvo.setTx_type(6);
					List<DEProductsDetailsViewDO> detailsList = new ArrayList<>();
					Query<NCDBCInvoiceDetailsDO> ncarbsdquery = session
							.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1");
					ncarbsdquery.setParameter(1, dataDO.getId());
					List<NCDBCInvoiceDetailsDO> ncarbsdresults = ncarbsdquery.getResultList();
					if (ncarbsdresults.size() > 0) {
						for (NCDBCInvoiceDetailsDO detailsDO : ncarbsdresults) {
							DEProductsDetailsViewDO pdetailsvo = new DEProductsDetailsViewDO();
							pdetailsvo.setTx_id(dataDO.getId());
							pdetailsvo.setTx_type(6);
							long pccode = detailsDO.getProd_code();
							pdetailsvo.setProd_code(pccode);
							if (pccode > 9) {
								pdetailsvo.setQuantity(detailsDO.getQuantity());
							} else {
								pdetailsvo.setQuantity(0);
								pdetailsvo.setNcquantity(detailsDO.getQuantity());
							}
							pdetailsvo.setC_stock(detailsDO.getC_stock());
							pdetailsvo.setE_stock(0);
							detailsList.add(pdetailsvo);
						}
					}
					pvo.setDetailsList(detailsList);
					doList.add(pvo);
				}
			}

			// Fetch RC
			Query<RCDataDO> rcquery = session.createQuery(
					"from RCDataDO where created_by = ?1 and deleted = ?2 " + " and rc_date between ?3 and ?4 ");
			rcquery.setParameter(1, agencyId);
			rcquery.setParameter(2, 0);
			rcquery.setParameter(3, fromDate);
			rcquery.setParameter(4, toDate);
			List<RCDataDO> rcresult = rcquery.getResultList();
			if (rcresult.size() > 0) {
				for (RCDataDO dataDO : rcresult) {
					DEProductsViewDO pvo = new DEProductsViewDO();
					pvo.setId(dataDO.getId());
					pvo.setTx_date(dataDO.getRc_date());
					pvo.setCreated_date(dataDO.getCreated_date());
					pvo.setTx_type(8);
					List<DEProductsDetailsViewDO> detailsList = new ArrayList<>();
					DEProductsDetailsViewDO pdetailsvo = new DEProductsDetailsViewDO();
					pdetailsvo.setTx_id(dataDO.getId());
					pdetailsvo.setTx_type(8);
					long pccode = new Long(dataDO.getProd_code());
					pdetailsvo.setProd_code(pccode);
					pdetailsvo.setQuantity(0);
					pdetailsvo.setNcquantity(dataDO.getNo_of_cyl());
					pdetailsvo.setC_stock(0);
					pdetailsvo.setE_stock(0);
					detailsList.add(pdetailsvo);
					pvo.setDetailsList(detailsList);
					doList.add(pvo);
				}
			}

			// Fetch DOM Purchases Returns
			Query<PurchaseReturnDO> querypr = session
					.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
							+ " and inv_date between ?3 and ?4");
			querypr.setParameter(1, agencyId);
			querypr.setParameter(2, 0);
			querypr.setParameter(3, fromDate);
			querypr.setParameter(4, toDate);
			List<PurchaseReturnDO> resultpr = querypr.getResultList();
			if (resultpr.size() > 0) {
				for (PurchaseReturnDO dataDO : resultpr) {
					DEProductsViewDO pvo = new DEProductsViewDO();
					pvo.setId(dataDO.getId());
					pvo.setTx_date(dataDO.getInv_date());
					pvo.setCreated_date(dataDO.getCreated_date());
					pvo.setTx_type(7);
					Query<PurchaseReturnDetailsDO> squerypr = session
							.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1");
					squerypr.setParameter(1, dataDO.getId());
					List<DEProductsDetailsViewDO> detailsList = new ArrayList<>();
					List<PurchaseReturnDetailsDO> itemsPRResult = squerypr.getResultList();
					if (itemsPRResult.size() > 0) {
						for (PurchaseReturnDetailsDO detailsDO : itemsPRResult) {
							DEProductsDetailsViewDO pdetailsvo = new DEProductsDetailsViewDO();
							pdetailsvo.setTx_id(dataDO.getId());
							pdetailsvo.setTx_type(7);
							pdetailsvo.setProd_code(detailsDO.getProd_code());
							pdetailsvo.setQuantity(detailsDO.getRtn_qty());
							pdetailsvo.setC_stock(detailsDO.getCs_fulls());
							pdetailsvo.setE_stock(detailsDO.getCs_emptys());
							pdetailsvo.setE_stock(0);
							detailsList.add(pdetailsvo);
						}
					}
					pvo.setDetailsList(detailsList);
					doList.add(pvo);
				}
			}

			// Fetch Sales Returns
			Query<SalesReturnDO> querysr = session.createQuery(
					"from SalesReturnDO where created_by = ?1 and deleted = ?2 " + " and inv_date between ?3 and ?4");
			querysr.setParameter(1, agencyId);
			querysr.setParameter(2, 0);
			querysr.setParameter(3, fromDate);
			querysr.setParameter(4, toDate);
			List<SalesReturnDO> resultsr = querysr.getResultList();
			if (resultsr.size() > 0) {
				for (SalesReturnDO dataDO : resultsr) {
					DEProductsViewDO pvo = new DEProductsViewDO();
					pvo.setId(dataDO.getId());
					pvo.setTx_date(dataDO.getInv_date());
					pvo.setCreated_date(dataDO.getCreated_date());
					pvo.setTx_type(9);
					Query<SalesReturnDetailsDO> squerysr = session
							.createQuery("from SalesReturnDetailsDO where sr_ref_id = ?1");
					squerysr.setParameter(1, dataDO.getId());
					List<DEProductsDetailsViewDO> detailsList = new ArrayList<>();
					List<SalesReturnDetailsDO> itemsSRResult = squerysr.getResultList();
					if (itemsSRResult.size() > 0) {
						for (SalesReturnDetailsDO detailsDO : itemsSRResult) {
							DEProductsDetailsViewDO pdetailsvo = new DEProductsDetailsViewDO();
							pdetailsvo.setTx_id(dataDO.getId());
							pdetailsvo.setTx_type(9);
							pdetailsvo.setProd_code(detailsDO.getProd_code());
							pdetailsvo.setQuantity(detailsDO.getRtn_qty());
							pdetailsvo.setC_stock(detailsDO.getCs_fulls());
							pdetailsvo.setE_stock(detailsDO.getCs_emptys());
							pdetailsvo.setE_stock(0);
							detailsList.add(pdetailsvo);
						}
					}
					pvo.setDetailsList(detailsList);
					doList.add(pvo);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// Bank Transactions
	@SuppressWarnings("unchecked")
	public List<BankTranxsDataDO> getAgencyBankTransactionsByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<BankTranxsDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<BankTranxsDataDO> query = session
					.createQuery("from BankTranxsDataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?3) "
							+ " and bt_date between ?4 and ?5 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			query.setParameter(4, fromDate);
			query.setParameter(5, toDate);
			List<BankTranxsDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (BankTranxsDataDO dataDO : result) {
					doList.add(dataDO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	public void submitDayEndConfirmation(long ded, long agencyId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AgencyDetailsDO dataDO = session.get(AgencyDetailsDO.class, new Long(agencyId));
			dataDO.setDayendDate(ded);
			session.update(dataDO);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> DEReportsPersistenceManager --> submitDayEndConfirmation  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

}
