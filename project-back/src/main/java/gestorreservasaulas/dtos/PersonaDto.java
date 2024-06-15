/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestorreservasaulas.dtos;

import lombok.Data;

@Data
public class PersonaDto {
    private Long id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;
    private String tipo;
}
