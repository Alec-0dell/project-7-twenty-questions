public class QuestionsGame {

	public boolean yesTo(String prompt) {
		System.out.print(prompt + "? (y/n) ");
		String response = console.nextLine().trim().toLowerCase();
		while (!response.equals("y") && !response.equals("n")) {
			System.out.println("Please answer y or n.");
			System.out.print(prompt + "? (y/n) ");
			response = console.nextLine().trim().toLowerCase();
		}
		return response.equals("y");
	}
}