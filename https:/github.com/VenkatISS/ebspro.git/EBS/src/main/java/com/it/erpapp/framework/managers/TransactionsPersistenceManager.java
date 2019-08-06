package com.it.erpapp.framework.managers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.it.erpapp.commons.UpdateDataOnDeletingTransactions;
import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.framework.model.AgencyCashDataDO;
import com.it.erpapp.framework.model.AgencySerialNosDO;
import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.BankDataDO;
import com.it.erpapp.framework.model.CVODataDO;
import com.it.erpapp.framework.model.pps.ARBDataDO;
import com.it.erpapp.framework.model.pps.EquipmentDataDO;
import com.it.erpapp.framework.model.transactions.ARBPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.OtherPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.PurchaseInvoiceDO;
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
import com.it.erpapp.framework.model.transactions.sales.DeliveryChallanDO;
import com.it.erpapp.framework.model.transactions.sales.DeliveryChallanDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.QuotationDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.QuotationsDO;
import com.it.erpapp.framework.model.transactions.sales.RCDataDO;
import com.it.erpapp.framework.model.transactions.sales.TVDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

public class TransactionsPersistenceManager {

	Logger logger = Logger.getLogger(TransactionsPersistenceManager.class.getName());

	int cyear = Calendar.getInstance().get(Calendar.YEAR);
	String cfy = (Integer.toString(cyear)).substring(2);
	
	int cmonth = Calendar.getInstance().get(Calendar.MONTH);
	int cApril = Calendar.APRIL;

