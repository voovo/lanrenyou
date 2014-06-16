/**
 * 
 */
package mybatis.framework.components.code;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.generator.api.dom.java.Interface;

/**
 *
 */
public class MapperUtil
{
	
	private Map<String, Interface> daoCache = new HashMap<String, Interface>();
	private MapperUtil instance = new MapperUtil();
	
	/**
	 * 
	 */
	private MapperUtil()
	{
	}
	
	/**
	 * @return the instance
	 */
	public MapperUtil getInstance()
	{
		return instance;
	}
	
	public void registerDao(String baseRecordType, Interface interfaze)
	{
		if (daoCache.containsKey(baseRecordType))
		{
			//System.out.println("DAO 重复: " + baseRecordType);
		}
		daoCache.put(baseRecordType, interfaze);
	}
	
	public Interface getDaoInterface(String baseRecordType)
	{
		return daoCache.get(baseRecordType);
	}
}
