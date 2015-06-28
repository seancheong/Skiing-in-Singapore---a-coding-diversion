package com.sean.skiingInSingapore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * This program is to calculate the most efficient way to ski.
 * A ski route can be said is efficient if the path to reach the destination is the longest,
 * and the drop point from the start to finish is the highest.
 * For more information, please read the README.txt
 * 
 * Author: Sean Cheong Zhen Xiong
 */

public class Ski {
	private int[][] skiMap;
	private SkiRoute skiRoute;
	private SkiRoute newSkiRoute;
	private int rowSize;
	private int colSize;
	private Boolean isFirstLine;
	
	public static void main(String[] args) {
		Ski ski = new Ski("Map.txt");
		System.out.println("Number of route: " + ski.getNumberOfRoute() + "\nSteep value: " + ski.calculateSteepValue() + "\n");
		ski.printSkiRoute();
	}
	
	public Ski(String fileName)	{
		try {
			readFile(fileName);
			
			skiRoute = new SkiRoute();
			calculateSkiRoute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * To read file that contains 2-d integer array with rowSize and colSize at the top.
	 */
	public void readFile(String filename) throws IOException {
        BufferedReader buffer = new BufferedReader(new FileReader(filename));

        String line;
        int row = 0;
        isFirstLine = true;

        while ((line = buffer.readLine()) != null) {
            String[] vals = line.trim().split("\\s+");
            int length = vals.length;
            
            if(isFirstLine) {
            	for(int col = 0; col < 2; col++) {
            		if(col == 0)
            			rowSize = Integer.parseInt(vals[col]);
            		else
            			colSize = Integer.parseInt(vals[col]);
            	}
            	skiMap = new int[rowSize][colSize];
            	isFirstLine = false;
            }
            else {
            	for (int col = 0; col < length; col++) {
                	skiMap[row][col] = Integer.parseInt(vals[col]);
                }
            	 row++;
            }
        }
        
        if(buffer != null)
        	buffer.close();
    }
	
	/*
	 * To calculate the most efficient ski route.
	 * It can be formed from any direction, e.g. east, south, west and north
	 */
	public void calculateSkiRoute() {
		for(int i=0; i<rowSize; i++) {
			for(int j=0; j<colSize; j++) {
				if(isSkiRouteInBound(i, j+1) && (skiMap[i][j] > skiMap[i][j+1])) {
					if(newSkiRoute != null)
						newSkiRoute = null;
					
					newSkiRoute = new SkiRoute(skiMap[i][j]);
					newSkiRoute.addRoute(skiMap[i][j+1]);
					calculateNextPossibleSkiRoute(i, j+1);
				}
				
				if(isSkiRouteInBound(i, j-1) && (skiMap[i][j] > skiMap[i][j-1])) {
					if(newSkiRoute != null)
						newSkiRoute = null;
					
					newSkiRoute = new SkiRoute(skiMap[i][j]);
					newSkiRoute.addRoute(skiMap[i][j-1]);
					calculateNextPossibleSkiRoute(i, j-1);
				}
				
				if(isSkiRouteInBound(i+1, j) && (skiMap[i][j] > skiMap[i+1][j])) {
					if(newSkiRoute != null)
						newSkiRoute = null;
					
					newSkiRoute = new SkiRoute(skiMap[i][j]);
					newSkiRoute.addRoute(skiMap[i+1][j]);
					calculateNextPossibleSkiRoute(i+1, j);
				}
				
				if(isSkiRouteInBound(i-1, j) && (skiMap[i][j] > skiMap[i-1][j])) {
					if(newSkiRoute != null)
						newSkiRoute = null;
					
					newSkiRoute = new SkiRoute(skiMap[i][j]);
					newSkiRoute.addRoute(skiMap[i-1][j]);
					calculateNextPossibleSkiRoute(i-1, j);
				}
				
				
			}
		}
	}
	
	public void calculateNextPossibleSkiRoute(int row, int col) {
		if(isSkiRouteInBound(row, col+1) && (skiMap[row][col] > skiMap[row][col+1])) {
			newSkiRoute.addRoute(skiMap[row][col+1]);
			calculateNextPossibleSkiRoute(row, col+1);
		}
		
		if(isSkiRouteInBound(row, col-1) && (skiMap[row][col] > skiMap[row][col-1])) {
			newSkiRoute.addRoute(skiMap[row][col-1]);
			calculateNextPossibleSkiRoute(row, col-1);
		}
		
		if(isSkiRouteInBound(row+1, col) && (skiMap[row][col] > skiMap[row+1][col])) {
			newSkiRoute.addRoute(skiMap[row+1][col]);
			calculateNextPossibleSkiRoute(row+1, col);
		}
		
		if(isSkiRouteInBound(row-1, col) && (skiMap[row][col] > skiMap[row-1][col])) {
			newSkiRoute.addRoute(skiMap[row-1][col]);
			calculateNextPossibleSkiRoute(row-1, col);
		}
		
		if(newSkiRoute.getNumberOfRoute() >= skiRoute.getNumberOfRoute()) {
			if(newSkiRoute.calculateSteepValue() > skiRoute.calculateSteepValue()) {
				skiRoute = null;
				skiRoute = new SkiRoute(newSkiRoute);
			}
		}
		
		// remove the last position, 
		// so that it will calculate the next possible ski route from previos position again
		newSkiRoute.removeLastSkiPoint();
	}
	
	public int getNumberOfRoute() {
		return skiRoute.getNumberOfRoute();
	}
	
	public int calculateSteepValue() {
		return skiRoute.calculateSteepValue();
	}
	
	public void printSkiRoute() {
		int length = getNumberOfRoute();
		
		for(int i = 0; i < length; i++){
			int number = i+1;
			System.out.println("Point " + number + ": " + skiRoute.getSkiRoute().get(i));
		}
	}
	
	public Boolean isSkiRouteInBound(int row, int col) {
		return row >= 0 && row < rowSize && col >= 0 && col < colSize;
	}
}
