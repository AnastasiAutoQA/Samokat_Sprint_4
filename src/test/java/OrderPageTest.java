import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import page_object_model.OrderPageModel;

@RunWith(Parameterized.class)
public class OrderPageTest {
    private WebDriver driver;
    //поля формы заказа
    private final String name;
    private final String lastname;
    private final String city;
    private final String metro;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String colour;
    private final String comment;

    //Класс-конструктор для формы заказа
    public OrderPageTest(String name, String lastname, String city, String metro, String phone, String date, String rentalPeriod, String colour, String comment) {
        this.name = name;
        this.lastname = lastname;
        this.city = city;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.colour = colour;
        this.comment = comment;
    }
    // Тестовые данные для заполнения формы заказа
    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"Вася", "Петров", "Москва", "Черкизовская", "+79118882233", "28.07.2023", "сутки", "чёрный жемчуг", "Спасибо, что привёз"},
                {"Анна", "Иванова", "Замкадыш", "Партизанская", "+79261239865", "05.08.2023", "шестеро суток", "серая безысходность", "Вход в арку направо"},
        };
    }

    @Before
    public void startBrowser() {
        // Стартуем браузер Chrome (в нем будет баг):
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);

        // Стартуем браузер FireFox (в нем проходит до конца тестового сценария):
        // System.setProperty("webdriver.gecko.driver", "C:\\WebDriverMozilla\\bin\\geckodriver.exe");
        // FirefoxOptions options = new FirefoxOptions();
        // options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        // driver = new FirefoxDriver(options);

    }
    @Test
    public void shouldCreateOrderChrome() {
        OrderPageModel objOrderPage = new OrderPageModel(driver);
        // Открываем страницу с формой заказа, соглашаемся с куки
        objOrderPage.open();
        objOrderPage.agreeWithCookiesOnOrderPage();
        // Заполняется первая страница формы заказа, переход по кнопке Далее и заполняется вторая страница формы заказа
        objOrderPage.fillOrderFirstPage(name, lastname, city, metro, phone);
        objOrderPage.transferToSecondPage();
        objOrderPage.fillOrderSecondPage(date, rentalPeriod, colour, comment);
        // Нажимается кнопка Да
        objOrderPage.fillOrderConfirmation();
        // Если не видно попапа Ваш заказ подтвержден - то будет ошибка с текстом "Заказ не был создан"
        Assert.assertTrue("Заказ не был создан", objOrderPage.confirmOrder().isDisplayed());
        // Если не видно данных о заказе с номером и тд - то будет ошибка с текстом "Данные заказа отсутсвуют"
        Assert.assertTrue("Данные заказа отсутсвуют", objOrderPage.confirmOrderData().isDisplayed());
        // Если ожидаемый текст "Заказ оформлен" не совпадет с текстом в попапе о подтверждении заказа - то будет ошибка "Неверный статус"
        // тут использовала substring, чтобы цеплять только первые 14 символов из текста, иначе тест падает-так как к тексту Заказ оформлен прилепливаются еще данные о заказе
        Assert.assertEquals("Неверный статус", "Заказ оформлен", objOrderPage.getConfirmationMessage().substring(0,14));
    }
    @After
    public void tearDown() {
        driver.quit();
    }
}
