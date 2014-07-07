package mybatis.framework.components.code;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.PropertyRegistry;

public class GeneratorCodePlugin extends PluginAdapter {
	private List<String> mapperResources = new ArrayList<String>();

	String targetPackage = null;

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(
			IntrospectedTable introspectedTable) {
		List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();

		AbstractJavaGenerator serviceGenerator = new ServiceGenerator(context,
				introspectedTable);

		List<CompilationUnit> compilationUnits = serviceGenerator
				.getCompilationUnits();
		for (CompilationUnit compilationUnit : compilationUnits) {
			GeneratedJavaFile gjf = new GeneratedJavaFile(
					compilationUnit,
					context.getJavaModelGeneratorConfiguration()
							.getTargetProject(),
					context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
					context.getJavaFormatter());
			answer.add(gjf);
		}

		String xml_package = introspectedTable.getMyBatis3XmlMapperPackage();
		String xml_name = introspectedTable.getMyBatis3XmlMapperFileName();
		mapperResources.add(xml_package.replace('.', '/') + "/" + xml_name);

		return answer;
	}

	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
		return null;
	}

	@Override
	public List<GeneratedXmlFile> contextGenerateAdditionalXmlFiles() {
		Document document = new Document();
		XmlElement root = new XmlElement("configuration");
		XmlElement mappers = new XmlElement("mappers");
		if (!mapperResources.isEmpty()) {
			for (String file : mapperResources) {
				XmlElement mapper = new XmlElement("mapper");
				mapper.addAttribute(new Attribute("resource", file));
				mappers.addElement(mapper);
			}
		}

		root.addElement(mappers);
		document.setRootElement(root);

		List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();

		GeneratedXmlFile gxf = new GeneratedXmlFile(document,
				"add_to_ibatis.xml", "", context
						.getSqlMapGeneratorConfiguration().getTargetProject(),
				false, context.getXmlFormatter());
		answer.add(gxf);

		return answer;
	}

}
