package sisamart.ExpenseManager.Main;

import sisamart.ExpenseManager.CommandProcessor;

/**
 * @author Silvia Martinkovičová
 */
public class Main {

    public static void main(String[] args){
        System.out.println("Welcome to Expense Manager!" + System.lineSeparator());

        CommandProcessor processor = new CommandProcessor();
        System.out.println("Type 'help' to show your available commands" + System.lineSeparator());

        processor.process();
    }

}
