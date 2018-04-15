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
import objects.Accommodation;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "hotelDoEndorsementManagedBean")
@ViewScoped
public class HotelDoEndorsementManagedBean implements Serializable {

    private Accommodation accommodation;
    private String decision;

    /**
     * Creates a new instance of hotelDoEndorsementManagedBean
     */
    public HotelDoEndorsementManagedBean() {
    }

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        accommodation = (Accommodation) ec.getFlash().get("accommodation");
        System.out.println("View accommodation: " + accommodation.getAccommodationId());
    }

    //validate or reject document
    public void validateDocument() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        //$$$ValidateAccommodation transaction
        System.out.println("Accommodation " + accommodation.getAccommodationId() + ": " + decision);

        EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
        String id = endorser.getEndorserId();
        accommodation.setEndorseBy(id);

        if (decision.equals("Validated")) {
            accommodation.setEndorseStatus(Constants.STATUS_VERIFIED);
        } else {
            accommodation.setEndorseStatus(Constants.STATUS_INVALIDATE);
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Accommodation/post_request" + accommodation.getOwner() + ".json"), accommodation);
        
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/hotelViewList.xhtml?faces-redirect=true");
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

}
