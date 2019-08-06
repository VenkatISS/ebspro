package com.it.erpapp.commons;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.framework.model.AgencyCashDataDO;
import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.GSTPaymentDetailsDO;
import com.it.erpapp.framework.model.transactions.ARBPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.OtherPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.PurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.others.AssetDataDO;
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
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

public class UpdateDataOnDeletingTransactions {

	Logger logger = Logger.getLogger(UpdateDataOnDeletingTransactions.class.getName());

	/*------------------------------------------------------------ DELETE IN DOM SALES ------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInDomSales(long agencyId, DOMSalesInvoiceDO dataDO,
			List<DOMSalesInvoiceDetailsDO> itemResult) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			BigDecimal samt = new BigDecimal(dataDO.getSi_amount());
			/*--------------------------------- Start Cash Calculation --------------------------------*/
			if (dataDO.getPayment_terms() == 1) {
				Query<AgencyCashDataDO> dscIdQuery = session
						.createQuery("from AgencyCashDataDO where created_by = ?1 and inv_no = ?2 and trans_type=8")
						.setMaxResults(1);
				dscIdQuery.setParameter(1, agencyId);
				dscIdQuery.setParameter(2, dataDO.getSr_no());
				AgencyCashDataDO dscIdDO = dscIdQuery.getSingleResult();

				if (dscIdDO.getId() != 0) {
					BigDecimal cashAmt = new BigDecimal(dscIdDO.getCash_amount());

					Query<AgencyCashDataDO> dscQuery = session.createQuery(
							"from AgencyCashDataDO where created_by = ?1 and ((t_date = ?2 and created_date >= ?3) OR (t_date > ?2)) and trans_type != 11 order by t_date , created_date");
					dscQuery.setParameter(1, agencyId);
					dscQuery.setParameter(2, dataDO.getSi_date());
					dscQuery.setParameter(3, dataDO.getCreated_date());
					List<AgencyCashDataDO> dscresult = dscQuery.getResultList();
					if (!(dscresult.isEmpty())) {
						for (AgencyCashDataDO dscDO : dscresult) {
							if (!(("0.00").equals(dscIdDO.getCash_amount()))) {
								BigDecimal cashBal = new BigDecimal(dscDO.getCash_balance());
								dscDO.setCash_balance((cashBal.subtract(cashAmt)).toString());
							}
							session.update(dscDO);
						}
					}
					dscIdDO.setTrans_type(11);
					session.update(dscIdDO);
				}
			}
			/*--------------------------------- End Cash Calculation --------------------------------*/

			if (dataDO.getPayment_terms() != 1) {
				Query<AgencyCVOBalanceDataDO> dsQuery = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and ((inv_date = ?2 and created_date >= ?3) OR (inv_date > ?2)) and deleted != 3 and cvo_refid = ?4 order by inv_date, created_date");
				dsQuery.setParameter(1, agencyId);
				dsQuery.setParameter(2, dataDO.getSi_date());
				dsQuery.setParameter(3, dataDO.getCreated_date());
				dsQuery.setParameter(4, dataDO.getCustomer_id());

				List<AgencyCVOBalanceDataDO> dsresult = dsQuery.getResultList();
				if (!(dsresult.isEmpty())) {
					for (AgencyCVOBalanceDataDO acvodDO : dsresult) {
						// --------------------------------- Start CVO Calculation --------------------------------
						// here update cvo bal(CBAL) and stock.
						BigDecimal cbal = new BigDecimal(acvodDO.getCbal_amount());
						if (acvodDO.getCvo_refid() == dataDO.getCustomer_id()) {
							acvodDO.setCbal_amount((cbal.subtract(samt)).toString());
						}
						session.update(acvodDO);
						// --------------------------------- End CVO Calculation --------------------------------
					}
				}
			}

			Query<AgencyStockDataDO> asdQuery = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and trans_date >= ?2 and deleted != 3 order by trans_date, created_date");
			asdQuery.setParameter(1, agencyId);
			asdQuery.setParameter(2, dataDO.getSi_date());
			List<AgencyStockDataDO> asdresult = asdQuery.getResultList();
			if (!(asdresult.isEmpty())) {
				for (AgencyStockDataDO asdDO : asdresult) {
					if ((asdDO.getTrans_date() == dataDO.getSi_date()
							&& asdDO.getCreated_date() > dataDO.getCreated_date())
							|| (asdDO.getTrans_date() > dataDO.getSi_date())) {

						// --------------------------------- Start Stock Calculation
						// --------------------------------
						if (!(itemResult.isEmpty())) {
							for (DOMSalesInvoiceDetailsDO sdsido : itemResult) {
								if (sdsido.getProd_code() == asdDO.getProd_code()) {
									asdDO.setCs_fulls(asdDO.getCs_fulls() + sdsido.getQuantity());
									asdDO.setCs_emptys(
											asdDO.getCs_emptys() - (sdsido.getQuantity() - sdsido.getPsv_cyls()));
									session.update(asdDO);
								}
							}
						}
						// --------------------------------- End Stock Calculation
						// --------------------------------
					}
				}
			}

			dataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInDomSales  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}

	}

	/*------------------------------------------------------------ DELETE IN COM SALES ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInCOMSales(COMSalesInvoiceDO dataDO,
			List<COMSalesInvoiceDetailsDO> itemResult) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			/*--------------------------------- Start Cash Calculation --------------------------------*/
			if (dataDO.getPayment_terms() == 1) {
				Query<AgencyCashDataDO> dscIdQuery = session
						.createQuery("from AgencyCashDataDO where created_by = ?1 and inv_no = ?2 and trans_type=8")
						.setMaxResults(1);
				dscIdQuery.setParameter(1, dataDO.getCreated_by());
				dscIdQuery.setParameter(2, dataDO.getSr_no());
				AgencyCashDataDO dscIdDO = dscIdQuery.getSingleResult();
				BigDecimal cashAmt = new BigDecimal(dscIdDO.getCash_amount());

				Query<AgencyCashDataDO> dscQuery = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and ((t_date = ?2 and created_date >= ?3) OR (t_date > ?2)) and trans_type != 11 order by t_date, created_date");
				dscQuery.setParameter(1, dataDO.getCreated_by());
				dscQuery.setParameter(2, dataDO.getSi_date());
				dscQuery.setParameter(3, dataDO.getCreated_date());
				List<AgencyCashDataDO> dscresult = dscQuery.getResultList();
				if (!(dscresult.isEmpty())) {
					for (AgencyCashDataDO dscDO : dscresult) {
						if (!(("0.00").equals(dscIdDO.getCash_amount()))) {
							BigDecimal cashBal = new BigDecimal(dscDO.getCash_balance());
							dscDO.setCash_balance((cashBal.subtract(cashAmt)).toString());
						}
						session.update(dscDO);
					}
				}
				dscIdDO.setTrans_type(11);
				session.update(dscIdDO);
			}
			/*--------------------------------- End Cash Calculation --------------------------------*/
			BigDecimal samt = new BigDecimal(dataDO.getSi_amount());

			if (dataDO.getPayment_terms() != 1) {
				Query<AgencyCVOBalanceDataDO> dsQuery = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and ((inv_date = ?2 and created_date >= ?3) OR (inv_date > ?2)) and deleted != 3 and cvo_refid = ?4 order by inv_date, created_date");
				dsQuery.setParameter(1, dataDO.getCreated_by());
				dsQuery.setParameter(2, dataDO.getSi_date());
				dsQuery.setParameter(3, dataDO.getCreated_date());
				dsQuery.setParameter(4, dataDO.getCustomer_id());

				List<AgencyCVOBalanceDataDO> dsresult = dsQuery.getResultList();
				if (!(dsresult.isEmpty())) {
					for (AgencyCVOBalanceDataDO acvoDO : dsresult) {
						/*--------------------------------- Start CVO Calculation --------------------------------*/
						// here update cvo bal(CBAL) and stock.
						BigDecimal cbal = new BigDecimal(acvoDO.getCbal_amount());
						if (acvoDO.getCvo_refid() == dataDO.getCustomer_id()) {
							acvoDO.setCbal_amount((cbal.subtract(samt)).toString());
						}
						session.update(acvoDO);
						/*--------------------------------- End CVO Calculation --------------------------------*/
					}
				}
			}

			Query<AgencyStockDataDO> asdQuery = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and trans_date >= ?2 and deleted != 3 order by trans_date, created_date");
			asdQuery.setParameter(1, dataDO.getCreated_by());
			asdQuery.setParameter(2, dataDO.getSi_date());
			List<AgencyStockDataDO> asdresult = asdQuery.getResultList();
			if (!(asdresult.isEmpty())) {
				for (AgencyStockDataDO asdDO : asdresult) {
					if ((asdDO.getTrans_date() == dataDO.getSi_date()
							&& asdDO.getCreated_date() > dataDO.getCreated_date())
							|| (asdDO.getTrans_date() > dataDO.getSi_date())) {

						/*--------------------------------- Start Stock Calculation --------------------------------*/
						if (!(itemResult.isEmpty())) {
							for (COMSalesInvoiceDetailsDO svo : itemResult) {
								if (svo.getProd_code() == asdDO.getProd_code()) {
									asdDO.setCs_fulls(asdDO.getCs_fulls() + svo.getQuantity());
									asdDO.setCs_emptys(asdDO.getCs_emptys() - svo.getPsv_cyls());
									session.update(asdDO);
								}
							}
						}
						/*--------------------------------- End Stock Calculation --------------------------------*/
					}
				}
			}

			dataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInCOMSales  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/*------------------------------------------------------------ DELETE IN ARB SALES ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInARBSales(long agencyId, ARBSalesInvoiceDO dataDO,
			List<ARBSalesInvoiceDetailsDO> itemResult) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			BigDecimal samt = new BigDecimal(dataDO.getSi_amount());
			/*--------------------------------- Start Cash Calculation --------------------------------*/
			if (dataDO.getPayment_terms() == 1) {
				Query<AgencyCashDataDO> ascIdQuery = session
						.createQuery("from AgencyCashDataDO where created_by = ?1 and inv_no = ?2 and trans_type=8")
						.setMaxResults(1);
				ascIdQuery.setParameter(1, agencyId);
				ascIdQuery.setParameter(2, dataDO.getSr_no());
				AgencyCashDataDO ascIdDO = ascIdQuery.getSingleResult();
				BigDecimal cashAmt = new BigDecimal(ascIdDO.getCash_amount());

				Query<AgencyCashDataDO> ascQuery = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and ((t_date = ?2 and created_date >= ?3) OR (t_date > ?2)) and trans_type != 11  order by t_date, created_date");
				ascQuery.setParameter(1, agencyId);
				ascQuery.setParameter(2, dataDO.getSi_date());
				ascQuery.setParameter(3, dataDO.getCreated_date());
				List<AgencyCashDataDO> ascresult = ascQuery.getResultList();
				if (!(ascresult.isEmpty())) {
					for (AgencyCashDataDO ascDO : ascresult) {
						if (!(("0.00").equals(ascIdDO.getCash_amount()))) {
							BigDecimal cashBal = new BigDecimal(ascDO.getCash_balance());
							ascDO.setCash_balance((cashBal.subtract(cashAmt)).toString());
						}
						session.update(ascDO);
					}
				}
				ascIdDO.setTrans_type(11);
				session.update(ascIdDO);
			}
			/*--------------------------------- End Cash Calculation --------------------------------*/

