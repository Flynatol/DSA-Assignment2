package dsa_assignment2;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
			//logger.log(Level.ALL, maze.getNumDoors());

			//Door to this chamber from ???
			Portal testPortal = new Portal(maze.getCurrentChamber(), i);

			if (!visited.contains(testPortal)) {
				//logger.log(Level.ALL, "visiting: " + testPortal.getChamber());


				Portal destination = maze.traverse(i);

				visitStack.push(destination);

				visitQueue.add(testPortal);
				visitQueue.add(destination);


				visited.add(testPortal);
				visited.add(destination);

				return destination;
			}


	 	}

		if (visitStack.isEmpty()){
			return null;
		}

		return maze.traverse(visitStack.pop().getDoor());



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
		/* WRITE YOUR CODE HERE */
		return null;
	}

}
