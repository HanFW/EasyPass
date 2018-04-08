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
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objects.TransportationReference;

/**
 *
 * @author Jingyuan
 */
@Named(value = "transportationProviderViewListManagedBean")
@SessionScoped
public class TransportationProviderViewListManagedBean implements Serializable {

    /**
     * Creates a new instance of TransportationProviderViewListManagedBean
     */
    public TransportationProviderViewListManagedBean() {
    }
    
     public ArrayList<TransportationReference> getTransportationReferences() throws IOException{
        //---Retrieve list of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<TransportationReference> transportationReferences = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/TransportationReference/get_response.json"), new TypeReference<List<TransportationReference>>(){});
        return transportationReferences;
    }
    
        //redirect to view & validate transportation reference of individual visa applicant
    public void viewTransportationReference (TransportationReference transportationReference) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("transportationReference", transportationReference);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/TransportationProviderDoEndorsement.xhtml?faces-redirect=true");
    }
    
}
