package com.mytodo.myrestfulwsapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskRESTfulDAO  extends SQLiteOpenHelper {
	private static final String LOGCAT = null;
	public static final String RESTFUL_SERVER_URI = "http://192.168.0.102:8080/RESTfulWebservice";

	public TaskRESTfulDAO(Context applicationcontext) {
        super(applicationcontext, "androidsqlite.db", null, 1);
        Log.d(LOGCAT,"Created");
    }
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		String query;
		query = "CREATE TABLE Tasks ( taskId INTEGER PRIMARY KEY, taskName TEXT,taskStatus TEXT,taskPriority TEXT)";
        database.execSQL(query);
        Log.d(LOGCAT,"Tasks table Created");
	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query;
		query = "DROP TABLE IF EXISTS Tasks";
		database.execSQL(query);
        onCreate(database);
	}
	
	public void insertTask(HashMap<String, String> queryValues) {
		/*SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("taskName", queryValues.get("taskName"));
		values.put("taskStatus", queryValues.get("taskStatus"));
		database.insert("Tasks", null, values);
		database.close();*/
		Log.d(LOGCAT,"insertTask ...taskName= "+queryValues.get("taskName"));
		putJsonData(RESTFUL_SERVER_URI+"/tasks/insert/"+queryValues.get("taskName")+"/desc/"+"/1/"+queryValues.get("taskStatus"));
		
		///tasks/insert/{taskName}/{taskDesc}/{taskPriority}/{taskStatus}
	}
	
	public int updateTask(HashMap<String, String> queryValues) {
		
		int res=0;
		///tasks/{taskId}/{taskStatus}
		
		Log.d(LOGCAT,"updateTask ...id= "+queryValues.get("taskId"));
		putJsonData(RESTFUL_SERVER_URI+"/tasks/"+queryValues.get("taskId")+"/"+queryValues.get("taskStatus"));
		return res;
		/*SQLiteDatabase database = this.getWritableDatabase();	 
	    ContentValues values = new ContentValues();
	    values.put("taskName", queryValues.get("taskName"));
	    values.put("taskStatus", queryValues.get("taskStatus"));
	    return database.update("Tasks", values, "taskId" + " = ?", new String[] { queryValues.get("taskId") });*/
	    //String updateQuery = "Update  words set txtWord='"+word+"' where txtWord='"+ oldWord +"'";
	    //Log.d(LOGCAT,updateQuery);
	    //database.rawQuery(updateQuery, null);
	    //return database.update("words", values, "txtWord  = ?", new String[] { word });

	}
	
	public void deleteTask(String id) {
		Log.d(LOGCAT,"deleteTask ...id= "+id);
		/*SQLiteDatabase database = this.getWritableDatabase();	 
		String deleteQuery = "DELETE FROM  Tasks where taskId='"+ id +"'";
		Log.d("query",deleteQuery);		
		database.execSQL(deleteQuery);*/
		///tasks/delete/{taskId}
		putJsonData(RESTFUL_SERVER_URI+"/tasks/delete/"+id);
	}
	
	public ArrayList<HashMap<String, String>> getAllTasks() {
		ArrayList<HashMap<String, String>> taskList = new ArrayList<HashMap<String, String>>();
		JSONArray jsonArr1=	(JSONArray) getJsonData(RESTFUL_SERVER_URI+"/tasks");		
		jsonArr1.length();
		try {
		for (int i =0;i<jsonArr1.length();i++){
			
				JSONObject jsObj= jsonArr1.getJSONObject(i);
				
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("taskId", jsObj.getString("id"));
	        	map.put("taskName", jsObj.getString("name"));
	        	map.put("taskStatus", jsObj.getString("status"));
	        	
	        	 Log.d("getAllTasks = ","populaing taskList = "+map);	
	        	 taskList.add(map);
			} 
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("getAllTasks = ","taskList = "+taskList);
		/*String selectQuery = "SELECT  * FROM Tasks";
	    SQLiteDatabase database = this.getWritableDatabase();
	    Cursor cursor = database.rawQuery(selectQuery, null);
	    if (cursor.moveToFirst()) {
	        do {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        	map.put("taskId", cursor.getString(0));
	        	map.put("taskName", cursor.getString(1));
	        	map.put("taskStatus", cursor.getString(2));
	        	
	        	 Log.d("getAllTasks = ","adding map = "+map);	
                wordList.add(map);
	        } while (cursor.moveToNext());
	    }
	    Log.d("getAllTasks = ","wordList = "+wordList);	*/
	    // return Task list
	    return taskList;
	}
	
	public Object getJsonData(String url){
		JSONParser jParser =null;
		JSONArray jsonArr=null;
		try {
				jParser = new JSONParser();
				// Getting JSON from URL
				String resp = jParser.makeServiceCall(url,1,null);				
				Log.d("Response_loc: ", "> " + resp);
				jsonArr = new JSONArray(resp);				
				Log.d("JSONArray: ", "> " + jsonArr);
				Log.d("JSONArray length: ", "> " + jsonArr.length());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				return jsonArr;
		
	}
	
public Object putJsonData(String url){
		String resp=null;
		try {
				JSONParser jParser = new JSONParser();
				// Getting JSON from URL
				resp = jParser.makeServiceCall(url,2,null);				
				Log.d("putJsonData : Response_loc: ", "> " + resp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				return resp;	
	}
	
	public HashMap<String, String> getTaskInfo(String id) {
		
		Log.d("getTaskInfo ", "id:> " + id);
		ArrayList<HashMap<String, String>> tasks =getAllTasks();
		HashMap<String, String> taskItem = new HashMap<String, String>();
		Iterator  iter = tasks.iterator();
		while(iter.hasNext()){
			taskItem =(HashMap<String, String>) iter.next();
			Log.d(" getTaskInfo taskList", "id:> " + id);
			String tskId= taskItem.get("taskId");
			if(tskId.equalsIgnoreCase("id")){
				break;
			}
		}
		Log.d("getTaskInfo ", "taskItem:> " + taskItem);
		return taskItem;
		/*HashMap<String, String> wordList = new HashMap<String, String>();
		SQLiteDatabase database = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM Tasks where taskId='"+id+"'";
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
	        do {
					//HashMap<String, String> map = new HashMap<String, String>();
	        	wordList.put("taskName", cursor.getString(1));
	        	wordList.put("taskStatus", cursor.getString(2));
				   //wordList.add(map);
	        } while (cursor.moveToNext());
	    }				    
		return wordList;*/
	}	
}
