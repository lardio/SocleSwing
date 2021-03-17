package fr.diginamic.swing.composants;

public abstract class MenuService {
	
	protected AbstractApplication application;
	
	protected Console console = new Console();
	
	public abstract void traitement();

	/**
	 * @return the application
	 */
	public AbstractApplication getApplication() {
		return application;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(AbstractApplication application) {
		this.application = application;
	}


}
