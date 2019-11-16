package com.androlord.studentapp.Funtions;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androlord.studentapp.Adapter.PdfAdapter;
import com.androlord.studentapp.Data.PdfModel;
import com.androlord.studentapp.Funtions.UploadPdf;
import com.androlord.studentapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import com.jaredrummler.materialspinner.MaterialSpinner;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import dmax.dialog.SpotsDialog;

public class ViewPdf extends AppCompatActivity implements  PdfAdapter.Onitemclicklistner {
    RecyclerView pdflistrecycler;
    private List<PdfModel> pdf_list;
    FirebaseAuth mAuth;
    String userid2;
    ConstraintLayout root;
    String course;

    FirebaseFirestore firebaseFirestore;
    PdfAdapter pdfAdapter;

    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        pdflistrecycler = findViewById(R.id.pdfrecyclerview);

        pdf_list = new ArrayList<>();

        layoutManager = new LinearLayoutManager(getApplicationContext());//   RecyclerView.LayoutManager layoutManager;
        pdflistrecycler.setLayoutManager(layoutManager);
        //passing image array and assiging adapter  ,,, adapter is object of recyler_adapter class
        pdfAdapter = new PdfAdapter(pdf_list,this);
        pdflistrecycler.setAdapter(pdfAdapter);
        final SpotsDialog waitingdilogp = new SpotsDialog(ViewPdf.this);
        waitingdilogp.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                waitingdilogp.dismiss();
            }
        }).start();


        firebaseFirestore = FirebaseFirestore.getInstance();


        //     blogRecyclerAdapter.notifyDataSetChanged();

    }


    @Override
    public void onNoteclick(int postion) {
        Toast.makeText(this, ""+pdf_list.get(postion).getImageurl() , Toast.LENGTH_SHORT).show();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_list.get(postion).getImageurl()));
        startActivity(browserIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ViewPdf.this);
        //alertDialog.setTitle("Choose A Course");
        // alertDialog.setMessage("Plz Fill Your Details...");
        alertDialog.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(ViewPdf.this);
        View addmoney_layout = inflater.inflate(R.layout.coursechooser, null);

        final MaterialSpinner spinner =addmoney_layout.findViewById(R.id.dropdownview3);

        //  MaterialSpinner spinner = (MaterialSpinner)findViewById(R.id.dropdownview3);
        spinner.setItems("CS001", "EG001", "MA001", "SC001");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(item.equals("CS001"))
                {
                    course="CS001";

                }
                else   if(item.equals("EG001"))
                {
                    course="EG001";

                } else
                if(item.equals("MA001"))
                {
                    course="MA001";
                } else if(item.equals("SC001"))
                {
                    course="SC001";
                }

            }
        });

        alertDialog.setView(addmoney_layout);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  Toast.makeText(WalletActivity.this,"Currently Not Available", Toast.LENGTH_SHORT).show();
                if(!TextUtils.isEmpty(course))
                {
                    Query firstquery = firebaseFirestore.collection("PDF").orderBy("timestamp", Query.Direction.DESCENDING);
                    firstquery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    String blogpostid = doc.getDocument().getId();
                                    PdfModel pdfModel = doc.getDocument().toObject(PdfModel.class);
                                    String course1=doc.getDocument().getString("course");
                                    if(course1.equals(course))
                                        pdf_list.add(pdfModel);
                                    pdfAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(ViewPdf.this, ""+"Select A Course", Toast.LENGTH_SHORT).show();
                }


            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent=new Intent(ViewPdf.this, UploadPdf.class);
                startActivity(intent);





            }
        });

        alertDialog.show();


    }
}

