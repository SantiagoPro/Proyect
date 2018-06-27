/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author Santiago
 */
@Named(value = "fileUpload")
@ViewScoped
public class FileUpload implements Serializable {

    private List<Part> filesParts;
    private Part filePart;

    /**
     * Creates a new instance of FileUpload
     */
    public FileUpload() {
    }

    private List<Part> getParts(){
        try {
            if (filePart != null) {
                HttpServletRequest rq = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                return rq.getParts().stream().filter(p -> filePart.getName().equals(p.getName())).collect(Collectors.toList());
            } else {
                return new ArrayList<>();
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Part getFilePart() {
        return filePart;
    }

    public void setFilePart(Part filePart) {
        this.filePart = filePart;
    }

    public List<Part> getFilesParts() {
        if (filesParts == null) {
            filesParts = getParts();
        }
        return filesParts;
    }
    
    public String accion() throws Exception {
        if (!getFilesParts().isEmpty()) {
            System.out.println("Se esta subiendo el archivo");
            try {
                for (Part part : filesParts) {
                    saveFileTemp(part);
                }
                filesParts = null;
                //sendMessageInfo(null, "Debe agregar por lo menos un archivo.", "");
            } catch (Exception e) {
                e.printStackTrace();
                for (Part part : filesParts) {
                    deleteFileTemp(part);
                }
                sendMessageInfo(null, e.getMessage(), "");
            }
            return "/usuarios/Principal.administrador.xhtml";
        } else {
            sendMessageInfo(null, "Debe agregar por lo menos un archivo.", "");
        }
        return "";
    }

    private void saveFileTemp(Part part) throws Exception {;
        File folder = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/resources/images/perfiles/");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try (InputStream is = part.getInputStream()) {
            Files.copy(is, (new File(folder, part.getSubmittedFileName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
            sendMessageInfo(null, "Se cargó correctamente el archivo " + part.getSubmittedFileName(), "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al guardar el archivo " + part.getSubmittedFileName(), e);
        }
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
