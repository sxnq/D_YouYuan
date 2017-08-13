package baway.com.dyouyuan.core;

public class JNICore {

static{

    System.loadLibrary("core");

}
    public static native String getSign(String sign);

}
