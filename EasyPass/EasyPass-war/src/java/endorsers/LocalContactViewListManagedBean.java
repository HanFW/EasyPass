/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endorsers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import objects.Citizen;
import objects.LocalContact;

/**
 *
 * @author Jingyuan
 */
@Named(value = "localContactViewListManagedBean")
@ViewScoped
public class LocalContactViewListManagedBean implements Serializable {
    private ArrayList<LocalContact>  localContacts= new ArrayList();
    private LocalContact localContact;

    /**
     * Creates a new instance of LocalContactViewListManagedBean
     */
    public LocalContactViewListManagedBean() {
    }
    
        
    @PostConstruct
    public void init(){
       System.out.println("INIT!!!!!!!");
        try {
            localContacts = getLocalContacts();
        } catch (IOException ex) {
            Logger.getLogger(LocalContactViewListManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<LocalContact> getLocalContacts() throws IOException {
        //---Retrieve list of visa applicants of the local contact
        ObjectMapper mapper = new ObjectMapper();
        localContacts = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/LocalContact/get_response.json"), new TypeReference<List<LocalContact>>() {
        });
        return localContacts;
    }

    //retrive the name of the citizen by citizenID (passportNumber)
    public String getCitizenNamebyID(String citizenID) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Citizen citizen = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Participant/Citizen/get_response_byID.json"), Citizen.class);
        return citizen.getName();
    }
    

    public void validateVisaApplicant(LocalContact localContact) throws IOException {
        
        //$$$ValidateLocalContact transaction
        System.out.println("Validate LocalContact: " + localContact.getLocalContactId());
        

    }
    
    public void rejectVisaApplicant(LocalContact localContact) throws IOException{
        //$$$RejectLocalContact transaction
        System.out.println("Reject LocalContact: " + localContact.getLocalContactId());
    }

    public LocalContact getLocalContact() {
        return localContact;
    }

    public void setLocalContact(LocalContact localContact) {
        this.localContact = localContact;
    }

}
