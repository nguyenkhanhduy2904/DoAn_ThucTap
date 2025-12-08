package models;

public class ImageUpload {
    private String imageName;
    private String url;
    private long size;
    private String imageContent;

    public ImageUpload() {
    }

    public ImageUpload(String imageName, String url, long size, String imageContent) {
        this.imageName = imageName;
        this.url = url;
        this.size = size;
        this.imageContent = imageContent;
    }
}
