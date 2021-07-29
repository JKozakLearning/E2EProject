Feature: Login into Website

  Scenario Outline: Positive test validating login with good email format
    Given Initialize the browser with chrome
    And Navigate to "http://qaclickacademy.com/" Site
    And Click on Login link in home page to land on sign in page
    When User enters email <username> and password <password> and press logs in button
    Then Verify that user is successfully login
    And Quit browser
    Examples:
      |username                     |password     |
      |nonrestricteduser@mail.com   |qwerty1234   |
      |restricteduser2@mail.com     |qwerty1234   |

  Scenario Outline: Negative test validating login with bad email format
    Given Initialize the browser with chrome
    And Navigate to "http://qaclickacademy.com/" Site
    And Click on Login link in home page to land on sign in page
    When User enters email <username> and password <password> and press logs in button
    Then Verify correct error msg
    And Quit browser
    Examples:
      |username                     |password   |
      |nonrestricteduser_mail.com   |qwerty1234 |
      |restricteduser2_mail.com     |qwerty1234 |

