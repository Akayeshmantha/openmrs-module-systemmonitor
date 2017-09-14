package org.openmrs.module.systemmonitor.curl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

/**
 * This class emulates curl (a UNIX command line tool to transfer data from or
 * to a server)
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
	public static JSONObject get(String urlString, String username, String password)
			throws UnknownHostException, SocketException {
		urlString = removeLinesAndSpacesCharactersFromString(urlString);
		if (StringUtils.isNotBlank(urlString) && !urlString.endsWith("null")) {
			try {
				Client client = Client.create();

				if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
					client.addFilter(new HTTPBasicAuthFilter(username, password));
				}

				WebResource webResource = client.resource(urlString);
				ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
				if (response.getStatus() != 200) {
					if(response.getStatus() == 429) {
						//too many requests
					} else
						throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
				} else
					return new JSONObject(response.getEntity(String.class));
			} catch (Exception e) {
				e.printStackTrace();
			}
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
	public static JSONObject post(String urlString, JSONObject data, String username, String password)
			throws UnknownHostException, SocketException {
		JSONObject serverResponse = null;

		urlString = removeLinesAndSpacesCharactersFromString(urlString);
		if (StringUtils.isNotBlank(urlString) || !urlString.endsWith("null")) {
			try {
				Client client = Client.create();

				if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
					client.addFilter(new HTTPBasicAuthFilter(username, password));
				}

				WebResource webResource = client.resource(urlString);
				ClientResponse response = webResource.type("application/json").post(ClientResponse.class,
						data.toString());

				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
				} else {
					try {
						serverResponse = new JSONObject(response.getEntity(String.class));
					} catch (JSONException ex) {
						ex.printStackTrace();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return serverResponse;
	}

	public static String sendNormalHtmlGET(String url) throws IOException, UnknownHostException, SocketException {
		if (StringUtils.isNotBlank(url) || !url.endsWith("null")) {
			url = removeLinesAndSpacesCharactersFromString(url);

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			try {
				// con.setRequestProperty("User-Agent", USER_AGENT);
				int responseCode = con.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) { // success
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();

					return response.toString();
				}
			} catch (UnknownHostException e) {//
				return null;
			} catch (SocketException e1) {// Network is unreachable
				return null;
			}
		}
		return null;
	}

	private static String removeLinesAndSpacesCharactersFromString(String string) {
		return StringUtils.isNotBlank(string) ? string.replace("\t", "").replace("\n", "") : string;
	}
}
