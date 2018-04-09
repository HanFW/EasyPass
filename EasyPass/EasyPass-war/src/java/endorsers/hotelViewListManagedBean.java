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
import objects.Accomodation;

/**
 *
 * @author Jingyuan
 */
@Named(value = "hotelViewListManagedBean")
@RequestScoped
public class hotelViewListManagedBean implements Serializable {

    /**
     * Creates a new instance of hotelViewListManagedBean
     */
    public hotelViewListManagedBean() {
    }
    
    public ArrayList<Accomodation> getAccomodations() throws IOException{
        //---Retrieve list of pending accomodation references of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Accomodation> accomodations = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Accomodation/get_response.json"), new TypeReference<List<Accomodation>>(){});
        return accomodations;
    }
    
        //redirect to view & validate hotel booking reference of individual visa applicant
    public void viewAccomodation (Accomodation accomodation) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("accomodation", accomodation);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/hotelDoEndorsement.xhtml?faces-redirect=true");
    }
    
}
