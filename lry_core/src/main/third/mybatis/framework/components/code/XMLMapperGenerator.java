/**
 * 
 */
package mybatis.framework.components.code;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

import java.util.Iterator;

import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.SimpleXMLMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

/**
 *
 */
public class XMLMapperGenerator extends SimpleXMLMapperGenerator
{
    public static final String IBATIS3_MAPPER_SYSTEM_ID = "http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd"; //$NON-NLS-1$

    public static final String IBATIS3_MAPPER_PUBLIC_ID = "-//ibatis.apache.org//DTD Mapper 3.0//EN"; //$NON-NLS-1$

	@Override
	public Document getDocument()
	{
//        Document document = new Document(
//                XmlConstants.MYBATIS3_MAPPER_PUBLIC_ID,
//                XmlConstants.MYBATIS3_MAPPER_SYSTEM_ID);
        Document document = new Document(
        		IBATIS3_MAPPER_PUBLIC_ID,
        		IBATIS3_MAPPER_SYSTEM_ID);
        document.setRootElement(getSqlMapElement());

        if (!context.getPlugins().sqlMapDocumentGenerated(document,
                introspectedTable)) {
            document = null;
        }

        return document;
    }
	
	@Override
	protected XmlElement getSqlMapElement()
	{
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString("Progress.12", table.toString())); //$NON-NLS-1$
        XmlElement answer = new XmlElement("mapper"); //$NON-NLS-1$
        String namespace = introspectedTable.getMyBatis3SqlMapNamespace();
        answer.addAttribute(new Attribute("namespace", //$NON-NLS-1$
                namespace));

        context.getCommentGenerator().addRootComment(answer);

        addResultMapElement(answer);
        addBaseColumnListElement(answer);
        addDeleteByPrimaryKeyElement(answer);
        addInsertElement(answer);
        addUpdateByPrimaryKeyElement(answer);
        addSelectByPrimaryKeyElement(answer);
        addSelectAllElement(answer);

        return answer;
    }

    protected void addBaseColumnListElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateBaseColumnList()) {
//            AbstractXmlElementGenerator elementGenerator = new BaseColumnListElementGenerator();
            initializeAndExecuteGenerator(new AbstractXmlElementGenerator(){

				@Override
				public void addElements(XmlElement parentElement)
				{
			        XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

			        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
			                introspectedTable.getBaseColumnListId())); // Base_Column_List

			        context.getCommentGenerator().addComment(answer);

			        StringBuilder sb = new StringBuilder();
			        Iterator<IntrospectedColumn> iter = introspectedTable
			                .getAllColumns().iterator();
			        while (iter.hasNext()) {
			            sb.append(MyBatis3FormattingUtilities.getSelectListPhrase(iter
			                    .next()));

			            if (iter.hasNext()) {
			                sb.append(", "); //$NON-NLS-1$
			            }

			            if (sb.length() > 80) {
			                answer.addElement(new TextElement(sb.toString()));
			                sb.setLength(0);
			            }
			        }

			        if (sb.length() > 0) {
			            answer.addElement((new TextElement(sb.toString())));
			        }

			        if (context.getPlugins().sqlMapBaseColumnListElementGenerated(
			                answer, introspectedTable)) {
			            parentElement.addElement(answer);
			        }
			    }
            	
            }, parentElement);
        }
    }

    protected void addSelectByPrimaryKeyElement(XmlElement parentElement) {
        if (introspectedTable.getRules().generateSelectByPrimaryKey()) {
//            AbstractXmlElementGenerator elementGenerator = new SimpleSelectByPrimaryKeyElementGenerator();
            initializeAndExecuteGenerator(new AbstractXmlElementGenerator(){

				@Override
				public void addElements(XmlElement parentElement)
				{
			        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

			        answer.addAttribute(new Attribute(
			                "id", introspectedTable.getSelectByPrimaryKeyStatementId())); //$NON-NLS-1$
			        answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
			                introspectedTable.getBaseResultMapId()));

			        String parameterType;
			        // PK fields are in the base class. If more than on PK
			        // field, then they are coming in a map.
			        if (introspectedTable.getPrimaryKeyColumns().size() > 1) {
			            parameterType = "map"; //$NON-NLS-1$
			        } else {
			            parameterType = introspectedTable.getPrimaryKeyColumns().get(0)
			                    .getFullyQualifiedJavaType().toString();
			        }

			        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
			                parameterType));

			        context.getCommentGenerator().addComment(answer);

			        answer.addElement(new TextElement("select")); //$NON-NLS-1$

			        StringBuilder sb = new StringBuilder();
			        answer.addElement(getBaseColumnListElement());

			        sb.setLength(0);
			        sb.append("from "); //$NON-NLS-1$
			        sb.append(introspectedTable
			                .getAliasedFullyQualifiedTableNameAtRuntime());
			        answer.addElement(new TextElement(sb.toString()));

			        boolean and = false;
			        for (IntrospectedColumn introspectedColumn : introspectedTable
			                .getPrimaryKeyColumns()) {
			            sb.setLength(0);
			            if (and) {
			                sb.append("  and "); //$NON-NLS-1$
			            } else {
			                sb.append("where "); //$NON-NLS-1$
			                and = true;
			            }

			            sb.append(MyBatis3FormattingUtilities
			                    .getAliasedEscapedColumnName(introspectedColumn));
			            sb.append(" = "); //$NON-NLS-1$
			            sb.append(MyBatis3FormattingUtilities
			                    .getParameterClause(introspectedColumn));
			            answer.addElement(new TextElement(sb.toString()));
			        }

			        if (context.getPlugins().sqlMapSelectByPrimaryKeyElementGenerated(
			                answer, introspectedTable)) {
			            parentElement.addElement(answer);
			        }
			    }}, parentElement);
        }
    }

    protected void addSelectAllElement(XmlElement parentElement) {
        initializeAndExecuteGenerator(new AbstractXmlElementGenerator(){

			@Override
			public void addElements(XmlElement parentElement)
			{
		        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

		        answer.addAttribute(new Attribute(
		                "id", introspectedTable.getSelectAllStatementId())); //$NON-NLS-1$
		        answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
		                introspectedTable.getBaseResultMapId()));

		        context.getCommentGenerator().addComment(answer);

		        answer.addElement(new TextElement("select")); //$NON-NLS-1$
		        
		        StringBuilder sb = new StringBuilder();

		        answer.addElement(getBaseColumnListElement());

		        sb.setLength(0);
		        sb.append("from "); //$NON-NLS-1$
		        sb.append(introspectedTable
		                .getAliasedFullyQualifiedTableNameAtRuntime());
		        answer.addElement(new TextElement(sb.toString()));
		        
		        String orderByClause = introspectedTable.getTableConfigurationProperty(PropertyRegistry.TABLE_SELECT_ALL_ORDER_BY_CLAUSE);
		        boolean hasOrderBy = StringUtility.stringHasValue(orderByClause);
		        if (hasOrderBy) {
		            sb.setLength(0);
		            sb.append("order by "); //$NON-NLS-1$
		            sb.append(orderByClause);
		            answer.addElement(new TextElement(sb.toString()));
		        }

		        if (context.getPlugins().sqlMapSelectAllElementGenerated(
		                answer, introspectedTable)) {
		            parentElement.addElement(answer);
		        }
		    }}, parentElement);
    }
	
}
