/**
 * 
 */
package mybatis.framework.components.code;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.ShellRunner;

/**
 *
 */
public class CodeGenerator
{
	private String generatorConfig; // config file path
	private boolean overwrite = true;
	/**
	 * 
	 */
	public CodeGenerator(String generatorConfig)
	{
		this(generatorConfig, true);
	}

	public CodeGenerator(String generatorConfig, boolean overwrite)
	{
		this.generatorConfig = generatorConfig;
		this.overwrite = overwrite;
	}
	
	public void run()
	{
		if (generatorConfig == null)
		{
			System.err.println("generatorConfig不能为空!");
			return;
		}
		File config = new File(generatorConfig);
		if (!config.exists() || !config.canRead())
		{
			System.err.println("配置文件不可用:" +generatorConfig);
			return;
		}
		List<String> param = new ArrayList<String>();
		param.add("-configfile");
		param.add(generatorConfig);
		if (overwrite)
		{
			param.add("-overwrite");
		}
		param.add("-verbose");
		
		String[] arg = param.toArray(new String[0]);
		ShellRunner.main(arg);
	}
}
