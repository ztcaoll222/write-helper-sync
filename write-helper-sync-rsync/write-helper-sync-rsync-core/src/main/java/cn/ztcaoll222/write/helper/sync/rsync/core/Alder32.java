package cn.ztcaoll222.write.helper.sync.rsync.core;

import cn.ztcaoll222.write.helper.sync.common.ObjectUtil;

/**
 * https://en.wikipedia.org/wiki/Adler-32
 *
 * @author ztcaoll222
 * Create time: 2019/9/1 14:28
 */
public class Alder32 {
    /**
     * largest prime smaller than 65536
     */
    private static final int BASE = 65521;

    /**
     * Largest value n such that 255n(n+1)/2 + (n+1)(BASE-1) <= 2^32-1
     *
     * NMAX is just how often modulo needs to be taken of the two checksum word halves to prevent overflowing a 32 bit nteger.
     * This is an optimization. We "could" take the modulo after each byte, and it must be taken before each
     * digest
     */
    private static final int NMAX = 5552;

    public static long sum(byte[] bytes, long adler) {
        if (!ObjectUtil.checkObj(bytes)) {
            return 1L;
        }

        long a = adler & 0xffff;
        long b = (adler >>> 16) & 0xffff;
        int len = bytes.length;
        int n;
        int i = 0;

        while (len > 0) {
            n = Math.min(len, NMAX);
            len -= n;
            do {
                a += bytes[i++];
                b += a;
                --n;
            } while (n > 0);

            a &= BASE;
            b &= BASE;
        }

        return ((b << 16) | a);
    }

    public static long sum(byte[] bytes) {
        return sum(bytes, 1);
    }

    public static long roll(long adler, int len, byte c1, byte c2) {
        long a = adler & 0xffff;
        long b = (adler >>> 16) & 0xffff;

        a = (a - c2 + c1 + BASE) % BASE;
        b = (b - ((len * c2) % BASE) + a - 1 + BASE) % BASE;

        return ((b << 16) | a);
    }
}
