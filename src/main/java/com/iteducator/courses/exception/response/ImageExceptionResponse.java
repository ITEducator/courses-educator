package com.iteducator.courses.exception.response;

public class ImageExceptionResponse {

    private String imageNotFound;

    public ImageExceptionResponse(String imageNotFound) {
        this.imageNotFound = imageNotFound;
    }

    public String getImageNotFound() {
        return imageNotFound;
    }

    public void setImageNotFound(String imageNotFound) {
        this.imageNotFound = imageNotFound;
    }
}
