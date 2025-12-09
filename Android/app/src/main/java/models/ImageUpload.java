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

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }
}
