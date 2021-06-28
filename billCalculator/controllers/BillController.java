package billCalculator.controllers;

import billCalculator.Bill;
import billCalculator.db.Const;
import billCalculator.db.DatabaseHandler;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.sql.SQLException;

/*
 * Класс-контроллер для представления работы приложения по пм02
 *
 * @Author Машина А.А.
 */
public class BillController {
    ObservableList<Bill> storeData = Const.STORE_DATA;

    @FXML
    private TableView<Bill> base_table;
    @FXML
    private TableColumn<Bill, Integer> tableNumber;
    @FXML
    private TableColumn<Bill, String> tableName;
    @FXML
    private TableColumn<Bill, String> tableCategory;
    @FXML
    private TableColumn<Bill, Double> tableSum;
    @FXML
    private TableColumn<Bill, String> tableCurrency;
    @FXML
    private TableColumn<Bill, Double> tablePercent;
    @FXML
    private TableColumn<Bill, String> tableDate;
    @FXML
    private TextField addNumber;
    @FXML
    private TextField addName;
    @FXML
    private TextField addSum;
    @FXML
    private TextField addPercent;
    @FXML
    private ComboBox<?> addCategory;
    @FXML
    private Label add_error;
    @FXML
    private ComboBox<String> addCurrency;
    @FXML
    private TextField editNumber;
    @FXML
    private Label edit_article_error;
    @FXML
    private Pane edit_panel;
    @FXML
    private TextField editName;
    @FXML
    private TextField editSum;
    @FXML
    private TextField editPercent;
    @FXML
    private ComboBox<String> editCategory;
    @FXML
    private Button save_button;
    @FXML
    private Label save_error;
    @FXML
    private Label save_success;
    @FXML
    private TextField delNumber;
    @FXML
    private Label del_success;
    @FXML
    private Label del_error;
    @FXML
    private ComboBox<String> analise;
    @FXML
    private ComboBox<String> analiseAlot;
    @FXML
    private BarChart<String, Double> barChar;

    @FXML
    private void initialize() {
        edit_panel.setVisible(false);

        setVisibleFalse(edit_article_error);
        setVisibleFalse(add_error);
        setVisibleFalse(del_error);
        setVisibleFalse(save_error);
        setVisibleFalse(save_success);
        setVisibleFalse(del_success);

        // устанавливаем тип и значение которое должно хранится в колонке
        tableNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tableSum.setCellValueFactory(new PropertyValueFactory<>("sum"));
        tableCurrency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        tablePercent.setCellValueFactory(new PropertyValueFactory<>("percent"));
        tableDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        showDataBase();
    }

