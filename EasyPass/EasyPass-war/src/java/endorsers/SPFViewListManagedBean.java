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
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objects.Citizen;

/**
 *
 * @author Jingyuan
 */
@Named(value = "sPFViewListManagedBean")
@RequestScoped
public class SPFViewListManagedBean {

    /**
     * Creates a new instance of SPFViewListManagedBean
     */
    public SPFViewListManagedBean() {
    }
    
    //retrieve list visa applicants
    public ArrayList<Citizen> getCitizens() throws IOException{
        //---Retrieve list of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Citizen> citizens = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Participant/Citizen/get_response.json"), new TypeReference<List<Citizen>>(){});
        return citizens;
    }
    
    //redirect to  validate criminal record of individual visa applicant
    public void redirectPage (Citizen citizen) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("citizen", citizen);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/SPFDoEndorsement.xhtml?faces-redirect=true");
    }
    

}
