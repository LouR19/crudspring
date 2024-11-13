package com.tarea.crud.controller;

import com.tarea.crud.Service.PacienteService;
import com.tarea.crud.model.Paciente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {
    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    @GetMapping
    public List<Paciente> getAllPacientes(){
        return pacienteService.get_Pacientes();
    }
    @GetMapping("/{ID}")
    public ResponseEntity<Paciente> getPacienteByID(@PathVariable Long ID){
        Optional<Paciente> paciente = pacienteService.getPacienteByID(ID);
        return paciente.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
    @GetMapping("/name/{nombre}")
    public ResponseEntity<Paciente> getPacienteByName(@PathVariable String nombre) {
        Optional<Paciente> paciente = pacienteService.getPacienteByName(nombre);
        return paciente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public Paciente createPaciente(@RequestBody Paciente paciente){
        return pacienteService.createPaciente(paciente);
    }
    @PutMapping("/{ID}")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable Long ID, @RequestBody Paciente paciente){
        Optional<Paciente> optionalPaciente = pacienteService.updatePaciente(ID, paciente);
        return optionalPaciente.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{ID}")
    public ResponseEntity<String> deletePaciente(@PathVariable Long ID){
        if (pacienteService.deletePaciente(ID)){
            return ResponseEntity.ok("Eliminado Correctamente 😉");
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/reporte/{letra}")
    public ResponseEntity<List<Paciente>> buscarPacientesPorLetraInicial(@PathVariable String letra) {
        List<Paciente> pacientes = pacienteService.getPacientesByInitialLetter(letra);
        if (pacientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        try (FileWriter wreport = new FileWriter("Reporte_pacientes.txt", true)) {
            for (Paciente paciente : pacientes) {
                wreport.write("\nID: " + paciente.getID());
                wreport.write("\nNombre: " + paciente.getNombre());
                wreport.write("\nEdad: " + paciente.getEdad());
                wreport.write("\nDiagnóstico: " + paciente.getDiagnostico());
                wreport.write("\nTeléfono: " + paciente.getTelefono());
                wreport.write("\nEmail: " + paciente.getEmail());
                wreport.write("\n----------------------------");
            }
            System.out.println("\nReporte generado en reporte_pacientes.txt.\n");
        } catch (IOException e) {
            System.out.println("Error al generar el reporte: " + e.getMessage());
        }
        return ResponseEntity.ok(pacientes);
    }

}
