package com.tarea.crud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paciente {
    private Long ID;
    private String nombre;
    private int edad;
    private String diagnostico;
    private String telefono;
    private String email;
}
