package com.mygdx.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

public class PhysicsEngine {

    GolfBall golfBall;
    Circle goal;
    Rectangle water;
    boolean ballStopped = true;
    boolean ballBlocked = false;

    boolean ballBlockedX = false;
    boolean ballBlockedY = false;


    static boolean initialCall = true;
    static double startTime = 0;
    private boolean usingMethod3 = false;
    public boolean firstAICall = true;

    private double frictionConstant = 0.6;
    private double g = 9.81;
    private double maxSpeed;
    private double mass = 1;

    private float vx1;
    private float vy1;
    private double slopey;
    private double slopex;
    float xChange;
    float yChange;
    float lastShotX;
    float lastShotY;

    private TiledMapTileLayer collisionLayer;

    private float collisionCoordsX;
    private float collisionCoordsY;

    FileInput FI;
    String[] mapInfo;
    //double[] splineDerivates;
    double[] splineDerivatesX;
    double[] splineDerivatesY;
    int nrOfKnots;
    final int courseSizeX = 640;
    final int courseSizeY = 480;
    final double splineIntervalSizeX;
    final double splineIntervalSizeY;

    private BicubicInterpolation interpolator;
    private double heightNew;
    private double heightOld;
    private double xNormalized;
    private double yNormalized;

    private double xNew;
    private double xOld;
    private double yNew;
    private double yOld;

    private int SlowForNrOfFrames;
    private int nrOfFramesSinceShot;

    DifferentiationCalculator DC = new DifferentiationCalculator(1, mass, frictionConstant, g);



    public PhysicsEngine(GolfBall golfBall, TiledMapTileLayer collisionLayer){
        this.golfBall = golfBall;
        this.collisionLayer = collisionLayer;

        //reading spline information from a file
        this.FI = new FileInput();
        mapInfo = new String[FI.getNrOfLines()];
        System.out.println(mapInfo[5]);
        mapInfo = FI.readMapInfo();
        nrOfKnots = Integer.parseInt(mapInfo[4]);

        splineDerivatesX = new double[nrOfKnots - 1];
        for(int i = 5; i < 5+splineDerivatesX.length; i++){ //starts reading from i = 5 because the first 5 lines in the file is other map information
            splineDerivatesX[i-5] = Double.parseDouble(mapInfo[i]);
            System.out.println(mapInfo[i]);
        }

        splineDerivatesY = new double[nrOfKnots - 1];
        for(int i = 5+splineDerivatesX.length; i < mapInfo.length; i++){
            splineDerivatesX[i-(5+splineDerivatesX.length)] = Double.parseDouble(mapInfo[i]);
            System.out.println(mapInfo[i]);
        }


        splineIntervalSizeX = courseSizeX / nrOfKnots;
        splineIntervalSizeY = courseSizeY / nrOfKnots;
    }

