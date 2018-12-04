package co.highusoft.viveicesi.view.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.LinkedList;
import java.util.List;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Actividad;
import co.highusoft.viveicesi.model.Evento;
import co.highusoft.viveicesi.utilities.RoundedCornersTransformation;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragEvento.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragEvento#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragEvento extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Evento evento;

    private OnFragmentInteractionListener mListener;

    public FragEvento() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragEvento.
     */
    // TODO: Rename and change types and number of parameters
    public static FragEvento newInstance(String param1, String param2) {
        FragEvento fragment = new FragEvento();
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
        evento = (Evento) bundle.getSerializable("evento");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private ImageView iv_img;

    private TextView tv_descripcion;
    private TextView tv_area;
    private TextView tv_fecha;
    private TextView tv_hora;
    private TextView tv_lugar;
    private TextView tv_titulo;

    private FirebaseStorage storage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_evento, container, false);

        storage = FirebaseStorage.getInstance();
        iv_img = view.findViewById(R.id.iv_imagen);
        tv_descripcion = view.findViewById(R.id.tv_descripcion);
        tv_area = view.findViewById(R.id.tv_area);
        tv_fecha = view.findViewById(R.id.tv_fecha);
        tv_hora = view.findViewById(R.id.tv_hora);
        tv_lugar = view.findViewById(R.id.tv_lugar);
        tv_titulo = view.findViewById(R.id.tv_nombre);


        tv_lugar.setText(evento.getLugar());
        tv_descripcion.setText(evento.getDescripcion());
        tv_area.setText(evento.getArea());
        tv_fecha.setText(evento.getmDay() + "/" + evento.getmMonth() + "/" + evento.getmYear());
        tv_titulo.setText(evento.getNombre());
        String min = "";
        if (evento.getMin() < 9)
            min = "0" + evento.getMin();
        else
            min = "" + evento.getMin();

        tv_hora.setText(evento.getHour() + ":" + min);

        StorageReference storageReference = storage.getReference().child("fotos").child(evento.getImg());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                int sCorner = 15;
                int sMargin = 2;
                int sBorder = 10;
                String sColor = "#2874A6";
                List<Transformation<Bitmap>> transforms = new LinkedList<>();
                transforms.add(new CenterCrop(getContext()));
                transforms.add(new RoundedCornersTransformation(getContext(), sCorner, sMargin, sColor, sBorder));

                MultiTransformation transformation = new MultiTransformation<Bitmap>(transforms);

                Glide.with(FragEvento.this).load(uri)
                        .apply(new RequestOptions()
                                .bitmapTransform(transformation))
                        .into(iv_img);
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
