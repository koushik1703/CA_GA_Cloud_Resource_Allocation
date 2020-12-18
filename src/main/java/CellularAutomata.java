import java.util.Random;
import java.util.ArrayList;

public class CellularAutomata {

    int column;
    int rack;
    int host;
    int totalLoad;
    float fitness;
    int dataCenter[][][];

    public CellularAutomata(int length, int width, int height, int load) {
        this.column = length;
        this.rack = width;
        this.host = height;
        this.totalLoad = load;
        this.dataCenter = new int[this.column][this.rack][this.host];

        for(int i = 0; i < this.column; i++) {
            for(int j = 0; j < this.rack; j++) {
                for(int k = 0; k < this.host; k++) {
                    if(i == (this.column - 1)  && j == (this.rack - 1) && k == (this.host - 1))
                        this.dataCenter[i][j][k] = load;
                    else
                        this.dataCenter[i][j][k] = new Random().nextInt(load);
                    load = load - this.dataCenter[i][j][k];
                }
            }
        }
    }

    public void fitness() {
        this.fitness = new Random().nextFloat();
        // Fitness using the power model from cloud simulator
    }

    public CellularAutomata evolve() {
        CellularAutomata child = new CellularAutomata(this.column, this.rack, this.host, this.totalLoad);

        for(int i = 0; i < this.column; i++) {
            for(int j = 0; j < this.rack; j++) {
                for(int k = 0; k < this.host; k++) {
                    child.dataCenter[i][j][k] = this.dataCenter[i][j][k];
                }
            }
        }

        int randomProbabilityKminus = new Random().nextInt(10);
        int randomProbabilityKplus = new Random().nextInt(10);
        int randomProbabilityJminus = new Random().nextInt(10);
        int randomProbabilityJplus = new Random().nextInt(10);
        int randomProbabilityIminus = new Random().nextInt(10);
        int randomProbabilityIplus = new Random().nextInt(10);

        int totalProbability = randomProbabilityKminus + randomProbabilityKplus + randomProbabilityJminus + randomProbabilityJplus + randomProbabilityIminus + randomProbabilityIplus;
        ArrayList<String> probabilityStrings = new ArrayList<String>();

        probabilityStrings = fillString(probabilityStrings, "kMinus", randomProbabilityKminus);
        probabilityStrings = fillString(probabilityStrings, "kPlus", randomProbabilityKplus);
        probabilityStrings = fillString(probabilityStrings, "jMinus", randomProbabilityJminus);
        probabilityStrings = fillString(probabilityStrings, "jplus", randomProbabilityJplus);
        probabilityStrings = fillString(probabilityStrings, "iMinus", randomProbabilityIminus);
        probabilityStrings = fillString(probabilityStrings, "iplus", randomProbabilityIplus);

        for(int i = 0; i < this.column; i++) {
            for(int j = 0; j < this.rack; j++) {
                for(int k = 0; k < this.host; k++) {
                    int selectedStringIndex = new Random().nextInt(totalProbability);
                    int temp;
                    switch (probabilityStrings.get(selectedStringIndex)) {
                        case "kMinus":
                            if((k - 1) >= 0) {
                                temp = child.dataCenter[i][j][k - 1];
                                child.dataCenter[i][j][k - 1] = child.dataCenter[i][j][k];
                                child.dataCenter[i][j][k] = temp;
                                break;
                            } else {
                                temp = child.dataCenter[i][j][this.host - 1];
                                child.dataCenter[i][j][this.host - 1] = child.dataCenter[i][j][k];
                                child.dataCenter[i][j][k] = temp;
                                break;
                            }

                        case "kPlus":
                            if((k + 1) < this.host) {
                                temp = child.dataCenter[i][j][k + 1];
                                child.dataCenter[i][j][k + 1] = child.dataCenter[i][j][k];
                                child.dataCenter[i][j][k] = temp;
                                break;
                            } else {
                                temp = child.dataCenter[i][j][0];
                                child.dataCenter[i][j][0] = child.dataCenter[i][j][k];
                                child.dataCenter[i][j][k] = temp;
                                break;
                            }

                        case "jMinus":
                            if((j - 1) >= 0) {
                                temp = child.dataCenter[i][j - 1][k];
                                child.dataCenter[i][j - 1][k] = child.dataCenter[i][j][k];
                                child.dataCenter[i][j][k] = temp;
                                break;
                            } else {
                                temp = child.dataCenter[i][this.rack - 1][k];
                                child.dataCenter[i][this.rack - 1][k] = child.dataCenter[i][j][k];
                                child.dataCenter[i][j][k] = temp;
                                break;
                            }

                        case "jPlus":
                            if((j + 1) < this.rack) {
                                temp = child.dataCenter[i][j + 1][k];
                                child.dataCenter[i][j + 1][k] = child.dataCenter[i][j][k];
                                child.dataCenter[i][j][k] = temp;
                                break;
                            } else {
                                temp = child.dataCenter[i][0][k];
                                child.dataCenter[i][0][k] = child.dataCenter[i][j][k];
                                child.dataCenter[i][j][k] = temp;
                                break;
                            }

                        case "iMinus":
                            if((i - 1) >= 0) {
                                temp = child.dataCenter[i - 1][j][k];
                                child.dataCenter[i - 1][j][k] = child.dataCenter[i][j][k];
                                child.dataCenter[i][j][k] = temp;
                                break;
                            } else {
                                temp = child.dataCenter[this.column - 1][j][k];
                                child.dataCenter[this.column - 1][j][k] = child.dataCenter[i][j][k];
                                child.dataCenter[i][j][k] = temp;
                                break;
                            }

                        case "iPlus":
                            if((i + 1) < this.column) {
                                temp = child.dataCenter[i + 1][j][k];
                                child.dataCenter[i + 1][j][k] = child.dataCenter[i][j][k];
                                child.dataCenter[i][j][k] = temp;
                                break;
                            } else {
                                temp = child.dataCenter[0][j][k];
                                child.dataCenter[0][j][k] = child.dataCenter[i][j][k];
                                child.dataCenter[i][j][k] = temp;
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
}
