package com.vsuc.seqr.utils.classes.reedsolomon;

public final class GenericGF {

    public static final GenericGF AZTEC_DATA_12 = new GenericGF(0x1069, 4096, 1); // x^12 + x^6 + x^5 + x^3 + 1
    public static final GenericGF AZTEC_DATA_10 = new GenericGF(0x409, 1024, 1); // x^10 + x^3 + 1
    public static final GenericGF AZTEC_DATA_6 = new GenericGF(0x43, 64, 1); // x^6 + x + 1
    public static final GenericGF AZTEC_PARAM = new GenericGF(0x13, 16, 1); // x^4 + x + 1
    public static final GenericGF QR_CODE_FIELD_256 = new GenericGF(0x011D, 256, 0); // x^8 + x^4 + x^3 + x^2 + 1
    public static final GenericGF DATA_MATRIX_FIELD_256 = new GenericGF(0x012D, 256, 1); // x^8 + x^5 + x^3 + x^2 + 1
    public static final GenericGF AZTEC_DATA_8 = DATA_MATRIX_FIELD_256;
    public static final GenericGF MAXICODE_FIELD_64 = AZTEC_DATA_6;

    private final int[] expTable;
    private final int[] logTable;
    private final GenericGFPoly zero;
    private final GenericGFPoly one;
    private final int size;
    private final int primitive;
    private final int generatorBase;

    /**
     * Create a representation of GF(size) using the given primitive polynomial.
     *
     * @param primitive irreducible polynomial whose coefficients are represented by
     *  the bits of an int, where the least-significant bit represents the constant
     *  coefficient
     * @param size the size of the field
     * @param b the factor b in the generator polynomial can be 0- or 1-based
     *  (g(x) = (x+a^b)(x+a^(b+1))...(x+a^(b+2t-1))).
     *  In most cases it should be 1, but for QR code it is 0.
     */
    public GenericGF(int primitive, int size, int b) {
        this.primitive = primitive;
        this.size = size;
        this.generatorBase = b;

        expTable = new int[size];
        logTable = new int[size];
        int x = 1;
        for (int i = 0; i < size; i++) {
            expTable[i] = x;
            x *= 2; // we're assuming the generator alpha is 2
            if (x >= size) {
                x ^= primitive;
                x &= size - 1;
            }
        }
        for (int i = 0; i < size - 1; i++) {
            logTable[expTable[i]] = i;
        }
        // logTable[0] == 0 but this should never be used
        zero = new GenericGFPoly(this, new int[]{0});
        one = new GenericGFPoly(this, new int[]{1});
    }

    GenericGFPoly getZero() {
        return zero;
    }

    GenericGFPoly getOne() {
        return one;
    }

    /**
     * @return the monomial representing coefficient * x^degree
     */
    GenericGFPoly buildMonomial(int degree, int coefficient) {
        if (degree < 0) {
            throw new IllegalArgumentException();
        }
        if (coefficient == 0) {
            return zero;
        }
        int[] coefficients = new int[degree + 1];
        coefficients[0] = coefficient;
        return new GenericGFPoly(this, coefficients);
    }

    /**
     * Implements both addition and subtraction -- they are the same in GF(size).
     *
     * @return sum/difference of a and b
     */
    static int addOrSubtract(int a, int b) {
        return a ^ b;
    }

    /**
     * @return 2 to the power of a in GF(size)
     */
    int exp(int a) {
        return expTable[a];
    }

    /**
     * @return base 2 log of a in GF(size)
     */
    int log(int a) {
        if (a == 0) {
            throw new IllegalArgumentException();
        }
        return logTable[a];
    }

    /**
     * @return multiplicative inverse of a
     */
    int inverse(int a) {
        if (a == 0) {
            throw new ArithmeticException();
        }
        return expTable[size - logTable[a] - 1];
    }

    /**
     * @return product of a and b in GF(size)
     */
    int multiply(int a, int b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        return expTable[(logTable[a] + logTable[b]) % (size - 1)];
    }

    public int getSize() {
        return size;
    }

    public int getGeneratorBase() {
        return generatorBase;
    }

    @Override
    public String toString() {
        return "GF(0x" + Integer.toHexString(primitive) + ',' + size + ')';
    }

}