    /**
     * Реализует работу анализа трат
     */
    @FXML
    public void onAnalise() {
        XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
        ObservableList<XYChart.Series<String, Double>> allSeries = barChar.getData();
        String category;
        double allSum = 0;
        String box = analise.getValue();
        switch (box) {
            case "Общая":
                allSeries.clear();
                category = "Общая";
                dataSeries.setName(category);//задаем имя поля
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum("Другое")
                        + calcSum("Поездки") + calcSum("Питание") + calcSum("Развлечения")
                        + calcSum("Одежда") + calcSum("Супермаркет"))); //вносим данные в оси
                barChar.getData().add(dataSeries); //задаем оси в приложении
                break;
            case "Другое":
                allSeries.clear();
                category = "Другое";
                dataSeries.setName(category);
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum(category)));
                barChar.getData().add(dataSeries);
                break;
            case "Поездки":
                allSeries.clear();
                category = "Поездки";
                dataSeries.setName(category);
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum(category)));
                barChar.getData().add(dataSeries);
                break;
            case "Питание":
                allSeries.clear();
                category = "Питание";
                dataSeries.setName(category);
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum(category)));
                barChar.getData().add(dataSeries);
                break;
            case "Развлечения":
                allSeries.clear();
                category = "Развлечения";
                dataSeries.setName(category);
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum(category)));
                barChar.getData().add(dataSeries);
                break;
            case "Одежда":
                allSeries.clear();
                category = "Одежда";
                dataSeries.setName(category);
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum(category)));
                barChar.getData().add(dataSeries);
                break;
            case "Супермаркет":
                allSeries.clear();
                category = "Супермаркет";
                dataSeries.setName(category);
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum(category)));
                barChar.getData().add(dataSeries);
                break;
        }
        barChar.setTitle("Статистика трат");
    }

    @FXML
    public void onAnaliseAlot() {
        XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
        String category;
        double allSum = 0;
        String box = analiseAlot.getValue();

        switch (box) {
            case "Общая":
                category = "Общая";
                dataSeries.setName(category);//задаем имя поля
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum("Другое")
                        + calcSum("Поездки") + calcSum("Питание") + calcSum("Развлечения")
                        + calcSum("Одежда") + calcSum("Супермаркет"))); //вносим данные в оси
                barChar.getData().add(dataSeries); //задаем оси в приложении
                break;
            case "Другое":
                category = "Другое";
                dataSeries.setName(category);
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum(category)));
                barChar.getData().add(dataSeries);
                break;
            case "Поездки":
                category = "Поездки";
                dataSeries.setName(category);
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum(category)));
                barChar.getData().add(dataSeries);
                break;
            case "Питание":
                category = "Питание";
                dataSeries.setName(category);
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum(category)));
                barChar.getData().add(dataSeries);
                break;
            case "Развлечения":
                category = "Развлечения";
                dataSeries.setName(category);
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum(category)));
                barChar.getData().add(dataSeries);
                break;
            case "Одежда":
                category = "Одежда";
                dataSeries.setName(category);
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum(category)));
                barChar.getData().add(dataSeries);
                break;
            case "Супермаркет":
                category = "Супермаркет";
                dataSeries.setName(category);
                dataSeries.getData().add(new XYChart.Data<>(category, calcSum(category)));
                barChar.getData().add(dataSeries);
                break;
        }
        barChar.setTitle("Статистика трат");
    }

    /**
     * Очищает таблицу
     */
    @FXML
    public void onClear() {
        ObservableList<XYChart.Series<String, Double>> allSeries = barChar.getData();
        allSeries.clear();
    }

    /**
     * Считает траты по одной из категорий
     *
     * @param word категория для поиска
     * @return сумму трат по определенной категории
     */
    private Double calcSum(String word) {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        ObservableList<Bill> idk = databaseHandler.getPartFromCategory(word);
        double allSum = 0;
        for (Bill bill : idk) {
            allSum += bill.getSum();
        }
        return allSum;
    }

    /**
     * Добавляет введенные данные в бд
     */
    @FXML
    void onAdd() throws SQLException, ClassNotFoundException {
        add_error.setVisible(false); //скрывает текст ошибки
        //проверяет правильность и заполненность полей
        if (isEmptyField(addNumber, add_error) && isIntZero(addNumber, add_error)
                && isEmptyField(addName, add_error) && isEmptyField(addPercent, add_error)
                && isEmptyField(addSum, add_error) && isDouble(addPercent, add_error)
                && isDouble(addSum, add_error) && isUnique(addNumber)) {
            //добавляет данные если номер уникальный
            if (isUnique(addNumber)) {
                Bill bill = new Bill();
                bill.setNumber(Integer.parseInt(addNumber.getText()));
                bill.setName(addName.getText());
                bill.setSum(Double.parseDouble(addSum.getText()));
                bill.setPercent(Double.parseDouble(addPercent.getText()));
                bill.setCategory(addCategory.getValue().toString());
                bill.setCurrency(addCurrency.getValue());
                Const.STORE_DATA.add(bill);

                DatabaseHandler databaseHandler = new DatabaseHandler();
                databaseHandler.setBill(bill);
            }
            base_table.refresh();
            showDataBase();
        } else {
            add_error.setVisible(true); //отображает текст ошибки
        }
    }

    /**
     * Удаляет данные из бд
     */
    @FXML
    void onDelete() {
        del_error.setVisible(false); //скрывает текст при ошибке выполнения команды
        del_success.setVisible(false); //отображает текст при успешном выполнении команды

        try {
            if (isEmptyField(delNumber, del_error) && isIntZero(delNumber, del_error)
                    && !isUnique(delNumber)) { //проверяет правильность и заполненность поля
                DatabaseHandler databaseHandler = new DatabaseHandler();
                databaseHandler.delete(Integer.parseInt(delNumber.getText()));

                //обновление данных бд для отображения
                base_table.refresh();
                showDataBase();

                del_success.setVisible(true); //отображает текст при успешном выполнении команды
            } else {
                del_error.setVisible(true); //отображает текст при ошибке выполнения команды
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Редактирует данные
     */
    @FXML
    void onEdit() {
        setVisibleFalse(edit_article_error);  //скрывает текст при ошибке выполнения команды
        save_button.setDisable(false); //включает кнопку сохранения при редактировании

        if (isEmptyField(editNumber, edit_article_error) && !isUnique(editNumber)
                && isIntZero(editNumber, edit_article_error)) { //проверяет правильность и заполненность поля артикля
            edit_panel.setVisible(true); //отображает панель редактирования при успешном выполнении команды
            setVisibleFalse(save_success); //скрывает текст успешного выполнения
            setVisibleFalse(save_error); //скрывает текст ошибки

            for (Bill bill : storeData) {
                if (bill.getNumber() == Integer.parseInt(editNumber.getText())) {
                    editName.setText(bill.getName());
                    editSum.setText(String.valueOf(bill.getSum()));
                    editPercent.setText(String.valueOf(bill.getPercent()));
                    editCategory.setValue(bill.getCategory());
                }
            }

        } else {
            setVisibleTrue(edit_article_error); //отображает текст при ошибке выполнения команды
            edit_panel.setVisible(false); //убирает видимость панели
        }
    }

    /**
     * Сохраняет данные введенные пользователем в бд
     */
    @FXML
    void onSave() throws SQLException, ClassNotFoundException {
        setVisibleFalse(save_error); //скрывает текст ошибки
        setVisibleFalse(save_success); //скрывает текст успешного выполнения
        //проверяет правильность и заполненность полей
        if (isEmptyField(editName, save_error) && isEmptyField(editSum, save_error)
                && isDouble(editPercent, save_error) && isDouble(editSum, save_error)
                && !editCategory.getValue().isEmpty()) {
            Bill bill = new Bill();
            bill.setNumber(Integer.parseInt(editNumber.getText()));
            bill.setName(editName.getText());
            bill.setCategory(editCategory.getValue());
            bill.setPercent(Double.parseDouble(editPercent.getText()));
            bill.setSum(Double.parseDouble(editSum.getText()));

            DatabaseHandler databaseHandler = new DatabaseHandler();
            databaseHandler.updateBill(bill);

            base_table.refresh();
            showDataBase();

            setVisibleTrue(save_success); //отображает текст при успешном выполнении

            save_button.setDisable(true); //выключает кнопку сохранения при редактировании
        } else {
            setVisibleTrue(save_error); //отображает текст при ошибке выполнении
        }
    }

    /**
     * Получает данные из бд для демонстрации
     */
    private void showDataBase() {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        storeData = databaseHandler.get();
        base_table.setItems(storeData);
    }

    /**
     * Проверяет текстовое поле на наличие введенных данных
     *
     * @param textField текстовое поле для проверки
     * @return false - поле пустое, true - поле не пустое
     */
    private boolean isEmptyField(TextField textField, Label label) {
        String text = textField.getText().replaceAll("^ +", "");
        textField.setText(text);
        if (text.isEmpty()) {
            label.setVisible(true);
            return false;
        }
        return true;
    }

    /**
     * Проверяет текстовое поле на правильность ввода дробного числа
     *
     * @param textField текстовое поле для проверки
     * @return false - поле не вещественного типа, true - поле вещественного типа
     */
    private boolean isDouble(TextField textField, Label label) {
        try {
            double doubleText = Double.parseDouble(textField.getText().replaceAll(",", ".").replaceAll(" +", ""));
            if (doubleText > 0) {
                textField.setText(Double.toString(doubleText));
                return true;
            }
        } catch (NumberFormatException e) {
            label.setVisible(true);
            return false;
        }
        return false;
    }

    /**
     * Проверяет текстовое поле на правильность ввода целого числа (не может быть равно нулю)
     *
     * @param textField текстовое поле для проверки
     * @return false - в поле не целое число, true - в поле целое число
     */
    private boolean isIntZero(TextField textField, Label label) {
        try {
            int intText = Integer.parseInt(textField.getText().replaceAll(" +", ""));
            if (intText > 0) {
                textField.setText(Integer.toString(intText));
                return true;
            }
        } catch (NumberFormatException e) {
            label.setVisible(true);
            return false;
        }
        return false;
    }

    /**
     * Проверяет уникальность введенного артикля
     *
     * @return false -  поле не уникальное,  true - поле уникальное
     */
    public boolean isUnique(TextField textField) {
        for (Bill bill : storeData) {
            if (bill.getNumber() == Integer.parseInt(textField.getText())) {
                return false;
            }
        }
        return true;
    }

    public void setVisibleTrue(Label label) {
        label.setVisible(true);
    }

    public void setVisibleFalse(Label label) {
        label.setVisible(false);
    }
}