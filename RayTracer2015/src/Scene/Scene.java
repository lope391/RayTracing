/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene;

import java.util.ArrayList;
import Math.Sphere;
import Math.Triangle;
import Math.Ray;
import Math.Solutions;
import Math.Vector4;
import Math.Point;

/**
 *
 * @author htrefftz
 */
public class Scene {

    // Ambient light, comes from "everywhere"
    static AmbientLight ambientLight;

    // Several Point Lights
    static ArrayList<PointLight> pointLights = new ArrayList<>();

    // Several Spheres
    static ArrayList<Sphere> spheres = new ArrayList<>();

    //Several Triangles
    static ArrayList<Triangle> triangles = new ArrayList<>();

    // Bacground color
    //static final Colour BACKGROUNDCOLOR = new Colour(0.7, 0.7, 0.9);

    static final Colour BACKGROUNDCOLOR = new Colour(0.1, 0.1, 0.1);
    //static final Colour BACKGROUNDCOLOR = new Colour(.1, 0.74 , 1);
    
    /**
     * Set the ambient light in the scene
     * @param myAmbientLight Ambient light to set
     */
    public static void setAmbientLight(AmbientLight myAmbientLight) {
        ambientLight = myAmbientLight;
    }
    
    /**
     * Add a point light
     * @param myPointLight Point Light to be added 
     */
    public static void addPointLight(PointLight myPointLight) {
        pointLights.add(myPointLight);
    }
    
    /**
     * Add a Sphere
     * @param mySphere Sphere to be added
     */
    public static void addSphere(Sphere mySphere) {
        spheres.add(mySphere);
    }

    /**
     * Compute the color of the closest object in the Scene this ray 
     * intersects with
     * @param ray Ray to intersect with the Scene
     * @return 
     */
    public static Colour intersectRay(Ray ray) {

        double minT = Double.MAX_VALUE;
        Sphere closestS = null;
        Triangle closestT = null;

        // Chose the closest surface
        for(Sphere sphere: spheres) {
            Solutions s = Sphere.intersect(sphere, ray);

            if(s.getNumSolutions() > 0) {

                // ignore solutins with a t value smaller than 0,
                // because they are behind the origin of the ray
                if(s.getT1() > 0.01) {

                    // Is this surface closer to the origin of the ray?
                    if(s.getT1() < minT) {
                        minT = s.getT1();
                        closestS = sphere;
                    }
                }
            }
        }

        if(closestS != null) {
            Colour acum  = new Colour(0, 0, 0);
            double Ko = closestS.getMaterial().Ko;
            double Kr = closestS.getMaterial().Kr;
            double Kt = closestS.getMaterial().Kt;

            // Compute this oject's color
            if(Ko != 0) {
                Colour thisColor = Colour.multiply(closestS.callShader(ray, minT),Ko);
                acum = Colour.add(acum, thisColor);
            }

            // Compute the reflection
            if(Kr != 0) {

                // Find the normal at the solution
                Point p = ray.evaluate(minT);
                Vector4 normal = closestS.computeNormal(p);

                // create the reflection ray (reflectedRay)
                Vector4 direction = Vector4.reflection(ray.getU(), normal);
                Ray reflectedRay = new Ray(p, direction);

                // send the ray to intersect with objects in the scene (Scene.intersectRay(reflectedRay))
                // (this is where recursion takes place)
                Colour reflectedColor = Scene.intersectRay(reflectedRay);

                // multiply the color by the corresponding Weight (Kr)
                reflectedColor = Colour.multiply(reflectedColor, Kr);

                // and add the color to acum
                acum = Colour.add(acum, reflectedColor);
            }

            return acum;
        }

        for(Triangle triangle: triangles){

        }

        return BACKGROUNDCOLOR;
    }
}
