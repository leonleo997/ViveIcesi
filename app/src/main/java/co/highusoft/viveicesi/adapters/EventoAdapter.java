package co.highusoft.viveicesi.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Comentario;
import co.highusoft.viveicesi.model.Evento;
import co.highusoft.viveicesi.utilities.RoundedCornersTransformation;

/**
 * Created by Domiciano on 01/05/2018.
 */

/*
    PARA USAR ESTE ADAPTADOR DEBE TENER EL ARCHIVO renglon.xml EN LA CARPETA LAYOUT
    DEBE TAMBIÉN USAR 
*/
public class EventoAdapter extends BaseAdapter {
    private ArrayList<Evento> arreglo;
    private Context context;
    private FirebaseStorage storage;

    public EventoAdapter(Context context) {
        this.context = context;
        arreglo = new ArrayList<Evento>();
        storage = FirebaseStorage.getInstance();
    }

    @Override
    public int getCount() {
        return arreglo.size();
    }

    @Override
    public Object getItem(int position) {
        return arreglo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View renglon = inflater.inflate(R.layout.renglon, null);
        Evento evento = arreglo.get(position);

        TextView titulo = renglon.findViewById(R.id.tv_titulo);
//        TextView area = renglon.findViewById(R.id.tv_area);
        TextView fecha = renglon.findViewById(R.id.tv_fecha);
        TextView lugar = renglon.findViewById(R.id.tv_lugar);
//        TextView descripción = renglon.findViewById(R.id.tv_descripcion);

        titulo.setText(evento.getNombre());
//        area.setText(evento.getArea());
        String year=evento.getmYear()+"";
        String month=evento.getmMonth()+"";
        String day=evento.getmDay()+"";
        String hour="";
        if(evento.getHour()<10){
            hour="0"+evento.getHour();
        }else{
            hour=""+evento.getHour();
        }
        String minute="";
        if(evento.getMin()<10){
            minute="0"+evento.getMin();
        }else{
            minute=""+evento.getMin();
        }



        fecha.setText(day + "-" + month + "-" + year + "  " + hour + ":" + minute);
        lugar.setText(evento.getLugar());
//        descripción.setText(evento.getDescripcion());

        final ImageView iv_evento = renglon.findViewById(R.id.iv_foto);


        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("fotos").child(evento.getImg());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("imagen", uri.toString());
                int sCorner = 15;
                int sMargin = 2;
                int sBorder = 10;
                String sColor = "#2874A6";

                List<Transformation<Bitmap>> transforms = new LinkedList<>();
                transforms.add(new CenterCrop(context));
                transforms.add(new RoundedCornersTransformation(context, sCorner, sMargin, sColor, sBorder));

                MultiTransformation transformation = new MultiTransformation<Bitmap>(transforms);

                Glide.with(context).load(uri)
                        .apply(new RequestOptions()
                                .bitmapTransform(transformation))
                        .into(iv_evento);
            }
        });


        return renglon;
    }

//    public void addComment(Comentario comentario){
//        comentarios.add(comentario);
//        notifyDataSetChanged();
//    }

    public void addEvent(Evento evento) {
        arreglo.add(evento);
        notifyDataSetChanged();
    }

    public void clear(){
        arreglo.clear();
        notifyDataSetChanged();
    }
}
