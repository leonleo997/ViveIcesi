package co.highusoft.viveicesi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Comentario;

/**
 * Created by Domiciano on 01/05/2018.
 */

/*
    PARA USAR ESTE ADAPTADOR DEBE TENER EL ARCHIVO renglon.xml EN LA CARPETA LAYOUT
    DEBE TAMBIÉN USAR 
*/
public class Adaptador extends BaseAdapter {
    ArrayList<Comentario> comentarios;
    Context context;

    public Adaptador(Context context) {
        this.context = context;
        comentarios = new ArrayList<Comentario>();
    }

    @Override
    public int getCount() {
        return comentarios.size();
    }

    @Override
    public Object getItem(int position) {
        return comentarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View renglon = inflater.inflate(R.layout.renglon, null);
        TextView tv_comentario =renglon.findViewById(R.id.tv_comentario);
        Button btn_like=renglon.findViewById(R.id.like_btn);

        tv_comentario.setText(comentarios.get(position).getContenido());

        return renglon;
    }

    public void addComment(Comentario comentario){
        comentarios.add(comentario);
        notifyDataSetChanged();
    }
}
