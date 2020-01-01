package com.tukangonline.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tukangonline.R;
import com.tukangonline.infoUser;
import com.tukangonline.login;

public class ProfileFragment extends Fragment {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference dataa;
    private TextView Pnama,Pemail,Pno,tipee;
    private Button btProfile,btSave,batal;
    private TextInputEditText edNama,edNo,edPass,edOld;
    private LinearLayout Playout,Elayout;
    private String nama,hp,pass,Oldpass,tipe;
    private TextInputLayout tnama,thp,tpass,told;
    private Spinner spinner;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile,container,false);


        Pnama = (TextView) view.findViewById(R.id.Pnama);
        Pemail = (TextView) view.findViewById(R.id.Pemail);
        Pno = (TextView) view.findViewById(R.id.Pnohp);
        tipee = (TextView) view.findViewById(R.id.tipee);
        btProfile = (Button) view.findViewById(R.id.btnEd);
        btSave = (Button) view.findViewById(R.id.btnSave);
        batal = (Button) view.findViewById(R.id.btnBatal);
        edNama = (TextInputEditText) view.findViewById(R.id.Ednama);
        edNo = (TextInputEditText) view.findViewById(R.id.Edno);
        edPass = (TextInputEditText) view.findViewById(R.id.EdPass);
        edOld = (TextInputEditText) view.findViewById(R.id.EdOPass);
        Playout = (LinearLayout) view.findViewById(R.id.Playout);
        Elayout = (LinearLayout) view.findViewById(R.id.EdProfile);
        Elayout.setVisibility(View.GONE);
        tnama =(TextInputLayout) view.findViewById(R.id.tnama);
        thp =(TextInputLayout) view.findViewById(R.id.thp);
        tpass =(TextInputLayout) view.findViewById(R.id.tpass);
        told =(TextInputLayout) view.findViewById(R.id.told);
        spinner = (Spinner) view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.jenis, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){


            Intent intent = new Intent(getActivity().getApplicationContext(),login.class);
            startActivity(intent);

        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        dataa = FirebaseDatabase.getInstance().getReference().child("member").child(user.getUid());

        if(dataa == null){
            btProfile.setVisibility(View.GONE);
            Elayout.setVisibility(View.GONE);
        }else {
            dataa.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    Pemail.setText(dataSnapshot.child("email").getValue().toString());
                    Pnama.setText(dataSnapshot.child("nama").getValue().toString());
                    Pno.setText(dataSnapshot.child("nohp").getValue().toString());
                    Oldpass = dataSnapshot.child("password").getValue().toString();
                    tipee.setText(dataSnapshot.child("tipe").getValue().toString());
                    btProfile.setVisibility(View.VISIBLE);
                    Elayout.setVisibility(View.GONE);
                    btProfile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btProfile.setEnabled(false);
                            Playout.setVisibility(View.GONE);
                            Elayout.setVisibility(View.VISIBLE);
                            edNama.setText(dataSnapshot.child("nama").getValue().toString());
                            edNo.setText(dataSnapshot.child("nohp").getValue().toString());




                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     tipe = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            batal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Elayout.setVisibility(View.GONE);
                    Playout.setVisibility(View.VISIBLE);
                    btProfile.setEnabled(true);
                }
            });
            btSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tnama.setError(null);
                    thp.setError(null);
                    tpass.setError(null);
                    told.setError(null);
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    String kepala = user.getUid();
                    String email = user.getEmail().trim();
                    String nama = edNama.getText().toString().trim();
                    String nohp = edNo.getText().toString().trim();
                    String password = edOld.getText().toString().trim();
                    infoUser userinfo = new infoUser(email,nama,nohp,password,tipe);

                     if(TextUtils.isEmpty(nama)){
                         tnama.setError("Nama tidak boleh kosong");
                         tnama.requestFocus();
                     }if (TextUtils.isEmpty(nohp)){
                        thp.setError("No hp tidak boleh kosong");
                        thp.requestFocus();
                    }if(TextUtils.isEmpty(password)){
                        told.setError("Masukan Password anda");
                        told.requestFocus();
                    }if(!password.equals(Oldpass)){
                        told.setError("Masukan Password dengan Benar");
                        told.requestFocus();
                    }if(Oldpass == null){
                        tpass.setError("Buat Password Baru");
                        databaseReference.child("member").child(Oldpass).setValue(edPass);
                    }
                     else{
                        tnama.setError(null);
                        told.setError(null);
                        databaseReference.child("member").child(kepala).setValue(userinfo);
                        Toast.makeText(getActivity(), "Berhasil disimpan", Toast.LENGTH_LONG).show();
                        Elayout.setVisibility(View.GONE);
                        Playout.setVisibility(View.VISIBLE);
                        btProfile.setEnabled(true);
                        tipee.setText(tipe);
                    };


                }
            });

        }
        return view;

    }
}