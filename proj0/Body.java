public class Body {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    public static double G = 6.67e-11;

    public Body(double xP, double yP, double xV,
            double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Body(Body b) {
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Body b) {
        double xDist = Math.pow(xxPos - b.xxPos, 2);
        double yDist = Math.pow(yyPos - b.yyPos, 2);
        double dist = Math.sqrt(xDist + yDist);
        return dist;
    }

    public double calcForceExertedBy(Body b) {
        double f = (G * mass * b.mass) / Math.pow(calcDistance(b), 2);
        return f;
    }

    public double calcForceExertedByX(Body b) {
        double f = calcForceExertedBy(b);
        double dx = b.xxPos - xxPos;
        double fx = f * dx / calcDistance(b);
        return fx;
    }

    public double calcForceExertedByY(Body b) {
        double f = calcForceExertedBy(b);
        double dy = b.yyPos - yyPos;
        double fy = f * dy / calcDistance(b);
        return fy;
    }

    public double calcNetForceExertedByX(Body[] bs) {
        double fxnet = 0;
        for (int i = 0; i < bs.length; i++) {
            if (!bs[i].equals(this)) {
                fxnet += calcForceExertedByX(bs[i]);
            }
        }
        return fxnet;
    }

    public double calcNetForceExertedByY(Body[] bs) {
        double fynet = 0;
        for (int i = 0; i < bs.length; i++) {
            if (!bs[i].equals(this)) {
                fynet += calcForceExertedByY(bs[i]);
            }
        }
        return fynet;
    }

    public void update(double dt, double fxnet, double fynet) {
        double axnet = fxnet / this.mass;
        double aynet = fynet / this.mass;
        this.xxVel += dt * axnet;
        this.yyVel += dt * aynet;
        this.xxPos += dt * this.xxVel;
        this.yyPos += dt * this.yyVel;
        return;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
        return;
    }
}
