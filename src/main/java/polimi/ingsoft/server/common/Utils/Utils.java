package polimi.ingsoft.server.common.Utils;

import polimi.ingsoft.client.ui.cli.IPChoiceCLI;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Utils {
    public static String getRandomNickname() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }

    /**
     * Allows to exclude localhost Network Interfaces and IPV6 interfaces
     *
     * @param out allows to print log
     * @return the proper Client's IP
     * @throws UnknownHostException whenever the host is unknown
     */
    public static String getHostAddress(PrintStream out) throws UnknownHostException {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                // Skip loopback and non-up interfaces
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();

                    // Skip loopback addresses and local addresses
                    if (inetAddress.isLoopbackAddress() || inetAddress.getHostAddress().equals("127.0.0.1")) {
                        continue;
                    } else if (IPChoiceCLI.isValidIP(inetAddress.getHostAddress())) {
                        return inetAddress.getHostAddress();
                    }
                }
            }

            throw new UnknownHostException();
        } catch (SocketException e) {
            e.printStackTrace(out);
            throw new UnknownHostException();
        }
    }
}
