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
import objects.Insurance;

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
    public InsuranceCoViewListManagedBean() {
    }

    public ArrayList<Insurance> getPendingInsurances() throws IOException {
        //---Retrieve list of pending insurance contract of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Insurance> insurances = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Insurance/get_response.json"), new TypeReference<List<Insurance>>() {
        });

        ArrayList<Insurance> filteredInsurances = new ArrayList<>();
        for (int i = 0; i < insurances.size(); i++) {
            if (insurances.get(i).getEndorsementState().equals("PENDING")) {
                filteredInsurances.add(insurances.get(i));
            }
        }

        return filteredInsurances;
    }

    public ArrayList<Insurance> getValidatedInsurances() throws IOException {
        //---Retrieve list of validated insurance contract of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Insurance> insurances = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Insurance/get_response.json"), new TypeReference<List<Insurance>>() {
        });

        ArrayList<Insurance> filteredInsurances = new ArrayList<>();
        for (int i = 0; i < insurances.size(); i++) {
            if (insurances.get(i).getEndorsementState().equals("VERIFIED")) {
                filteredInsurances.add(insurances.get(i));
            }
        }

        return filteredInsurances;
    }

    public ArrayList<Insurance> getRejectedInsurances() throws IOException {
        //---Retrieve list of rejected insurance contract of visa applicants
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Insurance> insurances = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/Insurance/get_response.json"), new TypeReference<List<Insurance>>() {
        });

        ArrayList<Insurance> filteredInsurances = new ArrayList<>();
        for (int i = 0; i < insurances.size(); i++) {
            if (insurances.get(i).getEndorsementState().equals("INVALIDATE")) {
                filteredInsurances.add(insurances.get(i));
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
