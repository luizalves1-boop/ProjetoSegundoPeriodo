import java.io.*;
import java.util.ArrayList;
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
        ArrayList<Usuario> usuarios = new ArrayList<>();
        carregarFilmesNoArray(filmes, raizFilmes);
        carregarSeriesNoArray(series, raizSeries);
        carregarUsuariosNoArray(usuarios, raizUsuarios);
        int opcao;
        do {
            menuPrincipal();
            opcao = sc.nextInt();
            switch (opcao) {
                case 1:
                    iniciarResetar(raiz, raizFilmes, raizSeries, raizUsuarios, arqIdFilme, arqIdSerie, arqIdUsuario, filmes, series, usuarios);
                    break;
                case 2:
                    gerenciarFilmes(filmes, raizFilmes, arqIdFilme);
                    break;
                case 3:
                    gerenciarSeries(series, raizSeries, arqIdSerie);
                    break;
                case 4:
                    gerenciarUsuarios(usuarios, raizUsuarios, arqIdUsuario);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Essa opção não existe!");
                    break;
            }
        } while (opcao != 5);
    }

    public static void iniciarResetar(String raiz, String raizFilmes, String raizSeries, String raizUsuarios,
                                      String arqIdFilme, String arqIdSerie, String arqIdUsuario, ArrayList<Filme> filmes,
                                      ArrayList<Serie> series, ArrayList<Usuario> usuarios) {
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
        filmes.clear();
        series.clear();
        usuarios.clear();
    }

    public static void menuPrincipal() {
        System.out.println("\n-----------------------"
                + "\n1) Iniciar/Resetar"
                + "\n2) Filmes"
                + "\n3) Séries"
                + "\n4) Usuários"
                + "\n5) Sair"
                + "\n-----------------------");
        System.out.println("Opção: ");

    }

    public static void menuSecundario(String menu) {
        System.out.println("\n" + menu);
        System.out.println("-----------------------"
                + "\n1) Adicionar"
                + "\n2) Buscar"
                + "\n3) Listar"
                + "\n4) Atualizar"
                + "\n5) Apagar"
                + "\n6) Voltar"
                + "\n-----------------------");
        System.out.println("Opção: ");
    }

    public static void menuUsuario() {
        System.out.println("\nUsuário");
        System.out.println("-----------------------"
                + "\n1) Cadastrar"
                + "\n2) Logar"
                + "\n3) Listar"
                + "\n4) Excluir"
                + "\n5) Voltar"
                + "\n-----------------------");
        System.out.println("Opção: ");
    }

    public static void menuDeBusca() {
        System.out.println("\n-----------------------" +
                "\n1) Id" +
                "\n2) Nome" +
                "\n3) Data de Lançamento" +
                "\n4) Gênero" +
                "\n-----------------------");
        System.out.println("Opção: ");
    }

    public static void menuDeListagem() {
        System.out.println("\n-----------------------" +
                "\n1) ID" +
                "\n2) Ordem Alfabética" +
                "\n3) Data de Lançamento" +
                "\n4) Duração do Filme" +
                "\n-----------------------");
        System.out.println("Opção: ");
    }

    public static void menuDeLogin() {
        System.out.println("\n-----------------------" +
                "\n1) Meu Perfil" +
                "\n2) Meus Filmes" +
                "\n3) Minhas Séries" +
                "\n4) Sair" +
                "\n-----------------------");
        System.out.println("Opção: ");
    }

    public static void menuFilmesSeries(){
        System.out.println("\n-----------------------" +
                "\n1) Adicionar" +
                "\n2) Para Assistir" +
                "\n3) Assistindo" +
                "\n4) Concluídos" +
                "\n5) Informações Totais"+
                "\n6) Sair"+
                "\n-----------------------");
        System.out.println("Opção: ");
    }


