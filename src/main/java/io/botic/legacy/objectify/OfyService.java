package io.botic.legacy.objectify;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import io.botic.casestudy.model.*;
import io.botic.legacy.objectify.eg.EGPlayer;
import io.botic.legacy.objectify.flat.FlatPlayer;
import io.botic.legacy.objectify.generic.GenericTestEntity;
import io.botic.legacy.objectify.simple.SingleEntity;
import io.botic.legacy.objectify.simple.UnindexedEntity;
import io.botic.legacy.objectify.eg.EGTeam;
import io.botic.legacy.objectify.flat.FlatTeam;
import io.botic.legacy.objectify.simple.IndexedEntity;
import io.botic.legacy.objectify.tx.TxEntity;

/**
 * Like recommended in the Objectify best practices.
 */
public class OfyService {

    static {
        // Final benchmark entities
        factory().register(LightweightEntity.class);
        factory().register(HeavyweightEntity.class);
        factory().register(GroupedEntity.class);
        factory().register(RetrievableEntity.class);
        factory().register(NamedKeyEntity.class);
        factory().register(TreeEntity.class);

        // Legacy entities
        factory().register(FlatTeam.class);
        factory().register(FlatPlayer.class);
        factory().register(EGTeam.class);
        factory().register(EGPlayer.class);
        factory().register(IndexedEntity.class);
        factory().register(UnindexedEntity.class);
        factory().register(SingleEntity.class);
        factory().register(TxEntity.class);
        factory().register(GenericTestEntity.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
