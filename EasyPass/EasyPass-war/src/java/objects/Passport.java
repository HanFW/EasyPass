/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author Jingyuan
 */
public class Passport {
     private String $class;
     private String passportNumber;
     private String[] visaApplications;
     private String owner;

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String[] getVisaApplications() {
        return visaApplications;
    }

    public void setVisaApplications(String[] visaApplications) {
        this.visaApplications = visaApplications;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    
    
     
     
}
