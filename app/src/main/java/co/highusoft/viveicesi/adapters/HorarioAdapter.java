package co.highusoft.viveicesi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Actividad;
import co.highusoft.viveicesi.model.Horario;

public class HorarioAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Horario> horarios;

    public HorarioAdapter(Context context){

        this.context= context;
        horarios = new ArrayList<Horario>();
    }

    @Override
    public int getCount() {
        return horarios.size();
    }

    @Override
    public Object getItem(int i) {
        return horarios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View renglon = inflater.inflate(R.layout.renglon_horario, null);
        Horario horario= horarios.get(i);

        TextView tv_dia_semana=renglon.findViewById(R.id.tv_dia_semana);
        TextView tv_hora_inicio=renglon.findViewById(R.id.tv_hora_inicio);
        TextView tv_hora_fin=renglon.findViewById(R.id.tv_hora_fin);
        TextView tv_ubicacion=renglon.findViewById(R.id.tv_ubicacion);

        tv_dia_semana.setText(horario.getDiaSemana());
        tv_hora_inicio.setText(horario.getHoraEntrada());
        tv_hora_fin.setText(horario.getHoraSalida());
        tv_ubicacion.setText(horario.getLugar());

        return renglon;
    }

    public void addHorario(Horario horario) {
        horarios.add(horario);
        notifyDataSetChanged();
    }

    public ArrayList<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<Horario> horarios) {
        this.horarios = horarios;
        notifyDataSetChanged();
    }
}
