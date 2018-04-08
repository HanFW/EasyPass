/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endorsers;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import objects.Accomodation;

/**
 *
 * @author Jingyuan
 */
@Named(value = "hotelDoEndorsementManagedBean")
@ViewScoped
public class hotelDoEndorsementManagedBean implements Serializable {
    private Accomodation accomodation;
    private String decision; 
    
    /**
     * Creates a new instance of hotelDoEndorsementManagedBean
     */
    public hotelDoEndorsementManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        accomodation = (Accomodation) ec.getFlash().get("accomodation");
        System.out.println("View accomodation: " + accomodation.getAccomodationId());
    }
    
    //validate or reject document
    public void validateDocument () throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        //$$$ValidateAccomodation transaction
        System.out.println("Accomodation " + accomodation.getAccomodationId() + ": " + decision);
        
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/hotelViewList.xhtml?faces-redirect=true");
    }

    public Accomodation getAccomodation() {
        return accomodation;
    }

    public void setAccomodation(Accomodation accomodation) {
        this.accomodation= accomodation;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
    
}
