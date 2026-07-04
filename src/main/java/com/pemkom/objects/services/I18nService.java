package com.pemkom.objects.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class I18nService {

    private static ResourceBundle bundle;
    private static Locale currentLocale;

    // Interface Observer — setiap panel yang mau auto-update daftarkan diri
    public interface I18nChangeListener {
        void onLanguageChanged();
    }

    // Daftar semua panel yang sedang mendengarkan perubahan bahasa
    private static final List<I18nChangeListener> listeners = new ArrayList<>();

    static {
       setLocale(new Locale("id"));
    }

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        bundle = ResourceBundle.getBundle("messages", currentLocale);
        // Otomatis beritahu semua panel untuk update teks
        notifyListeners();
    }

    public static String get(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException | NullPointerException e) {
            return "!" + key + "!";
        }
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    // Daftarkan panel sebagai listener
    public static synchronized void registerListener(I18nChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    // Hapus panel dari listener (saat panel ditutup)
    public static synchronized void unregisterListener(I18nChangeListener listener) {
        listeners.remove(listener);
    }

    // Panggil onLanguageChanged() di semua panel terdaftar
    private static void notifyListeners() {
        for (I18nChangeListener listener : listeners) {
            if (listener != null) {
                listener.onLanguageChanged();
            }
        }
    }
}