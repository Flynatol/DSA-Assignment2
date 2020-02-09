package dsa_assignment2;

import java.util.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * A Drone class to simulate the decisions and information collected by a drone
 * on exploring an underground maze.
 * 
 */
public class Drone implements DroneInterface
{
	private static final Logger logger     = Logger.getLogger(Drone.class);
	
	public String getStudentID()
	{
		//change this return value to return your student id number
		return "2029844";
	}

	public String getStudentName()
	{
		//change this return value to return your name
		return "Flynn McGouran-Collins";
	}

	/**
	 * The Maze that the Drone is in
	 */
	private Maze                maze;

	/**
	 * The stack containing the portals to backtrack through when all other
	 * doorways of the current chamber have been explored (see assignment
	 * handout). Note that in Java, the standard collection class for both
	 * Stacks and Queues are Deques
	 */
	private Deque<Portal>       visitStack = new ArrayDeque<>();

	/**
	 * The set of portals that have been explored so far.
	 */
	private Set<Portal>         visited    = new HashSet<>();

	/**
	 * The Queue that contains the sequence of portals that the Drone has
	 * followed from the start
	 */
	private Deque<Portal>       visitQueue = new ArrayDeque<>();

	/**
	 * This constructor should never be used. It is private to make it
	 * uncallable by any other class and has the assert(false) to ensure that if
	 * it is ever called it will throw an exception.
	 */
	@SuppressWarnings("unused")
	private Drone()
	{
		assert (false);
	}

	/**
	 * Create a new Drone object and place it in chamber 0 of the given Maze
	 * 
	 * @param maze
	 *            the maze to put the Drone in.
	 */
	public Drone(Maze maze)
	{
		this.maze = maze;
	}

	/* 
	 * @see dsa_assignment2.DroneInterface#searchStep()
	 */
	@Override
	public Portal searchStep()
	{
		for (int i = 0; i < maze.getNumDoors(); i++) {

			Portal testPortal = new Portal(maze.getCurrentChamber(), i);

			if (!visited.contains(testPortal)) {
				return travelAdd(i, true);
			}

	 	}

		if (visitStack.isEmpty()){
			return null;
		}

		return travelAdd(visitStack.pop().getDoor(), false);

	}

	private Portal travelAdd(int i, boolean onStack){
		Portal forward = new Portal(maze.getCurrentChamber(), i);
		Portal backward = maze.traverse(i);

		if (onStack) {
			visitStack.push(backward);
		}

		visitQueue.add(forward);
		visitQueue.add(backward);


		visited.add(forward);
		visited.add(backward);

		return backward;
	}

	/* 
	 * @see dsa_assignment2.DroneInterface#getVisitOrder()
	 */
	@Override
	public Portal[] getVisitOrder()
	{
		return visitQueue.toArray(new Portal[0]);
	}

	/*
	 * @see dsa_assignment2.DroneInterface#findPathBack()
	 */
	@Override
	public Portal[] findPathBack()
	{
		Deque<Portal> tempStack = new ArrayDeque<>();
		ArrayList<Portal> output = new ArrayList<>();
		tempStack.addAll(visitStack);
		Portal tempPortal;

		HashMap<Integer, Portal> map = new HashMap<Integer, Portal>();

		while (!tempStack.isEmpty()) {
			tempPortal = tempStack.pop();
			map.put(tempPortal.getChamber(), tempPortal);
		}

		tempStack.addAll(visitStack);

		while (!tempStack.isEmpty()) {
			tempPortal = tempStack.pop();
			if (tempPortal.getChamber() == 0) {
				break;
			}
			if (tempPortal.equals(map.get(tempPortal.getChamber()))) {
				logger.log(Level.ALL, tempPortal.toString());
				output.add(tempPortal);
			}
		}

		return output.toArray(new Portal[0]);
	}

}
