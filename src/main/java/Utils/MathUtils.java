package Utils;

public class MathUtils {
    public static float roundAvoid(float value, int places) {
        float scale = (float) Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }
}
