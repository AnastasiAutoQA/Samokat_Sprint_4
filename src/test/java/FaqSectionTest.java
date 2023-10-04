//Тестовый сценарий: Выпадающий список в разделе «Вопросы о важном».
// Нужно проверить: когда нажимаешь на стрелочку, открывается соответствующий текст.
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import page_object_model.MainPageModel;

import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class FaqSectionTest extends BaseTest {
    // private WebDriver driver;
    //Номер вопроса
    private final int number;
    //Вопрос
    private final String checkedQuestionText;
    //Ответ
    private final String checkedAnswerText;

    public FaqSectionTest(int number, String checkedQuestionText, String checkedAnswerText) {
        this.number = number;
        this.checkedQuestionText = checkedQuestionText;
        this.checkedAnswerText = checkedAnswerText;
    }
    @Parameterized.Parameters
    public static Object[][] getExpectedText() {
        return new Object[][]{
                // Ожидаемые результаты Вопрос-Ответ в секции "О важном"
                {0, "Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }
    //Проверяем открытие секции ответа по клику на вопрос.
    // Проверяем соответствие вопроса и ответа ожидаемому набору данных.
    @Test
    public void shouldCheckFAQContent() {
        MainPageModel objMainPage = new MainPageModel(driver);
        objMainPage.open();
        objMainPage.acceptCookiesOnMainPage();
        objMainPage.scrollToFaqSection();
        objMainPage.openQuestionOnClick(number);
        String questionText = objMainPage.getQuestionText(number);
        String answerText = objMainPage.getAnswerText(number);
        Assert.assertEquals(1, objMainPage.sizeOfFaqSectionVisible()); // Должен открыться один ответ
        Assert.assertEquals("Показан неверный вопрос", checkedQuestionText, questionText);
        Assert.assertEquals("Показан неверный ответ", checkedAnswerText, answerText);
    }
}