    /**
     * Used for bicubic spline interpolation, but we're using normal splines for now
     */
    /*
    public PhysicsEngine(GolfBall golfBall, TiledMapTileLayer collisionLayer, BicubicInterpolation interpolator) {
        this.golfBall = golfBall;
        this.collisionLayer = collisionLayer;
        this.interpolator = interpolator;
        //double splineNormalizeXCoeff = courseSizeX
    }
    */
    public void moveBall(double direction, double initialSpeed){

        float oldXCoords = golfBall.getXCoords();
        float oldYCoords = golfBall.getYCoords();

        float tileWidth = collisionLayer.getTileWidth();
        float tileHeight = collisionLayer.getTileHeight();

        boolean collisionX = false;
        boolean collisionY = false;


        ballStopped = false;


        /**
         * Used for bicubic spline interpolation, but we're using normal splines for now
         */
        /*

        xOld = xNew;
        yOld = yNew;
        heightOld = heightNew;

        xNormalized = golfBall.x * (1.0/courseSizeX);
        yNormalized = golfBall.y * (1.0/courseSizeY);

        System.out.println("xNorm: " + xNormalized);
        System.out.println("yNorm: " + yNormalized);

        xNew = golfBall.x;
        yNew = golfBall.y;
        heightNew = interpolator.findHeightXandYDimensions(xNormalized,yNormalized);
        System.out.println("heightNew: " + heightNew);

        */

        if(initialCall) {
            startTime = System.nanoTime() / 1000000000.0; //defining the value for which t = 0
            initialCall = false;

            double directionCoefficientX = (Math.cos(Math.toRadians(direction)));
            double directionCoefficientY = (Math.sin(Math.toRadians(direction)));


            golfBall.setVX2((float) (directionCoefficientX*initialSpeed));
            //System.out.println("initial vx2 " + golfBall.getVx2());
            golfBall.setVY2((float) (directionCoefficientY*initialSpeed));
            //System.out.println("initial vy2 " + golfBall.getVy2());

            lastShotX = golfBall.x;
            lastShotY = golfBall.y;
        }

        initialCall = false;


        vx1 = golfBall.getVx2();
        vy1 = golfBall.getVy2();

        if(!firstAICall)
        {
            if      (((Math.abs(vx1 + (float) findfx()) <= 20) &&
                    (-mass * g * dx() == 0) && (-mass * g * dy() == 0) &&
                    (Math.abs(vy1 + (float) findfy())) <= 20) ||
                    (ballBlocked))
            {
              //  System.out.println("check");
                golfBall.setVX2(0);
                golfBall.setVY2(0);
                ballStopped = true;
                ballBlocked = false;
                initialCall = true;
            } else {
                golfBall.setVX2(vx1 + (float) findfx());
                golfBall.setVY2(vy1 + (float) findfy());
            }
        }


        //stopping condition for the ball: the speed has been below 10 for the last 60 frames

        /*
        boolean shouldStop = false;

        if (((Math.abs(vx1 + (float) findfx()) <= 10) && ((Math.abs(vy1 + (float) findfy())) <= 10)) && !ballStopped)
            SlowForNrOfFrames++;
        else SlowForNrOfFrames = 0;


        if(SlowForNrOfFrames > 60)
            shouldStop = true;
        */


        double elapsedTime = System.nanoTime() / 1000000000.0 - startTime; //defining the value of t for the current call of render()

        //the ball is fully stopped if: its abs. speed is less than 20
        //and either the gravity inflicted on the ball is 0
        //or the ball is blocked in both x and y directions
        //AND it has been more than 3 seconds since the start of the shot

        if(!usingMethod3 && firstAICall)
        {
            DC.setParam(vx1, vy1, dx(), dy());
            double velocityX = vx1 + (float) (1)*DC.calculateDifferentiation('x');
            double velocityY = vy1 + (float) (1)*DC.calculateDifferentiation('y');

            if      ((((Math.abs(velocityX) <= 10) && ((Math.abs(velocityY)) <= 10))
                    || (((-mass * g * dx() == 0) && (-mass * g * dy() == 0))
                    || (ballBlockedX && ballBlockedY))) && elapsedTime > 3)
            {
                ballStopped = true;
                initialCall = true;
            }
            else {
                if(!ballBlockedX) golfBall.setVX2((float) velocityX);
                if(!ballBlockedY) golfBall.setVY2((float) velocityY);
            }
        }
//        System.out.println("ballStopped = " + ballStopped);


        //change in x and y during the elapsed time
         xChange = (float) ((1.0/60.0)*vx1);
         yChange = (float) ((1.0/60.0)*vy1);

        //checking if the ball collides with the squares surrounding it

        boolean colRight = false;
        boolean colTop = false;

        if(golfBall.getVx2() < 0){
            //top left

            TiledMapTileLayer.Cell collisionCellTopLeft =  collisionLayer.getCell((int) (golfBall.x / tileWidth), (int) ((golfBall.y + 2*golfBall.radius) / tileHeight));
            collisionX = collisionCellTopLeft.getTile().getProperties().containsKey("solid");

            //center left
            //if we already found it collides with top left we don't want to check again because we would set the collision variable to false
            if(!collisionX) {
                TiledMapTileLayer.Cell collisionCellCenterLeft = collisionLayer.getCell((int) (golfBall.x / tileWidth), (int) ((golfBall.y + golfBall.radius) / tileHeight));
                collisionX = collisionCellTopLeft.getTile().getProperties().containsKey("solid");
            }

            //bottom left
            //if we already found it collides with top left or center left we don't want to check again because we would set the collision variable to false
            if(!collisionX) {
                TiledMapTileLayer.Cell collisionCellBottomLeft = collisionLayer.getCell((int) (golfBall.x / tileWidth), (int) ((golfBall.y) / tileHeight));
                collisionX = collisionCellTopLeft.getTile().getProperties().containsKey("solid");
            }

        }
        else if(golfBall.getVx2() > 0){
            //top right
            colRight = true;

            TiledMapTileLayer.Cell collisionCellTopRight =  collisionLayer.getCell((int) ((golfBall.x + 2*golfBall.radius) / tileWidth), (int) ((golfBall.y + golfBall.radius) / tileHeight));
            collisionX = collisionCellTopRight.getTile().getProperties().containsKey("solid");
            //if(collisionCellTopRight.getTile() == null) collisionX = true;

            //center right
            if(!collisionX) {
                TiledMapTileLayer.Cell collisionCellCenterRight = collisionLayer.getCell((int) ((golfBall.x + golfBall.radius) / tileWidth), (int) ((golfBall.y + golfBall.radius / 2) / tileHeight));
                collisionX = collisionCellTopRight.getTile().getProperties().containsKey("solid");
            }

            //bottom right
            if(!collisionX) {
                TiledMapTileLayer.Cell collisionCellBottomRight = collisionLayer.getCell((int) (golfBall.x / tileWidth), (int) ((golfBall.y) / tileHeight));
                collisionX = collisionCellTopRight.getTile().getProperties().containsKey("solid");
            }
        }

        //if we have a collision on X, we set the balls xCoords to those of the previous frame plus or minus 10 depending
        //on which side the ball collided on (to move it "outside" of the wall), and then set its speed in x direction to 0
        if(collisionX){
            if(colRight){
                golfBall.x = oldXCoords-10;
            }
            else{
                golfBall.x = oldXCoords+10;
            }
            //colRight = false;
            golfBall.setVX2(0);
            ballBlockedX = true;
        }


        if(golfBall.getVy2() < 0) {
            //bottom left

            TiledMapTileLayer.Cell collisionCellBottomLeft = collisionLayer.getCell((int) ((golfBall.x) / tileWidth), (int) ((golfBall.y / tileHeight)));
            collisionY = collisionCellBottomLeft.getTile().getProperties().containsKey("solid");

            //bottom center
            if (!collisionY) {
                TiledMapTileLayer.Cell collisionCellBottomCenter = collisionLayer.getCell((int) ((golfBall.x + golfBall.radius) / tileWidth), (int) ((golfBall.y + golfBall.radius) / tileHeight));
                collisionY = collisionCellBottomCenter.getTile().getProperties().containsKey("solid");
            }

            //bottom right
            if (!collisionY) {
                TiledMapTileLayer.Cell collisionCellBottomRight = collisionLayer.getCell((int) ((golfBall.x + 2 * golfBall.radius) / tileWidth), (int) ((golfBall.y) / tileHeight));
                collisionY = collisionCellBottomRight.getTile().getProperties().containsKey("solid");
            }
        }
        else if(golfBall.getVy2() > 0) {
                //top left
                colTop = true;

                TiledMapTileLayer.Cell collisionCellTopLeft = collisionLayer.getCell((int) ((golfBall.x) / tileWidth), (int) ((golfBall.y + 2 * golfBall.radius) / tileHeight));
                collisionY = collisionCellTopLeft.getTile().getProperties().containsKey("solid");

                //top center
                if (!collisionY) {
                    TiledMapTileLayer.Cell collisionCellTopCenter = collisionLayer.getCell((int) ((golfBall.x + golfBall.radius) / tileWidth), (int) ((golfBall.y + 2 * golfBall.radius) / tileHeight));
                    collisionY = collisionCellTopCenter.getTile().getProperties().containsKey("solid");
                }

                //top right
                if (!collisionY) {
                    TiledMapTileLayer.Cell collisionCellTopRight = collisionLayer.getCell((int) ((golfBall.x + 2 * golfBall.radius) / tileWidth), (int) ((golfBall.y + 2 * golfBall.radius) / tileHeight));
                    collisionY = collisionCellTopRight.getTile().getProperties().containsKey("solid");
                }
            }


        if (collisionY){
            if(colTop) {
                golfBall.y = oldYCoords-10;
            }
            else{
                golfBall.y = oldYCoords+10;
            }
            golfBall.setVY2(0);
            ballBlockedY = true;
        }

        /*if(!ballBlockedX)*/ golfBall.x += xChange;
        /*if(!ballBlockedY)*/ golfBall.y += yChange;
            //z = x+y;
        //}
        //else {
        //    ballStopped = true;
        //    initialCall = true;
        //}
        //System.out.println("speedX " + golfBall.getVx2());
        //System.out.println("speedY : " + golfBall.getVy2());
      //  System.out.println("balloccX : " + golfBall.x );
        //System.out.println("possssX " +positionX());
       // System.out.println("balloccY : " + golfBall.y );
        //System.out.println("posssY " + positionY());
    }

