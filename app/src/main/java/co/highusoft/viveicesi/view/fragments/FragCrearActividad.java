package co.highusoft.viveicesi.view.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Constantes;


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

    private TimePickerDialog.OnTimeSetListener onTimeStartSetListener;
    private TextView mDisplayTimeStart;
    private int hourS;
    private int minS;

    private TimePickerDialog.OnTimeSetListener onTimeFinishSetListener;
    private TextView mDisplayTimeFinish;
    private int hourF;
    private int minF;


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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public Spinner sp_tipo_actividad;
    public Spinner sp_dias_semana;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final List<String> plantsList = new ArrayList<>(Arrays.asList(Constantes.TIPOS_ACTIVIDADES));
        final View view=inflater.inflate(R.layout.fragment_frag_crear_actividad, container, false);

        inicializarComponentes(view);
        sp_tipo_actividad=view.findViewById(R.id.sp_tipo_area);
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this.getContext(), R.layout.spinner_item, plantsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        sp_tipo_actividad.setAdapter(spinnerArrayAdapter);

        final List<String> diasList = new ArrayList<>(Arrays.asList(Constantes.DIAS_SEMANA));
        sp_dias_semana=view.findViewById(R.id.sp_dia_semana);
        final ArrayAdapter<String> spinnerArrayAdapterDias = new ArrayAdapter<String>(
                this.getContext(), R.layout.spinner_item, diasList);

        spinnerArrayAdapterDias.setDropDownViewResource(R.layout.spinner_item);
        sp_dias_semana.setAdapter(spinnerArrayAdapterDias);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void inicializarComponentes(final View view){

        mDisplayTimeStart = view.findViewById(R.id.tv_hora_inicio);
        mDisplayTimeFinish = view.findViewById(R.id.tv_hora_fin);


        mDisplayTimeFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

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
                mDisplayTimeStart.setText(hourOfDay + ":" + minute);
                hourS = hourOfDay;
                minS = minute;
            }
        };


        onTimeFinishSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mDisplayTimeFinish.setText(hourOfDay + ":" + minute);
                hourF = hourOfDay;
                minF = minute;
            }
        };
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
}
