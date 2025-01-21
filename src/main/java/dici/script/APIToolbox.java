package dici.script;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The APIToolbox class provides utility methods for interacting with APIs.
 */
public class APIToolbox {

	/**
	 * Retrieves the average price per square meter for real estate transactions in a given area.
	 *
	 * This method fetches data from an external API, processes the JSON response to extract relevant
	 * information, and calculates the average price per square meter based on the provided INSEE code.
	 *
	 * @param codeInsee The INSEE code of the area for which to retrieve the price data.
	 * @return The average price per square meter for the specified area, or 0 if no data is available or an error occurs.
	 */
	public static float getPriceM2 ( String codeInsee ) {
		float totalSum   = 0;
		int   totalCount = 0;
	
		try {
			String nextPageUrl = "https://apidf-preprod.cerema.fr/dvf_opendata/mutations/?anneemut_max=2021&anneemut_min=2021&code_insee=" + codeInsee + "&page_size=500";
	
			while ( nextPageUrl != null ) {
				URL url = new URL ( nextPageUrl );
				HttpURLConnection conn = ( HttpURLConnection ) url.openConnection ( );
				conn.setRequestMethod   ( "GET"                        );
				conn.setRequestProperty ( "Accept", "application/json" );
	
				int responseCode = conn.getResponseCode ( );
				if ( responseCode == 302 ) {
					String newUrl = conn.getHeaderField ( "Location" );
					if ( newUrl != null ) {
						nextPageUrl = newUrl;
						conn.disconnect ( );
						continue;
					} else {
						throw new RuntimeException ( "Redirection with no location header" );
					}
				} else if ( responseCode != 200 ) {
					throw new RuntimeException ( "Failed: HTTP error code: " + responseCode );
				}
	
				try ( InputStreamReader reader = new InputStreamReader ( conn.getInputStream ( ) ) ) {
					JsonElement jsonElement = JsonParser.parseReader ( reader );
					JsonObject  jsonObject  = jsonElement.getAsJsonObject ( );
	
					if ( jsonObject.has ( "results" ) ) {
						JsonArray jsonArray = jsonObject.getAsJsonArray ( "results" );
	
						for ( JsonElement element : jsonArray ) {
							JsonObject obj = element.getAsJsonObject ( );
							if ( !obj.get ( "valeurfonc" ).isJsonNull ( ) && 
							     !obj.get ( "sbati"      ).isJsonNull ( ) ) {
								float price   = obj.get ( "valeurfonc" ).getAsFloat ( );
								float surface = obj.get ( "sbati"      ).getAsFloat ( );
		
								if ( surface > 0 ) {
									totalSum += price / surface;
									totalCount++;
								}
							}
						}
					}
	
					nextPageUrl = jsonObject.has ( "next" ) && !jsonObject.get ( "next" ).isJsonNull ( ) ? jsonObject.get ( "next" ).getAsString ( ) : null;
				}
	
				conn.disconnect ( );
			}
	
			if ( totalCount > 0 ) {
				return totalSum / totalCount;
			} else {
				return 0;
			}
		} catch ( Exception e ) {
			e.printStackTrace ( );
			return 0;
		}
	}
	
}