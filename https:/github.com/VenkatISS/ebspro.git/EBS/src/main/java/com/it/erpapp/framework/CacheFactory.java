package com.it.erpapp.framework;

import java.util.List;

import com.it.erpapp.cache.CacheManager;
import com.it.erpapp.framework.model.CashTransEnumDO;
import com.it.erpapp.framework.model.ProductCategoryDO;
import com.it.erpapp.framework.model.StatutoryItemDO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class CacheFactory {

	
	
	public List<ProductCategoryDO> getProductCatogoryData() throws BusinessException{
		return CacheManager.getProductCategoryDataList();
	}

	public List<StatutoryItemDO> getStatutoryItemsData() throws BusinessException{
		return CacheManager.getStatutoryItemDataList();
	}
	
	public List<ProductCategoryDO> getCylinderTypesData() throws BusinessException {
		return CacheManager.getCylinderTypesDataList();
	}

	public List<ProductCategoryDO> getARBTypesData() throws BusinessException {
		return CacheManager.getARBTypesDataList();
	}

	public List<ProductCategoryDO> getServiceTypesData() throws BusinessException {
		return CacheManager.getServiceTypesDataList();
	}
	public List<CashTransEnumDO> getCashTransEnumData() throws BusinessException{
		return CacheManager.getCashTransEnumDataList();
	}

}
