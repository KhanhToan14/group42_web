package com.web.recruitment.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageUtils {
    private ImageUtils() {
        throw new IllegalStateException("Image Utility class");
    }

    public static String resize(String imageBase64) throws IOException {
        String[] imageComponent = imageBase64.split(",");
        String imageType = imageComponent[0];

        if (imageType.equals("data:image/svg+xml;base64")) {
            return imageBase64;
        }

        String type;
        if (imageType.equals("data:image/png;base64")) {
            type = "png";
        } else if (imageType.equals("data:image/jpeg;base64")) {
            type = "jpeg";
        } else {
            return null;
        }

        String base64 = imageComponent[1];
        byte[] imageByte = Base64.getDecoder().decode(base64);
        BufferedImage image;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {
            image = ImageIO.read(bis);
        }


        BufferedImage resizeImage = new BufferedImage(32, 32, image.getType());
        var g = resizeImage.createGraphics();
        g.drawImage(image, 0, 0, 32, 32, null);
        g.dispose();

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ImageIO.write(resizeImage, type, bos);
            String resizeBase64 = Base64.getEncoder().encodeToString(bos.toByteArray());
            return imageType + "," + resizeBase64;
        }
    }
}
