/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rid.utils;

import com.sun.xml.bind.StringInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

/**
 *
 * @author Santiago
 */
@Named(value = "fileUpload")
@ViewScoped
public class FileUpload implements Serializable{
    
    private Part archivo;
    private String ruta;
    private File carpeta;

    public FileUpload() {
    }
    
    @PostConstruct
    public void init(){
        carpeta = new File (FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "imgPerfil");
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
    
    public String cargarArchivo(Long nombreArchivo){
        
        if (archivo != null) {
            File folder = carpeta;
            if (!folder.exists()) {
                carpeta.mkdir();
            }
            try (InputStream is = archivo.getInputStream()){
                File f = new File(carpeta,archivo.getSubmittedFileName());
                File f2 = new File(carpeta,nombreArchivo + "." + archivo.getContentType().substring(6, 9));
                f.renameTo(f2);
                Files.copy(is, f2.toPath(), StandardCopyOption.REPLACE_EXISTING);
                ruta = carpeta.getAbsolutePath() + "/" + f2.getName();
                return f2.getName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }
    

//    public static String savePicTemp(String foto, String nombreFoto, String nombre, String apellido) throws FileNotFoundException, IOException {
//        nombreFoto = nombre + apellido;
//        String ubicacion = null;
//        ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//        String path = sc.getRealPath("") + File.separatorChar
//                + "resources" + File.separatorChar
//                + "images" + File.separatorChar + "perfiles" + nombreFoto;
//        File f = null;
//        InputStream is = null;
//
//        try {
//            f = new File(path);
//            is = new StringInputStream(foto);
//            FileOutputStream out = new FileOutputStream(f.getAbsolutePath());
//
//            int c = 0;
//            while ((c = is.read()) >= 0) {
//                out.write(c);
//            }
//            out.flush();
//            out.close();
//            ubicacion = "\\build\\web\\resources\\images\\perfiles" + nombreFoto;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ubicacion;
//    }
}
