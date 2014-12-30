package com.example.nicolascageapp;

import android.app.Activity;
import android.os.Bundle;

public class SettingsPage extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
	}

}