    public float positionX(){

        return lastShotX;
    }
    public float positionY(){

        return lastShotY;
    }


   public double findfx(){
        double G;
        double H;
        double fx;
       slopex = dx();
       if(ballBlockedX) G = 0;
       else
        G = -mass * g * slopex;

        H = -frictionConstant*mass*g*(vx1/(Math.sqrt((vx1*vx1)+(vy1*vy1) + 0.000001)));
        fx = G + H;
        return fx;
    }
    public double findfy(){
        double G;
        double H;
        double fy;
        slopey = dy();
       if(ballBlockedY) G = 0;
        else
        G = -mass * g * slopey;

       H = -frictionConstant*mass*g*(vy1/(Math.sqrt((vx1*vx1)+(vy1*vy1) + 0.000001)));
        fy = G + H;
       return fy;
    }

    public double dx(){
        double d = 0;
        for(int i = 0; i < nrOfKnots-1; i++){
            if(golfBall.x > i * splineIntervalSizeX && golfBall.x < (i+1) * splineIntervalSizeX){
                d = splineDerivatesX[i];
            }
        }
        return d;
    }
    public double dy(){
        double d = 0;
        for(int i = 0; i < nrOfKnots-1; i++){
            if(golfBall.y > i * splineIntervalSizeY && golfBall.y < (i+1) * splineIntervalSizeY){
                d = splineDerivatesY[i];
            }
        }
        return d;
    }


