package game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Scanner;

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

public class Asylum extends GameDescription implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -78135229798072209L;
	private static Object lock = new Object();
	private static Manager frame;
	private String player;
	transient HandleDB db;

	//game params
	Integer health, maxMoves;
	Boolean gasVuln, breathedGas, compassUsed;


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

	private void initNew() throws SQLException, CloneNotSupportedException {
		health = 100;
		gasVuln = true;
		breathedGas = false;
		maxMoves = 3;
		compassUsed = false;

		WeightedHashedGraph<Room, Gateway> m = new WeightedHashedGraph<Room, Gateway>();
		Command nord = new Command(CommandType.NORD, "nord");
        nord.setAlias(new String[]{"n", "N", "Nord", "NORD"});
        getCommands().add(nord);
        Command inventory = new Command(CommandType.INVENTORY, "inventario");
        inventory.setAlias(new String[]{"inv", "i", "I"});
        getCommands().add(inventory);
        Command sud = new Command(CommandType.SOUTH, "sud");
        sud.setAlias(new String[]{"s", "S", "Sud", "SUD"});
        getCommands().add(sud);
        Command est = new Command(CommandType.EAST, "est");
        est.setAlias(new String[]{"e", "E", "Est", "EST"});
        getCommands().add(est);
        Command ovest = new Command(CommandType.WEST, "ovest");
        ovest.setAlias(new String[]{"o", "O", "Ovest", "OVEST"});
        getCommands().add(ovest);
        Command nord_est = new Command(CommandType.NORTH_EAST, "nord_est");
        nord_est.setAlias(new String[]{"ne", "NE", "Ne", "nE", "Nord_est", "Nord_Est", "nord_Est", "NORD_EST"});
        getCommands().add(nord_est);
        Command nord_ovest = new Command(CommandType.NORTH_WEST, "nord_ovest");
        nord_ovest.setAlias(new String[]{"no", "NO", "No", "nO", "Nord_ovest", "Nord_Ovest", "nord_Ovest", "NORD_OVEST"});
        getCommands().add(nord_ovest);
        Command sud_est = new Command(CommandType.SOUTH_EAST, "sud_est");
        sud_est.setAlias(new String[]{"se", "SE", "Se", "sE", "Sud_est", "Sud_Est", "sud_Est", "SUD_EST"});
        getCommands().add(sud_est);
        Command sud_ovest = new Command(CommandType.SOUTH_WEST, "sud_ovest");
        sud_ovest.setAlias(new String[]{"so", "SO", "So", "sO", "Sud_ovest", "Sud_Ovest", "sud_Ovest", "SUD_OVEST"});
        getCommands().add(sud_ovest);
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
        Command close = new Command(CommandType.CLOSE, "chiudi");
        close.setAlias(new String[]{});
        getCommands().add(close);
        Command push = new Command(CommandType.PUSH, "premi");
        push.setAlias(new String[]{"spingi","attiva"});
        getCommands().add(push);
        Command pull = new Command(CommandType.PULL, "tira");
        pull.setAlias(new String[]{});
        getCommands().add(pull);
        Command _break = new Command(CommandType.BREAK, "rompi");
        _break.setAlias(new String[]{"distruggi","spacca","colpisci", "attacca"});
        getCommands().add(_break);
        Command talk_to = new Command(CommandType.TALK_TO, "parla");
        talk_to.setAlias(new String[]{"dialoga","interagisci","comunica"});
        getCommands().add(talk_to);
        Command walk_to = new Command(CommandType.WALK_TO, "vai");
        walk_to.setAlias(new String[]{"entra"});
        getCommands().add(walk_to);
        Command up = new Command(CommandType.UP, "su");
        up.setAlias(new String[]{"sopra"});
        getCommands().add(up);
        Command down = new Command(CommandType.DOWN, "gi�");
        down.setAlias(new String[]{"sotto"});
        getCommands().add(down);
        Command give = new Command(CommandType.GIVE, "dai");
        give.setAlias(new String[]{});
        getCommands().add(give);
        Command use = new Command(CommandType.USE, "usa");
        use.setAlias(new String[]{"utilizza"});
        getCommands().add(use);
        Command turn_on = new Command(CommandType.TURN_ON, "accendi");
        turn_on.setAlias(new String[]{""});
        getCommands().add(turn_on);
        Command turn_off = new Command(CommandType.TURN_OFF, "spegni");
        turn_off.setAlias(new String[]{""});
        getCommands().add(turn_off);
        Command drop = new Command(CommandType.DROP, "lascia");
        drop.setAlias(new String[]{"getta","scarta"});
        getCommands().add(drop);

        //set inv
        setInventory(new Inventory());

      //Rooms

        //Start Game Message
        // Ti svegli confuso in una stanza...cerchi di ricordare cosa ti ha portato qui. Stavi indagando su qualcosa ma non riesci a ricordare...hai un forte dolore alla testa. Un odore nauseabondo � nell'aria...


        Room room1 = new Room("Sei nel dormitorio n�1, una stanza piccola e puzzolente",
			   	               "In un angolo giace un cadavere, accanto a te c'� un letto in disordine. Vedi una porta che conduce nel corridoio 1.",
				               "dormitorio 1");
        m.insNode(room1);
        Room room2 = new Room("Sei nel dormitorio n�2",
				               "Vedi un letto. Puoi solo tornare indietro nel corridoio 1.",
				               "dormitorio 2");
        m.insNode(room2);
        Room room3 = new Room("Sei in una stanza piena di vestiti sparsi sul pavimento ed una lavatrice. Ci saranno momenti pi� adatti per aggiornare il tuo guardaroba!",
				               "Noti una scatola per terra, conterr� qualcosa? Non vedi altre porte. Puoi solo tornare indietro nel corridoio 2. ",
				               "lavanderia");
        m.insNode(room3);
        Room room4 = new Room("Sei nel dormitorio n�4",
				               "Vedi un letto. Puoi solo tornare indietro nel corridoio 2.",
				               "dormitorio 4");
        m.insNode(room4);
        Room room5 = new Room("Sei nel dormitorio n�5",
				               "Vedi un letto. Puoi solo tornare indietro nel corridoio 1.",
				               "dormitorio 5");
        m.insNode(room5);
        Room room6 = new Room("Sei in una stanza piena di buchi sul muro, chiss� come sono stati fatti...",
				               "Vedi un letto ed un cacciavite per terra. Qualche paziente sperava forse che bastasse un cacciavite per crearsi una via di fuga...Puoi solo tornare indietro nel corridoio 1.",
				               "dormitorio 6");
        m.insNode(room6);
        Room room7 = new Room("Sei nel dormitorio n�7",
				               "Vedi un letto. Puoi solo tornare indietro nel corridoio 2.",
				               "dormitorio 7");
        m.insNode(room7);
        Room room8 = new Room("Sei nel dormitorio n�8",
				               "Vedi un letto e qualcosa sporgere da sotto ad esso. Non vedi altre porte. Puoi solo tornare indietro nel corridoio 2.",
				               "dormitorio 8");
        m.insNode(room8);
        Room hallway = new Room("Sei in un corridoio macabro ornato con membra umane lungo le pareti. � chiaro che non sei in un semplice manicomio...",
				                 "Senti dei lamenti mostruosi provenienti dalla porta di fronte a te. Puoi andare nel corridoio 2 o entrare nel dormitorio 1, 2, 5, o 6.",
				                 "corridoio 1");
        m.insNode(hallway);
        //Sei in un corridoio con numerose tracce di sangue. Qualcuno si sar� trascinato per provare a scappare?
        Room hallway2 = new Room("Non appena apri la porta, un umano insanguinato con la faccia sfigurata si scaglia contro di te",
				                  "Il sangue � dappertutto. Puoi tornare indietro nel corridoio 1, o entrare nel dormitorio 3, 4, 7, 8.",
				                  "corridoio 2");
        m.insNode(hallway2);
        Room hallway3 = new Room("Sei in un corridoio con quadri raffiguranti scheletri in azioni quotidiane. In un angolo c'� una statua della Santua Muerte. Forse sei in un luogo di culto?",
				                  "Vedi scheletri ovunque. Puoi tornare indietro nel corridoio 2, entrare nel bagno, o prendere le scale per il piano inferiore.",
				                  "corridoio 3");
        m.insNode(hallway3);
        Room bathroom = new Room("Non appena entri nella stanza, nell'aria inizia a circolare del gas tossico. Se vuoi sopravvivere, forse dovresti scappare da qui e trovare qualcosa con cui proteggerti!",
				                  "L'effetto del gas ti stordisce e non ti permette di vedere nulla. Puoi solo tornare indietro nel corridoio 3.",
				                  "bagno");
        m.insNode(bathroom);


      //second floor
        Room hallway4 = new Room("Sei nel corridoio del piano inferiore. L'atmosfera � pi� cupa, avverti un cattivo presentimento, sei vicino alla resa dei conti?",
				   	              "Vedi delle porte che consentono l'accesso all'infermeria, alla sala operatoria e alla sorveglianza. Puoi entrare in queste stanze o risalire nel corridoio 3.",
					              "corridoio 4");
  		m.insNode(hallway4);
  		Room infirmary = new Room("Sei in una stanza con numerosi scaffali pieni di medicine. Qui dovrebbero essere 'medicati' i pazienti.",
					               "Vedi diversi medicinali sparsi per la stanza ed una cassa. Puoi solo tornare indietro nel corridoio 4.",
					               "infermeria");
  		m.insNode(infirmary);
  		Room surgery = new Room("Non appena entri nella stanza, nell'aria inizia a circolare del gas tossico. Se vuoi sopravvivere, forse dovresti scappare da qui e trovare qualcosa con cui proteggerti!",
					             "L'effetto del gas ti stordisce e non ti permette di vedere nulla. Puoi solo tornare indietro nel corridoio 4.",
					             "sala operatoria");
  		m.insNode(surgery);
  		Room surveillance = new Room("Sei in una stanza stanza con numerosi schermi collegati alle telecamere di sicurezza per controllare l'edificio.",
					                  "Vedi un pc principale ed una pistola per terra. Puoi tornare nel corridoio 4 o proseguire verso la cella imbottita",
					                  "sorveglianza");
  		m.insNode(surveillance);
  		Room paddedCell = new Room("La scarsa illuminazione della stanza non ti permette di vedere bene. Dovresti utilizzare qualcosa per illuminare.", "Non riesci a vedere nulla, � troppo buio", "cella imbottita");

  		/*Room paddedCell = new Room("Sei nella stanza imbottita. Questa � usata per rinchiudere i pazienti in preda a forti crisi, in modo che non danneggino s� stessi.",
					                "In fondo alla stanza vedi uno specchio. Apparentemente, puoi solo tornare indietro nella sorveglianza.",
					                "Cella imbottita");*/
  		m.insNode(paddedCell);
  		Room office = new Room("Sei nell'ufficio del direttore del manicomio. Dai quadri posti lungo le pareti � possibile ripercorrere questi anni di esperimenti e gli effetti delle mutazioni nel corso del tempo. Hai di fronte la mente dietro tutto ci�, il direttore.",
					            "Vedi delle scale che conducono all'uscita della struttura, ma il passaggio � bloccato dal direttore. Puoi tornare indietro nella cella imbottita.",
					            "ufficio");
  		m.insNode(office);

  		Room exit = new Room("Sei finalmente uscito dal manicomio! Non puoi ancora credere a quello che ti � successo, sei ancora stordito, ma decidi di andare subito in ufficio a riferire tutto l'accaduto al tuo capo. Dopo aver risolto un caso del genere sarai sicuramente l'eroe della citt�!",
  				              "Sei all'esterno della struttura",
  				              "uscita");


		final Item key = new Item("chiave", "Una chiave che potrebbe tornare utile per aprire qualcosa.", null);
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
							if(t.getCurrentRoom().getName().equals("dormitorio 1")) {
								try {
									for(Room a : m.getAdjacents(t.getCurrentRoom())) {
										if(m.readArc(t.getCurrentRoom(), a).getLockedBy()==key.getId()) {
											for (Item i : t.getInventory().getList()) {
												if (i.equals(key)) {
													m.readArc(t.getCurrentRoom(), a).setLocked(false);
													System.out.println("La chiave sembra entrare perfettamente nella serratura della porta che conduce nel corridoio!");
													EventHandler.drop(key, t);
													break;
												}
											}
										}
									}
								
									

								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
							}else {
								System.out.println("Non c'� niente da aprire con questa chiave!");
							}
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(key, t);
							System.out.println("Hai preso la chiave!");
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
		Inventory corpseInv = new Inventory();
		corpseInv.add(key);
		final AdventureCharacter corpse = new AdventureCharacter(0, "cadavere", "Un corpo esanime dall'odore stomacevole. Deve essere l� da tanto tempo. Dalla tasca della sua giacca sembra spuntare una chiave.", null, corpseInv, key);
		final Item bed = new Item("letto", "Un letto nel quale dormono i pazienti.", null);
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

		final Weapon screwdriver = new Weapon("cacciavite", "Un cacciavite che potrebbe tornare utile.", null, 15, 5, 15);
		screwdriver.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(screwdriver.getDescription());
						}
					};
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
							System.out.println("Hai preso il cacciavite!");
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final Item gasmask = new Item("maschera-gas", "Una maschera per proteggerti dai gas tossici.", null);
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
							System.out.println("Stai indossando correttamente la maschera!");
							gasVuln = false;
							bathroom.setDescription("Non appena entri nella stanza, i gas tossici iniziano a circolare nell'aria, ma la maschera ti protegge.");
							bathroom.setLook("Vedi uno specchio ed un gabinetto. Potresti approfittare per...no, meglio evitare. Puoi solo tornare indietro nel corridoio.");
							surgery.setDescription("Sei nella stanza dove i pazienti sono sottoposti alle operazioni. Chiss� a questo punto di che operazioni si tratta...");
							surgery.setLook("Vedi un letto ed uno scaffale con tanti strumenti chirurgici. Puoi solo tornare indietro nel corridoio.");
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

		final Item torch = new Item("torcia", "Una torcia utile per illuminare gli spazi bui.", null);
		torch.setHandler(new CommandHandler() {
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
							System.out.println("Hai acceso la torcia!");
							t.getCurrentRoom().setVisible(true);
							if (t.getCurrentRoom().equals(paddedCell) && t.getCurrentRoom().getTrap()!= null) {
								t.getCurrentRoom().getTrap().accept(t);
								paddedCell.setDescription("Sei nella stanza imbottita. Questa � usata per rinchiudere i pazienti in preda a forti crisi, in modo che non danneggino s� stessi. Davanti a te vedi un essere mastodontico, � l'assistente del direttore.");
								paddedCell.setLook("In fondo alla stanza vedi uno specchio. Apparentemente, puoi solo tornare indietro nella sorveglianza.");
							}
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
							System.out.println(torch.getDescription());
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(torch, t);
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(torch, t);
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final Item pills = new Item("pillole", "Delle pillole che ti rendono immune temporaneamente ai gas tossici.", null);
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

		final Item adrenaline = new Item("adrenalina", "Delle siringhe di adrenalina che ti incrementano la salute.", null);
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

		final Item mirrorBathroom = new Item("specchio", "Uno specchio in cui viene riflessa la tua immagine. Mmm, non sei poi cos� male.", null);
		mirrorBathroom.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
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

		final Item compass = new Item("bussola", "Una bussola utile per un miglior orientamento nella mappa.", null);
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
							System.out.println("Grazie all'utilizzo della bussola ora sai in che direzione sono le porte e ti � possibile spostarti utilizzando i punti cardinali come comandi!");
							room1.setLook("In un angolo giace un cadavere, accanto a te c'� un letto in disordine. Vedi una porta a sud che conduce nel corridoio.");
							room2.setLook("Vedi un letto. Puoi solo tornare indietro nel corridoio.");
							room3.setLook("Non vedi altre porte. Puoi solo tornare nel corridoio a sud.");
							room4.setLook("Vedi un letto. Puoi solo tornare indietro nel corridoio a sud.");
							room5.setLook("Vedi un letto. Puoi solo tornare indietro nel corridoio a nord.");
							room6.setLook("Vedi un letto ed un cacciavite per terra. Qualche paziente sperava forse che bastasse un cacciavite per crearsi una via di fuga...Puoi solo tornare indietro nel corridoio a nord.");
							room7.setLook("Vedi un letto. Puoi solo tornare indietro nel corridoio a nord.");
							room8.setLook("Vedi un letto e qualcosa sporgere da sotto ad esso. Non vedi altre porte, puoi solo tornare indietro nel corridoio a nord.");
							hallway.setLook("Puoi proseguire a est o entrare nel dormitorio 1 a nord-ovest, 2 a nord-est, 5 a sud-ovest, o 6 a sud-est.");
							hallway2.setLook("Il sangue � dappertutto. Puoi tornare indietro ad ovest nel primo corridoio, o entrare nel dormitorio 3 a nord-ovest, 4 a nord-est, 7 a sud-ovest, 8 a sud-est.");
							hallway3.setLook("Vedi scheletri ovunque. Puoi tornare indietro ad ovest nel secondo corridoio, entrare nel bagno a nord, o prendere le scale a sud per il piano inferiore");
							bathroom.setLook("L'effetto del gas ti stordisce e non ti permette di vedere nulla. Puoi solo tornare indietro a sud nel corridoio.");
							hallway4.setLook("Vedi delle porte che consentono l'accesso all'infermeria a nord-est, alla sala operatoria a sud-est e alla sorveglianza ad ovest.");
							infirmary.setLook("Vedi diversi medicinali sparsi per la stanza ed una cassa. Puoi solo tornare indietro nel corridoio ad ovest.");
							surgery.setLook("Vedi un letto ed uno scaffale con tanti strumenti chirurgici. Puoi solo tornare indietro nel corridoio ad ovest.");
							surveillance.setLook("Vedi un pc principale ed una pistola per terra. Puoi tornare nel corridoio ad est o proseguire a sud verso la cella imbottita.");
							paddedCell.setLook("In fondo alla stanza vedi uno specchio. Apparentemente, puoi solo tornare indietro nella sorveglianza a nord.");
							office.setLook("Vedi delle scale a sud che conducono all'esterno della struttura, ma il passaggio � bloccato dal direttore. Puoi tornare indietro a nord nella cella imbottita.");
							compassUsed=true;

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

		final ItemContainer chest = new ItemContainer("cassa", "Una cassa che potrebbe contenere qualcosa al suo interno.", null);
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
								System.out.println("Questa cassa contiene: ");
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
								System.out.println("� bloccata! Probabilmente hai bisogno di una chiave...");
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

		final Item pc = new Item("pc", "Un computer utilizzato per interagire con i sistemi di sicurezza.", null);
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
							System.out.println("Accendi il PC: vengono mostrati i video degli esperimenti sui pazienti. Ora ricordi: stavi indagando sulla scomparsa di un paziente all'interno del manicomio quando qualcuno ti ha colpito alla testa! Dai video emerge che questi esperimenti causano delle mutazioni nei pazienti!");						}
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

		final Weapon scalpel = new Weapon("bisturi", "Un bisturi utilizzato negli esperimenti.", null, 15, 10, 30);
		scalpel.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(scalpel.getDescription());
						}
					};
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


		final Weapon gun = new Weapon("pistola", "Una pistola probabilmente utilizzata contro i pazienti pi� inquieti e difficili da controllare.", null, 7, 30, 70);
		gun.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(gun.getDescription());
						}
					};
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							if(gun.getShots()>0) {
								t.getCurrentEnemy().setHealth(t.getCurrentEnemy().getHealth()-gun.getDamage());
								gun.setShots(gun.getShots()-1);
							}else {
								System.out.println("Ops...Hai esaurito le munizioni!!");
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

		final Item mirrorCell = new Item("specchio", "Uno specchio in cui viene riflessa la tua immagine. Mmm, non sei poi cos� male.", null);
		mirrorBathroom.setHandler(new CommandHandler() {
			@Override
			public EventHandler apply(CommandType t) {
				// TODO Auto-generated method stub
				switch (t) {
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
								System.out.println("La rottura dello specchio ti rivela un passaggio segreto a sud nell'ufficio del direttore!");
							}
						}
					};
				default:
					return invalidCommand;
				}
			}
		});

		final Item codePaper = new Item("foglio", "Il codice e' 5030", null);
		codePaper.setHandler(new CommandHandler() {

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

		final Item blockNotes = new Item("blocco-note", "Un quaderno di appunti che a giudicare dalla grafia sembra proprio essere il tuo. A quanto pare stavi raccogliendo dati su questa struttura e indizi per la risoluzione di un caso, ma non riesci a ricordare bene. Avevi sottolineato pi� volte \"DEVO ROMPERE LO SPECCHIO PER PROSEGUIRE\"...", null);
		blockNotes.setHandler(new CommandHandler() {

			@Override
			public EventHandler apply(CommandType t) {
				switch(t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(blockNotes.getDescription());
						}
					};
				case PICK_UP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.pickUp(blockNotes, t);
						}
					};
				case DROP:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							EventHandler.drop(blockNotes, t);
						}
					};
				default:
					return invalidCommand;
				}};
		});

		final Item keypad = new Item("tastierino", "Un tastierino numerico in cui inserire un codice.", null);
		keypad.setHandler(new CommandHandler() {

			@Override
			public EventHandler apply(CommandType t) {
				switch(t) {
				case LOOK_AT:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							System.out.println(keypad.getDescription());
						}
					};
				case USE:
					return new EventHandler() {
						@Override
						public void accept(GameDescription t) {
							// TODO Auto-generated method stub
							Scanner scan = new Scanner(System.in);
							String codEntered = scan.nextLine();
							scan.close();
							String[] tokens = codePaper.getDescription().split("\\s+");
							if(codEntered.equals(tokens[3])) {
								hallway3.setTrap(null);
								System.out.println("Codice esatto! La trappola � stata disattivata!");
							} else System.out.println("Codice errato!");
						}
					};
				default:
					return invalidCommand;
				}};
		});


		final Enemy mutant = new Enemy(100, "mutante", "Un mutante dal viso fortemente sfigurato. Sar� mica Deadpool?", "Anche tu sei uno di loro?! Non ti lascer� farmi del male!", null, codePaper,5,20);
		final Enemy assistant = new Enemy(100, "assistente", "� l'assistente del direttore, o per lo meno ci� che rimane di lui, visto il suo corpo sensibilmente ingigantito dopo le mutazioni a cui si � sottoposto. Deve aver aiutato il direttore nel portare avanti questi folli esperimenti.",
				"Ancora tu? Pensavo che dopo quel forte colpo alla testa non ti saresti svegliato per un po'. Beh, il prossimo paziente sei proprio tu, quindi ti ringrazio per avermi risparmiato la fatica di salire al pieno superiore per prenderti. Non opporre resistenza e preparati ad accogliere nel tuo corpo i poteri del virus!",
				new Inventory(), null,5,20);
		final Enemy director = new Enemy(100, "direttore", "� il direttore, nonch� la mente contorta dietro tutto questo. I segni del virus sembrano meno evidenti su di lui. Avr� furbamente aspettato pi� miglioramenti possibili al virus prima di sottoporsi lui stesso ad esso. Eppure ti � sempre sembrato un tipo perbene...",
				"Muahahah! Eccoti qua agente. Dopo aver sentito gli spari dalla cella, ti aspettavo. Sei sopreso dopo aver scoperto i miei piani? Lo sarai di pi� dopo aver visto i poteri che acquisirai tramite il virus! Non prendermi per pazzo, grazie a questo virus non esisteranno mai pi� deboli in questo mondo. Io render� l'essere umano la creatura pi� potente che sia mai esistita sulla Terra! Si parler� di me per milioni e milioni di anni! Ma se non vuoi aiutarmi, non preoccuparti. Ci servono delle vittime sacrificali in onore della Santa Muerte che ci supporta in tutto questo. Dunque preparati a morire!",
				null, null,5,20);

		Item key_1= new Item("Chiave assistente", "Sembra la chiave di una porta...", null);
		key_1.setHandler(new CommandHandler() {

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
							if(t.getCurrentRoom().equals(paddedCell)) {
								try {
									for(Room a : m.getAdjacents(t.getCurrentRoom())) {
										if(m.readArc(t.getCurrentRoom(), a).getLockedBy()==key_1.getId()) {
											m.readArc(t.getCurrentRoom(), a).setLocked(false);
										}
									}
									System.out.println("La chiave sembra entrare perfettamente nella serratura della porta che conduce nell'ufficio del direttore!");
								} catch (Exception e) {
									System.out.println(e.getMessage());
								}
							}else {
								System.out.println("Non c'� niente da aprire con questa chiave!");
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

		assistant.getInv().add(key_1);
		chest.add(compass);
		chest.add(torch);
		chest.add(blockNotes);

		room1.getEnemies().add(corpse);
		room1.getObjects().add(bed);
		room2.getObjects().add(bed);
		room3.getObjects().add(chest);
		hallway2.getEnemies().add(mutant);
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
		m.insArc(hallway, room1, new Gateway(Direction.NORTH_WEST));
		m.insArc(room2, hallway, new Gateway(Direction.SOUTH));
		m.insArc(hallway, room2, new Gateway(Direction.NORTH_EAST));
		m.insArc(room5, hallway, new Gateway(Direction.NORTH));
		m.insArc(hallway, room5, new Gateway(Direction.SOUTH_WEST));
		m.insArc(room6, hallway, new Gateway(Direction.NORTH));
		m.insArc(hallway, room6, new Gateway(Direction.SOUTH_EAST));

		m.insArc(hallway, hallway2, new Gateway(Direction.EAST));
		m.insArc(hallway2, hallway, new Gateway(Direction.WEST));


		m.insArc(room3, hallway2, new Gateway(Direction.SOUTH));
		m.insArc(hallway2, room3, new Gateway(Direction.NORTH_WEST));
		m.insArc(room4, hallway2, new Gateway(Direction.SOUTH));
		m.insArc(hallway2, room4, new Gateway(Direction.NORTH_EAST));
		m.insArc(room7, hallway2, new Gateway(Direction.NORTH));
		m.insArc(hallway2, room7, new Gateway(Direction.SOUTH_WEST));
		m.insArc(room8, hallway2, new Gateway(Direction.NORTH));
		m.insArc(hallway2, room8, new Gateway(Direction.SOUTH_EAST));

		m.insArc(hallway3, hallway2, new Gateway(Direction.WEST));
		m.insArc(bathroom, hallway3, new Gateway(Direction.SOUTH));
		m.insArc(hallway3, bathroom, new Gateway(Direction.NORTH));

		m.insArc(hallway3, hallway4, new Gateway(Direction.DOWN));
		m.insArc(hallway4, hallway3, new Gateway(Direction.UP));

		m.insArc(hallway4, infirmary, new Gateway(Direction.NORTH_EAST));
		m.insArc(infirmary, hallway4, new Gateway(Direction.WEST));
		m.insArc(hallway4, surgery, new Gateway(Direction.SOUTH_EAST));
		m.insArc(surgery, hallway4, new Gateway(Direction.WEST));
		m.insArc(hallway4, surveillance, new Gateway(Direction.WEST));
		m.insArc(surveillance, hallway4, new Gateway(Direction.EAST));

		m.insArc(surveillance, paddedCell, new Gateway(Direction.SOUTH));
		m.insArc(paddedCell, surveillance, new Gateway(Direction.NORTH));

		//traps
		EventHandler gasTrap = new EventHandler() {

			@Override
			public void accept(GameDescription t) {
				// TODO Auto-generated method stub
				Asylum g = (Asylum) t;
				if(g.gasVuln) {
					g.breathedGas = true;
					g.maxMoves = 4;
					System.out.println("Stai respirando del gas! Non ti rimane molto tempo prima di perdere coscienza, devi fare subito qualcosa!");
				}
			}
		};

		bathroom.setTrap(gasTrap);
		surgery.setTrap(gasTrap);

		hallway3.setTrap(new EventHandler() {
			@Override
			public void accept(GameDescription t) {
				// TODO Auto-generated method stub
				Asylum g = (Asylum) t;
				g.health = 0;
				System.out.println("GAME OVER! Una trappola mortale posizionata sulla porta ti ha fatto a pezzi!");
			}
		});

		//stanza iniziale
		setCurrentRoom(room1);

		paddedCell.setTrap(new EventHandler() {

			@Override
			public void accept(GameDescription t) {
				// TODO Auto-generated method stub
				if (getCurrentRoom().hasLight()) {
					try {
						t.getMap().readArc(paddedCell, surveillance).setLockedBy(key_1.getId());
					} catch (Exception e) {}
					System.out.println("Sei in trappola! ......");
				}
			}
		});



		//inserimento in db
        this.db.insertionTuple(this.player, this);
        
        //set mappa
        setMap(m);

	}

	// CLOSE, PULL, WALK_TO,  TALK_TO, GIVE, USE, TURN_ON, TURN_OFF
	private void initFromSave(Asylum save) throws SQLException, Exception {
		this.breathedGas = save.breathedGas;
		this.gasVuln = save.gasVuln;
		this.health = save.health;
		this.maxMoves = save.maxMoves;
		this.player = save.player;
		this.setMap(save.getMap());
		this.setInventory(save.getInventory());
		this.setCurrentRoom(save.getCurrentRoom());
		this.setCurrentEnemy(save.getCurrentEnemy());
		this.setCommandTarget(save.getCommandTarget());
		this.setCommands(save.getCommands());
	}

	private Room searchDirection(Direction d) throws Exception {
		Collection<Room> ad = getMap().getAdjacents(getCurrentRoom());
		for(Room r: ad) {
			if(getMap().readArc(getCurrentRoom(), r).getDirection()==d)
				return r;
		}
		return null;
	}

	private Direction commandToDirection(CommandType c) {
		switch (c) {
		case NORD:
			return Direction.NORTH;
		case NORTH_EAST:
			return Direction.NORTH_EAST;
		case NORTH_WEST:
			return Direction.NORTH_WEST;
		case SOUTH:
			return Direction.SOUTH;
		case SOUTH_EAST:
			return Direction.SOUTH_EAST;
		case SOUTH_WEST:
			return Direction.SOUTH_WEST;
		case WEST:
			return Direction.WEST;
		case EAST:
			return Direction.EAST;
		case UP:
			return Direction.UP;
		case DOWN:
			return Direction.DOWN;
		default:
			return null;
		}
	}

	private void changeRoom(CommandType c, PrintStream out) {
		if(compassUsed || c==CommandType.UP || c==CommandType.DOWN) {
			try {
				Room next = searchDirection(commandToDirection(c));
				if (next == null) {
					out.println("Non esiste nessuna stanza adiacente in quella direzione!");
				} else if(getMap().readArc(getCurrentRoom(), next).isLocked()) {
					out.println("La porta sembra esser bloccata...");
				} else {
					setCurrentRoom(next);
					out.println(getCurrentRoom().getDescription());
				}
			} catch (Exception e) {
				out.println(e.getMessage());
			}
		}else {
			out.println("Se solo avessi una bussola...");
		}
	}

	@Override
	public void nextMove(ParserOutput p, PrintStream out) {
		// TODO Auto-generated method stub
		if (p.getObject()==null && p.getEnemy()==null && p.getTarget()==null) {
			switch (p.getCommand().getType()) {
			case INVENTORY:
				for(Item i : getInventory().getList()) {
					out.println(i.getName());
				}
				if(getInventory().getList().isEmpty())
					out.println("Inventario vuoto!");
				break;
			case LOOK_AT:
				out.println(getCurrentRoom().getLook());
				break;
			case NORD:
				changeRoom(p.getCommand().getType(), out);
				break;
			case NORTH_EAST:
				changeRoom(p.getCommand().getType(), out);
				break;
			case NORTH_WEST:
				changeRoom(p.getCommand().getType(), out);
				break;
			case SOUTH_EAST:
				changeRoom(p.getCommand().getType(), out);
				break;
			case SOUTH_WEST:
				changeRoom(p.getCommand().getType(), out);
				break;
			case EAST:
				changeRoom(p.getCommand().getType(), out);
				break;
			case WEST:
				changeRoom(p.getCommand().getType(), out);
				break;
			case SOUTH:
				changeRoom(p.getCommand().getType(), out);
				break;
			case UP:
				changeRoom(p.getCommand().getType(), out);
				break;
			case DOWN:
				changeRoom(p.getCommand().getType(), out);
				break;
			case WALK_TO:
				outer:
				try {
					for(Room a : getMap().getAdjacents(getCurrentRoom())) {
						if(a.getName().equals(p.getNextRoom())) {
							 if(getMap().readArc(getCurrentRoom(), a).isLocked()) {
								out.println("La porta sembra esser bloccata...");
								break outer;
							} else {
							setCurrentRoom(a);
							out.println(getCurrentRoom().getDescription());
							break;
						}
					}
					}
					if(!getCurrentRoom().getName().equals(p.getNextRoom())) {
						out.println("Nessuna stanza adiacente ha questo nome!");
					}

				}catch (Exception e) {
					// TODO: handle exception
				}
				break;
			default:
				out.println("Credo che tu sia un po' confuso...");

			}
		} else if(p.getObject()!=null && p.getTarget()==null) {
			p.getObject().getHandler().apply(p.getCommand().getType()).accept(this);
		} else if(p.getEnemy()!=null && p.getTarget()==null) {
			switch (p.getCommand().getType()) {
			case TALK_TO:
				out.println(p.getEnemy().getTalk());
				break;
			case BREAK:
				p.getEnemy().setHealth(p.getEnemy().getHealth()-5);
				break;
			case LOOK_AT:
				out.println(p.getEnemy().getDescription());
				if(p.getEnemy().getHealth() == 0) getCurrentRoom().getObjects().add(p.getEnemy().getDroppable());
				break;
			default:
				out.println("Credo che tu sia un po' confuso...");
				break;
			}
		} else if(p.getObject()!=null && p.getTarget()!=null) {
			switch (p.getCommand().getType()) {
			case DROP:
				if(p.getTarget() instanceof ItemContainer) {
					p.getObject().getHandler().apply(CommandType.DROP);
					getCurrentRoom().getObjects().remove(p.getObject());
					((ItemContainer) p.getTarget()).add(p.getObject());
				}else {
					out.println("Credo che tu sia un po' confuso...");
				}
				break;
			case BREAK:
				if(p.getTarget() instanceof Weapon) {
					p.getObject().getHandler().apply(CommandType.BREAK);
					Integer s = ((Weapon) p.getTarget()).getShots();
					((Weapon) p.getTarget()).setShots(s-1);
				}else {
					out.println("Credo che tu sia un po' confuso...");
				}
				break;
			default:
				out.println("Credo che tu sia un po' confuso...");
				break;
			}
		}
	}



}
