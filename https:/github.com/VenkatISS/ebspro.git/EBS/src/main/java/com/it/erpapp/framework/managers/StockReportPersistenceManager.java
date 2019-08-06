package com.it.erpapp.framework.managers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.vos.ProductStockVO;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

public class StockReportPersistenceManager {

	Logger logger = Logger.getLogger(StockReportPersistenceManager.class.getName());

	@SuppressWarnings("unchecked")
	public List<ProductStockVO> getAgencyProductStockByDateRangeAndProductType(long agencyId, long fromDate,
			long toDate, long productCode) throws BusinessException {

		List<ProductStockVO> consolidatedList = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String pcode = "";
			if (productCode < 13) {
				pcode = "prod_code";
			} else {
				pcode = "prod_id";
			}

			Query<AgencyStockDataDO> query = session
					.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted = ?2 " + " and " + pcode
							+ " = ?3 and trans_date between ?4 and ?5 order by trans_date , created_date");
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			if (productCode < 13) {
				query.setParameter(3, (int) productCode);
			} else {
				query.setParameter(3, productCode);
			}
			query.setParameter(4, fromDate);
			query.setParameter(5, toDate);

			List<AgencyStockDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (AgencyStockDataDO dataDO : result) {
					ProductStockVO psvo = new ProductStockVO();
					int sqty = dataDO.getFulls();
					int cs = dataDO.getCs_fulls();
					int es = dataDO.getCs_emptys();

					psvo.setCreatedDate(dataDO.getCreated_date());
					psvo.setQuantity(sqty);
					psvo.setCurrentStock(cs);
					psvo.setEmptyStock(es);
					psvo.setTtype(dataDO.getTrans_type());
					consolidatedList.add(psvo);
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
		return consolidatedList;
	}

	@SuppressWarnings("unchecked")
	public int getAgencyOpeningStockByDateRangeAndProductType(long agencyId, long fromDate, long toDate,
			long productCode) throws BusinessException {

		Session session = HibernateUtil.getSessionFactory().openSession();
		int ops = 0;
		try {
			String pcode = (productCode < 13) ? "prod_code" : "prod_id";

			// FROM AGENCY_STOCK_DATA WHERE CREATED_BY=12345678 AND DELETED = 0 AND
			// PROD_CODE=1 AND TRANS_DATE <= 1528655400000 ORDER BY TRANS_DATE DESC,
			// CREATED_DATE DESC LIMIT 1;
			
//			Query<AgencyStockDataDO> query = session
//					.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted != ?2 and " + pcode
//							+ " = ?3 " + "  and trans_date < ?4 order by trans_date desc, created_date desc")
//					.setMaxResults(1);
//			query.setParameter(1, agencyId);
//			query.setParameter(2, 3);
//			if (productCode < 13)
//				query.setParameter(3, (int) productCode);
//			else
//				query.setParameter(3, productCode);
//			query.setParameter(4, fromDate);
			
			Query<AgencyStockDataDO> query = session
					.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and " + pcode
							+ " = ?3 " + "  and trans_date < ?4 order by trans_date desc, created_date desc")
					.setMaxResults(1);
			query.setParameter(1, agencyId);
			query.setParameter(2, 0);
			if (productCode < 13)
				query.setParameter(3, (int) productCode);
			else
				query.setParameter(3, productCode);
			query.setParameter(4, fromDate);

			List<AgencyStockDataDO> result = query.getResultList();
			if (result.size() > 0) {
				for (AgencyStockDataDO dataDO : result) {
					ops = dataDO.getCs_fulls();
				}
			} else {
//				Query<AgencyStockDataDO> dQuery = session
//						.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted != ?2 and " + pcode
//								+ " = ?3 " + "  and trans_date = ?4 order by trans_date desc, created_date")
//						.setMaxResults(1);
//				dQuery.setParameter(1, agencyId);
//				dQuery.setParameter(2, 3);
//				if (productCode < 13)
//					dQuery.setParameter(3, (int) productCode);
//				else
//					dQuery.setParameter(3, productCode);
//				dQuery.setParameter(4, fromDate);
				
				Query<AgencyStockDataDO> dQuery = session
						.createQuery("from AgencyStockDataDO where created_by = ?1 and deleted = ?2 and " + pcode
								+ " = ?3 " + "  and trans_date = ?4 order by trans_date desc, created_date")
						.setMaxResults(1);
				dQuery.setParameter(1, agencyId);
				dQuery.setParameter(2, 0);
				if (productCode < 13)
					dQuery.setParameter(3, (int) productCode);
				else
					dQuery.setParameter(3, productCode);
				dQuery.setParameter(4, fromDate);

				result = dQuery.getResultList();
				if (result.size() > 0) {
					for (AgencyStockDataDO dataDO : result) {
						ops = dataDO.getCs_fulls();
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
		return ops;
	}
}
