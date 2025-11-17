import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String raiz = "Aplicativo/";
        String raizFilmes = raiz + "Filmes/";
        String raizSeries = raiz + "Séries/";
        String raizUsuarios = raiz + "Usuários/";
        String arqIdFilme = raiz + "idFilme.txt";
        String arqIdSerie = raiz + "idSerie.txt";
        String arqIdUsuario = raiz + "idUsuario.txt";
        ArrayList<Filme> filmes = new ArrayList<>();
        ArrayList<Serie> series = new ArrayList<>();
        carregarFilmesNoArray(filmes, raizFilmes);
        int opcao;
        do {
            menuPrincipal();
            opcao = sc.nextInt();
            switch (opcao) {
                case 1:
                    iniciarResetar(raiz, raizFilmes, raizSeries, raizUsuarios, arqIdFilme, arqIdSerie, arqIdUsuario);
                    break;
                case 2:
                    gerenciarFilmes(filmes, raizFilmes, arqIdFilme);
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
            }
        } while (opcao != 5);
    }
    private static void carregarFilmesNoArray(ArrayList<Filme> filmes, String raizFilmes) {
        File dir = new File(raizFilmes);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] arquivos = dir.listFiles();
        if (arquivos == null || arquivos.length == 0) {
            return;
        }
        for (File arquivo : arquivos) {
            if (arquivo.isFile()) {
                try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
                    String linha;
                    Filme f = new Filme();
                    ArrayList<String> generos = new ArrayList<>();

                    while ((linha = br.readLine()) != null) {
                        if (linha.startsWith("ID:")) {
                            f.id = Integer.parseInt(linha.substring(4).trim());
                        } else if (linha.startsWith("Nome:")) {
                            f.nome = linha.substring(6).trim();
                        } else if (linha.startsWith("Estreia:")) {
                            String dataStr = linha.substring(8).trim(); // ex: "12/05/2015"
                            String[] partes = dataStr.split("/");
                            f.data = new Estreia();
                            f.data.dia = Integer.parseInt(partes[0]);
                            f.data.mês = Integer.parseInt(partes[1]);
                            f.data.ano = Integer.parseInt(partes[2]);
                        } else if (linha.startsWith("Duração:")) {
                            String duracaoStr = linha.substring(9).trim(); // ex: "2h 30m"
                            String[] partes = duracaoStr.split("h|m");
                            f.tempo = new Tempo();
                            f.tempo.horas = Integer.parseInt(partes[0].trim());
                            f.tempo.minutos = Integer.parseInt(partes[1].trim());
                        } else if (linha.startsWith("Gêneros:")) {
                            String g = linha.substring(8).trim();
                            String[] gSplit = g.split("/");
                            for (String gen : gSplit) {
                                if (!gen.isEmpty()) {
                                    generos.add(gen);
                                }
                            }
                        }
                    }

                    // Transformar ArrayList em array de String
                    f.genero = new String[generos.size()];
                    f.genero = generos.toArray(f.genero);

                    filmes.add(f); // Adiciona o filme ao ArrayList

                } catch (IOException e) {
                    System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    private static void iniciarResetar(String raiz, String raizFilmes, String raizSeries, String raizUsuarios,
                                       String arqIdFilme, String arqIdSerie, String arqIdUsuario) {
        File dir = new File(raiz);
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = new File(raizFilmes);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            apagaArquivos(dir);
        }
        dir = new File(raizSeries);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            apagaArquivos(dir);
        }
        dir = new File(raizUsuarios);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            apagaArquivos(dir);
        }
        gravaId(0, arqIdFilme);
        gravaId(0, arqIdSerie);
        gravaId(0, arqIdUsuario);
    }

    private static void menuPrincipal() {
        System.out.println("\n-----------------------"
                + "\n1) Iniciar/Resetar"
                + "\n2) Filmes"
                + "\n3) Séries"
                + "\n4) Usuários"
                + "\n5) Sair"
                + "\n-----------------------");
        System.out.println("Opção: ");

    }

    private static void menuSecundario(String menu) {
        System.out.println("\n" + menu);
        System.out.println("-----------------------"
                + "\n1) Criar"
                + "\n2) Buscar"
                + "\n3) Listar"
                + "\n4) Atualizar"
                + "\n5) Apagar"
                + "\n6) Voltar"
                + "\n-----------------------");
        System.out.println("Opção: ");

    }

    private static void menuDeBusca() {
        System.out.println("\n-----------------------" +
                "\n1) Id" +
                "\n2) Nome" +
                "\n3) Ano de Lançamento" +
                "\n-----------------------");
        System.out.println("Opção: ");
    }

    private static void menuDeListagem() {
        System.out.println("\n-----------------------" +
                "\n1) ID" +
                "\n2) Ordem Alfabética" +
                "\n3) Data de Lançamento" +
                "\n4) Duração do Filme" +
                "\n-----------------------");
        System.out.println("Opção: ");
    }

    private static int leId(String arquivo) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(arquivo));
            int id = Integer.parseInt(br.readLine());
            br.close();
            return id;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao ler o id");
            e.printStackTrace();
        }
        return -1;
    }

    private static void gravaId(int i, String arquivo) {
        try {
            PrintWriter pw = new PrintWriter(arquivo);
            pw.println(i);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    private static void apagaArquivos(File dir) {
        File[] arquivos = dir.listFiles();
        if (arquivos != null) {
            for (File f : arquivos) {
                f.delete();
            }
        }
    }

    private static void gerenciarFilmes(ArrayList<Filme> filmes, String raizFilmes, String arqIdFilme) {
        Scanner sc = new Scanner(System.in);
        int opcao;
        do {
            menuSecundario("Filmes");
            opcao = sc.nextInt();
            switch (opcao) {
                case 1: //cadastrar
                    cadastrarFilme(filmes, raizFilmes, arqIdFilme);
                    break;
                case 2://buscar
                    buscarFilme(filmes, raizFilmes, arqIdFilme);
                    break;
                case 3://listar
                    listarFilme(filmes, raizFilmes);
                    break;
                case 4://atualizar
                    break;
                case 5://apagar
                    break;
                case 6://voltar
                    System.out.println("Voltando");
                    break;
            }
        } while (opcao != 6);
    }

    private static Filme criarFilme(int id) {
        Scanner sc = new Scanner(System.in);
        Filme f = new Filme();
        f.id = id;
        System.out.println("Informe o nome do filme: ");
        f.nome = sc.nextLine();
        System.out.println("Informe a data de estréia do filme (DD/MM/AAAA): ");
        int data = sc.nextInt();
        f.data = new Estreia();
        f.data.dia = data / 1000000;
        f.data.mês = (data % 1000000) / 10000;
        f.data.ano = (data % 1000000) % 10000;
        System.out.println("Informe qual a minutagem total do filme: ");
        int min = sc.nextInt();
        f.tempo = new Tempo();
        f.tempo.horas = min / 60;
        f.tempo.minutos = min % 60;
        f.genero = new String[10];
        int i = 0;
        char opcao;
        sc.nextLine();
        do {
            System.out.print("Gênero: ");
            f.genero[i] = sc.nextLine();
            i++;
            System.out.print("Quer adicionar mais um gênero a esse filme? (s/n): ");
            opcao = sc.next().charAt(0);
            sc.nextLine();
        } while (opcao == 's' && i <= 10);

        return f;
    }

    private static boolean gravarFilme(Filme f, String raizFilmes) {
        try {
            PrintWriter pw = new PrintWriter(raizFilmes + f.id);
            pw.append("ID: " + f.id + "\n");
            pw.append("Nome: " + f.nome + "\n");
            pw.append("Estreia: " + f.data.dia + "/" + f.data.mês + "/" + f.data.ano + "\n");
            pw.append("Duração: " + f.tempo.horas + "h " + f.tempo.minutos + "m\n");
            pw.append("Gêneros: ");
            for (int i = 0; i < 10; i++) {
                if (f.genero[i] != null) {
                    pw.append(f.genero[i] + "/");
                }
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void cadastrarFilme(ArrayList<Filme> filmes, String raizFilmes, String arqIdFilme) {
        int id = leId(arqIdFilme);
        Filme f = criarFilme(id);
        if (gravarFilme(f, raizFilmes)) {
            leFilme(filmes, f, id, raizFilmes);
            gravaId(++id, arqIdFilme);
        } else {
            System.out.println("Não foi possível gravar o filme!");
        }
        System.out.println();
    }

    public static void leFilme(ArrayList<Filme> filmes, Filme f, int id, String arquivo) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(arquivo + id));
            filmes.add(f);
            br.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao ler o id");
            e.printStackTrace();
        }
    }

    private static void buscarFilme(ArrayList<Filme> filmes, String raizFilmes, String arqIdFilme) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nQual tipo de buscar você quer utilizar: ");
        menuDeBusca();
        int opcao = sc.nextInt();
        switch (opcao) {
            case 1:
                buscaPorIdFilme(filmes, raizFilmes);
                break;
            case 2:
                buscaPorNomeFilme(filmes, raizFilmes);
                break;
            case 3:
                buscaPorAnoFilme(filmes, raizFilmes);
                break;
        }
    }

    private static void buscaPorIdFilme(ArrayList<Filme> filmes, String raiz) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Informe o Id do filme que deseja buscar: ");
        int id = sc.nextInt();
        for (Filme f : filmes) {
            if (f.id == id) {
                System.out.println("\n--- Filme encontrado (" + f.id + ") ---");
                escreverFilme(f);
            }
        }
    }

    private static void buscaPorNomeFilme(ArrayList<Filme> filmes, String raiz) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Informe o nome do filme que deseja buscar: ");
        String nome = sc.nextLine();
        for (Filme f : filmes) {
            if (f.nome.equals(nome)) {
                System.out.println("\n--- Filme encontrado (" + f.id + ") ---");
                escreverFilme(f);
            }
        }

    }

    private static void buscaPorAnoFilme(ArrayList<Filme> filmes, String raiz) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Informe o ano do filme que deseja buscar: ");
        int ano = sc.nextInt();
        for (Filme f : filmes) {
            if (f.data.ano == (ano)) {
                System.out.println("\n--- Filme encontrado (" + f.id + ") ---");
                escreverFilme(f);
            }
        }
    }

    private static void listarFilme(ArrayList<Filme> filmes, String raizFilmes) {
        Scanner sc = new Scanner(System.in);
        menuDeListagem();
        int opcao = sc.nextInt();
        switch (opcao) {
            case 1:
                listarIdFilme(filmes, raizFilmes);
                break;
            case 2:
                listarAlfabeticaFilme(filmes, raizFilmes);
                break;
            case 3:
                listarDataFilme(filmes, raizFilmes);
                break;
            case 4:
                listarDuracaoFilme(filmes, raizFilmes);
                break;
            default:
                return;
        }
    }

    private static void listarIdFilme(ArrayList<Filme> filmes, String raiz) {
        System.out.println("\n--- Filmes ---");
        filmes.sort(Comparator.comparingInt(f -> f.id));
        for (Filme f : filmes) {
            System.out.println("\n--- Filme(" + f.id + ") ---");
            escreverFilme(f);
        }
    }

    private static void listarAlfabeticaFilme(ArrayList<Filme> filmes, String raiz) {
        System.out.println("\n--- Filmes ---");
        filmes.sort(Comparator.comparing(f -> f.nome));
        for (Filme f : filmes) {
            System.out.println("\n--- Filme(" + f.id + ") ---");
            escreverFilme(f);
        }
    }

    private static void listarDataFilme(ArrayList<Filme> filmes, String raiz) {
        System.out.println("\n--- Filmes ---");
        filmes.sort(Comparator.comparingInt((Filme f) -> f.data.ano)
                .thenComparingInt(f -> f.data.mês)
                .thenComparingInt(f -> f.data.dia));
        for (Filme f : filmes) {
            System.out.println("\n--- Filme(" + f.id + ") ---");
            escreverFilme(f);
        }
    }

    private static void listarDuracaoFilme(ArrayList<Filme> filmes, String raiz) {
        System.out.println("\n--- Filmes ---");
        filmes.sort(Comparator.comparingInt(f -> f.tempo.horas * 60 + f.tempo.minutos));
        for (Filme f : filmes) {
            System.out.println("\n--- Filme(" + f.id + ") ---");
            escreverFilme(f);
        }
    }

    private static void escreverFilme(Filme f) {
        System.out.println("ID: " + f.id);
        System.out.println("Nome: " + f.nome);
        System.out.println("Estreia: " + f.data.dia + "/" + f.data.mês + "/" + f.data.ano);
        System.out.println("Duração: " + f.tempo.horas + "h " + f.tempo.minutos + "m");
        System.out.print("Gêneros: ");
        for (int i = 0; i < f.genero.length; i++) {
            if (f.genero[i] != null && !f.genero[i].isEmpty()) {
                System.out.print(f.genero[i]);
                if (i != f.genero.length - 1) {
                    System.out.print("/");
                }
            }
        }
        System.out.println();
    }
}

