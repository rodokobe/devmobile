package com.projeto.academicplanner.helper;

import java.text.SimpleDateFormat;

public class DateTimeCustom {

    public static String getNowDate() {
        long referenciaDataAtual = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String nowDate = simpleDateFormat.format(referenciaDataAtual);

        return nowDate;
    }

    public static String getNowTime() {
        long refenciaHoraAtual = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:00");
        String nowTime = simpleDateFormat.format(refenciaHoraAtual);

        return nowTime;
    }

}
