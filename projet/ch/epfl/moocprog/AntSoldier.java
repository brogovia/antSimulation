package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_HP;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_LIFESPAN;

public class AntSoldier extends Ant {

	public AntSoldier(ToricPosition pos) {
		super(pos,getConfig().getInt(ANT_SOLDIER_HP), getConfig().getTime(ANT_SOLDIER_LIFESPAN));
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

}
