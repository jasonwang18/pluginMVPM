package com.pluginmvpm.sample;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.pluginmvpm.base.BaseLog;
import com.pluginmvpm.base.core.methodcenter.BaseMethodCenter;
import com.pluginmvpm.base.core.methodcenter.MethodCenter;
import com.pluginmvpm.sample.pm.ViewMethodCenter;

import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


        Button test1 = (Button) findViewById(R.id.test1);
        test1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean result = viewMethodCenter.callMethodBoolean("synPresenterMethod3", new Object[]{"1", "2", "3"});

                BaseLog.i("call synmethod result:"+result);

            }
        });

        Button test2 = (Button) findViewById(R.id.test2);
        test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMethodCenter.callMethodVoid("aSynPresenterMethod", new Object[]{"123"});
            }
        });

    }

    private MethodCenter viewMethodCenter;

    protected void init() {

        ViewMethodCenter.setPresenterPath(Constant.PRESENTER_PATH);
        viewMethodCenter = new ViewMethodCenter();

        viewMethodCenter.setCallback(new BaseMethodCenter.Callback() {
            @Override
            public void onCallback(Message msg) {
                BaseLog.i("onCallback arg1:"+msg.arg1+" obj:"+msg.obj);

                if(msg.arg1 == Constant.MessageWhat.MESSAGE_WHAT_PRESENTER_ASYN_METHOD){
                    List<String> result = (List<String>) msg.obj;

                    BaseLog.i("result size:"+result);

                    for(String s:result){
                        BaseLog.i("result:"+s);
                    }

                }


            }
        });

    }

}
