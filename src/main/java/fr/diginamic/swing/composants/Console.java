package fr.diginamic.swing.composants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;

import fr.diginamic.swing.composants.ui.Form;
import fr.diginamic.swing.composants.ui.Input;
import fr.diginamic.swing.composants.ui.Selectable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import fr.diginamic.swing.composants.annotations.TableGenerator;
import fr.diginamic.swing.composants.error.ErrorManager;
import fr.diginamic.swing.composants.html.HtmlUtils;
import fr.diginamic.swing.composants.validator.FormValidator;

/**
 * Classe qui propose quelques méthodes pour construire des composants
 * graphiques
 * 
 * @author RichardBONNAMY
 *
 */
public class Console {

	/** holder */
	public static final List<Boolean> holder = new LinkedList<Boolean>();

	/**
	 * FONT_18 : police par défaut utilisée pour la construction de tous les
	 * composants graphiques
	 */
	public static final Font FONT_10 = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
	public static final Font FONT_12 = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	public static final Font FONT_14 = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
	public static final Font FONT_16 = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
	public static final Font FONT_18 = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
	/**
	 * dimension : classe qui fournit des informations sur l'écran du PC utilisé et
	 * notamment ses dimensions. Cette classe intervient dans le calcul de centrage
	 * des composants graphiques
	 */
	private static Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

	/** texte du panneau d'affichage */
	private static StringBuilder contentpane = new StringBuilder();

	/** afficheur */
	public static JTextPane afficheur;

	/**
	 * Constructeur
	 * 
	 */
	public Console() {
	}

	/**
	 * Méthode utilitaire qui permet de calculer l'abscisse (X) idéale d'un
	 * composant graphique pour que celui-ci soit centré par rapport à l'écran de
	 * l'ordinateur utilisé.
	 * 
	 * @param component composant
	 * @return int
	 */
	public static int getY(Component component) {
		return (int) ((dimension.getHeight() - component.getHeight()) / 2);
	}

	/**
	 * Méthode utilitaire qui permet de calculer l'ordonnée (Y) idéale d'un
	 * composant graphique pour que celui-ci soit centré par rapport à l'écran de
	 * l'ordinateur utilisé.
	 * 
	 * @param component composant
	 * @return int
	 */
	public static int getX(Component component) {
		return (int) ((dimension.getWidth() - component.getWidth()) / 2);
	}