public static int leId(String arquivo) {
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

public static void gravaId(int i, String arquivo) {
    try {
        PrintWriter pw = new PrintWriter(arquivo);
        pw.println(i);
        pw.flush();
        pw.close();
    } catch (Exception e) {
        System.out.print(e.getMessage());
    }
}

public static void apagaArquivos(File dir) {
    File[] arquivos = dir.listFiles();
    if (arquivos != null) {
        for (File f : arquivos) {
            f.delete();
        }
    }
}

//Filmes

public static void carregarFilmesNoArray(ArrayList<Filme> filmes, String raizFilmes) {
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
                        f.data.mes = Integer.parseInt(partes[1]);
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

public static void gerenciarFilmes(ArrayList<Filme> filmes, String raizFilmes, String arqIdFilme) {
    Scanner sc = new Scanner(System.in);
    int opcao;
    do {
        menuSecundario("Filmes");
        opcao = sc.nextInt();
        switch (opcao) {
            case 1: //cadastrar
                cadastrarFilme("adicionar", filmes, raizFilmes, arqIdFilme);
                break;
            case 2://buscar
                buscarFilme(filmes);
                break;
            case 3://listar
                listarFilme(filmes);
                break;
            case 4://atualizar
                atualizarFilme(filmes, raizFilmes);
                break;
            case 5://apagar
                excluirFilme(filmes, raizFilmes);
                break;
            case 6://voltar
                System.out.println("Voltando");
                break;
            default:
                System.out.println("Essa opção não existe!");
                break;
        }
    } while (opcao != 6);
}

public static Filme adicionarEditarFilme(int id) {
    Scanner sc = new Scanner(System.in);
    Filme f = new Filme();
    f.id = id;
    System.out.println("Informe o nome do filme: ");
    f.nome = sc.nextLine();
    System.out.println("Informe a data de estréia do filme (DD/MM/AAAA): ");
    int data = sc.nextInt();
    f.data = new Estreia();
    f.data.dia = data / 1000000;
    f.data.mes = (data % 1000000) / 10000;
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

public static boolean gravarFilme(Filme f, String raizFilmes) {
    try {
        PrintWriter pw = new PrintWriter(raizFilmes + f.id);
        pw.append("ID: " + f.id + "\n");
        pw.append("Nome: " + f.nome + "\n");
        pw.append("Estreia: ");
        if (f.data.dia < 10) {
            pw.append("0" + f.data.dia + "/");
        } else {
            pw.append(f.data.dia + "/");
        }
        if (f.data.mes < 10) {
            pw.append("0" + f.data.mes + "/");
        } else {
            pw.append(f.data.mes + "/");
        }
        pw.append(f.data.ano + "\n");
        pw.append("Duração: " + f.tempo.horas + "h " + f.tempo.minutos + "m\n");
        pw.append("Gêneros: ");
        for (int i = 0; i < f.genero.length; i++) {
            if (f.genero[i] != null) {
                pw.append(f.genero[i]);
            }
            if (i < (f.genero.length - 1) && f.genero[i + 1] != null) {
                pw.append("/");
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

public static void cadastrarFilme(String tipo, ArrayList<Filme> filmes, String raizFilmes, String arqIdFilme) {
    int id = leId(arqIdFilme);
    Filme f = adicionarEditarFilme(id);
    int id2 = f.id;
    if (gravarFilme(f, raizFilmes)) {
        leFilme("adicionar", filmes, f, id, raizFilmes);
        if (tipo.equals("adicionar")) {
            gravaId(++id, arqIdFilme);
        } else if (tipo.equals("atualizar")) {
            gravaId(id2, arqIdFilme);
        }
    } else {
        System.out.println("Não foi possível gravar o filme!");
    }
    System.out.println();
}

public static void editarFilme(ArrayList<Filme> filmes, String raizFilmes, int id) {
    Filme f = adicionarEditarFilme(id);
    if (gravarFilme(f, raizFilmes)) {
        leFilme("atualizar", filmes, f, id, raizFilmes);
    }
}

public static void leFilme(String tipo, ArrayList<Filme> filmes, Filme f, int id, String arquivo) {
    if (tipo.equals("adicionar")) {
        filmes.add(f);
    } else if (tipo.equals("atualizar")) {
        boolean encontrado = false;
        for (int i = 0; i < filmes.size(); i++) {
            if (filmes.get(i).id == id) {
                filmes.set(i, f);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Filme com ID " + id + " não encontrado para atualização.");
        }
    }
}

public static void escreverFilme(Filme f) {
    System.out.println("ID: " + f.id);
    System.out.println("Nome: " + f.nome);
    System.out.print("Estreia: ");
    if (f.data.dia < 10) {
        System.out.print("0" + f.data.dia + "/");
    } else {
        System.out.print(f.data.dia + "/");
    }
    if (f.data.mes < 10) {
        System.out.print("0" + f.data.mes + "/");
    } else {
        System.out.print(f.data.mes + "/");
    }
    System.out.println(f.data.ano);
    System.out.println("Duração: " + f.tempo.horas + "h " + f.tempo.minutos + "m");
    System.out.print("Gêneros: ");
    for (int i = 0; i < f.genero.length; i++) {
        if (f.genero[i] != null && !f.genero[i].isEmpty()) {
            System.out.print(f.genero[i]);
            if (i < (f.genero.length - 1) && f.genero[i + 1] != null) {
                System.out.print("/");
            }
        }
    }
    System.out.println();
}

public static void buscarFilme(ArrayList<Filme> filmes) {
    Scanner sc = new Scanner(System.in);
    System.out.println("\nInforme qual tipo de buscar você quer utilizar: ");
    menuDeBusca();
    int opcao = sc.nextInt();
    switch (opcao) {
        case 1:
            buscaPorIdFilme(filmes);
            break;
        case 2:
            buscaPorNomeFilme(filmes);
            break;
        case 3:
            buscaPorDataFilme(filmes);
            break;
        case 4:
            buscarPorGeneroFilme(filmes);
            break;
        default:
            System.out.println("Essa opção não existe!");
            break;
    }
}

public static void buscaPorIdFilme(ArrayList<Filme> filmes) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Informe o ID do filme que deseja buscar: ");
    int id = sc.nextInt();
    boolean encontrado = false;
    for (Filme f : filmes) {
        if (f.id == id) {
            System.out.println("\n--- Filme encontrado (" + f.id + ") ---");
            escreverFilme(f);
            encontrado = true;
        }
    }
    if (!encontrado) {
        System.out.println("Não foi possível encontrar um filme com esse ID.");
    }
}

public static void buscaPorNomeFilme(ArrayList<Filme> filmes) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Informe o nome do filme que deseja buscar: ");
    String nome = sc.nextLine();
    boolean encontrado = false;
    for (Filme f : filmes) {
        if (f.nome.equals(nome)) {
            System.out.println("\n--- Filme encontrado (" + f.id + ") ---");
            escreverFilme(f);
            encontrado = true;
        }
    }
    if (!encontrado) {
        System.out.println("Não foi possível encontrar um filme com esse nome.");
    }

}

public static void buscaPorDataFilme(ArrayList<Filme> filmes) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Informe a data do filme que deseja buscar: (DDMMAAAA)");
    String data = sc.nextLine();
    boolean encontrado = false;
    for (Filme f : filmes) {
        String dia;
        String mes;
        if (f.data.dia < 10) {
            dia = "0" + String.valueOf(f.data.dia);
        } else {
            dia = String.valueOf(f.data.dia);
        }
        if (f.data.mes < 10) {
            mes = "0" + String.valueOf(f.data.mes);
        } else {
            mes = String.valueOf(f.data.mes);
        }
        String ano = String.valueOf(f.data.ano);
        String dataf = dia + mes + ano;
        if (dataf.equals(data)) {
            System.out.println("\n--- Filme encontrado (" + f.id + ") ---");
            escreverFilme(f);
            encontrado = true;
        }
    }
    if (!encontrado) {
        System.out.println("Não foi possível encontrar um filme com essa data.");
    }
}

public static void buscarPorGeneroFilme(ArrayList<Filme> filmes) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Informe o Gênero do filme que deseja buscar: ");
    String genero = sc.nextLine();
    boolean encontrado = false;
    for (Filme f : filmes) {
        for (int i = 0; i < f.genero.length; i++) {
            if (f.genero[i] != null && !f.genero[i].isEmpty() && f.genero[i].equals(genero)) {
                System.out.println("\n--- Filme encontrado (" + f.id + ") ---");
                escreverFilme(f);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("Não foi possível encontrar um filme com esse ID.");
        }
    }
}

public static void listarFilme(ArrayList<Filme> filmes) {
    Scanner sc = new Scanner(System.in);
    menuDeListagem();
    int opcao = sc.nextInt();
    switch (opcao) {
        case 1:
            listarIdFilme(filmes);
            break;
        case 2:
            listarAlfabeticaFilme(filmes);
            break;
        case 3:
            listarDataFilme(filmes);
            break;
        case 4:
            listarDuracaoFilme(filmes);
            break;
        default:
            System.out.println("Essa opção não existe!");
            break;
    }
}

public static void listarIdFilme(ArrayList<Filme> filmes) {
    System.out.println("\n--- Filmes ---");
    filmes.sort(Comparator.comparingInt(f -> f.id));
    for (Filme f : filmes) {
        System.out.println("\n--- Filme(" + f.id + ") ---");
        escreverFilme(f);
    }
}

public static void listarAlfabeticaFilme(ArrayList<Filme> filmes) {
    System.out.println("\n--- Filmes ---");
    filmes.sort(Comparator.comparing(f -> f.nome));
    for (Filme f : filmes) {
        System.out.println("\n--- Filme(" + f.id + ") ---");
        escreverFilme(f);
    }
}

public static void listarDataFilme(ArrayList<Filme> filmes) {
    System.out.println("\n--- Filmes ---");
    filmes.sort(Comparator.comparingInt((Filme f) -> f.data.ano)
            .thenComparingInt(f -> f.data.mes)
            .thenComparingInt(f -> f.data.dia));
    for (Filme f : filmes) {
        System.out.println("\n--- Filme(" + f.id + ") ---");
        escreverFilme(f);
    }
}

public static void listarDuracaoFilme(ArrayList<Filme> filmes) {
    System.out.println("\n--- Filmes ---");
    filmes.sort(Comparator.comparingInt(f -> f.tempo.horas * 60 + f.tempo.minutos));
    for (Filme f : filmes) {
        System.out.println("\n--- Filme(" + f.id + ") ---");
        escreverFilme(f);
    }
}

public static void atualizarFilme(ArrayList<Filme> filmes, String raizFilmes) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Informe o ID do filme que você quer atualizar: ");
    int id = sc.nextInt();
    for (Filme f : filmes) {
        if (f.id == id) {
            System.out.println("Informe as novas informações do filme");
            editarFilme(filmes, raizFilmes, id);
        }
    }
}

public static void excluirFilme(ArrayList<Filme> filmes, String raizFilmes) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Informe o ID do filme que você quer excluir: ");
    int id = sc.nextInt();
    File dir = new File(raizFilmes + id);
    for (int i = 0; i < filmes.size(); i++) {
        if (filmes.get(i).id == id) {
            if (dir.delete()) {
                filmes.remove(i);
                System.out.println("O filme foi excluído com sucesso!");
            } else {
                System.out.println("O filme não pôde ser excluído!");
            }
            return;
        }
    }
}

//Séries

public static void carregarSeriesNoArray(ArrayList<Serie> series, String raizSeries) {
    File dir = new File(raizSeries);
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
                Serie s = new Serie();
                ArrayList<String> generos = new ArrayList<>();

                while ((linha = br.readLine()) != null) {
                    if (linha.startsWith("ID:")) {
                        s.id = Integer.parseInt(linha.substring(4).trim());
                    } else if (linha.startsWith("Nome:")) {
                        s.nome = linha.substring(6).trim();
                    } else if (linha.startsWith("Estreia:")) {
                        String dataStr = linha.substring(9).trim();
                        String[] partes = dataStr.split("/");
                        s.data = new Estreia();
                        s.data.dia = Integer.parseInt(partes[0]);
                        s.data.mes = Integer.parseInt(partes[1]);
                        s.data.ano = Integer.parseInt(partes[2]);
                    } else if (linha.startsWith("Temporadas:")) {
                        s.temporadas = Integer.parseInt(linha.substring(12).trim());
                    } else if (linha.startsWith("Episódios:")) {
                        s.episodios = Integer.parseInt(linha.substring(11).trim());
                    } else if (linha.startsWith("Gêneros:")) {
                        String g = linha.substring(9).trim();
                        String[] gSplit = g.split("/");
                        for (String gen : gSplit) {
                            if (!gen.isEmpty()) {
                                generos.add(gen);
                            }
                        }
                    }
                }
                // Transformar ArrayList em array de String
                s.genero = new String[generos.size()];
                s.genero = generos.toArray(s.genero);

                series.add(s); // Adiciona a série ao ArrayList

            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
                e.printStackTrace();
            }
        }
    }
}

