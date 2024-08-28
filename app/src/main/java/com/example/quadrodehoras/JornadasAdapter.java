package com.example.quadrodehoras;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quadrodehoras.databinding.ModelJornadaItemBinding;

import java.util.ArrayList;
import java.util.List;

public class JornadasAdapter extends RecyclerView.Adapter<JornadasAdapter.JornadaViewHolder> {

    private final ArrayList<Jornadas> jornadas;

    public JornadasAdapter(ArrayList<Jornadas> jornadas) {
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
        Jornadas jornada = jornadas.get(position);
        holder.binding.txtTitle.setText("Jornada: "+jornada.getName());

        StringBuilder st = new StringBuilder();
        st.append("Horários:");
        for (Jornada j : jornada.listJornadas) {
            st.append("\n");
            st.append(j.hrTrabalho);
            st.append("/");
            st.append(j.hrFolgas);
        }
        holder.binding.txtSubTitle.setText(st.toString());
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