package com.example.quadrodehoras;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quadrodehoras.databinding.FragmentJornadasBinding;

import java.util.ArrayList;
import java.util.List;


public class JornadasFragment extends Fragment {

    private FragmentJornadasBinding binding;
    ArrayList<Jornada> listJornadas;

    ArrayList<Jornadas> listJnds;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentJornadasBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listJnds = new ArrayList<>();

        listJornadas = getArguments().getParcelableArrayList("Jornada");
        RecyclerView recyclerView = binding.recyclerJornadas;

        jornadaAdapter = new JornadasAdapter(listJnds); // Inicializa o adapter fora do listener
        recyclerView.setAdapter(jornadaAdapter); // Seta o adapter no RecyclerView


        binding.buttonCadastrarJornada.setOnClickListener(v -> {
            // Validação dos campos de entrada (opcional, mas recomendado)
            btnAddEquipe();

        });

        binding.buttonCadastrarEquipe.setOnClickListener(v -> {
            // Validação dos campos de entrada (opcional, mas recomendado)
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("Jornadas", listJnds);
            NavHostFragment.findNavController(JornadasFragment.this)
                    .navigate(R.id.action_jornadasFragment_to_FirstFragment, bundle);

        });
    }

    JornadasAdapter jornadaAdapter; // Declara o adapter como membro da classe

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void btnAddEquipe() {
//        Toast.makeText(getApplicationContext(), "Botão de Ajuda", Toast.LENGTH_SHORT).show();
        Dialog dialog = new Dialog(requireContext(), android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog.setContentView(R.layout.jornada_dialog);
        dialog.setCancelable(true);
        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_jornada);

        SelectJornadaAdapter adapter = new SelectJornadaAdapter(listJornadas);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2); // 'this' se refere ao contexto
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        dialog.show();

        dialog.findViewById(R.id.button_add_Jornada).setOnClickListener(v13 -> {
            List<Jornada> jornadasSelecionadas = adapter.getJornadasSelected();
            String idDocJonada = concatenarIdsJornadas(jornadasSelecionadas);
            Jornadas jaExiste = equipeJaAdicionadaInJornadaMesmaData(listJnds,   idDocJonada);
            if(jaExiste != null){
                Toast.makeText(requireContext(), "Já existe uma equipe adicionada configuração de jornadas.", Toast.LENGTH_SHORT).show();
                return;
            }
            //  System.out.println("existeConflitoHorario: " + existeConflitoHorario(equipe));
            Jornadas Jns = new Jornadas(idDocJonada,   (ArrayList<Jornada>) jornadasSelecionadas);
            listJnds.add(Jns);
            jornadaAdapter.notifyItemInserted(listJnds.size() - 1); // Notifica o adapter da mudança
            dialog.dismiss();
        });
    }

    private Jornadas equipeJaAdicionadaInJornadaMesmaData(List<Jornadas> listJornadas,   String idDocJornada) {
        // Equipe já adicionada
        if(listJornadas == null) return null;
        return listJornadas.stream().filter(jornadas ->   jornadas.getName().equals(idDocJornada) ).findFirst().orElse(null);
        // Equipe não encontrada
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

}