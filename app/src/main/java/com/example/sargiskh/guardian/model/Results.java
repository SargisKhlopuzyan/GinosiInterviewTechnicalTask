package com.example.sargiskh.guardian.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Results implements Serializable {
    @SerializedName("id") public String id;

    @SerializedName("type") public String type;

    @SerializedName("sectionId") public String sectionId;

    @SerializedName("sectionName") public String sectionName;

    @SerializedName("webPublicationDate") public String webPublicationDate;

    @SerializedName("webTitle") public String webTitle;

    @SerializedName("webUrl") public String webUrl;

    @SerializedName("apiUrl") public String apiUrl;

    @SerializedName("isHosted") public String isHosted;

    @SerializedName("pillarId") public String pillarId;

    @SerializedName("pillarName") public String pillarName;
}