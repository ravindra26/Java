package com.service1.controllers;

import com.google.gson.Gson;
import com.service1.pojo.ImageCompressionPacket;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ravindra
 * @since 2020-10-17
 */
@RestController
public class ImagesController extends BaseController {

    Logger logger = Logger.getLogger("myLogger");

    /**
     *
     */
    public static String TMP_LOCATION = "/tmp/";

    @RequestMapping(value = "compressImage", method = RequestMethod.POST)
    @ResponseBody
    public String getCompressedImage(HttpServletRequest request, @RequestBody ImageCompressionPacket packet) throws FileNotFoundException, IOException {
        logger.info(new Gson().toJson(packet.getCompressinFactor()));
        byte[] data = Base64.getDecoder().decode(packet.getBase64());
        String filePath = TMP_LOCATION + "image." + packet.getImageExtension();
        try (OutputStream stream = new FileOutputStream(filePath)) {
            stream.write(data);
        }

        File input = new File(filePath);
        BufferedImage image = ImageIO.read(input);

        File compressedImageFile = new File("/tmp/compress." + packet.getImageExtension());
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(packet.getImageExtension());
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        // Check if canWriteCompressed is true
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(Float.parseFloat(packet.getCompressinFactor()));
        }
        // End of check
        writer.write(null, new IIOImage(image, null, null), param);

        FileInputStream fis = null;
        byte[] bArray = new byte[(int) compressedImageFile.length()];
        try {
            fis = new FileInputStream(compressedImageFile);
            fis.read(bArray);
            fis.close();
        } catch (IOException ioExp) {
            ioExp.printStackTrace();
        }
        String encodedfile = new String(Base64.getEncoder().encode(bArray), "UTF-8");

        //input.delete();
        return encodedfile;
    }

    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> uploadAndCompressFile(HttpServletRequest request, @RequestParam MultipartFile file, @RequestParam String name,@RequestParam String imageExtension,@RequestParam String compressionFactor) throws FileNotFoundException, IOException {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream
                        = new BufferedOutputStream(new FileOutputStream(new File(TMP_LOCATION+name)));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
                ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
            }
        }
        File input = new File(TMP_LOCATION+name);
        
         BufferedImage image = ImageIO.read(input);

        File compressedImageFile = new File("/tmp/compress." + imageExtension);
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(imageExtension);
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();
        // Check if canWriteCompressed is true
        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(Float.parseFloat(compressionFactor));
        }
        // End of check
        writer.write(null, new IIOImage(image, null, null), param);

        FileInputStream fis = null;
        byte[] bArray = new byte[(int) compressedImageFile.length()];
        try {
            fis = new FileInputStream(compressedImageFile);
            fis.read(bArray);
            fis.close();
        } catch (IOException ioExp) {
            ioExp.printStackTrace();
        }
        String encodedfile = new String(Base64.getEncoder().encode(bArray), "UTF-8");
        logger.info(encodedfile);

        input.delete();
        return ResponseEntity.status(HttpStatus.OK).body(encodedfile);
        
    }
}
