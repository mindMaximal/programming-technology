package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import sun.reflect.generics.tree.Tree;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    TextField buildTextField;

    @FXML
    TextField addTextField;

    @FXML
    Button addButton;

    @FXML
    TreeView<Integer> mainTree;

    @FXML
    TextField findTextFiled;

    @FXML
    Label infoLabel;

    TreeItem<Integer> root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        root = new TreeItem<>(10);
        TreeItem<Integer> child11 = new TreeItem<>(5);
        TreeItem<Integer> child12 = new TreeItem<>(20);

        // привязываем узлы к корневому узлу
        child11.getChildren().add(new TreeItem<>(3));
        child11.getChildren().add(new TreeItem<>(7));
        child11.setExpanded(true);

        child12.getChildren().add(new TreeItem<>(15));
        child12.getChildren().add(new TreeItem<>(25));
        child12.setExpanded(true);

        root.getChildren().add(child11);
        root.getChildren().add(child12);

        // передаем корневой узел в компоненту
        mainTree.setRoot(root);

        // раскрываем узел
        root.setExpanded(true);

        mainTree.getSelectionModel().selectedItemProperty().addListener((changed, oldNode, newNode) -> {

            String text = "Данные:\r\n\r\n";

            text += getNodeInfo(root, newNode);

            infoLabel.setText(text);
        });

        findTextFiled.textProperty().addListener((changed, oldNode, newNode) -> {
            try {
                String text = "Данные:\r\n\r\n";

                Integer value = Integer.parseInt(changed.getValue());
                Node sourceNode;

                TreeItem<Integer> node = find(value, root);

                if (node != null) {

                    text += getNodeInfo(root, node);
                } else {
                    text += "Такой элмент не найден";
                }

                infoLabel.setText(text);

            } catch(NumberFormatException e) {
                System.out.printf("Ошибка при вводе данных - %s%n", e);
            }
        });
    }

    public void buildButtonListener() {
        try {
            String str = buildTextField.getText();

            String[] text = str.split(" ");

            root = new TreeItem<>(Integer.parseInt(text[0]));

            for (int i = 1; i < text.length; i++) {
                add(root, new TreeItem<>(Integer.parseInt(text[i])));
            }

            mainTree.setRoot(root);

            // раскрываем узел
            root.setExpanded(true);
        } catch(NumberFormatException e) {
            System.out.printf("Ошибка при вводе данных - %s%n", e);
        }

    }

    public void addButtonListener() {
        int value = Integer.parseInt(addTextField.getText());

        boolean result = add(root, new TreeItem<Integer>(value));

        if (!result) {

            String text = "Данные:\r\n\r\nТакой элемент уже существует";

            infoLabel.setText(text);
        }

        addTextField.clear();
    }

    private String getNodeInfo(TreeItem<Integer> root, TreeItem<Integer> node) {

        String text = "";

        text += String.format("Значение: %d\r\n", node.getValue());

        text += String.format("Путь до корневого элемента: %s\r\n", getWay(node));

        text += String.format("Элементы этого уровня: %s\r\n", getElementsSameLevel(root, node));

        text += String.format("Самый глубокий потомок: %s\r\n",  getMinimalChildren(node).getValue());

        return text;
    }

    public TreeItem<Integer> find(Integer value, TreeItem<Integer> rootNode) {

        TreeItem<Integer> node = null;

        if (rootNode.getValue().equals(value)) {
            node = rootNode;
        } else if (rootNode.getChildren() != null) {
            ObservableList<TreeItem<Integer>> children = rootNode.getChildren();

            for (TreeItem<Integer> childrenNode : children) {
                node = find(value, childrenNode);

                if (node != null) {
                    return node;
                }

            }

        }

        return node;
    }

    public String getWay(TreeItem<Integer> node) {
        // получаем доступ к родительскому узлу
        TreeItem<Integer> parentNode = node.getParent();
        // выводим его значение

        StringBuilder way = new StringBuilder(node.getValue().toString());

        while(parentNode != null) {

            way.append(String.format(" - %s", parentNode.getValue().toString()));

            node = parentNode;

            parentNode = node.getParent();
        }

        return way.toString();
    }

    public TreeItem<Integer> getMinimalChildren(TreeItem<Integer> node) {

        int level = 0;

        ArrayList<ArrayList<TreeItem<Integer>>> levels = new ArrayList();

        dfsFill(levels, node, level);

        ArrayList<TreeItem<Integer>> list = levels.get(levels.size() - 1);

        TreeItem<Integer> min = list.get(0);

        for (TreeItem<Integer> el : list) {
            if (el.getValue() < min.getValue())
                min = el;
        }

        return min;
    }

    private void dfsFill(ArrayList<ArrayList<TreeItem<Integer>>> list, TreeItem<Integer> node, int level) {
        ArrayList<TreeItem<Integer>> sublist;

        if (list.size() > level) {
            sublist = list.get(level);
            sublist.add(node);
        } else {
            sublist = new ArrayList<>();
            sublist.add(node);

            list.add(sublist);
        }

        level++;

        for (TreeItem<Integer> n : node.getChildren()) {
            if (n.getChildren() != null) {
                dfsFill(list, n, level);
            }
        }

    }

    public String getElementsSameLevel(TreeItem<Integer> root, TreeItem<Integer> node) {

        StringBuilder text = new StringBuilder();

        ArrayList<ArrayList<TreeItem<Integer>>> levels = new ArrayList();

        int level = 0;

        dfsFill(levels, root, level);

        ArrayList<TreeItem<Integer>> list = null;

        for (ArrayList<TreeItem<Integer>> el : levels) {
            if (el.contains(node))
                list = el;
        }

        if (list != null) {
            for (TreeItem<Integer> el : list) {
                text.append(el.getValue()).append(" ");
            }
        }

        return text.toString();

    }

    public boolean add(TreeItem<Integer> root, TreeItem<Integer> node) {

        if (find(node.getValue(), root) != null)
            return false;

        TreeItem<Integer> parent = findParent(root, node);

        parent.getChildren().add(node);
        parent.setExpanded(true);

        return true;
    }

    private TreeItem<Integer> findParent(TreeItem<Integer> root, TreeItem<Integer> node) {

        TreeItem<Integer> n = null;
        TreeItem<Integer> children = null;

        if (root.getChildren().size() == 2) {

            if (node.getValue() < root.getValue()) {
                n = findParent(root.getChildren().get(0), node);
            } else {
                n = findParent(root.getChildren().get(1), node);
            }

        } else if (root.getChildren().size() == 1) {

            if (node.getValue() < root.getValue()) {
                //Влево
                if (root.getValue() < root.getChildren().get(0).getValue()) {
                    return root;
                } else {
                    n = findParent(root.getChildren().get(0), node);
                }
            } else {
                //Вправо
                if (root.getValue() < root.getChildren().get(0).getValue()) {
                    n = findParent(root.getChildren().get(0), node);
                } else {
                    return root;
                }
            }


        } else {
            n = root;
        }

        return n;
    }
}
