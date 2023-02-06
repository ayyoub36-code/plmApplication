import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * @author Mehdioui_Ayyoub 1 févr. 2023
 * 
 */
public class Plane {

	public static void main(String[] args) {
		// hashmap pour stocker les avions => plane (id,programme,phase,type)
		Map<Integer, ArrayList<String>> planes = new HashMap<Integer, ArrayList<String>>();
		Scanner scan = new Scanner(System.in);

		// moyen d'afficher un tableau : 1 printLn() 2 boucle for 3 boucle foreach 4
		// utiliser iterator() avec la methode hasNext() 5 utiliser ListIterator
		planes.put(1,
				new ArrayList<>(Arrays.asList("A320", "PLM_AIRBUS_IN_SERVICE", "Passenger", "propeller", "engine")));
		planes.put(2,
				new ArrayList<>(Arrays.asList("A400M", "PLM_AIRBUS_DEFINITION", "Cargo", "landing-gear", "engine")));
		planes.put(3, new ArrayList<>(Arrays.asList("A350", "PLM_AIRBUS_FEASABILITY", "Passenger", "landing-gear")));
		planes.put(4, new ArrayList<>(Arrays.asList("A380", "PLM_AIRBUS_STOPPED", "Passenger", "propeller")));
		planes.put(5, new ArrayList<>(
				Arrays.asList("A300", "PLM_AIRBUS_CONSTRUCTION", "Bussiness", "wings", "engine", "landing-gear")));
		planes.put(6, new ArrayList<>(Arrays.asList("A340", "PLM_AIRBUS_IN_SERVICE", "Cargo", "engine")));
		planes.put(7,
				new ArrayList<>(Arrays.asList("A350", "PLM_AIRBUS_IN_SERVICE", "Passenger", "propeller", "engine")));

		// items for planes
		Map<String, ArrayList<String>> items = new HashMap<String, ArrayList<String>>();
		items.put("wings", new ArrayList<>(Arrays.asList("metal_raw_material", "700.50€")));
		items.put("tail", new ArrayList<>(Arrays.asList("composite_material", "1700.50€")));
		items.put("engine", new ArrayList<>(Arrays.asList("aluminum", "2700.50€")));
		items.put("propeller", new ArrayList<>(Arrays.asList("stainless steel", "520.50€")));
		items.put("landing-gear", new ArrayList<>(Arrays.asList("Ti-alloy", "1700.50€")));

		do {// gérer le choix de l'utilisateur
			bienvenuMenu();
			menuPlm(planes, scan, items);
		} while (showMenu(scan));
	}

	/**
	 * @param planes
	 * @param scan
	 * @param items
	 */
	private static boolean showMenu(Scanner scan) {
		System.out.println("\nVoulez vous rester dans le menu pricipale ? O/N");
		String response = scan.next();
		while (response.equals("o")) {
			return true;
		}
		return false;
	}

	/**
	 * @param planes
	 * @param scan
	 * @param items
	 */
	private static void menuPlm(Map<Integer, ArrayList<String>> planes, Scanner scan,
			Map<String, ArrayList<String>> items) {
		int choice = 0;
		// bienvenuMenu();

		// gérer les saisies erronnées !
		while (!scan.hasNextInt())
			scan.next();
		choice = scan.nextInt();

		if (choice == 1)
			showAll(planes);
		// tous les avions contenant un mot clé
		if (choice == 2) {
			System.out.println("Taper votre recherche : exemple(A300,A,A400...)");
			String search = scan.next();
			if (findAllByKey(search, planes).isEmpty()) {
				System.out.println("Aucune correspondance !");
			} else
				showAll(findAllByKey(search, planes));
		}

		// permettre d’ajouter(acheter) des pièces pour un avion donné
		if (choice == 3) {
			System.out.println("Voulez vous ajouter/1 ou supprimer/2 une pièce ? taper le numéro correspondant ");
			int choiceDeleteOrAdd = scan.nextInt();
			if (choiceDeleteOrAdd == 1) {
				System.out.println("Liste des pièces disponible au stock :");
				System.out.println();
				display(items);
				System.out.println();
				System.out.println("Saisissez le nom de la pièce que vous voulez ajouter :");
				String item = scan.next();
				if (item != "") {
					display2(planes);
					System.out.println("Saisissez l'id de l'avion que vous voulez modifier :");
					int idPlane = scan.nextInt();
					// verif saisie pour l id de l'avion
					while (idPlane < 1 || idPlane > planes.size()) {
						System.out.println("Veuillez choisir dans la liste !");
						idPlane = scan.nextInt();
					}
					addItem(idPlane, item, planes);
				}
			}
			if (choiceDeleteOrAdd == 2) {
				display2(planes);
				System.out.println("Saisissez l'id de l'avion que vous voulez modifier :");
				int idPlane = scan.nextInt();
				display(items);
				System.out.println();
				System.out.println("Saisissez le nom de la pièce que vous voulez supprimer :");
				String item = scan.next();
				deleteItem(idPlane, item, planes);
			}
		}
		// afficher les détails d'un avion
		if (choice == 4) {
			// trouver l'avion par son id
			System.out.println("veuillez entrer l id de l'avion que vous voulez qu'on détaille :");
			int idPlane = scan.nextInt();
			showOneById(idPlane, planes, items);

		}
		if (choice == 5) {
			System.out.println("A bientôt !");
			System.exit(0);
			scan.close();
		}
	}

