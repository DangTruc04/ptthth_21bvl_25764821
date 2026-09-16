package network;

import java.net.URI;
import java.net.URISyntaxException;

public class baidexuat1 {
	 public static void main(String[] args) { 
	        if (args.length != 1) { 
	            System.out.println("Usage: java network.HostInspector <hostname>"); 
	            return; 
	        } 
	        try {
	            URI uri = new URI(args[0]);

	            System.out.println("URI: " + args[0]);
	            System.out.println("Scheme: " + uri.getScheme());
	            System.out.println("Host: " + uri.getHost());
	            System.out.println("Port: " + uri.getPort());
	            System.out.println("Path: " + uri.getPath());
	            System.out.println("Query: " + uri.getQuery());
	            System.out.println("Fragment: " + uri.getFragment());

	        } catch (URISyntaxException e) {
	            System.err.println(
	                "URI không hợp lệ: " + args[0]
	            );
	        }
	   }
}
