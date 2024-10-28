import java.io.*;
import java.net.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class ChatGPT {

    public static String chatGptEmailOptimization(String message)  throws IOException, InterruptedException, URISyntaxException{
        String apiKey = "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";  // Replace with your actual API key
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        try {
            // Create a URL object
            URI origurl = new URI(apiUrl);
            URL url = origurl.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String altmessage = "Don't add subject, or sender; and don't have any dialogue outside of the requested answer. Thank you. User Request: " + message;
          
            String jsonInputString = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"" + altmessage + "\"}]}";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

         
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                conn.disconnect();
                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray choices = jsonObject.getAsJsonArray("choices");
                JsonObject GPTmessage = choices.get(0).getAsJsonObject().getAsJsonObject("message");
                String content = GPTmessage.get("content").getAsString();
        
               
                return content;
            }

          
        } catch (IOException e) {
            e.printStackTrace();
            return "Something went wrong...";
        }
    }


    public static String chatGptGeneralQuestions(String message)  throws IOException, InterruptedException, URISyntaxException{
        String apiKey = "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";  // Replace with your actual API key
        String apiUrl = "https://api.openai.com/v1/chat/completions";

        try {
            // Create a URL object
            URI origurl = new URI(apiUrl);
            URL url = origurl.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String altmessage = "Only return answer to the question, do not add any other than the exact answer. User Request: " + message;
          
            String jsonInputString = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"user\", \"content\": \"" + altmessage + "\"}]}";

            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

         
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                conn.disconnect();
                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
                JsonArray choices = jsonObject.getAsJsonArray("choices");
                JsonObject GPTmessage = choices.get(0).getAsJsonObject().getAsJsonObject("message");
                String content = GPTmessage.get("content").getAsString();
        
               
                return content;
            }

          
        } catch (IOException e) {
            e.printStackTrace();
            return "Something went wrong...";
        }
    }
  
}


