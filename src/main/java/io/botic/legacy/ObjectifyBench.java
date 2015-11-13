package io.botic.legacy;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.VoidWork;
import io.botic.legacy.objectify.eg.EGAddress;
import io.botic.legacy.objectify.eg.EGPlayer;
import io.botic.legacy.objectify.flat.FlatPlayer;
import io.botic.legacy.objectify.simple.SingleEntity;
import io.botic.response.BenchmarkResult;
import io.botic.legacy.objectify.eg.EGTeam;
import io.botic.legacy.objectify.flat.FlatTeam;
import io.botic.response.TimedBenchResult;
import io.botic.legacy.utils.BenchUtils;

import java.util.*;
import java.util.logging.Logger;

import static io.botic.legacy.objectify.OfyService.ofy;

@Api(
    name="gaebench",
    version = "v1",
    description = "GAE Bench API",
    defaultVersion = AnnotationBoolean.TRUE
)
public class ObjectifyBench {
    private final Logger logger = Logger.getLogger(ObjectifyBench.class.getName());

    static {
        ofy();
    }

    private static final int TEAM_COUNT = 10;
    private static final int PLAYER_COUNT = 10;
    private static final int INDEXED_COUNT = 10;
    private static final int UNINDEXED_COUNT = INDEXED_COUNT;
    private static final int EG_SUBSUB_COUNT = 20;

    private static final Logger log = Logger.getLogger(CpuBench.class.getName());

    @ApiMethod(name="objectify.write.flat", httpMethod = "GET")
    public TimedBenchResult writeFlatBenchmark() {
        TimedBenchResult result = new TimedBenchResult("Objectify Write Flat Bench");
        int saveOps = 0;

        // Prepare Teams;
        ArrayList<FlatTeam> flatTeams = new ArrayList<FlatTeam>(20);
        for (int i = 0; i < TEAM_COUNT; i++) {
            FlatTeam t = BenchUtils.randomTeam();
            for (int u = 0; u < PLAYER_COUNT; u++) {
                FlatPlayer p = BenchUtils.randomPlayer();
                ofy().save().entity(p).now();
                saveOps++;
                t.getPlayers().add(Ref.create(p));
            }
            flatTeams.add(t);
        }

        for (FlatTeam t : flatTeams) {
            saveOps++;
            ofy().save().entity(t).now();
        }

        result.stop(saveOps);
        return result;
    }

    @ApiMethod(name="objectify.write.eg", httpMethod = "GET")
    public TimedBenchResult writeEgBenchmark() {
        TimedBenchResult result = new TimedBenchResult("Objectify Write EntityGrouped Bench");
        int saveOps = 0;

        for (int i = 0; i < TEAM_COUNT; i++) {
            EGTeam t = new EGTeam(BenchUtils.randomTeamName(),
                new EGAddress(
                    BenchUtils.randomStreet(), BenchUtils.randomZip(), BenchUtils.randomCity(), "England"
                ), BenchUtils.randomBudget());

            ofy().save().entity(t).now();
            saveOps++;

            for (int u = 0; u < PLAYER_COUNT; u++) {
                EGPlayer p = new EGPlayer(
                    BenchUtils.randomFirstName(),
                    BenchUtils.randomLastName(),
                    BenchUtils.randomBirthday(),
                    BenchUtils.randomShirtNumber()
                );
                p.setTeam(Ref.create((EGTeam) t));
                ofy().save().entity(p).now();
                saveOps++;
            }
        }

        result.stop(saveOps);
        return result;
    }

    @ApiMethod(name="objectify.write.indexed.sync", httpMethod = "GET")
    public TimedBenchResult writeIndexedBenchmark() {
        TimedBenchResult result = new TimedBenchResult("Indexed Sync Write Benchmark");

        for (int i = 0; i < INDEXED_COUNT; i++) {
            ofy().save().entity(BenchUtils.randomIndexedEntity()).now();
        }

        result.stop(INDEXED_COUNT);
        return result;
    }

    @ApiMethod(name="objectify.write.indexed.sync.tx", httpMethod = "GET")
    public TimedBenchResult writeIndexedBenchmarkTx() {
        TimedBenchResult result = new TimedBenchResult("Indexed Sync Write Benchmark");

        for (int i = 0; i < INDEXED_COUNT; i++) {
            ofy().transactNew(1, new VoidWork() {
                @Override
                public void vrun() {
                    ofy().save().entity(BenchUtils.randomIndexedEntity());
                }
            });
        }

        result.stop(INDEXED_COUNT);
        return result;
    }

    @ApiMethod(name="objectify.write.indexed.async", httpMethod = "GET")
    public TimedBenchResult writeIndexedAsyncBenchmark() {
        TimedBenchResult result = new TimedBenchResult("Indexed Async Write Benchmark");

        for (int i = 0; i < INDEXED_COUNT; i++) {
            ofy().save().entity(BenchUtils.randomIndexedEntity());
        }

        result.stop(INDEXED_COUNT);
        return result;
    }

    @ApiMethod(name="objectify.write.indexed.sync.batch", httpMethod = "GET")
    public TimedBenchResult writeIndexedBatchBenchmark() {
        TimedBenchResult result = new TimedBenchResult("Indexed Sync Batch Write Benchmark");

        ofy().save().entities(
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity()
        ).now();

        result.stop(10);
        return result;
    }

    @ApiMethod(name="objectify.write.indexed.async.batch", httpMethod = "GET")
    public TimedBenchResult writeIndexedAsyncBatchBenchmark() {
        TimedBenchResult result = new TimedBenchResult("Indexed Async Batch Write Benchmark");

        ofy().save().entities(
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity(),
            BenchUtils.randomIndexedEntity()
        );

        result.stop(10);
        return result;
    }

