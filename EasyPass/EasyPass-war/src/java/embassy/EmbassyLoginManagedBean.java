/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embassy;

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
@Named(value = "embassyLoginManagedBean")
@RequestScoped
public class EmbassyLoginManagedBean {
    @EJB
    private LoginSessionBeanLocal loginSessionBeanLocal;
    
    private String accountNumber;
    private String password;

    /**
     * Creates a new instance of EmbassyLoginManagedBean
     */
    public EmbassyLoginManagedBean() {
    }
    
    public void embassyDoLogin(ActionEvent event) throws IOException {
        try {
            Long embassyId = loginSessionBeanLocal.embassyDoLogin(accountNumber, password);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.getSessionMap().put("embassyId", embassyId);
            System.out.println("Embassy " + embassyId + " logged in.");
        } catch (NoSuchEntityException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Account does not exist.", " "));
        } catch (AuthenticationFailException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Incorrect password.", " "));
        }
    }
    
    public void embassyDoLogout(ActionEvent event) throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.getSessionMap().remove("embassyId");
        ec.redirect(ec.getRequestContextPath() + "/web/embassy/embassyLogin.xhtml?faces-redirect=true");
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
