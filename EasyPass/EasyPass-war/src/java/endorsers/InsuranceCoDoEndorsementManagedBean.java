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
import objects.Insurance;

/**
 *
 * @author Jingyuan
 */
@Named(value = "insuranceCoDoEndorsementManagedBean")
@ViewScoped
public class InsuranceCoDoEndorsementManagedBean implements Serializable {
   private Insurance insurance;
   private String decision;
    /**
     * Creates a new instance of InsuranceCoDoEndorsementManagedBean
     */
    public InsuranceCoDoEndorsementManagedBean() {
    }
    
     @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        insurance = (Insurance) ec.getFlash().get("insurance");
        System.out.println("View insurance: " + insurance.getInsuranceId());
    }
    
    //validate or reject document
    public void validateDocument () throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        //$$$ValidateInsurance transaction
        System.out.println("Insurance " + insurance.getInsuranceId() + ": " + decision);
        
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/insuranceCompanyViewList.xhtml?faces-redirect=true");
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance= insurance;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
    
}
