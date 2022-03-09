package com.seqr.utils;

import com.seqr.utils.classes.BitMatrix;
import com.seqr.utils.seqrcode.SeQRWriter;

import java.util.Map;

public class MultiFormatWriter implements Writer {

    @Override
    public BitMatrix encode(String contents,
                            CodeFormat format,
                            int width,
                            int height) throws WriterException {
        return encode(contents, format, width, height, null);
    }

    @Override
    public BitMatrix encode(String contents,
                            CodeFormat format,
                            int width, int height,
                            Map<EncodeHintType,?> hints) throws WriterException {

        Writer writer;
        switch (format) {
            case SEQR_CODE:
                writer = new SeQRWriter();
                break;
            case SEQR_ENC:
                writer = new SeQRWriter();
                break;
            default:
                throw new IllegalArgumentException("No encoder available for format " + format);
        }
        return writer.encode(contents, format, width, height, hints);
    }

}
