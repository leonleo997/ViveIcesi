package co.highusoft.viveicesi.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Comentario;
import co.highusoft.viveicesi.model.Evento;

/**
 * Created by Domiciano on 01/05/2018.
 */

/*
    PARA USAR ESTE ADAPTADOR DEBE TENER EL ARCHIVO renglon.xml EN LA CARPETA LAYOUT
    DEBE TAMBIÉN USAR 
*/
public class Adaptador extends BaseAdapter {
    ArrayList<Evento> arreglo;
    Context context;

    public Adaptador(Context context) {
        this.context = context;
        arreglo = new ArrayList<Evento>();
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

        TextView titulo=renglon.findViewById(R.id.tv_titulo);
        TextView area=renglon.findViewById(R.id.tv_area);
        TextView fecha=renglon.findViewById(R.id.tv_fecha);
        TextView lugar=renglon.findViewById(R.id.tv_lugar);
        TextView descripción=renglon.findViewById(R.id.tv_descripcion);

        Log.e(">>",evento.getNombre());
        titulo.setText(evento.getNombre());
        area.setText(evento.getArea());
        fecha.setText(evento.getmDay()+"/"+evento.getmMonth()+"/"+evento.getmYear()+"  "+evento.getHour()+":"+evento.getMin());
        lugar.setText(evento.getLugar());
        descripción.setText(evento.getDescripcion());


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
}
