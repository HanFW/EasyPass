/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author hanfengwei
 */
@Entity
public class EmbassyEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String embassyId;
    private String accountNumber;
    private String password;
    private String passwordSalt;
    private String countryName;

    public String getEmbassyId() {
        return embassyId;
    }

    public void setEmbassyId(String embassyId) {
        this.embassyId = embassyId;
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

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
