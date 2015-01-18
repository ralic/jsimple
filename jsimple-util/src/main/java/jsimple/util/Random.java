/*
 * Copyright (c) 2012-2015 Microsoft Mobile.  All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * This file is based on or incorporates material from Apache Harmony
 * http://harmony.apache.org (collectively, "Third Party Code"). Microsoft Mobile
 * is not the original author of the Third Party Code. The original copyright
 * notice and the license, under which Microsoft Mobile received such Third Party
 * Code, are set forth below.
 *
 *
 * Copyright 2006, 2010 The Apache Software Foundation.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jsimple.util;


/**
 * This class provides methods that generates pseudo-random numbers of different
 * types, such as {@code int}, {@code long}, {@code double}, and {@code float}.
 * 
 * @see Properties
 * @see PropertyResourceBundle
 */
public class Random {
    private static final long multiplier = 0x5deece66dL;

    /**
     * The boolean value indicating if the second Gaussian number is available.
     * 
     * @serial
     */
    private boolean haveNextNextGaussian;

    /**
     * @serial It is associated with the internal state of this generator.
     */
    private long seed;

    /**
     * The second Gaussian generated number.
     * 
     * @serial
     */
    private double nextNextGaussian;

    /**
     * Construct a random generator with the current time of day in milliseconds
     * as the initial state.
     * 
     * @see #setSeed
     */
    public Random() {
        setSeed(PlatformUtils.getCurrentTimeMillis() + hashCode());
    }

    /**
     * Construct a random generator with the given {@code seed} as the
     * initial state.
     * 
     * @param seed
     *            the seed that will determine the initial state of this random
     *            number generator.
     * @see #setSeed
     */
    public Random(long seed) {
        setSeed(seed);
    }

    /**
     * Returns a pseudo-random uniformly distributed {@code int} value of
     * the number of bits specified by the argument {@code bits} as
     * described by Donald E. Knuth in <i>The Art of Computer Programming,
     * Volume 2: Seminumerical Algorithms</i>, section 3.2.1.
     * 
     * @param bits
     *            number of bits of the returned value.
     * @return a pseudo-random generated int number.
     * @see #nextBytes
     * @see #nextDouble
     * @see #nextFloat
     * @see #nextInt()
     * @see #nextInt(int)
     * @see #nextLong
     */
    protected synchronized int next(int bits) {
        seed = (seed * multiplier + 0xbL) & ((1L << 48) - 1);
        return (int) (seed >>> (48 - bits));
    }

    /**
     * Returns the next pseudo-random, uniformly distributed {@code boolean} value
     * generated by this generator.
     * 
     * @return a pseudo-random, uniformly distributed boolean value.
     */
    public boolean nextBoolean() {
        return next(1) != 0;
    }

    /**
     * Modifies the {@code byte} array by a random sequence of {@code byte}s generated by this
     * random number generator.
     * 
     * @param buf
     *            non-null array to contain the new random {@code byte}s.
     * @see #next
     */
    public void nextBytes(byte[] buf) {
        int rand = 0, count = 0, loop = 0;
        while (count < buf.length) {
            if (loop == 0) {
                rand = nextInt();
                loop = 3;
            } else {
                loop--;
            }
            buf[count++] = (byte) rand;
            rand >>= 8;
        }
    }

    /**
     * Generates a normally distributed random {@code double} number between 0.0
     * inclusively and 1.0 exclusively.
     * 
     * @return a random {@code double} in the range [0.0 - 1.0)
     * @see #nextFloat
     */
    public double nextDouble() {
        return ((((long) next(26) << 27) + next(27)) / (double) (1L << 53));
    }

    /**
     * Generates a normally distributed random {@code float} number between 0.0
     * inclusively and 1.0 exclusively.
     * 
     * @return float a random {@code float} number between [0.0 and 1.0)
     * @see #nextDouble
     */
    public float nextFloat() {
        return (next(24) / 16777216f);
    }

    /**
     * Generates a uniformly distributed 32-bit {@code int} value from
     * the random number sequence.
     * 
     * @return a uniformly distributed {@code int} value.
     * @see java.lang.Integer#MAX_VALUE
     * @see java.lang.Integer#MIN_VALUE
     * @see #next
     * @see #nextLong
     */
    public int nextInt() {
        return next(32);
    }

    /**
     * Returns a new pseudo-random {@code int} value which is uniformly distributed
     * between 0 (inclusively) and the value of {@code n} (exclusively).
     * 
     * @param n
     *            the exclusive upper border of the range [0 - n).
     * @return a random {@code int}.
     */
    public int nextInt(int n) {
        if (n > 0) {
            if ((n & -n) == n) {
                return (int) ((n * (long) next(31)) >> 31);
            }
            int bits, val;
            do {
                bits = next(31);
                val = bits % n;
            } while (bits - val + (n - 1) < 0);
            return val;
        }
        throw new ProgrammerError("Invalid negative argument to nextInt");
    }

    /**
     * Generates a uniformly distributed 64-bit integer value from
     * the random number sequence.
     * 
     * @return 64-bit random integer.
     * @see java.lang.Integer#MAX_VALUE
     * @see java.lang.Integer#MIN_VALUE
     * @see #next
     * @see #nextInt()
     * @see #nextInt(int)
     */
    public long nextLong() {
        return ((long) next(32) << 32) + next(32);
    }

    /**
     * Modifies the seed a using linear congruential formula presented in <i>The
     * Art of Computer Programming, Volume 2</i>, Section 3.2.1.
     * 
     * @param seed
     *            the seed that alters the state of the random number generator.
     * @see #next
     * @see #Random()
     * @see #Random(long)
     */
    public synchronized void setSeed(long seed) {
        this.seed = (seed ^ multiplier) & ((1L << 48) - 1);
        haveNextNextGaussian = false;
    }
}
