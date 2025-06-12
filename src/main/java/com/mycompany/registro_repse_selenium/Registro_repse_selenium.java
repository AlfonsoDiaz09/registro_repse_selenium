/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.registro_repse_selenium;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v137.page.Page;
import org.openqa.selenium.devtools.v137.page.Page.PrintToPDFTransferMode;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

/**
 *
 * @author alfon
 */
public class Registro_repse_selenium {

    public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
        String razonSocialBuscar = "google";
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        ChromeDriver driver = new ChromeDriver(options);
        driver.get("https://repse.stps.gob.mx");

        Actions actions = new Actions(driver);
        Wait<ChromeDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(ElementNotInteractableException.class);

        // Esperar y hacer clic en el contenedor
        int intentosConsulta = 0;
        boolean consultaExitosa = false;
        while (intentosConsulta < 5 && !consultaExitosa) {
            try {
                WebElement contenedorConsulta = wait.until(ExpectedConditions.elementToBeClickable(By.id("act-consulta")));
                System.out.println("Esperando 5 segundos");
                Thread.sleep(5000);
                System.out.println("Continuamos...");
                actions.moveToElement(contenedorConsulta).click().perform();
                consultaExitosa = true;
                System.out.println("Click en #act-consulta exitoso");
            } catch (TimeoutException e) {
                System.out.println("Reintentando click en '#act-consulta'... intento #" + (intentosConsulta + 1));
                Thread.sleep(200);
                intentosConsulta++;
            }
        }

        if (!consultaExitosa) {
            System.out.println("No se pudo hacer click en '#act-consulta'. Deteniendo ejecución.");
            driver.quit();
            return;
        }
        
        // Esperar que aparezca el botón y hacer click en Consultar
        int intentosContinuar = 0;
        boolean continuarExitoso = false;
        while (intentosContinuar < 5 && !continuarExitoso) {
            try {
                WebElement botonContinuar = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-continue")));
                actions.moveToElement(botonContinuar).click().perform();
                continuarExitoso = true;
                System.out.println("Click en .btn-continue exitoso");
            } catch (TimeoutException e) {
                System.out.println("Reintentando click en '.btn-continue'... intento #" + (intentosContinuar + 1));
                Thread.sleep(200);
                intentosContinuar++;
            }
        }

        if (!continuarExitoso) {
            System.out.println("No se pudo hacer click en '.btn-continue'. Deteniendo ejecución.");
            driver.quit();
            return;
        }
        
        // Esperar y llenar input con Razón Social
        WebElement inputRazonSocial = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rsoc")));
        inputRazonSocial.sendKeys(razonSocialBuscar);
        