public static void gerenciarSeries(ArrayList<Serie> series, String raizSeries, String arqIdSerie) {
    Scanner sc = new Scanner(System.in);
    int opcao;
    do {
        menuSecundario("Séries");
        opcao = sc.nextInt();
        switch (opcao) {
            case 1: //cadastrar
                cadastrarSerie("adicionar", series, raizSeries, arqIdSerie);
                break;
            case 2://buscar
                buscarSerie(series);
                break;
            case 3://listar
                listarSerie(series);
                break;
            case 4://atualizar
                atualizarSerie(series, raizSeries);
                break;
            case 5://apagar
                excluirSerie(series, raizSeries);
                break;
            case 6://voltar
                System.out.println("Voltando");
                break;
        }
    } while (opcao != 6);
}

public static Serie adicionarEditarSerie(int id) {
    Scanner sc = new Scanner(System.in);
    Serie s = new Serie();
    s.id = id;
    System.out.println("Informe o nome da série: ");
    s.nome = sc.nextLine();
    System.out.println("Informe a data de estréia da série (DD/MM/AAAA): ");
    int data = sc.nextInt();
    s.data = new Estreia();
    s.data.dia = data / 1000000;
    s.data.mes = (data % 1000000) / 10000;
    s.data.ano = (data % 1000000) % 10000;
    System.out.println("Informe quantas temporadas têm a série: ");
    s.temporadas = sc.nextInt();
    System.out.println("Informe quantos episódios totais têm a série: ");
    s.episodios = sc.nextInt();
    s.genero = new String[10];
    int i = 0;
    char opcao;
    sc.nextLine();
    do {
        System.out.print("Gênero: ");
        s.genero[i] = sc.nextLine();
        i++;
        System.out.print("Quer adicionar mais um gênero a essa série? (s/n): ");
        opcao = sc.next().charAt(0);
        sc.nextLine();
    } while (opcao == 's' && i <= 10);

    return s;
}

