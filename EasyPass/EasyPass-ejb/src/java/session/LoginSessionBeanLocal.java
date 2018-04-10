/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.CitizenEntity;
import entity.EmbassyEntity;
import entity.EndorserEntity;
import exception.AuthenticationFailException;
import exception.NoSuchEntityException;
import javax.ejb.Local;

/**
 *
 * @author hanfengwei
 */
@Local
public interface LoginSessionBeanLocal {
    CitizenEntity citizenDoLogin(String accountNumber, String password) throws NoSuchEntityException, AuthenticationFailException;
    EmbassyEntity embassyDoLogin(String accountNumber, String password) throws NoSuchEntityException, AuthenticationFailException;
    EndorserEntity endorserDoLogin(String accountNumber, String password) throws NoSuchEntityException, AuthenticationFailException;
}
