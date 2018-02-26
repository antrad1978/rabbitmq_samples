package samples.testing;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import samples.CreditCardValidator;
 
public class CreditCardSteps {
	String value;
 
	@Given(".+credit card with number '(.+)'")
	public void prepareCreditCard(String number) {
		value=number;
	}
 
	@When("^the customer try to pay for an order$")
	public void the_customer_try_to_pay_for_an_order() throws Throwable{
		
	}
 
	@Then("^an error should be delivered$")
	public void an_error_should_be_delivered() throws Throwable{
		boolean res=CreditCardValidator.isValid(value);
		assertThat(false, equalTo(res));
	}
}
