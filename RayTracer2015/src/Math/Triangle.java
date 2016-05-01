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

    public static Point intersect(Triangle triangle, Ray ray){
        Vector4 u, v, n;
        Vector4 dir, w0, w;
        float r, a, b;

        u = new Vector4(triangle.b,triangle.a);
        v = new Vector4(triangle.c,triangle.a);
        n = Vector4.crossProduct(u,v);

        if(n.magnitude() == 0){
            return null;
        }

        dir = ray.getU();
        w0 = new Vector4(ray.p0.getX(),ray.p0.getY(),ray.p0.getZ());
        //w0 = Vector4.substract();
        a = -(float)(Vector4.dotProduct(n,w0));
        b =  (float)Vector4.dotProduct(n,dir);

        if(Math.abs(b) < 0.00000001f){
            return null;
        }

        r = a/b;
        if(r < 0.0){
            return null;
        }

        Point in = new Point(ray.p0.getX(), ray.p0.getY(), ray.p0.getZ());
        in.x += r*dir.getX();
        in.y += r*dir.getY();
        in.z += r*dir.getZ();

        return in;
    }


}
