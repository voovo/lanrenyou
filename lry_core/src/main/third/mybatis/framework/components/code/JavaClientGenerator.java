/**
 * 
 */
package mybatis.framework.components.code;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.SimpleJavaClientGenerator;
import org.mybatis.generator.config.PropertyRegistry;

/**
 * 生成Dao
 */
public class JavaClientGenerator extends SimpleJavaClientGenerator
{
	String base = null;
	
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
                introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getMyBatis3JavaMapperType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);

        String rootInterface = introspectedTable
            .getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface = context.getJavaClientGeneratorConfiguration()
                .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(
                    rootInterface);
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
        }
        
//        addDeleteByPrimaryKeyMethod(interfaze);
//        addInsertMethod(interfaze);
//        addSelectByPrimaryKeyMethod(interfaze);
//        addSelectAllMethod(interfaze);
//        addUpdateByPrimaryKeyMethod(interfaze);

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().clientGenerated(interfaze, null,
                introspectedTable)) {
            answer.add(interfaze);
        }
        
        /** 接口 */
        FullyQualifiedJavaType voType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        FullyQualifiedJavaType daoSuper = new FullyQualifiedJavaType("mybatis.framework.core.dao.IValueObjectDao");
        daoSuper.addTypeArgument(voType);
        interfaze.addSuperInterface(daoSuper);
        interfaze.addImportedType(daoSuper);
        interfaze.addImportedType(voType);
        
//        daoImpl.addAnnotation("@Repository");
//        daoImpl.addImportedType("org.springframework.stereotype.Repository");
        
        List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }

        return answer;
    }

	@Override
	public List<CompilationUnit> getExtraCompilationUnits()
	{
		progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
				introspectedTable.getFullyQualifiedTable().toString()));
		CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();

        FullyQualifiedJavaType interfazeType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
        
        String voClass = introspectedTable.getBaseRecordType();
        base = ServiceGenerator.getBasePackage(voClass);
        String model = table.getDomainObjectName();
		if (model.endsWith("VO") || model.endsWith("Vo"))
		{
			model = model.substring(0, model.length() - 2);
		}

        // 实现 
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(base + ".dao.impl." + model + "DaoImpl"); //
        TopLevelClass daoImpl = new TopLevelClass(type);
        daoImpl.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(daoImpl);
        
        daoImpl.addSuperInterface(interfazeType);
        daoImpl.addImportedType(interfazeType);
        daoImpl.addAnnotation("@Repository");
        daoImpl.addImportedType("org.springframework.stereotype.Repository");
        
        /**
         * 
         */
        String baseDao = getContext().getProperty("generate.dao.base");
        if (StringUtils.isEmpty(baseDao))
		{
        	baseDao = "mybatis.framework.core.dao.BaseDao"; //
		}
        FullyQualifiedJavaType superDao = new FullyQualifiedJavaType(baseDao); //
        superDao.addTypeArgument(new FullyQualifiedJavaType(voClass));
        daoImpl.setSuperClass(superDao);
        daoImpl.addImportedType(superDao);

//		Field namespace = new Field();
//		namespace.setVisibility(JavaVisibility.PRIVATE);
//		namespace.setType(FullyQualifiedJavaType.getStringInstance());
//		namespace.setName(DAO_NAMESPACE_FIELD);
//		namespace.setStatic(true);
////		field.setInitializationString("\"" + interfazeType.getFullyQualifiedName() + "\"");
//		namespace.setInitializationString("" + interfazeType.getShortName() + ".class.getName()");
//		daoImpl.addField(namespace);

//		Field component = new Field();
//		component.setVisibility(JavaVisibility.PRIVATE);
//		component.setType(FullyQualifiedJavaType.getStringInstance());
//		component.setName(DAO_COMPONENT_FIELD);
//		component.setStatic(true);
//		component.setInitializationString("\"" + getConfig(DAO_COMPONENT) + "\"");
//		daoImpl.addField(component);
//
//		Field environment = new Field();
//		environment.setVisibility(JavaVisibility.PRIVATE);
//		environment.setType(FullyQualifiedJavaType.getStringInstance());
//		environment.setName(DAO_ENVIRONMENT_FIELD);
//		environment.setStatic(true);
//		environment.setInitializationString("\"" + getConfig(DAO_ENVIRONMENT) + "\"");
//		daoImpl.addField(environment);
		
        addDefaultConstructor(daoImpl, interfazeType);
        
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        answer.add(daoImpl);
        
		return answer;
	}
	
	@Override
	public AbstractXmlGenerator getMatchedXMLGenerator()
	{
        return new XMLMapperGenerator();
	}

    protected void addDefaultConstructor(TopLevelClass topLevelClass, FullyQualifiedJavaType interfazeType) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(topLevelClass.getType().getShortName());
//        method.addBodyLine("super("+DAO_COMPONENT_FIELD+", "+DAO_ENVIRONMENT_FIELD+", " + interfazeType.getShortName() + ".class.getName());"); //$NON-NLS-1$
        method.addBodyLine("super(" + interfazeType.getShortName() + ".class.getName());"); //$NON-NLS-1$
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

}
