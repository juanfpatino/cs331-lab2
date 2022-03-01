public class SkolemFunction implements Qualifiable{

    public int num;
    private Variable x; //there exists (this) for all x

    public SkolemFunction(int num){

        this.num = num-48;

    }

    public Qualifiable getX(){

        return this.x;

    }

    public void setX(Variable x){

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
        if(x != null){

            return getName() + "(" + x.getName() + ")";

        }
        else{

            return getName();

        }
    }
}
