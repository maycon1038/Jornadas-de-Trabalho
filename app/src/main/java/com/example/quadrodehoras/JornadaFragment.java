package com.example.quadrodehoras;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.quadrodehoras.databinding.FragmentSecondBinding;

import java.util.ArrayList;
import java.util.List;


public class JornadaFragment extends Fragment {

    private FragmentSecondBinding binding;
    private Jornada novaJornada;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Jornada> jornada  = new ArrayList<>();


        novaJornada = new Jornada(numberToLetter(1), 12, 24);
        jornada.add(novaJornada);

        novaJornada = new Jornada(numberToLetter(2), 12, 48);
        jornada.add(novaJornada);

        novaJornada = new Jornada(numberToLetter(3), 12, 72);
        jornada.add(novaJornada);

        novaJornada = new Jornada(numberToLetter(4), 8, 16);
        jornada.add(novaJornada);

        novaJornada = new Jornada(numberToLetter(5), 6, 18);
        jornada.add(novaJornada);


        novaJornada = new Jornada(numberToLetter(6), 24, 72);
        jornada.add(novaJornada);
        novaJornada = new Jornada(numberToLetter(7), 12, 72);
        jornada.add(novaJornada);



        jornadaAdapter = new JornadaAdapter(jornada); // Inicializa o adapter fora do listener
        binding.listJornadas.setAdapter(jornadaAdapter); // Seta o adapter no RecyclerView

        binding.buttonCadastrarJornada.setOnClickListener(v -> {
            // Validação dos campos de entrada (opcional, mas recomendado)
            String hrTrabalhoStr = binding.edtHrT1.getText().toString();
            String hrFolgaStr = binding.edtHrF1.getText().toString();
            if (hrTrabalhoStr.isEmpty() || hrFolgaStr.isEmpty()) {
                // Exibe uma mensagem de erro ou toma outra ação apropriada
                return;
            }
            int horasTrabalho = Integer.parseInt(hrTrabalhoStr);
            int horasFolga = Integer.parseInt(hrFolgaStr);
            if (jornadaExiste(jornada, horasTrabalho, horasFolga)) {
                // Exibe uma mensagem informando que a jornada já existe
                Toast.makeText(getContext(), "Esta jornada já existe!", Toast.LENGTH_SHORT).show();

                return;
            }
            novaJornada = new Jornada(numberToLetter(jornada.size() + 1), horasTrabalho, horasFolga);
            jornada.add(novaJornada);
            jornadaAdapter.notifyItemInserted(jornada.size() - 1); // Notifica o adapter da mudança
            System.out.println("jjornadaAdapter: " + jornadaAdapter.getItemCount());
        });

        binding.buttonCadastrarEquipe.setOnClickListener(v -> {
            // Validação dos campos de entrada (opcional, mas recomendado)
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("Jornada", jornada);
            NavHostFragment.findNavController(JornadaFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment, bundle);

        });
    }

      JornadaAdapter jornadaAdapter; // Declara o adapter como membro da classe
    private String numberToLetter(int number) {
        if (number >= 1 && number <= 26) {
            return String.valueOf((char) ('A' + number - 1));
        } else {
            return "Número inválido";
        }
    }

    public boolean jornadaExiste(List<Jornada> jornadas, int hrTrabalho, int hrFolgas) {
        for (Jornada jornada : jornadas) {
            if (jornada.getHrTrabalho() == hrTrabalho && jornada.getHrFolgas() == hrFolgas) {
                return true; // Jornada já existe
            }
        }
        return false; // Jornada não existe
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}