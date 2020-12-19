import java.util.Random;
import java.util.ArrayList;

public class CellularAutomata {

    int column;
    int rack;
    int host;
    int totalLoad;
    float fitness;
    int dataCenter[][][];

    public CellularAutomata(int length, int width, int height, int load, boolean print) {
        this.column = length;
        this.rack = width;
        this.host = height;
        this.totalLoad = load;
        this.dataCenter = new int[this.column][this.rack][this.host];

        load = load - (this.column * this.rack * this.host);
        
        for(int columnIndex = 0; columnIndex < this.column; columnIndex++) {
            for(int rackIndex = 0; rackIndex < this.rack; rackIndex++) {
                for(int hostIndex = 0; hostIndex < this.host; hostIndex++) {
                    dataCenter[columnIndex][rackIndex][hostIndex] = new Random().nextInt(load);
                }
            }
        }

        this.dataCenter[this.column - 1][this.rack - 1][this.host - 1] = load;

		this.dataCenter = CAsorter.sort(this.dataCenter);
    }

    public void fitness() {
        this.fitness = new Random().nextFloat();
        // Fitness using the power model from cloud simulator
    }

    public CellularAutomata evolve() {
        CellularAutomata child = new CellularAutomata(this.column, this.rack, this.host, this.totalLoad, false);

        for(int columnIndex = 0; columnIndex < this.column; columnIndex++) {
            for(int rackIndex = 0; rackIndex < this.rack; rackIndex++) {
                for(int hostIndex = 0; hostIndex < this.host; hostIndex++) {
                    child.dataCenter[columnIndex][rackIndex][hostIndex] = this.dataCenter[columnIndex][rackIndex][hostIndex];
                }
            }
        }

        int randomProbabilityLeft = new Random().nextInt(10);
        int randomProbabilityRight = new Random().nextInt(10);
        int randomProbabilityUp = new Random().nextInt(10);
        int randomProbabilityDown = new Random().nextInt(10);
        int randomProbabilityFront = new Random().nextInt(10);
        int randomProbabilityBack = new Random().nextInt(10);

        int totalProbability = randomProbabilityLeft + randomProbabilityRight + randomProbabilityUp + randomProbabilityDown + randomProbabilityFront + randomProbabilityBack;
        ArrayList<String> probabilityStrings = new ArrayList<String>();

        probabilityStrings = fillString(probabilityStrings, "Left", randomProbabilityLeft);
        probabilityStrings = fillString(probabilityStrings, "Right", randomProbabilityRight);
        probabilityStrings = fillString(probabilityStrings, "Up", randomProbabilityUp);
        probabilityStrings = fillString(probabilityStrings, "Down", randomProbabilityDown);
        probabilityStrings = fillString(probabilityStrings, "Front", randomProbabilityFront);
        probabilityStrings = fillString(probabilityStrings, "Back", randomProbabilityBack);

        int firstColumn = 0;
        int lastColumn = this.column - 1;
        int firstRack = 0;
        int lastRack = this.rack - 1;
        int firstHost = 0;
        int lastHost = this.host - 1;

        for(int columnIndex = 0; columnIndex < this.column; columnIndex++) {
            for(int rackIndex = 0; rackIndex < this.rack; rackIndex++) {
                for(int hostIndex = 0; hostIndex < this.host; hostIndex++) {
                    int selectedStringIndex = new Random().nextInt(totalProbability);
                    int temp;
                    switch (probabilityStrings.get(selectedStringIndex)) {
                        case "Left":
                            if((hostIndex - 1) >= firstHost) {
                                temp = child.dataCenter[columnIndex][rackIndex][hostIndex - 1];
                                child.dataCenter[columnIndex][rackIndex][hostIndex - 1] = child.dataCenter[columnIndex][rackIndex][hostIndex];
                                child.dataCenter[columnIndex][rackIndex][hostIndex] = temp;
                                break;
                            } else {
                                temp = child.dataCenter[columnIndex][rackIndex][lastHost];
                                child.dataCenter[columnIndex][rackIndex][lastHost] = child.dataCenter[columnIndex][rackIndex][hostIndex];
                                child.dataCenter[columnIndex][rackIndex][hostIndex] = temp;
                                break;
                            }

                        case "Right":
                            if((hostIndex + 1) <= lastHost) {
                                temp = child.dataCenter[columnIndex][rackIndex][hostIndex + 1];
                                child.dataCenter[columnIndex][rackIndex][hostIndex + 1] = child.dataCenter[columnIndex][rackIndex][hostIndex];
                                child.dataCenter[columnIndex][rackIndex][hostIndex] = temp;
                                break;
                            } else {
                                temp = child.dataCenter[columnIndex][rackIndex][firstHost];
                                child.dataCenter[columnIndex][rackIndex][firstHost] = child.dataCenter[columnIndex][rackIndex][hostIndex];
                                child.dataCenter[columnIndex][rackIndex][hostIndex] = temp;
                                break;
                            }

                        case "Up":
                            if((rackIndex - 1) >= firstRack) {
                                temp = child.dataCenter[columnIndex][rackIndex - 1][hostIndex];
                                child.dataCenter[columnIndex][rackIndex - 1][hostIndex] = child.dataCenter[columnIndex][rackIndex][hostIndex];
                                child.dataCenter[columnIndex][rackIndex][hostIndex] = temp;
                                break;
                            } else {
                                temp = child.dataCenter[columnIndex][lastRack][hostIndex];
                                child.dataCenter[columnIndex][lastRack][hostIndex] = child.dataCenter[columnIndex][rackIndex][hostIndex];
                                child.dataCenter[columnIndex][rackIndex][hostIndex] = temp;
                                break;
                            }

                        case "Down":
                            if((rackIndex + 1) <= lastRack) {
                                temp = child.dataCenter[columnIndex][rackIndex + 1][hostIndex];
                                child.dataCenter[columnIndex][rackIndex + 1][hostIndex] = child.dataCenter[columnIndex][rackIndex][hostIndex];
                                child.dataCenter[columnIndex][rackIndex][hostIndex] = temp;
                                break;
                            } else {
                                temp = child.dataCenter[columnIndex][firstRack][hostIndex];
                                child.dataCenter[columnIndex][firstRack][hostIndex] = child.dataCenter[columnIndex][rackIndex][hostIndex];
                                child.dataCenter[columnIndex][rackIndex][hostIndex] = temp;
                                break;
                            }

                        case "Front":
                            if((columnIndex - 1) >= firstColumn) {
                                temp = child.dataCenter[columnIndex - 1][rackIndex][hostIndex];
                                child.dataCenter[columnIndex - 1][rackIndex][hostIndex] = child.dataCenter[columnIndex][rackIndex][hostIndex];
                                child.dataCenter[columnIndex][rackIndex][hostIndex] = temp;
                                break;
                            } else {
                                temp = child.dataCenter[lastColumn][rackIndex][hostIndex];
                                child.dataCenter[lastColumn][rackIndex][hostIndex] = child.dataCenter[columnIndex][rackIndex][hostIndex];
                                child.dataCenter[columnIndex][rackIndex][hostIndex] = temp;
                                break;
                            }

                        case "Back":
                            if((columnIndex + 1) <= lastColumn) {
                                temp = child.dataCenter[columnIndex + 1][rackIndex][hostIndex];
                                child.dataCenter[columnIndex + 1][rackIndex][hostIndex] = child.dataCenter[columnIndex][rackIndex][hostIndex];
                                child.dataCenter[columnIndex][rackIndex][hostIndex] = temp;
                                break;
                            } else {
                                temp = child.dataCenter[firstColumn][rackIndex][hostIndex];
                                child.dataCenter[firstColumn][rackIndex][hostIndex] = child.dataCenter[columnIndex][rackIndex][hostIndex];
                                child.dataCenter[columnIndex][rackIndex][hostIndex] = temp;
                                break;
                            }
                    }
                }
            }
        }
        return child;
    }

