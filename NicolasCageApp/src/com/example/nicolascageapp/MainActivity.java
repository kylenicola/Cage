package com.example.nicolascageapp;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.animation.Animation;

import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	static final int DIALOG_ABOUT_ID = 0;
	static final int DIALOG_HELP_ID = 1;
	static final int DIALOG_QUIT_ID = 2;
	static final int DIALOG_SETTINGS_ID = 3;

	static final int FADE_IN = 4;
	static final int FADE_OUT = 5;

	private final String TAG = "MainActivity";
	public boolean clicked = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);



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
					shake.setAnimationListener(new AnimationListener(){

						@Override
						public void onAnimationStart(Animation animation) {
							
						}

						@Override
						public void onAnimationEnd(Animation animation) {
							Handler h = new Handler();
							h.postDelayed(new Runnable(){

								@Override
								public void run() {
									Intent intent = new Intent(getBaseContext(), RattleTheCage.class);
									startActivity(intent);
								}

							}, 250);
							
						}

						@Override
						public void onAnimationRepeat(Animation animation) {
							
						}
						
					});
					cageFace.startAnimation(shake);
					


				}
				return false;
			}
		});

		final TextView abcsWithNicText = (TextView) findViewById(R.id.abcswithnic);
		final RelativeLayout mainMenuTop = (RelativeLayout) findViewById(R.id.main_menu_top_layout);

		TextView letterA = (TextView) findViewById (R.id.letterA);
		TextView letterB = (TextView) findViewById (R.id.letterB);
		TextView cageText = (TextView) findViewById (R.id.cageText);
		ImageView cageBanana = (ImageView) findViewById(R.id.cageBanana);

		ObjectAnimator animA = getAlphaAnimator(letterA, FADE_IN, -1);
		ObjectAnimator animAFade = getAlphaAnimator(letterA, FADE_OUT, -1);
		ObjectAnimator animB = getAlphaAnimator(letterB, FADE_IN, -1);
		ObjectAnimator animBFade = getAlphaAnimator(letterB, FADE_OUT, -1);
		ObjectAnimator animCageText = getAlphaAnimator(cageText, FADE_IN, -1);
		ObjectAnimator animCageBanana = getAlphaAnimator(cageBanana, FADE_IN, -1);

		final AnimatorSet animSet = new AnimatorSet();
		animSet.setInterpolator(new LinearInterpolator());
		animSet.play(animA).before(animAFade);
		animSet.play(animAFade).before(animB);
		animSet.play(animB).before(animBFade);
		animSet.play(animBFade).before(animCageText);
		animSet.play(animCageText).with(animCageBanana);
		animSet.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(getBaseContext(), AbcsWithNic.class);
						startActivity(intent);
					}
				}, 750);

			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}
		});

		abcsWithNicText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!clicked)
				{
					clicked = true;

					cageFace.setAlpha(0);
					abcsWithNicText.setTextColor(android.graphics.Color.YELLOW);
					animSet.start();

				}
				return true;
			}
		});


		final ImageView blueDog = (ImageView) findViewById(R.id.blueDog);

		final TextView cageCluesText = (TextView) findViewById(R.id.cageclueswhatdidhelose);
		cageCluesText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!clicked)
				{
					clicked = true;

					// handle text
					cageCluesText.setTextColor(android.graphics.Color.YELLOW);
					
					// place dog
					blueDog.setX(mainMenuTop.getWidth());
					
					// setting up animations.  First is the dog running across screen
					// second is cage turning into rageface and then chasing after dog.
					ObjectAnimator anim = ObjectAnimator.ofFloat(blueDog, "translationX", -mainMenuTop.getWidth() - blueDog.getWidth());
					anim.setDuration(2500);
					anim.addListener(new Animator.AnimatorListener() {

						@Override
						public void onAnimationStart(Animator animation) {


						}

						@Override
						public void onAnimationRepeat(Animator animation) {

						}

						@Override
						public void onAnimationEnd(Animator animation) {
							cageFace.setImageResource(R.drawable.main_menu_rage_face);

						}

						@Override
						public void onAnimationCancel(Animator animation) {

						}
					});
					
					ObjectAnimator anim2 = ObjectAnimator.ofFloat(cageFace, "translationX", -cageFace.getX() - cageFace.getWidth());
					anim2.setStartDelay(500);
					anim2.setDuration(750);
					anim2.addListener(new Animator.AnimatorListener() {

						@Override
						public void onAnimationStart(Animator animation) {

						}

						@Override
						public void onAnimationRepeat(Animator animation) {

						}

						@Override
						public void onAnimationEnd(Animator animation) {
							Handler handler = new Handler();
							handler.postDelayed(new Runnable() {
								@Override
								public void run() {
									Intent intent = new Intent(getBaseContext(), CageCluesVid.class);
									startActivity(intent);
								}
							}, 250);

						}

						@Override
						public void onAnimationCancel(Animator animation) {

						}
					});

					// animSet for two animations
					AnimatorSet animSet = new AnimatorSet();
					animSet.setInterpolator(new LinearInterpolator());
					animSet.play(anim).before(anim2);
					animSet.start();
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
		.setPositiveButton(R.string.std_yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				MainActivity.this.finish();
			}
		})
		.setNegativeButton(R.string.std_no, null);   
		return builder.create();
	}

	private Dialog createSettingsDialog(Builder builder) {


		builder.setPositiveButton("Ok", null).setMultiChoiceItems(R.array.Settings, null, new DialogInterface.OnMultiChoiceClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
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

	// gets alpha objectanimator
	private ObjectAnimator getAlphaAnimator(View v, int fade, int delay)
	{
		ObjectAnimator anim;
		if(fade == FADE_IN)
		{
			anim = ObjectAnimator.ofFloat(v, "alpha", 0, 1);
		}
		else
		{
			anim = ObjectAnimator.ofFloat(v, "alpha", 1, 0);
		}
		anim.setDuration(900);
		if(delay > 0)
		{
			anim.setStartDelay(delay);
		}
		return anim;
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
			Log.d(TAG, "settings clicked");
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
			Log.d(TAG, "help clicked");
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


