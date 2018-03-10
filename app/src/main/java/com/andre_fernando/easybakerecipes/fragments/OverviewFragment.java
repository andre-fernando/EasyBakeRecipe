package com.andre_fernando.easybakerecipes.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.activities.SplashScreenActivity;
import com.andre_fernando.easybakerecipes.components.adapters.Ingredients_Adapter;
import com.andre_fernando.easybakerecipes.components.adapters.Steps_Adapter;
import com.andre_fernando.easybakerecipes.data_objects.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;


public class OverviewFragment extends Fragment {
    private Unbinder unbinder;
    private stepsClickListener listener;
    private int recipe_no;
    private Recipe recipe;

    @BindView(R.id.rv_ingredients_list)
    RecyclerView rv_ingredients_list;

    @BindView(R.id.rv_steps_list)
    RecyclerView rv_steps_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.overview_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder= ButterKnife.bind(this,view);
        Init_Overview_Fragment();
    }

    void Init_Overview_Fragment(){
        final FragmentActivity fragmentActivity = getActivity();
        /*recipe_no = getArguments().getParcelable("recipe");
        recipe = SplashScreenActivity.AllRecipes.get(recipe_no);*/
        recipe = getArguments().getParcelable("recipe");
        if (recipe != null) {
            //For Ingredients
            Ingredients_Adapter ingredients_adapter = new Ingredients_Adapter(recipe.getIngredients());
            rv_ingredients_list.setLayoutManager(new LinearLayoutManager(fragmentActivity));
            rv_ingredients_list.hasFixedSize();
            rv_ingredients_list.setAdapter(ingredients_adapter);

            //For Steps
            Steps_Adapter steps_adapter = new Steps_Adapter(recipe.getSteps(),listener);
            rv_steps_list.setLayoutManager(new LinearLayoutManager(fragmentActivity));
            rv_steps_list.hasFixedSize();
            rv_steps_list.setAdapter(steps_adapter);
        } else Timber.e("Recipe was null!");

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof stepsClickListener){
            listener=(stepsClickListener) context;
        } else {
            Timber.e("stepsClickListener is not implemented in OverviewActivity.");
            throw new ClassCastException(context.toString()
                    +" must implement OverviewFragment.stepsClickListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface stepsClickListener{
        void ClickedStep(int position);
    }
}
