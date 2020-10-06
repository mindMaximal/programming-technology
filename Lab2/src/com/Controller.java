package com;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public TextField txtInputText;
    public Label systemInfo;
    public ComboBox<String> comboBox;
    private IdealGas idealGas = new IdealGas(2.28, 2, 300);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBox.getItems().setAll(
                "Давление",
                "Объем",
                "Температура"
        );
        systemInfo.setText(idealGas.getInfo());

        try {
            // создаем поток для чтения из файла
            FileInputStream fis = new FileInputStream("settings.xml");
            // создаем xml декодер из файла
            XMLDecoder decoder = new XMLDecoder(fis);

            Settings settings = (Settings) decoder.readObject();

            // а теперь заполняем форму
            comboBox.getSelectionModel().select(settings.comboBoxSelectedIndex);
            txtInputText.setText(Integer.toString(settings.txtInputText));
            systemInfo.setText(settings.systemInfo);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public void add(ActionEvent inputMethodEvent) {
        try {
            int value;
            String selectedValue;

            value = Integer.parseInt(txtInputText.getText());
            selectedValue = comboBox.getSelectionModel().getSelectedItem();

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
                default:
                    systemInfo.setText("Выберите параметр");
                    break;
            }

            systemInfo.setText(idealGas.getInfo());

        } catch (NumberFormatException e) {
            systemInfo.setText("Введено некорректное значение");
            return;
        } catch (NullPointerException e) {
            systemInfo.setText("Выберете параметр");
            return;
        }
    }

    public void reduce(ActionEvent inputMethodEvent) {
        try {
            int value;
            String selectedValue;

            value = Integer.parseInt(txtInputText.getText());
            selectedValue = comboBox.getSelectionModel().getSelectedItem();

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
                default:
                    systemInfo.setText("Выберите параметр");
                    break;
            }

            systemInfo.setText(idealGas.getInfo());

        } catch (NumberFormatException e) {
            systemInfo.setText("Введено некорректное значение");
            return;
        } catch (NullPointerException e) {
            systemInfo.setText("Выберете параметр");
            return;
        }

    }

    public void onStageClose() {
        // создали экземпляр класс
        Settings settings = new Settings();
        // зафиксировали значения полей
        settings.comboBoxSelectedIndex = comboBox.getSelectionModel().getSelectedIndex();
        settings.txtInputText = Integer.parseInt(txtInputText.getText());
        settings.systemInfo = systemInfo.getText();

        try {
            // создаем поток для записи в файл settings.xml
            FileOutputStream fos = new FileOutputStream("settings.xml");
            // создали энкодер, которые будет писать в поток
            XMLEncoder encoder = new XMLEncoder(fos);

            // записали настройки
            encoder.writeObject(settings);

            // закрыли энкодер и поток для записи
            // если не закрыть, то файл будет пустой
            encoder.close();
            fos.close();
        } catch (Exception e) {
            // на случай ошибки
            e.printStackTrace();
        }
    }
}
