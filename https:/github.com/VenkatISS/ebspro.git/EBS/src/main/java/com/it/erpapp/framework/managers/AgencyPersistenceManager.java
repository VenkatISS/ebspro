package com.it.erpapp.framework.managers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.it.erpapp.framework.model.AccountActivationDO;
import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.framework.model.AgencyCashDataDO;
import com.it.erpapp.framework.model.AgencyDO;
import com.it.erpapp.framework.model.AgencyDetailsDO;
import com.it.erpapp.framework.model.AgencySerialNosDO;
import com.it.erpapp.framework.model.BankDataDO;
import com.it.erpapp.framework.model.CVODataDO;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.mailservices.MailUtility;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;
import com.it.erpapp.utils.PasswordUtil;

public class AgencyPersistenceManager {

	Logger logger = Logger.getLogger(AgencyPersistenceManager.class.getName());

	public void createAgency(AgencyDO agencyDO, AgencyDetailsDO agencyDetailsDO, List<BankDataDO> bankDataDOList,
			AccountActivationDO accountActvDO) throws BusinessException {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			session.save(agencyDO);
			agencyDetailsDO.setCreatedBy(agencyDO.getAgencyCode());
			session.save(agencyDetailsDO);
			for (BankDataDO bankDataDO : bankDataDOList) {
				session.save(bankDataDO);
			}
			session.save(accountActvDO);

			long agencyCode = agencyDO.getAgencyCode();
			String emailId = agencyDO.getEmailId();
			String activationCode = accountActvDO.getActivationCode();
			// Send email notification
			MailUtility mailUtility = new MailUtility();
			mailUtility.sendRegistrationActivationMail(agencyCode, emailId, activationCode);

			tx.commit();

		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> AgencyPersistenceManager --> createAgency  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();

			String s = null;
			String emsg = e.getCause().toString();
			if (emsg.contains("Duplicate entry")) {
				if (emsg.contains(Long.toString(agencyDO.getAgencyCode()))) {
					s = "UNABLE TO REGISTER AGENCY. THE AGENCY ID ALREADY EXISTS.<br>PLEASE CHECK IT AND REGISTER AGAIN.";
				} else if (emsg.contains(agencyDO.getEmailId())) {
					s = "UNABLE TO REGISTER AGENCY. THE EMAIL ID ALREADY EXISTS.<br>PLEASE CHECK IT AND REGISTER AGAIN.";
				} else if (emsg.contains(agencyDetailsDO.getGstin())) {
					s = "UNABLE TO REGISTER AGENCY. THE GSTIN ALREADY EXISTS.<br>PLEASE CHECK IT AND REGISTER AGAIN.";
				}
			}
			throw new BusinessException(s);
		} finally {
			session.clear();
			session.close();
		}
	}

	// emailactivation
	@SuppressWarnings("unchecked")
	public AccountActivationDO activateDealerAccount(long agencyCode, String activationCode, int requestType) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;

		AgencyDO agencyDO;
		AccountActivationDO avtvDO = new AccountActivationDO();
		Query<AccountActivationDO> query = session.createQuery(
				"from AccountActivationDO where AGENCY_CODE = ?1 and ACTIVATION_CODE = ?2 and REQUEST_TYPE = ?3 ");
		query.setParameter(1, agencyCode);
		query.setParameter(2, activationCode);
		query.setParameter(3, 1);
		List<AccountActivationDO> result = query.getResultList();
		try {
			if (result.size() > 0) {
				for (AccountActivationDO acdo : result) {
					tx = session.beginTransaction();
					agencyDO = session.get(AgencyDO.class, new Long(agencyCode));
					if ((activationCode.equals(acdo.getActivationCode())) && (agencyDO.getStatus() == 0)) {
						agencyDO.setStatus(1);
						session.update(agencyDO);
						tx.commit();
					} else if (agencyDO.getStatus() == 2) {
						// when admin de-activates dealer's account...
						System.out.println(
								"Your account has been deactivated. If you have any queries, feel free to contact us through mail: support@ilpg.in or Ph.No : 040-23546767");
						logger.info(
								"Your account has been deactivated. If you have any queries, feel free to contact us through mail: support@ilpg.in or Ph.No : 040-23546767");
					}
				}
			}
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> AgencyPersistenceManager --> activateDealerAccount  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return avtvDO;
	}

	public void sendResetPasswordMail(String emailId) throws BusinessException {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Date resetPwddate = new Date();
			long ldate = resetPwddate.getTime();

			Long agencyCode = null;

			@SuppressWarnings("unchecked")
			Query<AgencyDO> query = session.createQuery("from AgencyDO where emailId = ?1");
			query.setParameter(1, emailId);

			List<AgencyDO> result = query.getResultList();

			if (result.size() > 0) {
				for (AgencyDO ado : result) {
					if (ado.getStatus() == 1) {
						if (emailId.equals(ado.getEmailId())) {
							agencyCode = ado.getAgencyCode();
						}
					} else if (ado.getStatus() == 0) {
						throw new BusinessException("YOUR ACCOUNT HAS NOT BEEN ACTIVATED. <br>"
								+ "WE SENT AN EMAIL WITH THE ACTIVATION LINK AT THE TIME OF YOUR REGISTRATION. <br>"
								+ "PLEASE ACTIVATE IT AND TRY AGAIN.");
					} else if (ado.getStatus() == 2) {
						System.out.println(
								"Your account has been deactivated. If you have any queries, feel free to contact us through mail: support@ilpg.in or Ph.No : 040-23546767");
					}
				}
				// Send email notification
				if (agencyCode != null) {
					MailUtility mailUtility = new MailUtility();
					mailUtility.sendResetPasswordMail(agencyCode, emailId, ldate);
				}
			} else {
				throw new BusinessException("THE EMAIL ID YOU HAVE PROVIDED IS NOT REGISTERED.");
			}
			tx.commit();
		} catch (BusinessException be) {
			logger.error(be.getExceptionMessage());
			logger.error(be);
			be.printStackTrace();
			throw be;
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> AgencyPersistenceManager --> sendResetPasswordMail  is not succesful");
			}
			logger.error(e);
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new BusinessException("UNABLE TO COMPLETE FORGET PASSWORD PROCESS");
		} finally {
			session.clear();
			session.close();
		}
	}

	// update password
	@SuppressWarnings("unchecked")
	public AgencyVO updatePassword(long agencyCode, String activationCode, String newPassword) {
		AgencyVO agencyVO = new AgencyVO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		AgencyDO agencyDO;
		Query<AgencyDO> query = session.createQuery("from AgencyDO where agencyCode = ?1 and status = ?2 ");
		query.setParameter(1, agencyCode);
		query.setParameter(2, 1);
		try {
			List<AgencyDO> result = query.getResultList();
			if (result.size() == 1) {

				List<AgencyVO> dresult = session.createQuery("from AgencyVO where agency_code = ?1")
						.setParameter(1, agencyCode).getResultList();
				if (dresult.size() == 1) {
					agencyVO = dresult.get(0);
					for (AgencyDO acdo : result) {
						tx = session.beginTransaction();
						agencyDO = session.get(AgencyDO.class, new Long(agencyCode));
						if (agencyCode == acdo.getAgencyCode()) {
							agencyDO.setPassCode(PasswordUtil.encryptPasscode(newPassword));
							session.update(agencyDO);
							tx.commit();
						}
					}
				}
			}
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> AgencyPersistenceManager --> updatePassword  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return agencyVO;
	}

	// change password
	@SuppressWarnings("unchecked")
	public AgencyVO changePassword(long agencyCode, String newPassword, String oldPassword) {
		AgencyVO agencyVO = new AgencyVO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		AgencyDO agencyDO;
		Query<AgencyDO> query = session.createQuery("from AgencyDO where agencyCode = ?1 and status = ?2 ");
		query.setParameter(1, agencyCode);
		query.setParameter(2, 1);
		try {
			String dealerOldPwdEncrypt = PasswordUtil.encryptPasscode(oldPassword);
			System.out.println("DEALER PASSWORD IS:" + dealerOldPwdEncrypt);

			List<AgencyDO> result = query.getResultList();
			if (result.size() == 1) {
				List<AgencyVO> dresult = session.createQuery("from AgencyVO where agency_code = ?1")
						.setParameter(1, agencyCode).getResultList();
				if (dresult.size() == 1) {
					for (AgencyDO aDO : result) {
						if (dealerOldPwdEncrypt.equals(aDO.getPassCode())) {
							agencyVO = dresult.get(0);
							for (AgencyDO acdo : result) {
								tx = session.beginTransaction();
								agencyDO = session.get(AgencyDO.class, new Long(agencyCode));
								if (agencyCode == acdo.getAgencyCode()) {
									agencyDO.setPassCode(PasswordUtil.encryptPasscode(newPassword));
									session.update(agencyDO);
									tx.commit();
								}
							}
						} else
							throw new BusinessException(
									"INVALID OLD PASSWORD /NEW  PASSWORD /CONFIRM PASSWORD.PLEASE CHECK IT ONCE AND TRY AGAIN");
					}
				}
			}
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> AgencyPersistenceManager --> changePassword  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return agencyVO;
	}

	public AgencyVO createAgencyData(AgencySerialNosDO agencySerialNosDO, String fy, String tarOb, String starOb, String cashb,
			String ed, String ujwob) throws BusinessException {
		AgencyVO agencyVO = new AgencyVO();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(agencySerialNosDO);
			AgencyDO aDO = session.get(AgencyDO.class, new Long(agencySerialNosDO.getCreatedBy()));
			aDO.setIsFirstTimeLogin(1);

			AgencyDetailsDO adDO = session.get(AgencyDetailsDO.class, new Long(agencySerialNosDO.getCreatedBy()));
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			long eddl = (df.parse(ed)).getTime();
			long dedate = eddl - 86400000;
			System.out.println("dedate: " + dedate);
			adDO.setEffectiveDate(eddl);
			adDO.setDayendDate(dedate);
			adDO.setLastTrxnDate(dedate);
			adDO.setSi_sno(agencySerialNosDO.getSiSno());
			adDO.setCs_sno(agencySerialNosDO.getCsSno());
			adDO.setFtl_fy(Integer.parseInt(fy));
			
			session.update(aDO);
			session.update(adDO);

			@SuppressWarnings("unchecked")
			Query<BankDataDO> query = session.createQuery("from BankDataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencySerialNosDO.getCreatedBy());
			query.setParameter(2, 0);
			List<BankDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (BankDataDO bankDataDO : result) {
					if (bankDataDO.getBank_code().equalsIgnoreCase("TAR ACCOUNT")) {
						bankDataDO.setAcc_ob(tarOb);
						bankDataDO.setAcc_cb(tarOb);
						session.update(bankDataDO);
					} else if (bankDataDO.getBank_code().equalsIgnoreCase("STAR ACCOUNT")) {
						bankDataDO.setAcc_ob(starOb);
						bankDataDO.setAcc_cb(starOb);
						session.update(bankDataDO);
					}
				}
			}

			AgencyCashDataDO cashDO = new AgencyCashDataDO();
			cashDO.setCash_balance(cashb);
			cashDO.setCash_amount(cashb);
			cashDO.setT_date(dedate);
			cashDO.setTrans_type(0);
			cashDO.setInv_no("NA");
			cashDO.setCreated_by(agencySerialNosDO.getCreatedBy());
			cashDO.setCreated_date(System.currentTimeMillis());
			cashDO.setVersion(1);
			cashDO.setDeleted(0);
			session.save(cashDO);

			CVODataDO cvoDO = new CVODataDO();
			cvoDO.setId(1);
			cvoDO.setCvo_cat(1);
			cvoDO.setCvo_name("UJWALA");
			cvoDO.setIs_gst_reg(2);
			cvoDO.setCvo_tin("NA");// not mandtry
			cvoDO.setCvo_pan("NA");// not mandtry
			cvoDO.setCvo_address("NA");
			cvoDO.setCvo_contact("NA");
			cvoDO.setCvo_email("NA");// not mandtry
			/*cvoDO.setObal(ujwob);
			cvoDO.setCbal(ujwob);*/
			cvoDO.setObal("0.00");
			cvoDO.setCbal("0.00");
			cvoDO.setEbal("0");
			cvoDO.setCreated_by(agencySerialNosDO.getCreatedBy());
			cvoDO.setCreated_date(System.currentTimeMillis());
			cvoDO.setVersion(1);
			cvoDO.setDeleted(0);
			session.save(cvoDO);

			AgencyCVOBalanceDataDO cvoBalDO = new AgencyCVOBalanceDataDO();
			cvoBalDO.setId(1);
			cvoBalDO.setInv_ref_no("NA");
			cvoBalDO.setInv_date(eddl);
			cvoBalDO.setTrans_type(0);
			cvoBalDO.setCvo_cat(1);
			cvoBalDO.setCvo_refid(cvoDO.getId());
			cvoBalDO.setRef_id(0);
			cvoBalDO.setCvoflag(0);
			cvoBalDO.setAmount(ujwob);
			cvoBalDO.setCbal_amount(ujwob);
			cvoBalDO.setCreated_by(agencySerialNosDO.getCreatedBy());
			cvoBalDO.setCreated_date(System.currentTimeMillis());
			cvoBalDO.setVersion(1);
			cvoBalDO.setDeleted(0);
			cvoBalDO.setDiscount("NA");
			session.save(cvoBalDO);

			@SuppressWarnings("unchecked")
			List<AgencyVO> dresult = session.createQuery("from AgencyVO where agency_code = ?1")
					.setParameter(1, agencySerialNosDO.getCreatedBy()).getResultList();
			if (dresult.size() == 1) {
				agencyVO = dresult.get(0);
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> AgencyPersistenceManager --> createAgencyData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException("UNABLE TO SAVE AGENCY DATA");
		} finally {
			session.clear();
			session.close();
		}
		return agencyVO;
	}

	public AgencyVO validateLogin(long agencyId, String pwd) throws BusinessException {
		AgencyVO agencyVO = new AgencyVO();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<AgencyDO> query = session
					.createQuery("from AgencyDO where agencyCode = ?1 and passCode = ?2 and status = ?3 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, PasswordUtil.encryptPasscode(pwd));
			System.out.println("pwd:" + PasswordUtil.encryptPasscode(pwd));
			query.setParameter(3, 1);
			List<AgencyDO> result = query.getResultList();
			if (result.size() == 1) {
				@SuppressWarnings("unchecked")
				List<AgencyVO> dresult = session.createQuery("from AgencyVO where agency_code = ?1")
						.setParameter(1, agencyId).getResultList();
				if (dresult.size() == 1) {
					agencyVO = dresult.get(0);
				} else {
					throw new BusinessException("INVALID AGENCY ID / PASSWORD / ACCOUNT NOT ACTIVE");
				}

			} else {
				throw new BusinessException("INVALID AGENCY ID / PASSWORD / ACCOUNT NOT ACTIVE");
			}
		} catch (BusinessException be) {
			logger.error(be);
			be.printStackTrace();
			throw new BusinessException("INVALID AGENCY ID / PASSWORD / ACCOUNT NOT ACTIVE");
		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException("INVALID AGENCY ID / PASSWORD / ACCOUNT NOT ACTIVE");
		}
		return agencyVO;
	}

	public AgencyVO fetchAgencyDetails(long agencyId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		AgencyVO agencyVO = new AgencyVO();
		@SuppressWarnings("unchecked")
		List<AgencyVO> dresult = session.createQuery("from AgencyVO where agency_code = ?1").setParameter(1, agencyId)
				.getResultList();
		if (dresult.size() == 1) {
			agencyVO = dresult.get(0);
		}
		return agencyVO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> fetchHomePageDetails(long agencyId) throws BusinessException {
		Map<String, Object> details = new HashMap<>();
		String cashbal = null;
		String tarcb = null;
		String starcb = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Query<AgencyCashDataDO> cihqry = session.createQuery(
					"select cash_balance from AgencyCashDataDO where created_by = ?1 and deleted = ?2 and trans_type != ?3 order by t_date desc, created_date desc")
					.setMaxResults(1);
			cihqry.setParameter(1, agencyId);
			cihqry.setParameter(2, 0);
			cihqry.setParameter(3, 11);

			List cihresult = cihqry.getResultList();
			if (cihresult.size() > 0) {
				cashbal = (String) cihresult.get(0);
			}
			if (cashbal != null)
				details.put("cashBal", cashbal);

			Query<BankDataDO> query = session.createQuery("from BankDataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<BankDataDO> bankdatares = query.getResultList();
			if (bankdatares.size() > 0) {
				for (BankDataDO bankDataDO : bankdatares) {
					if (bankDataDO.getBank_code().equalsIgnoreCase("TAR ACCOUNT")) {
						tarcb = bankDataDO.getAcc_cb();
					} else if (bankDataDO.getBank_code().equalsIgnoreCase("STAR ACCOUNT")) {
						starcb = bankDataDO.getAcc_cb();
					}
				}
			}
			if (tarcb != null)
				details.put("tarcb", tarcb);
			if (starcb != null)
				details.put("starcb", starcb);

			double custtotal = 0.00;
			double ventotal = 0.00;
			Query<CVODataDO> cvoqry = session.createQuery("from CVODataDO where created_by = ?1 and deleted = ?2 ");
			cvoqry.setParameter(1, agencyId);
			cvoqry.setParameter(2, 0);
			List<CVODataDO> cvores = cvoqry.getResultList();
			if (cvores.size() > 0) {
				for (CVODataDO cvodo : cvores) {
					if ((cvodo.getCvo_cat() == 1)) {
						custtotal += new Double(cvodo.getCbal());
					} else if ((cvodo.getCvo_cat() == 0) || (cvodo.getCvo_cat() == 3)) {
						ventotal += new Double(cvodo.getCbal());
					}
				}
			}
			details.put("dtrs_Rcbls", custtotal);
			details.put("ctrs_Pbls", ventotal);

		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
		return details;
	}
}