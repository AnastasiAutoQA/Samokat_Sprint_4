import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import page_object_model.MainPageModel;
import page_object_model.OrderPageModel;
import org.openqa.selenium.By;

@RunWith(Parameterized.class)
public class Order2ButtonsTest {
    private WebDriver driver;
    //private final int number;
    private final By orderButtonLocator;
    private final String orderPageUrl;
    // Наверху кнопка "Заказать"
    private static final By HEADER_ORDER_BUTTON = By.xpath("//div[@class='Header_Nav__AGCXC']/button[text() ='Заказать']");
    // Внизу кнопка "Заказать"
    private static final By MIDDLE_ORDER_BUTTON = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text() ='Заказать']");
    private static final String ORDER_PAGE_URL = "https://qa-scooter.praktikum-services.ru/order";

    public Order2ButtonsTest(By orderButtonLocator, String orderPageUrl) {
        this.orderButtonLocator = orderButtonLocator;
        this.orderPageUrl = orderPageUrl;
    }
    // Тестовые данные - 2 кнопка Заказать наверху и посередине страницы
    // и адрес страницы формы заказа, чтобы проверить, осуществился ли переход
    @Parameterized.Parameters
    public static Object[][] getExpectedButton() {
        return new Object[][]{
                {HEADER_ORDER_BUTTON, ORDER_PAGE_URL},
                {MIDDLE_ORDER_BUTTON, ORDER_PAGE_URL},
        };
    }
    @Before
    public void startBrowser() {
        //Стартуем браузер Chrome (в нем будет баг):
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(chromeOptions);

        // Стартуем браузер FireFox (в нем проходит до конца тестового сценария):
        //System.setProperty("webdriver.gecko.driver", "C:\\WebDriverMozilla\\bin\\geckodriver.exe");
        //FirefoxOptions options = new FirefoxOptions();
        //options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        //driver = new FirefoxDriver(options);
        //driver.manage().window().maximize();
        //driver.manage().deleteAllCookies();
        //driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

        @Test
        public void shouldClickOnAnyOrderButton() {
            MainPageModel objPage = new MainPageModel(driver);
            objPage.open();
            objPage.acceptCookiesOnMainPage();
            OrderPageModel objOrderPage = new OrderPageModel(driver);
            // Кликаем кнопку Заказать (верхнюю или посередине- в зависимости от входящего значения из Параметров)
            objOrderPage.clickOrderButton(orderButtonLocator);
            // Получаем текущий Url, куда перешли после клика, и сравниваем с ожидаемым
            String currentUrl = driver.getCurrentUrl();
            Assert.assertEquals(currentUrl, orderPageUrl);
        }

        @After
        public void tearDown () {
           driver.quit();
        }
}

