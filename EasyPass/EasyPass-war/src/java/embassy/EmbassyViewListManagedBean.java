/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embassy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objects.VisaApplication;
import objects.VisaStatus;

/**
 *
 * @author Jingyuan
 */
@Named(value = "embassyViewListManagedBean")
@RequestScoped
public class EmbassyViewListManagedBean {

    /**
     * Creates a new instance of EmbassyViewListManagedBean
     */
    public EmbassyViewListManagedBean() {
    }

    public ArrayList<VisaApplication> getPendingVisaApplications() throws IOException {
        //---Retrieve list of pending visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<VisaApplication> visaApplications = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/VisaApplication/get_response.json"), new TypeReference<List<VisaApplication>>() {
        });

        ArrayList<VisaApplication> filteredVisaApplications = new ArrayList<>();
        for (int i = 0; i < visaApplications.size(); i++) {
            String visaStatusID = visaApplications.get(i).getVisaStatus();

            //get visa status by visa status id
            VisaStatus visaStatus = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/VisaStatus/post_request_Jingyuan.json"), VisaStatus.class);

            if (visaStatus.getState().equals("PENDING")) {
                filteredVisaApplications.add(visaApplications.get(i));
            }
        }

        return filteredVisaApplications;
    }

    public ArrayList<VisaApplication> getApprovedVisaApplications() throws IOException {
        //---Retrieve list of approved visa applications
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<VisaApplication> visaApplications = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/VisaApplication/get_response.json"), new TypeReference<List<VisaApplication>>() {
        });

        ArrayList<VisaApplication> filteredVisaApplications = new ArrayList<>();
        for (int i = 0; i < visaApplications.size(); i++) {
            String visaStatusID = visaApplications.get(i).getVisaStatus();

            //get visa status by visa status id
            VisaStatus visaStatus = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/VisaStatus/post_request_Jingyuan.json"), VisaStatus.class);

            if (visaStatus.getState().equals("APPROVED")) {
                filteredVisaApplications.add(visaApplications.get(i));
            }
        }
        return filteredVisaApplications;

    }

    public ArrayList<VisaApplication> getRejectedVisaApplications() throws IOException {
        //---Retrieve list of rejected visa applications
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<VisaApplication> visaApplications = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/VisaApplication/get_response.json"), new TypeReference<List<VisaApplication>>() {
        });

        ArrayList<VisaApplication> filteredVisaApplications = new ArrayList<>();
        for (int i = 0; i < visaApplications.size(); i++) {
            String visaStatusID = visaApplications.get(i).getVisaStatus();

            //get visa status by visa status id
            VisaStatus visaStatus = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/VisaStatus/post_request_Jingyuan.json"), VisaStatus.class);

            if (visaStatus.getState().equals("DENIED")) {
                filteredVisaApplications.add(visaApplications.get(i));
            }
        }
        return filteredVisaApplications;

    }
    //redirect to view & validate basic information of individual visa applicant

    public void viewVisaApplication(VisaApplication visaApplication) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("visaApplication", visaApplication);
        ec.redirect(ec.getRequestContextPath() + "/web/embassy/embassyDoEndorsement.xhtml?faces-redirect=true");
    }

}
