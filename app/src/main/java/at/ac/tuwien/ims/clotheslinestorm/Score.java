package at.ac.tuwien.ims.clotheslinestorm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * we need this class for creating a new Score
 * @author Stefan Tobisch
 **/
@Entity(tableName = "score")
public class Score {

    @PrimaryKey(autoGenerate = true)

    private long id;

    private String username;

    private int score;

    public Score() {
    }

    public Score(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
