package co.highusoft.viveicesi.view.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Constantes;
import co.highusoft.viveicesi.model.Usuario;

import static co.highusoft.viveicesi.R.layout.spinner_item;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragEditarPerfil.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragEditarPerfil#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragEditarPerfil extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private EditText editTextNombre;
    private EditText editTextUsuario;
    private EditText editTextCorreoElectronico;
    private Spinner spinnerAreadeTrabajo;
    private Spinner spinnerRol;
    private ImageButton imageButtonFotoPerfil;
    private ImageButton imageButtonGuardar;
    private FirebaseDatabase db;
    private FirebaseAuth auth;
    private FirebaseStorage storage;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragEditarPerfil() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragEditarPerfil.
     */
    // TODO: Rename and change types and number of parameters
    public static FragEditarPerfil newInstance(String param1, String param2) {
        FragEditarPerfil fragment = new FragEditarPerfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View viewInflater = inflater.inflate(R.layout.fragment_frag_editar_perfil, container, false);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        editTextUsuario = viewInflater.findViewById(R.id.et_user);
        editTextCorreoElectronico = viewInflater.findViewById(R.id.et_correo);

        editTextNombre = viewInflater.findViewById(R.id.et_nombre);
        imageButtonFotoPerfil = viewInflater.findViewById(R.id.btn_cambiarFoto);
        imageButtonGuardar = viewInflater.findViewById(R.id.btn_save_changes);

        spinnerAreadeTrabajo = viewInflater.findViewById(R.id.sp_area);


//


        //Implemento el setOnItemSelectedListener: para realizar acciones cuando se seleccionen los ítems

        //Convierto la variable List<> en un ArrayList<>()
        final ArrayList listaOpciones = new ArrayList<>();
        //Arreglo con nombre de frutas
        final String[] opcionesEstudiantes = Constantes.ESTUDIANTES;
        final String[] opcionesTrabadores = Constantes.TRABAJADORES;

        //Agrego las frutas del arreglo `strFrutas` a la listaFrutas

//


        DatabaseReference myRef = db.getReference("Usuarios");

        myRef.orderByChild("correo").equalTo(auth.getCurrentUser().getEmail()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                editTextNombre.setText(user.getNombre());
                editTextCorreoElectronico.setText(user.getCorreo());
                editTextUsuario.setText(user.getUsuario());


                //


                if (user.gettipoUsuario().equals("Estudiante")) {

                    Collections.addAll(listaOpciones, opcionesEstudiantes);
                } else {
                    Collections.addAll(listaOpciones, opcionesTrabadores);
                }


                //Implemento el adapter con el contexto, layout, listaFrutas

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return viewInflater;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}