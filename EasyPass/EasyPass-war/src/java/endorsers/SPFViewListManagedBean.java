/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endorsers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.io.File;
import java.io.IOException;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objects.CriminalRecord;
import util.Constants;

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
    private ArrayList<CriminalRecord> pendingCriminalRecords;
    private ArrayList<CriminalRecord> verifiedCriminalRecords;
    private ArrayList<CriminalRecord> invalideCriminalRecords;

    public SPFViewListManagedBean() {
    }

    //retrieve list visa applicants
    public ArrayList<CriminalRecord> getPendingCriminalRecords() throws IOException {
        //---Retrieve list of pending criminal records of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            pendingCriminalRecords = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/CriminalRecord/get_response.json"), new TypeReference<List<CriminalRecord>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> criminalRecordResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.CriminalRecord")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(criminalRecordResponse.getBody().toString());
                pendingCriminalRecords = mapper.readValue(criminalRecordResponse.getBody().toString(), new TypeReference<List<CriminalRecord>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<CriminalRecord> filteredCriminalRecords = new ArrayList<>();
        for (int i = 0; i < pendingCriminalRecords.size(); i++) {
            if (pendingCriminalRecords.get(i).getEndorseStatus().equals("PENDING")) {
                filteredCriminalRecords.add(pendingCriminalRecords.get(i));
            }
        }

        return filteredCriminalRecords;
    }

    public ArrayList<CriminalRecord> getVaidatedCriminalRecords() throws IOException {
        //---Retrieve list of validated criminal records of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            verifiedCriminalRecords = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/CriminalRecord/get_response.json"), new TypeReference<List<CriminalRecord>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> criminalRecordResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.CriminalRecord")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(criminalRecordResponse.getBody().toString());
                verifiedCriminalRecords = mapper.readValue(criminalRecordResponse.getBody().toString(), new TypeReference<List<CriminalRecord>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<CriminalRecord> filteredCriminalRecords = new ArrayList<>();
        for (int i = 0; i < verifiedCriminalRecords.size(); i++) {
            if (verifiedCriminalRecords.get(i).getEndorseStatus().equals("VERIFIED")) {
                filteredCriminalRecords.add(verifiedCriminalRecords.get(i));
            }
        }

        return filteredCriminalRecords;
    }

    public ArrayList<CriminalRecord> getRejectedCriminalRecords() throws IOException {
        //---Retrieve list of rejected criminal records of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            invalideCriminalRecords = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/CriminalRecord/get_response.json"), new TypeReference<List<CriminalRecord>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> criminalRecordResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.CriminalRecord")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(criminalRecordResponse.getBody().toString());
                invalideCriminalRecords = mapper.readValue(criminalRecordResponse.getBody().toString(), new TypeReference<List<CriminalRecord>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<CriminalRecord> filteredCriminalRecords = new ArrayList<>();
        for (int i = 0; i < invalideCriminalRecords.size(); i++) {
            if (invalideCriminalRecords.get(i).getEndorseStatus().equals("INVALIDATE")) {
                filteredCriminalRecords.add(invalideCriminalRecords.get(i));
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

    //redirect to  view validated criminal record of individual visa applicant
    public void viewValidatedCriminalRecords(CriminalRecord criminalRecord) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("criminalRecord", criminalRecord);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/SPFViewValidatedCriminalRecords.xhtml?faces-redirect=true");
    }

    //redirect to  view rejected criminal record of individual visa applicant
    public void viewRejectedCriminalRecords(CriminalRecord criminalRecord) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("criminalRecord", criminalRecord);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/SPFViewRejectedCriminalRecords.xhtml?faces-redirect=true");
    }
}