//			if (dataDO.getPayment_terms() == 2) {
			if (dataDO.getPayment_terms() != 1) {
				Query<AgencyCVOBalanceDataDO> dsQuery = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and ((inv_date = ?2 and created_date >= ?3) OR (inv_date > ?2)) and deleted != 3 and cvo_refid = ?4 order by inv_date, created_date");
				dsQuery.setParameter(1, dataDO.getCreated_by());
				dsQuery.setParameter(2, dataDO.getSi_date());
				dsQuery.setParameter(3, dataDO.getCreated_date());
				dsQuery.setParameter(4, dataDO.getCustomer_id());

				List<AgencyCVOBalanceDataDO> dsresult = dsQuery.getResultList();
				if (!(dsresult.isEmpty())) {
					for (AgencyCVOBalanceDataDO acvoDO : dsresult) {
						/*--------------------------------- Start CVO Calculation --------------------------------*/
						// here update cvo bal(CBAL) and stock.
						BigDecimal cbal = new BigDecimal(acvoDO.getCbal_amount());
						if (acvoDO.getCvo_refid() == dataDO.getCustomer_id()) {
							acvoDO.setCbal_amount((cbal.subtract(samt)).toString());
						}
						session.update(acvoDO);
						/*--------------------------------- End CVO Calculation --------------------------------*/
					}
				}
			}

			Query<AgencyStockDataDO> asdQuery = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and trans_date >= ?2 and deleted != 3 order by trans_date, created_date");
			asdQuery.setParameter(1, agencyId);
			asdQuery.setParameter(2, dataDO.getSi_date());
			List<AgencyStockDataDO> asdresult = asdQuery.getResultList();
			if (!(asdresult.isEmpty())) {
				for (AgencyStockDataDO asdDO : asdresult) {
					if ((asdDO.getTrans_date() == dataDO.getSi_date()
							&& asdDO.getCreated_date() > dataDO.getCreated_date())
							|| (asdDO.getTrans_date() > dataDO.getSi_date())) {

						/*--------------------------------- Start Stock Calculation --------------------------------*/
						if (!(itemResult.isEmpty())) {
							for (ARBSalesInvoiceDetailsDO svo : itemResult) {
								if (svo.getProd_code() == asdDO.getProd_id()) {
									asdDO.setCs_fulls(asdDO.getCs_fulls() + svo.getQuantity());
									session.update(asdDO);
								}
							}
						}
						/*--------------------------------- End Stock Calculation --------------------------------*/
					}
				}
			}

			dataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInARBSales  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/*------------------------------------------------------------ DELETE IN SALES RETURN ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInSalesReturn(SalesReturnDO dataDO,
			List<SalesReturnDetailsDO> itemResult, Session session) {
		// Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.getTransaction();

			BigDecimal samt = new BigDecimal(dataDO.getInv_amt());
			String srno = Long.toString(dataDO.getSr_no());
			
			/*--------------------------------- Start Cash Calculation --------------------------------*/
			if (dataDO.getPayment_terms() == 1) {
				Query<AgencyCashDataDO> srcIdQuery = session
						.createQuery("from AgencyCashDataDO where created_by = ?1 and inv_no = ?2 and trans_type = 12")
						.setMaxResults(1);
				srcIdQuery.setParameter(1, dataDO.getCreated_by());
				srcIdQuery.setParameter(2, srno);
				AgencyCashDataDO srcIdDO = srcIdQuery.getSingleResult();

				if (srcIdDO.getId() != 0) {
					BigDecimal cashAmt = new BigDecimal(srcIdDO.getCash_amount());

					Query<AgencyCashDataDO> srcQuery = session.createQuery(
							"from AgencyCashDataDO where created_by = ?1 and ((t_date = ?2 and created_date >= ?3) OR (t_date > ?2)) and trans_type != 11 order by t_date , created_date");
					srcQuery.setParameter(1, dataDO.getCreated_by());
					srcQuery.setParameter(2, dataDO.getInv_date());
					srcQuery.setParameter(3, dataDO.getCreated_date());
					List<AgencyCashDataDO> srcresult = srcQuery.getResultList();
					if (!(srcresult.isEmpty())) {
						for (AgencyCashDataDO srcDO : srcresult) {
							if (!(("0.00").equals(srcIdDO.getCash_amount()))) {
								BigDecimal cashBal = new BigDecimal(srcDO.getCash_balance());
								srcDO.setCash_balance((cashBal.add(cashAmt)).toString());
							}
							session.update(srcDO);
						}
					}
					srcIdDO.setTrans_type(11);
					session.update(srcIdDO);
				}
			}
			/*--------------------------------- End Cash Calculation --------------------------------*/
			
			if (dataDO.getCvo_id() > 0) {
				Query<AgencyCVOBalanceDataDO> dsQuery = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and ((inv_date = ?2 and created_date >= ?3) OR (inv_date > ?2)) and deleted != 3 and cvo_refid = ?4 order by inv_date, created_date");
				dsQuery.setParameter(1, dataDO.getCreated_by());
				dsQuery.setParameter(2, dataDO.getInv_date());
				dsQuery.setParameter(3, dataDO.getCreated_date());
				dsQuery.setParameter(4, dataDO.getCvo_id());

				List<AgencyCVOBalanceDataDO> dsresult = dsQuery.getResultList();
				if (!(dsresult.isEmpty())) {
					for (AgencyCVOBalanceDataDO acvoDO : dsresult) {
						/*--------------------------------- Start CVO Calculation --------------------------------*/
						// here update cvo bal(CBAL) and stock.
						BigDecimal cbal = new BigDecimal(acvoDO.getCbal_amount());
						if (acvoDO.getCvo_refid() == dataDO.getCvo_id()) {
							acvoDO.setCbal_amount((cbal.add(samt)).toString());
						}
						session.update(acvoDO);
						/*--------------------------------- End CVO Calculation --------------------------------*/
					}
				}
			}

			Query<AgencyStockDataDO> asdQuery = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and trans_date >= ?2 and deleted != 3 order by trans_date, created_date");
			asdQuery.setParameter(1, dataDO.getCreated_by());
			asdQuery.setParameter(2, dataDO.getInv_date());
			List<AgencyStockDataDO> asdresult = asdQuery.getResultList();
			if (!(asdresult.isEmpty())) {
				for (AgencyStockDataDO asdDO : asdresult) {
					if ((asdDO.getTrans_date() == dataDO.getInv_date()
							&& asdDO.getCreated_date() > dataDO.getCreated_date())
							|| (asdDO.getTrans_date() > dataDO.getInv_date())) {

						/*--------------------------------- Start Stock Calculation --------------------------------*/
						if (!(itemResult.isEmpty())) {
							for (SalesReturnDetailsDO svo : itemResult) {
								long prdctCode = svo.getProd_code();
								if (prdctCode < 13) {
									if (svo.getProd_code() == asdDO.getProd_code()) {
										asdDO.setCs_fulls(asdDO.getCs_fulls() - svo.getRtn_qty());
										if (prdctCode < 10)
											asdDO.setCs_emptys(asdDO.getCs_emptys() + svo.getRtn_qty());
									}
								} else {
									if (svo.getProd_code() == asdDO.getProd_id()) {
										asdDO.setCs_fulls(asdDO.getCs_fulls() - svo.getRtn_qty());
									}
								}
							}
							session.update(asdDO);
						}
					}
					/*--------------------------------- End Stock Calculation --------------------------------*/
				}
			}

			dataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInSalesReturn  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}

	}

	/*------------------------------------------------------------ DELETE IN BANK TRANSACTIONS ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInBankTransactions(long agencyId, List<BankTranxsDataDO> btItemsResult) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			if (!(btItemsResult.isEmpty())) {
				for (BankTranxsDataDO btDatado : btItemsResult) {
					System.out.println("btIdo: " + btDatado);

					/*--------------------------------- Start Cash Calculation --------------------------------*/
					String srno = Long.toString(btDatado.getSr_no());

					if ((btDatado.getTrans_type() == 3) || (btDatado.getTrans_type() == 4)) {
						Query<AgencyCashDataDO> btcIdQuery = session.createQuery(
								"from AgencyCashDataDO where created_by = ?1 and inv_no = ?2 and (trans_type=3 or trans_type=4)")
								.setMaxResults(1);
						btcIdQuery.setParameter(1, agencyId);
						btcIdQuery.setParameter(2, srno);
						AgencyCashDataDO btcIdDO = btcIdQuery.getSingleResult();
						BigDecimal cashAmt = new BigDecimal(btcIdDO.getCash_amount());
						Query<AgencyCashDataDO> btcQuery = session.createQuery(
								"from AgencyCashDataDO where created_by = ?1 and ((t_date = ?2 and created_date >= ?3) OR (t_date > ?2)) and trans_type != 11  order by t_date, created_date");
						btcQuery.setParameter(1, agencyId);
						btcQuery.setParameter(2, btDatado.getBt_date());
						btcQuery.setParameter(3, btDatado.getCreated_date());
						List<AgencyCashDataDO> btcresult = btcQuery.getResultList();
						if (!(btcresult.isEmpty())) {
							for (AgencyCashDataDO btcDO : btcresult) {
								if (!(("0.00").equals(btcIdDO.getCash_amount()))) {
									BigDecimal cashBal = new BigDecimal(btcDO.getCash_balance());
									if (btDatado.getTrans_type() == 3) {
										btcDO.setCash_balance((cashBal.add(cashAmt)).toString());
									} else if (btDatado.getTrans_type() == 4) {
										btcDO.setCash_balance((cashBal.subtract(cashAmt)).toString());
									}

								}
								session.update(btcDO);
							}
						}
						btcIdDO.setTrans_type(11);
						session.update(btcIdDO);
					}

					/*--------------------------------- End Cash Calculation --------------------------------*/

					long bankId = btDatado.getBank_id();
					int ttype = btDatado.getTrans_type();
					BigDecimal amt = new BigDecimal(btDatado.getTrans_amount());
					BigDecimal chrgs = new BigDecimal(btDatado.getTrans_charges());
					Query<BankTranxsDataDO> btQuery = session.createQuery(
							"from BankTranxsDataDO where created_by = ?1 and ((bt_date = ?2 and created_date >= ?3) OR (bt_date > ?2)) and deleted != 3 and bank_id = ?4 order by bt_date, created_date");
					btQuery.setParameter(1, agencyId);
					btQuery.setParameter(2, btDatado.getBt_date());
					btQuery.setParameter(3, btDatado.getCreated_date());
					btQuery.setParameter(4, bankId);
					List<BankTranxsDataDO> btresult = btQuery.getResultList();

					if (!(btresult.isEmpty())) {
						for (BankTranxsDataDO btDO : btresult) {
							BigDecimal cbal = new BigDecimal(btDO.getBank_acb());
							if (btDO.getBank_id() == bankId) {
								if (btDatado.getDeleted() == 2) {
									if (ttype == 1 || ttype == 4 || ttype == 9 || ttype == 10) {
										btDO.setBank_acb((cbal.add(amt).add(chrgs)).toString());
									} else if (ttype == 2) {
										btDO.setBank_acb((cbal.subtract(amt)).toString()); // without charges
									} else if (ttype == 3) {
										btDO.setBank_acb((cbal.subtract(amt).add(chrgs)).toString()); // with charges
									}
								}
							}
							session.update(btDO);
						}
					}

					btDatado.setDeleted(3);
					session.flush();
					session.clear();
					session.update(btDatado);

				}
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInBankTransactions  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/*------------------------------------------------------------ DELETE IN CYLINDER PURCHASES ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInCylinderPurchases(long agencyId, PurchaseInvoiceDO dataDO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			Query<AgencyStockDataDO> asdQuery = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and prod_code = ?2 and trans_date >= ?3 and deleted != 3 order by trans_date, created_date");
			asdQuery.setParameter(1, agencyId);
			asdQuery.setParameter(2, dataDO.getProd_code());
			asdQuery.setParameter(3, dataDO.getStk_recvd_date());
			List<AgencyStockDataDO> asdresult = asdQuery.getResultList();
			if (!(asdresult.isEmpty())) {
				for (AgencyStockDataDO asdDO : asdresult) {
					if ((asdDO.getTrans_date() == dataDO.getStk_recvd_date()
							&& asdDO.getCreated_date() > dataDO.getCreated_date())
							|| (asdDO.getTrans_date() > dataDO.getStk_recvd_date())) {
						// --------------------------------- Start Stock Calculation
						// --------------------------------
						asdDO.setCs_fulls(asdDO.getCs_fulls() - dataDO.getQuantity());
						if (dataDO.getEmr_or_delv() == 2) {
							asdDO.setCs_emptys(asdDO.getCs_emptys() + dataDO.getQuantity());
						}
						session.update(asdDO);
						// --------------------------------- End Stock Calculation
						// --------------------------------
					}
				}
			}

			dataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInCylinderPurchases  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/*------------------------------------------------------------ DELETE IN ARB PURCHASES ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInARBPurchases(ARBPurchaseInvoiceDO dataDO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			BigDecimal samt = new BigDecimal(dataDO.getC_amount());

			if (dataDO.getVendor_id() > 0) {
				Query<AgencyCVOBalanceDataDO> arbpQuery = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and ((inv_date = ?2 and created_date >= ?3) OR (inv_date > ?2)) and deleted != 3 and cvo_refid = ?4 order by inv_date, created_date");
				arbpQuery.setParameter(1, dataDO.getCreated_by());
				arbpQuery.setParameter(2, dataDO.getInv_date());
				arbpQuery.setParameter(3, dataDO.getCreated_date());
				arbpQuery.setParameter(4, dataDO.getVendor_id());
				List<AgencyCVOBalanceDataDO> arbpresult = arbpQuery.getResultList();
				if (!(arbpresult.isEmpty())) {
					for (AgencyCVOBalanceDataDO cvoDO : arbpresult) {
						/*--------------------------------- Start CVO Calculation --------------------------------*/
						// here update cvo bal(CBAL) and stock.
						BigDecimal cbal = new BigDecimal(cvoDO.getCbal_amount());
						cvoDO.setCbal_amount((cbal.subtract(samt)).toString());
						session.update(cvoDO);
					}
					/*--------------------------------- End CVO Calculation --------------------------------*/
				}
			}

			Query<AgencyStockDataDO> asdQuery = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and prod_id = ?2 and trans_date >= ?3 and deleted != 3 order by trans_date, created_date");
			asdQuery.setParameter(1, dataDO.getCreated_by());
			asdQuery.setParameter(2, dataDO.getArb_code());
			asdQuery.setParameter(3, dataDO.getStk_recvd_date());

			List<AgencyStockDataDO> asdresult = asdQuery.getResultList();
			if (!(asdresult.isEmpty())) {
				for (AgencyStockDataDO asdDO : asdresult) {
					if ((asdDO.getTrans_date() == dataDO.getStk_recvd_date()
							&& asdDO.getCreated_date() > dataDO.getCreated_date())
							|| (asdDO.getTrans_date() > dataDO.getStk_recvd_date())) {

						/*--------------------------------- Start Stock Calculation --------------------------------*/
						asdDO.setCs_fulls(asdDO.getCs_fulls() - dataDO.getQuantity());
						session.update(asdDO);
						/*--------------------------------- End Stock Calculation --------------------------------*/
					}
				}
			}

			dataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInARBPurchases  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/*------------------------------------------------------------ DELETE IN OTHER PURCHASES ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInOtherPurchases(OtherPurchaseInvoiceDO dataDO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			BigDecimal pamt = new BigDecimal(dataDO.getP_amount());
			if (dataDO.getVendor_id() > 0) {
				Query<AgencyCVOBalanceDataDO> opQuery = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and ((inv_date = ?2 and created_date >= ?3) OR (inv_date > ?2)) and deleted != 3 and cvo_refid = ?4 order by inv_date, created_date");
				opQuery.setParameter(1, dataDO.getCreated_by());
				opQuery.setParameter(2, dataDO.getInv_date());
				opQuery.setParameter(3, dataDO.getCreated_date());
				opQuery.setParameter(4, dataDO.getVendor_id());

				List<AgencyCVOBalanceDataDO> opresult = opQuery.getResultList();
				if (!(opresult.isEmpty())) {
					for (AgencyCVOBalanceDataDO opDO : opresult) {
						// --------------------------------- Start CVO Calculation
						// --------------------------------
						if (opDO.getCvo_refid() > 0) {
							// here update cvo bal(CBAL) and stock.
							BigDecimal cbal = new BigDecimal(opDO.getCbal_amount());
							opDO.setCbal_amount((cbal.subtract(pamt)).toString());
						}
						// --------------------------------- End CVO Calculation
						// --------------------------------
						session.update(opDO);
					}
				}
			}

			dataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInOtherPurchases  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/*------------------------------------------------------------ DELETE IN PURCHASE RETURN ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInPurchaseReturns(PurchaseReturnDO dataDO,
			List<PurchaseReturnDetailsDO> itemResult, int cvoCat) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			BigDecimal iamt = new BigDecimal(dataDO.getInv_amt());

			if ((dataDO.getCvo_id() != 0) && (cvoCat != 2)) {
				Query<AgencyCVOBalanceDataDO> prQuery = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and ((inv_date = ?2 and created_date >= ?3) OR (inv_date > ?2)) and deleted != 3 and cvo_refid = ?4 order by inv_date, created_date");
				prQuery.setParameter(1, dataDO.getCreated_by());
				prQuery.setParameter(2, dataDO.getInv_date());
				prQuery.setParameter(3, dataDO.getCreated_date());
				prQuery.setParameter(4, dataDO.getCvo_id());

				List<AgencyCVOBalanceDataDO> prresult = prQuery.getResultList();
				if (!(prresult.isEmpty())) {
					for (AgencyCVOBalanceDataDO prDO : prresult) {
						// --------------------------------- Start CVO Calculation
						// --------------------------------
						if (prDO.getCvo_cat() != 2) {
							BigDecimal cbal = new BigDecimal(prDO.getCbal_amount());
							prDO.setCbal_amount((cbal.add(iamt)).toString());
						}
						// --------------------------------- End CVO Calculation
						// --------------------------------
						session.update(prDO);
					}
				}
			}

			Query<AgencyStockDataDO> asdQuery = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and trans_date >= ?2 and deleted != 3 order by trans_date, created_date");
			asdQuery.setParameter(1, dataDO.getCreated_by());
			asdQuery.setParameter(2, dataDO.getInv_date());
			List<AgencyStockDataDO> asdresult = asdQuery.getResultList();
			if (!(asdresult.isEmpty())) {
				for (AgencyStockDataDO asdDO : asdresult) {
					if ((asdDO.getTrans_date() == dataDO.getInv_date()
							&& asdDO.getCreated_date() > dataDO.getCreated_date())
							|| (asdDO.getTrans_date() > dataDO.getInv_date())) {

						// --------------------------------- Start Stock Calculation
						// --------------------------------
						if (!(itemResult.isEmpty())) {
							for (PurchaseReturnDetailsDO prdeDO : itemResult) {
								long productCode = prdeDO.getProd_code();
								if (productCode < 13) {
									if (prdeDO.getProd_code() == asdDO.getProd_code()) {
										asdDO.setCs_fulls(asdDO.getCs_fulls() + prdeDO.getRtn_qty());
										if (productCode < 10)
											asdDO.setCs_emptys(asdDO.getCs_emptys() - prdeDO.getRtn_qty());
									}
								} else {
									if (prdeDO.getProd_code() == asdDO.getProd_id()) {
										asdDO.setCs_fulls(asdDO.getCs_fulls() + prdeDO.getRtn_qty());
									}
								}
							}
							session.update(asdDO);
						}
					}
					// --------------------------------- End Stock Calculation
					// --------------------------------

				}
			}

			dataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInPurchaseReturns  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/*------------------------------------------------------------ DELETE IN NCDBC ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInNCDBC(long agencyId, NCDBCInvoiceDO dataDO,
			List<NCDBCInvoiceDetailsDO> itemResult) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			BigDecimal camt = new BigDecimal(dataDO.getCash_amount());

			/*--------------------------------- Start Cash Calculation --------------------------------*/
			if (!(camt.equals(BigDecimal.ZERO))) {
				Query<AgencyCashDataDO> nccIdQuery = session
						.createQuery("from AgencyCashDataDO where created_by = ?1 and inv_no = ?2 and trans_type=7")
						.setMaxResults(1);
				nccIdQuery.setParameter(1, agencyId);
				nccIdQuery.setParameter(2, dataDO.getSr_no());
				AgencyCashDataDO nccIdDO = nccIdQuery.getSingleResult();
				BigDecimal cashAmt = new BigDecimal(nccIdDO.getCash_amount());

				Query<AgencyCashDataDO> dscQuery = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and ((t_date = ?2 and created_date >= ?3) OR (t_date > ?2)) and trans_type != 11 order by t_date , created_date");
				dscQuery.setParameter(1, agencyId);
				dscQuery.setParameter(2, dataDO.getInv_date());
				dscQuery.setParameter(3, dataDO.getCreated_date());
				List<AgencyCashDataDO> dscresult = dscQuery.getResultList();
				if (!(dscresult.isEmpty())) {
					for (AgencyCashDataDO dscDO : dscresult) {
						if (!(("0.00").equals(nccIdDO.getCash_amount()))) {
							BigDecimal cashBal = new BigDecimal(dscDO.getCash_balance());
							dscDO.setCash_balance((cashBal.subtract(cashAmt)).toString());
						}
						session.update(dscDO);
					}
				}
				nccIdDO.setTrans_type(11);
				session.update(nccIdDO);
			}
			/*--------------------------------- End Cash Calculation --------------------------------*/

			Query<AgencyStockDataDO> asdQuery = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and trans_date >= ?2 and deleted != 3 order by trans_date, created_date");
			asdQuery.setParameter(1, agencyId);
			asdQuery.setParameter(2, dataDO.getInv_date());
			List<AgencyStockDataDO> asdresult = asdQuery.getResultList();
			if (!(asdresult.isEmpty())) {
				for (AgencyStockDataDO asdDO : asdresult) {
					if ((asdDO.getTrans_date() == dataDO.getInv_date()
							&& asdDO.getCreated_date() > dataDO.getCreated_date())
							|| (asdDO.getTrans_date() > dataDO.getInv_date())) {

						// --------------------------------- Start Stock Calculation
						// --------------------------------
						if (!(itemResult.isEmpty())) {
							for (NCDBCInvoiceDetailsDO sncido : itemResult) {

								if ((sncido.getProd_code() > 9 && sncido.getProd_code() < 13
										&& sncido.getProd_code() == asdDO.getProd_code())
										|| (sncido.getProd_code() >= 13
												&& sncido.getProd_code() == asdDO.getProd_id())) {

									asdDO.setCs_fulls(asdDO.getCs_fulls() + sncido.getQuantity());
									session.update(asdDO);
								}
							}
						}
						// --------------------------------- End Stock Calculation
						// --------------------------------

						/*--------------------------------- Start CVO Calculation --------------------------------*/
						// Present no need, as ledger is not maintained for ujwala.
						/*--------------------------------- End CVO Calculation --------------------------------*/
					}
				}
			}

			dataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInNCDBC  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/*------------------------------------------------------------ DELETE IN RC ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInRCSales(RCDataDO dataDO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			/*--------------------------------- Start Cash Calculation --------------------------------*/
			if (("cash").equals(dataDO.getPayment_terms())) {
				Query<AgencyCashDataDO> dscIdQuery = session
						.createQuery("from AgencyCashDataDO where created_by = ?1 and inv_no = ?2 and trans_type=6")
						.setMaxResults(1);
				dscIdQuery.setParameter(1, dataDO.getCreated_by());
				dscIdQuery.setParameter(2, dataDO.getSr_no());
				AgencyCashDataDO dscIdDO = dscIdQuery.getSingleResult();
				BigDecimal cashAmt = new BigDecimal(dscIdDO.getCash_amount());

				Query<AgencyCashDataDO> dscQuery = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and ((t_date = ?2 and created_date >= ?3) OR (t_date > ?2)) and trans_type != 11 order by t_date , created_date ");
				dscQuery.setParameter(1, dataDO.getCreated_by());
				dscQuery.setParameter(2, dataDO.getRc_date());
				dscQuery.setParameter(3, dataDO.getCreated_date());
				List<AgencyCashDataDO> dscresult = dscQuery.getResultList();
				if (!(dscresult.isEmpty())) {
					for (AgencyCashDataDO dscDO : dscresult) {
						if (!(("0.00").equals(dscIdDO.getCash_amount()))) {
							BigDecimal cashBal = new BigDecimal(dscDO.getCash_balance());
							dscDO.setCash_balance((cashBal.subtract(cashAmt)).toString());
						}
						session.update(dscDO);
					}
				}
				dscIdDO.setTrans_type(11);
				session.update(dscIdDO);
			}
			/*--------------------------------- End Cash Calculation --------------------------------*/

			Query<AgencyStockDataDO> asdQuery = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and prod_code = ?2 and trans_date >= ?3 and deleted != 3 order by trans_date, created_date");
			asdQuery.setParameter(1, dataDO.getCreated_by());
			asdQuery.setParameter(2, 10);
			asdQuery.setParameter(3, dataDO.getRc_date());
			List<AgencyStockDataDO> asdresult = asdQuery.getResultList();
			if (!(asdresult.isEmpty())) {
				for (AgencyStockDataDO asdDO : asdresult) {
					if ((asdDO.getTrans_date() == dataDO.getRc_date()
							&& asdDO.getCreated_date() > dataDO.getCreated_date())
							|| (asdDO.getTrans_date() > dataDO.getRc_date())) {

						/*--------------------------------- Start Stock Calculation --------------------------------*/
						if (dataDO.getNo_of_reg() > 0) {
							asdDO.setCs_fulls(asdDO.getCs_fulls() + dataDO.getNo_of_reg());
							session.update(asdDO);
						}
						/*--------------------------------- End Stock Calculation --------------------------------*/
					}
				}
			}

			dataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInRCSales  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}

	}

	/*------------------------------------------------------------ DELETE IN TV ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public void updateDataOnDeleteInTVSales(TVDataDO tvdataDO, Session session) {
		Transaction tx = null;
		try {
			tx = session.getTransaction();

			/*--------------------------------- Start Cash Calculation --------------------------------*/

			if (("Cash").equals(tvdataDO.getPayment_terms())) {
				Query<AgencyCashDataDO> dscIdQuery = session
						.createQuery("from AgencyCashDataDO where created_by = ?1 and inv_no = ?2 and trans_type=5")
						.setMaxResults(1);
				dscIdQuery.setParameter(1, tvdataDO.getCreated_by());
				dscIdQuery.setParameter(2, tvdataDO.getSr_no());
				AgencyCashDataDO tvcIdDO = dscIdQuery.getSingleResult();
				BigDecimal cashAmt = new BigDecimal(tvcIdDO.getCash_amount());

				Query<AgencyCashDataDO> dscQuery = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and ((t_date = ?2 and created_date >= ?3) OR (t_date > ?2)) and trans_type != 11 order by t_date , created_date");
				dscQuery.setParameter(1, tvdataDO.getCreated_by());
				dscQuery.setParameter(2, tvdataDO.getTv_date());
				dscQuery.setParameter(3, tvdataDO.getCreated_date());
				List<AgencyCashDataDO> tvcresult = dscQuery.getResultList();
				if (!(tvcresult.isEmpty())) {
					for (AgencyCashDataDO tvcDO : tvcresult) {
						if (!(("0.00").equals(tvcIdDO.getCash_amount()))) {
							BigDecimal cashBal = new BigDecimal(tvcDO.getCash_balance());
							tvcDO.setCash_balance((cashBal.add(cashAmt)).toString());
						}
						session.update(tvcDO);
					}
				}
				tvcIdDO.setTrans_type(11);
				session.update(tvcIdDO);
			}
			/*--------------------------------- End Cash Calculation --------------------------------*/

			Query<AgencyStockDataDO> asdQuery = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and trans_date >= ?2 and deleted != 3 order by trans_date, created_date");
			asdQuery.setParameter(1, tvdataDO.getCreated_by());
			asdQuery.setParameter(2, tvdataDO.getTv_date());
			List<AgencyStockDataDO> asdresult = asdQuery.getResultList();
			if (!(asdresult.isEmpty())) {
				for (AgencyStockDataDO asdDO : asdresult) {
					if ((asdDO.getTrans_date() == tvdataDO.getTv_date()
							&& asdDO.getCreated_date() > tvdataDO.getCreated_date())
							|| (asdDO.getTrans_date() > tvdataDO.getTv_date())) {

						// --------------------------------- Start Stock Calculation
						// --------------------------------
						if ((tvdataDO.getNo_of_reg() > 0) && (asdDO.getProd_code() == 10)) {
							asdDO.setCs_fulls(asdDO.getCs_fulls() - tvdataDO.getNo_of_reg());
							session.update(asdDO);
						}
						if (asdDO.getProd_code() != 10) {
							if (asdDO.getProd_code() == tvdataDO.getProd_code()) {
								asdDO.setCs_emptys(asdDO.getCs_emptys() - tvdataDO.getNo_of_cyl());
								session.update(asdDO);
							}
						}
						// --------------------------------- End Stock Calculation
						// --------------------------------
					}
				}
			}

			tvdataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(tvdataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInTVSales  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}

	}

	/*------------------------------------------------------------ DELETE IN RECEIPTS ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInReceipts(long agencyId, ReceiptsDataDO dataDO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			String srno = Long.toString(dataDO.getSr_no());
			BigDecimal samt = new BigDecimal(dataDO.getRcp_amount());
			/*--------------------------------- Start Cash Calculation --------------------------------*/
			if ((dataDO.getPayment_mode() == 1) && (dataDO.getCredited_bank() == 1)) {
				Query<AgencyCashDataDO> rcIdQuery = session
						.createQuery("from AgencyCashDataDO where created_by = ?1 and inv_no = ?2 and trans_type=2")
						.setMaxResults(1);
				rcIdQuery.setParameter(1, agencyId);
				rcIdQuery.setParameter(2, srno);

				AgencyCashDataDO rcIdDO = rcIdQuery.getSingleResult();
				BigDecimal cashAmt = new BigDecimal(rcIdDO.getCash_amount());

				Query<AgencyCashDataDO> rcQuery = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and ((t_date = ?2 and created_date >= ?3) OR (t_date > ?2)) and trans_type != 11 order by t_date, created_date ");
				rcQuery.setParameter(1, agencyId);
				rcQuery.setParameter(2, dataDO.getRcp_date());
				rcQuery.setParameter(3, dataDO.getCreated_date());
				List<AgencyCashDataDO> rcresult = rcQuery.getResultList();
				if (!(rcresult.isEmpty())) {
					for (AgencyCashDataDO rcDO : rcresult) {
						if (!(("0.00").equals(rcIdDO.getCash_amount()))) {
							BigDecimal cashBal = new BigDecimal(rcDO.getCash_balance());
							rcDO.setCash_balance((cashBal.subtract(cashAmt)).toString());
						}
						session.update(rcDO);
					}
				}
				rcIdDO.setTrans_type(11);
				session.update(rcIdDO);
			}
			/*--------------------------------- End Cash Calculation --------------------------------*/

			if (dataDO.getRcvd_from() > 0) {
				// here update cvo bal(CBAL) and stock.
				Query<AgencyCVOBalanceDataDO> rQuery = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and ((inv_date = ?2 and created_date >= ?3) OR (inv_date > ?2)) and deleted != 3 and cvo_refid = ?4 order by inv_date, created_date");
				rQuery.setParameter(1, agencyId);
				rQuery.setParameter(2, dataDO.getRcp_date());
				rQuery.setParameter(3, dataDO.getCreated_date());
				rQuery.setParameter(4, dataDO.getRcvd_from());

				List<AgencyCVOBalanceDataDO> rresult = rQuery.getResultList();
				if (!(rresult.isEmpty())) {
					for (AgencyCVOBalanceDataDO rDO : rresult) {
						/*--------------------------------- Start CVO Calculation --------------------------------*/
						BigDecimal cbal = new BigDecimal(rDO.getCbal_amount());
						rDO.setCbal_amount((cbal.add(samt)).toString());
						/*--------------------------------- End CVO Calculation --------------------------------*/
						session.update(rDO);
					}
				}
			}

			dataDO.setDeleted(3);
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInReceipts  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/*------------------------------------------------------------ DELETE IN PAYMENTS ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInPayments(PaymentsDataDO dataDO, GSTPaymentDetailsDO gstpDataDO,
			Session session) {
		Transaction tx = null;
		try {
			tx = session.getTransaction();

			String srno = Long.toString(dataDO.getSr_no());
			BigDecimal samt = new BigDecimal(dataDO.getPymt_amount());
			/*--------------------------------- Start Cash Calculation --------------------------------*/

			if ((dataDO.getPayment_mode() == 1) && (dataDO.getDebited_bank() == 1)) {
				Query<AgencyCashDataDO> pcIdQuery = session
						.createQuery("from AgencyCashDataDO where created_by = ?1 and inv_no = ?2 and trans_type=1")
						.setMaxResults(1);
				pcIdQuery.setParameter(1, dataDO.getCreated_by());
				pcIdQuery.setParameter(2, srno);
				AgencyCashDataDO pcIdDO = pcIdQuery.getSingleResult();
				BigDecimal cashAmt = new BigDecimal(pcIdDO.getCash_amount());
				Query<AgencyCashDataDO> pcQuery = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and ((t_date = ?2 and created_date >= ?3) OR (t_date > ?2)) and trans_type != 11 order by t_date , created_date ");
				pcQuery.setParameter(1, dataDO.getCreated_by());
				pcQuery.setParameter(2, dataDO.getPymt_date());
				pcQuery.setParameter(3, dataDO.getCreated_date());
				List<AgencyCashDataDO> pcresult = pcQuery.getResultList();
				if (!(pcresult.isEmpty())) {
					for (AgencyCashDataDO pcDO : pcresult) {
						if (!(("0.00").equals(pcIdDO.getCash_amount()))) {
							BigDecimal cashBal = new BigDecimal(pcDO.getCash_balance());
							pcDO.setCash_balance((cashBal.add(cashAmt)).toString());
						}
						session.update(pcDO);
					}
				}
				pcIdDO.setTrans_type(11);
				session.update(pcIdDO);
			}
			/*--------------------------------- End Cash Calculation --------------------------------*/

			if (dataDO.getCust_ven() != 3) {
				if (dataDO.getPaid_to() > 0) {
					Query<AgencyCVOBalanceDataDO> pQuery = session.createQuery(
							"from AgencyCVOBalanceDataDO where created_by = ?1 and ((inv_date = ?2 and created_date >= ?3) OR (inv_date > ?2)) and deleted != 3 and cvo_refid = ?4 order by inv_date, created_date");
					pQuery.setParameter(1, dataDO.getCreated_by());
					pQuery.setParameter(2, dataDO.getPymt_date());
					pQuery.setParameter(3, dataDO.getCreated_date());
					pQuery.setParameter(4, dataDO.getPaid_to());

					List<AgencyCVOBalanceDataDO> presult = pQuery.getResultList();
					if (!(presult.isEmpty())) {
						/*--------------------------------- Start CVO Calculation --------------------------------*/
						// here update cvo bal(CBAL).
						if ((dataDO.getCust_ven() == 2) && (dataDO.getPayment_mode() == 2)) { // subtract
							for (AgencyCVOBalanceDataDO pDO : presult) {
								BigDecimal cbal = new BigDecimal(pDO.getCbal_amount());
								pDO.setCbal_amount((cbal.subtract(samt)).toString());
								session.update(pDO);
							}
						} else { // add
							for (AgencyCVOBalanceDataDO pDO : presult) {
								BigDecimal cbal = new BigDecimal(pDO.getCbal_amount());
								pDO.setCbal_amount((cbal.add(samt)).toString());
								session.update(pDO);
							}
						}
						/*--------------------------------- End CVO Calculation --------------------------------*/
					}
				}
			} else {
				BigDecimal gamt = new BigDecimal(gstpDataDO.getGst_amount());

				Query<GSTPaymentDetailsDO> gstpQuery = session.createQuery(
						"from GSTPaymentDetailsDO where created_by = ?1 and ((pdate = ?2 and created_date >= ?3) OR (pdate > ?2)) and deleted != 3");
				gstpQuery.setParameter(1, dataDO.getCreated_by());
				gstpQuery.setParameter(2, gstpDataDO.getPdate());
				gstpQuery.setParameter(3, gstpDataDO.getCreated_date());
				List<GSTPaymentDetailsDO> gstpresult = gstpQuery.getResultList();
				if (!(gstpresult.isEmpty())) {
					for (GSTPaymentDetailsDO gstpDO : gstpresult) {
						/*--------------------------------- Start TAX Calculation --------------------------------*/
						BigDecimal cbal = new BigDecimal(gstpDO.getDs_total_gst_amount());
						if (gstpDO.getTax_type() == gstpDataDO.getTax_type()) {
							if (gstpDataDO.getTax_type() == 1) { // Present Only GST tax ledger is present and income
																	// tax is not there.
								gstpDO.setTotal_gst_amount((cbal.subtract(gamt)).toString());
								gstpDO.setDs_total_gst_amount((cbal.subtract(gamt)).toString());
								session.update(gstpDO);
							}
						}
						/*--------------------------------- End TAX Calculation --------------------------------*/
					}
				}
				gstpDataDO.setDeleted(3);
				session.update(gstpDataDO);
			}
			dataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInPayments  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
	}

	/*------------------------------------------------------------ DELETE IN CREDIT NOTES ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInCreditNote(long agencyId, CreditNotesDO dataDO, int cvoCat) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			BigDecimal samt = new BigDecimal(dataDO.getCredit_amount());

			/*---------------------------------------------- Start CVO Calculation -------------------------------------------*/
			if (!(dataDO.getCredit_note_type() == 1 && cvoCat == 2)) {
				// here update cvo bal(CBAL).
				Query<AgencyCVOBalanceDataDO> crQuery = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and ((inv_date = ?2 and created_date >= ?3) OR (inv_date > ?2)) and deleted != 3 and cvo_refid = ?4 order by inv_date, created_date ");
				crQuery.setParameter(1, agencyId);
				crQuery.setParameter(2, dataDO.getNote_date());
				crQuery.setParameter(3, dataDO.getCreated_date());
				crQuery.setParameter(4, dataDO.getCvo_id());

				List<AgencyCVOBalanceDataDO> crresult = crQuery.getResultList();
				if (!(crresult.isEmpty())) {
					for (AgencyCVOBalanceDataDO crDO : crresult) {
						BigDecimal cbal = new BigDecimal(crDO.getCbal_amount());
						crDO.setCbal_amount((cbal.add(samt)).toString());
						session.update(crDO);
					}
				}
			}
			/*---------------------------------------------- End CVO Calculation -------------------------------------------*/

			dataDO.setDeleted(3);
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInCreditNote  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/*------------------------------------------------------------ DELETE IN DEBIT NOTES ------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInDebitNote(long agencyId, DebitNotesDO dataDO, int cvoCat) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			BigDecimal samt = new BigDecimal(dataDO.getDebit_amount());

			/*--------------------------------------------- Start CVO Calculation ---------------------------------------------*/
			if (!(dataDO.getDebit_note_type() == 1 && cvoCat == 2)) {
				Query<AgencyCVOBalanceDataDO> drQuery = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and ((inv_date = ?2 and created_date >= ?3) OR (inv_date > ?2)) and deleted != 3 and cvo_refid = ?4 order by inv_date, created_date");
				drQuery.setParameter(1, agencyId);
				drQuery.setParameter(2, dataDO.getNote_date());
				drQuery.setParameter(3, dataDO.getCreated_date());
				drQuery.setParameter(4, dataDO.getCvo_id());

				List<AgencyCVOBalanceDataDO> drresult = drQuery.getResultList();
				if (!(drresult.isEmpty())) {
					for (AgencyCVOBalanceDataDO drDO : drresult) {
						BigDecimal cbal = new BigDecimal(drDO.getCbal_amount());
						drDO.setCbal_amount((cbal.subtract(samt)).toString());
						session.update(drDO);
					}
				}
			}
			/*----------------------------------------------- End CVO Calculation -----------------------------------------------*/

			dataDO.setDeleted(3);
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInDebitNote  is not succesful");
				logger.error(e1);
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/*------------------------------------------------------------ DELETE IN ASSETS------------------------------------------------------------------*/

	@SuppressWarnings("unchecked")
	public synchronized void updateDataOnDeleteInAssets(AssetDataDO dataDO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			/*--------------------------------- Start Cash Calculation --------------------------------*/
			String srno = Long.toString(dataDO.getId());
			if (dataDO.getAsset_ttype() == 2 && dataDO.getAsset_mop() == 1 && dataDO.getBank_id() == 0) {
				Query<AgencyCashDataDO> ascIdQuery = session
						.createQuery("from AgencyCashDataDO where created_by = ?1 and inv_no = ?2 and trans_type=8")
						.setMaxResults(1);
				ascIdQuery.setParameter(1, dataDO.getCreated_by());
				ascIdQuery.setParameter(2, srno);
				AgencyCashDataDO ascIdDO = ascIdQuery.getSingleResult();
				BigDecimal cashAmt = new BigDecimal(ascIdDO.getCash_amount());

				Query<AgencyCashDataDO> ascQuery = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and ((t_date = ?2 and created_date >= ?3) OR (t_date > ?2)) and trans_type != 11 order by t_date, created_date ");
				ascQuery.setParameter(1, dataDO.getCreated_by());
				ascQuery.setParameter(2, dataDO.getAsset_tdate());
				ascQuery.setParameter(3, dataDO.getCreated_date());
				List<AgencyCashDataDO> ascresult = ascQuery.getResultList();
				if (!(ascresult.isEmpty())) {
					for (AgencyCashDataDO ascDO : ascresult) {
						if (!(("0.00").equals(ascIdDO.getCash_amount()))) {
							BigDecimal cashBal = new BigDecimal(ascDO.getCash_balance());
							ascDO.setCash_balance((cashBal.subtract(cashAmt)).toString());
						}
						session.update(ascDO);
					}
				}
				ascIdDO.setTrans_type(11);
				session.update(ascIdDO);
			}
			/*--------------------------------- End Cash Calculation --------------------------------*/

			dataDO.setDeleted(3);
			session.flush();
			session.clear();
			session.update(dataDO);

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> UpdateDataOnDeletingTransactions --> updateDataOnDeleteInAssets  is not succesful");
				logger.error(e1);
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
