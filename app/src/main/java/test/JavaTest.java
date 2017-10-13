package test;

import com.whx.ktapplication.MyApplication;
import com.whx.ktapplication.data.ListKt;

import org.jetbrains.annotations.Nullable;



/**
 * Created by whx on 2017/8/18.
 */

public class JavaTest {

    @Nullable
    public Object getObject() {
        return "";
    }

    public void method() {
        println(TestKt.topConstValue);
        println(TestKt.getTopValue());

        println(Test.compObjConstValue);
        println(Test.Companion.getCompObjValue());

        println(Test.obj.objConstValue);
        println(Test.obj.INSTANCE.getObjValue());

        MyApplication.instance();

    }
    private void println(Object o) {
        System.out.println(o);
    }
}
