public class Main {



	public static void main(String[] args) {

		JDBCConnector connection = new JDBCConnector();

		//connection.connect("localhost", 5432, "postgres", "3111");

		connection.connect("balarama.db.elephantsql.com", 5432, "mcyaufsc", "XRc4UXD8j4hWUgvdeR32gspZCm7PVRud");

		

		ObservationDataGenerator generator = new ObservationDataGenerator(connection);

		// Activate the generator to create more observations

		generator.start();

		

		ApplicationGUI gui = new ApplicationGUI(connection);



	}

}