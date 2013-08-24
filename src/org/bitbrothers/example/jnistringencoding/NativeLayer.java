package org.bitbrothers.example.jnistringencoding;

public class NativeLayer {

    public static native byte[] convertStringToByteArray(String str);

    public static native String convertByteArrayToString(byte[] array);
}
