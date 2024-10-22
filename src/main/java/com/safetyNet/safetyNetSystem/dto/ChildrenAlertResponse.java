package com.safetyNet.safetyNetSystem.dto;

import java.util.List;

public class ChildrenAlertResponse {
    private List<PersonInfo> children;
    private List<PersonInfo> adults;

    public ChildrenAlertResponse(List<PersonInfo> children, List<PersonInfo> adults) {
        this.children = children;
        this.adults = adults;
    }

    public List<PersonInfo> getChildren() {
        return children;
    }

    public void setChildren(List<PersonInfo> children) {
        this.children = children;
    }

    public List<PersonInfo> getAdults() {
        return adults;
    }

    public void setAdults(List<PersonInfo> adults) {
        this.adults = adults;
    }
}
