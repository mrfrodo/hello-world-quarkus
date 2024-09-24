package example.com;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

@Path("/")
public class HelloResource {

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "hello";
    }

    @GET()
    @Path("/r")
    public String random() {
        String resourceDirectory = Paths.get("src", "main", "resources").toFile().getAbsolutePath();
        File file = new File(resourceDirectory + File.separator + "random_1000000.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            Random random = new Random();
            for (int i = 0; i < 1000000; i++) {
                int randomInt = random.nextInt(1000001);
                writer.write(Integer.toString(randomInt));
                writer.newLine();
            }
        } catch (IOException e) {
            return "Error occurred while creating the file";
        }
        return "File created successfully at: " + file.getAbsolutePath();
    }

    @GET
    @Path("/s")
    @Produces(MediaType.TEXT_PLAIN)
    public List<Integer> sort() {
        Long timestamp = System.currentTimeMillis();
        String resourceDirectory = Paths.get("src", "main", "resources").toFile().getAbsolutePath();
        File inputFile = new File(resourceDirectory + File.separator + "random_1000000.txt");
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                numbers.add(Integer.parseInt(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(numbers);
        Long timeUsed = System.currentTimeMillis() - timestamp;
        System.out.println(String.format("\n\n************* TIME USED %s ms.", timeUsed));
        return numbers;
    }
}
