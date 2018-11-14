package co.highusoft.viveicesi.model;

public class Usuario {
    private String uid;
    private String usuario;
    private String correo;
    private String nombre;
    private String area;
    private String tipoUsuario;


    public Usuario() {
    }

    public Usuario(String uid, String usuario, String correo, String nombre, String area, String tipoUsuario) {
        this.uid = uid;
        this.usuario = usuario;
        this.correo = correo;
        this.nombre = nombre;
        this.area = area;
        this.tipoUsuario = tipoUsuario;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String gettipoUsuario() {
        return tipoUsuario;
    }

    public void settipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
