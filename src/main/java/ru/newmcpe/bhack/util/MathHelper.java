package ru.newmcpe.bhack.util;

/**
 * Created by Babbaj on 12/5/2017.
 */
public class MathHelper {

    public static float clamp(float val, float min, float max) {
        if (val < min) return min;
        if (val > max) return max;
        return val;
    }

    public static float normalizeAngle(float a) {
        while (a > 180f) a -= 360f;
        while (a < -180f) a += 360f;
        return a;
    }

    public static double normalizeAngle(double angle) {
        while (angle <= -180) angle += 360;
        while (angle > 180) angle -= 360;
        return angle;
    }



    public static float distanceBetweenPoints(Vector a, Vector b) {
        double diffX = b.x - a.x;
        double diffY = b.y - a.y;
        float distance = (float) Math.sqrt(diffX*diffX + diffY*diffY);
        return distance;
    }

    public static float differenceBetweenAngles(final float ang1, final float ang2) {
        return Math.abs(((ang1 - ang2 + 180) % 360 + 360) % 360 - 180);
    }
}