public static boolean gravarSerie(Serie s, String raizSeries) {
    try {
        PrintWriter pw = new PrintWriter(raizSeries + s.id);
        pw.append("ID: " + s.id + "\n");
        pw.append("Nome: " + s.nome + "\n");
        pw.append("Estreia: ");
        if (s.data.dia < 10) {
            pw.append("0" + s.data.dia + "/");
        } else {
            pw.append(s.data.dia + "/");
        }
        if (s.data.mes < 10) {
            pw.append("0" + s.data.mes + "/");
        } else {
            pw.append(s.data.mes + "/");
        }
        pw.append(s.data.ano + "\n");
        pw.append("Temporadas: " + s.temporadas + "\n");
        pw.append("Episódios: " + s.episodios + "\n");
        pw.append("Gêneros: ");
        for (int i = 0; i < s.genero.length; i++) {
            if (s.genero[i] != null) {
                pw.append(s.genero[i]);
            }
            if (i < (s.genero.length - 1) && s.genero[i + 1] != null) {
                pw.append("/");
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

public static void cadastrarSerie(String tipo, ArrayList<Serie> series, String raizSeries, String arqIdSerie) {
    int id = leId(arqIdSerie);
    Serie s = adicionarEditarSerie(id);
    int id2 = s.id;
    if (gravarSerie(s, raizSeries)) {
        leSerie("adicionar", series, s, id, raizSeries);
        if (tipo.equals("adicionar")) {
            gravaId(++id, arqIdSerie);
        } else if (tipo.equals("atualizar")) {
            gravaId(id2, arqIdSerie);
        }
    } else {
        System.out.println("Não foi possível gravar a série!");
    }
    System.out.println();
}

public static void editarSerie(ArrayList<Serie> series, String raizSeries, int id) {
    Serie s = adicionarEditarSerie(id);
    if (gravarSerie(s, raizSeries)) {
        leSerie("atualizar", series, s, id, raizSeries);
    }
}

public static void leSerie(String tipo, ArrayList<Serie> series, Serie s, int id, String arquivo) {
    if (tipo.equals("adicionar")) {
        series.add(s);
    } else if (tipo.equals("atualizar")) {
        boolean encontrado = false;
        for (int i = 0; i < series.size(); i++) {
            if (series.get(i).id == id) {
                series.set(i, s);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Série com ID " + id + " não encontrada para atualização.");
        }
    }
}

public static void escreverSerie(Serie s) {
    System.out.println("ID: " + s.id);
    System.out.println("Nome: " + s.nome);
    System.out.print("Estreia: ");
    if (s.data.dia < 10) {
        System.out.print("0" + s.data.dia + "/");
    } else {
        System.out.print(s.data.dia + "/");
    }
    if (s.data.mes < 10) {
        System.out.print("0" + s.data.mes + "/");
    } else {
        System.out.print(s.data.mes + "/");
    }
    System.out.println(s.data.ano);
    System.out.println("Temporadas: " + s.temporadas);
    System.out.println("Episódios: " + s.episodios);
    System.out.print("Gêneros: ");
    for (int i = 0; i < s.genero.length; i++) {
        if (s.genero[i] != null && !s.genero[i].isEmpty()) {
            System.out.print(s.genero[i]);
            if (i < (s.genero.length - 1) && s.genero[i + 1] != null) {
                System.out.print("/");
            }
        }
    }
    System.out.println();
}

public static void buscarSerie(ArrayList<Serie> series) {
    Scanner sc = new Scanner(System.in);
    System.out.println("\nInforme qual tipo de buscar você quer utilizar: ");
    menuDeBusca();
    int opcao = sc.nextInt();
    switch (opcao) {
        case 1:
            buscaPorIdSerie(series);
            break;
        case 2:
            buscaPorNomeSerie(series);
            break;
        case 3:
            buscaPorDataSerie(series);
            break;
        case 4:
            buscarPorGeneroSerie(series);
            break;
        default:
            System.out.println("Essa opção não existe!");
            break;
    }
}

public static void buscaPorIdSerie(ArrayList<Serie> series) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Informe o ID da série que deseja buscar: ");
    int id = sc.nextInt();
    boolean encontrado = false;
    for (Serie s : series) {
        if (s.id == id) {
            System.out.println("\n--- Série encontrada (" + s.id + ") ---");
            escreverSerie(s);
            encontrado = true;
        }
    }
    if (!encontrado) {
        System.out.println("Não foi possível encontrar uma série com esse ID.");
    }
}

public static void buscaPorNomeSerie(ArrayList<Serie> series) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Informe o nome da série que deseja buscar: ");
    String nome = sc.nextLine();
    boolean encontrado = false;
    for (Serie s : series) {
        if (s.nome.equals(nome)) {
            System.out.println("\n--- Série encontrada (" + s.id + ") ---");
            escreverSerie(s);
            encontrado = true;
        }
    }
    if (!encontrado) {
        System.out.println("Não foi possível encontrar uma série com esse nome.");
    }

}

public static void buscaPorDataSerie(ArrayList<Serie> series) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Informe a data da série que deseja buscar: (DDMMAAAA)");
    String data = sc.nextLine();
    boolean encontrado = false;
    for (Serie s : series) {
        String dia;
        String mes;
        if (s.data.dia < 10) {
            dia = "0" + String.valueOf(s.data.dia);
        } else {
            dia = String.valueOf(s.data.dia);
        }
        if (s.data.mes < 10) {
            mes = "0" + String.valueOf(s.data.mes);
        } else {
            mes = String.valueOf(s.data.mes);
        }
        String ano = String.valueOf(s.data.ano);
        String dataf = dia + mes + ano;
        if (dataf.equals(data)) {
            System.out.println("\n--- Série encontrada (" + s.id + ") ---");
            escreverSerie(s);
            encontrado = true;
        }
    }
    if (!encontrado) {
        System.out.println("Não foi possível encontrar uma série com essa data.");
    }
}

public static void buscarPorGeneroSerie(ArrayList<Serie> series) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Informe o Gênero da série que deseja buscar: ");
    String genero = sc.nextLine();
    boolean encontrado = false;
    for (Serie s : series) {
        for (int i = 0; i < s.genero.length; i++) {
            if (s.genero[i] != null && !s.genero[i].isEmpty() && s.genero[i].equals(genero)) {
                System.out.println("\n--- Série encontrada (" + s.id + ") ---");
                escreverSerie(s);
                encontrado = true;
            }
        }
        if (!encontrado) {
            System.out.println("Não foi possível encontrar uma série com esse gênero.");
        }
    }
}

public static void listarSerie(ArrayList<Serie> series) {
    Scanner sc = new Scanner(System.in);
    menuDeListagem();
    int opcao = sc.nextInt();
    switch (opcao) {
        case 1:
            listarIdSerie(series);
            break;
        case 2:
            listarAlfabeticaSerie(series);
            break;
        case 3:
            listarDataSerie(series);
            break;
        case 4:
            listarTemporadasSerie(series);
            break;
        default:
            System.out.println("Essa opção não existe!");
            break;
    }
}

public static void listarIdSerie(ArrayList<Serie> series) {
    System.out.println("\n--- Séries ---");
    series.sort(Comparator.comparingInt(s -> s.id));
    for (Serie s : series) {
        System.out.println("\n--- Série(" + s.id + ") ---");
        escreverSerie(s);
    }
}

public static void listarAlfabeticaSerie(ArrayList<Serie> series) {
    System.out.println("\n--- Séries ---");
    series.sort(Comparator.comparing(s -> s.nome));
    for (Serie s : series) {
        System.out.println("\n--- Série(" + s.id + ") ---");
        escreverSerie(s);
    }
}

public static void listarDataSerie(ArrayList<Serie> series) {
    System.out.println("\n--- Séries ---");
    series.sort(Comparator.comparingInt((Serie s) -> s.data.ano)
            .thenComparingInt(s -> s.data.mes)
            .thenComparingInt(s -> s.data.dia));
    for (Serie s : series) {
        System.out.println("\n--- Série(" + s.id + ") ---");
        escreverSerie(s);
    }
}

public static void listarTemporadasSerie(ArrayList<Serie> series) {
    System.out.println("\n--- Séries ---");
    series.sort(Comparator.comparingInt(s -> s.temporadas));
    for (Serie s : series) {
        System.out.println("\n--- Série(" + s.id + ") ---");
        escreverSerie(s);
    }
}

public static void atualizarSerie(ArrayList<Serie> series, String raizSeries) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Informe o ID da série que você quer atualizar: ");
    int id = sc.nextInt();
    for (Serie s : series) {
        if (s.id == id) {
            System.out.println("Informe as novas informações da série");
            editarSerie(series, raizSeries, id);
        }
    }
}

