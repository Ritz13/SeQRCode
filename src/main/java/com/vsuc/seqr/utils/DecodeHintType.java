package com.vsuc.seqr.utils;

import java.util.List;

public enum DecodeHintType {

    /**
     * Unspecified, application-specific hint. Maps to an unspecified {@link Object}.
     */
    OTHER(Object.class),

    /**
     * Image is a pure monochrome image of a barcode. Doesn't matter what it maps to;
     * use {@link Boolean#TRUE}.
     */
    PURE_BARCODE(Void.class),

    /**
     * Image is known to be of one of a few possible formats.
     * Maps to a {@link List} of {@link CodeFormat}s.
     */
    POSSIBLE_FORMATS(List.class),

    /**
     * Spend more time to try to find a barcode; optimize for accuracy, not speed.
     * Doesn't matter what it maps to; use {@link Boolean#TRUE}.
     */
    TRY_HARDER(Void.class),

    /**
     * Specifies what character encoding to use when decoding, where applicable (type String)
     */
    CHARACTER_SET(String.class),

    /**
     * Allowed lengths of encoded data -- reject anything else. Maps to an {@code int[]}.
     */
    ALLOWED_LENGTHS(int[].class),

    /**
     * Assume Code 39 codes employ a check digit. Doesn't matter what it maps to;
     * use {@link Boolean#TRUE}.
     */
    ASSUME_CODE_39_CHECK_DIGIT(Void.class),

    /**
     * Assume the barcode is being processed as a GS1 barcode, and modify behavior as needed.
     * For example this affects FNC1 handling for Code 128 (aka GS1-128). Doesn't matter what it maps to;
     * use {@link Boolean#TRUE}.
     */
    ASSUME_GS1(Void.class),

    /**
     * If true, return the start and end digits in a Codabar barcode instead of stripping them. They
     * are alpha, whereas the rest are numeric. By default, they are stripped, but this causes them
     * to not be. Doesn't matter what it maps to; use {@link Boolean#TRUE}.
     */
    RETURN_CODABAR_START_END(Void.class),


    /**
     * Allowed extension lengths for EAN or UPC barcodes. Other formats will ignore this.
     * Maps to an {@code int[]} of the allowed extension lengths, for example [2], [5], or [2, 5].
     * If it is optional to have an extension, do not set this hint. If this is set,
     * and a UPC or EAN barcode is found but an extension is not, then no result will be returned
     * at all.
     */
    ALLOWED_EAN_EXTENSIONS(int[].class),

    /**
     * If true, also tries to decode as inverted image. All configured decoders are simply called a
     * second time with an inverted image. Doesn't matter what it maps to; use {@link Boolean#TRUE}.
     */
    ALSO_INVERTED(Void.class),

    // End of enumeration values.
    ;

    /**
     * Data type the hint is expecting.
     * Among the possible values the {@link Void} stands out as being used for
     * hints that do not expect a value to be supplied (flag hints). Such hints
     * will possibly have their value ignored, or replaced by a
     * {@link Boolean#TRUE}. Hint suppliers should probably use
     * {@link Boolean#TRUE} as directed by the actual hint documentation.
     */
    private final Class<?> valueType;

    DecodeHintType(Class<?> valueType) {
        this.valueType = valueType;
    }

}
