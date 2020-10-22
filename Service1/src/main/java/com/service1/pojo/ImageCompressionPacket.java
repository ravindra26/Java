
package com.service1.pojo;

/**
 *
 * @author Ravindra
 */
public class ImageCompressionPacket {
    private String base64;
    private String compressinFactor;
    private String imageExtension;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getCompressinFactor() {
        return compressinFactor;
    }

    public void setCompressinFactor(String compressinFactor) {
        this.compressinFactor = compressinFactor;
    }

    public String getImageExtension() {
        return imageExtension;
    }

    public void setImageExtension(String imageExtension) {
        this.imageExtension = imageExtension;
    }
    
}