public static void excluirSerie(ArrayList<Serie> series, String raizSeries) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Informe o ID da série que você quer excluir: ");
    int id = sc.nextInt();
    File dir = new File(raizSeries + id);
    for (int i = 0; i < series.size(); i++) {
        if (series.get(i).id == id) {
            if (dir.delete()) {
                series.remove(i);
                System.out.println("A série foi excluído com sucesso!");
            } else {
                System.out.println("A série não pôde ser excluído!");
            }
            return;
        }
    }
}

//Usuários

public static void carregarUsuariosNoArray(ArrayList<Usuario> usuarios, String raizUsuarios) {
    File dir = new File(raizUsuarios);
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
                Usuario u = new Usuario();
                while ((linha = br.readLine()) != null) {
                    if (linha.startsWith("ID:")) {
                        u.id = Integer.parseInt(linha.substring(4).trim());
                    } else if (linha.startsWith("Nome:")) {
                        u.nome = linha.substring(6).trim();
                    } else if (linha.startsWith("Telefone:")) {
                        u.telefone = Long.parseLong(linha.substring(10).trim());
                    } else if (linha.startsWith("Email:")) {
                        u.email = (linha.substring(7).trim());
                    } else if (linha.startsWith("Senha:")) {
                        u.senha = (linha.substring(7).trim());
                    }
                }
                usuarios.add(u); // Adiciona o usuário ao ArrayList

            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + arquivo.getName());
                e.printStackTrace();
            }
        }
    }
}

