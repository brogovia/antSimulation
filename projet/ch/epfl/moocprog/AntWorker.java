package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_HP;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_LIFESPAN;

public class AntWorker extends Ant {

	public AntWorker(ToricPosition pos) {
		super(pos,getConfig().getInt(ANT_WORKER_HP), getConfig().getTime(ANT_WORKER_LIFESPAN));
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
