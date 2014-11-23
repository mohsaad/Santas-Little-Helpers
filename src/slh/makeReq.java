package ebayFinder;

import java.io.*;
import java.net.*;


public class makeReq 
{
	private StringBuilder total;
	
	public makeReq()
	{
		
	}
	
	public static void main(String[] args)
	{
		makeReq test = new makeReq();
		System.out.println(test.requestItems("mario"));
		
	}
	
	public StringBuilder requestItems(String query)
	{
		query  = turnIntoUrl(query);
		
		StringBuilder totalQ = new StringBuilder();
		totalQ.append("http://svcs.ebay.com/services/search/FindingService/v1");;
		totalQ.append("?OPERATION-NAME=findItemsByKeywords");
		totalQ.append("&SECURITY-APPNAME=Mohammad-2aac-45bb-bd22-49867c29d6c6");
		totalQ.append("&RESPONSE-DATA-FORMAT=JSON");
		totalQ.append("&callback=cb_findItemsByKeywords");
		totalQ.append("&REST-PAYLOAD");
		totalQ.append("&keywords=");
		totalQ.append(query);
		totalQ.append("&paginationInput.entriesPerPage=3");
		
		//apply filters
		
		try
		{
			URLConnection connection = new URL(totalQ.toString()).openConnection();
			//connection.setRequestProperty("Accept-Charset", charset);
			InputStream response = connection.getInputStream();
			
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null)
			{
				sb.append(line);
			}
			rd.close();
			return new StringBuilder(sb.toString());
			
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return new StringBuilder();
		}

	}


	private String turnIntoUrl(String query)
	{
		try{
			return URLEncoder.encode(query.toString(), "UTF-8");
		}
		catch(UnsupportedEncodingException e){
			return "";
		}
	}
	
	
	
	
}

