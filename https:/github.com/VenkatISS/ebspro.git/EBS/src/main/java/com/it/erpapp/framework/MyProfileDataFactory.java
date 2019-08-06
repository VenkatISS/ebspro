package com.it.erpapp.framework;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.Part;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.it.erpapp.framework.managers.MyProfilePersistanceManager;
import com.it.erpapp.framework.model.vos.AgencyVO;
import com.it.erpapp.systemservices.exceptions.BusinessException;

public class MyProfileDataFactory {
	
	private MyProfilePersistanceManager getMyProfilePersistanceManager(){
		return new MyProfilePersistanceManager();
	}
	//Staff Data
	public AgencyVO getProfileData(long agencyId) throws BusinessException{
		return getMyProfilePersistanceManager().getProfileData(agencyId);
	}

	public AgencyVO updateProfileData(Map<String, String> requestvals,long agencyId) throws BusinessException{
		getMyProfilePersistanceManager().updateProfileData(agencyId,requestvals);
		return getMyProfilePersistanceManager().getProfileData(agencyId);
		
	}
	
	//saveProfileData
    public AgencyVO savePinNumber(Map<String, String> requestvals,long agencyId) throws BusinessException{
            getMyProfilePersistanceManager().savePinNumber(agencyId,requestvals);
            return getMyProfilePersistanceManager().getProfileData(agencyId);

    }
    //Sending confirmation EMAIL to new emailId
    public AgencyVO sendMailAndUpdateRegisterdEmailId(Map<String, String> requestvals,long agencyId) throws BusinessException{
            getMyProfilePersistanceManager().sendMailAndUpdateRegisterdEmailId(agencyId,requestvals);
            return getMyProfilePersistanceManager().getProfileData(agencyId);

    }
    
  //updating Email Id
    public AgencyVO updateRegisterdEmailId(Map<String, String> requestvals,long agencyId) throws BusinessException{
            getMyProfilePersistanceManager().updateRegisterdEmailId(agencyId,requestvals);
            return getMyProfilePersistanceManager().getProfileData(agencyId);

    }
    
    //changePinNumber
    public AgencyVO changePinNumber(Map<String, String> requestvals,long agencyId) throws BusinessException{
            getMyProfilePersistanceManager().changePinNumber(agencyId,requestvals);
            return getMyProfilePersistanceManager().getProfileData(agencyId);

    }
    //forgot pin number
    public AgencyVO forgotPinNumber(Map<String, String> requestvals,long agencyId,int otpNumber) throws BusinessException{
            getMyProfilePersistanceManager().forgotPinNumber(agencyId,requestvals,otpNumber);
            return getMyProfilePersistanceManager().getProfileData(agencyId);

    }
    
    //uploadpicTodb
    public AgencyVO uploadpicTodb(long agencyId,Part picPath) throws BusinessException, IOException, SQLException{
      getMyProfilePersistanceManager().uploadprofilepicToPath(agencyId,picPath);
      return getMyProfilePersistanceManager().getProfileData(agencyId);

}
    
    //removeProfile
  	public AgencyVO removeProfilePic(long agencyId) throws BusinessException{
  		getMyProfilePersistanceManager().removeProfilePic(agencyId);
        return getMyProfilePersistanceManager().getProfileData(agencyId);
  	}
  	
  //uploadataTodb
    public void uploadataTodbFromExcel(long agencyId,Part picPath, long effDate) throws BusinessException, IOException, SQLException, EncryptedDocumentException, InvalidFormatException{
        getMyProfilePersistanceManager().uploadataTodbFromExcel(agencyId, picPath, effDate);
    }
}
