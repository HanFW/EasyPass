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
import objects.Accommodation;

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
    public HotelViewListManagedBean() {
    }
    
    public ArrayList<Accommodation> getAccommodations() throws IOException{
        //---Retrieve list of pending accommodation references of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Accommodation> accommodations = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Accommodation/get_response.json"), new TypeReference<List<Accommodation>>(){});
        return accommodations;
    }
    
        //redirect to view & validate hotel booking reference of individual visa applicant
    public void viewAccommodation (Accommodation accommodation) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("accommodation", accommodation);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/hotelDoEndorsement.xhtml?faces-redirect=true");
    }
    
}
