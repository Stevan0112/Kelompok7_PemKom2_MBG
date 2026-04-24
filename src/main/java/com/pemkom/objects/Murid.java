/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemkom.objects;

public class Murid {

    private String uidRfid;
    private String idMurid;
    private String namaLengkap;
    private String sekolah;

    public Murid() {
    }

    public Murid(String uidRfid, String idMurid, String namaLengkap, String sekolah) {
        this.uidRfid = uidRfid;
        this.idMurid = idMurid;
        this.namaLengkap = namaLengkap;
        this.sekolah = sekolah;
    }

    @Override
    public String toString() {
        return "Murid{" +
                "uidRfid=" + uidRfid +
                ", idMurid=" + idMurid +
                ", namaLengkap=" + namaLengkap +
                ", sekolah=" + sekolah +
                '}';
    }

    public String getUidRfid() {
        return uidRfid;
    }

    public void setUidRfid(String uidRfid) {
        this.uidRfid = uidRfid;
    }

    public String getIdMurid() {
        return idMurid;
    }

    public void setIdMurid(String idMurid) {
        this.idMurid = idMurid;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getSekolah() {
        return sekolah;
    }

    public void setSekolah(String sekolah) {
        this.sekolah = sekolah;
    }
}
