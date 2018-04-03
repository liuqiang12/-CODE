package com.JH.itBusi.business.deviceCap.result;

import java.util.ArrayList;

import com.JH.dgather.frame.gathercontrol.result.ExecutiveResult;

public class CapGatherResult extends ExecutiveResult {
	public CapGatherResult(int successSize) {

		super(successSize);
	}

	private static final long serialVersionUID = 3597735661545543572L;

	private ArrayList<Integer> myTest = new ArrayList<Integer>();

	public ArrayList<Integer> getMyTest() {
		return this.myTest;
	}

	public CapGatherResult() {
		super(1);
	}
}
