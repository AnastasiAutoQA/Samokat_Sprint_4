package page_object_model;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class OrderPageModel {
    private final WebDriver driver;
    // Страница формы заказа и ссылка на нее
    private static final String ORDER_PAGE_URL = "https://qa-scooter.praktikum-services.ru/order";
    private static final By FIRST_ORDER_PAGE = By.className("App_App__15LM-");
    private static final By SECOND_ORDER_PAGE = By.className("Order_Form__17u6u");
    // Поля формы заказа 1 и 2 страниц
    private static final By NAME_INPUT_FIELD = By.xpath("//input[@placeholder ='* Имя']");
    private static final By LASTNAME_INPUT_FIELD = By.xpath("//input[@placeholder ='* Фамилия']");
    private static final By ADDRESS_INPUT_FIELD = By.xpath("//input[@placeholder ='* Адрес: куда привезти заказ']");
    private static final By METRO_STATION_DROPDOWN = By.className("select-search");
    private static final By PHONE_INPUT_FIELD = By.xpath("//input[@placeholder ='* Телефон: на него позвонит курьер']");
    private static final By NEXT_BUTTON = By.xpath("//button[text() ='Далее']");
    private static final By DATE_INPUT_FIELD = By.xpath("//input[@placeholder ='* Когда привезти самокат']");
    private static final By RENTAL_PERIOD_MENU = By.xpath("//span[@class = 'Dropdown-arrow']");
    //Раздел подтверждения заказа
    private static final By ORDER_BUTTON = By.xpath("//div[@class='Order_Buttons__1xGrp']/button[text() ='Заказать']");
    // Попап с запросом подтверждения заказа и кнопками Да/Нет
    private static final By REQUEST_FOR_CONFIRMATION = By.className("Order_Modal__YZ-d3");
    private static final By YES_BUTTON_CONFIRMATION = By.xpath("//button[text() ='Да']");
    //Попап с сообщением о созданном заказе и данными о заказе (номер заказа и тд)
    private static final By CONFIRMED_ORDER_MESSAGE = By.className("Order_ModalHeader__3FDaJ");
    private static final By CONFIRMED_ORDER_FORM = By.className("Order_Modal__YZ-d3");
    private static final By CONFIRMED_ORDER_DATA = By.className("Order_Text__2broi");

    public OrderPageModel(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(ORDER_PAGE_URL);
    }

    // Нажимаем кнопки Принять куки
    public void agreeWithCookiesOnOrderPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(FIRST_ORDER_PAGE));
        driver.findElement(By.className("App_CookieButton__3cvqF")).click();
    }

    //Нажимаем кнопку "Заказать" на главной странице
    public void clickOrderButton(By orderButtonLocator) {
        WebElement orderButton = driver.findElement(orderButtonLocator);
        orderButton.click();
    }

    //Заполняем данными поля первой страницы формы заказа - данные заходят из Параметров в тесте
    public void fillOrderFirstPage(String name, String lastname, String city, String metro, String phone) {
        driver.findElement(NAME_INPUT_FIELD).clear();
        driver.findElement(NAME_INPUT_FIELD).sendKeys(name);
        driver.findElement(LASTNAME_INPUT_FIELD).clear();
        driver.findElement(LASTNAME_INPUT_FIELD).sendKeys(lastname);
        driver.findElement(ADDRESS_INPUT_FIELD).clear();
        driver.findElement(ADDRESS_INPUT_FIELD).sendKeys(city);
        driver.findElement(METRO_STATION_DROPDOWN).click();
        By metroLocator = By.xpath("//div[@class='Order_Text__2broi' and text()='" + metro + "']");
        driver.findElement(metroLocator).click();
        driver.findElement(PHONE_INPUT_FIELD).clear();
        driver.findElement(PHONE_INPUT_FIELD).sendKeys(phone);
    }

    //Нажимаем кнопку Далее
    // Ожидание загрузки второй страницы формы заказа (где дата, период, цвет и коммент курьеру)
    public void transferToSecondPage() {
        driver.findElement(NEXT_BUTTON).click();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(SECOND_ORDER_PAGE));
    }

    //Заполняем данными поля второй страницы заказа - данные заходят из Параметров в тесте
    public void fillOrderSecondPage(String date, String rentalPeriod, String colour, String comment) {
        driver.findElement(DATE_INPUT_FIELD).click();
        driver.findElement(DATE_INPUT_FIELD).sendKeys(date);
        // Вводим срок аренды - количество дней
        driver.findElement(RENTAL_PERIOD_MENU).click();
        By rentalPeriodValue = By.xpath(".//*[contains(text(), '" + rentalPeriod + "') and starts-with(@class, 'Dropdown')]");
        driver.findElement(rentalPeriodValue).click();
        // Выбираем цвет самоката
        By colourSelected = By.xpath(".//label[contains(text(), '"+ colour +"')]");
        driver.findElement(colourSelected).click();
        // Вводится коммент
        By commentValue = By.xpath(".//input[@class='Input_Input__1iN_Z Input_Responsible__1jDKN']");
        driver.findElement(commentValue).sendKeys(comment);
        // Нажимаем кнопку Заказать - подтверждаем заказ
        driver.findElement(ORDER_BUTTON).click();
    }

    // Нажимаем кнопку Да - подтверждаем заказ
    public void fillOrderConfirmation() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(REQUEST_FOR_CONFIRMATION));
        driver.findElement(YES_BUTTON_CONFIRMATION).click();
    }
    // Методы возвращают элементы, а так же текст попапа с инфой о подтвержденном заказе
    // Ваш заказ подтвержен , номер закакза и тд
    public WebElement confirmOrder() {
        return driver.findElement(CONFIRMED_ORDER_FORM);
    }

    public WebElement confirmOrderData() {
        return driver.findElement(CONFIRMED_ORDER_DATA);
    }

    public String getConfirmationMessage() {
        return driver.findElement(CONFIRMED_ORDER_MESSAGE).getText();
    }
}
