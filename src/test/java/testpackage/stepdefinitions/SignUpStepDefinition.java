package testpackage.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import testpackage.framework.Hooks;

public class SignUpStepDefinition {
    @Given("Im a new user")
    public void im_a_new_user() {
        WebDriver driver= Hooks.driver;
        driver.navigate().to("http://demo.automationtesting.in/Register.html");
    }

    @When("I try to register using the signup page")
    public void i_try_to_register_using_the_signup_page() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("I should be able to see a confirmation message")
    public void i_should_be_able_to_see_a_confirmation_message() {
        // Write code here that turns the phrase above into concrete actions
    }
}
