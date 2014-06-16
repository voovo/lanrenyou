/**
 * 
 */
package mybatis.framework.components.code;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.mybatis3.model.SimpleModelGenerator;

/**
 *
 */
public class JavaModelGenerator extends SimpleModelGenerator
{
	FullyQualifiedJavaType integerType = new FullyQualifiedJavaType(Integer.class.getName());
	
	@Override
	public Field getJavaBeansField(IntrospectedColumn introspectedColumn)
	{

        FullyQualifiedJavaType fqjt = introspectedColumn.getFullyQualifiedJavaType();
        String property = introspectedColumn.getJavaProperty();

        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(fqjt);
        field.setName(property);
        String defaultValue = introspectedColumn.getDefaultValue();
        if (defaultValue != null && !"".equals(defaultValue))
		{
			if (fqjt.equals(FullyQualifiedJavaType.getStringInstance()))
			{
		        field.setInitializationString("\"" + defaultValue + "\"");
			} else if (fqjt.equals(FullyQualifiedJavaType.getIntInstance()) || fqjt.equals(integerType)) {

		        field.setInitializationString(defaultValue);
			}
		}
        context.getCommentGenerator().addFieldComment(field,
                introspectedTable, introspectedColumn);

        return field;
	}

}
