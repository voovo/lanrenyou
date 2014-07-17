package com.lanrenyou.search.index.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.lanrenyou.search.index.util.vo.Type;



/**
 * 
 * @author lujianming
 * 类型工厂
 *
 * 2011-6-1
 */

public class TypeFactory  {
	
	protected List<Type> types = new ArrayList<Type>();
	
	//the key is id 
	protected Map<String, Type> typeMap = new HashMap<String, Type>();
	
	//the key is name
	protected Map<String, Type> nameMap = new HashMap<String, Type>();
	
	protected String filePath ;
	
	//filter the type's name
	protected String [] exceptStrings = {};
	
	//“其他”的id 数组
	protected String [] others = {};
	
	protected String rootId = "0";
		
	protected void  init() {
		if(types.isEmpty()) {
			synchronized(TypeFactory.class) {
				if(!types.isEmpty()) return;
				//read data from the file and use map to save the data
				cacheData();
	
				//add the parent and child for all node
				bulidTree();
				
				initMap();

			}
		}

	}

	protected void initMap() {
		for(Type type : types) {
			wrapType(type);
			typeMap.put(type.getId(), type);
			nameMap.put(type.getName(), type);
		}
	}
	
	//假如类型属于其他，则给它名称上加上父类
	protected void wrapType(Type type) {
		if(isOther(type.getId())) {
			StringBuilder sb = new StringBuilder();
			sb.append(type.getName());
			sb.append("（");
			sb.append(type.getParent().getName());
			sb.append("）");
			type.setName(sb.toString());
		}
	}
	
	//类型是否是“其他”，是返回true，否返回false
	protected boolean isOther(String id) {
		for(String other : others) {
			if(other.equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	
	protected void cacheData() {
		try {
			InputStream inputStream = TypeFactory.class.getResourceAsStream(filePath);
			InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
			BufferedReader bufferReader = new BufferedReader(reader);
			
			String [] result = null;
			String line = "";
			while((line = bufferReader.readLine()) != null) {
				result = line.split(",");
				
				if( result != null) {
					Type type = new Type();
					Type parentType = new Type();
					type.setId(result[0]);
					type.setName(filterName(result[1]));
					parentType.setId(result[2]);
					type.setParent(parentType);
					
					types.add(type);
					
				}
			}
			bufferReader.close();
			reader.close();
			inputStream.close();

			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void bulidTree() {
		List<Type> tree =  new ArrayList<Type>();
  		for (Type type : types) {
			for (Iterator<Type> it = tree.iterator();it.hasNext();) {
				Type nt = it.next();
				Type parent = related(type, nt);
				if (parent != null) {
					it.remove();
					type = parent;
				}
			}
			tree.add(type);
		}
	}
	/**
	 * 关联类型一(type)和类型二(treeType)
	 * @param type
	 * @param treeType
	 * @return 返回treeType，如果二者没有上下级关系，返回null
	 */
	private Type related(Type type, Type treeType) {
		if (treeType.getParent().getId() != null 
				&& treeType.getParent().getId().trim().equals(type.getId().trim())) {
			type.addChild(treeType);
			treeType.setParent(type);
			return type;
		}
		if (isChild(type, treeType)) {
			return treeType;
		}
		if (isChild(treeType, type)) {
			return type;
		}
		return null;
	}
	/**
	 * 判断type是否是treeType的子类型
	 * @param type
	 * @param treeType
	 * @return
	 */
	private boolean isChild(Type type,
			Type treeType) {
		if (type.getParent().getId() != null && (type.getParent().getId().trim().equals(treeType.getId().trim()))) {
			treeType.addChild(type);
			type.setParent(treeType);
			return true;
		}
		List<Type> children = treeType.getChild();
		for (Type child : children) {
			if (isChild(type, child)) {
				return true;
			}
		}
		return false;
	}
	
	protected String filterName (String name) {
		for (String es : exceptStrings) {
			if (name.endsWith(es)) {
				return name.substring(0, name.lastIndexOf(es));
			}
		}
		return name;
	}
	
	public List<Type> getSubTypesById(String id) {
		init();
		if(typeMap.containsKey(id)) {
			return typeMap.get(id).getChild();
		} 
		return Collections.emptyList();
	}
	public Type getTypeById(String  id) {
		init();
		return typeMap.get(id);
	}

	public Type getTypeByName(String name) {
		init();
		return nameMap.get(name);
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public Map<String, Type> getTypeMap() {
		init();
		return typeMap;
	}

	public Map<String, Type> getNameMap() {
		init();
		return nameMap;
	}
	
	public String[] getOthers() {
		return others;
	}

	public void setOthers(String[] others) {
		this.others = others;
	}
}

