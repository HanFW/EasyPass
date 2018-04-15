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
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objects.TransportationReference;

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
    public TransportationProviderViewListManagedBean() {
    }

    public ArrayList<TransportationReference> getPendingTransportationReferences() throws IOException {
        //---Retrieve list of pending visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<TransportationReference> transportationReferences = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/TransportationReference/get_response.json"), new TypeReference<List<TransportationReference>>() {
        });

        ArrayList<TransportationReference> filteredTransportationReferences = new ArrayList<>();
        for (int i = 0; i < transportationReferences.size(); i++) {
            if (transportationReferences.get(i).getEndorseStatus().equals("PENDING")) {
                filteredTransportationReferences.add(transportationReferences.get(i));
            }
        }

        return filteredTransportationReferences;
    }

    public ArrayList<TransportationReference> getValidatedTransportationReferences() throws IOException {
        //---Retrieve list of validated visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<TransportationReference> transportationReferences = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/TransportationReference/get_response.json"), new TypeReference<List<TransportationReference>>() {
        });

        ArrayList<TransportationReference> filteredTransportationReferences = new ArrayList<>();
        for (int i = 0; i < transportationReferences.size(); i++) {
            if (transportationReferences.get(i).getEndorseStatus().equals("VERIFIED")) {
                filteredTransportationReferences.add(transportationReferences.get(i));
            }
        }

        return filteredTransportationReferences;
    }

    public ArrayList<TransportationReference> getRejectedTransportationReferences() throws IOException {
        //---Retrieve list of rejected visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<TransportationReference> transportationReferences = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/TransportationReference/get_response.json"), new TypeReference<List<TransportationReference>>() {
        });

        ArrayList<TransportationReference> filteredTransportationReferences = new ArrayList<>();
        for (int i = 0; i < transportationReferences.size(); i++) {
            if (transportationReferences.get(i).getEndorseStatus().equals("INVALIDATE")) {
                filteredTransportationReferences.add(transportationReferences.get(i));
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