	// Purchase Invoices
	public List<PurchaseInvoiceDO> getAgencyPurchaseInvoices(long agencyId) throws BusinessException {
		List<PurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<PurchaseInvoiceDO> query = session
					.createQuery("from PurchaseInvoiceDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<PurchaseInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (PurchaseInvoiceDO dataDO : result) {
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAgencyPurchaseInvoices(List<PurchaseInvoiceDO> doList, List<BankTranxsDataDO> btList,
			List<AgencyStockDataDO> asdDOList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<Long> refIds = new ArrayList<>();
			List<Long> createdDates = new ArrayList<>();
			for (PurchaseInvoiceDO dataDO : doList) {
				dataDO.setCreated_date(System.currentTimeMillis());
				createdDates.add(dataDO.getCreated_date());

				int csfulls = 0;
				int csempties = 0;

				int productCode = dataDO.getProd_code();
				Query<EquipmentDataDO> query = session
						.createQuery("from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				query.setParameter(3, productCode);
				List<EquipmentDataDO> result = query.getResultList();
				if (result.size() > 0) {
					for (EquipmentDataDO doObj : result) {
						int cfs = doObj.getCs_fulls();
						doObj.setCs_fulls(cfs + dataDO.getQuantity());
						if (dataDO.getEmr_or_delv() == 2) {
							int ces = doObj.getCs_emptys();
							doObj.setCs_emptys(ces - dataDO.getQuantity());
						}
						session.save(doObj);
						csfulls = doObj.getCs_fulls();
						csempties = doObj.getCs_emptys();
					}
				}

				Query<AgencyStockDataDO> checkDataQ = session.createQuery(
						"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date > ?4");
				checkDataQ.setParameter(1, dataDO.getCreated_by());
				checkDataQ.setParameter(2, 0);
				checkDataQ.setParameter(3, dataDO.getProd_code());
				checkDataQ.setParameter(4, dataDO.getStk_recvd_date());
				List<AgencyStockDataDO> checkDataR = checkDataQ.getResultList();

				// saving dataDO if backdate is entered
				if (!(checkDataR.isEmpty())) {

					// select * from AGENCY_STOCK_DATA WHERE CREATED_BY=1717171717 AND DELETED = 0
					// AND PROD_CODE=1 AND TRANS_DATE <= 1524940200000 ORDER BY TRANS_DATE DESC ,
					// CREATED_DATE DESC LIMIT 1;
					Query<AgencyStockDataDO> sbalQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date <= ?4 "
									+ "order by trans_date desc , created_date desc")
							.setMaxResults(1);
					sbalQ.setParameter(1, dataDO.getCreated_by());
					sbalQ.setParameter(2, 0);
					sbalQ.setParameter(3, dataDO.getProd_code());
					sbalQ.setParameter(4, dataDO.getStk_recvd_date());

					List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
					if (!(sbalRes.isEmpty())) {
						for (AgencyStockDataDO asdatado : sbalRes) {
							int cfulls = asdatado.getCs_fulls();
							int cempties = asdatado.getCs_emptys();

							dataDO.setCs_fulls(cfulls + dataDO.getQuantity());
							if (dataDO.getEmr_or_delv() == 2) {
								dataDO.setCs_emptys(cempties - dataDO.getQuantity());
							}
							refIds.add((long) session.save(dataDO));

							for (AgencyStockDataDO asDO : asdDOList) {
								if (asDO.getProd_code() == dataDO.getProd_code()) {
									asDO.setRef_id(dataDO.getId());
									asDO.setFulls(dataDO.getQuantity());
									asDO.setCs_fulls(cfulls + dataDO.getQuantity());
									if (dataDO.getEmr_or_delv() == 2) {
										asDO.setEmpties(dataDO.getQuantity());
									}
									asDO.setCs_emptys(cempties - asDO.getEmpties());
									asDO.setCreated_date(dataDO.getCreated_date());
									session.save(asDO);
								}
							}

						}
					}
					// updating remaining records from the user entered date- when back date is
					// given
					// select * from AGENCY_STOCK_DATA WHERE CREATED_BY=1717171717 AND DELETED = 0
					// AND PROD_CODE=1 AND TRANS_DATE > 1524940200000 ORDER BY TRANS_DATE ,
					// CREATED_DATE ;
					Query<AgencyStockDataDO> balUpdateQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date > ?4 "
									+ "order by trans_date, created_date");
					balUpdateQ.setParameter(1, dataDO.getCreated_by());
					balUpdateQ.setParameter(2, 0);
					balUpdateQ.setParameter(3, dataDO.getProd_code());
					balUpdateQ.setParameter(4, dataDO.getStk_recvd_date());

					List<AgencyStockDataDO> balUpdateRes = balUpdateQ.getResultList();
					if (!(balUpdateRes.isEmpty())) {
						for (AgencyStockDataDO bUpInasDO : balUpdateRes) {
							bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls() + dataDO.getQuantity());
							if (dataDO.getEmr_or_delv() == 2) {
								bUpInasDO.setCs_emptys(bUpInasDO.getCs_emptys() - dataDO.getQuantity());
							}
							session.update(bUpInasDO);
						}
					}
				} else {
					// If User enters Forward date:
					dataDO.setCs_fulls(csfulls);
					dataDO.setCs_emptys(csempties);
					refIds.add((long) session.save(dataDO));

					for (AgencyStockDataDO asDO : asdDOList) {
						if (asDO.getProd_code() == dataDO.getProd_code()) {
							asDO.setRef_id(dataDO.getId());
							asDO.setFulls(dataDO.getQuantity());
							asDO.setCs_fulls(csfulls);
							if (dataDO.getEmr_or_delv() == 2) {
								asDO.setEmpties(dataDO.getQuantity());
							}
							asDO.setCs_emptys(csempties);
							asDO.setCreated_date(dataDO.getCreated_date());
							session.save(asDO);
						}
					}
				}
			}

			// Save Bank Transaction
			int loopcount = 0;
			for (BankTranxsDataDO bankTranxData : btList) {
				AgencySerialNosDO snoDO = session.get(AgencySerialNosDO.class, new Long(bankTranxData.getCreated_by()));
				if((snoDO.getFy() != Integer.parseInt(cfy)) && (cmonth == cApril)) {
					snoDO.setFy(Integer.parseInt(cfy));
					snoDO.setCnSno(1);
					snoDO.setCsSno(1);
					snoDO.setDcSno(1);
					snoDO.setDnSno(1);
					snoDO.setPrSno(1);
					snoDO.setQtSno(1);
					snoDO.setSiSno(1);
					
					snoDO.setBtSno(1);
					snoDO.setPmtsSno(1);
					snoDO.setRcptsSno(1);
					snoDO.setSrSno(1);
				}
				
				int nno = snoDO.getBtSno();
				long bankId = bankTranxData.getBank_id();
				int i = 0;
				if (bankId > 0) {
					bankTranxData.setRef_id(refIds.get(loopcount));
					bankTranxData.setBtflag(3);
					bankTranxData.setCreated_date(createdDates.get(loopcount));

					BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
					long rdate = 0;
					Query<BankTranxsDataDO> bquery = session
							.createQuery("from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id= ?3 "
									+ "order by bt_date,id asc")
							.setMaxResults(1);
					bquery.setParameter(1, bankTranxData.getCreated_by());
					bquery.setParameter(2, 0);
					bquery.setParameter(3, bankId);

					List<BankTranxsDataDO> bresult = bquery.getResultList();

					if ((!bresult.isEmpty()) && bankTranxData.getBt_date() < bresult.get(0).getBt_date()) {
						BigDecimal tamt = new BigDecimal(bankTranxData.getTrans_amount());
						BigDecimal tc = new BigDecimal(bankTranxData.getTrans_charges());
						BigDecimal btcb = new BigDecimal(bresult.get(0).getBank_acb());
						BigDecimal lbtcb = new BigDecimal(bresult.get(0).getTrans_amount());
						BigDecimal tc1 = new BigDecimal(bresult.get(0).getTrans_charges());
						BigDecimal btamt = new BigDecimal(0);
						if (bresult.get(0).getTrans_type() == 2 || bresult.get(0).getTrans_type() == 3) {
							btamt = btcb.subtract(lbtcb).add(tc1);
						} else {
							btamt = btcb.add(lbtcb).add(tc1);
						}
						if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
							bankTranxData.setBank_acb((btamt.add(tamt).subtract(tc)).toString());
						} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4
								|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9
								|| bankTranxData.getTrans_type() == 10) {
							bankTranxData.setBank_acb((btamt.subtract(tamt).subtract(tc)).toString());
						}
						bankTranxData.setSr_no(-1);
						session.save(bankTranxData);
					}

					Query<BankTranxsDataDO> bquery1 = session.createQuery(
							"from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id= ?3 and bt_date > ?4 "
									+ "order by bt_date asc,id asc");

					bquery1.setParameter(1, bankTranxData.getCreated_by());
					bquery1.setParameter(2, 0);
					bquery1.setParameter(3, bankId);
					bquery1.setParameter(4, bankTranxData.getBt_date());

					List<BankTranxsDataDO> bresult1 = bquery1.getResultList();

					BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
					BigDecimal tamt = new BigDecimal(bankTranxData.getTrans_amount());
					BigDecimal tc = new BigDecimal(bankTranxData.getTrans_charges());

					if (!bresult1.isEmpty()) {
						for (BankTranxsDataDO btdObj : bresult1) {
							if (bankTranxData.getBt_date() < btdObj.getBt_date()) {
								BigDecimal btcb = new BigDecimal(btdObj.getBank_acb());
								BigDecimal btamt = new BigDecimal(0);
								BigDecimal tc1 = new BigDecimal(btdObj.getTrans_charges());
								BigDecimal lbtcb = new BigDecimal(btdObj.getTrans_amount());

								if (btdObj.getTrans_type() == 2 || btdObj.getTrans_type() == 3) {
									btamt = btcb.subtract(lbtcb).add(tc1);
								} else {
									btamt = btcb.add(lbtcb).add(tc1);
								}

								if ((rdate == 0 && bankTranxData.getSr_no() != -1) || rdate > btdObj.getBt_date()) {
									if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
										bankTranxData.setBank_acb((btamt.add(tamt).subtract(tc)).toString());
									} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4
											|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9
											|| bankTranxData.getTrans_type() == 10) {
										bankTranxData.setBank_acb((btamt.subtract(tamt).subtract(tc)).toString());
									}
									bankTranxData.setSr_no(nno);
									snoDO.setBtSno(nno + 1);

									rdate = btdObj.getBt_date();
									session.save(snoDO);
									session.save(bankTranxData);

								}
								btdObj.setBank_acb((btcb.subtract(tamt).subtract(tc)).toString());

								Query query2 = session.createQuery("update BankTranxsDataDO b set b.bank_acb = :bcb "
										+ " where b.created_by = :cid and b.id= :id and b.bank_id = :bid and b.bt_date >= :bdate");
								query2.setParameter("bcb", btdObj.getBank_acb());
								query2.setParameter("cid", btdObj.getCreated_by());
								query2.setParameter("id", btdObj.getId());
								query2.setParameter("bid", btdObj.getBank_id());
								query2.setParameter("bdate", bankTranxData.getBt_date());
								query2.executeUpdate();
							}
						}
						if (bankTranxData.getSr_no() == -1) {
							bankTranxData.setSr_no(nno);
							snoDO.setBtSno(nno + 1);
							session.update(snoDO);
							session.save(bankTranxData);
						}
					} else {
						bankTranxData.setSr_no(nno);
						if (bankTranxData.getBank_id() == bankDataDO.getId()) {
							snoDO.setBtSno(nno + 1);

							if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
								bankDataDO.setAcc_cb(((ccb.add(tamt).subtract(tc))).toString());
							} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4
									|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9
									|| bankTranxData.getTrans_type() == 10) {
								bankDataDO.setAcc_cb(((ccb.subtract(tamt).subtract(tc))).toString());
							}

							bankTranxData.setBank_acb(bankDataDO.getAcc_cb());
							session.save(bankDataDO);
							session.save(bankTranxData);
							i++;
						}
					}
					if (i == 0) {
						if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
							bankDataDO.setAcc_cb(((ccb.add(tamt).subtract(tc))).toString());
						} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4
								|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9
								|| bankTranxData.getTrans_type() == 10) {
							bankDataDO.setAcc_cb(((ccb.subtract(tamt).subtract(tc))).toString());
						}
						session.update(bankDataDO);
					}
				}
				++loopcount;
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> saveAgencyPurchaseInvoices  is not succesful");
			}
			logger.error(e.getCause());
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void deleteAgencyPurchaseInvoices(long itemId, long bankId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			PurchaseInvoiceDO dataDO = session.get(PurchaseInvoiceDO.class, new Long(itemId));
			if (dataDO.getDeleted() == 0) {
				int productCode = dataDO.getProd_code();
				Query<EquipmentDataDO> query = session
						.createQuery("from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				query.setParameter(3, productCode);
				List<EquipmentDataDO> result = query.getResultList();
				if (result.size() > 0) {
					for (EquipmentDataDO doObj : result) {
						int cfs = doObj.getCs_fulls();
						doObj.setCs_fulls(cfs - dataDO.getQuantity());
						if (dataDO.getEmr_or_delv() == 2) {
							int ces = doObj.getCs_emptys();
							doObj.setCs_emptys(ces + dataDO.getQuantity());
						}
						dataDO.setDs_fulls(doObj.getCs_fulls());
						dataDO.setDs_emptys(doObj.getCs_emptys());
						session.save(doObj);
					}
				}

				Query<BankTranxsDataDO> btqry = session
						.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 3");
				btqry.setParameter(1, dataDO.getId());
				List<BankTranxsDataDO> btItemsResult = btqry.getResultList();
				if ((bankId != 0) && (!(btItemsResult.isEmpty()))) {
					for (BankTranxsDataDO btDetailsDO : btItemsResult) {
						BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
						BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
						BigDecimal tamt = new BigDecimal(dataDO.getC_amount());
						bankDataDO.setAcc_cb((ccb.add(tamt)).toString());
						session.save(bankDataDO);

						btDetailsDO.setModified_by(dataDO.getCreated_by());
						btDetailsDO.setModified_date(System.currentTimeMillis());
						btDetailsDO.setDbank_acb(bankDataDO.getAcc_cb());
						btDetailsDO.setDeleted(2);
						session.update(btDetailsDO);
					}
				}

				dataDO.setDeleted(2);
				dataDO.setModified_by(dataDO.getCreated_by());
				dataDO.setModified_date(System.currentTimeMillis());
				session.update(dataDO);

				Query<AgencyStockDataDO> asdataDO = session
						.createQuery("from AgencyStockDataDO where ref_id = ?1 and stock_flag = 1");
				asdataDO.setParameter(1, dataDO.getId());
				List<AgencyStockDataDO> asItemsResult = asdataDO.getResultList();
				if (!(asItemsResult.isEmpty())) {
					for (AgencyStockDataDO asdDO : asItemsResult) {
						asdDO.setDeleted(3);
						asdDO.setModified_date(System.currentTimeMillis());
						asdDO.setModified_by(dataDO.getCreated_by());
						session.update(asdDO);
					}
				}

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInCylinderPurchases(dataDO.getCreated_by(), dataDO);
				if ((bankId != 0) && (!(btItemsResult.isEmpty())))
					udodt.updateDataOnDeleteInBankTransactions(dataDO.getCreated_by(), btItemsResult);

			}
			tx.commit();

		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> deleteAgencyPurchaseInvoices  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// ARB Purchase Invoices
	public List<ARBPurchaseInvoiceDO> getAgencyARBPurchaseInvoices(long agencyId) throws BusinessException {
		List<ARBPurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<ARBPurchaseInvoiceDO> query = session
					.createQuery("from ARBPurchaseInvoiceDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<ARBPurchaseInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (ARBPurchaseInvoiceDO dataDO : result) {
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

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void saveAgencyARBPurchaseInvoices(List<ARBPurchaseInvoiceDO> doList, List<AgencyStockDataDO> asdoList,
			List<AgencyCVOBalanceDataDO> cvoDOList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<Long> list = new ArrayList<>();
			List<Long> createdDatesList = new ArrayList<>();
			int i = 0;
			for (ARBPurchaseInvoiceDO dataDO : doList) {
				dataDO.setCreated_date(System.currentTimeMillis());
				createdDatesList.add(dataDO.getCreated_date());

				// Save Vendor Balance

				if (dataDO.getVendor_id() > 0) {
					CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getVendor_id()));

					dataDO.setVbal_amount("0");
					session.save(dataDO);
					list.add(dataDO.getId());

					int j = 0;
					for (AgencyCVOBalanceDataDO cvoDOObj : cvoDOList) {
						if (i == j) {
							cvoDOObj.setRef_id(dataDO.getId());
							cvoDOObj.setCvo_cat(cvoDO.getCvo_cat());
							cvoDOObj.setAmount(dataDO.getC_amount());
							cvoDOObj.setCbal_amount(cvoDO.getCbal());
							cvoDOObj.setCreated_date(dataDO.getCreated_date());
							session.save(cvoDOObj);
						}
						++j;
					}
				} else {
					session.save(dataDO);
				}
				++i;

				int cstock = 0;
				int prodcode = 0;
				Query<ARBDataDO> query = session
						.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 and id = ?3");
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				query.setParameter(3, dataDO.getArb_code());
				List<ARBDataDO> result = query.getResultList();
				if (result.size() > 0) {
					for (ARBDataDO doObj : result) {
						int cs = doObj.getCurrent_stock();
						cs = cs + dataDO.getQuantity();
						doObj.setCurrent_stock(cs);
						session.save(doObj);
						cstock = doObj.getCurrent_stock();
						prodcode = doObj.getProd_code();
					}
				}

				Query<AgencyStockDataDO> checkDataQ = session.createQuery(
						"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_id = ?3 and trans_date > ?4");
				checkDataQ.setParameter(1, dataDO.getCreated_by());
				checkDataQ.setParameter(2, 0);
				checkDataQ.setParameter(3, dataDO.getArb_code());
				checkDataQ.setParameter(4, dataDO.getStk_recvd_date());
				List<AgencyStockDataDO> checkDataR = checkDataQ.getResultList();

				if (!(checkDataR.isEmpty())) {
					// saving dataDO if backdate is entered
					// select * from AGENCY_STOCK_DATA WHERE CREATED_BY=1717171717 AND DELETED = 0
					// AND PROD_CODE=1 AND TRANS_DATE <= 1524940200000 ORDER BY TRANS_DATE DESC ,
					// CREATED_DATE DESC LIMIT 1;
					Query<AgencyStockDataDO> sbalQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_id = ?3 and trans_date <= ?4 "
									+ "order by trans_date desc , created_date desc")
							.setMaxResults(1);
					sbalQ.setParameter(1, dataDO.getCreated_by());
					sbalQ.setParameter(2, 0);
					sbalQ.setParameter(3, dataDO.getArb_code());
					sbalQ.setParameter(4, dataDO.getStk_recvd_date());

					List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
					if (!(sbalRes.isEmpty())) {
						for (AgencyStockDataDO asdatado : sbalRes) {
							int cfulls = asdatado.getCs_fulls();

							dataDO.setC_stock(cfulls + dataDO.getQuantity());
							session.save(dataDO);

							for (AgencyStockDataDO asDO : asdoList) {
								if (asDO.getProd_id() == dataDO.getArb_code()) {
									asDO.setRef_id(dataDO.getId());
									asDO.setProd_code(prodcode);
									asDO.setFulls(dataDO.getQuantity());
									asDO.setCs_fulls(cfulls + dataDO.getQuantity());
									asDO.setCreated_date(dataDO.getCreated_date());
									session.save(asDO);
								}
							}

						}
					}
					// updating remaining records from the user entered date- when back date is
					// given
					// select * from AGENCY_STOCK_DATA WHERE CREATED_BY=1717171717 AND DELETED = 0
					// AND PROD_CODE=1 AND TRANS_DATE > 1524940200000 ORDER BY TRANS_DATE ,
					// CREATED_DATE ;
					Query<AgencyStockDataDO> balUpdateQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_id = ?3 and trans_date > ?4 "
									+ "order by trans_date, created_date");
					balUpdateQ.setParameter(1, dataDO.getCreated_by());
					balUpdateQ.setParameter(2, 0);
					balUpdateQ.setParameter(3, dataDO.getArb_code());
					balUpdateQ.setParameter(4, dataDO.getStk_recvd_date());

					List<AgencyStockDataDO> balUpdateRes = balUpdateQ.getResultList();
					if (!(balUpdateRes.isEmpty())) {
						for (AgencyStockDataDO bUpInasDO : balUpdateRes) {
							bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls() + dataDO.getQuantity());
							session.update(bUpInasDO);
						}
					}
				} else {
					// If User enters Forward date:
					dataDO.setC_stock(cstock);
					session.save(dataDO);

					for (AgencyStockDataDO asDO : asdoList) {
						if (asDO.getProd_id() == dataDO.getArb_code()) {
							asDO.setRef_id(dataDO.getId());
							asDO.setProd_code(prodcode);
							asDO.setFulls(dataDO.getQuantity());
							asDO.setCs_fulls(cstock);
							asDO.setCreated_date(dataDO.getCreated_date());
							session.save(asDO);
						}
					}
				}
			}

			// Save Vendor Balance

			int count = 0;
			for (AgencyCVOBalanceDataDO dataDO : cvoDOList) {
				CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCvo_refid()));

				dataDO.setCreated_date(createdDatesList.get(count));

				long ldate = 0;

				if (dataDO.getCvo_refid() > 0) {
					Query<AgencyCVOBalanceDataDO> query2 = session.createQuery(
							"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat= ?3 and cvo_refid= ?4 "
									+ " and inv_ref_no != ?5 order by inv_date,cbal_amount asc")
							.setMaxResults(1);
					query2.setParameter(1, dataDO.getCreated_by());
					query2.setParameter(2, 0);
					query2.setParameter(3, 0);
					query2.setParameter(4, dataDO.getCvo_refid());
					query2.setParameter(5, "NA");
					List<AgencyCVOBalanceDataDO> result2 = query2.getResultList();
					if (!result2.isEmpty()) {
						if (dataDO.getInv_date() < result2.get(0).getInv_date()) {
							BigDecimal cb = new BigDecimal(result2.get(0).getCbal_amount());
							BigDecimal iamt = new BigDecimal(dataDO.getAmount());
							BigDecimal ccb1 = new BigDecimal(result2.get(0).getAmount());

							if (result2.get(0).getCvoflag() == 6 || result2.get(0).getCvoflag() == 7
									|| result2.get(0).getCvoflag() == 81 || result2.get(0).getCvoflag() == 82
									|| result2.get(0).getCvoflag() == 10 || result2.get(0).getCvoflag() == 11) {

								dataDO.setCbal_amount((cb.add(ccb1).add(iamt)).toString());
							} else {
								dataDO.setCbal_amount((cb.subtract(ccb1).add(iamt)).toString());
							}
							dataDO.setRef_id(-1);
							session.save(dataDO);
						}
					}
					Query<AgencyCVOBalanceDataDO> query3 = session.createQuery(
							"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat= ?3 and cvo_refid= ?4 and inv_date > ?5 order by inv_date,id,cbal_amount");
					query3.setParameter(1, dataDO.getCreated_by());
					query3.setParameter(2, 0);
					query3.setParameter(3, 0);
					query3.setParameter(4, dataDO.getCvo_refid());
					query3.setParameter(5, dataDO.getInv_date());
					List<AgencyCVOBalanceDataDO> result3 = query3.getResultList();

					BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
					BigDecimal iamt = new BigDecimal(dataDO.getAmount());

					if (!result3.isEmpty()) {
						for (AgencyCVOBalanceDataDO dsido : result3) {

							if (dataDO.getInv_date() < dsido.getInv_date()) {

								BigDecimal btcb = new BigDecimal(dsido.getCbal_amount());
								BigDecimal lbtcb = new BigDecimal(dsido.getAmount());
								BigDecimal btamt = new BigDecimal(0);
								btamt = btcb.subtract(lbtcb);

								if (ldate == 0 || ldate > dsido.getInv_date()) {
									if (dsido.getCvoflag() == 6 || dsido.getCvoflag() == 7 || dsido.getCvoflag() == 81
											|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 10
											|| dsido.getCvoflag() == 11) {
										dataDO.setCbal_amount((btcb.add(lbtcb).add(iamt)).toString());
									} else {
										dataDO.setCbal_amount((btcb.subtract(lbtcb).add(iamt)).toString());
									}

									ldate = dsido.getInv_date();
									session.save(dataDO);
								}
								dsido.setCbal_amount((btcb.add(iamt)).toString());
								session.saveOrUpdate(dsido);
								Query query4 = session
										.createQuery("update AgencyCVOBalanceDataDO b set b.cbal_amount = :bcb"
												+ " where b.created_by = :cid and b.id= :id and b.cvo_refid = :bid and b.inv_date >= :bdate");
								query4.setParameter("bcb", dsido.getCbal_amount());
								query4.setParameter("cid", dsido.getCreated_by());
								query4.setParameter("id", dsido.getId());
								query4.setParameter("bid", dsido.getCvo_refid());
								query4.setParameter("bdate", dsido.getInv_date());
								query4.executeUpdate();
							}
						}
						if (dataDO.getRef_id() == -1) {
							dataDO.setRef_id(list.get(count));
							session.save(dataDO);
						}
					} else {

						dataDO.setCbal_amount(ccb.add(iamt).toString());
					}

					cvoDO.setCbal(ccb.add(iamt).toString());
					session.update(cvoDO);

				}
				if (dataDO.getRef_id() == 0) {
					dataDO.setRef_id(list.get(count));
				}
				dataDO.setCvo_refid(cvoDO.getId());
				dataDO.setCvoflag(1);

				session.save(dataDO);

				++count;
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> saveAgencyARBPurchaseInvoices  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void deleteARBAgencyPurchaseInvoices(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ARBPurchaseInvoiceDO dataDO = session.get(ARBPurchaseInvoiceDO.class, new Long(itemId));

			if (dataDO.getDeleted() == 0) {
				Query<ARBDataDO> query = session
						.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 and id = ?3");
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				query.setParameter(3, dataDO.getArb_code());
				List<ARBDataDO> result = query.getResultList();
				if (result.size() > 0) {
					for (ARBDataDO doObj : result) {
						int cs = doObj.getCurrent_stock();
						cs = cs - dataDO.getQuantity();
						doObj.setCurrent_stock(cs);
						dataDO.setD_stock(cs);
						session.save(doObj);
					}
				}
				// Save Vendor Balance
				if (dataDO.getVendor_id() > 0) {
					CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getVendor_id()));
					BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
					BigDecimal iamt = new BigDecimal(dataDO.getC_amount());
					cvoDO.setCbal(ccb.subtract(iamt).toString());
					session.update(cvoDO);
					dataDO.setDbal_amount(cvoDO.getCbal());
				}
				dataDO.setDeleted(2);
				dataDO.setModified_by(dataDO.getCreated_by());
				dataDO.setModified_date(System.currentTimeMillis());
				session.update(dataDO);

				Query<AgencyStockDataDO> asdataDO = session
						.createQuery("from AgencyStockDataDO where ref_id = ?1 and stock_flag = 2");
				asdataDO.setParameter(1, dataDO.getId());
				List<AgencyStockDataDO> asItemsResult = asdataDO.getResultList();
				if (!(asItemsResult.isEmpty())) {
					for (AgencyStockDataDO asdDO : asItemsResult) {
						asdDO.setDeleted(3);
						asdDO.setModified_date(System.currentTimeMillis());
						asdDO.setModified_by(dataDO.getCreated_by());
						session.update(asdDO);
					}
				}

				Query<AgencyCVOBalanceDataDO> cvoDataDO = session
						.createQuery("from AgencyCVOBalanceDataDO where ref_id = ?1 and cvoflag = 1 ");
				cvoDataDO.setParameter(1, dataDO.getId());
				List<AgencyCVOBalanceDataDO> cvoItemsResult = cvoDataDO.getResultList();
				if (!(cvoItemsResult.isEmpty())) {
					for (AgencyCVOBalanceDataDO cvodDO : cvoItemsResult) {
						cvodDO.setDeleted(3);
						cvodDO.setModified_date(System.currentTimeMillis());
						cvodDO.setModified_by(dataDO.getCreated_by());
						session.update(cvodDO);
					}
				}

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInARBPurchases(dataDO);

			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> deleteARBAgencyPurchaseInvoices  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Other Purchase Invoices
	public List<OtherPurchaseInvoiceDO> getAgencyOTHERPurchaseInvoices(long agencyId) throws BusinessException {
		List<OtherPurchaseInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<OtherPurchaseInvoiceDO> query = session
					.createQuery("from OtherPurchaseInvoiceDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<OtherPurchaseInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (OtherPurchaseInvoiceDO dataDO : result) {
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAgencyOTHERPurchaseInvoices(List<OtherPurchaseInvoiceDO> doList,
			List<AgencyCVOBalanceDataDO> cvoDOList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			int i = 0;
			List<Long> list = new ArrayList<>();
			List<Long> createdDatesList = new ArrayList<>();
			for (OtherPurchaseInvoiceDO dataDO : doList) {
				// Save Vendor Balance
				if (dataDO.getVendor_id() > 0) {
					CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getVendor_id()));
					dataDO.setVbal_amount("0");
					dataDO.setCreated_date(System.currentTimeMillis());
					createdDatesList.add(dataDO.getCreated_date());
					session.save(dataDO);
					list.add(dataDO.getId());

					int j = 0;
					for (AgencyCVOBalanceDataDO cvoDOObj : cvoDOList) {
						if (i == j) {
							cvoDOObj.setRef_id(dataDO.getId());
							cvoDOObj.setCvo_cat(cvoDO.getCvo_cat());
							cvoDOObj.setAmount(dataDO.getP_amount());
							cvoDOObj.setCbal_amount(cvoDO.getCbal());
							cvoDOObj.setCreated_date(dataDO.getCreated_date());
							session.save(cvoDOObj);
						}
						++j;
					}
				} else {
					session.save(dataDO);
				}
				++i;
			}

			// Save Vendor Balance
			int count = 0;
			for (AgencyCVOBalanceDataDO dataDO : cvoDOList) {
				CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCvo_refid()));
				dataDO.setCreated_date(createdDatesList.get(count));
				long ldate = 0;

				if (dataDO.getCvo_refid() > 0) {
					Query<AgencyCVOBalanceDataDO> query2 = session.createQuery(
							"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat= ?3 and cvo_refid= ?4 "
									+ " and inv_ref_no != ?5 order by inv_date,cbal_amount asc")
							.setMaxResults(1);
					query2.setParameter(1, dataDO.getCreated_by());
					query2.setParameter(2, 0);
					query2.setParameter(3, 3);
					query2.setParameter(4, dataDO.getCvo_refid());
					query2.setParameter(5, "NA");
					List<AgencyCVOBalanceDataDO> result2 = query2.getResultList();
					if (!result2.isEmpty()) {
						if (dataDO.getInv_date() < result2.get(0).getInv_date()) {
							BigDecimal ccb = new BigDecimal(result2.get(0).getCbal_amount());
							BigDecimal iamt = new BigDecimal(dataDO.getAmount());
							BigDecimal ccb1 = new BigDecimal(result2.get(0).getAmount());
							if (result2.get(0).getCvoflag() == 7) {
								ccb = ccb.add(ccb1);
							} else {
								ccb = ccb.subtract(ccb1);
							}
							dataDO.setCbal_amount((ccb.add(iamt)).toString());
							dataDO.setRef_id(-1);
							session.save(dataDO);
						}
					}
					Query<AgencyCVOBalanceDataDO> query3 = session.createQuery(
							"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat= ?3 and cvo_refid= ?4 and inv_date > ?5 order by inv_date,id,cbal_amount");
					query3.setParameter(1, dataDO.getCreated_by());
					query3.setParameter(2, 0);
					query3.setParameter(3, 3);
					query3.setParameter(4, dataDO.getCvo_refid());
					query3.setParameter(5, dataDO.getInv_date());
					List<AgencyCVOBalanceDataDO> result3 = query3.getResultList();

					BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
					BigDecimal iamt = new BigDecimal(dataDO.getAmount());

					if (!result3.isEmpty()) {
						for (AgencyCVOBalanceDataDO dsido : result3) {

							if (dataDO.getInv_date() < dsido.getInv_date()) {

								BigDecimal tamt = new BigDecimal(dataDO.getAmount());
								BigDecimal btcb = new BigDecimal(dsido.getCbal_amount());
								BigDecimal lbtcb = new BigDecimal(dsido.getAmount());
								BigDecimal btamt = new BigDecimal(0);
								if (result2.get(0).getCvoflag() == 7) {
									btamt = btcb.add(lbtcb);
								} else {
									btamt = btcb.subtract(lbtcb);
								}
								if (ldate == 0 || ldate > dsido.getInv_date()) {
									dataDO.setCbal_amount((btamt.add(tamt)).toString());
									dataDO.setRef_id((list.get(count)));

									ldate = dsido.getInv_date();
									session.save(dataDO);
								}
								dsido.setCbal_amount((btcb.add(tamt)).toString());
								session.saveOrUpdate(dsido);
								Query query4 = session
										.createQuery("update AgencyCVOBalanceDataDO b set b.cbal_amount = :bcb"
												+ " where b.created_by = :cid and b.id= :id and b.cvo_refid = :bid and b.inv_date >= :bdate");
								query4.setParameter("bcb", dsido.getCbal_amount());
								query4.setParameter("cid", dsido.getCreated_by());
								query4.setParameter("id", dsido.getId());
								query4.setParameter("bid", dsido.getCvo_refid());
								query4.setParameter("bdate", dsido.getInv_date());
								query4.executeUpdate();
							}
						}
						if (dataDO.getRef_id() == -1) {
							dataDO.setRef_id(list.get(count));
							session.save(dataDO);
						}
					} else {
						dataDO.setCbal_amount(ccb.add(iamt).toString());
					}
					cvoDO.setCbal(ccb.add(iamt).toString());
					session.update(cvoDO);
				}
				if (dataDO.getRef_id() == 0) {
					dataDO.setRef_id(list.get(count));
				}
				dataDO.setCvo_refid(cvoDO.getId());
				dataDO.setCvoflag(2);

				session.save(dataDO);

				++count;
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> saveAgencyOTHERPurchaseInvoices  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	public synchronized void deleteOTHERAgencyPurchaseInvoices(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			OtherPurchaseInvoiceDO dataDO = session.get(OtherPurchaseInvoiceDO.class, new Long(itemId));

			if (dataDO.getDeleted() == 0) {
				// Update Vendor Balance
				if (dataDO.getVendor_id() > 0) {
					CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getVendor_id()));
					BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
					BigDecimal iamt = new BigDecimal(dataDO.getP_amount());
					cvoDO.setCbal(ccb.subtract(iamt).toString());
					session.update(cvoDO);
					dataDO.setDbal_amount(cvoDO.getCbal());
				}
				dataDO.setDeleted(2);
				dataDO.setModified_by(dataDO.getCreated_by());
				dataDO.setModified_date(System.currentTimeMillis());
				session.update(dataDO);

				@SuppressWarnings("unchecked")
				Query<AgencyCVOBalanceDataDO> cvoDataDO = session
						.createQuery("from AgencyCVOBalanceDataDO where ref_id = ?1 and cvoflag = 2 ");
				cvoDataDO.setParameter(1, dataDO.getId());
				List<AgencyCVOBalanceDataDO> cvoItemsResult = cvoDataDO.getResultList();
				if (!(cvoItemsResult.isEmpty())) {
					for (AgencyCVOBalanceDataDO cvodDO : cvoItemsResult) {
						cvodDO.setDeleted(3);
						cvodDO.setModified_date(System.currentTimeMillis());
						cvodDO.setModified_by(dataDO.getCreated_by());
						session.update(cvodDO);
					}
				}

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInOtherPurchases(dataDO);
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> deleteOTHERAgencyPurchaseInvoices  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Quotations
	public List<QuotationsDO> getAgencyQuotations(long agencyId) throws BusinessException {
		List<QuotationsDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<QuotationsDO> query = session
					.createQuery("from QuotationsDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<QuotationsDO> result = query.getResultList();
			if (result.size() > 0) {
				for (QuotationsDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<QuotationDetailsDO> squery = session.createQuery("from QuotationDetailsDO where qtn_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<QuotationDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						dataDO.getQuotationItemsList().addAll(itemsResult);
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

	public void saveAgencyQuotations(QuotationsDO quotationDO, List<QuotationDetailsDO> doList)
			throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AgencySerialNosDO snoDO = session.get(AgencySerialNosDO.class, new Long(quotationDO.getCreated_by()));			
			if((snoDO.getFy() != Integer.parseInt(cfy)) && (cmonth == cApril)) {
				snoDO.setFy(Integer.parseInt(cfy));
				snoDO.setCnSno(1);
				snoDO.setCsSno(1);
				snoDO.setDcSno(1);
				snoDO.setDnSno(1);
				snoDO.setPrSno(1);
				snoDO.setQtSno(1);
				snoDO.setSiSno(1);
				
				snoDO.setBtSno(1);
				snoDO.setPmtsSno(1);
				snoDO.setRcptsSno(1);
				snoDO.setSrSno(1);
			}
			
			int nno = snoDO.getQtSno();
			if (nno < 10) {
				quotationDO.setSr_no("QT-" + snoDO.getFy() + "-000" + nno);
			} else if (nno < 100) {
				quotationDO.setSr_no("QT-" + snoDO.getFy() + "-00" + nno);
			} else if (nno < 1000) {
				quotationDO.setSr_no("QT-" + snoDO.getFy() + "-0" + nno);
			} else {
				quotationDO.setSr_no("QT-" + snoDO.getFy() + "-" + nno);
			}
			snoDO.setQtSno(nno + 1);
			session.update(snoDO);
			session.save(quotationDO);
			for (QuotationDetailsDO dataDO : doList) {
				dataDO.setQtn_id(quotationDO.getId());
				session.save(dataDO);
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> saveAgencyQuotations  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	public void deleteQuotations(long itemId, long agencyId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			QuotationsDO quotationDO = session.get(QuotationsDO.class, new Long(itemId));
			quotationDO.setDeleted(1);
			session.update(quotationDO);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> deleteQuotations  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	public QuotationsDO getAgencyQSInvoiceById(long invoiceId) throws BusinessException {
		QuotationsDO rdo = new QuotationsDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<QuotationsDO> query = session.createQuery("from QuotationsDO where id = ?1 and deleted = ?2 ");
			query.setParameter(1, invoiceId);
			query.setParameter(2, 0);
			List<QuotationsDO> result = query.getResultList();
			if (result.size() > 0) {
				for (QuotationsDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<QuotationDetailsDO> squery = session.createQuery("from QuotationDetailsDO where qtn_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<QuotationDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						dataDO.getQuotationItemsList().addAll(itemsResult);
					}
					rdo = dataDO;
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
		return rdo;
	}

	// DOM Sales Invoices
	public List<DOMSalesInvoiceDO> getAgencyDSInvoices(long agencyId) throws BusinessException {
		List<DOMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<DOMSalesInvoiceDO> query = session
					.createQuery("from DOMSalesInvoiceDO where created_by = ?1 and (deleted = ?2 or deleted = ?3)");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			List<DOMSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (DOMSalesInvoiceDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<DOMSalesInvoiceDetailsDO> squery = session
							.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						if (dataDO.getDeleted() == 2) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String time = sdf.format(dataDO.getModified_date());
							dataDO.setSi_date(sdf.parse(time).getTime());
						}
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

	public DOMSalesInvoiceDO getAgencyDSInvoiceById(long invoiceId) throws BusinessException {
		DOMSalesInvoiceDO rdo = new DOMSalesInvoiceDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<DOMSalesInvoiceDO> query = session
					.createQuery("from DOMSalesInvoiceDO where id = ?1 and deleted = ?2 ");
			query.setParameter(1, invoiceId);
			query.setParameter(2, 0);
			List<DOMSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (DOMSalesInvoiceDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<DOMSalesInvoiceDetailsDO> squery = session
							.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						dataDO.getDetailsList().addAll(itemsResult);
					}
					rdo = dataDO;
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
		return rdo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAgencyDSInvoices(DOMSalesInvoiceDO dataDO, List<DOMSalesInvoiceDetailsDO> doList,
			List<BankTranxsDataDO> btList, List<AgencyStockDataDO> asdoList, AgencyCVOBalanceDataDO cdataDO)
			throws BusinessException {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AgencySerialNosDO snoDO = session.get(AgencySerialNosDO.class, new Long(dataDO.getCreated_by()));
			if((snoDO.getFy() != Integer.parseInt(cfy)) && (cmonth == cApril)) {
				snoDO.setFy(Integer.parseInt(cfy));
				snoDO.setCnSno(1);
				snoDO.setCsSno(1);
				snoDO.setDcSno(1);
				snoDO.setDnSno(1);
				snoDO.setPrSno(1);
				snoDO.setQtSno(1);
				snoDO.setSiSno(1);
				
				snoDO.setBtSno(1);
				snoDO.setPmtsSno(1);
				snoDO.setRcptsSno(1);
				snoDO.setSrSno(1);
			}
			List<Long> list = new ArrayList<>();
			long refId = 0;

			if (dataDO.getCustomer_id() > 0) {
				Query<CVODataDO> query = session.createQuery(
						"from CVODataDO where created_by = ?1 and deleted = ?2 and is_gst_reg = ?3 and id = ?4");
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				query.setParameter(3, 1);
				query.setParameter(4, dataDO.getCustomer_id());
				List<CVODataDO> result = query.getResultList();
				if (result.size() > 0) {
					int nno = snoDO.getSiSno();
					if (nno < 10) {
						dataDO.setSr_no("SI-" + snoDO.getFy() + "-000" + nno);
					} else if (nno < 100) {
						dataDO.setSr_no("SI-" + snoDO.getFy() + "-00" + nno);
					} else if (nno < 1000) {
						dataDO.setSr_no("SI-" + snoDO.getFy() + "-0" + nno);
					} else {
						dataDO.setSr_no("SI-" + snoDO.getFy() + "-" + nno);
					}
					snoDO.setSiSno(nno + 1);
					session.update(snoDO);
					refId = (long) session.save(dataDO);
				} else {
					Query<CVODataDO> query2 = session.createQuery(
							"from CVODataDO where created_by = ?1 and deleted = ?2 and is_gst_reg = ?3 and id = ?4");
					query2.setParameter(1, dataDO.getCreated_by());
					query2.setParameter(2, 0);
					query2.setParameter(3, 2);
					query2.setParameter(4, dataDO.getCustomer_id());
					List<CVODataDO> result2 = query2.getResultList();
					if (!result2.isEmpty()) {
						int nno = snoDO.getCsSno();
						if (nno < 10) {
							dataDO.setSr_no("CS-" + snoDO.getFy() + "-000" + nno);
						} else if (nno < 100) {
							dataDO.setSr_no("CS-" + snoDO.getFy() + "-00" + nno);
						} else if (nno < 1000) {
							dataDO.setSr_no("CS-" + snoDO.getFy() + "-0" + nno);
						} else {
							dataDO.setSr_no("CS-" + snoDO.getFy() + "-" + nno);
						}
						snoDO.setCsSno(nno + 1);
						session.update(snoDO);
						refId = (long) session.save(dataDO);
					}
				}
			} else if (dataDO.getCustomer_id() == 0) {
				int nno = snoDO.getCsSno();
				if (nno < 10) {
					dataDO.setSr_no("CS-" + snoDO.getFy() + "-000" + nno);
				} else if (nno < 100) {
					dataDO.setSr_no("CS-" + snoDO.getFy() + "-00" + nno);
				} else if (nno < 1000) {
					dataDO.setSr_no("CS-" + snoDO.getFy() + "-0" + nno);
				} else {
					dataDO.setSr_no("CS-" + snoDO.getFy() + "-" + nno);
				}
				snoDO.setCsSno(nno + 1);
				session.update(snoDO);
				refId = (long) session.save(dataDO);
			}

			// Save Customer Details
			if (dataDO.getCustomer_id() > 0) {
				dataDO.setCbal_amount("0");

				session.save(dataDO);
				list.add(dataDO.getId());
			}
			// Save Customer Details and backdates
			if (cdataDO.getCvo_refid() > 0) {
				long count = 0;
				CVODataDO cvoDO = session.get(CVODataDO.class, new Long(cdataDO.getCvo_refid()));
				BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
				BigDecimal siamt = new BigDecimal(cdataDO.getAmount());
				long rdate = 0;

				Query<AgencyCVOBalanceDataDO> query2 = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat= ?3 and cvo_refid= ?4 "
								+ " and inv_ref_no != ?5 order by inv_date,id")
						.setMaxResults(1);
				query2.setParameter(1, cdataDO.getCreated_by());
				query2.setParameter(2, 0);
				query2.setParameter(3, 1);
				query2.setParameter(4, cdataDO.getCvo_refid());
				query2.setParameter(5, "NA");
				List<AgencyCVOBalanceDataDO> result2 = query2.getResultList();
				if (result2.size() > 0) {
					if (cdataDO.getInv_date() < result2.get(0).getInv_date()) {
						BigDecimal cb = new BigDecimal(result2.get(0).getCbal_amount());
						BigDecimal iamt = new BigDecimal(cdataDO.getAmount());
						BigDecimal ccb1 = new BigDecimal(result2.get(0).getAmount());
						if (cdataDO.getCvoflag() == 31) {
							if (result2.get(0).getCvoflag() == 31 || result2.get(0).getCvoflag() == 41
									|| result2.get(0).getCvoflag() == 51) {
								cdataDO.setCbal_amount(cb.toString());
							} else if (result2.get(0).getCvoflag() == 6 || result2.get(0).getCvoflag() == 81
									|| result2.get(0).getCvoflag() == 82 || result2.get(0).getCvoflag() == 10
									|| result2.get(0).getCvoflag() == 11) {

								cdataDO.setCbal_amount((cb.add(ccb1)).toString());
							} else {
								cdataDO.setCbal_amount((cb.subtract(ccb1)).toString());
							}

						} else {
							if (result2.get(0).getCvoflag() == 31 || result2.get(0).getCvoflag() == 41
									|| result2.get(0).getCvoflag() == 51) {
								cdataDO.setCbal_amount((cb.add(iamt)).toString());
							} else if (result2.get(0).getCvoflag() == 6 || result2.get(0).getCvoflag() == 81
									|| result2.get(0).getCvoflag() == 82 || result2.get(0).getCvoflag() == 10
									|| result2.get(0).getCvoflag() == 11) {

								cdataDO.setCbal_amount((cb.add(ccb1).add(iamt)).toString());
							} else {
								cdataDO.setCbal_amount((cb.subtract(ccb1).add(iamt)).toString());
							}
						}

						cdataDO.setRef_id(-1);
						session.save(dataDO);
					}
				}
				Query<AgencyCVOBalanceDataDO> query3 = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat= ?3 and cvo_refid = ?4"
								+ " and inv_date > ?5 order by inv_date,id");
				query3.setParameter(1, cdataDO.getCreated_by());
				query3.setParameter(2, 0);
				query3.setParameter(3, 1);
				query3.setParameter(4, cdataDO.getCvo_refid());
				query3.setParameter(5, cdataDO.getInv_date());

				List<AgencyCVOBalanceDataDO> result3 = query3.getResultList();

				if (result3.size() > 0) {
					for (AgencyCVOBalanceDataDO dsido : result3) {
						BigDecimal ccb1 = new BigDecimal(dsido.getCbal_amount());
						BigDecimal iamt = new BigDecimal(cdataDO.getAmount());
						BigDecimal lamt = new BigDecimal(dsido.getAmount());

						if (cdataDO.getInv_date() < dsido.getInv_date()) {
							if ((rdate == 0 && cdataDO.getRef_id() != -1) || rdate > dsido.getInv_date()) {

								if (cdataDO.getCvoflag() == 31) {

									if (dsido.getCvoflag() == 31 || dsido.getCvoflag() == 41
											|| dsido.getCvoflag() == 51) {
										cdataDO.setCbal_amount(ccb1.toString());

									} else if (dsido.getCvoflag() == 6 || dsido.getCvoflag() == 81
											|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 10
											|| dsido.getCvoflag() == 11) {

										cdataDO.setCbal_amount((ccb1.add(lamt)).toString());
									} else {
										cdataDO.setCbal_amount((ccb1.subtract(lamt)).toString());

									}

								} else {
									if (dsido.getCvoflag() == 31 || dsido.getCvoflag() == 41
											|| dsido.getCvoflag() == 51) {
										cdataDO.setCbal_amount((ccb1.add(iamt)).toString());

									} else if (dsido.getCvoflag() == 6 || dsido.getCvoflag() == 81
											|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 10
											|| dsido.getCvoflag() == 11) {

										cdataDO.setCbal_amount((ccb1.add(iamt).add(lamt)).toString());
									} else {
										cdataDO.setCbal_amount((ccb1.add(iamt).subtract(lamt)).toString());

									}
								}
								cdataDO.setRef_id((list.get((int) count)));
								cdataDO.setInv_ref_no(dataDO.getSr_no());
								rdate = dsido.getInv_date();
								cdataDO.setCreated_date(dataDO.getCreated_date());
								session.save(cdataDO);
								refId = (long) session.save(dataDO);
							}
							int x = 0;
							if (cdataDO.getCvoflag() == 31) {
								++x;
							} else {
								dsido.setCbal_amount((ccb1.add(iamt)).toString());
							}
							if (x == 0) {
								Query query4 = session
										.createQuery("update AgencyCVOBalanceDataDO b set b.cbal_amount = :bcb"
												+ " where b.created_by = :cid and b.id= :id and  b.cvo_refid = :bid and b.inv_date >= :bdate");
								query4.setParameter("bcb", dsido.getCbal_amount());
								query4.setParameter("cid", dsido.getCreated_by());
								query4.setParameter("id", dsido.getId());
								query4.setParameter("bid", dsido.getCvo_refid());
								query4.setParameter("bdate", dsido.getInv_date());
								query4.executeUpdate();
							}
						}
					}
					if (cdataDO.getRef_id() == -1) {
						cdataDO.setRef_id(list.get((int) count));
						cdataDO.setInv_ref_no(dataDO.getSr_no());
						session.save(dataDO);
					}
				} else {
					cdataDO.setInv_ref_no(dataDO.getSr_no());
					if (cdataDO.getCvoflag() == 31) {
						cvoDO.setCbal(ccb.toString());
						cdataDO.setCbal_amount(ccb.toString());

					} else {
						cvoDO.setCbal(ccb.add(siamt).toString());
						cdataDO.setCbal_amount(ccb.add(siamt).toString());
					}
				}
				if (cdataDO.getRef_id() == 0) {
					cdataDO.setRef_id(list.get((int) count));
				}
				if (cdataDO.getCvoflag() == 31) {
					cvoDO.setCbal(ccb.toString());
				} else {
					cvoDO.setCbal(ccb.add(siamt).toString());
				}
				session.update(cvoDO);
				cdataDO.setCvo_refid(cvoDO.getId());
				cdataDO.setCvo_cat(cvoDO.getCvo_cat());
				cdataDO.setCreated_date(dataDO.getCreated_date());
				session.save(cdataDO);
			}

			for (DOMSalesInvoiceDetailsDO dataDetailsDO : doList) {
				int productCode = dataDetailsDO.getProd_code();
				dataDetailsDO.setDsi_id(dataDO.getId());
				int csfulls = 0;
				int csempties = 0;

				BigDecimal qty = new BigDecimal(dataDetailsDO.getQuantity());
				BigDecimal psv = new BigDecimal(dataDetailsDO.getPsv_cyls());
				BigDecimal disc = new BigDecimal(dataDetailsDO.getDisc_unit_rate());

				BigDecimal discount = (qty.subtract(psv)).multiply(disc);

				Query<EquipmentDataDO> query = session
						.createQuery("from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				query.setParameter(3, productCode);
				List<EquipmentDataDO> result = query.getResultList();
				if (result.size() > 0) {
					for (EquipmentDataDO doObj : result) {
						int cfs = doObj.getCs_fulls();
						int ces = doObj.getCs_emptys();
						doObj.setCs_fulls(cfs - dataDetailsDO.getQuantity());
						doObj.setCs_emptys(ces + dataDetailsDO.getQuantity() - dataDetailsDO.getPsv_cyls());
						session.save(doObj);
						csfulls = doObj.getCs_fulls();
						csempties = doObj.getCs_emptys();
					}
				}

				Query<AgencyStockDataDO> checkDataQ = session.createQuery(
						"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date > ?4");
				checkDataQ.setParameter(1, dataDO.getCreated_by());
				checkDataQ.setParameter(2, 0);
				checkDataQ.setParameter(3, productCode);
				checkDataQ.setParameter(4, dataDO.getSi_date());
				List<AgencyStockDataDO> checkDataR = checkDataQ.getResultList();

				if (!(checkDataR.isEmpty())) {
					// saving dataDO if backdate is entered
					Query<AgencyStockDataDO> sbalQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date <= ?4 "
									+ "order by trans_date desc , created_date desc")
							.setMaxResults(1);
					sbalQ.setParameter(1, dataDO.getCreated_by());
					sbalQ.setParameter(2, 0);
					sbalQ.setParameter(3, productCode);
					sbalQ.setParameter(4, dataDO.getSi_date());

					List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
					if (!(sbalRes.isEmpty())) {
						for (AgencyStockDataDO asdatado : sbalRes) {

							int cfulls = asdatado.getCs_fulls();
							int cempties = asdatado.getCs_emptys();

							dataDetailsDO.setCs_fulls(cfulls - dataDetailsDO.getQuantity());
							dataDetailsDO
									.setCs_emptys(cempties + dataDetailsDO.getQuantity() - dataDetailsDO.getPsv_cyls());
							session.save(dataDetailsDO);

							for (AgencyStockDataDO asDO : asdoList) {
								if (asDO.getProd_code() == productCode) {
									asDO.setRef_id(dataDO.getId());
									asDO.setInv_no(dataDO.getSr_no());
									asDO.setFulls(dataDetailsDO.getQuantity());
									asDO.setEmpties(dataDetailsDO.getQuantity() - dataDetailsDO.getPsv_cyls());
									asDO.setCs_fulls(cfulls - dataDetailsDO.getQuantity());
									asDO.setCs_emptys(
											cempties + dataDetailsDO.getQuantity() - dataDetailsDO.getPsv_cyls());
									asDO.setDiscount(discount.toString());
									asDO.setCreated_date(dataDO.getCreated_date());
									session.save(asDO);
								}
							}

						}
					}
					// updating remaining records from the user entered date- when back date is
					// given
					Query<AgencyStockDataDO> balUpdateQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date > ?4 "
									+ "order by trans_date, created_date");
					balUpdateQ.setParameter(1, dataDO.getCreated_by());
					balUpdateQ.setParameter(2, 0);
					balUpdateQ.setParameter(3, productCode);
					balUpdateQ.setParameter(4, dataDO.getSi_date());

					List<AgencyStockDataDO> balUpdateRes = balUpdateQ.getResultList();
					if (!(balUpdateRes.isEmpty())) {
						for (AgencyStockDataDO bUpInasDO : balUpdateRes) {
							bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls() - dataDetailsDO.getQuantity());
							bUpInasDO.setCs_emptys(bUpInasDO.getCs_emptys() + dataDetailsDO.getQuantity()
									- dataDetailsDO.getPsv_cyls());
							session.update(bUpInasDO);
						}
					}
				} else {
					// If User enters Forward date:

					dataDetailsDO.setCs_fulls(csfulls);
					dataDetailsDO.setCs_emptys(csempties);
					session.save(dataDetailsDO);

					for (AgencyStockDataDO asDO : asdoList) {
						if (asDO.getProd_code() == productCode) {
							asDO.setRef_id(dataDO.getId());
							asDO.setInv_no(dataDO.getSr_no());
							asDO.setFulls(dataDetailsDO.getQuantity());
							asDO.setEmpties(dataDetailsDO.getQuantity() - dataDetailsDO.getPsv_cyls());
							asDO.setCs_fulls(csfulls);
							asDO.setCs_emptys(csempties);
							asDO.setDiscount(discount.toString());
							asDO.setCreated_date(dataDO.getCreated_date());
							session.save(asDO);
						}
					}
				}

			}

			// Cash Backdates
			if (dataDO.getPayment_terms() != 2) {
				Query<AgencyCashDataDO> query1 = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and trans_type != ?2 and t_date >= ?3 order by t_date,created_date");
				query1.setParameter(1, dataDO.getCreated_by());
				query1.setParameter(2, 11);
				query1.setParameter(3, dataDO.getSi_date());
				List<AgencyCashDataDO> result1 = query1.getResultList();

				for (AgencyCashDataDO cashDO : result1) {
					if (dataDO.getSi_date() < cashDO.getT_date()) {
						BigDecimal tamt = new BigDecimal(dataDO.getCash_amount());
						BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
						cashDO.setCash_balance((btcb.add(tamt)).toString());
					} else if (dataDO.getSi_date() == cashDO.getT_date()) {
						BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
						cashDO.setCash_balance(btcb.toString());
					}
					session.save(cashDO);

					Query query2 = session.createQuery("update AgencyCashDataDO ac set ac.cash_balance = :acb"
							+ " where ac.created_by = :cid and ac.id= :id and ac.inv_no = :invno and ac.t_date > :tdate");
					query2.setParameter("acb", cashDO.getCash_balance());
					query2.setParameter("cid", cashDO.getCreated_by());
					query2.setParameter("id", cashDO.getId());
					query2.setParameter("invno", cashDO.getInv_no());
					query2.setParameter("tdate", dataDO.getSi_date());
					query2.executeUpdate();

				}
			}

			// Save Bank Transaction
			for (BankTranxsDataDO bankTranxData : btList) {
				int nno = snoDO.getBtSno();
				long bankId = bankTranxData.getBank_id();
				int i = 0;
				if (bankId > 0) {
					bankTranxData.setRef_id(refId);
					bankTranxData.setBtflag(4);

					BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
					long rdate = 0;
					Query<BankTranxsDataDO> bquery = session
							.createQuery("from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id= ?3 "
									+ "order by bt_date,id asc")
							.setMaxResults(1);
					bquery.setParameter(1, bankTranxData.getCreated_by());
					bquery.setParameter(2, 0);
					bquery.setParameter(3, bankId);

					List<BankTranxsDataDO> bresult = bquery.getResultList();

					if ((!bresult.isEmpty()) && bankTranxData.getBt_date() < bresult.get(0).getBt_date()) {
						BigDecimal tamt = new BigDecimal(bankTranxData.getTrans_amount());
						BigDecimal tc = new BigDecimal(bankTranxData.getTrans_charges());
						BigDecimal btcb = new BigDecimal(bresult.get(0).getBank_acb());
						BigDecimal lbtcb = new BigDecimal(bresult.get(0).getTrans_amount());
						BigDecimal tc1 = new BigDecimal(bresult.get(0).getTrans_charges());
						BigDecimal btamt = new BigDecimal(0);
						if (bresult.get(0).getTrans_type() == 2 || bresult.get(0).getTrans_type() == 3) {
							btamt = btcb.subtract(lbtcb).add(tc1);
						} else {
							btamt = btcb.add(lbtcb).add(tc1);
						}
						if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
							bankTranxData.setBank_acb((btamt.add(tamt).subtract(tc)).toString());
						} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4
								|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9
								|| bankTranxData.getTrans_type() == 10) {
							bankTranxData.setBank_acb((btamt.subtract(tamt).subtract(tc)).toString());
						}
						bankTranxData.setSr_no(-1);
						session.save(bankTranxData);
					}

					Query<BankTranxsDataDO> bquery1 = session.createQuery(
							"from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id= ?3 and bt_date > ?4 "
									+ "order by bt_date asc,id asc");

					bquery1.setParameter(1, bankTranxData.getCreated_by());
					bquery1.setParameter(2, 0);
					bquery1.setParameter(3, bankId);
					bquery1.setParameter(4, bankTranxData.getBt_date());

					List<BankTranxsDataDO> bresult1 = bquery1.getResultList();

					BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
					BigDecimal tamt = new BigDecimal(bankTranxData.getTrans_amount());
					BigDecimal tc = new BigDecimal(bankTranxData.getTrans_charges());

					if (!bresult1.isEmpty()) {
						for (BankTranxsDataDO btdObj : bresult1) {
							if (bankTranxData.getBt_date() < btdObj.getBt_date()) {
								BigDecimal btcb = new BigDecimal(btdObj.getBank_acb());
								BigDecimal btamt = new BigDecimal(0);
								BigDecimal lbtcb = new BigDecimal(btdObj.getTrans_amount());
								BigDecimal tc1 = new BigDecimal(btdObj.getTrans_charges());
								if (btdObj.getTrans_type() == 2 || btdObj.getTrans_type() == 3) {
									btamt = btcb.subtract(lbtcb).add(tc1);
								} else {
									btamt = btcb.add(lbtcb).add(tc1);
								}
								if ((rdate == 0 && bankTranxData.getSr_no() != -1) || rdate > btdObj.getBt_date()) {
									if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
										bankTranxData.setBank_acb((btamt.add(tamt).subtract(tc)).toString());
									} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4
											|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9
											|| bankTranxData.getTrans_type() == 10) {
										bankTranxData.setBank_acb((btamt.subtract(tamt).subtract(tc)).toString());
									}
									bankTranxData.setSr_no(nno);
									snoDO.setBtSno(nno + 1);
									rdate = btdObj.getBt_date();
									session.save(snoDO);
									session.save(bankTranxData);
								}
								btdObj.setBank_acb((btcb.add(tamt).subtract(tc)).toString());

								Query query2 = session.createQuery("update BankTranxsDataDO b set b.bank_acb = :bcb"
										+ " where b.created_by = :cid and b.id= :id and b.bank_id = :bid and b.bt_date >= :bdate");
								query2.setParameter("bcb", btdObj.getBank_acb());
								query2.setParameter("cid", btdObj.getCreated_by());
								query2.setParameter("id", btdObj.getId());
								query2.setParameter("bid", btdObj.getBank_id());
								query2.setParameter("bdate", bankTranxData.getBt_date());
								query2.executeUpdate();
							}
						}
						if (bankTranxData.getSr_no() == -1) {
							bankTranxData.setSr_no(nno);
							snoDO.setBtSno(nno + 1);
							session.update(snoDO);
							session.save(bankTranxData);
						}
					} else {
						bankTranxData.setSr_no(nno);
						if (bankTranxData.getBank_id() == bankDataDO.getId()) {
							snoDO.setBtSno(nno + 1);

							if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
								bankDataDO.setAcc_cb(((ccb.add(tamt).subtract(tc))).toString());
							} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4
									|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9
									|| bankTranxData.getTrans_type() == 10) {
								bankDataDO.setAcc_cb(((ccb.subtract(tamt).subtract(tc))).toString());
							}

							bankTranxData.setBank_acb(bankDataDO.getAcc_cb());
							session.save(bankDataDO);
							session.save(bankTranxData);
							i++;
						}
					}
					if (i == 0) {
						if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
							bankDataDO.setAcc_cb(((ccb.add(tamt).subtract(tc))).toString());
						} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4
								|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9
								|| bankTranxData.getTrans_type() == 10) {
							bankDataDO.setAcc_cb(((ccb.subtract(tamt).subtract(tc))).toString());
						}
						session.update(bankDataDO);
					}
				}
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> saveAgencyDSInvoices  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void deleteAgencyDSInvoices(long itemId, long agencyId) throws BusinessException {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			DOMSalesInvoiceDO dataDO = session.get(DOMSalesInvoiceDO.class, new Long(itemId));

			if (dataDO.getDeleted() == 0) {
				Query<DOMSalesInvoiceDetailsDO> squery = session
						.createQuery("from DOMSalesInvoiceDetailsDO where dsi_id = ?1");
				squery.setParameter(1, dataDO.getId());
				List<DOMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
				for (DOMSalesInvoiceDetailsDO dataDetailsDO : itemsResult) {
					int productCode = dataDetailsDO.getProd_code();
					Query<EquipmentDataDO> query = session.createQuery(
							"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
					query.setParameter(1, dataDO.getCreated_by());
					query.setParameter(2, 0);
					query.setParameter(3, productCode);
					List<EquipmentDataDO> result = query.getResultList();
					if (result.size() > 0) {
						for (EquipmentDataDO doObj : result) {
							int cfs = doObj.getCs_fulls();
							int ces = doObj.getCs_emptys();
							doObj.setCs_fulls(cfs + dataDetailsDO.getQuantity());
							doObj.setCs_emptys(ces - (dataDetailsDO.getQuantity() - dataDetailsDO.getPsv_cyls()));
							dataDetailsDO.setDs_fulls(doObj.getCs_fulls());
							dataDetailsDO.setDs_emptys(doObj.getCs_emptys());
							session.save(doObj);
							session.update(dataDetailsDO);
						}
					}
				}

				Query<BankTranxsDataDO> btqry = session
						.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 4");
				btqry.setParameter(1, dataDO.getId());
				List<BankTranxsDataDO> btItemsResult = btqry.getResultList();
				if (!(btItemsResult.isEmpty())) {
					for (BankTranxsDataDO btDetailsDO : btItemsResult) {
						BankDataDO bankDataDO = session.get(BankDataDO.class, btDetailsDO.getBank_id());
						BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
						BigDecimal tamt = new BigDecimal(btDetailsDO.getTrans_amount());
						bankDataDO.setAcc_cb((ccb.subtract(tamt)).toString());
						session.save(bankDataDO);

						btDetailsDO.setModified_by(agencyId);
						btDetailsDO.setModified_date(System.currentTimeMillis());
						btDetailsDO.setDbank_acb(bankDataDO.getAcc_cb());
						btDetailsDO.setDeleted(2);
						session.update(btDetailsDO);
					}
				}

				// Update Customer Details
				if (dataDO.getCustomer_id() > 0) {
					CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCustomer_id()));
					if (dataDO.getPayment_terms() == 2) {
						BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
						BigDecimal iamt = new BigDecimal(dataDO.getSi_amount());
						cvoDO.setCbal(ccb.subtract(iamt).toString());
						session.update(cvoDO);
					}
					dataDO.setDbal_amount(cvoDO.getCbal());
				}
				dataDO.setDeleted(2);
				dataDO.setModified_by(agencyId);
				dataDO.setModified_date(System.currentTimeMillis());
				session.update(dataDO);

				Query<AgencyStockDataDO> asdataDO = session
						.createQuery("from AgencyStockDataDO where ref_id = ?1 and stock_flag = 4");
				asdataDO.setParameter(1, dataDO.getId());
				List<AgencyStockDataDO> asItemsResult = asdataDO.getResultList();
				if (!(asItemsResult.isEmpty())) {
					for (AgencyStockDataDO asdDO : asItemsResult) {
						asdDO.setDeleted(3);
						asdDO.setModified_date(System.currentTimeMillis());
						asdDO.setModified_by(agencyId);
						session.update(asdDO);
					}
				}

				Query<AgencyCVOBalanceDataDO> cvoDataDO = session.createQuery(
						"from AgencyCVOBalanceDataDO where ref_id = ?1 and (cvoflag = 31 or cvoflag = 32)");
				cvoDataDO.setParameter(1, dataDO.getId());
				List<AgencyCVOBalanceDataDO> cvoItemsResult = cvoDataDO.getResultList();
				if (!(cvoItemsResult.isEmpty())) {
					for (AgencyCVOBalanceDataDO cvodDO : cvoItemsResult) {
						cvodDO.setDeleted(3);
						cvodDO.setModified_date(System.currentTimeMillis());
						cvodDO.setModified_by(agencyId);
						session.update(cvodDO);
					}
				}

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInDomSales(agencyId, dataDO, itemsResult);
				udodt.updateDataOnDeleteInBankTransactions(agencyId, btItemsResult);

			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> deleteAgencyDSInvoices  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// COM Sales Invoices
	public List<COMSalesInvoiceDO> getAgencyCSInvoices(long agencyId) throws BusinessException {
		List<COMSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<COMSalesInvoiceDO> query = session
					.createQuery("from COMSalesInvoiceDO where created_by = ?1 and (deleted = ?2 OR deleted = ?3) ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			List<COMSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (COMSalesInvoiceDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<COMSalesInvoiceDetailsDO> squery = session
							.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						if (dataDO.getDeleted() == 2) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String time = sdf.format(dataDO.getModified_date());
							dataDO.setSi_date(sdf.parse(time).getTime());
						}
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

	public COMSalesInvoiceDO getAgencyCSInvoicesById(long invId) throws BusinessException {
		COMSalesInvoiceDO rdo = new COMSalesInvoiceDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<COMSalesInvoiceDO> query = session
					.createQuery("from COMSalesInvoiceDO where id = ?1 and deleted = ?2 ");
			query.setParameter(1, invId);
			query.setParameter(2, 0);
			List<COMSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (COMSalesInvoiceDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<COMSalesInvoiceDetailsDO> squery = session
							.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						dataDO.getDetailsList().addAll(itemsResult);
					}
					rdo = dataDO;
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
		return rdo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAgencyCSInvoices(COMSalesInvoiceDO dataDO, List<COMSalesInvoiceDetailsDO> doList,
			List<BankTranxsDataDO> btList, List<AgencyStockDataDO> asdoList, AgencyCVOBalanceDataDO cdataDO)
			throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AgencySerialNosDO snoDO = session.get(AgencySerialNosDO.class, new Long(dataDO.getCreated_by()));
			if((snoDO.getFy() != Integer.parseInt(cfy)) && (cmonth == cApril)) {
				snoDO.setFy(Integer.parseInt(cfy));
				snoDO.setCnSno(1);
				snoDO.setCsSno(1);
				snoDO.setDcSno(1);
				snoDO.setDnSno(1);
				snoDO.setPrSno(1);
				snoDO.setQtSno(1);
				snoDO.setSiSno(1);
				
				snoDO.setBtSno(1);
				snoDO.setPmtsSno(1);
				snoDO.setRcptsSno(1);
				snoDO.setSrSno(1);
			}
			
			List<Long> list = new ArrayList<>();
			long refId = 0;
			
			// FOR INV NUMBERS
			if (dataDO.getCustomer_id() > 0) {
				Query<CVODataDO> query = session.createQuery(
						"from CVODataDO where created_by = ?1 and deleted = ?2 and is_gst_reg = ?3 and id = ?4");
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				query.setParameter(3, 1);
				query.setParameter(4, dataDO.getCustomer_id());
				List<CVODataDO> result = query.getResultList();
				if (result.size() > 0) {
					int nno = snoDO.getSiSno();
					if (nno < 10) {
						dataDO.setSr_no("SI-" + snoDO.getFy() + "-000" + nno);
					} else if (nno < 100) {
						dataDO.setSr_no("SI-" + snoDO.getFy() + "-00" + nno);
					} else if (nno < 1000) {
						dataDO.setSr_no("SI-" + snoDO.getFy() + "-0" + nno);
					} else {
						dataDO.setSr_no("SI-" + snoDO.getFy() + "-" + nno);
					}
					snoDO.setSiSno(nno + 1);
					session.update(snoDO);
					refId = (long) session.save(dataDO);
				} else {
					Query<CVODataDO> query2 = session.createQuery(
							"from CVODataDO where created_by = ?1 and deleted = ?2 and is_gst_reg = ?3 and id = ?4");
					query2.setParameter(1, dataDO.getCreated_by());
					query2.setParameter(2, 0);
					query2.setParameter(3, 2);
					query2.setParameter(4, dataDO.getCustomer_id());
					List<CVODataDO> result2 = query2.getResultList();
					if (!result2.isEmpty()) {
						int nno = snoDO.getCsSno();
						if (nno < 10) {
							dataDO.setSr_no("CS-" + snoDO.getFy() + "-000" + nno);
						} else if (nno < 100) {
							dataDO.setSr_no("CS-" + snoDO.getFy() + "-00" + nno);
						} else if (nno < 1000) {
							dataDO.setSr_no("CS-" + snoDO.getFy() + "-0" + nno);
						} else {
							dataDO.setSr_no("CS-" + snoDO.getFy() + "-" + nno);
						}
						snoDO.setCsSno(nno + 1);
						session.update(snoDO);
						refId = (long) session.save(dataDO);
					}
				}
			} else if (dataDO.getCustomer_id() == 0) {
				int nno = snoDO.getCsSno();
				if (nno < 10) {
					dataDO.setSr_no("CS-" + snoDO.getFy() + "-000" + nno);
				} else if (nno < 100) {
					dataDO.setSr_no("CS-" + snoDO.getFy() + "-00" + nno);
				} else if (nno < 1000) {
					dataDO.setSr_no("CS-" + snoDO.getFy() + "-0" + nno);
				} else {
					dataDO.setSr_no("CS-" + snoDO.getFy() + "-" + nno);
				}
				snoDO.setCsSno(nno + 1);
				session.update(snoDO);
				refId = (long) session.save(dataDO);
			}

			// Save Customer Details
			if (dataDO.getCustomer_id() > 0) {
				dataDO.setCbal_amount("0");
				refId = (long) session.save(dataDO);
				list.add(dataDO.getId());
			}

			// Save Customer Details and backdates
			if (cdataDO.getCvo_refid() > 0) {
				long count = 0;
				CVODataDO cvoDO = session.get(CVODataDO.class, new Long(cdataDO.getCvo_refid()));
				BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
				BigDecimal siamt = new BigDecimal(cdataDO.getAmount());

				long rdate = 0;

				Query<AgencyCVOBalanceDataDO> query2 = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat= ?3 and cvo_refid= ?4 "
								+ " and inv_ref_no != ?5 order by inv_date,id")
						.setMaxResults(1);
				query2.setParameter(1, cdataDO.getCreated_by());
				query2.setParameter(2, 0);
				query2.setParameter(3, 1);
				query2.setParameter(4, cdataDO.getCvo_refid());
				query2.setParameter(5, "NA");
				List<AgencyCVOBalanceDataDO> result2 = query2.getResultList();
				if (result2.size() > 0) {
					if (cdataDO.getInv_date() < result2.get(0).getInv_date()) {
						BigDecimal cb = new BigDecimal(result2.get(0).getCbal_amount());
						BigDecimal iamt = new BigDecimal(cdataDO.getAmount());
						BigDecimal ccb1 = new BigDecimal(result2.get(0).getAmount());
						if (cdataDO.getCvoflag() == 41) {
							if (result2.get(0).getCvoflag() == 31 || result2.get(0).getCvoflag() == 41
									|| result2.get(0).getCvoflag() == 51) {
								cdataDO.setCbal_amount(cb.toString());
							} else if (result2.get(0).getCvoflag() == 6 || result2.get(0).getCvoflag() == 81
									|| result2.get(0).getCvoflag() == 82 || result2.get(0).getCvoflag() == 10
									|| result2.get(0).getCvoflag() == 11) {

								cdataDO.setCbal_amount((cb.add(ccb1)).toString());
							} else {
								cdataDO.setCbal_amount((cb.subtract(ccb1)).toString());
							}

						} else {
							if (result2.get(0).getCvoflag() == 31 || result2.get(0).getCvoflag() == 41
									|| result2.get(0).getCvoflag() == 51) {
								cdataDO.setCbal_amount((cb.add(iamt)).toString());
							} else if (result2.get(0).getCvoflag() == 6 || result2.get(0).getCvoflag() == 81
									|| result2.get(0).getCvoflag() == 82 || result2.get(0).getCvoflag() == 10
									|| result2.get(0).getCvoflag() == 11) {
								cdataDO.setCbal_amount((cb.add(ccb1).add(iamt)).toString());
							} else {
								cdataDO.setCbal_amount((cb.subtract(ccb1).add(iamt)).toString());
							}
						}

						cdataDO.setRef_id(-1);
						refId = (long) session.save(dataDO);
					}
				}
				Query<AgencyCVOBalanceDataDO> query3 = session.createQuery(
						"from  AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat= ?3 and cvo_refid=  ?4"
								+ " and inv_date > ?5 order by inv_date,id");
				query3.setParameter(1, cdataDO.getCreated_by());
				query3.setParameter(2, 0);
				query3.setParameter(3, 1);
				query3.setParameter(4, cdataDO.getCvo_refid());
				query3.setParameter(5, cdataDO.getInv_date());

				List<AgencyCVOBalanceDataDO> result3 = query3.getResultList();

				if (result3.size() > 0) {
					for (AgencyCVOBalanceDataDO dsido : result3) {
						BigDecimal ccb1 = new BigDecimal(dsido.getCbal_amount());
						BigDecimal iamt = new BigDecimal(cdataDO.getAmount());
						BigDecimal lamt = new BigDecimal(dsido.getAmount());

						if (cdataDO.getInv_date() < dsido.getInv_date()) {
							if ((rdate == 0 && cdataDO.getRef_id() != -1) || rdate > dsido.getInv_date()) {

								if (cdataDO.getCvoflag() == 41) {
									if (dsido.getCvoflag() == 31 || dsido.getCvoflag() == 41
											|| dsido.getCvoflag() == 51) {
										cdataDO.setCbal_amount(ccb1.toString());

									} else if (dsido.getCvoflag() == 6 || dsido.getCvoflag() == 81
											|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 10
											|| dsido.getCvoflag() == 11) {

										cdataDO.setCbal_amount((ccb1.add(lamt)).toString());
									} else {
										cdataDO.setCbal_amount((ccb1.subtract(lamt)).toString());
									}

								} else {
									if (dsido.getCvoflag() == 31 || dsido.getCvoflag() == 41
											|| dsido.getCvoflag() == 51) {
										cdataDO.setCbal_amount((ccb1.add(iamt)).toString());

									} else if (dsido.getCvoflag() == 6 || dsido.getCvoflag() == 81
											|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 10
											|| dsido.getCvoflag() == 11) {

										cdataDO.setCbal_amount((ccb1.add(iamt).add(lamt)).toString());
									} else {
										cdataDO.setCbal_amount((ccb1.add(iamt).subtract(lamt)).toString());
									}
								}
								cdataDO.setRef_id((list.get((int) count)));
								cdataDO.setInv_ref_no(dataDO.getSr_no());
								rdate = dsido.getInv_date();
								cdataDO.setCreated_date(dataDO.getCreated_date());
								session.save(cdataDO);
								refId = (long) session.save(dataDO);
							}
							int x = 0;
							if (cdataDO.getCvoflag() == 41) {
								++x;
							} else {
								dsido.setCbal_amount((ccb1.add(iamt)).toString());
							}
							if (x == 0) {
								Query query4 = session
										.createQuery("update AgencyCVOBalanceDataDO b set b.cbal_amount = :bcb"
												+ " where b.created_by = :cid and b.id= :id and  b.cvo_refid = :bid and b.inv_date >= :bdate");
								query4.setParameter("bcb", dsido.getCbal_amount());
								query4.setParameter("cid", dsido.getCreated_by());
								query4.setParameter("id", dsido.getId());
								query4.setParameter("bid", dsido.getCvo_refid());
								query4.setParameter("bdate", dsido.getInv_date());
								query4.executeUpdate();
							}
						}
					}
					if (cdataDO.getRef_id() == -1) {
						cdataDO.setRef_id(list.get((int) count));
						cdataDO.setInv_ref_no(dataDO.getSr_no());
						cdataDO.setCreated_date(dataDO.getCreated_date());
						refId = (long) session.save(dataDO);
					}
				} else {
					cdataDO.setInv_ref_no(dataDO.getSr_no());
					if (cdataDO.getCvoflag() == 41) {
						cvoDO.setCbal(ccb.toString());
						cdataDO.setCbal_amount(ccb.toString());
					} else {
						cvoDO.setCbal(ccb.add(siamt).toString());
						cdataDO.setCbal_amount(ccb.add(siamt).toString());
					}
				}
				if (cdataDO.getRef_id() == 0) {
					cdataDO.setRef_id(list.get((int) count));
				}
				if (cdataDO.getCvoflag() == 41) {
					cvoDO.setCbal(ccb.toString());
				} else {
					cvoDO.setCbal(ccb.add(siamt).toString());
				}
				session.update(cvoDO);
				cdataDO.setCvo_refid(cvoDO.getId());
				cdataDO.setCvo_cat(cvoDO.getCvo_cat());
				cdataDO.setCreated_date(dataDO.getCreated_date());
				session.save(cdataDO);
			}

			for (COMSalesInvoiceDetailsDO dataDetailsDO : doList) {
				int productCode = dataDetailsDO.getProd_code();

				dataDetailsDO.setCsi_id(dataDO.getId());
				int csfulls = 0;
				int csempties = 0;

				BigDecimal qty = new BigDecimal(dataDetailsDO.getQuantity());
				BigDecimal psv = new BigDecimal(dataDetailsDO.getPre_cyls()); // PSV is saved in preCyls.
				BigDecimal disc = new BigDecimal(dataDetailsDO.getDisc_unit_rate());

				BigDecimal discount = (qty.subtract(psv)).multiply(disc);

				Query<EquipmentDataDO> query = session
						.createQuery("from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				query.setParameter(3, productCode);
				List<EquipmentDataDO> result = query.getResultList();
				if (result.size() > 0) {
					for (EquipmentDataDO doObj : result) {
						int cfs = doObj.getCs_fulls();
						int ces = doObj.getCs_emptys();
						doObj.setCs_fulls(cfs - dataDetailsDO.getQuantity());
						doObj.setCs_emptys(ces + dataDetailsDO.getPsv_cyls());
						session.save(doObj);
						csfulls = doObj.getCs_fulls();
						csempties = doObj.getCs_emptys();
					}
				}

				Query<AgencyStockDataDO> checkDataQ = session.createQuery(
						"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date > ?4");
				checkDataQ.setParameter(1, dataDO.getCreated_by());
				checkDataQ.setParameter(2, 0);
				checkDataQ.setParameter(3, productCode);
				checkDataQ.setParameter(4, dataDO.getSi_date());
				List<AgencyStockDataDO> checkDataR = checkDataQ.getResultList();

				if (!(checkDataR.isEmpty())) {
					// saving dataDO if backdate is entered
					Query<AgencyStockDataDO> sbalQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date <= ?4 "
									+ "order by trans_date desc , created_date desc")
							.setMaxResults(1);
					sbalQ.setParameter(1, dataDO.getCreated_by());
					sbalQ.setParameter(2, 0);
					sbalQ.setParameter(3, productCode);
					sbalQ.setParameter(4, dataDO.getSi_date());

					List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
					if (!(sbalRes.isEmpty())) {
						for (AgencyStockDataDO asdatado : sbalRes) {
							int cfulls = asdatado.getCs_fulls();
							int cempties = asdatado.getCs_emptys();

							dataDetailsDO.setCs_fulls(cfulls - dataDetailsDO.getQuantity());
							dataDetailsDO.setCs_emptys(cempties + dataDetailsDO.getPsv_cyls());
							session.save(dataDetailsDO);

							for (AgencyStockDataDO asDO : asdoList) {
								if (asDO.getProd_code() == productCode) {
									asDO.setRef_id(dataDO.getId());
									asDO.setInv_no(dataDO.getSr_no());
									asDO.setFulls(dataDetailsDO.getQuantity());
									asDO.setEmpties(dataDetailsDO.getPsv_cyls());
									asDO.setCs_fulls(cfulls - dataDetailsDO.getQuantity());
									asDO.setCs_emptys(cempties + dataDetailsDO.getPsv_cyls());
									asDO.setDiscount(discount.toString());
									asDO.setCreated_date(dataDO.getCreated_date());
									session.save(asDO);
								}
							}
						}
					}
					// updating remaining records from the user entered date- when back date is
					// given
					Query<AgencyStockDataDO> balUpdateQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date > ?4 "
									+ "order by trans_date, created_date");
					balUpdateQ.setParameter(1, dataDO.getCreated_by());
					balUpdateQ.setParameter(2, 0);
					balUpdateQ.setParameter(3, productCode);
					balUpdateQ.setParameter(4, dataDO.getSi_date());

					List<AgencyStockDataDO> balUpdateRes = balUpdateQ.getResultList();
					if (!(balUpdateRes.isEmpty())) {
						for (AgencyStockDataDO bUpInasDO : balUpdateRes) {
							bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls() - dataDetailsDO.getQuantity());
							bUpInasDO.setCs_emptys(bUpInasDO.getCs_emptys() + dataDetailsDO.getPsv_cyls());
							session.update(bUpInasDO);
						}
					}
				} else {
					// If User enters Forward date:

					dataDetailsDO.setCs_fulls(csfulls);
					dataDetailsDO.setCs_emptys(csempties);
					session.save(dataDetailsDO);

					for (AgencyStockDataDO asDO : asdoList) {
						if (asDO.getProd_code() == productCode) {
							asDO.setRef_id(dataDO.getId());
							asDO.setInv_no(dataDO.getSr_no());
							asDO.setFulls(dataDetailsDO.getQuantity());
							asDO.setEmpties(dataDetailsDO.getPsv_cyls());
							asDO.setCs_fulls(csfulls);
							asDO.setCs_emptys(csempties);
							asDO.setDiscount(discount.toString());
							asDO.setCreated_date(dataDO.getCreated_date());
							session.save(asDO);
						}
					}
				}

			}

			// CASH ADJUSTMENTS FOR BACKDATES
			if (dataDO.getPayment_terms() == 1) {
				Query<AgencyCashDataDO> query1 = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and trans_type != ?2 and t_date >= ?3 order by t_date,created_date");
				query1.setParameter(1, dataDO.getCreated_by());
				query1.setParameter(2, 11);
				query1.setParameter(3, dataDO.getSi_date());
				List<AgencyCashDataDO> result1 = query1.getResultList();

				for (AgencyCashDataDO cashDO : result1) {
					if (dataDO.getSi_date() < cashDO.getT_date()) {
						BigDecimal tamt = new BigDecimal(dataDO.getSi_amount());
						BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
						cashDO.setCash_balance((btcb.add(tamt)).toString());
					} else if (dataDO.getSi_date() == cashDO.getT_date()) {
						BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
						cashDO.setCash_balance(btcb.toString());
					}
					session.save(cashDO);

					Query query2 = session.createQuery("update AgencyCashDataDO ac set ac.cash_balance = :acb"
							+ " where ac.created_by = :cid and ac.id= :id and ac.inv_no = :invno and ac.t_date > :tdate");
					query2.setParameter("acb", cashDO.getCash_balance());
					query2.setParameter("cid", cashDO.getCreated_by());
					query2.setParameter("id", cashDO.getId());
					query2.setParameter("invno", cashDO.getInv_no());
					query2.setParameter("tdate", dataDO.getSi_date());
					query2.executeUpdate();
				}
			}
			
			// Save Bank Transaction
			if (dataDO.getPayment_terms() == 3) {
				for (BankTranxsDataDO bankTranxData : btList) {
					int nno = snoDO.getBtSno();
					long bankId = bankTranxData.getBank_id();
					int i=0;
					if (bankId > 0) {
						bankTranxData.setRef_id(refId);
						bankTranxData.setBtflag(14);
								
						BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
						long rdate=0;
						Query<BankTranxsDataDO> bquery = session.createQuery("from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id= ?3 "
								+ "order by bt_date,id asc").setMaxResults(1);
						bquery.setParameter(1, bankTranxData.getCreated_by());
						bquery.setParameter(2, 0);
						bquery.setParameter(3, bankId);

						List<BankTranxsDataDO> bresult = bquery.getResultList();

						if (bresult.size() > 0 && bankTranxData.getBt_date() < bresult.get(0).getBt_date()) {
							BigDecimal tamt = new BigDecimal(bankTranxData.getTrans_amount());
							BigDecimal tc = new BigDecimal(bankTranxData.getTrans_charges());
							BigDecimal btcb = new BigDecimal(bresult.get(0).getBank_acb());
							BigDecimal lbtcb = new BigDecimal(bresult.get(0).getTrans_amount());
							BigDecimal tc1 = new BigDecimal(bresult.get(0).getTrans_charges());
							BigDecimal btamt =new BigDecimal(0);
							if(bresult.get(0).getTrans_type() == 2 || bresult.get(0).getTrans_type() == 3) {
								btamt = btcb.subtract(lbtcb).add(tc1);
							}else{
								btamt = btcb.add(lbtcb).add(tc1);
							}
							if(bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
								bankTranxData.setBank_acb((btamt.add(tamt).subtract(tc)).toString());
							}else if(bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4 
									|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9 || bankTranxData.getTrans_type() == 10) {
								bankTranxData.setBank_acb((btamt.subtract(tamt).subtract(tc)).toString());
							}
							bankTranxData.setSr_no(-1);
							session.save(bankTranxData);
						}

						Query<BankTranxsDataDO> bquery1 = session.createQuery("from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id= ?3 and bt_date > ?4 "
								+ "order by bt_date asc,id asc");
						
						bquery1.setParameter(1, bankTranxData.getCreated_by());
						bquery1.setParameter(2, 0);
						bquery1.setParameter(3, bankId);
						bquery1.setParameter(4, bankTranxData.getBt_date());

						List<BankTranxsDataDO> bresult1 = bquery1.getResultList();

						BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
						BigDecimal tamt = new BigDecimal(bankTranxData.getTrans_amount());
						BigDecimal tc = new BigDecimal(bankTranxData.getTrans_charges());
									
						if (bresult1.size() > 0) {
							for (BankTranxsDataDO btdObj : bresult1) {
								if (bankTranxData.getBt_date() < btdObj.getBt_date()) {
									BigDecimal btcb = new BigDecimal(btdObj.getBank_acb());
									BigDecimal btamt = new BigDecimal(0);
									BigDecimal lbtcb = new BigDecimal(btdObj.getTrans_amount());
									BigDecimal tc1 = new BigDecimal(btdObj.getTrans_charges());
									if (btdObj.getTrans_type() == 2 || btdObj.getTrans_type() == 3) {
										btamt = btcb.subtract(lbtcb).add(tc1);
									}else{
										btamt = btcb.add(lbtcb).add(tc1);
									}
									if((rdate == 0 && bankTranxData.getSr_no()!=-1 ) || rdate > btdObj.getBt_date()) {
										if(bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
											bankTranxData.setBank_acb((btamt.add(tamt).subtract(tc)).toString());
										}else if(bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4 
												|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9 || bankTranxData.getTrans_type() == 10) {
											bankTranxData.setBank_acb((btamt.subtract(tamt).subtract(tc)).toString());
										}
										bankTranxData.setSr_no(nno);
										snoDO.setBtSno(nno + 1);
										rdate=btdObj.getBt_date();
										session.save(snoDO);
										session.save(bankTranxData);
									}
									btdObj.setBank_acb((btcb.add(tamt).subtract(tc)).toString());
											
									Query query2 = session.createQuery("update BankTranxsDataDO b set b.bank_acb = :bcb"
											+ " where b.created_by = :cid and b.id= :id and b.bank_id = :bid and b.bt_date >= :bdate");
									query2.setParameter("bcb", btdObj.getBank_acb());
									query2.setParameter("cid", btdObj.getCreated_by());
									query2.setParameter("id", btdObj.getId());
									query2.setParameter("bid", btdObj.getBank_id());
									query2.setParameter("bdate", bankTranxData.getBt_date());
									query2.executeUpdate();
								}
							}
							if (bankTranxData.getSr_no() == -1) {
								bankTranxData.setSr_no(nno);
								snoDO.setBtSno(nno + 1);
								session.update(snoDO);
								session.save(bankTranxData);
							}
						}else {
							bankTranxData.setSr_no(nno);
							if (bankTranxData.getBank_id() == bankDataDO.getId()) {
								snoDO.setBtSno(nno + 1);
									
								if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
									bankDataDO.setAcc_cb(((ccb.add(tamt).subtract(tc))).toString());
								} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4 
										|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9 || bankTranxData.getTrans_type() == 10) {
									bankDataDO.setAcc_cb(((ccb.subtract(tamt).subtract(tc))).toString());
								}
								
								bankTranxData.setBank_acb(bankDataDO.getAcc_cb());
								session.save(bankDataDO);
								session.save(bankTranxData);
								i++;
							}
						}
						if(i==0){
							if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
								bankDataDO.setAcc_cb(((ccb.add(tamt).subtract(tc))).toString());
							} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4 
									|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9 || bankTranxData.getTrans_type() == 10) {
								bankDataDO.setAcc_cb(((ccb.subtract(tamt).subtract(tc))).toString());
							}
							session.update(bankDataDO);
						}
					}
				}
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> saveAgencyCSInvoices  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void deleteAgencyCSInvoices(long itemId, long agencyId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			COMSalesInvoiceDO dataDO = session.get(COMSalesInvoiceDO.class, new Long(itemId));

			if (dataDO.getDeleted() == 0) {
				Query<COMSalesInvoiceDetailsDO> squery = session
						.createQuery("from COMSalesInvoiceDetailsDO where csi_id = ?1");
				squery.setParameter(1, dataDO.getId());
				List<COMSalesInvoiceDetailsDO> itemsResult = squery.getResultList();

				for (COMSalesInvoiceDetailsDO dataDetailsDO : itemsResult) {
					int productCode = dataDetailsDO.getProd_code();
					Query<EquipmentDataDO> query = session.createQuery(
							"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
					query.setParameter(1, dataDO.getCreated_by());
					query.setParameter(2, 0);
					query.setParameter(3, productCode);
					List<EquipmentDataDO> result = query.getResultList();
					if (result.size() > 0) {
						for (EquipmentDataDO doObj : result) {
							int cfs = doObj.getCs_fulls();
							int ces = doObj.getCs_emptys();
							doObj.setCs_fulls(cfs + dataDetailsDO.getQuantity());
							doObj.setCs_emptys(ces - dataDetailsDO.getPsv_cyls());
							dataDetailsDO.setDs_fulls(doObj.getCs_fulls());
							dataDetailsDO.setDs_emptys(doObj.getCs_emptys());
							session.save(doObj);
							session.save(dataDetailsDO);
						}

					}

				}

				// Update Customer Details
				if (dataDO.getCustomer_id() > 0) {
					CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCustomer_id()));
					if (dataDO.getPayment_terms() == 2) {
						BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
						BigDecimal iamt = new BigDecimal(dataDO.getSi_amount());
						cvoDO.setCbal(ccb.subtract(iamt).toString());
						session.update(cvoDO);
					}
					dataDO.setDbal_amount(cvoDO.getCbal());
				}
				
				// Update Bank Details
				List<BankTranxsDataDO> btItemsResult = new ArrayList<>();
				if(dataDO.getPayment_terms() == 3) {
					Query<BankTranxsDataDO> btqry = session.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 14");
					btqry.setParameter(1, dataDO.getId());
					btItemsResult = btqry.getResultList();
					if (!(btItemsResult.isEmpty())) {
						for (BankTranxsDataDO btDetailsDO : btItemsResult) {
							BankDataDO bankDataDO = session.get(BankDataDO.class, btDetailsDO.getBank_id());
							BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
							BigDecimal tamt = new BigDecimal(btDetailsDO.getTrans_amount());
							bankDataDO.setAcc_cb((ccb.subtract(tamt)).toString());
							session.save(bankDataDO);

							btDetailsDO.setModified_by(agencyId);
							btDetailsDO.setModified_date(System.currentTimeMillis());
							btDetailsDO.setDbank_acb(bankDataDO.getAcc_cb());
							btDetailsDO.setDeleted(2);
							session.update(btDetailsDO);
						}
					}
				}
				
				dataDO.setDeleted(2);
				dataDO.setModified_by(agencyId);
				dataDO.setModified_date(System.currentTimeMillis());
				session.update(dataDO);

				Query<AgencyStockDataDO> asdataDO = session
						.createQuery("from AgencyStockDataDO where ref_id = ?1 and stock_flag = 5");
				asdataDO.setParameter(1, dataDO.getId());
				List<AgencyStockDataDO> asItemsResult = asdataDO.getResultList();
				if (!(asItemsResult.isEmpty())) {
					for (AgencyStockDataDO asdDO : asItemsResult) {
						asdDO.setDeleted(3);
						asdDO.setModified_date(System.currentTimeMillis());
						asdDO.setModified_by(agencyId);
						session.update(asdDO);
					}
				}
				Query<AgencyCVOBalanceDataDO> cvodataDO = session.createQuery(
						"from AgencyCVOBalanceDataDO where ref_id = ?1 and (cvoflag = 41 or cvoflag = 42)");
				cvodataDO.setParameter(1, dataDO.getId());
				List<AgencyCVOBalanceDataDO> cvoItemsResult = cvodataDO.getResultList();
				if (!(cvoItemsResult.isEmpty())) {
					for (AgencyCVOBalanceDataDO cvodDO : cvoItemsResult) {
						cvodDO.setDeleted(3);
						cvodDO.setModified_date(System.currentTimeMillis());
						cvodDO.setModified_by(agencyId);
						session.update(cvodDO);
					}
				}

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInCOMSales(dataDO, itemsResult);
				if(!btItemsResult.isEmpty())
					udodt.updateDataOnDeleteInBankTransactions(agencyId, btItemsResult);
				

			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> deleteAgencyCSInvoices  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// ARB Sales Invoices
	public List<ARBSalesInvoiceDO> getAgencyARBSInvoices(long agencyId) throws BusinessException {
		List<ARBSalesInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<ARBSalesInvoiceDO> query = session
					.createQuery("from ARBSalesInvoiceDO where created_by = ?1 and (deleted = ?2 OR deleted = ?3)");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			List<ARBSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (ARBSalesInvoiceDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<ARBSalesInvoiceDetailsDO> squery = session
							.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						if (dataDO.getDeleted() == 2) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String time = sdf.format(dataDO.getModified_date());
							dataDO.setSi_date(sdf.parse(time).getTime());
						}
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

	public ARBSalesInvoiceDO getAgencyARBInvoicesById(long invId) throws BusinessException {
		ARBSalesInvoiceDO rdo = new ARBSalesInvoiceDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<ARBSalesInvoiceDO> query = session
					.createQuery("from ARBSalesInvoiceDO where id = ?1 and deleted = ?2 ");
			query.setParameter(1, invId);
			query.setParameter(2, 0);
			List<ARBSalesInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (ARBSalesInvoiceDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<ARBSalesInvoiceDetailsDO> squery = session
							.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						dataDO.getDetailsList().addAll(itemsResult);
					}
					rdo = dataDO;
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
		return rdo;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAgencyARBSInvoices(ARBSalesInvoiceDO dataDO, List<ARBSalesInvoiceDetailsDO> doList,
			List<AgencyStockDataDO> asdoList, List<BankTranxsDataDO> btList, AgencyCVOBalanceDataDO cdataDO) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AgencySerialNosDO snoDO = session.get(AgencySerialNosDO.class, new Long(dataDO.getCreated_by()));
			if((snoDO.getFy() != Integer.parseInt(cfy)) && (cmonth == cApril)) {
				snoDO.setFy(Integer.parseInt(cfy));
				snoDO.setCnSno(1);
				snoDO.setCsSno(1);
				snoDO.setDcSno(1);
				snoDO.setDnSno(1);
				snoDO.setPrSno(1);
				snoDO.setQtSno(1);
				snoDO.setSiSno(1);
				
				snoDO.setBtSno(1);
				snoDO.setPmtsSno(1);
				snoDO.setRcptsSno(1);
				snoDO.setSrSno(1);
			}
			
			List<Long> list = new ArrayList<>();
			long refId = 0;
			
			if (dataDO.getCustomer_id() > 0) {
				Query<CVODataDO> query = session.createQuery(
						"from CVODataDO where created_by = ?1 and deleted = ?2 and is_gst_reg = ?3 and id = ?4");
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				query.setParameter(3, 1);
				query.setParameter(4, dataDO.getCustomer_id());
				List<CVODataDO> result = query.getResultList();
				if (result.size() > 0) {
					int nno = snoDO.getSiSno();
					if (nno < 10) {
						dataDO.setSr_no("SI-" + snoDO.getFy() + "-000" + nno);
					} else if (nno < 100) {
						dataDO.setSr_no("SI-" + snoDO.getFy() + "-00" + nno);
					} else if (nno < 1000) {
						dataDO.setSr_no("SI-" + snoDO.getFy() + "-0" + nno);
					} else {
						dataDO.setSr_no("SI-" + snoDO.getFy() + "-" + nno);
					}
					snoDO.setSiSno(nno + 1);
					session.update(snoDO);
					refId = (long) session.save(dataDO);
				} else {
					Query<CVODataDO> query2 = session.createQuery(
							"from CVODataDO where created_by = ?1 and deleted = ?2 and is_gst_reg = ?3 and id = ?4");
					query2.setParameter(1, dataDO.getCreated_by());
					query2.setParameter(2, 0);
					query2.setParameter(3, 2);
					query2.setParameter(4, dataDO.getCustomer_id());
					List<CVODataDO> result2 = query2.getResultList();
					if (!result2.isEmpty()) {
						int nno = snoDO.getCsSno();
						if (nno < 10) {
							dataDO.setSr_no("CS-" + snoDO.getFy() + "-000" + nno);
						} else if (nno < 100) {
							dataDO.setSr_no("CS-" + snoDO.getFy() + "-00" + nno);
						} else if (nno < 1000) {
							dataDO.setSr_no("CS-" + snoDO.getFy() + "-0" + nno);
						} else {
							dataDO.setSr_no("CS-" + snoDO.getFy() + "-" + nno);
						}
						snoDO.setCsSno(nno + 1);
						session.update(snoDO);
						refId = (long) session.save(dataDO);
					}
				}
			} else if (dataDO.getCustomer_id() == 0) {
				int nno = snoDO.getCsSno();
				if (nno < 10) {
					dataDO.setSr_no("CS-" + snoDO.getFy() + "-000" + nno);
				} else if (nno < 100) {
					dataDO.setSr_no("CS-" + snoDO.getFy() + "-00" + nno);
				} else if (nno < 1000) {
					dataDO.setSr_no("CS-" + snoDO.getFy() + "-0" + nno);
				} else {
					dataDO.setSr_no("CS-" + snoDO.getFy() + "-" + nno);
				}
				snoDO.setCsSno(nno + 1);
				session.update(snoDO);
				refId = (long) session.save(dataDO);

			}

			// Save Customer Details
			if (dataDO.getCustomer_id() > 0) {
				dataDO.setCbal_amount("0");
				refId = (long) session.save(dataDO);
				list.add(dataDO.getId());
			}

			// Save Customer Details and backdates
			if (cdataDO.getCvo_refid() > 0) {
				long count = 0;
				CVODataDO cvoDO = session.get(CVODataDO.class, new Long(cdataDO.getCvo_refid()));
				BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
				BigDecimal siamt = new BigDecimal(cdataDO.getAmount());

				long rdate = 0;

				Query<AgencyCVOBalanceDataDO> query2 = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat= ?3 and cvo_refid= ?4 "
								+ " and inv_ref_no != ?5 order by inv_date,cbal_amount asc")
						.setMaxResults(1);
				query2.setParameter(1, cdataDO.getCreated_by());
				query2.setParameter(2, 0);
				query2.setParameter(3, 1);
				query2.setParameter(4, cdataDO.getCvo_refid());
				query2.setParameter(5, "NA");
				List<AgencyCVOBalanceDataDO> result2 = query2.getResultList();
				if (result2.size() > 0) {
					if (cdataDO.getInv_date() < result2.get(0).getInv_date()) {
						BigDecimal cb = new BigDecimal(result2.get(0).getCbal_amount());
						BigDecimal iamt = new BigDecimal(cdataDO.getAmount());
						BigDecimal ccb1 = new BigDecimal(result2.get(0).getAmount());
						if (cdataDO.getCvoflag() == 51) {
							if (result2.get(0).getCvoflag() == 31 || result2.get(0).getCvoflag() == 41
									|| result2.get(0).getCvoflag() == 51) {
								cdataDO.setCbal_amount(cb.toString());
							} else if (result2.get(0).getCvoflag() == 6 || result2.get(0).getCvoflag() == 81
									|| result2.get(0).getCvoflag() == 82 || result2.get(0).getCvoflag() == 10
									|| result2.get(0).getCvoflag() == 11) {

								cdataDO.setCbal_amount((cb.add(ccb1)).toString());
							} else {
								cdataDO.setCbal_amount((cb.subtract(ccb1)).toString());
							}

						} else {
							if (result2.get(0).getCvoflag() == 31 || result2.get(0).getCvoflag() == 41
									|| result2.get(0).getCvoflag() == 51) {
								cdataDO.setCbal_amount((cb.add(iamt)).toString());
							} else if (result2.get(0).getCvoflag() == 6 || result2.get(0).getCvoflag() == 81
									|| result2.get(0).getCvoflag() == 82 || result2.get(0).getCvoflag() == 10
									|| result2.get(0).getCvoflag() == 11) {
								cdataDO.setCbal_amount((cb.add(ccb1).add(iamt)).toString());
							} else {
								cdataDO.setCbal_amount((cb.subtract(ccb1).add(iamt)).toString());
							}
						}

						cdataDO.setRef_id(-1);
						refId = (long) session.save(dataDO);
					}
				}
				Query<AgencyCVOBalanceDataDO> query3 = session.createQuery(
						"from  AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat= ?3 and cvo_refid=  ?4"
								+ " and inv_date > ?5 order by inv_date,id,cbal_amount");
				query3.setParameter(1, cdataDO.getCreated_by());
				query3.setParameter(2, 0);
				query3.setParameter(3, 1);
				query3.setParameter(4, cdataDO.getCvo_refid());
				query3.setParameter(5, cdataDO.getInv_date());

				List<AgencyCVOBalanceDataDO> result3 = query3.getResultList();

				if (result3.size() > 0) {
					for (AgencyCVOBalanceDataDO dsido : result3) {
						BigDecimal ccb1 = new BigDecimal(dsido.getCbal_amount());
						BigDecimal iamt = new BigDecimal(cdataDO.getAmount());
						BigDecimal lamt = new BigDecimal(dsido.getAmount());

						if (cdataDO.getInv_date() < dsido.getInv_date()) {
							if ((rdate == 0 && cdataDO.getRef_id() != -1) || rdate > dsido.getInv_date()) {

								if (cdataDO.getCvoflag() == 51) {

									if (dsido.getCvoflag() == 31 || dsido.getCvoflag() == 41
											|| dsido.getCvoflag() == 51) {
										cdataDO.setCbal_amount(ccb1.toString());

									} else if (dsido.getCvoflag() == 6 || dsido.getCvoflag() == 81
											|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 10
											|| dsido.getCvoflag() == 11) {

										cdataDO.setCbal_amount((ccb1.add(lamt)).toString());
									} else {
										cdataDO.setCbal_amount((ccb1.subtract(lamt)).toString());
									}

								} else {
									if (dsido.getCvoflag() == 31 || dsido.getCvoflag() == 41
											|| dsido.getCvoflag() == 51) {
										cdataDO.setCbal_amount((ccb1.add(iamt)).toString());
									} else if (dsido.getCvoflag() == 6 || dsido.getCvoflag() == 81
											|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 10
											|| dsido.getCvoflag() == 11) {
										cdataDO.setCbal_amount((ccb1.add(iamt).add(lamt)).toString());
									} else {
										cdataDO.setCbal_amount((ccb1.add(iamt).subtract(lamt)).toString());
									}
								}
								cdataDO.setRef_id((list.get((int) count)));
								cdataDO.setInv_ref_no(dataDO.getSr_no());
								rdate = dsido.getInv_date();
								cdataDO.setCreated_date(dataDO.getCreated_date());
								session.save(cdataDO);
							}
							int x = 0;
							if (cdataDO.getCvoflag() == 51) {
								++x;
							} else {
								dsido.setCbal_amount((ccb1.add(iamt)).toString());
							}
							if (x == 0) {
								Query query4 = session
										.createQuery("update AgencyCVOBalanceDataDO b set b.cbal_amount = :bcb"
												+ " where b.created_by = :cid and b.id= :id and  b.cvo_refid = :bid and b.inv_date >= :bdate");
								query4.setParameter("bcb", dsido.getCbal_amount());
								query4.setParameter("cid", dsido.getCreated_by());
								query4.setParameter("id", dsido.getId());
								query4.setParameter("bid", dsido.getCvo_refid());
								query4.setParameter("bdate", dsido.getInv_date());
								query4.executeUpdate();
							}
						}
					}
					if (cdataDO.getRef_id() == -1) {
						cdataDO.setRef_id(list.get((int) count));
						cdataDO.setInv_ref_no(dataDO.getSr_no());
						refId = (long) session.save(dataDO);
					}
				} else {
					cdataDO.setInv_ref_no(dataDO.getSr_no());
					if (cdataDO.getCvoflag() == 51) {
						cvoDO.setCbal(ccb.toString());
						cdataDO.setCbal_amount(ccb.toString());
					} else {
						cvoDO.setCbal(ccb.add(siamt).toString());
						cdataDO.setCbal_amount(ccb.add(siamt).toString());
					}
				}
				if (cdataDO.getRef_id() == 0) {
					cdataDO.setRef_id(list.get((int) count));
				}
				if (cdataDO.getCvoflag() == 51) {
					cvoDO.setCbal(ccb.toString());
				} else {
					cvoDO.setCbal(ccb.add(siamt).toString());
				}
				session.update(cvoDO);
				cdataDO.setCvo_refid(cvoDO.getId());
				cdataDO.setCvo_cat(cvoDO.getCvo_cat());
				cdataDO.setCreated_date(dataDO.getCreated_date());
				session.save(cdataDO);
			}

			for (ARBSalesInvoiceDetailsDO dataDetailsDO : doList) {
				long productCode = dataDetailsDO.getProd_code();
				dataDetailsDO.setArbsi_id(dataDO.getId());

				BigDecimal qty = new BigDecimal(dataDetailsDO.getQuantity());
				BigDecimal disc = new BigDecimal(dataDetailsDO.getDisc_unit_rate());
				BigDecimal discount = qty.multiply(disc);

				int cstock = 0;
				int arbcode = 0;
				Query<ARBDataDO> query = session
						.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 and id = ?3");
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				query.setParameter(3, productCode);
				List<ARBDataDO> result = query.getResultList();
				if (result.size() > 0) {
					for (ARBDataDO doObj : result) {
						int cs = doObj.getCurrent_stock();
						doObj.setCurrent_stock(cs - dataDetailsDO.getQuantity());
						session.save(doObj);
						cstock = doObj.getCurrent_stock();
						arbcode = doObj.getProd_code();
					}
				}

				Query<AgencyStockDataDO> checkDataQ = session.createQuery(
						"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_id = ?3 and trans_date > ?4");
				checkDataQ.setParameter(1, dataDO.getCreated_by());
				checkDataQ.setParameter(2, 0);
				checkDataQ.setParameter(3, productCode);
				checkDataQ.setParameter(4, dataDO.getSi_date());
				List<AgencyStockDataDO> checkDataR = checkDataQ.getResultList();

				if (!(checkDataR.isEmpty())) {
					// saving dataDO if backdate is entered
					Query<AgencyStockDataDO> sbalQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_id = ?3 and trans_date <= ?4 "
									+ "order by trans_date desc , created_date desc")
							.setMaxResults(1);
					sbalQ.setParameter(1, dataDO.getCreated_by());
					sbalQ.setParameter(2, 0);
					sbalQ.setParameter(3, productCode);
					sbalQ.setParameter(4, dataDO.getSi_date());

					List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
					if (!(sbalRes.isEmpty())) {
						for (AgencyStockDataDO asdatado : sbalRes) {

							int cfulls = asdatado.getCs_fulls();

							dataDetailsDO.setC_stock(cfulls - dataDetailsDO.getQuantity());
							session.save(dataDetailsDO);

							for (AgencyStockDataDO asDO : asdoList) {
								if (asDO.getProd_id() == productCode) {
									asDO.setProd_code(arbcode);
									asDO.setRef_id(dataDO.getId());
									asDO.setInv_no(dataDO.getSr_no());
									asDO.setFulls(dataDetailsDO.getQuantity()); // no empties. So 0(passed in factory)
									asDO.setCs_fulls(cfulls - dataDetailsDO.getQuantity());
									asDO.setDiscount(discount.toString());
									asDO.setCreated_date(dataDO.getCreated_date());
									session.save(asDO);
								}
							}
						}
					}
					// updating remaining records from the user entered date- when back date is
					// given
					Query<AgencyStockDataDO> balUpdateQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_id = ?3 and trans_date > ?4 "
									+ "order by trans_date, created_date");
					balUpdateQ.setParameter(1, dataDO.getCreated_by());
					balUpdateQ.setParameter(2, 0);
					balUpdateQ.setParameter(3, productCode);
					balUpdateQ.setParameter(4, dataDO.getSi_date());

					List<AgencyStockDataDO> balUpdateRes = balUpdateQ.getResultList();
					if (!(balUpdateRes.isEmpty())) {
						for (AgencyStockDataDO bUpInasDO : balUpdateRes) {
							bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls() - dataDetailsDO.getQuantity());
							session.update(bUpInasDO);
						}
					}
				} else {
					// If User enters Forward date:

					dataDetailsDO.setC_stock(cstock);
					session.save(dataDetailsDO);

					for (AgencyStockDataDO asDO : asdoList) {
						if (asDO.getProd_id() == productCode) {
							asDO.setProd_code(arbcode);
							asDO.setRef_id(dataDO.getId());
							asDO.setInv_no(dataDO.getSr_no());
							asDO.setFulls(dataDetailsDO.getQuantity());
							asDO.setCs_fulls(cstock);
							asDO.setDiscount(discount.toString());
							asDO.setCreated_date(dataDO.getCreated_date());
							session.save(asDO);
						}
					}
				}
			}

			// CASH ADJUSTMENTS FOR BACK DATES
			if (dataDO.getPayment_terms() == 1) {
				Query<AgencyCashDataDO> query1 = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and trans_type != ?2 and t_date >= ?3 order by t_date,created_date");
				query1.setParameter(1, dataDO.getCreated_by());
				query1.setParameter(2, 11);
				query1.setParameter(3, dataDO.getSi_date());
				List<AgencyCashDataDO> result1 = query1.getResultList();

				for (AgencyCashDataDO cashDO : result1) {
					if (dataDO.getSi_date() < cashDO.getT_date()) {
						BigDecimal tamt = new BigDecimal(dataDO.getSi_amount());
						BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
						cashDO.setCash_balance((btcb.add(tamt)).toString());
					} else if (dataDO.getSi_date() == cashDO.getT_date()) {
						BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
						cashDO.setCash_balance(btcb.toString());
					}
					session.save(cashDO);
					Query query2 = session.createQuery("update AgencyCashDataDO ac set ac.cash_balance = :acb"
							+ " where ac.created_by = :cid and ac.id= :id and ac.inv_no = :invno and ac.t_date > :tdate");
					query2.setParameter("acb", cashDO.getCash_balance());
					query2.setParameter("cid", cashDO.getCreated_by());
					query2.setParameter("id", cashDO.getId());
					query2.setParameter("invno", cashDO.getInv_no());
					query2.setParameter("tdate", dataDO.getSi_date());
					query2.executeUpdate();

				}
			}
			
			// Save Bank Transaction
			if (dataDO.getPayment_terms() == 3) {
				for (BankTranxsDataDO bankTranxData : btList) {
					int nno = snoDO.getBtSno();
					long bankId = bankTranxData.getBank_id();
					int i=0;
					if (bankId > 0) {
						bankTranxData.setRef_id(refId);
						bankTranxData.setBtflag(15);
					
						BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
						long rdate=0;
						Query<BankTranxsDataDO> bquery = session.createQuery("from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id= ?3 "
								+ "order by bt_date,id asc").setMaxResults(1);
						bquery.setParameter(1, bankTranxData.getCreated_by());
						bquery.setParameter(2, 0);
						bquery.setParameter(3, bankId);

						List<BankTranxsDataDO> bresult = bquery.getResultList();
						
						if (bresult.size() > 0 && bankTranxData.getBt_date() < bresult.get(0).getBt_date()) {
							BigDecimal tamt = new BigDecimal(bankTranxData.getTrans_amount());
							BigDecimal tc = new BigDecimal(bankTranxData.getTrans_charges());
							BigDecimal btcb = new BigDecimal(bresult.get(0).getBank_acb());
							BigDecimal lbtcb = new BigDecimal(bresult.get(0).getTrans_amount());
							BigDecimal tc1 = new BigDecimal(bresult.get(0).getTrans_charges());
							BigDecimal btamt =new BigDecimal(0);
							if(bresult.get(0).getTrans_type() == 2 || bresult.get(0).getTrans_type() == 3) {
								btamt = btcb.subtract(lbtcb).add(tc1);
							}else{
								btamt = btcb.add(lbtcb).add(tc1);
							}
							if(bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
								bankTranxData.setBank_acb((btamt.add(tamt).subtract(tc)).toString());
							}else if(bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4 
									|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9 || bankTranxData.getTrans_type() == 10) {
								bankTranxData.setBank_acb((btamt.subtract(tamt).subtract(tc)).toString());
							}
							bankTranxData.setSr_no(-1);
							session.save(bankTranxData);
						}

						Query<BankTranxsDataDO> bquery1 = session.createQuery("from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id= ?3 and bt_date > ?4 "
								+ "order by bt_date asc,id asc");
			
						bquery1.setParameter(1, bankTranxData.getCreated_by());
						bquery1.setParameter(2, 0);
						bquery1.setParameter(3, bankId);
						bquery1.setParameter(4, bankTranxData.getBt_date());

						List<BankTranxsDataDO> bresult1 = bquery1.getResultList();

						BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
						BigDecimal tamt = new BigDecimal(bankTranxData.getTrans_amount());
						BigDecimal tc = new BigDecimal(bankTranxData.getTrans_charges());
						
						if (bresult1.size() > 0) {
							for (BankTranxsDataDO btdObj : bresult1) {
								if (bankTranxData.getBt_date() < btdObj.getBt_date()) {
									BigDecimal btcb = new BigDecimal(btdObj.getBank_acb());
									BigDecimal btamt = new BigDecimal(0);
									BigDecimal lbtcb = new BigDecimal(btdObj.getTrans_amount());
									BigDecimal tc1 = new BigDecimal(btdObj.getTrans_charges());
									if (btdObj.getTrans_type() == 2 || btdObj.getTrans_type() == 3) {
										btamt = btcb.subtract(lbtcb).add(tc1);
									}else{
										btamt = btcb.add(lbtcb).add(tc1);
									}
									if((rdate == 0 && bankTranxData.getSr_no()!=-1 ) || rdate > btdObj.getBt_date()) {
										if(bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
											bankTranxData.setBank_acb((btamt.add(tamt).subtract(tc)).toString());
										}else if(bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4 
												|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9 || bankTranxData.getTrans_type() == 10) {
											bankTranxData.setBank_acb((btamt.subtract(tamt).subtract(tc)).toString());
										}
										bankTranxData.setSr_no(nno);
										snoDO.setBtSno(nno + 1);
										rdate=btdObj.getBt_date();
										session.save(snoDO);
										session.save(bankTranxData);
									}
									btdObj.setBank_acb((btcb.add(tamt).subtract(tc)).toString());
	    								
									Query query2 = session.createQuery("update BankTranxsDataDO b set b.bank_acb = :bcb"
											+ " where b.created_by = :cid and b.id= :id and b.bank_id = :bid and b.bt_date >= :bdate");
									query2.setParameter("bcb", btdObj.getBank_acb());
									query2.setParameter("cid", btdObj.getCreated_by());
									query2.setParameter("id", btdObj.getId());
									query2.setParameter("bid", btdObj.getBank_id());
									query2.setParameter("bdate", bankTranxData.getBt_date());
									query2.executeUpdate();
								}
							}
							if (bankTranxData.getSr_no() == -1) {
								bankTranxData.setSr_no(nno);
								snoDO.setBtSno(nno + 1);
								session.update(snoDO);
								session.save(bankTranxData);
							}
						}else {
							bankTranxData.setSr_no(nno);
							if (bankTranxData.getBank_id() == bankDataDO.getId()) {
								snoDO.setBtSno(nno + 1);
						
								if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
									bankDataDO.setAcc_cb(((ccb.add(tamt).subtract(tc))).toString());
								} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4 
										|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9 || bankTranxData.getTrans_type() == 10) {
									bankDataDO.setAcc_cb(((ccb.subtract(tamt).subtract(tc))).toString());
								}

								bankTranxData.setBank_acb(bankDataDO.getAcc_cb());
								session.save(bankDataDO);
								session.save(bankTranxData);
								i++;
							}
						}
						if(i==0){
							if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
								bankDataDO.setAcc_cb(((ccb.add(tamt).subtract(tc))).toString());
							} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4 
									|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9 || bankTranxData.getTrans_type() == 10) {
								bankDataDO.setAcc_cb(((ccb.subtract(tamt).subtract(tc))).toString());
							}
							session.update(bankDataDO);
						}
					}
				}
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> saveAgencyARBSInvoices  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void deleteAgencyARBSInvoices(long itemId, long agencyId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ARBSalesInvoiceDO dataDO = session.get(ARBSalesInvoiceDO.class, new Long(itemId));

			if (dataDO.getDeleted() == 0) {
				Query<ARBSalesInvoiceDetailsDO> squery = session
						.createQuery("from ARBSalesInvoiceDetailsDO where arbsi_id = ?1");
				squery.setParameter(1, dataDO.getId());
				List<ARBSalesInvoiceDetailsDO> itemsResult = squery.getResultList();

				for (ARBSalesInvoiceDetailsDO dataDetailsDO : itemsResult) {
					long productCode = dataDetailsDO.getProd_code();
					Query<ARBDataDO> query = session
							.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 and ID = ?3");
					query.setParameter(1, dataDO.getCreated_by());
					query.setParameter(2, 0);
					query.setParameter(3, productCode);
					List<ARBDataDO> result = query.getResultList();
					if (result.size() > 0) {
						for (ARBDataDO doObj : result) {
							int cs = doObj.getCurrent_stock();
							doObj.setCurrent_stock(cs + dataDetailsDO.getQuantity());
							dataDetailsDO.setD_stock(doObj.getCurrent_stock());
							session.save(doObj);
							session.save(dataDetailsDO);
						}
					}
				}

				// Update Customer Details
				if (dataDO.getCustomer_id() > 0) {
					CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCustomer_id()));
					if (dataDO.getPayment_terms() == 2) {
						BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
						BigDecimal iamt = new BigDecimal(dataDO.getSi_amount());
						cvoDO.setCbal(ccb.subtract(iamt).toString());
						session.update(cvoDO);
					}
					dataDO.setDbal_amount(cvoDO.getCbal());
				}
				
				
				// Update Bank Details
				List<BankTranxsDataDO> btItemsResult = new ArrayList<>();
				if(dataDO.getPayment_terms() == 3) {
					Query<BankTranxsDataDO> btqry = session.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 15");
					btqry.setParameter(1, dataDO.getId());
					btItemsResult = btqry.getResultList();
					if (!(btItemsResult.isEmpty())) {
						for (BankTranxsDataDO btDetailsDO : btItemsResult) {
							BankDataDO bankDataDO = session.get(BankDataDO.class, btDetailsDO.getBank_id());
							BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
							BigDecimal tamt = new BigDecimal(btDetailsDO.getTrans_amount());
							bankDataDO.setAcc_cb((ccb.subtract(tamt)).toString());
							session.save(bankDataDO);

							btDetailsDO.setModified_by(agencyId);
							btDetailsDO.setModified_date(System.currentTimeMillis());
							btDetailsDO.setDbank_acb(bankDataDO.getAcc_cb());
							btDetailsDO.setDeleted(2);
							session.update(btDetailsDO);
						}
					}
				}
				
				dataDO.setDeleted(2);
				dataDO.setModified_by(agencyId);
				dataDO.setModified_date(System.currentTimeMillis());
				session.update(dataDO);

				Query<AgencyStockDataDO> asdataDO = session
						.createQuery("from AgencyStockDataDO where ref_id = ?1 and stock_flag = 6");
				asdataDO.setParameter(1, dataDO.getId());
				List<AgencyStockDataDO> asItemsResult = asdataDO.getResultList();
				if (!(asItemsResult.isEmpty())) {
					for (AgencyStockDataDO asdDO : asItemsResult) {
						asdDO.setDeleted(3);
						asdDO.setModified_date(System.currentTimeMillis());
						asdDO.setModified_by(agencyId);
						session.update(asdDO);
					}
				}

				Query<AgencyCVOBalanceDataDO> cvodataDO = session.createQuery(
						"from AgencyCVOBalanceDataDO where ref_id = ?1 and (cvoflag = 51 or cvoflag = 52)");
				cvodataDO.setParameter(1, dataDO.getId());
				List<AgencyCVOBalanceDataDO> cvoItemsResult = cvodataDO.getResultList();
				if (!(cvoItemsResult.isEmpty())) {
					for (AgencyCVOBalanceDataDO cvodDO : cvoItemsResult) {
						cvodDO.setDeleted(3);
						cvodDO.setModified_date(System.currentTimeMillis());
						cvodDO.setModified_by(agencyId);
						session.update(cvodDO);
					}
				}

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInARBSales(agencyId, dataDO, itemsResult);
				if(!btItemsResult.isEmpty())
					udodt.updateDataOnDeleteInBankTransactions(agencyId, btItemsResult);
				
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> deleteAgencyARBSInvoices  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Delivery Challan
	public List<DeliveryChallanDO> getAgencyDeliveryChallans(long agencyId) throws BusinessException {
		List<DeliveryChallanDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<DeliveryChallanDO> query = session
					.createQuery("from DeliveryChallanDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<DeliveryChallanDO> result = query.getResultList();
			if (result.size() > 0) {
				for (DeliveryChallanDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<DeliveryChallanDetailsDO> squery = session
							.createQuery("from DeliveryChallanDetailsDO where dc_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<DeliveryChallanDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						dataDO.getDcItemsList().addAll(itemsResult);
					}
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

	public DeliveryChallanDO getAgencyDeliveryChallenById(long invId) throws BusinessException {
		DeliveryChallanDO rdo = new DeliveryChallanDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<DeliveryChallanDO> query = session
					.createQuery("from DeliveryChallanDO where id = ?1 and deleted = ?2 ");
			query.setParameter(1, invId);
			query.setParameter(2, 0);
			List<DeliveryChallanDO> result = query.getResultList();
			if (result.size() > 0) {
				for (DeliveryChallanDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<DeliveryChallanDetailsDO> squery = session
							.createQuery("from DeliveryChallanDetailsDO where dc_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<DeliveryChallanDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						dataDO.getDcItemsList().addAll(itemsResult);
					}
					rdo = dataDO;
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
		return rdo;
	}

	public void saveAgencyDeliveryChallan(DeliveryChallanDO dcDataDO, List<DeliveryChallanDetailsDO> doList)
			throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AgencySerialNosDO snoDO = session.get(AgencySerialNosDO.class, new Long(dcDataDO.getCreated_by()));
			if((snoDO.getFy() != Integer.parseInt(cfy)) && (cmonth == cApril)) {
				snoDO.setFy(Integer.parseInt(cfy));
				snoDO.setCnSno(1);
				snoDO.setCsSno(1);
				snoDO.setDcSno(1);
				snoDO.setDnSno(1);
				snoDO.setPrSno(1);
				snoDO.setQtSno(1);
				snoDO.setSiSno(1);
				
				snoDO.setBtSno(1);
				snoDO.setPmtsSno(1);
				snoDO.setRcptsSno(1);
				snoDO.setSrSno(1);
			}
			
			int nno = snoDO.getDcSno();
			if (nno < 10) {
				dcDataDO.setSr_no("DC-" + snoDO.getFy() + "-000" + nno);
			} else if (nno < 100) {
				dcDataDO.setSr_no("DC-" + snoDO.getFy() + "-00" + nno);
			} else if (nno < 1000) {
				dcDataDO.setSr_no("DC-" + snoDO.getFy() + "-0" + nno);
			} else {
				dcDataDO.setSr_no("DC-" + snoDO.getFy() + "-" + nno);
			}
			snoDO.setDcSno(nno + 1);
			session.update(snoDO);
			session.save(dcDataDO);
			for (DeliveryChallanDetailsDO dataDO : doList) {
				dataDO.setDc_id(dcDataDO.getId());
				session.save(dataDO);
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> saveAgencyDeliveryChallan  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	public void deleteDeliveryChallan(long itemId, long agencyId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			DeliveryChallanDO dataDO = session.get(DeliveryChallanDO.class, new Long(itemId));
			dataDO.setDeleted(1);
			session.update(dataDO);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> TransactionsPersistenceManager --> deleteDeliveryChallan  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	/*
	 * -------------------------------------------------- POPUPS
	 * ------------------------------------------------------
	 */

	public PurchaseReturnDO getAgencyPurchaseReturnById(long invId) throws BusinessException {
		PurchaseReturnDO rdo = new PurchaseReturnDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<PurchaseReturnDO> query = session
					.createQuery("from PurchaseReturnDO where id = ?1 and deleted = ?2 ");
			query.setParameter(1, invId);
			query.setParameter(2, 0);
			List<PurchaseReturnDO> result = query.getResultList();
			if (result.size() > 0) {
				for (PurchaseReturnDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<PurchaseReturnDetailsDO> squery = session
							.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						dataDO.getDetailsList().addAll(itemsResult);
					}
					rdo = dataDO;
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
		return rdo;
	}

	public NCDBCInvoiceDO getAgencyNCDBCById(long invId) throws BusinessException {
		NCDBCInvoiceDO rdo = new NCDBCInvoiceDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<NCDBCInvoiceDO> query = session.createQuery("from NCDBCInvoiceDO where id = ?1 and deleted = ?2 ");
			query.setParameter(1, invId);
			query.setParameter(2, 0);
			List<NCDBCInvoiceDO> result = query.getResultList();
			if (result.size() > 0) {
				for (NCDBCInvoiceDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<NCDBCInvoiceDetailsDO> squery = session
							.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						dataDO.getDetailsList().addAll(itemsResult);
					}
					rdo = dataDO;
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
		return rdo;
	}

	public List<RCDataDO> getAgencyRCById(long invId) throws BusinessException {
		List<RCDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<RCDataDO> query = session.createQuery("from RCDataDO where id = ?1 and deleted = ?2 ");
			query.setParameter(1, invId);
			query.setParameter(2, 0);
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

	// tv popup
	public List<TVDataDO> getAgencyTVById(long invId) throws BusinessException {
		List<TVDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<TVDataDO> query = session.createQuery("from TVDataDO where id = ?1 and deleted = ?2 ");
			query.setParameter(1, invId);
			query.setParameter(2, 0);
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

	public CreditNotesDO getAgencyCreditNoteById(long invId) throws BusinessException {

		CreditNotesDO rdo = new CreditNotesDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<CreditNotesDO> query = session.createQuery("from CreditNotesDO where id = ?1 and deleted = ?2 ");
			query.setParameter(1, invId);
			query.setParameter(2, 0);
			List<CreditNotesDO> result = query.getResultList();
			if (result.size() > 0) {
				for (CreditNotesDO dataDO : result) {
					rdo = dataDO;
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
		return rdo;
	}

	public DebitNotesDO getAgencyDebitNoteById(long invId) throws BusinessException {
		DebitNotesDO rdo = new DebitNotesDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<DebitNotesDO> query = session.createQuery("from DebitNotesDO where id = ?1 and deleted = ?2 ");
			query.setParameter(1, invId);
			query.setParameter(2, 0);
			List<DebitNotesDO> result = query.getResultList();
			if (result.size() > 0) {
				for (DebitNotesDO dataDO : result) {
					rdo = dataDO;
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
		return rdo;
	}


//getAgencyPaymentsDataById

public PaymentsDataDO getAgencyPaymentsDataById(long invId) throws BusinessException {
	PaymentsDataDO rdo = new PaymentsDataDO();
	Session session = HibernateUtil.getSessionFactory().openSession();
	try {
		@SuppressWarnings("unchecked")
		Query<PaymentsDataDO> query = session.createQuery("from PaymentsDataDO where id = ?1 and deleted = ?2 ");
		query.setParameter(1, invId);
		query.setParameter(2, 0);
		List<PaymentsDataDO> result = query.getResultList();
		if (result.size() > 0) {
			for (PaymentsDataDO dataDO : result) {
				rdo = dataDO;
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
	return rdo;
}
//

public ReceiptsDataDO getAgencyReceiptsDataById(long invId) throws BusinessException {
	ReceiptsDataDO rdo = new ReceiptsDataDO();
	Session session = HibernateUtil.getSessionFactory().openSession();
	try {
		@SuppressWarnings("unchecked")
		Query<ReceiptsDataDO> query = session.createQuery("from ReceiptsDataDO where id = ?1 and deleted = ?2 ");
		query.setParameter(1, invId);
		query.setParameter(2, 0);
		List<ReceiptsDataDO> result = query.getResultList();
		if (result.size() > 0) {
			for (ReceiptsDataDO dataDO : result) {
				rdo = dataDO;
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
	return rdo;
}

}