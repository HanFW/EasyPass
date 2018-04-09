/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import exception.AuthenticationFailException;
import exception.NoSuchEntityException;
import javax.ejb.Local;

/**
 *
 * @author hanfengwei
 */
@Local
public interface LoginSessionBeanLocal {
    Long citizenDoLogin(String accountNumber, String password) throws NoSuchEntityException, AuthenticationFailException;
    Long embassyDoLogin(String accountNumber, String password) throws NoSuchEntityException, AuthenticationFailException;
    Long endorserDoLogin(String accountNumber, String password) throws NoSuchEntityException, AuthenticationFailException;
}
