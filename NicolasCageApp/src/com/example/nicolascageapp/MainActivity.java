package com.example.nicolascageapp;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.view.animation.Animation;

import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Build;

import java.util.Timer;

public class MainActivity extends Activity {

	static final int DIALOG_ABOUT_ID = 0;
	static final int DIALOG_HELP_ID = 1;
	static final int DIALOG_QUIT_ID = 2;
	static final int DIALOG_SETTINGS_ID = 3;

	private final String TAG = "MainActivity";
	public boolean clicked = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);



		final LinearLayout main_menu_layout = (LinearLayout) findViewById(R.id.main_menu_layout);

		final ImageView cageFace = (ImageView) findViewById(R.id.cageFace);
		final TextView rattleTheCageText = (TextView) findViewById(R.id.rattlethecage);
		rattleTheCageText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!clicked)
				{
					clicked = true;
					rattleTheCageText.setTextColor(android.graphics.Color.YELLOW);
					Animation shake = AnimationUtils.loadAnimation(getBaseContext(), R.anim.main_rattlecage_face_shake);

					cageFace.startAnimation(shake);
					Handler h = new Handler();
					h.postDelayed(new Runnable(){

						@Override
						public void run() {
							Intent intent = new Intent(getBaseContext(), RattleTheCage.class);
							startActivity(intent);
						}

					}, 2025);


				}
				return false;
			}
		});

		final TextView abcsWithNicText = (TextView) findViewById(R.id.abcswithnic);
		abcsWithNicText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!clicked)
				{
					clicked = true;
					abcsWithNicText.setTextColor(android.graphics.Color.YELLOW);
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent intent = new Intent(getBaseContext(), AbcsWithNic.class);
							startActivity(intent);
						}
					}, 1000);
				}
				return true;
			}
		});

		final RelativeLayout mainMenuTop = (RelativeLayout) findViewById(R.id.main_menu_top_layout);
		final ImageView blueDog = new ImageView(getBaseContext());
		blueDog.setImageResource(R.drawable.blue_dog);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		//params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		blueDog.setX(-500);		
		Log.d(TAG, "mainMenuTop width: " + String.valueOf(mainMenuTop.getMeasuredWidth()));
		//		/blueDog.setPadding(1, 1, 1, 1);
		mainMenuTop.addView(blueDog, params);
		mainMenuTop.invalidate();

		final AnimationListener animListener = new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {


			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

		};
		final TextView cageCluesText = (TextView) findViewById(R.id.cageclueswhatdidhelose);
		cageCluesText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!clicked)
				{
					clicked = true;

					final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.blues_clues_theme);
					//mp.start();				
					blueDog.setX(mainMenuTop.getWidth());
					ObjectAnimator anim = ObjectAnimator.ofFloat(blueDog, "translationX", -mainMenuTop.getWidth() - blueDog.getWidth());
					anim.setDuration(2500);
					ObjectAnimator anim2 = ObjectAnimator.ofFloat(cageFace, "translationX", -cageFace.getX() - cageFace.getWidth());
					//anim.setStartDelay(3500/2+200);
					anim.addListener(new Animator.AnimatorListener() {

						@Override
						public void onAnimationStart(Animator animation) {


						}

						@Override
						public void onAnimationRepeat(Animator animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animator animation) {
							cageFace.setImageResource(R.drawable.main_menu_rage_face);
							//
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationCancel(Animator animation) {
							// TODO Auto-generated method stub

						}
					});
					anim2.setStartDelay(500);
					anim2.setDuration(750);
					anim2.addListener(new Animator.AnimatorListener() {

						@Override
						public void onAnimationStart(Animator animation) {

						}

						@Override
						public void onAnimationRepeat(Animator animation) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationEnd(Animator animation) {
							mp.stop();
							Handler handler = new Handler();
							handler.postDelayed(new Runnable() {
								@Override
								public void run() {
									Intent intent = new Intent(getBaseContext(), CageCluesVid.class);
									startActivity(intent);
								}
							}, 1000);
							// TODO Auto-generated method stub

						}

						@Override
						public void onAnimationCancel(Animator animation) {
							// TODO Auto-generated method stub

						}
					});
					anim.setInterpolator(new LinearInterpolator());
					anim2.setInterpolator(new LinearInterpolator());
					AnimatorSet animSet = new AnimatorSet();
					animSet.play(anim).before(anim2);
					animSet.start();




					// handle text
					cageCluesText.setTextColor(android.graphics.Color.YELLOW);
				}
				return true;
			}
		});
	}

	//	public void onClickSettings(View view) {
	//		Intent intent = new Intent(this, Settings.class);
	//		startActivity(intent);
	//	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return super.onCreateOptionsMenu(menu);
	}

	private Dialog createAboutDialog(Builder builder) {
		Context context = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.main_action_about, null); 		
		builder.setView(layout);
		builder.setPositiveButton("OK", null);	
		return builder.create();
	}

	private Dialog createHelpDialog(Builder builder) {
		Context context = getApplicationContext();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.main_action_help, null); 		
		builder.setView(layout);
		builder.setPositiveButton("OK", null);	
		return builder.create();
	}

	private Dialog createQuitDialog(Builder builder) {
		builder.setMessage(R.string.quit_question).setCancelable(false)
		.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				MainActivity.this.finish();
			}
		})
		.setNegativeButton(R.string.no, null);   
		return builder.create();
	}

	private Dialog createSettingsDialog(Builder builder) {


		builder.setPositiveButton("Ok", null).setMultiChoiceItems(R.array.Settings, null, new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				// TODO Auto-generated method stub
				AlertDialog.Builder nopeBuilder = new AlertDialog.Builder(MainActivity.this);
				Dialog nopeDialog = null;
				if (isChecked)
				{
					nopeBuilder.setMessage("NICOLAS CAGE SLOWS FOR NO MAN").setPositiveButton("my bad", null);
					nopeDialog = nopeBuilder.create();
					nopeDialog.show();
				}
			}
		});
		return builder.create();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		switch(id)
		{
		case DIALOG_ABOUT_ID:
			dialog = createAboutDialog(builder);
			break;
		case DIALOG_HELP_ID:
			dialog = createHelpDialog(builder);
			break;
		case DIALOG_QUIT_ID:
			dialog = createQuitDialog(builder);
			break;
		case DIALOG_SETTINGS_ID:

			dialog = createSettingsDialog(builder);
			break;

		}
		return dialog;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Log.d("butthole", "settings clicked");
			showDialog(DIALOG_SETTINGS_ID);
			return true;
		}
		else if(id == R.id.action_about)
		{
			showDialog(DIALOG_ABOUT_ID);
			return true;
		}
		else if(id == R.id.action_help)
		{
			Log.d("butthole", "help clicked");
			showDialog(DIALOG_HELP_ID);
			return true;
		}
		else if(id == R.id.action_exit)
		{
			showDialog(DIALOG_QUIT_ID);
			return true;
		}
		else if(id == R.id.action_scores)
		{
			Intent intent = new Intent(this, ScoreMenu.class);
			startActivity(intent);
			// Create intent to go to scores menu
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}		


