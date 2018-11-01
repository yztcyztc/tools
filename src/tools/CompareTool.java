package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class CompareTool {
	
	File file1 = new File("D:/var/new.txt");
	File file2 = new File("D:/var/old.txt");
	
	Long filelength = file1.length();
	String encode = "UTF-8";
	
	//读取两个文件，比较输出1中不包含2的行
	public void diff(){		
		String s = "";
		ArrayList<String> list1 = new ArrayList<String>();
		ArrayList<String> list2 = new ArrayList<String>();
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(file1));
			while((s = br1.readLine())!=null)
				list1.add(s.toLowerCase().replace(" ", ""));
			br1.close();
			BufferedReader br2 = new BufferedReader(new FileReader(file2));
			while((s = br2.readLine())!=null)
				list2.add(s.toLowerCase().replace(" ", ""));
			br1.close();
			br2.close();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println("length-new:--"+list1.size());
		System.out.println("length-old:--"+list2.size());
		System.out.println("list-old:");
		for(String a:list2){
			if(list1.contains(a)){
				list1.remove(a);
			}else{
				System.out.println(a);
			}
		}
		System.out.println("list-new:");
		System.out.println("length-left:--"+list1.size());
		for(String a:list1){
			System.out.println(a);
		}		
	}
	
	//查找文件中相同的行
	public void clean(){
		ArrayList<String> list1 = new ArrayList<String>();
		String s;
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(file1));
			while((s = br1.readLine())!=null)
				list1.add(s.toLowerCase().replace(" ", ""));
			br1.close();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		for(;list1.size()>0;){
			String st = list1.get(0);
			list1.remove(0);
			//System.out.println("lenth:"+list1.size());
			if(list1.contains(st))
				System.out.println(st);
		}
	}
	
	//一次性读取文件内容
	public void read(){
		byte[] filecontent = new byte[filelength.intValue()];		 
		try {  
	        FileInputStream in = new FileInputStream(file1); 
	        in.read(filecontent);  
	        in.close();  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
		try {
			System.out.println(new String(filecontent,encode));
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
