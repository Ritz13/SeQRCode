package com.seqr.core;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.validators.PositiveInteger;
import com.seqr.utils.CodeFormat;

final class EncoderConfig {

    static final String DEFAULT_OUTPUT_FILE_BASE = "out";

    @Parameter(names = "--code_format",
            description = "Format to encode, from CodeFormat class")
    CodeFormat codeFormat = CodeFormat.SEQR_CODE;

    @Parameter(names = "--image_format",
            description = "Image output format, such as PNG, JPG")
    String imageFormat = "PNG";

    @Parameter(names = "--output",
            description = "File to write to. Defaults to out.png")
    String outputFileBase = DEFAULT_OUTPUT_FILE_BASE;

    @Parameter(names = "--width",
            description = "Image width",
            validateWith = PositiveInteger.class)
    int width = 800;

    @Parameter(names = "--height",
            description = "Image height",
            validateWith = PositiveInteger.class)
    int height = 800;

    @Parameter(names = "--error_correction_level",
            description = "Error correction level for the encoding")
    String errorCorrectionLevel = null;

    @Parameter(names = "--help",
            description = "Prints this help message",
            help = true)
    boolean help;

    @Parameter(description = "(Text to encode)", required = true)
    List<String> contents;

}
