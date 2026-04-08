package com.github.gcastelo22.tests;

import com.github.gcastelo22.core.BaseTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Functional test suite for the SWAPI "Starships" resource.
 * Focuses on list validation, pagination metadata and collection integrity.
 */
public class StarshipsTest extends BaseTest {

  /**
   * Validates the starships collection and its pagination metadata.
   * Ensures the API returns a populated list and correct navigation links.
   */
  @Test
  public void testStarshipsListAndPagination() throws Throwable {
    // Step 1: Execute the API call to list starships
    String responseText = call(
        "https://swapi.dev/api/starships/",
        null,
        200,
        "get",
        null
    );

    // Step 2: Validate the "count" field (Total number of resources)
    // Using our master regex that handles numeric values without quotes
    String countStr = extractFieldFromJson(responseText, "count");
    int count = Integer.parseInt(countStr);

    Assert.assertTrue("Validation Failed: The 'count' field should be greater than zero.",
        count > 0);

    // Step 3: Validate that the "results" list is present and not empty
    // We check if the "results" key exists and has content
    String results = extractFieldFromJson(responseText, "results");
    Assert.assertNotNull("Validation Failed: The 'results' list is missing.", results);
    Assert.assertFalse("Validation Failed: The 'results' list is empty.", results.isEmpty());

    // Step 4: Validate Pagination - "next" page link
    // The "next" field usually contains the URL for page 2
    String nextUrl = extractFieldFromJson(responseText, "next");

    Assert.assertNotNull("Validation Failed: The 'next' page link is missing.", nextUrl);
    Assert.assertTrue("Validation Failed: The 'next' link does not follow the expected URL pattern.",
        nextUrl.contains("https://swapi.dev/api/starships/?page=2"));

    // Success Logging
    LOG.info("Assertion Passed: Starships list is populated. Total count: " + count);
    LOG.info("Assertion Passed: Pagination is functional. Next page: " + nextUrl);
  }
}