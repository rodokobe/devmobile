package com.projeto.academicplanner.helper;

public class InstrucoesAlarme {


    //https://stackoverflow.com/questions/5976098/how-to-set-a-reminder-in-android


        //permissões gerais que não precisam de aprovação
        // <uses-permission android:name="android.permission.READ_CALENDAR" />
        // <uses-permission android:name="android.permission.WRITE_CALENDAR" />


    //private String[] permissoesNecessarias = new String[]{
    //        Manifest.permission.READ_CALENDAR,
    //        Manifest.permission.WRITE_CALENDAR,
    //};

    //validação das permissões necessárias para fazer o agendamento - acessar agenda e escrever
    //Permissao.validarPermissoes(permissoesNecessarias, this, 1);

    /*private void addReminderInCalendar() {
        Calendar cal = Calendar.getInstance();
        Uri EVENTS_URI = Uri.parse(getCalendarUriBase(true) + "events");
        ContentResolver cr = getContentResolver();
        TimeZone timeZone = TimeZone.getDefault();

        // Inserting an event in calendar.
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, "Sanjeev Reminder 01");
        values.put(CalendarContract.Events.DESCRIPTION, "A test Reminder.");
        values.put(CalendarContract.Events.ALL_DAY, 0);
        // event starts at 11 minutes from now
        values.put(CalendarContract.Events.DTSTART, cal.getTimeInMillis() + 11 * 60 * 1000);
        // ends 60 minutes from now
        values.put(CalendarContract.Events.DTEND, cal.getTimeInMillis() + 60 * 60 * 1000);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        Uri event = cr.insert(EVENTS_URI, values);

        // Display event id
        Toast.makeText(getApplicationContext(), "Event added :: ID :: " + event.getLastPathSegment(), Toast.LENGTH_SHORT).show();

        // Adding reminder for event added.
        Uri REMINDERS_URI = Uri.parse(getCalendarUriBase(true) + "reminders");
        values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(event.getLastPathSegment()));
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        values.put(CalendarContract.Reminders.MINUTES, 10);
        cr.insert(REMINDERS_URI, values);
    }

    // Returns Calendar Base URI, supports both new and old OS.
    private String getCalendarUriBase(boolean eventUri) {
        Uri calendarURI = null;
        try {
            if (android.os.Build.VERSION.SDK_INT <= 7) {
                calendarURI = (eventUri) ? Uri.parse("content://calendar/") : Uri.parse("content://calendar/calendars");
            } else {
                calendarURI = (eventUri) ? Uri.parse("content://com.android.calendar/") : Uri
                        .parse("content://com.android.calendar/calendars");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendarURI.toString();
    }*/


}
