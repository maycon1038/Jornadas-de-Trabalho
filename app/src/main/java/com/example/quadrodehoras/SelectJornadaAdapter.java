package com.example.quadrodehoras;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quadrodehoras.databinding.ModelJornadaItemSelectBinding;

import java.util.ArrayList;
import java.util.List;

public class SelectJornadaAdapter extends RecyclerView.Adapter<SelectJornadaAdapter.JornadaViewHolder> {

    private final List<Jornada> jornadas;

    private final List<Jornada> jornadasSelecionadas = new ArrayList<>();

    public SelectJornadaAdapter(List<Jornada> jornadas) {
        this.jornadas = jornadas;
        System.out.println("JornadaAdapter: " + jornadas.size());
    }

    @NonNull
    @Override
    public JornadaViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        ModelJornadaItemSelectBinding binding = ModelJornadaItemSelectBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new JornadaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(JornadaViewHolder holder, int position) {
        Jornada jornada = jornadas.get(position);
        int horasTrabalho = jornada.getHrTrabalho();
        int horasFolga = jornada.getHrFolgas();
        holder.binding.txtTitle.setText("Jornada: "+jornada.idDoc);
        holder.binding.txtSubTitle.setText(horasTrabalho + "/" + horasFolga);
        setJornada(holder, jornada);
    }

    private void setJornada(JornadaViewHolder holder, Jornada jornada) {
        holder.binding.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                jornadasSelecionadas.add(jornada);
            } else {
                jornadasSelecionadas.remove(jornada);
            }
        });
    }

    @Override
    public int getItemCount() {
        return jornadas.size();
    }


    public  List<Jornada> getJornadasSelected() {
        return jornadasSelecionadas;
    }

    static class JornadaViewHolder extends RecyclerView.ViewHolder {
        private final ModelJornadaItemSelectBinding binding;

        public JornadaViewHolder(ModelJornadaItemSelectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}