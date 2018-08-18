Feature: Amazon - Insider Test Automation Project


  @chrome
  Scenario: 1 - Amazon Wish List In/Out Test

    Given I open amazon page

    And I wait for page

    When I see the url is "https://www.amazon.com/"

    Then I see account and list button element by xpath

    When I mouse hover on account and list button element by xpath

    Then I see sign in element by xpath

    When I click sign in element by xpath

    And I wait for page

    Then I see email element by xpath

    And I see continue to sign in button element by xpath

    Then I fill by xpath
      | email | ozan.guldali@hotmail.com |

    And I click continue to sign in button element by xpath

    And I wait for page

    Then I see password element by xpath

    And I see sign in button element by xpath

    When I fill by xpath
      | password | 2018insider |

    And I click sign in button element by xpath

    And I wait for page

    Then I see search field element by xpath

    And I see search button element by xpath

    When I fill by xpath
      | search field | samsung |

    And I click search button element by xpath

    And I wait for page

    Then I see webpage title as "Amazon.com: samsung"

    And I see text
      | 1-16 of over  |
      | results for   |
      | "samsung"     |

    And I see the url contains "field-keywords=samsung"

    When I click second page button element by xpath

    And I see text
      | 17-32 of over |
      | results for   |
      | "samsung"     |

    And I see the url contains "samsung&page=2"

    When I click 18th product element by xpath

    And I wait for page

    Then I see add to list selector button element by xpath

    When I click add to list selector button element by xpath

    And I wait for 3 seconds

    Then I see add to wish list button element by xpath

    When I click add to wish list button element by xpath

    And I wait for 3 seconds

    And I click close add to list frame element by xpath

    And I wait for page

    Then I see account and list button element by xpath

    When I mouse hover on account and list button element by xpath

    And I wait for 3 seconds

    Then I see wish list button element by xpath

    When I click wish list button element by xpath

    And I wait for page

    Then I see my added product is on the list

    And I see delete from list button element by xpath

    When I click delete from list button element by xpath
    
    And I wait for 3 seconds

    Then I see text
      | Deleted |

    When I refresh the page

    And I wait for page

    Then I see my added product is not on the list





