/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citizen;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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
        applicationId = UUID.randomUUID().toString();
    }
    
    //control the flow of application tabs
    public String onFlowProcess(FlowEvent event) {
        String nextStep = event.getNewStep();
        return nextStep;
    }
    
    public void submitNewApplication (ActionEvent event) {
        if(!confirmation){
            //ask citizen to confirm before submitting application
            FacesContext.getCurrentInstance().addMessage("confirmation", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please confirm before submitting the application", " "));        
        } else {
            //create new visa application
            System.out.println("create new application");
            ObjectMapper mapper = new ObjectMapper();

        }
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
