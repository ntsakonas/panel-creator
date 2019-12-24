package com.sv1djg.panelcreator.renderers;

class FileRendererFactory {
    public static FileRenderer getRenderer() {
        return new PDFRenderer();
    }
}
