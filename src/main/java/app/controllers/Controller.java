package app.controllers;

import com.ritaja.xchangerate.api.CurrencyConverter;
import com.ritaja.xchangerate.api.CurrencyConverterBuilder;
import com.ritaja.xchangerate.api.CurrencyNotSupportedException;
import com.ritaja.xchangerate.endpoint.EndpointException;
import com.ritaja.xchangerate.service.ServiceException;
import com.ritaja.xchangerate.storage.StorageException;
import com.ritaja.xchangerate.util.Currency;
import com.ritaja.xchangerate.util.Strategy;
import com.weatherapi.api.WeatherAPIClient;
import com.weatherapi.api.controllers.APIsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.json.JSONException;

import java.math.BigDecimal;

public class Controller {

    private final ObservableList<Currency> currencies = FXCollections.observableArrayList(Currency.RUB, Currency.USD, Currency.EUR, Currency.GBP, Currency.JPY);
    private final CurrencyConverter converter = new CurrencyConverterBuilder()
            .strategy(Strategy.CURRENCY_LAYER_FILESTORE)
            .accessKey("b0382ecf8524786e264ca6cbb2c3442d")
            .buildConverter();
    @FXML
    public ChoiceBox<Currency> choiceBox1;
    public ChoiceBox<Currency> choiceBox2;
    @FXML
    private Label label;

    @FXML
    private void onClickMoney() {
        choiceBox1.setValue(Currency.RUB);
        choiceBox2.setValue(Currency.RUB);
        printCurrencyQuotes();
        choiceBox1.setItems(currencies);
        choiceBox2.setItems(currencies);
        choiceBox1.setOnAction(actionEvent -> printCurrencyQuotes());
        choiceBox2.setOnAction(actionEvent -> printCurrencyQuotes());
        try {
            converter.updateResource(choiceBox1.getValue(), choiceBox2.getValue());
        } catch (CurrencyNotSupportedException | StorageException | JSONException | EndpointException | ServiceException e) {
            e.printStackTrace();
        }
    }

    private void printCurrencyQuotes() {
        try {
            label.setText("Курс " + choiceBox1.getValue() + " к " + choiceBox2.getValue() + ": " +
                    converter.convertCurrency(new BigDecimal("1"), choiceBox1.getValue(), choiceBox2.getValue())
                            .toString());
        } catch (CurrencyNotSupportedException | JSONException | StorageException | EndpointException | ServiceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onClickWeather() throws Throwable {
        String key = "e6e6be094eff44b6a3372701210402";

        WeatherAPIClient client = new WeatherAPIClient(key);
        APIsController aPIs = client.getAPIs();
        String q = "Москва";
        String lang = "ru";
        label.setText("Погода в городе " + q + " сейчас: "
                + aPIs.getRealtimeWeather(q, lang).getCurrent().getTempC().toString() + "\n"
                + "Ощущается как: "
                + aPIs.getRealtimeWeather(q, lang).getCurrent().getFeelslikeC() + "\n"
                + aPIs.getRealtimeWeather(q, lang).getCurrent().getCondition().getText() + "\n"
                + "Скорость ветра: " + Math.round(aPIs.getRealtimeWeather(q, lang).getCurrent().getWindKph() * 1000 / 3600) + " м/с "
                + aPIs.getRealtimeWeather(q, lang).getCurrent().getWindDir() + "\n");
    }
}