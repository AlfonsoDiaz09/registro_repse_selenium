/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.registro_repse_selenium;

import java.time.LocalDate;

/**
 *
 * @author alfon
 */
public class RegistroRepseDto {
    private String razonSocial;
    private String entidadMunicipio;
    private String avisoRegistro;
    private String serviciosBrindados;
    private String folioRegistro;
    private LocalDate fechaConsulta;
    private LocalDate fechaVigencia;

    public RegistroRepseDto(String razonSocial, String entidadMunicipio, String avisoRegistro,
                            String serviciosBrindados, String folioRegistro,
                            LocalDate fechaConsulta, LocalDate fechaVigencia) {
        this.razonSocial = razonSocial;
        this.entidadMunicipio = entidadMunicipio;
        this.avisoRegistro = avisoRegistro;
        this.serviciosBrindados = serviciosBrindados;
        this.folioRegistro = folioRegistro;
        this.fechaConsulta = fechaConsulta;
        this.fechaVigencia = fechaVigencia;
    }

    @Override
    public String toString() {
        return "RegistroRepseDto {" +
            "\n  Nombre o Razón Social: " + razonSocial +
            "\n  Entidad/Municipio: " + entidadMunicipio +
            "\n  Aviso de Registro: " + avisoRegistro +
            "\n  Servicios Brindados: \n    - " + serviciosBrindados.replace("\n", "\n    - ") +
            "\n  Folio de Registro: " + folioRegistro +
            "\n  Fecha de Consulta: " + fechaConsulta +
            "\n  Vigencia del registro: " + fechaVigencia +
            "\n}";
    }
}
