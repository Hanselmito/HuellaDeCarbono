package com.github.Hanselmito.view;

public enum Scenes {
    ROOT("view/layout.fxml"),
    LOGIN("view/Login.fxml"),
    SECUNDARY("view/secondary.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }
}
