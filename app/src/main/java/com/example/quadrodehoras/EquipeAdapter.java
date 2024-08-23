package com.example.quadrodehoras;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quadrodehoras.databinding.ModelEquipeItemBinding; // Substitua pelo caminho correto do seu layout

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        holder.binding.txtTitle.setText("Equipe: " + equipe.jornada);

        StringBuilder st = new StringBuilder();


        st.append("\nEquipe: ").append(equipe.jornada);
        st.append("\nHorários de trabalho: ");
        for (Horario horario : equipe.horariosTrabalho) {
            System.out.println("    " + horario);
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