import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientDemo {

	public static void main(String[] args) {
		Socket echoSocket = null;
		
		try {
			System.out.println("Client starting...");
			echoSocket = new Socket(Config.SOCKET_NAME, Config.SOCKET_PORT);

			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
	
			BufferedReader stdIn = new BufferedReader(
			                    new InputStreamReader(System.in));
	
			String userInput;
			while (!echoSocket.isClosed() && (userInput = stdIn.readLine()) != null)
			{	
				if (userInput.equals("quit")) {
					echoSocket.close();
				} else {
					//sending to server over socket output stream
					out.println(userInput);
					
					//receiving from server over socket input stream
					System.out.println("server at "+ echoSocket.getInetAddress().getHostAddress() + " responses with echo: " + in.readLine());
				}
			}
			
			if (echoSocket != null && !echoSocket.isClosed()) echoSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
			
			if (echoSocket != null && !echoSocket.isClosed()) {
				try {
					echoSocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			};
		}
		
		System.out.println("Client Done.");
	}

}
