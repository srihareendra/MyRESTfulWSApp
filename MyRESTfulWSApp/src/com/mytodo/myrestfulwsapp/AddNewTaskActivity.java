package com.mytodo.myrestfulwsapp;

import java.util.HashMap;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddNewTaskActivity extends Activity {
	private static final String LOGCAT = null;
	EditText taskName;
	EditText taskStatus;
	TaskRESTfulDAO taskRESTfulDAO = new TaskRESTfulDAO(this);
	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_add_new_task);
	        taskName = (EditText) findViewById(R.id.taskName);
	        taskStatus= (EditText) findViewById(R.id.taskStatus);
	    }
	public void addNewTask(View view) {
		HashMap<String, String> queryValues =  new  HashMap<String, String>();
		
		Log.d(LOGCAT, "addNewTask entered....");
		Log.d(LOGCAT, "taskname= "+taskName.getText().toString());
		queryValues.put("taskName", taskName.getText().toString());
		queryValues.put("taskStatus", taskStatus.getText().toString());
		taskRESTfulDAO.insertTask(queryValues);
		this.callHomeActivity(view);
	}
	public void callHomeActivity(View view) {
		Intent objIntent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(objIntent);
	}	
}
