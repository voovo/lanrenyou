/**
 * 
 */
package mybatis.framework.components.code;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3SimpleImpl;

/**
 *
 */
public class IntrospectedTableImpl extends IntrospectedTableMyBatis3SimpleImpl
{
	String daoType = "dao";
	@Override
	public void initialize()
	{
		String dir = System.getProperty("user.dir");
		if (StringUtils.isEmpty(dir))
		{
			dir = context.getProperty("generate.src.dir");
		} else {
			File f = new File(dir, "target");
			if (!f.exists())
			{
				f.mkdirs();
			}
			dir = f.getAbsolutePath();
		}
		if (!StringUtils.isEmpty(dir))
		{
			System.out.println("生成代码目录: " + dir);
			getContext().getJavaClientGeneratorConfiguration().setTargetProject(dir);
			getContext().getJavaModelGeneratorConfiguration().setTargetProject(dir);
			getContext().getSqlMapGeneratorConfiguration().setTargetProject(dir);
		}

		String targetPackage = context.getProperty("generate.src.package");
		if (!StringUtils.isEmpty(targetPackage))
		{
			System.out.println("包名: " + targetPackage);
			getContext().getJavaClientGeneratorConfiguration().setTargetPackage(targetPackage + ".dao"); // dao
			getContext().getJavaModelGeneratorConfiguration().setTargetPackage(targetPackage + ".model"); // model
			getContext().getSqlMapGeneratorConfiguration().setTargetPackage(targetPackage + ".dao.mapper"); // mapper.xml
		}
		
		daoType = context.getProperty("generate.dao.type");
		
		super.initialize();
	}
	
	@Override
	protected AbstractJavaClientGenerator createJavaClientGenerator()
	{
		if ("mapper".equals(daoType))
		{
			return super.createJavaClientGenerator();
		}
		else
		{
			return new JavaClientGenerator();
		}
	}
	
	@Override
	protected void calculateJavaClientAttributes()
	{
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return;
        }
        super.calculateJavaClientAttributes();

		if (!"mapper".equals(daoType))
		{
	        StringBuilder sb = new StringBuilder();

	        sb.setLength(0);
	        sb.append(calculateJavaClientInterfacePackage());
	        sb.append(".I");
	        sb.append(fullyQualifiedTable.getDomainObjectName());
	        sb.append("Dao"); //$NON-NLS-1$
	        setMyBatis3JavaMapperType(sb.toString());
		}
        
    }
	
	@Override
	protected void calculateJavaModelGenerators(List<String> warnings,
			ProgressCallback progressCallback)
	{
//		super.calculateJavaModelGenerators(warnings, progressCallback);

        AbstractJavaGenerator javaGenerator = new JavaModelGenerator();
        initializeAbstractGenerator(javaGenerator, warnings,
                progressCallback);
        javaModelGenerators.add(javaGenerator);
	}

    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();

        if (xmlMapperGenerator != null) {
            Document document = xmlMapperGenerator.getDocument();
            GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                getMyBatis3XmlMapperFileName(), getMyBatis3XmlMapperPackage(),
                context.getSqlMapGeneratorConfiguration().getTargetProject(),
                false, context.getXmlFormatter());
            if (context.getPlugins().sqlMapGenerated(gxf, this)) {
                answer.add(gxf);
            }
        }

        return answer;
    }
}
