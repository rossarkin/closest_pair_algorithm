public class ClosestPairNaive {

	public final static double INF = java.lang.Double.POSITIVE_INFINITY;

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

	public static void findClosestPair(XYPoint points[])
	{
		double minDist = INF; 
		
		if(points.length>1){
			
			XYPoint minPoint1 = points[1];
			XYPoint minPoint2 = points[2];
			
			//iterate through the array points and check to see if the points are closer than the current minimums
			for(int i=0; i<points.length; i++){
				
				for(int j=i+1; j<points.length; j++){
					
					if(points[i].dist(points[j])<minDist){
						
						minDist = points[i].dist(points[j]);
						
					}

				}
			}
			System.out.println(minPoint1.toString() + " " + minPoint2.toString() + " " + minDist);

		} else{
			System.out.println(minDist);
		}
	}

}
