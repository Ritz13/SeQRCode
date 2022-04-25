package com.vsuc.seqr.core;

import com.beust.jcommander.JCommander;
import com.vsuc.seqr.utils.EncodeHintType;
import com.vsuc.seqr.utils.MultiFormatWriter;
import com.vsuc.seqr.utils.TriSeparator;
import com.vsuc.seqr.utils.classes.BitMatrix;

import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public final class CommandLineEncoder {

    private CommandLineEncoder() {
    }

    public static void main(String[] args) throws Exception {
        EncoderConfig config = new EncoderConfig();
        JCommander jCommander = new JCommander(config);
        jCommander.parse(args);
        jCommander.setProgramName(CommandLineEncoder.class.getSimpleName());
        if (config.help) {
            jCommander.usage();
            return;
        }

        String outFileString = config.outputFileBase;
        if (EncoderConfig.DEFAULT_OUTPUT_FILE_BASE.equals(outFileString)) {
            outFileString += '.' + config.imageFormat.toLowerCase(Locale.ENGLISH);
        }
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        if (config.errorCorrectionLevel != null) {
            hints.put(EncodeHintType.ERROR_CORRECTION, config.errorCorrectionLevel);
        }

        // Tri-Separate Plaintext
        String[] triText = new TriSeparator(config.contents.get(0)).separateText();
        BitMatrix[] matrices = new BitMatrix[3];
        for(int i=0; i<3; i++) {
            // Encode the plaintext to bit matrix
            matrices[i] = new MultiFormatWriter().encode(
                    triText[i], config.codeFormat, config.width,
                    config.height, hints);
        }

        // bit matrix to image
        MatrixToImageWriter.writeToPath(matrices, config.imageFormat,
                Paths.get(outFileString));
    }

}
