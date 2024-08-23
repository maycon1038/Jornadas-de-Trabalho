package com.example.quadrodehoras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.quadrodehoras.databinding.ModelJornadaItemBinding;
import java.util.List;

public class TurnoAdapter extends BaseAdapter {

    private final List<Turno> turnos;
    private final LayoutInflater inflater;

    public TurnoAdapter(Context context, List<Turno> turnos) {
        this.turnos = turnos;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return turnos.size();
    }

    @Override
    public Turno getItem(int position) {
        return turnos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // Pode ser o ID do turno, se disponível
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModelJornadaItemBinding binding;
        if (convertView == null) {
            binding = ModelJornadaItemBinding.inflate(inflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding); // Armazena o binding para reuso
        } else {
            binding = (ModelJornadaItemBinding) convertView.getTag(); // Reutiliza o binding
        }

        Turno turno = getItem(position);
        binding.txtTitle.setText(String.valueOf(turno.id));
        binding.txtSubTitle.setText(turno.name);

        return convertView;
    }
}