import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServidorHttp {
    public static void main(String[] args) throws Exception {

        ServidorHttp main = new ServidorHttp();
        /** Crea un servidor http que usará el puerto 8080 para comunicarse,
         *  con la ip de tu host para comunicarse con tu red privada.
         */
        final ServerSocket server = new ServerSocket(8080, 0, InetAddress.getByName("192.168.1.36"));
        System.out.println("Escuchando en puerto 8080... ");

        /* El bucle que se usará para el funcionamiento del servidor */

        while (true) {

            /*
                Se creará un onjeto socket cada vez que un cliente se conecte al servidor, el programa estará
                bloqueado para con el metodo .accept() hasta que se conecte un cliente.
             */
            Socket client = server.accept();
            /*
               Se crea un inputStreamReader para obtener los datos del cliente. Los datos del cliente se usaran con un
               getInputStrema
             */
            InputStreamReader input = new InputStreamReader(client.getInputStream());
            /* Se creará el bufferReader para leer los datos del cliente de manera correcta e imprimirlos */
            BufferedReader buffer = new BufferedReader(input);
            /* Se creará la String linea que alamacenará lo que lea el buffer con readLine  */
            String linea = buffer.readLine();



            /* La respuesta que le daremos al cliente tiene que ser acorde al protocolo HTTP */
            String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + main.archivoHtml();

            String prueba =   '\r'+ "hola";
            client.getOutputStream()
                    .write(httpResponse.getBytes(StandardCharsets.UTF_8));
            client.getOutputStream()
                    .write(prueba.getBytes(StandardCharsets.UTF_8));
            client.close();

            /* Se usará un bucle en el que se imprimirá las lineas hasta que esten vacías. */
            while (!linea.isEmpty()){
                System.out.println(linea);
                linea = buffer.readLine();
            }
        }
    }

    /**
     * Invoca al archivo html que se mostrará en el servidor.
     * @return retorna el texto del archivo html.
     * @throws IOException
     */
    public String archivoHtml() throws IOException {
        /* Se invoca el objeto File con el html */
        File file = new File("src/main/prueba.html");
        /*Se invoca el FileReader para leer los datos del archivo */
        FileReader lector = new FileReader(file);
        /* Invocaremos el bufferReader  para leer los datos y usar sus metodos de maneras mas eficientes */
        BufferedReader buffer = new BufferedReader(lector);
        StringBuilder builder = new StringBuilder();
        /* Declaramos la variable linea para usarla con el StringBuilder mas adelante */
        String linea;
        /* Este while añadirá a la String linea, cada línea del buffer si es null en la String.
            Se usará el metodo append de String Builder para almacenar cada linea.
         */
        while ((linea = buffer.readLine()) != null) {
            builder.append(linea);
        }
        return builder.toString();
    }

}
