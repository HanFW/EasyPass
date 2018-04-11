/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endorsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.EndorserEntity;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import objects.Insurance;
import util.Constants;

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
    public void validateDocument() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        //$$$ValidateInsurance transaction
        System.out.println("Insurance " + insurance.getInsuranceId() + ": " + decision);

        EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
        String id = endorser.getId();
        insurance.setEndorseBy(id);

        if (decision.equals("Validated")) {
            insurance.setEndorsementState(Constants.STATUS_VALIDATED);
        } else {
            insurance.setEndorsementState(Constants.STATUS_REJECTED);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Insurance/post_request" + insurance.getOwner() + ".json"), insurance);

        ec.redirect(ec.getRequestContextPath() + "/web/endorser/insuranceCompanyViewList.xhtml?faces-redirect=true");
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

}
