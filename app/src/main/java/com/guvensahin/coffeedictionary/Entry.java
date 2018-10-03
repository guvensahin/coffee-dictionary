package com.guvensahin.coffeedictionary;

import java.io.Serializable;

public class Entry implements Serializable {
    private String nameEng;
    private String nameTur;
    public String description;
    public String category;

    public void setNameEng(String _nameEng)
    {
        nameEng = _nameEng;
    }

    public void setNameTur(String _nameTur)
    {
        nameTur = _nameTur;
    }

    public String getNameEng() {
        return nameEng;
    }

    public String getNameTur() {
        return nameTur;
    }

    public String getName()
    {
        return nameEng.equals(nameTur) ? nameEng : nameEng + " (" + nameTur + ")";
    }
}