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
            String[] visaApplications = passport.getVisaApplications();
            String visaApplicationID = visaApplications[0];

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
                Passport passport = mapper.readValue(passportResponse.getBody().getObject().toString(), Passport.class);

                //get visa application id, assume there is only one visa application for one citizen
                String[] visaApplications = passport.getVisaApplications();
                visaApplicationID = visaApplications[0];
            } catch (Exception e) {
                e.printStackTrace();
            }

            //get visa application by visa Application ID
            try {
                HttpResponse<JsonNode> visaApplicationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.VisaApplication/" + visaApplicationID)
                        .header("accept", "application/json")
                        .asJson();
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

        //get visaStatus by visaStatusId
        ObjectMapper mapper = new ObjectMapper();
        VisaStatus visaStatus = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/VisaStatus/post_request_Jingyuan.json"), VisaStatus.class);
        return visaStatus.getState();
    }

    //get basic information endorsement state
    public String getBIStatus() throws IOException {
        //get BasicInfo ID
        String basicInfoID = getVisaApplication().getBasicInfo();
        ObjectMapper mapper = new ObjectMapper();

        //get BasicInfo by basicInfo ID
        BasicInfo basicInfo = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BasicInfo/post_request_Jingyuan.json"), BasicInfo.class);
        return basicInfo.getEndorsementState();
    }

    //get bank statement endorsement state
    public String getBSStatus() throws IOException {
        //get BankStatement ID
        String bankStatementID = getVisaApplication().getBankStatement();
        ObjectMapper mapper = new ObjectMapper();

        //get BankStatement by BankStatement ID
        BankStatement bankStatement = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BankStatement/post_request_Jingyuan.json"), BankStatement.class);
        return bankStatement.getEndorsementState();
    }

    //get transportation reference endorsement state
    public String getTRStatus() throws IOException {
        //get transportation reference ID
        String transportationReferenceID = getVisaApplication().getTransportationReference();
        ObjectMapper mapper = new ObjectMapper();

        //get transportation Reference by transportationReferenceID 
        TransportationReference transportationReference = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/TransportationReference/post_request_Jingyuan.json"), TransportationReference.class);
        return transportationReference.getEndorsementState();
    }

    //get accommodation endorsement state
    public String getAccommodationStatus() throws IOException {
        //get accommodationID
        String accommodationID = getVisaApplication().getAccommodation();
        ObjectMapper mapper = new ObjectMapper();

        //get accommodation by accommodationID 
        Accommodation accommodation = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Accommodation/post_request_Jingyuan.json"), Accommodation.class);
        return accommodation.getEndorsementState();
    }

    //get local contact endorsement state
    public String getLCStatus() throws IOException {
        //get localContactID
        String localContactID = getVisaApplication().getLocalContact();
        ObjectMapper mapper = new ObjectMapper();

        //get localContact by localContactID 
        LocalContact localContact = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/LocalContact/post_request_Jingyuan.json"), LocalContact.class);
        return localContact.getEndorsementState();
    }

    //get travel insurance endorsement state
    public String getInsuranceStatus() throws IOException {
        //get insuranceID
        String insuranceID = getVisaApplication().getInsurance();
        ObjectMapper mapper = new ObjectMapper();

        //get insurance by insuranceID 
        Insurance insurance = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Insurance/post_request_Jingyuan.json"), Insurance.class);
        return insurance.getEndorsementState();
    }

    //get criminal record endorsement state
    public String getCRStatus() throws IOException {
        //get criminalRecordID
        String criminalRecordID = getVisaApplication().getCriminalRecord();
        ObjectMapper mapper = new ObjectMapper();

        //get criminalRecord by criminalRecordID 
        CriminalRecord criminalRecord = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/CriminalRecord/post_request_Jingyuan.json"), CriminalRecord.class);
        return criminalRecord.getEndorsementState();
    }

}
