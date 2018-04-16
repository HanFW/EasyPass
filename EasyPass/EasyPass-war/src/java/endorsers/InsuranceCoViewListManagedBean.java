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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objects.Insurance;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "insuranceCoViewListManagedBean")
@RequestScoped
public class InsuranceCoViewListManagedBean implements Serializable {

    /**
     * Creates a new instance of InsuranceCoViewListManagedBean
     */
    private ArrayList<Insurance> pendingInsurances;
    private ArrayList<Insurance> verifiedInsurances;
    private ArrayList<Insurance> invalideInsurances;

    public InsuranceCoViewListManagedBean() {
    }

    public ArrayList<Insurance> getPendingInsurances() throws IOException {
        //---Retrieve list of pending insurance contract of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            pendingInsurances = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Insurance/get_response.json"), new TypeReference<List<Insurance>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> insuranceResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Insurance")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(insuranceResponse.getBody().toString());
                pendingInsurances = mapper.readValue(insuranceResponse.getBody().toString(), new TypeReference<List<Insurance>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<Insurance> filteredInsurances = new ArrayList<>();
        for (int i = 0; i < pendingInsurances.size(); i++) {
            if (pendingInsurances.get(i).getEndorseStatus().equals("PENDING")) {
                filteredInsurances.add(pendingInsurances.get(i));
            }
        }

        return filteredInsurances;
    }

    public ArrayList<Insurance> getValidatedInsurances() throws IOException {
        //---Retrieve list of validated insurance contract of visa applicants
        ObjectMapper mapper = new ObjectMapper();

        if (Constants.localTesting) {
            verifiedInsurances = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Insurance/get_response.json"), new TypeReference<List<Insurance>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> insuranceResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Insurance")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(insuranceResponse.getBody().toString());
                verifiedInsurances = mapper.readValue(insuranceResponse.getBody().toString(), new TypeReference<List<Insurance>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<Insurance> filteredInsurances = new ArrayList<>();
        for (int i = 0; i < verifiedInsurances.size(); i++) {
            if (verifiedInsurances.get(i).getEndorseStatus().equals("VERIFIED")) {
                filteredInsurances.add(verifiedInsurances.get(i));
            }
        }

        return filteredInsurances;
    }

    public ArrayList<Insurance> getRejectedInsurances() throws IOException {
        //---Retrieve list of rejected insurance contract of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            invalideInsurances = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Insurance/get_response.json"), new TypeReference<List<Insurance>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> insuranceResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Insurance")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(insuranceResponse.getBody().toString());
                invalideInsurances = mapper.readValue(insuranceResponse.getBody().toString(), new TypeReference<List<Insurance>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<Insurance> filteredInsurances = new ArrayList<>();
        for (int i = 0; i < invalideInsurances.size(); i++) {
            if (invalideInsurances.get(i).getEndorseStatus().equals("INVALIDATE")) {
                filteredInsurances.add(invalideInsurances.get(i));
            }
        }

        return filteredInsurances;
    }

    //redirect to view & validate insurance contract of individual visa applicant
    public void viewInsurance(Insurance insurance) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("insurance", insurance);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/insuranceCompanyDoEndorsement.xhtml?faces-redirect=true");
    }

}
