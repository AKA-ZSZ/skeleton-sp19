public class NBody {
    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Body[] bodies = readBodies(filename);
        double initT = 0;

        while (initT < T) {

            double[] xForces = new double[bodies.length];
            double[] yForces = new double[bodies.length];

            int i = 0;
            while (i < bodies.length) {
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
                i++;
            }

            i = 0;
            while (i < bodies.length) {
                bodies[i].update(dt, xForces[i], yForces[i]);
                i++;
            }

            StdDraw.setXscale(-radius, radius);
            StdDraw.setYscale(-radius, radius);
            StdDraw.picture(0, 0, "images/starfield.jpg");
            StdDraw.enableDoubleBuffering();

            i = 0;
            while (i < bodies.length) {
                bodies[i].draw();
                i++;
            }

            StdDraw.show();
            StdDraw.pause(10);
            initT += dt;
        }

        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }

    }

    public static double readRadius(String filename) {
        In in = new In(filename);
        int line = 0;
        double radius = 0;
        while (line < 2) {
            radius = in.readDouble();
            line++;
        }

        return radius;

    }

    public static Body[] readBodies(String filename) {
        In in = new In(filename);
        int length = in.readInt();
        // skip radius line
        in.readDouble();
        Body[] bs = new Body[5];

        int i = 0;
        while (!in.isEmpty() && i < length) {
            Body b = new Body(0, 0, 0, 0, 0, "");

            b.xxPos = in.readDouble();
            b.yyPos = in.readDouble();
            b.xxVel = in.readDouble();
            b.yyVel = in.readDouble();
            b.mass = in.readDouble();
            b.imgFileName = in.readString();

            bs[i] = b;
            i++;
        }
        return bs;

    }
}
