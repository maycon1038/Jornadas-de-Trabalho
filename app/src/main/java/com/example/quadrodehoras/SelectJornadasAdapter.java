package com.example.quadrodehoras;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quadrodehoras.databinding.ModelJornadasItemSelectBinding;

import java.util.List;

public class SelectJornadasAdapter extends RecyclerView.Adapter<SelectJornadasAdapter.JornadaViewHolder> {

    private final List<Jornadas> jornadas;

    private RadioButton radioButtonSelecionado = null;
    private  Jornadas  jornadasSelecionada;

    public SelectJornadasAdapter(List<Jornadas> jornadas) {
        this.jornadas = jornadas;
        System.out.println("JornadaAdapter: " + jornadas.size());
    }

    @NonNull
    @Override
    public JornadaViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        ModelJornadasItemSelectBinding binding = ModelJornadasItemSelectBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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
        setJornada(holder, jornada);
    }

    private void setJornada(JornadaViewHolder holder, Jornadas jornada) {
        holder.binding.radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (radioButtonSelecionado != null) {
                    radioButtonSelecionado.setChecked(false); // Desmarca o RadioButton anterior
                }
                radioButtonSelecionado = holder.binding.radioButton; // Atualiza a referência
                jornadasSelecionada = jornada;
            }
            // Não precisa fazer nada quando desmarcado, pois só um RadioButton pode ser selecionado por vez
        });
    }

    @Override
    public int getItemCount() {
        return jornadas.size();
    }


    public  Jornadas getJornadasSelected() {
        return jornadasSelecionada;
    }

    static class JornadaViewHolder extends RecyclerView.ViewHolder {
        private final ModelJornadasItemSelectBinding binding;

        public JornadaViewHolder(ModelJornadasItemSelectBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}