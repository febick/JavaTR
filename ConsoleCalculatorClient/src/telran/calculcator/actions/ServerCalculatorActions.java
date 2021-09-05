package telran.calculcator.actions;

import java.io.IOException;
import java.util.*;

import telran.consoleCalculator.service.ClientCalculatorTcp;
import telran.view.InputOutput;
import telran.view.Item;

public class ServerCalculatorActions {

	private static ClientCalculatorTcp service;

	public ServerCalculatorActions(ClientCalculatorTcp service) {
		ServerCalculatorActions.service = service;
	};

	public Item[] getActions() {
		return items();
	}

	private static Item[] items() {
		List<Item> res = new ArrayList<>();
		res.add(Item.of("Add", ServerCalculatorActions::add));
		res.add(Item.of("Subtract", ServerCalculatorActions::subtract));
		res.add(Item.of("Multiply", ServerCalculatorActions::multiply));
		res.add(Item.of("Divide", ServerCalculatorActions::divide));
		res.add(Item.of("Write custom expression", ServerCalculatorActions::custom));
		res.add(Item.of("Exit", io -> {
			service.write("exit");
		}, true));
		return res.toArray(new Item[0]);
	}

	private static void add(InputOutput io) {
		makeExpression(io, "+");
	}

	private static void subtract(InputOutput io) {
		makeExpression(io, "-");
	}

	private static void multiply(InputOutput io) {
		makeExpression(io, "*");
	}

	private static void divide(InputOutput io) {
		makeExpression(io, "/");
	}
	
	private static void custom(InputOutput io) {
		var expression = io.readString("Enter expression: ");
		getResult(io, expression);
	}

	private static void makeExpression(InputOutput io, String operator) {
		var firstValue = getNumber(io, true);
		var secondValue = getNumber(io, false);
		var expression = String.format("%d%s%d", firstValue, operator, secondValue); 
		getResult(io, expression);
	}

	private static void getResult(InputOutput io, String expression) {
		service.write(expression);
		try {
			io.writeObjectLine(service.read());
		} catch (IOException e) {
			io.writeObjectLine(e.getLocalizedMessage());
		}
	}

	private static int getNumber(InputOutput io, Boolean isFirst) {
		return io.readInt(String.format("Enter %s number:", isFirst ? "first" : "second"));
	}

}
