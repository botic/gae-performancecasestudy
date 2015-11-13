package io.botic.legacy;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import io.botic.legacy.objectify.eg.EGPlayer;
import io.botic.legacy.objectify.flat.FlatPlayer;
import io.botic.legacy.objectify.simple.UnindexedEntity;
import io.botic.legacy.objectify.eg.EGTeam;
import io.botic.legacy.objectify.flat.FlatTeam;
import io.botic.legacy.objectify.simple.IndexedEntity;
import io.botic.legacy.objectify.simple.SingleEntity;
import io.botic.legacy.objectify.tx.TxEntity;
import io.botic.response.BenchmarkResult;

import java.util.List;

import static io.botic.legacy.objectify.OfyService.ofy;

@Api(
    name="gaebench",
    version = "v1",
    description = "GAE Bench API",
    defaultVersion = AnnotationBoolean.TRUE
)
public class Cleaner {
    @ApiMethod(name="cleaner.txentity", httpMethod = "GET", path = "cleaner/txentity")
    public BenchmarkResult deleteTxEntities() {
        List<TxEntity> entities = ofy().load().type(TxEntity.class).limit(1500).list();
        int count = entities.size();
        ofy().delete().entities(entities).now();
        return new BenchmarkResult("Deleted.", count);
    }

    @ApiMethod(name="cleaner.singleentity", httpMethod = "GET", path = "cleaner/singleentity")
    public BenchmarkResult deleteSingleEntities() {
        List<SingleEntity> entities = ofy().load().type(SingleEntity.class).limit(1500).list();
        int count = entities.size();
        ofy().delete().entities(entities).now();
        return new BenchmarkResult("Deleted.", count);
    }

    @ApiMethod(name="cleaner.indexedentity", httpMethod = "GET", path = "cleaner/indexedentity")
    public BenchmarkResult deleteIndexedEntities() {
        List<IndexedEntity> entities = ofy().load().type(IndexedEntity.class).limit(1500).list();
        int count = entities.size();
        ofy().delete().entities(entities).now();
        return new BenchmarkResult("Deleted.", count);
    }

    @ApiMethod(name="cleaner.unindexedentity", httpMethod = "GET", path = "cleaner/unindexentity")
    public BenchmarkResult deleteUnindexedEntities() {
        List<UnindexedEntity> entities = ofy().load().type(UnindexedEntity.class).limit(1500).list();
        int count = entities.size();
        ofy().delete().entities(entities).now();
        return new BenchmarkResult("Deleted.", count);
    }

    @ApiMethod(name="cleaner.football", httpMethod = "GET", path = "cleaner/football")
    public BenchmarkResult deleteEverything() {
        int count = 0;
        String text = "";

        List<FlatPlayer> flat = ofy().load().type(FlatPlayer.class).limit(500).list();
        count += flat.size();
        text += "FlatPlayer<" + flat.size() + ">; ";
        ofy().delete().entities(flat).now();

        List<FlatTeam> flatTeamList = ofy().load().type(FlatTeam.class).limit(500).list();
        count += flatTeamList.size();
        text += "FlatTeam<" + flatTeamList.size() + ">; ";
        ofy().delete().entities(flatTeamList).now();

        List<EGPlayer> egPlayers = ofy().load().type(EGPlayer.class).limit(500).list();
        count += egPlayers.size();
        text += "EGPlayer<" + egPlayers.size() + ">; ";
        ofy().delete().entities(egPlayers).now();

        List<EGTeam> egTeamsList = ofy().load().type(EGTeam.class).limit(500).list();
        count += egTeamsList.size();
        text += "EGTeams<" + egTeamsList.size() + ">; ";
        ofy().delete().entities(egTeamsList).now();

        return new BenchmarkResult("Deleted. " + text, count);
    }
}
