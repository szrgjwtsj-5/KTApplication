package test;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by whx on 2017/8/3.
 */

public class JavaTest {
    private int a = 1;

    class Hhh {
        private int b = 2;
        private void func() {
            System.out.println(a);
        }
    }
    static class Lll {
        private int c = 3;
        private void func() {
            System.out.println(new JavaTest().a);
        }
    }
    public void test() {
        System.out.println(new Hhh().b);

    }
}
