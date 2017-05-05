/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman;

public class ComponentIds {
    public enum BIOS {
        _11And12G("159");
        String enumValue;


        BIOS(String value) {
            enumValue = value;
        }


        @Override
        public String toString() {
            return enumValue;
        }
    }

    public enum iDRAC {
        _11GMono("20137"), _11GModu("15051"), _12G("25227");
        String enumValue;


        iDRAC(String value) {
            enumValue = value;
        }


        @Override
        public String toString() {
            return enumValue;
        }
    }

    public enum LC {
        _11G("18980"), _12G("28897");
        String enumValue;


        LC(String value) {
            enumValue = value;
        }


        @Override
        public String toString() {
            return enumValue;
        }
    }

    public enum DRVPK {
        _11And12G("18981");
        String enumValue;


        DRVPK(String value) {
            enumValue = value;
        }


        @Override
        public String toString() {
            return enumValue;
        }
    }

    public enum DIAGS {
        _11G("196"), _12G("25806");
        String enumValue;


        DIAGS(String value) {
            enumValue = value;
        }


        @Override
        public String toString() {
            return enumValue;
        }
    }

    public enum PSU {
        _11And12G("17505");
        String enumValue;


        PSU(String value) {
            enumValue = value;
        }


        @Override
        public String toString() {
            return enumValue;
        }
    }

}
