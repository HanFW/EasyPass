/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author hanfengwei
 */
public class Constants {

    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_VERIFIED = "VERIFIED";
    public static final String STATUS_INVALIDATE = "INVALIDATE";

    public static final String APPLICATION_STATUS_PENDING = "PENDING";
    public static final String APPLICATION_STATUS_APPROVED = "APPROVED";
    public static final String APPLICATION_STATUS_DENIED = "DENIED";

    public static final String ENDORSER_ROLE_BANK = "BANK";
    public static final String ENDORSER_ROLE_TRANSPORTATION = "TRANSPORTATION";
    public static final String ENDORSER_ROLE_ACCOMMODATION = "ACCOMMODATION";
    public static final String ENDORSER_ROLE_INSURANCE = "INSURANCE";
    public static final String ENDORSER_ROLE_ICA = "ICA";
    public static final String ENDORSER_ROLE_SPF = "SPF";
    public static final String ENDORSER_ROLE_LOCALCONTACT = "LOCALCONTACT";

    //asset
    public static final String ASSET_ACCOMMODATION = "org.acme.easypass.Accommodation";
    public static final String ASSET_BANKSTATEMENT = "org.acme.easypass.BankStatement";
    public static final String ASSET_BASICINFO = "org.acme.easypass.BasicInfo";
    public static final String ASSET_CRIMINALRECORD = "org.acme.easypass.CriminalRecord";
    public static final String ASSET_SPF = "org.acme.easypass.SPF";
    public static final String ASSET_INSURANCE = "org.acme.easypass.Insurance";
    public static final String ASSET_INSURANCECOMPANY = "org.acme.easypass.InsuranceCompany";
    public static final String ASSET_LOCALCONTACT = "org.acme.easypass.LocalContact";
    public static final String ASSET_LOCALCONTACTPERSON = "org.acme.easypass.LocalContactPerson";
    public static final String ASSET_PASSPORT = "org.acme.easypass.Passport";
    public static final String ASSET_TRANSPORTATION = "org.acme.easypass.TransportationReference";
    public static final String ASSET_TRANSPORTATIONPROVIDER = "org.acme.easypass.TransportationProvider";
    public static final String ASSET_VISA = "org.acme.easypass.Visa";
    public static final String ASSET_VISAAPPLICATION = "org.acme.easypass.VisaApplication";
    public static final String ASSET_VISASTATUS = "org.acme.easypass.VisaStatus";

    //participant
    public static final String PARTICIPANT_BANK = "org.acme.easypass.Bank";
    public static final String PARTICIPANT_CITIZEN = "org.acme.easypass.Citizen";
    public static final String PARTICIPANT_EMBASSY = "org.acme.easypass.Embassy";
    public static final String PARTICIPANT_HOTEL = "org.acme.easypass.Hotel";
    public static final String PARTICIPANT_ICA = "org.acme.easypass.ICA";
    public static final String PARTICIPANT_INSURANCE = "org.acme.easypass.InsuranceCompany";
    public static final String PARTICIPANT_LOCALCONTACT = "org.acme.easypass.LocalContactPerson";
    public static final String PARTICIPANT_SFP = "org.acme.easypass.SPF";
    public static final String PARTICIPANT_TRANSPORTATION = "org.acme.easypass.TransportationProvider";

    //transaction
    public static final String TRANSACTION_VALIDATEBANKSTATEMENT = "org.acme.easypass.ValidateBankStatement";
    public static final String TRANSACTION_UPDATEVISASTATUS = "org.acme.easypass.UpdateVisaStatus";
    public static final String TRANSACTION_VALIDATEACCOMMODATION = "org.acme.easypass.ValidateAccommodationReference";
    public static final String TRANSACTION_VALIDATEBASICINFO = "org.acme.easypass.ValidateCitizenBasicInfo";
    public static final String TRANSACTION_VALIDATECRIMINALRECORD = "org.acme.easypass.ValidateCriminalRecord";
    public static final String TRANSACTION_VALIDATEINSURANCE = "org.acme.easypass.ValidateInsuranceReference";
    public static final String TRANSACTION_VALIDATELOCALCONTACT = "org.acme.easypass.ValidateLocalContactInformation";
    public static final String TRANSACTION_VALIDATETRANSPORTATION = "org.acme.easypass.ValidateTransportationBookingReference";

    public static final boolean localTesting = true;
}
