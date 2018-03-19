package com.andre_fernando.easybakerecipes.data_objects;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.andre_fernando.easybakerecipes.db.StepsTable;

import java.util.ArrayList;

public class Steps implements Parcelable {
    @SuppressWarnings("CanBeFinal")
    private int id;
    @SuppressWarnings("CanBeFinal")
    private String short_description;
    @SuppressWarnings("CanBeFinal")
    private String description;
    @SuppressWarnings("CanBeFinal")
    private String videoUrl;
    @SuppressWarnings("CanBeFinal")
    private String thumbnailUrl;

    /**
     * This is the default constructor
     * @param id Step ID
     * @param short_description A short description for the step
     * @param description Full description of the step
     * @param videoUrl Url of video in string fromat
     * @param thumbnailUrl Url of thumbnail in string format
     */
    public Steps(int id, String short_description, String description, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.short_description = short_description;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public static ArrayList<Steps> fromStepsTable(int Recipe_Id, ArrayList<StepsTable> stepsTables){
        ArrayList<Steps> unSorted = new ArrayList<>();
        for (StepsTable s: stepsTables){
            if (s.recipe_id == Recipe_Id){
                unSorted.add(new Steps(
                        s.id,
                        s.short_description,
                        s.description,
                        s.video_url,
                        s.thumbnail_url
                ));
            }
        }

        return sortSteps(unSorted);
    }

    private static ArrayList<Steps> sortSteps(ArrayList<Steps> unSorted){
        //Sort the steps
        ArrayList<Steps> toReturn = new ArrayList<>();
        for (int x=0;x<unSorted.size();x++){
            for (Steps s: unSorted){
                if (s.getId()==x){
                    toReturn.add(s);
                }
            }
        }
        return toReturn;
    }

    public int getId() {
        return id;
    }

    public String getShort_description() {
        return short_description;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoString() {
        return videoUrl;
    }

    public Uri getVideoUri(){ return Uri.parse(videoUrl);}

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public boolean hasVideo(){ return !this.videoUrl.isEmpty(); }

    public boolean hasImage(){ return !this.thumbnailUrl.isEmpty(); }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.short_description);
        dest.writeString(this.description);
        dest.writeString(this.videoUrl);
        dest.writeString(this.thumbnailUrl);
    }

    private Steps(Parcel in) {
        this.id = in.readInt();
        this.short_description = in.readString();
        this.description = in.readString();
        this.videoUrl = in.readString();
        this.thumbnailUrl = in.readString();
    }

    public static final Parcelable.Creator<Steps> CREATOR = new Parcelable.Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel source) {
            return new Steps(source);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
}
