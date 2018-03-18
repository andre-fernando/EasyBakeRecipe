package com.andre_fernando.easybakerecipes.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class RecipeContentProvider extends ContentProvider {
    private static final String AUTHORITY = "com.andre_fernando.easybakerecipes";
    private static final String RECIPE_TABLE = "RecipeTable";
    private static final String INGREDIENTS_TABLE = "IngredientsTable";
    private static final String STEPS_TABLE ="StepsTable";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    public static final Uri RECIPE_URI = append_Uri(RECIPE_TABLE);
    public static final Uri INGREDIENTS_URI = append_Uri(INGREDIENTS_TABLE);
    public static final Uri STEPS_URI = append_Uri(STEPS_TABLE);
    private static RecipeDB sDB;

    private static final int CODE_RECIPE_TABLE = 500;
    private static final int CODE_INGREDIENTS_TABLE = 501;
    private static final int CODE_STEPS_TABLE = 502;

    private static final UriMatcher sURIMATCHER = buildUriMatcher();

    private static Uri append_Uri(String path){
        return BASE_CONTENT_URI.buildUpon()
                .appendPath(path).build();
    }

    private static UriMatcher buildUriMatcher(){
        UriMatcher um = new UriMatcher(UriMatcher.NO_MATCH);
        um.addURI(AUTHORITY, RECIPE_TABLE, CODE_RECIPE_TABLE);
        um.addURI(AUTHORITY, INGREDIENTS_TABLE, CODE_INGREDIENTS_TABLE);
        um.addURI(AUTHORITY, STEPS_TABLE, CODE_STEPS_TABLE);
        return um;
    }




    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (sURIMATCHER.match(uri)){
            case CODE_RECIPE_TABLE:
                return sDB.recipeDAO().cp_getAllRecipes();
            case CODE_INGREDIENTS_TABLE:
                return sDB.ingredientsDAO().cp_getAllIngredients();
            case CODE_STEPS_TABLE:
                return sDB.stepsDAO().cp_getAllSteps();
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        sDB = RecipeDB.createDB(getContext());
        return true;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
