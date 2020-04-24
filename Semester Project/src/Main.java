import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Main extends Application {
	
	//belonging to LogInPanel
	@FXML private Button LogIn; 
	@FXML private Button SignUp;
	@FXML private Button Exit1; 
	@FXML private TextField UserNameText;
	@FXML private TextField PasswordText;
	
	//belonging to CreatePanel
	@FXML private Button Exit2; 
	@FXML private Button SignUp2; 
	@FXML private Button BackToLogIn; 
	@FXML private TextField UserCreateText;
	@FXML private TextField PasswordCreateText;
	@FXML private Label Exists;
	@FXML private Label Successful;
	
	//belonging to DataDisplayPanel
	@FXML private Button Exit3; 
	@FXML private Button Display;
	//table and columns, and checkboxes need to be added

public void start(Stage LogIn) throws IOException {
	// the first stage is LogIn, logIn requires a connection to the database to check if the username and the password are a match (further down)

		LogIn.setTitle("LogIn");  
		FXMLLoader loaderLogIn = new FXMLLoader();
		loaderLogIn.setLocation(getClass().getResource("LogInPanel.fxml")); 
		Scene LogInScene = new Scene(loaderLogIn.load());
		
		LogIn.setScene(LogInScene);
		LogIn.show();
		
		
	}
	public static void main(String[] args) {
		JDBCConnector connection = new JDBCConnector();
		//connection.connect("localhost", 5432, "postgres", "3111");
		connection.connect("balarama.db.elephantsql.com", 5432, "mcyaufsc", "XRc4UXD8j4hWUgvdeR32gspZCm7PVRud");
		
		ObservationDataGenerator generator = new ObservationDataGenerator(connection);
		// Activate the generator to create more observations
		generator.start();
		
		ApplicationGUI gui = new ApplicationGUI(connection);


		{ 
			launch(args); // will launch LogIn.show()
			}
		
		
		public void handleLogIn (ActionEvent e) throws IOException
		{ 
			if(e.getSource() == LogIn) //misses the database check for the User/password table
			{	
					FXMLLoader loaderDisplay = new FXMLLoader(); 
					loaderDisplay.setLocation(getClass().getResource("DataDisplayPanel.fxml")); 
		
					Scene scene2 = new Scene(loaderDisplay.load());
					Stage display = new Stage(); 
					display.setTitle("Display");
					display.setScene(scene2);
					display.show();
			}
			if(e.getSource() == SignUp) 
			{
				FXMLLoader loaderUserCreator = new FXMLLoader();
				loaderUserCreator.setLocation(getClass().getResource("UserCreator.fxml")); 
				Scene scene3 = new Scene(loaderUserCreator.load()); 
						
				Scene scene3 = new Scene(loaderUserCreator.load());
				Stage UserCreator = new Stage(); 
				UserCreator.setTitle("Sign Up");
				UserCreator.setScene(scene3);
				UserCreator.show();
			}
		}
			if(e.getSource() == Exit1) // when pressing the button Exit, the program will terminate
			{ 
				System.exit(0);
			}
		
		public void handleSignUp (ActionEvent e) throws IOException
		{ //misses the check if Username is unique (database) and if(e.getSource() == SignUp2) 
			if(e.getSource() == BackToLogIn) 
			{ 
				FXMLLoader loaderLogIn2 = new FXMLLoader();
				loaderLogIn2.setLocation(getClass().getResource("LogInPanel.fxml")); 
				Scene LogInScene = new Scene(loaderLogIn2.load());				
								
				Scene scene4 = new Scene(loaderLogIn2.load());
				Stage LogIn = new Stage(); 
				LogIn.setTitle("Log In");
				LogIn.setScene(scene4);
				LogIn.show();
			}
			
			if(e.getSource() == Exit2)
			{ 
				System.exit(0);
			}
		}

		public void handleDisplayData (ActionEvent e) throws IOException 
		{ //misses all connections to database and button functions
			if(e.getSource() == Exit3) 
			{ 
				System.exit(0);
			}
		}
	}
}