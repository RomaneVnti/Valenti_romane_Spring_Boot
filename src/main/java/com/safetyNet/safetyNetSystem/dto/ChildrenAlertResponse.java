package com.safetyNet.safetyNetSystem.dto;

import java.util.List;

public class ChildrenAlertResponse {
    private List<ChildInfo> children; // Changer le type ici
    private List<PersonInfo> adults;

    public ChildrenAlertResponse(List<ChildInfo> children, List<PersonInfo> adults) {
        this.children = children;
        this.adults = adults;
    }

    public List<ChildInfo> getChildren() { // Changer le type ici
        return children;
    }

    public void setChildren(List<ChildInfo> children) { // Changer le type ici
        this.children = children;
    }

    public List<PersonInfo> getAdults() {
        return adults;
    }

    public void setAdults(List<PersonInfo> adults) {
        this.adults = adults;
    }
}
