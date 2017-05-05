/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.entity;

import java.util.ArrayList;
import java.util.List;

public class BIOSConfiguration {

    private List<Group> groups;


    public List<Group> getGroups() {
        if (groups == null || groups.size() == 0) {
            groups = new ArrayList<Group>();
        }
        return groups;
    }


    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

}
