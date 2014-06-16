/**
 * 
 */
package code;

import mybatis.framework.components.code.CodeGenerator;


/**
 * 生成基础代码, 注意两个配置文件: code/generatorConfig.xml, code/mybatis.properties
 *
 */
public class CodeUtil
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// 
		String generatorConfig = CodeUtil.class.getClassLoader().getResource("code/generator.xml").getFile();
		CodeGenerator cg = new CodeGenerator(generatorConfig);
		cg.run();
	}

}
