package com.vsuc.seqr.utils;

import java.util.EnumMap;
import java.util.Map;

public class Result {

    private final String text;
    private final byte[] rawBytes;
    private final int numBits;
    private ResultPoint[] resultPoints;
    private final CodeFormat format;
    private Map<ResultMetadataType,Object> resultMetadata;
    private final long timestamp;

    public Result(String text,
                  byte[] rawBytes,
                  ResultPoint[] resultPoints,
                  CodeFormat format) {
        this(text, rawBytes, resultPoints, format, System.currentTimeMillis());
    }

    public Result(String text,
                  byte[] rawBytes,
                  ResultPoint[] resultPoints,
                  CodeFormat format,
                  long timestamp) {
        this(text, rawBytes, rawBytes == null ? 0 : 8 * rawBytes.length,
                resultPoints, format, timestamp);
    }

    public Result(String text,
                  byte[] rawBytes,
                  int numBits,
                  ResultPoint[] resultPoints,
                  CodeFormat format,
                  long timestamp) {
        this.text = text;
        this.rawBytes = rawBytes;
        this.numBits = numBits;
        this.resultPoints = resultPoints;
        this.format = format;
        this.resultMetadata = null;
        this.timestamp = timestamp;
    }

    /**
     * @return raw text encoded by the barcode
     */
    public String getText() {
        return text;
    }

    /**
     * @return raw bytes encoded by the barcode, if applicable, otherwise {@code null}
     */
    public byte[] getRawBytes() {
        return rawBytes;
    }

    /**
     * @return how many bits of {@link #getRawBytes()} are valid; typically 8 times its length
     * @since 3.3.0
     */
    public int getNumBits() {
        return numBits;
    }

    /**
     * @return points related to the barcode in the image. These are typically points
     *         identifying finder patterns or the corners of the barcode. The exact meaning is
     *         specific to the type of barcode that was decoded.
     */
    public ResultPoint[] getResultPoints() {
        return resultPoints;
    }

    /**
     * @return {@link CodeFormat} representing the format of the barcode that was decoded
     */
    public CodeFormat getCodeFormat() {
        return format;
    }

    /**
     * @return {@link Map} mapping {@link ResultMetadataType} keys to values. May be
     *   {@code null}. This contains optional metadata about what was detected about the barcode,
     *   like orientation.
     */
    public Map<ResultMetadataType,Object> getResultMetadata() {
        return resultMetadata;
    }

    public void putMetadata(ResultMetadataType type, Object value) {
        if (resultMetadata == null) {
            resultMetadata = new EnumMap<>(ResultMetadataType.class);
        }
        resultMetadata.put(type, value);
    }

    public void putAllMetadata(Map<ResultMetadataType,Object> metadata) {
        if (metadata != null) {
            if (resultMetadata == null) {
                resultMetadata = metadata;
            } else {
                resultMetadata.putAll(metadata);
            }
        }
    }

    public void addResultPoints(ResultPoint[] newPoints) {
        ResultPoint[] oldPoints = resultPoints;
        if (oldPoints == null) {
            resultPoints = newPoints;
        } else if (newPoints != null && newPoints.length > 0) {
            ResultPoint[] allPoints = new ResultPoint[oldPoints.length + newPoints.length];
            System.arraycopy(oldPoints, 0, allPoints, 0, oldPoints.length);
            System.arraycopy(newPoints, 0, allPoints, oldPoints.length, newPoints.length);
            resultPoints = allPoints;
        }
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return text;
    }

}
