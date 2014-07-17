package com.lanrenyou.search.index.util.vo;


import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author lujianming
 *
 * 2011-6-1
 */
public class Type {

	private String id;
	
	private String name;
	
	private Type parent;
	
	private boolean isValid;
	
	private List<Type> child = new ArrayList<Type>();

	public Type() {}
	
	public Type(String id, String name, Type parent) {
		this.id = id;
		this.name = name;
		this.parent = parent;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getParent() {
		return parent;
	}

	public void setParent(Type parent) {
		this.parent = parent;
	}

	public List<Type> getChild() {
		return child;
	}

	public void setChild(List<Type> child) {
		this.child = child;
	}
	
	public void addChild(Type type) {
		child.add(type);
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
}
