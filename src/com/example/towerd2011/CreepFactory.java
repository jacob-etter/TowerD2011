package com.example.towerd2011;


/* Creep Codes:
 * Basic:  0
 * Tank:   1
 * Speedy: 2
 */
public class CreepFactory
{
	
	GameView view;
	public CreepFactory(GameView view)
	{
		this.view = view;
	}
	
	public Creep getCreep(int type, float x, float y, double difficulty)
	{
		Creep ret_creep;
		
		switch(type){
		case 0:
			ret_creep = new CreepSimple(x, y, view, difficulty);
			break;
		case 1:
			ret_creep = new CreepTough(x, y, view, difficulty);
			break;
		case 2:
			ret_creep = new CreepQuick(x, y, view, difficulty);
			break;
		default:
			ret_creep = new CreepSimple(x, y, view, difficulty);
			break;
		}
		
		return ret_creep;
	}
	
	public void addCreep(int type, float x, float y, double difficulty)
	{
		view.getCreeplist().add(getCreep(type, x, y, difficulty));
	}

}
