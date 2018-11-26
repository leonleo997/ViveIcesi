package co.highusoft.viveicesi.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Actividad;
import co.highusoft.viveicesi.view.fragments.FragActividad;


public class ActividadAdapter extends BaseAdapter {


    private ArrayList<Actividad> actividades;
    private Context context;

    private FirebaseStorage storage;


    public ActividadAdapter(Context context) {

        this.context = context;
        actividades = new ArrayList<Actividad>();
    }


    public ActividadAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return actividades.size();
    }

    @Override
    public Object getItem(int i) {
        return actividades.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View renglonActividad = inflater.inflate(R.layout.renglon_actividad, null);
        Actividad actividad = actividades.get(i);


        TextView tv_valoracion = renglonActividad.findViewById(R.id.tv_valoracion);
        TextView tv_titulo = renglonActividad.findViewById(R.id.tv_titulo);
        final ImageView iv_actividad = renglonActividad.findViewById(R.id.iv_actividad);

        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("fotos").child(actividad.getImg());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("imagen", uri.toString());
                Glide.with(context).load(uri)
                        .apply(new RequestOptions()
                                .centerCrop())
                        .into(iv_actividad);
            }
        });

        tv_valoracion.setText("50 %");
        tv_titulo.setText(actividad.getNombre());


        return renglonActividad;
    }

    public void addActividad(Actividad actividad) {
        actividades.add(actividad);
        notifyDataSetChanged();
    }
}
