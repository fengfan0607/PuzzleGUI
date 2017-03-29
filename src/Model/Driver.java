package Model;

import java.util.Arrays;
import java.util.Observable;

import common.Data;

public class Driver extends Observable implements Data {
	private String name;
	private int[] prePlan;
	private int[] postPlan;

	public int[] getPrePlan() {
		return prePlan;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrePlan(int[] prePlan) {
		this.prePlan = prePlan;
	}

	public void setPrePlanAtPosition(int i, int value) {
		prePlan[i] = value;
		System.err.println("changed");
		setChanged();
		notifyObservers();
	}

	public int getPrePlanAtPosition(int i) {
		return prePlan[i];
	}

	public int[] getPostPlan() {
		return postPlan;
	}

	public Driver(String name, int[] prePlan, int[] postPlan) {
		super();
		this.name = name;
		this.prePlan = prePlan;
		this.postPlan = postPlan;
	}

	public void setPostPlan(int[] postPlan) {
		this.postPlan = postPlan;
	}

	public void setPostPlanAtPosition(int i, int value) {
		postPlan[i] = value;
		setChanged();
		notifyObservers();
	}

	public int getPostPlanAtPosition(int i) {
		return postPlan[i];
	}

	public Driver(int[] prePlan, int[] postPlan) {
		super();
		this.prePlan = prePlan;
		this.postPlan = postPlan;
	}

	public String toStringPrePlan() {
		for (int i = 0; i < prePlan.length; i++) {
			if (prePlan[i] == 0) {
				// setPostPlanAtPosition(i, 3);
				prePlan[i] = 3;
			}
		}

		return "driver " + name + " :  " + Arrays.toString(prePlan);
	}

	public String toStringPostPlan() {
		return "driver " + name + " :  " + Arrays.toString(getPostPlan());
	}

}
