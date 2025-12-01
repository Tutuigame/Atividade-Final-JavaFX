
package com.example.financefx.controller;

import com.example.financefx.App;
import com.example.financefx.dao.ExpenseDao;
import com.example.financefx.model.Expense;
import com.example.financefx.util.DateUtils;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.List;

public class ExpenseController {
    @FXML private TableView<Expense> table;
    @FXML private TableColumn<Expense, String> colDesc;
    @FXML private TableColumn<Expense, String> colCat;
    @FXML private TableColumn<Expense, Number> colAmount;
    @FXML private TableColumn<Expense, String> colDate;

    @FXML private TextField descField;
    @FXML private ComboBox<String> catCombo;
    @FXML private TextField amountField;
    @FXML private DatePicker datePicker;
    @FXML private Label formMessage;

    private final ExpenseDao dao = new ExpenseDao();
    private final ObservableList<Expense> items = FXCollections.observableArrayList();
    private Expense editing = null;

    @FXML
    public void initialize() {
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCat.setCellValueFactory(new PropertyValueFactory<>("category"));
        colAmount.setCellValueFactory(cell -> new SimpleDoubleProperty(cell.getValue().getAmount()));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.setItems(items);
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) loadForm(newSel);
        });

        catCombo.setItems(FXCollections.observableArrayList("Alimentação","Transporte","Moradia","Saúde","Lazer","Outros"));
        datePicker.setValue(LocalDate.now());

        loadTable();
    }

    private void loadTable() {
        items.clear();
        if (LoginController.CURRENT_USER == null) return;
        List<Expense> list = dao.listByUser(LoginController.CURRENT_USER.getId());
        items.addAll(list);
    }

    private void loadForm(Expense e) {
        editing = e;
        descField.setText(e.getDescription());
        catCombo.setValue(e.getCategory());
        amountField.setText(String.valueOf(e.getAmount()));
        datePicker.setValue(LocalDate.parse(e.getDate()));
        formMessage.setText("");
    }

    @FXML
    public void onNew() {
        editing = null;
        descField.clear();
        catCombo.getSelectionModel().clearSelection();
        amountField.clear();
        datePicker.setValue(LocalDate.now());
        table.getSelectionModel().clearSelection();
        formMessage.setText("");
    }

    @FXML
    public void onSave() {
        try {
            String desc = descField.getText();
            String cat = catCombo.getValue();
            double amount = Double.parseDouble(amountField.getText().replace(",", "."));
            String date = DateUtils.toIso(datePicker.getValue());

            if (desc == null || desc.isBlank() || cat == null) {
                formMessage.setText("Preencha descrição e categoria.");
                return;
            }

            int uid = LoginController.CURRENT_USER.getId();
            if (editing == null) {
                Expense e = new Expense(0, desc, cat, amount, date, uid);
                int id = dao.insert(e);
                e.setId(id);
                items.add(0, e);
            } else {
                editing.setDescription(desc);
                editing.setCategory(cat);
                editing.setAmount(amount);
                editing.setDate(date);
                dao.update(editing);
                table.refresh();
            }
            formMessage.setText("Salvo com sucesso.");
        } catch (NumberFormatException nfe) {
            formMessage.setText("Valor inválido.");
        } catch (Exception ex) {
            formMessage.setText("Erro ao salvar: " + ex.getMessage());
        }
    }

    @FXML
    public void onDelete() {
        Expense sel = table.getSelectionModel().getSelectedItem();
        if (sel == null) {
            formMessage.setText("Selecione um registro.");
            return;
        }
        dao.delete(sel.getId(), sel.getUserId());
        items.remove(sel);
        onNew();
        formMessage.setText("Removido.");
    }

    @FXML
    public void goDashboard() {
        App.setRoot("view/dashboard");
    }
}
