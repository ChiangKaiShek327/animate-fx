package io.github.chiangkaishek327.animated.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.awt.image.BufferedImage;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class SVGLoader {

    public static Image loadSVGImage(String url) throws IOException, TranscoderException {
        return loadSVGImage(new URL(url));
    }

    public static Image loadSVGImage(URL url) throws IOException, TranscoderException {
        return loadSVGImage(url.openStream());
    }

    /**
     * 
     * @param inputStream inputStream what has svg file content
     * @return
     * @throws IOException
     * @throws TranscoderException
     */
    public static Image loadSVGImage(InputStream inputStream) throws IOException, TranscoderException {
        BufferedImageTranscoder trans = new BufferedImageTranscoder();
        trans.createImage(100, 100);
        TranscoderInput transIn = new TranscoderInput(inputStream);

        trans.transcode(transIn, null);
        Image img = SwingFXUtils.toFXImage(trans.getBufferedImage(), null);
        return img;

    }

    public static class BufferedImageTranscoder extends ImageTranscoder {

        private BufferedImage img = null;

        @Override
        public BufferedImage createImage(int width, int height) {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            return bi;
        }

        @Override
        public void writeImage(BufferedImage img, TranscoderOutput to) throws TranscoderException {
            this.img = img;
        }

        public BufferedImage getBufferedImage() {
            return img;
        }
    }
}
