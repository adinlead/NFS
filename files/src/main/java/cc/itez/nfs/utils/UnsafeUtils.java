package cc.itez.nfs.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeUtils {
    private static final Unsafe UNSAFE = getUnsafe();

    private static Unsafe getUnsafe() {
        Field field = null;
        try {
            field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    private static long normalize(int value) {
        if (value >= 0) return value;
        return (~0L >>> 32) & value;
    }

    public static long getAddress(Object obj) {
        if (UNSAFE == null) {
            return -1;
        }
        Object[] array = new Object[]{obj};
        long baseOffset = UNSAFE.arrayBaseOffset(Object[].class);
        return normalize(UNSAFE.getInt(array, baseOffset));
    }

    public static String getHexAddress(Object obj){
        return "0x" + Long.toHexString(getAddress(obj)).toUpperCase();
    }
}
