import java.util.ArrayList;
import java.util.Random;

public class CApopulation {
    CellularAutomata[] caPopulation;
    ArrayList<CellularAutomata> matingPool;
    int generation;
    float mutationRate;

    public CApopulation(int populationCount, float mutationRate) {
        this.caPopulation = new CellularAutomata[populationCount];

        int columns = Integer.parseInt(Configuration.getConfig("Columns"));
        int racks = Integer.parseInt(Configuration.getConfig("Racks"));
        int hosts = Integer.parseInt(Configuration.getConfig("Hosts"));
        int totalLoad = Integer.parseInt(Configuration.getConfig("TotalLoad"));

        boolean print = true;
        for(int caIndex = 0; caIndex < this.caPopulation.length; caIndex++) {
            this.caPopulation[caIndex] = new CellularAutomata(columns, racks, hosts, totalLoad, print);
            print = false;
        }

        calcFitness();

        this.matingPool = new ArrayList<CellularAutomata>();
        this.generation = 1;
        this.mutationRate = mutationRate;
    }

    public void calcFitness() {
        for(int caIndex = 0; caIndex < this.caPopulation.length; caIndex++) {
            this.caPopulation[caIndex].fitness();
        }
    }

    public void naturalSelection() {
        this.matingPool.clear();
        float maxFitness = 0;

        for(int caIndex = 0; caIndex < this.caPopulation.length; caIndex++) {
            if(this.caPopulation[caIndex].fitness > maxFitness) {
                maxFitness = this.caPopulation[caIndex].fitness;
            }
        }

        for(int caIndex = 0; caIndex < this.caPopulation.length; caIndex++) {
            float fitness = this.caPopulation[caIndex].fitness/maxFitness;
            int n = (int) fitness * 100;
            for(int i = 0; i < n; i++) {
                this.matingPool.add(this.caPopulation[caIndex]);
            }
        }
    }

    public void generate() {
        for(int caIndex = 0; caIndex < this.caPopulation.length; caIndex++) {
            int p = new Random().nextInt(this.matingPool.size());
            CellularAutomata parent = this.matingPool.get(p);
            this.caPopulation[caIndex] = parent.evolve();
            this.caPopulation[caIndex].mutate(mutationRate);
        }
        this.generation++;
    }

    public CellularAutomata getBest() {
        float fittest = (float) 0.0;
        int index = 0;
        for(int caIndex = 0; caIndex < this.caPopulation.length; caIndex++) {
            if(this.caPopulation[caIndex].fitness > fittest) {
                fittest = this.caPopulation[caIndex].fitness;
                index = caIndex;
            }
        }
        return this.caPopulation[index];
    }

    public int getGeneration() {
        return generation;
    }

    public float getAverageFitness() {
        float total = 0;
        for(int caIndex = 0; caIndex < this.caPopulation.length; caIndex++) {
            total += this.caPopulation[caIndex].fitness;
        }
        return total/(this.caPopulation.length);
    }
}
