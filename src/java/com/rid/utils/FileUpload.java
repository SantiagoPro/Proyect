/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.utils;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Part;

/**
 *
 * @author Santiago
 */
@Named(value = "fileUpload")
@ViewScoped
public class FileUpload implements Serializable {

    private Part archivo;
    private String ruta = "";
    private File carpeta;

    /**
     * Creates a new instance of FileUpload
     */
    public FileUpload() {
    }

    @PostConstruct
    public void init() {
        carpeta = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "resources/images/perfiles");
    }

    public Part getArchivo() {
        return archivo;
    }

    public void setArchivo(Part archivo) {
        this.archivo = archivo;
    }

    public File getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(File carpeta) {
        this.carpeta = carpeta;
    }

    public String cargarArchivo(Integer nombreArchivo) {
        if (archivo != null) {
            File carp = carpeta;
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }
            try (InputStream is = archivo.getInputStream()) {
                File f = new File(carpeta, archivo.getSubmittedFileName());
                File f2 = new File(carpeta, nombreArchivo + "." + archivo.getContentType().substring(6,9));
                Files.copy(is, f2.toPath(),StandardCopyOption.REPLACE_EXISTING);
                ruta = carpeta.getAbsolutePath() + "/" + f2.getName();
                return f2.getName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    private void deleteFileTemp(Part part) throws Exception {
        System.out.println("********************************************");
        System.out.println(part.getContentType());
        System.out.println(part.getName());
        System.out.println(part.getSubmittedFileName());
        System.out.println(part.getSize());
        File file = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/files/temps/" + part.getSubmittedFileName());
        if (file.exists()) {
            file.deleteOnExit();
        }
    }

    public void sendMessageInfo(String clientId, String message, String detail) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_INFO, message, detail));
    }

}
