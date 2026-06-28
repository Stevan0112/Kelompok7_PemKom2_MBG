/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemkom.objects;

import java.time.LocalDate;

public class LogAbsensiView {

    private String namaLengkap;
    private String uidRfid;
    private String sekolah;
    private String jam;
    private String hari;
    private String status;
    private LocalDate tanggal;

    public LogAbsensiView(String namaLengkap, String uidRfid, String sekolah,
            String jam, String hari, String status, LocalDate tanggal) {
        this.namaLengkap = namaLengkap;
        this.uidRfid = uidRfid;
        this.sekolah = sekolah;
        this.jam = jam;
        this.hari = hari;
        this.status = status;
        this.tanggal = tanggal;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getUidRfid() {
        return uidRfid;
    }

    public String getSekolah() {
        return sekolah;
    }

    public String getJam() {
        return jam;
    }

    public String getHari() {
        return hari;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }
}
