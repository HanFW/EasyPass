/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package citizen;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import entity.CitizenEntity;
import exception.AuthenticationFailException;
import exception.NoSuchEntityException;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import session.LoginSessionBeanLocal;

/**
 *
 * @author hanfengwei
 */
@Named(value = "citizenLoginManagedBean")
@RequestScoped
public class CitizenLoginManagedBean {

    @EJB
    private LoginSessionBeanLocal loginSessionBeanLocal;

    private String accountNumber;
    private String password;

    /**
     * Creates a new instance of CitizenLoginManagedBean
     */
    public CitizenLoginManagedBean() {
    }

    public void http() {
        try {
            HttpResponse<JsonNode> jsonResponse2 = Unirest.post("http://localhost:3000/api/org.acme.easypass.Accommodation")
                    .header("accept", "application/json")
                    .field("$class", "org.acme.easypass.Accommodation")
                    .field("accommodationId", "001")
                    .field("carrierName", "abc")
                    .field("referenceNumber", "abc")
                    .field("accommodationReferenceImageURL", "abc")
                    .field("endorseStatus", "PENDING")
                    .field("owner", "resource:org.acme.easypass.Citizen#5804")
                    .asJson();
            System.out.println(jsonResponse2.getBody());

            HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:3000/api/org.acme.easypass.Accommodation")
                    .header("accept", "application/json")
                    .asJson();
            System.out.println(jsonResponse.getBody());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void citizenDoLogin(ActionEvent event) throws IOException {
        try {
            CitizenEntity citizen = loginSessionBeanLocal.citizenDoLogin(accountNumber, password);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.getSessionMap().put("citizen", citizen);
            ec.redirect(ec.getRequestContextPath() + "/web/citizen/citizenPortalMainPage.xhtml?faces-redirect=true");
        } catch (NoSuchEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account does not exist.", " "));
        } catch (AuthenticationFailException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect password.", " "));
        }
    }

    public void citizenDoLogout(ActionEvent event) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getSessionMap().remove("citizen");
        ec.redirect(ec.getRequestContextPath() + "/web/citizen/citizenLogin.xhtml?faces-redirect=true");
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
