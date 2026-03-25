package org.rplbo.app.Data;

public class RekamMedis {
    private int id;
    private String namaPasien;
    private String namaDokter;
    private String diagnosis;
    private String tanggal;

    public RekamMedis() {}

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNamaPasien() { return namaPasien; }
    public void setNamaPasien(String namaPasien) { this.namaPasien = namaPasien; }
    public String getNamaDokter() { return namaDokter; }
    public void setNamaDokter(String namaDokter) { this.namaDokter = namaDokter; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }
}