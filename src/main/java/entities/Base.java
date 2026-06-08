package entities;

import utilidades.Utilidades;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Base {
    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Base(Long id, LocalDateTime createdAt) {
        this.id=id;
        this.eliminado = false;
        this.createdAt = createdAt;
    }

    public Base() {
        this.eliminado = false;
        this.createdAt = Utilidades.generarFecha();
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

    public void setUpdatedAt() {
        this.updatedAt = Utilidades.generarFecha();;
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
                "- id=" + id +
                "- eliminado=" + eliminado +
                "- createdAt=" + createdAt +
                (this.updatedAt!=null?"- updatedAt=" + updatedAt :"" )+
                (this.deletedAt!=null?"- deletedAt=" + deletedAt :"" );

    }

    public Thread getCategoria() {
        return null;
    }
}
