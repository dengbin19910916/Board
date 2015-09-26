package com.wrox.controllers;

import java.util.Arrays;

/**
 * Kendo UI 的主题
 *
 * Created by Dengbin on 2015/9/25.
 */
public enum Theme {

    DEFAULT("default"),
    BLUE_OPAL("blueopal"),
    BOOTSTRAP("bootstarp"),
    SILVER("silver"),

    UNIFORM("uniform"),
    METRO("metro"),
    BLACK("black"),
    METRO_BLACK("metroblack"),
    HIGH_CONTRAST("highcontrast"),
    MOONLIGHT("moonlight"),
    FLAT("flat"),
    MATERIAL("material"),
    MATERIAL_BLACK("materialblack"),
    FIORI("fiori"),
    OFFICE365("office365"),
    NOVA("nova");

    private String name;

    Theme(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
