package com.andre_fernando.easybakerecipes.data_objects;


import android.os.Parcel;
import android.os.Parcelable;

import com.andre_fernando.easybakerecipes.db.IngredientsTable;
import com.andre_fernando.easybakerecipes.db.RecipeTable;
import com.andre_fernando.easybakerecipes.db.StepsTable;

import java.util.ArrayList;

public class Recipe implements Parcelable {
    @SuppressWarnings("CanBeFinal")
    private int id;
    @SuppressWarnings("CanBeFinal")
    private String name;
    @SuppressWarnings("CanBeFinal")
    private ArrayList<Ingredients> ingredients;
    @SuppressWarnings("CanBeFinal")
    private ArrayList<Steps> steps;
    @SuppressWarnings("CanBeFinal")
    private double servings;
    @SuppressWarnings("CanBeFinal")
    private String image;

    public Recipe(int id, String name, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps, double servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

// --Commented out by Inspection START (6/3/18 4:36 PM):
//    public static ArrayList<Recipe> convertCursor(Cursor recipe, Cursor ingredients, Cursor steps){
//        if (recipe != null && ingredients != null && steps != null){
//            ArrayList<Recipe> toReturn = new ArrayList<>();
//            //Recipes
//            while (recipe.moveToNext()){
//                int tempid;
//                String tempname, tempimage;
//                double tempservings;
//
//                tempid = recipe.getInt(0);
//                tempname = recipe.getString(1);
//                tempservings = recipe.getDouble(2);
//                tempimage = recipe.getString(3);
//
//                ArrayList<Ingredients> tempIngredients = Ingredients.getIngredientsWithId(ingredients,tempid);
//                ArrayList<Steps> tempSteps = Steps.getStepsWithId(steps,tempid);
//
//                toReturn.add(new Recipe(tempid,tempname,tempIngredients,tempSteps,tempservings,tempimage));
//            }
//            recipe.close();
//            ingredients.close();
//            steps.close();
//            return toReturn;
//        }
//        else return new ArrayList<>();
//    }
// --Commented out by Inspection STOP (6/3/18 4:36 PM)

    public static ArrayList<Recipe> fromRecipeDB(ArrayList<RecipeTable> recipeTables,
                                                 ArrayList<IngredientsTable> ingredientsTables,
                                                 ArrayList<StepsTable> stepsTables){
        ArrayList<Recipe> toReturn = new ArrayList<>();
        for (RecipeTable r: recipeTables){
            int id;
            double servings;
            String name, image;
            id=r.id;
            servings=r.servings;
            name = r.name;
            image = r.image;
            ArrayList<Ingredients> ingredients = Ingredients.fromIngredientsTable(id,ingredientsTables);
            ArrayList<Steps> steps = Steps.fromStepsTable(id,stepsTables);
            toReturn.add(new Recipe(id,name,ingredients,steps,servings,image));
        }
        return toReturn;
    }

    public int getId() {
        return id;
    }

// --Commented out by Inspection START (6/3/18 4:36 PM):
//    public void setId(int id) {
//        this.id = id;
//    }
// --Commented out by Inspection STOP (6/3/18 4:36 PM)

    public String getName() {
        return name;
    }

// --Commented out by Inspection START (6/3/18 4:36 PM):
//    public void setName(String name) {
//        this.name = name;
//    }
// --Commented out by Inspection STOP (6/3/18 4:36 PM)

    public ArrayList<Ingredients> getIngredients() {
        return ingredients;
    }

// --Commented out by Inspection START (6/3/18 4:36 PM):
//    public void setIngredients(ArrayList<Ingredients> ingredients) {
//        this.ingredients = ingredients;
//    }
// --Commented out by Inspection STOP (6/3/18 4:36 PM)

    public ArrayList<Steps> getSteps() {
        return steps;
    }

// --Commented out by Inspection START (6/3/18 4:36 PM):
//    public void setSteps(ArrayList<Steps> steps) {
//        this.steps = steps;
//    }
// --Commented out by Inspection STOP (6/3/18 4:36 PM)

    public double getServings() {
        return servings;
    }

// --Commented out by Inspection START (6/3/18 4:36 PM):
//    public void setServings(int servings) {
//        this.servings = servings;
//    }
// --Commented out by Inspection STOP (6/3/18 4:36 PM)

    public String getImage() {
        return image;
    }

// --Commented out by Inspection START (6/3/18 4:36 PM):
//    public void setImage(String image) {
//        this.image = image;
//    }
// --Commented out by Inspection STOP (6/3/18 4:36 PM)

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
        dest.writeDouble(this.servings);
        dest.writeString(this.image);
    }

    private Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredients = in.createTypedArrayList(Ingredients.CREATOR);
        this.steps = in.createTypedArrayList(Steps.CREATOR);
        this.servings = in.readDouble();
        this.image = in.readString();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}
