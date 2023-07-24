package page_object_model;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
public class MainPageModel {
    private final WebDriver driver;
    // URL лавной страницы Яндекс Самокат
    private static final String MAIN_PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    // Локатор для главной страницы
    private static final By HOME_PAGE = By.className("Home_HomePage__ZXKIX");
    //Элемент секции "Вопросы о важном"
    private static final By FAQ_ITEM = By.className("accordion__item");
    //Кнопка вопроса секции "Вопросы о важном"
    private static final By FAQ_QUESTION_BUTTON = By.className("accordion__heading");
    //Текст ответа секции "Вопросы о важном"
    private static final By FAQ_ANSWER_TEXT = By.tagName("p");
    //Секция видимого ответа "О важном" - not hidden
    private static final By FAQ_VISIBLE_ANSWER = By.xpath("//div[@class='accordion__panel' and not(@hidden)]");
    //Заголовок секции "Вопросы о важном"
    private static final By FAQ_SECTION = By.className("Home_SubHeader__zwi_E");

    public MainPageModel(WebDriver driver) {
        this.driver = driver;
    }

    //Открывает главную страницу Яндеккс Самокат
    public void open() {
        driver.get(MAIN_PAGE_URL);
    }

    //Нажимает на кнопку Принять куки и закрывает секцию с куки
    public void acceptCookiesOnMainPage() {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(HOME_PAGE));
        driver.findElement(By.className("App_CookieButton__3cvqF")).click();
    }

    //Скроллит до раздела "Вопросы о важном"
    public void scrollToFaqSection() {
        WebElement faqSection = driver.findElement(FAQ_SECTION);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", faqSection);
    }

    //Возвращает элемент списка секции "Вопросы о важном"
    // (по его индексу от 0 до 7, всего 8 вопросов и 8 ответов, индексы заданы в Параметрах, они не видны на Юай)
    private WebElement getFaqElement(int number) {
        List<WebElement> listFAQ = driver.findElements(FAQ_ITEM);
        return listFAQ.get(number);
    }

    //Кликает на вопрос секции "Вопросы о важном" согласно порядковому номеру
    public void openQuestionOnClick(int number) {
        WebElement item = getFaqElement(number);
        WebElement questionElement = item.findElement(FAQ_QUESTION_BUTTON);
        questionElement.click();
    }

    //Возвращает текст вопроса секции "Вопросы о важном" согласно порядковому номеру
    public String getQuestionText(int number) {
        WebElement item = getFaqElement(number);
        WebElement questionElement = item.findElement(FAQ_QUESTION_BUTTON);
        return questionElement.getText();
    }

    //Возвращает текст ответа секции "Вопросы о важном" согласно порядковому номеру
    public String getAnswerText(int number) {
        WebElement item = getFaqElement(number);
        WebElement answerElement = item.findElement(FAQ_ANSWER_TEXT);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(answerElement));
        return answerElement.getText();
    }

    //Возвращает количество открытых ответов секции "Вопросы о важном" (должен быть виден 1 ответ при нажатии на соответствующий вопрос)
    public int sizeOfFaqSectionVisible() {
        return driver.findElements(FAQ_VISIBLE_ANSWER).size();
    }
}

