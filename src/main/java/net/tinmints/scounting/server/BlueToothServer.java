package net.tinmints.scounting.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

/**
 * Class that implements an SPP Server which accepts single line of message from
 * an SPP client and sends a single line of response to the client.
 * 
 * test comment
 */
public class BlueToothServer extends Thread {

	public boolean run = true;
	private static UUID MY_UUID = new UUID("5c058be0dc1811e6bf26cec0c932ce01",false);
	
	
	// start server
	public void run() {

		// Create the servicve url btspp vs btl2cap vs btgoep
		String connectionString = "btspp://localhost:" + MY_UUID + ";name=Blue Tooth Scouting Server";
		
		// open server url
		StreamConnectionNotifier streamConnNotifier;
		try {
			streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);
			while(run) {
				// Wait for client connection
				System.out.println("\nServer Started. Waiting for clients to connect…");
				StreamConnection connection = streamConnNotifier.acceptAndOpen();
				
				System.out.println("connected?");
				// read string from spp client
				
				//	sleep(10000);
				
				InputStream inStream = connection.openInputStream();
				BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
				String lineRead = null;
				lineRead=bReader.readLine();
					System.out.println(lineRead);
				
				System.out.println("pass input");
				// send response to spp client
				OutputStream outStream = connection.openOutputStream();
				PrintWriter pWriter = new PrintWriter(new OutputStreamWriter(outStream));
				pWriter.write("Response String from SPP Server\r\n");
				pWriter.flush();
				
				//RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
				//System.out.println("Remote device address: " + dev.getBluetoothAddress());
				//System.out.println("Remote device name: " + dev.getFriendlyName(true));
				
				bReader.close();
				pWriter.close();
				
				
				System.out.println("pass output");
				connection.close();
			}
			streamConnNotifier.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		new BlueToothServer().start();
	}
}