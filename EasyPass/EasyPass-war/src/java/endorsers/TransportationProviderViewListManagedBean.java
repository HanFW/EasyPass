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
import objects.TransportationReference;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "transportationProviderViewListManagedBean")
@RequestScoped
public class TransportationProviderViewListManagedBean implements Serializable {

    /**
     * Creates a new instance of TransportationProviderViewListManagedBean
     */
    private ArrayList<TransportationReference> pendingTransportationReferences;
    private ArrayList<TransportationReference> verifiedTransportationReferences;
    private ArrayList<TransportationReference> invalideTransportationReferences;

    public TransportationProviderViewListManagedBean() {
    }

    public ArrayList<TransportationReference> getPendingTransportationReferences() throws IOException {
        //---Retrieve list of pending visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            pendingTransportationReferences = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/TransportationReference/get_response.json"), new TypeReference<List<TransportationReference>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> transportationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.TransportationReference")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(transportationResponse.getBody().toString());
                pendingTransportationReferences = mapper.readValue(transportationResponse.getBody().toString(), new TypeReference<List<TransportationReference>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<TransportationReference> filteredTransportationReferences = new ArrayList<>();
        for (int i = 0; i < pendingTransportationReferences.size(); i++) {
            if (pendingTransportationReferences.get(i).getEndorseStatus().equals("PENDING")) {
                filteredTransportationReferences.add(pendingTransportationReferences.get(i));
            }
        }

        return filteredTransportationReferences;
    }

    public ArrayList<TransportationReference> getValidatedTransportationReferences() throws IOException {
        //---Retrieve list of validated visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            verifiedTransportationReferences = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/TransportationReference/get_response.json"), new TypeReference<List<TransportationReference>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> transportationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.TransportationReference")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(transportationResponse.getBody().toString());
                verifiedTransportationReferences = mapper.readValue(transportationResponse.getBody().toString(), new TypeReference<List<TransportationReference>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<TransportationReference> filteredTransportationReferences = new ArrayList<>();
        for (int i = 0; i < verifiedTransportationReferences.size(); i++) {
            if (verifiedTransportationReferences.get(i).getEndorseStatus().equals("VERIFIED")) {
                filteredTransportationReferences.add(verifiedTransportationReferences.get(i));
            }
        }

        return filteredTransportationReferences;
    }

    public ArrayList<TransportationReference> getRejectedTransportationReferences() throws IOException {
        //---Retrieve list of rejected visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            invalideTransportationReferences = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/TransportationReference/get_response.json"), new TypeReference<List<TransportationReference>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> transportationResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.TransportationReference")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(transportationResponse.getBody().toString());
                invalideTransportationReferences = mapper.readValue(transportationResponse.getBody().toString(), new TypeReference<List<TransportationReference>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<TransportationReference> filteredTransportationReferences = new ArrayList<>();
        for (int i = 0; i < invalideTransportationReferences.size(); i++) {
            if (invalideTransportationReferences.get(i).getEndorseStatus().equals("INVALIDATE")) {
                filteredTransportationReferences.add(invalideTransportationReferences.get(i));
            }
        }

        return filteredTransportationReferences;
    }

    //redirect to view & validate transportation reference of individual visa applicant
    public void viewTransportationReference(TransportationReference transportationReference) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("transportationReference", transportationReference);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/TransportationProviderDoEndorsement.xhtml?faces-redirect=true");
    }

}
