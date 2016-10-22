package eric.clapton.infrastructure;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

public interface Convertible {
    boolean toBoolean();

    Byte toByte();

    Character toChar();

    Date toDate();

    BigDecimal toBigDecimal();

    BigInteger toBigInteger();

    Double toDouble();

    Short toShort();

    Integer toInteger();

    Long toLong();

    Float toFloat();

    String ToString();
}
