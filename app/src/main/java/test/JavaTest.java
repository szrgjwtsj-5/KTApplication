package test;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by whx on 2017/8/3.
 */

public class JavaTest {
    public void test() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("", null);
    }
}