    /**
     * Used for bicubic spline interpolation, but we're using normal splines for now
     */
    /*
    public double dx(){
        double changeInX = xNew - xOld;
        if(changeInX == 0) changeInX = 0.0001;
        double slopex = (heightNew - heightOld) / changeInX;
        return slopex;
    }

    public double dy(){
        double changeInY = yNew - yOld;
        if(changeInY == 0) changeInY = 0.0001;
        double slopey = (heightNew - heightOld) / changeInY;
        return slopey;
    }
    */

    public boolean getBallStopped(){
        return ballStopped;
    }

    public boolean getBallBlocked(){
        return ballBlocked;
    }

  /*  public static double[] differentiation(double[] arr) {
        double[] done = new double[arr.length - 1];
        for (int i = 0; i < done.length; i++) {
            done[i] = arr[i]*(arr.length-i-1);
        }
        return done;
    } */

    public static double calcAngle(double x, double y){
        y = -y;
        x = -x;
        double angle;
        angle = (Math.acos(x/(Math.sqrt(x*x + y*y))));
        angle = angle*180/Math.PI;
        if (y>=0)
            return angle;
        else
            return 360-angle;
    }
    public double aiAngle(float bx,float by,float gx,float gy){
        firstAICall =false;

        double aiDir;
        if(gx>=bx && gy>=by){
            System.out.println("top right");
            aiDir=Math.atan((gy-by)/(gx-bx));
            aiDir = Math.toDegrees(aiDir) -5;
            System.out.println("Angle: " + aiDir);
        }
        else if(gx<=bx && gy>=by){
            aiDir=Math.atan((gy-by)/(bx-gx));
            aiDir = Math.toDegrees(aiDir) +85;

        }
        else if(gx<=bx && gy<=by){
            aiDir=Math.atan((by-gy)/(bx-gx));
            aiDir = 175- Math.toDegrees(aiDir);

        }
        else{ //gx>=bx && gy<=by
            aiDir=Math.atan((by-gy)/(gx-bx));
            aiDir = 355-Math.toDegrees(aiDir);
            System.out.println("Angle: " + aiDir);
        }

        return aiDir;

    }

    public void setGravitationalForce(double gravitationalForce){
        this.g = gravitationalForce;
    }

    public void setFrictionConstant(double frictionConstant){
        this.frictionConstant = frictionConstant;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void useMethod3(boolean used){
        usingMethod3 = used;
    }

    public boolean isBallBlockedX() {
        return ballBlockedX;
    }

    public void setBallBlockedX(boolean ballBlockedX) {
        this.ballBlockedX = ballBlockedX;
    }

    public boolean isBallBlockedY() {
        return ballBlockedY;
    }

    public void setBallBlockedY(boolean ballBlockedY) {
        this.ballBlockedY = ballBlockedY;
    }

}