/**
 * 
 */
package mybatis.framework.components.code;

import java.sql.Types;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 *
 */
public class JavaTypeResolverImpl extends JavaTypeResolverDefaultImpl
{
	/**
	 * 
	 */
	public JavaTypeResolverImpl()
	{
		super();
		
		// 更改TINYINT 映射为int
        typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT", //$NON-NLS-1$
                new FullyQualifiedJavaType(int.class.getName())));
	}

}
