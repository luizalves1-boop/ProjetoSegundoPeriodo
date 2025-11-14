import java.io.*;
import java.sql.SQLOutput;
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
        int opcao;

        do {
            menuPrincipal();
            opcao = sc.nextInt();
            switch (opcao) {
                case 1:
                    gerenciarFilmes(raizFilmes, arqIdFilme);
                    break;
                case 2:
                    gerenciarSeries(raizSeries, arqIdSerie);
                    break;
                case 3:
                    gerenciarUsuarios(raizFilmes, raizSeries, raizUsuarios, arqIdFilme, arqIdSerie, arqIdUsuario);
                    break;
                case 4:
                    iniciarResetar(raiz, raizFilmes, raizSeries, raizUsuarios, arqIdFilme, arqIdSerie, arqIdUsuario);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
            }
        } while (opcao != 5);
    }

    private static void gerenciarFilmes(String raizFilmes, String arqIdFilme) {
        Scanner sc = new Scanner(System.in);
        int opcao;
        do {
            menuSecundario("Filmes");
            opcao = sc.nextInt();
            switch (opcao) {
                case 1: //cadastrar
                    cadastrarFilme(raizFilmes, arqIdFilme);
                    break;
                case 2://buscar
                    break;
                case 3://listar
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

    private static void cadastrarFilme(String raizFilmes, String arqIdFilme) {
        int id = leId(arqIdFilme);
        Filme f = criaFilme(id);
        if (gravaFilme(f, raizFilmes)) {
            gravaId(++id, arqIdFilme);
        } else {
            System.out.println("Não foi possível gravar o contato!");
        }
    }

    private static Filme criaFilme(int id) {
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

    private static boolean gravaFilme(Filme f, String raizFilmes) {
        try {
            PrintWriter pw = new PrintWriter(raizFilmes + f.id); //o id é o nome do arquivo
            pw.append("ID: " + f.id + "\n");
            pw.append("Nome: " + f.nome + "\n");
            pw.append("Data: " + f.data.dia + "/" + f.data.mês + "/" + f.data.ano + "\n");
            pw.append("Duração: "+ f.tempo.horas + "h "+ f.tempo.minutos+ "m\n");
            for (int i = 0; i < 10; i++) {
                if (f.genero[i] != null) {
                    pw.append("Gênero " + (i + 1) + ": " + f.genero[i] + "\n");
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

    private static void gerenciarSeries(String raizSeries, String arqIdSerie) {
        Scanner sc = new Scanner(System.in);
        int opcao;
        do {
            menuSecundario("Séries");
            opcao = sc.nextInt();
            switch (opcao) {
                case 1: //cadastrar
                    break;
                case 2://buscar
                    break;
                case 3://listar
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

    private static void gerenciarUsuarios(String raizFilmes, String raizSeries, String raizUsuarios, String arqIdFilme, String arqIdSerie, String arqIdUsuario) {
        Scanner sc = new Scanner(System.in);
        int opcao;
        do {
            menuSecundario("Usuários");
            opcao = sc.nextInt();
            switch (opcao) {
                case 1: //cadastrar

                    break;
                case 2://buscar
                    break;
                case 3://listar
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

    private static void menuPrincipal() {
        System.out.println("\n-----------------------"
                + "\n1) Filmes"
                + "\n2) Séries"
                + "\n3) Usuários"
                + "\n4) Iniciar/Resetar"
                + "\n5) Sair"
                + "\n-----------------------");
        System.out.println("Opção: ");

    }

    private static void menuSecundario(String menu) {
        System.out.println("\n" + menu);
        System.out.println("-----------------------"
                + "\n1) Gravar"
                + "\n2) Buscar"
                + "\n3) Listar"
                + "\n4) Atualizar"
                + "\n5) Apagar"
                + "\n6) Voltar"
                + "\n-----------------------");
        System.out.println("Opção: ");

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
        String[] arquivos = dir.list();
        for (String s : arquivos) {
            File f = new File(s);
            f.delete();
        }
    }
}


