package io.botic.casestudy.benchmark;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.modules.ModulesService;
import com.google.appengine.api.modules.ModulesServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.Work;
import io.botic.casestudy.model.*;
import io.botic.response.SimpleResponse;
import io.botic.response.TimedBenchResult;
import io.botic.response.TimedOperationsResult;

import javax.inject.Named;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static io.botic.legacy.objectify.OfyService.ofy;

@Api(
    name="gaebench",
    version = "v1",
    description = "GAE Bench API",
    defaultVersion = AnnotationBoolean.TRUE
)
public class DatastoreBench {

    static {
        ofy();
    }

    private static final Logger logger = Logger.getLogger(DatastoreBench.class.getName());
    private static final Random random = new java.util.Random();

    @ApiMethod(name = "datastore.writeLightweightEntity", httpMethod = "POST", path = "datastore/writeLightweightEntity")
    public TimedBenchResult writeLightweightEntity(@Named("baseString") String baseString) {
        TimedBenchResult result = new TimedBenchResult("datastore/writeLightweightEntity");

        for (int i = 0; i < 5; i++) {
            LightweightEntity entity = new LightweightEntity(
                baseString,
                random.nextLong() + "-" + random.nextLong(),
                baseString + "-" + random.nextLong()
            );
            ofy().save().entity(entity).now(); // Blocking call
        }

        return result.stop(5);
    }

    @ApiMethod(name = "datastore.writeHeavyweightEntity", httpMethod = "POST", path = "datastore/writeHeavyweightEntity")
    public TimedBenchResult writeHeavyweightEntity(@Named("baseString") String baseString) {
        TimedBenchResult result = new TimedBenchResult("datastore/writeHeavyweightEntity");

        String randomString = java.lang.Long.toHexString(java.lang.Double.doubleToLongBits(java.lang.Math.random())) +
            random.nextLong() + " " + random.nextLong() + " " + random.nextGaussian();
        StringBuffer sb = new StringBuffer(120000);
        for (int i = 0; i < 1500; i++) {
            sb.append(randomString);
        }

        logger.info("StringBuffer: " + sb.length());

        String longBaseString = sb.toString();
        sb = null; // free buffer for gc

        byte[] randomBytes = new byte[1024];
        random.nextBytes(randomBytes);

        for (int i = 0; i < 5; i++) {
            HeavyweightEntity entity = new HeavyweightEntity(
                longBaseString + longBaseString + longBaseString,
                longBaseString + baseString + baseString,
                baseString + longBaseString + longBaseString,
                longBaseString + random.nextLong(),
                baseString + longBaseString + baseString,
                baseString,
                random.nextInt(),
                random.nextLong(),
                random.nextFloat(),
                random.nextDouble(),
                random.nextBoolean(),
                randomBytes[0],
                new java.util.Date(),
                randomBytes
            );
            ofy().save().entity(entity).now(); // Blocking call
        }

        return result.stop(5);
    }

    @ApiMethod(name = "datastore.writeEntityGroup", httpMethod = "POST", path = "datastore/writeEntityGroup")
    public TimedBenchResult writeEntityGroup(@Named("baseString") final String baseString) {
        final TimedBenchResult result = new TimedBenchResult("datastore/writeEntityGroup");

        // Starts a transaction to build up an entity group
        int entityCount = ofy().transact(new Work<Integer>() {
            @Override
            public Integer run() {
                int entityCount = 0;

                // Creates the root entity
                GroupedEntity root = new GroupedEntity(baseString + "-level.0-root", 0);
                ofy().save().entity(root).now();
                entityCount++;

                // Create 3 first-level children
                for (int i = 0; i < 3; i++) {
                    GroupedEntity child = new GroupedEntity(baseString + "-level.1." + i + "-child", root, 1);
                    ofy().save().entity(child).now();
                    entityCount++;

                    // Create for each direct child also children
                    for (int u = 0; u < 3; u++) {
                        GroupedEntity grandchild = new GroupedEntity(baseString + "-level.2." + i + "." + u + "-grandchild", child, 2);
                        ofy().save().entity(grandchild).now();
                        entityCount++;

                        // And also 3 great grandchildren
                        for (int x = 0; x < 3; x++) {
                            GroupedEntity greatgrandchild = new GroupedEntity(baseString + "-level.3." + i + "." + u + "." + x + "-greatgrandchild", grandchild, 3);
                            ofy().save().entity(greatgrandchild).now();
                            entityCount++;
                        }
                    }
                }

                return entityCount;
            }
        });

        result.setText(result.getText() + " - " + entityCount + " entities");
        return result.stop(entityCount);
    }

