import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorMoedas {

    private static final String API_KEY = "46d79da5e69402bf263ec36b";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    // Método para obter a taxa de câmbio entre duas moedas
    public static double obterTaxaCambio(String de, String para) throws Exception {
        String urlStr = BASE_URL + API_KEY + "/latest/" + de;
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Checa se a resposta foi bem-sucedida
        if (connection.getResponseCode() == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            return jsonResponse.getAsJsonObject("conversion_rates").get(para).getAsDouble();
        } else {
            throw new RuntimeException("Erro na conexão: " + connection.getResponseCode());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao Conversor de Moedas!");

        while (true) {
            System.out.println("\n1) Dólar =>> Peso argentino");
            System.out.println("2) Peso argentino =>> Dólar");
            System.out.println("3) Dólar =>> Real brasileiro");
            System.out.println("4) Real brasileiro =>> Dólar");
            System.out.println("5) Dólar =>> Peso colombiano");
            System.out.println("6) Peso colombiano =>> Dólar");
            System.out.println("7) Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();

            if (opcao == 7) {
                System.out.println("Saindo do conversor. Até logo!");
                break;
            }

            double valor, valorConvertido = 0;
            String moedaOrigem = "", moedaDestino = "";

            try {
                switch (opcao) {
                    case 1:
                        moedaOrigem = "USD";
                        moedaDestino = "ARS";
                        System.out.print("Digite o valor em Dólares: ");
                        valor = scanner.nextDouble();
                        valorConvertido = valor * obterTaxaCambio(moedaOrigem, moedaDestino);
                        break;
                    case 2:
                        moedaOrigem = "ARS";
                        moedaDestino = "USD";
                        System.out.print("Digite o valor em Pesos argentinos: ");
                        valor = scanner.nextDouble();
                        valorConvertido = valor * obterTaxaCambio(moedaOrigem, moedaDestino);
                        break;
                    case 3:
                        moedaOrigem = "USD";
                        moedaDestino = "BRL";
                        System.out.print("Digite o valor em Dólares: ");
                        valor = scanner.nextDouble();
                        valorConvertido = valor * obterTaxaCambio(moedaOrigem, moedaDestino);
                        break;
                    case 4:
                        moedaOrigem = "BRL";
                        moedaDestino = "USD";
                        System.out.print("Digite o valor em Reais brasileiros: ");
                        valor = scanner.nextDouble();
                        valorConvertido = valor * obterTaxaCambio(moedaOrigem, moedaDestino);
                        break;
                    case 5:
                        moedaOrigem = "USD";
                        moedaDestino = "COP";
                        System.out.print("Digite o valor em Dólares: ");
                        valor = scanner.nextDouble();
                        valorConvertido = valor * obterTaxaCambio(moedaOrigem, moedaDestino);
                        break;
                    case 6:
                        moedaOrigem = "COP";
                        moedaDestino = "USD";
                        System.out.print("Digite o valor em Pesos colombianos: ");
                        valor = scanner.nextDouble();
                        valorConvertido = valor * obterTaxaCambio(moedaOrigem, moedaDestino);
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        continue;
                }
                System.out.printf("Valor %.2f [%s] corresponde ao valor final de =>>> %.2f [%s]\n", valor, moedaOrigem, valorConvertido, moedaDestino);
            } catch (Exception e) {
                System.out.println("Erro ao converter moeda: " + e.getMessage());
            }
            System.out.println("*****************************************************************");
        }
        scanner.close();
    }
}
