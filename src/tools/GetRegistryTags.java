package tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetRegistryTags {
	public String[] getNames(String names){
		System.out.println(names.replaceAll("  ", " "));
		return names.replaceAll("  ", " ").split(" ");
	}
	public void getTags(String name[]) throws IOException{
		for(String n : name){
			URL url = new URL("http://10.3.15.191:5000/v2/base/"+n+"/tags/list");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setAllowUserInteraction(true);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			int code = conn.getResponseCode();
			if(code != 200){
				System.out.println(code+"/error image:"+n);
				continue;
			}			
			InputStream ins = conn.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = ins.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			String jsonString = baos.toString();
//			System.out.print(jsonString);
//			JSONObject jsonObject = JSONObject.fromObject(jsonString);
//			JSONArray jsonArray = jsonObject.getJSONArray("tags");
//			System.out.println(jsonArray.toString());
			baos.close();
			ins.close();
			conn.disconnect();
			int last = jsonString.lastIndexOf("\"");
			int first = jsonString.indexOf("[")+2;
			String tags = jsonString.substring(first, last);
			String tag[] = tags.split("\",\"");
			for(String nn:tag)
				System.out.println("10.3.15.191:5000/base/"+n+":"+nn);
		}			
	}
}
