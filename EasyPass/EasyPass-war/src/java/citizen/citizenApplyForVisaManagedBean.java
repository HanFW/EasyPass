/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citizen;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import objects.Accomodation;
import objects.BankStatement;
import objects.BasicInfo;
import objects.Insurance;
import objects.LocalContact;
import objects.TransportationReference;
import objects.VisaApplication;
import objects.VisaStatus;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author hanfengwei
 */
@Named(value = "citizenApplyForVisaManagedBean")
@ViewScoped
public class citizenApplyForVisaManagedBean implements Serializable{
    
    private VisaApplication visaApplication;
    private String applicationId;
    //basic info
    private String firstName;
    private String lastName;
    private Date birthday;
    private String sex;
    private String nationality;
    private String identityNumber;
    private String residentialAddress;
    private String maritalStatus;
    //plan of visit
    private Date startDate;
    private Date endDate;
    private String purposeOfVisit;
    private String localContactName;
    private String localContactIdentityNumber;
    //supporting documents
    // - bank
    private String bankName;
    private String accountNumber;
    private String bankStatementURL;
    private UploadedFile bankStatementFile;
    // - transportation
    private String carrierName;
    private String transportationReference;
    private String transportationURL;
    private UploadedFile transportationFile;
    // - accomodation
    private String accommodationName;
    private String accommodationReference;
    private String accommodationURL;
    private UploadedFile accommodationFile;
    // - insurance
    private String insuranceCompanyName;
    private String insuranceReference;
    private String insuranceURL;
    private UploadedFile insuranceFile;
    
    private boolean confirmation;

