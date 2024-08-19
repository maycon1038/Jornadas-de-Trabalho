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

    private FragmentFirstBinding binding;
    ArrayList<Integer> listHorariosTrabalhos;

    ArrayList<Integer> listHorariosFolgas;
    int equipesNecessarias = 0;

    private Calendar selectedDateTime;
    private int TotalHoursInDay;
    private TextView result;

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
            listHorariosTrabalhos = new ArrayList<>();
            listHorariosFolgas = new ArrayList<>();
            listHorariosTrabalhos.add(12);
            listHorariosTrabalhos.add(12);
            listHorariosFolgas.add(24);
            listHorariosFolgas.add(48);
            int horasTrabalho = listHorariosTrabalhos.stream().mapToInt(Integer::intValue).sum(); // 12 horas + 12 horas
            int horasFolga =  listHorariosFolgas.stream().mapToInt(Integer::intValue).sum(); // 24 horas + 72 horas
            TotalHoursInDay = horasTrabalho / listHorariosTrabalhos.size();
            equipesNecessarias = calcularEquipesParaFolga(horasTrabalho, horasFolga, listHorariosTrabalhos.size());
            binding.txtEquipeResult.setText(String.valueOf(equipesNecessarias));
        }
        );

        binding.button2.setOnClickListener(v ->

                {
                    listHorariosTrabalhos = new ArrayList<>();
                    listHorariosFolgas = new ArrayList<>();
                    listHorariosTrabalhos.add(12);
                    listHorariosTrabalhos.add(12);
                    listHorariosFolgas.add(24);
                    listHorariosFolgas.add(72);
                    int horasTrabalho = listHorariosTrabalhos.stream().mapToInt(Integer::intValue).sum(); // 12 horas + 12 horas
                    int horasFolga =  listHorariosFolgas.stream().mapToInt(Integer::intValue).sum(); // 24 horas + 72 horas
                    TotalHoursInDay = horasTrabalho / listHorariosTrabalhos.size();
                      equipesNecessarias = calcularEquipesParaFolga(horasTrabalho, horasFolga, listHorariosTrabalhos.size());
                    binding.txtEquipeResult.setText(String.valueOf(equipesNecessarias));
                }
        );

        binding.button3.setOnClickListener(v ->

                {
                    listHorariosTrabalhos = new ArrayList<>();
                    listHorariosFolgas = new ArrayList<>();
                    listHorariosTrabalhos.add(12);
                    listHorariosFolgas.add(72);
                    int horasTrabalho = listHorariosTrabalhos.stream().mapToInt(Integer::intValue).sum(); // 12 horas + 12 horas
                    int horasFolga =  listHorariosFolgas.stream().mapToInt(Integer::intValue).sum(); // 24 horas + 72 horas
                    TotalHoursInDay = horasTrabalho / listHorariosTrabalhos.size();
                      equipesNecessarias = calcularEquipesParaFolga(horasTrabalho, horasFolga, listHorariosTrabalhos.size());
                    binding.txtEquipeResult.setText(String.valueOf(equipesNecessarias));
                }
        );


        binding.button4.setOnClickListener(v ->

                {
                    listHorariosTrabalhos = new ArrayList<>();
                    listHorariosFolgas = new ArrayList<>();
                    listHorariosTrabalhos = new ArrayList<>();
                    listHorariosFolgas = new ArrayList<>();
                    listHorariosTrabalhos.add(24);
                    listHorariosFolgas.add(72);
                    int horasTrabalho = listHorariosTrabalhos.stream().mapToInt(Integer::intValue).sum(); // 12 horas + 12 horas
                    int horasFolga =  listHorariosFolgas.stream().mapToInt(Integer::intValue).sum(); // 24 horas + 72 horas
                    TotalHoursInDay = horasTrabalho / listHorariosTrabalhos.size();
                      equipesNecessarias = calcularEquipesParaFolga(horasTrabalho, horasFolga, listHorariosTrabalhos.size());
                    binding.txtEquipeResult.setText(String.valueOf(equipesNecessarias));
                }
        );


        binding.button5.setOnClickListener(v ->

                {
                    listHorariosTrabalhos = new ArrayList<>();
                    listHorariosFolgas = new ArrayList<>();
                    listHorariosTrabalhos = new ArrayList<>();
                    listHorariosFolgas = new ArrayList<>();
                    listHorariosTrabalhos.add(6);
                    listHorariosFolgas.add(18);
                    int horasTrabalho = listHorariosTrabalhos.stream().mapToInt(Integer::intValue).sum(); // 12 horas + 12 horas
                    int horasFolga =  listHorariosFolgas.stream().mapToInt(Integer::intValue).sum(); // 24 horas + 72 horas
                    TotalHoursInDay = horasTrabalho / listHorariosTrabalhos.size();
                      equipesNecessarias = calcularEquipesParaFolga(horasTrabalho, horasFolga, listHorariosTrabalhos.size());
                    binding.txtEquipeResult.setText(String.valueOf(equipesNecessarias));
                }
        );




        Button selectDateTimeButton = binding.buttonVerificarPrevisao;

        selectedDateTime = Calendar.getInstance();

        selectDateTimeButton.setOnClickListener(v -> showDateTimePicker()); }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Equipe getJornadaEquipe(ArrayList<Integer> listHorariosTrabalhos, ArrayList<Integer> listHorariosFolgas, Date dataInicial) {
        List<Horario> horariosTrabalho = new ArrayList<>();
        List<Horario> horariosFolga = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        int totalVezesNodia = 24 /TotalHoursInDay;
        Date dateTrEnd;
        Date dateFgEnd;
        int cturnos = 0;
        int Turnos = cturnos;

        for (int tu = 0; tu < totalVezesNodia; tu++) {
                               dateTrEnd = addTimeToDate(dataInicial, listHorariosTrabalhos.get(cturnos), 0);
                               horariosTrabalho.add(new Horario(totalVezesNodia == 1?  "Único" :  (Turnos + 1) + "º Turno", dataInicial, dateTrEnd));
                               dateFgEnd =  addTimeToDate(dateTrEnd, listHorariosFolgas.get(cturnos), 0);
                               // dateStart = dateFgEnd;
                               horariosFolga.add(new Horario( "Folga ", dateTrEnd, dateFgEnd));
                              System.out.println("dataInicial: "+ dateFormat.format(dataInicial));
            if(Turnos == totalVezesNodia - 1){
                Turnos = 0;
            }else {
                Turnos++;
            }
          dataInicial = dateFgEnd;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    private  Equipe setHorariosToEquipe(List<Horario> horariosTrabalho, List<Horario> horariosFolga) {


        Equipe equipe = new Equipe();

        for (Horario horario : horariosTrabalho) {
            equipe.horariosTrabalho.add(horario);
        }

        for (Horario horario : horariosFolga) {
            equipe.horariosFolga.add(horario);
        }

        return equipe;
    }

    private   int calcularEquipesParaFolga(int horasTrabalho, int horasFolga, int qtdVezesTrabalhou) {

        // calcula o total de horas de trabalho por dia
        int TotalHoursInDay = horasTrabalho / qtdVezesTrabalhou;
        int TotalDaysInCiclo = (horasTrabalho + horasFolga) / 24;
        System.out.println("horasTrabalho: "+horasTrabalho);
        System.out.println("horasFolga: "+horasFolga);
        System.out.println("TotalHoursInDay: "+TotalHoursInDay);
        System.out.println("TotalDaysInCiclo: "+TotalDaysInCiclo);

        // Calcula a diferença entre horas de folga e horas de trabalho da equipe original
        int diferencaHoras = horasFolga + horasTrabalho;

        // Se a diferença for negativa, significa que a equipe original já trabalha mais do que folga,
        // então não são necessárias equipes adicionais
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String formattedDateTime = dateFormat.format(selectedDateTime.getTime());


         System.out.println(formattedDateTime);
         Date starDate = selectedDateTime.getTime();
         StringBuilder st = new StringBuilder();

         for (int i = 0; i < equipesNecessarias; i++) {
               Equipe eq = getJornadaEquipe(listHorariosTrabalhos, listHorariosFolgas, starDate);
             st.append("\nEquipe: ").append(numberToLetter(i + 1));
             st.append("\nHorários de trabalho: ");
             System.out.println("Equipe: " +numberToLetter(i + 1));
             System.out.println("  Horários de trabalho: ");
             for (Horario horario : eq.horariosTrabalho) {
                 System.out.println("    " + horario);
                 st.append("\n");
                 st.append(horario);
             }
          /*   st.append("\nHorários de folga: ");
             System.out.println("  Horários de folga:");
             for (Horario horario : eq.horariosFolga) {
                 System.out.println("    " + horario);
                 st.append("\n");
                 st.append(horario);
             }*/
               starDate = eq.horariosTrabalho.get(0).horaFim;

         }

        result.setText(st.toString());

    }

    private String numberToLetter(int number) {
        if (number >= 1 && number <= 26) {
            return String.valueOf((char) ('A' + number - 1));
        } else {
            return "Número inválido";
        }
    }

}