    public ArrayList<String> fillString(ArrayList<String> probabilityStrings, String stringToFill, int times) {
        for(int i = 0; i < times; i++) {
            probabilityStrings.add(stringToFill);
        }
        return probabilityStrings;
    }

    public void mutate(float mutationRate) {
        for(int columnIndex = 0; columnIndex < this.column; columnIndex++) {
            for(int rackIndex = 0; rackIndex < this.rack; rackIndex++) {
                for(int hostIndex = 0; hostIndex < this.host; hostIndex++) {
                    if(new Random().nextFloat() < mutationRate) {
                        int operationToBeSelected = new Random().nextInt(2);
                        int randomColumn = new Random().nextInt(this.column);
                        int randomRack = new Random().nextInt(this.rack);
                        int randomHost = new Random().nextInt(this.host);

                        if (operationToBeSelected == 0) {
                            int randomValueToSubract = new Random().nextInt(this.dataCenter[columnIndex][rackIndex][hostIndex]);
                            this.dataCenter[columnIndex][rackIndex][hostIndex] = this.dataCenter[columnIndex][rackIndex][hostIndex] - randomValueToSubract;
                            this.dataCenter[randomColumn][randomRack][randomHost] = this.dataCenter[randomColumn][randomRack][randomHost] + randomValueToSubract;
                        } else {
                            int randomValueToAdd = new Random().nextInt(this.dataCenter[randomColumn][randomRack][randomHost]);
                            this.dataCenter[columnIndex][rackIndex][hostIndex] = this.dataCenter[columnIndex][rackIndex][hostIndex] + randomValueToAdd;
                            this.dataCenter[randomColumn][randomRack][randomHost] = this.dataCenter[randomColumn][randomRack][randomHost] - randomValueToAdd;
                        }
                    }
                }
            }
        }
    }
}
