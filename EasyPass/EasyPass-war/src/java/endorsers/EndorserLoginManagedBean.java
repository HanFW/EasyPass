/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endorsers;

import entity.EndorserEntity;
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
@Named(value = "endorserLoginManagedBean")
@RequestScoped
public class EndorserLoginManagedBean {

    @EJB
    private LoginSessionBeanLocal loginSessionBeanLocal;

    private String accountNumber;
    private String password;

    /**
     * Creates a new instance of EndorserLoginManagedBean
     */
    public EndorserLoginManagedBean() {
    }

    public void endorserDoLogin(ActionEvent event) throws IOException {
        try {
            EndorserEntity endorser = loginSessionBeanLocal.endorserDoLogin(accountNumber, password);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.getSessionMap().put("endorser", endorser);
            ec.redirect(ec.getRequestContextPath() + "/web/endorser/endorserViewList");
        } catch (NoSuchEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account does not exist.", " "));
        } catch (AuthenticationFailException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect password.", " "));
        }
    }

    public void endorserDoLogout(ActionEvent event) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getSessionMap().remove("endorser");
        ec.redirect(ec.getRequestContextPath() + "/web/endorser/endorserLogin.xhtml?faces-redirect=true");
    }

    public String getEndorserName() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        EndorserEntity endorser = (EndorserEntity) ec.getSessionMap().get("endorser");
        if (endorser == null) {
            return "Endorser";
        } else {
            return endorser.getEndorserName();
        }
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
