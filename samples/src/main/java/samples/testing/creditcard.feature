Feature: Order submission
  To allow a customer to terminate an order we should process his payment.
 
  Scenario: Submit order with payment by credit card
    Given a credit card with number 'xxxx1234567890'
    When the customer try to pay for an order
    Then an error should be delivered