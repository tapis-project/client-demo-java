package edu.utexas.tacc.tapis.client.demo;

import edu.utexas.tacc.tapis.tenants.client.TenantsClient;
import edu.utexas.tacc.tapis.tokens.client.TokensClient;
import org.apache.commons.lang3.StringUtils;

/**
 * Get a short term JWT from the tokens service and use it to get info from the tenants service
 * NOTE: The services can only be reached while on the TACC VPN.
 */
public class ClientDemo
{
  public static void main(String[] args) throws Exception
  {
    System.out.println("Starting ClientDemo");
    String tenant = "dev";
    String usrName = "testuser1";

    // Use the tokens service to get a user token
    String tokensBaseURL = "https://dev.develop.tapis.io";
    System.out.println("Retrieving token for tenant " + tenant + " from tokens service at: " + tokensBaseURL);
    var tokClient = new TokensClient(tokensBaseURL);
    String usrJWT = tokClient.getUsrToken(tenant, usrName);
    System.out.println("Got usrJWT: " + usrJWT);
    // Basic check of JWT
    if (StringUtils.isBlank(usrJWT))
    {
      System.out.println("Token service returned invalid JWT");
      System.exit(1);
    }

    // The token allows us to call the tenants service to get information about a tenant
    // In this case we look up the Security Kernel service base URL
    String tenantsBaseURL = "https://dev.develop.tapis.io";
    var tenantsClient = new TenantsClient(tenantsBaseURL);
    String skBaseURL = tenantsClient.getSKBasePath(tenant);
    System.out.println("Found Security Kernel service base URL: " + skBaseURL);

    System.exit(0);
  }
}
