package com.JH.dgather.frame.xmlparse.model;

public class SnmpObjId {

	int[] oids;
	
	private SnmpObjId(String oid) {
		if(oid.charAt(0) == '.')
			oid = oid.substring(1);
		String[] insts = oid.split("\\.");
		oids = new int[insts.length];
		for(int i = 0; i < insts.length; i ++)
			oids[i] = Integer.parseInt(insts[i]);
	}
	
	public static SnmpObjId getSnmpObjId(String oid) {
		return new SnmpObjId(oid);
	}
	
	public void decrement() {
		if(oids[size() - 1] == 0) {
			int[] tmp = new int[size() - 1];
			System.arraycopy(oids, 0, tmp, 0, size() - 1);
			oids = tmp;
		}
		oids[size() - 1] = oids[size() - 1] - 1;
	}
	
	public String getSubOid(int start, int end) {
		String ret = "";
		for(int i = start; i < end; i++) {
			ret += oids[i] + ".";
		}
		return ret.substring(0, ret.length() - 1);
	}
	
	public int size() {
		return oids.length;
	}
	
	public static void main(String[] args) {
		SnmpObjId o = SnmpObjId.getSnmpObjId(".1.3.6.1.2.1");
		String[] array = {"0", "2"};
		int end = o.size() - Integer.parseInt(array[0]);
		int num = Integer.parseInt(array[1]);
		System.out.println(o.getSubOid(end - num, end));
	}
}
