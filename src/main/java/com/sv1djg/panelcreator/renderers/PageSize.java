package com.sv1djg.panelcreator.renderers;

public enum PageSize {
    A4(210f, 297f);

    public final float widthMM;
    public final float heightMM;

    PageSize(float widthMM, float heightMM) {
        this.widthMM = widthMM;
        this.heightMM = heightMM;
    }
}