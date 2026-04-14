package com.github.gcastelo22.tests;

import com.github.gcastelo22.core.BaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Functional test suite for the SWAPI "People" resource.
 * Inherits infrastructure from BaseTest to demonstrate both Happy Path and Error Handling scenarios.
 */
public class PeoplesTest extends BaseTest {

  /**
   * Happy Path Test: Validates character details for Luke Skywalker (ID 1).
   * Verifies a successful 200 OK status and performs data validation on the "name" field.
   */
  @Test
  public void testLukeSkywalkerDetails() throws Throwable {
    // Step 1: Execute the API call
    String responseText = call(
        "https://swapi.dev/api/people/1",
        null,
        200,
        "get",
        null
    );

    // Step 2: Extract the actual name using the universal Regex engine
    String expectedName = "Luke Skywalker";
    String actualName = extractFieldFromJson(responseText, "name");

    // Step 3: Perform Assertion
    Assert.assertEquals("Validation Failed: Character name does not match the expected value.",
        expectedName,
        actualName);

    // Step 4: Success Logging for audit purposes
    LOG.info("Assertion Passed: Character name '" + actualName + "' matches the expected value.");
  }

  /**
   * Error Handling Test: Validates the API's response when a resource is not found.
   * Ensures the framework correctly handles and verifies a 404 status code.
   */
  @Test
  public void testPeopleNotFound() throws Throwable {
    /* * Scenario: Requesting a non-existent character ID (9999).
     * The call() method will throw an InvalidHttpErrorCodeException if the status is not 404.
     */
    String responseText = call(
        "https://swapi.dev/api/people/9999",
        null,
        404,
        "get",
        null
    );

    // Extraction: Verifying the error detail message returned by the API
    String expectedDetail = "Not found";
    String actualDetail = extractFieldFromJson(responseText, "detail");

    Assert.assertEquals("Validation Failed: Error detail message does not match.",
        expectedDetail,
        actualDetail);

    // Success Logging
    LOG.info("Assertion Passed: Resource correctly returned 404 and the '" + actualDetail + "' message.");
  }
}