package Math;

import Scene.Material;
import Scene.Colour;
import Scene.Shader;

/**
 * Created by lope on 5/1/2016.
 */
public class Triangle {

    Point a;
    Point b;
    Point c;
    Material material;
    Vector4 normal;

    public Triangle(Point a, Point b, Point c, Material mat){
        this.a = a;
        this.b = b;
        this.c = c;
        material = mat;

        Vector4 u = new Vector4(b,a);
        Vector4 v = new Vector4(c,a);

        normal = Vector4.crossProduct(u,v);
        normal.normalize();
    }

    public Colour callShader(Ray ray, double minT){
        Point point = ray.evaluate(minT);
        return Shader.computeColor(point,normal,material);
    }

//    public static Solutions intersect(Triangle trianle, Ray ray){
//
//    }


}
