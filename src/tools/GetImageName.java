package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class GetImageName {
	//用于获取marathon里所有镜像的名称
	
	public JSONArray getApps(){
		JSONArray jsonarry = null;
		try {
//		URL url = new URL("http://172.20.17.4:8080/v2/apps/dcee/*");	
		URL url = new URL("http://20.12.17.153:8080/v2/apps/dcee/*");	
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("GET");
		System.out.println(conn.getResponseCode());		
		InputStream in = conn.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
		jsonarry = JSONObject.fromObject(br.readLine()).getJSONArray("*");
		//System.out.println(jsonarry.size());		
		//System.out.println(json.getJSONArray("*").getJSONObject(0).getJSONObject("container").getJSONObject("docker").getString("image"));
		//System.out.println(br.readLine().length());
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return jsonarry;
		
	}
	public void getNames(){
		JSONArray jsonarry = this.getApps();
		System.out.println("num:"+jsonarry.size());
		StringBuffer sb = new StringBuffer();
		String tmp = null;
		for(int i = 0 ;i < jsonarry.size();i++){
			String image = jsonarry.getJSONObject(i).getJSONObject("container").getJSONObject("docker").getString("image");
			if(!image.contains("-lb")){
				tmp = image.split("000/")[1];
				sb.append(tmp+"\n");
				System.out.println(tmp);
			}
		}
		sb.deleteCharAt(sb.length()-1);//去掉最后一个\n
		creatFile("D:/var", "new.txt", sb.toString());
	}
	public void getPorts(){
		JSONArray jsonarry = this.getApps();
		//String port = null;
		for(int i = 0 ;i < jsonarry.size();i++){
			JSONObject app = jsonarry.getJSONObject(i);
			StringBuffer sb = new StringBuffer();
			String appName = app.getString("id").split("/")[2];
			sb.append(appName+":");
			JSONArray ports = app.getJSONObject("container").getJSONObject("docker").getJSONArray("portMappings");
			for(int j = 0;j<ports.size();j++){
				String port = ports.getJSONObject(j).getString("servicePort");
				sb.append("--"+port);
			}
			System.out.println(sb.toString());
		}
		
	}
	public void getEnv(){
		JSONArray jsonarry = this.getApps();
		//System.out.println(jsonarry.getJSONObject(0).getJSONObject("env"));
		for(int i = 0 ;i < jsonarry.size();i++){
			JSONObject app = jsonarry.getJSONObject(i);
			JSONObject envs = app.getJSONObject("env");
			Iterator<?> it = envs.keys();
			String appName = app.getString("id").split("/")[2];
			
			//System.out.println("----------"+app.getString("id").split("/")[2]+"-------------");
			StringBuffer sb = new StringBuffer();
			while(it.hasNext()){
				String key = (String)it.next();
				if(envs.getString(key).contains("eos"))
					System.out.println("eos-----:"+envs.getString(key));
				sb = sb.append(key+"="+envs.getString(key)+'\n');
				//System.out.println(sb.toString());
			}
			creatFile("D:/envs",appName+".txt", sb.toString().replaceAll("172.20.17.4", "192.168.33.101"));
		}
	}
	
	public void getConfig(){
		JSONArray jsonarry = this.getApps();
		//System.out.println(jsonarry.getJSONObject(0).getJSONObject("env"));
		for(int i = 0 ;i < jsonarry.size();i++){
			JSONObject app = jsonarry.getJSONObject(i);
			JSONObject envs = app.getJSONObject("env");
			Iterator it = envs.keys();
			String appName = app.getString("id").split("/")[2];
			
			//System.out.println("----------"+app.getString("id").split("/")[2]+"-------------");
			StringBuffer sb = new StringBuffer();
			sb.append(app.toString());
			creatFile("D:/envs",appName+".txt", sb.toString().replaceAll("172.20.26.113", "192.168.33.101"));
		}
	}
	
	public void creatFile(String path,String name,String data){
		File file = new File(path,name);
		try {		
			if(!file.exists()){
				//file.mkdirs();
				file.createNewFile();
			}		
			FileWriter fw = new FileWriter(file);
			fw.append(data);
			fw.flush();
			fw.close();
		}catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
		}
	}
}
