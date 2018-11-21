package co.highusoft.viveicesi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Actividad;


public class ActividadAdapter extends BaseAdapter {


    ArrayList<Actividad> arreglo;
    Context context;

    public ActividadAdapter(Context context) {

        this.context = context;
        arreglo = new ArrayList<Actividad>();
    }


    public ActividadAdapter() {
        super();
    }

    @Override
    public int getCount() {
        return arreglo.size();
    }

    @Override
    public Object getItem(int i) {
        return arreglo.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View renglonActividad = inflater.inflate(R.layout.renglon_actividad, null);
        Actividad actividad = arreglo.get(i);


        TextView textViewPorcentaje = renglonActividad.findViewById(R.id.tvrg_porcentaje);
        TextView textViewTitulo = renglonActividad.findViewById(R.id.tvrg_titulo);

//       textViewPorcentaje.setText(actividad.getPorcentaje());
        textViewTitulo.setText(actividad.getNombre());

        return renglonActividad;
    }
}
