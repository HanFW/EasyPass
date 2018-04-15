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
import objects.CriminalRecord;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "sPFDoEndorsementManagedBean")
@ViewScoped
public class SPFDoEndorsementManagedBean implements Serializable {

    private CriminalRecord criminalRecord;
    private String decision;
    private Boolean showPanel;
    private String recordDetail;
    private String recordNumber;

    /**
     * Creates a new instance of SPFDoEndorsementManagedBean
     */
    public SPFDoEndorsementManagedBean() {
    }

    @PostConstruct
    public void init() {
        System.out.println("init!!!!!!");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        criminalRecord = (CriminalRecord) ec.getFlash().get("criminalRecord");
        System.out.println("criminal record: " + criminalRecord);
//        System.out.println("View criminalRecord: " + criminalRecord.getCriminalRecordId());
    }

    public void updateCriminalRecord() throws IOException {
        System.out.println("Criminal Record updated");
        ObjectMapper mapper = new ObjectMapper();

        //get endorserID 
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
        String endorserID = endorser.getEndorserId();

        String owner = criminalRecord.getOwner();
        if (decision.equals("Validated")) {
            criminalRecord.setEndorseStatus(Constants.STATUS_VERIFIED);
            criminalRecord.setEndorseBy(endorserID);
            mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/CriminalRecord/put_request" + owner + ".json"), criminalRecord);
        } else {
            //$$$ValidateCriminalRecord transaction
            System.out.println("Validate criminal Record: " + criminalRecord.getCriminalRecordId());
            criminalRecord.setRecordNumber(recordNumber);
            criminalRecord.setRecordDetail(recordDetail);
            String state = Constants.STATUS_INVALIDATE;
            criminalRecord.setEndorseStatus(state);
            criminalRecord.setEndorseBy(endorserID);
            mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/CriminalRecord/put_request" + owner + ".json"), criminalRecord);
        }

        //validate criminal record of visa applicant
        System.out.println("Criminal Record:  " + criminalRecord.getOwner() + ": " + decision);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/SPFViewList.xhtml?faces-redirect=true");

    }

    public void showCriminalRecordPanel() {
        if (decision.equals("Rejected")) {
            showPanel = true;
        } else {
            showPanel = false;
        }

    }

    public CriminalRecord getCriminalRecord() {
        return criminalRecord;
    }

    public void setCriminalRecord(CriminalRecord criminalRecord) {
        this.criminalRecord = criminalRecord;
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

    public String getRecordDetail() {
        return recordDetail;
    }

    public void setRecordDetail(String recordDetail) {
        this.recordDetail = recordDetail;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

}
