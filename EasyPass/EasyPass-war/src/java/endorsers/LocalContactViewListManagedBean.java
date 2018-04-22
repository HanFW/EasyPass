/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endorsers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import entity.EndorserEntity;
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import objects.Citizen;
import objects.LocalContact;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "localContactViewListManagedBean")
@ViewScoped
public class LocalContactViewListManagedBean implements Serializable {

    private ArrayList<LocalContact> localContacts = new ArrayList();
    private LocalContact localContact;
    private Citizen citizen;

    /**
     * Creates a new instance of LocalContactViewListManagedBean
     */
    public LocalContactViewListManagedBean() {
    }

    @PostConstruct
    public void init() {
        System.out.println("INIT!!!!!!!");
        try {
            localContacts = getPendingLocalContacts();
        } catch (IOException ex) {
            Logger.getLogger(LocalContactViewListManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<LocalContact> getPendingLocalContacts() throws IOException {
        //---Retrieve list of pending visa applicants of the local contact
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            localContacts = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/LocalContact/get_response.json"), new TypeReference<List<LocalContact>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> localContactResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.LocalContact")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(localContactResponse.getBody().toString());
                localContacts = mapper.readValue(localContactResponse.getBody().toString(), new TypeReference<List<LocalContact>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<LocalContact> filteredLocalContacts = new ArrayList<>();
        for (int i = 0; i < localContacts.size(); i++) {
            if (localContacts.get(i).getEndorseStatus().equals("PENDING")) {
                filteredLocalContacts.add(localContacts.get(i));
            }
        }

        return filteredLocalContacts;
    }

    public ArrayList<LocalContact> getRejectedLocalContacts() throws IOException {
        //---Retrieve list of rejected visa applicants of the local contact
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            localContacts = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/LocalContact/get_response.json"), new TypeReference<List<LocalContact>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> localContactResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.LocalContact")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(localContactResponse.getBody().toString());
                localContacts = mapper.readValue(localContactResponse.getBody().toString(), new TypeReference<List<LocalContact>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<LocalContact> filteredLocalContacts = new ArrayList<>();
        for (int i = 0; i < localContacts.size(); i++) {
            if (localContacts.get(i).getEndorseStatus().equals("INVALIDATE")) {
                filteredLocalContacts.add(localContacts.get(i));
            }
        }

        return filteredLocalContacts;
    }

    public ArrayList<LocalContact> getValidatedLocalContacts() throws IOException {
        //---Retrieve list of validated visa applicants of the local contact
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            localContacts = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/LocalContact/get_response.json"), new TypeReference<List<LocalContact>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> localContactResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.LocalContact")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(localContactResponse.getBody().toString());
                localContacts = mapper.readValue(localContactResponse.getBody().toString(), new TypeReference<List<LocalContact>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<LocalContact> filteredLocalContacts = new ArrayList<>();
        for (int i = 0; i < localContacts.size(); i++) {
            if (localContacts.get(i).getEndorseStatus().equals("VERIFIED")) {
                filteredLocalContacts.add(localContacts.get(i));
            }
        }

        return filteredLocalContacts;
    }

    //retrive the name of the citizen by citizenID (passportNumber)
    public String getCitizenNamebyID(String citizenID) throws IOException {
        
        String[] citizenIdArray = citizenID.split("#");

        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            citizen = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Participant/Citizen/get_response_byID.json"), Citizen.class);
        } else {
            try {
                HttpResponse<JsonNode> citizenResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Citizen/" + citizenIdArray[1])
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(citizenResponse.getBody().toString());
                citizen = mapper.readValue(citizenResponse.getBody().getObject().toString(), Citizen.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return citizen.getName();
    }

    public void validateVisaApplicant(LocalContact localContact) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        //$$$ValidateLocalContact transaction
        System.out.println("Validate LocalContact: " + localContact.getLocalContactId());

        if (Constants.localTesting) {

            EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
            String id = endorser.getEndorserId();
            localContact.setEndorseBy(id);

            localContact.setEndorseStatus(Constants.STATUS_VERIFIED);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Approval Submitted", " "));
            ec.getFlash().setKeepMessages(true);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/LocalContact/post_request" + localContact.getOwner() + ".json"), localContact);

        } else {

            EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
            String id = endorser.getEndorserId();
            localContact.setEndorseBy(id);

            localContact.setEndorseStatus(Constants.STATUS_VERIFIED);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Approval Submitted", " "));
            ec.getFlash().setKeepMessages(true);

            try {
                HttpResponse<JsonNode> localContactResponse = Unirest.post("http://localhost:3000/api/org.acme.easypass.ValidateLocalContactInformation")
                        .field("$class", Constants.TRANSACTION_VALIDATELOCALCONTACT)
                        .field("endorseStatus", localContact.getEndorseStatus())
                        .field("localContact", Constants.ASSET_LOCALCONTACT + "#" + localContact.getLocalContactId())
                        .field("localContactPerson", Constants.ASSET_LOCALCONTACTPERSON + "#" + endorser.getEndorserId())
                        .asJson();
                System.out.println(localContactResponse.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void rejectVisaApplicant(LocalContact localContact) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        //$$$RejectLocalContact transaction
        System.out.println("Reject LocalContact: " + localContact.getLocalContactId());

        if (Constants.localTesting) {

            EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
            String id = endorser.getEndorserId();
            localContact.setEndorseBy(id);

            localContact.setEndorseStatus(Constants.STATUS_INVALIDATE);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejection Submitted", " "));
            ec.getFlash().setKeepMessages(true);

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/LocalContact/post_request" + localContact.getOwner() + ".json"), localContact);

        } else {

            EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
            String id = endorser.getEndorserId();
            localContact.setEndorseBy(id);

            localContact.setEndorseStatus(Constants.STATUS_INVALIDATE);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejection Submitted", " "));
            ec.getFlash().setKeepMessages(true);

            try {
                HttpResponse<JsonNode> localContactResponse = Unirest.post("http://localhost:3000/api/org.acme.easypass.ValidateLocalContactInformation")
                        .field("$class", Constants.TRANSACTION_VALIDATELOCALCONTACT)
                        .field("endorseStatus", localContact.getEndorseStatus())
                        .field("localContact", Constants.ASSET_LOCALCONTACT + "#" + localContact.getLocalContactId())
                        .field("localContactPerson", Constants.ASSET_LOCALCONTACTPERSON + "#" + endorser.getEndorserId())
                        .asJson();
                System.out.println(localContactResponse.getBody());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public LocalContact getLocalContact() {
        return localContact;
    }

    public void setLocalContact(LocalContact localContact) {
        this.localContact = localContact;
    }

}
