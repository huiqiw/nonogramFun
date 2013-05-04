package uw.cse403.nonogramfun;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class CreateGameMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_game_menu, menu);
		return true;
	}

	
	public void CreateGameSmallScreen(View view) {
		Intent i = new Intent(this, CreateGameScreen.class);
		i.putExtra("size", 5);
		startActivity(i);
	}

	public void CreateGameMediumScreen(View view) {
		Intent i = new Intent(this, CreateGameScreen.class);
		i.putExtra("size", 10);
		startActivity(i);
	}

	public void CreateGameLargeScreen(View view) {
		Intent i = new Intent(this, CreateGameScreen.class);
		i.putExtra("size", 14);
		startActivity(i);
	}

}
