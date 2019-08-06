package com.it.erpapp.framework.managers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.it.erpapp.framework.bos.AgencyCVOBalanceDataBO;
import com.it.erpapp.framework.bos.AgencyStockDataBO;
import com.it.erpapp.framework.model.AgencyCVOBalanceDataDO;
import com.it.erpapp.framework.model.AgencyStockDataDO;
import com.it.erpapp.framework.model.BankDataDO;
import com.it.erpapp.framework.model.CVODataDO;
import com.it.erpapp.framework.model.FleetDataDO;
import com.it.erpapp.framework.model.StaffDataDO;
import com.it.erpapp.framework.model.StatutoryDataDO;
import com.it.erpapp.framework.model.pps.ARBDataDO;
import com.it.erpapp.framework.model.pps.AreaCodeDataDO;
import com.it.erpapp.framework.model.pps.EquipmentDataDO;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.mailservices.MailUtility;
import com.it.erpapp.response.MessageObject;
import com.it.erpapp.systemservices.exceptions.BusinessException;
import com.it.erpapp.utils.HibernateUtil;

	public class MyProfilePersistanceManager {

		Logger logger = Logger.getLogger(MyProfilePersistanceManager.class.getName());
	
		public AgencyVO getProfileData(long agencyId) throws BusinessException {
			AgencyVO agencyVO = new AgencyVO();
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				@SuppressWarnings("unchecked")
				List<AgencyVO> dresult = session.createQuery("from AgencyVO where agency_code = ?1")
				.setParameter(1, agencyId)
				.getResultList();
				if(dresult.size()==1) {
					agencyVO = dresult.get(0);
				}
			}catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
				throw new BusinessException(e.getMessage());
			}finally {
				session.clear();
				session.close();
			}
			return agencyVO;
		}

		@SuppressWarnings("rawtypes")
		public void updateProfileData(long agencyId,Map<String, String> requestvals) throws BusinessException {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction tx = null;
			String cpname = requestvals.get("cpname");
			String cdmobile = requestvals.get("cdmobile"); 
			String clnno = requestvals.get("clnno");
//			String cemailId = requestvals.get("cemailId");
			String cOffcAddr = requestvals.get("cOffcAddr");
				
			try {
				tx = session.beginTransaction();
				/*Query query = session.createQuery("UPDATE AgencyDO SET emailId='"+cemailId+"' WHERE agencyCode='"+agencyId+"'");
				query.executeUpdate();*/
				Query query1 = session.createQuery(
					"UPDATE AgencyDetailsDO SET contactPerson='"+cpname+"', dealerMobile='"+cdmobile+"',officeLandline='"+clnno+"', address='"+cOffcAddr+"' WHERE createdBy='"+agencyId+"'");
				query1.executeUpdate();
				tx.commit();
			}catch(Exception e) {
				try {
					if (tx != null) tx.rollback();
				}catch (HibernateException e1) {
					logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MyProfilePersistanceManager --> updateProfileData  is not succesful");
				}
				e.printStackTrace();
				logger.error(e);
				throw new BusinessException(e.getMessage());
			}finally {
				session.clear();
				session.close();
			}
		}
	
		//SEND OTP FOR UPDATING REGISTERD EMAILID
		@SuppressWarnings("rawtypes")
		public void sendMailAndUpdateRegisterdEmailId(long agencyId,Map<String, String> requestvals) throws BusinessException {
			Session session = HibernateUtil.getSessionFactory().openSession();
			String oldEmailId = requestvals.get("oldEmailId");
			String newEmailId = requestvals.get("newEmailId");
			String mNewStatus = ""; String mOldStatus = "";
    	
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				Query query = session.createQuery("from AgencyDO where agency_code = ?1 AND email_id=?2 ");
				query.setParameter(1, agencyId);
				query.setParameter(2, oldEmailId );
				List dataDo=query.getResultList();
				if(dataDo.size()>0) {
    			Query chqQuery = session.createQuery("from AgencyDO where email_id=?1 ");
    			chqQuery.setParameter(1, newEmailId);
        		List chDataDo = chqQuery.getResultList();
        		if(chDataDo.size()>0) {
        			throw new BusinessException("THIS EMAIL ID HAS ALREADY BEEN IN USE WITH DIFFERENT ACCOUNT.");
        		}else {
        			MailUtility mailUtility = new MailUtility();
//    				mNewStatus = mailUtility.sendingConfEmailsForEmailIdChange(agencyId,newEmailId,"new",oldEmailId,newEmailId);
        			mNewStatus = mailUtility.sendingIntimationEmailsForEmailIdChange(agencyId,newEmailId,"new",oldEmailId,newEmailId);
        		}
    		}
    		
				if(mNewStatus.equalsIgnoreCase("SUCCESS")) {
					MailUtility mailUtility = new MailUtility();
					mOldStatus = mailUtility.sendingIntimationEmailsForEmailIdChange(agencyId,oldEmailId,"old",oldEmailId,newEmailId);
				}else if((mNewStatus.equalsIgnoreCase("ERROR")) && (mOldStatus.equalsIgnoreCase("ERROR"))) {
					throw new BusinessException("CANNOT SEND EMAILS . PLEASE VERIFY YOUR NEW EMAIL ID OR TRY AGAIN LATER.");
				}else if(mNewStatus.equalsIgnoreCase("ERROR")) {
    			throw new BusinessException("CANNOT SEND EMAIL TO NEW EMAIL ID . PLEASE VERIFY YOUR NEW EMAIL ID OR TRY AGAIN LATER.");
    		}else if(mOldStatus.equalsIgnoreCase("ERROR")) {
    			throw new BusinessException("CANNOT SEND EMAIL TO OLD EMAIL ID . PLEASE VERIFY YOUR NEW EMAIL ID OR TRY AGAIN LATER.");
    		}
    		
    	}catch(BusinessException be) {
    		try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> MyProfilePersistanceManager --> sendMailAndUpdateRegisterdEmailId  is not succesful");
			}
			logger.error(be.getCause());
			logger.error(be);
			be.printStackTrace();			
			throw new BusinessException(be.getExceptionMessage());
    	}catch(Exception e) {
    		try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> MyProfilePersistanceManager --> sendMailAndUpdateRegisterdEmailId  is not succesful");
			}
			logger.error(e.getCause());
			logger.error(e);
			e.printStackTrace();

    	}finally {
    		session.clear();
    		session.close();
    	}
    }
    
  // UPDATING REGISTERD EMAILID
    @SuppressWarnings("rawtypes")
    public void updateRegisterdEmailId(long agencyId,Map<String, String> requestvals) throws BusinessException {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	String oldEmailId = requestvals.get("oldEmailId");
    	String newEmailId = requestvals.get("newEmailId");
    	Transaction tx = null;
    	try {
    		tx = session.beginTransaction();
    		Query query = session.createQuery("from AgencyDO where agency_code = ?1 AND email_id=?2 ");
    		query.setParameter(1, agencyId);
    		query.setParameter(2, oldEmailId );
    		List dataDo=query.getResultList();
    		if(dataDo.size()>0) {
    			Query upQuery = session.createQuery("UPDATE AgencyDO SET email_id= ?1 WHERE agency_code = ?2 AND email_id=?3 ");
    			upQuery.setParameter(1, newEmailId);
    			upQuery.setParameter(2, agencyId );
    			upQuery.setParameter(3, oldEmailId );
    			upQuery.executeUpdate();
    			tx.commit();
    			
    		}else {
    			throw new BusinessException("EMAIL ID HAS ALREADY BEEN UPDATED.");
    		}
    		
    	}catch(Exception e) {
    		try {
				if (tx != null)
					tx.rollback();
			} catch (HibernateException e1) {
				logger.error(
						"Transaction rollback in  com.it.erpapp.framework.managers --> MyProfilePersistanceManager --> updateRegisterdEmailId  is not succesful");
			}
			logger.error(e.getCause());
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
    	}finally {
    		session.clear();
    		session.close();
    	}
    }
    
    
	
	//savePinNumber
	@SuppressWarnings("rawtypes")
	public void savePinNumber(long agencyId,Map<String, String> requestvals) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String agencyPin = requestvals.get("agencyPin");
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(
					"UPDATE  AgencyDetailsDO SET pinNumber='"+agencyPin +"' WHERE createdBy='"+agencyId+"'");
			query.executeUpdate();
			tx.commit();
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MyProfilePersistanceManager --> savePinNumber  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}

	//changePinNumber
	@SuppressWarnings("rawtypes")
	public void changePinNumber(long agencyId,Map<String, String> requestvals) throws BusinessException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String agencyoPin = requestvals.get("agencyoPin");
		String agencynPin = requestvals.get("agencynPin");
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			
			Query query = session.createQuery("FROM AgencyDetailsDO WHERE createdBy=?1 AND pinNumber=?2 ");
			query.setParameter(1, agencyId);
			query.setParameter(2,agencyoPin );
			List dataDo=query.getResultList();
			if(dataDo.size()>0) {
				Query query1 = session.createQuery(
						"UPDATE  AgencyDetailsDO SET pinNumber='"+agencynPin +"' WHERE createdBy="+agencyId);
				query1.executeUpdate();
				tx.commit();
			}
		}catch(Exception e) {
			try {
				if (tx != null) tx.rollback();
			}catch (HibernateException e1) {
				logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MyProfilePersistanceManager --> changePinNumber  is not succesful");
			}
			logger.error(e);
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			session.clear();
			session.close();
		}
	}
	//forgot pin number
    @SuppressWarnings("rawtypes")
    public void forgotPinNumber(long agencyId,Map<String, String> requestvals,int otp) throws BusinessException {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	String emailForpin = requestvals.get("emailIdforOtp");
    	try {
 //   		Transaction tx = session.beginTransaction();
    		Query query = session.createQuery("from AgencyDO where agency_code = ?1 AND email_id=?2 ");
    		query.setParameter(1, agencyId);
    		query.setParameter(2,emailForpin );
    		List dataDo=query.getResultList();
    		if(dataDo.size()>0) {
    			MailUtility mailUtility = new MailUtility();
    			mailUtility.sendOtpThroughMail(agencyId,emailForpin,otp);
    		}
    	}catch(Exception e) {
    		e.printStackTrace();
    		logger.error(e);
    		throw new BusinessException(e.getMessage());
    	}finally {
    		session.clear();
    		session.close();
    	}
    }
    
    //uploadProfilePic
    @SuppressWarnings({ "rawtypes" })
    public void uploadprofilepicToPath(long agencyId,Part picPath) throws BusinessException, IOException, SQLException {
    	Session session = HibernateUtil.getSessionFactory().openSession();
    	Transaction tx = null;
		InputStream fin=null;
		OutputStream filepath = null;
    	try {
    		tx = session.beginTransaction();
    		fin=picPath.getInputStream();
    		if(fin!=null) {
    			filepath = new FileOutputStream(new File("E:\\Tomcat09\\webapps\\profile_pics\\"+agencyId+".jpg"));
//    			filepath = new FileOutputStream(new File("/opt/tomcat9/webapps/profile_pics/"+agencyId+".jpg"));
    			int c = 0;
    			while ((c = fin.read()) > -1) {
    				filepath.write(c);
    			}
    			Query query=session.createQuery("UPDATE  AgencyDetailsDO SET IMAGE_STATUS=?1  WHERE CREATED_BY=?2");
    			query.setParameter(1, 1);
    			query.setParameter(2, agencyId);
    			query.executeUpdate();
    			tx.commit();
    		}
    	}catch(Exception e) {
    		try {
    			if (tx != null) tx.rollback();
    		}catch (HibernateException e1) {
    			logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MyProfilePersistanceManager --> savePinNumber  is not succesful");
    		}
    		logger.error(e);
    		e.printStackTrace();
    		throw new BusinessException(e.getMessage());
    	}finally {
    		if(fin != null) { fin.close();}
    		if(filepath != null) { filepath.close();}
    		session.clear();
    		session.close();
    	}
    }
    
    //removeProfilePic
   	@SuppressWarnings("rawtypes")
	public AgencyVO removeProfilePic(long agencyId) throws BusinessException {
       	Transaction tx = null;
       	AgencyVO agencyVO = new AgencyVO();
       	Session session = HibernateUtil.getSessionFactory().openSession();
       	try {
			Path path=Paths.get("E:\\Tomcat09\\webapps\\profile_pics\\"+agencyId+".jpg");
//			Path path=Paths.get("/opt/tomcat9/webapps/profile_pics/"+agencyId+".jpg");
            Files.delete(path);
            
       		tx = session.beginTransaction();
       		Query query=session.createQuery("UPDATE  AgencyDetailsDO SET IMAGE_STATUS=?1  WHERE CREATED_BY=?2");
   			query.setParameter(1, 0);
   			query.setParameter(2, agencyId);
   			query.executeUpdate();
   			tx.commit();
       	}catch(Exception e) {
       		logger.error(e);
       		e.printStackTrace();
       		throw new BusinessException(e.getMessage());
       	}finally {
       		session.clear();
       		session.close();
       	}
       	return agencyVO;
   	}
   	
   	public String fetchProducts(ArrayList<String> delProds) {
   		String prods = "";
   		for(String d : delProds){
   			if(prods != "")
   				prods = prods +", "+d;
   			else if(prods == "")
   				prods = d;
   		}
   		return prods;
   	}
   	
   	//uploadataTodb through excel
   	@SuppressWarnings({ "rawtypes", "unused", "deprecation", "unchecked" })
   	public void uploadataTodbFromExcel(long agencyId,Part uploadedDataFile ,long effDateInFTL) throws BusinessException, IOException, SQLException, EncryptedDocumentException, InvalidFormatException {
   		Session session = HibernateUtil.getSessionFactory().openSession();
    	Transaction tx = null;
    	InputStream inputstream = null;
    	MessageObject msg=new MessageObject(9008, "upload excel", "ERROR");

    	inputstream=uploadedDataFile.getInputStream();

//    	tx = session.beginTransaction();
    	if(uploadedDataFile != null){

    		System.out.println(uploadedDataFile);

    		//Workbook workbook = WorkbookFactory.create(new File("/home/venkat/Pictures/EBS.xls"));
    		Workbook workbook = WorkbookFactory.create(inputstream);

    		try {
    			tx = session.beginTransaction();
    			int insertStatus=0;
    			for(int i=0;i<workbook.getNumberOfSheets();i++) {
    				System.out.println("sheet name is:"+workbook.getSheetName(i));
    				Sheet sheet = workbook.getSheetAt(i);
    				
    				//Staff Master
    				if(sheet.getSheetName().equals("Staff Master")){
    					try {
    						Iterator<Row> rowIterator = sheet.rowIterator();
    						rowIterator.next();
    						while (rowIterator.hasNext()) {
    							Row row = rowIterator.next();
    							StaffDataDO staffDataDO=new StaffDataDO();
    							Cell empc=row.getCell(0);
                				Cell empnam=row.getCell(1);
                				Cell empdob=row.getCell(2);
                				Cell empdsn=row.getCell(3);
                				Cell emprole=row.getCell(4);
                				String empname=null;
                				String Design = null;
                				String role = null;
                				Date dob = null;
                				String empcode = null;
                				
                				if(empc!=null && empc.getCellTypeEnum()!=CellType.BLANK){
                					if(empc.getCellTypeEnum() == CellType.NUMERIC){
                						empcode = Integer.toString((int)empc.getNumericCellValue());
                					}else if(empc.getCellTypeEnum() == CellType.STRING){
                						empcode = empc.getStringCellValue();
                					}else {
                						empcode = empc.getStringCellValue();
                					}
                				}

                				if(empnam!=null && empnam.getCellTypeEnum()!=CellType.NUMERIC && empnam.getCellTypeEnum()!=CellType.BLANK)
                					empname = row.getCell(1).getStringCellValue();
                				
                				if(empdsn!=null && empdsn.getCellTypeEnum()!=CellType.NUMERIC && empdsn.getCellTypeEnum()!=CellType.BLANK)
                					Design=empdsn.getStringCellValue();
                				if(emprole!=null && emprole.getCellTypeEnum()!=CellType.NUMERIC  && emprole.getCellTypeEnum()!=CellType.BLANK)
                					role=emprole.getStringCellValue();
                				
                				if(empdob!=null && empdob.getCellTypeEnum()!=CellType.STRING  && empdob.getCellTypeEnum()!=CellType.BLANK)
                					dob = row.getCell(2).getDateCellValue();

                				if(emprole!=null && emprole.getCellTypeEnum()!=CellType.BLANK){
                					if(role.equals("DELIVERY STAFF"))
                						staffDataDO.setRole(0);
                					else if(role.equals("SHOWROOM STAFF"))
                						staffDataDO.setRole(1);
                					else if(role.equals("GODOWN STAFF"))
                						staffDataDO.setRole(2);
                					else if(role.equals("INSPECTOR"))
                						staffDataDO.setRole(3);
                					else if(role.equals("MECHANIC"))
                						staffDataDO.setRole(4);
                					else if(role.equals("OTHERS"))
                						staffDataDO.setRole(5);
                				}
                				if(empcode!=null && empname!=null && Design!=null && role!=null && dob!=null) {
                					staffDataDO.setEmp_code(empcode);
                					staffDataDO.setEmp_name(empname);
                					staffDataDO.setDob(dob);
                					staffDataDO.setDesignation(Design);
                					staffDataDO.setCreated_by(agencyId);
                					staffDataDO.setCreated_date(System.currentTimeMillis());
                					staffDataDO.setVersion(1);
                					staffDataDO.setDeleted(0);
                					//doList.add(staffDataDO);
                					session.save(staffDataDO);
                					insertStatus++;
                				}
                			}
                		}catch (Exception e) {
                			try {
                				if (tx != null)
                					tx.rollback();
                			}catch (HibernateException e1) {
                				logger.error(
                						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> saveAgencyBOMData  is not succesful");
                			}
                			logger.error(e);
                			e.printStackTrace();
                			
                			String s=null;
                			System.out.println("get cause:"+e.getMessage());
   	                        
                			String emsg=e.getMessage();
                			if(emsg.contains("NullPointerException"))
                				s="Unable to upload staff data with null values";
                			else if(emsg.contains("NumberFormatException"))
                				s="Check the column values Staff data before upload ";
                			else if(emsg.contains("Cannot get a STRING value from a NUMERIC cell"))
                				s="Check the column values types in Staff data before upload ";
                			else
                				s="Please check staff data sheet columns and then upload it.";
                			//msg.setMessageText(s);
                			throw new BusinessException(s);
                		}
                	}
                	
                	//  Statutory Master
                	if(sheet.getSheetName().equals("Statutory Master")) {

                		List<StatutoryDataDO> satdoList = new ArrayList<>();
            			Set<Integer> satunq=new HashSet<>();
            			List<Integer> satdup=new ArrayList<>();

                		try {
                			Query<StatutoryDataDO> query = session.createQuery("from StatutoryDataDO where created_by = ?1 and deleted = ?2 ");
                			query.setParameter(1, agencyId);
                			query.setParameter(2, 0);
                			List<StatutoryDataDO> result = query.getResultList();
                            if (result.size() > 0) {
                            	for (StatutoryDataDO doObj : result) {
                            		satdoList.add(doObj);
                            		if(doObj.getItem_type()==4 || doObj.getItem_type()==5 || doObj.getItem_type()==6)
                            			satdup.add(doObj.getItem_type());
                            		else
                            			satunq.add(doObj.getItem_type());
                            	}
                            }
                		}catch (Exception e) {
                			logger.error(e);
                            e.printStackTrace();
                            throw new BusinessException(e.getMessage());
                		}

                		try {
                			Iterator<Row> rowIterator = sheet.rowIterator();
                			rowIterator.next();
                			while (rowIterator.hasNext()) {
                				Row row = rowIterator.next();
                				Cell item=row.getCell(0);
                				String itemtype=null;
                				int satunqv=0;
                				if(item!=null && item.getCellTypeEnum()!=CellType.BLANK) {
                					StatutoryDataDO satDataDO=new StatutoryDataDO();
                                	itemtype = row.getCell(0).getStringCellValue();
                                	Cell itemValue=row.getCell(5);
                                	int itemType=0;
                                	String itemString=null;
                                	int itemDropdown=0;
                                	Date validupto=null;
                                	String remind=null;
                                	String remarks=null;
                                	String refno=null;
                                	
                                	if(itemValue!=null && itemValue.getCellTypeEnum()!=CellType.BLANK) {
                                		switch(itemValue.getCachedFormulaResultType()){
                                			case Cell.CELL_TYPE_NUMERIC:itemType=(int) itemValue.getNumericCellValue();
                                			break;
                                			case Cell.CELL_TYPE_STRING:itemString= itemValue.getStringCellValue();
                                			break;
                                		}
                                	/*if(itemType!=4 || itemType!=5 || itemType!=6){
                                        if(!satunq.contains(itemType))
                                        	satunq.add(itemType);
                                	}
                                	if(itemType==4 || itemType==5 || itemType==6)
                                	// if(!satdup.contains(itemType)){
                                    	satdup.add(itemType);
										//satunqv=1;
                                	 */
                                	}

                                	if(satdoList.size()==0) {
                                		if(itemtype.equals("DISTRIBUTORSHIP AGREEMENT")){
                                			itemDropdown=1;
                                			//satDataDO.setItem_type(1);
                                		}else if(itemtype.equals("EXPLOSIVE LICENCE")){
                                			itemDropdown=2;
                                			//satDataDO.setItem_type(2);
                                		}else if(itemtype.equals("CIVIL SUPPLY LICENCE")){
                                			itemDropdown=3;
                                			//	satDataDO.setItem_type(3);
                                		}else if(itemtype.equals("PLATFORM WEIGHING SCALE")){
                                			itemDropdown=4;
                                			//	satDataDO.setItem_type(4);
                                        }else if(itemtype.equals("PORTABLE WEIGHING SCALE")){
                                        	itemDropdown=5;
                                        	satDataDO.setItem_type(5);
                                        }else if(itemtype.equals("FIRE EXTINGHUISHER")){
                                        	itemDropdown=6;
                                        	//satDataDO.setItem_type(6);
                                        }else if(itemtype.equals("LABOUR LICENCE")){
                                        	itemDropdown=7;
                                        	//satDataDO.setItem_type(7);
                                        }
                                		
                                		if(itemType!=4 && itemType!=5 && itemType!=6){
                                			if(satunq.contains(itemDropdown)){
                                				//empty
                                			}else{
                                				satDataDO.setItem_type(itemDropdown);
                                				satunq.add(itemDropdown);
                                			}
                                		}else if(itemDropdown==4 || itemDropdown==5 || itemDropdown==6){
                                			if(satdup.contains(itemDropdown) || !satdup.contains(itemDropdown)){
                                				satDataDO.setItem_type(itemDropdown);
                                			}
                                		}
                                	}else{
                                		if(itemType!=4 && itemType!=5 && itemType!=6){
                                			if(satunq.contains(itemType)){
                                				//empty
                                			}else{
                                				satDataDO.setItem_type(itemType);
                                				satunq.add(itemType);
                                			}
                                		}else if(itemType==4 || itemType==5 || itemType==6){
                                			if(satdup.contains(itemType) || !satdup.contains(itemType)){
                                				satDataDO.setItem_type(itemType);
                                			}
                                		}
                                	}
                                	                              	
                                	/*else {
                                		for(StatutoryDataDO sdata:satdoList) {
                                			if(itemType!=sdata.getItem_type() && itemtype.equals("DISTRIBUTORSHIP AGREEMENT")){
                                				itemDropdown=1;
                                			satDataDO.setItem_type(itemDropdown);
                                			break;
                                			}
                                			else if(itemType!=sdata.getItem_type() && itemtype.equals("EXPLOSIVE LICENCE")){
                                				itemDropdown=2;
                                    			satDataDO.setItem_type(itemDropdown);
                                    			break;
                                			}
                                			else if(itemType!=sdata.getItem_type() && itemtype.equals("CIVIL SUPPLY LICENCE")){
                                				itemDropdown=3;
                                    			satDataDO.setItem_type(itemDropdown);
                                    			break;
                                			}
                                			else if(itemType!=sdata.getItem_type() && itemtype.equals("PLATFORM WEIGHING SCALE")){
                                				itemDropdown=4;
                                    			satDataDO.setItem_type(itemDropdown);
                                    			break;
                                			}
                                			else if(itemType!=sdata.getItem_type() && itemtype.equals("PORTABLE WEIGHING SCALE")){
                                				itemDropdown=5;
                                    			satDataDO.setItem_type(itemDropdown);
                                    			break;
                                			}
                                			else if(itemType!=sdata.getItem_type() && itemtype.equals("FIRE EXTINGHUISHER")){
                                				itemDropdown=6;
                                			satDataDO.setItem_type(itemDropdown);
                                			break;
                                		}
                                			else if(itemType!=sdata.getItem_type() && itemtype.equals("LABOUR LICENCE")){
                                				itemDropdown=7;
                                    			satDataDO.setItem_type(itemDropdown);
                                    			break;
                                			}

                                		}
									}*/

                                	Cell datecell=row.getCell(2);
                                	if(datecell!=null && datecell.getCellTypeEnum()!=CellType.BLANK)
                                		validupto =row.getCell(2).getDateCellValue();

                                	Cell rmndcell=row.getCell(3);
                                	if(rmndcell!=null && rmndcell.getCellTypeEnum()!=CellType.BLANK)
                                		remind=row.getCell(3).getStringCellValue();

                                	Cell remarkscell=row.getCell(4);
                                	if(remarkscell!=null && remarkscell.getCellTypeEnum()!=CellType.BLANK)
                                		remarks=row.getCell(4).getStringCellValue();
                                	
                                	Cell refcell = row.getCell(1);
                                	if(refcell!=null && refcell.getCellTypeEnum()!=CellType.BLANK)
                                		refcell.setCellType(CellType.STRING);
                                        
                                	refno=refcell.getStringCellValue();

                                	if(remind.equals("7 DAYS"))
                                		satDataDO.setRemind_before(1);
                                	else if(remind.equals("15 DAYS"))
                                		satDataDO.setRemind_before(2);
                                	else if(remind.equals("30 DAYS"))
                                		satDataDO.setRemind_before(3);
                                	else if(remind.equals("60 DAYS"))
                                		satDataDO.setRemind_before(4);

                                	System.out.println("satunqv:"+satunqv+"itemtype:"+itemtype+"refno:"+refno+"remarks:"+remarks+"validupto:"+validupto+"itemDropdown:"+itemDropdown+"satDataDO.getRemind_before():"+satDataDO.getRemind_before());

                                	if(itemtype!=null && refno!=null && satDataDO.getItem_type()!=0 && remarks!=null && validupto!=null && satDataDO.getRemind_before()!=0) {
                                		//   satDataDO.setItem_type(itemDropdown);
                                		satDataDO.setItem_ref_no(refno);
                                		satDataDO.setValid_upto(validupto);
                                		satDataDO.setRemarks(remarks);
                                		satDataDO.setCreated_by(agencyId);
                                		satDataDO.setCreated_date(System.currentTimeMillis());
                                		satDataDO.setVersion(1);
                                		satDataDO.setDeleted(0);
                                		session.save(satDataDO);
                                		insertStatus++;
                                	}
                				}
                			}
                		}catch(Exception e) {
                			try {
                				if (tx != null)
                					tx.rollback();
                			}
                			catch (HibernateException e1) {
                				logger.error(
                						"Transaction rollback in  com.it.erpapp.framework.managers --> MyProfilePersistenceManager --> saveStatutoryData  is not succesful");
                			}
                			logger.error(e);
                			e.printStackTrace();
                		}
                	}
                	
                	
                	// Equipment Master
                	if((sheet.getSheetName()).equals("Equipment Master")) {
                		List<EquipmentDataDO> doList = new ArrayList<>();
                		Set<Integer> eqpdata =new HashSet<Integer>();
                		
                		ArrayList<String> delProdsArrList = new ArrayList<>();
                		ArrayList<Integer> delProdsList = new ArrayList<>();
                		
                		String errFlag = "NO";
                		
                		try {
//                            Query<EquipmentDataDO> query = session.createQuery("from EquipmentDataDO where created_by = ?1 and deleted = ?2 ");
//                            query.setParameter(1, agencyId);
//                            query.setParameter(2, 0);
                			
                			Query<EquipmentDataDO> query = session.createQuery("from EquipmentDataDO where created_by = ?1");
                			query.setParameter(1, agencyId);
                			
                            List<EquipmentDataDO> result = query.getResultList();
                            if (result.size() > 0) {
                            	for (EquipmentDataDO doObj : result) {
                            		if(doObj.getDeleted() == 0) {
                            			doList.add(doObj);
                            			eqpdata.add(doObj.getProd_code());
                            		}else if(doObj.getDeleted() == 1){
                            			String prodName = "";
                            			delProdsList.add(doObj.getProd_code());
                            			errFlag = "YES";
                            		}
                            	}
                            }
                            
                            //  Sheet sheet1 = workbook.getSheetAt(0);
                            Iterator<Row> rowIterator = sheet.rowIterator();
                            rowIterator.next();
                            //Set<Integer> eqpdata =new HashSet<Integer>();

                            while (rowIterator.hasNext()) {
                            	Row row = rowIterator.next();
                            	EquipmentDataDO eqpDataDO=new EquipmentDataDO();
                            	Cell prod=row.getCell(0);
                            	if(prod!=null && prod.getCellTypeEnum()!=CellType.BLANK) {
                            		prod.setCellType(CellType.STRING);
                            		String prod_code=prod.getStringCellValue();

                            		Cell units=row.getCell(1);
                            		Cell gst=row.getCell(2);
                            		Cell hsncode=row.getCell(3);
                            		Cell sdeposit=row.getCell(4);
                            		Cell fulls=row.getCell(5);
                            		Cell emtys=row.getCell(6);
                            		Cell effd=row.getCell(7);
                            		Cell eqpcodeV=row.getCell(8);
                            		int eqpcodeint=0;
                            		String eqps=null;
                            		//	int unqprodc=0;

                            		if(eqpcodeV!=null && eqpcodeV.getCellTypeEnum()!=CellType.BLANK) {
                            			switch(eqpcodeV.getCachedFormulaResultType()){
                							case Cell.CELL_TYPE_NUMERIC : eqpcodeint=(int) eqpcodeV.getNumericCellValue();
                							break;
                							case Cell.CELL_TYPE_STRING : eqps= eqpcodeV.getStringCellValue();
                							break;
                            			}
                                  
                            			/*  if(!data.contains(eqpcodeint)){
                                    		data.add(eqpcodeint);
                                    		unqprodc=1;
                                    	}*/
                            		}

                            		String eunits = null;
                            		String gstv = null;
                            		int hsn = 0;
                            		int fullsv = -1;
                					int secdposit = -1;
                					int emtysv = -1;
                					Date effdate = null;
                					int prodcValue = 0;

                					if(prod != null && prod.getCellTypeEnum() != CellType.BLANK ) {
                						if(doList.size() == 0) {
                							if(prod_code.equals("DOMESTIC-5 KG SUB CYLINDER"))
                								prodcValue = 1;
                							else if(prod_code.equals("DOMESTIC-14.2 KG SUB CYLINDER"))
                								prodcValue = 2;
                							else if(prod_code.equals("DOMESTIC-14.2 KG NS CYLINDER"))
                								prodcValue = 3;
                							else if(prod_code.equals("COMMERCIAL-5 KG FTL CYLINDER"))
                								prodcValue = 4;
                							else if(prod_code.equals("COMMERCIAL-19 KG NDNE CYLINDER"))
                								prodcValue = 5;
                							else if(prod_code.equals("COMMERCIAL-19 KG CUTTING GAS CYLINDER"))
                								prodcValue = 6;
                							else if(prod_code.equals("COMMERCIAL-35 KG NDNE CYLINDER"))
                								prodcValue = 7;
                							else if(prod_code.equals("COMMERCIAL-47.5 KG NDNE CYLINDER"))
                								prodcValue = 8;
                							else if(prod_code.equals("COMMERCIAL-450 KG NDNE CYLINDERS"))
                								prodcValue = 9;
                							else if(prod_code.equals("REGULATOR-SC TYPE"))
                								prodcValue = 10;
                							else if(prod_code.equals("REGULATOR-MF ELECTRONIC"))
                								prodcValue = 11;
                							else if(prod_code.equals("REGULATOR-FTL"))
                								prodcValue = 12;

                							if((!(eqpdata.contains(eqpcodeint))) && (!(delProdsList.contains(eqpcodeint)))) {
                								eqpDataDO.setProd_code(eqpcodeint);
                								eqpdata.add(eqpcodeint);
                							}else if(delProdsList.contains(eqpcodeint)){
                                				if(!(delProdsArrList.contains(prod_code)))
                                					delProdsArrList.add(prod_code);
                							}
                								
                						}else {
                							if((!(eqpdata.contains(eqpcodeint))) && (!(delProdsList.contains(eqpcodeint)))) {
                								eqpDataDO.setProd_code(eqpcodeint);
                								eqpdata.add(eqpcodeint);
                							}else if(delProdsList.contains(eqpcodeint)){
                                				if(!(delProdsArrList.contains(prod_code)))
                                					delProdsArrList.add(prod_code);
                							}
                						}
                						
                						Cell cell2=row.getCell(1);
                						// cell2.setCellType(Cell.CELL_TYPE_STRING);
                						eunits=row.getCell(1).getStringCellValue();
                						if(cell2!=null) {
                							if(eunits.equals("NOS"))
                								eqpDataDO.setUnits(1);
                							else if(eunits.equals("KGS"))
                								eqpDataDO.setUnits(2);
                						}

                						Cell cell3=row.getCell(2);
                						cell3.setCellType(CellType.STRING);
                						String gstval=cell3.getStringCellValue();

                						if(eqpcodeint<10){
                							if(cell3!=null && !gstval.equals("NA")) {
                								gstv=gstval;
                								// gstv=row.getCell(2).getStringCellValue();
                								eqpDataDO.setGstp(String.valueOf(gstv));
                							}
                						}else if(eqpcodeint==12){
                							gstv="18";
                							eqpDataDO.setGstp(String.valueOf(gstv));
                						}else if(eqpcodeint==10 || eqpcodeint==11){
                							// if(cell3!=null && !gstval.equals("NA")) {
                							gstv="NA";
                							// gstv=row.getCell(2).getStringCellValue();
                							eqpDataDO.setGstp(String.valueOf(gstv));
                						}
                					
                						if(hsncode!=null && hsncode.getCellTypeEnum()!=CellType.BLANK)
                							hsn=(int) row.getCell(3).getNumericCellValue();

                						if(sdeposit.getCellTypeEnum()!=CellType.BLANK && sdeposit.getCellTypeEnum()==CellType.NUMERIC) {
                							secdposit=(int) row.getCell(4).getNumericCellValue();
                						}

                						if(fulls.getCellTypeEnum()!=CellType.BLANK && fulls.getCellTypeEnum()==CellType.NUMERIC) {
                							fullsv=(int) row.getCell(5).getNumericCellValue();
                						}
  	                        	
                						if(emtys.getCellTypeEnum()!=CellType.BLANK && emtys.getCellTypeEnum()==CellType.NUMERIC) {
                							emtysv=(int) row.getCell(6).getNumericCellValue();
                						}
  	                        	
                						if(effd.getCellTypeEnum()!=CellType.BLANK)
                							effdate = row.getCell(7).getDateCellValue();
                						// 	&& units!=null && units.getCellTypeEnum()!=CellType.BLANK && gst!=null && gst.getCellTypeEnum()!=CellType.BLANK && hsn!=0 && sdeposit!=null && sdeposit.getCellTypeEnum()!=CellType.BLANK && fulls!=null && fulls.getCellTypeEnum()!=CellType.BLANK && emtys!=null && emtys.getCellTypeEnum()!=CellType.BLANK && effd!=null && effd.getCellTypeEnum()!=CellType.BLANK              
  	                        	
                						if(eqpDataDO.getProd_code()!=0 && prod_code!=null && fullsv!=-1 && emtysv!=-1 && effdate!=null && hsn!=0 && secdposit!=-1 && gstv!=null && eunits!=null && eqpcodeint!=0) {
                							//eqpDataDO.setGstp(gstv);
                							// eqpDataDO.setProd_code(prodcValue);
                							eqpDataDO.setCsteh_no(String.valueOf(hsn));
                							eqpDataDO.setSecurity_deposit(String.valueOf(secdposit));
                							eqpDataDO.setOs_fulls(fullsv);
                							eqpDataDO.setCs_fulls(fullsv);
                							eqpDataDO.setOs_emptys(emtysv);
                							eqpDataDO.setCs_emptys(emtysv);
                							eqpDataDO.setEffective_date(effdate.getTime());
                							eqpDataDO.setVatp(String.valueOf(0));
                							eqpDataDO.setCreated_by(agencyId);
                							eqpDataDO.setCreated_date(System.currentTimeMillis());
                							eqpDataDO.setVersion(1);
                							eqpDataDO.setDeleted(0);
                							//doList.add(staffDataDO);
                							session.save(eqpDataDO);

                							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                							AgencyStockDataDO asdo = new AgencyStockDataBO().
                									createDO(0, 0, "NA", sdf.format(effdate), 0, eqpDataDO.getProd_code(), 0, fullsv, emtysv, fullsv, emtysv, 0, "0.00", agencyId);                							
                							
                							session.save(asdo);
                							
                							insertStatus++;
                						}
                					}
                            	}
                            }
                            
                            if(delProdsList.isEmpty() == false && delProdsArrList.isEmpty() == false ) {
                            	throw new BusinessException("The Product(s) "+ fetchProducts(delProdsArrList) +" has already been added and deleted.So, please add these in Equipment Master Only.");
//                            	throw new BusinessException("The Product(s) "+ delProdsArrList +" has already been added and deleted.So, please add these in Equipment Master Only.");
                            }
                		}catch(BusinessException be){
                			logger.error(be.getExceptionMessage());
                            be.printStackTrace();
                            throw new BusinessException(be.getExceptionMessage());
                		}catch (Exception e) {
                			logger.error(e);
                            e.printStackTrace();
                            throw new BusinessException(e.getMessage());
                		}
                	}

                	
                	//NFR,BLPG&ARB
                	if((sheet.getSheetName()).equals("NFR,BLPG&ARB")) {
                		//  Sheet sheet1 = workbook.getSheetAt(0);
                		List<ARBDataDO> arbdoList = new ArrayList<>();
                		//Set<Integer> arbdata =new HashSet();
                		Set<String> arbbrand =new HashSet<>();
                		Set<String> arbpname =new HashSet<>();
                		
                		try {
                			Query<ARBDataDO> query = session.createQuery("from ARBDataDO where created_by = ?1 and deleted = ?2 ");
                			query.setParameter(1, agencyId);
                			query.setParameter(2, 0);
                			List<ARBDataDO> result = query.getResultList();
                			if (result.size() > 0) {
                				for (ARBDataDO doObj : result) {
                					arbdoList.add(doObj);
                					//	arbdata.add(doObj.getProd_code());
                					arbbrand.add(doObj.getProd_brand());
                					arbpname.add(doObj.getProd_name());
                				}
                			}
                		}catch (Exception e) {
                			logger.error(e);
                			e.printStackTrace();
                			throw new BusinessException(e.getMessage());
                		}
                		Iterator<Row> rowIterator = sheet.rowIterator();
                		rowIterator.next();
                		while (rowIterator.hasNext()) {
                			Row row = rowIterator.next();
                			ARBDataDO arbDataDO=new ARBDataDO();
                			Cell pcell=row.getCell(0);

                			Cell arbcodeV=row.getCell(9);
                			int arbcodeint=0;
                			String arbstring=null;
                			int unqarbprodc=0;
                			if(arbcodeV!=null && arbcodeV.getCellTypeEnum()!=CellType.BLANK){
                				switch(arbcodeV.getCachedFormulaResultType()){
                					case Cell.CELL_TYPE_NUMERIC:arbcodeint=(int) arbcodeV.getNumericCellValue();
                					break;
                					case Cell.CELL_TYPE_STRING:arbstring= arbcodeV.getStringCellValue();
                					break;
                				}
                			}

                			//String pcode = row.getCell(0).getStringCellValue();
                			if(pcell!=null && pcell.getCellTypeEnum()!=CellType.BLANK) {
                				Cell pbrand=row.getCell(1);
                				String pbrandv=null;
                				if(pbrand!=null && pbrand.getCellTypeEnum()!=CellType.BLANK) {
                					pbrand.setCellType(Cell.CELL_TYPE_STRING);
                					pbrandv = pbrand.getStringCellValue();
                				}

                				Cell pname=row.getCell(2);
                				String pnamev=null;
                				int hsn = 0;
                				int gstp = 0;
                				double pprice = 0;
                				int os = 0;
                				int unitsV=0;
                				String units=null;
                				Date effdate = null;
                				if(pname!=null && pname.getCellTypeEnum()!=CellType.BLANK) {
                					pname.setCellType(Cell.CELL_TYPE_STRING);
                					pnamev = pname.getStringCellValue();
                				}

                				Cell hsncode=row.getCell(3);
                				if(hsncode!=null && hsncode.getCellTypeEnum()!=CellType.BLANK)
                					hsn=(int) row.getCell(3).getNumericCellValue();
                				
                				Cell uts=row.getCell(4);
                				if(uts!=null && uts.getCellTypeEnum()!=CellType.BLANK) {
                					units=row.getCell(4).getStringCellValue();
                					if(units.equals("NOS"))
                						unitsV=1;
                					else if(units.equals("KGS"))
                						unitsV=2;
                					else if(units.equals("SET"))
                						unitsV=3;
                				}

                				Cell gstcode=row.getCell(5);
                				if(gstcode!=null && gstcode.getCellTypeEnum()!=CellType.BLANK)
                					gstp=(int) row.getCell(5).getNumericCellValue();

                				Cell prodprice=row.getCell(6);
                				if(prodprice!=null && prodprice.getCellTypeEnum()!=CellType.BLANK)
                					pprice=row.getCell(6).getNumericCellValue();

                				Cell opnfulls=row.getCell(7);
                				if(opnfulls!=null && opnfulls.getCellTypeEnum()!=CellType.BLANK)
                					os=(int) row.getCell(7).getNumericCellValue();

                				Cell datev=row.getCell(8);
                				if(datev!=null && datev.getCellTypeEnum()!=CellType.BLANK)
                					effdate=row.getCell(8).getDateCellValue();

                				String pcode=row.getCell(0).getStringCellValue();
                				int prodcValue=0;
	
                				if(arbdoList.size()==0) {
                					if(pcode.equals("STOVE"))
                						prodcValue=13;
                					else if(pcode.equals("SURAKSHA"))
                						prodcValue=14;
                					else if(pcode.equals("LIGHTER"))
                						prodcValue=15;
                					else if(pcode.equals("KITCHENWARE"))
                						prodcValue=16;
                					else if(pcode.equals("OTHERS"))
                						prodcValue=17;
                					
                					if(arbbrand.contains(pbrandv) && arbpname.contains(pnamev)){
                						// removing arb duplicates
                    				}else{
                						arbDataDO.setProd_code((prodcValue));
                						arbDataDO.setProd_brand(pbrandv);
                						arbDataDO.setProd_name(pnamev);
                						arbbrand.add(pbrandv);
                						arbpname.add(pnamev);
                    				}
                				}else {
                					if(pcode.equals("STOVE"))
                						prodcValue=13;
                					else if(pcode.equals("SURAKSHA"))
                						prodcValue=14;
                					else if(pcode.equals("LIGHTER"))
                						prodcValue=15;
                					else if(pcode.equals("KITCHENWARE"))
                						prodcValue=16;
                					else if(pcode.equals("OTHERS"))
                						prodcValue=17;
                					
                					if(arbbrand.contains(pbrandv) && arbpname.contains(pnamev)){
                						// removing arb duplicates
                    				}else{
                						arbDataDO.setProd_code((prodcValue));
                						arbDataDO.setProd_brand(pbrandv);
                						arbDataDO.setProd_name(pnamev);
                						arbbrand.add(pbrandv);
                						arbpname.add(pnamev);
                    				}
                					
                					for(ARBDataDO arbdo:arbdoList) {
                						if(arbdo.getProd_code()==arbcodeint && arbdo.getProd_brand()!=pbrandv && arbdo.getProd_name()!=pnamev && effdate!=null) {
                							switch(pcode) {
                								case "STOVE" :	System.out.println(pcode);
                												prodcValue=13;
                												break;
                								case "SURAKSHA":System.out.println(pcode);
                												prodcValue=14;
                												break;
                								case "LIGHTER":	System.out.println(pcode);
                												prodcValue=15;
                												break;
                								case "KITCHENWARE":	System.out.println(pcode);
                													prodcValue=16;
                													break;
                								case "OTHERS":	System.out.println(pcode);
                												prodcValue=17;
                												break;
                							}
                						}
                					}
                					
                				}

                				if(arbDataDO.getProd_brand()!=null && arbDataDO.getProd_name()!=null && hsn!=0 &&  gstp!=0 && pprice!=0 && os!=0 && unitsV!=0) {
                					arbDataDO.setProd_code(prodcValue);
                            		arbDataDO.setUnits(unitsV);
                            		//arbDataDO.setProd_brand(pbrandv);
                            	//	arbDataDO.setProd_name(pnamev);
                            		arbDataDO.setGstp(String.valueOf(gstp));
                            		arbDataDO.setCsteh_no(String.valueOf(hsn));
                            		arbDataDO.setProd_mrp(String.valueOf(pprice));
                            		arbDataDO.setOpening_stock(os);
                            		arbDataDO.setCurrent_stock(os);
                            		arbDataDO.setEffective_date(effdate.getTime());
                            		arbDataDO.setCreated_by(agencyId);
                            		arbDataDO.setCreated_date(System.currentTimeMillis());
                            		arbDataDO.setProd_offer_price(String.valueOf(0));
                            		arbDataDO.setVatp(String.valueOf(0));
                            		arbDataDO.setVersion(1);
                            		arbDataDO.setDeleted(0);
                            		//doList.add(staffDataDO);
                            		session.save(arbDataDO);
                            		
                            		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            		
                            		AgencyStockDataDO asdoObj = new AgencyStockDataBO().
                            				createDO(0, 0, "NA", sdf.format(effdate), 0, prodcValue, arbDataDO.getId(), os, 0, os, 0, 0, "0.00", agencyId);
                            		session.save(asdoObj);
                            		
                            		insertStatus++;
                				}
                			}

                		}
                	}


                	//Area Codes
                	if((sheet.getSheetName()).equals("Area Codes")) {
                		//  Sheet sheet1 = workbook.getSheetAt(0);
                		try{
                		Iterator<Row> rowIterator = sheet.rowIterator();
                		rowIterator.next();
                		while (rowIterator.hasNext()) {
                			Row row = rowIterator.next();
                			AreaCodeDataDO areaCodeDO=new AreaCodeDataDO();
                			String acode=null;
                			String aname=null;
                			Date effdate=null;
                			int owd=0;
                			int tcharges=0;
                			Cell areacell=row.getCell(0);
                		
                			if(areacell!=null && areacell.getCellTypeEnum()!=CellType.BLANK) {
                				if(areacell.getCellTypeEnum() == CellType.NUMERIC ){
                					acode = Integer.toString((int)row.getCell(0).getNumericCellValue());
                				}else if(areacell.getCellTypeEnum() == CellType.STRING){
                					acode = row.getCell(0).getStringCellValue();
                				}else {
                					acode = row.getCell(0).getStringCellValue();
                				}
                			}
                			
                			Cell anamecell=row.getCell(1);
                			if(anamecell!=null && anamecell.getCellTypeEnum()!=CellType.BLANK){
                				aname = row.getCell(1).getStringCellValue();

                				Cell owdcell=row.getCell(2);
                				if(owdcell!=null && owdcell.getCellTypeEnum()!=CellType.BLANK){
                					owd = (int) row.getCell(2).getNumericCellValue();

                					Cell tccell=row.getCell(3);
                					if(tccell!=null && tccell.getCellTypeEnum()!=CellType.BLANK && owd!=0)
                						tcharges=(int) tccell.getNumericCellValue();
                					else
                						tcharges=0;
                						
                					
                					Cell datecell=row.getCell(4);
                					if(tccell!=null && tccell.getCellTypeEnum()!=CellType.BLANK)
                						effdate=row.getCell(4).getDateCellValue();

                					if(acode!=null && aname!=null && (owd!=0 || owd==0) && (tcharges!=0 || tcharges==0) && effdate!=null) {
                						areaCodeDO.setArea_code(acode);
                						areaCodeDO.setArea_name(aname);
                						areaCodeDO.setOne_way_dist(owd);
                						areaCodeDO.setTransport_charges(String.valueOf(tcharges));
                						areaCodeDO.setEffective_date(effdate.getTime());
                						areaCodeDO.setCreated_by(agencyId);
                						areaCodeDO.setCreated_date(System.currentTimeMillis());
                						areaCodeDO.setVersion(1);
                						areaCodeDO.setDeleted(0);
                						//doList.add(staffDataDO);
                						session.save(areaCodeDO);
                						insertStatus++;
                					}
                				}
                			}
                			
                		}
                		}catch (Exception e) {
                			try {
                				if (tx != null)
                					tx.rollback();
                			}catch (HibernateException e1) {
                				logger.error(
                						"Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> saveAreaCode  is not succesful");
                			}
                			logger.error(e);
                			e.printStackTrace();
                			
                			String s=null;
                			System.out.println("get cause:"+e.getMessage());
   	                        
                			String emsg=e.getMessage();
                			if(emsg.contains("NullPointerException"))
                				s="Unable to upload Area data with null values";
                			else if(emsg.contains("NumberFormatException"))
                				s="Check the column values Area data before upload ";
                			else if(emsg.contains("Cannot get a STRING value from a NUMERIC cell"))
                				s="Check the column values types in Area data before upload ";
                			else
                				s="Please check Area data sheet columns and then upload it.";
                			//msg.setMessageText(s);
                			throw new BusinessException(s);
                		}
                	
                	}

      
                	//CustomerVendorMaster
                	if((sheet.getSheetName()).equals("CustomerVendorMaster")) {
                		List<CVODataDO> cvodoList = new ArrayList<>();
                		List<String> tinunq = new ArrayList<>();

                		try {
                			Query<CVODataDO> query = session.createQuery("from CVODataDO where created_by = ?1 and deleted = ?2 ");
                			query.setParameter(1, agencyId);
                			query.setParameter(2, 0);
                			List<CVODataDO> result = query.getResultList();
                			if (result.size() > 0) {
                				for (CVODataDO cvoObj : result) {
                					cvodoList.add(cvoObj);
                					tinunq.add(cvoObj.getCvo_tin());
                				}
                			}
                		} catch (Exception e) {
                			logger.error(e);
                			e.printStackTrace();
                			throw new BusinessException(e.getMessage());
                		}
                		
                		//  Sheet sheet1 = workbook.getSheetAt(0);
                		Iterator<Row> rowIterator = sheet.rowIterator();
                		rowIterator.next();
                		Set cvodataunq=new HashSet();
                		while (rowIterator.hasNext()) {
                			Row row = rowIterator.next();
                			CVODataDO cvoDataDO=new CVODataDO();
                			
                			Cell cat=row.getCell(1);
                			Cell cvoNamecell=row.getCell(0);
                			//int cvounq=0;
                			if(cat!=null && cat.getCellTypeEnum()!=CellType.BLANK) {
                				String cvoname=null;
                				if(cvoNamecell!=null && cvoNamecell.getCellTypeEnum()!=CellType.BLANK){
                					cvoname = row.getCell(0).getStringCellValue();
                					cvoDataDO.setCvo_name(cvoname);
                				}
                				String category = row.getCell(1).getStringCellValue();
                				
                				switch(category) {
                					case "VENDOR":	System.out.println(category);
                									cvoDataDO.setCvo_cat(0);
                									break;
                					case "CUSTOMER":System.out.println(category);
                									cvoDataDO.setCvo_cat(1);
                									break;
                					case "OMC-IOCL":System.out.println(category);
                									cvoDataDO.setCvo_cat(2);
                									break;
                					case "OTHERS":	System.out.println(category);
                									cvoDataDO.setCvo_cat(3);
                									break;
                				}
                				if(!cvodataunq.contains(cvoDataDO.getCvo_cat())){
                					cvodataunq.add(cvoDataDO.getCvo_cat());
                					//cvounq=1;
                				}

                				Cell gstreg=row.getCell(2);
                				if(gstreg!=null && gstreg.getCellTypeEnum()!=CellType.BLANK) {
                					String gstyn = row.getCell(2).getStringCellValue();
                					if(gstyn.equals("YES"))
                						cvoDataDO.setIs_gst_reg(1);
                					else if(gstyn.equals("NO")) {
                						cvoDataDO.setIs_gst_reg(2);
                						cvoDataDO.setCvo_tin("NA");
                					}
                				}

                				Cell tinc=row.getCell(3);
                				Cell panc = row.getCell(4);
                				String tin = null;
                				String pan;
                			//	if(tinc!=null && tinc.getCellTypeEnum()!=CellType.BLANK)
                					//tin = row.getCell(3).getStringCellValue();
                					if(tinc!=null && tinc.getCellTypeEnum()!=CellType.BLANK) {
                						//	for(CVODataDO cvodata :cvodoList) {
                						//if((row.getCell(3).getStringCellValue()).equals(cvodata.getCvo_tin())){
                						System.out.println("tin value is:"+row.getCell(3).getStringCellValue());
                						if(!row.getCell(3).getStringCellValue().equals("NA")){
                							if((tinunq.contains(row.getCell(3).getStringCellValue()))){
                								tin=null;
                								//break;
                							}else{
                								tin = row.getCell(3).getStringCellValue();
                								tinunq.add(row.getCell(3).getStringCellValue());
                							}
                						}else
                							tin = row.getCell(3).getStringCellValue();
                				
                					}else
                						tin = "NA";
                			
                				if(panc!=null && panc.getCellTypeEnum()!=CellType.BLANK)
                					pan = row.getCell(4).getStringCellValue();
                				else
                					pan = "NA";
                				
                				Cell addc=row.getCell(5);
                				String address;
                				if(addc!=null && addc.getCellTypeEnum()!=CellType.BLANK)
                					address = row.getCell(5).getStringCellValue();
                				else
                					address="NA";
                				
                				Cell contact=row.getCell(6);
                				String mobile=null;;
                			
                				if(contact!=null && contact.getCellTypeEnum()!=CellType.BLANK) {
                					contact.setCellType(CellType.STRING);
                					mobile=contact.getStringCellValue();
	                			}else
	                				mobile="NA";
                				
                				/*if(numc!=null && numc.getCellTypeEnum()!=CellType.BLANK)
                				contact = (int) row.getCell(6).getNumericCellValue();
*/                			

                				String email=null;
                				Cell emailc=row.getCell(7);
                				if(emailc!=null && emailc.getCellTypeEnum()!=CellType.BLANK)
                					email = row.getCell(7).getStringCellValue();
                				else
                					email="NA";
                				
                				Cell cell=row.getCell(8);
                				String obal;
                				if(cell!=null && cell.getCellTypeEnum()!=CellType.BLANK) {
                					cell.setCellType(Cell.CELL_TYPE_STRING);
                					obal=cell.getStringCellValue();
                				}else
                					obal="0.00";
                				System.out.println("tin value is:"+tin);

                				if(cvoDataDO.getCvo_cat()!=-1 && cvoDataDO.getIs_gst_reg()!=0 && cvoDataDO.getCvo_name()!=null && tin!=null && pan!=null && address!=null && mobile!=null && email!=null && obal!=null){
                					cvoDataDO.setCvo_name(cvoname);
                					cvoDataDO.setCvo_tin(tin);
                					cvoDataDO.setCvo_pan(pan);
                					cvoDataDO.setCvo_address(address);
                					cvoDataDO.setCvo_contact(String.valueOf(mobile));
                					cvoDataDO.setCvo_email(email);
                					cvoDataDO.setObal((obal));
            						cvoDataDO.setCbal((obal));
            						cvoDataDO.setEbal(String.valueOf(0));
            						cvoDataDO.setCreated_by(agencyId);
            						cvoDataDO.setCreated_date(System.currentTimeMillis());
            						cvoDataDO.setVersion(1);
            						cvoDataDO.setDeleted(0);
            						//doList.add(staffDataDO);
            						session.save(cvoDataDO);
            						
            						Date effDate = new Date(effDateInFTL);
            						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            						AgencyCVOBalanceDataDO acvoBalDO = new AgencyCVOBalanceDataBO().
            								createDO(obal, cvoDataDO.getCvo_cat(), agencyId, 0, "NA", 0, cvoDataDO.getId(), sdf.format(effDate),"NA");
            						
//            						acvoBalDO.setCvo_refid(cvoDataDO.getId());
            						
            						session.save(acvoBalDO);
            						
            						insertStatus++;
                				}
                			}
                		}
                	}

                	
                	//Bank Master
                	if((sheet.getSheetName()).equals("Bank Master")) {
                		List<BankDataDO> bankdoList = new ArrayList<>();
                		Set<Integer> bankdataunq=new HashSet<Integer>();
                		Set<String> bankacc=new HashSet<String>();

                		try {
                			Query<BankDataDO> query = session.createQuery("from BankDataDO where created_by = ?1 and deleted = ?2 ");
                			query.setParameter(1, agencyId);
                			query.setParameter(2, 0);
                			List<BankDataDO> result = query.getResultList();
                			if (result.size() > 0) {
                				for (BankDataDO bankObj : result) {
                					bankdoList.add(bankObj);
                					bankacc.add(bankObj.getBank_acc_no());
                				}
                				System.out.println("acc no:"+bankacc);
                			}
                		} catch (Exception e) {
                			logger.error(e);
                			e.printStackTrace();
                			throw new BusinessException(e.getMessage());
                		}
                		
                		//  Sheet sheet1 = workbook.getSheetAt(0);
                		Iterator<Row> rowIterator = sheet.rowIterator();
                		rowIterator.next();
                		while (rowIterator.hasNext()) {
                			Row row = rowIterator.next();
                			BankDataDO bankDataDO=new BankDataDO();
                			Cell bankname = row.getCell(0);
                			int bankcodeint=0;
                			String bankstr=null;
                			int unqbank=0;
                			Cell banknameFormula = row.getCell(7);
                			if(banknameFormula!=null && banknameFormula.getCellTypeEnum()!=CellType.BLANK){
                				switch(banknameFormula.getCachedFormulaResultType()){
                					case Cell.CELL_TYPE_NUMERIC:bankcodeint=(int) banknameFormula.getNumericCellValue();
                        			break;
                        			case Cell.CELL_TYPE_STRING:bankstr= banknameFormula.getStringCellValue();
                        			break;
                				}
                				/*if(bankdataunq.isEmpty()){
                        				bankdataunq.add(bankcodeint);
                        				unqbank=1;
                        			}
                        			else if(!bankdataunq.contains(bankcodeint)){
                        				bankdataunq.add(bankcodeint);
                        				unqbank=1;
                        			}*/
                			}

                			int bnameV=0;
                			String obalV=null;
                			String ifsc=null;
                			String branch=null;
                			String acctype=null;
                			String accnoV=null;
                			String address=null;
                			if(bankname!=null) {
                				Cell acctypecell = row.getCell(1);
                				if(acctypecell!=null && acctypecell.getCellTypeEnum()!=CellType.BLANK) {
                					acctype = row.getCell(1).getStringCellValue();
                        		}
                				Cell accno = row.getCell(2);
                				if(accno!=null && accno.getCellTypeEnum()!=CellType.BLANK) {
                					accno.setCellType(CellType.STRING);
                					if(bankacc.contains(accno.getStringCellValue())){
                						//
                					}else {
                						bankacc.add(accno.getStringCellValue());
                						bankDataDO.setBank_acc_no(accno.getStringCellValue());
                					}
                        				
                					/*
                        				for(BankDataDO bankdata:bankdoList) {
                        					if(bankdata.getBank_acc_no().equals(accno.getStringCellValue()))
                        						break;
                        					else {
                        						//accno.setCellType(CellType.STRING);
                                		
                                		if(satunq.contains(itemType)){
                                			//empty
                                		}
                                		else
                                			satDataDO.setItem_type(itemType);
                                		
                                		if(itemType==4 || itemType==5 || itemType==6){
                                		if(satdup.contains(itemType) || !satdup.contains(itemType)){
                                			satDataDO.setItem_type(itemType);
                                		}
                                		}

                                	
                        						accnoV=accno.getStringCellValue();
                        					}
                        				}*/
                				}
                				
                				Cell branchcell = row.getCell(3);
                				if(branchcell!=null && branchcell.getCellTypeEnum()!=CellType.BLANK) {
                					branch = row.getCell(3).getStringCellValue();
                				}
                				
                				Cell ifsccell = row.getCell(4);
                				if(ifsccell!=null && ifsccell.getCellTypeEnum()!=CellType.BLANK) {
                					ifsc = row.getCell(4).getStringCellValue();
                				}
                				
                				//String address = row.getCell(5).getStringCellValue();
                				Cell addcell=row.getCell(5);
                				if(addcell!=null && addcell.getCellTypeEnum()!=CellType.BLANK) {
                					addcell.setCellType(CellType.STRING);
                					address=addcell.getStringCellValue();
                				}
                				Cell obal=row.getCell(6);
                				if(obal!=null && obal.getCellTypeEnum()!=CellType.BLANK) {
                					obal.setCellType(CellType.STRING);
                					obalV = obal.getStringCellValue();
                				}
                				if(bankDataDO.getBank_acc_no()!=null && bankcodeint!=0 && acctype!=null && branch!=null && ifsc!=null && obalV!=null && addcell!=null) {
                					bankDataDO.setBank_name(String.valueOf(bankcodeint));
                					bankDataDO.setBank_code(acctype);
                					//bankDataDO.setBank_acc_no(String.valueOf(accnoV));
                					bankDataDO.setBank_branch(branch);
                					bankDataDO.setBank_ifsc_code(ifsc);
                					bankDataDO.setBank_addr(address);
                					bankDataDO.setAcc_ob(obalV);
                					bankDataDO.setAcc_cb(obalV);
                					bankDataDO.setOd_and_loan_acceptable_bal("NA");
                					bankDataDO.setDeleted(0);
                					bankDataDO.setCreated_date(System.currentTimeMillis());
                					bankDataDO.setCreated_by(agencyId);
                					bankDataDO.setVersion(1);
                					bankDataDO.setDeleted(0);
                					//doList.add(staffDataDO);
                					session.save(bankDataDO);
                					insertStatus++;
                				}
                			}
                		}
                	}
                    
                	
                	//Vehicles Master
                	if((sheet.getSheetName()).equals("Vehicles Master")) {
                		//  Sheet sheet1 = workbook.getSheetAt(0);
                		Iterator<Row> rowIterator = sheet.rowIterator();
                		rowIterator.next();
                		while (rowIterator.hasNext()) {
                			Row row = rowIterator.next();
                			FleetDataDO fleetDataDO=new FleetDataDO();
                			Cell vnoc=row.getCell(0);
                			Cell vmakecell=row.getCell(1);
                			Cell vtypecell=row.getCell(2);
                			Cell vusagecell=row.getCell(3);

                			String vno=null;
                			String vmake=null;
                			String vtype=null;
                			int vtypeVal=-1;
                			int vUsage=-1;
                        		
                			if(vtypecell!=null && vtypecell.getCellTypeEnum()!=CellType.BLANK) {
                				
                				if(vnoc!=null && vnoc.getCellTypeEnum()!=CellType.BLANK) {
                					vno = row.getCell(0).getStringCellValue();
                				}
                				if(vmakecell!=null && vmakecell.getCellTypeEnum()!=CellType.BLANK) {
                					vmake = row.getCell(1).getStringCellValue();
                				}
                        		
                				vtype=row.getCell(2).getStringCellValue();
                				if(vtype.equals("TWO WHEELER"))
                					vtypeVal=0;
                				else if(vtype.equals("THREE WHEELER"))
                					vtypeVal=1;
                				else if(vtype.equals("FOUR WHEELER"))
                					vtypeVal=2;
                				else if(vtype.equals("TRUCK"))
                					vtypeVal=3;
                				else if(vtype.equals("OTHERS"))
                					vtypeVal=4;
                        		
                        		if(vusagecell!=null && vusagecell.getCellTypeEnum()!=CellType.BLANK) {
                        			String vusage=row.getCell(3).getStringCellValue();
                        			if(vusage.equals("INWARD DELIVERY"))
                        				vUsage=0;
                        			else if(vusage.equals("OUTWARD DELIVERY"))
                        				vUsage=1;
                        			else if(vusage.equals("CONVEYANCE"))
                        				vUsage=2;
                        			else if(vusage.equals("OTHERS"))
                        				vUsage=3;
                        		}
                        		if(vUsage!=-1 && vtypeVal!=-1 && vmake!=null && vno!=null) {
                        			fleetDataDO.setVehicle_type(vtypeVal);
                        			fleetDataDO.setVehicle_no(vno);
                        			fleetDataDO.setVehicle_make(vmake);
                        			fleetDataDO.setVehicle_usuage(vUsage);
                        			fleetDataDO.setCreated_by(agencyId);
                        			fleetDataDO.setCreated_date(System.currentTimeMillis());
                        			fleetDataDO.setDeleted(0);
                        			fleetDataDO.setVersion(1);
                        			//doList.add(staffDataDO);
                        			session.save(fleetDataDO);
                        			insertStatus++;
                        		}
                			}
                		}
                	}
    			}

    			try {
    				if(insertStatus!=0) {
    					Query query = session.createQuery("UPDATE AgencyDetailsDO SET xl_upload_status=?1 WHERE createdBy=?2");
    					query.setParameter(1, 1);
    					query.setParameter(2, agencyId);
    					query.executeUpdate();
                	}
    			}catch (Exception e) {
    				try {
                		if (tx != null)
                            tx.rollback();
                	}catch (HibernateException e1) {
                        logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> PPMasterDataPersistenceManager --> saveAgencyBOMData  is not succesful");
                	}
                	logger.error(e);
                	e.printStackTrace();
                }
                if(insertStatus!=0) {
                	tx.commit();
                }
    		}catch(BusinessException be) {
    			throw be;
    		}catch(Exception e) {
    			e.printStackTrace();        		
    		}
    	}
    }
   	
}



            /*catch (Exception e) {
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
                }*/

                    /*try {
                            if (tx != null) tx.rollback();
                    }catch (HibernateException e1) {
                            logger.error("Transaction rollback in  com.it.erpapp.framework.managers --> MyProfilePersistanceManager --> savePinNumber  is not succesful");
                    }
                    logger.error(e);
                    e.printStackTrace();
                    throw new BusinessException(e.getMessage());
            }finally {
                    if(fin != null) { fin.close();}
                    if(filepath != null) { filepath.close();}
                    session.clear();
                    session.close();
            }*/