public static void gerenciarUsuarios(ArrayList<Usuario> usuarios, String raizUsuarios, String arqIdUsuario) {
    Scanner sc = new Scanner(System.in);
    int opcao;
    do {
        menuUsuario();
        opcao = sc.nextInt();
        switch (opcao) {
            case 1: //cadastrar
                cadastrarUsuario(usuarios, raizUsuarios, arqIdUsuario);
                break;
            case 2://logar
                logarUsuario(usuarios);
                break;
            case 3://listar
                break;
            case 4://atualizar

                break;
            case 5://voltar
                System.out.println("Voltando");
                break;
            default:
                System.out.println("Essa opção não existe!");
                break;
        }
    } while (opcao != 5);
}

public static Usuario criarUsuario(ArrayList<Usuario> usuarios, int id) {
    Scanner sc = new Scanner(System.in);
    Usuario u = new Usuario();
    u.id = id;
    System.out.println("Informe o nome de usuário: ");
    u.nome = sc.nextLine();
    System.out.println("Informe o telefone do usuário: ");
    u.telefone = sc.nextLong();
    System.out.println("Informe o email do usuário: ");
    u.email = verificarEmail(usuarios);
    sc.nextLine();
    System.out.println("Informe a senha do usuário: ");
    u.senha = sc.nextLine();
    return u;
}

