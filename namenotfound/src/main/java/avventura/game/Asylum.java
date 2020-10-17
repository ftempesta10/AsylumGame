package game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

import engine.Command;
import engine.CommandType;
import engine.GameDescription;
import engine.Gateway;
import engine.Item;
import engine.ItemContainer;
import engine.ParserOutput;
import engine.Room;
import hashedGraph.WeightedHashedGraph;

public class Asylum extends GameDescription {
	private static Object lock = new Object();
	private static Manager frame;
	private String player;
	HandleDB db;

	//game params
	Integer health, maxMoves;
	Boolean gasVuln, breathedGas;

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
		health = 100;
		gasVuln = true;
		breathedGas = false;
		maxMoves = 3;

		WeightedHashedGraph<Room, Gateway> m;
		Command nord = new Command(CommandType.NORD, "nord");
        nord.setAlias(new String[]{"n", "N", "Nord", "NORD"});
        getCommands().add(nord);
        Command iventory = new Command(CommandType.INVENTORY, "inventario");
        iventory.setAlias(new String[]{"inv", "i", "I"});
        getCommands().add(iventory);
        Command sud = new Command(CommandType.SOUTH, "sud");
        sud.setAlias(new String[]{"s", "S", "Sud", "SUD"});
        getCommands().add(sud);
        Command est = new Command(CommandType.EAST, "est");
        est.setAlias(new String[]{"e", "E", "Est", "EST"});
        getCommands().add(est);
        Command ovest = new Command(CommandType.WEST, "ovest");
        ovest.setAlias(new String[]{"o", "O", "Ovest", "OVEST"});
        getCommands().add(ovest);
        Command end = new Command(CommandType.END, "end");
        end.setAlias(new String[]{"end", "fine", "esci", "muori", "ammazzati", "ucciditi", "suicidati","exit"});
        getCommands().add(end);
        Command look = new Command(CommandType.LOOK_AT, "osserva");
        look.setAlias(new String[]{"guarda", "vedi", "trova", "cerca", "descrivi"});
        getCommands().add(look);
        Command pickup = new Command(CommandType.PICK_UP, "raccogli");
        pickup.setAlias(new String[]{"prendi"});
        getCommands().add(pickup);
        Command open = new Command(CommandType.OPEN, "apri");
        open.setAlias(new String[]{});
        getCommands().add(open);
        Command push = new Command(CommandType.PUSH, "premi");
        push.setAlias(new String[]{"spingi","attiva"});
        getCommands().add(push);
      //Rooms
        Room room1 = new Room("You awake confused in a room...try to remember what brought you here. You have severe pain in your head, something must have hit you. A nauseating stench is in the air ...",
        					   	"There is a corpse, an unmade bed and a key, you can only go south towards the corridor",
        						"Dormitory 1");
        Room room2 = new Room("You can only go back to the hallway",
								"There is only one bad",
								"Dormitory 2");
        Room room3 = new Room("You can only go back to the hallway",
								"A room full of clothes and with a washing machine. Better leave them in their place ...",
								"Laundry");
        Room room4 = new Room("You can only go back to the hallway",
								"There is only one bad",
								"Dormitory 4");
        Room room5 = new Room("You can only go back to the hallway",
								"A room with an unmade bed",
								"Dormitory 5");
        Room room6 = new Room("You can only go back to the hallway",
								"A room with a bed and numerous holes in the wall, who knows how they were made...",
								"Dormitory 6");
        Room room7 = new Room("You can only go back to the hallway",
								"A room with an unmade bed and strange writing on the wall",
								"Dormitory 7");
        Room room8 = new Room("You can only go back to the hallway",
								"A room with an unmade bed",
        						"Dormitory 8");
        Room hallway = new Room(" From the hallway you can enter room 2, 5, 6 or continue down the hallway towards a door from which monstrous voices are coming ...",
								" A macabre hallway adorned with parts of the human body along the walls",
        						"Hallway 1");
        Room hallway2 = new Room("You can go back down the hallway or enter room 3, 4, 7, 8 or continue down the hallway",
								"A hallway with numerous traces of blood. Did someone drag themselves trying to escape?",
        						"Hallway 2");
        Room hallway3 = new Room("You can go back down the hallway, enter in the bathroom or take the stairs",
								"An hallway with paintings depicting skeletons in daily actions. In one corner there is a statue of Santua Muerte. Maybe you are in a place of worship?",
								"Hallway 3");
        Room bathroom = new Room("You can only go back to the hallway",
								"A room with a mirror and a toilet. You could take advantage of ... no, better to avoid",
        						"Bathroom");
		Item key = new Item("key", "Useful key to open something");
		Item corpse = new Item("corpse", "Decaying corpse");
		Item bed = new Item("bed", "Bed in which the patients slept");
		Item screwdriver = new Item("screwdriver", "Screwdriver, this might come in handy");
		Item gasmask = new Item("gasmask", "Mask to protect yourself from toxic gases");
		Item mirror = new Item("mirror", "Mirror in which your image is reflected");
		ItemContainer chest = new ItemContainer("chest", "Chest that may contain something");
		room1.getObjects().add(corpse);
		room1.getObjects().add(bed);
		room1.getObjects().add(key);
		room2.getObjects().add(bed);
		room3.getObjects().add(chest);
		room4.getObjects().add(bed);
		room5.getObjects().add(bed);
		room6.getObjects().add(screwdriver);
		room7.getObjects().add(bed);
		room8.getObjects().add(gasmask);
		room8.getObjects().add(bed);
		bathroom.getObjects().add(mirror);

		Room hallway4 = new Room("You can enter in the surgery, in the infirmary or in surveillance",
							   	"An hallway with paintings depicting skeletons in daily actions. In one corner there is a statue of Santua Muerte. Maybe you are in a place of worship?",
								"Hallway 4");
		Room infirmary = new Room("You can only go back to the hallway",
								"A room with several shelves full of medicines. Here the patients were to be medicated",
								"Infirmary");
		Room surgery = new Room("You can only go back to the hallway",
								"A room with many medical tools. Here the operations were carried out on patients",
								"Surgery");
		Room surveillance = new Room("You can go back to the hallway, or in the padded cell",
								"A room with several screens connected to security cameras to monitor the building",
								"Surveillance");
		Room paddedCell = new Room("You can go back to the surveillance, or in the",
								"A room with several screens connected to security cameras to monitor the building",
								"Padded Cell");
		Room office = new Room("You can go back to the padded cell or go out the back door",
								"The room of the director of the asylum, full of documents and paperwork",
								"Office");

	}

	// CLOSE, PULL, WALK_TO,  TALK_TO, GIVE, USE, TURN_ON, TURN_OFF
	private void initFromSave(Asylum save) {

	}

	@Override
	public void nextMove(ParserOutput p, PrintStream out) {
		// TODO Auto-generated method stub

	}

}
