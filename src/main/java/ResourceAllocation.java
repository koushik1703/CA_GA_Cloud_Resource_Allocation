import org.cloudbus.cloudsim.power.models.*;

public class ResourceAllocation {
    public static void main(String []args) {
        int populationCount = 100;
        CApopulation caPopulation = new CApopulation(populationCount);

        for(int i = 0; i < 100; i++) {
            caPopulation.naturalSelection();
            caPopulation.generate();

            caPopulation.calcFitness();
        }
        System.out.println("Completed Successfully!");
    }
}