public static String verificarEmail(ArrayList<Usuario> usuarios) {
    Scanner sc = new Scanner(System.in);
    String email = "";
    int arroba = 0;
    boolean jaCadastrado = false;
    boolean valido = false;
    do {
        email = sc.nextLine();
        for (Usuario u : usuarios) {
            if (email.equals(u.email)) {
                jaCadastrado = true;
            }
        }
        if (!jaCadastrado) {
            for (int i = 0; i < email.length(); i++) {
                if (email.charAt(i) == '@') {
                    arroba++;
                }
            }
            if (arroba != 1) {
                System.out.println("Esse email não é válido, informe outro: ");
            } else {
                return email;
            }
        } else {
            System.out.println("Esse email já foi cadastrado, tente outro: ");
        }
        jaCadastrado = false;
    } while (!valido);
    return email;
}

public static boolean gravarUsuario(Usuario u, String raizUsuarios) {
    try {
        PrintWriter pw = new PrintWriter(raizUsuarios + u.id);
        pw.append("ID: " + u.id + "\n");
        pw.append("Nome: " + u.nome + "\n");
        pw.append("Telefone" + u.telefone + "\n");
        pw.append("Email: " + u.email + "\n");
        pw.append("Senha: " + u.senha + "\n");
        pw.flush();
        pw.close();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
        return false;
    }
    return true;
}

