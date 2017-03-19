package net.tinmints.scounting.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

import net.tinmints.scounting.server.dao.Dao;
import net.tinmints.scounting.server.model.ScoutData;

/**
 * Class that implements an SPP Server which accepts single line of message from
 * an SPP client and sends a single line of response to the client.
 * 
 * test comment
 */
public class BlueToothServer extends Thread {

	public boolean run = true;
	private static UUID MY_UUID = new UUID("5c058be0dc1811e6bf26cec0c932ce01",false);
	Dao dao = Dao.newInstance();
	
	// start server
	public void run() {
		System.out.println("\nServer Started. Waiting for clients to connect…");
		// Create the servicve url btspp vs btl2cap vs btgoep
		String connectionString = "btspp://localhost:" + MY_UUID + ";name=Blue Tooth Scouting Server";
		
		// open server url
		StreamConnectionNotifier streamConnNotifier;
		try {
			streamConnNotifier = (StreamConnectionNotifier) Connector.open(connectionString);
			while(run) {
				// Wait for client connection
				
				StreamConnection connection = streamConnNotifier.acceptAndOpen();
				
				//System.out.println("connected?");
				// read string from spp client
				
				//	sleep(10000);
				
				InputStream inStream = connection.openInputStream();
				BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
				String lineRead = null;
				ScoutData data = new ScoutData();
				boolean getData=false;
				String match = null;
				String team = null;
				while(!(lineRead=bReader.readLine()).equals("done") ) {
					if(lineRead.startsWith("GET")) {
						getData=true;
						String[] p = lineRead.split(":");
						match = p[1];
						team = p[2];
					} else {
						//System.out.println(lineRead);
						String[] p = lineRead.split(":");
						String action = p[0];
						String value = null;
						if(p.length>1) {
							value = p[1];
							setData(action,value,data);
						}
					}
					
			    }
				String responseMessage = null;
				if(!getData) {
					try {
						String code = dao.insertData(data);
						responseMessage = "Match: " + data.getMatchNumber() + " Team: " + data.getTeamNumber() + " recieved and " + code + " \r\n";
						System.out.println(responseMessage);
					} catch(Exception e) {
						e.printStackTrace();
						responseMessage = e.getMessage() + "\r\n";
					}
				} else {
					try {
						ScoutData d = dao.getScoutData(Integer.parseInt(team), Integer.parseInt(match));
						if(d!=null) {
							responseMessage = serialize(d) + "done" + System.getProperty("line.separator");
							System.out.println("Data sent for match " + match + " and team " + team);
						} else {
							responseMessage = "no data"  + System.getProperty("line.separator") + "done" + System.getProperty("line.separator");
							System.out.println("No data for match " + match + " and team " + team);
						}
						
						
					} catch(Exception e) {
						e.printStackTrace();
						responseMessage = e.getMessage() + "\r\n";
					}
				}
				
				// send response to spp client
				OutputStream outStream = connection.openOutputStream();
				PrintWriter pWriter = new PrintWriter(new OutputStreamWriter(outStream));
				pWriter.write(responseMessage);
				pWriter.flush();
				
				//RemoteDevice dev = RemoteDevice.getRemoteDevice(connection);
				//System.out.println("Remote device address: " + dev.getBluetoothAddress());
				//System.out.println("Remote device name: " + dev.getFriendlyName(true));
				
				bReader.close();
				pWriter.close();
				
				
				//System.out.println("pass output");
				connection.close();
			}
			streamConnNotifier.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setData(String action, String value, ScoutData data) {
		try {
			if(action.startsWith("is")) {
				String field = action.replaceFirst("is", "");
				field = field.substring(0,1).toLowerCase() + field.substring(1);
				set(data,field,Boolean.parseBoolean(value));
			} else {
				String field = action.replaceFirst("get", "");
				field = field.substring(0,1).toLowerCase() + field.substring(1);
				if(data.getClass().getMethod(action).getReturnType().equals(Integer.TYPE)) {
					set(data,field,Integer.parseInt(value));
				} else {
					set(data,field,value);
				}
			}
		}catch(Throwable e) {
			System.out.println(action + " " + value);
			e.printStackTrace();
		}
		
	}
	
	public boolean set(Object object, String fieldName, Object fieldValue) {
		//System.out.println(fieldName + " " + fieldValue);
	    Class<?> clazz = object.getClass();
	    while (clazz != null) {
	        try {
	            Field field = clazz.getDeclaredField(fieldName);
	            field.setAccessible(true);
	            field.set(object, fieldValue);
	            //System.out.println("WAS SET"  + fieldName + "->" + fieldValue);
	            return true;
	        } catch (NoSuchFieldException e) {
	            clazz = clazz.getSuperclass();
	        } catch (Exception e) {
	            throw new IllegalStateException(e);
	        }
	    }
	    return false;
	}
	
    private String serialize(ScoutData data) {

        Method[] methods = data.getClass().getMethods();
        StringBuilder string = new StringBuilder();
        for(int i=0;i<methods.length;i++) {
            try {
                String name = methods[i].getName();
                if((name.startsWith("get") || name.startsWith("is")) && !name.startsWith("getClass")) {
                    string.append(methods[i].getName()).append(":").append(methods[i].invoke(data)).append(System.getProperty("line.separator"));
                }
            }catch(Exception e) {

            }

        }

        return string.toString();
    }
	
	
	public static void main(String args[]) {
		new BlueToothServer().start();
	}
}