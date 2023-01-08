package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // write your code here

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        System.out.print("Enter your name: ");
        
        String userInputName = scanner.nextLine();
        System.out.printf("\r\nHello, %s\r\n", userInputName);
        
        int userScore = getUserRating(userInputName);

        String gameParameters = scanner.nextLine();
        System.out.println("Okay, let's start");
        if (gameParameters.isEmpty()){
            gameParameters = "rock,paper,scissors";
        }

        while(true) {
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "rock","gun","lightning","devil","dragon","water","air","paper","sponge","wolf","tree","human","snake","scissors","fire":
                    GameOptions userOption = GameOptions.valueOf(userInput.toUpperCase());
                    int optionId = random.nextInt(0, gameParameters.split(",").length);
                    GameOptions pcOption = GameOptions.values()[optionId];
                    int score = printGameOutput(userOption, pcOption, gameParameters);
                    userScore += score;
                    break;
                case "!exit" :
                    System.out.println("Bye!");
                    return;
                case "!rating":
                    System.out.printf("Your rating: %d\r\n", userScore);
                default:
                    System.out.println("Invalid input");
                    break;
            }

        }
//
//        switch (userOption) {
//            case SCISSORS:
//
//                System.out.println("Sorry, but the computer chose rock");
//                break;
//            case ROCK:
//                System.out.println("Sorry, but the computer chose paper");
//                break;
//            case PAPER:
//                System.out.println("Sorry, but the computer chose scissors");
//                break;
//        }

    }

    public static int getUserRating(String userInputName) {
        try (Scanner scanner = new Scanner(new File("rating.txt"))) {
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if (line.contains(userInputName)) {
                    return Integer.parseInt(line.split(" ")[1]);

                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static int printGameOutput(GameOptions userOption, GameOptions pcOption) {
        if (userOption == pcOption) {
            System.out.println(String.format("There is a draw (%s)", userOption.name().toLowerCase()));
            return 50;
        } else if (userOption == GameOptions.SCISSORS && pcOption == GameOptions.PAPER ||
                userOption == GameOptions.ROCK && pcOption == GameOptions.SCISSORS ||
                userOption == GameOptions.PAPER && pcOption == GameOptions.ROCK) {
            System.out.println(String.format("Well done. The computer chose %s and failed", pcOption.name().toLowerCase()));
            return 100;
        } else {
            System.out.println(String.format("Sorry, but the computer chose %s", pcOption.name().toLowerCase()));
            return 0;
        }
    }

    public static int printGameOutput(GameOptions userOption, GameOptions pcOption, String gameParameters) {

        String userOptionText = userOption.name().toLowerCase();
        String pcOptionText = pcOption.name().toLowerCase();

        String[] gameParametersArray = gameParameters.split(userOptionText);
        List<String> leftSideArray = new ArrayList<>(List.of(gameParametersArray[0].split(",")));
        List<String> rightSideArray = gameParametersArray.length == 1 ? new ArrayList<>() : new ArrayList<>(List.of(gameParametersArray[1].split(",")));
        leftSideArray.remove("");
        rightSideArray.remove("");
        while (rightSideArray.size() != leftSideArray.size()) {

            if (rightSideArray.size() < leftSideArray.size()) {
                String currentOption = leftSideArray.remove(0);
                rightSideArray.add(currentOption);
            } else {
                String currentOption = rightSideArray.remove(rightSideArray.size()-1);
                leftSideArray.add(currentOption);
            }
        }



        if (userOption == pcOption) {
            System.out.println(String.format("There is a draw (%s)", userOption.name().toLowerCase()));
            return 50;
        } else if (leftSideArray.contains(pcOptionText)) {
            System.out.println(String.format("Well done. The computer chose %s and failed", pcOption.name().toLowerCase()));
            return 100;
        } else {
            System.out.println(String.format("Sorry, but the computer chose %s", pcOption.name().toLowerCase()));
            return 0;
        }
    }
}
