package LocadoraCarros.classe;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.joda.time.*;

public class Uteis {

    public static String decimal2(Double pValor) throws Exception {
        return new DecimalFormat("###,##0.00").format(round(round(pValor, 3), 2));
    }

    public static double round(Double pValor, int pQtd) throws Exception {
        if (pValor.isNaN() || pValor.isInfinite()) {
            return 0;
        }

        BigDecimal valor = new BigDecimal(String.valueOf(pValor)).setScale(pQtd, RoundingMode.HALF_UP);
        return valor.doubleValue();
    }

    public static boolean eNumero(String i_valor) throws Exception {
        String accepted = "1234567890";

        for (int i = 0; i < i_valor.length(); i++) {
            if (!accepted.contains(i_valor.substring(i, i + 1))) {
                return false;
            }
        }

        if (i_valor.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static String somaDatas(String i_data, String i_tipo, int i_qtde) throws Exception {
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat formatter;

        try {
            formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
            gc.setTime(formatter.parse(i_data));

        } catch (Exception ex) {
            try {
                formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
                gc.setTime(formatter.parse(i_data));

            } catch (Exception ex2) {
                try {
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    gc.setTime(formatter.parse(i_data));

                } catch (Exception ex3) {
                    try {
                        formatter = new SimpleDateFormat("HH:mm:ss");
                        formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
                        gc.setTime(formatter.parse(i_data));

                    } catch (Exception ex4) {
                        try {
                            formatter = new SimpleDateFormat("HH:mm");
                            formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
                            gc.setTime(formatter.parse(i_data));

                        } catch (Exception ex5) {
                            formatter = new SimpleDateFormat("MM/yyyy");
                            //formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
                            gc.setTime(formatter.parse(i_data));
                        }
                    }
                }
            }
        }

        if (i_tipo.toLowerCase().equals("d")) {
            gc.add(Calendar.DAY_OF_MONTH, i_qtde);

        } else if (i_tipo.toLowerCase().equals("m")) {
            gc.add(Calendar.MONTH, i_qtde);

        } else if (i_tipo.toLowerCase().equals("y")) {
            gc.add(Calendar.YEAR, i_qtde);

        } else if (i_tipo.toLowerCase().equals("n")) {
            gc.add(Calendar.MINUTE, i_qtde);
        }

        return formatter.format(gc.getTime());
    }

    public static long diferencaDatas(String i_data1, String i_data2, String i_tipo) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));

        long time1 = formatter.parse(i_data1).getTime();
        long time2 = formatter.parse(i_data2).getTime();

        if (i_tipo.toLowerCase().equals("d")) {
            return (time2 - time1) / (1000 * 60 * 60 * 24);

        } else if (i_tipo.toLowerCase().equals("m")) {
            LocalDate start = new LocalDate(dataBanco(i_data1));
            LocalDate end = new LocalDate(dataBanco(i_data2));
            Period period = new Period(start, end);
            return period.getMonths();

        } else if (i_tipo.toLowerCase().equals("y")) {
            return (time2 - time1) / (1000l * 60l * 60l * 24l * 365l);

        } else if (i_tipo.toLowerCase().equals("mn")) {
            DateTime start = new DateTime(getTime(i_data1));
            DateTime end = new DateTime(getTime(i_data2));
            Minutes period = Minutes.minutesBetween(start, end);
            return period.getMinutes();

        } else if (i_tipo.toLowerCase().equals("s")) {
            DateTime start = new DateTime(getTime(i_data1));
            DateTime end = new DateTime(getTime(i_data2));
            Seconds period = Seconds.secondsBetween(start, end);
            return period.getSeconds();

        } else {
            throw new Exception("Tipo inválido!");
        }
    }

    public static String dataBanco(String i_data) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(i_data));
    }

    public static long getTime(String i_data) throws Exception {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

            return formatter.parse(i_data).getTime();

        } catch (Exception ex) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

                return formatter.parse(i_data).getTime();

            } catch (Exception ex2) {
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

                    return formatter.parse(i_data).getTime();

                } catch (Exception ex3) {
                    try {
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

                        return formatter.parse(i_data).getTime();

                    } catch (Exception ex4) {
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

                            return formatter.parse(i_data).getTime();
                        } catch (Exception ex5) {
                            SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
                            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

                            return formatter.parse(i_data).getTime();
                        }
                    }
                }
            }
        }
    }

    public static String getDataAtual() throws Exception {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        } catch (Exception ex) {
            return "";
        }
    }

    public static String dataGUI(String i_data) throws Exception {
        return dataGUI(new SimpleDateFormat("yyyy-MM-dd").parse(i_data));
    }

    public static String dataGUI(Date i_data) throws Exception {
        return new SimpleDateFormat("dd/MM/yyyy").format(i_data);
    }

}
