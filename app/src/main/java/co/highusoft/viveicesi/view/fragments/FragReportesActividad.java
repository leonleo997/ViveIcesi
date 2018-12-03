package co.highusoft.viveicesi.view.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Actividad;
import co.highusoft.viveicesi.model.Constantes;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragReportesActividad.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragReportesActividad#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragReportesActividad extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FirebaseDatabase db;

    private PieChart pieChart_p1;
    private PieChart pieChart_p2;
    private PieChart pieChart_p3;
    private PieChart pieChart_p4;
    private PieChart pieChart_p5;
    private PieChart pieChart_p6;
    private PieChart pieChart_p7;
    private PieChart pieChart_p8;
    private PieChart pieChart_p9;
    private PieChart pieChart_p10;
    private PieChart pieChart_p11;


    public FragReportesActividad() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragReportesActividad.
     */
    // TODO: Rename and change types and number of parameters
    public static FragReportesActividad newInstance(String param1, String param2) {
        FragReportesActividad fragment = new FragReportesActividad();
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


    private Spinner sp_actividad;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_reportes_actividad, container, false);
        db = FirebaseDatabase.getInstance();


        inicializarComponentes(view);
        sp_actividad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                refrescarReportes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }


    private void refrescarReportes() {
        Description desc = new Description();
        desc.setText("Pregunta 1");
        desc.setTextSize(15);
        pieChart_p1.setDescription(desc);
        pieChart_p1.setRotationEnabled(true);
        addDataSet(pieChart_p1, "P1");

        desc = new Description();
        desc.setText("Pregunta 2");
        desc.setTextSize(15);
        pieChart_p2.setDescription(desc);
        pieChart_p2.setRotationEnabled(true);
        addDataSet(pieChart_p2, "P2");

        desc = new Description();
        desc.setText("Pregunta 3");
        desc.setTextSize(15);
        pieChart_p3.setDescription(desc);
        pieChart_p3.setRotationEnabled(true);
        addDataSet(pieChart_p3, "P3");

        desc = new Description();
        desc.setText("Pregunta 4");
        desc.setTextSize(15);
        pieChart_p4.setDescription(desc);
        pieChart_p4.setRotationEnabled(true);
        addDataSet(pieChart_p4, "P4");

        desc = new Description();
        desc.setText("Pregunta 5");
        desc.setTextSize(15);
        pieChart_p5.setDescription(desc);
        pieChart_p5.setRotationEnabled(true);
        addDataSet(pieChart_p5, "P5");

        desc = new Description();
        desc.setText("Pregunta 6");
        desc.setTextSize(15);
        pieChart_p6.setDescription(desc);
        pieChart_p6.setRotationEnabled(true);
        addDataSet(pieChart_p6, "P6");

        desc = new Description();
        desc.setText("Pregunta 7");
        desc.setTextSize(15);
        pieChart_p7.setDescription(desc);
        pieChart_p7.setRotationEnabled(true);
        addDataSet(pieChart_p7, "P7");

        desc = new Description();
        desc.setText("Pregunta 8");
        desc.setTextSize(15);
        pieChart_p8.setDescription(desc);
        pieChart_p8.setRotationEnabled(true);
        addDataSet(pieChart_p8, "P8");

        desc = new Description();
        desc.setText("Pregunta 9");
        desc.setTextSize(15);
        pieChart_p9.setDescription(desc);
        pieChart_p9.setRotationEnabled(true);
        addDataSet(pieChart_p9, "P9");

        desc = new Description();
        desc.setText("Pregunta 10");
        desc.setTextSize(15);
        pieChart_p10.setDescription(desc);
        pieChart_p10.setRotationEnabled(true);
        addDataSet(pieChart_p10, "P10");

        desc = new Description();
        desc.setText("Pregunta 11");
        desc.setTextSize(15);
        pieChart_p11.setDescription(desc);
        pieChart_p11.setRotationEnabled(true);
        addDataSet(pieChart_p11, "P11");
    }

    private void addDataSet(final PieChart pieChart_p, String childName) {

        final ArrayList<Float> yData = new ArrayList<>();
        final ArrayList<String> xData = new ArrayList<>();

        db.getReference().child("Actividades")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        for (DataSnapshot postsnap :
                                dataSnapshot.getChildren()) {
                            Actividad acti = postsnap.getValue(Actividad.class);
                            if (acti.getNombre().equals(sp_actividad.getSelectedItem().toString())) {
                                actualizarGrafica(pieChart_p,postsnap.getRef(), xData, yData, childName);
                            }
                        }
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

    private void actualizarGrafica(PieChart pieChart_p, DatabaseReference ref, ArrayList<String> xData
            , ArrayList<Float> yData, String preg) {

        ref.child("Encuestas").child(preg).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                yData.clear();
                xData.clear();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    yData.add(new Float(child.getChildrenCount()));
                    xData.add(child.getKey());

                    Log.e(">>p1", "onDataChange: " + child.getKey() + " values:" + child.getChildrenCount());
                }

                Float total = 0f;
                for (Float value :
                        yData) {
                    total += value;
                }

                for (int i = 0; i < yData.size(); i++) {
                    yData.set(i, (yData.get(i) * 100) / total);
                }

                ArrayList<PieEntry> yEntries = new ArrayList<>();
                ArrayList<String> xEntries = new ArrayList<>();
                for (int i = 0; i < yData.size(); i++) {
                    yEntries.add(new PieEntry(yData.get(i), xData.get(i)));
                }

                for (int i = 0; i < xData.size(); i++) {
                    xEntries.add(xData.get(i));
                }

                PieDataSet pieDataSet = new PieDataSet(yEntries, "Respuesta");
                pieDataSet.setSliceSpace(2);
                pieDataSet.setValueTextSize(12);

                ArrayList<Integer> colors = new ArrayList<>();
                colors.add(Color.GRAY);
                colors.add(Color.BLUE);
                colors.add(Color.GREEN);
                colors.add(Color.RED);
                colors.add(Color.YELLOW);

                pieDataSet.setColors(colors);


                Legend legend = pieChart_p.getLegend();
                legend.setForm(Legend.LegendForm.CIRCLE);
                legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

                PieData pieData = new PieData(pieDataSet);
                pieChart_p.setData(pieData);
                pieChart_p.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponentes(View view) {

        pieChart_p1 = view.findViewById(R.id.pc_pregunta_1);
        pieChart_p2 = view.findViewById(R.id.pc_pregunta_2);
        pieChart_p3 = view.findViewById(R.id.pc_pregunta_3);
        pieChart_p4 = view.findViewById(R.id.pc_pregunta_4);
        pieChart_p5 = view.findViewById(R.id.pc_pregunta_5);
        pieChart_p6 = view.findViewById(R.id.pc_pregunta_6);
        pieChart_p7 = view.findViewById(R.id.pc_pregunta_7);
        pieChart_p8 = view.findViewById(R.id.pc_pregunta_8);
        pieChart_p9 = view.findViewById(R.id.pc_pregunta_9);
        pieChart_p10 = view.findViewById(R.id.pc_pregunta_10);
        pieChart_p11 = view.findViewById(R.id.pc_pregunta_11);


        sp_actividad = view.findViewById(R.id.sp_actividad);

        final List<String> actividades = new ArrayList<>();
        db.getReference().child("Actividades")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        for (DataSnapshot postsnap :
                                dataSnapshot.getChildren()
                                ) {
                            Actividad actividad = postsnap.getValue(Actividad.class);
                            actividades.add(actividad.getNombre());
                            final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    getContext(), R.layout.spinner_item, actividades);
                            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                            sp_actividad.setAdapter(spinnerArrayAdapter);
                        }

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
