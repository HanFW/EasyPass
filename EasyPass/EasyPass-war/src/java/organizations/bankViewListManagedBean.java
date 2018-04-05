/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package organizations;

import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;

/**
 *
 * @author hanfengwei
 */
@Named(value = "bankViewListManagedBean")
@RequestScoped
public class bankViewListManagedBean {

    /**
     * Creates a new instance of bankViewListManagedBean
     */
    public bankViewListManagedBean() {
    }
    
    public ArrayList<Object> getApplicantList(ActionEvent event) {
        return null;
    }
}
