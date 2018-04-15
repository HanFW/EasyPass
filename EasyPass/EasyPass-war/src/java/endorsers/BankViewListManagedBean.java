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
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import objects.BankStatement;
import util.Constants;

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
    private ArrayList<BankStatement> pendingBankStatements;
    private ArrayList<BankStatement> verifiedBankStatements;
    private ArrayList<BankStatement> invalideBankStatements;

    public BankViewListManagedBean() {
    }

    //retrieve list of pending bank statements submitted
    public ArrayList<BankStatement> getPendingBankStatements() throws IOException {
        //---Retrieve list of pending bank statements
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            pendingBankStatements = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BankStatement/get_response.json"), new TypeReference<List<BankStatement>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> bankStatementResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.BankStatement")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(bankStatementResponse.getBody().toString());
                pendingBankStatements = mapper.readValue(bankStatementResponse.getBody().toString(), new TypeReference<List<BankStatement>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<BankStatement> filteredBankStatements = new ArrayList<>();
        for (int i = 0; i < pendingBankStatements.size(); i++) {
            if (pendingBankStatements.get(i).getEndorseStatus().equals("PENDING")) {
                filteredBankStatements.add(pendingBankStatements.get(i));
            }
        }

        return filteredBankStatements;
    }

    //retrieve list of validted bank statements 
    public ArrayList<BankStatement> getValidatedBankStatements() throws IOException {
        //---Retrieve list of pending bank statements
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            verifiedBankStatements = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BankStatement/get_response.json"), new TypeReference<List<BankStatement>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> bankStatementResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.BankStatement")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(bankStatementResponse.getBody().toString());
                verifiedBankStatements = mapper.readValue(bankStatementResponse.getBody().toString(), new TypeReference<List<BankStatement>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<BankStatement> filteredBankStatements = new ArrayList<>();
        for (int i = 0; i < verifiedBankStatements.size(); i++) {
            if (verifiedBankStatements.get(i).getEndorseStatus().equals("VERIFIED")) {
                filteredBankStatements.add(verifiedBankStatements.get(i));
            }
        }

        return filteredBankStatements;
    }

    //retrieve list of rejected bank statements submitted
    public ArrayList<BankStatement> getRejectedBankStatements() throws IOException {
        //---Retrieve list of pending bank statements
        ObjectMapper mapper = new ObjectMapper();
        if (Constants.localTesting) {
            invalideBankStatements = mapper.readValue(new File("/Users/Jingyuan/Desktop/IS4302/project/data/Asset/BankStatement/get_response.json"), new TypeReference<List<BankStatement>>() {
            });
        } else {
            try {
                HttpResponse<JsonNode> bankStatementResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.BankStatement")
                        .header("accept", "application/json")
                        .asJson();
                System.out.println(bankStatementResponse.getBody().toString());
                invalideBankStatements = mapper.readValue(bankStatementResponse.getBody().toString(), new TypeReference<List<BankStatement>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<BankStatement> filteredBankStatements = new ArrayList<>();
        for (int i = 0; i < invalideBankStatements.size(); i++) {
            if (invalideBankStatements.get(i).getEndorseStatus().equals("INVALIDATE")) {
                filteredBankStatements.add(invalideBankStatements.get(i));
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
