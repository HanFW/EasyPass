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
import objects.BasicInfo;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
@Named(value = "iCAViewListManagedBean")
@RequestScoped
public class ICAViewListManagedBean implements Serializable {

    /**
     * Creates a new instance of ICAViewListManagedBean
     */
    private ArrayList<BasicInfo> pendingBasicInfos;
    private ArrayList<BasicInfo> verifiedBasicInfos;
    private ArrayList<BasicInfo> invalideBasicInfos;

    public ICAViewListManagedBean() {
    }

    public ArrayList<BasicInfo> getPendingBasicInfos() throws IOException {
        //---Retrieve list of pending visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            pendingBasicInfos = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BasicInfo/get_response.json"), new TypeReference<List<BasicInfo>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> basicInfoResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.BasicInfo")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(basicInfoResponse.getBody().toString());
                pendingBasicInfos = mapper.readValue(basicInfoResponse.getBody().toString(), new TypeReference<List<BasicInfo>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<BasicInfo> filteredBasicInfos = new ArrayList<>();
        for (int i = 0; i < pendingBasicInfos.size(); i++) {
            if (pendingBasicInfos.get(i).getEndorseStatus().equals("PENDING")) {
                filteredBasicInfos.add(pendingBasicInfos.get(i));
            }
        }

        return filteredBasicInfos;
    }

    public ArrayList<BasicInfo> getValidatedBasicInfos() throws IOException {
        //---Retrieve list of validated visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            verifiedBasicInfos = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BasicInfo/get_response.json"), new TypeReference<List<BasicInfo>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> basicInfoResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.BasicInfo")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(basicInfoResponse.getBody().toString());
                verifiedBasicInfos = mapper.readValue(basicInfoResponse.getBody().toString(), new TypeReference<List<BasicInfo>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<BasicInfo> filteredBasicInfos = new ArrayList<>();
        for (int i = 0; i < verifiedBasicInfos.size(); i++) {
            if (verifiedBasicInfos.get(i).getEndorseStatus().equals("VERIFIED")) {
                filteredBasicInfos.add(verifiedBasicInfos.get(i));
            }
        }

        return filteredBasicInfos;
    }

    public ArrayList<BasicInfo> getRejectedBasicInfos() throws IOException {
        //---Retrieve list of rejected visa applicants
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            invalideBasicInfos = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BasicInfo/get_response.json"), new TypeReference<List<BasicInfo>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> basicInfoResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.BasicInfo")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(basicInfoResponse.getBody().toString());
                invalideBasicInfos = mapper.readValue(basicInfoResponse.getBody().toString(), new TypeReference<List<BasicInfo>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<BasicInfo> filteredBasicInfos = new ArrayList<>();
        for (int i = 0; i < invalideBasicInfos.size(); i++) {
            if (invalideBasicInfos.get(i).getEndorseStatus().equals("INVALIDATE")) {
                filteredBasicInfos.add(invalideBasicInfos.get(i));
            }
        }

        return filteredBasicInfos;
    }

    //redirect to view & validate basic information of individual visa applicant
    public void viewBasicInfo(BasicInfo basicInfo) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("basicInfo", basicInfo);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/ICADoEndorsement.xhtml?faces-redirect=true");
    }

    //redirect to view endorsed basic information of individual visa applicant
    public void viewEndorsedBasicInfo(BasicInfo basicInfo) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("basicInfo", basicInfo);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/ICAViewEndorsedDocuments.xhtml?faces-redirect=true");
    }
}
