package io.botic.legacy.utils;

import io.botic.legacy.objectify.flat.FlatAddress;
import io.botic.legacy.objectify.flat.FlatPlayer;
import io.botic.legacy.objectify.flat.FlatTeam;
import io.botic.legacy.objectify.simple.UnindexedEntity;
import io.botic.legacy.objectify.simple.IndexedEntity;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

public class BenchUtils {
    private static Random random = new Random();
    private static String[] firstNames = new String[]{"Peter", "Tim", "Ron", "Fabricio", "Paul", "Ryan", "Sammy", "Yoan"};
    private static String[] lastNames = new String[]{"Ameobi", "Riviere", "Cabella", "Taylor", "Coloccini", "Krul", "Williamson"};

    private static String[] cities = new String[]{"London", "Liverpool", "Newcastle", "Manchester", "Everton", "Sunderland", "Leicester",
    "Guildford", "Grove", "Lanark"};

    private static String[] streets = new String[]{"Abbotswell Street", "Adelaide Road", "Avenue Cottages", "Birkbeck Road",
    "Richmond Place", "Baker Street", "Hall Road", "Flood Walk", "Greville Mews"};

    public static String randomFirstName() {
        return firstNames[random.nextInt(firstNames.length)];
    }

    public static String randomLastName() {
        return lastNames[random.nextInt(lastNames.length)];
    }

    public static String randomCity() {
        return cities[random.nextInt(cities.length)];
    }

    public static String randomTeamName() {
        return "FC " + (1890 + random.nextInt(65)) + " " + cities[random.nextInt(cities.length)];
    }

    public static String randomStreet() {
        return streets[random.nextInt(streets.length)] + (1 + random.nextInt(90));
    }

    public static String randomZip() {
        return "" + (10000 + random.nextInt(90000));
    }

    public static Date randomBirthday() {
        return (new GregorianCalendar(
            (1980 + random.nextInt(15)),
            random.nextInt(12),
            random.nextInt(28)
        )).getTime();
    }

    public static int randomShirtNumber() {
        return random.nextInt(50) + 1;
    }

    public static Long randomBudget() {
        return 10000000l + random.nextInt(30000000);
    }

    public static FlatAddress randomAddress() {
        return new FlatAddress(randomStreet(), randomZip(), randomCity(), "England");
    }

    public static FlatTeam randomTeam() {
        return new FlatTeam(randomTeamName(), randomAddress(), randomBudget());
    }

    public static FlatPlayer randomPlayer() {
        return new FlatPlayer(randomFirstName(), randomLastName(), randomBirthday(), randomShirtNumber());
    }

    public static IndexedEntity randomIndexedEntity() {
        byte[] ba = new byte[10];

        // fill byte[]
        random.nextBytes(ba);

        return new IndexedEntity("This is an IndexedEntity", random.nextInt(), random.nextLong(),
            random.nextFloat(), random.nextDouble(), random.nextBoolean(), (byte) 0, new Date(), ba
        );
    }

    public static UnindexedEntity randomUnindexedEntity() {
        byte[] ba = new byte[10];

        // fill byte[]
        random.nextBytes(ba);

        return new UnindexedEntity("This is an UnindexedEntity", random.nextInt(), random.nextLong(),
            random.nextFloat(), random.nextDouble(), random.nextBoolean(), (byte) 0, new Date(), ba
        );
    }

    public static char randomCharacter() {
        return (char)(random.nextInt(26) + 'a');
    }

    public static String randomString(int length) {
        if (length > 0) {
            StringBuffer sb = new StringBuffer(length);
            for (int i = 0; i < length; i++) {
                sb.append(randomCharacter());
            }
            return sb.toString().toUpperCase(Locale.ENGLISH);
        }

        return "";
    }

    public static Random getRandom() {
        return random;
    }

}
