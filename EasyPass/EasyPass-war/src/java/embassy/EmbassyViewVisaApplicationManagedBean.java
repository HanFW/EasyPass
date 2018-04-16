/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embassy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objects.Accommodation;
import objects.BankStatement;
import objects.BasicInfo;
import objects.Citizen;
import objects.CriminalRecord;
import objects.Insurance;
import objects.LocalContact;
import objects.Passport;
import objects.TransportationReference;
import objects.Visa;
import objects.VisaApplication;
import objects.VisaStatus;
import util.Constants;

/**
 *
 * @author hanfengwei
 */
@Named(value = "embassyViewVisaApplicationManagedBean")
@RequestScoped
public class EmbassyViewVisaApplicationManagedBean {

    private VisaApplication visaApplication;
    private Citizen citizen;
    private VisaStatus visaStatus;
    private BasicInfo basicInfo;
    private BankStatement bankStatement;
    private TransportationReference transportation;
    private Accommodation accommodation;
    private Insurance insurance;
    private LocalContact localContact;
    private CriminalRecord criminalRecord;
    private Passport passport;
    private boolean hasVisa;
    private Visa visa;

    /**
     * Creates a new instance of EmbassyViewVisaApplicationManagedBean
     */
    public EmbassyViewVisaApplicationManagedBean() {
    }

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        visaApplication = (VisaApplication) ec.getFlash().get("visaApplication");
        if (visaApplication != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                if (Constants.localTesting) {
                    //get all objects in visa application
                    citizen = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Participant/Citizen/get_response_byId.json"), Citizen.class);
                    visaStatus = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaStatus/get_response_byId.json"), VisaStatus.class);
                    basicInfo = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/BasicInfo/get_response_byId.json"), BasicInfo.class);
                    bankStatement = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/BankStatement/get_response_byId.json"), BankStatement.class);
                    transportation = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/TransportationReference/get_response_byId.json"), TransportationReference.class);
                    accommodation = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/Accommodation/get_response_byId.json"), Accommodation.class);
                    insurance = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/Insurance/get_response_byId.json"), Insurance.class);
                    localContact = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/LocalContact/get_response_byId.json"), LocalContact.class);
                    criminalRecord = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/CriminalRecord/get_response_byId.json"), CriminalRecord.class);

                    if (visaStatus.getStatusState().equals(Constants.APPLICATION_STATUS_APPROVED)) {
                        hasVisa = true;
                        visa = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/Visa/get_response_byId.json"), Visa.class);
                    } else {
                        hasVisa = false;
                    }

                } else {
                    try {
                        String[] citizenIdArray = visaApplication.getPassport().split("#");
                        HttpResponse<JsonNode> citizenResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Citizen/" + citizenIdArray[1])
                                .header("accept", "application/json")
                                .asJson();
                        citizen = mapper.readValue(citizenResponse.getBody().getObject().toString(), Citizen.class);

                        String[] visaStatusIdArray = visaApplication.getVisaStatus().split("#");
                        HttpResponse<JsonNode> visaStatusResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.VisaStatus/" + visaStatusIdArray[1])
                                .header("accept", "application/json")
                                .asJson();
                        visaStatus = mapper.readValue(visaStatusResponse.getBody().getObject().toString(), VisaStatus.class);

                        String[] basicInfoIdArray = visaApplication.getBasicInfo().split("#");
                        HttpResponse<JsonNode> basicInfoResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.BasicInfo/" + basicInfoIdArray[1])
                                .header("accept", "application/json")
                                .asJson();
                        basicInfo = mapper.readValue(basicInfoResponse.getBody().getObject().toString(), BasicInfo.class);

                        String[] bankStatementIdArray = visaApplication.getBankStatement().split("#");
                        HttpResponse<JsonNode> bankStatementResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.BankStatement/" + bankStatementIdArray[1])
                                .header("accept", "application/json")
                                .asJson();
                        bankStatement = mapper.readValue(bankStatementResponse.getBody().getObject().toString(), BankStatement.class);

                        String[] transportationIdArray = visaApplication.getTransportationReference().split("#");
                        HttpResponse<JsonNode> transportationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.TransportationReference/" + transportationIdArray[1])
                                .header("accept", "application/json")
                                .asJson();
                        transportation = mapper.readValue(transportationResponse.getBody().getObject().toString(), TransportationReference.class);

                        String[] accommodationIdArray = visaApplication.getAccommodation().split("#");
                        HttpResponse<JsonNode> accommodationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Accommodation/" + accommodationIdArray[1])
                                .header("accept", "application/json")
                                .asJson();
                        accommodation = mapper.readValue(accommodationResponse.getBody().getObject().toString(), Accommodation.class);

                        String[] insuranceIdArrayId = visaApplication.getInsurance().split("#");
                        HttpResponse<JsonNode> insuranceResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Insurance/" + insuranceIdArrayId[1])
                                .header("accept", "application/json")
                                .asJson();
                        insurance = mapper.readValue(insuranceResponse.getBody().getObject().toString(), Insurance.class);

                        String[] localContactIdArray = visaApplication.getLocalContact().split("#");
                        HttpResponse<JsonNode> localContactResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.LocalContact/" + localContactIdArray[1])
                                .header("accept", "application/json")
                                .asJson();
                        localContact = mapper.readValue(localContactResponse.getBody().getObject().toString(), LocalContact.class);

                        String[] criminalRecordIdArray = visaApplication.getCriminalRecord().split("#");
                        HttpResponse<JsonNode> criminalRecordResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.CriminalRecord/" + criminalRecordIdArray[1])
                                .header("accept", "application/json")
                                .asJson();
                        criminalRecord = mapper.readValue(criminalRecordResponse.getBody().getObject().toString(), CriminalRecord.class);

                        String[] passportIdArray = visaApplication.getPassport().split("#");
                        HttpResponse<JsonNode> passportResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Passport/" + passportIdArray[1])
                                .header("accept", "application/json")
                                .asJson();
                        passport = mapper.readValue(passportResponse.getBody().getObject().toString(), Passport.class);

                        if (visaStatus.getStatusState().equals(Constants.APPLICATION_STATUS_APPROVED)) {
                            hasVisa = true;
                            visa = passport.getVisas().get(0);
                        } else {
                            hasVisa = false;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(EmbassyReviewVisaApplicationManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public VisaApplication getVisaApplication() {
        return visaApplication;
    }

    public void setVisaApplication(VisaApplication visaApplication) {
        this.visaApplication = visaApplication;
    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public VisaStatus getVisaStatus() {
        return visaStatus;
    }

    public void setVisaStatus(VisaStatus visaStatus) {
        this.visaStatus = visaStatus;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public BankStatement getBankStatement() {
        return bankStatement;
    }

    public void setBankStatement(BankStatement bankStatement) {
        this.bankStatement = bankStatement;
    }

    public TransportationReference getTransportation() {
        return transportation;
    }

    public void setTransportation(TransportationReference transportation) {
        this.transportation = transportation;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public LocalContact getLocalContact() {
        return localContact;
    }

    public void setLocalContact(LocalContact localContact) {
        this.localContact = localContact;
    }

    public CriminalRecord getCriminalRecord() {
        return criminalRecord;
    }

    public void setCriminalRecord(CriminalRecord criminalRecord) {
        this.criminalRecord = criminalRecord;
    }

    public boolean isHasVisa() {
        return hasVisa;
    }

    public void setHasVisa(boolean hasVisa) {
        this.hasVisa = hasVisa;
    }

    public Visa getVisa() {
        return visa;
    }

    public void setVisa(Visa visa) {
        this.visa = visa;
    }

}
