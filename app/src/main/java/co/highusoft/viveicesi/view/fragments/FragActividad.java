package co.highusoft.viveicesi.view.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.adapters.HorarioAdapter;
import co.highusoft.viveicesi.model.Actividad;
import co.highusoft.viveicesi.model.Usuario;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragActividad.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragActividad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragActividad extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView nombre;
    private ImageView imagenActividad;
    private TextView descripcion;
    private Button botonCalificarActividad;

    private CalificacionActividades calificacionActividades;

    private Actividad actividad;

    private ListView view_horarios;
    private HorarioAdapter horarioAdapter;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragActividad() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragActividad.
     */
    // TODO: Rename and change types and number of parameters
    public static FragActividad newInstance(String param1, String param2) {
        FragActividad fragment = new FragActividad();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        actividad = (Actividad) bundle.getSerializable("actividad");
        Log.e(">>: ", actividad.getNombre());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FirebaseDatabase db;
    FirebaseAuth auth;
    FirebaseStorage storage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        calificacionActividades = new CalificacionActividades();

        final View viewInflater = inflater.inflate(R.layout.fragment_frag_actividad, null);
        imagenActividad = viewInflater.findViewById(R.id.iv_imagen);
        nombre = viewInflater.findViewById(R.id.tv_nombre);
        descripcion = viewInflater.findViewById(R.id.tv_descripcion);

        view_horarios = viewInflater.findViewById(R.id.list_horarios);
        horarioAdapter = new HorarioAdapter(this.getActivity());
        view_horarios.setAdapter(horarioAdapter);
        horarioAdapter.setHorarios(actividad.getHorarios());

        nombre.setText(actividad.getNombre());
        descripcion.setText(actividad.getDescripcion());

        botonCalificarActividad = viewInflater.findViewById(R.id.bt_pasar_calificar_actividad);
        Log.e(">>>", auth.getCurrentUser().getEmail());

        StorageReference storageReference = storage.getReference().child("fotos").child(actividad.getImg());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(FragActividad.this).load(uri)
                        .into(imagenActividad);
            }
        });
        //borrar

        botonCalificarActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(">>>", auth.getCurrentUser().getEmail());
                DatabaseReference myRef = db.getReference("Usuarios");
                myRef.orderByChild("correo").equalTo(auth.getCurrentUser().getEmail()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        Bitmap m = BitmapFactory.decodeFile(user.getFoto());
                        imagenActividad.setImageBitmap(m);
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
            }
        });


        //

        // Inflate the layout for this fragment
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
