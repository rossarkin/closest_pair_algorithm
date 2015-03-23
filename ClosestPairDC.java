import java.util.ArrayList;
import java.util.Arrays;

public class ClosestPairDC {

	public final static double INF = java.lang.Double.POSITIVE_INFINITY;
	public static XYPoint minPoint1; 
	public static XYPoint minPoint2;
	public static XYPoint minTemp1;
	public static XYPoint minTemp2;
 
	//
	// findClosestPair()
	//
	// Given a collection of nPoints points, find and ***print***
	//  * the closest pair of points
	//  * the distance between them
	// in the form "(x1, y1) (x2, y2) distance"
	//

	// INPUTS:
	//  - points sorted in nondecreasing order by X coordinate
	//  - points sorted in nondecreasing order by Y coordinate
	//
	
	public static double closestPairHelper(XYPoint pointsByX[], 
			XYPoint pointsByY[]){

		int nPoints = pointsByX.length;
		
		//base case: if there is only one point, return a distance of infinity
		if (nPoints == 1){
			return INF; 
		}
		
		//checks to see if any points are the same. If they are, return a minimum distance of zero.
		for(int i = 0; i<pointsByX.length-1; i++ ){
			if(pointsByX[i].x==pointsByX[i+1].x){
				if(pointsByX[i].y==pointsByX[i+1].y){
				minPoint1=pointsByX[i];
				minPoint2=pointsByX[i+1];
				return 0.0;
				}
			}
		}
		
		//base case: if there are only two points, they are the minimum points. Assign them to temporary minimum variables.
		if (nPoints==2){
			minTemp1 = pointsByX[0];
			minTemp2 = pointsByX[1];
			return minTemp1.dist(minTemp2);
		}
		else {
	
	//DIVIDE		
			int mid = (nPoints/2);

			XYPoint midPoint = pointsByX[mid];
			XYPoint XL[] = Arrays.copyOfRange(pointsByX, 0, mid+1); //copies the left half of pointsByX to a new array XL
			XYPoint XR[] = Arrays.copyOfRange(pointsByX, mid+1, nPoints); //copies the right half of pointsByX to a new array XR

			XYPoint YL[] = new XYPoint[XL.length]; //create an array for the left half of pointsByY, referring to the same points as XL
			XYPoint YR[] = new XYPoint[XR.length];//create an array for the right half of pointsByY, referring to the same points as XR

			int ylCount = 0; //set a count to keep track of the YL index in the following loop
			int yrCount = 0; ///set a count to keep track of the YR index in the following loop
			
			//iterates through pointsByY and assigns points to either YL or YR depending on their x position
			for(int i=0; i<pointsByY.length; i++){
				if(pointsByY[i] == midPoint){ //the midpoint is assigned to YL
					YL[ylCount] = pointsByY[i];
					ylCount++;
				}
				else if(pointsByY[i].isLeftOf(midPoint)){ //points left of the midpoint are assigned to YL
					YL[ylCount] = pointsByY[i];
					ylCount++;
				}
				else{ //points right of the midpoint are assigned to YR
					YR[yrCount] = pointsByY[i];
					yrCount++;
				}
			}
	
	//CONQUER
			//recurse this method on the two halves of the initial set of points
			double distL = closestPairHelper(XL,YL);
			double distR = closestPairHelper(XR, YR);
			
			//find the minimum of those two recursive calls
			double lrDist = Math.min(distL,distR);
			
	//COMBINE
			//create an arraylist for points that may be closer than the current minimum across the midpoint
			ArrayList<XYPoint> yStripList = new ArrayList<XYPoint>(); 
			for(int i = 0; i < pointsByY.length; i++){
				if((pointsByY[i].x)-(midPoint.x)<lrDist){
					yStripList.add(pointsByY[i]);
				}
			}
			
			//conver the previous arraylist to an array
			Object[] yStripTemp = yStripList.toArray();
			XYPoint[] yStrip = new XYPoint[yStripTemp.length];
			for(int i=0; i<yStripTemp.length; i++){
				yStrip[i] = (XYPoint) yStripTemp[i];
			}
			
			double minDist = lrDist;	
			
			//iterate through the yStrip array to determine if any points are closer across the midpoint
			for(int j=0; j<=yStrip.length-2; j++){
				int k = j+1;
				while(k <= (yStrip.length-1) && (yStrip[k].y - yStrip[j].y<lrDist)){
					double d = yStrip[j].dist(yStrip[k]);
					minDist = Math.min(minDist, d);
					if (minDist == d){
						minPoint1 = yStrip[k];
						minPoint2 = yStrip[j];

					}
					k++;
				}
			}
			//check to see if the values stored in the temporary variables are closer than the current minimum variables
			//if they are, reassign the minimums to the new minimums
			if(minTemp1.dist(minTemp2)<minPoint1.dist(minPoint2)){
				minPoint1 = minTemp1;
				minPoint2 = minTemp2;

			}
			return minDist;
		} 
		
	}
	
	//a method for ensuring that points are printed in the correct order
	public static void sort(XYPoint point1, XYPoint point2){
		if(point1.x < point2.x){
			minPoint1 = point1;
			minPoint2 = point2;
		}
		else if(point2.x < point1.x){
			minPoint1 = point2;
			minPoint2 = point1;
		}
		else if(point1.y < point2.y){
			minPoint1 = point1;
			minPoint2 = point2;
		}
		else if(point2.y < point1.y){
			minPoint1 = point2;
			minPoint2 = point1;
		}
	}
	
	public static void findClosestPair(XYPoint pointsByX[], 
			XYPoint pointsByY[]){
		double minDist = closestPairHelper(pointsByX, pointsByY);
		sort(minPoint1,minPoint2);
		System.out.println(minPoint1.toString() + " " +  minPoint2.toString() + " " + minDist);
	}
}
