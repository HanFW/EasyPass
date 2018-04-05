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
@Named(value = "themePreferences")
@SessionScoped
public class themePreferences implements Serializable {
    
    private String theme = "blue-grey";

    /**
     * Creates a new instance of themePreferences
     */
    public themePreferences() {
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
