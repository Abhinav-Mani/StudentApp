package com.androlord.studentapp.Adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androlord.studentapp.Data.PdfModel;
import com.androlord.studentapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.ViewHolder>implements View.OnClickListener,View.OnLongClickListener {


    public List<PdfModel> pdf_list;
    Context context;
    public String blogpostid1;
    FirebaseAuth mAuth;
    String postid;
    private FirebaseFirestore firebaseFirestore;
   PdfAdapter pdfAdapter;
    String Userimage;
    String userid2;
    Onitemclicklistner onitemclicklistner;



    public PdfAdapter(List<PdfModel> pdf_list,Onitemclicklistner onitemclicklistner) {
        this.pdf_list = pdf_list;
        this.onitemclicklistner=onitemclicklistner;

    }





    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pdfview_layout, viewGroup, false);
        context = viewGroup.getContext();
        firebaseFirestore=FirebaseFirestore.getInstance();

        return new ViewHolder(view,onitemclicklistner);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfAdapter.ViewHolder viewHolder, int i) {
        viewHolder.pdfdesc.setText(pdf_list.get(i).getDesc());

        viewHolder.author.setText(pdf_list.get(i).getAuthor());


    }

    @Override
    public int getItemCount() {
        return pdf_list.size();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView pdfdesc;
        TextView author;

        View mView;
        Onitemclicklistner onitemclicklistner;

        public ViewHolder(@NonNull View itemView,Onitemclicklistner onitemclicklistner) {
            super(itemView);
            mView=itemView;
            this.onitemclicklistner=onitemclicklistner;
            mView.setOnClickListener(this);

            pdfdesc=mView.findViewById(R.id.pdfdesc);
            author=mView.findViewById(R.id.author);

        }

        @Override
        public void onClick(View v) {
            onitemclicklistner.onNoteclick(getAdapterPosition());

        }
    }
    public  interface Onitemclicklistner{
        void onNoteclick(int postion);
    }




    }
