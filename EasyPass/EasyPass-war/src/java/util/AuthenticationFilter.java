/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.EndorserEntity;
import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hanfengwei
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/web/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class AuthenticationFilter implements Filter {

    private FilterConfig filterConfig = null;

    public AuthenticationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession httpSession = req.getSession(true);
        String requestServletPath = req.getServletPath();
        
        if (httpSession.getAttribute("endorser") == null) { //endorser not authenticated: redirect to login page
            req.getRequestDispatcher("/web/endorser/endorserLogin.xhtml").forward(request, response);
        } else if (httpSession.getAttribute("citizen") == null) { //citizen not authenticated: redirect to login page
            req.getRequestDispatcher("/web/citizen/citizenLogin.xhtml").forward(request, response);
        } else if (httpSession.getAttribute("embassy") == null) { //embassy not authenticated: redirect to login page
            req.getRequestDispatcher("/web/embassy/embassyLogin.xhtml").forward(request, response);
        } else {
            EndorserEntity endorser = (EndorserEntity) httpSession.getAttribute("endorser");
            if (requestServletPath.equals("/web/endorser/endorserLogin")) { //redirect endorser based on role
                switch (endorser.getEndorserRole()) {
                    case Constants.ENDORSER_ROLE_BANK:
                        req.getRequestDispatcher("/web/endorser/bankViewList.xhtml").forward(request, response);
                        break;
                    case Constants.ENDORSER_ROLE_TRANSPORTATION:
                        req.getRequestDispatcher("/web/endorser/TransportationProviderViewList.xhtml").forward(request, response);
                        break;
                    case Constants.ENDORSER_ROLE_ACCOMMODATION:
                        req.getRequestDispatcher("/web/endorser/hotelViewList.xhtml").forward(request, response);
                        break;
                    case Constants.ENDORSER_ROLE_INSURANCE:
                        req.getRequestDispatcher("/web/endorser/insuranceCompanyViewList.xhtml").forward(request, response);
                        break;
                    case Constants.ENDORSER_ROLE_ICA:
                        req.getRequestDispatcher("/web/endorser/ICAViewList.xhtml").forward(request, response);
                        break;
                    case Constants.ENDORSER_ROLE_SPF:
                        req.getRequestDispatcher("/web/endorser/SPFViewList.xhtml").forward(request, response);
                        break;
                    case Constants.ENDORSER_ROLE_LOCALCONTACT:
                        req.getRequestDispatcher("/web/endorser/LocalContactDoEndorsement.xhtml").forward(request, response);
                        break;
                    default:
                        req.getRequestDispatcher("/web/endorser/endorserLogin.xhtml").forward(request, response);
                }
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {
    }
}
