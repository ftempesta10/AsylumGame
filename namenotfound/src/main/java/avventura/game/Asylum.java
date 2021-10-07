package game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;

import engine.AdventureCharacter;
import engine.Command;
import engine.CommandHandler;
import engine.CommandType;
import engine.Direction;
import engine.Enemy;
import engine.EventHandler;
import engine.GameDescription;
import engine.Gateway;
import engine.Inventory;
import engine.Item;
import engine.ItemContainer;
import engine.ParserOutput;
import engine.Room;
import engine.Weapon;
import hashedGraph.WeightedHashedGraph;

public class Asylum extends GameDescription {
	private static Object lock = new Object();
	private static Manager frame;
	private String player;
	HandleDB db;

	//game params
	Integer health, maxMoves;
	Boolean gasVuln, breathedGas;


	final EventHandler invalidCommand = new EventHandler() {

		@Override
		public void accept(GameDescription t) {
			// TODO Auto-generated method stub
			System.out.println("I am not sure this is possible!");
		}
	};

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

		WeightedHashedGraph<Room, Gateway> m = new WeightedHashedGraph<Room, Gateway>();
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
        Command brek = new Command(CommandType.BREAK, "rompi");
        push.setAlias(new String[]{"distruggi","spacca","colpisci"});
        getCommands().add(push);

      //Rooms
        Room room1 = new Room("You awake confused in a room...try to remember what brought you here. You have severe pain in your head, something must have hit you. A nauseating stench is in the air ...",
        					   	"There is a corpse, an unmade bed and a key, you can only go south towards the corridor",
        						"Dormitory 1");
        m.insNode(room1);
        Room room2 = new Room("You can only go back to the hallway",
								"There is only one bad",
								"Dormitory 2");
        m.insNode(room2);
        Room room3 = new Room("You can only go back to the hallway",
								"A room full of clothes and with a washing machine. Better leave them in their place ...",
								"Laundry");
        m.insNode(room3);
        Room room4 = new Room("You can only go back to the hallway",
								"There is only one bad",
								"Dormitory 4");
        m.insNode(room4);
        Room room5 = new Room("You can only go back to the hallway",
								"A room with an unmade bed",
								"Dormitory 5");
        m.insNode(room5);
        Room room6 = new Room("You can only go back to the hallway",
								"A room with a bed and numerous holes in the wall, who knows how they were made...",
								"Dormitory 6");
        m.insNode(room6);
        Room room7 = new Room("You can only go back to the hallway",
								"A room with an unmade bed and strange writing on the wall",
								"Dormitory 7");
        m.insNode(room7);
        Room room8 = new Room("You can only go back to the hallway",
								"A room with an unmade bed",
        						"Dormitory 8");
        m.insNode(room8);
        Room hallway = new Room(" From the hallway you can enter room 2, 5, 6 or continue down the hallway towards a door from which monstrous voices are coming ...",
								" A macabre hallway adorned with parts of the human body along the walls",
        						"Hallway 1");
        m.insNode(hallway);
        Room hallway2 = new Room("You can go back down the hallway or enter room 3, 4, 7, 8 or continue down the hallway",
								"A hallway with numerous traces of blood. Did someone drag themselves trying to escape?",
        						"Hallway 2");
        m.insNode(hallway2);
        Room hallway3 = new Room("You can go back down the hallway, enter in the bathroom or take the stairs",
								"An hallway with paintings depicting skeletons in daily actions. In one corner there is a statue of Santua Muerte. Maybe you are in a place of worship?",
								"Hallway 3");
        m.insNode(hallway3);
        Room bathroom = new Room("You can only go back to the hallway",
								"A room with a mirror and a toilet. You could take advantage of ... no, better to avoid",
        						"Bathroom");
        m.insNode(bathroom);

      //second floor
  		Room hallway4 = new Room("You can enter in the surgery, in the infirmary or in surveillance",
  							   	"An hallway with paintings depicting skeletons in daily actions. In one corner there is a statue of Santua Muerte. Maybe you are in a place of worship?",
  								"Hallway 4");
  		m.insNode(hallway4);
  		Room infirmary = new Room("You can only go back to the hallway",
  								"A room with several shelves full of medicines. Here the patients were to be medicated",
  								"Infirmary");
  		m.insNode(infirmary);
  		Room surgery = new Room("You can only go back to the hallway",
  								"A room with many medical tools. Here the operations were carried out on patients",
  								"Surgery");
  		m.insNode(surgery);
  		Room surveillance = new Room("You can go back to the hallway",
  								"A room with several screens connected to security cameras to monitor the building",
  								"Surveillance");
  		m.insNode(surveillance);
  		Room paddedCell = new Room("You can go back to the surveillance, or in the",
  								"A room with several screens connected to security cameras to monitor the building",
  								"Padded Cell");
  		m.insNode(paddedCell);
  		Room office = new Room("You can go back to the padded cell or go out the back door",
  								"The room of the director of the asylum, full of documents and paperwork",
  								"Office");
  		m.insNode(office);


