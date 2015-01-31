package com.example.nicolascageapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class StatsClearPrefsDialogFragment extends DialogFragment {

	public interface StatsClearPrefsDialogFragmentListener {
		public void onDialogPositiveClick(DialogFragment dialog);
	}
	
	StatsClearPrefsDialogFragmentListener mListener;
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		try {
			mListener = (StatsClearPrefsDialogFragmentListener) activity;
		} catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString() +
					" must implement StatsClearPrefsDialogFragmentListener");
		}
	}
	
	@Override 
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Are you sure you want to reset stats?");
		builder.setPositiveButton(R.string.std_yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mListener.onDialogPositiveClick(StatsClearPrefsDialogFragment.this);
			}
		});
		builder.setNegativeButton(R.string.std_no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// do nothing!
				
			}
		});
		
		
		return builder.create();
	}
}
