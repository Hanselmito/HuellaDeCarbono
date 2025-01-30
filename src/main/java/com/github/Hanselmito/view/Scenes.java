package com.github.Hanselmito.view;

public enum Scenes {
    ROOT("view/layout.fxml"),
    LOGIN("view/Login.fxml"),
    REGISTER("view/Register.fxml"),
    MANAGERHUELLA("view/ManageHuellaController.fxml"),
    SETTINGUSER("view/SettingUsuario.fxml"),
    MENU("view/Menu.fxml");

    private String url;
    Scenes(String url){
        this.url=url;
    }
    public String getURL(){
        return url;
    }
}
