package com.example.quadrodehoras;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.quadrodehoras.databinding.ModelEquipeItemBinding; // Substitua pelo caminho correto do seu layout

import java.util.Calendar;
import java.util.Date;
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
        int horasTrabalho = equipe.jornadas.listJornadas.stream().mapToInt(Jornada::getHrTrabalho).sum(); // 12 horas + 12 horas
        int horasFolga = equipe.jornadas.listJornadas.stream().mapToInt(Jornada::getHrFolgas).sum(); // 12 horas + 12 horas
        int TotalHoursInDay = horasTrabalho / equipe.jornadas.listJornadas.size();
        int totalTurnosNoDia = 24 / TotalHoursInDay;

        Date startDate = equipe.dateStart;
        StringBuilder st = new StringBuilder();
        st.append("Horários de trabalho:");
        for (Jornada jornadaAtual : equipe.jornadas.listJornadas) {
            Date dataFimTrabalho = addTimeToDate(startDate, jornadaAtual.getHrTrabalho(), 0);
            String nomeTurno = totalTurnosNoDia == 1 ? "Turno Único (24h)" : jornadaAtual.turno + "º Turno";



            Horario ht = new Horario(nomeTurno, jornadaAtual.turno, startDate, dataFimTrabalho);
            Date dataFimFolga = addTimeToDate(dataFimTrabalho, jornadaAtual.getHrFolgas(), 0);
            Horario hf =new Horario(nomeTurno, jornadaAtual.turno, dataFimTrabalho, dataFimFolga);
            //System.out.println("dataInicial: " + dateFormat.format(startDate)); // Pode ser removido em produção
            st.append("\n");
            st.append(ht);
            startDate = dataFimFolga;
        }
        holder.binding.txtSubTitle.setText(st.toString());
        // ... outros campos a serem preenchidos
    }

    private Date addTimeToDate(Date date, int hoursToAdd, int minutesToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.HOUR_OF_DAY, hoursToAdd);
        calendar.add(Calendar.MINUTE, minutesToAdd);
        calendar.add(Calendar.SECOND, 0);

        return calendar.getTime();
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