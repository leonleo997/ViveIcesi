package co.highusoft.viveicesi.view.fragments;

import android.Manifest;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.adapters.HorarioAdapter;
import co.highusoft.viveicesi.utilities.UtilDomi;
import co.highusoft.viveicesi.model.Actividad;
import co.highusoft.viveicesi.model.Constantes;
import co.highusoft.viveicesi.model.Horario;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragCrearActividad.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragCrearActividad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragCrearActividad extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int REQUEST_GALLERY = 101;

    private String path;


    private TimePickerDialog.OnTimeSetListener onTimeStartSetListener;
    private TextView mDisplayTimeStart;
    private int hourS;
    private int minS;

    private TimePickerDialog.OnTimeSetListener onTimeFinishSetListener;
    private TextView mDisplayTimeFinish;
    private int hourF;
    private int minF;

    private int diaSemana;

    private Spinner sp_tipo_actividad;
    private Spinner sp_dias_semana;
    private ListView view_horarios;

    private ImageButton btn_anhadirFoto;

    private EditText et_nombre;
    private EditText et_descripcion;
    private EditText et_lugar;


    private HorarioAdapter horarioAdapter;

    private FirebaseDatabase bd;
    private FirebaseStorage storage;

    Dialog dialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragCrearActividad() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragCrearActividad.
     */
    // TODO: Rename and change types and number of parameters
    public static FragCrearActividad newInstance(String param1, String param2) {
        FragCrearActividad fragment = new FragCrearActividad();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storage = FirebaseStorage.getInstance();
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, 11);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final List<String> plantsList = new ArrayList<>(Arrays.asList(Constantes.TIPOS_ACTIVIDADES));
        final View view = inflater.inflate(R.layout.fragment_frag_crear_actividad, container, false);

        inicializarComponentes(view);

        sp_tipo_actividad = view.findViewById(R.id.sp_tipo_area);
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this.getContext(), R.layout.spinner_item, plantsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        sp_tipo_actividad.setAdapter(spinnerArrayAdapter);

        final List<String> diasList = new ArrayList<>(Arrays.asList(Constantes.DIAS_SEMANA));
        sp_dias_semana = view.findViewById(R.id.sp_dia_semana);
        final ArrayAdapter<String> spinnerArrayAdapterDias = new ArrayAdapter<String>(
                this.getContext(), R.layout.spinner_item, diasList);

        spinnerArrayAdapterDias.setDropDownViewResource(R.layout.spinner_item);
        sp_dias_semana.setAdapter(spinnerArrayAdapterDias);


        //Horario renglon--------------------------------------
        view_horarios = view.findViewById(R.id.lv_horario);
        horarioAdapter = new HorarioAdapter(this.getActivity());
        view_horarios.setAdapter(horarioAdapter);
        view_horarios.setVisibility(View.GONE);
        //-----------------------------------------------------


        final EditText et_lugar = view.findViewById(R.id.et_lugar);

        Button btn_horario = view.findViewById(R.id.btn_add_horario);
        btn_horario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Horario horario = new Horario();
                horario.setDiaSemana(sp_dias_semana.getSelectedItem().toString());
                horario.setHoraEntrada(hourS + ":" + minS);
                horario.setHoraSalida(hourF + ":" + minF);
                horario.setLugar(et_lugar.getText().toString());
                horarioAdapter.addHorario(horario);
                view_horarios.setVisibility(View.VISIBLE);
            }
        });

        btn_anhadirFoto = view.findViewById(R.id.btn_aniadirFoto);
        btn_anhadirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i, REQUEST_GALLERY);

            }
        });

        return view;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void inicializarComponentes(final View view) {

        mDisplayTimeStart = view.findViewById(R.id.tv_hora_inicio);
        mDisplayTimeFinish = view.findViewById(R.id.tv_hora_fin);


        mDisplayTimeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                diaSemana = c.get(Calendar.DAY_OF_WEEK);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        view.getContext(),
                        onTimeStartSetListener,
                        hour,
                        minute,
                        false
                );

                timePickerDialog.show();
            }
        });

        mDisplayTimeFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        view.getContext(),
                        onTimeFinishSetListener,
                        hour,
                        minute,
                        false
                );

                timePickerDialog.show();
            }
        });


        onTimeStartSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(minute<10){
                    mDisplayTimeStart.setText(hourOfDay + ":0" + minute);
                }else{
                    mDisplayTimeStart.setText(hourOfDay + ":" + minute);
                }
                hourS = hourOfDay;
                minS = minute;
            }
        };


        onTimeFinishSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(minute<10){
                    mDisplayTimeFinish.setText(hourOfDay + ":0" + minute);
                }else{
                    mDisplayTimeFinish.setText(hourOfDay + ":" + minute);
                }
                hourF = hourOfDay;
                minF = minute;
            }
        };


        et_nombre = view.findViewById(R.id.et_name);
        et_descripcion = view.findViewById(R.id.et_descripcion);
        et_lugar = view.findViewById(R.id.et_lugar);

        final Button btn_crear_actividad = view.findViewById(R.id.btn_crear_actividad);
        btn_crear_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bd = FirebaseDatabase.getInstance();

                Actividad actividad = new Actividad();
                actividad.setDescripcion(et_descripcion.getText().toString());
                actividad.setImg(path);
                actividad.setNombre(et_nombre.getText().toString());
                actividad.setHorarios(horarioAdapter.getHorarios());
                actividad.setLugar(et_lugar.getText().toString());

                if (path != null) {
                    try {
                        StorageReference ref = storage.getReference().child("fotos").child(actividad.getImg());
                        FileInputStream file = new FileInputStream(new File(path));
                        UploadTask task=ref.putStream(file);

                    } catch (FileNotFoundException ex) {

                    }
                }
                String tipo_actividad = sp_tipo_actividad.getSelectedItem().toString();

                DatabaseReference dbr = bd.getReference().child("Actividades")
                        .child(tipo_actividad).push();
                dbr.setValue(actividad);


                et_nombre.setText("");
                et_descripcion.setText("");
                et_lugar.setText("");

                Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.contenedorFragments);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.detach(currentFragment);
                fragmentTransaction.attach(currentFragment);
                fragmentTransaction.commit();

                Toast.makeText(getContext(), "La actividad se ha creado exitosamente!", Toast.LENGTH_SHORT).show();


            }
        });
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
     * "http://developer.android.com/training/basics/fragments/comm unicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void showAlertDialog(View v) {

//        AlertDialog.Builder builderAlert = new AlertDialog.Builder(FragCrearActividad.this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY && resultCode == this.getActivity().RESULT_OK) {
            path = UtilDomi.getPath(this.getContext(), data.getData());
            Bitmap m = BitmapFactory.decodeFile(path);
            ImageView img_foto = this.getActivity().findViewById(R.id.foto_actividad);
            img_foto.setImageBitmap(m);
        }
    }
}