        // Esperar y hacer click en Buscar
        int intentosBuscar = 0;
        boolean buscarExitoso = false;
        while (intentosBuscar < 5 && !buscarExitoso) {
            try {
                WebElement btnBuscar = wait.until(ExpectedConditions.elementToBeClickable(By.id("bnt_busqueda")));
                actions.moveToElement(btnBuscar).click().perform();
                
                // Esperar y obtener las filas de las empresas encontradas
                List<WebElement> listaEmpresas = wait.until(
                    ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("#tablaem tbody tr"), 0)
                );
                if (!listaEmpresas.isEmpty()) {
                    // Esperar y hacer click en Seleccionar en la primera empresa de la lista
                    WebElement btnSeleccionar = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btnselectemp")));
                    actions.moveToElement(btnSeleccionar).click().perform();
                }
                buscarExitoso = true;
                System.out.println("Click en .btn_busqueda exitoso");
            } catch (TimeoutException e) {
                System.out.println("Reintentando click en '.btn_busqueda'... intento #" + (intentosBuscar + 1));
                Thread.sleep(200);
                intentosBuscar++;
            }
        }
        
        if (!buscarExitoso) {
            System.out.println("Error de validación captcha. Deteniendo ejecución.");
            driver.quit();
            return;
        }
        
        LocalDate fechaActual = LocalDate.now();
        System.out.println("Fecha actual: " + fechaActual);
        
        // Esperar y obtener la fecha de consulta
        WebElement fechaConsultaElemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[contains(text(), 'Fecha de consulta:')]")));
        String textoFecha = fechaConsultaElemento.getText();
        String fechaConsultaStr = textoFecha.replace("Fecha de consulta:", "").trim();
        LocalDate fechaConsulta = LocalDate.parse(fechaConsultaStr, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("Fecha consulta: " + fechaConsulta);
        
        // Esperar y obtener la vigencia del registro
        WebElement fechaVigenciaElemento = driver.findElement(
            By.xpath("//p[text()='Vigencia del Registro']/following-sibling::p[@class='highlightname']")
        );
        String fechaVigenciaStr = fechaVigenciaElemento.getText().trim();
        LocalDate fechaVigencia = LocalDate.parse(fechaVigenciaStr, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("Fecha vigencia de registro: " + fechaVigencia);

        // Comparar
        if (!fechaConsulta.equals(fechaActual)) {
            System.out.println("Fecha de consulta distinta. Obtenida: " + fechaConsulta + ", Esperada: " + fechaActual);
            driver.quit();
        }
        if (!fechaActual.isBefore(fechaVigencia)) {
            System.out.println("Registro vencido. Fecha de vigencia era: " + fechaVigencia);
            driver.quit();
        }
        
        // Folio de registro
        WebElement folioRegistroElemento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(text(), 'REGISTRO LOCALIZADO FOLIO:')]")));
        String textoFolio = folioRegistroElemento.getText();
        String folioRegistro = textoFolio.replace("REGISTRO LOCALIZADO FOLIO:", "").trim();
        System.out.println("Folio Registro: " + folioRegistro);
        
        // Nombre o Razón Social
        WebElement razonSocialElemento = driver.findElement(
            By.xpath("//p[text()='Nombre o Razón Social']/following-sibling::p[@class='highlightname']")
        );
        String razonSocial = razonSocialElemento.getText().trim();
        System.out.println("Nombre o Razón Social: " + razonSocial);
        
        // Entidad / Municipio
        WebElement entidadMunicipioElemento = driver.findElement(
            By.xpath("//p[text()='Entidad / Municipio']/following-sibling::p[@class='highlightname']")
        );
        String entidadMunicipio = entidadMunicipioElemento.getText().trim();
        System.out.println("Entidad / Municipio: " + entidadMunicipio);
        
        // Aviso de registro / Fecha de aviso de registro
        WebElement avisoRegistroElemento = driver.findElement(
            By.xpath("//p[text()='Aviso de registro N. / Fecha de aviso de registro']/following-sibling::p[@class='highlightname']")
        );
        String avisoRegistro = avisoRegistroElemento.getText().trim();
        System.out.println("Aviso de registro N. / Fecha de aviso de registro: " + avisoRegistro);
        
        // Servicios brindados
        List<WebElement> serviciosBrindadosElemento = driver.findElements(
            By.xpath("//div[contains(@class, 'whitebox')][contains(., 'Ofreciendo los siguientes servicios')]//ul")
        );
        List<String> listaServicios = new ArrayList<>();
        for (WebElement ul : serviciosBrindadosElemento) {
            String textoServiciosBrindados = ul.getText();
            String serviciosBrindados = textoServiciosBrindados.replace("- ", "").trim();
            if (!serviciosBrindados.isEmpty()) {
                listaServicios.add(serviciosBrindados);
            }
        }
        
        // Creando RegistroRepseDTO
        RegistroRepseDto dto = new RegistroRepseDto(
            razonSocial,
            entidadMunicipio,
            avisoRegistro,
            folioRegistro,
            fechaConsulta,
            fechaVigencia,
            listaServicios
        );

        System.out.println(dto);
        
        System.out.println("Guardando informacion...");
        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        Page.PrintToPDFResponse pdf = devTools.send(
            Page.printToPDF(
                Optional.empty(), // landscape
                Optional.empty(), // displayHeaderFooter
                Optional.empty(), // printBackground
                Optional.empty(), // scale
                Optional.empty(), // paperWidth
                Optional.empty(), // paperHeight
                Optional.empty(), // marginTop
                Optional.empty(), // marginBottom
                Optional.empty(), // marginLeft
                Optional.empty(), // marginRight
                Optional.empty(), // pageRanges
                Optional.empty(), // headerTemplate
                Optional.empty(), // footerTemplate
                Optional.empty(), // preferCSSPageSize
                Optional.of(PrintToPDFTransferMode.RETURNASBASE64), // transferMode
                Optional.empty(), // page orientation
                Optional.empty()  // generate tagged PDF
            )
        );
        byte[] pdfData = Base64.getDecoder().decode(pdf.getData());

        // Guardar el archivo
        String fileNameSave = razonSocialBuscar + "_" + fechaConsulta + ".pdf";
        try (FileOutputStream fos = new FileOutputStream(fileNameSave)) {
            fos.write(pdfData);
            System.out.println("PDF guardado como " + fileNameSave);
        }
        
        driver.quit();
    }
}
