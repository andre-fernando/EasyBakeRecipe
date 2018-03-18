package com.andre_fernando.easybakerecipes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.activities.MainActivity;
import com.andre_fernando.easybakerecipes.activities.SplashScreenActivity;
import com.andre_fernando.easybakerecipes.components.adapters.Recipe_List_Adapter;
import com.andre_fernando.easybakerecipes.utils.ClickListener;
import com.andre_fernando.easybakerecipes.utils.RecyclerTouchListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


public class RecipeListFragment extends Fragment {
    private Unbinder unbinder;
    private RecipeSelectListener listener;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.rv_recipe_list)
    RecyclerView rv_recipe_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup view, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recipe_list_fragment,view,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //noinspection ConstantConditions
        if (view != null) {
            unbinder= ButterKnife.bind(this,view);
            Init_RecylerView();
        }
    }

    private void Init_RecylerView(){
        final FragmentActivity fragmentActivity = getActivity();
        if (MainActivity.twoPane){
            rv_recipe_list.setLayoutManager(new GridLayoutManager(fragmentActivity,3));
        } else {
            rv_recipe_list.setLayoutManager(new GridLayoutManager(fragmentActivity,1));
        }

        Recipe_List_Adapter list_adapter = new Recipe_List_Adapter
                (SplashScreenActivity.AllRecipes,fragmentActivity);
        rv_recipe_list.setAdapter(list_adapter);
        rv_recipe_list.addOnItemTouchListener(new RecyclerTouchListener(fragmentActivity, rv_recipe_list, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                listener.recipeSelected(position);
            }

            @Override
            public void onLongClick(View view, int position) { }
        }));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RecipeSelectListener){
            listener = (RecipeSelectListener) context;
        }else {
            Timber.e("RecipeSelectListener is not implemented in MainActivity.");
            throw new ClassCastException(context.toString()+" must implement RecipeListFragment.RecipeSelectListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface RecipeSelectListener{
        void recipeSelected(int position);
    }
}
