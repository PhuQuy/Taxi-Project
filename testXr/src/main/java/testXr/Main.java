package testXr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import testXr.User;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Main {
	private static final String SERVICE_URL = "http://127.0.0.1:8089/WebService/sample";
	// connection timeout, in milliseconds (waiting to connect)
	private static final int CONN_TIMEOUT = 3000;

	// socket timeout, in milliseconds (waiting for data)
	private static final int SOCKET_TIMEOUT = 5000;

	// private static final String TAG = "WebServiceTask";

	private static HttpParams getHttpParams() {

		HttpParams htpp = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
		HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

		return htpp;
	}

	private static HttpResponse doResponse(String url) {
		HttpClient httpClient = new DefaultHttpClient(getHttpParams());
		HttpResponse response = null;

		try {
			HttpGet httpget = new HttpGet(url);
			response = httpClient.execute(httpget);
		} catch (Exception e) {
			System.out.println("bug " + e.toString());
		}

		return response;

	}

	private static String inputStreamToString(InputStream is) {

		String line = "";
		StringBuilder total = new StringBuilder();

		// Wrap a BufferedReader around the InputStream
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));

		try {
			// Read response until the end
			while ((line = rd.readLine()) != null) {
				total.append(line);
			}
		} catch (IOException e) {
			System.out.println(e.toString());
		}

		// Return full string
		return total.toString();
	}

	protected static String doInBackground(String url) {

		String result = "";
		HttpResponse response = doResponse(url);
		User user = new User();
		if (response == null) {
			return result;
		} else {

			try {

				result = inputStreamToString(response.getEntity().getContent());
				user = new JSONDeserializer<User>().use(null, User.class)
						.deserialize(result);

			} catch (IllegalStateException e) {
				System.out.println(e.toString());

			} catch (IOException e) {
				System.out.println(e.toString());
			}

		}

		return user.getEmail();
	}

	private static String signup() {
		User user = new User();
		user.setEmail("phuquy.uit@gmail.com");
		user.setEnable(true);
		user.setName("Quy");
		user.setPassword("phuquy");
		HttpClient httpClient = new DefaultHttpClient(getHttpParams());
		HttpResponse response = null;
		String result = "";
		try {
			HttpPost httppost = new HttpPost("http://127.0.0.1:8089/WebService/signup");
			 JSONObject json = new JSONObject();
			 json.put("userJson", user);
			 StringEntity se = new StringEntity( json.toString());
			 httppost.setHeader("Content-type", "application/json");
			 se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            // Add parameters
            httppost.setEntity(se);

            response = httpClient.execute(httppost);
            result = inputStreamToString(response.getEntity().getContent());
		} catch (Exception e) {
			System.out.println("bug " + e.toString());
		}

		return result;
	}

	public static void main(String[] args) {
	//	System.out.println(doInBackground(SERVICE_URL));
		System.out.println(signup());
	}
}
