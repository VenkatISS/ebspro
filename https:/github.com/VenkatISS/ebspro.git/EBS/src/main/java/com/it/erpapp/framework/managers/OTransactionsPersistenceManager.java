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
import com.it.erpapp.framework.model.GSTPaymentDetailsDO;
import com.it.erpapp.framework.model.pps.ARBDataDO;
import com.it.erpapp.framework.model.pps.EquipmentDataDO;
import com.it.erpapp.framework.model.transactions.others.AssetDataDO;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.framework.model.transactions.others.CreditNotesDO;
import com.it.erpapp.framework.model.transactions.others.DebitNotesDO;
import com.it.erpapp.framework.model.transactions.others.PaymentsDataDO;
import com.it.erpapp.framework.model.transactions.others.ReceiptsDataDO;
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

public class OTransactionsPersistenceManager {

	Logger logger = Logger.getLogger(OTransactionsPersistenceManager.class.getName());

	int cyear = Calendar.getInstance().get(Calendar.YEAR);
	String cfy = (Integer.toString(cyear)).substring(2);
	
	int cmonth = Calendar.getInstance().get(Calendar.MONTH);
	int cApril = Calendar.APRIL;

	// Receipts Data
	public List<ReceiptsDataDO> getAgencyReceiptsData(long agencyId) throws BusinessException {
		List<ReceiptsDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<ReceiptsDataDO> query = session
					.createQuery("from ReceiptsDataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?3)");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			List<ReceiptsDataDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (ReceiptsDataDO dataDO : result) {
					if (dataDO.getDeleted() == 2) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String time = sdf.format(dataDO.getModified_date());
						dataDO.setRcp_date(sdf.parse(time).getTime());
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

	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	public void saveAgencyReceiptsData(List<ReceiptsDataDO> doList, List<BankTranxsDataDO> bankTranxsDOList,
			AgencyCVOBalanceDataDO cdataDO) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			long refId = 0;
			List<Long> list = new ArrayList<>();
			String invsrid = "0";
			for (ReceiptsDataDO dataDO : doList) {
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
				
				int nno = snoDO.getRcptsSno();
				dataDO.setSr_no(nno);
				snoDO.setRcptsSno(nno + 1);
				session.update(snoDO);

				if (dataDO.getRcvd_from() > 0) {
					dataDO.setCbal_amount("0");
					session.save(dataDO);
					list.add(dataDO.getId());
					invsrid = String.valueOf(dataDO.getSr_no());
				}

				// Customer Cash update code
				if (dataDO.getPayment_mode() == 1) {
					Query<AgencyCashDataDO> query1 = session.createQuery(
							"from AgencyCashDataDO where created_by = ?1 and trans_type != ?2 and t_date >= ?3 order by t_date,created_date");
					query1.setParameter(1, dataDO.getCreated_by());
					query1.setParameter(2, 11);
					query1.setParameter(3, dataDO.getRcp_date());
					List<AgencyCashDataDO> result1 = query1.getResultList();

					for (AgencyCashDataDO cashDO : result1) {
						if (dataDO.getRcp_date() < cashDO.getT_date()) {
							BigDecimal tamt = new BigDecimal(dataDO.getRcp_amount());
							BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
							cashDO.setCash_balance((btcb.add(tamt)).toString());
						} else if (dataDO.getRcp_date() == cashDO.getT_date()) {
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
						query2.setParameter("tdate", dataDO.getRcp_date());
						query2.executeUpdate();
					}
				}

				// Save Customer Back dates Adjustement code
				CVODataDO cvoDO = session.get(CVODataDO.class, new Long(cdataDO.getCvo_refid()));
				// long count1 = 0;
				if (cdataDO.getCvo_refid() > 0) {
					String sid = "0";
					long rdate = 0;

					Query<AgencyCVOBalanceDataDO> query2 = session.createQuery(
							"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat= ?3 and cvo_refid= ?4 "
									+ " and inv_ref_no != ?5 order by inv_date,id,cbal_amount asc")
							.setMaxResults(1);
					query2.setParameter(1, cdataDO.getCreated_by());
					query2.setParameter(2, 0);
					query2.setParameter(3, 1);
					query2.setParameter(4, cdataDO.getCvo_refid());
					query2.setParameter(5, "NA");
					List<AgencyCVOBalanceDataDO> result2 = query2.getResultList();
					if (!result2.isEmpty()) {
						if (cdataDO.getInv_date() < result2.get(0).getInv_date()) {
							BigDecimal cb = new BigDecimal(result2.get(0).getCbal_amount());
							BigDecimal iamt = new BigDecimal(cdataDO.getAmount());
							BigDecimal ccb1 = new BigDecimal(result2.get(0).getAmount());

							if (result2.get(0).getCvoflag() == 31 || result2.get(0).getCvoflag() == 41
									|| result2.get(0).getCvoflag() == 51) {
								cdataDO.setCbal_amount((cb.subtract(iamt)).toString()); // insert bank amount between
																						// two cash records
							} else if (result2.get(0).getCvoflag() == 6 || result2.get(0).getCvoflag() == 81
									|| result2.get(0).getCvoflag() == 82 || result2.get(0).getCvoflag() == 10
									|| result2.get(0).getCvoflag() == 11) {
								cdataDO.setCbal_amount((cb.add(ccb1).subtract(iamt)).toString());
							} else {
								cdataDO.setCbal_amount((cb.subtract(ccb1).subtract(iamt)).toString());

							}

							cdataDO.setRef_id(-1);
							cdataDO.setCreated_date(dataDO.getCreated_date());
							session.save(cdataDO);
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
					BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
					BigDecimal iamt = new BigDecimal(cdataDO.getAmount());

					if (!result3.isEmpty()) {
						BigDecimal amt = new BigDecimal(0);
						for (AgencyCVOBalanceDataDO dsido : result3) {
							if (cdataDO.getInv_date() < dsido.getInv_date()) {
								BigDecimal tamt = new BigDecimal(cdataDO.getAmount());
								BigDecimal btcb = new BigDecimal(dsido.getCbal_amount());
								BigDecimal lbtcb = new BigDecimal(dsido.getAmount());

								if ((rdate == 0 && cdataDO.getRef_id() != -1) || rdate > dsido.getInv_date()) {
									if (dsido.getCvoflag() == 31 || dsido.getCvoflag() == 41
											|| dsido.getCvoflag() == 51) {
										// insert bank amount between two cash records
										cdataDO.setCbal_amount((btcb.subtract(iamt)).toString());
									} else if (dsido.getCvoflag() == 6 || dsido.getCvoflag() == 81
											|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 10
											|| dsido.getCvoflag() == 11) {
										cdataDO.setCbal_amount((btcb.add(lbtcb).subtract(iamt)).toString());
									} else {
										cdataDO.setCbal_amount((btcb.subtract(lbtcb).subtract(iamt)).toString());

									}

									cdataDO.setInv_ref_no(invsrid);
									rdate = dsido.getInv_date();
									cdataDO.setCreated_date(dataDO.getCreated_date());
									session.save(cdataDO);
								}
								dsido.setCbal_amount((btcb.subtract(tamt)).toString());
								
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
						if (cdataDO.getRef_id() == -1) {
							cdataDO.setInv_ref_no(invsrid);
							cdataDO.setRef_id(dataDO.getId());
							cdataDO.setCreated_date(dataDO.getCreated_date());
							session.save(cdataDO);
						}
					} else {
						cdataDO.setInv_ref_no(invsrid);
						cvoDO.setCbal(ccb.subtract(iamt).toString());
						cdataDO.setCbal_amount(ccb.subtract(iamt).toString());
					}
					if (cdataDO.getRef_id() == 0) {
						cdataDO.setRef_id(dataDO.getId());
					}
					cvoDO.setCbal(ccb.subtract(iamt).toString());
					session.update(cvoDO);
					cdataDO.setCvo_refid(cvoDO.getId());
					cdataDO.setCvo_cat(cvoDO.getCvo_cat());
					cdataDO.setCreated_date(dataDO.getCreated_date());
					session.save(cdataDO);
				}
			}

			// Save Bank Transaction
			for (BankTranxsDataDO bankTranxData : bankTranxsDOList) {
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
				int count = 0;
				if (bankId > 0) {
					bankTranxData.setRef_id(list.get(count));
					bankTranxData.setBtflag(8);

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
				++count;
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> saveAgencyReceiptsData  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void deleteAgencyReceiptsData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ReceiptsDataDO dataDO = session.get(ReceiptsDataDO.class, new Long(itemId));

			if (dataDO.getDeleted() == 0) {
				// Save Customer Details
				if (dataDO.getRcvd_from() > 0) {
					CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getRcvd_from()));
					BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
					BigDecimal iamt = new BigDecimal(dataDO.getRcp_amount());
					cvoDO.setCbal(ccb.add(iamt).toString());
					session.update(cvoDO);
					dataDO.setDbal_amount(cvoDO.getCbal());
				}

				// Save Bank Details	
				List<BankTranxsDataDO> btItemsResult = new ArrayList<>();
				long bankId = dataDO.getCredited_bank();
				if (bankId > 1) {
					Query<BankTranxsDataDO> btqry = session
							.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 8");
					btqry.setParameter(1, dataDO.getId());
					btItemsResult = btqry.getResultList();
					if (!(btItemsResult.isEmpty())) {
						for (BankTranxsDataDO btDetailsDO : btItemsResult) {
							BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
							BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
							BigDecimal tamt = new BigDecimal(dataDO.getRcp_amount());
							bankDataDO.setAcc_cb((ccb.subtract(tamt)).toString());
							session.save(bankDataDO);

							btDetailsDO.setModified_by(dataDO.getCreated_by());
							btDetailsDO.setModified_date(System.currentTimeMillis());
							btDetailsDO.setDbank_acb(bankDataDO.getAcc_cb());
							btDetailsDO.setDeleted(2);
							session.update(btDetailsDO);
						}
					}
				}
				dataDO.setDeleted(2);
				dataDO.setModified_by(dataDO.getCreated_by());
				dataDO.setModified_date(System.currentTimeMillis());
				session.update(dataDO);

				// Save CVO_BALANCE_DATA details
				Query<AgencyCVOBalanceDataDO> cvodataDO = session
						.createQuery("from AgencyCVOBalanceDataDO where ref_id = ?1 and cvoflag = 6)");
				cvodataDO.setParameter(1, dataDO.getId());
				List<AgencyCVOBalanceDataDO> cvoItemsResult = cvodataDO.getResultList();
				if (!(cvoItemsResult.isEmpty())) {
					for (AgencyCVOBalanceDataDO cvodDO : cvoItemsResult) {
						cvodDO.setDeleted(3);
						cvodDO.setModified_date(System.currentTimeMillis());
						cvodDO.setModified_by(dataDO.getCreated_by());
						session.update(cvodDO);
					}
				}

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInReceipts(dataDO.getCreated_by(), dataDO);
				if (!(btItemsResult.isEmpty()) && (bankId > 1))
					udodt.updateDataOnDeleteInBankTransactions(dataDO.getCreated_by(), btItemsResult);
			}
			tx.commit();

		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> deleteAgencyReceiptsData  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Payments Data
	public List<PaymentsDataDO> getAgencyPaymentsData(long agencyId) throws BusinessException {
		List<PaymentsDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<PaymentsDataDO> query = session
					.createQuery("from PaymentsDataDO where created_by = ?1 and (deleted = ?2 OR deleted = ?3) ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			List<PaymentsDataDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (PaymentsDataDO dataDO : result) {
					if (dataDO.getDeleted() == 2) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String time = sdf.format(dataDO.getModified_date());
						dataDO.setPymt_date(sdf.parse(time).getTime());
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAgencyPaymentsData(List<PaymentsDataDO> doList, List<BankTranxsDataDO> bankTranxsDOList,
			GSTPaymentDetailsDO gpDO, String charges, long agencyId, AgencyCVOBalanceDataDO cdataDO)
			throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<Long> list = new ArrayList<>();
			String invsrid = "0";
			long createdDate = 0;
			for (PaymentsDataDO dataDO : doList) {
				createdDate = dataDO.getCreated_date();
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
				
				int nno = snoDO.getPmtsSno();
				dataDO.setSr_no(nno);
				snoDO.setPmtsSno(nno + 1);
				session.update(snoDO);

				if (dataDO.getCust_ven() != 3) {
					// Save Vendor Balance
					if (dataDO.getPaid_to() > 0) {
						dataDO.setVbal_amount("0");
						session.save(dataDO);
						list.add(dataDO.getId());
						invsrid = String.valueOf(dataDO.getSr_no());
					}
				} else {
					dataDO.setVbal_amount("0.00");
					// Save data in GSTPaymentDetails table
					Query<GSTPaymentDetailsDO> query = session.createQuery(
							"from GSTPaymentDetailsDO where created_by = ?1 and (deleted = ?2 OR deleted = ?3) order by created_date desc")
							.setMaxResults(1);
					query.setParameter(1, agencyId);
					query.setParameter(2, 0);
					query.setParameter(3, 2);
					List<GSTPaymentDetailsDO> result = query.getResultList();
					GSTPaymentDetailsDO gstpdDO = new GSTPaymentDetailsDO();

					BigDecimal chargesbd = new BigDecimal(charges);
					BigDecimal paymentAmt = new BigDecimal(dataDO.getPymt_amount());

					if (!(result.isEmpty())) {
						gstpdDO = result.get(0);
						BigDecimal gpcb = new BigDecimal(gstpdDO.getFinal_gst_amount());

						String gstamt;
						if (gpDO.getGst_amount() != null) {
							gstamt = gpDO.getGst_amount();
						} else {
							gstamt = paymentAmt.subtract(chargesbd).toString();
						}

						BigDecimal pamt = new BigDecimal(gstamt);

						long sid = 0;
						long date = 0;
						Query<GSTPaymentDetailsDO> query2 = session
								.createQuery("from GSTPaymentDetailsDO where created_by = ?1 and deleted = ?2"
										+ " order by pdate,id")
								.setMaxResults(1);
						query2.setParameter(1, dataDO.getCreated_by());
						query2.setParameter(2, 0);
						List<GSTPaymentDetailsDO> result2 = query2.getResultList();
						if (!result2.isEmpty()) {
							if (dataDO.getPymt_date() < result2.get(0).getPdate()) {
								BigDecimal gptcb = new BigDecimal(result2.get(0).getDs_total_gst_amount());
								BigDecimal gsta = new BigDecimal(result2.get(0).getGst_amount());

								if (dataDO.getTax_type() == 1) {
									gptcb = gptcb.subtract(gsta);
									gpDO.setTotal_gst_amount(gptcb.add(pamt).toString());
									gpDO.setDs_total_gst_amount(gptcb.add(pamt).toString());
									gpDO.setFinal_gst_amount(gpcb.add(pamt).toString());
								} else if (dataDO.getTax_type() == 2) {
									gpDO.setTotal_gst_amount(gptcb.toString());
									gpDO.setDs_total_gst_amount(gptcb.toString());
									gpDO.setFinal_gst_amount(gpcb.toString());
								}

								session.save(gpDO);
								sid = gpDO.getId();
							}
						}
						Query<GSTPaymentDetailsDO> query1 = session.createQuery(
								"from GSTPaymentDetailsDO where created_by = ?1 and deleted = ?2 and pdate > ?3");
						query1.setParameter(1, dataDO.getCreated_by());
						query1.setParameter(2, 0);
						query1.setParameter(3, dataDO.getPymt_date());
						List<GSTPaymentDetailsDO> result1 = query1.getResultList();
						if (!result1.isEmpty()) {
							Query<GSTPaymentDetailsDO> query3 = session.createQuery(
									"from GSTPaymentDetailsDO where created_by = ?1 and deleted = ?2 and pdate >= ?3 "
											+ "order by pdate,id,total_gst_amount");
							query3.setParameter(1, dataDO.getCreated_by());
							query3.setParameter(2, 0);
							query3.setParameter(3, dataDO.getPymt_date());
							List<GSTPaymentDetailsDO> result3 = query3.getResultList();

							if (!result3.isEmpty()) {
								for (GSTPaymentDetailsDO dsido : result3) {
									if (sid != dsido.getId()) {
										BigDecimal gccb = new BigDecimal(dsido.getTotal_gst_amount());
										if (dataDO.getPymt_date() <= dsido.getPdate()) {
											if (dataDO.getPymt_date() == dsido.getPdate() && sid == 0
													&& (date > dsido.getPdate() || date == dsido.getPdate()
															|| date == 0)) {

												if (dataDO.getTax_type() == 1) {
													gpDO.setTotal_gst_amount(gccb.add(pamt).toString());
													gpDO.setDs_total_gst_amount(gccb.add(pamt).toString());
													gpDO.setFinal_gst_amount(gpcb.add(pamt).toString());
												} else if (dataDO.getTax_type() == 2) {
													gpDO.setTotal_gst_amount(gccb.toString());
													gpDO.setDs_total_gst_amount(gccb.toString());
													gpDO.setFinal_gst_amount(gpcb.toString());
												}

												date = dsido.getPdate();
												session.save(gpDO);
											}

											if (dataDO.getPymt_date() < dsido.getPdate()) {
												dsido.setTotal_gst_amount((gccb.add(pamt)).toString());
												dsido.setDs_total_gst_amount((gccb.add(pamt)).toString());

												if (sid == 0 && (date > dsido.getPdate() || date == 0)) {
													BigDecimal ccb1 = new BigDecimal(dsido.getGst_amount());
													gccb = gccb.subtract(ccb1);
													if (dataDO.getTax_type() == 1) {
														gpDO.setTotal_gst_amount(gccb.add(pamt).toString());
														gpDO.setDs_total_gst_amount(gccb.add(pamt).toString());
														gpDO.setFinal_gst_amount(gpcb.add(pamt).toString());
													} else if (dataDO.getTax_type() == 2) {
														gpDO.setTotal_gst_amount(gccb.toString());
														gpDO.setDs_total_gst_amount(gccb.toString());
														gpDO.setFinal_gst_amount(gpcb.toString());
													}
													date = dsido.getPdate();
													session.save(gpDO);
												}

												Query query4 = session.createQuery(
														"update GSTPaymentDetailsDO b set b.total_gst_amount = :bcb"
																+ " where b.created_by = :cid and b.id= :id and b.pdate >= :bdate");
												query4.setParameter("bcb", dsido.getTotal_gst_amount());
												query4.setParameter("cid", dsido.getCreated_by());
												query4.setParameter("id", dsido.getId());
												query4.setParameter("bdate", dsido.getPdate());
												query4.executeUpdate();
											}
										}
									}
								}
							} else {
								if (dataDO.getTax_type() == 1) {
									gpDO.setTotal_gst_amount(gpcb.add(pamt).toString());
									gpDO.setDs_total_gst_amount(gpcb.add(pamt).toString());
									gpDO.setFinal_gst_amount(gpcb.add(pamt).toString());
								} else if (dataDO.getTax_type() == 2) {
									gpDO.setTotal_gst_amount(gpcb.toString());
									gpDO.setDs_total_gst_amount(gpcb.toString());
									gpDO.setFinal_gst_amount(gpcb.toString());
								}
							}
						} else {
							if (dataDO.getTax_type() == 1) {
								gpDO.setTotal_gst_amount(gpcb.add(pamt).toString());
								gpDO.setDs_total_gst_amount(gpcb.add(pamt).toString());
								gpDO.setFinal_gst_amount(gpcb.add(pamt).toString());
							} else if (dataDO.getTax_type() == 2) {
								gpDO.setTotal_gst_amount(gpcb.toString());
								gpDO.setDs_total_gst_amount(gpcb.toString());
								gpDO.setFinal_gst_amount(gpcb.toString());
							}
						}
					} else {
						if (dataDO.getTax_type() == 1) {
							gpDO.setTotal_gst_amount(paymentAmt.subtract(chargesbd).toString());
							gpDO.setDs_total_gst_amount(paymentAmt.subtract(chargesbd).toString());
							gpDO.setFinal_gst_amount(paymentAmt.subtract(chargesbd).toString());
						} else if (dataDO.getTax_type() == 2) {
							gpDO.setTotal_gst_amount("0.00");
							gpDO.setDs_total_gst_amount("0.00");
							gpDO.setFinal_gst_amount("0.00");
						}
					}
					gpDO.setPayment_id(dataDO.getSr_no());
					session.save(gpDO);
				}
				session.save(dataDO);
				list.add(dataDO.getId());

				// Customer Cash update code
				if (dataDO.getPayment_mode() == 1) {
					Query<AgencyCashDataDO> query1 = session.createQuery(
							"from AgencyCashDataDO where created_by = ?1 and trans_type != ?2 and t_date >= ?3 order by t_date,created_date");
					query1.setParameter(1, dataDO.getCreated_by());
					query1.setParameter(2, 11);
					query1.setParameter(3, dataDO.getPymt_date());
					List<AgencyCashDataDO> result1 = query1.getResultList();

					for (AgencyCashDataDO cashDO : result1) {
						if (dataDO.getPymt_date() < cashDO.getT_date()) {
							BigDecimal tamt = new BigDecimal(dataDO.getPymt_amount());
							BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
							cashDO.setCash_balance((btcb.subtract(tamt)).toString());
						} else if (dataDO.getPymt_date() == cashDO.getT_date()) {
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
						query2.setParameter("tdate", dataDO.getPymt_date());
						query2.executeUpdate();
					}
				}
			}

			// BackDates Adjustemnet code
			long count1 = 0;
			if (cdataDO.getCvo_refid() > 0) {
				CVODataDO cvoDO = session.get(CVODataDO.class, new Long(cdataDO.getCvo_refid()));
				long rdate = 0;

				Query<AgencyCVOBalanceDataDO> query2 = session.createQuery(
						"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and (cvo_cat= ?3 or cvo_cat= ?6 ) and cvo_refid= ?4 "
								+ " and inv_ref_no != ?5 order by inv_date,id,cbal_amount asc")
						.setMaxResults(1);
				query2.setParameter(1, cdataDO.getCreated_by());
				query2.setParameter(2, 0);
				query2.setParameter(3, cdataDO.getCvo_cat());
				query2.setParameter(6, 3);
				query2.setParameter(4, cdataDO.getCvo_refid());
				query2.setParameter(5, "NA");
				List<AgencyCVOBalanceDataDO> result2 = query2.getResultList();
				if (!result2.isEmpty()) {
					if (cdataDO.getInv_date() < result2.get(0).getInv_date()) {
						BigDecimal cb = new BigDecimal(result2.get(0).getCbal_amount());
						BigDecimal iamt = new BigDecimal(cdataDO.getAmount());
						BigDecimal ccb1 = new BigDecimal(result2.get(0).getAmount());

						if (cdataDO.getCvo_cat() == 0 || cdataDO.getCvo_cat() == 3) {
							if (result2.get(0).getCvoflag() == 1 || result2.get(0).getCvoflag() == 2
									|| result2.get(0).getCvoflag() == 91) {
								cdataDO.setCbal_amount((cb.subtract(ccb1).subtract(iamt)).toString());
							} else {
								if (result2.get(0).getCvoflag() == 7 || result2.get(0).getCvoflag() == 81
										|| result2.get(0).getCvoflag() == 82 || result2.get(0).getCvoflag() == 10) {
									cdataDO.setCbal_amount((cb.add(ccb1).subtract(iamt)).toString());
								}
							}
						} else {
							if (result2.get(0).getCvoflag() == 31 || result2.get(0).getCvoflag() == 41
									|| result2.get(0).getCvoflag() == 51) {
								cdataDO.setCbal_amount((cb.add(iamt)).toString());
							} else if (result2.get(0).getCvoflag() == 6 || result2.get(0).getCvoflag() == 82
									|| result2.get(0).getCvoflag() == 11) {
								cdataDO.setCbal_amount((cb.add(ccb1).add(iamt)).toString());
							} else {
								cdataDO.setCbal_amount((cb.subtract(ccb1).add(iamt)).toString());
							}
						}
						cdataDO.setRef_id(-1);
						cdataDO.setCreated_date(createdDate);
						session.save(cdataDO);
					}
				}
				Query<AgencyCVOBalanceDataDO> query3 = session.createQuery(
						"from  AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and (cvo_cat= ?3 or cvo_cat= ?6 ) and cvo_refid=  ?4"
								+ " and inv_date > ?5 order by inv_date,id,cbal_amount");
				query3.setParameter(1, cdataDO.getCreated_by());
				query3.setParameter(2, 0);
				query3.setParameter(3, cdataDO.getCvo_cat());
				query3.setParameter(6, 3);
				query3.setParameter(4, cdataDO.getCvo_refid());
				query3.setParameter(5, cdataDO.getInv_date());

				List<AgencyCVOBalanceDataDO> result3 = query3.getResultList();
				BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
				BigDecimal iamt = new BigDecimal(cdataDO.getAmount());

				if (!result3.isEmpty()) {
					for (AgencyCVOBalanceDataDO dsido : result3) {
						if (cdataDO.getInv_date() < dsido.getInv_date()) {
							BigDecimal tamt = new BigDecimal(cdataDO.getAmount());
							BigDecimal btcb = new BigDecimal(dsido.getCbal_amount());
							BigDecimal lbtcb = new BigDecimal(dsido.getAmount());

							if ((rdate == 0 && cdataDO.getRef_id() != -1) || rdate > dsido.getInv_date()) {

								if (cdataDO.getCvo_cat() == 0 || cdataDO.getCvo_cat() == 3) {
									if (dsido.getCvoflag() == 1 || dsido.getCvoflag() == 2
											|| dsido.getCvoflag() == 91) {
										cdataDO.setCbal_amount((btcb.subtract(lbtcb).subtract(iamt)).toString());
									} else {
										if (dsido.getCvoflag() == 7 || dsido.getCvoflag() == 81
												|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 10) {
											cdataDO.setCbal_amount((btcb.add(lbtcb).subtract(iamt)).toString());
										}
									}
								} else {
									if (result3.get(0).getCvoflag() == 31 || result3.get(0).getCvoflag() == 41
											|| result3.get(0).getCvoflag() == 51) {
										cdataDO.setCbal_amount((btcb.add(iamt)).toString());
									} else if (result3.get(0).getCvoflag() == 6 || result3.get(0).getCvoflag() == 82
											|| result3.get(0).getCvoflag() == 11) {
										cdataDO.setCbal_amount((btcb.add(lbtcb).add(iamt)).toString());
									} else {
										cdataDO.setCbal_amount((btcb.subtract(lbtcb).add(iamt)).toString());
									}
								}
								cdataDO.setInv_ref_no(invsrid);
								rdate = dsido.getInv_date();
								cdataDO.setCreated_date(createdDate);
								session.save(cdataDO);
							}
							if (dsido.getCvo_cat() == 1) {
								dsido.setCbal_amount((btcb.add(tamt)).toString());
							} else {
								dsido.setCbal_amount((btcb.subtract(tamt)).toString());
							}
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
					if (cdataDO.getRef_id() == -1) {
						cdataDO.setInv_ref_no(invsrid);
						cdataDO.setRef_id(list.get((int) count1));
						cdataDO.setCreated_date(createdDate);
						session.save(cdataDO);
					}
				} else {
					cdataDO.setInv_ref_no(invsrid);
					if (cdataDO.getCvo_cat() == 0 || cdataDO.getCvo_cat() == 3) {
						cvoDO.setCbal(ccb.subtract(iamt).toString());
						cdataDO.setCbal_amount(ccb.subtract(iamt).toString());
					} else {
						cvoDO.setCbal(ccb.add(iamt).toString());
						cdataDO.setCbal_amount(ccb.add(iamt).toString());
					}
				}
				if (cdataDO.getRef_id() == 0) {
					cdataDO.setRef_id(list.get((int) count1));
				}
				if (cdataDO.getCvo_cat() == 0) {
					cvoDO.setCbal(ccb.subtract(iamt).toString());
				} else {
					cvoDO.setCbal(ccb.add(iamt).toString());
				}
				session.update(cvoDO);
				cdataDO.setCvo_refid(cvoDO.getId());
				cdataDO.setCvo_cat(cvoDO.getCvo_cat());
				cdataDO.setCreated_date(createdDate);
				session.save(cdataDO);
			}

			// Save Bank Transaction
			for (BankTranxsDataDO bankTranxData : bankTranxsDOList) {
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
				int count = 0;
				int i = 0;

				if (bankId > 0) {
					bankTranxData.setRef_id(list.get(count));
					bankTranxData.setBtflag(9);

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
					BigDecimal tamt = new BigDecimal(bankTranxData.getTrans_amount());
					BigDecimal tc = new BigDecimal(bankTranxData.getTrans_charges());
					BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());

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
								btdObj.setBank_acb((btcb.subtract(tamt).subtract(tc)).toString());

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
				++count;
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> saveAgencyPaymentsData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public synchronized void deleteAgencyPaymentData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			PaymentsDataDO dataDO = session.get(PaymentsDataDO.class, new Long(itemId));
			GSTPaymentDetailsDO gpdDO = new GSTPaymentDetailsDO();

			if (dataDO.getDeleted() == 0) {
				if (dataDO.getCust_ven() != 3) {
					// Save Vendor Balance
					if (dataDO.getPaid_to() > 0) {
						CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getPaid_to()));
						BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
						BigDecimal iamt = new BigDecimal(dataDO.getPymt_amount());
						if ((dataDO.getCust_ven() == 2) && (dataDO.getPayment_mode() == 2)) {
							cvoDO.setCbal(ccb.subtract(iamt).toString());
						} else {
							cvoDO.setCbal(ccb.add(iamt).toString());
						}
						session.update(cvoDO);
						dataDO.setDbal_amount(cvoDO.getCbal());
						session.update(cvoDO);
					}
				} else {
					Query<GSTPaymentDetailsDO> query = session.createQuery(
							"from GSTPaymentDetailsDO where payment_id = ?1 and created_by = ?2 and deleted = ?3 ");
					query.setParameter(1, dataDO.getSr_no());
					query.setParameter(2, dataDO.getCreated_by());
					query.setParameter(3, 0);
					List<GSTPaymentDetailsDO> result = query.getResultList();

					if (!(result.isEmpty())) {
						gpdDO = result.get(0);
						if (dataDO.getTax_type() == 1) {
							BigDecimal damt = new BigDecimal(gpdDO.getGst_amount());

							Query<GSTPaymentDetailsDO> gstpquery = session.createQuery(
									"from GSTPaymentDetailsDO where created_by = ?1 order by created_date desc ")
									.setMaxResults(1);
							gstpquery.setParameter(1, dataDO.getCreated_by());
							List<GSTPaymentDetailsDO> gstpres = gstpquery.getResultList();
							GSTPaymentDetailsDO gstpdDO = new GSTPaymentDetailsDO();

							if (!(gstpres.isEmpty())) {
								gstpdDO = gstpres.get(0);
								BigDecimal fgstamt = new BigDecimal(gstpdDO.getFinal_gst_amount());
								gstpdDO.setFinal_gst_amount(fgstamt.subtract(damt).toString());
								gpdDO.setDt_gst_amount(fgstamt.subtract(damt).toString());
								session.update(gstpdDO);
							}
						}
						gpdDO.setDeleted(2);
						gpdDO.setModified_by(dataDO.getCreated_by());
						gpdDO.setModified_date(System.currentTimeMillis());
						session.update(gpdDO);
					}
				}

				List<BankTranxsDataDO> btItemsResult = new ArrayList<>();
				long bankId = dataDO.getDebited_bank();
				if (bankId > 1) {
					Query<BankTranxsDataDO> btqry = session
							.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 9");
					btqry.setParameter(1, dataDO.getId());
					btItemsResult = btqry.getResultList();
					if (!(btItemsResult.isEmpty())) {
						for (BankTranxsDataDO btDetailsDO : btItemsResult) {

							BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
							BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
							BigDecimal tamt = new BigDecimal(dataDO.getPymt_amount());
							BigDecimal bcharges = new BigDecimal(dataDO.getBank_charges());
							bankDataDO.setAcc_cb(((ccb.add(tamt)).add(bcharges)).toString());
							session.save(bankDataDO);

							btDetailsDO.setModified_by(dataDO.getCreated_by());
							btDetailsDO.setModified_date(System.currentTimeMillis());
							btDetailsDO.setDbank_acb(bankDataDO.getAcc_cb());
							btDetailsDO.setDeleted(2);
							session.update(btDetailsDO);
						}
					}
				}
				dataDO.setDeleted(2);
				dataDO.setModified_by(dataDO.getCreated_by());
				dataDO.setModified_date(System.currentTimeMillis());
				session.update(dataDO);

				Query<AgencyCVOBalanceDataDO> cvodataDO = session
						.createQuery("from AgencyCVOBalanceDataDO where ref_id = ?1 and cvoflag = 7");
				cvodataDO.setParameter(1, dataDO.getId());
				List<AgencyCVOBalanceDataDO> cvoItemsResult = cvodataDO.getResultList();
				if (!(cvoItemsResult.isEmpty())) {
					for (AgencyCVOBalanceDataDO cvodDO : cvoItemsResult) {
						cvodDO.setDeleted(3);
						cvodDO.setModified_date(System.currentTimeMillis());
						cvodDO.setModified_by(dataDO.getCreated_by());
						session.update(cvodDO);
					}
				}

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInPayments(dataDO, gpdDO, session);
				if ((bankId > 1) && (!(btItemsResult.isEmpty())))
					udodt.updateDataOnDeleteInBankTransactions(dataDO.getCreated_by(), btItemsResult);

			}

		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> deleteAgencyPaymentData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Bank Transactions Data
	// Fetch Bank Charges
	public List<BankTranxsDataDO> getAgencyBankTranxsDataWithCharges(long fdl, long tdl, long agencyId)
			throws BusinessException {
		List<BankTranxsDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<BankTranxsDataDO> query = session.createQuery(
					"from BankTranxsDataDO where created_by = ?1 and deleted = ?2" + " and trans_amount > 0 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<BankTranxsDataDO> result = query.getResultList();
			if (!result.isEmpty()) {
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

	public List<BankTranxsDataDO> getAgencyBankTranxsData(long agencyId) throws BusinessException {
		List<BankTranxsDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<BankTranxsDataDO> query = session
					.createQuery("from BankTranxsDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?3)");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			List<BankTranxsDataDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (BankTranxsDataDO dataDO : result) {
					if (dataDO.getDeleted() == 2) {
						BankTranxsDataDO dataDO2 = new BankTranxsDataDO();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String time = sdf.format(dataDO.getModified_date());

						dataDO2.setId(dataDO.getId());
						dataDO2.setRef_id(dataDO.getRef_id());
						dataDO2.setBtflag(dataDO.getBtflag());
						dataDO2.setSr_no(dataDO.getSr_no());
						dataDO2.setBank_id(dataDO.getBank_id());
						dataDO2.setTrans_amount(dataDO.getTrans_amount());
						dataDO2.setTrans_charges(dataDO.getTrans_charges());
						if (dataDO.getTrans_type() == 1) {
							dataDO2.setTrans_type(7);
						} else if (dataDO.getTrans_type() == 2) {
							dataDO2.setTrans_type(6);
						} else if (dataDO.getTrans_type() == 3) {
							dataDO2.setTrans_type(13);
						} else if (dataDO.getTrans_type() == 4) {
							dataDO2.setTrans_type(14);
						} else if (dataDO.getTrans_type() == 5) {
							dataDO2.setTrans_type(8);
						} else if (dataDO.getTrans_type() == 9) {
							dataDO2.setTrans_type(11);
						} else if (dataDO.getTrans_type() == 10) {
							dataDO2.setTrans_type(12);
						}
						dataDO2.setDbank_acb(dataDO.getDbank_acb());
						dataDO2.setTrans_mode(dataDO.getTrans_mode());
						dataDO2.setInstr_details(dataDO.getInstr_details());
						dataDO2.setAcc_head(0);
						dataDO2.setNarration(dataDO.getNarration());
						dataDO2.setCreated_by(agencyId);
						dataDO2.setBt_date(sdf.parse(time).getTime());
						dataDO2.setBank_acb(dataDO.getBank_acb());
						dataDO2.setCreated_date(sdf.parse(time).getTime());
						dataDO2.setDeleted(dataDO.getDeleted());
						dataDO2.setModified_by(dataDO.getModified_by());
						dataDO2.setModified_date(dataDO.getModified_date());
						doList.add(dataDO2);
					} else {
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAgencyBankTranxsData(List<BankTranxsDataDO> doList, int ttype, long agencyId)
			throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			int nno = 0;
			if (ttype == 1) {
				AgencySerialNosDO snoDO = session.get(AgencySerialNosDO.class, new Long(agencyId));
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
				
				nno = snoDO.getBtSno();
				// dataDO.setSr_no(nno);
				snoDO.setBtSno(nno + 1);
				session.update(snoDO);
			}

			for (BankTranxsDataDO bankTranxData : doList) {
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
				if (ttype == 2) {
					nno = snoDO.getBtSno();
					// dataDO.setSr_no(nno);
					snoDO.setBtSno(nno + 1);
					session.update(snoDO);
				}

				long bankId = bankTranxData.getBank_id();
				int i = 0;
				if (bankId > 0) {
					bankTranxData.setRef_id(0);
					bankTranxData.setBtflag(1);

					BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
					long rdate = 0;

					// new 01062018
					// Customer Cash update code
					if (bankTranxData.getTrans_type() == 3 || bankTranxData.getTrans_type() == 4) {
						Query<AgencyCashDataDO> query1 = session.createQuery(
								"from AgencyCashDataDO where created_by = ?1 and trans_type != ?2 and t_date >= ?3 order by t_date,created_date");
						query1.setParameter(1, bankTranxData.getCreated_by());
						query1.setParameter(2, 11);
						query1.setParameter(3, bankTranxData.getBt_date());
						List<AgencyCashDataDO> result1 = query1.getResultList();

						for (AgencyCashDataDO cashDO : result1) {
							if (bankTranxData.getBt_date() < cashDO.getT_date()) {
								BigDecimal tamt = new BigDecimal(bankTranxData.getTrans_amount());
								BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
								if (bankTranxData.getTrans_type() == 3) {
									cashDO.setCash_balance((btcb.subtract(tamt)).toString());
								} else if (bankTranxData.getTrans_type() == 4) {
									cashDO.setCash_balance((btcb.add(tamt)).toString());
								}
							} else if (bankTranxData.getBt_date() == cashDO.getT_date()) {
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
							query2.setParameter("tdate", bankTranxData.getBt_date());
							query2.executeUpdate();
						}
					}

					// Save Bank Transaction
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
					BigDecimal tamt = new BigDecimal(bankTranxData.getTrans_amount());
					BigDecimal tc = new BigDecimal(bankTranxData.getTrans_charges());
					BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());

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
								if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
									btdObj.setBank_acb((btcb.add(tamt).subtract(tc)).toString());
								} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4
										|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9
										|| bankTranxData.getTrans_type() == 10) {
									btdObj.setBank_acb((btcb.subtract(tamt).subtract(tc)).toString());
								}

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
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> saveAgencyBankTranxsData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	public synchronized void deleteAgencyBankTranxsData(long itemId, long agencyId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			@SuppressWarnings("unchecked")
			Query<BankTranxsDataDO> query = session
					.createQuery("from BankTranxsDataDO where created_by = ?1 and sr_no = ?2 and deleted = 0");
			query.setParameter(1, agencyId);
			query.setParameter(2, itemId);
			List<BankTranxsDataDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (BankTranxsDataDO dataDO : result) {
					BankDataDO bankDataDO = session.get(BankDataDO.class, dataDO.getBank_id());
					BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
					BigDecimal tamt = new BigDecimal(dataDO.getTrans_amount());
					BigDecimal charges = new BigDecimal(dataDO.getTrans_charges());
					if (dataDO.getTrans_type() == 1) {
						bankDataDO.setAcc_cb((ccb.add(tamt).add(charges)).toString());
					} else if (dataDO.getTrans_type() == 2) {
						bankDataDO.setAcc_cb((ccb.subtract(tamt)).toString());
					} else if (dataDO.getTrans_type() == 3) {
						bankDataDO.setAcc_cb(((ccb.subtract(tamt)).add(charges)).toString());
					} else if (dataDO.getTrans_type() == 4) {
						bankDataDO.setAcc_cb((ccb.add(tamt).add(charges)).toString());
					} else if (dataDO.getTrans_type() == 5) {
						bankDataDO.setAcc_cb((ccb.subtract(tamt).subtract(charges)).toString());
					}
					dataDO.setDbank_acb(bankDataDO.getAcc_cb());

					session.save(bankDataDO);
					dataDO.setDeleted(2);
					dataDO.setModified_by(dataDO.getCreated_by());
					dataDO.setModified_date(System.currentTimeMillis());
					session.update(dataDO);
				}
				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInBankTransactions(agencyId, result);
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> deleteAgencyBankTranxsData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Credit Notes - New
	public List<CreditNotesDO> getAgencyCreditNotes(long agencyId) throws BusinessException {
		List<CreditNotesDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<CreditNotesDO> query = session
					.createQuery("from CreditNotesDO where created_by = ?1 and (deleted = ?2 OR deleted = ?3) ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			List<CreditNotesDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (CreditNotesDO dataDO : result) {
					if (dataDO.getDeleted() == 2) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String time = sdf.format(dataDO.getModified_date());
						dataDO.setNote_date(sdf.parse(time).getTime());
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAgencyCreditNotes(CreditNotesDO dataDO, BankTranxsDataDO bankTranxData,
			AgencyCVOBalanceDataDO cdataDO) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			long refId = 0;
			String invsrid = "0";
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
			
			int nno = snoDO.getCnSno();
			if (nno < 10) {
				dataDO.setSid("CN-" + snoDO.getFy() + "-000" + nno);
			} else if (nno < 100) {
				dataDO.setSid("CN-" + snoDO.getFy() + "-00" + nno);
			} else if (nno < 1000) {
				dataDO.setSid("CN-" + snoDO.getFy() + "-0" + nno);
			} else {
				dataDO.setSid("CN-" + snoDO.getFy() + "-" + nno);
			}
			snoDO.setCnSno(nno + 1);
			session.update(snoDO);

			// Save Customer Details
			CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCvo_id()));
			int cvoCat = cvoDO.getCvo_cat();
			int drnType = dataDO.getCredit_note_type();
			dataDO.setCbal_amount("0");
			refId = (long) session.save(dataDO);
			invsrid = String.valueOf(dataDO.getSid());

			// Save Customer Details and back dates
			if (!(drnType == 1 && cvoCat == 2)) {
				if (cdataDO.getCvo_refid() > 0) {
					long rdate = 0;

					Query<AgencyCVOBalanceDataDO> query2 = session.createQuery(
							"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat != ?3  and cvo_refid= ?4 "
									+ " and inv_ref_no != ?5 order by inv_date,id,cbal_amount asc")
							.setMaxResults(1);
					query2.setParameter(1, cdataDO.getCreated_by());
					query2.setParameter(2, 0);
					query2.setParameter(3, 3);
					query2.setParameter(4, cdataDO.getCvo_refid());
					query2.setParameter(5, "NA");
					List<AgencyCVOBalanceDataDO> result2 = query2.getResultList();
					if (!result2.isEmpty()) {
						if (dataDO.getNote_date() < result2.get(0).getInv_date()) {
							BigDecimal cb = new BigDecimal(result2.get(0).getCbal_amount());
							BigDecimal iamt = new BigDecimal(dataDO.getCredit_amount());
							BigDecimal ccb1 = new BigDecimal(result2.get(0).getAmount());
							if (dataDO.getCredit_note_type() == 2) {
								if (result2.get(0).getCvoflag() == 31 || result2.get(0).getCvoflag() == 41
										|| result2.get(0).getCvoflag() == 51) {
									cdataDO.setCbal_amount((cb.subtract(iamt)).toString());
								} else if (result2.get(0).getCvoflag() == 6 || result2.get(0).getCvoflag() == 81
										|| result2.get(0).getCvoflag() == 82 || result2.get(0).getCvoflag() == 11) {
									cdataDO.setCbal_amount((cb.add(ccb1).subtract(iamt)).toString());
								} else {
									cdataDO.setCbal_amount((cb.subtract(ccb1).subtract(iamt)).toString());
								}
							} else {
								if (result2.get(0).getCvoflag() == 7 || result2.get(0).getCvoflag() == 81
										|| result2.get(0).getCvoflag() == 82 || result2.get(0).getCvoflag() == 10) {
									cdataDO.setCbal_amount((cb.add(ccb1).subtract(iamt)).toString());
								} else {
									cdataDO.setCbal_amount((cb.subtract(ccb1).subtract(iamt)).toString());
								}
							}
							cdataDO.setRef_id(-1);
							cdataDO.setCreated_date(dataDO.getCreated_date());
							session.save(cdataDO);
						}
					}
					Query<AgencyCVOBalanceDataDO> query3 = session.createQuery(
							"from  AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat!= ?3 and cvo_refid=  ?4"
									+ " and inv_date > ?5 order by inv_date,id,cbal_amount");
					query3.setParameter(1, cdataDO.getCreated_by());
					query3.setParameter(2, 0);
					query3.setParameter(3, 3);
					query3.setParameter(4, cdataDO.getCvo_refid());
					query3.setParameter(5, cdataDO.getInv_date());

					List<AgencyCVOBalanceDataDO> result3 = query3.getResultList();
					BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
					BigDecimal iamt = new BigDecimal(cdataDO.getAmount());
					if (!result3.isEmpty()) {
						for (AgencyCVOBalanceDataDO dsido : result3) {
							if (cdataDO.getInv_date() < dsido.getInv_date()) {
								BigDecimal tamt = new BigDecimal(cdataDO.getAmount());
								BigDecimal btcb = new BigDecimal(dsido.getCbal_amount());
								BigDecimal lbtcb = new BigDecimal(dsido.getAmount());
								if ((rdate == 0 && cdataDO.getRef_id() != -1) || rdate > dsido.getInv_date()) {

									if (dataDO.getCredit_note_type() == 2) {
										if (dsido.getCvoflag() == 31 || dsido.getCvoflag() == 41
												|| dsido.getCvoflag() == 51) {
											cdataDO.setCbal_amount((btcb.subtract(tamt)).toString());
										} else if (dsido.getCvoflag() == 6 || dsido.getCvoflag() == 81
												|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 11) {
											cdataDO.setCbal_amount((btcb.add(lbtcb).subtract(iamt)).toString());
										} else {
											cdataDO.setCbal_amount((btcb.subtract(lbtcb).subtract(iamt)).toString());
										}
									} else {
										if (dsido.getCvoflag() == 7 || dsido.getCvoflag() == 81
												|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 10) {
											cdataDO.setCbal_amount((btcb.add(lbtcb).subtract(iamt)).toString());
										} else {
											cdataDO.setCbal_amount((btcb.subtract(lbtcb).subtract(iamt)).toString());
										}

									}

									cdataDO.setInv_ref_no(invsrid);
									rdate = dsido.getInv_date();
									cdataDO.setCreated_date(dataDO.getCreated_date());
									session.save(cdataDO);
								}
								dsido.setCbal_amount((btcb.subtract(tamt)).toString());

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
						if (cdataDO.getRef_id() == -1) {
							cdataDO.setInv_ref_no(invsrid);
							cdataDO.setRef_id(refId);
							cdataDO.setCreated_date(dataDO.getCreated_date());
							session.save(cdataDO);
						}
					} else {
						cdataDO.setInv_ref_no(invsrid);
						cvoDO.setCbal(ccb.subtract(iamt).toString());
						cdataDO.setCbal_amount(ccb.subtract(iamt).toString());
					}
					if (cdataDO.getRef_id() == 0) {
						cdataDO.setRef_id(refId);
						cvoDO.setCbal(ccb.subtract(iamt).toString());
					}
					session.update(cvoDO);
					cdataDO.setCvo_refid(cvoDO.getId());
					cdataDO.setCvo_cat(cvoDO.getCvo_cat());
					cdataDO.setCreated_date(dataDO.getCreated_date());
					session.save(cdataDO);
				}
			}
			long bankId = bankTranxData.getBank_id();
			AgencySerialNosDO snoDO1 = session.get(AgencySerialNosDO.class, new Long(dataDO.getCreated_by()));
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
			
			int nno1 = snoDO1.getBtSno();
			int i = 0;
			if (bankId > 0) {
				bankTranxData.setRef_id(refId);
				bankTranxData.setBtflag(10);

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
								bankTranxData.setSr_no(nno1);
								snoDO.setBtSno(nno1 + 1);
								rdate = btdObj.getBt_date();
								session.save(snoDO);
								session.save(bankTranxData);
							}
							if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
								btdObj.setBank_acb((btcb.add(tamt).subtract(tc)).toString());
							} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4
									|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9
									|| bankTranxData.getTrans_type() == 10) {
								btdObj.setBank_acb((btcb.subtract(tamt).subtract(tc)).toString());
							}

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
						bankTranxData.setSr_no(nno1);
						snoDO.setBtSno(nno1 + 1);
						session.update(snoDO);
						session.save(bankTranxData);
					}
				} else {
					bankTranxData.setSr_no(nno1);
					if (bankTranxData.getBank_id() == bankDataDO.getId()) {
						snoDO.setBtSno(nno1 + 1);

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

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> saveAgencyCreditNotes  is not succesful");
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
	public synchronized void deleteCreditNotes(long itemId, long agencyId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			CreditNotesDO dataDO = session.get(CreditNotesDO.class, new Long(itemId));

			int cvoCat = 0;
			if (dataDO.getDeleted() == 0) {
				// Save Customer Details
				CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCvo_id()));
				BigDecimal cvob = new BigDecimal(cvoDO.getCbal());
				cvoCat = cvoDO.getCvo_cat();
				int crnType = dataDO.getCredit_note_type();
				if (!(crnType == 1 && cvoCat == 2)) {
					BigDecimal iamt = new BigDecimal(dataDO.getCredit_amount());
					cvoDO.setCbal(cvob.add(iamt).toString());
					session.update(cvoDO);
				}
				dataDO.setDbal_amount(cvoDO.getCbal());

				List<BankTranxsDataDO> btItemsResult = new ArrayList<>();
				long bankId = dataDO.getBank_id();
				if (bankId != 0) {
					Query<BankTranxsDataDO> btqry = session
							.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 10");
					btqry.setParameter(1, dataDO.getId());
					btItemsResult = btqry.getResultList();
					if (!(btItemsResult.isEmpty())) {
						for (BankTranxsDataDO btDetailsDO : btItemsResult) {

							BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
							BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
							BigDecimal tamt = new BigDecimal(dataDO.getCredit_amount());
							if (btDetailsDO.getTrans_type() == 1) {
								bankDataDO.setAcc_cb((ccb.add(tamt)).toString());
							} else if (btDetailsDO.getTrans_type() == 2) {
								bankDataDO.setAcc_cb((ccb.subtract(tamt)).toString());
							}
							session.save(bankDataDO);

							btDetailsDO.setModified_by(dataDO.getCreated_by());
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

				Query<AgencyCVOBalanceDataDO> cvodataDO = session.createQuery(
						"from AgencyCVOBalanceDataDO where ref_id = ?1 and (cvoflag = 81 or cvoflag = 82)");
				cvodataDO.setParameter(1, dataDO.getId());
				List<AgencyCVOBalanceDataDO> cvoItemsResult = cvodataDO.getResultList();
				if (!(cvoItemsResult.isEmpty())) {
					for (AgencyCVOBalanceDataDO cvodDO : cvoItemsResult) {
						cvodDO.setDeleted(3);
						cvodDO.setModified_date(System.currentTimeMillis());
						cvodDO.setModified_by(dataDO.getCreated_by());
						session.update(cvodDO);
					}
				}

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInCreditNote(dataDO.getCreated_by(), dataDO, cvoCat);
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
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> deleteCreditNotes  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Debit Notes - New
	public List<DebitNotesDO> getAgencyDebitNotes(long agencyId) throws BusinessException {
		List<DebitNotesDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<DebitNotesDO> query = session
					.createQuery("from DebitNotesDO where created_by = ?1 and (deleted = ?2 OR deleted = ?3) ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			List<DebitNotesDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (DebitNotesDO dataDO : result) {
					if (dataDO.getDeleted() == 2) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String time = sdf.format(dataDO.getModified_date());
						dataDO.setNote_date(sdf.parse(time).getTime());
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveAgencyDebitNotes(DebitNotesDO dataDO, BankTranxsDataDO bankTranxData,
			AgencyCVOBalanceDataDO cdataDO) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			long refId = 0;
			String invsrid = "0";
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
			
			int nno = snoDO.getDnSno();
			if (nno < 10) {
				dataDO.setSid("DN-" + snoDO.getFy() + "-000" + nno);
			} else if (nno < 100) {
				dataDO.setSid("DN-" + snoDO.getFy() + "-00" + nno);
			} else if (nno < 1000) {
				dataDO.setSid("DN-" + snoDO.getFy() + "-0" + nno);
			} else {
				dataDO.setSid("DN-" + snoDO.getFy() + "-" + nno);
			}
			snoDO.setDnSno(nno + 1);
			session.update(snoDO);

			dataDO.setCbal_amount("0");
			refId = (long) session.save(dataDO);
			invsrid = String.valueOf(dataDO.getSid());

			// Save Customer Details
			CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCvo_id()));
			int cvoCat = cvoDO.getCvo_cat();
			int drnType = dataDO.getDebit_note_type();
			// Save Customer Details and backdates
			if (!(drnType == 1 && cvoCat == 2)) {
				if (cdataDO.getCvo_refid() > 0) {
					long rdate = 0;

					Query<AgencyCVOBalanceDataDO> query2 = session.createQuery(
							"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat != ?3  and cvo_refid= ?4 "
									+ " and inv_ref_no != ?5 order by inv_date,id,cbal_amount asc")
							.setMaxResults(1);
					query2.setParameter(1, cdataDO.getCreated_by());
					query2.setParameter(2, 0);
					query2.setParameter(3, 3);
					query2.setParameter(4, cdataDO.getCvo_refid());
					query2.setParameter(5, "NA");
					List<AgencyCVOBalanceDataDO> result2 = query2.getResultList();

					if (!result2.isEmpty()) {
						if (cdataDO.getInv_date() < result2.get(0).getInv_date()) {
							BigDecimal cb = new BigDecimal(result2.get(0).getCbal_amount());
							BigDecimal iamt = new BigDecimal(cdataDO.getAmount());
							BigDecimal ccb1 = new BigDecimal(result2.get(0).getAmount());
							if (dataDO.getDebit_note_type() == 2) {
								if (result2.get(0).getCvoflag() == 31 || result2.get(0).getCvoflag() == 41
										|| result2.get(0).getCvoflag() == 51) {
									cdataDO.setCbal_amount((cb.add(iamt)).toString());
								} else if (result2.get(0).getCvoflag() == 6 || result2.get(0).getCvoflag() == 81
										|| result2.get(0).getCvoflag() == 82 || result2.get(0).getCvoflag() == 11) {
									cdataDO.setCbal_amount((cb.add(ccb1).add(iamt)).toString());
								} else {
									cdataDO.setCbal_amount((cb.subtract(ccb1).add(iamt)).toString());
								}
							} else {

								if (result2.get(0).getCvoflag() == 7 || result2.get(0).getCvoflag() == 81
										|| result2.get(0).getCvoflag() == 82 || result2.get(0).getCvoflag() == 10) {
									cdataDO.setCbal_amount((cb.add(ccb1).add(iamt)).toString());
								} else {
									cdataDO.setCbal_amount((cb.subtract(ccb1).add(iamt)).toString());
								}
							}
							cdataDO.setRef_id(-1);
							cdataDO.setCreated_date(dataDO.getCreated_date());
							session.save(cdataDO);
						}
					}
					Query<AgencyCVOBalanceDataDO> query3 = session.createQuery(
							"from  AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat!= ?3 and cvo_refid=  ?4"
									+ " and inv_date > ?5 order by inv_date,id,cbal_amount");
					query3.setParameter(1, cdataDO.getCreated_by());
					query3.setParameter(2, 0);
					query3.setParameter(3, 3);
					query3.setParameter(4, cdataDO.getCvo_refid());
					query3.setParameter(5, cdataDO.getInv_date());
					List<AgencyCVOBalanceDataDO> result3 = query3.getResultList();
					BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
					BigDecimal iamt = new BigDecimal(cdataDO.getAmount());

					if (!result3.isEmpty()) {
						for (AgencyCVOBalanceDataDO dsido : result3) {
							if (cdataDO.getInv_date() < dsido.getInv_date()) {
								BigDecimal btcb = new BigDecimal(dsido.getCbal_amount());
								BigDecimal lbtcb = new BigDecimal(dsido.getAmount());

								if ((rdate == 0 && cdataDO.getRef_id() != -1) || rdate > dsido.getInv_date()) {
									if (dataDO.getDebit_note_type() == 2) {
										if (dsido.getCvoflag() == 31 || dsido.getCvoflag() == 41
												|| dsido.getCvoflag() == 51) {
											cdataDO.setCbal_amount((btcb.add(iamt)).toString());
										} else if (dsido.getCvoflag() == 6 || dsido.getCvoflag() == 81
												|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 11) {
											cdataDO.setCbal_amount((btcb.add(lbtcb).add(iamt)).toString());
										} else {
											cdataDO.setCbal_amount((btcb.subtract(lbtcb).add(iamt)).toString());
										}
									} else {
										if (dsido.getCvoflag() == 7 || dsido.getCvoflag() == 81
												|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 10) {
											cdataDO.setCbal_amount((btcb.add(lbtcb).add(iamt)).toString());
										} else {
											cdataDO.setCbal_amount((btcb.subtract(lbtcb).add(iamt)).toString());
										}

									}
									cdataDO.setInv_ref_no(invsrid);
									rdate = dsido.getInv_date();
									cdataDO.setCreated_date(dataDO.getCreated_date());
									session.save(cdataDO);
								}

								dsido.setCbal_amount((btcb.add(iamt)).toString());

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
						if (cdataDO.getRef_id() == -1) {
							cdataDO.setRef_id(refId);
							cdataDO.setInv_ref_no(invsrid);
							cdataDO.setCreated_date(dataDO.getCreated_date());
							session.save(cdataDO);
						}
					} else {
						cdataDO.setInv_ref_no(invsrid);
						cvoDO.setCbal(ccb.add(iamt).toString());
						cdataDO.setCbal_amount(ccb.add(iamt).toString());
					}
					if (cdataDO.getRef_id() == 0) {
						cdataDO.setRef_id(refId);
						cvoDO.setCbal(ccb.add(iamt).toString());
					}

					session.update(cvoDO);
					cdataDO.setCvo_refid(cvoDO.getId());
					cdataDO.setCvo_cat(cvoDO.getCvo_cat());
					cdataDO.setCreated_date(dataDO.getCreated_date());
					session.save(cdataDO);
				}
			}

			AgencySerialNosDO snoDO1 = session.get(AgencySerialNosDO.class, new Long(dataDO.getCreated_by()));
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
			
			int nno1 = snoDO1.getBtSno();
			// Save Bank Transaction
			if (dataDO.getDebit_note_type() == 1) {
				long bankId = bankTranxData.getBank_id();
				int i = 0;
				if (bankId > 0) {
					bankTranxData.setRef_id(refId);
					bankTranxData.setBtflag(11);

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
									bankTranxData.setSr_no(nno1);
									snoDO.setBtSno(nno1 + 1);
									rdate = btdObj.getBt_date();
									session.save(snoDO);
									session.save(bankTranxData);
								}
								if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
									btdObj.setBank_acb((btcb.add(tamt).subtract(tc)).toString());
								} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4
										|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9
										|| bankTranxData.getTrans_type() == 10) {
									btdObj.setBank_acb((btcb.subtract(tamt).subtract(tc)).toString());
								}

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
							bankTranxData.setSr_no(nno1);
							snoDO.setBtSno(nno1 + 1);
							session.update(snoDO);
							session.save(bankTranxData);
						}
					} else {
						bankTranxData.setSr_no(nno1);
						if (bankTranxData.getBank_id() == bankDataDO.getId()) {
							snoDO.setBtSno(nno1 + 1);

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
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> saveAgencyDebitNotes  is not succesful");
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
	public synchronized void deleteDebitNotes(long itemId, long agencyId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			DebitNotesDO dataDO = session.get(DebitNotesDO.class, new Long(itemId));

			if (dataDO.getDeleted() == 0) {
				// Save Customer Details
				CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCvo_id()));
				BigDecimal cvob = new BigDecimal(cvoDO.getCbal());
				int cvoCat = cvoDO.getCvo_cat();
				int drnType = dataDO.getDebit_note_type();
				if (!(drnType == 1 && cvoCat == 2)) {
					BigDecimal iamt = new BigDecimal(dataDO.getDebit_amount());
					cvoDO.setCbal(cvob.subtract(iamt).toString());
					session.update(cvoDO);
				}
				dataDO.setDbal_amount(cvoDO.getCbal());

				List<BankTranxsDataDO> btItemsResult = new ArrayList<>();
				long bankId = dataDO.getBank_id();
				if (bankId != 0 && dataDO.getDebit_note_type() == 1) {
					Query<BankTranxsDataDO> btqry = session
							.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 11");
					btqry.setParameter(1, dataDO.getId());
					btItemsResult = btqry.getResultList();
					if (!(btItemsResult.isEmpty())) {
						for (BankTranxsDataDO btDetailsDO : btItemsResult) {
							BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
							BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
							BigDecimal tamt = new BigDecimal(dataDO.getDebit_amount());
							bankDataDO.setAcc_cb((ccb.add(tamt)).toString());
							session.save(bankDataDO);

							btDetailsDO.setModified_by(dataDO.getCreated_by());
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

				Query<AgencyCVOBalanceDataDO> cvodataDO = session.createQuery(
						"from AgencyCVOBalanceDataDO where ref_id = ?1 and (cvoflag = 91 or cvoflag = 92)");
				cvodataDO.setParameter(1, dataDO.getId());
				List<AgencyCVOBalanceDataDO> cvoItemsResult = cvodataDO.getResultList();
				if (!(cvoItemsResult.isEmpty())) {
					for (AgencyCVOBalanceDataDO cvodDO : cvoItemsResult) {
						cvodDO.setDeleted(3);
						cvodDO.setModified_date(System.currentTimeMillis());
						cvodDO.setModified_by(dataDO.getCreated_by());
						session.update(cvodDO);
					}
				}

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInDebitNote(dataDO.getCreated_by(), dataDO, cvoCat);
				if ((bankId != 0) && (dataDO.getDebit_note_type() == 1) && (!(btItemsResult.isEmpty())))
					udodt.updateDataOnDeleteInBankTransactions(dataDO.getCreated_by(), btItemsResult);
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> deleteDebitNotes  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Purchase Returns Data
	public List<PurchaseReturnDO> getPurchaseReturnsData(long agencyId) throws BusinessException {
		List<PurchaseReturnDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<PurchaseReturnDO> query = session
					.createQuery("from PurchaseReturnDO where created_by = ?1 and (deleted = ?2  OR deleted = ?3)");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			List<PurchaseReturnDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (PurchaseReturnDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<PurchaseReturnDetailsDO> squery = session
							.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
					if (!itemsResult.isEmpty()) {
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
	public void savePurchaseReturnsData(PurchaseReturnDO dataDO, List<PurchaseReturnDetailsDO> doList,
			List<AgencyStockDataDO> asdoList, AgencyCVOBalanceDataDO cdataDO) throws BusinessException {
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
			
			int nno = snoDO.getPrSno();
			if (nno < 10) {
				dataDO.setSid("PR-" + snoDO.getFy() + "-00" + nno);
			} else if (nno < 100) {
				dataDO.setSid("PR-" + snoDO.getFy() + "-0" + nno);
			} else {
				dataDO.setSid("PR-" + snoDO.getFy() + "-" + nno);
			}
			snoDO.setPrSno(nno + 1);
			session.update(snoDO);

			// Save Vendor Details
			if (dataDO.getCvo_id() > 0) {
				dataDO.setVbal_amount("0");
			}
			session.save(dataDO);

			// Save Vendor Details & BALANCE ADJUSTEMENT
			if (dataDO.getCvo_id() > 0) {
				CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCvo_id()));
				if (cvoDO.getCvo_cat() != 2) {
					long rdate = 0;
					Query<AgencyCVOBalanceDataDO> query3 = session.createQuery(
							"from  AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat!= ?3 and cvo_refid=  ?4"
									+ " and inv_date > ?5 order by inv_date,id,cbal_amount");
					query3.setParameter(1, cdataDO.getCreated_by());
					query3.setParameter(2, 0);
					query3.setParameter(3, 3);
					query3.setParameter(4, cdataDO.getCvo_refid());
					query3.setParameter(5, cdataDO.getInv_date());

					List<AgencyCVOBalanceDataDO> result3 = query3.getResultList();
					BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
					BigDecimal iamt = new BigDecimal(cdataDO.getAmount());
					if (!result3.isEmpty()) {
						for (AgencyCVOBalanceDataDO dsido : result3) {
							if (cdataDO.getInv_date() < dsido.getInv_date()) {
								BigDecimal tamt = new BigDecimal(cdataDO.getAmount());
								BigDecimal btcb = new BigDecimal(dsido.getCbal_amount());
								BigDecimal lbtcb = new BigDecimal(dsido.getAmount());
								if ((rdate == 0 && cdataDO.getRef_id() != -1) || rdate > dsido.getInv_date()) {

									if (dsido.getCvoflag() == 31 || dsido.getCvoflag() == 41
											|| dsido.getCvoflag() == 51) {
										cdataDO.setCbal_amount((btcb.subtract(tamt)).toString());
									} else if (dsido.getCvoflag() == 6 || dsido.getCvoflag() == 7
											|| dsido.getCvoflag() == 81 || dsido.getCvoflag() == 82
											|| dsido.getCvoflag() == 10 || dsido.getCvoflag() == 11) {
										cdataDO.setCbal_amount((btcb.add(lbtcb).subtract(iamt)).toString());
									} else {
										cdataDO.setCbal_amount((btcb.subtract(lbtcb).subtract(iamt)).toString());
									}
									cdataDO.setInv_ref_no(dataDO.getSid());
									rdate = dsido.getInv_date();
									cdataDO.setCreated_date(dataDO.getCreated_date());
									session.save(cdataDO);

								}
								dsido.setCbal_amount((btcb.subtract(tamt)).toString());

								@SuppressWarnings("rawtypes")
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
						if (cdataDO.getRef_id() == -1) {
							cdataDO.setInv_ref_no(dataDO.getSid());
							cdataDO.setRef_id(dataDO.getId());
							cdataDO.setCreated_date(dataDO.getCreated_date());
							session.save(cdataDO);
						}
					} else {
						cdataDO.setInv_ref_no(dataDO.getSid());
						cvoDO.setCbal(ccb.subtract(iamt).toString());
						cdataDO.setCbal_amount(ccb.subtract(iamt).toString());
					}

					if (cdataDO.getRef_id() == 0) {
						cdataDO.setRef_id(dataDO.getId());
						cvoDO.setCbal(ccb.subtract(iamt).toString());
					}
					session.update(cvoDO);
					cdataDO.setCvo_refid(cvoDO.getId());
					cdataDO.setCvo_cat(cvoDO.getCvo_cat());
					cdataDO.setCreated_date(dataDO.getCreated_date());
					session.save(cdataDO);
				}
			}

			for (PurchaseReturnDetailsDO detailsDO : doList) {
				detailsDO.setPr_ref_id(dataDO.getId());

				int productCode = detailsDO.getProd_code();
				if (productCode < 13) {

					int csfulls = 0;
					int csempties = 0;

					Query<EquipmentDataDO> query = session.createQuery(
							"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
					query.setParameter(1, dataDO.getCreated_by());
					query.setParameter(2, 0);
					query.setParameter(3, productCode);
					List<EquipmentDataDO> result = query.getResultList();
					if (!result.isEmpty()) {
						for (EquipmentDataDO doObj : result) {

							int cfs = doObj.getCs_fulls();
							int ecs = doObj.getCs_emptys();
							doObj.setCs_fulls(cfs - detailsDO.getRtn_qty());
							if (productCode < 10)
								doObj.setCs_emptys(ecs + detailsDO.getRtn_qty());
							session.save(doObj);
							session.save(detailsDO);

							csfulls = doObj.getCs_fulls();
							csempties = doObj.getCs_emptys();
						}

						Query<AgencyStockDataDO> checkDataQ = session.createQuery(
								"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date > ?4");
						checkDataQ.setParameter(1, dataDO.getCreated_by());
						checkDataQ.setParameter(2, 0);
						checkDataQ.setParameter(3, productCode);
						checkDataQ.setParameter(4, dataDO.getInv_date());
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
							sbalQ.setParameter(4, dataDO.getInv_date());

							List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
							if (!(sbalRes.isEmpty())) {
								for (AgencyStockDataDO asdatado : sbalRes) {
									int cfulls = asdatado.getCs_fulls();
									int cempties = asdatado.getCs_emptys();

									detailsDO.setCs_fulls(cfulls - detailsDO.getRtn_qty());
									if (productCode < 10) {
										detailsDO.setCs_emptys(cempties + detailsDO.getRtn_qty());
									}
									session.save(detailsDO);

									for (AgencyStockDataDO asDO : asdoList) {
										if (asDO.getProd_code() == productCode) {
											asDO.setRef_id(dataDO.getId());
											asDO.setInv_no(dataDO.getSid());
											asDO.setFulls(detailsDO.getRtn_qty());
											asDO.setCs_fulls(cfulls - detailsDO.getRtn_qty());
											if (productCode < 10) {
												asDO.setEmpties(detailsDO.getRtn_qty());
											}
											asDO.setCs_emptys(cempties + detailsDO.getRtn_qty());
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
							balUpdateQ.setParameter(4, dataDO.getInv_date());

							List<AgencyStockDataDO> balUpdateRes = balUpdateQ.getResultList();
							if (!(balUpdateRes.isEmpty())) {
								for (AgencyStockDataDO bUpInasDO : balUpdateRes) {
									bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls() - detailsDO.getRtn_qty());
									if (productCode < 10) {
										bUpInasDO.setCs_emptys(bUpInasDO.getCs_emptys() + detailsDO.getRtn_qty());
									}
									session.update(bUpInasDO);
								}
							}
						} else {
							// If User enters Forward date:

							detailsDO.setCs_fulls(csfulls);
							detailsDO.setCs_emptys(csempties);
							session.save(detailsDO);

							for (AgencyStockDataDO asDO : asdoList) {
								if (asDO.getProd_code() == detailsDO.getProd_code()) {
									asDO.setRef_id(dataDO.getId());
									asDO.setInv_no(dataDO.getSid());
									asDO.setFulls(detailsDO.getRtn_qty());
									if (productCode < 10)
										asDO.setEmpties(detailsDO.getRtn_qty());
									asDO.setCs_fulls(csfulls);
									asDO.setCs_emptys(csempties);
									asDO.setCreated_date(dataDO.getCreated_date());
									session.save(asDO);
								}
							}

						}
					}
				} else {
					// For ARB
					int cstock = 0;
					int arbcode = 0;
					long pc = productCode;
					Query<ARBDataDO> query = session
							.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 and id = ?3");
					query.setParameter(1, dataDO.getCreated_by());
					query.setParameter(2, 0);
					query.setParameter(3, pc);
					List<ARBDataDO> result1 = query.getResultList();
					if (!result1.isEmpty()) {
						for (ARBDataDO doObj : result1) {
							int cs = doObj.getCurrent_stock();
							doObj.setCurrent_stock(cs - detailsDO.getRtn_qty());
							session.save(doObj);
							cstock = doObj.getCurrent_stock();
							arbcode = doObj.getProd_code();
						}
					}

					Query<AgencyStockDataDO> checkDataQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_id = ?3 and trans_date > ?4");
					checkDataQ.setParameter(1, dataDO.getCreated_by());
					checkDataQ.setParameter(2, 0);
					checkDataQ.setParameter(3, pc);
					checkDataQ.setParameter(4, dataDO.getInv_date());
					List<AgencyStockDataDO> checkDataR = checkDataQ.getResultList();

					if (!(checkDataR.isEmpty())) {
						// saving dataDO if backdate is entered
						Query<AgencyStockDataDO> sbalQ = session.createQuery(
								"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_id = ?3 and trans_date <= ?4 "
										+ "order by trans_date desc , created_date desc")
								.setMaxResults(1);
						sbalQ.setParameter(1, dataDO.getCreated_by());
						sbalQ.setParameter(2, 0);
						sbalQ.setParameter(3, pc);
						sbalQ.setParameter(4, dataDO.getInv_date());

						List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
						if (!(sbalRes.isEmpty())) {
							for (AgencyStockDataDO asdatado : sbalRes) {

								int cfulls = asdatado.getCs_fulls();
								detailsDO.setCs_fulls(cfulls - detailsDO.getRtn_qty());
								session.save(detailsDO);

								for (AgencyStockDataDO asDO : asdoList) {
									if (asDO.getProd_code() == pc) {
										asDO.setProd_id(pc);
										asDO.setRef_id(dataDO.getId());
										asDO.setProd_code(arbcode);
										asDO.setInv_no(dataDO.getSid());
										asDO.setFulls(detailsDO.getRtn_qty());
										asDO.setCs_fulls(cfulls - detailsDO.getRtn_qty());
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
						balUpdateQ.setParameter(3, pc);
						balUpdateQ.setParameter(4, dataDO.getInv_date());

						List<AgencyStockDataDO> balUpdateRes = balUpdateQ.getResultList();
						if (!(balUpdateRes.isEmpty())) {
							for (AgencyStockDataDO bUpInasDO : balUpdateRes) {
								bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls() - detailsDO.getRtn_qty());
								session.update(bUpInasDO);
							}
						}
					} else {
						// If User enters Forward date:
						detailsDO.setCs_fulls(cstock);
						session.save(detailsDO);

						for (AgencyStockDataDO asDO : asdoList) {
							if (asDO.getProd_code() == pc) {
								asDO.setProd_id(pc);
								asDO.setRef_id(dataDO.getId());
								asDO.setProd_code(arbcode);
								asDO.setInv_no(dataDO.getSid());
								asDO.setFulls(detailsDO.getRtn_qty());
								asDO.setCs_fulls(cstock);
								asDO.setCreated_date(dataDO.getCreated_date());
								session.save(asDO);
							}
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
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> savePurchaseReturnsData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	@SuppressWarnings({ "unchecked" })
	public synchronized void deletePurchaseReturnsData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			PurchaseReturnDO dataDO = session.get(PurchaseReturnDO.class, new Long(itemId));

			if (dataDO.getDeleted() == 0) {
				Query<PurchaseReturnDetailsDO> squery = session
						.createQuery("from PurchaseReturnDetailsDO where pr_ref_id = ?1");
				squery.setParameter(1, dataDO.getId());
				List<PurchaseReturnDetailsDO> itemsResult = squery.getResultList();
				int cvoCat = 0;
				// Save Vendor Details
				if (dataDO.getCvo_id() > 0) {
					CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCvo_id()));
					if (cvoDO.getCvo_cat() != 2) {
						BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
						BigDecimal iamt = new BigDecimal(dataDO.getInv_amt());
						cvoDO.setCbal(ccb.add(iamt).toString());
						session.update(cvoDO);
					}
					dataDO.setDbal_amount(cvoDO.getCbal());
					cvoCat = cvoDO.getCvo_cat();
				}

				for (PurchaseReturnDetailsDO detailsDO : itemsResult) {
					detailsDO.setPr_ref_id(dataDO.getId());
					int productCode = detailsDO.getProd_code();
					if (productCode < 13) {
						Query<EquipmentDataDO> query = session.createQuery(
								"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
						query.setParameter(1, dataDO.getCreated_by());
						query.setParameter(2, 0);
						query.setParameter(3, productCode);
						List<EquipmentDataDO> result = query.getResultList();
						if (!result.isEmpty()) {
							for (EquipmentDataDO doObj : result) {
								int cfs = doObj.getCs_fulls();
								int ecs = doObj.getCs_emptys();
								doObj.setCs_fulls(cfs + detailsDO.getRtn_qty());
								if (productCode < 10)
									doObj.setCs_emptys(ecs - detailsDO.getRtn_qty());
								session.save(doObj);
								detailsDO.setDs_fulls(doObj.getCs_fulls());
								detailsDO.setDs_emptys(doObj.getCs_emptys());
								session.save(detailsDO);
							}
						}
					} else {
						long pc = productCode;
						Query<ARBDataDO> query = session
								.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 and id = ?3");
						query.setParameter(1, dataDO.getCreated_by());
						query.setParameter(2, 0);
						query.setParameter(3, pc);
						List<ARBDataDO> result1 = query.getResultList();
						if (!result1.isEmpty()) {
							for (ARBDataDO doObj : result1) {
								int cs = doObj.getCurrent_stock();
								doObj.setCurrent_stock(cs + detailsDO.getRtn_qty());
								session.save(doObj);
								detailsDO.setDs_fulls(doObj.getCurrent_stock());
								session.save(detailsDO);
							}
						}
					}

				}
				dataDO.setDeleted(2);
				dataDO.setModified_by(dataDO.getCreated_by());
				dataDO.setModified_date(System.currentTimeMillis());
				session.update(dataDO);

				Query<AgencyStockDataDO> asdataDO = session
						.createQuery("from AgencyStockDataDO where ref_id = ?1 and stock_flag = 3");
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

				Query<AgencyCVOBalanceDataDO> cvodataDO = session
						.createQuery("from AgencyCVOBalanceDataDO where ref_id = ?1 and cvoflag = 10");
				cvodataDO.setParameter(1, dataDO.getId());
				List<AgencyCVOBalanceDataDO> cvoItemsResult = cvodataDO.getResultList();
				if (!(cvoItemsResult.isEmpty())) {
					for (AgencyCVOBalanceDataDO cvodDO : cvoItemsResult) {
						cvodDO.setDeleted(3);
						cvodDO.setModified_date(System.currentTimeMillis());
						cvodDO.setModified_by(dataDO.getCreated_by());
						session.update(cvodDO);
					}
				}

				tx.commit();

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInPurchaseReturns(dataDO, itemsResult, cvoCat);
			}

		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> deletePurchaseReturnsData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Sales Returns Data
	public List<SalesReturnDO> getSalesReturnsData(long agencyId) throws BusinessException {
		List<SalesReturnDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<SalesReturnDO> query = session
					.createQuery("from SalesReturnDO where created_by = ?1 and deleted = ?2");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<SalesReturnDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (SalesReturnDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<SalesReturnDetailsDO> squery = session
							.createQuery("from SalesReturnDetailsDO where sr_ref_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<SalesReturnDetailsDO> itemsResult = squery.getResultList();
					if (!itemsResult.isEmpty()) {
						dataDO.getDetailsList().addAll(itemsResult);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveSalesReturnsData(SalesReturnDO dataDO, List<SalesReturnDetailsDO> doList,
			List<AgencyStockDataDO> asdoList, List<BankTranxsDataDO> bankTranxDOList, 
			AgencyCVOBalanceDataDO cdataDO) throws BusinessException {
		
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
			
			long refId = 0;
			long cvorefId = 0;

			int nno = snoDO.getSrSno();
			dataDO.setSr_no(nno);
			snoDO.setSrSno(nno + 1);
			session.update(snoDO);

			// Save Customer/Vendor Details
//			if (dataDO.getCvo_id() > 0) {
				dataDO.setCbal_amt("0");
				refId = (long) session.save(dataDO);
				cvorefId = refId;
//			}
			
			// Save Vendor Details
			if (cdataDO.getCvo_refid() > 0) {
				CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCvo_id()));
				if (cdataDO.getCvo_cat() == 1) {
					long rdate = 0;

					Query<AgencyCVOBalanceDataDO> query3 = session.createQuery(
							"from AgencyCVOBalanceDataDO where created_by = ?1 and deleted = ?2 and cvo_cat= ?3 and cvo_refid=  ?4"
									+ " and inv_date > ?5 order by inv_date,id,cbal_amount");
					query3.setParameter(1, cdataDO.getCreated_by());
					query3.setParameter(2, 0);
					query3.setParameter(3, 1);
					query3.setParameter(4, cdataDO.getCvo_refid());
					query3.setParameter(5, cdataDO.getInv_date());

					List<AgencyCVOBalanceDataDO> result3 = query3.getResultList();
					BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
					BigDecimal iamt = new BigDecimal(cdataDO.getAmount());
					if (!result3.isEmpty()) {
						for (AgencyCVOBalanceDataDO dsido : result3) {
							if (cdataDO.getInv_date() < dsido.getInv_date()) {
								BigDecimal btcb = new BigDecimal(dsido.getCbal_amount());
								BigDecimal lbtcb = new BigDecimal(dsido.getAmount());
								if ((rdate == 0 && cdataDO.getRef_id() != -1) || rdate > dsido.getInv_date()) {

									if (dsido.getCvoflag() == 31 || dsido.getCvoflag() == 41
											|| dsido.getCvoflag() == 51) {
										cdataDO.setCbal_amount((btcb.subtract(iamt)).toString());
									} else if (dsido.getCvoflag() == 6 || dsido.getCvoflag() == 81
											|| dsido.getCvoflag() == 82 || dsido.getCvoflag() == 10
											|| dsido.getCvoflag() == 11) {
										cdataDO.setCbal_amount((btcb.add(lbtcb).subtract(iamt)).toString());
									} else {
										cdataDO.setCbal_amount((btcb.subtract(lbtcb).subtract(iamt)).toString());
									}
									cdataDO.setRef_id(cvorefId);
									cdataDO.setInv_ref_no(Long.toString(dataDO.getSr_no()));
									rdate = dsido.getInv_date();
									cdataDO.setCreated_date(dataDO.getCreated_date());
									session.save(cdataDO);
									refId = (long) session.save(dataDO);
									cvorefId = refId;

								}
								dsido.setCbal_amount((btcb.subtract(iamt)).toString());

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
						if (cdataDO.getRef_id() == -1) {
							cdataDO.setInv_ref_no(Long.toString(dataDO.getSr_no()));
							cdataDO.setRef_id(dataDO.getId());
							cdataDO.setCreated_date(dataDO.getCreated_date());
							session.save(cdataDO);
						}
					} else {
						cdataDO.setInv_ref_no(Long.toString(dataDO.getSr_no()));
						cvoDO.setCbal(ccb.subtract(iamt).toString());
						cdataDO.setCbal_amount(ccb.subtract(iamt).toString());
					}
					if (cdataDO.getRef_id() == 0) {
						cdataDO.setRef_id(dataDO.getId());
						cvoDO.setCbal(ccb.subtract(iamt).toString());
					}
					session.update(cvoDO);
					cdataDO.setCvo_refid(cvoDO.getId());
					cdataDO.setCvo_cat(cvoDO.getCvo_cat());
					cdataDO.setCreated_date(dataDO.getCreated_date());
					session.save(cdataDO);
				} else if (cvoDO.getCvo_cat() != 1) {
					cdataDO.setCbal_amount(cvoDO.getCbal());
					cdataDO.setCreated_date(dataDO.getCreated_date());
					session.save(cdataDO);
				}
			}
			
			// UPDATE STOCK DATA
			for (SalesReturnDetailsDO detailsDO : doList) {
				detailsDO.setSr_ref_id(dataDO.getId());
				int productCode = detailsDO.getProd_code();
				if (productCode < 13) {
					int csfulls = 0;
					int csempties = 0;

					Query<EquipmentDataDO> query = session.createQuery(
							"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
					query.setParameter(1, dataDO.getCreated_by());
					query.setParameter(2, 0);
					query.setParameter(3, productCode);
					List<EquipmentDataDO> result = query.getResultList();
					if (!result.isEmpty()) {
						for (EquipmentDataDO doObj : result) {
							int cfs = doObj.getCs_fulls();
							int ces = doObj.getCs_emptys();
							doObj.setCs_fulls(cfs + detailsDO.getRtn_qty());
							if (productCode < 10)
								doObj.setCs_emptys(ces - detailsDO.getRtn_qty());
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
					checkDataQ.setParameter(4, dataDO.getInv_date());
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
						sbalQ.setParameter(4, dataDO.getInv_date());

						List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
						if (!(sbalRes.isEmpty())) {
							for (AgencyStockDataDO asdatado : sbalRes) {

								int cfulls = asdatado.getCs_fulls();
								int cempties = asdatado.getCs_emptys();

								detailsDO.setCs_fulls(cfulls + detailsDO.getRtn_qty());
								if (productCode < 10)
									detailsDO.setCs_emptys(cempties - detailsDO.getRtn_qty());
								session.save(detailsDO);

								for (AgencyStockDataDO asDO : asdoList) {
									if (asDO.getProd_code() == productCode) {
										asDO.setRef_id(dataDO.getId());
										asDO.setInv_no(Long.toString(dataDO.getSr_no()));
										asDO.setFulls(detailsDO.getRtn_qty());
										asDO.setCs_fulls(cfulls + detailsDO.getRtn_qty());
										if (productCode < 10) {
											asDO.setEmpties(detailsDO.getRtn_qty());
											asDO.setCs_emptys(cempties - detailsDO.getRtn_qty());
										}
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
						balUpdateQ.setParameter(4, dataDO.getInv_date());

						List<AgencyStockDataDO> balUpdateRes = balUpdateQ.getResultList();
						if (!(balUpdateRes.isEmpty())) {
							for (AgencyStockDataDO bUpInasDO : balUpdateRes) {
								bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls() + detailsDO.getRtn_qty());
								if (productCode < 10)
									bUpInasDO.setCs_emptys(bUpInasDO.getCs_emptys() - detailsDO.getRtn_qty());
								session.update(bUpInasDO);
							}
						}
					} else {
						// If User enters Forward date:
						detailsDO.setCs_fulls(csfulls);
						detailsDO.setCs_emptys(csempties);
						session.save(detailsDO);
						for (AgencyStockDataDO asDO : asdoList) {
							if (asDO.getProd_code() == productCode) {
								asDO.setRef_id(dataDO.getId());
								asDO.setInv_no(Long.toString(dataDO.getSr_no()));
								asDO.setFulls(detailsDO.getRtn_qty());
								if (productCode < 10)
									asDO.setEmpties(detailsDO.getRtn_qty());
								asDO.setCs_fulls(csfulls);
								asDO.setCs_emptys(csempties);
								asDO.setCreated_date(dataDO.getCreated_date());
								session.save(asDO);
							}
						}
					}
				} else {
					// FOR ARB
					long pc = productCode;

					int cstock = 0;
					int arbcode = 0;
					Query<ARBDataDO> query = session
							.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 and id = ?3");
					query.setParameter(1, dataDO.getCreated_by());
					query.setParameter(2, 0);
					query.setParameter(3, pc);
					List<ARBDataDO> result = query.getResultList();
					if (!result.isEmpty()) {
						for (ARBDataDO doObj : result) {
							int cs = doObj.getCurrent_stock();
							doObj.setCurrent_stock(cs + detailsDO.getRtn_qty());
							session.save(doObj);
							cstock = doObj.getCurrent_stock();
							arbcode = doObj.getProd_code();
						}
					}

					Query<AgencyStockDataDO> checkDataQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_id = ?3 and trans_date > ?4");
					checkDataQ.setParameter(1, dataDO.getCreated_by());
					checkDataQ.setParameter(2, 0);
					checkDataQ.setParameter(3, pc);
					checkDataQ.setParameter(4, dataDO.getInv_date());
					List<AgencyStockDataDO> checkDataR = checkDataQ.getResultList();

					if (!(checkDataR.isEmpty())) {
						// saving dataDO if backdate is entered
						Query<AgencyStockDataDO> sbalQ = session.createQuery(
								"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_id = ?3 and trans_date <= ?4 "
										+ "order by trans_date desc , created_date desc")
								.setMaxResults(1);
						sbalQ.setParameter(1, dataDO.getCreated_by());
						sbalQ.setParameter(2, 0);
						sbalQ.setParameter(3, pc);
						sbalQ.setParameter(4, dataDO.getInv_date());

						List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
						if (!(sbalRes.isEmpty())) {
							for (AgencyStockDataDO asdatado : sbalRes) {

								int cfulls = asdatado.getCs_fulls();

								detailsDO.setCs_fulls(cfulls + detailsDO.getRtn_qty());
								session.save(detailsDO);

								for (AgencyStockDataDO asDO : asdoList) {
									if (asDO.getProd_code() == pc) {
										asDO.setProd_code(arbcode);
										asDO.setProd_id(pc);
										asDO.setRef_id(dataDO.getId());
										asDO.setInv_no(Long.toString(dataDO.getSr_no()));
										asDO.setFulls(detailsDO.getRtn_qty()); // no empties. So 0(passed in factory)
										asDO.setCs_fulls(cfulls + detailsDO.getRtn_qty());
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
						balUpdateQ.setParameter(3, pc);
						balUpdateQ.setParameter(4, dataDO.getInv_date());

						List<AgencyStockDataDO> balUpdateRes = balUpdateQ.getResultList();
						if (!(balUpdateRes.isEmpty())) {
							for (AgencyStockDataDO bUpInasDO : balUpdateRes) {
								bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls() + detailsDO.getRtn_qty());
								session.update(bUpInasDO);
							}
						}
					} else {
						// If User enters Forward date:

						detailsDO.setCs_fulls(cstock);
						session.save(detailsDO);

						for (AgencyStockDataDO asDO : asdoList) {
							if (asDO.getProd_code() == pc) {
								asDO.setProd_code(arbcode);
								asDO.setProd_id(pc);
								asDO.setRef_id(dataDO.getId());
								asDO.setInv_no(Long.toString(dataDO.getSr_no()));
								asDO.setFulls(detailsDO.getRtn_qty());
								asDO.setCs_fulls(cstock);
								asDO.setCreated_date(dataDO.getCreated_date());
								session.save(asDO);
							}
						}
					}
				}
			}

			// Save Cash Data
			if (dataDO.getPayment_terms() == 1) {
				Query<AgencyCashDataDO> query1 = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and trans_type != ?2 and t_date >= ?3 order by t_date,created_date");
				query1.setParameter(1, dataDO.getCreated_by());
				query1.setParameter(2, 11);
				query1.setParameter(3, dataDO.getInv_date());
				List<AgencyCashDataDO> result1 = query1.getResultList();

				for (AgencyCashDataDO cashDO : result1) {
					if (dataDO.getInv_date() < cashDO.getT_date()) {
						BigDecimal tamt = new BigDecimal(dataDO.getInv_amt());
						BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
						cashDO.setCash_balance((btcb.subtract(tamt)).toString());
					} else if (dataDO.getInv_date() == cashDO.getT_date()) {
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
					query2.setParameter("tdate", dataDO.getInv_date());
					query2.executeUpdate();
				}
			}
			
			// Save Bank Transaction
			if (dataDO.getPayment_terms() == 2 || dataDO.getPayment_terms() == 3) {
				for (BankTranxsDataDO bankTranxData : bankTranxDOList) {
					AgencySerialNosDO snoDO1 = session.get(AgencySerialNosDO.class,new Long(bankTranxData.getCreated_by()));
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
					
					int nno1 = snoDO1.getBtSno();
					long bankId = bankTranxData.getBank_id();
					int i=0;

					if (bankId > 0) {
						bankTranxData.setRef_id(refId);
						bankTranxData.setBtflag(16);
						
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
										bankTranxData.setSr_no(nno1);
										snoDO1.setBtSno(nno1 + 1);
										rdate=btdObj.getBt_date();
										session.save(snoDO1);
										session.save(bankTranxData);
									}
									btdObj.setBank_acb((btcb.subtract(tamt).subtract(tc)).toString());
												
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
								bankTranxData.setSr_no(nno1);
								snoDO1.setBtSno(nno1 + 1);
								session.update(snoDO1);
								session.save(bankTranxData);
							}
						}else {
							bankTranxData.setSr_no(nno1);
							if (bankTranxData.getBank_id() == bankDataDO.getId()) {
								snoDO1.setBtSno(nno1 + 1);
								
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
		}catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> saveSalesReturnsData  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized void deleteSalesReturnsData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			SalesReturnDO dataDO = session.get(SalesReturnDO.class, new Long(itemId));

			if (dataDO.getDeleted() == 0) {
				Query<SalesReturnDetailsDO> squery = session
						.createQuery("from SalesReturnDetailsDO where sr_ref_id = ?1");
				squery.setParameter(1, dataDO.getId());
				List<SalesReturnDetailsDO> itemsResult = squery.getResultList();

				// Save Customer Details
				if (dataDO.getCvo_id() > 0) {
					CVODataDO cvoDO = session.get(CVODataDO.class, new Long(dataDO.getCvo_id()));
					BigDecimal ccb = new BigDecimal(cvoDO.getCbal());
					BigDecimal iamt = new BigDecimal(dataDO.getInv_amt());
					cvoDO.setCbal(ccb.add(iamt).toString());
					session.update(cvoDO);
					dataDO.setDbal_amt(cvoDO.getCbal());
				}
				for (SalesReturnDetailsDO detailsDO : itemsResult) {
					int productCode = detailsDO.getProd_code();
					if (productCode < 13) {
						Query<EquipmentDataDO> query = session.createQuery(
								"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
						query.setParameter(1, dataDO.getCreated_by());
						query.setParameter(2, 0);
						query.setParameter(3, productCode);
						List<EquipmentDataDO> result = query.getResultList();
						if (!result.isEmpty()) {
							for (EquipmentDataDO doObj : result) {
								int cfs = doObj.getCs_fulls();
								int ecs = doObj.getCs_emptys();
								doObj.setCs_fulls(cfs - detailsDO.getRtn_qty());
								if (productCode < 10)
									doObj.setCs_emptys(ecs + detailsDO.getRtn_qty());
								session.save(doObj);
								detailsDO.setDs_fulls(doObj.getCs_fulls());
								detailsDO.setDs_emptys(doObj.getCs_emptys());
								session.save(detailsDO);
							}
						}
					} else {
						long pc = productCode;
						Query<ARBDataDO> query = session
								.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 and id = ?3");
						query.setParameter(1, dataDO.getCreated_by());
						query.setParameter(2, 0);
						query.setParameter(3, pc);
						List<ARBDataDO> result1 = query.getResultList();
						if (!result1.isEmpty()) {
							for (ARBDataDO doObj : result1) {
								int cs = doObj.getCurrent_stock();
								doObj.setCurrent_stock(cs - detailsDO.getRtn_qty());
								detailsDO.setDs_fulls(doObj.getCurrent_stock());
								session.save(doObj);
								session.save(detailsDO);
							}
						}
					}
				}
				
				// Save Bank Details	
				List<BankTranxsDataDO> btItemsResult = new ArrayList<>();
				if(dataDO.getPayment_terms() == 2 || dataDO.getPayment_terms() == 3) {
					Query<BankTranxsDataDO> btqry = session.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 16");
					btqry.setParameter(1, dataDO.getId());
					btItemsResult = btqry.getResultList();
					if (!(btItemsResult.isEmpty())) {
						for (BankTranxsDataDO btDetailsDO : btItemsResult) {
							BankDataDO bankDataDO = session.get(BankDataDO.class, btDetailsDO.getBank_id());
							BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
							BigDecimal tamt = new BigDecimal(btDetailsDO.getTrans_amount());
							bankDataDO.setAcc_cb((ccb.add(tamt)).toString());
							session.save(bankDataDO);

							btDetailsDO.setModified_by(dataDO.getCreated_by());
							btDetailsDO.setModified_date(System.currentTimeMillis());
							btDetailsDO.setDbank_acb(bankDataDO.getAcc_cb());
							btDetailsDO.setDeleted(2);
							session.update(btDetailsDO);
						}
					}
				}
				
				
				dataDO.setDeleted(2);
				dataDO.setModified_by(dataDO.getCreated_by());
				dataDO.setModified_date(System.currentTimeMillis());
				session.update(dataDO);

				Query<AgencyStockDataDO> asdataDO = session
						.createQuery("from AgencyStockDataDO where ref_id = ?1 and stock_flag = 7");
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

				Query<AgencyCVOBalanceDataDO> cvodataDO = session
						.createQuery("from AgencyCVOBalanceDataDO where ref_id = ?1 and cvoflag = 11");
				cvodataDO.setParameter(1, dataDO.getId());
				List<AgencyCVOBalanceDataDO> cvoItemsResult = cvodataDO.getResultList();
				if (!(cvoItemsResult.isEmpty())) {
					for (AgencyCVOBalanceDataDO cvodDO : cvoItemsResult) {
						cvodDO.setDeleted(3);
						cvodDO.setModified_date(System.currentTimeMillis());
						cvodDO.setModified_by(dataDO.getCreated_by());
						session.update(cvodDO);
					}
				}

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInSalesReturn(dataDO, itemsResult, session);
				if(!btItemsResult.isEmpty())
					udodt.updateDataOnDeleteInBankTransactions(dataDO.getCreated_by(), btItemsResult);
			}
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> deleteSalesReturnsData  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Assets Data
	public List<AssetDataDO> getAgencyAssetsData(long agencyId) throws BusinessException {
		List<AssetDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<AssetDataDO> query = session.createQuery("from AssetDataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<AssetDataDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (AssetDataDO dataDO : result) {
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
	public void saveAgencyAssetsData(List<AssetDataDO> doList, List<AgencyCashDataDO> agencycashDOList,
			List<BankTranxsDataDO> bankTranxsDOList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<Long> brefIdList = new ArrayList<>();
			List<Long> crefIdList = new ArrayList<>();
			for (AssetDataDO dataDO : doList) {
				if (dataDO.getAsset_ttype() != 3) {
					if (dataDO.getAsset_mop() != 1)
						brefIdList.add((long) session.save(dataDO));
					else
						crefIdList.add((long) session.save(dataDO));
				} else
					session.save(dataDO);
			}

			// Cash balance adjustment:
			long count = 0;
			for (AgencyCashDataDO cashTranxData : agencycashDOList) {

				long ttype = cashTranxData.getTrans_type();
				long srno = 0;
				count = count + srno;
				long ldate = 0;

				if (ttype == 8) {
					Query<AgencyCashDataDO> bquery = session.createQuery(
							"from AgencyCashDataDO where created_by = ?1 and deleted = ?2 and inv_no != ?3 "
									+ "order by t_date, created_date")
							.setMaxResults(1);
					bquery.setParameter(1, cashTranxData.getCreated_by());
					bquery.setParameter(2, 0);
					bquery.setParameter(3, "NA");
					List<AgencyCashDataDO> bresult = bquery.getResultList();

					if ((!bresult.isEmpty()) && cashTranxData.getT_date() < bresult.get(0).getT_date()) {
						BigDecimal tamt = new BigDecimal(cashTranxData.getCash_amount());
						BigDecimal btcb = new BigDecimal(bresult.get(0).getCash_balance());
						BigDecimal lbtcb = new BigDecimal(bresult.get(0).getCash_amount());
						BigDecimal btamt = new BigDecimal(0);
						btamt = btcb.subtract(lbtcb);
						cashTranxData.setCash_balance((btamt.add(tamt)).toString());

						cashTranxData.setInv_no("-1");
						session.save(cashTranxData);
					}

					Query<AgencyCashDataDO> query = session.createQuery(
							"from AgencyCashDataDO where created_by = ?1 and deleted = ?2  and t_date > ?3");
					query.setParameter(1, cashTranxData.getCreated_by());
					query.setParameter(2, 0);
					query.setParameter(3, cashTranxData.getT_date());
					List<AgencyCashDataDO> result = query.getResultList();

					if (!result.isEmpty()) {
						Query<AgencyCashDataDO> bquery1 = session.createQuery(
								"from AgencyCashDataDO where created_by = ?1 and deleted = ?2 and t_date > ?3 "
										+ "order by t_date asc,id asc");
						bquery1.setParameter(1, cashTranxData.getCreated_by());
						bquery1.setParameter(2, 0);
						bquery1.setParameter(3, cashTranxData.getT_date());
						List<AgencyCashDataDO> bresult1 = bquery1.getResultList();

						if (!bresult1.isEmpty()) {
							for (AgencyCashDataDO btdObj : bresult1) {

								if (cashTranxData.getT_date() < btdObj.getT_date()) {
									BigDecimal tamt = new BigDecimal(cashTranxData.getCash_amount());
									BigDecimal btcb = new BigDecimal(btdObj.getCash_balance());
									BigDecimal lbtcb = new BigDecimal(btdObj.getCash_amount());
									BigDecimal btamt = new BigDecimal(0);
									btamt = btcb.subtract(lbtcb);

									if (ldate == 0 || ldate > btdObj.getT_date()) {
										cashTranxData.setCash_balance((btamt.add(tamt)).toString());
										cashTranxData.setInv_no(String.valueOf(crefIdList.get((int) count)));

										ldate = btdObj.getT_date();
										session.save(cashTranxData);
									}
									btdObj.setCash_balance((btcb.add(tamt)).toString());

									Query query2 = session
											.createQuery("update AgencyCashDataDO b set b.cash_balance = :bcb"
													+ " where b.created_by = :cid and b.id= :id and b.inv_no < :srno and b.t_date >= :bdate");
									query2.setParameter("bcb", btdObj.getCash_balance());
									query2.setParameter("cid", btdObj.getCreated_by());
									query2.setParameter("id", btdObj.getId());
									query2.setParameter("srno", btdObj.getInv_no());
									query2.setParameter("bdate", cashTranxData.getT_date());
									query2.executeUpdate();
								}
							}
							if (cashTranxData.getInv_no() == "-1") {
								cashTranxData.setInv_no(String.valueOf(crefIdList.get((int) count)));
								session.save(cashTranxData);
							}
						}
					} else {
						Query<AgencyCashDataDO> query3 = session.createQuery(
								"from AgencyCashDataDO where created_by = ?1 and deleted = ?2 order by cash_balance desc")
								.setMaxResults(1);
						query3.setParameter(1, cashTranxData.getCreated_by());
						query3.setParameter(2, 0);
						List<AgencyCashDataDO> result3 = query3.getResultList();

						cashTranxData.setInv_no(String.valueOf(crefIdList.get((int) count)));
						BigDecimal ccb = new BigDecimal(result3.get(0).getCash_balance());
						BigDecimal tamt = new BigDecimal(cashTranxData.getCash_amount());
						cashTranxData.setCash_balance(((ccb.add(tamt))).toString());

						session.save(cashTranxData);
					}
					if (cashTranxData.getInv_no() == "-1") {
						cashTranxData.setInv_no(String.valueOf(crefIdList.get((int) count)));
						session.save(cashTranxData);
					}
				}
				++count;
			}

			// Save Bank Transaction
			if (!(bankTranxsDOList.isEmpty())) {
				int bcount = 0;
				for (BankTranxsDataDO bankTranxData : bankTranxsDOList) {
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
						bankTranxData.setRef_id(brefIdList.get(bcount));
						bankTranxData.setBtflag(12);

						BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
						long rdate = 0;
						Query<BankTranxsDataDO> bquery = session.createQuery(
								"from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id= ?3 "
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
									if (btdObj.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
										btamt = btcb.subtract(lbtcb).add(tc1);
									} else {
										btamt = btcb.add(lbtcb).add(tc1);
									}
									if ((rdate == 0 && bankTranxData.getSr_no() != -1) || rdate > btdObj.getBt_date()) {
										if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
											bankTranxData.setBank_acb((btamt.add(tamt).subtract(tc)).toString());
										} else if (bankTranxData.getTrans_type() == 1
												|| bankTranxData.getTrans_type() == 4
												|| bankTranxData.getTrans_type() == 6
												|| bankTranxData.getTrans_type() == 9
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
					++bcount;
				}
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> saveAgencyAssetsData  is not succesful");
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
	public synchronized void deleteAgencyAssetData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AssetDataDO dataDO = session.get(AssetDataDO.class, itemId);

			if (dataDO.getDeleted() == 0) {
				List<BankTranxsDataDO> btItemsResult = new ArrayList<>();
				long bankId = dataDO.getBank_id();
				if (bankId > 1) {
					Query<BankTranxsDataDO> btqry = session
							.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 12");
					btqry.setParameter(1, dataDO.getId());
					btItemsResult = btqry.getResultList();
					for (BankTranxsDataDO btDetailsDO : btItemsResult) {
						BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
						BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
						BigDecimal tamt = new BigDecimal(dataDO.getAsset_value());
						bankDataDO.setAcc_cb((ccb.subtract(tamt)).toString());
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

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInAssets(dataDO);
				if (dataDO.getAsset_ttype() != 3 && (bankId != 0) && (!(btItemsResult.isEmpty())))
					udodt.updateDataOnDeleteInBankTransactions(dataDO.getCreated_by(), btItemsResult);
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> deleteAgencyAssetData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// TV Data
	public List<TVDataDO> getAgencyTCData(long agencyId) throws BusinessException {
		List<TVDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<TVDataDO> query = session
					.createQuery("from TVDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?3) ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			List<TVDataDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (TVDataDO dataDO : result) {
					if (dataDO.getDeleted() == 2) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String time = sdf.format(dataDO.getModified_date());
						dataDO.setTv_date(sdf.parse(time).getTime());
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

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void saveAgencyTVData(TVDataDO dataDO, BankTranxsDataDO bankTranxData, List<AgencyStockDataDO> asdoList)
			throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			long refId = 0;
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

			// NO OF CYLINDERS & REGULATORS MUST BE ADDED TO EMPTYS AND STOCK RESPECTIVELY
			// Cylinder and Regulator Data Update

			List<Integer> prodCodes = new ArrayList<>();
			prodCodes.add(dataDO.getProd_code()); // Cylinder product code
			prodCodes.add(10); // Regulator product code
			dataDO.setR_prod_code(10);// Default to Regulator Standard

			int csfulls = 0;
			int csempties = 0;
			int rsfulls = 0;

			for (Integer pc : prodCodes) {
				Query<EquipmentDataDO> queryc = session
						.createQuery("from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
				queryc.setParameter(1, dataDO.getCreated_by());
				queryc.setParameter(2, 0);
				queryc.setParameter(3, pc);
				List<EquipmentDataDO> resultc = queryc.getResultList();
				if (!resultc.isEmpty()) {
					for (EquipmentDataDO doObj : resultc) {
						if (pc < 10) {
							int ces = doObj.getCs_emptys();
							doObj.setCs_emptys(ces + dataDO.getNo_of_cyl());
							session.save(doObj);
							csfulls = doObj.getCs_fulls();
							csempties = doObj.getCs_emptys();
						} else if (pc < 13) {
							int cfs = doObj.getCs_fulls();
							doObj.setCs_fulls(cfs + dataDO.getNo_of_reg());
							session.save(doObj);
							rsfulls = doObj.getCs_fulls();
						}
					}
				}

				Query<AgencyStockDataDO> checkDataQ = session.createQuery(
						"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date > ?4");
				checkDataQ.setParameter(1, dataDO.getCreated_by());
				checkDataQ.setParameter(2, 0);
				checkDataQ.setParameter(3, pc);
				checkDataQ.setParameter(4, dataDO.getTv_date());
				List<AgencyStockDataDO> checkDataR = checkDataQ.getResultList();

				if (!(checkDataR.isEmpty())) {
					// saving dataDO if backdate is entered
					Query<AgencyStockDataDO> sbalQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date <= ?4 "
									+ "order by trans_date desc , created_date desc")
							.setMaxResults(1);
					sbalQ.setParameter(1, dataDO.getCreated_by());
					sbalQ.setParameter(2, 0);
					sbalQ.setParameter(3, pc);
					sbalQ.setParameter(4, dataDO.getTv_date());

					List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
					if (!(sbalRes.isEmpty())) {
						for (AgencyStockDataDO asdatado : sbalRes) {

							int cfulls = asdatado.getCs_fulls();
							int cempties = asdatado.getCs_emptys();

							if (pc < 10) {
								dataDO.setCs_fulls(cfulls);
								dataDO.setCs_emptys(cempties + dataDO.getNo_of_cyl());
							} else if (pc < 13) {
								dataDO.setReg_stock(cfulls + dataDO.getNo_of_reg());
							}
							refId = (long) session.save(dataDO);

							for (AgencyStockDataDO asDO : asdoList) {
								if (asDO.getProd_code() == pc) {
									asDO.setRef_id(dataDO.getId());
									asDO.setInv_no(dataDO.getSr_no());
									if (pc < 10) {
										asDO.setFulls(0);
										asDO.setEmpties(dataDO.getNo_of_cyl());
										asDO.setCs_fulls(cfulls);
										asDO.setCs_emptys(cempties + dataDO.getNo_of_cyl());
									} else if (pc < 13) {
										asDO.setFulls(dataDO.getNo_of_reg());
										asDO.setCs_fulls(cfulls + dataDO.getNo_of_reg());
									}
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
					balUpdateQ.setParameter(3, pc);
					balUpdateQ.setParameter(4, dataDO.getTv_date());

					List<AgencyStockDataDO> balUpdateRes = balUpdateQ.getResultList();
					if (!(balUpdateRes.isEmpty())) {
						for (AgencyStockDataDO bUpInasDO : balUpdateRes) {
							if (pc < 10) {
								bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls());
								bUpInasDO.setCs_emptys(bUpInasDO.getCs_emptys() + dataDO.getNo_of_cyl());
							} else if (pc < 13) {
								bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls() + dataDO.getNo_of_reg());
							}
							session.update(bUpInasDO);
						}
					}
				} else {
					// If User enters Forward date:
					if (pc < 10) {
						dataDO.setCs_fulls(csfulls);
						dataDO.setCs_emptys(csempties);
					} else if (pc < 13) {
						dataDO.setReg_stock(rsfulls);
					}
					refId = (long) session.save(dataDO);

					for (AgencyStockDataDO asDO : asdoList) {
						if (asDO.getProd_code() == pc) {
							asDO.setRef_id(dataDO.getId());
							asDO.setInv_no(dataDO.getSr_no());
							if (pc < 10) {
								asDO.setFulls(0);
								asDO.setEmpties(dataDO.getNo_of_cyl());
								asDO.setCs_fulls(csfulls);
								asDO.setCs_emptys(csempties);
							} else if (pc < 13) {
								asDO.setFulls(dataDO.getNo_of_reg());
								asDO.setCs_fulls(rsfulls);
							}
							asDO.setCreated_date(dataDO.getCreated_date());
							session.save(asDO);
						}
					}
				}
			}

			for (AgencyStockDataDO asdDO : asdoList) {
				asdDO.setRef_id(dataDO.getId());
				session.save(asdDO);
			}

			// Update Cash Data for backDates:
			Query<AgencyCashDataDO> query1 = session.createQuery(
					"from AgencyCashDataDO where created_by = ?1 and trans_type != ?2 and t_date >= ?3 order by t_date,created_date");
			query1.setParameter(1, dataDO.getCreated_by());
			query1.setParameter(2, 11);
			query1.setParameter(3, dataDO.getTv_date());
			List<AgencyCashDataDO> result1 = query1.getResultList();

			for (AgencyCashDataDO cashDO : result1) {
				if (dataDO.getTv_date() < cashDO.getT_date()) {
					BigDecimal tamt = new BigDecimal(dataDO.getTv_amount());
					BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
					cashDO.setCash_balance((btcb.subtract(tamt)).toString());
				} else if (dataDO.getTv_date() == cashDO.getT_date()) {
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
				query2.setParameter("tdate", dataDO.getTv_date());
				query2.executeUpdate();
			}

			// Save Bank Transaction
			nno = snoDO.getBtSno();
			long bankId = bankTranxData.getBank_id();
			int i = 0;
			long count = 0;
			if (bankId > 0) {
				bankTranxData.setRef_id(refId);
				bankTranxData.setBtflag(7);

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

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> saveAgencyTVData  is not succesful");
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
	public synchronized void deleteTVData(long itemId, long agencyId, long bankId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			TVDataDO dataDO = session.get(TVDataDO.class, new Long(itemId));

			if (dataDO.getDeleted() == 0) {
				// Cylinder Data Update
				int productCodec = dataDO.getProd_code();
				Query<EquipmentDataDO> queryc = session
						.createQuery("from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
				queryc.setParameter(1, dataDO.getCreated_by());
				queryc.setParameter(2, 0);
				queryc.setParameter(3, productCodec);
				List<EquipmentDataDO> resultc = queryc.getResultList();
				if (!resultc.isEmpty()) {
					for (EquipmentDataDO doObj : resultc) {
						int cfs = doObj.getCs_fulls();
						int ces = doObj.getCs_emptys();
						doObj.setCs_emptys(ces - dataDO.getNo_of_cyl());
						dataDO.setDs_fulls(cfs);
						dataDO.setDs_emptys(ces - dataDO.getNo_of_cyl());
						session.save(doObj);
					}
				}

				// Regulator Data Update
				int productCode = 10;
				Query<EquipmentDataDO> query = session
						.createQuery("from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				query.setParameter(3, productCode);
				List<EquipmentDataDO> result = query.getResultList();
				if (!result.isEmpty()) {
					for (EquipmentDataDO doObj : result) {
						int cfs = doObj.getCs_fulls();
						doObj.setCs_fulls(cfs - dataDO.getNo_of_reg());
						dataDO.setDreg_stock(cfs - dataDO.getNo_of_reg());
						session.save(doObj);
					}
				}

				List<BankTranxsDataDO> btItemsResult = new ArrayList<>();
				if (bankId != 0) {
					Query<BankTranxsDataDO> btqry = session
							.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 7");
					btqry.setParameter(1, dataDO.getId());

					btItemsResult = btqry.getResultList();
					if (!(btItemsResult.isEmpty())) {
						for (BankTranxsDataDO btDetailsDO : btItemsResult) {
							BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
							double deptamt = Double.parseDouble(dataDO.getCyl_deposit())
									+ Double.parseDouble(dataDO.getReg_deposit());
							BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
							BigDecimal tamt = BigDecimal.valueOf(deptamt);
							bankDataDO.setAcc_cb((ccb.subtract(tamt)).toString());
							session.save(bankDataDO);

							btDetailsDO.setModified_by(dataDO.getCreated_by());
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
						.createQuery("from AgencyStockDataDO where ref_id = ?1 and stock_flag = 10");
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

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInTVSales(dataDO, session);
				if ((bankId != 0) && (!(btItemsResult.isEmpty())))
					udodt.updateDataOnDeleteInBankTransactions(dataDO.getCreated_by(), btItemsResult);

			}
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> deleteTVData  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// RC Data
	public List<RCDataDO> getAgencyRCData(long agencyId) throws BusinessException {
		List<RCDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<RCDataDO> query = session
					.createQuery("from RCDataDO where created_by = ?1 and (deleted = ?2 or  deleted = ?3)");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			List<RCDataDO> result = query.getResultList();
			if (!result.isEmpty()) {

				for (RCDataDO dataDO : result) {
					if (dataDO.getDeleted() == 2) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						String time = sdf.format(dataDO.getModified_date());
						dataDO.setRc_date(sdf.parse(time).getTime());
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

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public void saveAgencyRCData(RCDataDO dataDO, BankTranxsDataDO bankTranxData, AgencyStockDataDO asdataDO)
			throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			long refId = 0;
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

			int productCode = 10;
			int csfulls = 0;

			Query<EquipmentDataDO> query = session
					.createQuery("from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
			query.setParameter(1, dataDO.getCreated_by());
			query.setParameter(2, 0);
			query.setParameter(3, productCode);
			List<EquipmentDataDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (EquipmentDataDO doObj : result) {
					int cfs = doObj.getCs_fulls();
					doObj.setCs_fulls(cfs - dataDO.getNo_of_reg());
					session.save(doObj);
					csfulls = doObj.getCs_fulls();
				}
			}

			Query<AgencyStockDataDO> checkDataQ = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date > ?4");
			checkDataQ.setParameter(1, dataDO.getCreated_by());
			checkDataQ.setParameter(2, 0);
			checkDataQ.setParameter(3, productCode);
			checkDataQ.setParameter(4, dataDO.getRc_date());
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
				sbalQ.setParameter(4, dataDO.getRc_date());

				List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
				if (!(sbalRes.isEmpty())) {
					for (AgencyStockDataDO asdatado : sbalRes) {

						int cfulls = asdatado.getCs_fulls();
						dataDO.setReg_stock(cfulls - dataDO.getNo_of_reg());
						refId = (long) session.save(dataDO);

						if (asdataDO.getProd_code() == productCode) {
							asdataDO.setRef_id(dataDO.getId());
							asdataDO.setInv_no(dataDO.getSr_no());
							asdataDO.setFulls(dataDO.getNo_of_reg());
							asdataDO.setCs_fulls(cfulls - dataDO.getNo_of_reg());
							asdataDO.setCreated_date(dataDO.getCreated_date());
							session.save(asdataDO);
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
				balUpdateQ.setParameter(4, dataDO.getRc_date());

				List<AgencyStockDataDO> balUpdateRes = balUpdateQ.getResultList();
				if (!(balUpdateRes.isEmpty())) {
					for (AgencyStockDataDO bUpInasDO : balUpdateRes) {
						bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls() - dataDO.getNo_of_reg());
						session.update(bUpInasDO);
					}
				}
			} else {
				// If User enters Forward date:
				dataDO.setReg_stock(csfulls);
				refId = (long) session.save(dataDO);

				if (asdataDO.getProd_code() == productCode) {
					asdataDO.setRef_id(dataDO.getId());
					asdataDO.setInv_no(dataDO.getSr_no());
					asdataDO.setFulls(dataDO.getNo_of_reg());
					asdataDO.setCs_fulls(csfulls);
					asdataDO.setCreated_date(dataDO.getCreated_date());
					session.save(asdataDO);
				}
			}

			// Update Cash Data for backDates
			Query<AgencyCashDataDO> query1 = session.createQuery(
					"from AgencyCashDataDO where created_by = ?1 and trans_type != ?2 and t_date >= ?3 order by t_date,created_date");
			query1.setParameter(1, dataDO.getCreated_by());
			query1.setParameter(2, 11);
			query1.setParameter(3, dataDO.getRc_date());
			List<AgencyCashDataDO> result1 = query1.getResultList();

			for (AgencyCashDataDO cashDO : result1) {
				if (dataDO.getRc_date() < cashDO.getT_date()) {
					BigDecimal tamt = new BigDecimal(dataDO.getRc_amount());
					BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
					cashDO.setCash_balance((btcb.add(tamt)).toString());
				} else if (dataDO.getRc_date() == cashDO.getT_date()) {
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
				query2.setParameter("tdate", dataDO.getRc_date());
				query2.executeUpdate();
			}

			// Save Bank Transaction
			nno = snoDO.getBtSno();
			long bankId = bankTranxData.getBank_id();
			int i = 0;
			long count = 0;
			if (bankId > 0) {
				bankTranxData.setRef_id(refId);
				bankTranxData.setBtflag(6);

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
							btdObj.setBank_acb((btcb.subtract(tamt).subtract(tc)).toString());

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

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> saveAgencyRCData  is not succesful");
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
	public synchronized void deleteRCData(long itemId, long agencyId, long bankId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			RCDataDO dataDO = session.get(RCDataDO.class, new Long(itemId));

			if (dataDO.getDeleted() == 0) {
				// Regulator Data Update - Product Code 10
				int productCode = 10;
				Query<EquipmentDataDO> query = session
						.createQuery("from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				query.setParameter(3, productCode);
				List<EquipmentDataDO> result = query.getResultList();
				if (!result.isEmpty()) {
					for (EquipmentDataDO doObj : result) {
						int cfs = doObj.getCs_fulls();
						doObj.setCs_fulls(cfs + dataDO.getNo_of_reg());
						dataDO.setDreg_stock(cfs + dataDO.getNo_of_reg());
						session.save(doObj);
					}
				}

				List<BankTranxsDataDO> btItemsResult = new ArrayList<>();
				if (bankId != 0) {
					Query<BankTranxsDataDO> btqry = session
							.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 6");
					btqry.setParameter(1, dataDO.getId());

					btItemsResult = btqry.getResultList();
					if (!(btItemsResult.isEmpty())) {
						for (BankTranxsDataDO btDetailsDO : btItemsResult) {
							BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
							double deptamt = Double.parseDouble(dataDO.getCyl_deposit())
									+ Double.parseDouble(dataDO.getReg_deposit());
							BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
							BigDecimal tamt = BigDecimal.valueOf(deptamt);
							bankDataDO.setAcc_cb((ccb.add(tamt)).toString());
							session.save(bankDataDO);

							btDetailsDO.setModified_by(dataDO.getCreated_by());
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
						.createQuery("from AgencyStockDataDO where ref_id = ?1 and stock_flag = 9");
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

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInRCSales(dataDO);
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
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> deleteRCData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// NC DBC Invoices
	public List<NCDBCInvoiceDO> getAgencyNCDBCInvoices(long agencyId) throws BusinessException {
		List<NCDBCInvoiceDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<NCDBCInvoiceDO> query = session.createQuery(
					"from NCDBCInvoiceDO where created_by = ?1 and (deleted = ?2 or deleted = ?3) order by id desc");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 2);
			List<NCDBCInvoiceDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (NCDBCInvoiceDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<NCDBCInvoiceDetailsDO> squery = session
							.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (!itemsResult.isEmpty()) {
						if (dataDO.getDeleted() == 2) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String time = sdf.format(dataDO.getModified_date());
							dataDO.setInv_date(sdf.parse(time).getTime());
						}
						dataDO.getDetailsList().addAll(itemsResult);
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

	public NCDBCInvoiceDO getAgencyNCDBCInvoiceById(long invoiceId) throws BusinessException {
		NCDBCInvoiceDO rdo = new NCDBCInvoiceDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<NCDBCInvoiceDO> query = session.createQuery("from NCDBCInvoiceDO where id = ?1 and deleted = ?2 ");
			query.setParameter(1, invoiceId);
			query.setParameter(2, 0);
			List<NCDBCInvoiceDO> result = query.getResultList();
			if (!result.isEmpty()) {
				for (NCDBCInvoiceDO dataDO : result) {
					@SuppressWarnings("unchecked")
					Query<NCDBCInvoiceDetailsDO> squery = session
							.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1");
					squery.setParameter(1, dataDO.getId());
					List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();
					if (!itemsResult.isEmpty()) {
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
	public void saveAgencyNCDBCInvoices(NCDBCInvoiceDO dataDO, List<NCDBCInvoiceDetailsDO> doList,
			List<BankTranxsDataDO> bankTranxsDOList, List<AgencyStockDataDO> asdoList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			long refId = 0;
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

			// BigDecimal nocs = new BigDecimal(dataDO.getNo_of_conns());
			for (NCDBCInvoiceDetailsDO dataDetailsDO : doList) {
				long productCode = dataDetailsDO.getProd_code();
				dataDetailsDO.setInv_id(dataDO.getId());

				BigDecimal qty = new BigDecimal(dataDetailsDO.getQuantity());
				BigDecimal disc = new BigDecimal(dataDetailsDO.getDisc_unit_rate());

				BigDecimal discount = qty.multiply(disc);

				if (productCode > 9 && productCode <= 12) {

					int csfulls = 0;

					Query<EquipmentDataDO> query = session.createQuery(
							"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
					query.setParameter(1, dataDO.getCreated_by());
					query.setParameter(2, 0);
					query.setParameter(3, (int) productCode);
					List<EquipmentDataDO> result = query.getResultList();
					if (!result.isEmpty()) {
						for (EquipmentDataDO doObj : result) {
							int cfs = doObj.getCs_fulls();
							doObj.setCs_fulls(cfs - dataDetailsDO.getQuantity());
							session.save(doObj);
							csfulls = doObj.getCs_fulls();
						}
					}

					Query<AgencyStockDataDO> checkDataQ = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date > ?4");
					checkDataQ.setParameter(1, dataDO.getCreated_by());
					checkDataQ.setParameter(2, 0);
					checkDataQ.setParameter(3, (int) productCode);
					checkDataQ.setParameter(4, dataDO.getInv_date());
					List<AgencyStockDataDO> checkDataR = checkDataQ.getResultList();

					if (!(checkDataR.isEmpty())) {
						// saving dataDO if backdate is entered
						Query<AgencyStockDataDO> sbalQ = session.createQuery(
								"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and trans_date <= ?4 "
										+ "order by trans_date desc , created_date desc")
								.setMaxResults(1);
						sbalQ.setParameter(1, dataDO.getCreated_by());
						sbalQ.setParameter(2, 0);
						sbalQ.setParameter(3, (int) productCode);
						sbalQ.setParameter(4, dataDO.getInv_date());

						List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
						if (!(sbalRes.isEmpty())) {
							for (AgencyStockDataDO asdatado : sbalRes) {
								int cfulls = asdatado.getCs_fulls();

								dataDetailsDO.setC_stock(cfulls - dataDetailsDO.getQuantity());
								dataDetailsDO.setE_stock(0);
								session.save(dataDetailsDO);

								for (AgencyStockDataDO asDO : asdoList) {
									if (asDO.getProd_code() == productCode) {
										asDO.setRef_id(dataDO.getId());
										asDO.setInv_no(dataDO.getSr_no());
										asDO.setFulls(dataDetailsDO.getQuantity());
										asDO.setEmpties(0);
										asDO.setCs_fulls(cfulls - dataDetailsDO.getQuantity());
										asDO.setCs_emptys(0);
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
						balUpdateQ.setParameter(3, (int) productCode);
						balUpdateQ.setParameter(4, dataDO.getInv_date());

						List<AgencyStockDataDO> balUpdateRes = balUpdateQ.getResultList();
						if (!(balUpdateRes.isEmpty())) {
							for (AgencyStockDataDO bUpInasDO : balUpdateRes) {
								bUpInasDO.setCs_fulls(bUpInasDO.getCs_fulls() - dataDetailsDO.getQuantity());
								session.update(bUpInasDO);
							}
						}
					} else {
						// If User enters Forward date:
						dataDetailsDO.setC_stock(csfulls);
						dataDetailsDO.setE_stock(0);
						session.save(dataDetailsDO);

						for (AgencyStockDataDO asDO : asdoList) {
							if (asDO.getProd_code() == productCode) {
								asDO.setRef_id(dataDO.getId());
								asDO.setInv_no(dataDO.getSr_no());
								asDO.setFulls(dataDetailsDO.getQuantity());
								asDO.setCs_fulls(csfulls);
								asDO.setDiscount(discount.toString());
								asDO.setCreated_date(dataDO.getCreated_date());
								session.save(asDO);
							}
						}
					}
				} else if (productCode > 100) {

					int cstock = 0;
					int arbcode = 0;
					Query<ARBDataDO> query = session
							.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 and id = ?3");
					query.setParameter(1, dataDO.getCreated_by());
					query.setParameter(2, 0);
					query.setParameter(3, productCode);
					List<ARBDataDO> result = query.getResultList();
					if (!result.isEmpty()) {
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
					checkDataQ.setParameter(4, dataDO.getInv_date());
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
						sbalQ.setParameter(4, dataDO.getInv_date());

						List<AgencyStockDataDO> sbalRes = sbalQ.getResultList();
						if (!(sbalRes.isEmpty())) {
							for (AgencyStockDataDO asdatado : sbalRes) {

								int cfulls = asdatado.getCs_fulls();

								dataDetailsDO.setC_stock(cfulls - dataDetailsDO.getQuantity());
								session.save(dataDetailsDO);

								for (AgencyStockDataDO asDO : asdoList) {
									if (asDO.getProd_code() == productCode) {
										asDO.setProd_code(arbcode);
										asDO.setProd_id(productCode);
										asDO.setRef_id(dataDO.getId());
										asDO.setInv_no(dataDO.getSr_no());
										asDO.setFulls(dataDetailsDO.getQuantity()); // no empties. So 0(passed in
																					// factory)
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
						balUpdateQ.setParameter(4, dataDO.getInv_date());

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
						dataDetailsDO.setE_stock(0);
						session.save(dataDetailsDO);

						for (AgencyStockDataDO asDO : asdoList) {
							if (asDO.getProd_code() == productCode) {
								asDO.setProd_code(arbcode);
								asDO.setProd_id(productCode);
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
				} else {
					dataDetailsDO.setInv_id(dataDO.getId());
					session.save(dataDetailsDO);
				}
			}

			// discount
			float dis = 0;

			for (NCDBCInvoiceDetailsDO dataDetailsDO1 : doList) {
				if (dataDetailsDO1.getInv_id() == dataDO.getId())
					dis = dis
							+ ((Float.parseFloat(dataDetailsDO1.getDisc_unit_rate())) * (dataDetailsDO1.getQuantity()));
				System.out.println(dis);
			}

			// update ujwala in cvo
			if (("0").equals(dataDO.getCash_amount()) && ("").equals(dataDO.getOnline_amount())) {
				Query<CVODataDO> query = session
						.createQuery("from CVODataDO where created_by = ?1 and deleted = ?2 order by id")
						.setMaxResults(1);
				query.setParameter(1, dataDO.getCreated_by());
				query.setParameter(2, 0);
				List<CVODataDO> result = query.getResultList();
				if (!result.isEmpty()) {
					for (CVODataDO cvoObj : result) {
						BigDecimal cbal = new BigDecimal(cvoObj.getCbal());
						BigDecimal invamt = new BigDecimal(dataDO.getInv_amount());
						cvoObj.setCbal((cbal.add(invamt)).toString());
						session.save(cvoObj);
					}
				}
			}

			if (Double.parseDouble(dataDO.getCash_amount()) > 0) {
				Query<AgencyCashDataDO> query1 = session.createQuery(
						"from AgencyCashDataDO where created_by = ?1 and trans_type != ?2 and t_date >= ?3 order by t_date,created_date");
				query1.setParameter(1, dataDO.getCreated_by());
				query1.setParameter(2, 11);
				query1.setParameter(3, dataDO.getInv_date());
				List<AgencyCashDataDO> result1 = query1.getResultList();

				for (AgencyCashDataDO cashDO : result1) {
					if (dataDO.getInv_date() < cashDO.getT_date()) {
						BigDecimal tamt = new BigDecimal(dataDO.getCash_amount());
						BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
						cashDO.setCash_balance((btcb.add(tamt)).toString());
					} else if (dataDO.getInv_date() == cashDO.getT_date()) {
						BigDecimal btcb = new BigDecimal(cashDO.getCash_balance());
						cashDO.setCash_balance(btcb.toString());
						if (cashDO.getDiscount() == null) {
							cashDO.setDiscount(String.valueOf(dis));
						}
					}
					session.save(cashDO);

					Query query2 = session.createQuery("update AgencyCashDataDO ac set ac.cash_balance = :acb"
							+ " where ac.created_by = :cid and ac.id= :id and ac.inv_no = :invno and ac.t_date > :tdate");
					query2.setParameter("acb", cashDO.getCash_balance());
					query2.setParameter("cid", cashDO.getCreated_by());
					query2.setParameter("id", cashDO.getId());
					query2.setParameter("invno", cashDO.getInv_no());
					query2.setParameter("tdate", dataDO.getInv_date());
					query2.executeUpdate();
				}
			}

			// Save Bank Transaction
//			if (dataDO.getConn_type() != 3 && dataDO.getConn_type() != 4) {
			if (dataDO.getConn_type() != 3 && dataDO.getConn_type() != 4 && dataDO.getConn_type() != 5) {
				for (BankTranxsDataDO bankTranxData : bankTranxsDOList) {
					// AgencySerialNosDO snoDO = session.get(AgencySerialNosDO.class,new
					// Long(bankTranxData.getCreated_by()));
					nno = snoDO.getBtSno();
					long bankId = bankTranxData.getBank_id();
					int i = 0;
					if (bankId > 0) {
						bankTranxData.setRef_id(refId);
						bankTranxData.setBtflag(5);

						BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
						long rdate = 0;
						Query<BankTranxsDataDO> bquery = session.createQuery(
								"from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id= ?3 "
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
										} else if (bankTranxData.getTrans_type() == 1
												|| bankTranxData.getTrans_type() == 4
												|| bankTranxData.getTrans_type() == 6
												|| bankTranxData.getTrans_type() == 9
												|| bankTranxData.getTrans_type() == 10) {
											bankTranxData.setBank_acb((btamt.subtract(tamt).subtract(tc)).toString());
										}
										bankTranxData.setSr_no(nno);
										snoDO.setBtSno(nno + 1);
										rdate = btdObj.getBt_date();
										session.save(snoDO);
										session.save(bankTranxData);
									}
									if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
										btdObj.setBank_acb((btcb.add(tamt).subtract(tc)).toString());
									} else if (bankTranxData.getTrans_type() == 1 || bankTranxData.getTrans_type() == 4
											|| bankTranxData.getTrans_type() == 6 || bankTranxData.getTrans_type() == 9
											|| bankTranxData.getTrans_type() == 10) {
										btdObj.setBank_acb((btcb.subtract(tamt).subtract(tc)).toString());
									}

									Query query2 = session
											.createQuery("update BankTranxsDataDO b set b.bank_acb = :bcb "
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
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> saveAgencyNCDBCInvoices  is not succesful");
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
	public synchronized void deleteAgencyNCDBCInvoices(long itemId, long agencyId, long starId)
			throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			NCDBCInvoiceDO dataDO = session.get(NCDBCInvoiceDO.class, new Long(itemId));

			if (dataDO.getDeleted() == 0) {
				Query<NCDBCInvoiceDetailsDO> squery = session
						.createQuery("from NCDBCInvoiceDetailsDO where inv_id = ?1");
				squery.setParameter(1, dataDO.getId());
				List<NCDBCInvoiceDetailsDO> itemsResult = squery.getResultList();

				for (NCDBCInvoiceDetailsDO dataDetailsDO : itemsResult) {
					long productCode = dataDetailsDO.getProd_code();
					if (productCode <= 9) {
						Query<EquipmentDataDO> query = session.createQuery(
								"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
						query.setParameter(1, dataDO.getCreated_by());
						query.setParameter(2, 0);
						query.setParameter(3, (int) productCode);
						List<EquipmentDataDO> result = query.getResultList();
						if (!result.isEmpty()) {
							for (EquipmentDataDO doObj : result) {
								int cfs = doObj.getCs_fulls();
								int ces = doObj.getCs_emptys();
								dataDetailsDO.setDc_stock(cfs);
								dataDetailsDO.setDe_stock(ces);
								session.save(dataDetailsDO);
							}
						}
					} else if (productCode > 9 && productCode <= 12) {
						Query<EquipmentDataDO> query = session.createQuery(
								"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3");
						query.setParameter(1, dataDO.getCreated_by());
						query.setParameter(2, 0);
						query.setParameter(3, (int) productCode);
						List<EquipmentDataDO> result = query.getResultList();
						if (!result.isEmpty()) {
							for (EquipmentDataDO doObj : result) {
								int cfs = doObj.getCs_fulls();
								doObj.setCs_fulls(cfs + dataDetailsDO.getQuantity());
								session.save(doObj);
								dataDetailsDO.setDc_stock(cfs + dataDetailsDO.getQuantity());
								dataDetailsDO.setDe_stock(0);
								session.save(dataDetailsDO);
							}
						}
					} else if (productCode > 100) {
						Query<ARBDataDO> query = session
								.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 and id = ?3");
						query.setParameter(1, dataDO.getCreated_by());
						query.setParameter(2, 0);
						query.setParameter(3, productCode);
						List<ARBDataDO> result = query.getResultList();
						if (!result.isEmpty()) {
							for (ARBDataDO doObj : result) {
								int cs = doObj.getCurrent_stock();
								doObj.setCurrent_stock(cs + dataDetailsDO.getQuantity());
								session.save(doObj);
								dataDetailsDO.setDc_stock(cs + dataDetailsDO.getQuantity());
								dataDetailsDO.setDe_stock(0);
								session.save(dataDetailsDO);

							}
						}
					}
				}

				if (dataDO.getConn_type() == 3) {
					Query<CVODataDO> query = session
							.createQuery("from CVODataDO where created_by = ?1 and deleted = ?2 order by id")
							.setMaxResults(1);
					query.setParameter(1, dataDO.getCreated_by());
					query.setParameter(2, 0);
					List<CVODataDO> result = query.getResultList();
					if (!result.isEmpty()) {
						for (CVODataDO cvoObj : result) {
							BigDecimal cbal = new BigDecimal(cvoObj.getCbal());
							BigDecimal invamt = new BigDecimal(dataDO.getInv_amount());
							cvoObj.setCbal((cbal.subtract(invamt)).toString());
							session.save(cvoObj);
						}
					}
				}

				List<BankTranxsDataDO> btItemsResult = new ArrayList<>();
				if (dataDO.getConn_type() != 3 && dataDO.getConn_type() != 4) {
					List<Long> btdIds = new ArrayList<>();

					Query<BankTranxsDataDO> btqry = session
							.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 5");
					btqry.setParameter(1, dataDO.getId());
					btItemsResult = btqry.getResultList();
					for (BankTranxsDataDO btDetailsDO : btItemsResult) {
						btdIds.add(btDetailsDO.getId());

						BankDataDO bankDataDO = session.get(BankDataDO.class, btDetailsDO.getBank_id());
						BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
						BigDecimal tamt = new BigDecimal(btDetailsDO.getTrans_amount());

						if (!(("STAR ACCOUNT").equals(bankDataDO.getBank_code()))) {
							bankDataDO.setAcc_cb((ccb.subtract(tamt)).toString());
							btDetailsDO.setTrans_type(2);
						} else {
							bankDataDO.setAcc_cb((ccb.add(tamt)).toString());
							btDetailsDO.setTrans_type(1);
						}
						session.save(bankDataDO);

						btDetailsDO.setModified_by(dataDO.getCreated_by());
						btDetailsDO.setModified_date(System.currentTimeMillis());
						btDetailsDO.setDbank_acb(bankDataDO.getAcc_cb());
						btDetailsDO.setDeleted(2);
						session.update(btDetailsDO);
					}
				}
				dataDO.setDeleted(2);
				dataDO.setModified_by(agencyId);
				dataDO.setModified_date(System.currentTimeMillis());
				session.update(dataDO);

				Query<AgencyStockDataDO> asdataDO = session
						.createQuery("from AgencyStockDataDO where ref_id = ?1 and stock_flag = 8");
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

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				udodt.updateDataOnDeleteInNCDBC(dataDO.getCreated_by(), dataDO, itemsResult);
				if (dataDO.getConn_type() != 3 && dataDO.getConn_type() != 4 && (!(btItemsResult.isEmpty())))
					udodt.updateDataOnDeleteInBankTransactions(dataDO.getCreated_by(), btItemsResult);
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> OTransactionsPersistenceManager --> deleteAgencyNCDBCInvoices  is not succesful");
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