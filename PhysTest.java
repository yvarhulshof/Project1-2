import java.util.Timer;
import java.util.TimerTask;


public class PhysTest {
	
	private double x;
	private double y;
	private double z;
	private double speed;
	private double acc;
	private double time;
	final static double mass = 1;
	final static double g = 9.81;
	final static double cofr = 0.8;
	final static double grad = 0;
	final static int rate = 1000;
	private double s;
	private double forceD;
	private double vx1;
	private double vx2;
	private double vy1;
	private double vy2;
	private double px1;
	private double px2;
	private double py1;
	private double py2;
	private double slopey;
	private double slopex;
		
	
	

	public PhysTest(double spdx, double spdy, double posx, double posy) {
		System.out.println(spdx +", "+ spdy);
		x = posx;
		y = posy;
		
		forceD = mass*g;
		time = 0;
		vx2 = spdx;
		vy2 = spdy;
		px2 = posx;
		py2 = posy;
		
		}
		
		public void move(){
			for (int i = 0; i<1000; i++) {
				try {
        //sending the actual Thread of execution to sleep X milliseconds
        Thread.sleep(1);
 	   } catch(InterruptedException ie) {}
 	  
 	   
 	  	z = Math.sin(px2) + (py2*py2);
    	vx1 = vx2;
    	vy1 = vy2;
    	px1 = px2;
    	py1 = py2;
    	vx2 = vx1 + 0.001*findfx();
		
		px2 = px1 + 0.001*vx1;
		
		
    	vy2 = vy1 + 0.001*findfy();
		
		py2 = py1 + 0.001*vy1;
		
		}
		}
		
		public double findfx(){
			double G = 0; //for now, DO NOT APPLY
			double H;
			double fx;
			slopex = dx();
		G = -mass * g * slopex;
			
			H = -cofr*mass*g*(vx1/(Math.sqrt((vx1*vx1)+(vy1*vy1))));
			fx = G + H;
			return fx;
		}
		public double findfy(){
			double G = 0; // = 0; for now, DO NOT APPLY
			double H;
			double fy;
			slopey = dy();
			G = -mass * g * slopey;
			
			H = -cofr*mass*g*(vy1/(Math.sqrt((vx1*vx1)+(vy1*vy1))));
			fy = G + H;
			return fy;
		}
		public double dx(){
			double d = Math.cos(px2); //for now, DO NOT APPLY
			return d;
		}
		public double dy(){
			double d = 2*py2; //for now, DO NOT APPLY
			return d;
		}
		
		public void get() {
			System.out.println("speedx = " + vx2 + ", speedy = " + vy2 + ", x = " + px2 + ", y = " + py2 + ", z = " + z + ", time = " + time);
			time = time + 1;
		}
	 
	public static void main(String[] args){
	
		
		Test3 a = new PhysTest(20, 20, -80, -80);
		for (int i = 0; i<50; i++) {
		a.move();
		a.get();
		}
	}
}
		