package com.github.gcastelo22.core;

import com.github.gcastelo22.exceptions.InvalidHttpErrorCodeException;
import com.github.gcastelo22.exceptions.InvalidRequestTypeException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * Core engine of the automation framework.
 * Provides low-level HTTP client management and universal Regex-based data extraction.
 */
public class BaseTest {

  public static Logger LOG = Logger.getLogger(BaseTest.class.getName());

  /**
   * Orchestrates the HTTP request lifecycle with automated resource disposal.
   */
  public String call(String endpoint, String input, int expectedHttpCode, String requestType,
      String accessToken)
      throws Throwable {
    ClientConfig config = new DefaultClientConfig();
    Client client = Client.create(config);

    try {
      WebResource webResource = client.resource(UriBuilder.fromUri(endpoint).build());
      ClientResponse response = null;
      String token = (accessToken != null) ? "Bearer " + accessToken : "";

      switch (requestType.toLowerCase()) {
        case "post":
          response = webResource.header("Authorization", token).type(MediaType.APPLICATION_JSON)
              .post(ClientResponse.class, input);
          break;
        case "get":
          response = webResource.header("Authorization", token).type(MediaType.APPLICATION_JSON)
              .get(ClientResponse.class);
          break;
        case "put":
          response = webResource.header("Authorization", token).type(MediaType.APPLICATION_JSON)
              .put(ClientResponse.class, input);
          break;
        case "delete":
          response = webResource.header("Authorization", token).type(MediaType.APPLICATION_JSON)
              .delete(ClientResponse.class, input);
          break;
        default:
          throw new InvalidRequestTypeException("Unsupported HTTP method: " + requestType);
      }

      String responseText = response.getEntity(String.class);

      if (responseText == null || responseText.isEmpty()) {
        LOG.log(Level.INFO, "Empty response received. HTTP Status: {0}\n", response.getStatus());
      } else {
        LOG.log(Level.INFO, "Inbound Response Payload:\n{0}\n\n", responseText);
      }

      if (response.getStatus() != expectedHttpCode) {
        throw new InvalidHttpErrorCodeException("Network Failure - Expected: " + expectedHttpCode + " | Received: " + response.getStatus());
      }

      return responseText;

    } finally {
      if (client != null) {
        client.destroy();
      }
    }
  }

  /**
   * Master extraction method.
   * Uses a sophisticated Regex to capture field values regardless of data type (String, Number or Boolean).
   * * Explanation of the Regex: \"field\" : \s* (?: \" ([^\"]*) \" | ([^,}\s]+) )
   * 1. It looks for the field name.
   * 2. It optionally matches a value inside quotes (captures Group 1).
   * 3. OR it matches a raw value until a comma, brace or space (captures Group 2).
   *
   * @param json The JSON string to parse.
   * @param field The key name to search for.
   * @return The extracted value as a String.
   */
  public String extractFieldFromJson(String json, String field) throws Exception {
    // This pattern handles: "key":"string", "key":123 and "key":true
    String regex = "\"" + field + "\":\\s*(?:\"([^\"]*)\"|([^,}\\s]+))";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(json);

    if (matcher.find()) {
      // Return Group 1 (if it was a String) or Group 2 (if it was Numeric/Boolean)
      return matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
    } else {
      throw new Exception("Field '" + field + "' not found in the response payload.");
    }
  }
}