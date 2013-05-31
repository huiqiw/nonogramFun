package uw.cse403.nonogramfun;

/**
 * CSE 403 AA
 * Project Nonogram: Frontend
 * @author  Xiaoxia Jian, Huiqi Wang, Renhao Xie, Alan Loh
 * @version v1.0, University of Washington 
 * @since   Spring 2013 
 */

import java.io.IOException;
import java.net.UnknownHostException;

import org.json.JSONException;

import uw.cse403.nonogramfun.network.NonoClient;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;


public class CreateGameScreen extends Activity implements OnClickListener{
	private Button[][] buttons;
	protected int dimension;
	private boolean test;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_game_screen);

		Bundle bundle = getIntent().getExtras();
		dimension = bundle.getInt("size");
		test = bundle.getBoolean("test");
		
		if(dimension == 5){
			setTitle("Small");
		}else if (dimension == 10){
			setTitle("Medium");
		}else if (dimension == 14){
			setTitle("Large");
		}
		
		// Initialize a 2D array of buttons. First coordinate is row #, second coordinate is column # (so y,x instead of x,y)
		buttons = new Button[dimension][dimension];

		TableLayout layout = new TableLayout (this);
		layout.setLayoutParams( new TableLayout.LayoutParams() );
		//layout.setPadding(50,50,50,50);
		layout = createTable(layout);
		
		Button submitButton = (Button) findViewById(R.id.creategamesubmit);
		submitButton.setOnClickListener(new SubmitButtonListener()); 
		
		//super.setContentView(layout); 
		HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.nonogram_gameboard);
		scrollView.addView(layout);	
	}

    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onClick(View arg0) {
		if(((Cell)arg0).getSelectVal()){
			((Cell) arg0).setText("");	
		}
		else{
			((Cell) arg0).setText("X");
			((Cell) arg0).setTextColor(Color.BLUE);
		}
			
		((Cell) arg0).setSelectVal();
	}
	
	
	private TableLayout createTable(TableLayout layout) {
		
		for (int i = 0; i < dimension; i++) {
			TableRow tr = new TableRow(this);
			for (int j = 0; j < dimension; j++) {
	        	buttons[i][j] = new Cell(this);
	        	
	        	if (test){
	        		//test scenario
	        		buttons[i][j].setText(Integer.toString(i)+""+Integer.toString(j));
	        	}
	        	
	        	if((i % 2 == j % 2)){
	        		buttons[i][j].setBackgroundColor(Color.LTGRAY);
	        	}
	        	else{
	        		buttons[i][j].setBackgroundColor(Color.WHITE);
	        	}
	        	buttons[i][j].setOnClickListener(this);
	        	tr.addView(buttons[i][j],50,50);
			}
			layout.addView(tr);
		}
		
		return layout;
	}
	
	public void returnMainScreen(View view) {
		Intent i = new Intent(this, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
	
	private class SubmitButtonListener implements OnClickListener {
		@Override
		public void onClick(View v) {

			final Integer[][] gameArray = new Integer[dimension][dimension];
			boolean isEmpty = true;

			for (int i = 0; i < dimension; i++) {
				for (int j = 0; j < dimension; j++) {
					if(buttons[i][j].getText().toString().equalsIgnoreCase("x")){
						gameArray[i][j] = Color.WHITE;
						isEmpty = false;
					} else {
						gameArray[i][j] = Color.BLACK;
					}
				}
			}

			if ( isEmpty ) {
				showAlertDialog(v, "Error", "Please do not submit an empty game");
			} else {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							NonoClient.createPuzzle(gameArray, Integer.valueOf(Color.WHITE), "Puzzle 1");
						} catch (UnknownHostException e) {
							//e.printStackTrace();
						} catch (IOException e) {
							//e.printStackTrace();
						} catch (JSONException e) {
							//e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
				thread.start();
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				showAlertDialog(v, "Submit Success", "Puzzle Created");
			}
			
		}

		
		private void showAlertDialog(final View v, String title, String message){
			AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
			alertDialog.setTitle(title);
			alertDialog.setMessage(message);
			// -1 = BUTTON_POSITIVE = a positive button?
			alertDialog.setButton(-1, "OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					returnMainScreen(v);
				}
			});
			alertDialog.show();
		}
		
	}
	
	public int getDimension(){
		return dimension;
	}
}