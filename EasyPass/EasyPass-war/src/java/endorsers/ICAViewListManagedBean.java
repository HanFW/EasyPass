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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objects.BasicInfo;

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
    public ICAViewListManagedBean() {
    }

    public ArrayList<BasicInfo> getPendingBasicInfos() throws IOException {
        //---Retrieve list of pending visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<BasicInfo> basicInfos = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BasicInfo/get_response.json"), new TypeReference<List<BasicInfo>>() {
        });

        ArrayList<BasicInfo> filteredBasicInfos = new ArrayList<>();
        for (int i = 0; i < basicInfos.size(); i++) {
            if (basicInfos.get(i).getEndorsementState().equals("PENDING")) {
                filteredBasicInfos.add(basicInfos.get(i));
            }
        }

        return filteredBasicInfos;
    }

    public ArrayList<BasicInfo> getValidatedBasicInfos() throws IOException {
        //---Retrieve list of validated visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<BasicInfo> basicInfos = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BasicInfo/get_response.json"), new TypeReference<List<BasicInfo>>() {
        });

        ArrayList<BasicInfo> filteredBasicInfos = new ArrayList<>();
        for (int i = 0; i < basicInfos.size(); i++) {
            if (basicInfos.get(i).getEndorsementState().equals("VERIFIED")) {
                filteredBasicInfos.add(basicInfos.get(i));
            }
        }

        return filteredBasicInfos;
    }

    public ArrayList<BasicInfo> getRejectedBasicInfos() throws IOException {
        //---Retrieve list of rejected visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<BasicInfo> basicInfos = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BasicInfo/get_response.json"), new TypeReference<List<BasicInfo>>() {
        });

        ArrayList<BasicInfo> filteredBasicInfos = new ArrayList<>();
        for (int i = 0; i < basicInfos.size(); i++) {
            if (basicInfos.get(i).getEndorsementState().equals("INVALIDATE")) {
                filteredBasicInfos.add(basicInfos.get(i));
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

}
