/**
 * The class that stores information about the user
 * @author Sean Wiese sean.wiese@colorado.edu
 * Copyright (c) 2011 Sean Wiese
 */
package com.example.towerd2011;

public class User {
	private int money;
	private int score;
	private String username;
	private int lives;
	/**
	 * constructor for user
	 * @param money_starting
	 * @param score_starting
	 * @param gamelives
	 * @param name
	 */
	public User(int money_starting, int score_starting, int gamelives, String name){
		money = money_starting;
		score = score_starting;
		username = name;
		lives = gamelives;
	}
	/**
	 * 
	 * @return
	 */
	public int getMoney(){
		return money;
	}
	/**
	 * 
	 * @param value
	 */
	public void setMoney(int value){
		money = value;
	}
	/**
	 * 
	 * @param value
	 */
	public void incMoney(int value){
		money = money + value;
	}
	/**
	 * 
	 * @param value
	 */
	public void decMoney(int value){
		money = money - value;
	}
	/**
	 * 
	 * @return
	 */
	public int getScore(){
		return score;
	}
	/**
	 * 
	 * @param value
	 */
	public void setScore(int value){
		score = value;
	}
	/**
	 * 
	 * @param value
	 */
	public void incScore(int value){
		score = score + value;
	}
	/**
	 * 
	 * @param value
	 */
	public void decScore(int value){
		score = score - value;
	}
	/**
	 * 
	 * @return
	 */
	public String getUsername(){
		return username;
	}
	/**
	 * 
	 * @param name
	 */
	public void setUsername(String name){
		username = name;
	}
	/**
	 * 
	 * @param value
	 */
	public void decLives(int value){
		lives -= value;
	}
	/**
	 * 
	 * @param value
	 */
	public void incLives(int value){
		lives += value;
	}
	/**
	 * 
	 * @return
	 */
	public int getLives(){
		return lives;
	}
}
