package com.mytodo.myrestfulwsapp;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;


public class MainActivity extends ListActivity {
	Intent intent;
	TextView taskId;
	TaskRESTfulDAO taskRESTfulDAO = new TaskRESTfulDAO(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ArrayList<HashMap<String, String>> tasksList =  taskRESTfulDAO.getAllTasks();
				
		if(tasksList.size()!=0) {
			ListView lv = getListView();
			lv.setOnItemClickListener(new OnItemClickListener() {
				  @Override 
				  public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					  taskId = (TextView) view.findViewById(R.id.taskId);
					  String valTaskId = taskId.getText().toString();					  
					  Intent  objIndent = new Intent(getApplicationContext(),EditTask.class);
					  objIndent.putExtra("taskId", valTaskId); 
					  startActivity(objIndent); 
				  }
			}); 
			ListAdapter adapter = new SimpleAdapter( MainActivity.this,tasksList, R.layout.view_task_entry, new String[] { "taskId","taskName","taskStatus"}, new int[] {R.id.taskId, R.id.taskName,R.id.taskStatus}); 
			setListAdapter(adapter);
		}
	}
	public void showAddForm(View view) {
		Intent objIntent = new Intent(getApplicationContext(), AddNewTaskActivity.class);
		startActivity(objIntent);
	}
}
