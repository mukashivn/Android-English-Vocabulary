package com.core.app.data.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class RealmMigrations implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
       //TODO: Migrate realm
        final RealmSchema schema = realm.getSchema();
        /*
        if (oldVersion == 1) {
            final RealmObjectSchema userSchema = schema.get("UserData");
            userSchema.addField("age", int.class);
        }
        */
    }
}