		final Item key = new Item("key", "Useful key to open something", null);
		key.setHandler(new CommandHandler() {

			@Override
			public EventHandler apply(CommandType t) {
				switch(t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(key.getDescription());
						}
					};
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							WeightedHashedGraph<Room, Gateway> m = t.getMap();
							if(t.getCurrentRoom().getName().equals("Dormitory 1")) {
								try {
									for(Room a : m.getAdjacents(t.getCurrentRoom())) {
										if(m.readArc(t.getCurrentRoom(), a).getLockedBy()==key.getId()) {
											m.readArc(t.getCurrentRoom(), a).setLocked(false);
										}
									}
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
							}else {
								System.out.println("There is nothing to open here with this key!");
							}
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(key, t);
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(key, t);
						}
					};
				default:
					return invalidCommand;
				}};
		});
		Inventory corpeInv = new Inventory();
		corpeInv.add(key);
		final Item hint = new Item("note", "Remember: 12689 to stay alive!", null);
		hint.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(hint.getDescription());
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(hint, t);
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(hint, t);
						}
					};
				default:
					return invalidCommand;
				}
			}
		});
		corpeInv.add(hint);
		final AdventureCharacter corpse = new AdventureCharacter(0, "corpe", "decaying corpe", null, corpeInv, null);
		final Item bed = new Item("bed", "Bed in which the patients slept", null);
		bed.setHandler(new CommandHandler() {

			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(bed.getDescription());
						}
					};
				default:
					return invalidCommand;
				}
			}
		});
		/*
		final Item bed = new Item("bed", "Bed in which the patients slept", new CommandHandler() {

			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(bed.getDescription());
						}
					};
				default:
					return invalidCommand;
				}
			}
		});*/

		final Weapon screwdriver = new Weapon("screwdriver", "Screwdriver, this might come in handy", null, 0, 5, 15);
		screwdriver.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							t.getCurrentEnemy().setHealth(t.getCurrentEnemy().getHealth()-screwdriver.getDamage());
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(screwdriver, t);
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(screwdriver, t);
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final Item gasmask = new Item("gasmask", "Mask to protect yourself from toxic gases", null);
		gasmask.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				switch(t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(gasmask.getDescription());
						}
					};
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							gasVuln = false;
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(gasmask, t);
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(gasmask, t);
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final Item flashlight = new Item("flashlight", "Torch to illuminate dark areas", null);
		flashlight.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case USE:
				case TURN_ON:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							t.getCurrentRoom().setVisible(true);
						}
					};
				case TURN_OFF:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							t.getCurrentRoom().setVisible(t.getCurrentRoom().hasLight());
						}
					};
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(flashlight.getDescription());
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(flashlight, t);
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(flashlight, t);
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final Item pills = new Item("pills", "Pills that cure you of some discomfort", null);
		pills.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							breathedGas=false;					}
					};
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(pills.getDescription());
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(pills, t);
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(pills, t);
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final Item adrenaline = new Item("adrenaline", "Syringes of adrenaline that increase your health", null);
		adrenaline.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							health = health + 20;
						}
					};
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(adrenaline.getDescription());
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(adrenaline, t);
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(adrenaline, t);
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final Item mirrorBathroom = new Item("mirror", "Mirror in which your image is reflected", null);
		mirrorBathroom.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(mirrorBathroom.getDescription());
						}
					};
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(mirrorBathroom.getDescription());
						}
					};
				case BREAK:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							if(!mirrorBathroom.isPushed()) {
								t.getCurrentRoom().getObjects().add(pills);
								mirrorBathroom.setPushed(true);
							}
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final Item compass = new Item("compass", "Compass useful for better orientation", null);
		compass.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(compass.getDescription());
						}
					};
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							//DA SCRIVERE
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(compass, t);
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(compass, t);
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final ItemContainer chest = new ItemContainer("chest", "Chest that may contain something", null);
		chest.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							if(!chest.isOpened()) {
								System.out.println(chest.getDescription());
							}else {
								System.out.println("This chest contains: ");
								for(Item i: chest.getContent()) {
									System.out.println(i.getName());
								}
							}
						}
					};
				case OPEN:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							if(!chest.isOpened() && !chest.isLocked()) {
								chest.setOpened(true);
							}else if(chest.isLocked()) {
								System.out.println("Is locked! Probably you need a key...");
							}
						}
					};
				case CLOSE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
								chest.setOpened(false);
								if(chest.getLockedBy()!=null)
									chest.setLocked(true);
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final Item pc = new Item("pc", "Computer used to interact with security systems", null);
		pc.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							//DA SCRIVERE
						}
					};
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(pc.getDescription());
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final Weapon scalpel = new Weapon("scalpel", "Scalpel used in experiments", null, 15, 10, 30);
		scalpel.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							t.getCurrentEnemy().setHealth(t.getCurrentEnemy().getHealth()-scalpel.getDamage());
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(scalpel, t);
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(scalpel, t);
						}
					};
				default:
					return invalidCommand;
				}
			}
		});


		final Weapon gun = new Weapon("gun", "Gun probably used against rebellious patients", null, 7, 30, 70);
		gun.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							if(gun.getShots()>0) {
								t.getCurrentEnemy().setHealth(t.getCurrentEnemy().getHealth()-gun.getDamage());
								gun.setShots(gun.getShots()-1);
							}else {
								System.out.println("Ops... You run out of bullets!!");
							}
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(gun, t);
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(gun, t);
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final Item mirrorCell = new Item("mirror", "Mirror in which your image is reflected", null);
		mirrorBathroom.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(mirrorCell.getDescription());
						}
					};
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(mirrorCell.getDescription());
						}
					};
				case BREAK:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							if(!mirrorCell.isPushed()) {

								//inserire l'arco
								m.insArc(paddedCell, office, new Gateway(Direction.SOUTH));
								mirrorCell.setPushed(true);
							}
						}
					};
				default:
					return invalidCommand;
				}
			}
		});
		
		final Item codePaper = new Item("code paper", "Useful code to open something", null);
		key.setHandler(new CommandHandler() {

			@Override
			public EventHandler apply(CommandType t) {
				switch(t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(codePaper.getDescription());
						}
					};
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							WeightedHashedGraph<Room, Gateway> m = t.getMap();
							if(t.getCurrentRoom().getName().equals("hallway2")) {
								try {
									for(Room a : m.getAdjacents(t.getCurrentRoom())) {
										if(m.readArc(t.getCurrentRoom(), a).getLockedBy()==codePaper.getId()) {
											m.readArc(t.getCurrentRoom(), a).setLocked(false);
										}
									}
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
							}else {
								System.out.println("There is nothing to open here with this code!");
							}
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(codePaper, t);
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(codePaper, t);
						}
					};
				default:
					return invalidCommand;
				}};
		});
		
		final Enemy human = new Enemy(100, "human", "disfigured human", "commento umano", null, codePaper,5,20);
		final Enemy assistant = new Enemy(100, "assistant", "director's assistant", "commento assistente", null, null,5,20);
		final Enemy director = new Enemy(100, "director", "asylum's director", "commento direttore", null, null,5,20);


		room1.getEnemies().add(corpse);
		room1.getObjects().add(bed);
		room1.getObjects().add(key);
		room2.getObjects().add(bed);
		room3.getObjects().add(chest);
		hallway2.getEnemies().add(human);
		room4.getObjects().add(bed);
		room5.getObjects().add(bed);
		room6.getObjects().add(screwdriver);
		room7.getObjects().add(bed);
		room8.getObjects().add(gasmask);
		room8.getObjects().add(bed);
		bathroom.getObjects().add(mirrorBathroom);



		infirmary.getObjects().add(pills);
		infirmary.getObjects().add(adrenaline);
		infirmary.getObjects().add(adrenaline);
		surgery.getObjects().add(scalpel);
		surgery.getObjects().add(bed);
		surveillance.getObjects().add(gun);
		surveillance.getObjects().add(pc);
		paddedCell.getObjects().add(mirrorCell);
		paddedCell.getEnemies().add(assistant);
		office.getEnemies().add(director);



		//doors
		m.insArc(room1, hallway, new Gateway(Direction.SOUTH, key.getId(), true));
		m.insArc(room2, hallway, new Gateway(Direction.SOUTH));
		m.insArc(room5, hallway, new Gateway(Direction.NORTH));
		m.insArc(room6, hallway, new Gateway(Direction.NORTH));

		m.insArc(hallway, hallway2, new Gateway(Direction.EAST));

		m.insArc(room3, hallway2, new Gateway(Direction.SOUTH));
		m.insArc(room4, hallway2, new Gateway(Direction.SOUTH));
		m.insArc(room7, hallway2, new Gateway(Direction.NORTH));
		m.insArc(room8, hallway2, new Gateway(Direction.NORTH));

		m.insArc(hallway2, hallway3, new Gateway(Direction.EAST));

		m.insArc(bathroom, hallway3, new Gateway(Direction.SOUTH));

		m.insArc(hallway3, hallway4, new Gateway(Direction.SOUTH));

		m.insArc(hallway4, surgery, new Gateway(Direction.EAST));
		m.insArc(hallway4, infirmary, new Gateway(Direction.EAST));
		m.insArc(hallway4, surveillance, new Gateway(Direction.OVEST));

		m.insArc(surveillance, paddedCell, new Gateway(Direction.SOUTH));

	}

	// CLOSE, PULL, WALK_TO,  TALK_TO, GIVE, USE, TURN_ON, TURN_OFF
	private void initFromSave(Asylum save) {

	}

	@Override
	public void nextMove(ParserOutput p, PrintStream out) {
		// TODO Auto-generated method stub

	}

}
