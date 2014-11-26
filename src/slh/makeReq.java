package slh;

import java.util.ArrayList;
import java.io.*;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.net.*;
import com.google.gson.*;
import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

public class makeReq 
{
	public class EbayItem
	{
		public String name;
		public String url;
		public String img_url;
		public String price;
		
		public EbayItem(String name, String url, String img_url, String price)
		{
			this.name = name;
			this.url = url;
			this.img_url = img_url;
			this.price = price;
		}
	}
	
	public makeReq()
	{
		
	}
	
	public String getProducts(String query)
	{
		String temp1 = requestItems(query).toString();
		return makeGSON(convertToItems(temp1));
	}
	
	public static void main(String[] args)
	{
		makeReq test = new makeReq();
		System.out.println(test.getProducts("zelda"));
		
	}
	
	public StringBuilder requestItems(String query)
	{
		query  = turnIntoUrl(query);
		
		StringBuilder totalQ = new StringBuilder();
		totalQ.append("http://svcs.ebay.com/services/search/FindingService/v1");;
		totalQ.append("?OPERATION-NAME=findItemsByKeywords");
		totalQ.append("&SECURITY-APPNAME=Mohammad-2aac-45bb-bd22-49867c29d6c6");
		totalQ.append("&RESPONSE-DATA-FORMAT=XML");
		//totalQ.append("&callback=cb_findItemsByKeywords");
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
			
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(response));
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
	
	public ArrayList<EbayItem> convertToItems(String xmlToConv)
	{
		ArrayList<EbayItem> prods = new ArrayList<EbayItem>();
		try
		{
			// build XML doc
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			ByteArrayInputStream tempStream = new ByteArrayInputStream(xmlToConv.getBytes("utf-8"));
			InputSource tempIn = new InputSource(tempStream);
			Document dom = db.parse(tempIn);		
			tempStream.close();
			
			

			String temp_name; 
			String temp_url;
			String temp_img;
			String temp_price;
			
			//get all info for products
			Element doc = dom.getDocumentElement();
			NodeList names = doc.getElementsByTagName("title"); 
			NodeList urls = doc.getElementsByTagName("viewItemURL");
			NodeList imgs = doc.getElementsByTagName("galleryURL");
			NodeList prices = doc.getElementsByTagName("currentPrice");
			//System.out.println(imgs.item(0).getTextContent());
			
			for(int i = 0; i < prices.getLength(); i++)
			{
				temp_name = names.item(i).getTextContent();
				temp_url = urls.item(i).getTextContent();
				temp_img = imgs.item(i).getTextContent();
				temp_price = prices.item(i).getTextContent();
				prods.add(new EbayItem(temp_name, temp_url, temp_img, temp_price));
			}
		}
		catch(Exception e)
		{
			System.out.println("Error\n");
			e.printStackTrace();
			System.exit(-1);
		}
		return prods;
	}
	
	public String makeGSON(ArrayList<EbayItem> products)
	{
		if(products.size() <= 0)
			return "";
		
		Gson gson = new Gson();
		return gson.toJson(products).toString();
	}
	
}

