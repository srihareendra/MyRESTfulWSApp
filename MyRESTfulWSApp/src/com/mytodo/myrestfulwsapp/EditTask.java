package com.mytodo.myrestfulwsapp;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class EditTask extends Activity {

	EditText taskName;
	EditText taskStatus;
	TaskRESTfulDAO taskRESTfulDAO = new TaskRESTfulDAO(this);
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_edit_task);
			taskName = (EditText) findViewById(R.id.taskName);
			taskStatus= (EditText) findViewById(R.id.taskStatus);
			
			Intent objIntent = getIntent();
			String taskId = objIntent.getStringExtra("taskId");
			Log.d("Reading: ", "Reading all tasks..");
			HashMap<String, String> taskList = taskRESTfulDAO.getTaskInfo(taskId);
			Log.d("taskName",taskList.get("taskName"));
			if(taskList.size()!=0) {
				taskName.setText(taskList.get("taskName"));
				taskStatus.setText(taskList.get("taskStatus"));
			}
	    }
	public void editTask(View view) {
		HashMap<String, String> queryValues =  new  HashMap<String, String>();
		taskName = (EditText) findViewById(R.id.taskName);
		taskStatus = (EditText) findViewById(R.id.taskStatus);
		Intent objIntent = getIntent();
		String taskId = objIntent.getStringExtra("taskId");
		queryValues.put("taskId", taskId);
		queryValues.put("taskName", taskName.getText().toString());
		queryValues.put("taskStatus", taskStatus.getText().toString());
		
		taskRESTfulDAO.updateTask(queryValues);
		this.callHomeActivity(view);
		
	}
	public void removeTask(View view) {
		Intent objIntent = getIntent();
		String taskId = objIntent.getStringExtra("taskId");
		taskRESTfulDAO.deleteTask(taskId);
		this.callHomeActivity(view);
		
	}
	public void callHomeActivity(View view) {
		Intent objIntent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(objIntent);
	}
}
