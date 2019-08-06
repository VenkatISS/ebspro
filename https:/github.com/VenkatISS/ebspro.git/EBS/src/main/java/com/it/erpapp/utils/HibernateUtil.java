package com.it.erpapp.utils;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.it.erpapp.framework.model.AccountActivationDO;
import com.it.erpapp.framework.model.AgencyCDRSerialNODO;
import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.framework.model.AgencyCashDataDO;
import com.it.erpapp.framework.model.AgencyDO;
import com.it.erpapp.framework.model.AgencyDetailsDO;
import com.it.erpapp.framework.model.AgencyPRSerialNODO;
import com.it.erpapp.framework.model.AgencySISerialNODO;
import com.it.erpapp.framework.model.AgencySerialNosDO;
import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.BankDataDO;
import com.it.erpapp.framework.model.CVODataDO;
import com.it.erpapp.framework.model.CashTransEnumDO;
import com.it.erpapp.framework.model.CreditLimitsDataDO;
import com.it.erpapp.framework.model.ExpenditureDataDO;
import com.it.erpapp.framework.model.FleetDataDO;
import com.it.erpapp.framework.model.GSTPaymentDetailsDO;
import com.it.erpapp.framework.model.GSTPaymentsDataDO;
import com.it.erpapp.framework.model.PartnerInfoDO;
import com.it.erpapp.framework.model.PartnerTranxDO;
import com.it.erpapp.framework.model.ProductCategoryDO;
import com.it.erpapp.framework.model.StaffDataDO;
import com.it.erpapp.framework.model.StatutoryDataDO;
import com.it.erpapp.framework.model.StatutoryItemDO;
import com.it.erpapp.framework.model.finreports.BalanceSheetDO;
import com.it.erpapp.framework.model.finreports.BalanceSheetDetailsDO;
import com.it.erpapp.framework.model.finreports.CapitalAccountReportDO;
import com.it.erpapp.framework.model.finreports.DepreciationDO;
import com.it.erpapp.framework.model.finreports.PAndLAccountDO;
import com.it.erpapp.framework.model.finreports.PAndLAccountDetailsDO;
import com.it.erpapp.framework.model.pps.ARBDataDO;
import com.it.erpapp.framework.model.pps.ARBPriceDataDO;
import com.it.erpapp.framework.model.pps.AreaCodeDataDO;
import com.it.erpapp.framework.model.pps.EquipmentDataDO;
import com.it.erpapp.framework.model.pps.RefillPriceDataDO;
import com.it.erpapp.framework.model.pps.ServicesDataDO;
import com.it.erpapp.framework.model.pps.ServicesGSTData;
import com.it.erpapp.framework.model.transactions.ARBPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.AgencyBOMDO;
import com.it.erpapp.framework.model.transactions.BOMItemsDO;
import com.it.erpapp.framework.model.transactions.DeletedRecordsDO;
import com.it.erpapp.framework.model.transactions.OtherPurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.PurchaseInvoiceDO;
import com.it.erpapp.framework.model.transactions.others.AssetDataDO;
import com.it.erpapp.framework.model.transactions.others.BankTranxsDataDO;
import com.it.erpapp.framework.model.transactions.others.CreditNotesDO;
import com.it.erpapp.framework.model.transactions.others.DebitNotesDO;
import com.it.erpapp.framework.model.transactions.others.PaymentsDataDO;
import com.it.erpapp.framework.model.transactions.others.ReceiptsDataDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.ARBSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.COMSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.DEProductsDetailsViewDO;
import com.it.erpapp.framework.model.transactions.sales.DEProductsViewDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.DOMSalesInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.DeliveryChallanDO;
import com.it.erpapp.framework.model.transactions.sales.DeliveryChallanDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDO;
import com.it.erpapp.framework.model.transactions.sales.NCDBCInvoiceDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDO;
import com.it.erpapp.framework.model.transactions.sales.PurchaseReturnDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.QuotationDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.QuotationsDO;
import com.it.erpapp.framework.model.transactions.sales.RCDataDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDO;
import com.it.erpapp.framework.model.transactions.sales.SalesReturnDetailsDO;
import com.it.erpapp.framework.model.transactions.sales.TVDataDO;
import com.it.erpapp.framework.model.vos.AgencyVO;

