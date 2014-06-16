package com.lanrenyou.test.JunitBase;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mybatis.framework.core.model.IValueObject;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/demoApplicationContext.xml" })
public abstract class Junit4Base {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());


	public void dump(Object o)
	{
		System.out.println("object is null ? " + (o == null));
		Map<String,String> msp = beanToMap(o);
		for(String s:msp.keySet()){
			System.out.print("\tkey:"+s);
			System.out.println("  "+msp.get(s));
		}
		System.out.println("-------------------------------------------");
	}
	public void dump(Map map)
	{
		System.out.println("Map is null ? " + (map == null));
		for (Iterator iterator = map.values().iterator(); iterator.hasNext();)
		{
			Object c = iterator.next();
			if (c instanceof IValueObject)
			{
				dump(c);
			}else {
				System.out.println("v:" + c);
			}
		}
		System.out.println("-------------------------------------------");
	}

	public Map<String, String> beanToMap(Object bean)
	{
		Map<String, String> returnMap = new HashMap<String, String>();
		if (bean != null)
		{
			Class c = bean.getClass();
			Field[] fields = c.getDeclaredFields();
			for (Field field : fields)
			{
				try
				{
					if (Modifier.isFinal(field.getModifiers())
							|| Modifier.isStatic(field.getModifiers()))
					{
						continue;
					}
					String propertyName = field.getName();
					field.setAccessible(true);
					Object value = field.get(bean);
					if (value != null)
					{
						if (field.getType() == Date.class)
						{
							value = String.valueOf(((Date) value).getTime());
						}
						returnMap.put(propertyName, String.valueOf(value));
					}
				}
				catch (Exception e)
				{
					System.err.println(field.getName() + "转化出错");
				}
			}
		}
		return returnMap;
	}
}
