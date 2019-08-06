package com.it.erpapp.framework.managers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.it.erpapp.cache.DepreciationPercentManager;
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

public class ProcessorsUtilManager {

	Logger logger = Logger.getLogger(ProcessorsUtilManager.class.getName());

	private final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	// Process Depreciation Report
	public void processDepreciationReportByRecordId(long recordId) {

		List<DepreciationDataDO> depDataList = new ArrayList<>();

		// Fetch Depreciation Percentages
		DepreciationPercentManager dpm = DepreciationPercentManager.getInstance();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			DepreciationDO dataDO = session.get(DepreciationDO.class, new Long(recordId));
			int fy = dataDO.getFyv();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromdate = fy + "-04-01";
			String todate = (fy + 1) + "-03-31";
			String hydate = fy + "-09-30";
			long fdl = (sdf.parse(fromdate)).getTime();
			long tdl = (sdf.parse(todate)).getTime();
			long hydl = (sdf.parse(hydate)).getTime();

			// Fetch Assets from Other Purchases
			@SuppressWarnings("unchecked")
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
					depDataList.add(depDataDO);
				}
			}

			// Fetch Assets from Assets Management
			@SuppressWarnings("unchecked")
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
					depDataList.add(depDataDO);
				}
			}

			BigDecimal ta = new BigDecimal("0.00");
			BigDecimal td = new BigDecimal("0.00");
			// Process Entries...
			for (DepreciationDataDO depDataDO : depDataList) {
				int ps = depDataDO.getDtype();
				BigDecimal ba = new BigDecimal(depDataDO.getBasic_amount());
				BigDecimal da = new BigDecimal("0.00");
				if (depDataDO.getMh() == 141) {
					// NOT APPLICABLE
				} else if (depDataDO.getMh() == 142) {
					long rd = depDataDO.getInv_date();
					if (rd <= hydl) {
						BigDecimal dp = new BigDecimal(ps == 1 ? dpm.getDepreciationPercentForFullYear("142")
								: dpm.getDepreciationPercentForHalfYear("142"));
						da = ba.multiply(dp).divide(ONE_HUNDRED);
					} else if (rd > hydl) {
						BigDecimal dp = new BigDecimal(ps == 1 ? dpm.getDepreciationPercentForHalfYear("142")
								: dpm.getDepreciationPercentForFullYear("142"));
						da = ba.multiply(dp).divide(ONE_HUNDRED);
					}
				} else if (depDataDO.getMh() == 143) {
					// NOT APPLICABLE
				} else if (depDataDO.getMh() == 144) {
					long rd = depDataDO.getInv_date();
					if (rd <= hydl) {
						BigDecimal dp = new BigDecimal(ps == 1 ? dpm.getDepreciationPercentForFullYear("144")
								: dpm.getDepreciationPercentForHalfYear("144"));
						da = ba.multiply(dp).divide(ONE_HUNDRED);
					} else if (rd > hydl) {
						BigDecimal dp = new BigDecimal(ps == 1 ? dpm.getDepreciationPercentForHalfYear("144")
								: dpm.getDepreciationPercentForFullYear("144"));
						da = ba.multiply(dp).divide(ONE_HUNDRED);
					}
				} else if (depDataDO.getMh() == 145) {
					long rd = depDataDO.getInv_date();
					if (rd <= hydl) {
						BigDecimal dp = new BigDecimal(ps == 1 ? dpm.getDepreciationPercentForFullYear("145")
								: dpm.getDepreciationPercentForHalfYear("145"));
						da = ba.multiply(dp).divide(ONE_HUNDRED);
					} else if (rd > hydl) {
						BigDecimal dp = new BigDecimal(ps == 1 ? dpm.getDepreciationPercentForHalfYear("145")
								: dpm.getDepreciationPercentForFullYear("145"));
						da = ba.multiply(dp).divide(ONE_HUNDRED);
					}
				} else if (depDataDO.getMh() == 146) {
					long rd = depDataDO.getInv_date();
					if (rd <= hydl) {
						BigDecimal dp = new BigDecimal(ps == 1 ? dpm.getDepreciationPercentForFullYear("146")
								: dpm.getDepreciationPercentForHalfYear("146"));
						da = ba.multiply(dp).divide(ONE_HUNDRED);
					} else if (rd > hydl) {
						BigDecimal dp = new BigDecimal(ps == 1 ? dpm.getDepreciationPercentForHalfYear("146")
								: dpm.getDepreciationPercentForFullYear("146"));
						da = ba.multiply(dp).divide(ONE_HUNDRED);
					}
				} else if (depDataDO.getMh() == 147) {
					// NOT APPLICABLE
				}

				if (depDataDO.getDtype() == 1) {
					ta = ta.add(ba);
					td = td.add(da);
				} else if (depDataDO.getDtype() == 2) {
					ta = ta.subtract(ba);
					td = td.subtract(da);
				}
			}
			BigDecimal oa = new BigDecimal(dataDO.getO_amt());
			dataDO.setP_amt(ta.toString());
			dataDO.setD_amt(td.toString());
			dataDO.setC_amt(((ta.add(oa)).subtract(td)).toString());
			dataDO.setStatus(3);
			session.update(dataDO);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> ProcessorsUtilManager --> processDepreciationReportByRecordId  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Process Capital Account
	public void processCapitalAccountReportByRecordId(long recordId) {

		int obc = 0;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			CapitalAccountReportDO dataDO = session.get(CapitalAccountReportDO.class, new Long(recordId));
			int fy = dataDO.getFyv();
			BigDecimal oamt = new BigDecimal(dataDO.getO_amt());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromdate = fy + "-04-01";
			String todate = (fy + 1) + "-03-31";
			long fdl = (sdf.parse(fromdate)).getTime();
			long tdl = (sdf.parse(todate)).getTime();

			// Fetch Partner Opening Balances
			List<PartnerInfoDO> piDOList = new ArrayList<>();
			if (dataDO.getIs_new() == 1) {
				obc = obc + 1;
				@SuppressWarnings("unchecked")
				Query<PartnerInfoDO> query = session.createQuery("from PartnerInfoDO where created_by = ?1");
				query.setParameter(1, dataDO.getCreated_by());
				List<PartnerInfoDO> result = query.getResultList();
				if (result.size() > 0) {
					for (PartnerInfoDO piDO : result) {
						if (piDO.getStatus() != 3) {
							piDOList.add(piDO);
						} else if (piDO.getStatus() == 3) {
							long wdl = piDO.getModified_date();
							if ((wdl > 0) && ((wdl >= fdl) && (wdl <= tdl))) {
								piDOList.add(piDO);
							}
						}
					}
				}

				BigDecimal pob = new BigDecimal("0.00");
				for (PartnerInfoDO piDO : piDOList) {
					pob = pob.add(new BigDecimal(piDO.getOpening_balance()));
				}

				dataDO.setO_amt(pob.toString());
				dataDO.setPo_amt(pob.toString());
				oamt = pob;
			}

			// Fetch Partner Transactions
			List<PartnerTranxDO> ptDOList = new ArrayList<>();
			@SuppressWarnings("unchecked")
			Query<PartnerTranxDO> ptquery = session
					.createQuery("from PartnerTranxDO where created_by = ?1 and deleted = ?2"
							+ " and tranx_date between ?3 and ?4 ");
			ptquery.setParameter(1, dataDO.getCreated_by());
			ptquery.setParameter(2, 0);
			ptquery.setParameter(3, fdl);
			ptquery.setParameter(4, tdl);
			List<PartnerTranxDO> result = ptquery.getResultList();
			if (result.size() > 0) {
				for (PartnerTranxDO ptdataDO : result) {
					ptDOList.add(ptdataDO);
				}
			}

			BigDecimal ti = new BigDecimal("0.00");
			BigDecimal tw = new BigDecimal("0.00");
			BigDecimal cb = new BigDecimal("0.00");

			// Process Entries...

			for (PartnerTranxDO ptDO : ptDOList) {
				BigDecimal tamt = new BigDecimal(ptDO.getTranx_amount());
				if (ptDO.getTranx_type() == 1) {
					ti = ti.add(tamt);
					cb = cb.add(tamt);
				} else {
					tw = tw.add(tamt);
					cb = cb.subtract(tamt);
				}
			}

			dataDO.setI_amt(ti.toString());
			dataDO.setW_amt(tw.toString());
			dataDO.setC_amt((oamt.add(cb)).toString());
			dataDO.setStatus(3);
			session.update(dataDO);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> ProcessorsUtilManager --> processCapitalAccountReportByRecordId  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Process P&L Account
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void processPAndLAccountByRecordId(long recordId, int pandlPreviousStatus) {

		Map<String, String> plDetailsMap = new HashMap<String, String>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		PAndLAccountDO dataDO = new PAndLAccountDO();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dataDO = session.get(PAndLAccountDO.class, new Long(recordId));
			int fy = dataDO.getFyv();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String fromdate = fy + "-04-01";
			String todate = (fy + 1) + "-03-31";
			long fdl = (sdf.parse(fromdate)).getTime();
			long tdl = (sdf.parse(todate)).getTime();
			plDetailsMap.put("1", "0.00");
			plDetailsMap.put("2", "0.00");
			plDetailsMap.put("3", "0.00");
			plDetailsMap.put("4", "0.00");
			plDetailsMap.put("6", "0.00");
			plDetailsMap.put("7", "0.00");
			plDetailsMap.put("8", "0.00");
			plDetailsMap.put("9", "0.00");
			plDetailsMap.put("10", "0.00");
			plDetailsMap.put("13", "0.00");

			// Fetch Assets from Other Purchases
			String sqlQuery = "select op.ch, sum(op.p_amount) from OtherPurchaseInvoiceDO op where op.created_by="
					+ dataDO.getCreated_by() + " and op.deleted=0"
					+ " and op.ch>0 and op.ch<14 and (op.inv_date between " + fdl + " and " + tdl + ") group by op.ch";
			List<?> dataList = session.createQuery(sqlQuery).list();
			DecimalFormat df = new DecimalFormat("#.##");
			for (int i = 0; i < dataList.size(); i++) {
				Object[] row = (Object[]) dataList.get(i);
				plDetailsMap.put(row[0].toString(), df.format(Float.parseFloat(row[1].toString())));
			}

			PAndLAccountDetailsDO detailsDO = new PAndLAccountDetailsDO();
			Query<PAndLAccountDetailsDO> dquery = session.createQuery("from PAndLAccountDetailsDO where pl_id = ?1");
			dquery.setParameter(1, recordId);
			List<PAndLAccountDetailsDO> dresult = dquery.getResultList();
			if (dresult.size() > 0) {
				for (PAndLAccountDetailsDO qDO : dresult) {
					detailsDO = qDO;
				}
			}

			if (dataList.size() >= 0) {
				// Process Map Entries
				for (String mkey : plDetailsMap.keySet()) {
					switch (mkey) {
					case "1":
						detailsDO.setFo_amt(plDetailsMap.get(mkey));
						System.out.println("Key - " + mkey + " and Value - " + plDetailsMap.get(mkey));
						break;

					case "2":
						detailsDO.setFi_amt(plDetailsMap.get(mkey));
						break;

					case "3":
						detailsDO.setUc_amt(plDetailsMap.get(mkey));
						break;

					case "4":
						detailsDO.setIl_amt(plDetailsMap.get(mkey));
						break;

					case "6":
						detailsDO.setSp_amt(plDetailsMap.get(mkey));
						break;

					case "7":
						detailsDO.setTc_amt(plDetailsMap.get(mkey));
						break;

					case "8":
						detailsDO.setRn_amt(plDetailsMap.get(mkey));
						break;

					case "9":
						detailsDO.setBp_amt(plDetailsMap.get(mkey));
						break;

					case "10":
						detailsDO.setSw_amt(plDetailsMap.get(mkey));
						break;

					case "13":
						detailsDO.setMs_amt(plDetailsMap.get(mkey));
						break;

					default:
						break;
					}
				}
			}

			// Fetch Bank Transaction Charges
			BigDecimal btc = new BigDecimal("0.00");
			OTransactionsPersistenceManager otpm = new OTransactionsPersistenceManager();
			List<BankTranxsDataDO> bankTranxsWithChargesList = otpm.getAgencyBankTranxsDataWithCharges(fdl, tdl,
					dataDO.getCreated_by());
			for (BankTranxsDataDO btDO : bankTranxsWithChargesList) {
				btc = btc.add(new BigDecimal(btDO.getTrans_charges()));
			}
			detailsDO.setBc_amt(btc.toString());

			// Fetch Depreciation Details
			Query<DepreciationDO> drquery = session
					.createQuery("from DepreciationDO where fyv = ?1 and created_by = ?2");
			drquery.setParameter(1, dataDO.getFyv());
			drquery.setParameter(2, dataDO.getCreated_by());
			List<DepreciationDO> drresult = drquery.getResultList();
			if (drresult.size() > 0) {
				for (DepreciationDO depDO : drresult) {
					detailsDO.setDp_amt(depDO.getD_amt());
				}
			} else {
				detailsDO.setDp_amt("0.00");
			}

			dataDO.setStatus(3);
			session.update(dataDO);
			tx.commit();
		} catch (Exception e) {
			int flag = 0;
			try {
				flag = 1;
				if (tx != null)
					tx.rollback();
				flag = 2;

				tx = session.beginTransaction();
				dataDO.setStatus(pandlPreviousStatus);
				session.update(dataDO);
				tx.commit();

			} catch (HibernateException e1) {
				if (flag == 1)
					logger.error(
							"Transaction rollback in  com.it.erpapp.framework.managers --> ProcessorsUtilManager --> processPAndLAccountByRecordId  is not succesful");
				else if (flag == 2)
					logger.error(
							"Got an Exception while setting previous status to pandl account - ProcessorsUtilManager --> processPAndLAccountByRecordId ");
			} catch (Exception e2) {
				logger.error(
						"Got an Exception while setting previous status to pandl account - ProcessorsUtilManager --> processPAndLAccountByRecordId ");
				logger.error(e2);
				e2.printStackTrace();
				throw new BusinessException(e2.getMessage());
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		System.out.println();
	}

	// Process Balance Sheet
	@SuppressWarnings({ "unchecked" })
	public void processBalanceSheetByRecordId(long recordId) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			BalanceSheetDO dataDO = session.get(BalanceSheetDO.class, new Long(recordId));
			BalanceSheetDetailsDO detailsDO = new BalanceSheetDetailsDO();
			int fy = dataDO.getFyv();

			Query<BalanceSheetDetailsDO> bsdquery = session.createQuery("from BalanceSheetDetailsDO where bs_id = ?1");
			bsdquery.setParameter(1, recordId);
			List<BalanceSheetDetailsDO> bsdresult = bsdquery.getResultList();
			if (bsdresult.size() > 0) {
				for (BalanceSheetDetailsDO bsdDO : bsdresult) {
					detailsDO = bsdDO;
				}
			}

			// Fetch Capital Account Report
			Query<CapitalAccountReportDO> caquery = session
					.createQuery("from CapitalAccountReportDO where fyv = ?1 and created_by = ?2");
			caquery.setParameter(1, fy);
			caquery.setParameter(2, dataDO.getCreated_by());
			List<CapitalAccountReportDO> caresult = caquery.getResultList();
			if (caresult.size() > 0) {
				for (CapitalAccountReportDO caDO : caresult) {

					if (caDO.getStatus() == 4) {

						detailsDO.setCaob_amt(caDO.getO_amt());
						detailsDO.setCani_amt(caDO.getI_amt());
						detailsDO.setCanw_amt(caDO.getW_amt());
						detailsDO.setCacb_amt(caDO.getC_amt());
					} else {

						dataDO.setStatus(5);
						dataDO.setRemarks("SUBMIT CAPITAL ACCOUNT REPORT TO PROCESS BALANCE SHEET");
						session.update(dataDO);
						tx.commit();
						return;
					}
				}
			} else {

				dataDO.setStatus(5);
				dataDO.setRemarks("SUBMIT CAPITAL ACCOUNT REPORT TO PROCESS BALANCE SHEET");
				session.update(dataDO);
				tx.commit();
				return;
			}

			// Fetch P&L Report
			Query<PAndLAccountDO> plquery = session
					.createQuery("from PAndLAccountDO where fyv = ?1 and created_by = ?2");
			plquery.setParameter(1, dataDO.getFyv());
			plquery.setParameter(2, dataDO.getCreated_by());
			List<PAndLAccountDO> plresult = plquery.getResultList();
			if (plresult.size() > 0) {
				for (PAndLAccountDO depDO : plresult) {
					if (depDO.getStatus() == 4) {

						Query<PAndLAccountDetailsDO> pldquery = session
								.createQuery("from PAndLAccountDetailsDO where pl_id = ?1");
						pldquery.setParameter(1, depDO.getId());
						List<PAndLAccountDetailsDO> pldresult = pldquery.getResultList();
						for (PAndLAccountDetailsDO pldDetailsDO : pldresult) {
							detailsDO.setPlnp_amt(pldDetailsDO.getNp_amt());
							detailsDO.setCacs_amt(pldDetailsDO.getCsv_amt());
						}
					} else {

						dataDO.setStatus(5);
						dataDO.setRemarks("SUBMIT P &amp; L REPORT TO PROCESS BALANCE SHEET");
						session.update(dataDO);
						tx.commit();
						return;
					}
				}
			} else {

				dataDO.setStatus(5);
				dataDO.setRemarks("SUBMIT P &amp; L REPORT TO PROCESS BALANCE SHEET");
				session.update(dataDO);
				tx.commit();
				return;
			}

			// Fetch Depreciation Details
			Query<DepreciationDO> daquery = session
					.createQuery("from DepreciationDO where fyv = ?1 and created_by = ?2");
			daquery.setParameter(1, dataDO.getFyv());
			daquery.setParameter(2, dataDO.getCreated_by());
			List<DepreciationDO> daresult = daquery.getResultList();
			if (daresult.size() > 0) {
				for (DepreciationDO depDO : daresult) {
					if (depDO.getStatus() == 4) {

						detailsDO.setFaob_amt(depDO.getO_amt());
						detailsDO.setFanp_amt(depDO.getP_amt());
						detailsDO.setFadp_amt(depDO.getD_amt());
						detailsDO.setFacb_amt(depDO.getC_amt());
					} else {

						dataDO.setStatus(5);
						dataDO.setRemarks("SUBMIT DEPRECIATION REPORT TO PROCESS BALANCE SHEET");
						session.update(dataDO);
						tx.commit();
						return;
					}
				}
			} else {

				dataDO.setStatus(5);
				dataDO.setRemarks("SUBMIT DEPRECIATION REPORT TO PROCESS BALANCE SHEET");
				session.update(dataDO);
				tx.commit();
				return;
			}

			// Fetch Bank Stuff for Secured loans & Cash @Bank
			List<BankDataDO> ebdList = new ArrayList<>();
			Query<BankDataDO> bquery = session.createQuery("from BankDataDO where created_by = ?1 and deleted = ?2 ");
			bquery.setParameter(1, dataDO.getCreated_by());
			bquery.setParameter(2, 0);
			List<BankDataDO> result = bquery.getResultList();
			if (result.size() > 0) {
				for (BankDataDO bankDataDO : result) {
					String bcode = bankDataDO.getBank_code();
					if (bcode.equalsIgnoreCase("SAVINGS") || bcode.equalsIgnoreCase("CURRENT")
							|| bcode.equalsIgnoreCase("LOAN") || bcode.equalsIgnoreCase("OVER DRAFT")) {
						ebdList.add(bankDataDO);
					}
				}
			}

			BigDecimal tlb = new BigDecimal("0.00");
			BigDecimal tcb = new BigDecimal("0.00");
			for (BankDataDO bdDO : ebdList) {
				String bcode = bdDO.getBank_code();
				if (bcode.equalsIgnoreCase("SAVINGS") || bcode.equalsIgnoreCase("CURRENT")) {
					tcb = tcb.add(new BigDecimal(bdDO.getAcc_cb()));
				} else if (bcode.equalsIgnoreCase("LOAN") || bcode.equalsIgnoreCase("OVER DRAFT")) {
					tlb = tlb.add(new BigDecimal(bdDO.getAcc_cb()));
				}
			}
			detailsDO.setSltb_amt(tlb.toString());
			detailsDO.setCabc_amt(tcb.toString());

			dataDO.setStatus(3);
			dataDO.setRemarks("SUCCESS");
			session.update(dataDO);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> ProcessorsUtilManager --> processBalanceSheetByRecordId  is not succesful");
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
