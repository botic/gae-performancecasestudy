package io.botic.legacy.utils;

import com.googlecode.objectify.Key;
import io.botic.legacy.objectify.tx.TxEntity;

import java.util.regex.Pattern;

public class KeyUtils {
    private final static Pattern idPartPattern = Pattern.compile("\\d\\d\\d");

    public static Key<TxEntity> splitFullId(final String fullId) {
        String[] idParts = fullId.split("-");
        if (idParts.length > 3) {
            throw new IllegalArgumentException("Invalid pattern! " + fullId);
        }

        for (String part : idParts) {
            if (part.length() != 3 || !idPartPattern.matcher(part).matches()) {
                throw new IllegalArgumentException("Invalid pattern! " + fullId);
            }
        }

        switch (idParts.length) {
            case 3:
                return Key.create(
                    Key.create(
                        Key.create(TxEntity.class, idParts[2]),
                        TxEntity.class, idParts[1] + "-" + idParts[2]
                    ),
                    TxEntity.class, fullId
                );
            case 2:
                return Key.create(
                    Key.create(
                        TxEntity.class, idParts[1]
                    ),
                    TxEntity.class, fullId
                );
            case 1:
                return Key.create(TxEntity.class, fullId);
            default:
                throw new java.lang.IllegalStateException("fullId not valid!");
        }
    }
}
