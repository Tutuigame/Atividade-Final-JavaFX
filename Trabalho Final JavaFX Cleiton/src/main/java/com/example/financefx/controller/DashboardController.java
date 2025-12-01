
package com.example.financefx.controller;

import com.example.financefx.App;
import com.example.financefx.dao.ExpenseDao;
import com.example.financefx.util.DateUtils;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.util.Map;

public class DashboardController {
    @FXML private Label usernameLabel;
    @FXML private PieChart pieChart;
    @FXML private DatePicker monthPicker;

    private final ExpenseDao expenseDao = new ExpenseDao();

    @FXML
    public void initialize() {
        pieChart.setAnimated(false);
        if (LoginController.CURRENT_USER != null) {
            usernameLabel.setText(LoginController.CURRENT_USER.getUsername());
        }
        monthPicker.setValue(LocalDate.now());
        monthPicker.valueProperty().addListener((obs, oldV, newV) -> refreshChart());
        refreshChart();
    }

    private void refreshChart() {
        if (LoginController.CURRENT_USER == null) return;
        String monthIso = DateUtils.monthIso(monthPicker.getValue());
        Map<String, Double> data = expenseDao.sumByCategory(LoginController.CURRENT_USER.getId(), monthIso);

        javafx.collections.ObservableList<PieChart.Data> items =
                javafx.collections.FXCollections.observableArrayList();
        data.forEach((cat, total) -> items.add(new PieChart.Data(cat, total)));
        pieChart.setData(items);
    }

    @FXML
    public void goExpenses() {
        App.setRoot("view/expenses");
    }

    @FXML
    public void logout() {
        LoginController.CURRENT_USER = null;
        App.setRoot("view/login");
    }
}
