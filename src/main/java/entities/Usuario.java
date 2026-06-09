package entities;

import enums.Rol;

public class Usuario extends Base {
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;

    public Usuario(String trim, String trimmed, String s, String string, String trim1, Rol rol) {
        super();
    }

    public Usuario(String nombre, String apellido, String mail, String celular, Rol rol) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = this.celular;
        this.contrasenia = contrasenia;
        this.rol = rol;

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


