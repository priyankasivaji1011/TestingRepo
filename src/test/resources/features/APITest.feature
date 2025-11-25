
Feature: Validate all API responses in sequence
	
	  Scenario: Validate multiple APIs
	    Given I call all APIs using keys from config
	    Then I verify all API responses from config
	 