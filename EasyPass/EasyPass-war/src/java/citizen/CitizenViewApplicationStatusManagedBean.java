/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citizen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import entity.CitizenEntity;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objects.Accommodation;
import objects.BankStatement;
import objects.BasicInfo;
import objects.CriminalRecord;
import objects.Insurance;
import objects.LocalContact;
import objects.Passport;
import objects.TransportationReference;
import objects.VisaApplication;
import objects.VisaStatus;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "citizenViewApplicationStatusManagedBean")
@RequestScoped
public class CitizenViewApplicationStatusManagedBean {

    private VisaApplication visaApplication;
    private VisaStatus visaStatus;
    private BasicInfo basicInfo;
    private BankStatement bankStatement;
    private TransportationReference transportationReference;
    private Accommodation accommodation;
    private LocalContact localContact;
    private Insurance insurance;
    private CriminalRecord criminalRecord;

    /**
     * Creates a new instance of CitizenViewApplicationStatusManagedBean
     */
    public CitizenViewApplicationStatusManagedBean() {
    }

    public VisaApplication getVisaApplication() throws IOException {

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        CitizenEntity citizen = (CitizenEntity) ec.getSessionMap().get("citizen");
        String id = citizen.getCitizenId();//get citizenID (passport number)

        if (Constants.localTesting) {
            //get passport by passport number
            ObjectMapper mapper = new ObjectMapper();
            Passport passport = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Passport/get_response.json"), Passport.class);

            //get visa application id, assume there is only one visa application for one citizen
            List<VisaApplication> visaApplications = passport.getVisaApplications();
            String visaApplicationID = visaApplications.get(0).getVisaApplicationId();

            //get visa application by visa Application ID
            VisaApplication visaApplication = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/VisaApplication/post_request_Jingyuan.json"), VisaApplication.class);
            return visaApplication;

        } else {
            //get passport by passport number
            ObjectMapper mapper = new ObjectMapper();
            String visaApplicationID = "";

            try {
                HttpResponse<JsonNode> passportResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Passport/" + citizen.getCitizenId())
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(passportResponse.getBody().getObject().toString());
                Passport passport = mapper.readValue(passportResponse.getBody().getObject().toString(), Passport.class);

                //get visa application id, assume there is only one visa application for one citizen
                List<VisaApplication> visaApplications = passport.getVisaApplications();
                visaApplicationID = visaApplications.get(0).getVisaApplicationId();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //get visa application by visa Application ID
            try {
                HttpResponse<JsonNode> visaApplicationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.VisaApplication/" + visaApplicationID)
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(visaApplicationResponse.getBody().getObject().toString());
                visaApplication = mapper.readValue(visaApplicationResponse.getBody().getObject().toString(), VisaApplication.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return visaApplication;
        }
    }

    public String getVisaStatus() throws IOException {

        //get visaStatusId 
        String visaStatusId = getVisaApplication().getVisaStatus();
        String[] visaStatusIdArray = visaStatusId.split("#");

        if (Constants.localTesting) {
            //get visaStatus by visaStatusId
            ObjectMapper mapper = new ObjectMapper();
            visaStatus = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/VisaStatus/post_request_Jingyuan.json"), VisaStatus.class);
            return visaStatus.getStatusState();
        } else {
            //get visaStatus by visaStatusId
            ObjectMapper mapper = new ObjectMapper();
            try {
                HttpResponse<JsonNode> visaStatusResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.VisaStatus/" + visaStatusIdArray[1])
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(visaStatusResponse.getBody().getObject().toString());
                visaStatus = mapper.readValue(visaStatusResponse.getBody().getObject().toString(), VisaStatus.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return visaStatus.getStatusState();
        }
    }

    //get basic information endorsement state
    public String getBIStatus() throws IOException {
        //get BasicInfo ID
        String basicInfoID = getVisaApplication().getBasicInfo();
        String[] basicInfoIDArray = basicInfoID.split("#");

        if (Constants.localTesting) {
            ObjectMapper mapper = new ObjectMapper();

            //get BasicInfo by basicInfo ID
            basicInfo = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BasicInfo/post_request_Jingyuan.json"), BasicInfo.class);
            return basicInfo.getEndorseStatus();
        } else {
            ObjectMapper mapper = new ObjectMapper();

            //get BasicInfo by basicInfo ID
            try {
                HttpResponse<JsonNode> basicInfoResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.BasicInfo/" + basicInfoIDArray[1])
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(basicInfoResponse.getBody().getObject().toString());
                basicInfo = mapper.readValue(basicInfoResponse.getBody().getObject().toString(), BasicInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return basicInfo.getEndorseStatus();
        }
    }

    //get bank statement endorsement state
    public String getBSStatus() throws IOException {
        //get BankStatement ID
        String bankStatementID = getVisaApplication().getBankStatement();
        String[] bankStatementIDArray = bankStatementID.split("#");

        if (Constants.localTesting) {
            ObjectMapper mapper = new ObjectMapper();

            //get BankStatement by BankStatement ID
            bankStatement = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BankStatement/post_request_Jingyuan.json"), BankStatement.class);
            return bankStatement.getEndorseStatus();
        } else {
            ObjectMapper mapper = new ObjectMapper();

            //get BankStatement by BankStatement ID
            try {
                HttpResponse<JsonNode> bankStatementResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.BankStatement/" + bankStatementIDArray[1])
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(bankStatementResponse.getBody().getObject().toString());
                bankStatement = mapper.readValue(bankStatementResponse.getBody().getObject().toString(), BankStatement.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bankStatement.getEndorseStatus();
        }
    }

    //get transportation reference endorsement state
    public String getTRStatus() throws IOException {
        //get transportation reference ID
        String transportationReferenceID = getVisaApplication().getTransportationReference();
        String[] transportationReferenceIDArray = transportationReferenceID.split("#");

        if (Constants.localTesting) {
            ObjectMapper mapper = new ObjectMapper();

            //get transportation Reference by transportationReferenceID 
            transportationReference = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/TransportationReference/post_request_Jingyuan.json"), TransportationReference.class);
            return transportationReference.getEndorseStatus();
        } else {
            ObjectMapper mapper = new ObjectMapper();

            //get transportation Reference by transportationReferenceID 
            try {
                HttpResponse<JsonNode> transportationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.TransportationReference/" + transportationReferenceIDArray[1])
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(transportationResponse.getBody().getObject().toString());
                transportationReference = mapper.readValue(transportationResponse.getBody().getObject().toString(), TransportationReference.class);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return transportationReference.getEndorseStatus();
        }
    }

    //get accommodation endorsement state
    public String getAccommodationStatus() throws IOException {
        //get accommodationID
        String accommodationID = getVisaApplication().getAccommodation();
        String[] accommodationIDArray = accommodationID.split("#");

        if (Constants.localTesting) {
            ObjectMapper mapper = new ObjectMapper();

            //get accommodation by accommodationID 
            accommodation = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Accommodation/post_request_Jingyuan.json"), Accommodation.class);
            return accommodation.getEndorseStatus();
        } else {
            ObjectMapper mapper = new ObjectMapper();

            //get accommodation by accommodationID 
            try {
                HttpResponse<JsonNode> accommodationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Accommodation/" + accommodationIDArray[1])
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(accommodationResponse.getBody().getObject().toString());
                accommodation = mapper.readValue(accommodationResponse.getBody().getObject().toString(), Accommodation.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return accommodation.getEndorseStatus();
        }
    }

    //get local contact endorsement state
    public String getLCStatus() throws IOException {
        //get localContactID
        String localContactID = getVisaApplication().getLocalContact();
        String[] localContactIDArray = localContactID.split("#");

        if (Constants.localTesting) {
            ObjectMapper mapper = new ObjectMapper();

            //get localContact by localContactID 
            localContact = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/LocalContact/post_request_Jingyuan.json"), LocalContact.class);
            return localContact.getEndorseStatus();
        } else {
            ObjectMapper mapper = new ObjectMapper();

            //get localContact by localContactID 
            try {
                HttpResponse<JsonNode> localContactResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.LocalContact/" + localContactIDArray[1])
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(localContactResponse.getBody().getObject().toString());
                localContact = mapper.readValue(localContactResponse.getBody().getObject().toString(), LocalContact.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return localContact.getEndorseStatus();
        }
    }

    //get travel insurance endorsement state
    public String getInsuranceStatus() throws IOException {
        //get insuranceID
        String insuranceID = getVisaApplication().getInsurance();
        String[] insuranceIDArray = insuranceID.split("#");

        if (Constants.localTesting) {
            ObjectMapper mapper = new ObjectMapper();

            //get insurance by insuranceID 
            insurance = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Insurance/post_request_Jingyuan.json"), Insurance.class);
            return insurance.getEndorseStatus();
        } else {
            ObjectMapper mapper = new ObjectMapper();

            //get insurance by insuranceID 
            try {
                HttpResponse<JsonNode> insuranceResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Insurance/" + insuranceIDArray[1])
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(insuranceResponse.getBody().getObject().toString());
                insurance = mapper.readValue(insuranceResponse.getBody().getObject().toString(), Insurance.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return insurance.getEndorseStatus();
        }
    }

    //get criminal record endorsement state
    public String getCRStatus() throws IOException {
        //get criminalRecordID
        String criminalRecordID = getVisaApplication().getCriminalRecord();
        String[] criminalRecordIDArray = criminalRecordID.split("#");

        if (Constants.localTesting) {
            ObjectMapper mapper = new ObjectMapper();

            //get criminalRecord by criminalRecordID 
            criminalRecord = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/CriminalRecord/post_request_Jingyuan.json"), CriminalRecord.class);
            return criminalRecord.getEndorseStatus();
        } else {
            ObjectMapper mapper = new ObjectMapper();

            //get criminalRecord by criminalRecordID 
            try {
                HttpResponse<JsonNode> criminalRecordResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.CriminalRecord/" + criminalRecordIDArray[1])
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(criminalRecordResponse.getBody().getObject().toString());
                criminalRecord = mapper.readValue(criminalRecordResponse.getBody().getObject().toString(), CriminalRecord.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return criminalRecord.getEndorseStatus();
        }
    }

}
