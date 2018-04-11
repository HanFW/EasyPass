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
import objects.CriminalRecord;

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
    public ArrayList<CriminalRecord> getPendingCriminalRecords() throws IOException {
        //---Retrieve list of pending criminal records of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<CriminalRecord> criminalRecords = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/CriminalRecord/get_response.json"), new TypeReference<List<CriminalRecord>>() {
        });

        ArrayList<CriminalRecord> filteredCriminalRecords = new ArrayList<>();
        for (int i = 0; i < criminalRecords.size(); i++) {
            if (criminalRecords.get(i).getEndorsementState().equals("PENDING")) {
                filteredCriminalRecords.add(criminalRecords.get(i));
            }
        }

        return filteredCriminalRecords;
    }

    public ArrayList<CriminalRecord> getVaidatedCriminalRecords() throws IOException {
        //---Retrieve list of validated criminal records of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<CriminalRecord> criminalRecords = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/CriminalRecord/get_response.json"), new TypeReference<List<CriminalRecord>>() {
        });

        ArrayList<CriminalRecord> filteredCriminalRecords = new ArrayList<>();
        for (int i = 0; i < criminalRecords.size(); i++) {
            if (criminalRecords.get(i).getEndorsementState().equals("VERIFIED")) {
                filteredCriminalRecords.add(criminalRecords.get(i));
            }
        }

        return filteredCriminalRecords;
    }

    public ArrayList<CriminalRecord> getRejectedCriminalRecords() throws IOException {
        //---Retrieve list of rejected criminal records of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<CriminalRecord> criminalRecords = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/CriminalRecord/get_response.json"), new TypeReference<List<CriminalRecord>>() {
        });

        ArrayList<CriminalRecord> filteredCriminalRecords = new ArrayList<>();
        for (int i = 0; i < criminalRecords.size(); i++) {
            if (criminalRecords.get(i).getEndorsementState().equals("INVALIDATE")) {
                filteredCriminalRecords.add(criminalRecords.get(i));
            }
        }

        return filteredCriminalRecords;
    }

    //redirect to  validate criminal record of individual visa applicant
    public void redirectPage(CriminalRecord criminalRecord) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("criminalRecord", criminalRecord);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/SPFDoEndorsement.xhtml?faces-redirect=true");
    }

}
