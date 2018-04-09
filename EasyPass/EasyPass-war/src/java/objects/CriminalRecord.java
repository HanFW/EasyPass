/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.UUID;

/**
 *
 * @author Jingyuan
 */
public class CriminalRecord {

    private String criminalRecordId;
    private String recordDetails;
    private String endorsementState;
    private String owner;

    public CriminalRecord(String recordDetails, String state, String Owner) {
        String id = UUID.randomUUID().toString();

        this.criminalRecordId = id;
        this.recordDetails = recordDetails;
        this.owner = Owner;
        this.endorsementState = state;

    }
    

    public String getCriminalRecordId() {
        return criminalRecordId;
    }

    public void setCriminalRecordId(String criminalRecordId) {
        this.criminalRecordId = criminalRecordId;
    }

    public String getRecordDetails() {
        return recordDetails;
    }

    public void setRecordDetails(String recordDetails) {
        this.recordDetails = recordDetails;
    }

    public String getEndorsementState() {
        return endorsementState;
    }

    public void setEndorsementState(String endorsementState) {
        this.endorsementState = endorsementState;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
