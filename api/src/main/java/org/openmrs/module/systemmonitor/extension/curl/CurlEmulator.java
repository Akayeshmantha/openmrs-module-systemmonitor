package org.openmrs.module.systemmonitor.extension.curl;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * This class emulates curl (a UNIX command line tool to transfer data from
 * or to a server)
 * 
 * @author k-joseph
 *
 */
public class CurlEmulator {

	/**
	 * This methods emulates a normal json REST API fetch (curl get equivalent)
	 * 
	 * @param urlString,
	 *            url to fetch
	 * 
	 * @return json response from the url
	 */
	public JSONObject get(String urlString) {
		try {
			Client client = Client.create();
			WebResource webResource = client.resource(urlString);
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			return new JSONObject(response.getEntity(String.class));
		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;
	}

	/**
	 * This methods emulates a normal json REST API post/sending data to server
	 * (curl post equivalent)
	 * 
	 * @param urlString
	 * @param data;
	 *            to be sent to the server
	 * @param username,
	 *            password; used to authenticate the request in-case if required
	 * 
	 * @return serverReponse, text response message from the server
	 */
	public String post(String urlString, JSONObject data, String username, String password) {
		String serverResponse = null;

		try {
			Client client = Client.create();

			if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
				client.addFilter(new HTTPBasicAuthFilter(username, password));
			}

			WebResource webResource = client.resource(urlString);
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, data.toString());

			if (response.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}

			serverResponse = response.getEntity(String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return serverResponse;
	}
}
