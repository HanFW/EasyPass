/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embassy;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        visaStatus.setUpdatedBy(Constants.PARTICIPANT_EMBASSY + "#" + embassy.getId());
        
        //update visa status & issue visa
        ObjectMapper mapper = new ObjectMapper();
        if(isApproved) { //crete visa for approved application
            //update visa status
            visaStatus.setState(Constants.APPLICATION_STATUS_APPROVED);
            mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaStatus/put_request_" + basicInfo.getFirstName() + ".json"), visaStatus);
            
            //create new visa
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String validUtilString = df.format(validUtil);
            Visa visa = new Visa(validUtilString, visaType, citizen.getPassportNumber(), embassy.getId(), visaApplication.getVisaApplicationId());
            mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/Visa/post_request_" + basicInfo.getFirstName() + ".json"), visa);
        } else { //reject visa application & update message
            visaStatus.setState(Constants.APPLICATION_STATUS_DENIED);
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
