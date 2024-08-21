package com.example.quadrodehoras;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.quadrodehoras.databinding.FragmentFirstBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FirstFragment extends Fragment {

    ArrayList<Jornadas> listJornadas;
    int equipesNecessarias = 0;
    ArrayList<Datas> Datas = new ArrayList<>();
    private FragmentFirstBinding binding;
    private Calendar selectedDateTime;
    private int TotalHoursInDay;
    private TextView result;
    private String myJornada;

    // Método auxiliar para comparar se duas datas representam o mesmo dia
    private static boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        result = binding.txtResult;

        binding.button1.setOnClickListener(v ->
                {
                    listJornadas = new ArrayList<>();
                    listJornadas.add(new Jornadas(1, 12, 24));
                    listJornadas.add(new Jornadas(2, 12, 48));
                    myJornada = "12/24-12/48";
                    binding.txtEquipeResult.setText(String.valueOf(equipesNecessarias));
                }
        );

        binding.button2.setOnClickListener(v ->

                {
                    listJornadas = new ArrayList<>();
                    listJornadas.add(new Jornadas(1, 12, 24));
                    listJornadas.add(new Jornadas(2, 12, 72));
                    myJornada = "12/24-12/72";
                    binding.txtEquipeResult.setText(String.valueOf(equipesNecessarias));
                }
        );

        binding.button3.setOnClickListener(v ->

                {
                    listJornadas = new ArrayList<>();
                    listJornadas.add(new Jornadas(1, 12, 72));
                    myJornada = "12/72";
                    binding.txtEquipeResult.setText(String.valueOf(equipesNecessarias));
                }
        );


        binding.button4.setOnClickListener(v ->

                {
                    listJornadas = new ArrayList<>();
                    listJornadas.add(new Jornadas(1, 24, 72));
                    myJornada = "24/72";
                    binding.txtEquipeResult.setText(String.valueOf(equipesNecessarias));
                }
        );


        binding.button5.setOnClickListener(v ->

                {
                    listJornadas = new ArrayList<>();
                    listJornadas.add(new Jornadas(1, 6, 18));
                    myJornada = "6/18";
                    binding.txtEquipeResult.setText(String.valueOf(equipesNecessarias));
                }
        );


        Button selectDateTimeButton = binding.buttonVerificarPrevisao;

        selectedDateTime = Calendar.getInstance();

        selectDateTimeButton.setOnClickListener(v -> showDateTimePicker());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Equipe getJornadaEquipe(List<Jornadas> jornadas, Date dataInicial, int totalTurnosNoDia, int qtdJornadas, int indiceJornada, int indiceTurno) {
        List<Horario> horariosTrabalho = new ArrayList<>();
        List<Horario> horariosFolga = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        for (int i = 0; i < qtdJornadas; i++) {
            Jornadas jornadaAtual = jornadas.get(indiceJornada);
            Date dataFimTrabalho = addTimeToDate(dataInicial, jornadaAtual.getHrTrabalho(), 0);
            String nomeTurno = totalTurnosNoDia == 1 ? "Único" : (indiceTurno + 1) + "º Turno";
            horariosTrabalho.add(new Horario(nomeTurno, (indiceTurno + 1), dataInicial, dataFimTrabalho));
            Date dataFimFolga = addTimeToDate(dataFimTrabalho, jornadaAtual.getHrFolgas(), 0);
            horariosFolga.add(new Horario(nomeTurno, (indiceTurno + 1), dataFimTrabalho, dataFimFolga));
            System.out.println("dataInicial: " + dateFormat.format(dataInicial)); // Pode ser removido em produção
            indiceTurno = (indiceTurno + 1) % totalTurnosNoDia; // Incrementa e mantém dentro do limite
            indiceJornada = (indiceJornada + 1) % qtdJornadas; // Incrementa e mantém dentro do limite
            dataInicial = dataFimFolga;
        }

        return setHorariosToEquipe(horariosTrabalho, horariosFolga);
    }

    private Date addTimeToDate(Date date, int hoursToAdd, int minutesToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.HOUR_OF_DAY, hoursToAdd);
        calendar.add(Calendar.MINUTE, minutesToAdd);
        calendar.add(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    private Date addDayToDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DAY_OF_MONTH, day);

        return calendar.getTime();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Equipe setHorariosToEquipe(List<Horario> horariosTrabalho, List<Horario> horariosFolga) {


        Equipe equipe = new Equipe();

        for (Horario horario : horariosTrabalho) {
            equipe.horariosTrabalho.add(horario);
        }

        for (Horario horario : horariosFolga) {
            equipe.horariosFolga.add(horario);
        }

        return equipe;
    }

    private int calcularEquipesParaFolga(int horasTrabalho, int horasFolga, int qtdVezesTrabalhou) {

        // calcula o total de horas de trabalho por dia
        int TotalHoursInDay = horasTrabalho / qtdVezesTrabalhou;
        int diferencaHoras = horasFolga + horasTrabalho;
        if (diferencaHoras <= 0) {
            return 0;
        }
        return (int) Math.ceil((double) diferencaHoras / (qtdVezesTrabalhou * TotalHoursInDay));
    }

    private void showDateTimePicker() {
        // Date Picker
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    selectedDateTime.set(Calendar.YEAR, year);
                    selectedDateTime.set(Calendar.MONTH, monthOfYear);
                    selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Time Picker
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            requireContext(),
                            (view1, hourOfDay, minute) -> {
                                selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                selectedDateTime.set(Calendar.MINUTE, minute);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    updateDateTimeTextView();
                                }
                            },
                            selectedDateTime.get(Calendar.HOUR_OF_DAY),
                            selectedDateTime.get(Calendar.MINUTE),
                            true // 24-hour format
                    );
                    timePickerDialog.show();
                },
                selectedDateTime.get(Calendar.YEAR),
                selectedDateTime.get(Calendar.MONTH),
                selectedDateTime.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateDateTimeTextView() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDateTime = dateFormat.format(selectedDateTime.getTime());
        System.out.println(formattedDateTime);

        Date startDate = selectedDateTime.getTime();
        StringBuilder resultText = new StringBuilder();
        int horasTrabalho = listJornadas.stream().mapToInt(Jornadas::getHrTrabalho).sum(); // 12 horas + 12 horas
        int horasFolga = listJornadas.stream().mapToInt(Jornadas::getHrFolgas).sum(); // 24 horas + 72 horas
        TotalHoursInDay = horasTrabalho / listJornadas.size();
        equipesNecessarias = calcularEquipesParaFolga(horasTrabalho, horasFolga, listJornadas.size());
        int diasNecessarios = (horasTrabalho + horasFolga) / 24;
        int totalTurnosNoDia = 24 / TotalHoursInDay;
        int qtdJornadas = listJornadas.size();

        int indiceJornada = 0;
        int indiceTurno = 0;
        ArrayList<Datas> listDatas = new ArrayList<>();
        for (int i = 0; i < equipesNecessarias; i++) {

            Equipe equipe = getJornadaEquipe(listJornadas, startDate, totalTurnosNoDia, qtdJornadas, indiceJornada, indiceTurno);

            String eq = numberToLetter(i + 1);
            for (Horario horario : equipe.horariosTrabalho) {
                String nomeTurno = totalTurnosNoDia == 1 ? "Único" : horario.turno + "º Turno";
                Datas data = new Datas(myJornada, horario.turno, eq);
                data.setNameTurno(nomeTurno);
                data.setDataTimeInicio(horario.horaInicio);
                data.setDataTimeFim(horario.horaFim);
                listDatas.add(data);
            }
            // Atualiza índices para a próxima iteração
            indiceJornada = (indiceJornada + 1) % qtdJornadas;
            indiceTurno = (indiceTurno + 1) % totalTurnosNoDia;

            startDate = equipe.horariosTrabalho.get(0).horaFim;
            if (equipeJaAdicionada(listDatas, startDate)) {
                indiceJornada = (indiceJornada + 1) % qtdJornadas;
                indiceTurno = (indiceTurno + 1) % totalTurnosNoDia;
                startDate = addTimeToDate(startDate, TotalHoursInDay, 0);
            }

        }


        for (int i = 0; i < diasNecessarios; i++) {
            Date data = addDayToDate(selectedDateTime.getTime(), i);
            resultText.append("\nData: " ).append(dateFormat.format(data));
            listDatas.forEach(datas ->
            {
                if (isSameDay(datas.getDataTimeInicio(), data)) {
                    resultText.append("\nEquipe: " ).append(datas.getEquipe());
                    resultText.append("\nHorários de trabalho: \n" );
                    resultText.append(datas).append("\n" );
                }

            });

        }



        result.setText(resultText.toString());
    }

    private boolean equipeJaAdicionada(List<Datas> listDatas, Date data) {
        for (Datas dataEquipe : listDatas) {
            if (dataEquipe.getDataTimeInicio().equals(data)) {
                return true; // Equipe já adicionada
            }
        }
        return false; // Equipe não encontrada
    }


    private String numberToLetter(int number) {
        if (number >= 1 && number <= 26) {
            return String.valueOf((char) ('A' + number - 1));
        } else {
            return "Número inválido";
        }
    }
}