    @ApiMethod(name="objectify.write.unindexed.sync", httpMethod = "GET")
    public TimedBenchResult writeUnindexedBenchmark() {
        TimedBenchResult result = new TimedBenchResult("Unindexed Sync Write Benchmark");

        for (int i = 0; i < UNINDEXED_COUNT; i++) {
            ofy().save().entity(BenchUtils.randomUnindexedEntity()).now();
        }

        result.stop(INDEXED_COUNT);
        return result;
    }

    @ApiMethod(name="objectify.write.unindexed.async", httpMethod = "GET")
    public TimedBenchResult writeUnindexedAsyncBenchmark() {
        TimedBenchResult result = new TimedBenchResult("Unindexed Async Write Benchmark");

        for (int i = 0; i < UNINDEXED_COUNT; i++) {
            ofy().save().entity(BenchUtils.randomUnindexedEntity());
        }

        result.stop(UNINDEXED_COUNT);
        return result;
    }

    @ApiMethod(name="objectify.write.unindexed.sync.batch", httpMethod = "GET")
    public TimedBenchResult writeUnndexedBatchBenchmark() {
        TimedBenchResult result = new TimedBenchResult("Unindexed Sync Batch Write Benchmark");

        ofy().save().entities(
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity()
        ).now();

        result.stop(10);
        return result;
    }

    @ApiMethod(name="objectify.write.unindexed.async.batch", httpMethod = "GET")
    public TimedBenchResult writeUnindexedAsyncBatchBenchmark() {
        TimedBenchResult result = new TimedBenchResult("Unindexed Async Batch Write Benchmark");

        ofy().save().entities(
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity(),
            BenchUtils.randomUnindexedEntity()
        );

        result.stop(10);
        return result;
    }

    @ApiMethod(name="objectify.egwrites.create", httpMethod = "GET")
    public TimedBenchResult egWritesCreate() {
        TimedBenchResult result = new TimedBenchResult("Create test data");

        String groupId = "EG-" + BenchUtils.randomString(10) + "-" + (1 + BenchUtils.getRandom().nextInt(1000));

        // Root Level
        SingleEntity root = new SingleEntity("root-" + groupId, null, "Root");
        ofy().save().entity(root).now();

        // First level children
        SingleEntity c1 = new SingleEntity("c1-" + groupId, Ref.create(root), "C1");
        SingleEntity c2 = new SingleEntity("c2-" + groupId, Ref.create(root), "C2");
        SingleEntity c3 = new SingleEntity("c3-" + groupId, Ref.create(root), "C3");
        ofy().save().entities(c1, c2, c3).now();

        // Second level children
        List<SingleEntity> subSubSet = new ArrayList<>(EG_SUBSUB_COUNT);
        for (int i = 1; i <= EG_SUBSUB_COUNT; i++) {
            subSubSet.add(new SingleEntity("c1c" + i + "-" + groupId, Ref.create(c1), "C1C" + i));
        }
        ofy().save().entities(subSubSet).now();

        result.stop(4 + EG_SUBSUB_COUNT);
        return result;
    }

    @ApiMethod(name="objectify.egwrites.notsameeg.create", httpMethod = "GET")
    public TimedBenchResult nonEgWritesCreate() {
        TimedBenchResult result = new TimedBenchResult("Create test data");

        String groupId = "NG-" + BenchUtils.randomString(10) + "-" + (1 + BenchUtils.getRandom().nextInt(1000));

        // Root Level
        SingleEntity root = new SingleEntity("root-" + groupId, null, "NG-Root");
        ofy().save().entity(root).now();

        // Not in the root entity group
        SingleEntity c1 = new SingleEntity("ng-c1-" + groupId, null, "NG-C1");
        SingleEntity c2 = new SingleEntity("ng-c2-" + groupId, null, "NG-C2");
        SingleEntity c3 = new SingleEntity("ng-c3-" + groupId, null, "NG-C3");
        ofy().save().entities(c1, c2, c3).now();

        // Not in the root entity group
        List<SingleEntity> subSubSet = new ArrayList<>(EG_SUBSUB_COUNT);
        for (int i = 1; i <= EG_SUBSUB_COUNT; i++) {
            subSubSet.add(new SingleEntity("ng-c1c" + i + "-" + groupId, null, "NG-SUB" + i));
        }
        ofy().save().entities(subSubSet).now();

        result.stop(4 + EG_SUBSUB_COUNT);
        return result;
    }

    /**
     * This method is just for debugging
     */
    @ApiMethod(name="objectify.testdata.create", httpMethod = "GET")
    public BenchmarkResult createTestData() {
        logger.info("Test data method called.");
        FlatTeam t = new FlatTeam("Benchmark " + BenchUtils.randomTeamName(), BenchUtils.randomAddress(), 200000l);
        FlatPlayer p = new FlatPlayer("Firstname", "Lastname", new Date(), 99);
        ofy().save().entity(p).now();
        t.getPlayers().add(Ref.create(p));
        ofy().save().entity(t).now();
        p.setFirstName("Changed Name");
        ofy().save().entity(p).now();

        FlatTeam got = ofy().load().key(Key.create(FlatTeam.class, t.getId())).now();

        String returnStr = "";
        for (Ref<FlatPlayer> gotP : got.getPlayers()) {
            returnStr += gotP.get().toString();
        }

        return new BenchmarkResult(returnStr, 0);
    }

}
