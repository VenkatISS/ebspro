package com.it.erpapp.framework.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.pps.ARBDataDO;
import com.it.erpapp.framework.model.pps.ARBPriceDataDO;
import com.it.erpapp.framework.model.pps.AreaCodeDataDO;
import com.it.erpapp.framework.model.pps.EquipmentDataDO;
import com.it.erpapp.framework.model.pps.RefillPriceDataDO;
import com.it.erpapp.framework.model.pps.ServicesDataDO;
import com.it.erpapp.framework.model.pps.ServicesGSTData;
import com.it.erpapp.framework.model.transactions.AgencyBOMDO;
import com.it.erpapp.framework.model.transactions.BOMItemsDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

public class PPMasterDataPersistenceManager {

	Logger logger = Logger.getLogger(PPMasterDataPersistenceManager.class.getName());

	// Equipment Data
	public List<EquipmentDataDO> getAgencyEquipmentData(long agencyId) throws BusinessException {
		List<EquipmentDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<EquipmentDataDO> query = session
					.createQuery("from EquipmentDataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<EquipmentDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (EquipmentDataDO doObj : result) {
					doList.add(doObj);
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

	// Equipment Data
	public List<EquipmentDataDO> getAgencyAllEquipmentData(long agencyId) throws BusinessException {
		List<EquipmentDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<EquipmentDataDO> query = session
					.createQuery("from EquipmentDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?3) ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 1);
			List<EquipmentDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (EquipmentDataDO doObj : result) {
					doList.add(doObj);
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

	// Deleted Equipment Data
	public List<EquipmentDataDO> getAgencyDEquipmentData(long agencyId) throws BusinessException {
		List<EquipmentDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<EquipmentDataDO> query = session
					.createQuery("from EquipmentDataDO where created_by = ?1 and deleted = ?2");
			query.setParameter(1, agencyId);
			query.setParameter(2, 1);
			List<EquipmentDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (EquipmentDataDO doObj : result) {
					doList.add(doObj);
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
	
	// Get Stock Data
	public List<AgencyStockDataDO> getAgencyStockData(long agencyId) throws BusinessException {
		List<AgencyStockDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<AgencyStockDataDO> query = session
					.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted = ?2 order by created_date");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<AgencyStockDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (AgencyStockDataDO doObj : result) {
					doList.add(doObj);
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
	public void saveAgencyEquipmentData(List<EquipmentDataDO> doList, List<AgencyStockDataDO> asdoList)
			throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (EquipmentDataDO doObj : doList) {
				int pc = doObj.getProd_code();

				Query<EquipmentDataDO> query = session.createQuery(
						"from EquipmentDataDO where created_by = ?1 and deleted = ?2 and prod_code =" + pc);
				query.setParameter(1, doObj.getCreated_by());
				query.setParameter(2, 1);
				List<EquipmentDataDO> result = query.getResultList();
				if (result.size() > 0) {
					for (EquipmentDataDO doObj2 : result) {
						long effdate = doObj2.getEffective_date();
						doObj2.setSecurity_deposit(doObj.getSecurity_deposit());
						doObj2.setEffective_date(doObj.getEffective_date());
						doObj2.setDeleted(0);
						doObj2.setModified_date(System.currentTimeMillis());
						doObj2.setModified_by(doObj.getCreated_by());
//						session.update(doObj2);

						for (AgencyStockDataDO asdoObj : asdoList) {
							if (doObj.getProd_code() == asdoObj.getProd_code()) {

								Query<AgencyStockDataDO> asdDOq = session.createQuery(
										"from AgencyStockDataDO where created_by = ?1 and deleted != ?2 and prod_code ="
												+ pc + " and trans_date <= ?3 order by trans_date, created_date");
								asdDOq.setParameter(1, doObj.getCreated_by());
								asdDOq.setParameter(2, 3);
								asdDOq.setParameter(3, doObj.getEffective_date());
								List<AgencyStockDataDO> asresult = asdDOq.getResultList();

								String invNo = "";
								long oldEffDate = 0;
								long stockCount = 0;
								if (asresult.size() == 1) {
									for (AgencyStockDataDO asdo : asresult) {
										invNo = asdo.getInv_no();
									}
								} else if (asresult.isEmpty()) {
									invNo = "EMPTY";
								} else if (asresult.size() > 1) {
									for (AgencyStockDataDO asdDo : asresult) {
										if (("NA").equalsIgnoreCase(asdDo.getInv_no())) {
											oldEffDate = asdDo.getTrans_date();
											break;
										}
									}

									Query<Long> asdDOCountq = session.createQuery(
											"select count(*) from AgencyStockDataDO where created_by = ?1 and deleted != ?2 and prod_code ="
													+ pc + " and trans_date between ?3 and ?4");
									asdDOCountq.setParameter(1, doObj.getCreated_by());
									asdDOCountq.setParameter(2, 3);
									asdDOCountq.setParameter(3, oldEffDate);
									asdDOCountq.setParameter(4, doObj.getEffective_date());
									stockCount = asdDOCountq.getSingleResult();
									if (oldEffDate == doObj.getEffective_date())
										stockCount = 1;
								}

								if ((asresult.size() <= 1) && ((("NA").equals(invNo)) || (("EMPTY").equals(invNo)))
										&& (doObj.getEffective_date() <= effdate) && (stockCount <= 1)) {

									Query<AgencyStockDataDO> asdDOquery = session.createQuery(
											"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code ="
													+ pc + " and inv_no = ?3 order by trans_date,created_date")
											.setMaxResults(1);
									asdDOquery.setParameter(1, doObj.getCreated_by());
									asdDOquery.setParameter(2, 1);
									asdDOquery.setParameter(3, "NA");
									List<AgencyStockDataDO> asdDOres = asdDOquery.getResultList();

									if (!(asdDOres.isEmpty())) {
										for (AgencyStockDataDO asdDO : asdDOres) {
											asdDO.setFulls(doObj.getOs_fulls());
											asdDO.setEmpties(doObj.getOs_emptys());
											asdDO.setCs_fulls(doObj.getOs_fulls());
											asdDO.setCs_emptys(doObj.getOs_emptys());

											doObj2.setOs_fulls(doObj.getOs_fulls());
											doObj2.setOs_emptys(doObj.getOs_emptys());
											doObj2.setCs_fulls(doObj.getOs_fulls());
											doObj2.setCs_emptys(doObj.getOs_emptys());
											
											asdDO.setTrans_date(doObj.getEffective_date());
											asdDO.setDeleted(0);
											asdDO.setModified_date(System.currentTimeMillis());
											asdDO.setModified_by(doObj.getCreated_by());
											session.update(asdDO);
										}
									}
//									ELSE {
//										SAVE ASDO HERE - BECAUSE NA IS NOT PRESENT   
//									}
								} else {
									// SELECT * FROM AGENCY_STOCK_DATA WHERE CREATED_BY=12345678 AND DELETED!=3 AND
									// PROD_CODE=4 AND TRANS_DATE <= ?3 ORDER BY TRANS_DATE DESC, CREATED_DATE DESC
									// LIMIT 1;
									Query<AgencyStockDataDO> asdDOqry = session.createQuery(
											"from AgencyStockDataDO where created_by = ?1 and deleted != ?2 "
													+ "and prod_code =" + pc
													+ " and trans_date <= ?3 order by trans_date desc,created_date desc")
											.setMaxResults(1);
									asdDOqry.setParameter(1, doObj.getCreated_by());
									asdDOqry.setParameter(2, 3);
									asdDOqry.setParameter(3, doObj.getEffective_date());
									List<AgencyStockDataDO> asdDOr = asdDOqry.getResultList();

									if (!(asdDOr.isEmpty())) {
										for (AgencyStockDataDO asdDOobj : asdDOr) {
											int cfulls = asdDOobj.getCs_fulls();
											int cempties = asdDOobj.getCs_emptys();
											int fulls = asdDOobj.getFulls();
											int empties = asdDOobj.getEmpties();
											
											Query<Long> asdDOfeqry = session.createQuery(
													"select count(*) from AgencyStockDataDO where created_by = ?1 and deleted != ?2 "
															+ "and prod_code =" + pc +" and inv_no != ?3 ")
													.setMaxResults(1);
											asdDOfeqry.setParameter(1, doObj.getCreated_by());
											asdDOfeqry.setParameter(2, 3);
											asdDOfeqry.setParameter(3, "NA");

											List<Long> asdDOfer = asdDOfeqry.getResultList();

											if(asdDOfer.get(0) == 0){
												doObj2.setOs_fulls(asdoObj.getFulls());
												doObj2.setOs_emptys(asdoObj.getEmpties());
												doObj2.setCs_fulls(asdoObj.getCs_fulls());
												doObj2.setCs_emptys(asdoObj.getCs_emptys());
												
//												asdoObj.setFulls(asdoObj.getFulls());
//												asdoObj.setEmpties(asdoObj.getEmpties());
//												asdoObj.setCs_fulls(asdoObj.getCs_fulls());
//												asdoObj.setCs_emptys(asdoObj.getCs_emptys());
											}else if(asdDOfer.get(0) > 0){
												asdoObj.setFulls(fulls);
												asdoObj.setEmpties(empties);
												asdoObj.setCs_fulls(cfulls);
												asdoObj.setCs_emptys(cempties);
											}
											
											asdoObj.setTrans_date(doObj.getEffective_date());
											asdoObj.setRef_id(0);
											asdoObj.setInv_no("NA");
//											asdoObj.setFulls(0);
//											asdoObj.setEmpties(0);
//											asdoObj.setCs_fulls(cfulls);
//											asdoObj.setCs_emptys(cempties);
											session.save(asdoObj);
										}
									}
								}

								break;
							}
						}
						session.update(doObj2);
					}
				} else {
					session.save(doObj);
					for (AgencyStockDataDO asdoObj : asdoList) {
						if (doObj.getProd_code() == asdoObj.getProd_code()) {
							asdoObj.setRef_id(0);
							asdoObj.setInv_no("NA");
							session.save(asdoObj);
							break;
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
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> saveAgencyEquipmentData  is not succesful");
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
	public void deleteAgencyEquipmentData(long id) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			EquipmentDataDO doObj = session.get(EquipmentDataDO.class, new Long(id));
			int pc = doObj.getProd_code();

			Query<RefillPriceDataDO> query = session
					.createQuery("from RefillPriceDataDO where created_by = ?1 and deleted = ?2 and prod_code =" + pc);
			query.setParameter(1, doObj.getCreated_by());
			query.setParameter(2, 0);
			List<RefillPriceDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (RefillPriceDataDO doObj2 : result) {
					doObj2.setDeleted(1);
					session.update(doObj2);
				}
			}
			doObj.setDeleted(1);
			session.update(doObj);

			Query<AgencyStockDataDO> asdDOqry = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and inv_no = ?3 and prod_code ="
							+ pc);
			asdDOqry.setParameter(1, doObj.getCreated_by());
			asdDOqry.setParameter(2, 0);
			asdDOqry.setParameter(3, "NA");
			List<AgencyStockDataDO> asdDOres = asdDOqry.getResultList();
			if (!asdDOres.isEmpty()) {
				for (AgencyStockDataDO asDdo : asdDOres) {
					asDdo.setDeleted(1);
					asDdo.setModified_by(doObj.getCreated_by());
					asDdo.setModified_date(System.currentTimeMillis());
					session.update(asDdo);
				}
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> deleteAgencyEquipmentData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// ARB Data
	public List<ARBDataDO> getAgencyARBData(long agencyId) throws BusinessException {
		List<ARBDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			// Query<ARBDataDO> query = session.createQuery("from ARBDataDO
			// where created_by = ?1 and deleted = ?2 ");
			Query<ARBDataDO> query = session.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<ARBDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (ARBDataDO doObj : result) {
					doList.add(doObj);
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

	// ARB Data
	public List<ARBDataDO> getAgencyAllARBData(long agencyId) throws BusinessException {
		List<ARBDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<ARBDataDO> query = session.createQuery("from ARBDataDO where created_by = ?1 ");
			query.setParameter(1, agencyId);
			List<ARBDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (ARBDataDO doObj : result) {
					doList.add(doObj);
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
	public void saveAgencyARBData(List<ARBDataDO> doList, List<AgencyStockDataDO> asdoList, List<String> stockNum, List<String> arbprodNum) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<Long> pidList = new ArrayList<>();
			List<Long> newEffdatesList = new ArrayList<>();
			List<Long> oldEffdatesList = new ArrayList<>();
			Map<Long, Integer> map = new HashMap<>();
			int arbCounter = 0;
			
			for (ARBDataDO doObj : doList) {

				for(int i=0 ; i< arbprodNum.size() ; i++){
					if( i == arbCounter) {

						int pc = doObj.getProd_code();
						String brand = doObj.getProd_brand();
						String pn = doObj.getProd_name();
						String hsn = doObj.getCsteh_no();

						Query<ARBDataDO> query = session
								.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 and prod_code =" + pc
										+ " and prod_brand =" + "'" + brand + "'" + " and prod_name =" + "'" + pn + "'"
										+ " and csteh_no=" + "'" + hsn + "'");
						query.setParameter(1, doObj.getCreated_by());
						query.setParameter(2, 1);
						List<ARBDataDO> result = query.getResultList();
						if (result.size() > 0) {
							for (ARBDataDO doObj2 : result) {
								if (doObj.getProd_code() == doObj2.getProd_code()
										&& (doObj.getProd_brand()).equalsIgnoreCase((doObj2.getProd_brand()))
										&& (doObj.getProd_name()).equalsIgnoreCase((doObj2.getProd_name()))
										&& (doObj.getCsteh_no()).equalsIgnoreCase((doObj2.getCsteh_no()))) {

									newEffdatesList.add(doObj.getEffective_date());
									oldEffdatesList.add(doObj2.getEffective_date());

									doObj.setId(doObj2.getId());
									doObj2.setEffective_date(doObj.getEffective_date());
									doObj2.setDeleted(0);
									doObj2.setModified_by(doObj.getCreated_by());
									doObj2.setModified_date(System.currentTimeMillis());
//									session.update(doObj2);

									map.put(doObj.getId(), 1);
									pidList.add(doObj.getId());
									int stkCounter = 0;
									// GET UNIQUE VALUES FOR EACH ROW ON USER ADDITION FROM FRONT END AND CHECK HERE FOR SAME PROD OR NOT. CHECK (1)
									for (AgencyStockDataDO asdoObj : asdoList) {
										
										for(int s=0 ; s< stockNum.size() ; s++){
											if( s == stkCounter) {
										
												if (doObj.getProd_code() == asdoObj.getProd_code() &&  stockNum.get(s) == arbprodNum.get(i)) { // OVER HERE (1)
													asdoObj.setProd_id(doObj.getId());
											
													// deleted case

													Query<AgencyStockDataDO> asquery = session.createQuery(
															"from AgencyStockDataDO where created_by = ?1 and deleted != ?2 and prod_id = ?3 and trans_date <= ?4");
													asquery.setParameter(1, asdoObj.getCreated_by());
													asquery.setParameter(2, 3);
													asquery.setParameter(3, asdoObj.getProd_id());
													asquery.setParameter(4, doObj.getEffective_date());
													List<AgencyStockDataDO> asres = asquery.getResultList();

													String invNo = "";
													long oldEffDate = 0;
													long stockCount = 0;
													if (asres.size() == 1) {
														for (AgencyStockDataDO asdo : asres) {
															invNo = asdo.getInv_no();
														}
													} else if (asres.isEmpty()) {
														invNo = "EMPTY";
													} else if (asres.size() > 1) {
														for (AgencyStockDataDO asdDo : asres) {
															if (("NA").equalsIgnoreCase(asdDo.getInv_no())) {
																oldEffDate = asdDo.getTrans_date();
																break;
															}
														}

														Query<Long> asdDOCountq = session.createQuery(
																"select count(*) from AgencyStockDataDO where created_by = ?1 and deleted != ?2 and prod_id = ?3 and trans_date between ?4 and ?5");
														asdDOCountq.setParameter(1, asdoObj.getCreated_by());
														asdDOCountq.setParameter(2, 3);
														asdDOCountq.setParameter(3, asdoObj.getProd_id());
														asdDOCountq.setParameter(4, oldEffDate);
														asdDOCountq.setParameter(5, doObj.getEffective_date());
														stockCount = asdDOCountq.getSingleResult();
														if (oldEffDate == doObj.getEffective_date())
															stockCount = 1;
													}

													if ((asres.size() <= 1) && ((("NA").equals(invNo)) || (("EMPTY").equals(invNo)))
															&& (doObj.getEffective_date() <= doObj2.getEffective_date()) && (stockCount <= 1)) {

														// SELECT * FROM AGENCY_STOCK_DATA WHERE CREATED_BY=12345678 AND DELETED=1 AND
														// PROD_CODE=4 AND INV_NO="NA" order by trans_date,created_date;
														Query<AgencyStockDataDO> asdDOquery = session.createQuery(
																"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and prod_id = ?4 and inv_no = ?5 order by trans_date,created_date")
																.setMaxResults(1);
														asdDOquery.setParameter(1, asdoObj.getCreated_by());
														asdDOquery.setParameter(2, 1);
														asdDOquery.setParameter(3, asdoObj.getProd_code());
														asdDOquery.setParameter(4, asdoObj.getProd_id());
														asdDOquery.setParameter(5, "NA");
														List<AgencyStockDataDO> asdDOres = asdDOquery.getResultList();

														if (!(asdDOres.isEmpty())) {
															for (AgencyStockDataDO asdDO : asdDOres) {
											
																asdDO.setFulls(doObj.getOpening_stock());
																asdDO.setEmpties(0);
																asdDO.setCs_fulls(doObj.getOpening_stock());
																asdDO.setCs_emptys(0);

																doObj2.setOpening_stock(doObj.getOpening_stock());
																doObj2.setCurrent_stock(doObj.getOpening_stock());											

																asdDO.setTrans_date(doObj.getEffective_date());
																asdDO.setDeleted(0);
																asdDO.setModified_by(asdoObj.getCreated_by());
																asdDO.setModified_date(System.currentTimeMillis());
																session.update(asdDO);
															}
														}

													} else {
														// SELECT * FROM AGENCY_STOCK_DATA WHERE CREATED_BY=12345678 AND DELETED!=3 AND
														// PROD_CODE=4 AND TRANS_DATE <= 3 ORDER BY TRANS_DATE DESC, CREATED_DATE DESC
														// LIMIT 1;
														Query<AgencyStockDataDO> asdDOq = session
																.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted != ?2 "
																		+ "and prod_code = ?3 and prod_id = ?4 and trans_date <= ?5 order by trans_date desc,created_date desc")
																.setMaxResults(1);
														asdDOq.setParameter(1, asdoObj.getCreated_by());
														asdDOq.setParameter(2, 3);
														asdDOq.setParameter(3, asdoObj.getProd_code());
														asdDOq.setParameter(4, asdoObj.getProd_id());
														asdDOq.setParameter(5, doObj.getEffective_date());

														List<AgencyStockDataDO> asdDOr = asdDOq.getResultList();

														if (!(asdDOr.isEmpty())) {
															for (AgencyStockDataDO asdDOobj : asdDOr) {
																int cfulls = asdDOobj.getCs_fulls();
																int fulls = asdDOobj.getFulls();
											
																Query<Long> asdDOfeqry = session.createQuery(
																		"select count(*) from AgencyStockDataDO where created_by = ?1 and deleted != ?2 "
																				+ "and prod_code = ?3 and prod_id = ?4 and inv_no != ?5 ")
																		.setMaxResults(1);
																asdDOfeqry.setParameter(1, doObj.getCreated_by());
																asdDOfeqry.setParameter(2, 3);
																asdDOfeqry.setParameter(3, asdoObj.getProd_code());
																asdDOfeqry.setParameter(4, asdoObj.getProd_id());
																asdDOfeqry.setParameter(5, "NA");

																List<Long> asdDOfer = asdDOfeqry.getResultList();

																if(asdDOfer.get(0) == 0){
																	doObj2.setOpening_stock(asdoObj.getFulls());
																	doObj2.setCurrent_stock(asdoObj.getCs_fulls());												
																}else if(asdDOfer.get(0) > 0){
																	asdoObj.setFulls(fulls);
																	asdoObj.setCs_fulls(cfulls);
																}
											
																asdoObj.setProd_id(asdDOobj.getProd_id());
																asdoObj.setTrans_date(doObj.getEffective_date());
																asdoObj.setRef_id(0);
																asdoObj.setInv_no("NA");
//																asdoObj.setFulls(0);
//																asdoObj.setEmpties(0);
//																asdoObj.setCs_fulls(cfulls);
																session.save(asdoObj);
															}
														}
													}
													break;
												}
												break;
											}
										}
										
										++stkCounter;
									}///////////////////
							
									session.update(doObj2);
							
								} else {
									
									session.save(doObj);
									int stkCounter = 0;
									for (AgencyStockDataDO asdoObj : asdoList) {
										for(int j=0 ; j< stockNum.size() ; j++){
											if( j == stkCounter) {
												if (doObj.getProd_code() == asdoObj.getProd_code() && stockNum.get(j) == arbprodNum.get(i) ) {
													asdoObj.setProd_id(doObj.getId());
													asdoObj.setRef_id(0);
													asdoObj.setInv_no("NA");
													session.save(asdoObj);
													break;
												}
												break;
											}
										}
										++stkCounter;
									}
							
								}
							}
						} else {
							session.save(doObj);
							int stkCounter = 0;
							for (AgencyStockDataDO asdoObj : asdoList) {
								for(int j=0 ; j< stockNum.size() ; j++){
									if( j == stkCounter) {
										if (doObj.getProd_code() == asdoObj.getProd_code() && stockNum.get(j) == arbprodNum.get(i) ) {
											asdoObj.setProd_id(doObj.getId());
											asdoObj.setRef_id(0);
											asdoObj.setInv_no("NA");
											session.save(asdoObj);
											break;
										}
										break;
									}
								}
								++stkCounter;
							}
					
						}
						break;
					}
				}
				++arbCounter;
			}///////////////////////////////////////////////////

			/*
			int count = 0;
			int oefdcount = 0;
			for (AgencyStockDataDO asdoObj : asdoList) {
				asdoObj.setProd_id(pidList.get(count));
				if (map.get(asdoObj.getProd_id()) == 0) {
					// non deleted case
					asdoObj.setRef_id(0);
					asdoObj.setInv_no("NA");
					session.save(asdoObj);
				} else {
					// deleted case

					Query<AgencyStockDataDO> asquery = session.createQuery(
							"from AgencyStockDataDO where created_by = ?1 and deleted != ?2 and prod_id = ?3 and trans_date <= ?4");
					asquery.setParameter(1, asdoObj.getCreated_by());
					asquery.setParameter(2, 3);
					asquery.setParameter(3, asdoObj.getProd_id());
					asquery.setParameter(4, newEffdatesList.get(count));
					List<AgencyStockDataDO> asres = asquery.getResultList();

					String invNo = "";
					long oldEffDate = 0;
					long stockCount = 0;
					if (asres.size() == 1) {
						for (AgencyStockDataDO asdo : asres) {
							invNo = asdo.getInv_no();
						}
					} else if (asres.isEmpty()) {
						invNo = "EMPTY";
					} else if (asres.size() > 1) {
						for (AgencyStockDataDO asdDo : asres) {
							if (("NA").equalsIgnoreCase(asdDo.getInv_no())) {
								oldEffDate = asdDo.getTrans_date();
								break;
							}
						}

						Query<Long> asdDOCountq = session.createQuery(
								"select count(*) from AgencyStockDataDO where created_by = ?1 and deleted != ?2 and prod_id = ?3 and trans_date between ?4 and ?5");
						asdDOCountq.setParameter(1, asdoObj.getCreated_by());
						asdDOCountq.setParameter(2, 3);
						asdDOCountq.setParameter(3, asdoObj.getProd_id());
						asdDOCountq.setParameter(4, oldEffDate);
						asdDOCountq.setParameter(5, newEffdatesList.get(count));
						stockCount = asdDOCountq.getSingleResult();
						if (oldEffDate == newEffdatesList.get(count))
							stockCount = 1;
					}

					if ((asres.size() <= 1) && ((("NA").equals(invNo)) || (("EMPTY").equals(invNo)))
							&& (newEffdatesList.get(count) <= oldEffdatesList.get(oefdcount)) && (stockCount <= 1)) {

						// SELECT * FROM AGENCY_STOCK_DATA WHERE CREATED_BY=12345678 AND DELETED=1 AND
						// PROD_CODE=4 AND INV_NO="NA" order by trans_date,created_date;
						Query<AgencyStockDataDO> asdDOquery = session.createQuery(
								"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and prod_id = ?4 and inv_no = ?5 order by trans_date,created_date")
								.setMaxResults(1);
						asdDOquery.setParameter(1, asdoObj.getCreated_by());
						asdDOquery.setParameter(2, 1);
						asdDOquery.setParameter(3, asdoObj.getProd_code());
						asdDOquery.setParameter(4, asdoObj.getProd_id());
						asdDOquery.setParameter(5, "NA");
						List<AgencyStockDataDO> asdDOres = asdDOquery.getResultList();

						if (!(asdDOres.isEmpty())) {
							for (AgencyStockDataDO asdDO : asdDOres) {
								asdDO.setTrans_date(newEffdatesList.get(count));
								asdDO.setDeleted(0);
								asdDO.setModified_by(asdoObj.getCreated_by());
								asdDO.setModified_date(System.currentTimeMillis());
								session.update(asdDO);
							}
						}

					} else {
						// SELECT * FROM AGENCY_STOCK_DATA WHERE CREATED_BY=12345678 AND DELETED!=3 AND
						// PROD_CODE=4 AND TRANS_DATE <= 3 ORDER BY TRANS_DATE DESC, CREATED_DATE DESC
						// LIMIT 1;
						Query<AgencyStockDataDO> asdDOq = session
								.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted != ?2 "
										+ "and prod_code = ?3 and prod_id = ?4 and trans_date <= ?5 order by trans_date desc,created_date desc")
								.setMaxResults(1);
						asdDOq.setParameter(1, asdoObj.getCreated_by());
						asdDOq.setParameter(2, 3);
						asdDOq.setParameter(3, asdoObj.getProd_code());
						asdDOq.setParameter(4, asdoObj.getProd_id());
						asdDOq.setParameter(5, newEffdatesList.get(count));

						List<AgencyStockDataDO> asdDOr = asdDOq.getResultList();

						if (!(asdDOr.isEmpty())) {
							for (AgencyStockDataDO asdDOobj : asdDOr) {
								int cfulls = asdDOobj.getCs_fulls();

								asdoObj.setProd_id(asdDOobj.getProd_id());
								asdoObj.setTrans_date(newEffdatesList.get(count));
								asdoObj.setRef_id(0);
								asdoObj.setInv_no("NA");
								asdoObj.setFulls(0);
								asdoObj.setEmpties(0);
								asdoObj.setCs_fulls(cfulls);
								session.save(asdoObj);
							}
						}
					}
					++oefdcount;
				}
				++count;
			}*/
				
				
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> saveAgencyARBData  is not succesful");
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
	public void ERTFsaveAgencyARBData(List<ARBDataDO> doList, List<AgencyStockDataDO> asdoList)
			throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (ARBDataDO doObj : doList) {
				int pc = doObj.getProd_code();
				String brand = doObj.getProd_brand();
				String pn = doObj.getProd_name();
				String hsn = doObj.getCsteh_no();

				Query<ARBDataDO> query = session
						.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 and prod_code =" + pc
								+ " and prod_brand =" + "'" + brand + "'" + " and prod_name =" + "'" + pn + "'"
								+ " and csteh_no=" + "'" + hsn + "'");
				query.setParameter(1, doObj.getCreated_by());
				query.setParameter(2, 1);
				List<ARBDataDO> result = query.getResultList();
				if (!result.isEmpty()) {
					for (ARBDataDO doObj2 : result) {
						long effDate = doObj2.getEffective_date();
						doObj2.setEffective_date(doObj.getEffective_date());
						doObj2.setDeleted(0);
						doObj2.setModified_by(doObj.getCreated_by());
						doObj2.setModified_date(System.currentTimeMillis());
						session.update(doObj2);

						for (AgencyStockDataDO asdoObj : asdoList) {

							if (doObj.getProd_code() == asdoObj.getProd_code()) {

								if (doObj.getEffective_date() <= effDate) {
									// SELECT * FROM AGENCY_STOCK_DATA WHERE CREATED_BY=12345678 AND DELETED=1 AND
									// PROD_CODE=4 AND INV_NO="NA";
									Query<AgencyStockDataDO> asdDOquery = session.createQuery(
											"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and prod_code = ?3 and prod_id = ?4 and inv_no = ?5 ");
									asdDOquery.setParameter(1, doObj.getCreated_by());
									asdDOquery.setParameter(2, 1);
									asdDOquery.setParameter(3, pc);
									asdDOquery.setParameter(4, doObj2.getId());
									asdDOquery.setParameter(5, "NA");
									List<AgencyStockDataDO> asdDOres = asdDOquery.getResultList();

									if (!(asdDOres.isEmpty())) {
										for (AgencyStockDataDO asdDO : asdDOres) {
											asdDO.setTrans_date(doObj.getEffective_date());
											asdDO.setDeleted(0);
											asdDO.setModified_by(doObj.getCreated_by());
											asdDO.setModified_date(System.currentTimeMillis());
											session.update(asdDO);
										}
									}

								} else {
									// SELECT * FROM AGENCY_STOCK_DATA WHERE CREATED_BY=12345678 AND DELETED!=3 AND
									// PROD_CODE=4 AND TRANS_DATE <= 3 ORDER BY TRANS_DATE DESC, CREATED_DATE DESC
									// LIMIT 1;
									Query<AgencyStockDataDO> asdDOq = session.createQuery(
											"from AgencyStockDataDO where created_by = ?1 and deleted != ?2 "
													+ "and prod_code = ?3 and prod_id = ?4 and trans_date <= ?5 order by trans_date desc,created_date desc")
											.setMaxResults(1);
									asdDOq.setParameter(1, doObj.getCreated_by());
									asdDOq.setParameter(2, 3);
									asdDOq.setParameter(3, pc);
									asdDOq.setParameter(4, doObj2.getId());
									asdDOq.setParameter(5, doObj.getEffective_date());

									List<AgencyStockDataDO> asdDOr = asdDOq.getResultList();

									if (!(asdDOr.isEmpty())) {
										for (AgencyStockDataDO asdDOobj : asdDOr) {
											int cfulls = asdDOobj.getCs_fulls();

											asdoObj.setProd_id(asdDOobj.getProd_id());
											asdoObj.setTrans_date(doObj.getEffective_date());
											asdoObj.setRef_id(0);
											asdoObj.setInv_no("NA");
											asdoObj.setFulls(0);
											asdoObj.setEmpties(0);
											asdoObj.setCs_fulls(cfulls);
											session.save(asdoObj);
										}
									}
								}
								break;
							}
						}
					}
				} else {
					session.save(doObj);

					for (AgencyStockDataDO asdoObj : asdoList) {
						if (doObj.getProd_code() == asdoObj.getProd_code()) {
							asdoObj.setProd_id(doObj.getId());
							asdoObj.setRef_id(0);
							asdoObj.setInv_no("NA");
							session.save(asdoObj);
							break;
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
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> ERTFsaveAgencyARBData  is not succesful");
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
	public void deleteAgencyARBData(long id) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ARBDataDO doObj = session.get(ARBDataDO.class, new Long(id));
			long ac = doObj.getId();

			Query<ARBPriceDataDO> query = session
					.createQuery("from ARBPriceDataDO where created_by = ?1 and deleted = ?2 and arb_code =" + ac);
			query.setParameter(1, doObj.getCreated_by());
			query.setParameter(2, 0);
			List<ARBPriceDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (ARBPriceDataDO doObj2 : result) {
					doObj2.setDeleted(1);
					session.update(doObj2);
				}
			}
			doObj.setDeleted(1);
			session.update(doObj);

			Query<AgencyStockDataDO> asdDOqry = session.createQuery(
					"from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and inv_no = ?3 and prod_code = ?4 and prod_id = ?5");
			asdDOqry.setParameter(1, doObj.getCreated_by());
			asdDOqry.setParameter(2, 0);
			asdDOqry.setParameter(3, "NA");
			asdDOqry.setParameter(4, doObj.getProd_code());
			asdDOqry.setParameter(5, ac);

			List<AgencyStockDataDO> asdDOres = asdDOqry.getResultList();
			if (!asdDOres.isEmpty()) {
				for (AgencyStockDataDO asDdo : asdDOres) {
					asDdo.setDeleted(1);
					asDdo.setModified_by(doObj.getCreated_by());
					asDdo.setModified_date(System.currentTimeMillis());
					session.update(asDdo);
				}
			}

			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> deleteAgencyARBData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Services Data
	public List<ServicesDataDO> getAgencyServicesData(long agencyId) throws BusinessException {
		List<ServicesDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<ServicesDataDO> query = session.createQuery("from ServicesDataDO where deleted = ?1 ");
			query.setParameter(1, 0);
			List<ServicesDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (ServicesDataDO doObj : result) {
					doList.add(doObj);
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

	public void saveAgencyServicesData(List<ServicesDataDO> doList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (ServicesDataDO doObj : doList) {
				session.save(doObj);
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> saveAgencyServicesData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	public void deleteAgencyServicesData(long id) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ServicesDataDO doObj = session.get(ServicesDataDO.class, new Long(id));
			doObj.setDeleted(1);
			session.update(doObj);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> deleteAgencyServicesData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}
	
	

	//Services GST 10052019
		
		
		@SuppressWarnings({ "unchecked", "unused" })
		public void saveAgencyServicesGST(long agencyId) throws BusinessException {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				List<ServicesGSTData> list=new ArrayList<>();
				Query<ServicesDataDO> query = session.createQuery("from ServicesDataDO where deleted = ?1");
				query.setParameter(1, 0);
				List<ServicesDataDO> result = query.getResultList();
				if (result.size() > 0) {
				

				for (ServicesDataDO doObj : result) {
					ServicesGSTData gstData=new ServicesGSTData();

					gstData.setService_product_id(doObj.getProd_code());
					gstData.setGst_percent_applicable("1");
					gstData.setGstp_applicable_amt(doObj.getGst_amt());
					gstData.setCreated_by(agencyId);
					gstData.setCreated_date(System.currentTimeMillis());
					gstData.setVersion(1);
					gstData.setDeleted(0);
	            	
					session.save(gstData);

				}

			 }
				tx.commit();
			} catch (Exception e) {
				try {
					if (tx != null)
						tx.rollback();
				} catch (HibernateException e1) {
					logger.error(
							"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> saveAgencyServicesData  is not succesful");
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
	public List<ServicesGSTData> getAgencyServicesGSTData(long agencyId) throws BusinessException {
			List<ServicesGSTData> doList = new ArrayList<>();
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				Query<ServicesGSTData> query = session.createQuery("from ServicesGSTData where created_by= ?1 and deleted = ?2 ");
				query.setParameter(1,agencyId);
				query.setParameter(2, 0);
				List<ServicesGSTData> result = query.getResultList();
				if (result.size() > 0) {
					for (ServicesGSTData doObj : result) {
						doList.add(doObj);
					}
				}else{
					
					Query<ServicesDataDO> query1 = session.createQuery("from ServicesDataDO where deleted = ?1");
					query1.setParameter(1, 0);
					List<ServicesDataDO> result1 = query1.getResultList();
					if (result1.size() > 0) {
					

					for (ServicesDataDO doObj : result1) {
						ServicesGSTData gstData=new ServicesGSTData();

						gstData.setService_product_id(doObj.getProd_code());
						gstData.setGst_percent_applicable("1");
						gstData.setGstp_applicable_amt(doObj.getGst_amt());
						gstData.setCreated_by(agencyId);
						gstData.setCreated_date(System.currentTimeMillis());
						gstData.setVersion(1);
						gstData.setDeleted(0);
		            	
						session.save(gstData);

					}
					tx.commit();

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


	@SuppressWarnings("rawtypes")
	public List<ServicesDataDO> updateServiceGSTPercentage(long agencyId,String gstp) throws BusinessException {
		List<ServicesDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			@SuppressWarnings("unchecked")
			Query<ServicesDataDO> query = session.createQuery("from ServicesDataDO where deleted = ?1 ");
			query.setParameter(1, 0);
			List<ServicesDataDO> result = query.getResultList();
			if (result.size() > 0) {
				String gstper="0";
				double gstamt=0;
				double gstpamount=0;
				for (ServicesDataDO doObj : result) {
			
					gstamt=(float)(0.18*(Double.parseDouble(doObj.getProd_charges())));
					if(gstp.equals("1"))
					{
						gstper="0.75";
						gstpamount= (0.75*gstamt);
						
					}else if(gstp.equals("2"))
					{
						gstper="0.50";
						gstpamount= (0.5*gstamt);
					
					}else if(gstp.equals("3"))
					{
						gstper="0.25";
						gstpamount= (0.25*gstamt);
					
					}else if(gstp.equals("4"))
					{
						gstper="0";
						gstpamount= 0.00;
					
					}else{
						gstper="1";
						gstpamount= gstamt;
					}
		
					Query query2 = session.createQuery("update ServicesGSTData s set s.gstp_applicable_amt = :gstamt,s.gst_percent_applicable = :gstpa,s.modified_date= :mdate"
							+ " where s.created_by = :cid and s.service_product_id= :servicesid ");
					query2.setParameter("cid", agencyId);
					query2.setParameter("servicesid", doObj.getProd_code());
					query2.setParameter("gstamt", String.valueOf(gstpamount));
					query2.setParameter("gstpa", String.valueOf(gstper));
					query2.setParameter("mdate", System.currentTimeMillis());
					query2.executeUpdate();
					

				}
				}
			tx.commit();

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

	// Area Code Data
	public List<AreaCodeDataDO> getAgencyAreaCodeData(long agencyId) throws BusinessException {
		List<AreaCodeDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<AreaCodeDataDO> query = session
					.createQuery("from AreaCodeDataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<AreaCodeDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (AreaCodeDataDO doObj : result) {
					doList.add(doObj);
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

	// Area Code Data
	public List<AreaCodeDataDO> getAgencyAllAreaCodeData(long agencyId) throws BusinessException {
		List<AreaCodeDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<AreaCodeDataDO> query = session
					.createQuery("from AreaCodeDataDO where created_by = ?1 and (deleted = ?2 or deleted = ?3) ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			query.setParameter(3, 1);
			List<AreaCodeDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (AreaCodeDataDO doObj : result) {
					doList.add(doObj);
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

	@SuppressWarnings("unchecked")
	public void saveAgencyAreaCodeData(List<AreaCodeDataDO> doList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (AreaCodeDataDO doObj : doList) {
				String ac = doObj.getArea_code();
				Query<AreaCodeDataDO> query = session.createQuery(
						"from AreaCodeDataDO where created_by = ?1 and deleted = ?2 and area_code=" + "'" + ac + "'");
				query.setParameter(1, doObj.getCreated_by());
				query.setParameter(2, 1);
				List<AreaCodeDataDO> result = query.getResultList();
				if (result.size() > 0) {
					for (AreaCodeDataDO doObj2 : result) {
						if ((doObj.getArea_code()).equalsIgnoreCase((doObj2.getArea_code()))) {
							session.clear();
							doObj.setId(doObj2.getId());
							doObj.setDeleted(0);
							doObj.setCreated_date(System.currentTimeMillis());
							session.update(doObj);
						} else {
							session.save(doObj);
						}
					}
				} else {
					session.save(doObj);
				}
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> saveAgencyAreaCodeData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	public void deleteAgencyAreaCodeData(long id) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AreaCodeDataDO doObj = session.get(AreaCodeDataDO.class, new Long(id));
			doObj.setDeleted(1);
			session.update(doObj);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> deleteAgencyAreaCodeData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// Refill Price Data
	public List<RefillPriceDataDO> getAgencyRefillPriceData(long agencyId) throws BusinessException {
		List<RefillPriceDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<RefillPriceDataDO> query = session
					.createQuery("from RefillPriceDataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<RefillPriceDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (RefillPriceDataDO doObj : result) {
					doList.add(doObj);
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

	public void saveAgencyRefillPriceData(List<RefillPriceDataDO> doList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (RefillPriceDataDO doObj : doList) {
				session.save(doObj);
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> saveAgencyRefillPriceData  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	public void deleteAgencyRefillPriceData(long id) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			RefillPriceDataDO doObj = session.get(RefillPriceDataDO.class, new Long(id));
			doObj.setDeleted(1);
			session.update(doObj);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> deleteAgencyRefillPriceData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// ARB Price Data
	public List<ARBPriceDataDO> getAgencyARBPriceData(long agencyId) throws BusinessException {
		List<ARBPriceDataDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<ARBPriceDataDO> query = session
					.createQuery("from ARBPriceDataDO where created_by = ?1 and deleted = ?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<ARBPriceDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (ARBPriceDataDO doObj : result) {
					doList.add(doObj);
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

	public void saveAgencyARBPriceData(List<ARBPriceDataDO> doList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			for (ARBPriceDataDO doObj : doList) {
				session.save(doObj);
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> saveAgencyARBPriceData  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	public void deleteAgencyARBPriceData(long id) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			ARBPriceDataDO doObj = session.get(ARBPriceDataDO.class, new Long(id));
			doObj.setDeleted(1);
			session.delete(doObj);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> deleteAgencyARBPriceData  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	// BOM Data
	public List<AgencyBOMDO> getAgencyBOMData(long agencyId) throws BusinessException {
		List<AgencyBOMDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<AgencyBOMDO> query = session
					.createQuery("from AgencyBOMDO where created_by = ?1 and deleted = ?2 order by created_date desc");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			List<AgencyBOMDO> result = query.getResultList();
			if (result.size() > 0) {
				for (AgencyBOMDO doObj : result) {
					@SuppressWarnings("unchecked")
					Query<BOMItemsDO> squery = session.createQuery("from BOMItemsDO where bom_id = ?1");
					squery.setParameter(1, doObj.getId());
					List<BOMItemsDO> itemsResult = squery.getResultList();
					if (itemsResult.size() > 0) {
						doObj.getBomItemsSet().addAll(itemsResult);
					}
					doList.add(doObj);
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

	public void saveAgencyBOMData(AgencyBOMDO agencyBOMDO, List<BOMItemsDO> doList) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(agencyBOMDO);
			for (BOMItemsDO doObj : doList) {
				doObj.setBom_id(agencyBOMDO.getId());
				session.save(doObj);
			}
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> saveAgencyBOMData  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

	public void deleteAgencyBOMData(long id) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			AgencyBOMDO dataDO = session.get(AgencyBOMDO.class, new Long(id));
			dataDO.setDeleted(1);
			session.update(dataDO);
			tx.commit();
		} catch (Exception e) {
			try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> deleteAgencyBOMData  is not succesful");
			}
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException(e.getMessage());
		} finally {
			session.clear();
			session.close();
		}
	}

}
