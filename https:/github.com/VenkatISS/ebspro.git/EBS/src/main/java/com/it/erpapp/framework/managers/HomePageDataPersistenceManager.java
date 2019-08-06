package com.it.erpapp.framework.managers;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.it.erpapp.framework.model.StatutoryDataDO;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

public class HomePageDataPersistenceManager {
	
	Logger logger = Logger.getLogger(HomePageDataPersistenceManager.class.getName());
	
	public List<StatutoryDataDO> getAgencyAlertsStatutoryData(long agencyId) throws BusinessException {
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

	public List<AgencyVO> getAgencyClientsData() {
		List<AgencyVO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<AgencyVO> query = session.createQuery("from AgencyVO");
			List<AgencyVO> result = query.getResultList(); 
			if(result.size()>0) {
				for (AgencyVO avo : result) {
					doList.add(avo);
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
}