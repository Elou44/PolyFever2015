package polyFever.module.menu;

public class BoutonMenuPlay extends Bouton {
	protected Menu pere;
	protected MenuPlay fils;
	
	public BoutonMenuPlay(Menu p, MenuPlay f, float x, float y, float l, float h){
		super(x, y, l, h);
		this.pere = p;
		this.fils = f;
		
	}
	
	
	@Override
	public Menu action() {
		return fils.changementMenu();
	}
}
