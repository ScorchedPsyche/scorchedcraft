package com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives;

public class MathUtil {
    public static double distanceBetween3dCoordinates(float x0, float y0, float z0, float x1, float y1, float z1)
    {
        return Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2) + Math.pow(z0 - z1, 2));
    }
    
    public static int clamp(int value, int min, int max)
    {
        if (value < min){
            return min;
        }
        else if (value > max){
            return max;
        }
        
        return value;
    }
    
    public static float clamp(float value, float min, float max)
    {
        if (value < min){
            return min;
        }
        else if (value > max){
            return max;
        }
        
        return value;
    }
}
