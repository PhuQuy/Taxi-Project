package testXr;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;







import testXr.User;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class Main {
	private static final String SERVICE_URL = "http://127.0.0.1:8089/WebService/user";
	
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
		user.setEmail("Children2ee.uit@gmail.com");
		user.setEnable(true);
		user.setName("Children2");
		user.setPassword("children2");
		List<String> roles = new ArrayList<String>();
		String role = "Children";
		roles.add(role);
		HttpClient httpClient = new DefaultHttpClient(getHttpParams());
		HttpResponse response = null;
		String result = "";
		try {
			//{"email":"Admin.uit@gmail.com","enable":true,"name":"Admin","password":"admin"}
			HttpPost httppost = new HttpPost(SERVICE_URL + "/add_user");
			 String json = new JSONSerializer().exclude("*.class").serialize(user);
			 List<NameValuePair> postParams = new ArrayList<NameValuePair>();  
			 postParams.add(new BasicNameValuePair("user", json));   
			 postParams.add(new BasicNameValuePair("role", role));  
			 httppost.setEntity(new UrlEncodedFormEntity(postParams)); 

            response = httpClient.execute(httppost);
            result = inputStreamToString(response.getEntity().getContent());
		} catch (Exception e) {
			System.out.println("bug " + e.toString());
		}

		return result;
	}
	
	private static String saveMessage() {
		HttpClient httpClient = new DefaultHttpClient(getHttpParams());
		HttpResponse response = null;
		String result = "";
		try {
			HttpPost httppost = new HttpPost(SERVICE_URL+"/saveMessage");
			 List<NameValuePair> postParams = new ArrayList<NameValuePair>();  
			 postParams.add(new BasicNameValuePair("userRecieve", "Children.uit@gmail.com"));   
			 postParams.add(new BasicNameValuePair("userSend", "Admin.uit@gmail.com"));  
			 postParams.add(new BasicNameValuePair("content", "I'm Quý"));
			 Date d= new Date();
			 
			 postParams.add(new BasicNameValuePair("date", d.getTime() +""));  
			 httppost.setEntity(new UrlEncodedFormEntity(postParams)); 

            response = httpClient.execute(httppost);
            result = inputStreamToString(response.getEntity().getContent());
		} catch (Exception e) {
			System.out.println("bug " + e.toString());
		}

		return result;
	}
	
	private static String checkUser() {
		HttpClient httpClient = new DefaultHttpClient(getHttpParams());
		HttpResponse response = null;
		String result = "";
		try {
			HttpPost httppost = new HttpPost(SERVICE_URL+"/check_user");
			 List<NameValuePair> postParams = new ArrayList<NameValuePair>();  
			 postParams.add(new BasicNameValuePair("email", "Children.uit@gmail.com"));    
			 postParams.add(new BasicNameValuePair("password", "children"));
			 httppost.setEntity(new UrlEncodedFormEntity(postParams)); 

            response = httpClient.execute(httppost);
            result = inputStreamToString(response.getEntity().getContent());
		} catch (Exception e) {
			System.out.println("bug " + e.toString());
		}

		return result;
	}
	
	private static String addUserManage() {
		HttpClient httpClient = new DefaultHttpClient(getHttpParams());
		HttpResponse response = null;
		String result = "";
		try {
			HttpPost httppost = new HttpPost(SERVICE_URL+"/add_user_manage");
			 List<NameValuePair> postParams = new ArrayList<NameValuePair>();  
			 User userManage = new User();
			 userManage.setEmail("Parent.uit@gmail.com");
			 userManage.setPassword("parent");
			 String jsonUserManage = new JSONSerializer().exclude("*.class").serialize(userManage);
			 User userBeManage = new User();
			 userBeManage.setEmail("Children.uit@gmail.com");
			 userBeManage.setPassword("children");
			 String jsonUserBeManage = new JSONSerializer().exclude("*.class").serialize(userBeManage);
			 postParams.add(new BasicNameValuePair("userManage", jsonUserManage));   
			 postParams.add(new BasicNameValuePair("userBeManage", jsonUserBeManage));  
			 httppost.setEntity(new UrlEncodedFormEntity(postParams)); 

            response = httpClient.execute(httppost);
            result = inputStreamToString(response.getEntity().getContent());
		} catch (Exception e) {
			System.out.println("bug " + e.toString());
		}

		return result;
	}
	
	private static String getMessage() {
		HttpClient httpClient = new DefaultHttpClient(getHttpParams());
		HttpResponse response = null;
		String result = "";
		try {
			HttpPost httppost = new HttpPost(SERVICE_URL+"/get_message");
			 List<NameValuePair> postParams = new ArrayList<NameValuePair>();  
			 User user = new User();
			 user.setEmail("Parent.uit@gmail.com");
			 user.setPassword("parent");
			 String json = new JSONSerializer().exclude("*.class").serialize(user);
			 postParams.add(new BasicNameValuePair("user", json));   
			 httppost.setEntity(new UrlEncodedFormEntity(postParams)); 
			 //httppost.setHeader("Content-type:", value);

            response = httpClient.execute(httppost);
            
            result = inputStreamToString(response.getEntity().getContent());
   
            List<Message> messages = new JSONDeserializer<List<Message>>().use(null, ArrayList.class).use("values", Message.class)
					.deserialize(result);
            for(Message m : messages) {
            	System.out.println(m.getContent());
            }
            
		} catch (Exception e) {
			System.out.println("bug " + e.toString());
		}

		return result;
	}
	
	private static String saveApp() {
		HttpClient httpClient = new DefaultHttpClient(getHttpParams());
		HttpResponse response = null;
		String result = "";
		try {
			HttpPost httppost = new HttpPost(SERVICE_URL+"/saveApp");
			 List<NameValuePair> postParams = new ArrayList<NameValuePair>();  
			 AppItem appItem = new AppItem();
			 appItem.setLabel("hỏi");
			 appItem.setPackageName("không biết");
			 //appItem.setOwner(null);
			 List<AppItem> appItems = new ArrayList<AppItem>();
			 appItems.add(appItem);
			 String json = new JSONSerializer().exclude("*.class").serialize(appItems);   
			 postParams.add(new BasicNameValuePair("user", "Children1.uit@gmail.com"));
			 postParams.add(new BasicNameValuePair("apps", URLEncoder.encode(json, "UTF-8")));  
			 httppost.setEntity(new UrlEncodedFormEntity(postParams,"UTF-8")); 

            response = httpClient.execute(httppost);
            
            result = inputStreamToString(response.getEntity().getContent());
        //    Response response2 = new JSONDeserializer<Response>().use(null, Response.class)
			//		.deserialize(result);
          //  System.out.println(response2.getStatus().getStatusMessage());
            return result;
		} catch (Exception e) {
			System.out.println("bug " + e.toString());
		}

		return result;
	}
	
	private static String getApp() {
		HttpClient httpClient = new DefaultHttpClient(getHttpParams());
		HttpResponse response = null;
		String result = "";
		try {
			HttpPost httppost = new HttpPost(SERVICE_URL+"/getApp");
			 List<NameValuePair> postParams = new ArrayList<NameValuePair>();  
			 User user = new User();
			 user.setEmail("Parent.uit@gmail.com");
			 user.setPassword("parent");
			 String json = new JSONSerializer().exclude("*.class").serialize(user);
			 postParams.add(new BasicNameValuePair("user", json));   
			 httppost.setEntity(new UrlEncodedFormEntity(postParams)); 
			 //httppost.setHeader("Content-type:", value);

            response = httpClient.execute(httppost);
            
            result = inputStreamToString(response.getEntity().getContent());
            result =  URLDecoder.decode(result, "UTF-8");
		} catch (Exception e) {
			System.out.println("bug " + e.toString());
		}

		return result;
	}
	
	private static String getLockApp() {
		HttpClient httpClient = new DefaultHttpClient(getHttpParams());
		HttpResponse response = null;
		String result = "";
		try {
			HttpPost httppost = new HttpPost(SERVICE_URL+"/getLockApp");
			 List<NameValuePair> postParams = new ArrayList<NameValuePair>();  
			 User user = new User();
			 user.setEmail("Children.uit@gmail.com");
			 user.setPassword("children");
			 String json = new JSONSerializer().exclude("*.class").serialize(user);
			 postParams.add(new BasicNameValuePair("user", json));   
			 httppost.setEntity(new UrlEncodedFormEntity(postParams)); 
			 //httppost.setHeader("Content-type:", value);

            response = httpClient.execute(httppost);
            
            result = inputStreamToString(response.getEntity().getContent());
            result =  URLDecoder.decode(result, "UTF-8");
		} catch (Exception e) {
			System.out.println("bug " + e.toString());
		}

		return result;
	}

	private static String getCallLog() {
		HttpClient httpClient = new DefaultHttpClient(getHttpParams());
		HttpResponse response = null;
		String result = "";
		try {
			HttpPost httppost = new HttpPost(SERVICE_URL+"/getCallLog");
			 List<NameValuePair> postParams = new ArrayList<NameValuePair>();  
			 User user = new User();
			 user.setEmail("Parent.uit@gmail.com");
			 user.setPassword("parent");
			 String json = new JSONSerializer().exclude("*.class").serialize(user);
			 postParams.add(new BasicNameValuePair("user", json));   
			 httppost.setEntity(new UrlEncodedFormEntity(postParams)); 
			 //httppost.setHeader("Content-type:", value);

            response = httpClient.execute(httppost);
            
            result = inputStreamToString(response.getEntity().getContent());
            result =  URLDecoder.decode(result, "UTF-8");
		} catch (Exception e) {
			System.out.println("bug " + e.toString());
		}
		return result;
	}

	
	public static void main(String[] args) throws Exception {
	//	System.out.println(doInBackground(SERVICE_URL));
	//	System.out.println(signup());
	//	System.out.println(checkUser());
		//System.out.println(addUserManage());
		System.out.println(saveMessage());
		//System.out.println(getMessage());
	//	System.out.println(saveApp());
	//	System.out.println(getApp());
	//	System.out.println(getLockApp());
	//	System.out.println(getCallLog());
		
		/*String input = "không biết";
        System.out.println("Original input string from client: " + input);

        String encoded;
        try {
            encoded = URLEncoder.encode(input, "UTF-8");
         
        System.out.println("URL-encoded by client with UTF-8: " + encoded);

        String incorrectDecoded = URLDecoder.decode(encoded, "ISO-8859-1");
        System.out.println("Then URL-decoded by server with ISO-8859-1: " + incorrectDecoded);

        String correctDecoded = URLDecoder.decode(encoded, "UTF-8");
        System.out.println("Server should URL-decode with UTF-8: " + correctDecoded);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	*/
	/*	  String string = "I am what I am hhhhhhhhhhhhhhhhhhhhhhhhhhhhh"
	                + "bjggujhhhhhhhhh"
	                + "rggggggggggggggggggggggggg"
	                + "esfffffffffffffffffffffffffffffff"
	                + "esffffffffffffffffffffffffffffffff"
	                + "esfekfgy enter code here`etd`enter code here wdd"
	                + "heljwidgutwdbwdq8d"
	                + "skdfgysrdsdnjsvfyekbdsgcu"
	                +"jbujsbjvugsduddbdj";

	       System.out.println("after compress:");
	        byte[] compressed = compress(string);
	        System.out.println(compressed);
	        System.out.println("after decompress:");
	        String decomp = decompress(compressed);
	        System.out.println(decomp);
	}
	
	public static byte[] compress(String data) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
		GZIPOutputStream gzip = new GZIPOutputStream(bos);
		gzip.write(data.getBytes());
		gzip.close();
		byte[] compressed = bos.toByteArray();
		bos.close();
		return compressed;
	}
	
	public static String decompress(byte[] compressed) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
		GZIPInputStream gis = new GZIPInputStream(bis);
		BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		gis.close();
		bis.close();
		return sb.toString();*/
	}
}