public static void cadastrarUsuario(ArrayList<Usuario> usuarios, String raizUsuarios, String arqIdUsuario) {
    int id = leId(arqIdUsuario);
    Usuario u = criarUsuario(usuarios, id);
    int id2 = u.id;
    if (gravarUsuario(u, raizUsuarios)) {
        leUsuario(usuarios, u);
        gravaId(++id, arqIdUsuario);
    } else {
        System.out.println("Não foi possível gravar a usuário!");
    }
    System.out.println();
}

public static void leUsuario(ArrayList<Usuario> usuarios, Usuario u) {
    usuarios.add(u);
}

public static void logarUsuario(ArrayList<Usuario> usuarios) {
    boolean login = loginUsuario(usuarios);
    if (login) {

    } else {
        return;
    }
}

public static boolean loginUsuario(ArrayList<Usuario> usuarios) {
    Scanner sc = new Scanner(System.in);
    boolean login = false;
    int tentativas = 0;
    System.out.println("Email: ");
    String email = sc.nextLine();
    do {
        for (Usuario u : usuarios) {
            if (!u.email.equals(email)) {
                System.out.println("Esse email não foi cadastrado!");
                return false;
            }
        }
        System.out.println("Senha: ");
        String senha = sc.nextLine();
        for (Usuario u : usuarios) {
            if (u.email.equals(email) && u.senha.equals(senha)) {
                System.out.println("Login Concluído!");
                login = true;
            } else if (u.email.equals(email) && !u.senha.equals(senha)) {
                tentativas++;
                if (tentativas == 4) {
                    System.out.println("Você atingiu a quantidade máxima de tentativas!Volte para a aba de login.");
                    return false;
                } else {
                    System.out.println("Senha Errada! Tente novamente: ");
                }
            }
        }
    } while (!login);
    return login;
}
}