package io.botic.legacy;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Nullable;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.Work;
import io.botic.legacy.utils.KeyUtils;
import io.botic.legacy.objectify.tx.TxEntity;
import io.botic.response.EntityGroupResponse;
import io.botic.response.SimpleResponse;

import javax.inject.Named;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static io.botic.legacy.objectify.OfyService.ofy;

@Api(
    name="gaebench",
    version = "v1",
    description = "GAE Bench API",
    defaultVersion = AnnotationBoolean.TRUE
)
public class EntityGroupTester {

    private static final Logger logger = Logger.getLogger(EntityGroupTester.class.getName());

    static {
        ofy();
    }

    @ApiMethod(name="egtester.create", httpMethod = "POST", path="egtester/create")
    public SimpleResponse createTxEntity(@Named("text") String text, @Named("id") String id) {
        if (id.length() < 3) {
            return new SimpleResponse("ID needs at least 3 characters!");
        }

        Key<TxEntity> key = KeyUtils.splitFullId(id);
        final TxEntity txe;
        if (key.getParent() != null) {
            TxEntity parent = ofy().load().key(Key.create(TxEntity.class, key.getParent().getName())).now();
            if (parent == null) {
                return new SimpleResponse("Parent not found!");
            }
            txe = new TxEntity(id, Ref.create(parent), text);
        } else {
            txe = new TxEntity(id, null, text);
        }

        ofy().save().entity(txe).now();

        return new SimpleResponse("Created: " + txe.toString());
    }

    @ApiMethod(name="egtester.update.single", httpMethod = "POST", path="egtester/update")
    public SimpleResponse updateTxEntity(@Named("text") final String text, @Named("fullId") String fullId,
                                         @Named("penalty") @Nullable final String penalty) {

        final Key<TxEntity> toLoad = KeyUtils.splitFullId(fullId);
        final AtomicInteger attempts = new AtomicInteger(0);

        TxEntity modified = ofy().transact(new Work<TxEntity>() {
            public TxEntity run() {
                logger.info("Attempt #" + attempts.incrementAndGet());
                TxEntity toModify = ofy().load().key(toLoad).now();
                if (toModify != null) {
                    toModify.setText(text);
                    try {
                        if ("on".equals(penalty)) {
                            Thread.sleep(3000);
                        }
                        ofy().save().entity(toModify).now();
                        if ("on".equals(penalty)) {
                            Thread.sleep(3000);
                        }
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }

                return ofy().load().key(toLoad).now();
            }
        });
        if (modified == null) {
            return new SimpleResponse("Found no entity to modify.");
        }

        return new SimpleResponse(attempts.get() + " attempt(s), modified: " + modified.toString());
    }

    @ApiMethod(name="egtester.showgroup", httpMethod = "GET", path="egtester/show")
    public EntityGroupResponse showGroup(@Named("ancestorId") final String ancestorId) {

        final Key<TxEntity> anchestorKey = KeyUtils.splitFullId(ancestorId);
        EntityGroupResponse response = new EntityGroupResponse();

        if (anchestorKey != null) {
            List txEntities = ofy().load().ancestor(anchestorKey).limit(500).list();
            for (Object entity : txEntities) {
                response.getEntities().add(entity.toString());
            }

            response.setText("Loaded " + txEntities.size() + " entities for the ancestor " + anchestorKey.toString());
        } else {
            response.setText("Ancestor for key " + ancestorId + " is null!");
        }

        return response;
    }

    @ApiMethod(name="egtester.showentity", httpMethod = "GET", path="egtester/showentity")
    public EntityGroupResponse showEntity(@Named("id") final String id) {
        final Key<TxEntity> toLoad = KeyUtils.splitFullId(id);
        TxEntity root = ofy().load().key(toLoad).now();
        EntityGroupResponse response = new EntityGroupResponse();
        response.setText("Entity loaded: " + root);
        return response;
    }
}
