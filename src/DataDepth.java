import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class DataDepth {

	public DataDepth(){}
	
	public class Point{
		public double x;
		public double y;
		public int indeks;
		
		Point(double x_value, double y_value, int in){
			x = x_value;
			y = y_value;
			indeks = in;
		}
		public void print1(){
			System.out.println(indeks);
		}
		public void print2(){
			System.out.println(this.x + " " + this.y);
		}
	}
	
	//right turn
	public int isRight(Point a, Point b, Point c){
	    double pos = ((b.x - a.x)*(c.y - a.y) - (b.y - a.y)*(c.x - a.x));
	    if(pos < 0) {
	        return 1;
	    }
	    else if(pos > 0) {
	        return -1;
	    }
	    else {
	        return 0;
	    }
	}
	//incremental algorithm
	public List<Point> build(Point[] allP){	
		
		//lower hull
		List<Point> hull1 = new ArrayList();
		hull1.add(allP[0]);
		hull1.add(allP[1]);
		for(int i = 2; i<allP.length; i++){
			hull1.add(allP[i]);
			while(hull1.size() >= 3){
				Point p = hull1.get(hull1.size()-3);
				Point q = hull1.get(hull1.size()-2);
				Point r = hull1.get(hull1.size()-1);
				if(isRight(p,q,r) == 1){
					hull1.remove(q);
				}
				else
					break;
			}
		}
		
		//upper hull
		List<Point> hull2 = new ArrayList();
		hull2.add(allP[allP.length-1]);
		hull2.add(allP[allP.length-2]);
		for(int j = allP.length -3; j>=0; j--){
			hull2.add(allP[j]);
			while(hull2.size() >= 3){
				Point p = hull2.get(hull2.size()-3);
				Point q = hull2.get(hull2.size()-2);
				Point r = hull2.get(hull2.size()-1);
				if(isRight(p,q,r) == 1){
					hull2.remove(q);
				}
				else
					break;
			}
		}
		//p0 and pn are duplicated -> delete
		hull2.remove(hull2.size()-1);
		hull2.remove(0);
		
		List<Point> hull = new ArrayList<Point>(hull1);
		hull.addAll(hull2);
		return hull;
	}
	
	
	public List<Point> solve(double[][] points){
		
		Point[] allP = new Point[points.length];
		
		for(int j = 0; j<points.length; j++){
			allP[j] = new Point(points[j][0], points[j][1], j);
		}
	
		//sort points, most left and most right will be nedded to construct both CH
		Arrays.sort(allP, new Comparator<Point>(){
			public int compare(Point p1, Point p2){
				return Double.compare(p1.x, p2.x);
			}		
		});
		
		List<Point> hull = build(allP);
	
		return hull;
	}

	
	public static void main(String[] args) throws FileNotFoundException {
		
		double[][] points = new double [10][2];

		Scanner sc = new Scanner(new File("points3.txt")).useDelimiter(" ");
		int j = 0;
		while (sc.hasNextLine()){
			String line = sc.nextLine();
			double[] l = new double [2];
			l[0] = Double.parseDouble(line.trim().split("\\s+")[0]);
			l[1] = Double.parseDouble(line.trim().split("\\s+")[1]);
			points[j] = l;
			j++;
		}
		
		//build CH till there are still points to build on
		DataDepth d = new DataDepth();
		int i=0;
		while(points.length > 1){
			System.out.println("Depth "+i++);
			List<Point> sol = d.solve(points);
			for(int s = 0; s<sol.size();s++){
				sol.get(s).print2();
			}
			double[][] pointsNew = new double [points.length-sol.size()][2];
			int z = 0;
			for(int m = 0; m<points.length; m++){	
				boolean pointOnCH = false;
				for(int s = 0; s<sol.size();s++){
					if(Double.compare(sol.get(s).x, points[m][0]) == 0  && Double.compare(sol.get(s).y, points[m][1]) == 0){
						pointOnCH = true;
					}
				}
				if(!pointOnCH){
					double[] t = new double [2];
					t[0] = points[m][0];
					t[1] = points[m][1];
					pointsNew[z] = t; 
					z++;
				}
			}
			points = pointsNew;
		}
		//in case of 1 point left
		if(points.length == 1){
			System.out.println("Depth "+i++);
			double[] t = new double [2];
			t[0] = points[0][0];
			t[1] = points[0][1];
			System.out.print(t[0]+" ");
			System.out.println(t[1]);
		}		 
	}
}