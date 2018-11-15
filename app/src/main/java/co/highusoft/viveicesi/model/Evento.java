package co.highusoft.viveicesi.model;

public class Evento {
    private String nombre;
    private String area;
    private String descripcion;

    public int mYear;
    public int mMonth;
    public int mDay;

    public int hour;
    public int min;

    public Evento(String nombre, String area, String descripcion, int mYear, int mMonth, int mDay, int hour, int min) {

        this.nombre = nombre;
        this.area = area;
        this.descripcion = descripcion;
        this.mYear = mYear;
        this.mMonth = mMonth;
        this.mDay = mDay;
        this.hour = hour;
        this.min = min;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getmYear() {

        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public int getmMonth() {
        return mMonth;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public int getmDay() {
        return mDay;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
