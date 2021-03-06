package com.github.fastxml.benchmark;

import com.github.fastxml.util.ByteUtils;

import java.util.Random;

/**
 * Created by weager on 2016/07/20.
 */
public class Test {
    private static String s = "aiaj23ri2jnj;i23jjnef;nw;21j-reuonw;'90nfijweo'pjw09wei;n;s'01i2w;k;eqwjfijeiooijjiofdks;;lkkldsdk;ad;lsak;lajio23oiasdl;aklklaa2iiojasklk;sadfkla;fajfklklsflks;ka;fdakdkafdklskafldkflklsdfkasdklalklklkll;k;a;a;adkfl;f;k;aak;fkadf;kklfaak;ka;dfa;fkafkafafkakjadkla;fjlafafka;fkafl;afaafdfdk;ak;ladajadklakl;akfklafdkljdfkllkds;aklad;fklakla;ds";

    public static void main(String[] args) {
        Random r = new Random(100);
        byte[] bs = new byte[100000];
        for (int i = 0; i < bs.length; i++) {
            bs[i] = (byte) r.nextInt(122);
        }

        long s = System.currentTimeMillis();
        testArray(bs);
        System.out.println("array: " + (System.currentTimeMillis() - s));
        s = System.currentTimeMillis();
        testBranch(bs);
        System.out.println("branch: " + (System.currentTimeMillis() - s));

        s = System.nanoTime();
        testReadArrayThenCheckAll(bs); // faster
        System.out.println("testReadArrayThenCheckAll: " + (System.nanoTime() - s));
        s = System.nanoTime();
        testReadAndCheckOneByOne(bs);
        System.out.println("testReadAndCheckOneByOne: " + (System.nanoTime() - s));
    }

    public static void testArray(byte[] bs) {
        for (int n = 0; n < 10; n++) {
            int length = bs.length;
            for (int i = 1; i < length; i++) {
                ByteUtils.isValidTokenChar(bs[i]);
                doSomethingElse();
            }
        }
    }

    public static void testBranch(byte[] bs) {
        for (int n = 0; n < 10; n++) {
            int length = bs.length;
            for (int i = 1; i < length; i++) {
                isValidChar(bs[i]);
                doSomethingElse();
            }
        }
    }

    public static String doSomethingElse() {
        String[] ss = s.split(";");
        StringBuilder sb = new StringBuilder();
        for (String str : ss) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static boolean isValidChar(byte b) {
        return b > 0 && ((b >= 'a' && b <= 'z') || (b >= 'A' && b <= 'Z') || (b >= '0' && b <= '9')
                || b == ':' || b == '-' || b == '_' || b == '.');
    }

    public static int testReadArrayThenCheckAll(byte[] bytes) {
        int count = 0;
        for (int n = 0; n < 10; n++) {
            byte[] copyBytes = new byte[bytes.length];
            // Read all bytes
            for (int i = 0; i < bytes.length; i++) {
                copyBytes[i] = bytes[i];
            }
            // then count byte greater than 60
            for (int i = 0; i < copyBytes.length; i++) {
                if (copyBytes[i] > 60) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int testReadAndCheckOneByOne(byte[] bytes) {
        int count = 0;
        for (int n = 0; n < 10; n++) {
            byte[] copyBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                copyBytes[i] = bytes[i];
                if (copyBytes[i] > 60) {
                    count++;
                }
            }
        }
        return count;
    }
}
