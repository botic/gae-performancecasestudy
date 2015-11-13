package io.botic.legacy;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import io.botic.legacy.objectify.generic.GenericTestEntity;
import io.botic.response.ResultListTimedBenchResult;
import io.botic.response.SimpleResponse;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static io.botic.legacy.objectify.OfyService.ofy;

@Api(
    name="gaebench",
    version = "v1",
    description = "GAE Bench API",
    defaultVersion = AnnotationBoolean.TRUE
)
public class ObjectifyReadBench {
    private final Logger logger = Logger.getLogger(ObjectifyBench.class.getName());

    static {
        ofy();
    }

    @ApiMethod(name="readbench.read.range", httpMethod = "GET", path="readbench/range")
    public ResultListTimedBenchResult readRange(@Named("start") String start, @Named("end") String end) {
        ResultListTimedBenchResult result = new ResultListTimedBenchResult("readBench - range");

        // Query operations are not cached!
        List<GenericTestEntity> results = ofy().load().type(GenericTestEntity.class)
            .filter("suffixedTitle >=", start)
            .filter("suffixedTitle <=", end).list();
        result.setResults(results);

        return result;
    }

    @ApiMethod(name="readbench.read.rawTitle", httpMethod = "GET", path="readbench/rawTitle")
    public ResultListTimedBenchResult readBatch(@Named("rawTitles") String rawTitles) {
        String[] titles = rawTitles.split(",");

        if (titles.length > 500) {
            return new ResultListTimedBenchResult("Invalid call! Maximum is 500 rawTitles!");
        }

        ResultListTimedBenchResult result = new ResultListTimedBenchResult("readBench - batch");

        // Query operations are not cached!
        List<GenericTestEntity> results = ofy().load().type(GenericTestEntity.class).filter("rawTitle IN", titles).list();
        result.setResults(results);

        return result;
    }

    @ApiMethod(name="readbench.create", httpMethod = "POST", path="readbench/create")
    public SimpleResponse createEntity(@Named("start") int start, @Named("count") int count) {
        if (count > 1000) {
            return new SimpleResponse("Maximum count is 1.000!");
        } else if (start < 0) {
            return new SimpleResponse("Start must be >= 0!");
        } else if (start > Integer.MAX_VALUE - count) {
            return new SimpleResponse("Start + Count > Integer.MAX_VALUE!");
        }

        ArrayList<GenericTestEntity> entities = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            entities.add(new GenericTestEntity("" + (start + i)));
        }

        ofy().save().entities((List<GenericTestEntity>) entities).now();
        return new SimpleResponse("Created new " + count + " entities.");
    }
}
