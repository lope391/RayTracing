/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Math;

import Scene.Material;
import Scene.Colour;
import Scene.Shader;

/**
 *
 * @author htrefftz
 */
public class Sphere {
    Point center;
    double radius;
    Material material;
    /**
     * Constructor
     * @param center Center of the Sphere
     * @param radius Radius of the Sphere
     * @param material Material of the Sphere
     */
    public Sphere(Point center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }
    
    /**
     * Intersect a Sphere with a ray. Returns the t value(s) for the ray at the solution(s)
     * (if any).
     * @param sphere Sphere to intersect the Ray with
     * @param ray Ray on intersect the Sphere with
     * @return Solutions. May be 0, 1 or 2 solutions
     */
    public static Solutions intersect(Sphere sphere, Ray ray) {

        double a = Vector4.dotProduct(ray.u, ray.u);

        Vector4 centerOrigin = new Vector4(sphere.center, ray.p0);

        double b = 2 * (Vector4.dotProduct(centerOrigin,ray.u));
        double c = Vector4.dotProduct(centerOrigin, centerOrigin) - sphere.radius * sphere.radius;
        double det = b*b - 4*a*c;

        if(det < 0) {
            // No solutions
            return new Solutions(0, 0, 0);
        } else if (det > 0) {
            // Two solutions
            double sol1 = (-b - Math.sqrt(det))/(2*a);
            double sol2 = (-b + Math.sqrt(det))/(2*a);
            return new Solutions(2, sol1, sol2);
        } else {
            // One solution
            double sol = (-b) / (2*a);
            return new Solutions(1, sol, 0);
        }
    }
    
    /**
     * Returns the normal of the sphere at point p.
     * Point p is assumed to be at the surface of the sphere
     * @param p point at the surface
     * @return normal at point p
     */
    public Vector4 computeNormal(Point p) {
        Vector4 normal = new Vector4(center, p);
        normal.normalize();
        return normal;
    }
    
    /**
     * Call the shader to determine the color a the point of intersection
     * determined by the ray and parameter minT
     * @param ray ray that determines the point
     * @param minT value of parameter t
     * @return Colour determined by the shader for this point
     */
    public Colour callShader(Ray ray, double minT) {
        Point point = ray.evaluate(minT);
        Vector4 normal = new Vector4(center, point);
        normal.normalize();
        return Shader.computeColor(point, normal, material);
    }

    public Material getMaterial() {
        return material;
    }    
    
    @Override
    public String toString() {
        return "Sphere{" + "center=" + center + ", radius=" + radius + '}';
    }
    
    /**
     * Test main program
     * @param args 
     */
    public static void main(String [] args) {
        Material m = new Material();
        Sphere sphere1 = new Sphere(new Point(0, 0, -100d), 50d, m);
        Ray ray1 = new Ray(new Point(0, 0, 0), new Point(0, 0, -10));
        System.out.println(Sphere.intersect(sphere1, ray1));
        Ray ray2 = new Ray(new Point(50, 0, 0), new Point(50, 0, -10));
        System.out.println(Sphere.intersect(sphere1, ray2));
        Ray ray3 = new Ray(new Point(100, 0, 0), new Point(100, 0, -10));
        System.out.println(Sphere.intersect(sphere1, ray3));
        Point p = new Point(Math.sin(Math.PI / 4d), Math.cos(Math.PI / 4d), -100);
        System.out.println(sphere1.computeNormal(p));
    }
}
