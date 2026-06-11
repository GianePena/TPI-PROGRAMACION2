package entities;

import utilidades.Utilidades;

import java.time.LocalDateTime;
import java.util.UUID;

public class Base {
    protected Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;


    // Constructor para objetos nuevos
    public Base() {
        this.setEliminado(false);
        this.setCreatedAt();
    }

    // Constructor para objetos recuperados de la DB
    public Base(Long id, boolean eliminado, LocalDateTime createdAt) {
        this.setId(id);
        this.setEliminado(eliminado);
        this.createdAt = createdAt;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setCreatedAt(){
        this.createdAt = Utilidades.generarFecha();
    }
    public void setUpdatedAt() {
        this.updatedAt = Utilidades.generarFecha();
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public void setDeletedAt() {
        this.deletedAt = Utilidades.generarFecha();;
    }

    @Override
    public String toString() {
        return
                "| ID: " + id ;
    }

}