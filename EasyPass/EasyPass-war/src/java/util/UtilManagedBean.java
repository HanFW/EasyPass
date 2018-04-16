/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author hanfengwei
 */
@Named(value = "utilManagedBean")
@SessionScoped
public class UtilManagedBean implements Serializable {
    
    private String theme = "dark-green";

    /**
     * Creates a new instance of utilManagedBean
     */
    public UtilManagedBean() {
    }
    
    public String formatId(String originalId){
        return originalId.substring(originalId.indexOf("#")+1);
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
