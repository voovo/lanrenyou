package mybatis.framework.core.service;


import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import mybatis.framework.core.dao.IValueObjectDao;
import mybatis.framework.core.model.IValueObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Service接口抽象实现,主要用来获得Dao对象

 *
 * @param <V>
 */
public abstract class BaseService<V extends IValueObject> implements IValueObjectService<V>
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	BaseService()
	{
	}
	
	
	/**
	 * 返回dao
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected final IValueObjectDao<V> getDao()
	{
		if (dao != null)
			return dao;
		/**
		 * 通过解析field获得DAO
		 */
		Class targetC = getGenericClass(getClass(), 0);
		if (targetC == Object.class)
			return dao;

		try
		{
			Field[] fields = getClass().getDeclaredFields();
			if (fields != null)
			{
				int len = fields.length;
				for (int i = 0; i < len; i++)
				{
					Field field = fields[i];

					Object o = null;
					try
					{
						field.setAccessible(true);
						o = field.get(this);
					}
					catch (Exception e)
					{
						continue;
					}
					if (!useObject(o, targetC))
						continue;
					dao = (IValueObjectDao) o;
					break;
				}
			}
		}
		catch (Exception e)
		{
			logger.info("Can not get bean info", e);
			return dao;
		}
		
		return dao;
	}

	private boolean useObject(Object obj, Class targetC)
	{
		if (obj instanceof IValueObjectDao)
		{
			Class clazz[] = obj.getClass().getInterfaces();
			Class arr[] = clazz;
			int len = arr.length;
			for (int i = 0; i < len; i++)
			{
				Class interClazz = arr[i];
				java.lang.reflect.Type genType[] = interClazz.getGenericInterfaces();
				for (int j = 0; j < genType.length; j++)
				{
					java.lang.reflect.Type interGenType = genType[j];
					if ((interGenType instanceof ParameterizedType)
							&& ((ParameterizedType) interGenType).getActualTypeArguments()[0] == targetC)
						return true;
				}

			}

		}
		return false;
	}
	
	private IValueObjectDao<V> dao;

	private Class getGenericClass(Class clazz, int index)
        throws IndexOutOfBoundsException
    {
        java.lang.reflect.Type genType;
        for(genType = clazz.getGenericSuperclass(); !(genType instanceof ParameterizedType); genType = clazz.getGenericSuperclass())
        {
            if(clazz == java.lang.Object.class)
                return clazz;
            clazz = clazz.getSuperclass();
        }

        java.lang.reflect.Type params[] = ((ParameterizedType)genType).getActualTypeArguments();
        if(index >= params.length || index < 0)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("Index: ").append(index).append(", Size of Parameterized Type: ").append(params.length).toString());
        else
            return (Class)params[index];
    }

}
