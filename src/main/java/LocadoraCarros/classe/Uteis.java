package LocadoraCarros.classe;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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

}
