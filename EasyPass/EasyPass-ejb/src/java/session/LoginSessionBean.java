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
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author hanfengwei
 */
@Stateless
public class LoginSessionBean implements LoginSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(LoginSessionBean.class.getName()); // used to output info
    private static ConsoleHandler handler = null; // set logger's output to console

    public LoginSessionBean() {
        handler = new ConsoleHandler();
        handler.setLevel(Level.FINEST);
        LOGGER.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
    }

    //check citizen password & login
    @Override
    public CitizenEntity citizenDoLogin(String accountNumber, String password) throws NoSuchEntityException, AuthenticationFailException {
        if (accountNumber == null || password == null) {
            LOGGER.log(Level.SEVERE, "Null field: accountNumber / password");
            return null;
        }

        //query citizen by accountNumber
        CitizenEntity citizen = null;
        Query query = em.createQuery("select c from CitizenEntity c where c.accountNumber = :accountNumber")
                .setParameter("accountNumber", accountNumber);
        try {
            citizen = (CitizenEntity) query.getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) { // citizen not found
                throw new NoSuchEntityException("Citizen " + accountNumber + " not found");
            }
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        //authenticate citizen
        if (hashPassword(password, citizen.getPasswordSalt()).equals(citizen.getPassword())) { //password is correct
            return citizen;
        } else { //password is incorrect
            throw new AuthenticationFailException("Password incorrect");
        }
    }

    @Override
    public EmbassyEntity embassyDoLogin(String accountNumber, String password) throws NoSuchEntityException, AuthenticationFailException {
        if (accountNumber == null || password == null) {
            LOGGER.log(Level.SEVERE, "Null field: accountNumber / password");
            return null;
        }

        //query embassy by accountNumber
        EmbassyEntity embassy = null;
        Query query = em.createQuery("select e from EmbassyEntity e where e.accountNumber = :accountNumber")
                .setParameter("accountNumber", accountNumber);
        try {
            embassy = (EmbassyEntity) query.getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) { //embassy not found
                throw new NoSuchEntityException("Embassy " + accountNumber + " not found");
            }
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        //authenticate embassy
        if (hashPassword(password, embassy.getPasswordSalt()).equals(embassy.getPassword())) { //password is correct
            return embassy;
        } else { //password is incorrect
            throw new AuthenticationFailException("Password incorrect");
        }
    }

    @Override
    public EndorserEntity endorserDoLogin(String accountNumber, String password) throws NoSuchEntityException, AuthenticationFailException {
        if (accountNumber == null || password == null) {
            LOGGER.log(Level.SEVERE, "Null field: accountNumber / password");
            return null;
        }

        //query endorser by accountNumber
        EndorserEntity endorser = null;
        Query query = em.createQuery("select e from EndorserEntity e where e.accountNumber = :accountNumber")
                .setParameter("accountNumber", accountNumber);
        try {
            endorser = (EndorserEntity) query.getSingleResult();
        } catch (Exception e) {
            if (e instanceof NoResultException) { //endorser not found
                throw new NoSuchEntityException("Endorser " + accountNumber + " not found");
            }
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        //authenticate endorser
        if (hashPassword(password, endorser.getPasswordSalt()).equals(endorser.getPassword())) { //password is correct
            return endorser;
        } else { //password is incorrect
            throw new AuthenticationFailException("Password incorrect");
        }
    }

    private String hashPassword(String password, String passwordSalt) {
        try {
            String stringToHash = password + passwordSalt;
            MessageDigest md = MessageDigest.getInstance("MD5");
            String hashtext = new BigInteger(1, md.digest(stringToHash.getBytes())).toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        return null;
    }
}
