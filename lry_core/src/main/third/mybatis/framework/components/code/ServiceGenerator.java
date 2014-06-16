/**
 * 
 */
package mybatis.framework.components.code;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.Context;

/**
 *
 */
public class ServiceGenerator extends AbstractJavaGenerator
{
	String servicePackage = null;
	
	
	/**
	 * 
	 */
	public ServiceGenerator(Context context, IntrospectedTable introspectedTable)
	{
		setContext(context);
		setIntrospectedTable(introspectedTable);
		String targetPackage = context.getProperty("generate.src.package");
		if (!StringUtils.isEmpty(targetPackage))
		{
			servicePackage = targetPackage + ".service";
		}
	}
	/**
	 * 
	 */
	@Override
	public List<CompilationUnit> getCompilationUnits()
	{
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();

        CommentGenerator commentGenerator = context.getCommentGenerator();
        
        // 得到VO类名
        String voClass = introspectedTable.getBaseRecordType();
        if (StringUtils.isEmpty(servicePackage))
		{
        	servicePackage = getServicePackageFromVO(voClass);
		}
        String model = table.getDomainObjectName();
		if (model.endsWith("VO") || model.endsWith("Vo"))
		{
			model = model.substring(0, model.length() - 2);
		}
        
        // 接口 
        FullyQualifiedJavaType typeInter = new FullyQualifiedJavaType(servicePackage + ".I" + model + "Service"); //
//        type.getPackageName()
        Interface interfaze = new Interface(typeInter);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);

//        addSuperClass(serviceInter, true);
//        addMethods(serviceInter, true);
        FullyQualifiedJavaType voType = new FullyQualifiedJavaType(voClass);
        FullyQualifiedJavaType daoSuper = new FullyQualifiedJavaType("mybatis.framework.core.service.IValueObjectService");
        daoSuper.addTypeArgument(voType);
        interfaze.addSuperInterface(daoSuper);
        interfaze.addImportedType(daoSuper);
        interfaze.addImportedType(voType);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        answer.add(interfaze);

        CompilationUnit serviceImpl = getServiceImpl(interfaze, model, voClass);
		if (serviceImpl != null)
		{
			answer.add(serviceImpl);
		}
		
        return answer;
	}

	/**
	 * @param model 
	 * @param voClass 
	 * @param typeInter
	 * @return
	 */
	private CompilationUnit getServiceImpl(Interface interfaze, String model, String voClass)
	{
        CommentGenerator commentGenerator = context.getCommentGenerator();
        // 实现 
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(servicePackage + ".impl." + model + "ServiceImpl"); //
        TopLevelClass serviceImpl = new TopLevelClass(type);
        serviceImpl.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(serviceImpl);
        
        serviceImpl.addSuperInterface(interfaze.getType());
        serviceImpl.addImportedType(interfaze.getType());
        serviceImpl.addAnnotation("@Service");
        serviceImpl.addImportedType("org.springframework.stereotype.Service");
        serviceImpl.addImportedType("org.springframework.beans.factory.annotation.Autowired");
        
        // super
        FullyQualifiedJavaType superService = new FullyQualifiedJavaType("mybatis.framework.core.service.BaseVOService"); //
        superService.addTypeArgument(new FullyQualifiedJavaType(voClass));
        serviceImpl.setSuperClass(superService);
        serviceImpl.addImportedType(superService);
        
        Field mapper = getMapperField();
        serviceImpl.addField(mapper);
        serviceImpl.addImportedType(mapper.getType());

		return serviceImpl;
	}

	/**
	 * @param introspectedTable
	 * @return
	 */
	private Field getMapperField()
	{
		FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
		String dao = fqjt.getShortName();
		if (dao.length() > 2 && dao.startsWith("I") && Character.isUpperCase(dao.charAt(1)))
		{
			dao = dao.substring(1);
		}
		
		StringBuilder name = new StringBuilder(dao);
		name.setCharAt(0, Character.toLowerCase(name.charAt(0)));

		Field field = new Field();
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setType(fqjt);
		field.setName(name.toString());
		field.addAnnotation("@Autowired");

		return field;
	}

	/**
	 * @param vo
	 * @return
	 */
	private static String getServicePackageFromVO(String vo)
	{
		return getBasePackage(vo) + ".service";
	}

	public static String getBasePackage(String vo)
	{
		int i = vo.lastIndexOf(".");
		String packageName = vo.substring(0, i); // vo package name
		i = packageName.lastIndexOf("."); 
		packageName = packageName.substring(0, i); // service package name
		
		return packageName;
	}

}
