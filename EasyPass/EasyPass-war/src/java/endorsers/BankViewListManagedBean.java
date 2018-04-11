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
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objects.BankStatement;

/**
 *
 * @author hanfengwei
 */
@Named(value = "bankViewListManagedBean")
@RequestScoped
public class BankViewListManagedBean {

    /**
     * Creates a new instance of bankViewListManagedBean
     */
    public BankViewListManagedBean() {
    }

    //retrieve list of pending bank statements submitted
    public ArrayList<BankStatement> getPendingBankStatements() throws IOException {
        //---Retrieve list of pending bank statements
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<BankStatement> bankStatements = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BankStatement/get_response.json"), new TypeReference<List<BankStatement>>() {
        });

        ArrayList<BankStatement> filteredBankStatements = new ArrayList<>();
        for (int i = 0; i < bankStatements.size(); i++) {
            if (bankStatements.get(i).getEndorsementState().equals("PENDING")) {
                filteredBankStatements.add(bankStatements.get(i));
            }
        }

        return filteredBankStatements;
    }
    
        //retrieve list of validted bank statements 
    public ArrayList<BankStatement> getValidatedBankStatements() throws IOException {
        //---Retrieve list of pending bank statements
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<BankStatement> bankStatements = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BankStatement/get_response.json"), new TypeReference<List<BankStatement>>() {
        });

        ArrayList<BankStatement> filteredBankStatements = new ArrayList<>();
        for (int i = 0; i < bankStatements.size(); i++) {
            if (bankStatements.get(i).getEndorsementState().equals("VERIFIED")) {
                filteredBankStatements.add(bankStatements.get(i));
            }
        }

        return filteredBankStatements;
    }
    
        //retrieve list of rejected bank statements submitted
    public ArrayList<BankStatement> getRejectedBankStatements() throws IOException {
        //---Retrieve list of pending bank statements
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<BankStatement> bankStatements = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BankStatement/get_response.json"), new TypeReference<List<BankStatement>>() {
        });

        ArrayList<BankStatement> filteredBankStatements = new ArrayList<>();
        for (int i = 0; i < bankStatements.size(); i++) {
            if (bankStatements.get(i).getEndorsementState().equals("INVALIDATE")) {
                filteredBankStatements.add(bankStatements.get(i));
            }
        }

        return filteredBankStatements;
    }

    //redirect to view & validate bank statment of individual visa applicant
    public void viewBankStatement(BankStatement bankStatement) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getFlash().put("bankStatement", bankStatement);
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/bankDoEndorsement.xhtml?faces-redirect=true");
    }
}
