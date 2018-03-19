package com.andre_fernando.easybakerecipes.components.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andre_fernando.easybakerecipes.R;
import com.andre_fernando.easybakerecipes.data_objects.Steps;
import com.andre_fernando.easybakerecipes.fragments.OverviewFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


// Steps Adapter
public class Steps_Adapter extends RecyclerView.Adapter<Steps_Adapter.ViewHolder>{
    private final ArrayList<Steps> steps_list;
    private final OverviewFragment.stepsClickListener listener;

    public Steps_Adapter(ArrayList<Steps> steps_list, OverviewFragment.stepsClickListener listener) {
        this.steps_list = steps_list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.steps_adapter_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        final int adapterPosition = vh.getAdapterPosition();
        int step_number= steps_list.get(adapterPosition).getId();
        String toDisplay;

        if (step_number == 0){ // if it's the Recipe Introduction
            toDisplay =steps_list.get(adapterPosition).getShort_description();
            vh.step.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        } else {
            toDisplay = String.format("%s: %s",
                        step_number,
                        steps_list.get(adapterPosition).getShort_description());
        }

        vh.step.setText(toDisplay);

        vh.step.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.ClickedStep(adapterPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.bt_step_number)
        Button step;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
