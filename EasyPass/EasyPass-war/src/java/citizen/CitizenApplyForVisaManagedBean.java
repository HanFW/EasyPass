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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import objects.Accommodation;
import objects.BankStatement;
import objects.BasicInfo;
import objects.CriminalRecord;
import objects.Insurance;
import objects.LocalContact;
import objects.TransportationReference;
import objects.VisaApplication;
import objects.VisaStatus;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;
import util.Constants;

/**
 *
 * @author hanfengwei
 */
@Named(value = "citizenApplyForVisaManagedBean")
@ViewScoped
public class CitizenApplyForVisaManagedBean implements Serializable {

    private VisaApplication visaApplication;
    private String applicationId;
    //basic info
    private String firstName;
    private String lastName;
    private Date birthday;
    private String sex;
    private String nationality;
    private String countryOfResidence;
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
    // - accommodation
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
    public CitizenApplyForVisaManagedBean() {
    }

    @PostConstruct
    public void init() {
        visaApplication = new VisaApplication();
        applicationId = visaApplication.getVisaApplicationId();
    }

    //control the flow of application tabs
    public String onFlowProcess(FlowEvent event) {
        String nextStep = event.getNewStep();

        //documents validation
        if (event.getOldStep().equals("documents")) {
            if (bankStatementFile == null) {
                FacesContext.getCurrentInstance().addMessage("bankStatement", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please upload your bank statement.", " "));
                nextStep = event.getOldStep();
            }
            if (transportationFile == null) {
                FacesContext.getCurrentInstance().addMessage("transportationFile", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please upload your transportation document.", " "));
                nextStep = event.getOldStep();
            }
            if (accommodationFile == null) {
                FacesContext.getCurrentInstance().addMessage("accommodationFile", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please upload your accommodation document.", " "));
                nextStep = event.getOldStep();
            }
            if (insuranceFile == null) {
                FacesContext.getCurrentInstance().addMessage("insuranceFile", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please upload your Insurance document.", " "));
                nextStep = event.getOldStep();
            }
        }
        return nextStep;
    }

    public void submitNewApplication(ActionEvent event) throws IOException {
        if (!confirmation) {
            //ask citizen to confirm before submitting application
            FacesContext.getCurrentInstance().addMessage("confirmation", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please confirm before submitting the application", " "));
        } else {
            CitizenEntity citizen = (CitizenEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("citizen");

            if (Constants.localTesting) {
                //create new visa application
                ObjectMapper mapper = new ObjectMapper();

                // - create new visa status
                VisaStatus visaStatus = new VisaStatus(applicationId, citizen.getCitizenId());
                mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaStatus/post_request_" + firstName + ".json"), visaStatus);
                // - create new basic info
                BasicInfo basicInfo = new BasicInfo(firstName, lastName, formatDate(birthday), countryOfResidence, identityNumber, residentialAddress, maritalStatus, citizen.getCitizenId(), sex, nationality, applicationId, citizen.getCitizenId());
                mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/BasicInfo/post_request_" + firstName + ".json"), basicInfo);
                // - create bank statement
                BankStatement bankStatement = new BankStatement(bankName, accountNumber, bankStatementURL, applicationId, citizen.getCitizenId());
                mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/BankStatement/post_request_" + firstName + ".json"), bankStatement);
                // - create transportation reference
                TransportationReference transportation = new TransportationReference(carrierName, transportationReference, transportationURL, applicationId, citizen.getCitizenId());
                mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/TransportationReference/post_request_" + firstName + ".json"), transportation);
                // - create accommodation reference
                Accommodation accommodation = new Accommodation(accommodationName, accommodationReference, accommodationURL, applicationId, citizen.getCitizenId());
                mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/Accommodation/post_request_" + firstName + ".json"), accommodation);
                // - create insurance reference
                Insurance insurance = new Insurance(insuranceCompanyName, insuranceReference, insuranceURL, applicationId, citizen.getCitizenId());
                mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/Insurance/post_request_" + firstName + ".json"), insurance);
                // - create local contact
                LocalContact localContact = new LocalContact(localContactName, localContactIdentityNumber, applicationId, citizen.getCitizenId());
                mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/LocalContact/post_request_" + firstName + ".json"), localContact);
                // - create criminal record
                CriminalRecord criminalRecord = new CriminalRecord(applicationId, citizen.getCitizenId(), firstName + " " + lastName);
                mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/CriminalRecord/post_request_" + firstName + ".json"), criminalRecord);
                // - update visa application
                visaApplication.updateVisaApplicationInfo(formatDate(startDate), formatDate(endDate), purposeOfVisit);
                visaApplication.updateVisaApplicationReferences(visaStatus.getVisaStatusId(),
                        citizen.getCitizenId(), citizen.getCitizenId(),
                        basicInfo.getBasicInfoId(), bankStatement.getBankStatementId(),
                        transportation.getTransportationReferenceId(), accommodation.getAccommodationId(),
                        insurance.getInsuranceId(), localContact.getLocalContactId(), criminalRecord.getCriminalRecordId());
                mapper.writeValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaApplication/post_request_" + firstName + ".json"), visaApplication);

            } else {
                // http request;
                //create new visa application
                ObjectMapper mapper = new ObjectMapper();

                // - create new visa status
                VisaStatus visaStatus = new VisaStatus(applicationId, citizen.getCitizenId());
                try {
                    HttpResponse<JsonNode> visaStatusResponse = Unirest.post("http://localhost:3000/api/org.acme.easypass.VisaStatus")
                            .header("accept", "application/json")
                            .field("$class", Constants.ASSET_VISASTATUS)
                            .field("visaStatusId", visaStatus.getVisaStatusId())
                            .field("message", visaStatus.getMessage())
                            .field("statusState", visaStatus.getStatusState())
                            .field("owner", visaStatus.getOwner())
                            .field("visaApplication", visaStatus.getVisaApplication())
                            .asJson();
                    System.out.println(visaStatusResponse.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // - create new basic info
                BasicInfo basicInfo = new BasicInfo(firstName, lastName, formatDate(birthday), countryOfResidence, identityNumber, residentialAddress, maritalStatus, citizen.getCitizenId(), sex, nationality, applicationId, citizen.getCitizenId());
                try {
                    HttpResponse<JsonNode> basicInfoResponse = Unirest.post("http://localhost:3000/api/org.acme.easypass.BasicInfo")
                            .header("accept", "application/json")
                            .field("$class", Constants.ASSET_BASICINFO)
                            .field("basicInfoId", basicInfo.getBasicInfoId())
                            .field("firstName", basicInfo.getFirstName())
                            .field("lastName", basicInfo.getLastName())
                            .field("birthday", basicInfo.getBirthday())
                            .field("identityNumber", basicInfo.getIdentityNumber())
                            .field("residentialAddress", basicInfo.getResidentialAddress())
                            .field("countryOfResidence", basicInfo.getCountryOfResidence())
                            .field("maritalStatus", basicInfo.getMaritalStatus())
                            .field("passportNumber", basicInfo.getPassportNumber())
                            .field("sex", basicInfo.getSex())
                            .field("nationality", basicInfo.getNationality())
                            .field("endorseStatus", basicInfo.getEndorseStatus())
                            .field("owner", basicInfo.getOwner())
                            .field("visaApplication", basicInfo.getVisaApplication())
                            .asJson();
                    System.out.println(basicInfoResponse.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // - create bank statement
                BankStatement bankStatement = new BankStatement(bankName, accountNumber, bankStatementURL, applicationId, citizen.getCitizenId());
                try {
                    HttpResponse<JsonNode> bankStatementResponse = Unirest.post("http://localhost:3000/api/org.acme.easypass.BankStatement")
                            .header("accept", "application/json")
                            .field("$class", Constants.ASSET_BANKSTATEMENT)
                            .field("bankStatementId", bankStatement.getBankStatementId())
                            .field("bankName", bankStatement.getBankName())
                            .field("accountNumber", bankStatement.getAccountNumber())
                            .field("bankStatementImageURL", bankStatement.getBankStatementImageURL())
                            .field("endorseStatus", bankStatement.getEndorseStatus())
                            .field("owner", bankStatement.getOwner())
                            .field("visaApplication", bankStatement.getVisaApplication())
                            .asJson();
                    System.out.println(bankStatementResponse.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // - create transportation reference
                TransportationReference transportation = new TransportationReference(carrierName, transportationReference, transportationURL, applicationId, citizen.getCitizenId());
                try {
                    HttpResponse<JsonNode> transportationResponse = Unirest.post("http://localhost:3000/api/org.acme.easypass.TransportationReference")
                            .header("accept", "application/json")
                            .field("$class", Constants.ASSET_TRANSPORTATION)
                            .field("transportationReferenceId", transportation.getTransportationReferenceId())
                            .field("carrierName", transportation.getCarrierName())
                            .field("reference", transportation.getReference())
                            .field("transportationReferenceImageURL", transportation.getTransportationReferenceImageURL())
                            .field("endorseStatus", transportation.getEndorseStatus())
                            .field("owner", transportation.getOwner())
                            .field("visaApplication", transportation.getVisaApplication())
                            .asJson();
                    System.out.println(transportationResponse.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // - create accommodation reference
                Accommodation accommodation = new Accommodation(accommodationName, accommodationReference, accommodationURL, applicationId, citizen.getCitizenId());
                try {
                    HttpResponse<JsonNode> accommodationResponse = Unirest.post("http://localhost:3000/api/org.acme.easypass.Accommodation")
                            .header("accept", "application/json")
                            .field("$class", Constants.ASSET_ACCOMMODATION)
                            .field("accommodationId", accommodation.getAccommodationId())
                            .field("carrierName", accommodation.getCarrierName())
                            .field("referenceNumber", accommodation.getReferenceNumber())
                            .field("accommodationReferenceImageURL", accommodation.getAccommodationReferenceImageURL())
                            .field("endorseStatus", accommodation.getEndorseStatus())
                            .field("owner", accommodation.getOwner())
                            .field("visaApplication", accommodation.getVisaApplication())
                            .asJson();
                    System.out.println(accommodationResponse.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // - create insurance reference
                Insurance insurance = new Insurance(insuranceCompanyName, insuranceReference, insuranceURL, applicationId, citizen.getCitizenId());
                try {
                    HttpResponse<JsonNode> insuranceResponse = Unirest.post("http://localhost:3000/api/org.acme.easypass.Insurance")
                            .header("accept", "application/json")
                            .field("$class", Constants.ASSET_INSURANCE)
                            .field("insuranceId", insurance.getInsuranceId())
                            .field("companyName", insurance.getCompanyName())
                            .field("referenceNumber", insurance.getReferenceNumber())
                            .field("insuranceContractImageURL", insurance.getInsuranceContractImageURL())
                            .field("endorseStatus", insurance.getEndorseStatus())
                            .field("owner", insurance.getOwner())
                            .field("visaApplication", insurance.getVisaApplication())
                            .asJson();
                    System.out.println(insuranceResponse.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // - create local contact
                LocalContact localContact = new LocalContact(localContactName, localContactIdentityNumber, applicationId, citizen.getCitizenId());
                try {
                    HttpResponse<JsonNode> localContactResponse = Unirest.post("http://localhost:3000/api/org.acme.easypass.LocalContact")
                            .header("accept", "application/json")
                            .field("$class", Constants.ASSET_LOCALCONTACT)
                            .field("localContactId", localContact.getLocalContactId())
                            .field("contactName", localContact.getContactName())
                            .field("identityNumber", localContact.getIdentityNumber())
                            .field("endorseStatus", localContact.getEndorseStatus())
                            .field("owner", localContact.getOwner())
                            .field("visaApplication", localContact.getVisaApplication())
                            .asJson();
                    System.out.println(localContactResponse.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // - create criminal record
                CriminalRecord criminalRecord = new CriminalRecord(applicationId, citizen.getCitizenId(), firstName + " " + lastName);
                try {
                    HttpResponse<JsonNode> criminalRecordResponse = Unirest.post("http://localhost:3000/api/org.acme.easypass.CriminalRecord")
                            .header("accept", "application/json")
                            .field("$class", Constants.ASSET_CRIMINALRECORD)
                            .field("criminalRecordId", criminalRecord.getCriminalRecordId())
                            .field("recordNumber", criminalRecord.getRecordNumber())
                            .field("recordDetail", criminalRecord.getRecordDetail())
                            .field("endorseStatus", criminalRecord.getEndorseStatus())
                            .field("owner", criminalRecord.getOwner())
                            .field("visaApplication", criminalRecord.getVisaApplication())
                            .field("fullName", criminalRecord.getFullName())
                            .asJson();
                    System.out.println(criminalRecordResponse.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    HttpResponse<JsonNode> visaApplicationResponse = Unirest.post("http://localhost:3000/api/org.acme.easypass.VisaApplication")
                            .header("accept", "application/json")
                            .field("$class", Constants.ASSET_VISAAPPLICATION)
                            .field("visaApplicationId", visaApplication.getVisaApplicationId())
                            .field("startDate", formatDate(startDate))
                            .field("endDate", formatDate(endDate))
                            .field("purposeOfVisit", purposeOfVisit)
                            .field("state", "PENDING")
                            .field("visaStatus", visaStatus.getVisaStatusId())
                            .field("passport", citizen.getCitizenId())
                            .field("basicInfo", basicInfo.getBasicInfoId())
                            .field("bankStatement", bankStatement.getBankStatementId())
                            .field("transportationReference", transportation.getTransportationReferenceId())
                            .field("accommodation", accommodation.getAccommodationId())
                            .field("insurance", insurance.getInsuranceId())
                            .field("localContact", localContact.getLocalContactId())
                            .field("criminalRecord", criminalRecord.getCriminalRecordId())
                            .field("owner", citizen.getCitizenId())
                            .asJson();
                    System.out.println("post" + visaApplicationResponse.getBody());

                    HttpResponse<JsonNode> passportResponse = Unirest.put("http://localhost:3000/api/org.acme.easypass.Passport/" + citizen.getCitizenId())
                            .header("accept", "application/json")
                            .field("$class", Constants.ASSET_PASSPORT)
                            .field("passportNumber", citizen.getCitizenId())
                            .field("fullName", firstName + " " + lastName)
                            .field("visaApplications", visaApplicationResponse.getBody().getArray())
                            .field("owner", citizen.getCitizenId())
                            .asJson();
                    System.out.println(passportResponse.getBody());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //redirect to view application status
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Submitted!", " "));
            ec.getFlash().setKeepMessages(true);
            ec.redirect(ec.getRequestContextPath() + "/web/citizen/citizenPortalMainPage.xhtml?faces-redirect=true");
        }
    }

    private String formatDate(Date dateOriginal) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        return df.format(dateOriginal);
    }

    public void bankStatementUpload(FileUploadEvent event) throws FileNotFoundException, IOException {
        this.bankStatementFile = event.getFile();

        if (bankStatementFile != null) {
            String filename = applicationId + "-bankStatement.pdf";
            bankStatementURL = "https://localhost:8181/" + filename;
            String newFilePath = System.getProperty("user.dir").replace("config", "docroot") + System.getProperty("file.separator");
            InputStream inputStream;
            try (OutputStream output = new FileOutputStream(new File(newFilePath, filename))) {
                int a;
                int BUFFER_SIZE = 8192;
                byte[] buffer = new byte[BUFFER_SIZE];
                inputStream = bankStatementFile.getInputstream();
                while (true) {
                    a = inputStream.read(buffer);
                    if (a < 0) {
                        break;
                    }
                    output.write(buffer, 0, a);
                    output.flush();
                }
            }
            inputStream.close();
            FacesContext.getCurrentInstance().addMessage("bankStatement", new FacesMessage(FacesMessage.SEVERITY_INFO, "Bank statement uploaded successfully!", " "));
        } else {
            FacesContext.getCurrentInstance().addMessage("bankStatement", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot find file, please upload again.", " "));
        }
    }

    public void transportationFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {
        this.transportationFile = event.getFile();

        if (transportationFile != null) {
            String filename = applicationId + "-transportation.pdf";
            transportationURL = "https://localhost:8181/" + filename;
            String newFilePath = System.getProperty("user.dir").replace("config", "docroot") + System.getProperty("file.separator");
            InputStream inputStream;
            try (OutputStream output = new FileOutputStream(new File(newFilePath, filename))) {
                int a;
                int BUFFER_SIZE = 8192;
                byte[] buffer = new byte[BUFFER_SIZE];
                inputStream = bankStatementFile.getInputstream();
                while (true) {
                    a = inputStream.read(buffer);
                    if (a < 0) {
                        break;
                    }
                    output.write(buffer, 0, a);
                    output.flush();
                }
            }
            inputStream.close();
            FacesContext.getCurrentInstance().addMessage("transportationFile", new FacesMessage(FacesMessage.SEVERITY_INFO, "Transportation document uploaded successfully!", " "));
        } else {
            FacesContext.getCurrentInstance().addMessage("transportationFile", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot find file, please upload again.", " "));
        }
    }

    public void accommodationFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {
        this.accommodationFile = event.getFile();

        if (accommodationFile != null) {
            String filename = applicationId + "-accommodation.pdf";
            accommodationURL = "https://localhost:8181/" + filename;
            String newFilePath = System.getProperty("user.dir").replace("config", "docroot") + System.getProperty("file.separator");
            InputStream inputStream;
            try (OutputStream output = new FileOutputStream(new File(newFilePath, filename))) {
                int a;
                int BUFFER_SIZE = 8192;
                byte[] buffer = new byte[BUFFER_SIZE];
                inputStream = bankStatementFile.getInputstream();
                while (true) {
                    a = inputStream.read(buffer);
                    if (a < 0) {
                        break;
                    }
                    output.write(buffer, 0, a);
                    output.flush();
                }
            }
            inputStream.close();
            FacesContext.getCurrentInstance().addMessage("accommodationFile", new FacesMessage(FacesMessage.SEVERITY_INFO, "Accommodation document uploaded successfully!", " "));
        } else {
            FacesContext.getCurrentInstance().addMessage("accommodationFile", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot find file, please upload again.", " "));
        }
    }

    public void insuranceFileUpload(FileUploadEvent event) throws FileNotFoundException, IOException {
        this.insuranceFile = event.getFile();

        if (insuranceFile != null) {
            String filename = applicationId + "-insurance.pdf";
            insuranceURL = "https://localhost:8181/" + filename;
            String newFilePath = System.getProperty("user.dir").replace("config", "docroot") + System.getProperty("file.separator");
            InputStream inputStream;
            try (OutputStream output = new FileOutputStream(new File(newFilePath, filename))) {
                int a;
                int BUFFER_SIZE = 8192;
                byte[] buffer = new byte[BUFFER_SIZE];
                inputStream = bankStatementFile.getInputstream();
                while (true) {
                    a = inputStream.read(buffer);
                    if (a < 0) {
                        break;
                    }
                    output.write(buffer, 0, a);
                    output.flush();
                }
            }
            inputStream.close();
            FacesContext.getCurrentInstance().addMessage("insuranceFile", new FacesMessage(FacesMessage.SEVERITY_INFO, "Insurance document uploaded successfully!", " "));
        } else {
            FacesContext.getCurrentInstance().addMessage("insuranceFile", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot find file, please upload again.", " "));
        }
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

    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
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
