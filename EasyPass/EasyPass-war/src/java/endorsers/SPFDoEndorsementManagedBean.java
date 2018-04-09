/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endorsers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import objects.Citizen;
import objects.CriminalRecord;

/**
 *
 * @author Jingyuan
 */
@Named(value = "sPFDoEndorsementManagedBean")
@ViewScoped
public class SPFDoEndorsementManagedBean implements Serializable {

    private Citizen citizen;
    private String decision;
    private Boolean showPanel;
    private String recordDetails;

    /**
     * Creates a new instance of SPFDoEndorsementManagedBean
     */
    public SPFDoEndorsementManagedBean() {
    }

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        citizen = (Citizen) ec.getFlash().get("citizen");
        System.out.println("View citizen: " + citizen.getPassportNumber());
    }

    public void submitCriminalRecord() throws IOException {
        System.out.println("Criminal Record submitted");
        ObjectMapper mapper = new ObjectMapper();

        String owner = citizen.getPassportNumber();
        if (decision.equals("Validated")) {
            CriminalRecord record = new CriminalRecord("", "VERIFIED", owner);
            mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/CriminalRecord/post_response"+owner+".json"), record);
        } else {
            CriminalRecord record = new CriminalRecord(recordDetails, "INVALIDATE", owner);
            mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/CriminalRecord/post_response"+owner+".json"), record);
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        //validate criminal record of visa applicant
        System.out.println("Criminal Record:  " + citizen.getPassportNumber() + ": " + decision);

        ec.redirect(ec.getRequestContextPath() + "/web/endorser/SPFViewList.xhtml?faces-redirect=true");

    }

    public void showCriminalRecordPanel() {
        if (decision.equals("Rejected")) {
            showPanel = true;
        } else {
            showPanel = false;
        }

    }

    public Citizen getCitizen() {
        return citizen;
    }

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Boolean getShowPanel() {
        return showPanel;
    }

    public void setShowPanel(Boolean showPanel) {
        this.showPanel = showPanel;
    }

    public String getRecordDetails() {
        return recordDetails;
    }

    public void setRecordDetails(String recordDetails) {
        this.recordDetails = recordDetails;
    }

}
