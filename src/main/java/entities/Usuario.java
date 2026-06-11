package entities;

import enums.Rol;

import java.time.LocalDateTime;

public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;


    public Usuario(String nombre, String apellido, String mail, String celular,String contrasenia, Rol rol) {
        super();
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setMail(mail);
        this.setCelular(celular);
        this.setContrasenia(contrasenia);
        this.setRol(rol);
    }


    public Usuario(Long id, boolean eliminado, LocalDateTime createdAt, String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        super(id, eliminado, createdAt);
        this.id=id;
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setMail(mail);
        this.setCelular(celular);
        this.setContrasenia(contrasenia);
        this.setRol(rol);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {return apellido;}
    public void setApellido(String apellido) {this.apellido = apellido;}

    public String getMail() {return mail;}
    public void setMail(String mail) {this.mail = mail;}

    public String getCelular() {return celular;}
    public void setCelular(String cedular) {this.celular = cedular;}

    public String getContrasenia() {return contrasenia;}
    public  void  setContrasenia(String contrasenia) {this.contrasenia = contrasenia;}

    public Rol getRol() {return rol;}
    public void setRol(Rol rol) {this.rol = rol;}

    public Long getId(){
        return  super.getId();
    }
    public void setId(Long id){

    }
    public  java.time.LocalDateTime getCreatedAt(){
        return super.getCreatedAt();
    }
    public void setCreatedAt(java.time.LocalDateTime createdAt){

    }


    @Override
    public String toString() {
        return "Usuario {" +
                "id=" + getId() +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", mail='" + mail + '\'' +
                ", celular='" + celular + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                ", eliminado=" + isEliminado() +
                ", CreatedAt=" + getCreatedAt() +
                '}';

        }
        public String toStringLista() {
        return String.format("ID %d - %s %s - Mail: %s - Celular: %s - Contasenia: %s - Rol: %s",
                getId(), nombre , apellido, mail, celular, contrasenia, rol);

        }

    }


