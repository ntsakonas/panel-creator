package com.sv1djg.panelcreator.renderers;

import com.sv1djg.panelcreator.panelitems.UnitType;

import java.io.IOException;

interface FileRenderer {
    OutputRenderer.Operations operations();

    void prepare(PageSize pageSize, PageOrientation pageOrientation, UnitType inputUnit) throws IOException;

    void save() throws IOException;
}
