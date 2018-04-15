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
import entity.EmbassyEntity;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import objects.Accommodation;
import objects.BankStatement;
import objects.BasicInfo;
import objects.Citizen;
import objects.CriminalRecord;
import objects.Insurance;
import objects.LocalContact;
import objects.TransportationReference;
import objects.Visa;
import objects.VisaApplication;
import objects.VisaStatus;
import util.Constants;

/**
 *
 * @author hanfengwei
 */
@Named(value = "embassyReviewVisaApplicationManagedBean")
@ViewScoped
public class EmbassyReviewVisaApplicationManagedBean implements Serializable {

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
    private String visaType;
    private Date validUtil;
    private String decision;
    private String message;
    private boolean isApproved;
    private boolean isRejected;

    /**
     * Creates a new instance of EmbassyReviewVisaApplicationManagedBean
     */
    public EmbassyReviewVisaApplicationManagedBean() {
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
                } else {
                    try {
                        HttpResponse<JsonNode> citizenResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Citizen/" + visaApplication.getPassport())
                                .header("accept", "application/json")
                                .asJson();
                        citizen = mapper.readValue(citizenResponse.getBody().getObject().toString(), Citizen.class);

                        HttpResponse<JsonNode> visaStatusResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.VisaStatus/" + visaApplication.getVisaStatus())
                                .header("accept", "application/json")
                                .asJson();
                        visaStatus = mapper.readValue(visaStatusResponse.getBody().getObject().toString(), VisaStatus.class);

                        HttpResponse<JsonNode> basicInfoResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Citizen/" + visaApplication.getBasicInfo())
                                .header("accept", "application/json")
                                .asJson();
                        basicInfo = mapper.readValue(basicInfoResponse.getBody().getObject().toString(), BasicInfo.class);

                        HttpResponse<JsonNode> bankStatementResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.BankStatement/" + visaApplication.getBankStatement())
                                .header("accept", "application/json")
                                .asJson();
                        bankStatement = mapper.readValue(bankStatementResponse.getBody().getObject().toString(), BankStatement.class);

                        HttpResponse<JsonNode> transportationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.TransportationReference/" + visaApplication.getTransportationReference())
                                .header("accept", "application/json")
                                .asJson();
                        transportation = mapper.readValue(transportationResponse.getBody().getObject().toString(), TransportationReference.class);

                        HttpResponse<JsonNode> accommodationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Accommodation/" + visaApplication.getAccommodation())
                                .header("accept", "application/json")
                                .asJson();
                        accommodation = mapper.readValue(accommodationResponse.getBody().getObject().toString(), Accommodation.class);

                        HttpResponse<JsonNode> insuranceResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Insurance/" + visaApplication.getInsurance())
                                .header("accept", "application/json")
                                .asJson();
                        insurance = mapper.readValue(insuranceResponse.getBody().getObject().toString(), Insurance.class);

                        HttpResponse<JsonNode> localContactResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.LocalContact/" + visaApplication.getLocalContact())
                                .header("accept", "application/json")
                                .asJson();
                        localContact = mapper.readValue(localContactResponse.getBody().getObject().toString(), LocalContact.class);

                        HttpResponse<JsonNode> criminalRecordResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.CriminalRecord/" + visaApplication.getCriminalRecord())
                                .header("accept", "application/json")
                                .asJson();
                        criminalRecord = mapper.readValue(criminalRecordResponse.getBody().getObject().toString(), CriminalRecord.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(EmbassyReviewVisaApplicationManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //default decision is approve
        decision = "APPROVED";
        isApproved = true;
    }

    //listener: change input panel based on decision
    public void changeDecisionPanel() {
        isApproved = decision.equals(Constants.APPLICATION_STATUS_APPROVED);
        isRejected = decision.equals(Constants.APPLICATION_STATUS_DENIED);
    }

    public void makeDecision(ActionEvent event) throws IOException {
        //set updatedBy (embassyId)
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        EmbassyEntity embassy = (EmbassyEntity) ec.getSessionMap().get("embassy");
        visaStatus.setUpdatedBy(Constants.PARTICIPANT_EMBASSY + "#" + embassy.getEmbassyId());

        //update visa status & issue visa
        ObjectMapper mapper = new ObjectMapper();
        if (isApproved) { //crete visa for approved application

            if (Constants.localTesting) {
                //update visa status

                try {
                    HttpResponse<JsonNode> visaStatusResponse = Unirest.put("http://localhost:3000/api/org.acme.easypass.VisaStatus/" + visaStatus.getVisaStatusId())
                            .header("accept", "application/json")
                            .field("$class", Constants.ASSET_VISASTATUS)
                            .field("visaStatusId", visaStatus.getVisaStatusId())
                            .field("message", visaStatus.getMessage())
                            .field("statusState", Constants.APPLICATION_STATUS_APPROVED)
                            .field("owner", visaStatus.getOwner())
                            .field("visaApplication", visaStatus.getVisaApplication())
                            .asJson();
                    System.out.println(visaStatusResponse.getBody());

                    //create new visa
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    String validUtilString = df.format(validUtil);
                    Visa visa = new Visa(validUtilString, visaType, citizen.getPassportNumber(), embassy.getEmbassyId(), visaApplication.getVisaApplicationId());
                    
                    
                    mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/Visa/post_request_" + basicInfo.getFirstName() + ".json"), visa);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                //update visa status
                visaStatus.setStatusState(Constants.APPLICATION_STATUS_APPROVED);
                mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaStatus/put_request_" + basicInfo.getFirstName() + ".json"), visaStatus);

                //create new visa
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                String validUtilString = df.format(validUtil);
                Visa visa = new Visa(validUtilString, visaType, citizen.getPassportNumber(), embassy.getEmbassyId(), visaApplication.getVisaApplicationId());
                mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/Visa/post_request_" + basicInfo.getFirstName() + ".json"), visa);
            }
        } else { //reject visa application & update message
            visaStatus.setStatusState(Constants.APPLICATION_STATUS_DENIED);
            visaStatus.setMessage(message);
            mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaStatus/put_request_" + basicInfo.getFirstName() + ".json"), visaStatus);
        }

        ec.redirect(ec.getRequestContextPath() + "/web/embassy/embassyViewList.xhtml?faces-redirect=true");
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

    public String getVisaType() {
        return visaType;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }

    public Date getValidUtil() {
        return validUtil;
    }

    public void setValidUtil(Date validUtil) {
        this.validUtil = validUtil;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isIsApproved() {
        return isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public boolean isIsRejected() {
        return isRejected;
    }

    public void setIsRejected(boolean isRejected) {
        this.isRejected = isRejected;
    }
}
