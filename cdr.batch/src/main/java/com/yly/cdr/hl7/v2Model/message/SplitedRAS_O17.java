package com.yly.cdr.hl7.v2Model.message;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Structure;
import ca.uhn.hl7v2.model.v24.group.RAS_O17_ORDER;
import ca.uhn.hl7v2.model.v24.message.RAS_O17;

@SuppressWarnings("serial")
public class SplitedRAS_O17 extends RAS_O17 {
	public SplitedRAS_O17(){
		super();
	}
	
	protected Map<String, List<Structure>> myStructures;
	
	public SplitedRAS_O17(RAS_O17 raso17){
		this();
		Field structuresField = getField(this, "structures");
		if(structuresField != null){
			structuresField.setAccessible(true);
			try {
				Object s = structuresField.get(this);
				if(s != null){
					myStructures = (Map<String, List<Structure>>) s;
					List<Structure> msh = new ArrayList<Structure>();
					msh.add(raso17.getMSH());
					myStructures.put("MSH", msh);
					List<Structure> patient = new ArrayList<Structure>();
					patient.add(raso17.getPATIENT());
					myStructures.put("PATIENT", patient);
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void insertRepetition(String name, Structure structure, int index) throws HL7Exception {
        if (structure == null) {
            throw new NullPointerException("Structure may not be null");
        }
		List<Structure> list = myStructures.get(name);
		if (list == null) {
            throw new HL7Exception("The structure " + name + " does not exist in the group "
                    + this.getClass().getName());
        }
        if (list.size() < index) {
            throw new HL7Exception("Invalid index: " + index + ", structure " + name + " must be between 0 and "
                    + (list.size()));
        }
        list.add(index, structure);
	}
	
	public void insertORDER(RAS_O17_ORDER structure, int rep) throws HL7Exception { 
		insertRepetition("ORDER", structure, rep);
	}
	
	public void insertORDERAtLast(RAS_O17_ORDER structure) throws HL7Exception { 
		insertORDER(structure, getORDERSize());
	}
	
	public void insertORDERALL(List<RAS_O17_ORDER> orderList) throws HL7Exception {
		for(RAS_O17_ORDER order : orderList){
			insertORDERAtLast(order);
		}
	}
	
	public int getORDERSize(){
		return myStructures.get("ORDER").size();
	}
	
	
	private Field getField(Object obj, String filedName){
		Field field = null;
		Class<?> clazz = obj.getClass();
		while(true){
			try{
				field = clazz.getDeclaredField(filedName);
				return field;
			} catch (Exception e){
				
			}
			if(clazz != Object.class){
				clazz = clazz.getSuperclass();
			} else {
				break;
			}
		}
		return null;
	}
}