/**
 * 
 */
import java.io.IOException;
import java.util.Date;

import tools.*;
/**
 * @author Administrator
 *
 */
public class Tools {

	static GetRegistryTags tool = new GetRegistryTags();
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO 自动生成的方法存根
		CompareTool ct = new CompareTool();
		GetImageName gin = new GetImageName();
		System.out.println(new Date());
		gin.getNames();
		ct.diff();
		//gin.getEnv();
		//gin.getPorts();
		//gin.getConfig();
		//ct.clean();
//		String names[] = tool.getNames("alphine  alpine  app-manage  auth_server  openresty  tomcat  tomcat-conf-down  tomcat-docker  tomcat-test");
		//tool.getTags(names);
		//for(String n:names
		//System.out.println(n);
	}

}
