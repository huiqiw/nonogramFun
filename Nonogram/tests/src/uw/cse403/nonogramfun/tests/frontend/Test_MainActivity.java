package uw.cse403.nonogramfun.tests.frontend;

import org.junit.Test;

import uw.cse403.nonogramfun.*;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;
/**
 * Black Box Tests
 * MainActivity class
 * @author Huiqi Wang
 *
 */
public class Test_MainActivity extends ActivityInstrumentationTestCase2<MainActivity> {
	private Solo solo;
	
	public Test_MainActivity() {
		super(MainActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(),getActivity());
	}

	public void testViewsCreated(){
		assertEquals(true, solo.searchButton("How To Play"));
		assertEquals(true, solo.searchButton("Play A Game"));
		assertEquals(true, solo.searchButton("Create A Game"));
		assertEquals(true, solo.searchButton("Scoreboard"));
	}
	
	@Test
	public void testHowToPlay(){
		solo.assertCurrentActivity("Not MainActivity", MainActivity.class);
		solo.clickOnButton("How To Play");
		solo.assertCurrentActivity("Not HowToPlay", HowToPlay.class);
	}
	
	@Test
	public void testCreateGameMenu(){
		solo.assertCurrentActivity("Not MainActivity", MainActivity.class);
		solo.clickOnButton("Create A Game");
		solo.assertCurrentActivity("Not CreateGame", CreateGameMenu.class);
	}
	
	@Test
	public void testPlayGameMenu(){
		solo.assertCurrentActivity("Not MainActivity", MainActivity.class);
		solo.clickOnButton("Play A Game");
		solo.assertCurrentActivity("Not PlayGame", PlayGameMenu.class);
	}
  
	@Test
	public void testScoreBoard(){
		solo.assertCurrentActivity("Not MainActivity", MainActivity.class);
		solo.clickOnButton("Scoreboard");
		solo.assertCurrentActivity("Not Scoreboard", ScoreBoard.class);
	}
	
	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