    /**
     * Creates a new instance of citizenApplyForVisaManagedBean
     */
    public citizenApplyForVisaManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        visaApplication = new VisaApplication();
        applicationId = visaApplication.getVisaApplicationId();
    }
    
    //control the flow of application tabs
    public String onFlowProcess(FlowEvent event) {
        String nextStep = event.getNewStep();
        return nextStep;
    }
    
    public void submitNewApplication (ActionEvent event) throws IOException {
        if(!confirmation){
            //ask citizen to confirm before submitting application
            FacesContext.getCurrentInstance().addMessage("confirmation", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please confirm before submitting the application", " "));        
        } else {
            //create new visa application
            ObjectMapper mapper = new ObjectMapper();
            
            // - create new visa status
            VisaStatus visaStatus = new VisaStatus("EmbassyId(TBC)",applicationId);
            mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaStatus/post_request_" + firstName + ".json"), visaStatus);
            // - create new basic info
            BasicInfo basicInfo = new BasicInfo(firstName,lastName, formatDate(birthday),identityNumber, residentialAddress, maritalStatus, "passportnumber(TBC)", sex, nationality, applicationId);
            mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/BasicInfo/post_request_" + firstName + ".json"), basicInfo);
            // - create bank statement
            BankStatement bankStatement = new BankStatement(bankName, accountNumber, bankStatementURL, "bankdId(TBC)", applicationId);
            mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/BankStatement/post_request_" + firstName + ".json"), bankStatement);
            // - create transportation reference
            TransportationReference transportation = new TransportationReference(carrierName, transportationReference, transportationURL, "carrierId(TBC)", applicationId);
            mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/TransportationReference/post_request_" + firstName + ".json"), transportation);
            // - create accommodation reference
            Accomodation accommodation = new Accomodation(accommodationName, accommodationReference, accommodationURL, "hotelId(TBC)", applicationId);
            mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/Accomodation/post_request_" + firstName + ".json"), accommodation);
            // - create insurance reference
            Insurance insurance = new Insurance(insuranceCompanyName, insuranceReference, insuranceURL, "insuranceCompanyId(TBC)", applicationId);
            mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/Insurance/post_request_" + firstName + ".json"), insurance);
            // - create local contact
            LocalContact localContact = new LocalContact(localContactName, localContactIdentityNumber, "localcontactId(TBC)", applicationId);
            mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/LocalContact/post_request_" + firstName + ".json"), localContact);
            // - update visa application
            visaApplication.updateVisaApplicationInfo(formatDate(startDate), formatDate(endDate), purposeOfVisit);
            visaApplication.updateVisaApplicationReferences(visaStatus.getVisaStatusId(), 
                    "passportId(TBC)", "citizenId(TBC)", 
                    basicInfo.getBasicInfoId(), bankStatement.getBankStatementId(), 
                    transportation.getTransportationReferenceId(), accommodation.getAccomodationId(), 
                    insurance.getInsuranceId(), localContact.getLocalContactId());
            mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaApplication/post_request_" + firstName + ".json"), visaApplication);
        }
    }
    
    private String formatDate(Date dateOriginal) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(dateOriginal);
    }
    
    public void bankStatementUpload (FileUploadEvent event) throws FileNotFoundException, IOException {
        
        System.out.println("test upload");
//        this.bankStatementFile = event.getFile();
//        
//        if (bankStatementFile != null) {
//            String filename = applicationId + "-bankStatement.pdf";
//            String newFilePath = System.getProperty("user.dir").replace("config", "docroot") + System.getProperty("file.separator");
//            OutputStream output = new FileOutputStream(new File(newFilePath, filename));
//
//            int a;
//            int BUFFER_SIZE = 8192;
//            byte[] buffer = new byte[BUFFER_SIZE];
//
//            InputStream inputStream = bankStatementFile.getInputstream();
//
//            while (true) {
//                a = inputStream.read(buffer);
//                if (a < 0) {
//                    break;
//                }
//                output.write(buffer, 0, a);
//                output.flush();
//            }
//
//            output.close();
//            inputStream.close();
//            FacesContext.getCurrentInstance().addMessage("bankStatement", new FacesMessage(FacesMessage.SEVERITY_INFO, "Bank statement uploaded successful!", " "));
//        }
    }
    
    public void transportationFileUpload (FileUploadEvent event) {
        
    }
    
    public void accommodationFileUpload (FileUploadEvent event) {
        
    }
    
    public void insuranceFileUpload (FileUploadEvent event) {
        
    }

    public VisaApplication getVisaApplication() {
        return visaApplication;
    }

    public void setVisaApplication(VisaApplication visaApplication) {
        this.visaApplication = visaApplication;
    }
    
    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }

    public String getLocalContactName() {
        return localContactName;
    }

    public void setLocalContactName(String localContactName) {
        this.localContactName = localContactName;
    }

    public String getLocalContactIdentityNumber() {
        return localContactIdentityNumber;
    }

    public void setLocalContactIdentityNumber(String localContactIdentityNumber) {
        this.localContactIdentityNumber = localContactIdentityNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankStatementURL() {
        return bankStatementURL;
    }

    public void setBankStatementURL(String bankStatementURL) {
        this.bankStatementURL = bankStatementURL;
    }

    public UploadedFile getBankStatementFile() {
        return bankStatementFile;
    }

    public void setBankStatementFile(UploadedFile bankStatementFile) {
        this.bankStatementFile = bankStatementFile;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getTransportationReference() {
        return transportationReference;
    }

    public void setTransportationReference(String transportationReference) {
        this.transportationReference = transportationReference;
    }

    public String getTransportationURL() {
        return transportationURL;
    }

    public void setTransportationURL(String transportationURL) {
        this.transportationURL = transportationURL;
    }

    public UploadedFile getTransportationFile() {
        return transportationFile;
    }

    public void setTransportationFile(UploadedFile transportationFile) {
        this.transportationFile = transportationFile;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public String getAccommodationReference() {
        return accommodationReference;
    }

    public void setAccommodationReference(String accommodationReference) {
        this.accommodationReference = accommodationReference;
    }

    public String getAccommodationURL() {
        return accommodationURL;
    }

    public void setAccommodationURL(String accommodationURL) {
        this.accommodationURL = accommodationURL;
    }

    public UploadedFile getAccommodationFile() {
        return accommodationFile;
    }

    public void setAccommodationFile(UploadedFile accommodationFile) {
        this.accommodationFile = accommodationFile;
    }

    public String getInsuranceCompanyName() {
        return insuranceCompanyName;
    }

    public void setInsuranceCompanyName(String insuranceCompanyName) {
        this.insuranceCompanyName = insuranceCompanyName;
    }

    public String getInsuranceReference() {
        return insuranceReference;
    }

    public void setInsuranceReference(String insuranceReference) {
        this.insuranceReference = insuranceReference;
    }

    public String getInsuranceURL() {
        return insuranceURL;
    }

    public void setInsuranceURL(String insuranceURL) {
        this.insuranceURL = insuranceURL;
    }

    public UploadedFile getInsuranceFile() {
        return insuranceFile;
    }

    public void setInsuranceFile(UploadedFile insuranceFile) {
        this.insuranceFile = insuranceFile;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }
}
