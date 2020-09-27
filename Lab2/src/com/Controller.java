package com;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextField txt;
    public Label systemInfo;
    public ComboBox<String> select;
    private IdealGas idealGas = new IdealGas(2.28, 2, 300);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        select.getItems().setAll(
                "Давление",
                "Объем",
                "Температура"
        );
        systemInfo.setText(idealGas.getInfo());
    }

    public void add(ActionEvent inputMethodEvent) {
        int value = Integer.parseInt(txt.getText());
        String selectedValue = select.getSelectionModel().getSelectedItem();

        switch (selectedValue) {
            case "Давление":
                idealGas.add(new Pressure(value));
                break;
            case "Объем":
                idealGas.add(new Volume(value));
                break;
            case "Температура":
                idealGas.add(new Temperature(value));
                break;
        }

        systemInfo.setText(idealGas.getInfo());
    }

    public void reduce(ActionEvent inputMethodEvent) {
        int value = Integer.parseInt(txt.getText());
        String selectedValue = select.getSelectionModel().getSelectedItem();

        switch (selectedValue) {
            case "Давление":
                idealGas.reduce(new Pressure(value));
                break;
            case "Объем":
                idealGas.reduce(new Volume(value));
                break;
            case "Температура":
                idealGas.reduce(new Temperature(value));
                break;
        }

        systemInfo.setText(idealGas.getInfo());
    }
}
