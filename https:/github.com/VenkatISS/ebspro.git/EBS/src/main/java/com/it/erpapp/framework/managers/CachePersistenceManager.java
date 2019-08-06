package com.it.erpapp.framework.managers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.it.erpapp.framework.model.CashTransEnumDO;
import com.it.erpapp.framework.model.ProductCategoryDO;
import com.it.erpapp.framework.model.StatutoryItemDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

public class CachePersistenceManager {

	Logger logger = Logger.getLogger(CachePersistenceManager.class.getName());

	public List<ProductCategoryDO> getProductCatogoryData() throws BusinessException {
		List<ProductCategoryDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<ProductCategoryDO> query = session.createQuery("from ProductCategoryDO where deleted = ?1 ");
			query.setParameter(1, 0);
			List<ProductCategoryDO> result = query.getResultList();
			if (result.size() > 0) {
				for (ProductCategoryDO productCategoryDO : result) {
					doList.add(productCategoryDO);
				}
			} else {
				throw new BusinessException("UNABLE TO FETCH PRODUCT CATOGORY DATA");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return doList;
	}

	public List<StatutoryItemDO> getStatutoryItemData() throws BusinessException {
		List<StatutoryItemDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<StatutoryItemDO> query = session.createQuery("from StatutoryItemDO ");
			List<StatutoryItemDO> result = query.getResultList();
			if (result.size() > 0) {
				for (StatutoryItemDO statutoryItemDO : result) {
					doList.add(statutoryItemDO);
				}
			} else {
				throw new BusinessException("UNABLE TO FETCH STATUTORY ITEM DATA");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return doList;
	}

	public List<CashTransEnumDO> getCashTransEnumData() throws BusinessException {
		List<CashTransEnumDO> doList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			@SuppressWarnings("unchecked")
			Query<CashTransEnumDO> query = session.createQuery("from CashTransEnumDO");
			List<CashTransEnumDO> result = query.getResultList();
			if (result.size() > 0) {
				for (CashTransEnumDO cashTransEnumDO : result) {
					doList.add(cashTransEnumDO);
				}
			} else {
				throw new BusinessException("UNABLE TO FETCH CASH TRANSACTION ENUM DATA");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		return doList;
	}
}
