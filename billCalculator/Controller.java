package billCalculator;
/*
 * реализуется
 * проверка корректности чека
 * рассчет чаевых на основе чека
 * рассчет итоговой суммы с возможностью округления
 *
 * @Author Mashina
 */

import java.text.DecimalFormat;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class Controller {
    @FXML
    private TextField input;

    @FXML
    private TextField outputBill;
    @FXML
    private TextField outputRubBill;

    @FXML
    private TextField outputTips;
    @FXML
    private TextField outputRubTips;

    @FXML
    private TextField result;
    @FXML
    private TextField rubResult;

    @FXML
    private Label errorText;

    @FXML
    private Slider tips;

    @FXML
    private ToggleButton inRubButton;

    @FXML
    private ToggleButton roundButton;

    @FXML
    private ComboBox<?> currencyBox;

    /**
     * Выполняет рассчет данных при нажатии кнопки "Посчтитать" в гф. интерфейсе
     */
    @FXML
    void onCalculate() {
        errorText.setVisible(false);
        Double billInput = getInput(input);
        if (billInput != null) {
            double calcTips = getDecimalValue(calcTips(tips, billInput));
            setCalculatedText(billInput, calcTips);
        }
    }

    /**
     * Обработка выбранной валюты
     */
    @FXML
    void onChosenCurrency() {
        String currency = currencyBox.getValue().toString();
        if (!currency.equals("Рубли")) {
            inRubButton.setVisible(true);
        } else {
            inRubButton.setVisible(false);
            setInvisibleRubArea();
        }
        clearWithoutInput();
    }

    /**
     * Вызов методов округления и пересчета значения
     */
    @FXML
    void onRoundButton() {
        try {
            double tips = getDecimalValue(outputTips);
            Double inputResult = getDecimalValue(result);
            if (inputResult == null) {
                throw new NumberFormatException("Значение не посчитано");
            }
            Double inputRubResult = getDecimalValue(rubResult);

            if (roundButton.isSelected()) {
                if (tips != 0) {
                    floorRound(inputResult, inputRubResult);
                } else {
                    ceilRound(inputResult, inputRubResult);
                }
            } else {
                onCalculate();
            }
        } catch (NumberFormatException e) {
            getAnimation(input);
            errorText.setText("Значение не посчитано");
            errorText.setVisible(true);
        }
    }

    /**
     * Округляет итоговую суммы чека в бОльшую сторону
     *
     * @param inputResult    значение в поле результата в валюте
     * @param inputRubResult значение в поле результата в рублях
     */
    private void ceilRound(double inputResult, Double inputRubResult) {
        if (inputRubResult != null) {
            double roundRubResult = Math.ceil(inputRubResult);
            rubResult.setText(decimalFormatting(roundRubResult));
        }
        double roundResult = Math.ceil(inputResult); //округление в большую сторону
        result.setText(decimalFormatting(roundResult));
    }

    /**
     * Округляет итоговую суммы чека в мельшую сторону
     *
     * @param inputResult    значение в поле результата в валюте
     * @param inputRubResult значение в поле результата в рублях
     */
    private void floorRound(double inputResult, Double inputRubResult) {
        if (inputRubResult != null) {
            double roundRubResult = Math.floor(inputRubResult);
            rubResult.setText(decimalFormatting(roundRubResult));
        }

        double roundResult = Math.floor(inputResult); //округление в меньшую сторону
        result.setText(decimalFormatting(roundResult));
    }

    /**
     * Если кнопка "в рубли" в гф. интерфейсе активна делает видимым поле рублей
     * иначе делает невидимым
     */
    @FXML
    void onRubButton() {
        if (inRubButton.isSelected()) {
            setVisibleRubArea();
        } else {
            setInvisibleRubArea();
        }
    }

    /**
     * Устанавливает размер и делает видимым поля валюты в рублях
     */
    private void setVisibleRubArea() {
        outputTips.setMaxSize(73, 25);
        outputBill.setMaxSize(73, 25);
        result.setMaxSize(73, 25);
        outputRubTips.setVisible(true);
        outputRubBill.setVisible(true);
        rubResult.setVisible(true);
    }

    /**
     * Устанавлвает исходный размер и делает невидимыми поля валюты в рублях
     */
    private void setInvisibleRubArea() {
        outputTips.setMaxSize(150, 31);
        outputBill.setMaxSize(150, 31);
        result.setMaxSize(150, 31);
        outputRubTips.setVisible(false);
        outputRubBill.setVisible(false);
        rubResult.setVisible(false);
    }

    /**
     * Рассчитывает чаевые
     *
     * @param slider   значение чаевых со слайдера (0/5/10)%
     * @param getInput корректное число представленное в чеке
     * @return значение чаевых от суммы чека
     */
    private double calcTips(Slider slider, Double getInput) {
        int tipPercent = (int) slider.getValue();
        return getInput * tipPercent / 100;
    }

    /**
     * Задает посчитанные значения в поля
     *
     * @param input    корректное число представленное в чеке
     * @param calcTips значение чаевых от суммы чека
     */
    private void setCalculatedText(Double input, double calcTips) {
        String currency = currencyBox.getValue().toString();
        double formatResult = getDecimalValue(input + calcTips);

        HashMap<String, Double> currencyAndRate = new HashMap<>();
        currencyAndRate.put("Доллары", 72.0);
        currencyAndRate.put("Евро", 78.0);
        currencyAndRate.put("Тенге", 6.0);

        for (String key : currencyAndRate.keySet()) {
            if (currency.equals(key)) {
                double keyValue = currencyAndRate.get(key);
                outputRubBill.setText(decimalFormatting(input * keyValue));
                outputRubTips.setText(decimalFormatting(calcTips * keyValue));
                rubResult.setText(decimalFormatting(formatResult * keyValue));
            }
        }

        outputBill.setText(decimalFormatting(input));
        outputTips.setText(decimalFormatting(calcTips));
        result.setText(decimalFormatting(formatResult));
    }

    /**
     * Проверяет корректность введенных данных чека и возвращает их
     *
     * @param input текстовое поле чека
     * @return корректные данные чека
     */
    private Double getInput(TextField input) {
        try {
            String replaceInput = input.getText();
            replaceInput = replaceInput.replaceAll(",", ".");
            Double doubleReplaceInput = Double.parseDouble(replaceInput);
            if (doubleReplaceInput <= 0) throw new NullPointerException("Число слишком маленькое");
            if (doubleReplaceInput > 9999) throw new UnsupportedOperationException("Число слишком большое");
            if (doubleReplaceInput <= 10 && currencyBox.getValue().equals("Рубли"))
                throw new NullPointerException("Число слишком маленькое");
            return doubleReplaceInput;
        } catch (NumberFormatException e) {
            getAnimation(input);
            errorText.setText("Неверное число");
            errorText.setVisible(true);
        } catch (NullPointerException e) {
            getAnimation(input);
            errorText.setText("Число слишком маленькое");
            errorText.setVisible(true);
        } catch (UnsupportedOperationException e) {
            getAnimation(input);
            errorText.setText("Число слишком большое");
            errorText.setVisible(true);
        }
        return null;
    }

    /**
     * Возвращает значение округленного поля
     *
     * @param input текстовое поле для округления
     * @return округленный значение в виде вещественного числа
     */
    private Double getDecimalValue(TextField input) {
        return (input.getText().isEmpty()) ? null : Double.parseDouble(decimalFormatting(Double.parseDouble(input.getText())));
    }

    /**
     * Возвращает значение округленного числа
     *
     * @param input число для округления
     * @return округленное значение числа
     */
    private Double getDecimalValue(double input) {
        return Double.parseDouble(decimalFormatting(input));
    }

    /**
     * Округляет числа
     *
     * @param number число для округления до двух знаков после запятой
     * @return округленное число с двумя знаками после запятой, запятая заменена на точку
     */
    private String decimalFormatting(double number) {
        String numbers = new DecimalFormat("0.00").format(number);
        return numbers.replaceAll(",", ".");
    }

    /**
     * Очищает поля и убирает лейбл
     */
    @FXML
    void onClear() {
        errorText.setVisible(false);
        input.clear();
        outputBill.clear();
        outputRubBill.clear();
        outputTips.clear();
        outputRubTips.clear();
        result.clear();
        rubResult.clear();
        roundButton.setSelected(false);
    }

    /**
     * Очищает поля и убирает лейбл, поле с введенным чеком остается нетронутым
     */
    private void clearWithoutInput() {
        errorText.setVisible(false);
        outputBill.clear();
        outputRubBill.clear();
        outputTips.clear();
        outputRubTips.clear();
        result.clear();
        rubResult.clear();
        inRubButton.setSelected(false);
        roundButton.setSelected(false);
        setInvisibleRubArea();
    }

    /**
     * Реализует анимацию тряски
     *
     * @param node поле, которое должно трястись
     */
    private void getAnimation(TextField node) {
        Shake animation = new Shake(node);
        animation.playAnimation();
    }
}
