package at.ac.tuwien.ims.clotheslinestorm;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;
/**
 * Inferface for functionality of using the database
 * @author Stefan Tobisch
 **/


/*
 * defines the sql which are used
 * */
@Dao
public interface ScoreDao {

    /*
    * shows all score-entries ordered from high to low
    * */
    @Query("SELECT * FROM Score ORDER BY score DESC")
    List<Score> findAllScoreDesc();

    @Delete
    void deleteScore(Score score);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Score score);
}
