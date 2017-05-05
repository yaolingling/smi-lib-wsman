/**
 * Copyright © 2017 DELL Inc. or its subsidiaries.  All Rights Reserved.
 */
package com.dell.isg.smi.wsman.command.entity;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class PCIDeviceView implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public enum SlotType {
        OTHER("0001", "SLOT_TYPE_OTHER"), UNKNOWN("0002", "SLOT_TYPE_UNKNOWN"), ISA("0003", "SLOT_TYPE_ISA"), MCA("0004", "SLOT_TYPE_MCA"), EISA("0005", "SLOT_TYPE_EISA"), PCI("0006", "SLOT_TYPE_PCI"), PC_CARD_PCMCIA("0007", "SLOT_TYPE_PCMCIA"), VL_VESA("0008", "SLOT_TYPE_VESA"), PROPRIETARY("0009", "SLOT_TYPE_PROPRIETARY"), PROCESSOR_CARD_SLOT("000A", "SLOT_TYPE_PROCESSOR_CARD_SLOT"), MEMORY_CARD_SLOT("000B", "SLOT_TYPE_PROPRIETARY_MEMORY_CARD_SLOT"), RISE_CARD_SLOT("000C", "SLOT_TYPE_IO_RISER_CARD_SLOT"), NUMBUS("000D", "SLOT_TYPE_NUBUS"), PCI_66("000E", "SLOT_TYPE_PCI_66"), AGP("000F", "SLOT_TYPE_AGP"), AGP_2X("0010", "SLOT_TYPE_AGP_2X"), AGP_4X("0011", "SLOT_TYPE_AGP_4X"), PCI_X("0012", "SLOT_TYPE_PCI_X"), AGP_8X("0013", "SLOT_TYPE_AGP_8X"), PC_C20("00A0", "SLOT_TYPE_PC_98_C20"), PC_C24("00A1", "SLOT_TYPE_PC_98_C24"), PC_E("00A2", "SLOT_TYPE_PC_98_E"), PC_LOCALBUS("00A3", "SLOT_TYPE_PC_98_LOCAL_BUS"), PC_CARD("00A4", "SLOT_TYPE_PC_98_CARD"), PC_EXPRESS("00A5", "SLOT_TYPE_PCI_EXPRESS"), PC_EXPRESS_X1("00A6", "SLOT_TYPE_PCI_EXPRESS_X1"), PC_EXPRESS_X2("00A7", "SLOT_TYPE_PCI_EXPRESS_X2"), PC_EXPRESS_X4("00A8", "SLOT_TYPE_PCI_EXPRESS_X4"), PC_EXPRESS_X8("00A9", "SLOT_TYPE_PCI_EXPRESS_X8"), PC_EXPRESS_X16("00AA", "SLOT_TYPE_PCI_EXPRESS_X16"), PC_EXPRESS_GEN2("00AB", "SLOT_TYPE_PCI_EXPRESS_GEN2"), PC_EXPRESS_GEN2_X1("00AC", "SLOT_TYPE_PCI_EXPRESS_GEN2_X1"), PC_EXPRESS_GEN2_X2("00AD", "SLOT_TYPE_PCI_EXPRESS_GEN2_X2"), PC_EXPRESS_GEN2_X4("00AE", "SLOT_TYPE_PCI_EXPRESS_GEN2_X4"), PC_EXPRESS_GEN2_X8("00AF", "SLOT_TYPE_PCI_EXPRESS_GEN2_X8"), PC_EXPRESS_GEN2_X16("00B0", "SLOT_TYPE_PCI_EXPRESS_GEN2_X16"), PC_EXPRESS_GEN3("00B1", "SLOT_TYPE_PCI_EXPRESS_GEN3"), PC_EXPRESS_GEN3_X1("00B2", "SLOT_TYPE_PCI_EXPRESS_GEN3_X1"), PC_EXPRESS_GEN3_X2("00B3", "SLOT_TYPE_PCI_EXPRESS_GEN3_X2"), PC_EXPRESS_GEN3_X4("00B4", "SLOT_TYPE_PCI_EXPRESS_GEN3_X4"), PC_EXPRESS_GEN3_X8("00B5", "SLOT_TYPE_PCI_EXPRESS_GEN3_X8"), PC_EXPRESS_GEN3_X16("00B6", "SLOT_TYPE_PCI_EXPRESS_GEN3_X16");

        private String value = null;
        private String slotType = null;


        private SlotType(String value, String slotType) {
            this.value = value;
            this.slotType = slotType;
        }


        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }


        /**
         * @param value the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }


        /**
         * @return the slotType
         */
        public String getSlotType() {
            return slotType;
        }


        /**
         * @param slotType the slotType to set
         */
        public void setSlotType(String slotType) {
            this.slotType = slotType;
        }


        public static SlotType getSlotType(String value) {
            SlotType[] slotTypeArr = SlotType.values();
            for (SlotType slotType : slotTypeArr) {
                if (StringUtils.equalsIgnoreCase(slotType.getValue(), value)) {
                    return slotType;
                }
            }
            return null;
        }
    }

    public enum SlotWidth {
        OTHER("0001", "SLOT_WIDTH_NA"), UNKNOWN("0002", "SLOT_WIDTH_UNKNOWN"), EIGHT_BIT("0003", "SLOT_WIDTH_8_BITS"), SIXTEEN_BIT("0004", "SLOT_WIDTH_16_BITS"), THIRTYTWO_BIT("0005", "SLOT_WIDTH_32_BITS"), SIXTYFOUR_BIT("0006", "SLOT_WIDTH_64_BITS"), ONETWENTYEIGHT_BIT("0007", "SLOT_WIDTH_128_BITS"), ONE_X("0008", "SLOT_WIDTH_1X"), TWO_X("0009", "SLOT_WIDTH_2X"), FOUR_X("000A", "SLOT_WIDTH_4X"), EIGHT_X("000B", "SLOT_WIDTH_8X"), TWELVE_X("000C", "SLOT_WIDTH_12X"), SIXTEEN_X("000D", "SLOT_WIDTH_16X"), THIRTYTWO_X("000E", "SLOT_WIDTH_32X");

        private String value = null;
        private String width = null;


        private SlotWidth(String value, String width) {
            this.value = value;
            this.width = width;
        }


        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }


        /**
         * @param value the value to set
         */
        public void setValue(String value) {
            this.value = value;
        }


        /**
         * @return the width
         */
        public String getWidth() {
            return width;
        }


        /**
         * @param width the width to set
         */
        public void setWidth(String width) {
            this.width = width;
        }


        public static SlotWidth getSlotWidth(String value) {
            SlotWidth[] slotWidthArr = SlotWidth.values();
            for (SlotWidth slotWidth : slotWidthArr) {
                if (StringUtils.equalsIgnoreCase(slotWidth.getValue(), value)) {
                    return slotWidth;
                }
            }
            return null;
        }
    }
}
