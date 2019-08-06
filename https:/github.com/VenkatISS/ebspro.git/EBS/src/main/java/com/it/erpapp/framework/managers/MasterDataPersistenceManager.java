package com.it.erpapp.framework.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.framework.model.AgencyCashDataDO;
import com.it.erpapp.framework.model.BankDataDO;
import com.it.erpapp.framework.model.CVODataDO;
import com.it.erpapp.framework.model.CreditLimitsDataDO;
import com.it.erpapp.framework.model.ExpenditureDataDO;
import com.it.erpapp.framework.model.FleetDataDO;
import com.it.erpapp.framework.model.StaffDataDO;
import com.it.erpapp.framework.model.StatutoryDataDO;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

public class MasterDataPersistenceManager {

	Logger logger = Logger.getLogger(MasterDataPersistenceManager.class.getName());

	public List<StatutoryDataDO> getAgencyStatutoryData(long agencyId) throws BusinessException {
		List<StatutoryDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<StatutoryDataDO> query = session.createQuery("from StatutoryDataDO where created_by = ?1 and deleted = ?2");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<StatutoryDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (StatutoryDataDO statutoryDataDO : result) {
					doList.add(statutoryDataDO);
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

	public void saveAgencyStatutoryData(List<StatutoryDataDO> doList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (StatutoryDataDO statutoryDataDO : doList) {
				session.save(statutoryDataDO);
			}
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> saveAgencyStatutoryData  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}

	public void deleteAgencyStatutoryData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			StatutoryDataDO sddo = session.get(StatutoryDataDO.class, new Long(itemId));
			sddo.setDeleted(1);
			session.update(sddo);
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> deleteAgencyStatutoryData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}

	//Staff Data
	public List<StaffDataDO> getAgencyStaffData(long agencyId) throws BusinessException {
		List<StaffDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<StaffDataDO> query = session.createQuery("from StaffDataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<StaffDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (StaffDataDO staffDataDO : result) {
					doList.add(staffDataDO);
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
	
	//Staff Data
		public List<StaffDataDO> getAgencyAllStaffData(long agencyId) throws BusinessException {
			List<StaffDataDO> doList = new ArrayList<>();
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				@SuppressWarnings("unchecked")
				Query<StaffDataDO> query = session.createQuery("from StaffDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?3) ");
				query.setParameter(1, agencyId);
				query.setParameter(2, 0);
				query.setParameter(3, 1);
				List<StaffDataDO> result = query.getResultList(); 
				if(result.size()>0) {
					for (StaffDataDO staffDataDO : result) {
						doList.add(staffDataDO);
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
		public void saveAgencyStaffData(List<StaffDataDO> doList) throws BusinessException {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				for (StaffDataDO staffDataDO : doList) {
					String ec = staffDataDO.getEmp_code();
					Query<StaffDataDO> query = session.createQuery("from StaffDataDO where created_by = ?1 and deleted = ?2 and emp_code="+"'"+ec+"'");
					query.setParameter(1, staffDataDO.getCreated_by());
					query.setParameter(2, 1);
					List<StaffDataDO> result = query.getResultList(); 
					if(result.size()>0) {
						for (StaffDataDO staffDataDO2 : result) {
							if((staffDataDO.getEmp_code()).equalsIgnoreCase((staffDataDO2.getEmp_code()))){
								session.clear();
								staffDataDO.setId(staffDataDO2.getId());
								staffDataDO.setDeleted(0);
								staffDataDO.setCreated_date(System.currentTimeMillis());
								session.update(staffDataDO);
							}else{
								session.save(staffDataDO);
							}
						}
					}else{
						session.save(staffDataDO);
					}
				}
				tx.commit();
			}catch(Exception e) {
				try {
					if (tx != null) tx.rollback();
				}catch (HibernateException e1) {
					logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> saveAgencyStaffData  is not succesful");
				}
				e.printStackTrace();
				logger.error(e);
				throw new BusinessException(e.getMessage());
			}finally {
				session.clear();
				session.close();
			}
		}


	public void deleteAgencyStaffData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			StaffDataDO sddo = session.get(StaffDataDO.class, new Long(itemId));
			sddo.setDeleted(1);
			session.update(sddo);
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> deleteAgencyStaffData  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}

	//Fleet Data
	public List<FleetDataDO> getAgencyFleetData(long agencyId) throws BusinessException {
		List<FleetDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<FleetDataDO> query = session.createQuery("from FleetDataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<FleetDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (FleetDataDO fleetDataDO : result) {
					doList.add(fleetDataDO);
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
	//Fleet Data
		public List<FleetDataDO> getAgencyAllFleetData(long agencyId) throws BusinessException {
			List<FleetDataDO> doList = new ArrayList<>();
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				@SuppressWarnings("unchecked")
				Query<FleetDataDO> query = session.createQuery("from FleetDataDO where created_by = ?1 and (deleted = ?2  or deleted = ?3)");
				query.setParameter(1, agencyId);
				query.setParameter(2, 0);
				query.setParameter(3, 1);
				List<FleetDataDO> result = query.getResultList(); 
				if(result.size()>0) {
					for (FleetDataDO fleetDataDO : result) {
						doList.add(fleetDataDO);
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
		public void saveAgencyFleetData(List<FleetDataDO> doList) throws BusinessException {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				for (FleetDataDO fleetDataDO : doList) {
					String vn = fleetDataDO.getVehicle_no();
					Query<FleetDataDO> query = session.createQuery("from FleetDataDO where created_by = ?1 and deleted = ?2 and vehicle_no="+"'"+vn+"'");
					query.setParameter(1, fleetDataDO.getCreated_by());
					query.setParameter(2, 1);
					List<FleetDataDO> result = query.getResultList(); 
					if(result.size()>0) {
						for (FleetDataDO fleetDataDO2 : result) {
							if((fleetDataDO.getVehicle_no()).equalsIgnoreCase((fleetDataDO2.getVehicle_no()))){
								session.clear();
								fleetDataDO.setId(fleetDataDO2.getId());
								fleetDataDO.setDeleted(0);
								fleetDataDO.setCreated_date(System.currentTimeMillis());
								session.update(fleetDataDO);
							}else{
								session.save(fleetDataDO);
							}
						}
					}else{
						session.save(fleetDataDO);
					}
				}
				tx.commit();
			}catch(Exception e) {
				try {
					if (tx != null) tx.rollback();
				}catch (HibernateException e1) {
					logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> saveAgencyFleetData  is not succesful");
				}
				e.printStackTrace();
				logger.error(e);
				throw new BusinessException(e.getMessage());
			}finally {
				session.clear();
				session.close();
			}
		}


	public void deleteAgencyFleetData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			FleetDataDO fddo = session.get(FleetDataDO.class, new Long(itemId));
			fddo.setDeleted(1);
			session.update(fddo);
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> deleteAgencyFleetData is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}

	
	// Ledger Report Based OPENINGBALANCE Based On Bank
	
			@SuppressWarnings("unchecked")
			public String getOpeningBalanceByBank(long agencyId,long  fromDate, long bId) throws BusinessException {
				String opbal = "0";
				Session session = HibernateUtil.getSessionFactory().openSession();
				try {
					Query<BankTranxsDataDO> query = session.createQuery("from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id = ?3 "
							+ "  and bt_date < ?4 order by bt_date desc, created_date desc").setMaxResults(1);
					query.setParameter(1, agencyId);
					query.setParameter(2, 0);
					query.setParameter(3, bId);
					query.setParameter(4, fromDate);

					List<BankTranxsDataDO> result = query.getResultList();
					if(result.size()>0) {
						for (BankTranxsDataDO dataDO : result) {
							opbal =dataDO.getBank_acb();
						}
					}else {
						Query<BankTranxsDataDO> dquery = session.createQuery("from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id = ?3 "
								+ "  and bt_date >= ?4 order by bt_date asc,created_date").setMaxResults(1);
						dquery.setParameter(1, agencyId);
						dquery.setParameter(2, 0);
						dquery.setParameter(3, bId);
						dquery.setParameter(4, fromDate);

						result = dquery.getResultList();
						if(result.size()>0) {
							for (BankTranxsDataDO dataDO : result) {
								//BigDecimal btransamount=new BigDecimal(dataDO.getTrans_amount());
								double dbacbb =Double.valueOf(dataDO.getBank_acb());
								double dbatrcb =Double.valueOf(dataDO.getTrans_amount());
						    //	opbal =String.valueOf(dbacbb+dbatrcb);
								
								if((dataDO.getBtflag()==1 && (dataDO.getTrans_type()==2 || dataDO.getTrans_type()==4))|| dataDO.getBtflag() == 4
										|| (dataDO.getBtflag()==5 && dataDO.getTrans_type()==2)|| dataDO.getBtflag()==7 || dataDO.getBtflag()==8
										|| (dataDO.getBtflag()==10 && dataDO.getTrans_type()==2) || (dataDO.getBtflag()==13 && dataDO.getTrans_type()==2) 
										|| dataDO.getBtflag()==14 || dataDO.getBtflag()==15) {

									opbal =String.valueOf(dbacbb-dbatrcb);
								}else if((dataDO.getBtflag()==1 && (dataDO.getTrans_type()==1 || dataDO.getTrans_type()==3)) || dataDO.getBtflag()==3 ||
                                        (dataDO.getBtflag()==5 && dataDO.getTrans_type()==1)|| dataDO.getBtflag()==6 || (dataDO.getBtflag()==9) ||
                                        (dataDO.getBtflag()==10 && dataDO.getTrans_type()==1) || (dataDO.getBtflag()==11 && dataDO.getTrans_type()==1)
                                        || dataDO.getBtflag()==12 || (dataDO.getBtflag()==13 && dataDO.getTrans_type()==1) || dataDO.getBtflag()==16){
									opbal =String.valueOf(dbacbb+dbatrcb);
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
				return opbal;
			}

	//Bank Data
	public List<BankDataDO> getAgencyBankData(long agencyId) throws BusinessException {
		List<BankDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<BankDataDO> query = session.createQuery("from BankDataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<BankDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (BankDataDO bankDataDO : result) {
					doList.add(bankDataDO);
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
	
	//Bank Data
		public List<BankDataDO> getAgencyAllBankData(long agencyId) throws BusinessException {
			List<BankDataDO> doList = new ArrayList<>();
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				@SuppressWarnings("unchecked")
				Query<BankDataDO> query = session.createQuery("from BankDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?3) ");
				query.setParameter(1, agencyId);
				query.setParameter(2, 0);
				query.setParameter(3, 1);
				List<BankDataDO> result = query.getResultList(); 
				if(result.size()>0) {
					for (BankDataDO bankDataDO : result) {
						doList.add(bankDataDO);
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
		public void saveAgencyBankData(List<BankDataDO> doList) throws BusinessException {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				for (BankDataDO bankDataDO : doList) {
					String acn = bankDataDO.getBank_acc_no();
					Query<BankDataDO> query = session.createQuery("from BankDataDO where created_by = ?1 and deleted = ?2 and bank_acc_no="+"'"+acn+"'");
					query.setParameter(1, bankDataDO.getCreated_by());
					query.setParameter(2, 1);
					List<BankDataDO> result = query.getResultList(); 
					if(result.size()>0) {
						for (BankDataDO bankDataDO2 : result) {
							if((bankDataDO.getBank_acc_no()).equalsIgnoreCase((bankDataDO2.getBank_acc_no()))){
								session.clear();
								bankDataDO.setId(bankDataDO2.getId());
								bankDataDO.setDeleted(0);
								bankDataDO.setCreated_date(System.currentTimeMillis());
								session.update(bankDataDO);
							}else{
								session.save(bankDataDO);
							}
						}
					}else{
						session.save(bankDataDO);
					}
				}
				tx.commit();
			}catch(Exception e) {
				try {
					if (tx != null) tx.rollback();
				}catch (HibernateException e1) {
					logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> saveAgencyBankData is not succesful");
				}
				logger.error(e);
				e.printStackTrace();
				throw new BusinessException(e.getMessage());
			}finally {
				session.clear();
				session.close();
			}
		}

	public void deleteAgencyBankData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			BankDataDO bddo = session.get(BankDataDO.class, new Long(itemId));
			bddo.setDeleted(1);
			session.update(bddo);
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> deleteAgencyBankData is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}

	//updateAgencyBankData
    @SuppressWarnings("unchecked")
    public void updateAgencyBankData(List<BankDataDO> doList) throws BusinessException {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                    tx = session.beginTransaction();
                    for (BankDataDO bankDataDO : doList) {
                            long bankId=bankDataDO.getId();
                            Query<BankDataDO> query = session.createQuery("from BankDataDO where created_by = ?1 and deleted = ?2 and id=?3");
                            query.setParameter(1, bankDataDO.getCreated_by());
                            query.setParameter(2, 0);
                            query.setParameter(3, bankId);
                            List<BankDataDO> result = query.getResultList();
                            if(result.size()>0) {
                                    for (BankDataDO bankDataDO2 : result) {
                                            Query<BankDataDO> updateQuery = session.createQuery("update BankDataDO bdo set bdo.bank_branch = :bankBranch,bdo.bank_ifsc_code= :bankIfscCode,bdo.bank_addr = :bankAddr,"
                                                            + "bdo.modified_by = :modifiedBy,bdo.modified_date = :modifiedDate where bdo.created_by = :createdBy and bdo.deleted = :bank_deleted  and bdo.id = :bank_id");


                                            //updateQuery.setParameter("cvoCat", cvodo.getCvo_cat());
                                            updateQuery.setParameter("bankBranch", bankDataDO.getBank_branch());
                                            updateQuery.setParameter("bankIfscCode", bankDataDO.getBank_ifsc_code());
                                            updateQuery.setParameter("bankAddr", bankDataDO.getBank_addr());
                                            updateQuery.setParameter("modifiedDate", bankDataDO.getModified_date());
                                            updateQuery.setParameter("createdBy", bankDataDO.getCreated_by());
                                            updateQuery.setParameter("modifiedBy", bankDataDO.getCreated_by());
                                            updateQuery.setParameter("bank_deleted", bankDataDO.getDeleted());
                                            updateQuery.setParameter("bank_id", bankDataDO.getId());
                                            updateQuery.executeUpdate();
                                    }

                            }


            }
                    tx.commit();
            }
            catch(Exception e) {
                    try {
                            if (tx != null) tx.rollback();
                    }catch (HibernateException e1) {
                            logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> saveAgencyBankData is not succesful");
                    }
                    logger.error(e);
                    e.printStackTrace();
                    throw new BusinessException(e.getMessage());
            }finally {
                    session.clear();
                    session.close();
            }
    }
	//CVO Data
	public List<CVODataDO> getAgencyCVOData(long agencyId) throws BusinessException {
		List<CVODataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<CVODataDO> query = session.createQuery("from CVODataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<CVODataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (CVODataDO cvodo : result) {
					doList.add(cvodo);
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
	
	//CVO Data
		public List<CVODataDO> getAgencyAllCVOData(long agencyId) throws BusinessException {
			List<CVODataDO> doList = new ArrayList<>();
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				@SuppressWarnings("unchecked")
				Query<CVODataDO> query = session.createQuery("from CVODataDO where created_by = ?1");
				query.setParameter(1, agencyId);
				List<CVODataDO> result = query.getResultList(); 
				if(result.size()>0) {
					for (CVODataDO cvodo : result) {
						doList.add(cvodo);
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
		public void saveAgencyCVOData(List<CVODataDO> doList,
				List<AgencyCVOBalanceDataDO> acvoDOList, String effdate) throws BusinessException {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
	            List<Long> list = new ArrayList<>();
	            Map<Long,Integer> map =new HashMap<>();
	            
				for (CVODataDO cvodo : doList) {
					if(cvodo.getIs_gst_reg()==1){
						String gstin = cvodo.getCvo_tin();
						Query<CVODataDO> query = session.createQuery("from CVODataDO where created_by = ?1 and deleted = ?2 and cvo_tin="+"'"+gstin+"'");
						query.setParameter(1, cvodo.getCreated_by());
						query.setParameter(2, 1);
						List<CVODataDO> result = query.getResultList(); 
						if(result.size()>0) {
							for (CVODataDO cvodo2 : result) {
								if((cvodo.getCvo_tin()).equalsIgnoreCase((cvodo2.getCvo_tin()))){
									session.clear();
									cvodo.setId(cvodo2.getId());
									cvodo.setDeleted(0);
									cvodo.setCreated_date(System.currentTimeMillis());
									session.update(cvodo);

									map.put(cvodo.getId(), 1);
		                            list.add(cvodo.getId());
									
								}else{
									list.add((Long) session.save(cvodo));
		                            map.put(cvodo.getId(), 0);
								}
							}
						}else{
							list.add((Long) session.save(cvodo));
		                    map.put(cvodo.getId(), 0);
						}
					}else{
						list.add((Long) session.save(cvodo));
	                    map.put(cvodo.getId(), 0);
					}
				}
				
				int count=0;
				for (AgencyCVOBalanceDataDO cvobaldo : acvoDOList) {
					cvobaldo.setCvo_refid(list.get(count));
					cvobaldo.setRef_id(0);
					cvobaldo.setCvoflag(0);
					cvobaldo.setInv_date(Long.parseLong(effdate));
					if(map.get(cvobaldo.getCvo_refid())==0){
						session.save(cvobaldo);	
					}
					++count;
		       	}
				
				tx.commit();
								
			}catch(Exception e) {
				try {
					if (tx != null) tx.rollback();
				}catch (HibernateException e1) {
					logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> saveAgencyCVOData is not succesful");
				}
				e.printStackTrace();
				logger.error(e);
				throw new BusinessException(e.getMessage());
			}finally {
				session.clear();
				session.close();
			}
		}

	public void deleteAgencyCVOData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			CVODataDO cvodo = session.get(CVODataDO.class, new Long(itemId));
			cvodo.setDeleted(1);
			session.update(cvodo);
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> deleteAgencyCVOData is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}

	//update cvo

    @SuppressWarnings("unchecked")
    public void updateAgencyCVOData(List<CVODataDO> doList,
                    List<AgencyCVOBalanceDataDO> acvoDOList, String effdate) throws BusinessException {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                    tx = session.beginTransaction();
        List<Long> list = new ArrayList<>();
        Map<Long,Integer> map =new HashMap<>();

                    for (CVODataDO cvodo : doList) {
                            if(cvodo.getIs_gst_reg()==1 || cvodo.getIs_gst_reg()==2){
                                    String gstin = cvodo.getCvo_tin();
                                    long id=cvodo.getId();
                                    Query<CVODataDO> query = session.createQuery("from CVODataDO where created_by = ?1 and deleted = ?2  and id=?3");
                                    query.setParameter(1, cvodo.getCreated_by());
                                    query.setParameter(2, 0);
                                    query.setParameter(3, cvodo.getId());
                                    List<CVODataDO> result = query.getResultList();
                                    if(result.size()>0) {
                                            for (CVODataDO cvodo2 : result) {
                                                            //cvodo.setId(cvodo2.getId());

                                                    Query<CVODataDO> updateQuery = session.createQuery("update CVODataDO cvo set cvo.cvo_address = :cvoAddress,cvo.cvo_name= :cvoName,cvo.is_gst_reg = :isGstReg,"
                                                                    + "cvo.cvo_tin = :cvoTin,cvo.cvo_email = :cvoEmai,cvo.cvo_pan = :cvoPan,cvo.modified_by = :modifiedBy,"
                                                                    + "cvo.modified_date = :modifiedDate,cvo.cvo_contact=:cvoContact where cvo.created_by = :createdBy and cvo.deleted = :cvo_deleted  and cvo.id = :cvo_id");

                                                    //updateQuery.setParameter("cvoCat", cvodo.getCvo_cat());
                                                    updateQuery.setParameter("cvoName", cvodo.getCvo_name());
                                                    updateQuery.setParameter("cvoAddress", cvodo.getCvo_address());
                                                    updateQuery.setParameter("isGstReg", cvodo.getIs_gst_reg());
                                                    updateQuery.setParameter("cvoTin", cvodo.getCvo_tin());
                                                    updateQuery.setParameter("cvoEmai", cvodo.getCvo_email());
                                                    updateQuery.setParameter("cvoPan", cvodo.getCvo_pan());
                                                    updateQuery.setParameter("modifiedDate", cvodo.getModified_date());
                                                    updateQuery.setParameter("cvoContact", cvodo.getCvo_contact());
                                                    updateQuery.setParameter("createdBy", cvodo.getCreated_by());
                                                    updateQuery.setParameter("modifiedBy", cvodo.getCreated_by());
                                                    updateQuery.setParameter("cvo_deleted", cvodo.getDeleted());
                                                    updateQuery.setParameter("cvo_id", cvodo.getId());
                                                    updateQuery.executeUpdate();

                                                    /*List<CVODataDO> res=updateQuery.getResultList();
                                                    for(CVODataDO cvo:res) {
                                                            System.out.println("address is:"+cvo.getCvo_address());
                                                    }*/

                                                    }
                                    }
                            }
                                    }

                    tx.commit();

            }catch(Exception e) {
                    try {
                            if (tx != null) tx.rollback();
                    }catch (HibernateException e1) {
                            logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> saveAgencyCVOData is not succesful");
                    }
                    e.printStackTrace();
                    logger.error(e);
                    throw new BusinessException(e.getMessage());
            }finally {
                    session.clear();
                    session.close();
            }
    }
	//Expenditure Data
	public List<ExpenditureDataDO> getAgencyExpenditureData(long agencyId) throws BusinessException {
		List<ExpenditureDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<ExpenditureDataDO> query = session.createQuery("from ExpenditureDataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<ExpenditureDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (ExpenditureDataDO edo : result) {
					doList.add(edo);
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

	public void saveAgencyExpenditureData(List<ExpenditureDataDO> doList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (ExpenditureDataDO edo : doList) {
				session.save(edo);
			}
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> saveAgencyExpenditureData is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}

	public void deleteAgencyExpenditureData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ExpenditureDataDO dataDO = session.get(ExpenditureDataDO.class, new Long(itemId));
			dataDO.setDeleted(1);
			session.update(dataDO);
			tx.commit();
		}catch(Exception e) {
			try {
				if(tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> deleteAgencyExpenditureData is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}

	//Credit Limits Data
	public List<CreditLimitsDataDO> getAgencyCreditLimitsData(long agencyId) throws BusinessException {
		List<CreditLimitsDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<CreditLimitsDataDO> query = session.createQuery("from CreditLimitsDataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<CreditLimitsDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (CreditLimitsDataDO dataDO : result) {
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

	public void saveAgencyCreditLimitsData(List<CreditLimitsDataDO> doList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (CreditLimitsDataDO edo : doList) {
				session.save(edo);
			}
			tx.commit();
		}catch(Exception e) {
			try {
				if(tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> saveAgencyCreditLimitsData is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}

	public void deleteAgencyCreditLimitsData(long itemId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			CreditLimitsDataDO dataDO = session.get(CreditLimitsDataDO.class, new Long(itemId));
			dataDO.setDeleted(1);
			session.update(dataDO);
			tx.commit();
		}catch(Exception e) {
			try {
				if(tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> deleteAgencyCreditLimitsData is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void updateCreditlimitsData(Map<String, String> requestparams,long agencyId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		
		 String cid=requestparams.get("cid");
		 String dis19kgndne=requestparams.get("dis19kgndne");
		 String dis19kgcgas=requestparams.get("dis19kgcgas");
		 String dis35kgndne=requestparams.get("dis35kgndne");
		 String dis47_5kgndne=requestparams.get("dis47_5kgndne");
		 String dis450kgsumo=requestparams.get("dis450kgsumo");

		try {
			tx = session.beginTransaction();
			Query query1 = session.createQuery(
					"UPDATE CreditLimitsDataDO SET disc_19kg_ndne='"+dis19kgndne+"', disc_19kg_cutting_gas='"+dis19kgcgas+"',disc_35kg_ndne='"+dis35kgndne+"',disc_47_5kg_ndne='"+dis47_5kgndne+"', disc_450kg_sumo='"+dis450kgsumo+"' WHERE created_by='"+agencyId+"' AND cust_id='"+Long.parseLong(cid)+"'");
			query1.executeUpdate();
			tx.commit();
		}catch(Exception e) {
			try {
				if(tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> updateCreditlimitsData is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}
	
/*------------------ Start Opening Balance Method 06052019 -----------------------------------------*/
	
    //fetch opening balance data
	
	         //Bank
	public List<BankDataDO> getAgencyBankOpeningBalanceData(long agencyId) throws BusinessException {
		List<BankDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<BankDataDO> query = session.createQuery("from BankDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?3) order by created_date asc ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 1);
			List<BankDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (BankDataDO bankDataDO : result) {
					doList.add(bankDataDO);
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

	          //Cash
	public List<AgencyCashDataDO> getAgencyCashOpeningBalanceData(long agencyId) throws BusinessException {
		List<AgencyCashDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			/*Query<AgencyCashDataDO> query = session.createQuery("from AgencyCashDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?3) ").setMaxResults(1);
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 1);
			List<AgencyCashDataDO> result = query.getResultList(); 
			if(result.size()>0) {
				for (AgencyCashDataDO cashDataDO : result) {
					doList.add(cashDataDO);
				}
			}*/
			
			Query<AgencyCashDataDO> query1 = session.createQuery("from AgencyCashDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?3) order by created_date desc ");
			query1.setParameter(1, agencyId);
			query1.setParameter(2, 0);
			query1.setParameter(3, 1);
			List<AgencyCashDataDO> result1= query1.getResultList(); 
			if(result1.size()>0) {
				for (AgencyCashDataDO cashDataDO : result1) {
					doList.add(cashDataDO);
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

	//UJWALA
	
	public List<CVODataDO> getAgencyUjwalaOpeningBalanceData(long agencyId) throws BusinessException {
		List<CVODataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<CVODataDO> cvoquery1 = session.createQuery("from CVODataDO where created_by = ?1 and (deleted = ?2 or deleted = ?3) order by created_date asc ").setMaxResults(1);
			cvoquery1.setParameter(1, agencyId);
			cvoquery1.setParameter(2, 0);
			cvoquery1.setParameter(3, 1);
			List<CVODataDO> cvoresult = cvoquery1.getResultList(); 
			if(cvoresult.size()>0) {
				for (CVODataDO cvoDataDO : cvoresult) {
					doList.add(cvoDataDO);
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

	
    //UPDATE opening balance data
	
	@SuppressWarnings("rawtypes")
	public void updateOpeningBalanceData(Map<String, String> requestparams,long agencyId) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		
		 long id=Long.parseLong(requestparams.get("bId"));
		 String balamt=requestparams.get("bamount");
		 String bname=requestparams.get("bname");

		 BigDecimal bamt = new BigDecimal(balamt);
			
		try {
			tx = session.beginTransaction();
			
			// update balance code to bank data
			
			Query<BankDataDO> bquery = session.createQuery("from BankDataDO where created_by = ?1 and deleted = ?2 and id = ?3 order by created_date asc ");
			bquery.setParameter(1, agencyId);
			bquery.setParameter(2, 0);
			bquery.setParameter(3, id);

			List<BankDataDO> bresult = bquery.getResultList(); 
			
		//	if(bresult.get(0).getBank_code().equals(bname)){
				
			if(bresult.size()>0 && (bname.equals("TAR ACCOUNT") || bname.equals("STAR ACCOUNT"))) {
			
			BigDecimal cashbalance = new BigDecimal(bresult.get(0).getAcc_cb());
			
			Query cquery1 = session.createQuery("update BankDataDO ac set ac.acc_ob = :aob, ac.acc_cb = :acb"
					+ " where ac.created_by = :cid  and  ac.id = :id");
			cquery1.setParameter("aob", bamt.toString());
			cquery1.setParameter("acb", cashbalance.add(bamt).toString());
			cquery1.setParameter("cid", agencyId);
			cquery1.setParameter("id", id);
			cquery1.executeUpdate();
			
			
			Query<BankTranxsDataDO> bquery1 = session
					.createQuery("from BankTranxsDataDO where created_by = ?1 and deleted = ?2 and bank_id= ?3 order by bt_date,id asc");
			bquery1.setParameter(1, agencyId);
			bquery1.setParameter(2, 0);
			bquery1.setParameter(3, id);

			List<BankTranxsDataDO> bresult1 = bquery1.getResultList();
		
			if ((!bresult1.isEmpty())) {
				
				for(BankTranxsDataDO btd:bresult1){
					
			        BigDecimal btbalance = new BigDecimal(btd.getBank_acb());
			
					
					Query cquery2 = session.createQuery("update BankTranxsDataDO ac set ac.bank_acb = :acb"
							+ " where ac.created_by = :cid and ac.id= :btrid and ac.bank_id = :bid");
					cquery2.setParameter("acb", btbalance.add(bamt).toString());
					cquery2.setParameter("cid", agencyId);
					cquery2.setParameter("btrid", btd.getId());
					cquery2.setParameter("bid", id);
					cquery2.executeUpdate();

	    		 }
			   }
		     }
		//   }
			
			// update cash balance to agency cash data

			Query<AgencyCashDataDO> acquery = session.createQuery("from AgencyCashDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?3) ");
			acquery.setParameter(1, agencyId);
			acquery.setParameter(2, 0);
			acquery.setParameter(3, 1);
			List<AgencyCashDataDO> acresult = acquery.getResultList(); 
			
			if(acresult.size()>0 && bname.equals("NA")) {
			
				for (AgencyCashDataDO cashDataDO : acresult) {
			
					BigDecimal cashbalance = new BigDecimal(cashDataDO.getCash_balance());
			
					if(cashDataDO.getId()==id){
						
						Query acquery0 = session.createQuery("update AgencyCashDataDO ac set ac.cash_amount = :aca, ac.cash_balance = :acb"
								+ " where ac.created_by = :cid and ac.id= :acid");
						acquery0.setParameter("aca", bamt.toString());
						acquery0.setParameter("acb", bamt.toString());
						acquery0.setParameter("cid", agencyId);
						acquery0.setParameter("acid", id);
						acquery0.executeUpdate();
						
						
					}else{
					Query acquery1 = session.createQuery("update AgencyCashDataDO ac set ac.cash_balance = :acb"
							+ " where ac.created_by = :cid and ac.id= :acid");
					acquery1.setParameter("acb", cashbalance.add(bamt).toString());
					acquery1.setParameter("cid", agencyId);
					acquery1.setParameter("acid", cashDataDO.getId());
					acquery1.executeUpdate();
					}
				}
			}

			
			
			// update ujwala balance to cvo

						Query<CVODataDO> ujwalaquery = session.createQuery("from CVODataDO where created_by = ?1 and deleted = ?2 and id = ?3 order by created_date asc ");
						ujwalaquery.setParameter(1, agencyId);
						ujwalaquery.setParameter(2, 0);
						ujwalaquery.setParameter(3, id);
						List<CVODataDO> ujwalaresult = ujwalaquery.getResultList(); 
						if(ujwalaresult.size()>0 && bname.equals("UJWALA")) {
						//	for (CVODataDO cvoDataDO : ujwalaresult) {
						
						//		BigDecimal ujwalabalance = new BigDecimal(cvoDataDO.getCbal());
							
							    BigDecimal ujwalabalance = new BigDecimal(ujwalaresult.get(0).getCbal());
							
								
								Query ujwalaquery1 = session.createQuery("update CVODataDO ac set ac.obal = :aob, ac.cbal = :acb"
										+ " where ac.created_by = :cid and ac.id = :cvoid");
								ujwalaquery1.setParameter("aob", bamt.toString());
								ujwalaquery1.setParameter("acb", ujwalabalance.add(bamt).toString());
								ujwalaquery1.setParameter("cid", agencyId);
								ujwalaquery1.setParameter("cvoid", id);
								ujwalaquery1.executeUpdate();

						//	}
						}

			tx.commit();
		}catch(Exception e) {
			try {
				if(tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MasterDataPersistenceManager --> updateCreditlimitsData is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}


	// End Opening Balance Method 06052019

}
