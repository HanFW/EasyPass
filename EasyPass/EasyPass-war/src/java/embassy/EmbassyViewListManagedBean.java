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
import util.Constants;

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
        ArrayList<VisaApplication> visaApplications = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaApplication/get_response.json"), new TypeReference<List<VisaApplication>>() {
        });

        ArrayList<VisaApplication> filteredVisaApplications = new ArrayList<>();
        for (int i = 0; i < visaApplications.size(); i++) {
            String visaStatusID = visaApplications.get(i).getVisaStatus();

            //get visa status by visa status id
            VisaStatus visaStatus = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaStatus/post_request_Jingyuan.json"), VisaStatus.class);

            if (visaStatus.getStatusState().equals(Constants.APPLICATION_STATUS_PENDING)) {
                filteredVisaApplications.add(visaApplications.get(i));
            }
        }

        return filteredVisaApplications;
    }

    public ArrayList<VisaApplication> getApprovedVisaApplications() throws IOException {
        //---Retrieve list of approved visa applications
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<VisaApplication> visaApplications = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaApplication/get_response.json"), new TypeReference<List<VisaApplication>>() {
        });

        ArrayList<VisaApplication> filteredVisaApplications = new ArrayList<>();
        for (int i = 0; i < visaApplications.size(); i++) {
            String visaStatusID = visaApplications.get(i).getVisaStatus();

            //get visa status by visa status id
            VisaStatus visaStatus = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaStatus/post_request_Jingyuan.json"), VisaStatus.class);

            if (visaStatus.getStatusState().equals(Constants.APPLICATION_STATUS_APPROVED)) {
                filteredVisaApplications.add(visaApplications.get(i));
            }
        }
        return filteredVisaApplications;
    }

    public ArrayList<VisaApplication> getRejectedVisaApplications() throws IOException {
        //---Retrieve list of rejected visa applications
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<VisaApplication> visaApplications = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaApplication/get_response.json"), new TypeReference<List<VisaApplication>>() {
        });

        ArrayList<VisaApplication> filteredVisaApplications = new ArrayList<>();
        for (int i = 0; i < visaApplications.size(); i++) {
            String visaStatusID = visaApplications.get(i).getVisaStatus();

            //get visa status by visa status id
            VisaStatus visaStatus = mapper.readValue(new File("/Users/hanfengwei/Desktop/IS4302/project/data/Asset/VisaStatus/post_request_Jingyuan.json"), VisaStatus.class);

            if (visaStatus.getStatusState().equals(Constants.APPLICATION_STATUS_DENIED)) {
                filteredVisaApplications.add(visaApplications.get(i));
            }
        }
        return filteredVisaApplications;

    }
    
    //redirect to review basic information of individual visa applicant & issue visa
    public void reviewVisaApplication(VisaApplication visaApplication) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("visaApplication", visaApplication);
        ec.redirect(ec.getRequestContextPath() + "/web/embassy/embassyReviewVisaApplication.xhtml?faces-redirect=true");
    }
    
    //redirect to view visa application results
    public void viewVisaApplication(VisaApplication visaApplication) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("visaApplication", visaApplication);
        ec.redirect(ec.getRequestContextPath() + "/web/embassy/embassyViewVisaApplication.xhtml?faces-redirect=true");
    }

}
