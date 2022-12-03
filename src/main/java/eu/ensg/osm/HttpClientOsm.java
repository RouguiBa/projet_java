package eu.ensg.osm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;


public class HttpClientOsm {
	
	/** OverPass API URL. */
	private static final String URL_OVERPASS_API_1 = "http://overpass-api.de/api/interpreter";
	private static final String URL_OVERPASS_API_2 = "https://lz4.overpass-api.de/api/interpreter";
	private static final String URL_OVERPASS_API_3 = "https://z.overpass-api.de/api/interpreter";
	
	/**
	 * @see http://wiki.openstreetmap.org/wiki/Overpass_API/Language_Guide
	 * Example : way(50.746,7.154,50.748,7.157);out body;
	 * 
	 * @param data : 
	 *    param request in UTF-8 to send.
	 * @return 
	 * @throws IOException 
	 */
	
	public static String tryRequest(String data, String baseURL)throws IOException{
		StringBuffer response = new StringBuffer();
		String urlTxt = baseURL + "?data=" + URLEncoder.encode(data, "UTF-8");
		
		URL url = new URL(urlTxt);
	    URLConnection urlConn = url.openConnection();
	    // urlConn.setRequestProperty("Accept-Charset", "UTF-8");
	    
	    // Get connection inputstream
	    InputStream is = urlConn.getInputStream();

	    BufferedReader s = new BufferedReader(new InputStreamReader(is));
	    String line = s.readLine();
	    while (line != null) {
	    	response.append(line);
	    	line = s.readLine();
	    }
	    s.close();
		return response.toString();
	}
	
	public static String getOsmXML(String data) throws IOException {
		String response = null;
		try {
			response = tryRequest(data, URL_OVERPASS_API_1);
		} catch (IOException e) {
			//e.printStackTrace();	
			try {
				response = tryRequest(data, URL_OVERPASS_API_2);
			} catch (IOException e1) {
			//e1.printStackTrace();
				try {
					tryRequest(data,URL_OVERPASS_API_3);
				} catch (IOException e2){
					e2.printStackTrace();
				}
			}
		}
		return response;
	}
}

