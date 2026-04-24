/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemkom.objects;

public class Sekolah {

    private String idSekolah;
    private String namaSekolah;
    private String alamat;

    public Sekolah() {
    }

    public Sekolah(String idSekolah, String namaSekolah, String alamat) {
        this.idSekolah = idSekolah;
        this.namaSekolah = namaSekolah;
        this.alamat = alamat;
    }

    public String getIdSekolah() {
        return idSekolah;
    }

    public void setIdSekolah(String idSekolah) {
        this.idSekolah = idSekolah;
    }

    public String getNamaSekolah() {
        return namaSekolah;
    }

    public void setNamaSekolah(String namaSekolah) {
        this.namaSekolah = namaSekolah;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public String toString() {
        return namaSekolah + " - " + alamat;
    }
}