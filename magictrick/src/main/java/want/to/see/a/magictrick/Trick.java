package want.to.see.a.magictrick;

public abstract class Trick<T extends Secrets> {

	abstract public T onCreateSecret();

}