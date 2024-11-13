package com.tarea.crud.Service;

import com.tarea.crud.model.Paciente;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {
    private final List<Paciente> pacientes = new ArrayList<>();
    private Long nextID = 1L;

    public List<Paciente> get_Pacientes() {
        return pacientes;
    }

    public Optional<Paciente> getPacienteByID(Long ID) {
        return pacientes.stream().filter(paciente -> paciente.getID().equals(ID)).findFirst();
    }
    public Optional<Paciente> getPacienteByName(String nombre) {
        return pacientes.stream().filter(paciente -> paciente.getNombre().equalsIgnoreCase(nombre)).findFirst();
    }
    public Paciente createPaciente(Paciente paciente) {
        paciente.setID(nextID++);
        pacientes.add(paciente);
        return paciente;
    }

    public Optional<Paciente> updatePaciente(Long ID, Paciente pacienteD) {
        return getPacienteByID(ID).map(paciente ->{
           paciente.setNombre(pacienteD.getNombre());
           paciente.setEdad(pacienteD.getEdad());
           paciente.setDiagnostico(pacienteD.getDiagnostico());
           paciente.setTelefono(pacienteD.getTelefono());
           paciente.setEmail(pacienteD.getEmail());
           return paciente;
        });
    }
    public boolean deletePaciente(Long ID){
        return pacientes.removeIf(paciente -> paciente.getID().equals(ID));
    }
    public List<Paciente> getPacientesByInitialLetter(String letra) {
        return pacientes.stream().filter(nombre -> nombre.getNombre().toLowerCase().startsWith(letra.toLowerCase())).collect(Collectors.toList());
    }
}
