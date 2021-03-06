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
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objects.Accommodation;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "hotelViewListManagedBean")
@RequestScoped
public class HotelViewListManagedBean implements Serializable {

    /**
     * Creates a new instance of hotelViewListManagedBean
     */
    private ArrayList<Accommodation> pendingAccommodations;
    private ArrayList<Accommodation> verifiedAccommodations;
    private ArrayList<Accommodation> invalideAccommodations;

    public HotelViewListManagedBean() {
    }

    public ArrayList<Accommodation> getPendingAccommodations() throws IOException {
        //---Retrieve list of pending accommodation references of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            pendingAccommodations = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Accommodation/get_response.json"), new TypeReference<List<Accommodation>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> accommodationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Accommodation")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(accommodationResponse.getBody().toString());
                pendingAccommodations = mapper.readValue(accommodationResponse.getBody().toString(), new TypeReference<List<Accommodation>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<Accommodation> filteredAccommodations = new ArrayList<>();
        for (int i = 0; i < pendingAccommodations.size(); i++) {
            if (pendingAccommodations.get(i).getEndorseStatus().equals("PENDING")) {
                filteredAccommodations.add(pendingAccommodations.get(i));
            }
        }

        return filteredAccommodations;
    }

    public ArrayList<Accommodation> getValidtedAccommodations() throws IOException {
        //---Retrieve list of pending accommodation references of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            verifiedAccommodations = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Accommodation/get_response.json"), new TypeReference<List<Accommodation>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> accommodationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Accommodation")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(accommodationResponse.getBody().toString());
                verifiedAccommodations = mapper.readValue(accommodationResponse.getBody().toString(), new TypeReference<List<Accommodation>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<Accommodation> filteredAccommodations = new ArrayList<>();
        for (int i = 0; i < verifiedAccommodations.size(); i++) {
            if (verifiedAccommodations.get(i).getEndorseStatus().equals("VERIFIED")) {
                filteredAccommodations.add(verifiedAccommodations.get(i));
            }
        }

        return filteredAccommodations;
    }

    public ArrayList<Accommodation> getRejectedAccommodations() throws IOException {
        //---Retrieve list of pending accommodation references of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            invalideAccommodations = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Accommodation/get_response.json"), new TypeReference<List<Accommodation>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> accommodationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Accommodation")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(accommodationResponse.getBody().toString());
                invalideAccommodations = mapper.readValue(accommodationResponse.getBody().toString(), new TypeReference<List<Accommodation>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<Accommodation> filteredAccommodations = new ArrayList<>();
        for (int i = 0; i < invalideAccommodations.size(); i++) {
            if (invalideAccommodations.get(i).getEndorseStatus().equals("INVALIDATE")) {
                filteredAccommodations.add(invalideAccommodations.get(i));
            }
        }

        return filteredAccommodations;
    }

    //redirect to view & validate hotel booking reference of individual visa applicant
    public void viewAccommodation(Accommodation accommodation) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("accommodation", accommodation);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/hotelDoEndorsement.xhtml?faces-redirect=true");
    }
    
    //redirect to view endorsed hotel booking reference of individual visa applicant
    public void viewEndorsedAccommodation(Accommodation accommodation) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("accommodation", accommodation);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/hotelViewEndorsedDocuments.xhtml?faces-redirect=true");
    }

}
