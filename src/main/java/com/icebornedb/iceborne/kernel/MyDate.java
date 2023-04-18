package com.icebornedb.iceborne.kernel;

import java.util.Calendar;

public class MyDate {
    private static final int[] WEEKDAY = {2, 3, 4, 5, 6, 7, 1};
    private static final int[] MONTH   = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final MyDate STANDARD_DAY  = new MyDate(2020, 1, 1);
    public static final MyDate TODAY   = new MyDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DATE));

    private int year;
    private int month;
    private int day;

    public MyDate(int y, int m, int d) {
        this.year  = y;
        this.month = m;
        this.day   = d;
    }
    public static int getMonthDay(int year, int month) {
        return (month == 2 && year % 4 == 0) ? 29 : MONTH[month];
    }

    public int dateDeviation(MyDate myDate) {
        int i;

        int basic = Math.min(year, myDate.year);
        int thisDay = 0; // how many days during basic.01.01 to this day?
        int dateDay = 0;
        int runAmount;

        runAmount = this.year / 4 - basic / 4 + (basic % 4 == 0 ? 1 : 0);
        thisDay   += (basic == this.year) ? 0 :(runAmount * 366 + (this.year - basic - runAmount) * 365);
        for(i = 1; i < this.month; i++) {
            thisDay += getMonthDay(this.year, i);
        }
        thisDay   += this.day - ((this.month == 1) ? 1 : 0);

        runAmount = myDate.year / 4 - basic / 4 + (basic % 4 == 0 ? 1 : 0);
        dateDay   += (basic == myDate.year) ? 0 : (runAmount * 366 + (myDate.year - basic - runAmount) * 365);
        for(i = 1; i < myDate.month; i++) {
            dateDay += getMonthDay(myDate.year, i);
        }
        dateDay   += myDate.day - ((myDate.month == 1) ? 1 : 0);

        return thisDay - dateDay;
    }

    public int getWeekday() {
        int x = ((dateDeviation(STANDARD_DAY)) + 1) % 7;
        return WEEKDAY[x];
    }

    @Override
    public String toString() {
        StringBuilder bud = new StringBuilder();
        bud.append(String.format("%d.%d.%d, ", year, month, day));
        switch (getWeekday()) {
            case 1 : bud.append("Monday");    break;
            case 2 : bud.append("Tuesday");   break;
            case 3 : bud.append("Wednesday"); break;
            case 4 : bud.append("Thursday");  break;
            case 5 : bud.append("Friday");    break;
            case 6 : bud.append("Saturday");  break;
            case 7 : bud.append("Sunday");    break;
        }
        return bud.toString();
    }

    public String simpleString() {
        return String.format("%d,%d,%d", year, month, day);
    }
}