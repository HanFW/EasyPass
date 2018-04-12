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
import objects.BasicInfo;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "iCADoEndorsementManagedBean")
@ViewScoped
public class ICADoEndorsementManagedBean implements Serializable{
    private BasicInfo basicInfo;
    private String decision; 
    /**
     * Creates a new instance of ICADoEndorsementManagedBean
     */
    public ICADoEndorsementManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        basicInfo = (BasicInfo) ec.getFlash().get("basicInfo");
        System.out.println("View basic Info: " + basicInfo.getBasicInfoId());
    }
    
    //validate or reject document
    public void validateDocument () throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        //$$$ValidateBasicInfo transaction
        System.out.println("BasicInfo " + basicInfo.getBasicInfoId() + ": " + decision);
        
        EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
        String id = endorser.getId();
        basicInfo.setEndorseBy(id);
        
        
        if (decision.equals("Validated")) {
            basicInfo.setEndorsementState(Constants.STATUS_VERIFIED);
        } else {
            basicInfo.setEndorsementState(Constants.STATUS_INVALIDATE);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BasicInfo/post_request" + basicInfo.getOwner() + ".json"), basicInfo);
        
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/ICAViewList.xhtml?faces-redirect=true");
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo= basicInfo;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
    
}
