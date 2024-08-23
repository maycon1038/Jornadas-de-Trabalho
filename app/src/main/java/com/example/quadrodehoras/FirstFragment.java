package com.example.quadrodehoras;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quadrodehoras.databinding.FragmentFirstBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class FirstFragment extends Fragment {

    ArrayList<Jornada> listJornadas;
    int equipesNecessarias = 0;
    ArrayList<Datas> Datas = new ArrayList<>();
    private FragmentFirstBinding binding;
    private Calendar selectedDateTime;
    private int TotalHoursInDay;
    private TextView result;
    private String myJornada;
    int indiceEquipe = 1;
    ArrayList<Equipe> listEquipes = new ArrayList<>();
    private RecyclerView recyclerView;
    private EquipeAdapter equipeAdapter;


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

       // result = binding.txtResult;
        assert getArguments() != null;
        listJornadas = getArguments().getParcelableArrayList("Jornada");
        System.out.println("listJornadas: " + listJornadas);
         equipeAdapter = new EquipeAdapter(listEquipes);
         recyclerView = binding.itemListPp;
        binding.button1.setOnClickListener(v ->
                {
                    recyclerView.setAdapter(equipeAdapter);
                    btnAddEquipe();
                }
        );



        selectedDateTime = Calendar.getInstance();

        binding.buttonCadastrarJornada.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );

    }

    public void btnAddEquipe() {
//        Toast.makeText(getApplicationContext(), "Botão de Ajuda", Toast.LENGTH_SHORT).show();
        Dialog dialog = new Dialog(requireContext(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.setContentView(R.layout.equipe_dialog);
        dialog.setCancelable(true);
        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_ajuda);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());
        recyclerView.setLayoutManager(layoutManager);
        SelectJornadaAdapter adapter = new SelectJornadaAdapter(listJornadas);
        recyclerView.setAdapter(adapter);
        dialog.show();
        dialog.findViewById(R.id.button_selec_data).setOnClickListener(v1 -> showDateTimePicker());
        dialog.findViewById(R.id.button_add_Equipe).setOnClickListener(v13 -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            Date startDate = selectedDateTime.getTime();
            String formattedDateTime = dateFormat.format(startDate);
            System.out.println(formattedDateTime);
            System.out.println("formattedDateTime: " +  formattedDateTime);
            System.out.println("adapter.getJornadasSelected(): " +  adapter.getJornadasSelected().size());

              Equipe equipe = new Equipe(indiceEquipe,  numberToLetter(equipeAdapter.getItemCount() + 1), startDate);
             int indiceJornada = 0;
             int qtdJornadas = (adapter.getJornadasSelected().size() + 1);

            for (Jornada jornadaAtual : adapter.getJornadasSelected()) {
                indiceJornada = (indiceJornada + 1) % qtdJornadas; // Incrementa e mantém dentro do limite
                Date dataFimTrabalho = addTimeToDate(startDate, jornadaAtual.getHrTrabalho(), 0);
                String nomeTurno = qtdJornadas == 1 ? "Único" : indiceJornada + "º Turno";
                equipe.horariosTrabalho.add(new Horario(nomeTurno, indiceJornada, startDate, dataFimTrabalho));
                Date dataFimFolga = addTimeToDate(dataFimTrabalho, jornadaAtual.getHrFolgas(), 0);
                equipe.horariosFolga.add(new Horario(nomeTurno, indiceJornada, dataFimTrabalho, dataFimFolga));
                System.out.println("dataInicial: " + dateFormat.format(startDate)); // Pode ser removido em produção
                startDate = dataFimFolga;
            }
            listEquipes.add(equipe);
            equipeAdapter.notifyItemInserted(listEquipes.size() - 1); // Notifica o adapter da mudança
            dialog.dismiss();
        });
    }

    private void updateDateTimeTextView() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDateTime = dateFormat.format(selectedDateTime.getTime());
        System.out.println(formattedDateTime);

        Date startDate = selectedDateTime.getTime();


        StringBuilder resultText = new StringBuilder();
        int horasTrabalho = listJornadas.stream().mapToInt(Jornada::getHrTrabalho).sum(); // 12 horas + 12 horas
        int horasFolga = listJornadas.stream().mapToInt(Jornada::getHrFolgas).sum(); // 24 horas + 72 horas
        TotalHoursInDay = horasTrabalho / listJornadas.size();
        equipesNecessarias = calcularEquipesParaFolga(horasTrabalho, horasFolga, listJornadas.size());
        int diasNecessarios = (horasTrabalho + horasFolga) / 24;
        int totalTurnosNoDia = 24 / TotalHoursInDay;
        // binding.buttonVerificarPrevisao.setText(myJornada);

        int indiceEquipe = 1;
        ArrayList<Datas> listDatas = new ArrayList<>();

        for (int i = 0; i < diasNecessarios; i++) {

            for (int t = 1; t <= totalTurnosNoDia; t++) {


                if(listJornadas.size() == 1 && t == 1){
                    String eq = numberToLetter(indiceEquipe);
                    String nomeTurno = totalTurnosNoDia == 1 ? "Único" : t + "º Turno";
                    Datas data = new Datas(myJornada, t, eq);
                    data.setNameTurno(nomeTurno);
                    data.setDataTimeInicio(startDate);
                    data.setDataTimeFim(addTimeToDate(startDate, TotalHoursInDay, 0));
                    //prencher dados do 1 turno para cada equipe
                    if (!equipeJaAdicionada(listDatas, startDate)) {
                        listDatas.add(data);
                    }

                }else if(listJornadas.size() == totalTurnosNoDia){
                    String eq = numberToLetter(indiceEquipe);
                    String nomeTurno = totalTurnosNoDia == 1 ? "Único" : t + "º Turno";
                    Datas data = new Datas(myJornada, t, eq);
                    data.setNameTurno(nomeTurno);
                    data.setDataTimeInicio(startDate);
                    data.setDataTimeFim(addTimeToDate(startDate, TotalHoursInDay, 0));
                    if (!equipeJaAdicionada(listDatas, startDate)) {
                        listDatas.add(data);
                    }

                }


                startDate = addTimeToDate(startDate, TotalHoursInDay, 0);
                indiceEquipe = (indiceEquipe + 1) % (equipesNecessarias);

            }

        }
        for (int i = 0; i < diasNecessarios; i++) {
            Date data = addDayToDate(selectedDateTime.getTime(), i);
            resultText.append("\n").append(dateFormat.format(data));
            listDatas.forEach(datas ->
            {
                if (isSameDay(datas.getDataTimeInicio(), data)) {
                    resultText.append("\n").append("Equipe").append(": " ).append(datas.getEquipe()).append(" - " )
                            .append(datas.getNameTurno()).append(": " )
                            .append(datas.getDataTimeInicioFormat()).append(" até ").append(datas.getDataTimeFimFormat());
                }

            });

        }



        result.setText(resultText.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
                                   getDateTimeTextView();
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


    private void getDateTimeTextView() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDateTime = dateFormat.format(selectedDateTime.getTime());
        System.out.println(formattedDateTime);
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
