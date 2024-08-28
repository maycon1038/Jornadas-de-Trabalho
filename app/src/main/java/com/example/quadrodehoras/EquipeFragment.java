package com.example.quadrodehoras;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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


public class EquipeFragment extends Fragment {

    ArrayList<Jornada> listJornadas;
    int equipesNecessarias = 0;
    private FragmentFirstBinding binding;
    private Calendar selectedDateTime;
    private int TotalHoursInDay;
    ArrayList<Equipe> listEquipes = new ArrayList<>();
    private RecyclerView recyclerView;
    private EquipeAdapter equipeAdapter;


    ArrayList<Jornadas> listJnds;


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
        listJnds = new ArrayList<>();
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
                NavHostFragment.findNavController(EquipeFragment.this)
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

            Date startDate = selectedDateTime.getTime();
            List<Jornada> jornadasSelecionadas = adapter.getJornadasSelected();
            int horasTrabalho =jornadasSelecionadas.stream().mapToInt(Jornada::getHrTrabalho).sum(); // 12 horas + 12 horas
            int horasFolga =jornadasSelecionadas.stream().mapToInt(Jornada::getHrFolgas).sum(); // 12 horas + 12 horas
            TotalHoursInDay = horasTrabalho / jornadasSelecionadas.size();
            int totalTurnosNoDia = 24 / TotalHoursInDay;
            String idDocJonada = concatenarIdsJornadas(jornadasSelecionadas);
            System.out.println("idsjns: " + idDocJonada);

             int qtdEquipe = contarJornadasComMesmoId(listJnds,  idDocJonada) + 1;
            equipesNecessarias = calcularEquipesParaFolga(horasTrabalho, horasFolga, listJornadas.size());
            if(qtdEquipe >= equipesNecessarias) {

                Toast.makeText(requireContext(), "Você já possui a quantidade máxima de equipes", Toast.LENGTH_SHORT).show();
                return;
            }

            Equipe equipe = new Equipe(  numberToLetter(qtdEquipe) + " ("+ idDocJonada +")", startDate);

            if(!listEquipes.isEmpty() && existeConflitoHorario(listEquipes, startDate, horasTrabalho + horasFolga)){
                Toast.makeText(requireContext(), "Existe um conflito de horário.", Toast.LENGTH_SHORT).show();
                return;
            }


            for (Jornada jornadaAtual : adapter.getJornadasSelected()) {
                Date dataFimTrabalho = addTimeToDate(startDate, jornadaAtual.getHrTrabalho(), 0);
                String nomeTurno = totalTurnosNoDia == 1 ? "Turno Único (24h)" : jornadaAtual.turno + "º Turno";
                equipe.horariosTrabalho.add(new Horario(nomeTurno, jornadaAtual.turno, startDate, dataFimTrabalho));
                Date dataFimFolga = addTimeToDate(dataFimTrabalho, jornadaAtual.getHrFolgas(), 0);
                equipe.horariosFolga.add(new Horario(nomeTurno, jornadaAtual.turno, dataFimTrabalho, dataFimFolga));
                //System.out.println("dataInicial: " + dateFormat.format(startDate)); // Pode ser removido em produção
                 startDate = dataFimFolga;
            }
            Jornadas jaExiste = equipeJaAdicionadaInJornadaMesmaData(listJnds, startDate, idDocJonada);
            if(jaExiste != null){
                Toast.makeText(requireContext(), "Já existe uma equipe adicionada configuração de jornadas.", Toast.LENGTH_SHORT).show();
                return;
            }

          //  System.out.println("existeConflitoHorario: " + existeConflitoHorario(equipe));
            Jornadas Jns = new Jornadas(idDocJonada, equipe.equipe, startDate, (ArrayList<Jornada>) jornadasSelecionadas);
            listJnds.add(Jns);
            listEquipes.add(equipe);
            equipeAdapter.notifyItemInserted(listEquipes.size() - 1); // Notifica o adapter da mudança
            dialog.dismiss();
        });
    }

    public boolean existeConflitoHorario(ArrayList<Equipe> equipes, Date dataStart2, int qtdHorasTrabalhoAndFolga) {

        Date dataStart = equipes.get(0).horariosTrabalho.get(0).horaInicio;
         do{
             for (Equipe equipe: equipes) {
                  dataStart = equipe.horariosTrabalho.get(0).horaInicio;
                 dataStart = addTimeToDate(dataStart,qtdHorasTrabalhoAndFolga,0 );
                 String myFormat = "dd/MM/yyyy HH:mm"; //In which you need put here
                 SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));
                 System.out.println("dataStart1: " + sdf.format(dataStart) + "dataStart2: " + sdf.format(dataStart2));
                 if(dataStart.equals(dataStart2)){
                     return true;
                 }
             }


         }while (dataStart2.after(dataStart));
        return false; // Nenhum conflito encontrado
    }

    public String concatenarIdsJornadas(List<Jornada> jornadasSelecionadas) {
        StringBuilder idsConcatenados = new StringBuilder();
        for (Jornada jornada : jornadasSelecionadas) {
            System.out.println("jornada: " + jornada.getIdDoc());
            idsConcatenados.append(jornada.getHrTrabalho());
            idsConcatenados.append("/");
            idsConcatenados.append(jornada.getHrFolgas());
            idsConcatenados.append(" - ");
        }
        String ultimosTresChars = idsConcatenados.substring(idsConcatenados.length() - 3);
        // Remove o último " / " se houver
        if (ultimosTresChars.equals(" - ")) {
            idsConcatenados.delete(idsConcatenados.length() - 3, idsConcatenados.length());
        }
        return idsConcatenados.toString();
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

    private Jornadas equipeJaAdicionadaInJornadaMesmaData(List<Jornadas> listJornadas, Date data, String idDocJornada) {
        // Equipe já adicionada
        if(listJornadas == null) return null;
        return listJornadas.stream().filter(jornadas -> jornadas.getDataInicio().equals(data) && jornadas.getName().equals(idDocJornada) ).findFirst().orElse(null);
        // Equipe não encontrada
    }



    private int contarJornadasComMesmoId(List<Jornadas> listJornadas, String idDocJornada) {
        int count = 0;
        for (Jornadas jornada : listJornadas) {
            if (jornada.getName().equals(idDocJornada)) {
                count++;
            }
        }
        return count;
    }



    private String numberToLetter(int number) {
        if (number >= 1 && number <= 26) {
            return String.valueOf((char) ('A' + number - 1));
        } else {
            return "Número inválido";
        }
    }
}
