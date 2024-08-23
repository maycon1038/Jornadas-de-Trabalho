package com.example.quadrodehoras;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quadrodehoras.databinding.ModelJornadaItemBinding;
import java.util.List;

public class JornadaAdapter extends RecyclerView.Adapter<JornadaAdapter.JornadaViewHolder> {

    private final List<Jornada> jornadas;

    public JornadaAdapter(List<Jornada> jornadas) {
        this.jornadas = jornadas;
        System.out.println("JornadaAdapter: " + jornadas.size());
    }

    @NonNull
    @Override
    public JornadaViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        ModelJornadaItemBinding binding = ModelJornadaItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new JornadaViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(JornadaViewHolder holder, int position) {
        Jornada jornada = jornadas.get(position);
        int horasTrabalho = jornada.getHrTrabalho();
        int horasFolga = jornada.getHrFolgas();
        holder.binding.txtTitle.setText("Jornada: "+jornada.idDoc);
        holder.binding.txtSubTitle.setText(horasTrabalho + "/" + horasFolga);
    }

    @Override
    public int getItemCount() {
        return jornadas.size();
    }



    static class JornadaViewHolder extends RecyclerView.ViewHolder {
        private final ModelJornadaItemBinding binding;

        public JornadaViewHolder(ModelJornadaItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}