public class HibernateUtil {
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			synchronized (HibernateUtil.class) {
				if (sessionFactory == null) {
					try {
						// Create registry builder
						StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

						// Hibernate settings equivalent to hibernate.cfg.xml's properties
						//Map<String, String> settings = new HashMap<>();
						Map<String, Object> settings = new HashMap<>();

						settings.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
						settings.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/erpappdb");
						
//						settings.put("hibernate.connection.url", "jdbc:mysql://192.168.0.131:3306/erpappdb");
						
						settings.put("hibernate.connection.username", "root");
						settings.put("hibernate.connection.password", "root");
//						settings.put("hibernate.connection.password", "Jagan@321");
						settings.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
						settings.put("hibernate.show_sql", "true");
						settings.put("hibernate.id.new_generator_mappings", "false");
						settings.put("hibernate.connection.autocommit", "false");
						settings.put("hibernate.connection.autoReconnect", "true");
						// 'autoReconnect=true'
						
						// c3p0 configuration
                        settings.put("hibernate.c3p0.min_size",  5);				//Initial number of database connections
                        settings.put("hibernate.c3p0.max_size",  20);				//Maximum number of database connections to open
//						settings.put("hibernate.c3p0.acquire_increment",  1);		//Number of connections acquired at a time when pool is exhausted  - default is 3
                        settings.put("hibernate.c3p0.timeout",  1800);				//Connection idle time
                        settings.put("hibernate.c3p0.max_statements",  150);		//PreparedStatement cache size
                        settings.put("hibernate.c3p0.initialPoolSize",  5);			//No. of connections a pool will try to acquire upon startup.  Should be between minpoolsize and maxpoolsize.
						

						// Apply settings
						registryBuilder.applySettings(settings);

						// Create registry
						registry = registryBuilder.build();

						// Create MetadataSources
						MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(AgencyDO.class)
								.addAnnotatedClass(AccountActivationDO.class)
								.addAnnotatedClass(AgencyDetailsDO.class)
								.addAnnotatedClass(AgencySISerialNODO.class)
								.addAnnotatedClass(AgencyCDRSerialNODO.class)
								.addAnnotatedClass(AgencyPRSerialNODO.class)
								.addAnnotatedClass(AgencySerialNosDO.class)
								.addAnnotatedClass(AgencyVO.class)
								.addAnnotatedClass(GSTPaymentsDataDO.class)
								.addAnnotatedClass(GSTPaymentDetailsDO.class)
								.addAnnotatedClass(AgencyCashDataDO.class)
								.addAnnotatedClass(CashTransEnumDO.class)
								.addAnnotatedClass(ProductCategoryDO.class)
								.addAnnotatedClass(StatutoryDataDO.class)
								.addAnnotatedClass(StatutoryItemDO.class)
								.addAnnotatedClass(FleetDataDO.class)
								.addAnnotatedClass(ExpenditureDataDO.class)
								.addAnnotatedClass(CreditLimitsDataDO.class)
								.addAnnotatedClass(CVODataDO.class)
								.addAnnotatedClass(BankDataDO.class)
								.addAnnotatedClass(AreaCodeDataDO.class)
								.addAnnotatedClass(StaffDataDO.class)
								.addAnnotatedClass(EquipmentDataDO.class)
								.addAnnotatedClass(ARBDataDO.class)
								.addAnnotatedClass(ServicesDataDO.class)
								.addAnnotatedClass(ServicesGSTData.class)
								.addAnnotatedClass(AgencyBOMDO.class)
								.addAnnotatedClass(BOMItemsDO.class)
								.addAnnotatedClass(RefillPriceDataDO.class)
								.addAnnotatedClass(ARBPriceDataDO.class)
								.addAnnotatedClass(PurchaseInvoiceDO.class)
								.addAnnotatedClass(ARBPurchaseInvoiceDO.class)
								.addAnnotatedClass(OtherPurchaseInvoiceDO.class)
								.addAnnotatedClass(QuotationsDO.class)
								.addAnnotatedClass(QuotationDetailsDO.class)
								.addAnnotatedClass(DeliveryChallanDO.class)
								.addAnnotatedClass(DeliveryChallanDetailsDO.class)
								.addAnnotatedClass(BankTranxsDataDO.class)
								.addAnnotatedClass(DOMSalesInvoiceDO.class)
								.addAnnotatedClass(DOMSalesInvoiceDetailsDO.class)
								.addAnnotatedClass(COMSalesInvoiceDO.class)
								.addAnnotatedClass(COMSalesInvoiceDetailsDO.class)
								.addAnnotatedClass(ARBSalesInvoiceDO.class)
								.addAnnotatedClass(ARBSalesInvoiceDetailsDO.class)
								.addAnnotatedClass(ReceiptsDataDO.class)
								.addAnnotatedClass(PaymentsDataDO.class)
								.addAnnotatedClass(AssetDataDO.class)
								.addAnnotatedClass(CreditNotesDO.class)
								.addAnnotatedClass(DebitNotesDO.class)
								.addAnnotatedClass(SalesReturnDO.class)
								.addAnnotatedClass(SalesReturnDetailsDO.class)
								.addAnnotatedClass(PurchaseReturnDO.class)
								.addAnnotatedClass(PurchaseReturnDetailsDO.class)
								.addAnnotatedClass(TVDataDO.class)
								.addAnnotatedClass(RCDataDO.class)
								.addAnnotatedClass(NCDBCInvoiceDO.class)
								.addAnnotatedClass(NCDBCInvoiceDetailsDO.class)
								.addAnnotatedClass(DEProductsViewDO.class)
								.addAnnotatedClass(DEProductsDetailsViewDO.class)
								.addAnnotatedClass(PartnerInfoDO.class)
								.addAnnotatedClass(PartnerTranxDO.class)
								.addAnnotatedClass(DepreciationDO.class)
								.addAnnotatedClass(CapitalAccountReportDO.class)
								.addAnnotatedClass(PAndLAccountDO.class)
								.addAnnotatedClass(PAndLAccountDetailsDO.class)
								.addAnnotatedClass(BalanceSheetDO.class)
								.addAnnotatedClass(BalanceSheetDetailsDO.class)
								.addAnnotatedClass(AgencyStockDataDO.class)
								.addAnnotatedClass(AgencyCVOBalanceDataDO.class)
								.addAnnotatedClass(DeletedRecordsDO.class);

						// Create Metadata
						Metadata metadata = sources.getMetadataBuilder().build();

						// Create SessionFactory
						sessionFactory = metadata.getSessionFactoryBuilder().build();

					} catch (Exception e) {
						e.printStackTrace();
						if (registry != null) {
							StandardServiceRegistryBuilder.destroy(registry);
						}
					}
				}
			}
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}