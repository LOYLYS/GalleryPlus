package edu.ptit.vhlee.galleryplus;

public class Photo {
    private String mPath;
    private String mName;

    public Photo(String path, String name) {
        mPath = path;
        mName = name;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