    @ApiMethod(name = "datastore.writeEntityGroupWithoutTransaction", httpMethod = "POST", path = "datastore/writeEntityGroupWithoutTransaction")
    public TimedBenchResult writeEntityGroupWithoutTransaction(@Named("baseString") final String baseString) {
        final TimedBenchResult result = new TimedBenchResult("datastore/writeEntityGroupWithoutTransaction");

        // Starts a transaction to build up an entity group
        int entityCount = 0;

        // Creates the root entity
        GroupedEntity root = new GroupedEntity(baseString + "-level.0-root", 0);
        ofy().save().entity(root).now();
        entityCount++;

        // Create 3 first-level children
        for (int i = 0; i < 3; i++) {
            GroupedEntity child = new GroupedEntity(baseString + "-level.1." + i + "-child", root, 1);
            ofy().save().entity(child).now();
            entityCount++;

            // Create for each direct child also children
            for (int u = 0; u < 3; u++) {
                GroupedEntity grandchild = new GroupedEntity(baseString + "-level.2." + i + "." + u + "-grandchild", child, 2);
                ofy().save().entity(grandchild).now();
                entityCount++;

                // And also 3 great grandchildren
                for (int x = 0; x < 3; x++) {
                    GroupedEntity greatgrandchild = new GroupedEntity(baseString + "-level.3." + i + "." + u + "." + x + "-greatgrandchild", grandchild, 3);
                    ofy().save().entity(greatgrandchild).now();
                    entityCount++;
                }
            }
        }

        result.setText(result.getText() + " - " + entityCount + " entities");
        return result.stop(entityCount);
    }

    @ApiMethod(name = "datastore.writeRetrievableEntity", httpMethod = "POST", path = "datastore/writeRetrievableEntity")
    public TimedBenchResult writeRetrievableEntity(@Named("baseString") String baseString) {
        TimedBenchResult result = new TimedBenchResult("datastore/writeRetrievableEntity");

        StringBuffer randomStringBuffer = new StringBuffer(15000);
        for (int i = 0; i < 1000; i++) {
            randomStringBuffer.append(java.lang.Long.toHexString(java.lang.Double.doubleToLongBits(java.lang.Math.random())));
        }
        String longString = baseString + randomStringBuffer.toString();
        randomStringBuffer = null;

        RetrievableEntity entity = new RetrievableEntity(
            baseString,
            1000 + random.nextInt(9000),
            new Date(),
            longString
        );
        ofy().save().entity(entity).now(); // Blocking call

        return result.stop(1);
    }

    @ApiMethod(name = "datastore.readRetrievableEntity", httpMethod = "GET", path = "datastore/readRetrievableEntity")
    public TimedOperationsResult readRetrievableEntity(@Named("number") int number) {
        TimedOperationsResult tres = new TimedOperationsResult();
        RetrievableEntity result = ofy().load().type(RetrievableEntity.class).filter("number =", number).first().now();
        tres.setText(result.toString());
        return tres.stop(1);
    }

    @ApiMethod(name = "datastore.readRetrievableEntityList", httpMethod = "GET", path = "datastore/readRetrievableEntityList")
    public TimedOperationsResult readRetrievableEntityList(@Named("baseString") String baseString) {
        TimedOperationsResult tres = new TimedOperationsResult();
        List<RetrievableEntity> results = ofy().load().type(RetrievableEntity.class).filter("name =", baseString).limit(100).list();
        tres.setText("Found " + results.size() + " entities.");
        return tres.stop(results.size());
    }

    @ApiMethod(name = "datastore.writeNamedKeyEntity", httpMethod = "POST", path = "datastore/writeNamedKeyEntity")
    public TimedBenchResult writeNamedKeyEntity(@Named("keyString") String keyString) throws ConflictException {

        // Check if entity already exists
        NamedKeyEntity existingEntity = ofy().load().key(Key.create(NamedKeyEntity.class, keyString)).now();
        if (existingEntity != null) {
            throw new ConflictException("Entity " + keyString + " already exists!");
        }

        TimedBenchResult result = new TimedBenchResult("datastore/writeNamedKeyEntity");

        NamedKeyEntity entity = new NamedKeyEntity(keyString, "Created at " + (new Date()).toString());
        ofy().save().entity(entity).now(); // Blocking call

        return result.stop(1);
    }

    @ApiMethod(name = "datastore.readNamedKeyEntityByKey", httpMethod = "GET", path = "datastore/readNamedKeyEntityByKey")
    public NamedKeyEntity readNamedKeyEntityByKey(@Named("number") int number) throws NotFoundException {
        NamedKeyEntity result = ofy().load().key(Key.create(NamedKeyEntity.class, "ent-" + number)).now();

        if (result == null) {
            throw new NotFoundException("Entity ent-" + number + " not found.");
        }

        return result;
    }

