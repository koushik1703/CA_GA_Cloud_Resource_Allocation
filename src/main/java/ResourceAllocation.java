public class ResourceAllocation {
    public static void main(String []args) {

    	int populationCount = Integer.parseInt(Configuration.getConfig("Population"));
    	float mutationRate = Float.parseFloat(Configuration.getConfig("MutationRate"));
    	int numberOfEvolution = Integer.parseInt(Configuration.getConfig("NumberOfEvolution"));
    	
        CApopulation caPopulation = new CApopulation(populationCount, mutationRate);

        for(int i = 0; i < numberOfEvolution; i++) {
            caPopulation.naturalSelection();
            caPopulation.generate();

            caPopulation.calcFitness();
        }
        System.out.println("Completed Successfully!");
    }
}
