package com.it.erpapp.framework.managers;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.it.erpapp.commons.UpdateDataOnDeletingTransactions;
import com.it.erpapp.framework.model.AgencySerialNosDO;
import com.it.erpapp.framework.model.BankDataDO;
import com.it.erpapp.framework.model.PartnerInfoDO;
import com.it.erpapp.framework.model.PartnerTranxDO;
import com.it.erpapp.framework.model.finreports.BalanceSheetDO;
import com.it.erpapp.framework.model.finreports.BalanceSheetDetailsDO;
import com.it.erpapp.framework.model.finreports.CapitalAccountReportDO;
import com.it.erpapp.framework.model.finreports.DepreciationDO;
import com.it.erpapp.framework.model.finreports.DepreciationDataDO;
import com.it.erpapp.framework.model.finreports.PAndLAccountDO;
import com.it.erpapp.framework.model.finreports.PAndLAccountDetailsDO;
import com.it.erpapp.framework.model.transactions.OtherPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.others.AssetDataDO;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

public class FinancialReportsPersistenceManager {

	Logger logger = Logger.getLogger(FinancialReportsPersistenceManager.class.getName());

	// Partners Data
	public List<PartnerInfoDO> getAgencyPartnersData(long agencyId) throws BusinessException {
		List<PartnerInfoDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<PartnerInfoDO> query = session
					.createQuery("from PartnerInfoDO where created_by = ?1 and deleted = ?2");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<PartnerInfoDO> result = query.getResultList();
			if (result.size() > 0) {
				for (PartnerInfoDO dataDO : result) {
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

	public void saveAgencyPartnersData(List<PartnerInfoDO> doList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (PartnerInfoDO dataDO : doList) {
				session.save(dataDO);
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> saveAgencyPartnersData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	public void deleteAgencyPartnersData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			PartnerInfoDO dataDO = session.get(PartnerInfoDO.class, new Long(itemId));
			dataDO.setDeleted(1);
			session.update(dataDO);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> deleteAgencyPartnersData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Partners Transactions Data
	public List<PartnerTranxDO> getAgencyPartnersTranxsData(long agencyId) throws BusinessException {
		List<PartnerTranxDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<PartnerTranxDO> query = session
					.createQuery("from PartnerTranxDO where created_by = ?1 and deleted = ?2");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<PartnerTranxDO> result = query.getResultList();
			if (result.size() > 0) {
				for (PartnerTranxDO dataDO : result) {
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
	public void saveAgencyPartnersTranxsData(List<PartnerTranxDO> doList, List<BankTranxsDataDO> bankTransactionsList)
			throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<Long> brefIdList = new ArrayList<>();
			for (PartnerTranxDO dataDO : doList) {
				int ttype = dataDO.getTranx_type();
				if (ttype != 3)
					brefIdList.add((long) session.save(dataDO));
				else
					session.save(dataDO);

				// Save Partner Status for Withdrawal
				if (ttype == 3) {
					PartnerInfoDO partnerInfoDO = session.get(PartnerInfoDO.class, new Long(dataDO.getPartner_id()));
					partnerInfoDO.setStatus(2);
					partnerInfoDO.setModified_date(System.currentTimeMillis());
					session.update(partnerInfoDO);
				}
			}

			// Save Bank Transaction
			int count = 0;
			for (BankTranxsDataDO bankTranxData : bankTransactionsList) {
				AgencySerialNosDO snoDO = session.get(AgencySerialNosDO.class, new Long(bankTranxData.getCreated_by()));
				int nno = snoDO.getBtSno();
				long bankId = bankTranxData.getBank_id();
				int i = 0;
				if (bankId > 0) {
					bankTranxData.setRef_id(brefIdList.get(count));
					bankTranxData.setBtflag(13);

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
								if (bankTranxData.getTrans_type() == 2 || bankTranxData.getTrans_type() == 3) {
									btdObj.setBank_acb((btcb.add(tamt).subtract(tc)).toString());
								} else {
									btdObj.setBank_acb((btcb.subtract(tamt)).toString());
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
				++count;
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> saveAgencyPartnersTranxsData  is not succesful");
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
	public void deleteAgencyPartnersTranxsData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			PartnerTranxDO dataDO = session.get(PartnerTranxDO.class, new Long(itemId));
			if (dataDO.getDeleted() == 0) {

				List<BankTranxsDataDO> btItemsResult = new ArrayList<>();
				long bankId = dataDO.getBank_id();

				if (bankId > 1 && dataDO.getTranx_type() != 3) {
					Query<BankTranxsDataDO> btqry = session
							.createQuery("from BankTranxsDataDO where ref_id=?1 and btflag = 13");
					btqry.setParameter(1, dataDO.getId());
					btItemsResult = btqry.getResultList();
					for (BankTranxsDataDO btDetailsDO : btItemsResult) {
						BankDataDO bankDataDO = session.get(BankDataDO.class, bankId);
						BigDecimal ccb = new BigDecimal(bankDataDO.getAcc_cb());
						BigDecimal tamt = new BigDecimal(dataDO.getTranx_amount());
						if (dataDO.getTranx_type() == 1)
							bankDataDO.setAcc_cb((ccb.subtract(tamt)).toString());
						else if (dataDO.getTranx_type() == 2)
							bankDataDO.setAcc_cb((ccb.add(tamt)).toString());
						session.save(bankDataDO);

						btDetailsDO.setModified_by(dataDO.getCreated_by());
						btDetailsDO.setModified_date(System.currentTimeMillis());
						btDetailsDO.setDbank_acb(bankDataDO.getAcc_cb());
						btDetailsDO.setDeleted(2);
						session.update(btDetailsDO);
					}
				}

				UpdateDataOnDeletingTransactions udodt = new UpdateDataOnDeletingTransactions();
				if (dataDO.getTranx_type() != 3 && (bankId != 0) && (!(btItemsResult.isEmpty())))
					udodt.updateDataOnDeleteInBankTransactions(dataDO.getCreated_by(), btItemsResult);

				dataDO.setDeleted(3);
				dataDO.setModified_by(dataDO.getCreated_by());
				dataDO.setModified_date(System.currentTimeMillis());
				session.update(dataDO);
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> deleteAgencyPartnersTranxsData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Capital Report Data
	public List<PartnerTranxDO> getAgencyPartnersTranxsDataByPartnerIdAndYear(long fromDate, long toDate,
			long partnerId, long agencyId) throws BusinessException {
		List<PartnerTranxDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<PartnerTranxDO> query = session
					.createQuery("from PartnerTranxDO where created_by = ?1 and deleted = ?2"
							+ " and tranx_date between ?3 and ?4 and partner_id = ?5");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, fromDate);
			query.setParameter(4, toDate);
			query.setParameter(5, partnerId);
			List<PartnerTranxDO> result = query.getResultList();
			if (result.size() > 0) {
				for (PartnerTranxDO dataDO : result) {
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

	public DepreciationDO fetchDepreciationReportForAgencyByFinancialYear(long fdl, long tdl, int sfy, long agencyId) {
		DepreciationDO depDO = new DepreciationDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			@SuppressWarnings("unchecked")
			Query<DepreciationDO> query = session
					.createQuery("from DepreciationDO where created_by = ?1 and deleted = ?2" + " and fyv = ?3");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, sfy);
			List<DepreciationDO> result = query.getResultList();
			if (result.size() > 0) {
				for (DepreciationDO dataDO : result) {
					depDO = dataDO;
				}
			} else {
				query.setParameter(3, (sfy - 1));
				String lycdamt = "0.00";
				List<DepreciationDO> oresult = query.getResultList();
				if (oresult.size() > 0) {
					for (DepreciationDO dataDO : oresult) {
						lycdamt = dataDO.getC_amt();
					}
				}

				tx = session.beginTransaction();
				depDO.setO_amt(lycdamt);
				depDO.setP_amt("0.00");
				depDO.setC_amt("0.00");
				depDO.setD_amt("0.00");
				depDO.setFyv(sfy);
				depDO.setStatus(1);
				depDO.setCreated_by(agencyId);
				depDO.setCreated_date(System.currentTimeMillis());
				depDO.setModified_by(agencyId);
				depDO.setModified_date(System.currentTimeMillis());
				depDO.setVersion(1);
				depDO.setDeleted(0);
				session.save(depDO);
				tx.commit();
			}
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> fetchDepreciationReportForAgencyByFinancialYear  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return depDO;
	}

	public DepreciationDO processDepreciationReportForAgencyByFinancialYear(long recordId, int status, long agencyId) {
		DepreciationDO depDO = new DepreciationDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			depDO = session.get(DepreciationDO.class, new Long(recordId));
			depDO.setStatus(2);
			session.update(depDO);

			// Initiate Thread to do the process
			ExecutorService executorService = Executors.newSingleThreadExecutor();

			executorService.execute(new Runnable() {
				public void run() {
					System.out.println("Depreciation Report Started ...");
					ProcessorsUtilManager pum = new ProcessorsUtilManager();
					pum.processDepreciationReportByRecordId(recordId);
					System.out.println("Depreciation Report Completed ...");
				}
			});
			executorService.shutdown();
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> processDepreciationReportForAgencyByFinancialYear  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return depDO;
	}

	// Fetch Asset Details...
	@SuppressWarnings("unchecked")
	public List<DepreciationDataDO> getDepreciationDataDOList(long recordId) {
		List<DepreciationDataDO> allDetailsDO = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			DepreciationDO dataDO = session.get(DepreciationDO.class, new Long(recordId));
			int fy = dataDO.getFyv();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromdate = fy + "-04-01";
			String todate = (fy + 1) + "-03-31";

			long fdl = (sdf.parse(fromdate)).getTime();
			long tdl = (sdf.parse(todate)).getTime();

			Query<OtherPurchaseInvoiceDO> oquery = session
					.createQuery("from OtherPurchaseInvoiceDO where created_by = ?1 and deleted = ?2 "
							+ " and ch = ?5 and inv_date between ?3 and ?4 ");
			oquery.setParameter(1, dataDO.getCreated_by());
			oquery.setParameter(2, 0);
			oquery.setParameter(3, fdl);
			oquery.setParameter(4, tdl);
			oquery.setParameter(5, 14);
			List<OtherPurchaseInvoiceDO> oresult = oquery.getResultList();
			if (oresult.size() > 0) {
				for (OtherPurchaseInvoiceDO opdataDO : oresult) {
					DepreciationDataDO depDataDO = new DepreciationDataDO();
					depDataDO.setId(opdataDO.getId());
					depDataDO.setMh(opdataDO.getMh());
					depDataDO.setInv_date(opdataDO.getInv_date());
					depDataDO.setBasic_amount(opdataDO.getP_amount());
					depDataDO.setDtype(1);
					allDetailsDO.add(depDataDO);
				}
			}

			Query<AssetDataDO> aquery = session.createQuery(
					"from AssetDataDO where created_by = ?1 and deleted = ?2 " + " and asset_tdate between ?3 and ?4 ");
			aquery.setParameter(1, dataDO.getCreated_by());
			aquery.setParameter(2, 0);
			aquery.setParameter(3, fdl);
			aquery.setParameter(4, tdl);
			List<AssetDataDO> aresult = aquery.getResultList();
			if (aresult.size() > 0) {
				for (AssetDataDO adataDO : aresult) {
					DepreciationDataDO depDataDO = new DepreciationDataDO();
					depDataDO.setId(adataDO.getId());
					depDataDO.setMh(adataDO.getAsset_ah());
					depDataDO.setInv_date(adataDO.getAsset_tdate());
					depDataDO.setBasic_amount(adataDO.getAsset_value());
					depDataDO.setDtype(2);
					allDetailsDO.add(depDataDO);
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
		return allDetailsDO;
	}

	public DepreciationDO submitDepreciationReportForAgencyByFinancialYear(long recordId, long agencyId) {
		DepreciationDO depDO = new DepreciationDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			depDO = session.get(DepreciationDO.class, new Long(recordId));
			depDO.setStatus(4);
			session.update(depDO);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> submitDepreciationReportForAgencyByFinancialYear  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return depDO;
	}

	public CapitalAccountReportDO fetchCapitalAccountReportForAgencyByFinancialYear(long fdl, long tdl, int sfy,
			long agencyId) {
		CapitalAccountReportDO carDO = new CapitalAccountReportDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			@SuppressWarnings("unchecked")
			Query<CapitalAccountReportDO> query = session.createQuery(
					"from CapitalAccountReportDO where created_by = ?1 and deleted = ?2" + " and fyv = ?3");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, sfy);
			List<CapitalAccountReportDO> result = query.getResultList();
			if (result.size() > 0) {
				for (CapitalAccountReportDO dataDO : result) {
					carDO = dataDO;
				}
			} else {
				query.setParameter(3, (sfy - 1));
				String lycdamt = "0.00";
				int isNew = 1;
				List<CapitalAccountReportDO> oresult = query.getResultList();
				if (oresult.size() > 0) {
					for (CapitalAccountReportDO dataDO : oresult) {
						lycdamt = dataDO.getC_amt();
						isNew = isNew + 1;
					}
				}

				tx = session.beginTransaction();
				carDO.setO_amt(lycdamt);
				carDO.setIs_new(isNew);
				carDO.setPo_amt("0.00");
				carDO.setI_amt("0.00");
				carDO.setW_amt("0.00");
				carDO.setC_amt("0.00");
				carDO.setFyv(sfy);
				carDO.setStatus(1);
				carDO.setCreated_by(agencyId);
				carDO.setCreated_date(System.currentTimeMillis());
				carDO.setModified_by(agencyId);
				carDO.setModified_date(System.currentTimeMillis());
				carDO.setVersion(1);
				carDO.setDeleted(0);
				session.save(carDO);
				tx.commit();
			}
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> fetchCapitalAccountReportForAgencyByFinancialYear  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return carDO;
	}

	public CapitalAccountReportDO processCapitalAccountReportForAgencyByFinancialYear(long recordId, int status,
			long agencyId) {
		CapitalAccountReportDO carDO = new CapitalAccountReportDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			carDO = session.get(CapitalAccountReportDO.class, new Long(recordId));
			carDO.setStatus(2);
			session.update(carDO);
			// tx.commit();
			// Initiate Thread to do the process
			ExecutorService executorService = Executors.newSingleThreadExecutor();

			executorService.execute(new Runnable() {
				public void run() {
					System.out.println("Capital Account Report Started ...");
					ProcessorsUtilManager pum = new ProcessorsUtilManager();
					pum.processCapitalAccountReportByRecordId(recordId);
					System.out.println("Capital Account Report Completed ...");
				}
			});
			executorService.shutdown();
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> processCapitalAccountReportForAgencyByFinancialYear  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return carDO;
	}

	public CapitalAccountReportDO submitCapitalAccountReportForAgencyByFinancialYear(long recordId, long agencyId) {
		CapitalAccountReportDO carDO = new CapitalAccountReportDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			carDO = session.get(CapitalAccountReportDO.class, new Long(recordId));
			carDO.setStatus(4);
			session.update(carDO);
			tx.commit();
		} catch (Exception e) {
			logger.error(e);
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> submitCapitalAccountReportForAgencyByFinancialYear  is not succesful");
			}
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return carDO;
	}

	public PAndLAccountDO fetchPAndLAccountForAgencyByFinancialYear(int sfy, long agencyId) {
		PAndLAccountDO palDO = new PAndLAccountDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			@SuppressWarnings("unchecked")
			Query<PAndLAccountDO> query = session
					.createQuery("from PAndLAccountDO where created_by = ?1 and deleted = ?2" + " and fyv = ?3");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, sfy);
			List<PAndLAccountDO> result = query.getResultList();
			if (result.size() > 0) {
				for (PAndLAccountDO dataDO : result) {
					palDO = dataDO;
				}
			} else {
				tx = session.beginTransaction();
				palDO.setFyv(sfy);
				palDO.setStatus(1);
				palDO.setCreated_by(agencyId);
				palDO.setCreated_date(System.currentTimeMillis());
				palDO.setModified_by(agencyId);
				palDO.setModified_date(System.currentTimeMillis());
				palDO.setVersion(1);
				palDO.setDeleted(0);
				session.save(palDO);

				PAndLAccountDetailsDO detailsDO = new PAndLAccountDetailsDO();
				detailsDO.setPl_id(palDO.getId());
				detailsDO.setOsv_amt("0.00");
				detailsDO.setCsv_amt("0.00");
				detailsDO.setPv_amt("0.00");
				detailsDO.setSv_amt("0.00");
				detailsDO.setOi_amt("0.00");
				detailsDO.setGp_amt("0.00");
				session.save(detailsDO);
				tx.commit();
			}
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> fetchPAndLAccountForAgencyByFinancialYear  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return palDO;
	}

	public PAndLAccountDetailsDO fetchPAndLAccountDetailsForAgencyByFinancialYear(long recordId) {
		PAndLAccountDetailsDO detailsDO = new PAndLAccountDetailsDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<PAndLAccountDetailsDO> query = session.createQuery("from PAndLAccountDetailsDO where pl_id = ?1");
			query.setParameter(1, recordId);
			List<PAndLAccountDetailsDO> result = query.getResultList();
			if (result.size() > 0) {
				for (PAndLAccountDetailsDO dataDO : result) {
					detailsDO = dataDO;
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
		return detailsDO;
	}

	public PAndLAccountDO processPAndLAccountForAgencyByFinancialYear(long recordId, long agencyId) {
		PAndLAccountDO dataDO = new PAndLAccountDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dataDO = session.get(PAndLAccountDO.class, new Long(recordId));
			int pandlPreviousStatus = dataDO.getStatus();
			dataDO.setStatus(2);
			session.update(dataDO);
			// Initiate Thread to do the process
			ExecutorService executorService = Executors.newSingleThreadExecutor();

			executorService.execute(new Runnable() {
				public void run() {
					System.out.println("P and L Account Report Started ...");
					ProcessorsUtilManager pum = new ProcessorsUtilManager();
					pum.processPAndLAccountByRecordId(recordId, pandlPreviousStatus);
					System.out.println("P and L Account Report Completed ...");
				}
			});
			executorService.shutdown();
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> processPAndLAccountForAgencyByFinancialYear  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return dataDO;
	}

	public PAndLAccountDO savePAndLAccountForAgencyByFinancialYear(String osv, String csv, String tpv, String tsv,
			String toi, long recordId, long agencyId) {
		PAndLAccountDO dataDO = new PAndLAccountDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dataDO = session.get(PAndLAccountDO.class, new Long(recordId));
			int pandlPreviousStatus = dataDO.getStatus();
			dataDO.setStatus(2);
			session.update(dataDO);

			PAndLAccountDetailsDO detailsDO = new PAndLAccountDetailsDO();
			@SuppressWarnings("unchecked")
			Query<PAndLAccountDetailsDO> query = session.createQuery("from PAndLAccountDetailsDO where pl_id = ?1");
			query.setParameter(1, recordId);
			List<PAndLAccountDetailsDO> result = query.getResultList();
			if (result.size() > 0) {
				for (PAndLAccountDetailsDO plDetailsDO : result) {
					detailsDO = plDetailsDO;
				}
			}
			detailsDO.setOsv_amt(osv);
			detailsDO.setCsv_amt(csv);
			detailsDO.setPv_amt(tpv);
			detailsDO.setSv_amt(tsv);
			detailsDO.setOi_amt(toi);
			session.update(detailsDO);
			tx.commit();
			// Initiate Thread to do the re-process
			ExecutorService executorService = Executors.newSingleThreadExecutor();

			executorService.execute(new Runnable() {
				public void run() {
					System.out.println("P and L Account Report Started ...");
					ProcessorsUtilManager pum = new ProcessorsUtilManager();
					pum.processPAndLAccountByRecordId(recordId, pandlPreviousStatus);
					System.out.println("P and L Account Report Completed ...");
				}
			});
			executorService.shutdown();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> savePAndLAccountForAgencyByFinancialYear  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return dataDO;
	}

	public PAndLAccountDO submitPAndLAccountForAgencyByFinancialYear(String gpv, String npv, long recordId,
			long agencyId) {
		PAndLAccountDO dataDO = new PAndLAccountDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dataDO = session.get(PAndLAccountDO.class, new Long(recordId));
			dataDO.setStatus(4);
			session.update(dataDO);
			PAndLAccountDetailsDO detailsDO = new PAndLAccountDetailsDO();

			@SuppressWarnings("unchecked")
			Query<PAndLAccountDetailsDO> query = session.createQuery("from PAndLAccountDetailsDO where pl_id = ?1");
			query.setParameter(1, recordId);
			List<PAndLAccountDetailsDO> result = query.getResultList();
			if (result.size() > 0) {
				for (PAndLAccountDetailsDO plDetailsDO : result) {
					detailsDO = plDetailsDO;
				}
			}
			detailsDO.setGp_amt(gpv);
			detailsDO.setNp_amt(npv);
			session.update(detailsDO);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> submitPAndLAccountForAgencyByFinancialYear  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return dataDO;
	}

	public BalanceSheetDO fetchBalanceSheetForAgencyByFinancialYear(int sfy, long agencyId) {
		BalanceSheetDO bsDO = new BalanceSheetDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			@SuppressWarnings("unchecked")
			Query<BalanceSheetDO> query = session
					.createQuery("from BalanceSheetDO where created_by = ?1 and deleted = ?2" + " and fyv = ?3");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, sfy);
			List<BalanceSheetDO> result = query.getResultList();
			if (result.size() > 0) {
				for (BalanceSheetDO dataDO : result) {
					bsDO = dataDO;
				}
			} else {
				tx = session.beginTransaction();
				bsDO.setFyv(sfy);
				bsDO.setStatus(1);
				bsDO.setCreated_by(agencyId);
				bsDO.setCreated_date(System.currentTimeMillis());
				bsDO.setModified_by(agencyId);
				bsDO.setModified_date(System.currentTimeMillis());
				bsDO.setVersion(1);
				bsDO.setDeleted(0);
				session.save(bsDO);

				BalanceSheetDetailsDO detailsDO = new BalanceSheetDetailsDO();
				detailsDO.setBs_id(bsDO.getId());
				session.save(detailsDO);
				tx.commit();
			}
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> fetchBalanceSheetForAgencyByFinancialYear  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return bsDO;
	}

	public BalanceSheetDetailsDO fetchBalanceSheetDetailsForAgencyByFinancialYear(long recordId) {
		BalanceSheetDetailsDO detailsDO = new BalanceSheetDetailsDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<BalanceSheetDetailsDO> query = session.createQuery("from BalanceSheetDetailsDO where bs_id = ?1");
			query.setParameter(1, recordId);
			List<BalanceSheetDetailsDO> result = query.getResultList();
			if (result.size() > 0) {
				for (BalanceSheetDetailsDO dataDO : result) {
					detailsDO = dataDO;
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
		return detailsDO;
	}

	public BalanceSheetDO processBalanceSheetForAgencyByFinancialYear(long recordId, long agencyId) {
		BalanceSheetDO dataDO = new BalanceSheetDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dataDO = session.get(BalanceSheetDO.class, new Long(recordId));
			dataDO.setStatus(2);
			session.update(dataDO);
			tx.commit();
			// Initiate Thread to do the process
			ExecutorService executorService = Executors.newSingleThreadExecutor();

			executorService.execute(new Runnable() {
				public void run() {
					System.out.println("P and L Account Report Started ...");
					ProcessorsUtilManager pum = new ProcessorsUtilManager();
					pum.processBalanceSheetByRecordId(recordId);
					System.out.println("P and L Account Report Completed ...");
				}
			});
			executorService.shutdown();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> processBalanceSheetForAgencyByFinancialYear  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return dataDO;
	}

	public BalanceSheetDO saveBalanceSheetForAgencyByFinancialYear(String sc, String fr, String pr, String sd,
			String la, String ch, String dp, String rs, long recordId, long agencyId) {
		BalanceSheetDO dataDO = new BalanceSheetDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dataDO = session.get(BalanceSheetDO.class, new Long(recordId));
			dataDO.setStatus(2);
			session.update(dataDO);
			BalanceSheetDetailsDO detailsDO = new BalanceSheetDetailsDO();
			@SuppressWarnings("unchecked")
			Query<BalanceSheetDetailsDO> query = session.createQuery("from BalanceSheetDetailsDO where bs_id = ?1");
			query.setParameter(1, recordId);
			List<BalanceSheetDetailsDO> result = query.getResultList();
			if (result.size() > 0) {
				for (BalanceSheetDetailsDO bsDetailsDO : result) {
					detailsDO = bsDetailsDO;
				}
			}
			detailsDO.setClsc_amt(sc);
			detailsDO.setUlfr_amt(fr);
			detailsDO.setProv_amt(pr);
			detailsDO.setCasd_amt(sd);
			detailsDO.setCala_amt(la);
			detailsDO.setCach_amt(ch);
			detailsDO.setCadp_amt(dp);
			detailsDO.setCars_amt(rs);
			session.update(detailsDO);
			tx.commit();
			// Initiate Thread to do the re-process
			ExecutorService executorService = Executors.newSingleThreadExecutor();

			executorService.execute(new Runnable() {
				public void run() {
					System.out.println("Balance Sheet Started ...");
					ProcessorsUtilManager pum = new ProcessorsUtilManager();
					pum.processBalanceSheetByRecordId(recordId);
					System.out.println("Balance Sheet Completed ...");
				}
			});
			executorService.shutdown();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> saveBalanceSheetForAgencyByFinancialYear  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return dataDO;
	}

	public BalanceSheetDO submitBalanceSheetForAgencyByFinancialYear(String tlv, String tav, long recordId,
			long agencyId) {
		BalanceSheetDO dataDO = new BalanceSheetDO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dataDO = session.get(BalanceSheetDO.class, new Long(recordId));
			dataDO.setStatus(4);
			session.update(dataDO);
			BalanceSheetDetailsDO detailsDO = new BalanceSheetDetailsDO();
			@SuppressWarnings("unchecked")
			Query<BalanceSheetDetailsDO> query = session.createQuery("from BalanceSheetDetailsDO where bs_id = ?1");
			query.setParameter(1, recordId);
			List<BalanceSheetDetailsDO> result = query.getResultList();
			if (result.size() > 0) {
				for (BalanceSheetDetailsDO plDetailsDO : result) {
					detailsDO = plDetailsDO;
				}
			}
			detailsDO.setFytl_amt(tlv);
			detailsDO.setFyta_amt(tav);
			session.update(detailsDO);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> FinancialReportsPersistenceManager --> submitBalanceSheetForAgencyByFinancialYear  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return dataDO;
	}

}
