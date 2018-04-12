/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citizen;

import com.fasterxml.jackson.databind.ObjectMapper;
import entity.CitizenEntity;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import objects.Passport;
import objects.Visa;
import objects.VisaApplication;

/**
 *
 * @author Jingyuan
 */
@Named(value = "citizenViewEVisaManagedBean")
@ViewScoped
public class CitizenViewEVisaManagedBean implements Serializable {

    private boolean showVisa=false;
    private boolean showText=true;

    /**
     * Creates a new instance of CitizenViewEVisaManagedBean
     */
    public CitizenViewEVisaManagedBean() {
    }

    @PostConstruct
    public void init() {
        try {
            System.out.println("init citizen view eVisa!!!!!!");
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            CitizenEntity citizen = (CitizenEntity) ec.getSessionMap().get("citizen");
            String id = citizen.getId();//get citizenID (passport number)
            
            //get passport by passport number
            ObjectMapper mapper = new ObjectMapper();
            Passport passport = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Passport/get_response.json"), Passport.class);
            
            //get visaId (visa is linked to passport)
            String visaId = passport.getVisaId();
            
            if(visaId!=null){
                showVisa=true;
                showText=false;
            }
        } catch (IOException ex) {
            Logger.getLogger(CitizenViewEVisaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Visa getVisa() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        CitizenEntity citizen = (CitizenEntity) ec.getSessionMap().get("citizen");
        String id = citizen.getId();//get citizenID (passport number)

        //get passport by passport number
        ObjectMapper mapper = new ObjectMapper();
        Passport passport = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Passport/get_response.json"), Passport.class);

        //get visaId (visa is linked to passport)
        String visaId = passport.getVisaId();

        //get visa by visa ID
        Visa visa = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Visa/get_visa_byVisaId.json"), Visa.class);
        return visa;
    }

    public boolean isShowVisa() {
        return showVisa;
    }

    public void setShowVisa(boolean showVisa) {
        this.showVisa = showVisa;
    }

    public boolean isShowText() {
        return showText;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
    }
    
    

}
