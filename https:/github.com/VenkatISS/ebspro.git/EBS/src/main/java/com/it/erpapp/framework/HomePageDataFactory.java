package com.it.erpapp.framework;

import java.util.List;

import com.it.erpapp.framework.managers.HomePageDataPersistenceManager;
import com.it.erpapp.framework.model.StatutoryDataDO;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class HomePageDataFactory {
	public List<StatutoryDataDO> getAgencyAlertsStatutoryData(long agencyId) throws BusinessException {
		return getHomePageDataPersistenceManager().getAgencyAlertsStatutoryData(agencyId);
	}

	public List<AgencyVO> getAgencyClientsData() {
		return getHomePageDataPersistenceManager().getAgencyClientsData();
	}

	private HomePageDataPersistenceManager getHomePageDataPersistenceManager() {
		return new HomePageDataPersistenceManager();
	}

}