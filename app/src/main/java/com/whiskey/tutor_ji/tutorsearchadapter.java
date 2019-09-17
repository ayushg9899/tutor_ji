package com.whiskey.tutor_ji;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class tutorsearchadapter extends RecyclerView.Adapter<tutorsearchadapter.myviewholder> {

    Context context;
    ArrayList<tutorsearchgetter>tutorsearchgetterArrayList;

    public tutorsearchadapter(Context context, ArrayList<tutorsearchgetter> tutorsearchgetterArrayList) {
        this.context = context;
        this.tutorsearchgetterArrayList = tutorsearchgetterArrayList;
    }

    @NonNull
    @Override
    public tutorsearchadapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.tutorlist,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tutorsearchadapter.myviewholder holder, int position) {
        holder.name.setText(tutorsearchgetterArrayList.get(position).getName());
        holder.school.setText(tutorsearchgetterArrayList.get(position).getSchool());
        holder.state.setText(tutorsearchgetterArrayList.get(position).getAddress_State());
        holder.email.setText(tutorsearchgetterArrayList.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return tutorsearchgetterArrayList.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        TextView name,school,email,state;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.tutorname);
            school=itemView.findViewById(R.id.tutorschool);
            email=itemView.findViewById(R.id.tutoremail);
            state=itemView.findViewById(R.id.tutorcity);

        }
    }
}
