package util;

import java.lang.reflect.Constructor;

import scala.collection.immutable.Stream.Cons;

/**
 *Messieurs Time
 */
public class Stopwatch {
	private int iterations = 0;
	private int intervall = 100;
	private boolean start = false;
	private Thread timer;
/**
 * {@link Constructor} of {@link Stopwatch} Class
 * @param intervall Quality of Measurement  
 * 1=very good 
 * 1000=not so good  
 */
	public Stopwatch(int intervall) {
		this.intervall = intervall;
		
	}
/**
 * Starts the Stopwatch
 */
	public void start() {
		start = true;
		this.timer = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (start) {
					try {
						Thread.sleep(intervall);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						return;
					}
					iterations++;
				}
			}
		});
		timer.start();
	}
/*
 * Stops the Stopwatch
 */
	public int stop() {
		start = false;
		timer=null;
		int result = iterations * intervall;
		return result;
	}
/**
 * @return past time in ms
 */
	public int getresult() {
		int result = iterations * intervall;
		return result;

	}
	/**
	 * Resets Stopwatch
	 */
	public void reset(){
		start=false;
		iterations=0;
		
	}
}
