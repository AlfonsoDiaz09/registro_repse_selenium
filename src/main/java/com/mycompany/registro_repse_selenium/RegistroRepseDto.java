/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registro_repse_selenium;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author alfon
 */
public class RegistroRepseDto {
    private String razonSocial;
    private String entidadMunicipio;
    private String avisoRegistro;
    private String folioRegistro;
    private LocalDate fechaConsulta;
    private LocalDate fechaVigencia;
    private List<String> serviciosBrindados;

    public RegistroRepseDto(String razonSocial, String entidadMunicipio, String avisoRegistro,
                            String folioRegistro, LocalDate fechaConsulta, LocalDate fechaVigencia,
                            List<String> serviciosBrindados) {
        this.razonSocial = razonSocial;
        this.entidadMunicipio = entidadMunicipio;
        this.avisoRegistro = avisoRegistro;
        this.folioRegistro = folioRegistro;
        this.fechaConsulta = fechaConsulta;
        this.fechaVigencia = fechaVigencia;
        this.serviciosBrindados = serviciosBrindados;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("####### Registro Repse DTO #######\n");
        sb.append("Fecha de Consulta: " + fechaConsulta + "\n");
        sb.append("Folio de Registro: " + folioRegistro + "\n");
        sb.append("Nombre o Razón Social: " + razonSocial + "\n");
        sb.append("Entidad/Municipio: " + entidadMunicipio + "\n");
        sb.append("Aviso de Registro: " + avisoRegistro + "\n");
        sb.append("Vigencia del registro: " + fechaVigencia + "\n");
        sb.append("Servicios brindados: \n");
        for (String servicio : serviciosBrindados) {
            sb.append("   - " + servicio + "\n");
        }
        return sb.toString();
    }
}
