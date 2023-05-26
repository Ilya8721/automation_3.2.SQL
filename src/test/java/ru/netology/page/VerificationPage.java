package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
  private SelenideElement codeField = $("[data-test-id=code] input");
  private SelenideElement verifyButton = $("[data-test-id=action-verify]");
  private SelenideElement errorMessagePopUp = $("[data-test-id=error-notification]");


  public VerificationPage() {
    codeField.shouldBe(visible);
  }

  public DashboardPage validVerify(String verificationCode) {
    codeField.setValue(verificationCode);
    verifyButton.click();
    return new DashboardPage();
  }

  public void shouldReturnAnErrorMessage(String verificationCode) {
    codeField.setValue(verificationCode);
    verifyButton.click();
    errorMessagePopUp
            .shouldBe(visible)
            .shouldHave(Condition.text("Ошибка"))
            .shouldHave(Condition.text("Превышено количество попыток ввода кода!"));
  }
}
