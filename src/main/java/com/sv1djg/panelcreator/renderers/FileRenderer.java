package com.sv1djg.panelcreator.renderers;

import java.io.IOException;

interface FileRenderer {
    OutputRenderer.Operations operations();

    void prepare() throws IOException;

    void save() throws IOException;
}
