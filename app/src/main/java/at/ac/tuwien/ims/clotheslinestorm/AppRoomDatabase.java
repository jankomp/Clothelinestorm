package at.ac.tuwien.ims.clotheslinestorm;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * defines all entities and version
 * update version when changes are made
 * @author Tobisch Stefan
 * **/

@Database(entities = {Score.class}, version = 1)

public abstract class AppRoomDatabase extends RoomDatabase{
    public abstract ScoreDao scoreDao();

    private static AppRoomDatabase INSTANCE;

    // Singleton Pattern, only one database is available in the app
    public static AppRoomDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppRoomDatabase.class, "score_database")
                    .build();
        }
        return INSTANCE;
    }
}