    @ApiMethod(name = "datastore.readNamedKeyEntityByIndex", httpMethod = "GET", path = "datastore/readNamedKeyEntityByIndex")
    public NamedKeyEntity readNamedKeyEntityByIndex(@Named("number") int number) throws NotFoundException {
        NamedKeyEntity result = ofy().load().type(NamedKeyEntity.class).filter("indexedIdentifier =", "ent-" + number).first().now();

        if (result == null) {
            throw new NotFoundException("Entity ent-" + number + " not found.");
        }

        return result;
    }

    @ApiMethod(name = "datastore.createTreeGroup", httpMethod = "POST", path = "datastore/createTreeGroup")
    public TimedBenchResult createTreeGroup(@Named("root") final String root) throws ConflictException {
        TreeEntity te = ofy().load().key(Key.create(TreeEntity.class, root)).now();

        if (te != null) {
            throw new ConflictException("Entity " + root + " already exists.");
        }

        TimedBenchResult result = new TimedBenchResult("datastore/createTreeGroup");

        int entityCount = ofy().transact(new Work<Integer>() {
            @Override
            public Integer run() {
                DecimalFormat keyFormat = new DecimalFormat("000");
                TreeEntity treeRoot = new TreeEntity(root, (new Date()).toString(), null);
                ofy().save().entity(treeRoot).now();
                int counter = 1;

                for (int i = 0; i <= 10; i++) {
                    TreeEntity level1 = new TreeEntity(root + "-" + keyFormat.format(i), (new Date()).toString(), Ref.create(treeRoot));
                    ofy().save().entity(level1).now();
                    counter++;

                    for (int u = 0; u <= 10; u++) {
                        TreeEntity level2 = new TreeEntity(root + "-" + keyFormat.format(i) + "-" + keyFormat.format(u), (new Date()).toString(), Ref.create(level1));
                        ofy().save().entity(level2).now();
                        counter++;

                        for (int v = 0; v <= 10; v++) {
                            TreeEntity level3 = new TreeEntity(root + "-" + keyFormat.format(i) + "-" + keyFormat.format(u) + "-" + keyFormat.format(v), (new Date()).toString(), Ref.create(level2));
                            ofy().save().entity(level3).now();
                            counter++;
                        }
                    }
                }

                return counter;
            }
        });

        return result.stop(entityCount);
    }

    @ApiMethod(name = "datastore.updateTreeGroup", httpMethod = "POST", path = "datastore/updateTreeGroup")
    public SimpleResponse updateTreeGroup(@Named("keyString") String keyString) throws NotFoundException {
        String[] idParts = keyString.split("-");
        if (idParts.length > 4) {
            throw new NotFoundException("Invalid key.");
        }

        // Generating a valid datastore key is hard ...
        final Key<TreeEntity> key;
        switch (idParts.length) {
            case 4:
                key = Key.create(
                    Key.create(
                        Key.create(
                            Key.create(TreeEntity.class, idParts[0]),
                            TreeEntity.class,
                            idParts[0] + "-" + idParts[1]
                        ),
                        TreeEntity.class,
                        idParts[0] + "-" + idParts[1] + "-" + idParts[2]
                    ),
                    TreeEntity.class,
                    keyString
                );
                break;
            case 3:
                key = Key.create(
                    Key.create(
                        Key.create(TreeEntity.class, idParts[0]),
                        TreeEntity.class,
                        idParts[0] + "-" + idParts[1]
                    ),
                    TreeEntity.class,
                    keyString
                );
                break;
            case 2:
                key = Key.create(
                    Key.create(TreeEntity.class, idParts[0]),
                    TreeEntity.class,
                    keyString
                );
                break;
            case 1:
                key = Key.create(TreeEntity.class, keyString);
                break;
            default:
                key = null;
        }

        if (key == null) {
            throw new NotFoundException("Invalid key!");
        }

        final AtomicInteger attempts = new AtomicInteger(0);
        final String text = (new Date()).toString();
        TreeEntity modified = ofy().transact(new Work<TreeEntity>() {
            public TreeEntity run() {
                int currentAttempts = attempts.incrementAndGet();
                TreeEntity toModify = ofy().load().key(key).now();
                if (toModify != null) {
                    toModify.setText(text);
                    ofy().save().entity(toModify).now();
                }

                if (currentAttempts > 1) {
                    logger.info("Attempt #" + currentAttempts);
                }

                return toModify;
            }
        });
        if (modified == null) {
            return new SimpleResponse("Found no entity to modify.");
        }

        return new SimpleResponse(attempts.get() + " attempt(s), modified: " + modified.getId());
    }

}