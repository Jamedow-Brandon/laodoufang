package com.jamedow.laodoufang.utils;


import java.net.InetAddress;

public final class UUIDHexGenerator {

    private static final int IP;
    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);
    private static short counter = (short) 0;
    private static UUIDHexGenerator uuidgen = new UUIDHexGenerator();

    static {
        int ipadd;
        try {
            ipadd = toInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }

    public static UUIDHexGenerator getInstance() {
        return uuidgen;
    }

    public static int toInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + (int) bytes[i];
        }
        return result;
    }

    /**
     * @return UUID
     */
    public static String getUUIDHex() {
        UUIDHexGenerator uuid = new UUIDHexGenerator();
        return uuid.generate();
    }

    protected String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    protected String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    protected int getJVM() {
        return JVM;
    }

    protected synchronized short getCount() {
        if (counter < 0) {
            counter = 0;
        }
        return counter++;
    }

    protected int getIP() {
        return IP;
    }

    protected short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    protected int getLoTime() {
        return (int) System.currentTimeMillis();
    }

    public String generate() {

        String sep = "";
        return format(getIP()) + sep + format(getJVM()) + sep + format(getHiTime()) + sep + format(getLoTime()) + sep + format(getCount());
    }

    /*
    public static void main(String[] str){
        for (int i=0;i<=100;i++){
            System.out.println(UUIDHexGenerator.getUUIDHex());
        }
    }
    */
}