	/**
	 * Permet d'activer un formulaire SWING. La méthode est synchrone, c'est à dire
	 * que la méthode ne rend la main que lorsque le formateur a cliqué sur Valider
	 * (return true) ou Annuler (return false).
	 * 
	 * @param titreFormulaire titre du formulaire
	 * @param form            formulaire
	 * @return boolean
	 */
	public boolean input(String titreFormulaire, final Form form, final FormValidator formValidator) {

		JFrame fenetreRecherche = new JFrame(titreFormulaire);
		Container container = fenetreRecherche.getContentPane();
		container.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		// Lorsqu'on ferme une fenêtre secondaire on ne souhaite pas arrêter
		// l'application
		// mais simplement la masquer.
		fenetreRecherche.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// On réalise une boucle sur le tableau de questions pour générer et
		// positionner
		// les divers éléments sur la fenêtre secondaire

		Map<String, JComponent> fields = new HashMap<>();

		int maxWidth = 0;

		int maxInputWidth = 0;
		int maxLabelWidth = 0;
		for (Input input : form) {

			// Nombre de carcatères dans le texte de la question
			int nbCaracteres = input.getLabel().length();

			// Largeur approximative en pixels du texte à afficher
			int largeurLabel = nbCaracteres * 10 + 100;

			// Recherche du texte le plus large parmi tous les textes des questions
			if (largeurLabel > maxLabelWidth) {
				maxLabelWidth = largeurLabel;
			}
			if (input.getWidth() > maxInputWidth) {
				maxInputWidth = input.getWidth();
			}
		}

		maxWidth = maxLabelWidth + maxInputWidth + 30;
		// Calcul de son positionnement par défaut et de ses dimensions.
		// La hauteur dépend notamment du nombre de questions qu'on a à poser
		// à l'utilisateur

		fenetreRecherche.setBounds(100, 100, maxWidth, 110 + 40 * (form.size() - 1));
		// Calcul des coordonnées x et y pour que la fenêtre secondaire soit centrée.
		int x = Console.getX(fenetreRecherche);
		int y = Console.getY(fenetreRecherche);
		fenetreRecherche.setLocation(x, y);

		for (int i = 0; i < form.getInputs().size(); i++) {

			Input input = form.getInputs().get(i);

			// Création du label
			JLabel label = new JLabel(input.getLabel());
			label.setPreferredSize(new Dimension(200, 20));
			label.setSize(new Dimension(200, 20));
			label.setFont(FONT_14);
			fenetreRecherche.add(label);
			gbc.anchor = GridBagConstraints.LINE_START;
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.gridwidth=1;
			gbc.gridheight=1;
			gbc.weightx=0;
			gbc.weighty=0;
			gbc.insets = new Insets(5, 5, 0, 5);
			container.add(label, gbc);

			JComponent component = input.convert();
			component.setSize(new Dimension(input.getWidth(), 30));
			component.setPreferredSize(new Dimension(input.getWidth(), 30));
			component.setMaximumSize(new Dimension(input.getWidth(), 30));
			component.setName(input.getName());
			component.setFont(FONT_14);
			gbc.anchor = GridBagConstraints.LINE_START;
			gbc.gridx = 1;
			gbc.gridy = i;
			gbc.gridwidth=1;
			gbc.gridheight=1;
			gbc.weightx=1;
			gbc.weighty=1;
			gbc.fill = GridBagConstraints.BOTH;
			container.add(component, gbc);
			fields.put(form.getInputs().get(i).getName(), component);

		}

		// Création du bouton Valider

		JButton annuler = new JButton("Annul.");
		annuler.setSize(new Dimension(75, 25));
		annuler.setBackground(new Color(248, 215, 218));
		gbc.anchor = GridBagConstraints.LINE_END;
		gbc.gridx = 0;
		gbc.gridy = form.getInputs().size() + 1;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		gbc.fill = GridBagConstraints.NONE;
		// Lorsqu'on clique sur le bouton Valider on lance un système pour attendre la
		// saisie par l'utilisateur de la valeur demandée.
		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				form.setValidated(true);
				synchronized (Console.holder) {

					Console.holder.add(false);
					Console.holder.notify();
				}
				fenetreRecherche.setVisible(false);
			}
		});
		container.add(annuler, gbc);

		JButton valider = new JButton("Valider");
		valider.setSize(new Dimension(75, 25));
		valider.setBackground(new Color(0, 195, 255));
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.gridx = 1;
		gbc.gridy = form.getInputs().size() + 1;
		gbc.gridwidth=1;
		gbc.gridheight=1;
		gbc.fill=GridBagConstraints.NONE;
		

		// Lorsqu'on clique sur le bouton Valider on lance un système pour attendre la
		// saisie
		// par l'utilisateur de la valeur demandée.
		valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				synchronized (Console.holder) {
					form.setValidated(true);
					for (String name : fields.keySet()) {
						Input input = form.getInput(name);
						input.setValue(fields.get(name));
					}
					if (formValidator == null || (formValidator != null && formValidator.validate(form))) {
						fenetreRecherche.setVisible(false);
						Console.holder.add(true);
						Console.holder.notify();
					}
				}

			}
		});
		container.add(valider, gbc);
		fenetreRecherche.getRootPane().setDefaultButton(valider);
		fenetreRecherche.setVisible(true);

		synchronized (Console.holder) {
			// wait for input from field
			while (Console.holder.isEmpty()) {
				try {
					Console.holder.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			return Console.holder.remove(0);
		}
	}

	/**
	 * Permet d'activer un formulaire SWING. La méthode est synchrone, c'est à dire
	 * que la méthode ne rend la main que lorsque le formateur a cliqué sur Valider
	 * (return true) ou Annuler (return false).
	 * 
	 * @param titreFenetre titre du formulaire
	 * @param form         formulaire
	 * @return boolean
	 */
	public boolean confirm(String titreFenetre, String question) {

		JFrame fenetreRecherche = new JFrame(titreFenetre);

		// Lorsqu'on ferme une fenêtre secondaire on ne souhaite pas arrêter
		// l'application
		// mais simplement la masquer.
		fenetreRecherche.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Dans chaque fenêtre secondaire les composants graphiques seront positionnés
		// en coordonnées x,y d'où la suppression du layout
		fenetreRecherche.getContentPane().setLayout(null);

		// Par défaut les fenêtres secondaires ne sont pas visibles.
		fenetreRecherche.setVisible(false);

		int maxWidth = question.length() * 12;

		// Calcul de son positionnement par défaut et de ses dimensions.
		// La hauteur dépend notamment du nombre de questions qu'on a à poser
		// à l'utilisateur

		fenetreRecherche.setBounds(100, 100, maxWidth, 120);
		// Calcul des coordonnées x et y pour que la fenêtre secondaire soit centrée.
		int x = Console.getX(fenetreRecherche);
		int y = Console.getY(fenetreRecherche);
		fenetreRecherche.setLocation(x, y);

		JLabel label = new JLabel(question);
		label.setBounds(10, 18, maxWidth, 20);
		label.setFont(FONT_14);
		fenetreRecherche.add(label);

		// Création du bouton Valider

		JButton annuler = new JButton("Annul.");
		annuler.setBounds((maxWidth - 170) / 2, 50, 75, 25);
		annuler.setBackground(new Color(248, 215, 218));

		JButton valider = new JButton("Valider");
		valider.setBounds((maxWidth - 170) / 2 + 95, 50, 75, 25);
		valider.setBackground(new Color(0, 195, 255));

		// Lorsqu'on clique sur le bouton Valider on lance un système pour attendre la
		// saisie
		// par l'utilisateur de la valeur demandée.
		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				synchronized (Console.holder) {

					Console.holder.add(false);
					Console.holder.notify();
				}
				fenetreRecherche.setVisible(false);
			}
		});

		// Lorsqu'on clique sur le bouton Valider on lance un système pour attendre la
		// saisie
		// par l'utilisateur de la valeur demandée.
		valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				synchronized (Console.holder) {
					Console.holder.add(true);
					Console.holder.notify();

				}
				fenetreRecherche.setVisible(false);
			}
		});
		fenetreRecherche.add(annuler);
		fenetreRecherche.add(valider);
		fenetreRecherche.getRootPane().setDefaultButton(valider);
		fenetreRecherche.setVisible(true);

		synchronized (Console.holder) {
			// wait for input from field
			while (Console.holder.isEmpty()) {
				try {
					Console.holder.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			return Console.holder.remove(0);
		}
	}

	/**
	 * Permet d'activer un formulaire SWING. La méthode est synchrone, c'est à dire
	 * que la méthode ne rend la main que lorsque le formateur a cliqué sur Valider
	 * (return true) ou Annuler (return false).
	 * 
	 * @param titreFormulaire titre du formulaire
	 * @param form            formulaire
	 * @return boolean
	 */
	public void alert(String message) {

		JFrame fenetre = new JFrame("Alerte");

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets.bottom = 10;
		constraints.anchor = GridBagConstraints.CENTER;

		fenetre.getContentPane().setBackground(new Color(248, 215, 218));
		fenetre.getContentPane().setLayout(new GridBagLayout());
		fenetre.setBounds(100, 100, 500, 120);
		int x = Console.getX(fenetre);
		int y = Console.getY(fenetre);
		fenetre.setLocation(x, y);

		fenetre.setVisible(true);

		JLabel label = new JLabel(message);
		label.setFont(FONT_14);
		label.setForeground(new Color(114, 28, 36));
//		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		fenetre.add(label, constraints);

		JButton valider = new JButton("Fermer");
		constraints.gridx = 0;
		constraints.gridy = 2;
		fenetre.add(valider, constraints);
		valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				fenetre.setVisible(false);
			}
		});
	}

	/**
	 * Affiche un texte
	 * 
	 * @param text texte
	 * @return Console
	 */
	public Console print(String text) {

		String textModifie = traiteImages(text);
		appendToPane(textModifie);
		contentpane.append(textModifie);
		return this;
	}

	private String traiteImages(String textModifie) {
		boolean startWithTag = false;
		if (textModifie.startsWith("<")) {
			startWithTag = true;
		}

		// Si le texte contient des images, on modifie les sources
		if (textModifie.contains("img") && textModifie.contains("src")) {
			Document doc = Jsoup.parse(textModifie);
			Elements elts = doc.getElementsByAttribute("src");
			if (elts != null && elts.size() > 0) {
				for (Element elt : elts) {
					String value = elt.attr("src");

					URL url = getClass().getClassLoader().getResource(value);
					if (url != null) {
						elt.attr("src", url.toString());
					}
				}
			}
			if (startWithTag) {
				textModifie = doc.body().children().toString() + " " + doc.body().ownText();
			} else {
				textModifie = doc.body().ownText() + " " + doc.body().children().toString();
			}
		}
		return textModifie;
	}
	
	/**
	 * Affiche un texte HTML suivi d'un passage à la ligne
	 * 
	 * @param text texte
	 * @return Console.
	 */
	public Console println(String text) {
		return print(text).print("<br>");
	}

	/**
	 * Affiche un texte avec une couleur donnée ainsi que des attributs css au
	 * format suivant: propriété1: valeur1; propriété2: valeur2;
	 * 
	 * Le texte est ensuite suivi d'un passage à la ligne
	 * 
	 * @param text       texte
	 * @param attributes attributs CSS
	 * @return Console
	 */
	public Console println(String text, String... attributes) {
		return println(HtmlUtils.toSpan(text, attributes));
	}

	/**
	 * Affiche un texte avec une couleur donnée ainsi que des attributs css au
	 * format suivant: propriété1: valeur1; propriété2: valeur2;
	 * 
	 * @param text       texte
	 * @param c          couleur
	 * @param attributes attributs CSS
	 * @return Console
	 */
	public Console print(String text, Color c, String... attributes) {
		return print(HtmlUtils.toSpan(text, c, attributes));
	}

	/**
	 * Affiche un texte avec une couleur donnée ainsi que des attributs css au
	 * format suivant: propriété1: valeur1; propriété2: valeur2;
	 * 
	 * Le texte est ensuite suivi d'un passage à la ligne
	 * 
	 * @param text       texte
	 * @param c          couleur
	 * @param attributes attributs CSS
	 * @return Console
	 */
	public Console println(String text, Color c, String... attributes) {
		return println(HtmlUtils.toSpan(text, c, attributes));
	}

	/**
	 * Permet d'aligner sur une même ligne plusieurs éléments HTML (ex: image et
	 * texte)
	 * 
	 * @param htmlElts éléments HTML à aligner verticalement
	 * @return Console
	 */
	public Console printAlign(String... htmlElts) {
		StringBuilder htmlBuilder = new StringBuilder();
		htmlBuilder.append("<table><tr>");
		for (String texte : htmlElts) {
			htmlBuilder.append("<td>").append(texte).append("</td>");
		}
		htmlBuilder.append("</tr></table>");
		return println(htmlBuilder.toString());
	}
	
	/** Permet d'afficher un objet
	 * @param obj objet à afficher
	 * @return {@link Console}
	 */
	public Console print(Object obj) {
		return print(obj.toString());
	}
	
	/** Permet d'afficher un objet suivi d'un retour à la ligne
	 * @param obj objet à afficher
	 * @return {@link Console}
	 */
	public Console println(List<? extends Selectable<?>> selectables, Class<? extends Selectable<?>> classe) {
		return print(TableGenerator.toHtml(selectables, classe)).print("<br>");
	}


	/**
	 * Efface le contenu de l'afficheur
	 */
	public void clear() {
		contentpane = new StringBuilder();
		afficheur.setText("");
	}

	/**
	 * Ajoute du HTML dans le panneau
	 * 
	 * @param html html
	 * @return Console
	 */
	protected Console appendToPane(String html) {
		HTMLDocument doc = (HTMLDocument) afficheur.getStyledDocument();
		try {
			doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), html);
		} catch (IOException | BadLocationException e) {
			ErrorManager.manage(e.getMessage(), e);
		}
		return this;
	}
	
	/**
	 * Ajoute du HTML dans le panneau
	 * 
	 * @param html html
	 * @return Console
	 */
	public Console modifyTable(String id, List<? extends Selectable<?>> entites, Class<? extends Selectable<?>> classe) {
		HTMLDocument doc = (HTMLDocument) afficheur.getStyledDocument();
		javax.swing.text.Element elt = doc.getElement(id);
		try {
			doc.setOuterHTML(elt, traiteImages(TableGenerator.toHtml(entites, classe)));
		} catch (BadLocationException | IOException e) {
			ErrorManager.manage("Erreur lors de la modification du code HTML de la table id="+id, e);
		}
		return this;
	}

}
