package com.founder.hie.message.terser;

import java.util.StringTokenizer;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.Structure;
import ca.uhn.hl7v2.util.Terser;

public class MessageTerser {
	private Message message;
	private Terser terser;

	public Message getMessage() {
		return message;
	}

	public Terser getTerser() {
		return terser;
	}
	public MessageTerser(Message message) {
		this.message = message;
	}
	public MessageTerser(Terser terser) {
		this.terser = terser;
	}
	public MessageTerser(Message message,Terser terser) {
		this.message = message;
		this.terser = terser;
	}
	public String getValue(String path) throws HL7Exception{
	String 	StrPath=path.replace("[", "(").replace("]",")");
		return terser.get(StrPath);
	}
	//通过message获取某个节点下的数组长度
	public int getArrayCount(String path) throws HL7Exception{
		int length=0;//返回数组长度
		int i=0;//没什么用
		if(path !=null && ! ("").equals(path)){
			String splitPath[] = path.replaceFirst("/", "").split("/");
			Structure[] structure=null;
			Boolean ifExist = false;//name是否存在
			//获得name的长度和内容
			int lengthOfNames = message.getNames().length;
			String[] strNames = message.getNames();
			//判断根目录是否合法
			for(int j = 0 ; j < lengthOfNames ; j++){
				if(splitPath[0].split("\\[")[0].equals(strNames[j])){
					ifExist = true;
				}
			}
			if(ifExist){
				if(splitPath[0].contains("[")){
					structure=message.getAll(splitPath[0].split("\\[")[0]);
					i=Integer.parseInt(splitPath[0].split("\\[")[1].toString().substring(0,1));
				}else{
					structure = message.getAll(splitPath[0]);
				}
			}else{
				return length = 0; 
			}
			
			ifExist = false;
			length=structure.length;//元素个数
			if(splitPath.length>1){//路径层数
				int d = 1;
				Structure[] structure2=null;
				lengthOfNames = 0;
				strNames = null;
				while(d < splitPath.length){
					//判断子目录是否合法
					lengthOfNames = ((Group) structure[0]).getNames().length;
					strNames = ((Group) structure[0]).getNames();
					for(int j = 0 ; j < lengthOfNames ; j++){
						if(splitPath[d].split("\\[")[0].equals(strNames[j])){
							ifExist = true;
						}
					}
					if(ifExist){
						structure2 = ((Group) structure[0]).getAll(splitPath[d].split("\\[")[0]);
						structure = structure2;
						length=structure2.length;
					}else{
						return length = 0;
					}
					ifExist = false;
					d++;
				}
			}
		}
		return length;
	}
	
//	public int getArrayCount(String path) throws HL7Exception{
//		int length=0;
//		int i=0;
//		if(path !=null && ! ("").equals(path)){
//			String splitPath[] = path.replaceFirst("/", "").split("/");
//			Structure[] structure=null;
//			if(splitPath[0].contains("[")){
//				structure=message.getAll(splitPath[0].split("\\[")[0]);
//				i=Integer.parseInt(splitPath[0].split("\\[")[1].toString().substring(0,1));
//			}else{
//				structure = message.getAll(splitPath[0]);
//			}
//			length=structure.length;
//			if(splitPath.length>1){
//				Structure[] structureTwo=null;
//				if(splitPath.length==2 ){
//					structureTwo = ((Group) structure[i]).getAll(splitPath[1]);
//					length=structureTwo.length;
//				}else if(splitPath.length==3 && !splitPath[1].contains("[")){
//					structureTwo = ((Group) structure[i]).getAll(splitPath[1]);
//					structureTwo = ((Group) structureTwo[i]).getAll(splitPath[2]);
//					length=structureTwo.length;
//				}else if(splitPath.length==4 && !splitPath[2].contains("[")){
//					structureTwo = ((Group) structure[i]).getAll(splitPath[1]);
//					structureTwo = ((Group) structureTwo[i]).getAll(splitPath[2]);
//					structureTwo = ((Group) structureTwo[i]).getAll(splitPath[3]);
//					length=structureTwo.length;
//				}
//			}
//		}
//		return length;
//	}
	//封装含有"^"内容的长度
	public int getTerserIndices(String path,Terser terser) throws HL7Exception{
		int[] ind = Terser.getIndices(path);
        StringTokenizer tok = new StringTokenizer(path, "-", false);
        Segment segment = terser.getSegment(tok.nextToken());
    	return segment.getField(ind[0]).length;
	}
}
