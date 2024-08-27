package com.example.quadrodehoras;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quadrodehoras.databinding.ModelEquipeItemBinding; // Substitua pelo caminho correto do seu layout

import java.util.List;

public class EquipeAdapter extends RecyclerView.Adapter<EquipeAdapter.EquipeViewHolder> {

    private final List<Equipe> equipes;

    public EquipeAdapter(List<Equipe> equipes) {
        this.equipes = equipes;
    }

    static class EquipeViewHolder extends RecyclerView.ViewHolder {
        private final ModelEquipeItemBinding binding;

        public EquipeViewHolder(ModelEquipeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public EquipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ModelEquipeItemBinding binding = ModelEquipeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EquipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EquipeViewHolder holder, int position) {
        Equipe equipe = equipes.get(position);
        // Preencha os elementos do layout com os dados da equipe
        holder.binding.txtTitle.setText("Equipe: " + equipe.equipe);

        StringBuilder st = new StringBuilder();
        st.append("Horários de trabalho:");
        for (Horario horario : equipe.horariosTrabalho) {
            st.append("\n");
            st.append(horario);
        }
        st.append("\nHorários de Folga:");
        for (Horario horario : equipe.horariosFolga) {
            st.append("\n");
            st.append(horario);
        }
        holder.binding.txtSubTitle.setText(st.toString());
        // ... outros campos a serem preenchidos
    }




    @Override
    public int getItemCount() {
        return equipes.size();
    }


    private String numberToLetter(int number) {
        if (number >= 1 && number <= 26) {
            return String.valueOf((char) ('A' + number - 1));
        } else {
            return "Número inválido";
        }
    }
}