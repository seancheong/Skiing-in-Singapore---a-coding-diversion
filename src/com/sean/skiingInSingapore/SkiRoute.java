package com.sean.skiingInSingapore;

import java.util.ArrayList;
import java.util.List;

/*
 * This class is to form a ski route from a list of ski position
 * 
 * Author: Sean Cheong Zhen Xiong
 */
public class SkiRoute {
	private List<Integer> skiRoute;
	
	public SkiRoute() {
		skiRoute = new ArrayList<Integer>();
	}
	
	public SkiRoute(int skiPoint) {
		skiRoute = new ArrayList<Integer>();
		skiRoute.add(skiPoint);
	}
	
	public SkiRoute(SkiRoute cloneObject) {
		if(skiRoute != null)
			skiRoute.clear();
		
		this.skiRoute = new ArrayList<Integer>(cloneObject.skiRoute);
	}
	
	public void removeLastSkiPoint() {
		skiRoute.remove(skiRoute.size() - 1);
	}

	public void addRoute(int skiPoint) {
		skiRoute.add(skiPoint);
	}
	
	public int getNumberOfRoute() {
		return skiRoute.size();
	}
	
	public List<Integer> getSkiRoute() {
		return skiRoute;
	}
	
	public int calculateSteepValue() {
		if(skiRoute.size() == 0)
			return 0;
		
		return skiRoute.get(0) - skiRoute.get(skiRoute.size() - 1);
	}
}
