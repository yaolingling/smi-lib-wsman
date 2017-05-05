/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.entity;

import java.util.HashMap;
import java.util.Map;

public class Group {

    private Map<String, String> map;

    private String name;


    public Map<String, String> getMap() {
        if (null == map) {
            map = new HashMap<String, String>();
        }
        return map;
    }


    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

}