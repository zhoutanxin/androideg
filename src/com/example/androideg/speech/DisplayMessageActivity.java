package com.example.androideg.speech;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DisplayMessageActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		// 设置布局
		setContentView(R.layout.activity_display_message);
		
		// 从intent中获取数据
		Intent intent = getIntent();
		ArrayList<String> strList = intent.getStringArrayListExtra(EgSpeechActivity.EXTRA_MESSAGE);

		//找到list，然后赋值
		ListView list = (ListView) findViewById(R.id.list);
		list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strList));
    }

}
