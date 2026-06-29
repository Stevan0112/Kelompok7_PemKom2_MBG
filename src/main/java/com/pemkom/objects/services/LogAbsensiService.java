/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pemkom.objects.services;

import com.mongodb.client.model.Filters;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.pemkom.objects.GenericDAO;
import com.pemkom.objects.LogAbsensi;
import com.pemkom.objects.LogAbsensiView;
import com.pemkom.objects.Murid;
import com.pemkom.objects.Sekolah;
import java.util.Date;
import java.time.ZoneId;
import java.time.LocalDateTime;

public class LogAbsensiService {

    private final GenericDAO<LogAbsensi> logDAO;
    private final GenericDAO<Murid> muridDAO;

    private static final DateTimeFormatter FMT_JAM
            = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter FMT_HARI
            = DateTimeFormatter.ofPattern("EEEE", new Locale("id", "ID"));

    public LogAbsensiService() {
        this.logDAO = new GenericDAO<>("Log Absensi", LogAbsensi.class);
        this.muridDAO = new GenericDAO<>("Murid", Murid.class);
    }

    public List<LogAbsensiView> getAllLogView() {
        List<LogAbsensi> logs = logDAO.findAll();
        List<LogAbsensiView> result = new ArrayList<>();

        for (LogAbsensi log : logs) {
            Murid murid = muridDAO.findOne(
                    Filters.eq("uidRfid", log.getUidRfid())
            );

            String nama = (murid != null) ? murid.getNamaLengkap() : "(tidak dikenal)";
            String sekolah = (murid != null) ? murid.getSekolah() : "-";
            LocalDateTime waktu = log.getWaktuTap()
    .toInstant()
    .atZone(ZoneId.systemDefault())
    .toLocalDateTime();

String jam  = waktu.format(FMT_JAM);
String hari = waktu.format(FMT_HARI);
LocalDate tgl = waktu.toLocalDate();

            result.add(new LogAbsensiView(
                    nama, log.getUidRfid(), sekolah, jam, hari, log.getStatus(), tgl
            ));
        }
        return result;
    }

    // Filter bisa kombinasi atau salah satu saja
    public List<LogAbsensiView> filterLog(String sekolah, String namaCari, LocalDate tanggal) {
        List<LogAbsensiView> semua = getAllLogView();
        List<LogAbsensiView> filtered = new ArrayList<>();

        for (LogAbsensiView item : semua) {
            boolean cocokSekolah = sekolah == null || sekolah.isEmpty()
                    || sekolah.equals("Semua")
                    || item.getSekolah().equalsIgnoreCase(sekolah);

            boolean cocokNama = namaCari == null || namaCari.isEmpty()
                    || item.getNamaLengkap().toLowerCase()
                            .contains(namaCari.toLowerCase());

            boolean cocokTanggal = tanggal == null
                    || item.getTanggal().equals(tanggal);

            if (cocokSekolah && cocokNama && cocokTanggal) {
                filtered.add(item);
            }
        }
        return filtered;
    }

    // Ambil semua nama sekolah untuk isi dropdown
    public List<String> getAllSekolah() {
        List<Sekolah> list = new GenericDAO<>("Sekolah", Sekolah.class).findAll();
        List<String> namaSekolah = new ArrayList<>();
        namaSekolah.add("Semua");
        for (Sekolah s : list) {
            namaSekolah.add(s.getNamaSekolah()); // sesuaikan nama getter kamu
        }
        return namaSekolah;
    }
}
