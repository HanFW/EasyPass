/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citizen;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import entity.CitizenEntity;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import objects.Passport;
import objects.Visa;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "citizenViewEVisaManagedBean")
@ViewScoped
public class CitizenViewEVisaManagedBean implements Serializable {

    private boolean showVisa = false;
    private boolean showText = true;
    private Passport passport;
    private Visa visa;

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
            String citizenId = citizen.getCitizenId();//get citizenID (passport number)

            if (Constants.localTesting) {
                //get passport by passport id
                ObjectMapper mapper = new ObjectMapper();
                passport = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Passport/get_response.json"), Passport.class);

                //get visaId (visa is linked to passport)
                String visaId = passport.getVisas().get(0).getVisaId();

                if (visaId != null) {
                    showVisa = true;
                    showText = false;
                }

            } else {
                //get passport by passport id
                ObjectMapper mapper = new ObjectMapper();

                try {
                    HttpResponse<JsonNode> passportResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Passport/" + citizenId)
                            .header("accept", "application/json")
                            .asJson();
                    passport = mapper.readValue(passportResponse.getBody().getObject().toString(), Passport.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //get visaId (visa is linked to passport)
                String visaId = passport.getVisas().get(0).getVisaId();

                if (visaId != null) {
                    showVisa = true;
                    showText = false;
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(CitizenViewEVisaManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Visa getVisa() throws IOException {

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        CitizenEntity citizen = (CitizenEntity) ec.getSessionMap().get("citizen");
        String citizenId = citizen.getCitizenId();//get citizenID (passport number)

        //get passport by passport id
        ObjectMapper mapper = new ObjectMapper();

        if (Constants.localTesting) {
            passport = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Passport/get_response.json"), Passport.class);

            //get visaId (visa is linked to passport)
            String visaId = passport.getVisas().get(0).getVisaId();

            //get visa by visa ID
            Visa visa = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Visa/get_visa_byVisaId.json"), Visa.class);
            return visa;
        } else {

            try {
                HttpResponse<JsonNode> passportResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Passport/" + citizenId)
                        .header("accept", "application/json")
                        .asJson();
                passport = mapper.readValue(passportResponse.getBody().getObject().toString(), Passport.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //get visaId (visa is linked to passport)
            String visaId = passport.getVisas().get(0).getVisaId();

            //get visa by visa ID
            try {
                HttpResponse<JsonNode> visaResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Visa/" + visaId)
                        .header("accept", "application/json")
                        .asJson();
                visa = mapper.readValue(visaResponse.getBody().getObject().toString(), Visa.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return visa;
        }

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
