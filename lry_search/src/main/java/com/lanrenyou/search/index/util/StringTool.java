package com.lanrenyou.search.index.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class StringTool {

	public static String getText(String html){
		StringBuilder sb=new StringBuilder(html);
		int s,e=sb.length();
		while((e=sb.lastIndexOf(">",e))!=-1){
			s=sb.lastIndexOf("<",e);
			if(s==-1){
				sb.deleteCharAt(e);
				break;
			}
			sb.delete(s, e+1);
			e=s;
		}
		
		return sb.toString();
	}
	
	
	public static String getTextFileContent(String path){
		try {
			File file=new File(path);
			if(!file.exists()){
				return null;
			}
			BufferedReader br=new BufferedReader(new FileReader(file));
			StringBuilder sb=new StringBuilder();
			String s;
			
			while((s=br.readLine())!=null){
				sb.append(s).append("\n");
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean WriteContentToTextFile(String path,String content){
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(path));
			bw.write(content);
			bw.flush();
			bw.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void main(String[] args) {
		String html="<p align=\"left\"> <b> </b><b>工作职责： <br /></b> 1. 负责公司产品开发、调试及维护工作；<br /> 2. 根据项目需求设计项目开发方案； <br /> 3. 编写硬件及软件开发文档；<br /> 4. 进行行业相关技术的研究。<br /><br /> <b>职位要求： <br /></b> 1. 本科以上学历，二年以上硬件开发工作经验，具有独立开发硬件系统的能力； <br /> 2. 精通 C/C++ 语言；<br /> 3. 熟悉单片机的程序设计，具有实际的单片机程序编写、调试经验<br /> 4. 有良好的数字电路、模拟电路、通信原理等基础知识； <br /> 5. 具有良好的英语阅读、沟通能力； <br /> 6. 具有良好的团队精神、较强的学习能力；</p>";
		System.out.println(getText(html));
	}
}
