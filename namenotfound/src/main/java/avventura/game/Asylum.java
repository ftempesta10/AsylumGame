package game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

import engine.GameDescription;
import engine.ParserOutput;

public class Asylum extends GameDescription {
	private static Object lock = new Object();
	private static Manager frame;
	private String player;
	HandleDB db;
	@Override
	public void init() throws Exception {
		// TODO Auto-generated method stub
		frame = new Manager();
		Thread t= new Thread( new Runnable() {
			@Override
			public void run() {
				synchronized(lock) {
	                while (frame.isVisible())
	                    try {
	                        lock.wait();
	                    } catch (InterruptedException e) {
	                        e.printStackTrace();
	                    }
	            }
	        }

			});
		frame.addWindowListener(new WindowAdapter() {

	        @Override
	        public void windowClosing(WindowEvent arg0) {
	            synchronized (lock) {
	                frame.setVisible(false);
	                lock.notify();
	            }
	        }

	    });
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosed(e);
				synchronized (lock) {
	                frame.setVisible(false);
	                lock.notify();
	            }
			}

		});
		t.start();
		db = new HandleDB();
		t.join();
		player=frame.getPlayer();
		if(frame.getSave()==null) {
			initNew();
		}else {
			initFromSave((Asylum) frame.getSave());
		}
	}

	private void initNew() {

	}

	private void initFromSave(Asylum save) {

	}

	@Override
	public void nextMove(ParserOutput p, PrintStream out) {
		// TODO Auto-generated method stub

	}

}
