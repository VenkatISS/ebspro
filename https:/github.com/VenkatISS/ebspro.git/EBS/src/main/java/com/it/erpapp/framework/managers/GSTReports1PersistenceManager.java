package com.it.erpapp.framework.managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.it.erpapp.framework.model.CVODataDO;
import com.it.erpapp.framework.model.pps.EquipmentDataDO;
import com.it.erpapp.framework.model.pps.RefillPriceDataDO;
import com.it.erpapp.framework.model.transactions.GSTR1CDNDataDO;
import com.it.erpapp.framework.model.transactions.GSTR1DocsDataDO;
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
import com.it.erpapp.framework.model.transactions.sales.TVDataDO;
import com.it.erpapp.framework.model.vos.GSTR1ProductDataVO;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

public class GSTReports1PersistenceManager {

	Logger logger = Logger.getLogger(FinancialReportsPersistenceManager.class.getName());

	int cyear = Calendar.getInstance().get(Calendar.YEAR);
	String cfy = (Integer.toString(cyear)).substring(2);

	// DOM Sales Invoices
	@SuppressWarnings("unchecked")
	public List<DOMSalesInvoiceDO> getAgencyGSTR1DSInvoicesByDateRangeAndAllProductType(long agencyId, long fromDate,
			long toDate) throws BusinessException {

		List<DOMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<DOMSalesInvoiceDO> query = session
					.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<DOMSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (DOMSalesInvoiceDO dataDO : result) {
					Query<DOMSalesInvoiceDetailsDO> squery = session
							.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1 ");
					squery.setParameter(1, dataDO.getId());
					List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						dataDO.getDetailsList().addAll(itemsResult);
						doList.add(dataDO);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<DOMSalesInvoiceDO> getAgencyGSTR1B2BDSInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<DOMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<DOMSalesInvoiceDO> query = session
					.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<DOMSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (DOMSalesInvoiceDO dataDO : result) {
					long cid = dataDO.getCustomer_id();

					Query<CVODataDO> query2 = session.createQuery(
							"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
					query2.setParameter(1, agencyId);
					query2.setParameter(2, 0);
					query2.setParameter(3, cid);
					query2.setParameter(4, 1);
					query2.setParameter(5, 1);
					List<CVODataDO> result2 = query2.getResultList();
					if (result2.size() > 0) {
						Query<DOMSalesInvoiceDetailsDO> squery = session
								.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<DOMSalesInvoiceDO> getAgencyGSTR1B2CLDSInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<DOMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<DOMSalesInvoiceDO> query = session
					.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4  AND si_amount>250000");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<DOMSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (DOMSalesInvoiceDO dataDO : result) {
					long cid = dataDO.getCustomer_id();
					if (cid == 0) {
						Query<DOMSalesInvoiceDetailsDO> squery = session
								.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {
							Query<DOMSalesInvoiceDetailsDO> squery = session
									.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1 ");
							squery.setParameter(1, dataDO.getId());
							List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
							if (itemsResult.size() > 0) {
								dataDO.getDetailsList().addAll(itemsResult);
								doList.add(dataDO);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<DOMSalesInvoiceDO> getAgencyGSTR1B2CSDSInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<DOMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<DOMSalesInvoiceDO> query = session
					.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 AND si_amount<=250000");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<DOMSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (DOMSalesInvoiceDO dataDO : result) {
					long cid = dataDO.getCustomer_id();
					if (cid == 0) {
						Query<DOMSalesInvoiceDetailsDO> squery = session
								.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {
							Query<DOMSalesInvoiceDetailsDO> squery = session
									.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1 ");
							squery.setParameter(1, dataDO.getId());
							List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
							if (itemsResult.size() > 0) {
								dataDO.getDetailsList().addAll(itemsResult);
								doList.add(dataDO);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<COMSalesInvoiceDO> getAgencyGSTR1CSInvoicesByDateRangeAndAllProductType(long agencyId, long fromDate,
			long toDate) throws BusinessException {
		List<COMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<COMSalesInvoiceDO> query = session
					.createQuery("from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<COMSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (COMSalesInvoiceDO dataDO : result) {
					Query<COMSalesInvoiceDetailsDO> squery = session
							.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						dataDO.getDetailsList().addAll(itemsResult);
					}
					doList.add(dataDO);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<COMSalesInvoiceDO> getAgencyGSTR1B2BCSInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<COMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<COMSalesInvoiceDO> query = session
					.createQuery("from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 AND customer_id>0");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<COMSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (COMSalesInvoiceDO dataDO : result) {
					long cid = dataDO.getCustomer_id();

					Query<CVODataDO> query2 = session.createQuery(
							"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
					query2.setParameter(1, agencyId);
					query2.setParameter(2, 0);
					query2.setParameter(3, cid);
					query2.setParameter(4, 1);
					query2.setParameter(5, 1);
					List<CVODataDO> result2 = query2.getResultList();
					if (result2.size() > 0) {
						Query<COMSalesInvoiceDetailsDO> squery = session
								.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1");
						squery.setParameter(1, dataDO.getId());
						List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
						}
						doList.add(dataDO);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<COMSalesInvoiceDO> getAgencyGSTR1B2CLCSInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<COMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<COMSalesInvoiceDO> query = session
					.createQuery("from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 AND si_amount>250000");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<COMSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (COMSalesInvoiceDO dataDO : result) {
					long cid = dataDO.getCustomer_id();
					if (cid == 0) {
						Query<COMSalesInvoiceDetailsDO> squery = session
								.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {
							Query<COMSalesInvoiceDetailsDO> squery = session
									.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1");
							squery.setParameter(1, dataDO.getId());
							List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
							if (itemsResult.size() > 0) {
								dataDO.getDetailsList().addAll(itemsResult);
							}
							doList.add(dataDO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<COMSalesInvoiceDO> getAgencyGSTR1B2CSCSInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<COMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<COMSalesInvoiceDO> query = session
					.createQuery("from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 AND si_amount<=250000");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<COMSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (COMSalesInvoiceDO dataDO : result) {
					long cid = dataDO.getCustomer_id();
					if (cid == 0) {
						Query<COMSalesInvoiceDetailsDO> squery = session
								.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {
							Query<COMSalesInvoiceDetailsDO> squery = session
									.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1");
							squery.setParameter(1, dataDO.getId());
							List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
							if (itemsResult.size() > 0) {
								dataDO.getDetailsList().addAll(itemsResult);
							}
							doList.add(dataDO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<ARBSalesInvoiceDO> getAgencyGSTR1B2BARBSInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<ARBSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<ARBSalesInvoiceDO> query = session
					.createQuery("from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 AND customer_id>0");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<ARBSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (ARBSalesInvoiceDO dataDO : result) {
					long cid = dataDO.getCustomer_id();

					Query<CVODataDO> query2 = session.createQuery(
							"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
					query2.setParameter(1, agencyId);
					query2.setParameter(2, 0);
					query2.setParameter(3, cid);
					query2.setParameter(4, 1);
					query2.setParameter(5, 1);
					List<CVODataDO> result2 = query2.getResultList();
					if (result2.size() > 0) {
						Query<ARBSalesInvoiceDetailsDO> squery = session
								.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
						}
						doList.add(dataDO);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseReturnDO> getAgencyGSTR1B2BPurReturnSInvoicesByDateRange(long agencyId, long fromDate,
			long toDate) throws BusinessException {
		List<PurchaseReturnDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<PurchaseReturnDO> query = session
					.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
							+ " and inv_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<PurchaseReturnDO> result = query.getResultList();
			if (result.size() > 0) {
				for (PurchaseReturnDO dataDO : result) {
					long cid = dataDO.getCvo_id();

					Query<CVODataDO> query2 = session.createQuery(
							"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
					query2.setParameter(1, agencyId);
					query2.setParameter(2, 0);
					query2.setParameter(3, cid);
					query2.setParameter(4, 1);
					query2.setParameter(5, 1);
					List<CVODataDO> result2 = query2.getResultList();
					if (result2.size() > 0) {
						Query<PurchaseReturnDetailsDO> squery = session
								.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
						}
						doList.add(dataDO);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseReturnDO> getAgencyGSTR1B2CLPurReturnSInvoicesByDateRange(long agencyId, long fromDate,
			long toDate) throws BusinessException {
		List<PurchaseReturnDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<PurchaseReturnDO> query = session
					.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
							+ " and inv_date between ?3 and ?4 AND inv_amt > 250000");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<PurchaseReturnDO> result = query.getResultList();
			if (result.size() > 0) {
				for (PurchaseReturnDO dataDO : result) {
					long cid = dataDO.getCvo_id();
					if (cid == 0) {
						Query<PurchaseReturnDetailsDO> squery = session
								.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {
							Query<PurchaseReturnDetailsDO> squery = session
									.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 ");
							squery.setParameter(1, dataDO.getId());
							List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
							if (itemsResult.size() > 0) {
								dataDO.getDetailsList().addAll(itemsResult);
							}
							doList.add(dataDO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<PurchaseReturnDO> getAgencyGSTR1B2CSPurReturnSInvoicesByDateRange(long agencyId, long fromDate,
			long toDate) throws BusinessException {
		List<PurchaseReturnDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<PurchaseReturnDO> query = session
					.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
							+ " and inv_date between ?3 and ?4 AND inv_amt <= 250000");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<PurchaseReturnDO> result = query.getResultList();
			if (result.size() > 0) {
				for (PurchaseReturnDO dataDO : result) {
					long cid = dataDO.getCvo_id();
					if (cid == 0) {
						Query<PurchaseReturnDetailsDO> squery = session
								.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {
							Query<PurchaseReturnDetailsDO> squery = session
									.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 ");
							squery.setParameter(1, dataDO.getId());
							List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
							if (itemsResult.size() > 0) {
								dataDO.getDetailsList().addAll(itemsResult);
							}
							doList.add(dataDO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<ARBSalesInvoiceDO> getAgencyGSTR1B2CLARBSInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<ARBSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<ARBSalesInvoiceDO> query = session
					.createQuery("from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4  AND si_amount>250000");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<ARBSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (ARBSalesInvoiceDO dataDO : result) {
					long cid = dataDO.getCustomer_id();
					if (cid == 0) {
						Query<ARBSalesInvoiceDetailsDO> squery = session
								.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {
							Query<ARBSalesInvoiceDetailsDO> squery = session
									.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1 ");
							squery.setParameter(1, dataDO.getId());
							List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
							if (itemsResult.size() > 0) {
								dataDO.getDetailsList().addAll(itemsResult);
							}
							doList.add(dataDO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<ARBSalesInvoiceDO> getAgencyGSTR1B2CSARBSInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<ARBSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<ARBSalesInvoiceDO> query = session
					.createQuery("from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 AND si_amount<=250000");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<ARBSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (ARBSalesInvoiceDO dataDO : result) {
					long cid = dataDO.getCustomer_id();
					if (cid == 0) {
						Query<ARBSalesInvoiceDetailsDO> squery = session
								.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {
							Query<ARBSalesInvoiceDetailsDO> squery = session
									.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1 ");
							squery.setParameter(1, dataDO.getId());
							List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
							if (itemsResult.size() > 0) {
								dataDO.getDetailsList().addAll(itemsResult);
							}
							doList.add(dataDO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<NCDBCInvoiceDO> getAgencyGSTR1B2BNCDBCInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<NCDBCInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<NCDBCInvoiceDO> query = session
					.createQuery("from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and inv_date between ?3 and ?4 AND customer_no>0");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (NCDBCInvoiceDO dataDO : result) {
					Query<NCDBCInvoiceDetailsDO> squery = session
							.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 ");
					squery.setParameter(1, dataDO.getId());
					List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						dataDO.getDetailsList().addAll(itemsResult);
					}
					doList.add(dataDO);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<RCDataDO> getAgencyGSTR1B2BRCInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<RCDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<RCDataDO> query = session.createQuery("from RCDataDO where created_by = ?1 and deleted = ?2 "
					+ " and rc_date between ?3 and ?4 AND customer_no>0");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<RCDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (RCDataDO dataDO : result) {
					doList.add(dataDO);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<TVDataDO> getAgencyGSTR1B2BTVInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<TVDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<TVDataDO> query = session.createQuery("from TVDataDO where created_by = ?1 and deleted = ?2 "
					+ " and tv_date between ?3 and ?4 AND customer_no>0");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<TVDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (TVDataDO dataDO : result) {
					doList.add(dataDO);
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<NCDBCInvoiceDO> getAgencyGSTR1B2CLNCDBCInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {

		List<NCDBCInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<NCDBCInvoiceDO> query = session.createQuery(
					"from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 " + " and inv_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (NCDBCInvoiceDO dataDO : result) {
					long cid = Long.parseLong(dataDO.getCustomer_no());
					if (cid == 0) {
						Query<NCDBCInvoiceDetailsDO> squery = session
								.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {
							Query<NCDBCInvoiceDetailsDO> squery = session
									.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 ");
							squery.setParameter(1, dataDO.getId());
							List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
							if (itemsResult.size() > 0) {
								dataDO.getDetailsList().addAll(itemsResult);
							}
							doList.add(dataDO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<RCDataDO> getAgencyGSTR1B2CLRCInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<RCDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<RCDataDO> query = session.createQuery("from RCDataDO where created_by = ?1 and deleted = ?2 "
					+ " and rc_date between ?3 and ?4 AND rc_amount>=250000");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<RCDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (RCDataDO dataDO : result) {
					long cid = Long.parseLong(dataDO.getCustomer_no());
					if (cid == 0) {

						doList.add(dataDO);

					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {

							doList.add(dataDO);
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<TVDataDO> getAgencyGSTR1B2CLTVInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<TVDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<TVDataDO> query = session.createQuery("from TVDataDO where created_by = ?1 and deleted = ?2 "
					+ " and tv_date between ?3 and ?4 AND tv_amount>=250000");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<TVDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (TVDataDO dataDO : result) {
					long cid = Long.parseLong(dataDO.getCustomer_no());
					if (cid == 0) {

						doList.add(dataDO);

					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {

							doList.add(dataDO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<NCDBCInvoiceDO> getAgencyGSTR1B2CSNCDBCInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<NCDBCInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<NCDBCInvoiceDO> query = session.createQuery(
					"from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 " + " and inv_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (NCDBCInvoiceDO dataDO : result) {

					long cid = Long.parseLong(dataDO.getCustomer_no());
					if (cid == 0) {
						Query<NCDBCInvoiceDetailsDO> squery = session
								.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 ");
						squery.setParameter(1, dataDO.getId());
						List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
						if (itemsResult.size() > 0) {
							dataDO.getDetailsList().addAll(itemsResult);
							doList.add(dataDO);
						}
					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {

							Query<NCDBCInvoiceDetailsDO> squery = session
									.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 ");
							squery.setParameter(1, dataDO.getId());
							List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
							if (itemsResult.size() > 0) {
								dataDO.getDetailsList().addAll(itemsResult);
							}
							doList.add(dataDO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<RCDataDO> getAgencyGSTR1B2CSRCInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<RCDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<RCDataDO> query = session.createQuery("from RCDataDO where created_by = ?1 and deleted = ?2 "
					+ " and rc_date between ?3 and ?4  AND rc_amount<=250000");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<RCDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (RCDataDO dataDO : result) {

					long cid = Long.parseLong(dataDO.getCustomer_no());
					if (cid == 0) {

						doList.add(dataDO);

					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {

							doList.add(dataDO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	@SuppressWarnings("unchecked")
	public List<TVDataDO> getAgencyGSTR1B2CSTVInvoicesByDateRange(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<TVDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<TVDataDO> query = session.createQuery("from TVDataDO where created_by = ?1 and deleted = ?2 "
					+ " and tv_date between ?3 and ?4  AND tv_amount<=250000");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<TVDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (TVDataDO dataDO : result) {
					long cid = Long.parseLong(dataDO.getCustomer_no());
					if (cid == 0) {

						doList.add(dataDO);

					} else {
						Query<CVODataDO> query2 = session.createQuery(
								"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
						query2.setParameter(1, agencyId);
						query2.setParameter(2, 0);
						query2.setParameter(3, cid);
						query2.setParameter(4, 2);
						query2.setParameter(5, 1);
						List<CVODataDO> result2 = query2.getResultList();
						if (result2.size() > 0) {

							doList.add(dataDO);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}
	

	// DOM Cyclinders gstr1 Report
	@SuppressWarnings("unchecked")
	public List<GSTR1ProductDataVO> getAgencyGSTR1DSInvoicesByDateRangeAndByProductType(long agencyId, long fromDate,
			long toDate, long productCode) throws BusinessException {

		List<GSTR1ProductDataVO> consolidatedList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			// Fetch DOM Sales Count
			Query<DOMSalesInvoiceDO> queryDOMSI = session
					.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 ");
			queryDOMSI.setParameter(1, agencyId);
			queryDOMSI.setParameter(2, 0);
			queryDOMSI.setParameter(3, fromDate);
			queryDOMSI.setParameter(4, toDate);
			List<DOMSalesInvoiceDO> resultDOMSI = queryDOMSI.getResultList();
			if (resultDOMSI.size() > 0) {
				for (DOMSalesInvoiceDO dataDO : resultDOMSI) {
					Query<DOMSalesInvoiceDetailsDO> squery = session
							.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, (int) productCode);
					List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (DOMSalesInvoiceDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(1);
							psvo.setTotal_quantity(detailsDO.getQuantity() - detailsDO.getPsv_cyls());
							float tax = (detailsDO.getQuantity() - detailsDO.getPsv_cyls())
									* (Float.parseFloat(detailsDO.getUnit_rate())
											- Float.parseFloat(detailsDO.getDisc_unit_rate()));
							String taxable = Float.toString(tax);
							psvo.setTaxable_value(taxable);
							psvo.setCgst_amount(detailsDO.getCgst_amount());
							psvo.setSgst_amount(detailsDO.getSgst_amount());
							psvo.setIgst_amount(detailsDO.getIgst_amount());
							float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
									+ Float.parseFloat(detailsDO.getSgst_amount())
									+ Float.parseFloat(detailsDO.getIgst_amount()));
							String total_value = Float.toString(t);
							psvo.setTotal_value(total_value);
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}
				}
			}

			// Fetch Purchase Returns
			Query<PurchaseReturnDO> queryPRSI = session
					.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
							+ " and inv_date between ?3 and ?4 ");
			queryPRSI.setParameter(1, agencyId);
			queryPRSI.setParameter(2, 0);
			queryPRSI.setParameter(3, fromDate);
			queryPRSI.setParameter(4, toDate);
			List<PurchaseReturnDO> resultPRSI = queryPRSI.getResultList();
			if (resultPRSI.size() > 0) {
				for (PurchaseReturnDO dataDO : resultPRSI) {
					Query<PurchaseReturnDetailsDO> squery = session
							.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, (int) productCode);
					List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (PurchaseReturnDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(1);
							psvo.setTotal_quantity(detailsDO.getRtn_qty());
							float tax = (detailsDO.getRtn_qty()) * (Float.parseFloat(detailsDO.getUnit_rate()));
							String taxable = Float.toString(tax);
							psvo.setTaxable_value(taxable);
							psvo.setCgst_amount(detailsDO.getCgst_amount());
							psvo.setSgst_amount(detailsDO.getSgst_amount());
							psvo.setIgst_amount(detailsDO.getIgst_amount());
							float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
									+ Float.parseFloat(detailsDO.getSgst_amount())
									+ Float.parseFloat(detailsDO.getIgst_amount()));
							String total_value = Float.toString(t);
							psvo.setTotal_value(total_value);
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}
				}
			}

			Query<RCDataDO> rcquery = session.createQuery("from RCDataDO where created_by = ?1 and deleted = ?2 "
					+ " and prod_code = ?5 and rc_date between ?3 and ?4 ");
			rcquery.setParameter(1, agencyId);
			rcquery.setParameter(2, 0);
			rcquery.setParameter(3, fromDate);
			rcquery.setParameter(4, toDate);
			rcquery.setParameter(5, (int) productCode);
			List<RCDataDO> rcresult = rcquery.getResultList();
			if (rcresult.size() > 0) {
				for (RCDataDO dataDO : rcresult) {
					Query<RefillPriceDataDO> rpquery = session.createQuery(
							"from RefillPriceDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
					rpquery.setParameter(1, agencyId);
					rpquery.setParameter(2, 0);
					rpquery.setParameter(3, (int) productCode);
					List<RefillPriceDataDO> rpresult = rpquery.getResultList();
					if (rpresult.size() > 0) {
						for (RefillPriceDataDO doObj : rpresult) {
							Query<EquipmentDataDO> equery = session.createQuery(
									"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
							equery.setParameter(1, agencyId);
							equery.setParameter(2, 0);
							equery.setParameter(3, (int) productCode);
							List<EquipmentDataDO> eresult = equery.getResultList();
							if (eresult.size() > 0) {
								for (EquipmentDataDO edoObj : eresult) {
									GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
									String bp = doObj.getBase_price();
									psvo.setProd_code(dataDO.getProd_code());
									psvo.setProd_type(1);
									psvo.setTotal_quantity(dataDO.getNo_of_cyl());
									float taxable = (Float.parseFloat(bp) * dataDO.getNo_of_cyl());
									psvo.setTaxable_value(Float.toString(taxable));
									float tax = (taxable * Float.parseFloat(edoObj.getGstp())) / 100;
									psvo.setIgst_amount("0");
									psvo.setCgst_amount(Float.toString(tax / 2));
									psvo.setSgst_amount(Float.toString(tax / 2));
									float t = (taxable + tax);
									String total_value = Float.toString(t);
									psvo.setTotal_value(total_value);
									psvo.setCreatedDate(dataDO.getCreated_date());
									consolidatedList.add(psvo);
								}
							}
						}
					}
				}
			}

			// Fetch NCDBC
			Query<NCDBCInvoiceDO> ncdbcquery = session.createQuery(
					"from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2  and inv_date between ?3 and ?4 ");
			ncdbcquery.setParameter(1, agencyId);
			ncdbcquery.setParameter(2, 0);
			ncdbcquery.setParameter(3, fromDate);
			ncdbcquery.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = ncdbcquery.getResultList();
			if (result.size() > 0) {
				for (NCDBCInvoiceDO dataDO : result) {
					Query<NCDBCInvoiceDetailsDO> squery = session
							.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, productCode);
					List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (NCDBCInvoiceDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(1);
							psvo.setTotal_quantity(detailsDO.getQuantity());
							float tax = (detailsDO.getQuantity()) * (Float.parseFloat(detailsDO.getUnit_rate())
									- Float.parseFloat(detailsDO.getDisc_unit_rate()));
							String taxable = Float.toString(tax);
							psvo.setTaxable_value(taxable);
							psvo.setCgst_amount(detailsDO.getCgst_amount());
							psvo.setSgst_amount(detailsDO.getSgst_amount());
							psvo.setIgst_amount("0");
							float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
									+ Float.parseFloat(detailsDO.getSgst_amount()));
							String total_value = Float.toString(t);
							psvo.setTotal_value(total_value);
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}

				}
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());

		} finally {
			session.clear();
			session.close();
		}
		return consolidatedList;
	}

/*		
//  For: OfferPrce - Dom is not calculated under Offerprice For Now - Will be used later. 
	// DOM Cyclinders gstr1 Report
	@SuppressWarnings("unchecked")
	public List<GSTR1ProductDataVO> getAgencyGSTR1DSInvoicesByDateRangeAndByProductType(long agencyId, long fromDate,
			long toDate, long productCode, int ofp_acceptable) throws BusinessException {

		List<GSTR1ProductDataVO> consolidatedList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			
			// Fetch DOM Sales Count
			Query<DOMSalesInvoiceDO> queryDOMSI = session
					.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 ");
			queryDOMSI.setParameter(1, agencyId);
			queryDOMSI.setParameter(2, 0);
			queryDOMSI.setParameter(3, fromDate);
			queryDOMSI.setParameter(4, toDate);
			List<DOMSalesInvoiceDO> resultDOMSI = queryDOMSI.getResultList();
			if (resultDOMSI.size() > 0) {
				for (DOMSalesInvoiceDO dataDO : resultDOMSI) {
					Query<DOMSalesInvoiceDetailsDO> squery = session
							.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, (int) productCode);
					List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						
//						for (DOMSalesInvoiceDetailsDO detailsDO : itemsResult) {
//							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
//							psvo.setProd_code(detailsDO.getProd_code());
//							psvo.setProd_type(1);
//							psvo.setTotal_quantity(detailsDO.getQuantity() - detailsDO.getPsv_cyls());
//							float tax = (detailsDO.getQuantity() - detailsDO.getPsv_cyls())
//									* (Float.parseFloat(detailsDO.getUnit_rate())
//											- Float.parseFloat(detailsDO.getDisc_unit_rate()));
//							String taxable = Float.toString(tax);
//							psvo.setTaxable_value(taxable);
//							psvo.setCgst_amount(detailsDO.getCgst_amount());
//							psvo.setSgst_amount(detailsDO.getSgst_amount());
//							psvo.setIgst_amount(detailsDO.getIgst_amount());
//							float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
//									+ Float.parseFloat(detailsDO.getSgst_amount())
//									+ Float.parseFloat(detailsDO.getIgst_amount()));
//							String total_value = Float.toString(t);
//							psvo.setTotal_value(total_value);
//							psvo.setCreatedDate(dataDO.getCreated_date());
//							consolidatedList.add(psvo);
//						}
						
						for (DOMSalesInvoiceDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(1);
							psvo.setTotal_quantity(detailsDO.getQuantity() - detailsDO.getPsv_cyls());
							
							if((ofp_acceptable == 0) || ((dataDO.getSr_no()).contains("SI")) ) {
								float tax = (detailsDO.getQuantity() - detailsDO.getPsv_cyls())
										* (Float.parseFloat(detailsDO.getUnit_rate())
												- Float.parseFloat(detailsDO.getDisc_unit_rate()));
								String taxable = Float.toString(tax);
								psvo.setTaxable_value(taxable);
								psvo.setCgst_amount(detailsDO.getCgst_amount());
								psvo.setSgst_amount(detailsDO.getSgst_amount());
								psvo.setIgst_amount(detailsDO.getIgst_amount());
								float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
											+ Float.parseFloat(detailsDO.getSgst_amount())
											+ Float.parseFloat(detailsDO.getIgst_amount()));
								String total_value = Float.toString(t);
								psvo.setTotal_value(total_value);
							}else if((ofp_acceptable == 1) && ((dataDO.getSr_no()).contains("CS"))){
								psvo.setTaxable_value(detailsDO.getOp_taxable_amt());
								psvo.setCgst_amount(detailsDO.getOp_cgst_amt());
								psvo.setSgst_amount(detailsDO.getOp_sgst_amt());
								psvo.setIgst_amount(detailsDO.getOp_igst_amt());
								psvo.setTotal_value(detailsDO.getOp_total_amt());
							}
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}
				}
			}

			// Fetch Purchase Returns
			Query<PurchaseReturnDO> queryPRSI = session
					.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
							+ " and inv_date between ?3 and ?4 ");
			queryPRSI.setParameter(1, agencyId);
			queryPRSI.setParameter(2, 0);
			queryPRSI.setParameter(3, fromDate);
			queryPRSI.setParameter(4, toDate);
			List<PurchaseReturnDO> resultPRSI = queryPRSI.getResultList();
			if (resultPRSI.size() > 0) {
				for (PurchaseReturnDO dataDO : resultPRSI) {
					Query<PurchaseReturnDetailsDO> squery = session
							.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, (int) productCode);
					List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (PurchaseReturnDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(1);
							psvo.setTotal_quantity(detailsDO.getRtn_qty());
							float tax = (detailsDO.getRtn_qty()) * (Float.parseFloat(detailsDO.getUnit_rate()));
							String taxable = Float.toString(tax);
							psvo.setTaxable_value(taxable);
							psvo.setCgst_amount(detailsDO.getCgst_amount());
							psvo.setSgst_amount(detailsDO.getSgst_amount());
							psvo.setIgst_amount(detailsDO.getIgst_amount());
							float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
									+ Float.parseFloat(detailsDO.getSgst_amount())
									+ Float.parseFloat(detailsDO.getIgst_amount()));
							String total_value = Float.toString(t);
							psvo.setTotal_value(total_value);
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}
				}
			}

			Query<RCDataDO> rcquery = session.createQuery("from RCDataDO where created_by = ?1 and deleted = ?2 "
					+ " and prod_code = ?5 and rc_date between ?3 and ?4 ");
			rcquery.setParameter(1, agencyId);
			rcquery.setParameter(2, 0);
			rcquery.setParameter(3, fromDate);
			rcquery.setParameter(4, toDate);
			rcquery.setParameter(5, (int) productCode);
			List<RCDataDO> rcresult = rcquery.getResultList();
			if (rcresult.size() > 0) {
				for (RCDataDO dataDO : rcresult) {
					Query<RefillPriceDataDO> rpquery = session.createQuery(
							"from RefillPriceDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
					rpquery.setParameter(1, agencyId);
					rpquery.setParameter(2, 0);
					rpquery.setParameter(3, (int) productCode);
					List<RefillPriceDataDO> rpresult = rpquery.getResultList();
					if (rpresult.size() > 0) {
						for (RefillPriceDataDO doObj : rpresult) {
							Query<EquipmentDataDO> equery = session.createQuery(
									"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
							equery.setParameter(1, agencyId);
							equery.setParameter(2, 0);
							equery.setParameter(3, (int) productCode);
							List<EquipmentDataDO> eresult = equery.getResultList();
							if (eresult.size() > 0) {
								for (EquipmentDataDO edoObj : eresult) {
									
									GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
									psvo.setProd_code(dataDO.getProd_code());
									psvo.setProd_type(1);
									psvo.setTotal_quantity(dataDO.getNo_of_cyl());

									String bp = doObj.getBase_price();
									
									if(ofp_acceptable == 0) {
										bp = doObj.getBase_price();
									}else if(ofp_acceptable == 1){
										bp = doObj.getOp_base_price();
									}
									
									float taxable = (Float.parseFloat(bp) * dataDO.getNo_of_cyl());
									psvo.setTaxable_value(Float.toString(taxable));
									float tax = (taxable * Float.parseFloat(edoObj.getGstp())) / 100;
									psvo.setIgst_amount("0");
									psvo.setCgst_amount(Float.toString(tax / 2));
									psvo.setSgst_amount(Float.toString(tax / 2));
									float t = (taxable + tax);
									String total_value = Float.toString(t);
									psvo.setTotal_value(total_value);
									
									psvo.setCreatedDate(dataDO.getCreated_date());
									consolidatedList.add(psvo);
									
								}
							}
						}
					}
				}
			}

			// Fetch NCDBC
			Query<NCDBCInvoiceDO> ncdbcquery = session.createQuery(
					"from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2  and inv_date between ?3 and ?4 ");
			ncdbcquery.setParameter(1, agencyId);
			ncdbcquery.setParameter(2, 0);
			ncdbcquery.setParameter(3, fromDate);
			ncdbcquery.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = ncdbcquery.getResultList();
			if (result.size() > 0) {
				for (NCDBCInvoiceDO dataDO : result) {
					Query<NCDBCInvoiceDetailsDO> squery = session
							.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, productCode);
					List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (NCDBCInvoiceDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(1);
							psvo.setTotal_quantity(detailsDO.getQuantity());
							psvo.setIgst_amount("0");
							
							if(ofp_acceptable == 0) {
								float tax = (detailsDO.getQuantity()) * (Float.parseFloat(detailsDO.getUnit_rate())
											- Float.parseFloat(detailsDO.getDisc_unit_rate()));
								String taxable = Float.toString(tax);
								psvo.setTaxable_value(taxable);
								psvo.setCgst_amount(detailsDO.getCgst_amount());
								psvo.setSgst_amount(detailsDO.getSgst_amount());
								
								float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
											+ Float.parseFloat(detailsDO.getSgst_amount()));
								String total_value = Float.toString(t);
								psvo.setTotal_value(total_value);
								
							}else if(ofp_acceptable == 1) {
								
								psvo.setTaxable_value(detailsDO.getOp_taxable_amt());
								psvo.setCgst_amount(detailsDO.getOp_cgst_amt());
								psvo.setSgst_amount(detailsDO.getOp_sgst_amt());
								psvo.setTotal_value(detailsDO.getOp_total_amt());
							}
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}

				}
			}

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());

		} finally {
			session.clear();
			session.close();
		}
		return consolidatedList;
	}
*/

/*	// COM Cyclinders gstr1 Report
	@SuppressWarnings("unchecked")
	public List<GSTR1ProductDataVO> getAgencyGSTR1CSInvoicesByDateRangeAndByProductType(long agencyId, long fromDate,
			long toDate, long productCode) throws BusinessException {

		List<GSTR1ProductDataVO> consolidatedList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			// Fetch COM Sales Count
			Query<COMSalesInvoiceDO> queryCOMSI = session.createQuery(
					"from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 and si_date between ?3 and ?4 ");
			queryCOMSI.setParameter(1, agencyId);
			queryCOMSI.setParameter(2, 0);
			queryCOMSI.setParameter(3, fromDate);
			queryCOMSI.setParameter(4, toDate);
			List<COMSalesInvoiceDO> resultCOMSI = queryCOMSI.getResultList();
			if (resultCOMSI.size() > 0) {
				for (COMSalesInvoiceDO dataDO : resultCOMSI) {
					Query<COMSalesInvoiceDetailsDO> squery = session
							.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, (int) productCode);
					List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						
						for (COMSalesInvoiceDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(1);
							psvo.setTotal_quantity(detailsDO.getQuantity() - detailsDO.getPre_cyls());
							
							if((dataDO.getSr_no()).contains("SI")) {
								float tax = (detailsDO.getQuantity() - detailsDO.getPre_cyls())
												* (Float.parseFloat(detailsDO.getUnit_rate())
												- Float.parseFloat(detailsDO.getDisc_unit_rate()));
								String taxable = Float.toString(tax);
								psvo.setTaxable_value(taxable);
								psvo.setCgst_amount(detailsDO.getCgst_amount());
								psvo.setSgst_amount(detailsDO.getSgst_amount());
								psvo.setIgst_amount(detailsDO.getIgst_amount());
								float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
											+ Float.parseFloat(detailsDO.getSgst_amount())
											+ Float.parseFloat(detailsDO.getIgst_amount()));
								String total_value = Float.toString(t);
								psvo.setTotal_value(total_value);
							}else if((dataDO.getSr_no()).contains("CS")) {
								psvo.setTaxable_value(detailsDO.getOp_taxable_amt());
								psvo.setCgst_amount(detailsDO.getOp_cgst_amt());
								psvo.setSgst_amount(detailsDO.getOp_sgst_amt());
								psvo.setIgst_amount(detailsDO.getOp_igst_amt());
								psvo.setTotal_value(detailsDO.getOp_total_amt());
							}
							
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}
				}
			}
			// Fetch Purchase Returns
			Query<PurchaseReturnDO> queryPRSI = session
					.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
							+ " and inv_date between ?3 and ?4 ");
			queryPRSI.setParameter(1, agencyId);
			queryPRSI.setParameter(2, 0);
			queryPRSI.setParameter(3, fromDate);
			queryPRSI.setParameter(4, toDate);
			List<PurchaseReturnDO> resultPRSI = queryPRSI.getResultList();
			if (resultPRSI.size() > 0) {
				for (PurchaseReturnDO dataDO : resultPRSI) {
					Query<PurchaseReturnDetailsDO> squery = session
							.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, (int) productCode);
					List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (PurchaseReturnDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(1);
							psvo.setTotal_quantity(detailsDO.getRtn_qty());

							float tax = (detailsDO.getRtn_qty()) * (Float.parseFloat(detailsDO.getUnit_rate()));

							String taxable = Float.toString(tax);
							psvo.setTaxable_value(taxable);
							psvo.setCgst_amount(detailsDO.getCgst_amount());
							psvo.setSgst_amount(detailsDO.getSgst_amount());
							psvo.setIgst_amount(detailsDO.getIgst_amount());
							float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
									+ Float.parseFloat(detailsDO.getSgst_amount())
									+ Float.parseFloat(detailsDO.getIgst_amount()));
							String total_value = Float.toString(t);
							psvo.setTotal_value(total_value);
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}
				}
			}
			// Fetch RC
			Query<RCDataDO> rcquery = session.createQuery("from RCDataDO where created_by = ?1 and deleted = ?2 "
					+ " and prod_code = ?5 and rc_date between ?3 and ?4 ");
			rcquery.setParameter(1, agencyId);
			rcquery.setParameter(2, 0);
			rcquery.setParameter(3, fromDate);
			rcquery.setParameter(4, toDate);
			rcquery.setParameter(5, (int) productCode);
			List<RCDataDO> rcresult = rcquery.getResultList();
			if (rcresult.size() > 0) {
				for (RCDataDO dataDO : rcresult) {
					Query<RefillPriceDataDO> rpquery = session.createQuery(
							"from RefillPriceDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
					rpquery.setParameter(1, agencyId);
					rpquery.setParameter(2, 0);
					rpquery.setParameter(3, (int) productCode);
					List<RefillPriceDataDO> rpresult = rpquery.getResultList();
					if (rpresult.size() > 0) {
						for (RefillPriceDataDO doObj : rpresult) {
							Query<EquipmentDataDO> equery = session.createQuery(
									"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
							equery.setParameter(1, agencyId);
							equery.setParameter(2, 0);
							equery.setParameter(3, (int) productCode);
							List<EquipmentDataDO> eresult = equery.getResultList();
							if (eresult.size() > 0) {
								for (EquipmentDataDO edoObj : eresult) {
									GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
//									String bp = doObj.getBase_price();
									String bp = doObj.getOp_base_price();
									psvo.setProd_code(dataDO.getProd_code());
									psvo.setProd_type(1);
									psvo.setTotal_quantity(dataDO.getNo_of_cyl());
									float taxable = (Float.parseFloat(bp) * dataDO.getNo_of_cyl());
									psvo.setTaxable_value(Float.toString(taxable));
									float tax = (taxable * Float.parseFloat(edoObj.getGstp())) / 100;
									psvo.setIgst_amount("0");
									psvo.setCgst_amount(Float.toString(tax / 2));
									psvo.setSgst_amount(Float.toString(tax / 2));
									float t = (taxable + tax);
									String total_value = Float.toString(t);
									psvo.setTotal_value(total_value);
									psvo.setCreatedDate(dataDO.getCreated_date());
									consolidatedList.add(psvo);
								}
							}
						}
					}
				}
			}
			// Fetch NCDBC
			Query<NCDBCInvoiceDO> ncdbcquery = session.createQuery(
					"from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 " + " and inv_date between ?3 and ?4 ");
			ncdbcquery.setParameter(1, agencyId);
			ncdbcquery.setParameter(2, 0);
			ncdbcquery.setParameter(3, fromDate);
			ncdbcquery.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = ncdbcquery.getResultList();
			if (result.size() > 0) {
				for (NCDBCInvoiceDO dataDO : result) {
					Query<NCDBCInvoiceDetailsDO> squery = session
							.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, productCode);
					List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (NCDBCInvoiceDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(1);
							psvo.setTotal_quantity(detailsDO.getQuantity());
//							float tax = (detailsDO.getQuantity()) * (Float.parseFloat(detailsDO.getUnit_rate())
//									- Float.parseFloat(detailsDO.getDisc_unit_rate()));
//							String taxable = Float.toString(tax);
							psvo.setTaxable_value(detailsDO.getOp_taxable_amt());
//							psvo.setCgst_amount(detailsDO.getCgst_amount());
//							psvo.setSgst_amount(detailsDO.getSgst_amount());
							psvo.setCgst_amount(detailsDO.getOp_cgst_amt());
							psvo.setSgst_amount(detailsDO.getOp_sgst_amt());
							psvo.setIgst_amount("0");
//							float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
//									+ Float.parseFloat(detailsDO.getSgst_amount()));
//							String total_value = Float.toString(t);
							psvo.setTotal_value(detailsDO.getOp_total_amt());
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return consolidatedList;
	}*/
	
	// COM Cyclinders gstr1 Report
	@SuppressWarnings("unchecked")
	public List<GSTR1ProductDataVO> getAgencyGSTR1CSInvoicesByDateRangeAndByProductType(long agencyId, long fromDate,
			long toDate, long productCode, int ofp_acceptable) throws BusinessException {

		List<GSTR1ProductDataVO> consolidatedList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			// Fetch COM Sales Count
			Query<COMSalesInvoiceDO> queryCOMSI = session.createQuery(
					"from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 and si_date between ?3 and ?4 ");
			queryCOMSI.setParameter(1, agencyId);
			queryCOMSI.setParameter(2, 0);
			queryCOMSI.setParameter(3, fromDate);
			queryCOMSI.setParameter(4, toDate);
			List<COMSalesInvoiceDO> resultCOMSI = queryCOMSI.getResultList();
			if (resultCOMSI.size() > 0) {
				for (COMSalesInvoiceDO dataDO : resultCOMSI) {
					Query<COMSalesInvoiceDetailsDO> squery = session
							.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, (int) productCode);
					List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						
						for (COMSalesInvoiceDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(1);
							psvo.setTotal_quantity(detailsDO.getQuantity() - detailsDO.getPre_cyls());
							
							if((ofp_acceptable == 0) || ((dataDO.getSr_no()).contains("SI"))) {
								float tax = (detailsDO.getQuantity() - detailsDO.getPre_cyls())
												* (Float.parseFloat(detailsDO.getUnit_rate())
												- Float.parseFloat(detailsDO.getDisc_unit_rate()));
								String taxable = Float.toString(tax);
								psvo.setTaxable_value(taxable);
								psvo.setCgst_amount(detailsDO.getCgst_amount());
								psvo.setSgst_amount(detailsDO.getSgst_amount());
								psvo.setIgst_amount(detailsDO.getIgst_amount());
								float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
											+ Float.parseFloat(detailsDO.getSgst_amount())
											+ Float.parseFloat(detailsDO.getIgst_amount()));
								String total_value = Float.toString(t);
								psvo.setTotal_value(total_value);
							}else if((ofp_acceptable == 1) && ((dataDO.getSr_no()).contains("CS"))) {
								psvo.setTaxable_value(detailsDO.getOp_taxable_amt());
								psvo.setCgst_amount(detailsDO.getOp_cgst_amt());
								psvo.setSgst_amount(detailsDO.getOp_sgst_amt());
								psvo.setIgst_amount(detailsDO.getOp_igst_amt());
								psvo.setTotal_value(detailsDO.getOp_total_amt());
							}							
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}
				}
			}
			// Fetch Purchase Returns
			Query<PurchaseReturnDO> queryPRSI = session
					.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
							+ " and inv_date between ?3 and ?4 ");
			queryPRSI.setParameter(1, agencyId);
			queryPRSI.setParameter(2, 0);
			queryPRSI.setParameter(3, fromDate);
			queryPRSI.setParameter(4, toDate);
			List<PurchaseReturnDO> resultPRSI = queryPRSI.getResultList();
			if (resultPRSI.size() > 0) {
				for (PurchaseReturnDO dataDO : resultPRSI) {
					Query<PurchaseReturnDetailsDO> squery = session
							.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, (int) productCode);
					List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (PurchaseReturnDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(1);
							psvo.setTotal_quantity(detailsDO.getRtn_qty());

							float tax = (detailsDO.getRtn_qty()) * (Float.parseFloat(detailsDO.getUnit_rate()));

							String taxable = Float.toString(tax);
							psvo.setTaxable_value(taxable);
							psvo.setCgst_amount(detailsDO.getCgst_amount());
							psvo.setSgst_amount(detailsDO.getSgst_amount());
							psvo.setIgst_amount(detailsDO.getIgst_amount());
							float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
									+ Float.parseFloat(detailsDO.getSgst_amount())
									+ Float.parseFloat(detailsDO.getIgst_amount()));
							String total_value = Float.toString(t);
							psvo.setTotal_value(total_value);
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}
				}
			}
			// Fetch RC
			Query<RCDataDO> rcquery = session.createQuery("from RCDataDO where created_by = ?1 and deleted = ?2 "
					+ " and prod_code = ?5 and rc_date between ?3 and ?4 ");
			rcquery.setParameter(1, agencyId);
			rcquery.setParameter(2, 0);
			rcquery.setParameter(3, fromDate);
			rcquery.setParameter(4, toDate);
			rcquery.setParameter(5, (int) productCode);
			List<RCDataDO> rcresult = rcquery.getResultList();
			if (rcresult.size() > 0) {
				for (RCDataDO dataDO : rcresult) {
					Query<RefillPriceDataDO> rpquery = session.createQuery(
							"from RefillPriceDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
					rpquery.setParameter(1, agencyId);
					rpquery.setParameter(2, 0);
					rpquery.setParameter(3, (int) productCode);
					List<RefillPriceDataDO> rpresult = rpquery.getResultList();
					if (rpresult.size() > 0) {
						for (RefillPriceDataDO doObj : rpresult) {
							Query<EquipmentDataDO> equery = session.createQuery(
									"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
							equery.setParameter(1, agencyId);
							equery.setParameter(2, 0);
							equery.setParameter(3, (int) productCode);
							List<EquipmentDataDO> eresult = equery.getResultList();
							if (eresult.size() > 0) {
								for (EquipmentDataDO edoObj : eresult) {
									GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();

									String bp = doObj.getBase_price();
									if(ofp_acceptable == 0){
										bp = doObj.getBase_price();
									}else if(ofp_acceptable == 1){
										bp = doObj.getOp_base_price();
									}
																		
									psvo.setProd_code(dataDO.getProd_code());
									psvo.setProd_type(1);
									psvo.setTotal_quantity(dataDO.getNo_of_cyl());
									float taxable = (Float.parseFloat(bp) * dataDO.getNo_of_cyl());
									psvo.setTaxable_value(Float.toString(taxable));
									float tax = (taxable * Float.parseFloat(edoObj.getGstp())) / 100;
									psvo.setIgst_amount("0");
									psvo.setCgst_amount(Float.toString(tax / 2));
									psvo.setSgst_amount(Float.toString(tax / 2));
									float t = (taxable + tax);
									String total_value = Float.toString(t);
									psvo.setTotal_value(total_value);
									psvo.setCreatedDate(dataDO.getCreated_date());
									consolidatedList.add(psvo);
								}
							}
						}
					}
				}
			}
			// Fetch NCDBC
			Query<NCDBCInvoiceDO> ncdbcquery = session.createQuery(
					"from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 " + " and inv_date between ?3 and ?4 ");
			ncdbcquery.setParameter(1, agencyId);
			ncdbcquery.setParameter(2, 0);
			ncdbcquery.setParameter(3, fromDate);
			ncdbcquery.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = ncdbcquery.getResultList();
			if (result.size() > 0) {
				for (NCDBCInvoiceDO dataDO : result) {
					Query<NCDBCInvoiceDetailsDO> squery = session
							.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, productCode);
					List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (NCDBCInvoiceDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(1);
							psvo.setTotal_quantity(detailsDO.getQuantity());
							psvo.setIgst_amount("0");
							
							if(ofp_acceptable == 0){
								float tax = (detailsDO.getQuantity()) * (Float.parseFloat(detailsDO.getUnit_rate())
										- Float.parseFloat(detailsDO.getDisc_unit_rate()));
								String taxable = Float.toString(tax);
								psvo.setTaxable_value(taxable);
								
								psvo.setCgst_amount(detailsDO.getCgst_amount());
								psvo.setSgst_amount(detailsDO.getSgst_amount());
								
								float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
												+ Float.parseFloat(detailsDO.getSgst_amount()));
								String total_value = Float.toString(t);
								psvo.setTotal_value(total_value);
							}else if(ofp_acceptable == 1){
								psvo.setTaxable_value(detailsDO.getOp_taxable_amt());
								psvo.setCgst_amount(detailsDO.getOp_cgst_amt());
								psvo.setSgst_amount(detailsDO.getOp_sgst_amt());
								psvo.setTotal_value(detailsDO.getOp_total_amt());
							}

							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return consolidatedList;
	}
	
	

	@SuppressWarnings("unchecked")
	public List<GSTR1ProductDataVO> getAgencyGSTR1RegInvoicesByDateRangeAndByProductType(long agencyId, long fromDate,
			long toDate, int productCode) throws BusinessException {

		List<GSTR1ProductDataVO> consolidatedList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {

			// Fetch NCDBC
			Query<NCDBCInvoiceDO> ncdbcquery = session.createQuery(
					"from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 " + " and inv_date between ?3 and ?4 ");
			ncdbcquery.setParameter(1, agencyId);
			ncdbcquery.setParameter(2, 0);
			ncdbcquery.setParameter(3, fromDate);
			ncdbcquery.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = ncdbcquery.getResultList();
			if (result.size() > 0) {
				for (NCDBCInvoiceDO dataDO : result) {
					Query<NCDBCInvoiceDetailsDO> squery = session
							.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, productCode);
					List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (NCDBCInvoiceDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(3);
							psvo.setTotal_quantity(detailsDO.getQuantity());
							float tax = (detailsDO.getQuantity()) * (Float.parseFloat(detailsDO.getDeposit_amount()));
							String taxable = Float.toString(tax);
							psvo.setTaxable_value(taxable);
							psvo.setCgst_amount(detailsDO.getCgst_amount());
							psvo.setSgst_amount(detailsDO.getSgst_amount());
							psvo.setIgst_amount("0");
							float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
									+ Float.parseFloat(detailsDO.getSgst_amount()));
							String total_value = Float.toString(t);
							psvo.setTotal_value(total_value);
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return consolidatedList;
	}

	@SuppressWarnings("unchecked")
	public List<GSTR1ProductDataVO> getAgencyGSTR1ServicesInvoicesByDateRangeAndByProductType(long agencyId,
			long fromDate, long toDate, long productCode) throws BusinessException {

		List<GSTR1ProductDataVO> consolidatedList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {

			// Fetch NCDBC
			Query<NCDBCInvoiceDO> ncdbcquery = session.createQuery(
					"from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 " + " and inv_date between ?3 and ?4 ");
			ncdbcquery.setParameter(1, agencyId);
			ncdbcquery.setParameter(2, 0);
			ncdbcquery.setParameter(3, fromDate);
			ncdbcquery.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = ncdbcquery.getResultList();
			if (result.size() > 0) {
				for (NCDBCInvoiceDO dataDO : result) {
					Query<NCDBCInvoiceDetailsDO> squery = session
							.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, productCode);
					List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (NCDBCInvoiceDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(4);
							psvo.setTotal_quantity(detailsDO.getQuantity());
							float tax = (detailsDO.getQuantity()) * (Float.parseFloat(detailsDO.getUnit_rate())
									- Float.parseFloat(detailsDO.getDisc_unit_rate()));
							String taxable = Float.toString(tax);
							psvo.setTaxable_value(taxable);
							psvo.setCgst_amount(detailsDO.getCgst_amount());
							psvo.setSgst_amount(detailsDO.getSgst_amount());
							psvo.setIgst_amount("0");
							float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
									+ Float.parseFloat(detailsDO.getSgst_amount()));
							String total_value = Float.toString(t);
							psvo.setTotal_value(total_value);
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}

				}
			}
			if (productCode == 23) {
				Query<RCDataDO> rcquery = session.createQuery(
						"from RCDataDO where created_by = ?1 and deleted = ?2 " + " and rc_date between ?3 and ?4 ");
				rcquery.setParameter(1, agencyId);
				rcquery.setParameter(2, 0);
				rcquery.setParameter(3, fromDate);
				rcquery.setParameter(4, toDate);
				List<RCDataDO> rcresult = rcquery.getResultList();
				if (rcresult.size() > 0) {
					for (RCDataDO dataDO : rcresult) {
						GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
						psvo.setProd_code(23);
						psvo.setProd_type(4);
						psvo.setTotal_quantity(dataDO.getNo_of_cyl());
						float taxable = Float.parseFloat(dataDO.getDgcc_amount())
								+ Float.parseFloat(dataDO.getAdmin_charges());
						psvo.setTaxable_value(Float.toString(taxable));
						float tax = (taxable * 18) / 100;
						psvo.setIgst_amount("0");
						psvo.setCgst_amount(Float.toString(tax / 2));
						psvo.setSgst_amount(Float.toString(tax / 2));
						float t = (taxable + tax);
						String total_value = Float.toString(t);
						psvo.setTotal_value(total_value);
						psvo.setCreatedDate(dataDO.getCreated_date());
						consolidatedList.add(psvo);
					}
				}
			}
			if ((productCode != 23) && (productCode == 20 || productCode == 28 || productCode == 29)) {
				// Fetch TV
				Query<TVDataDO> tvquery = session.createQuery(
						"from TVDataDO where created_by = ?1 and deleted = ?2 " + " and tv_date between ?3 and ?4 ");
				tvquery.setParameter(1, agencyId);
				tvquery.setParameter(2, 0);
				tvquery.setParameter(3, fromDate);
				tvquery.setParameter(4, toDate);
				List<TVDataDO> tvresult = tvquery.getResultList();
				if (tvresult.size() > 0) {
					for (TVDataDO dataDO : tvresult) {
						if (dataDO.getTv_charges_type() == productCode) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							// psvo.setProd_code(20);
							psvo.setProd_code(dataDO.getTv_charges_type());
							psvo.setProd_type(4);
							psvo.setTotal_quantity(dataDO.getNo_of_cyl());
							String taxable = dataDO.getAdmin_charges();
							psvo.setTaxable_value(taxable);
							float tax = (Float.parseFloat(taxable) * 18) / 100;
							psvo.setIgst_amount("0");
							psvo.setCgst_amount(Float.toString(tax / 2));
							psvo.setSgst_amount(Float.toString(tax / 2));
							float t = (Float.parseFloat(taxable) + tax);
							String total_value = Float.toString(t);
							psvo.setTotal_value(total_value);
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return consolidatedList;
	}

	@SuppressWarnings("unchecked")
	public List<GSTR1ProductDataVO> getAgencyGSTR1ASInvoicesByDateRangeAndByProductType(long agencyId, long fromDate,
			long toDate, long productCode, int ofp_acceptable) throws BusinessException {

		List<GSTR1ProductDataVO> consolidatedList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			// Fetch ARB Sales Count
			Query<ARBSalesInvoiceDO> queryARBSI = session
					.createQuery("from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and si_date between ?3 and ?4 ");
			queryARBSI.setParameter(1, agencyId);
			queryARBSI.setParameter(2, 0);
			queryARBSI.setParameter(3, fromDate);
			queryARBSI.setParameter(4, toDate);
			List<ARBSalesInvoiceDO> resultARBSI = queryARBSI.getResultList();
			if (resultARBSI.size() > 0) {
				for (ARBSalesInvoiceDO dataDO : resultARBSI) {
					Query<ARBSalesInvoiceDetailsDO> squery = session
							.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, productCode);
					List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (ARBSalesInvoiceDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(2);
							psvo.setTotal_quantity(detailsDO.getQuantity());
							
							if((ofp_acceptable == 0) || (dataDO.getSr_no().contains("SI"))){
								float tax = (detailsDO.getQuantity()) * (Float.parseFloat(detailsDO.getUnit_rate())
											- Float.parseFloat(detailsDO.getDisc_unit_rate()));
								String taxable = Float.toString(tax);
								psvo.setTaxable_value(taxable);
								psvo.setCgst_amount(detailsDO.getCgst_amount());
								psvo.setSgst_amount(detailsDO.getSgst_amount());
								psvo.setIgst_amount(detailsDO.getIgst_amount());
								float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
											+ Float.parseFloat(detailsDO.getSgst_amount())
											+ Float.parseFloat(detailsDO.getIgst_amount()));
								String total_value = Float.toString(t);
								psvo.setTotal_value(total_value);
							}else if((ofp_acceptable == 1) && (dataDO.getSr_no().contains("CS"))){
								psvo.setTaxable_value(detailsDO.getOp_taxable_amt());
								psvo.setCgst_amount(detailsDO.getOp_cgst_amt());
								psvo.setSgst_amount(detailsDO.getOp_sgst_amt());
								psvo.setIgst_amount(detailsDO.getOp_igst_amt());
								psvo.setTotal_value(detailsDO.getOp_total_amt());
							}
							
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}
				}
			}

			// Fetch Purchase Returns
			Query<PurchaseReturnDO> queryPRSI = session
					.createQuery("from PurchaseReturnDO where created_by = ?1 and deleted = ?2 "
							+ " and inv_date between ?3 and ?4 ");
			queryPRSI.setParameter(1, agencyId);
			queryPRSI.setParameter(2, 0);
			queryPRSI.setParameter(3, fromDate);
			queryPRSI.setParameter(4, toDate);
			List<PurchaseReturnDO> resultPRSI = queryPRSI.getResultList();
			if (resultPRSI.size() > 0) {
				for (PurchaseReturnDO dataDO : resultPRSI) {
					Query<PurchaseReturnDetailsDO> squery = session
							.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, (int) productCode);
					List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (PurchaseReturnDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(2);
							psvo.setTotal_quantity(detailsDO.getRtn_qty());
							float tax = (detailsDO.getRtn_qty()) * (Float.parseFloat(detailsDO.getUnit_rate()));
							String taxable = Float.toString(tax);
							psvo.setTaxable_value(taxable);
							psvo.setCgst_amount(detailsDO.getCgst_amount());
							psvo.setSgst_amount(detailsDO.getSgst_amount());
							psvo.setIgst_amount(detailsDO.getIgst_amount());
							float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
									+ Float.parseFloat(detailsDO.getSgst_amount())
									+ Float.parseFloat(detailsDO.getIgst_amount()));
							String total_value = Float.toString(t);
							psvo.setTotal_value(total_value);
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}
				}
			}

			// Fetch NCDBC
			Query<NCDBCInvoiceDO> ncdbcquery = session.createQuery(
					"from NCDBCInvoiceDO where created_by = ?1 and deleted = ?2 " + " and inv_date between ?3 and ?4 ");
			ncdbcquery.setParameter(1, agencyId);
			ncdbcquery.setParameter(2, 0);
			ncdbcquery.setParameter(3, fromDate);
			ncdbcquery.setParameter(4, toDate);
			List<NCDBCInvoiceDO> result = ncdbcquery.getResultList();
			if (result.size() > 0) {
				for (NCDBCInvoiceDO dataDO : result) {
					Query<NCDBCInvoiceDetailsDO> squery = session
							.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1 and prod_code = ?2");
					squery.setParameter(1, dataDO.getId());
					squery.setParameter(2, productCode);
					List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						for (NCDBCInvoiceDetailsDO detailsDO : itemsResult) {
							GSTR1ProductDataVO psvo = new GSTR1ProductDataVO();
							psvo.setProd_code(detailsDO.getProd_code());
							psvo.setProd_type(2);
							psvo.setTotal_quantity(detailsDO.getQuantity());
							psvo.setIgst_amount("0");
							
							if(ofp_acceptable == 0){
								float tax = (detailsDO.getQuantity()) * (Float.parseFloat(detailsDO.getUnit_rate())
												- Float.parseFloat(detailsDO.getDisc_unit_rate()));
								String taxable = Float.toString(tax);
								psvo.setTaxable_value(taxable);
								psvo.setCgst_amount(detailsDO.getCgst_amount());
								psvo.setSgst_amount(detailsDO.getSgst_amount());
								
								float t = (Float.parseFloat(taxable) + Float.parseFloat(detailsDO.getCgst_amount())
												+ Float.parseFloat(detailsDO.getSgst_amount()));
								String total_value = Float.toString(t);
								psvo.setTotal_value(total_value);
							}else if(ofp_acceptable == 1) {
								psvo.setTaxable_value(detailsDO.getOp_taxable_amt());
								psvo.setCgst_amount(detailsDO.getOp_cgst_amt());
								psvo.setSgst_amount(detailsDO.getOp_sgst_amt());
								psvo.setTotal_value(detailsDO.getOp_total_amt());
							}
														
							psvo.setCreatedDate(dataDO.getCreated_date());
							consolidatedList.add(psvo);
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return consolidatedList;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public GSTR1DocsDataDO getAgencyGSTR1B2BSalesDocsData(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		GSTR1DocsDataDO psvo = new GSTR1DocsDataDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		long total_no = 0;
		long cancelled = 0;
		String sr_no_from = "";
		String sr_no_to = "";
		List<Integer> maxarray = new ArrayList<>();
		List<Integer> minarray = new ArrayList<>();
		try {
			// Fetch DOM Sales Count
			Query<Long> query = session.createQuery(
					"SELECT count(DISTINCT dsid.dsi_id) FROM DOMSalesInvoiceDO dsi,DOMSalesInvoiceDetailsDO dsid WHERE dsi.created_by = ?1 and dsi.id = dsid.dsi_id and deleted = ?2"
							+ " and si_date between ?3 and ?4 and sr_no like 'SI%'");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<Long> result = query.list();

			total_no = total_no + result.get(0);
			if (result.get(0) > 0) {

				// Fetch SR_NO_TO
				Query<String> query5 = session.createQuery(
						"SELECT sr_no FROM DOMSalesInvoiceDO where created_by = ?1 AND deleted =?2 and si_date between ?3 and ?4 and sr_no like 'SI%' order by created_date ")
						.setMaxResults(1);
				query5.setParameter(1, agencyId);
				query5.setParameter(2, 0);
				query5.setParameter(3, fromDate);
				query5.setParameter(4, toDate);
				List<String> result5 = query5.list();

				if (result5.get(0) != null) {
					minarray.add(Integer.parseInt(result5.get(0).substring(6)));
				}

				// Fetch SR_NO_TO
				Query<String> query9 = session.createQuery(
						"SELECT sr_no FROM DOMSalesInvoiceDO where created_by = ?1 AND deleted =?2 and si_date between ?3 and ?4 and sr_no like 'SI%' order by created_date desc")
						.setMaxResults(1);
				query9.setParameter(1, agencyId);
				query9.setParameter(2, 0);
				query9.setParameter(3, fromDate);
				query9.setParameter(4, toDate);
				List<String> result9 = query9.list();

				if (result9.get(0) != null)
					maxarray.add(Integer.parseInt(result9.get(0).substring(6)));

			}

			// Fetch DOM cancelled Count
			Query<Long> query11 = session.createQuery(
					"SELECT count(DISTINCT dsid.dsi_id) FROM DOMSalesInvoiceDO dsi,DOMSalesInvoiceDetailsDO dsid WHERE dsi.created_by = ?1 and dsi.id = dsid.dsi_id and "
							+ "(deleted = ?2 or deleted = ?5) and si_date between ?3 and ?4 and sr_no like 'SI%' ");
			query11.setParameter(1, agencyId);
			query11.setParameter(2, 2);
			query11.setParameter(3, fromDate);
			query11.setParameter(4, toDate);
			query11.setParameter(5, 3);
			List<Long> result11 = query11.list();

			cancelled = cancelled + result11.get(0);

			// Fetch COM Sales Count
			Query<Long> query2 = session.createQuery(
					"SELECT count(DISTINCT csid.csi_id) FROM COMSalesInvoiceDO csi,COMSalesInvoiceDetailsDO csid WHERE csi.created_by = ?1 and csi.id = csid.csi_id and deleted = ?2"
							+ " and si_date between ?3 and ?4 and sr_no like 'SI%'");
			query2.setParameter(1, agencyId);
			query2.setParameter(2, 0);
			query2.setParameter(3, fromDate);
			query2.setParameter(4, toDate);
			List<Long> result2 = query2.list();

			total_no = total_no + result2.get(0);

			if (result2.get(0) > 0) {

				// Fetch SR_NO_TO
				Query<String> query6 = session.createQuery(
						"SELECT sr_no FROM COMSalesInvoiceDO where  created_by = ?1 AND deleted =?2 and si_date between ?3 and ?4 and sr_no like 'SI%' order by created_date")
						.setMaxResults(1);
				query6.setParameter(1, agencyId);
				query6.setParameter(2, 0);
				query6.setParameter(3, fromDate);
				query6.setParameter(4, toDate);
				List<String> result6 = query6.list();

				if (result6.get(0) != null)
					minarray.add(Integer.parseInt(result6.get(0).substring(6)));

				// Fetch SR_NO_TO
				Query<String> query10 = session.createQuery(
						"SELECT sr_no FROM COMSalesInvoiceDO where  created_by = ?1 AND deleted =?2 and si_date between ?3 and ?4 and sr_no like 'SI%' order by created_date desc")
						.setMaxResults(1);
				query10.setParameter(1, agencyId);
				query10.setParameter(2, 0);
				query10.setParameter(3, fromDate);
				query10.setParameter(4, toDate);
				List<String> result10 = query10.list();

				if (result10.get(0) != null)
					maxarray.add(Integer.parseInt(result10.get(0).substring(6)));

			}

			// Fetch COM cancelled Count
			Query<Long> query22 = session.createQuery(
					"SELECT count(DISTINCT csid.csi_id) FROM COMSalesInvoiceDO csi,COMSalesInvoiceDetailsDO csid WHERE csi.created_by = ?1 and csi.id = csid.csi_id and "
							+ "(deleted = ?2 or deleted = ?5) and si_date between ?3 and ?4 and sr_no like 'SI%'");
			query22.setParameter(1, agencyId);
			query22.setParameter(2, 2);
			query22.setParameter(3, fromDate);
			query22.setParameter(4, toDate);
			query22.setParameter(5, 3);
			List<Long> result22 = query22.list();

			cancelled = cancelled + result22.get(0);

			// Fetch ARB Sales Count
			Query<Long> query3 = session.createQuery(
					"SELECT count(DISTINCT arbsid.arbsi_id) FROM ARBSalesInvoiceDO arbsi,ARBSalesInvoiceDetailsDO arbsid WHERE arbsi.created_by = ?1 and arbsi.id = arbsid.arbsi_id and deleted = ?2"
							+ " and si_date between ?3 and ?4 and sr_no like 'SI%'");
			query3.setParameter(1, agencyId);
			query3.setParameter(2, 0);
			query3.setParameter(3, fromDate);
			query3.setParameter(4, toDate);
			List<Long> result3 = query3.list();

			total_no = total_no + result3.get(0);
			if (result3.get(0) > 0) {

				// Fetch SR_NO_TO
				Query<String> query7 = session.createQuery(
						"SELECT sr_no FROM ARBSalesInvoiceDO where  created_by = ?1 AND deleted =?2 and si_date between ?3 and ?4 and sr_no like 'SI%' order by created_date")
						.setMaxResults(1);
				query7.setParameter(1, agencyId);
				query7.setParameter(2, 0);
				query7.setParameter(3, fromDate);
				query7.setParameter(4, toDate);
				List<String> result7 = query7.list();

				if (result7.get(0) != null)
					minarray.add(Integer.parseInt(result7.get(0).substring(6)));
				// Fetch SR_NO_TO
				Query<String> query12 = session.createQuery(
						"SELECT sr_no FROM ARBSalesInvoiceDO where  created_by = ?1 AND deleted =?2 and si_date between ?3 and ?4 and sr_no like 'SI%' order by created_date desc")
						.setMaxResults(1);
				query12.setParameter(1, agencyId);
				query12.setParameter(2, 0);
				query12.setParameter(3, fromDate);
				query12.setParameter(4, toDate);
				List<String> result12 = query12.list();

				if (result12.get(0) != null)
					maxarray.add(Integer.parseInt(result12.get(0).substring(6)));

			}
			// Fetch ARB cancelled Count
			Query<Long> query33 = session.createQuery(
					"SELECT count(DISTINCT arbsid.arbsi_id) FROM ARBSalesInvoiceDO arbsi,ARBSalesInvoiceDetailsDO arbsid WHERE arbsi.created_by = ?1 and arbsi.id = arbsid.arbsi_id and"
							+ "(deleted = ?2 or deleted = ?5) and si_date between ?3 and ?4 and sr_no like 'SI%'");
			query33.setParameter(1, agencyId);
			query33.setParameter(2, 2);
			query33.setParameter(3, fromDate);
			query33.setParameter(4, toDate);
			query33.setParameter(5, 3);
			List<Long> result33 = query33.list();

			cancelled = cancelled + result33.get(0);

			if (total_no > 0) {
				int from_no = getMinValue(minarray);
				int to_no = getMaxValue(maxarray);
				if (from_no < 10)
					sr_no_from = "SI-" + cfy + "-000" + from_no;
				else if (from_no < 100)
					sr_no_from = "SI-" + cfy + "-00" + from_no;
				else if (from_no < 1000)
					sr_no_from = "SI-" + cfy + "-0" + from_no;
				else
					sr_no_from = "SI-" + cfy + "-" + from_no;

				if (from_no < 10)
					sr_no_to = "SI-" + cfy + "-000" + to_no;
				else if (from_no < 100)
					sr_no_to = "SI-" + cfy + "-00" + to_no;
				else if (from_no < 1000)
					sr_no_to = "SI-" + cfy + "-0" + to_no;
				else
					sr_no_to = "SI-" + cfy + "-" + to_no;
			}
			psvo.setTotal_no(total_no);
			psvo.setCancelled(cancelled);
			psvo.setSr_no_from(sr_no_from);
			psvo.setSr_no_to(sr_no_to);
			psvo.setDoc_type(1);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return psvo;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public GSTR1DocsDataDO getAgencyGSTR1B2CSalesDocsData(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		GSTR1DocsDataDO psvo = new GSTR1DocsDataDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		long total_no = 0;
		long cancelled = 0;
		String sr_no_from = "";
		String sr_no_to = "";
		List<Integer> maxarray = new ArrayList<>();
		List<Integer> minarray = new ArrayList<>();
		try {
			// Fetch DOM Sales Count
			Query<Long> query = session.createQuery(
					"SELECT count(DISTINCT dsid.dsi_id) FROM DOMSalesInvoiceDO dsi,DOMSalesInvoiceDetailsDO dsid WHERE dsi.created_by = ?1 and dsi.id = dsid.dsi_id and deleted = ?2"
							+ " and si_date between ?3 and ?4 and sr_no like 'CS%'");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<Long> result = query.list();

			total_no = total_no + result.get(0);
			if (result.get(0) > 0) {

				// Fetch SR_NO_TO
				Query<String> query5 = session.createQuery(
						"SELECT sr_no FROM DOMSalesInvoiceDO where created_by = ?1 AND deleted =?2 and si_date between ?3 and ?4 and sr_no like 'CS%' order by created_date")
						.setMaxResults(1);
				query5.setParameter(1, agencyId);
				query5.setParameter(2, 0);
				query5.setParameter(3, fromDate);
				query5.setParameter(4, toDate);
				List<String> result5 = query5.list();

				if (result5.get(0) != null) {
					minarray.add(Integer.parseInt(result5.get(0).substring(6)));
				}

				// Fetch SR_NO_TO
				Query<String> query9 = session.createQuery(
						"SELECT sr_no FROM DOMSalesInvoiceDO where created_by = ?1 AND deleted =?2 and si_date between ?3 and ?4 and sr_no like 'CS%' order by created_date desc")
						.setMaxResults(1);
				query9.setParameter(1, agencyId);
				query9.setParameter(2, 0);
				query9.setParameter(3, fromDate);
				query9.setParameter(4, toDate);
				List<String> result9 = query9.list();

				if (result9.get(0) != null)
					maxarray.add(Integer.parseInt(result9.get(0).substring(6)));

			}

			// Fetch DOM cancelled Count
			Query<Long> query11 = session.createQuery(
					"SELECT count(DISTINCT dsid.dsi_id) FROM DOMSalesInvoiceDO dsi,DOMSalesInvoiceDetailsDO dsid WHERE dsi.created_by = ?1 and dsi.id = dsid.dsi_id and "
							+ "(deleted = ?2 or deleted = ?5) and si_date between ?3 and ?4 and sr_no like 'CS%'");
			query11.setParameter(1, agencyId);
			query11.setParameter(2, 2);
			query11.setParameter(3, fromDate);
			query11.setParameter(4, toDate);
			query11.setParameter(5, 3);
			List<Long> result11 = query11.list();

			cancelled = cancelled + result11.get(0);

			// Fetch COM Sales Count
			Query<Long> query2 = session.createQuery(
					"SELECT count(DISTINCT csid.csi_id) FROM COMSalesInvoiceDO csi,COMSalesInvoiceDetailsDO csid WHERE csi.created_by = ?1 and csi.id = csid.csi_id and deleted = ?2"
							+ " and si_date between ?3 and ?4 and sr_no like 'CS%'");
			query2.setParameter(1, agencyId);
			query2.setParameter(2, 0);
			query2.setParameter(3, fromDate);
			query2.setParameter(4, toDate);
			List<Long> result2 = query2.list();

			total_no = total_no + result2.get(0);

			if (result2.get(0) > 0) {
				// Fetch SR_NO_TO
				Query<String> query6 = session.createQuery(
						"SELECT sr_no FROM COMSalesInvoiceDO where  created_by = ?1 AND deleted =?2 and si_date between ?3 and ?4 and sr_no like 'CS%' order by created_date")
						.setMaxResults(1);
				query6.setParameter(1, agencyId);
				query6.setParameter(2, 0);
				query6.setParameter(3, fromDate);
				query6.setParameter(4, toDate);
				List<String> result6 = query6.list();

				if (result6.get(0) != null)
					minarray.add(Integer.parseInt(result6.get(0).substring(6)));

				// Fetch SR_NO_TO
				Query<String> query10 = session.createQuery(
						"SELECT sr_no FROM COMSalesInvoiceDO where  created_by = ?1 AND deleted =?2 and si_date between ?3 and ?4 and sr_no like 'CS%' order by created_date desc")
						.setMaxResults(1);
				query10.setParameter(1, agencyId);
				query10.setParameter(2, 0);
				query10.setParameter(3, fromDate);
				query10.setParameter(4, toDate);
				List<String> result10 = query10.list();

				if (result10.get(0) != null)
					maxarray.add(Integer.parseInt(result10.get(0).substring(6)));

			}

			// Fetch COM cancelled Count
			Query<Long> query22 = session.createQuery(
					"SELECT count(DISTINCT csid.csi_id) FROM COMSalesInvoiceDO csi,COMSalesInvoiceDetailsDO csid WHERE csi.created_by = ?1 and csi.id = csid.csi_id and "
							+ "(deleted = ?2 or deleted = ?5) and si_date between ?3 and ?4 and sr_no like 'CS%'");
			query22.setParameter(1, agencyId);
			query22.setParameter(2, 2);
			query22.setParameter(3, fromDate);
			query22.setParameter(4, toDate);
			query22.setParameter(5, 3);
			List<Long> result22 = query22.list();

			cancelled = cancelled + result22.get(0);

			// Fetch ARB Sales Count
			Query<Long> query3 = session.createQuery(
					"SELECT count(DISTINCT arbsid.arbsi_id) FROM ARBSalesInvoiceDO arbsi,ARBSalesInvoiceDetailsDO arbsid WHERE arbsi.created_by = ?1 and arbsi.id = arbsid.arbsi_id and deleted = ?2"
							+ " and si_date between ?3 and ?4 and sr_no like 'CS%'");
			query3.setParameter(1, agencyId);
			query3.setParameter(2, 0);
			query3.setParameter(3, fromDate);
			query3.setParameter(4, toDate);
			List<Long> result3 = query3.list();

			total_no = total_no + result3.get(0);
			if (result3.get(0) > 0) {

				// Fetch SR_NO_TO
				Query<String> query7 = session.createQuery(
						"SELECT sr_no FROM ARBSalesInvoiceDO where  created_by = ?1 AND deleted =?2 and si_date between ?3 and ?4 and sr_no like 'CS%' order by created_date")
						.setMaxResults(1);
				query7.setParameter(1, agencyId);
				query7.setParameter(2, 0);
				query7.setParameter(3, fromDate);
				query7.setParameter(4, toDate);
				List<String> result7 = query7.list();

				if (result7.get(0) != null)
					minarray.add(Integer.parseInt(result7.get(0).substring(6)));
				// Fetch SR_NO_TO
				Query<String> query12 = session.createQuery(
						"SELECT sr_no FROM ARBSalesInvoiceDO where  created_by = ?1 AND deleted =?2 and si_date between ?3 and ?4 and sr_no like 'CS%' order by created_date desc")
						.setMaxResults(1);
				query12.setParameter(1, agencyId);
				query12.setParameter(2, 0);
				query12.setParameter(3, fromDate);
				query12.setParameter(4, toDate);
				List<String> result12 = query12.list();

				if (result12.get(0) != null)
					maxarray.add(Integer.parseInt(result12.get(0).substring(6)));

			}
			// Fetch ARB cancelled Count
			Query<Long> query33 = session.createQuery(
					"SELECT count(DISTINCT arbsid.arbsi_id) FROM ARBSalesInvoiceDO arbsi,ARBSalesInvoiceDetailsDO arbsid WHERE arbsi.created_by = ?1 and arbsi.id = arbsid.arbsi_id and "
							+ "(deleted = ?2 or deleted = ?5) and si_date between ?3 and ?4 and sr_no like 'CS%'");
			query33.setParameter(1, agencyId);
			query33.setParameter(2, 2);
			query33.setParameter(3, fromDate);
			query33.setParameter(4, toDate);
			query33.setParameter(5, 3);
			List<Long> result33 = query33.list();

			cancelled = cancelled + result33.get(0);

			// Fetch NCDBC Sales Count
			Query<Long> query4 = session.createQuery(
					"SELECT count(DISTINCT ncdbcid.inv_id) FROM NCDBCInvoiceDO ncdbci,NCDBCInvoiceDetailsDO ncdbcid WHERE ncdbci.created_by = ?1 and ncdbci.id = ncdbcid.inv_id and deleted = ?2"
							+ " and inv_date between ?3 and ?4 and sr_no like 'CS%'");
			query4.setParameter(1, agencyId);
			query4.setParameter(2, 0);
			query4.setParameter(3, fromDate);
			query4.setParameter(4, toDate);
			List<Long> result4 = query4.list();

			total_no = total_no + result4.get(0);

			if (result4.get(0) > 0) {

				// Fetch SR_NO_TO
				Query<String> query8 = session.createQuery(
						"SELECT sr_no FROM NCDBCInvoiceDO where  created_by = ?1 AND deleted =?2 and inv_date between ?3 and ?4 and sr_no like 'CS%' order by created_date")
						.setMaxResults(1);
				query8.setParameter(1, agencyId);
				query8.setParameter(2, 0);
				query8.setParameter(3, fromDate);
				query8.setParameter(4, toDate);
				List<String> result8 = query8.list();
				if (result8.get(0) != null)
					minarray.add(Integer.parseInt(result8.get(0).substring(6)));

				// Fetch SR_NO_TO
				Query<String> query13 = session.createQuery(
						"SELECT sr_no FROM NCDBCInvoiceDO where  created_by = ?1 AND deleted =?2 and inv_date between ?3 and ?4 and sr_no like 'CS%' order by created_date desc")
						.setMaxResults(1);
				query13.setParameter(1, agencyId);
				query13.setParameter(2, 0);
				query13.setParameter(3, fromDate);
				query13.setParameter(4, toDate);
				List<String> result13 = query13.list();

				if (result13.get(0) != null)
					maxarray.add(Integer.parseInt(result13.get(0).substring(6)));

			}

			// Fetch NCDBC cancelled Count
			Query<Long> query44 = session.createQuery(
					"SELECT count(DISTINCT ncdbcid.inv_id) FROM NCDBCInvoiceDO ncdbci,NCDBCInvoiceDetailsDO ncdbcid WHERE ncdbci.created_by = ?1 and ncdbci.id = ncdbcid.inv_id and "
							+ "(deleted = ?2 or deleted = ?5) and inv_date between ?3 and ?4 and sr_no like 'CS%'");
			query44.setParameter(1, agencyId);
			query44.setParameter(2, 2);
			query44.setParameter(3, fromDate);
			query44.setParameter(4, toDate);
			query44.setParameter(5, 3);
			List<Long> result44 = query44.list();

			cancelled = cancelled + result44.get(0);

			// Fetch RC Count
			Query<Long> query5 = session
					.createQuery("SELECT count(DISTINCT id) from RCDataDO where created_by = ?1 and deleted = ?2"
							+ " and rc_date between ?3 and ?4 and sr_no like 'CS%'");
			query5.setParameter(1, agencyId);
			query5.setParameter(2, 0);
			query5.setParameter(3, fromDate);
			query5.setParameter(4, toDate);
			List<Long> result5 = query5.list();

			total_no = total_no + result5.get(0);

			if (result5.get(0) > 0) {

				// Fetch SR_NO_TO
				Query<String> query8 = session.createQuery(
						"SELECT sr_no from RCDataDO where created_by = ?1 and deleted = ?2 and rc_date between ?3 and ?4 and sr_no like 'CS%' order by created_date")
						.setMaxResults(1);
				query8.setParameter(1, agencyId);
				query8.setParameter(2, 0);
				query8.setParameter(3, fromDate);
				query8.setParameter(4, toDate);
				List<String> result8 = query8.list();
				if (result8.get(0) != null)
					minarray.add(Integer.parseInt(result8.get(0).substring(6)));

				// Fetch SR_NO_TO
				Query<String> query13 = session.createQuery(
						"SELECT sr_no from RCDataDO where created_by = ?1 and deleted = ?2 and rc_date between ?3 and ?4 and sr_no like 'CS%' order by created_date desc")
						.setMaxResults(1);
				query13.setParameter(1, agencyId);
				query13.setParameter(2, 0);
				query13.setParameter(3, fromDate);
				query13.setParameter(4, toDate);
				List<String> result13 = query13.list();

				if (result13.get(0) != null)
					maxarray.add(Integer.parseInt(result13.get(0).substring(6)));

			}

			// Fetch RC cancelled Count
			Query<Long> query6 = session.createQuery(
					"SELECT count(DISTINCT id) from RCDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?5) and rc_date between ?3 and ?4 and sr_no like 'CS%'");
			query6.setParameter(1, agencyId);
			query6.setParameter(2, 2);
			query6.setParameter(3, fromDate);
			query6.setParameter(4, toDate);
			query6.setParameter(5, 3);
			List<Long> result6 = query6.list();

			cancelled = cancelled + result6.get(0);

			// Fetch TV Count
			Query<Long> query7 = session
					.createQuery("SELECT count(DISTINCT id) from TVDataDO where created_by = ?1 and deleted = ?2"
							+ " and tv_date between ?3 and ?4 and sr_no like 'CS%'");
			query7.setParameter(1, agencyId);
			query7.setParameter(2, 0);
			query7.setParameter(3, fromDate);
			query7.setParameter(4, toDate);
			List<Long> result7 = query7.list();

			total_no = total_no + result7.get(0);

			if (result7.get(0) > 0) {

				// Fetch SR_NO_TO
				Query<String> query8 = session.createQuery(
						"SELECT sr_no from TVDataDO where created_by = ?1 and deleted = ?2 and tv_date between ?3 and ?4 and sr_no like 'CS%' order by created_date")
						.setMaxResults(1);
				query8.setParameter(1, agencyId);
				query8.setParameter(2, 0);
				query8.setParameter(3, fromDate);
				query8.setParameter(4, toDate);
				List<String> result8 = query8.list();
				if (result8.get(0) != null)
					minarray.add(Integer.parseInt(result8.get(0).substring(6)));

				// Fetch SR_NO_TO
				Query<String> query13 = session.createQuery(
						"SELECT sr_no from TVDataDO where created_by = ?1 and deleted = ?2 and tv_date between ?3 and ?4 and sr_no like 'CS%' order by created_date desc")
						.setMaxResults(1);
				query13.setParameter(1, agencyId);
				query13.setParameter(2, 0);
				query13.setParameter(3, fromDate);
				query13.setParameter(4, toDate);
				List<String> result13 = query13.list();

				if (result13.get(0) != null)
					maxarray.add(Integer.parseInt(result13.get(0).substring(6)));
			}

			// Fetch TV cancelled Count
			Query<Long> query8 = session
					.createQuery("SELECT count(DISTINCT id) from TVDataDO where created_by = ?1 and "
							+ "(deleted = ?2 or deleted = ?5) and tv_date between ?3 and ?4 and sr_no like 'CS%'");
			query8.setParameter(1, agencyId);
			query8.setParameter(2, 2);
			query8.setParameter(3, fromDate);
			query8.setParameter(4, toDate);
			query8.setParameter(5, 3);
			List<Long> result8 = query8.list();

			cancelled = cancelled + result8.get(0);

			if (total_no > 0) {
				int from_no = getMinValue(minarray);
				int to_no = getMaxValue(maxarray);
				if (from_no < 10)
					sr_no_from = "CS-" + cfy + "-000" + from_no;
				else if (from_no < 100)
					sr_no_from = "CS-" + cfy + "-00" + from_no;
				else if (from_no < 1000)
					sr_no_from = "CS-" + cfy + "-0" + from_no;
				else
					sr_no_from = "CS-" + cfy + "-" + from_no;

				if (from_no < 10)
					sr_no_to = "CS-" + cfy + "-000" + to_no;
				else if (from_no < 100)
					sr_no_to = "CS-" + cfy + "-00" + to_no;
				else if (from_no < 1000)
					sr_no_to = "CS-" + cfy + "-0" + to_no;
				else
					sr_no_to = "CS-" + cfy + "-" + to_no;
			}
			psvo.setTotal_no(total_no);
			psvo.setCancelled(cancelled);
			psvo.setSr_no_from(sr_no_from);
			psvo.setSr_no_to(sr_no_to);
			psvo.setDoc_type(2);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return psvo;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public GSTR1DocsDataDO getAgencyGSTR1PRDocsData(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		GSTR1DocsDataDO psvo = new GSTR1DocsDataDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		long total_no = 0;
		long cancelled = 0;
		String sr_no_from = "";
		String sr_no_to = "";
		try {
			// Fetch DOM Sales Count
			Query<Long> query = session.createQuery(
					"SELECT count(DISTINCT dsid.pr_ref_id) FROM PurchaseReturnDO dsi,PurchaseReturnDetailsDO dsid WHERE dsi.created_by = ?1 and dsi.id = dsid.pr_ref_id and deleted = ?2"
							+ " and inv_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<Long> result = query.list();

			total_no = total_no + result.get(0);

			if (result.get(0) > 0) {
				// Fetch SR_NO_TO
				Query<String> query5 = session.createQuery(
						"SELECT sid FROM PurchaseReturnDO where  created_by = ?1 AND deleted =?2 and inv_date between ?3 and ?4 order by created_date")
						.setMaxResults(1);
				query5.setParameter(1, agencyId);
				query5.setParameter(2, 0);
				query5.setParameter(3, fromDate);
				query5.setParameter(4, toDate);
				List<String> result5 = query5.list();

				if (result5.get(0) != null)
					sr_no_from = result5.get(0);

				// Fetch SR_NO_FROM
				Query<String> query6 = session.createQuery(
						"SELECT sid FROM PurchaseReturnDO where  created_by = ?1 AND deleted =?2 and inv_date between ?3 and ?4 order by created_date desc")
						.setMaxResults(1);
				query6.setParameter(1, agencyId);
				query6.setParameter(2, 0);
				query6.setParameter(3, fromDate);
				query6.setParameter(4, toDate);
				List<String> result6 = query6.list();

				if (result6.get(0) != null)
					sr_no_to = result6.get(0);
			}

			// Fetch cancelled Count
			Query<Long> query11 = session.createQuery(
					"SELECT count(DISTINCT dsid.pr_ref_id) FROM PurchaseReturnDO dsi,PurchaseReturnDetailsDO dsid WHERE dsi.created_by = ?1 and dsi.id = dsid.pr_ref_id and "
							+ "(deleted = ?2 or deleted = ?5) and inv_date between ?3 and ?4 ");
			query11.setParameter(1, agencyId);
			query11.setParameter(2, 2);
			query11.setParameter(3, fromDate);
			query11.setParameter(4, toDate);
			query11.setParameter(5, 3);
			List<Long> result11 = query11.list();

			cancelled = cancelled + result11.get(0);

			psvo.setTotal_no(total_no);
			psvo.setCancelled(cancelled);
			psvo.setSr_no_from(sr_no_from);
			psvo.setSr_no_to(sr_no_to);

			psvo.setDoc_type(3);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return psvo;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public GSTR1DocsDataDO getAgencyGSTR1CNDocsData(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		GSTR1DocsDataDO psvo = new GSTR1DocsDataDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		long total_no = 0;
		long cancelled = 0;
		String sr_no_from = "";
		String sr_no_to = "";
		try {
			// Fetch DOM Sales Count
			Query<Long> query = session.createQuery(
					"SELECT count(DISTINCT id) FROM CreditNotesDO  WHERE credit_note_type = 2 and created_by = ?1 and deleted = ?2"
							+ " and note_date between ?3 and ?4 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<Long> result = query.list();
			total_no = total_no + result.get(0);
			if (result.get(0) > 0) {
				// Fetch SR_NO_TO
				Query<String> query5 = session.createQuery(
						"SELECT sid FROM CreditNotesDO where  credit_note_type = 2 and created_by = ?1 AND deleted =?2 and note_date between ?3 and ?4 order by created_date")
						.setMaxResults(1);
				query5.setParameter(1, agencyId);
				query5.setParameter(2, 0);
				query5.setParameter(3, fromDate);
				query5.setParameter(4, toDate);
				List<String> result5 = query5.list();
				if (result5.get(0) != null)
					sr_no_from = result5.get(0);

				// Fetch SR_NO_TO
				Query<String> query6 = session.createQuery(
						"SELECT sid FROM CreditNotesDO where  credit_note_type = 2 AND created_by = ?1 AND deleted =?2 and note_date between ?3 and ?4 order by created_date DESC")
						.setMaxResults(1);
				query6.setParameter(1, agencyId);
				query6.setParameter(2, 0);
				query6.setParameter(3, fromDate);
				query6.setParameter(4, toDate);
				List<String> result6 = query6.list();
				if (result6.get(0) != null)
					sr_no_to = result6.get(0);

			}
			// Fetch DOM cancelled Count
			Query<Long> query22 = session.createQuery(
					"SELECT count(DISTINCT id) FROM CreditNotesDO  WHERE credit_note_type = 2 AND credit_note_type = 2 and created_by = ?1 and "
							+ "(deleted = ?2 or deleted = ?5) and note_date between ?3 and ?4 ");
			query22.setParameter(1, agencyId);
			query22.setParameter(2, 2);
			query22.setParameter(3, fromDate);
			query22.setParameter(4, toDate);
			query22.setParameter(5, 3);
			List<Long> result22 = query22.list();
			cancelled = cancelled + result22.get(0);

			psvo.setTotal_no(total_no);
			psvo.setCancelled(cancelled);
			psvo.setSr_no_from(sr_no_from);
			psvo.setSr_no_to(sr_no_to);
			psvo.setDoc_type(4);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return psvo;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public GSTR1DocsDataDO getAgencyGSTR1DNDocsData(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		GSTR1DocsDataDO psvo = new GSTR1DocsDataDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		long total_no = 0;
		long cancelled = 0;
		String sr_no_from = "";
		String sr_no_to = "";
		try {
			// Fetch DOM Sales Count
			Query<Long> query2 = session.createQuery(
					"SELECT count(DISTINCT id) FROM DebitNotesDO  WHERE debit_note_type = 2 AND created_by = ?1 and deleted = ?2"
							+ " and note_date between ?3 and ?4 ");
			query2.setParameter(1, agencyId);
			query2.setParameter(2, 0);
			query2.setParameter(3, fromDate);
			query2.setParameter(4, toDate);
			List<Long> result2 = query2.list();
			total_no = total_no + result2.get(0);

			if (result2.get(0) > 0) {

				// Fetch SR_NO_TO
				Query<String> query7 = session.createQuery(
						"SELECT sid FROM DebitNotesDO where  debit_note_type = 2 AND created_by = ?1 AND deleted =?2 and note_date between ?3 and ?4 order by created_date")
						.setMaxResults(1);
				query7.setParameter(1, agencyId);
				query7.setParameter(2, 0);
				query7.setParameter(3, fromDate);
				query7.setParameter(4, toDate);
				List<String> result7 = query7.list();
				if (result7.get(0) != null)
					sr_no_from = result7.get(0);

				// Fetch SR_NO_TO
				Query<String> query8 = session.createQuery(
						"SELECT sid FROM DebitNotesDO where  debit_note_type = 2 AND created_by = ?1 AND deleted =?2 and note_date between ?3 and ?4 order by created_date DESC")
						.setMaxResults(1);
				query8.setParameter(1, agencyId);
				query8.setParameter(2, 0);
				query8.setParameter(3, fromDate);
				query8.setParameter(4, toDate);
				List<String> result8 = query8.list();
				if (result8.get(0) != null)
					sr_no_to = result8.get(0);

			}

			// Fetch DEBIT NOTES cancelled Count
			Query<Long> query11 = session.createQuery(
					"SELECT count(DISTINCT id) FROM DebitNotesDO  WHERE debit_note_type = 2 AND created_by = ?1 and "
							+ "(deleted = ?2 or deleted = ?5) and note_date between ?3 and ?4 ");
			query11.setParameter(1, agencyId);
			query11.setParameter(2, 2);
			query11.setParameter(3, fromDate);
			query11.setParameter(4, toDate);
			query11.setParameter(5, 3);
			List<Long> result11 = query11.list();
			cancelled = cancelled + result11.get(0);

			psvo.setTotal_no(total_no);
			psvo.setCancelled(cancelled);
			psvo.setSr_no_from(sr_no_from);
			psvo.setSr_no_to(sr_no_to);
			psvo.setDoc_type(5);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return psvo;
	}

	// getting the maximum value
	public static Integer getMaxValue(List<Integer> array) {
		if (!array.isEmpty()) {
			Integer maxValue = array.get(0);
			for (int i = 1; i < array.size(); i++) {
				if (array.get(i) > maxValue) {
					maxValue = array.get(i);
				}
			}

			return maxValue;
		}
		return 0;
	}

	// getting the miniumum value
	public static Integer getMinValue(List<Integer> array) {
		if (!array.isEmpty()) {
			int minValue = array.get(0);
			for (int i = 1; i < array.size(); i++) {
				if (array.get(i) < minValue) {
					minValue = array.get(i);
				}
			}
			return minValue;
		}
		return 0;
	}

	// Credit Notes
	@SuppressWarnings("unchecked")
	public List<GSTR1CDNDataDO> getAgencyGSTR1CNRData(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<GSTR1CDNDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<CreditNotesDO> query = session.createQuery(
					"from CreditNotesDO where credit_note_type = 2 and created_by = ?1 and deleted = ?2 and note_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<CreditNotesDO> result = query.getResultList();
			if (result.size() > 0) {
				for (CreditNotesDO dataDO : result) {
					GSTR1CDNDataDO gstcdnDO = new GSTR1CDNDataDO();

					long cid = dataDO.getCvo_id();
					String refNo = dataDO.getRef_no();
					Query<DOMSalesInvoiceDO> domQuery = session.createQuery(
							"from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 and sr_no = ?3");
					domQuery.setParameter(1, agencyId);
					domQuery.setParameter(2, 0);
					domQuery.setParameter(3, refNo);
					List<DOMSalesInvoiceDO> domResult = domQuery.getResultList();
					if (domResult.size() > 0) {
						for (DOMSalesInvoiceDO domDO : domResult) {
							gstcdnDO.setReceipt_date(domDO.getSi_date());
						}
					} else {
						Query<COMSalesInvoiceDO> comQuery = session.createQuery(
								"from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 and sr_no = ?3");
						comQuery.setParameter(1, agencyId);
						comQuery.setParameter(2, 0);
						comQuery.setParameter(3, refNo);
						List<COMSalesInvoiceDO> comResult = comQuery.getResultList();
						if (comResult.size() > 0) {
							for (COMSalesInvoiceDO comDO : comResult) {
								gstcdnDO.setReceipt_date(comDO.getSi_date());
							}
						} else {
							Query<ARBSalesInvoiceDO> arbQuery = session.createQuery(
									"from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 and sr_no = ?3");
							arbQuery.setParameter(1, agencyId);
							arbQuery.setParameter(2, 0);
							arbQuery.setParameter(3, refNo);
							List<ARBSalesInvoiceDO> arbResult = arbQuery.getResultList();
							if (arbResult.size() > 0) {
								for (ARBSalesInvoiceDO arbDO : arbResult) {
									gstcdnDO.setReceipt_date(arbDO.getSi_date());
								}
							}
						}
					}
					Query<CVODataDO> query2 = session.createQuery(
							"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
					query2.setParameter(1, agencyId);
					query2.setParameter(2, 0);
					query2.setParameter(3, cid);
					query2.setParameter(4, 1);
					query2.setParameter(5, 1);
					List<CVODataDO> result2 = query2.getResultList();
					if (result2.size() > 0) {

						gstcdnDO.setId(dataDO.getSid());
						gstcdnDO.setNote_date(dataDO.getNote_date());
						gstcdnDO.setCvo_id(dataDO.getCvo_id());
						gstcdnDO.setNote_type(dataDO.getCredit_note_type());
						gstcdnDO.setRef_no(dataDO.getRef_no());
						gstcdnDO.setCredit_or_debit_amount(dataDO.getCredit_amount());
						gstcdnDO.setGstp(dataDO.getGstp());
						gstcdnDO.setTaxable_amount(dataDO.getTaxable_amount());
						gstcdnDO.setNreasons(dataDO.getNreasons());
						gstcdnDO.setDoc_type("C");
						doList.add(gstcdnDO);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// DebitNotes - New
	@SuppressWarnings("unchecked")
	public List<GSTR1CDNDataDO> getAgencyGSTR1DNRData(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<GSTR1CDNDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<DebitNotesDO> query = session.createQuery(
					"from DebitNotesDO where debit_note_type = 2 and created_by = ?1 and deleted = ?2 and note_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<DebitNotesDO> result = query.getResultList();
			if (result.size() > 0) {
				for (DebitNotesDO dataDO : result) {
					long cid = dataDO.getCvo_id();

					GSTR1CDNDataDO gstcdnDO = new GSTR1CDNDataDO();
					String refNo = dataDO.getRef_no();
					Query<DOMSalesInvoiceDO> domQuery = session.createQuery(
							"from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 and sr_no = ?3");
					domQuery.setParameter(1, agencyId);
					domQuery.setParameter(2, 0);
					domQuery.setParameter(3, refNo);

					List<DOMSalesInvoiceDO> domResult = domQuery.getResultList();
					if (domResult.size() > 0) {
						for (DOMSalesInvoiceDO domDO : domResult) {
							gstcdnDO.setReceipt_date(domDO.getSi_date());
						}
					} else {
						Query<COMSalesInvoiceDO> comQuery = session.createQuery(
								"from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 and sr_no = ?3");
						comQuery.setParameter(1, agencyId);
						comQuery.setParameter(2, 0);
						comQuery.setParameter(3, refNo);
						List<COMSalesInvoiceDO> comResult = comQuery.getResultList();
						if (comResult.size() > 0) {
							for (COMSalesInvoiceDO comDO : comResult) {
								gstcdnDO.setReceipt_date(comDO.getSi_date());
							}
						} else {
							Query<ARBSalesInvoiceDO> arbQuery = session.createQuery(
									"from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 and sr_no = ?3");
							arbQuery.setParameter(1, agencyId);
							arbQuery.setParameter(2, 0);
							arbQuery.setParameter(3, refNo);
							List<ARBSalesInvoiceDO> arbResult = arbQuery.getResultList();
							if (arbResult.size() > 0) {
								for (ARBSalesInvoiceDO arbDO : arbResult) {
									gstcdnDO.setReceipt_date(arbDO.getSi_date());
								}
							}
						}
					}

					Query<CVODataDO> query2 = session.createQuery(
							"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
					query2.setParameter(1, agencyId);
					query2.setParameter(2, 0);
					query2.setParameter(3, cid);
					query2.setParameter(4, 1);
					query2.setParameter(5, 1);
					List<CVODataDO> result2 = query2.getResultList();
					if (result2.size() > 0) {
						gstcdnDO.setId(dataDO.getSid());
						gstcdnDO.setNote_date(dataDO.getNote_date());
						gstcdnDO.setCvo_id(dataDO.getCvo_id());
						gstcdnDO.setNote_type(dataDO.getDebit_note_type());
						gstcdnDO.setRef_no(dataDO.getRef_no());
						gstcdnDO.setCredit_or_debit_amount(dataDO.getDebit_amount());
						gstcdnDO.setGstp(dataDO.getGstp());
						gstcdnDO.setTaxable_amount(dataDO.getTaxable_amount());
						gstcdnDO.setNreasons(dataDO.getNreasons());
						gstcdnDO.setDoc_type("D");
						doList.add(gstcdnDO);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// Credit Notes - New
	@SuppressWarnings("unchecked")
	public List<GSTR1CDNDataDO> getAgencyGSTR1CNURData(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<GSTR1CDNDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<CreditNotesDO> query = session.createQuery(
					"from CreditNotesDO where credit_note_type = 2 and created_by = ?1 and deleted = ?2 and note_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<CreditNotesDO> result = query.getResultList();
			if (result.size() > 0) {
				for (CreditNotesDO dataDO : result) {
					long cid = dataDO.getCvo_id();

					GSTR1CDNDataDO gstcdnDO = new GSTR1CDNDataDO();

					String refNo = dataDO.getRef_no();
					Query<DOMSalesInvoiceDO> domQuery = session.createQuery(
							"from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 and sr_no = ?3");
					domQuery.setParameter(1, agencyId);
					domQuery.setParameter(2, 0);
					domQuery.setParameter(3, refNo);
					List<DOMSalesInvoiceDO> domResult = domQuery.getResultList();
					if (domResult.size() > 0) {
						for (DOMSalesInvoiceDO domDO : domResult) {
							gstcdnDO.setReceipt_date(domDO.getSi_date());
						}
					} else {
						Query<COMSalesInvoiceDO> comQuery = session.createQuery(
								"from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 and sr_no = ?3");
						comQuery.setParameter(1, agencyId);
						comQuery.setParameter(2, 0);
						comQuery.setParameter(3, refNo);
						List<COMSalesInvoiceDO> comResult = comQuery.getResultList();
						if (comResult.size() > 0) {
							for (COMSalesInvoiceDO comDO : comResult) {
								gstcdnDO.setReceipt_date(comDO.getSi_date());
							}
						} else {
							Query<ARBSalesInvoiceDO> arbQuery = session.createQuery(
									"from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 and sr_no = ?3");
							arbQuery.setParameter(1, agencyId);
							arbQuery.setParameter(2, 0);
							arbQuery.setParameter(3, refNo);
							List<ARBSalesInvoiceDO> arbResult = arbQuery.getResultList();
							if (arbResult.size() > 0) {
								for (ARBSalesInvoiceDO arbDO : arbResult) {
									gstcdnDO.setReceipt_date(arbDO.getSi_date());
								}
							}
						}
					}

					Query<CVODataDO> query2 = session.createQuery(
							"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
					query2.setParameter(1, agencyId);
					query2.setParameter(2, 0);
					query2.setParameter(3, cid);
					query2.setParameter(4, 2);
					query2.setParameter(5, 1);
					List<CVODataDO> result2 = query2.getResultList();
					if (result2.size() > 0) {
						gstcdnDO.setId(dataDO.getSid());
						gstcdnDO.setNote_date(dataDO.getNote_date());
						gstcdnDO.setCvo_id(dataDO.getCvo_id());
						gstcdnDO.setNote_type(dataDO.getCredit_note_type());
						gstcdnDO.setRef_no(dataDO.getRef_no());
						gstcdnDO.setCredit_or_debit_amount(dataDO.getCredit_amount());
						gstcdnDO.setGstp(dataDO.getGstp());
						gstcdnDO.setTaxable_amount(dataDO.getTaxable_amount());
						gstcdnDO.setNreasons(dataDO.getNreasons());
						gstcdnDO.setDoc_type("C");
						doList.add(gstcdnDO);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}

	// DebitNotes - New
	@SuppressWarnings("unchecked")
	public List<GSTR1CDNDataDO> getAgencyGSTR1DNURData(long agencyId, long fromDate, long toDate)
			throws BusinessException {
		List<GSTR1CDNDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<DebitNotesDO> query = session.createQuery(
					"from DebitNotesDO where debit_note_type = 2 and created_by = ?1 and deleted = ?2 and note_date between ?3 and ?4");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			List<DebitNotesDO> result = query.getResultList();
			if (result.size() > 0) {
				for (DebitNotesDO dataDO : result) {
					long cid = dataDO.getCvo_id();

					GSTR1CDNDataDO gstcdnDO = new GSTR1CDNDataDO();

					String refNo = dataDO.getRef_no();
					Query<DOMSalesInvoiceDO> domQuery = session.createQuery(
							"from DOMSalesInvoiceDO where created_by = ?1 and deleted = ?2 and sr_no = ?3");
					domQuery.setParameter(1, agencyId);
					domQuery.setParameter(2, 0);
					domQuery.setParameter(3, refNo);
					List<DOMSalesInvoiceDO> domResult = domQuery.getResultList();
					if (domResult.size() > 0) {
						for (DOMSalesInvoiceDO domDO : domResult) {
							gstcdnDO.setReceipt_date(domDO.getSi_date());
						}
					} else {
						Query<COMSalesInvoiceDO> comQuery = session.createQuery(
								"from COMSalesInvoiceDO where created_by = ?1 and deleted = ?2 and sr_no = ?3");
						comQuery.setParameter(1, agencyId);
						comQuery.setParameter(2, 0);
						comQuery.setParameter(3, refNo);
						List<COMSalesInvoiceDO> comResult = comQuery.getResultList();
						if (comResult.size() > 0) {
							for (COMSalesInvoiceDO comDO : comResult) {
								gstcdnDO.setReceipt_date(comDO.getSi_date());
							}
						} else {
							Query<ARBSalesInvoiceDO> arbQuery = session.createQuery(
									"from ARBSalesInvoiceDO where created_by = ?1 and deleted = ?2 and sr_no = ?3");
							arbQuery.setParameter(1, agencyId);
							arbQuery.setParameter(2, 0);
							arbQuery.setParameter(3, refNo);
							List<ARBSalesInvoiceDO> arbResult = arbQuery.getResultList();
							if (arbResult.size() > 0) {
								for (ARBSalesInvoiceDO arbDO : arbResult) {
									gstcdnDO.setReceipt_date(arbDO.getSi_date());
								}
							}
						}
					}

					Query<CVODataDO> query2 = session.createQuery(
							"from CVODataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?5) and id=?3 and is_gst_reg=?4");
					query2.setParameter(1, agencyId);
					query2.setParameter(2, 0);
					query2.setParameter(3, cid);
					query2.setParameter(4, 2);
					query2.setParameter(5, 1);
					List<CVODataDO> result2 = query2.getResultList();
					if (result2.size() > 0) {
						gstcdnDO.setId(dataDO.getSid());
						gstcdnDO.setNote_date(dataDO.getNote_date());
						gstcdnDO.setCvo_id(dataDO.getCvo_id());
						gstcdnDO.setNote_type(dataDO.getDebit_note_type());
						gstcdnDO.setRef_no(dataDO.getRef_no());
						gstcdnDO.setCredit_or_debit_amount(dataDO.getDebit_amount());
						gstcdnDO.setGstp(dataDO.getGstp());
						gstcdnDO.setTaxable_amount(dataDO.getTaxable_amount());
						gstcdnDO.setNreasons(dataDO.getNreasons());
						gstcdnDO.setDoc_type("D");
						doList.add(gstcdnDO);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return doList;
	}
}
