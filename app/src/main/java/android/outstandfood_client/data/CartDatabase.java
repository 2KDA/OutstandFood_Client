package android.outstandfood_client.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {CartModel.class},version = 1)
public abstract class CartDatabase extends RoomDatabase {
    public static final String DATABASE = "CART_FOOD";

    public static CartDatabase Instance;

    public static synchronized CartDatabase getInstance(Context context) {
        if (Instance == null) {
            Instance = Room.databaseBuilder(context.getApplicationContext(), CartDatabase.class, DATABASE)
                    .allowMainThreadQueries()
                    .build();
        }
        return Instance;
    }

    public abstract CartDao cartDao();
}
