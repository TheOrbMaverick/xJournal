package com.example.android.xjournal;


public class ListItem  {

    private String title;
    private String entry;

    public ListItem(String title, String entry) {
        this.title = title;
        this.entry = entry;
    }

    public String getTitle() {
        return title;
    }

    public String getEntry() {
        return entry;
    }
}