	/**
	 * 
	 */
	private static void bienvenuMenu() {
		System.out.println(
				"Bienvenue dans l'application de gestion du cycle de vie d'avions AIRBUS, faites votre choix dans le menu :");
		System.out.println("\n1 : Afficher tous les avions");
		System.out.println("2 : Afficher tous les avions contenant un mot clé dans le programme");
		System.out.println("3 : Ajouter ou supprimer une pièce pour un avion donné");
		System.out.println("4 : Afficher un avion avec les infos détaillées de chaque pièce");
		System.out.println("5 : Quitter  l'application !");
	}

	/**
	 * find plane by id
	 * 
	 * @param idPlane demander l id de l avion
	 * @param planes  fournir le tableau d avions
	 * @param items   fournir un tableau de pièces
	 */
	public static void showOneById(int idPlane, Map<Integer, ArrayList<String>> planes,
			Map<String, ArrayList<String>> items) {
		// trouver l avion
		for (Entry<Integer, ArrayList<String>> entry : planes.entrySet())
			if (entry.getKey() == idPlane) {
				System.out.println(entry.getValue());
				// trouver toutes les pièces
				for (Entry<String, ArrayList<String>> entryItem : items.entrySet()) {
					for (int i = 0; i < entry.getValue().size(); i++) {
						if (entryItem.getKey().contains(entry.getValue().get(i))) {
							System.out.println(entryItem.getKey() + " = " + entryItem.getValue());
						}
					}
				}
			}
	}

	public static void deleteItem(int idPlane, String item, Map<Integer, ArrayList<String>> planes) {
		// supprimer une pièce
		for (Entry<Integer, ArrayList<String>> entry : planes.entrySet())
			if (entry.getKey() == idPlane) {
				entry.getValue().remove(item); // add item
				System.out.println();
				System.out.println("l'avion " + entry.getValue().get(0) + " à été modifier");
				System.out.println(entry.getKey() + " = " + entry.getValue());
			}
	}

	// trouver la liste des avions contenant un mot clé => programme
	public static Map<Integer, ArrayList<String>> findAllByKey(String search, Map<Integer, ArrayList<String>> planes) {
		Map<Integer, ArrayList<String>> listeSearched = new HashMap<Integer, ArrayList<String>>();
		int counter = 0;
		for (Entry<Integer, ArrayList<String>> entry : planes.entrySet()) {
			if (entry.getValue().get(0).contains(search))
				// System.out.println(entry.getValue());
				listeSearched.put(counter, entry.getValue());
			counter++;
		}
		return listeSearched;
	}

	public static void showAll(Map<Integer, ArrayList<String>> planes) {
		for (Entry<Integer, ArrayList<String>> entry : planes.entrySet())
			System.out.println(entry.getKey() + "=" + entry.getValue());
	}

	// addItems in planes
	public static void addItem(int idPlane, String item, Map<Integer, ArrayList<String>> planes) {
		// trouver l'avion by id
		for (Entry<Integer, ArrayList<String>> entry : planes.entrySet())
			if (entry.getKey() == idPlane) {
				if (entry.getValue().contains(item)) {
					System.out.println("cet pièce fait déja partie de notre avion !");
				} else {
					entry.getValue().add(item); // add item
					System.out.println();
					System.out.println("l'avion " + entry.getValue().get(0) + " à été modifier");
					System.out.println(entry.getKey() + " = " + entry.getValue());
				}

			}
	}

	public static void display(Map<String, ArrayList<String>> items) {
		for (Entry<String, ArrayList<String>> entry : items.entrySet())
			System.out.println(entry.getKey() + "=" + entry.getValue());
	}

	public static void display2(Map<Integer, ArrayList<String>> planes) {
		for (Entry<Integer, ArrayList<String>> entry : planes.entrySet())
			System.out.println(entry.getKey() + "=" + entry.getValue());
	}
}