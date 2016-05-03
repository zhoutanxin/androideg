package com.example.androideg.speech;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EgSpeechActivity extends Activity implements OnClickListener {
	 public final static String EXTRA_MESSAGE = "com.example.androideg.speech.MESSAGE";
	 
	 private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;  
	 private static final String SPEECH_PROMPT = "请讲话";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		// 检查是否存在recognition activity
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		Button btn = (Button) findViewById(R.id.btn_speek);
		if (activities.size() != 0) {
			//如果存在recognition activity则为按钮绑定点击事件
			btn.setOnClickListener(this);
		} else {
			// 如果不存在则禁用按钮
			btn.setEnabled(false);
			btn.setText("语音识别不可用");
		}

    }

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btn_speek){
			startVoiceRecognitionActivity();
		}
	}
	
	/**
	 * 启动语音识别activity，接收用户语音输入
	 */
	private void startVoiceRecognitionActivity(){
		//通过Intent传递语音识别的模式  
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);  
        //语言模式：自由形式的语音识别  
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        //提示语音开始  
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, SPEECH_PROMPT);  
        //开始执行我们的Intent、语音识别  并等待返回结果
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 确定是语音识别activity返回的结果
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE){
			// 确定返回结果的状态是成功
			if (resultCode == RESULT_OK){
				// 获取语音识别结果  
	            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);  
	            startDisplayMessageActivity(matches);
			}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * 启动展示activity，显示识别结果
	 * @param message
	 */
	private void startDisplayMessageActivity(ArrayList<String> strList){
		Intent intent = new Intent(this, DisplayMessageActivity.class);
    	intent.putExtra(EXTRA_MESSAGE, strList);
    	startActivity(intent);
	}
}
