/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.UUID;
import util.Constants;

/**
 *
 * @author Jingyuan
 */
public class CriminalRecord {

    private String $class;
    private String criminalRecordId;
    private String recordNumber;
    private String recordDetail;
    private String endorsementState;
    private String owner;
    private String endorseBy;

//    public CriminalRecord(String recordDetail, String state, String Owner) {
//        String id = UUID.randomUUID().toString();
//
//        this.criminalRecordId = id;
//        this.recordDetail = recordDetail;
//        this.owner = Owner;
//        this.endorsementState = state;
//
//    }
    public CriminalRecord() {
        this.$class = "org.acme.easypass.CriminalRecord";
        this.criminalRecordId = "org.acme.easypass.CriminalRecord#" + UUID.randomUUID().toString();
        this.endorsementState = Constants.STATUS_PENDING;
        this.owner = "CitizenId(TBC)";
    }
    

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }

    public String getCriminalRecordId() {
        return criminalRecordId;
    }

    public void setCriminalRecordId(String criminalRecordId) {
        this.criminalRecordId = criminalRecordId;
    }

    public String getRecordDetail() {
        return recordDetail;
    }

    public void setRecordDetail(String recordDetail) {
        this.recordDetail = recordDetail;
    }

    public String getEndorsementState() {
        return endorsementState;
    }

    public void setEndorsementState(String endorsementState) {
        this.endorsementState = endorsementState;
    }

    public String getOwner() {
        return owner.substring(owner.indexOf("#") + 1);
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getEndorseBy() {
        return endorseBy;
    }

    public void setEndorseBy(String endorseBy) {
        this.endorseBy = endorseBy;
    }
    
    

}
