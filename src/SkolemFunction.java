public class SkolemFunction implements Qualifiable{

    private int num;
    private Qualifiable x; //there exists (this) for all x

    public SkolemFunction(int num){

        this.num = num;

    }

    public Qualifiable getX(){

        return this.x;

    }

    public void setX(Qualifiable x){

        this.x = x;

    }

    public String getName() {
        return "SKF" + num;
    }

    public void setName(String name) {
        this.num = Integer.parseInt(name);
    }

    @Override
    public String toString() {
        return getName() + "(" + x.getName() + ")";
    }
}
